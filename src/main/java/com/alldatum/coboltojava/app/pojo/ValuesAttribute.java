package com.alldatum.coboltojava.app.pojo;

import java.util.List;

public class ValuesAttribute<T> {
	
	private List<T> values;

	public List<T> getValues() {
		return values;
	}

	public void setValues(List<T> values) {
		this.values = values;
	}
	
	
}
