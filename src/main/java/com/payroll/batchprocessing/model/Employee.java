package com.payroll.batchprocessing.model;


public class Employee {

    private String id;
    private String name;
    private Integer hours;
    private Double pay;

    public Employee() {
    }

    public Employee(String id, String name, Integer hours, Double pay) {
        this.id = id;
        this.name = name;
        this.hours = hours;
        this.pay = pay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHours() {
        return hours;
    }
    public void setHours(Integer hours) {
        this.hours = hours;
    }
    public void setPay(Double pay) {
        this.pay = pay;
    }
    public Double getPay() {
        return pay;
    }



    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", hours=" + hours +
                ", Pay=" + pay +
                '}';
    }
}

