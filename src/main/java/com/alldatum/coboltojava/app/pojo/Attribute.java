package com.alldatum.coboltojava.app.pojo;

import java.util.Objects;

public class Attribute {

    public enum DataType{String, Integer, Double, List;}
    private final DataType dataType;
    private Integer bytes;
    private String name;
    private boolean withComp;
    private boolean withDecimal;
    private Integer bytesDecimal;


	public Integer getBytesDecimal() { 
		return bytesDecimal; 
	}

    public void setBytesDecimal(Integer bytesDecimal) { 
    	this.bytesDecimal = bytesDecimal; 
    }
    
    public Attribute(DataType dataType) {
        this.dataType = Objects.requireNonNull(dataType);
    }

    public DataType getDataType() {
        return dataType;
    }

    public Integer getBytes() {
        return bytes;
    }

    public void setBytes(Integer bytes) {
        this.bytes = bytes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isWithComp() {
        return withComp;
    }

    public void setWithComp(boolean withComp) {
        this.withComp = withComp;
    }

    public boolean isWithDecimal() {
        return withDecimal;
    }

    public void setWithDecimal(boolean withDecimal) {
        this.withDecimal = withDecimal;
    }

}

