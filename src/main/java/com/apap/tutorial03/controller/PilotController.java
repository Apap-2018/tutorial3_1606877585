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

@Controller
public class PilotController {
    @Autowired
    private PilotService pilotService;

    @RequestMapping("/pilot/add")
    public String add(@RequestParam(value = "id",required = true)String id,
                      @RequestParam(value = "licenseNumber" , required = true)String licenseNumber,
                      @RequestParam(value = "name",required = true)String name,
                      @RequestParam(value = "flyHour",required = true)int flyhour){
        PilotModel pilot = new PilotModel(id,licenseNumber,name,flyhour);
        pilotService.addPilot(pilot);
        return "add";
    }
    @RequestMapping("/pilot/view")
    public String view(@RequestParam("licenseNumber")String licenseNumber, Model model) {
        PilotModel archieve = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
        System.out.println(archieve==null);

        model.addAttribute("pilot",archieve);
        model.addAttribute("title","View Detail");
        return "view-pilot";

    }
    @RequestMapping("/pilot/viewall")
    public String viewall(Model model){
        List<PilotModel> archieve = pilotService.getPilotList();

        model.addAttribute("listPilot",archieve);
        return "viewall-pilot";
    }

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
