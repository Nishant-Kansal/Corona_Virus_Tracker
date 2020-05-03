package com.nishant.coronvirustracker.coronavirustracker.models;

public class LocationStats {

	private String state;
	private String country;
	private int latestTotalCases;
	private int diffFromPrevDay;
	private int noOfDeaths;
	
	public int getNoOfDeaths() {
		return noOfDeaths;
	}
	public void setNoOfDeaths(int noOfDeaths) {
		this.noOfDeaths = noOfDeaths;
	}
	public int getDiffFromPrevDay() {
		return diffFromPrevDay;
	}
	public void setDiffFromPrevDay(int diffFromPrevDay) {
		this.diffFromPrevDay = diffFromPrevDay;
	}
	public int getLatestTotalCases() {
		return latestTotalCases;
	}
	public void setLatestTotalCases(int latestTotalCases) {
		this.latestTotalCases = latestTotalCases;
	}
	public String getState() {
		return state;
	}
	@Override
	public String toString() {
		return "LocationStats [state=" + state + ", country=" + country + ", totalCases=" + latestTotalCases + "]";
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
}
