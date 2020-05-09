package com.nishant.coronvirustracker.coronavirustracker.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.nishant.coronvirustracker.coronavirustracker.config.CovidConfig;
import com.nishant.coronvirustracker.coronavirustracker.constants.Constants;
import com.nishant.coronvirustracker.coronavirustracker.models.CountryWiseStats;

@Service
public class StateWiseDataService {
	
	@Autowired
	private CovidConfig config;

	private List<CountryWiseStats> allStatesData = new ArrayList<CountryWiseStats>();
	private static String COVID19_URL_INDIA = "https://www.mohfw.gov.in/";
	
	public List<CountryWiseStats> getStatewiseStats(){
		return allStatesData;
	}

	@PostConstruct
	@Scheduled(cron = "* * * * * *")
	public void getStateWiseData() throws IOException {
		List<CountryWiseStats> stateWiseStats = new ArrayList<CountryWiseStats>();
		Document doc = Jsoup.connect(COVID19_URL_INDIA).get();
		List<Map<String,String>> listMap = parseTable(doc, 0);
//		Map<String, String> finalMap= ((Map<String, String>) listMap).entrySet().stream()
//				.filter(x -> "Total number of confirmed cases in India".equals(x.getValue()) && 
//						"*States wise distribution is subject to further verification and reconciliation".equals(x.getValue()) && 
//						"*Our figures are being reconciled with ICMR".equals(x.getValue())).
//				collect(Collectors.toMap(Entry :: getKey, Entry :: getValue));
				
		for(Map<String,String> mapData : listMap) {
			if(!mapData.get(Constants.SERIAL_NO_CONSTANT).equals(Constants.SERIAL_NO_CONSTANT_1)
					&& !mapData.get(Constants.SERIAL_NO_CONSTANT).equals(Constants.SERIAL_NO_CONSTANT_2)
					&& !mapData.get(Constants.SERIAL_NO_CONSTANT).equals(Constants.SERIAL_NO_CONSTANT_3)) {
				CountryWiseStats statewiseData = new CountryWiseStats();
				List<String> list = mapData.entrySet().stream().map(Entry :: getValue).collect(Collectors.toList());
				statewiseData.setSerialNo(list.get(0));
				statewiseData.setDischargedCases(list.get(1));
				statewiseData.setConfirmed(list.get(2));
				statewiseData.setNoOfDeaths(list.get(3));
				statewiseData.setState(list.get(4));
				stateWiseStats.add(statewiseData);
			}
		}
		this.allStatesData = stateWiseStats;
	}

	public List<Map<String,String>> parseTable(Document doc, int tableOrder) {
		Element table = doc.select(Constants.TABLE).get(tableOrder);
		Elements rows = table.select(Constants.TABLE_ROW);
		Elements first = rows.get(0).select(Constants.TABLE_COLUMN);

		List<String> headers = new ArrayList<String>();
		for(Element header : first)
			headers.add(header.text());

		List<Map<String,String>> listMap = new ArrayList<Map<String,String>>();
		for(int row=1;row<rows.size();row++) {
			Elements colVals = rows.get(row).select(Constants.TABLE_COLUMN);
			//check column size here
			int colCount = 0;
			Map<String,String> tuple = new HashMap<String,String>();
			for(Element colVal : colVals)
				tuple.put(headers.get(colCount++), colVal.text());
			listMap.add(tuple);
		}
		return listMap;
	}

}
