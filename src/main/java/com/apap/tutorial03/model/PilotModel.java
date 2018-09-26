package com.apap.tutorial03.model;

public class PilotModel {
    private String id;
    private String licenseNumber;
    private String name;
    private Integer flyHour;

    public PilotModel(String id, String licenseNumber, String name, Integer flyhour) {
        this.id = id;
        this.licenseNumber = licenseNumber;
        this.name = name;
        this.flyHour = flyhour;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFlyHour() {
        return flyHour;
    }

    public void setFlyHour(Integer flyhour) {
        this.flyHour = flyhour;
    }
}
