package com.alldatum.coboltojava.app.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import com.alldatum.coboltojava.app.pojo.Attribute;
import com.alldatum.coboltojava.app.pojo.ValuesAttribute;

public interface IFileCBL {
	
	List<Attribute> attributes(InputStream file);
    String extractString( String stringText, int characters, int position, boolean occurs, int vcampos);
    Long stringComp3(String att, String stringText, int digits, int position) throws Exception;
    double comp3decimal (String att, String cadena, int digitoss9, int digitosv9, int posicion ) throws Exception;
    Long comp3(byte[] input) throws Exception;
    double bytesCalculate(float digits);
    List<String> values(InputStream fileDat)  throws Exception;
    HashMap<String, ValuesAttribute> mapKeysCBL(List<Attribute> attributes);
    HashMap<String, ValuesAttribute> mapValues(List<Attribute> attributes, List<String> blocks, HashMap<String, ValuesAttribute> mapKeysCBL);
    void readFileDAT(InputStream fileDAT) throws IOException;
}
