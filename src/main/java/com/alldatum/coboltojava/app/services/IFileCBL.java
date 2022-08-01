package com.alldatum.coboltojava.app.services;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import com.alldatum.coboltojava.app.pojo.Attribute;
import com.alldatum.coboltojava.app.pojo.ValuesAttribute;

public interface IFileCBL {
	
	List<Attribute> attributes(InputStream file);
    String extractString(String stringText, int characters, int position, boolean occurs, int vcampos, String tipoDeDato) throws Exception;
    Long stringComp3(String stringText, int digits, int position) throws Exception;
    String comp3decimal (String cadena, int digitoss9, int digitosv9, int posicion ) throws Exception;
    Long comp3(byte[] input) throws Exception;
    double bytesCalculate(float digits);
    List<String> values(InputStream fileDat)  throws Exception;
    HashMap<String, ValuesAttribute> mapKeysCBL(List<Attribute> attributes);
}
