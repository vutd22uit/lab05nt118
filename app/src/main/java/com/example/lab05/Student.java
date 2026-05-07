package com.example.lab05;

import com.google.gson.annotations.SerializedName;

public class Student {
    private int id;
    private String name;
    private int age;
    
    @SerializedName("nclass") // Đã đổi từ "class" thành "nclass" để khớp với MySQL
    private String studentClass;

    public Student(int id, String name, int age, String studentClass) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.studentClass = studentClass;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getStudentClass() {
        return studentClass;
    }
}