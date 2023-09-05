package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class Family {

    @SerializedName("id_family")
    private int idFamily;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("second_name")
    private String secondName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("birthdate")
    private String birthdate;

    @SerializedName("age")
    private int age;

    @SerializedName("number")
    private String number;

    @SerializedName("login")
    private String login;

    @SerializedName("password")
    private String password;

    public Family(int idFamily, String firstName, String secondName, String lastName, String birthdate, int age, String number, String login, String password) {
        this.idFamily = idFamily;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.age = age;
        this.number = number;
        this.login = login;
        this.password = password;
    }

    public int getIdFamily() {
        return idFamily;
    }

    public void setIdFamily(int idFamily) {
        this.idFamily = idFamily;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
