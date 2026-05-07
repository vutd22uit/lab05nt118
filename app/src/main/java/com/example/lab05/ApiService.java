package com.example.lab05;

import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("get_all_students.php")
    Call<List<Student>> getAllStudents();

    @GET("get_student_by_id.php")
    Call<Student> getStudentById(@Query("id") int id);

    @FormUrlEncoded
    @POST("insert_student.php")
    Call<ResponseBody> insertStudent(
            @Field("name") String name,
            @Field("age") int age,
            @Field("nclass") String studentClass
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(
            @Field("username") String username,
            @Field("password") String password
    );
}