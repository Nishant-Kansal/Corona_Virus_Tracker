package com.nishant.coronvirustracker.coronavirustracker.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.nishant.coronvirustracker.coronavirustracker.models.CountryWiseStats;
import com.nishant.coronvirustracker.coronavirustracker.services.StateWiseDataService;

@Controller
public class CoronaVirusIndiaController {
	
	@Autowired
	private StateWiseDataService stateWiseData;
	
	@RequestMapping("/tracker/countryWise")
	public String country(Model model) {
		List<CountryWiseStats> countryWiseStats = stateWiseData.getStatewiseStats();
		int totalReportedCases = countryWiseStats.stream().mapToInt(stats -> Integer.valueOf(stats.getConfirmed())).sum();
		int dischargedCases = countryWiseStats.stream().mapToInt(stats -> Integer.valueOf(stats.getDischargedCases())).sum();
		int totalDeathReported = countryWiseStats.stream().mapToInt(stats -> Integer.valueOf(stats.getNoOfDeaths())).sum();
		int activeCases = totalReportedCases-dischargedCases;
		model.addAttribute("totalReportedCases", totalReportedCases);
		model.addAttribute("activeCases", activeCases);
		model.addAttribute("dischargedCases", dischargedCases);
		model.addAttribute("totalDeathReported", totalDeathReported);
		model.addAttribute("statewiseStats", countryWiseStats);
		return "country";
	}
}
