package com.apap.tutorial03.service;

import com.apap.tutorial03.model.PilotModel;

import java.util.ArrayList;
import java.util.List;

public interface PilotService {
    void addPilot(PilotModel pilot);
    List<PilotModel> getPilotList();
    PilotModel getPilotDetailByLicenseNumber(String licenseNumber);
    boolean deletePilot(String id);
    ArrayList<String> getPilotDetailById(String id);
}
