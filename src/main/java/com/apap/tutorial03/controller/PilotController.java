package com.apap.tutorial03.controller;

import com.apap.tutorial03.model.PilotModel;
import com.apap.tutorial03.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * This class treat as the apps controller
 */
@Controller
public class PilotController {
    @Autowired
    private PilotService pilotService;

    /**
     * Mapping to handling operation of adding new pilot. The rest of adding process
     * have been handled by the service
     * @param id
     * @param licenseNumber
     * @param name
     * @param flyhour
     * @return
     */
    @RequestMapping("/pilot/add")
    public String add(@RequestParam(value = "id",required = true)String id,
                      @RequestParam(value = "licenseNumber" , required = true)String licenseNumber,
                      @RequestParam(value = "name",required = true)String name,
                      @RequestParam(value = "flyHour",required = true)int flyhour){
        PilotModel pilot = new PilotModel(id,licenseNumber,name,flyhour);
        pilotService.addPilot(pilot);
        return "add";
    }

    /**
     * This mapping is to show a pilots using require parameter ( license number)
     * Using parameter
     * @param licenseNumber
     * @param model
     * @return
     */
    @RequestMapping("/pilot/view")
    public String view(@RequestParam("licenseNumber")String licenseNumber, Model model) {
        PilotModel archieve = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
        model.addAttribute("pilot",archieve);
        model.addAttribute("title","View Detail");
        return "view-pilot";

    }

    /**
     * This mapping is to shows all pilots in the system
     * @param model
     * @return
     */
    @RequestMapping("/pilot/viewall")
    public String viewall(Model model){
        List<PilotModel> archieve = pilotService.getPilotList();

        model.addAttribute("listPilot",archieve);
        return "viewall-pilot";
    }

    /**
     * This mapping is to show a pilots using require parameter ( license number)
     * The different between this one and the one above is how we approach it.
     * This method's solution using PathVariable
     * @param licenseNumber
     * @param model
     * @return
     */
    @RequestMapping("/pilot/view/license-number/{licenseNumber}")
    public String viewPath(@PathVariable String licenseNumber , Model model){
        PilotModel archieve = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
        if (archieve!=null) {
            model.addAttribute("pilot", archieve);
            return "view-pilot";
        }else {
            String message = "License Number yang anda cari tidak ditemukan";
            model.addAttribute("errorMessage",message);
            return "error";
        }

    }

    /**
     * This mapping objective is update pilot fly hour information
     * Find the pilot with a license number. It will update the information if
     * the pilot are found , else it will shows an error page to tell whats wrong
     * @param licenseNumber
     * @param flyHour
     * @param model
     * @return
     */
    @RequestMapping("/pilot/update/license-number/{licenseNumber}/fly-hour/{flyHour}")
    public String update(@PathVariable String licenseNumber , @PathVariable String flyHour,Model model){
        boolean updatePointer = true;
        PilotModel archieve = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
        if (archieve!=null){
            archieve.setFlyHour(Integer.parseInt(flyHour));
            model.addAttribute("updatePointer",updatePointer);
            model.addAttribute("pilot",archieve);
            model.addAttribute("title","Update Pilot");
            return "view-pilot";
        }else {
            String message = "License Number yang anda cari tidak ditemukan, proses update dibatalkan";
            model.addAttribute("errorMessage",message);
            return "error";
        }

    }

    /**
     * This mapping objective is delete pilot using id
     *  Find the pilot with a license number. It will delete the pilot if
     *   the pilot are found , else it will shows an error page to tell whats wrong
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/pilot/delete/id/{id}")
    public String delete(@PathVariable String id , Model model){
        boolean deletePilot = pilotService.deletePilot(id);
        if (deletePilot){
            String idPilot = id;
            model.addAttribute("id",idPilot);

            return "pilot-delete";

        }
        String message = "Id yang anda cari tidak ditemukan, proses delete dibatalkan";
        model.addAttribute("errorMessage",message);
        return "error";

    }
}
