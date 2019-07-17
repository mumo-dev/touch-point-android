package com.example.touchpoint.models;

import java.io.Serializable;

public class Contract implements Serializable {


    private int id;
    private String commercial_name;

    private String corporate_name;
    private String name_and_position_ccr;
    private String type_and_number_of_id;
    private String address;
    private String business_name;
    private String pin_number;
    private String reg_no_of_business;
    private String phone;
    private String email;
    private String name;
    private String created_at;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    private int user_id;
    private String signature_url;
    private String dob;
    private String date;


    public Contract() {
    }

    public Contract(String commercial_name, String corporate_name,
                    String name_and_position_ccr, String type_and_number_of_id, String address,
                    String business_name, String pin_number, String reg_no_of_business, String phone,
                    String email, String name, int userId, String dob, String date) {
        this.commercial_name = commercial_name;

        this.corporate_name = corporate_name;
        this.name_and_position_ccr = name_and_position_ccr;
        this.type_and_number_of_id = type_and_number_of_id;
        this.address = address;
        this.business_name = business_name;
        this.pin_number = pin_number;
        this.reg_no_of_business = reg_no_of_business;
        this.phone = phone;
        this.email = email;
        this.name = name;
        this.user_id = userId;
        this.dob = dob;
        this.date = date;
    }


    public String getCommercial_name() {
        return commercial_name;
    }

    public void setCommercial_name(String commercial_name) {
        this.commercial_name = commercial_name;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getCorporate_name() {
        return corporate_name;
    }

    public void setCorporate_name(String corporate_name) {
        this.corporate_name = corporate_name;
    }

    public String getName_and_position_ccr() {
        return name_and_position_ccr;
    }

    public void setName_and_position_ccr(String name_and_position_ccr) {
        this.name_and_position_ccr = name_and_position_ccr;
    }

    public String getType_and_number_of_id() {
        return type_and_number_of_id;
    }

    public void setType_and_number_of_id(String type_and_number_of_id) {
        this.type_and_number_of_id = type_and_number_of_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getPin_number() {
        return pin_number;
    }

    public void setPin_number(String pin_number) {
        this.pin_number = pin_number;
    }

    public String getReg_no_of_business() {
        return reg_no_of_business;
    }

    public void setReg_no_of_business(String reg_no_of_business) {
        this.reg_no_of_business = reg_no_of_business;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSignature_url() {
        return signature_url;
    }

    public void setSignature_url(String signature_url) {
        this.signature_url = signature_url;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
