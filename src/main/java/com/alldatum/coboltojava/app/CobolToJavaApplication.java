package com.alldatum.coboltojava.app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

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
	
	@Autowired
	private IFileCBLImpl iFileCBLImpl;
	
	public static void main(String[] args) {
		SpringApplication.run(CobolToJavaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String hola="hola   hola";
		 Variables.bait=7;
		 long numero=0;
		 String subca="";

		 InputStream file = new FileInputStream("C:\\Users\\Alldatum Business\\Downloads\\POLIZA.CBL");
		 InputStream fileDat = new FileInputStream("C:\\Users\\Alldatum Business\\Downloads\\POLIZA.DAT");
		 /*String archivo = "catego1";
			String ruta = "c:\\users\\Alldatum Business\\"+archivo+"2.dat";
			 File file = new File(ruta);
			    // Si el archivo no existe es creado
			    if (!file.exists()) {
			        file.createNewFile();
			    }
			    FileWriter fw = new FileWriter(file);
			    BufferedWriter bw = new BufferedWriter(fw);
			    
			    
				long numero=0;
				int numero2=34, sumabytes=900;
				int i=0, bloque=0, s=0, reg=2, lin=0, grupo=0;
				String [] flujo = new String[35000000];
				String [] lineas = new String[35000000];
				String cadena="";
				String subca="", subca2="";
				String cadenaprueba="";
				char[] flujocharprueba = new char [100000000];
				
				for(int k=0; k<35000000; k++) {
					lineas[k]="";
				}

				InputStream ins = new FileInputStream("C:\\Users\\Alldatum Business\\Downloads\\poliza4.dat");
				//InputStream ins = new FileInputStream("c:\\users\\Alldatum Business\\Desktop\\POLIZA.DAT");
				 Scanner obj = new Scanner(ins);
		         Scanner entradaEscaner = new Scanner (System.in);
		         String entradaTeclado = "";
		         
		         while (obj.hasNextLine()) {
		        	 flujo[i]=obj.nextLine();
		        	 i++;
		             /*if(esMultiplo(i, 100000)) {
		             	System.out.println(".");
		             }*/
		        
		         //}

		 

		 FileCBL cblPoliza = new FileCBL();
		 cblPoliza.setAttributes(iFileCBLImpl.attributes(file));
		 List<Attribute> attributes = cblPoliza.getAttributes();
		 HashMap<String, ValuesAttribute> mapValuesDAT = iFileCBLImpl.mapKeysCBL(attributes);
		 List<String> values = iFileCBLImpl.values(fileDat);
		 
		 /*numero=iFileCBLImpl.stringComp3(flujo[numero2],5,2002);System.out.println("RAMSUBRAM="+numero);
			numero=iFileCBLImpl.stringComp3(flujo[numero2],7,Variables.bait);System.out.println("NPOLIZA"+numero);
			numero=iFileCBLImpl.stringComp3(flujo[numero2],5,Variables.bait);System.out.println("RAMSUBRAM1"+numero);
			numero=iFileCBLImpl.stringComp3(flujo[numero2],7,Variables.bait);System.out.println("NPOLORG"+numero);
			numero=iFileCBLImpl.stringComp3(flujo[numero2],7,Variables.bait);System.out.println("NPOLIZA1="+numero);
			subca=iFileCBLImpl.extractString(flujo[numero2],12,Variables.bait,false, Variables.vcampos);
			System.out.println("IDCLIENTE= "+ subca);
			numero=iFileCBLImpl.stringComp3(flujo[numero2],5,Variables.bait);System.out.println("CONSEC="+numero);
			numero=iFileCBLImpl.stringComp3(flujo[numero2],2,Variables.bait);System.out.println("STPOLIZA"+numero);
			numero=iFileCBLImpl.stringComp3(flujo[numero2],5,Variables.bait);System.out.println("RAMSUBRAM2"+numero);
			numero=iFileCBLImpl.stringComp3(flujo[numero2],7,Variables.bait);System.out.println("NPOLIZA2"+numero);
			numero=iFileCBLImpl.stringComp3(flujo[numero2],1,Variables.bait);System.out.println("FMAADMVA="+numero);
			numero=iFileCBLImpl.stringComp3(flujo[numero2],5,Variables.bait);System.out.println("RAMSUBRAM3="+numero);
			numero=iFileCBLImpl.stringComp3(flujo[numero2],7,Variables.bait);System.out.println("NPOLIZA3="+numero);
			numero=iFileCBLImpl.stringComp3(flujo[numero2],1,Variables.bait);System.out.println("CVCONTRAT"+numero);
			numero=iFileCBLImpl.stringComp3(flujo[numero2],7,Variables.bait);System.out.println("RENOVPOL"+numero);
			
			subca=iFileCBLImpl.extractString(flujo[numero2],70,Variables.bait+2,true,Variables.vcampos);
			System.out.println("colaseg= "+ subca);
			
			subca=iFileCBLImpl.extractString(flujo[numero2],50,Variables.bait+2,false,Variables.vcampos);
			System.out.println("contrat= "+ subca);
					
			subca=iFileCBLImpl.extractString(flujo[numero2],13,Variables.bait+2,false, Variables.vcampos);
			System.out.println("rfc= "+ subca);
			
			subca=iFileCBLImpl.extractString(flujo[numero2],50,Variables.bait+2,false,Variables.vcampos);
			System.out.println("calle= "+ subca);
			
			subca=iFileCBLImpl.extractString(flujo[numero2],6,Variables.bait+2,false,Variables.vcampos);
			System.out.println("el supuesto número= "+ subca);
			
			subca=iFileCBLImpl.extractString(flujo[numero2],25,Variables.bait+2,false,Variables.vcampos);
			System.out.println("colonia= "+ subca);
			
			subca=iFileCBLImpl.extractString(flujo[numero2],25,Variables.bait+2,false,Variables.vcampos);
			System.out.println("poblacion= "+ subca);
			
			numero=iFileCBLImpl.stringComp3(flujo[numero2],5,Variables.bait);System.out.println(numero);
			numero=iFileCBLImpl.stringComp3(flujo[numero2],2,Variables.bait);System.out.println(numero);
			
			subca=iFileCBLImpl.extractString(flujo[numero2],20,Variables.bait,true,Variables.vcampos);
			System.out.println("telefonos= "+ subca);
			
			subca=iFileCBLImpl.extractString(flujo[numero2],60,Variables.bait,false,Variables.vcampos);
			System.out.println("contacto= "+ subca);
			
			numero=iFileCBLImpl.stringComp3(flujo[numero2],1,Variables.bait);System.out.println("moneda= "+numero);
			numero=iFileCBLImpl.stringComp3(flujo[numero2],3,Variables.bait);System.out.println("nesqpago="+numero);*/
		 
//		 attributes.forEach(attri -> {
//			 System.out.println(attri.getName().concat(" ").concat(String.valueOf(attri.getBytes())).concat(" ").concat(String.valueOf(attri.getBytesDecimal())));
//		 });
		 
		 
		// int cadenaLength = 0;
		 
		 values.forEach(cadena -> {

			 System.out.println(cadena);
			 int cadenaLength = 1;

			 

			while(cadenaLength > 0) {
				 attributes.forEach(attribute -> {
//					 System.out.println(attribute.getName());
					 mapValuesDAT.entrySet().forEach(campoKey -> {
//						 System.out.println(attribute.getName());
						 String value = "";
						 switch(attribute.getDataType()) {
						 case String:
//							 System.out.println(attribute.getName() + "String");
							 try {
							if(campoKey.getKey() == attribute.getName()) {
								if(Variables.comp3==0 ) {
									Variables.bait+=2;
								}
								if( Variables.listasin==1) {
									Variables.bait-=2;
								}
								/*if(dataTypeAnt == attribute.getDataType()) {
									
								}*/
								if(attribute.getName().equals("POLIZA-COLONIA")) {
									 System.out.print("");
									 Variables.bait+=2;
								 }
								
								value = iFileCBLImpl.extractString(cadena, attribute.getBytes(), Variables.bait, false, Variables.vcampos, String.valueOf(attribute.getDataType()));
								campoKey.getValue().addValue(value);
								System.out.println(attribute.getName() +" "+ value);
							}
								//valueLength = value.length();  
							}catch(Exception e) {}
							break;
						 case Integer:
							if(campoKey.getKey() == attribute.getName()) {
								try {
									if(attribute.getName().equals("POLIZA-TPCOMIS")) {
										 System.out.print("");
										 Variables.bait++;
									 }
									if (Variables.decimal==1) {
										Variables.bait++;
									}
									value = String.valueOf(iFileCBLImpl.stringComp3(cadena, attribute.getBytes(), Variables.bait));
									campoKey.getValue().addValue(value);
									System.out.println(attribute.getName() +" "+ value);
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
										if(Variables.listaint==1) {
											Variables.bait+=1;
										}
										value = String.valueOf(iFileCBLImpl.comp3decimal(cadena, attribute.getBytes(), 2, Variables.bait+1));
										System.out.println(attribute.getName() +" "+ value);
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
									 if(attribute.getName().equals("POLIZA-NAGENTE-VEC")) {
										 System.out.print("");
										 
									 }
									 value = iFileCBLImpl.extractString(cadena, attribute.getBytes(), Variables.bait, true, Variables.vcampos, String.valueOf(attribute.getDataType()));
									 campoKey.getValue().addValue(value);
									 System.out.println(attribute.getName() +" "+ value);
								 } catch (Exception e) {
									 // TODO Auto-generated catch block
									 e.printStackTrace();
								 }
								 //valueLength = value.length();  
							 }
							 break;
						 }					 
					 });
					  
				 });
				 cadenaLength--;
			 }
		 });
		 
		 for(String campoKey: mapValuesDAT.keySet()) {

			 //if(campoKey.equals("POLIZA-RAMSUBRAMO")||campoKey.equals("POLIZA-NPOLIZA")||campoKey.equals("POLIZA-RAMSUBRAM1")||campoKey.equals("POLIZA-NPOLORG")||campoKey.equals("POLIZA-NPOLIZA1")||campoKey.equals("POLIZA-IDCLIENTE")||campoKey.equals("POLIZA-CONSEC")||campoKey.equals("POLIZA-STPOLIZA")||campoKey.equals("POLIZA-RAMSUBRAM2")||campoKey.equals("POLIZA-NPOLIZA2")||campoKey.equals("POLIZA-FMAADMVA")) {


			 if(campoKey.equals("CATEGOPL-DESCRIP-VEC")) {

			// if(campoKey.equals("POLIZA-RAMSUBRAMO")||campoKey.equals("POLIZA-NPOLIZA")||campoKey.equals("POLIZA-RAMSUBRAM1")||campoKey.equals("POLIZA-IDCLIENTE")) {

				 mapValuesDAT.get(campoKey).getValues().forEach(value ->{
					 System.out.println(campoKey + " ----- " + value);
				 });				 

			 }
		 //}

//			 }
		 }
	   }

	}


