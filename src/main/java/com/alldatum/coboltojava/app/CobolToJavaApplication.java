package com.alldatum.coboltojava.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alldatum.coboltojava.app.pojo.FileCBL;
import com.alldatum.coboltojava.app.pojo.ValuesAttribute;
import com.alldatum.coboltojava.app.services.IFileCBLImpl;

class Variables{
	 static int bait=0;
	 static int vcampos=0;
	 static int comp3=0;
}

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
		 InputStream fileDat = new FileInputStream("C:\\Users\\Alldatum Business\\Downloads\\poliza4.dat");
		 File filePrueba = new File("C:\\Users\\Alldatum Business\\Downloads\\poliza4.dat");
		 FileCBL cblPoliza = new FileCBL();
		 HashMap<String, ValuesAttribute> mapa = new HashMap<>();
		 cblPoliza.setAttributes(iFileCBLImpl.attributes(file));
		 
	}

}
