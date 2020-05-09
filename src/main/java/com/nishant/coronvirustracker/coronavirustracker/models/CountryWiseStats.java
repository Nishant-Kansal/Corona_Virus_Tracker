package com.nishant.coronvirustracker.coronavirustracker.models;

public class CountryWiseStats {

	private String serialNo;
	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDischargedCases() {
		return dischargedCases;
	}

	public void setDischargedCases(String dischargedCases) {
		this.dischargedCases = dischargedCases;
	}

	public String getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(String confirmed) {
		this.confirmed = confirmed;
	}

	public String getNoOfDeaths() {
		return noOfDeaths;
	}

	public void setNoOfDeaths(String noOfDeaths) {
		this.noOfDeaths = noOfDeaths;
	}

	private String state;
	private String dischargedCases;
	private String confirmed;
	private String noOfDeaths;

	@Override
	public String toString() {
		return "StatewiseStats [serialNo=" + serialNo + ", state=" + state + ", dischargedCases=" + dischargedCases
				+ ", confirmed=" + confirmed + ", noOfDeaths=" + noOfDeaths + "]";
	}
	}
