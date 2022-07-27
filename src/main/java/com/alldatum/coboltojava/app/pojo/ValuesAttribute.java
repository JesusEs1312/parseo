package com.alldatum.coboltojava.app.pojo;

import java.util.ArrayList;
import java.util.List;

public class ValuesAttribute {
	
	private List<String> values;
	
	public ValuesAttribute() {
		this.values = new ArrayList<>();
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}
	
	public void addValue(String value) {
		this.values.add(value);
	}
	
	
}
