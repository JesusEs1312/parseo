package com.alldatum.coboltojava.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.naming.directory.AttributeInUseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alldatum.coboltojava.app.pojo.Attribute;
import com.alldatum.coboltojava.app.pojo.FileCBL;
import com.alldatum.coboltojava.app.pojo.ValuesAttribute;
import com.alldatum.coboltojava.app.pojo.Variables;
import com.alldatum.coboltojava.app.services.IFileCBLImpl;


@SpringBootApplication
public class CobolToJavaApplication implements CommandLineRunner{
	
	private static final Logger log = LoggerFactory.getLogger(CobolToJavaApplication.class);
	private System p;
	
	@Autowired
	private IFileCBLImpl iFileCBLImpl;
	
	public Attribute.DataType dtt = null;
	
	public static void main(String[] args) {
		SpringApplication.run(CobolToJavaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String hola="hola   hola";
		 Variables.bait=7;
		 InputStream file    = new FileInputStream("C:\\Users\\Alldatum Business\\Downloads\\CATEGOPL.CBL");
		 InputStream fileDat = new FileInputStream("C:\\Users\\Alldatum Business\\Downloads\\poliza4.dat");
		 FileCBL cblPoliza   = new FileCBL();
		 cblPoliza.setAttributes(iFileCBLImpl.attributes(file));
		 List<Attribute> attributes = cblPoliza.getAttributes();
		 HashMap<String, ValuesAttribute> mapValuesDAT = iFileCBLImpl.mapKeysCBL(attributes);
		 List<String> values = iFileCBLImpl.values(fileDat);
		 HashMap<String, ValuesAttribute> mapValues = iFileCBLImpl.mapValues(attributes, values, mapValuesDAT);
		 
//		 iFileCBLImpl.readFileDAT(fileDat);
		 
		 
		 
//		 for(String campoKey: mapValues.keySet()) {
//			 mapValues.get(campoKey).getValues().forEach(value ->{
//				 System.out.println(campoKey + " ----- " + value);
//			 });				 
//		 }
		 
		 attributes.forEach(attribute -> {
			 p.out.println(attribute.getName()
					 .concat(" ").concat(String.valueOf(attribute.getBytes()))
					 .concat(" ").concat(String.valueOf(attribute.getDataType()))
					 .concat(" ").concat(String.valueOf(attribute.getBytesDecimal()))
					 .concat(" ").concat(String.valueOf(attribute.getDataTypeList())));
		 });
		 
		 /*
		// int cadenaLength = 0;
		 values.forEach(cadena -> {
			 System.out.println(cadena);
			 int cadenaLength = 1;
			while(cadenaLength > 0) {
				 attributes.forEach(attribute -> {//--subramo
//					 System.out.println(attribute.getName());
					 mapValuesDAT.entrySet().forEach(campoKey -> {
//						 System.out.println(attribute.getName());
						 String value = "";
						 switch(attribute.getDataType()) {
						 case String:
//							 System.out.println(attribute.getName() + "String");
							if(campoKey.getKey() == attribute.getName()) {
								if(attribute.getDataType() == dtt) {
									Variables.bait++;
								}
								value = iFileCBLImpl.extractString(cadena, attribute.getBytes(), Variables.bait, false, Variables.vcampos);
								campoKey.getValue().addValue(value);
								//valueLength = value.length();  
							}
							break;
						 case Integer:
							if(campoKey.getKey() == attribute.getName()) {
								try {
									value = String.valueOf(iFileCBLImpl.stringComp3(attribute.getName(), cadena, attribute.getBytes(), Variables.bait));
									campoKey.getValue().addValue(value);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								//valueLength = value.length();  
							}
							break;
						 case Double:
								if(campoKey.getKey() == attribute.getName()) {
									try {
										value = String.valueOf(iFileCBLImpl.comp3decimal(attribute.getName(),cadena, attribute.getBytes(), attribute.getBytesDecimal(), Variables.bait));
										campoKey.getValue().addValue(value);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									//valueLength = value.length();  
								}
							break;
						 case List:
							 if(campoKey.getKey() == attribute.getName()) {
								 try {
									 value = iFileCBLImpl.extractString(cadena, attribute.getBytes(), Variables.bait, true, Variables.vcampos);
									 campoKey.getValue().addValue(value);
								 } catch (Exception e) {
									 // TODO Auto-generated catch block
									 e.printStackTrace();
								 }
								 //valueLength = value.length();  
							 }
							 break;
						 }					 
					 });
					 dtt = attribute.getDataType();
				 });
				 cadenaLength--;
			 }
		 });
		 */
		 
		 /*
		 for(String campoKey: mapValuesDAT.keySet()) {

//			 if(campoKey.equals("CATEGOPL-RAMSUBRAMO")) {

//			 if(campoKey.equals("POLIZA-RAMSUBRAMO") || campoKey.equals("POLIZA-IDCLIENTE")) {
				 mapValuesDAT.get(campoKey).getValues().forEach(value ->{
					 System.out.println(campoKey + " ----- " + value);
				 });				 
//			 }
//			 }
	   }*/
		 
//		 values.forEach(c -> {
//			 System.out.println(c);
//			 System.out.println(c.getBytes().length);
//			 System.out.println(c.length());
//		 });
		 
	}
}
