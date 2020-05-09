package com.nishant.coronvirustracker.coronavirustracker.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.nishant.coronvirustracker.coronavirustracker.config.CovidConfig;
import com.nishant.coronvirustracker.coronavirustracker.constants.Constants;
import com.nishant.coronvirustracker.coronavirustracker.models.LocationStats;

@Service
public class CoronoTrackerService {
	
	@Autowired
	private CovidConfig config;

	private static Logger log = Logger.getLogger(CoronoTrackerService.class);
	
	private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	private static String VIRUS_DEATH_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";
	private List<LocationStats> allStats = new ArrayList<LocationStats>();

	public List<LocationStats> getLocationStats() {
		return allStats;
	}

	@PostConstruct
	@Scheduled(cron = "* * * * * *")
	public void coronaVirusTracker() throws Exception {
		List<LocationStats> newlocationStats = new ArrayList<LocationStats>();
		StringBuffer confirmedCasesData = getVirusData(VIRUS_DATA_URL);
		Reader confirmedCasesReader = new StringReader(confirmedCasesData.toString());
		Iterable<CSVRecord> confirmRecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(confirmedCasesReader);
		for (CSVRecord record : confirmRecords) {
			LocationStats locStat = new LocationStats();
			locStat.setState(record.get(Constants.PROVINCE_STATE));
			locStat.setCountry(record.get(Constants.COUNTRY_REGION));
			int latestCases = Integer.parseInt(record.get((record.size()-1)));
			int prevDayCases = Integer.parseInt(record.get((record.size()-2)));
			locStat.setLatestTotalCases(latestCases);
			locStat.setDiffFromPrevDay(latestCases-prevDayCases);
			newlocationStats.add(locStat);
		}
		StringBuffer deathData = getVirusData(VIRUS_DEATH_DATA_URL);
		Reader deathReader = new StringReader(deathData.toString());
		Iterable<CSVRecord> deathRecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(deathReader);
		for (CSVRecord deathRecord : deathRecords) {
			for(LocationStats locStat : newlocationStats) {
				if(locStat.getCountry().equalsIgnoreCase(deathRecord.get(Constants.COUNTRY_REGION))) {
					locStat.setNoOfDeaths(Integer.parseInt(deathRecord.get((deathRecord.size()-1))));
				}
			}
		}
		this.allStats= newlocationStats;
	}

	public StringBuffer getVirusData(String url) throws Exception {
		StringBuffer response = null;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		int responseCode = con.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
				response.append("\r");
			}
			in.close();
		}
		else {
			log.info("GET request not worked");
		}
		return response;
	}


}
