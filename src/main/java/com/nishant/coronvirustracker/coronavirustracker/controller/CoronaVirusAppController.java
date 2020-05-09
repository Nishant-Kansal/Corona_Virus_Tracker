package com.nishant.coronvirustracker.coronavirustracker.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.nishant.coronvirustracker.coronavirustracker.models.LocationStats;
import com.nishant.coronvirustracker.coronavirustracker.services.CoronoTrackerService;


@Controller
public class CoronaVirusAppController {
	
	@Autowired
	private CoronoTrackerService coronoTrackerService;
	
	@RequestMapping("/tracker")
	public String global(Model model) {
		List<LocationStats> locationStats = coronoTrackerService.getLocationStats();
		int totalReportedCases = locationStats.stream().mapToInt(stats -> stats.getLatestTotalCases()).sum();
		int newReportedCases = locationStats.stream().mapToInt(stats -> stats.getDiffFromPrevDay()).sum();
		int totalDeathReported = locationStats.stream().mapToInt(stats -> stats.getNoOfDeaths()).sum();
		model.addAttribute("locationStats",locationStats);
		model.addAttribute("totalReportedCases", totalReportedCases);
		model.addAttribute("newReportedCases", newReportedCases);
		model.addAttribute("totalDeathReported", totalDeathReported);
		return "global";
	}

}
