package com.example.lab05;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etFullname;
    private Button btnRegister;
    private TextView tvLoginLink;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DatabaseHelper(this);

        etFullname = findViewById(R.id.et_fullname);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnRegister = findViewById(R.id.btn_register);
        tvLoginLink = findViewById(R.id.tv_login_link);

        btnRegister.setOnClickListener(v -> {
            String fullname = etFullname.getText().toString().trim();
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (fullname.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                boolean success = dbHelper.registerUser(username, password, fullname);
                if (success) {
                    Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    finish(); // Go back to Login
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvLoginLink.setOnClickListener(v -> finish());
    }
}