package com.example.coronavirustracker.controllers;

import com.example.coronavirustracker.models.LocationStats;
import com.example.coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model) {
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        String totalCase = String.valueOf(totalReportedCases);
        String totalCases = "";
        for(int i = totalCase.length() - 1; i >= 0; i--) {
            totalCases = String.valueOf(totalCase.charAt(i)) + totalCases;
            if(i % 3 == 0 && i > 0) totalCases = "," + totalCases;
        }

        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        String newCase = String.valueOf(totalNewCases);
        String newCases = "";
        for(int i = newCase.length() - 1; i >= 0; i--) {
            newCases = String.valueOf(newCase.charAt(i)) + newCases;
            if(i % 3 == 0 && i > 0) newCases = "," + newCases;
        }

        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalCases);
        model.addAttribute("totalNewCases", newCases);

        return "home";
    }
}
