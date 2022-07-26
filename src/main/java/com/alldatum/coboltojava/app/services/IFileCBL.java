package com.alldatum.coboltojava.app.services;

import java.io.InputStream;
import java.util.List;

import com.alldatum.coboltojava.app.pojo.Attribute;

public interface IFileCBL {
	
	List<Attribute> attributes(InputStream file);
    String extractString(String stringText, int characters, int position);
    Long stringComp3(String stringText, int digits, int position) throws Exception;
    Long comp3(byte[] input) throws Exception;
    double bytesCalculate(float digits);
    List<String> values(InputStream fileDat)  throws Exception;
    
}
