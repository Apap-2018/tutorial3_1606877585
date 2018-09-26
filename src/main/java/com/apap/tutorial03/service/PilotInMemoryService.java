package com.apap.tutorial03.service;

import com.apap.tutorial03.model.PilotModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class is to manage all of the pilot. In general it has 4 control variable
 * A list to hold all the instance of new pilot added to the system,
 * A hashmap to save the index of list that contain pilot of certain license number,
 * A Hashmap to save the index and license number of a pilot that has an id,
 * A counter to track where a pilot is saved.
 */
@Service
public class PilotInMemoryService implements PilotService {
    private static List<PilotModel> archivePilot = new ArrayList<>() ;
    private static HashMap<String,Integer> mapping = new HashMap<>();
    private static HashMap<String,ArrayList<String>> nameCollector = new HashMap<>();
    private static int counter = 0;


    /**
     * First it check the license number whether the system have a pilot with that
     * license number or not. If not , add to system.
     * Content Arraylist to save the license number and index of array where it is saved
     * with their id served as the key to save the arraylist
     * Next it saved the index in mapping hashmap with license number as the key.
     * The objective of both hashmap is to make searching a lot faster and easier.
     * If the system has pilot with the license number , just save it straight to the list.
     * The system will return the first pilot with the number later in searching.
     * @param pilot Pilot that want to be added to the system
     */
    @Override
    public void addPilot(PilotModel pilot) {
        if (getPilotDetailByLicenseNumber(pilot.getLicenseNumber())==null) {
            ArrayList<String> content = new ArrayList<>();
            archivePilot.add(counter, pilot);
            content.add(pilot.getLicenseNumber());
            content.add(Integer.toString(counter));
            nameCollector.put(pilot.getId(),content);
            mapping.put(pilot.getLicenseNumber(), counter++);
        }else {
            archivePilot.add(counter++,pilot);

        }

    }


    /**
     *
     * @return The List contains all of the pilot
     */
    @Override
    public List<PilotModel> getPilotList() {
        return archivePilot;
    }

    /**
     * This method objective is to return pilot with certain license number.
     * It will checked using the mapping hashmap. Return null if it is not found
     * @param licenseNumber
     * @return
     */
    @Override
    public PilotModel getPilotDetailByLicenseNumber(String licenseNumber) {
        if (mapping.get(licenseNumber)!=null) {
            return archivePilot.get(mapping.get(licenseNumber));
        }else {
            return null;
        }
    }

    /**
     * This method objective is to return arraylist containing informatio of pilot
     * with certain id.
     * It will checked using the nameCollector hashmap. Return null if none  found
     * @param id
     * @return
     */
    @Override
    public ArrayList<String> getPilotDetailById(String id){
        if (nameCollector.get(id)!=null) {
            return nameCollector.get(id);
        }else {
            return null;
        }

    }

    /**
     * This method intended to delete a pilot with certain id.
     * Remove the instance from list and all record on the hashmap.
     * Decrease the counter as the size of list reduced.
     * @param id
     * @return true if success , otherwise false
     */
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
