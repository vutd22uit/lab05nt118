package com.example.lab05;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView tvResult;
    private Button btnGetById, btnGetAll, btnInsert;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tv_result);
        btnGetById = findViewById(R.id.btn_get_by_id);
        btnGetAll = findViewById(R.id.btn_get_all);
        btnInsert = findViewById(R.id.btn_insert);

        // Khởi tạo Retrofit kết nối tới WebService local (XAMPP)
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2/lab05/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        btnGetById.setOnClickListener(v -> getStudentById(1));
        btnGetAll.setOnClickListener(v -> getAllStudents());
        btnInsert.setOnClickListener(v -> showInsertDialog());
    }

    private void showInsertDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_insert_student, null);
        EditText etName = dialogView.findViewById(R.id.et_student_name);
        EditText etAge = dialogView.findViewById(R.id.et_student_age);
        EditText etClass = dialogView.findViewById(R.id.et_student_class);

        new AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("INSERT", (dialog, which) -> {
                    String name = etName.getText().toString().trim();
                    String ageStr = etAge.getText().toString().trim();
                    String sClass = etClass.getText().toString().trim();

                    if (!name.isEmpty() && !ageStr.isEmpty() && !sClass.isEmpty()) {
                        try {
                            int age = Integer.parseInt(ageStr);
                            insertStudent(name, age, sClass);
                        } catch (NumberFormatException e) {
                            Toast.makeText(this, "Tuổi phải là số", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void getStudentById(int id) {
        apiService.getStudentById(id).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Student s = response.body();
                    String result = "ID: " + s.getId() + "\n" +
                            "Name: " + s.getName() + "\n" +
                            "Age: " + s.getAge() + "\n" +
                            "Class: " + s.getStudentClass();
                    tvResult.setText(result);
                } else {
                    tvResult.setText("Student not found");
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                tvResult.setText("Error: " + t.getMessage());
            }
        });
    }

    private void getAllStudents() {
        apiService.getAllStudents().enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Student> students = response.body();
                    StringBuilder sb = new StringBuilder();
                    for (Student s : students) {
                        sb.append("ID: ").append(s.getId()).append("\n")
                          .append("Name: ").append(s.getName()).append("\n")
                          .append("Age: ").append(s.getAge()).append("\n")
                          .append("Class: ").append(s.getStudentClass()).append("\n\n");
                    }
                    tvResult.setText(sb.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                tvResult.setText("Error: " + t.getMessage());
            }
        });
    }

    private void insertStudent(String name, int age, String studentClass) {
        apiService.insertStudent(name, age, studentClass).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Insert thành công!", Toast.LENGTH_SHORT).show();
                    getAllStudents();
                } else {
                    Toast.makeText(MainActivity.this, "Insert thất bại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
