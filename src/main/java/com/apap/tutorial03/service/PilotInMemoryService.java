package com.apap.tutorial03.service;

import com.apap.tutorial03.model.PilotModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class PilotInMemoryService implements PilotService {
    private static List<PilotModel> archivePilot = new ArrayList<>() ;
    private static HashMap<String,Integer> mapping = new HashMap<>();
    private static HashMap<String,ArrayList<String>> nameCollector = new HashMap<>();
    private static int counter = 0;



    @Override
    public void addPilot(PilotModel pilot) {
        if (getPilotDetailByLicenseNumber(pilot.getLicenseNumber())==null) {
            System.out.println("Normally added");
            ArrayList<String> content = new ArrayList<>();
            archivePilot.add(counter, pilot);
            content.add(pilot.getLicenseNumber());
            content.add(Integer.toString(counter));
            nameCollector.put(pilot.getId(),content);
            mapping.put(pilot.getLicenseNumber(), counter++);
        }else {
            archivePilot.add(counter++,pilot);
            System.out.println("Alternate added");
        }

    }



    @Override
    public List<PilotModel> getPilotList() {
        return archivePilot;
    }

    @Override
    public PilotModel getPilotDetailByLicenseNumber(String licenseNumber) {
        if (mapping.get(licenseNumber)!=null) {
            return archivePilot.get(mapping.get(licenseNumber));
        }else {
            return null;
        }
    }
    @Override
    public ArrayList<String> getPilotDetailById(String id){
        if (nameCollector.get(id)!=null) {
            return nameCollector.get(id);
        }else {
            return null;
        }

    }

    @Override
    public boolean deletePilot(String id) {
       ArrayList<String> content = getPilotDetailById(id);
        if (content!=null){
            mapping.remove(content.get(0));
            nameCollector.remove(id);
            archivePilot.remove(Integer.parseInt(content.get(1)));
            counter--;
            return true;
        }
        return false;
    }


}
