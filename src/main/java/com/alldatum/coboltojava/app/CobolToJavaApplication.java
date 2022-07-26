package com.alldatum.coboltojava.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alldatum.coboltojava.app.pojo.Attribute;
import com.alldatum.coboltojava.app.pojo.FileCBL;
import com.alldatum.coboltojava.app.pojo.ValuesAttribute;
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
		 InputStream file = new FileInputStream("C:\\Users\\Alldatum Business\\Downloads\\POLIZA.CBL");
		 InputStream fileDat = new FileInputStream("C:\\Users\\Alldatum Business\\Downloads\\poliza4 (1).dat");
		 File filePrueba = new File("C:\\Users\\Alldatum Business\\Downloads\\poliza4 (1).dat");
		 FileCBL cblPoliza = new FileCBL();
		 HashMap<String, ValuesAttribute<Attribute.DataType>> mapa = new HashMap<>();
		 cblPoliza.setAttributes(iFileCBLImpl.attributes(file));
		 
		 /*
		 cblPoliza.getAttributes().forEach(attribute -> {
			 String dataType = String.valueOf(attribute.getDataType());
			 mapa.put(attribute.getName(), new ValuesAttribute<Attribute.DataType>());
		 });
		 
		 
		 List<Attribute> attributeList = cblPoliza.getAttributes().stream()
				 	.filter(attribute -> attribute.getName().equalsIgnoreCase("POLIZA-CONTRAT"))
	                .collect(Collectors.toList());
		 
		 attributeList.forEach(
				 attribute -> {
					 long numero   = 0;
	                 String cadena = "";
	                 String subca  = "";
	                 int vcampos   = 0;
	                 int bait      = 0;
	                 try {
	                	 numero = iFileCBLImpl.stringComp3(cadena,attribute.getBytes(),12);
	                 } catch (Exception e) {
	                     e.printStackTrace();
	                 }
//	                 cadena= "Â¿Â¿Â¿Â¿Â¿â€”RAUL ALVAREZ ANDRADEÂ¡Ã¨AR7Ã€\f309VA71 909BÂªCENTROâ€˜ CORDOBA?â€�P\f\f012717145Ã¨8Â¿Â©";
	                 cadena = "Â¿Â¿Â¿Â¿Â¿â€”FABIOLA CEBRIAN GARCIAÅ¸ CEGF841Ã€9?AV BENITO JUAREZ?39Å’BARR DE MEXICOâ€°VILLA DE REYESâ€°";
	                 if(vcampos == 0) {
	                	 attribute.setValue(iFileCBLImpl.extractString(cadena,attribute.getBytes(),7));
	                     System.out.println(attribute.getName().concat(" -> ").concat(attribute.getValue()));
	                 }
		 });*/
//		 List<String> values = iFileCBLImpl.values(fileDat);
//		 int i = 0;
//		 values.stream().limit(10).forEach(value -> {
//			 System.out.println(value);
//		 });;
		 
		 byte[] bytesFile = Files.readAllBytes(filePrueba.toPath());
		 int length = 0;
		 boolean count   = false;
		 boolean leer    = false;
		 boolean detener = false;
		 int i = 0;
		 int baitTemp = -1;
		 int bait = fileDat.read();
		 List<String> values = new ArrayList<>();
		 String cadena = new String();
		 
		 while(bait != -1) {
			 if((bait == 70 || bait == 71) && !count && !leer) {
				 System.out.println(i + " " + (char)bait + " " + bait);
				 count = true;
			 } else if(count && !leer) {
				 if(bait != baitTemp) {
					 length = 0;
					 count = false;
					 System.out.println("Ya entre a disminuir" + bait + " " + (char)bait);
				 }
				 if(bait == 0) {
					 length++;
					 System.out.println("Ya entre porque es 0" + " " + length);
					 if(length == 4) {
						 leer   = true;
						 count  = false;
						 length = 0;
						 System.out.println("Vamos a comenzar a leer" + " - " + bait);
					 }
				 }
				 System.out.println(bait + " - "+  "Ya entre");
				 baitTemp = bait;
				 if(!count)
					 length = 0;
			 } else if(leer && !count) {
				 System.out.println(bait + " ---- " + (char)bait);
				 if(bait != baitTemp) {
					 length = 0;
					 System.out.println("Ya entre a disminuir segunda");
				 }
				 
				 if(bait == 255) {
					 length++;
					 System.out.println("ENTRE 255" + " " + length);
					 if(length == 10) {
						 detener = true;
					 }
				 }
				 
				 if(!detener) {
					 cadena           += (char)bait;
					 baitTemp         = bait;
				 } else {
					 System.out.println("AGREGAR CADENA--------------------------------------");
					 values.add(cadena);
					 count   = false;
					 leer    = false;
					 detener = false;
					 cadena  = "";
					 if(values.size() == 15) {
						 break; 
					 }
				 }
				 
			 }
			 bait = fileDat.read();
//			 i++;
		 }
		 /*
		 for(byte bait: bytesFile) {
			 if((bait == 6 || bait == 70 || bait == 71) && !count) {
				 count = true;
			 } else if(count) {
				 if(bait != baitTemp) {
					 length = 0;
				 }
				 
				 if(bait == 0) {
					 length++;
					 if(length == 4) {
						 leer   = true;
						 count  = false;
						 length = 0;
					 }
				 }
			 } else if(leer) {
				 if(bait != baitTemp) {
					 length = 0;
				 }
				 
				 if(bait == 152) {
					 System.out.println("Entreeeeeeeee");
					 length++;
					 if(length == 10) {
						 detener = true;
					 }
				 }
				 
				 if(!detener) {
					 bytesFileTemp[i] = bait;
					 baitTemp         = bait;
				 } else {
					 System.out.println("Entreeeeeeeee");
					 values.add(bytesFileTemp);
					 count = false;
				 }
			 }
			 i++;
		 }
		 */
		 
		 values.forEach(value -> {
			 System.out.println(value + " " + "..... \n\n\n");
		 });
		 
		 System.out.println(values.size());
		 
	    
	}

}
