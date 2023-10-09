package com.example.springBatch.Entities;

public class Orginizations {
	private int index;
	private String name;
	private String country;
	private String industry;

	public Orginizations() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Orginizations(int index, String name, String country, String industry) {
		super();
		this.index = index;
		this.name = name;
		this.country = country;
		this.industry = industry;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

}
