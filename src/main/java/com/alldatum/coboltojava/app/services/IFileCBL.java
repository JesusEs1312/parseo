package com.alldatum.coboltojava.app.services;

import java.io.InputStream;
import java.util.List;

import com.alldatum.coboltojava.app.pojo.Attribute;

public interface IFileCBL {
	
	List<Attribute> attributes(InputStream file);
    String extractString(String stringText, int characters, int position, boolean occurs, int bait, int vcampos, int comp3);
    Long stringComp3(String stringText, int digits, int position) throws Exception;
    double comp3decimal (String cadena, int digitoss9, int digitosv9, int posicion ) throws Exception;
    Long comp3(byte[] input) throws Exception;
    double bytesCalculate(float digits);
    List<String> values(InputStream fileDat)  throws Exception;
    
}
