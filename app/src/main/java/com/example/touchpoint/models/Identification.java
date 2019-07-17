package com.example.touchpoint.models;

import java.io.Serializable;

public class Identification implements Serializable {

    private int id;
    private String salesagent_name;
    private String salesagent_zone;
    private String salesagent_phone;
    private String pos_name;
    private String pos_address;
    private String owner_name;
    private String owner_phone;
    private String businesspermit_number;
    private String kra_pin;
    private String supervisor_name;
    private String supervisor_phone;
    private String number_cni_supervisor;
    private String cashier1_name;
    private String cashier1_phone;
    private String cni_cashier1;
    private String cashier2_name;
    private String cashier2_phone;
    private String cni_cashier2;
    private String cashier3_name;
    private String device_imei;
    private String device_serial_no;
    private String surface_room;
    private String products_type;
    private String core_business;
    private String secondary_activity;
    private int employees_no;
    private boolean waiting_room;
    private boolean computer_available;
    private String services_to_market;
    private String payment_phone_number;
    private String payment_amount;
    private String payment_goods_refno;
    private String back_image_url;
    private String front_image_url;
    private String topology_of_point;
    private int user_id;
    private String created_at;


    public Identification() {
    }

    public Identification(String salesagent_name, String salesagent_zone, String salesagent_phone,
                          String pos_name, String pos_address, String owner_name, String owner_phone,
                          String businesspermit_number, String kra_pin, String supervisor_name,
                          String supervisor_phone, String number_cni_supervisor, String cashier1_name,
                          String cashier1_phone, String cni_cashier1, String cashier2_name,
                          String cashier2_phone, String cni_cashier2, String cashier3_name, String device_imei,
                          String device_serial_no, String surface_room, String products_type,
                          String core_business, String secondary_activity,
                          int employees_no, boolean waiting_room, boolean computer_available,
                          String services_to_market, String payment_phone_number, String payment_amount,
                          String payment_goods_refno, String topology_of_point) {
        this.salesagent_name = salesagent_name;
        this.salesagent_zone = salesagent_zone;
        this.salesagent_phone = salesagent_phone;
        this.pos_name = pos_name;
        this.pos_address = pos_address;
        this.owner_name = owner_name;
        this.owner_phone = owner_phone;
        this.businesspermit_number = businesspermit_number;
        this.kra_pin = kra_pin;
        this.supervisor_name = supervisor_name;
        this.supervisor_phone = supervisor_phone;
        this.number_cni_supervisor = number_cni_supervisor;
        this.cashier1_name = cashier1_name;
        this.cashier1_phone = cashier1_phone;
        this.cni_cashier1 = cni_cashier1;
        this.cashier2_name = cashier2_name;
        this.cashier2_phone = cashier2_phone;
        this.cni_cashier2 = cni_cashier2;
        this.cashier3_name = cashier3_name;
        this.device_imei = device_imei;
        this.device_serial_no = device_serial_no;
        this.surface_room = surface_room;
        this.products_type = products_type;
        this.core_business = core_business;
        this.secondary_activity = secondary_activity;
        this.employees_no = employees_no;
        this.waiting_room = waiting_room;
        this.computer_available = computer_available;
        this.services_to_market = services_to_market;
        this.payment_phone_number = payment_phone_number;
        this.payment_amount = payment_amount;
        this.payment_goods_refno = payment_goods_refno;
        this.topology_of_point = topology_of_point;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSalesagent_name() {
        return salesagent_name;
    }

    public void setSalesagent_name(String salesagent_name) {
        this.salesagent_name = salesagent_name;
    }

    public String getSalesagent_zone() {
        return salesagent_zone;
    }

    public void setSalesagent_zone(String salesagent_zone) {
        this.salesagent_zone = salesagent_zone;
    }

    public String getSalesagent_phone() {
        return salesagent_phone;
    }

    public void setSalesagent_phone(String salesagent_phone) {
        this.salesagent_phone = salesagent_phone;
    }

    public String getPos_name() {
        return pos_name;
    }

    public void setPos_name(String pos_name) {
        this.pos_name = pos_name;
    }

    public String getPos_address() {
        return pos_address;
    }

    public void setPos_address(String pos_address) {
        this.pos_address = pos_address;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getOwner_phone() {
        return owner_phone;
    }

    public void setOwner_phone(String owner_phone) {
        this.owner_phone = owner_phone;
    }

    public String getBusinesspermit_number() {
        return businesspermit_number;
    }

    public void setBusinesspermit_number(String businesspermit_number) {
        this.businesspermit_number = businesspermit_number;
    }

    public String getKra_pin() {
        return kra_pin;
    }

    public void setKra_pin(String kra_pin) {
        this.kra_pin = kra_pin;
    }

    public String getSupervisor_name() {
        return supervisor_name;
    }

    public void setSupervisor_name(String supervisor_name) {
        this.supervisor_name = supervisor_name;
    }

    public String getSupervisor_phone() {
        return supervisor_phone;
    }

    public void setSupervisor_phone(String supervisor_phone) {
        this.supervisor_phone = supervisor_phone;
    }

    public String getNumber_cni_supervisor() {
        return number_cni_supervisor;
    }

    public void setNumber_cni_supervisor(String number_cni_supervisor) {
        this.number_cni_supervisor = number_cni_supervisor;
    }

    public String getCashier1_name() {
        return cashier1_name;
    }

    public void setCashier1_name(String cashier1_name) {
        this.cashier1_name = cashier1_name;
    }

    public String getCashier1_phone() {
        return cashier1_phone;
    }

    public void setCashier1_phone(String cashier1_phone) {
        this.cashier1_phone = cashier1_phone;
    }

    public String getCni_cashier1() {
        return cni_cashier1;
    }

    public void setCni_cashier1(String cni_cashier1) {
        this.cni_cashier1 = cni_cashier1;
    }

    public String getCashier2_name() {
        return cashier2_name;
    }

    public void setCashier2_name(String cashier2_name) {
        this.cashier2_name = cashier2_name;
    }

    public String getCashier2_phone() {
        return cashier2_phone;
    }

    public void setCashier2_phone(String cashier2_phone) {
        this.cashier2_phone = cashier2_phone;
    }

    public String getCni_cashier2() {
        return cni_cashier2;
    }

    public void setCni_cashier2(String cni_cashier2) {
        this.cni_cashier2 = cni_cashier2;
    }

    public String getCashier3_name() {
        return cashier3_name;
    }

    public void setCashier3_name(String cashier3_name) {
        this.cashier3_name = cashier3_name;
    }

    public String getDevice_imei() {
        return device_imei;
    }

    public void setDevice_imei(String device_imei) {
        this.device_imei = device_imei;
    }

    public String getDevice_serial_no() {
        return device_serial_no;
    }

    public void setDevice_serial_no(String device_serial_no) {
        this.device_serial_no = device_serial_no;
    }

    public String getSurface_room() {
        return surface_room;
    }

    public void setSurface_room(String surface_room) {
        this.surface_room = surface_room;
    }

    public String getProducts_type() {
        return products_type;
    }

    public void setProducts_type(String products_type) {
        this.products_type = products_type;
    }

    public String getCore_business() {
        return core_business;
    }

    public void setCore_business(String core_business) {
        this.core_business = core_business;
    }

    public String getSecondary_activity() {
        return secondary_activity;
    }

    public void setSecondary_activity(String secondary_activity) {
        this.secondary_activity = secondary_activity;
    }

    public int getEmployees_no() {
        return employees_no;
    }

    public void setEmployees_no(int employees_no) {
        this.employees_no = employees_no;
    }

    public boolean isWaiting_room() {
        return waiting_room;
    }

    public void setWaiting_room(boolean waiting_room) {
        this.waiting_room = waiting_room;
    }

    public boolean isComputer_available() {
        return computer_available;
    }

    public void setComputer_available(boolean computer_available) {
        this.computer_available = computer_available;
    }

    public String getServices_to_market() {
        return services_to_market;
    }

    public void setServices_to_market(String services_to_market) {
        this.services_to_market = services_to_market;
    }

    public String getPayment_phone_number() {
        return payment_phone_number;
    }

    public void setPayment_phone_number(String payment_phone_number) {
        this.payment_phone_number = payment_phone_number;
    }

    public String getPayment_amount() {
        return payment_amount;
    }

    public void setPayment_amount(String payment_amount) {
        this.payment_amount = payment_amount;
    }

    public String getPayment_goods_refno() {
        return payment_goods_refno;
    }

    public void setPayment_goods_refno(String payment_goods_refno) {
        this.payment_goods_refno = payment_goods_refno;
    }



    public String getTopology_of_point() {
        return topology_of_point;
    }

    public void setTopology_of_point(String topology_of_point) {
        this.topology_of_point = topology_of_point;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getBack_image_url() {
        return back_image_url;
    }

    public void setBack_image_url(String back_image_url) {
        this.back_image_url = back_image_url;
    }

    public String getFront_image_url() {
        return front_image_url;
    }

    public void setFront_image_url(String front_image_url) {
        this.front_image_url = front_image_url;
    }
}
