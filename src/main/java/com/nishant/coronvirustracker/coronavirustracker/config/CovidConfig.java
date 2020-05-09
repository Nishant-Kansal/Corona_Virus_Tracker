package com.nishant.coronvirustracker.coronavirustracker.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "covidUrl")
public class CovidConfig {
	
	private String VIRUS_DATA_URL;
	private String VIRUS_DEATH_DATA_URL;
	private String INDIA_COVID19_DATA;
	
	public String getVIRUS_DATA_URL() {
		return VIRUS_DATA_URL;
	}
	public void setVIRUS_DATA_URL(String vIRUS_DATA_URL) {
		VIRUS_DATA_URL = vIRUS_DATA_URL;
	}
	public String getVIRUS_DEATH_DATA_URL() {
		return VIRUS_DEATH_DATA_URL;
	}
	public void setVIRUS_DEATH_DATA_URL(String vIRUS_DEATH_DATA_URL) {
		VIRUS_DEATH_DATA_URL = vIRUS_DEATH_DATA_URL;
	}
	public String getINDIA_COVID19_DATA() {
		return INDIA_COVID19_DATA;
	}
	public void setINDIA_COVID19_DATA(String iNDIA_COVID19_DATA) {
		INDIA_COVID19_DATA = iNDIA_COVID19_DATA;
	}
}
