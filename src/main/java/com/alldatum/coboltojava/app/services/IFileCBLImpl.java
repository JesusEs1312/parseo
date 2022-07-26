package com.alldatum.coboltojava.app.services;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.alldatum.coboltojava.app.pojo.Attribute;

@Service
public class IFileCBLImpl implements IFileCBL {

    @Override
    public List<Attribute> attributes(InputStream file) {//---------------- FUNCTION THAT RETURN THE CBL ATTRIBUTES
        List<Attribute> attributesList = new ArrayList<>();
        Attribute attribute            = null;
        Scanner obj                    = new Scanner(file);
        boolean endName                = false;
        boolean startBytes             = false;
        Pattern patternWithoutSpace    = Pattern.compile("^[\\S]");
        String nomTemp                 = "";
        boolean withCompTemp           = false;
        boolean withDecimalTemp        = false;
        Integer bytesTemp              = null;

        while (obj.hasNextLine()) { //--- Read file row
            boolean withDecimal = false;
            boolean withComp    = false;
            boolean wasArray    = false;
            String row          = obj.nextLine().trim();
            String attributeAux = "";
            String name         = "";
            String dataType     = "";
            Integer bytes       = null;

            if(row.startsWith("04") || row.startsWith("05") || row.startsWith("OCCURS")){
                char characters[] = row.toCharArray();//--- Convert row to character array
                for(int i = 0; i < characters.length; i++){//--- Read character array
                    Matcher matcherWithoutSpace = patternWithoutSpace.matcher(String.valueOf(characters[i]));
                    if(i > 2){
                        if (!endName){
                            attributeAux += characters[i];
                            if(characters[i] == ' '){
                                if(attributeAux.equalsIgnoreCase("URS ")){
                                    attributeAux = "OCCURS";
                                }
                                name = attributeAux.trim();
                                endName = true;
                                attributeAux = "";
                            }
                        } else if(endName){
                            if (matcherWithoutSpace.matches() && characters[i] != '.'){
                                attributeAux += characters[i];
                                if(attributeAux.equalsIgnoreCase("PIC")){
                                    attributeAux = "";
                                } else if (characters[i] == '(' || characters[i] == ')'  || startBytes && !withDecimal) {
                                    if (characters[i] == '(') {
                                        startBytes = true;
                                        attributeAux = "";
                                    } else if (characters[i] == ')'){
                                        startBytes = false;
                                        String bytesString = attributeAux.substring(0, attributeAux.length() - 1);
                                        bytes = Integer.parseInt(bytesString);
                                        attributeAux = "";
                                    }
                                } else if (characters[i] == 'X'){
                                    dataType     = "X";
                                    attributeAux = "";
                                } else if (attributeAux.equalsIgnoreCase("S9")) {
                                    dataType     = attributeAux;
                                    attributeAux = "";
                                } else if (attributeAux.equalsIgnoreCase("V9")){
                                    withDecimal  = true;
                                    attributeAux = "";
                                } else if (attributeAux.equalsIgnoreCase("COMP-3")){
                                    withComp     = true;
                                    attributeAux = "";
                                }
                            }//--- End the validation regexp (^[\S])
                        }//--- End (endName == true)
                    }//--- End (if > 2)
                } //--- End read characters Array
                if(bytes == null && !name.equalsIgnoreCase("OCCURS")){
                    nomTemp = name;
                } else if (row.startsWith("05")) {
                    withCompTemp    = withComp;
                    withDecimalTemp = withDecimal;
                    bytesTemp       = bytes;
                } else {
                    if(dataType.equalsIgnoreCase("X")){
                        attribute = new Attribute(Attribute.DataType.String);
                    } else if (dataType.equalsIgnoreCase("S9") && withDecimal) {
                        attribute = new Attribute(Attribute.DataType.Double);
                    } else if (name.equalsIgnoreCase("OCCURS")){
                        attribute = new Attribute(Attribute.DataType.List);
                        wasArray = true;
                    } else {
                        attribute = new Attribute(Attribute.DataType.Integer);
                    }
                    if (wasArray){
                        attribute.setName(nomTemp);
                        attribute.setBytes(bytesTemp);
                        attribute.setWithComp(withCompTemp);
                        attribute.setWithDecimal(withDecimalTemp);
                        attribute.setValue(null);
                        wasArray = false;
                    } else {
                        attribute.setName(name);
                        attribute.setBytes(bytes);
                        attribute.setWithComp(withComp);
                        attribute.setWithDecimal(withDecimal);
                        attribute.setValue(null);
                    }
                    attributesList.add(attribute);
                }
                endName = false;
            }//--- End read row
        }//--- End read file row
        return attributesList;
    }

    @Override
    public String extractString(String stringText, int characters, int position) {
        String subca     = "";
        String subca2    = "";
        int avanzar      = 0;
        int bait         = 0;
        int vCampos      = 0;
        char[] flujochar = new char[2000000];
        //--- para que sirve getChars() -> Se utilizar para copiar los caracteres de una cadena a una matriz de caracteres
        stringText.getChars(0, stringText.length(), flujochar, 0);
        avanzar = (int)flujochar[position - 1];
        if(avanzar >= characters) {
            vCampos = 1;
            for(int l = position; l < (position + characters); l++) {
                subca += flujochar[l];
//                System.out.println("subca= " + subca);
//                System.out.println("l= " + l);
                bait = l;
            }
            for(int l = (bait + 1); l < (position + avanzar); l++) {
                subca2 += flujochar[l];
//                System.out.println("subca2 = " + subca2);
//                System.out.println("l = " + l);
                bait = l;
            }
        } else {
            for(int l = position; l < (position + avanzar); l++) {
                subca += flujochar[l];
//                System.out.println("subca= "+ subca);
//                System.out.println("l= "+ l);
                bait = l;
            }
//            System.out.println("flujochar[variables.bait+1]= " + flujochar[bait + 1]);
//            System.out.println((int)flujochar[bait + 1]);
            if((flujochar[bait + 1] == 192 && (flujochar[bait + 2] > 0 && flujochar[bait + 2] <= 20)) || (flujochar[bait + 1] == 232 && (flujochar[bait + 3] > 0 && flujochar[bait + 3] < 20))) {
                for(int l = (bait + 1); l < (position + characters); l++) {
                    if(flujochar[l] == '?' || flujochar[l] == 'Š') {
                        break;
                    }
                    else {
                        subca += flujochar[l];
//                        System.out.println("subca= " + subca);
//                        System.out.println("l= " + l);
                        bait = l;
                    }
                }
            }

        }
        return subca;
    }

    @Override
    public Long stringComp3(String stringText, int digits, int position) throws Exception {
        int numbytes     = 0;
        String subca     = "";
        long comp3       = 0;
        char[] flujochar = new char[2000000];

        stringText.getChars(0, stringText.length(), flujochar, 0);
        numbytes = (int) bytesCalculate(digits);
        for(int l = position; l < (position + numbytes); l++) {
            subca += flujochar[l];
        }
        comp3 = comp3(subca.getBytes());
        return comp3;
    }

    @Override
    public Long comp3(byte[] input) throws Exception {
        final int Positivo = 0x0C;      // ultimo nibble del campo positivo
        final int Negativo = 0x0D;      // ultimo nibble del campo negativo
        final int SemSinal = 0x0F;      // ultimo nibble del campo sin signo
        final int GetHO    = 0x0F;      // para obter los High Order bits
        final int GetLO    = 0x0F;      // para obter los Low Order bits
        long saida  = 0;                 // Valor convertido
        int digito1 = 0;                 // Guarda el valor del primer nibble
        int digito2 = 0;                  // Guarda el valor del segundo nibble

        for(int i = 0; i < input.length; i++) {
            digito1 = (input[i] >> 4) & GetHO;
            if (i == input.length - 1) {
                saida = (saida * 10) + digito1;
                digito2 = input[i] & GetLO;
                if (digito2 == Negativo) {
                    saida =- saida;
                } else {
                    if(digito2 != Positivo && digito2 != SemSinal) {
                        //System.out.println("El campo no es comp-3");
                        saida = 10000000;
                    }
                }
            } else {                           // no es el ultimo digito
                saida = (saida * 10) + digito1;
                digito2 = input[i] & GetLO;        // Obtiene el último nibble
                saida = (saida * 10) + digito2;
            }
        }
        return saida;
    }

    @Override
    public double bytesCalculate(float digits) {
        double bytes = Math.ceil((digits + 1) / 2);
        return bytes;
    }

    @Override
	public List<String> values(InputStream fileDat) throws Exception {
    	byte[] bytesFile = fileDat.readAllBytes();
    	int length = 0;
    	
    	
    	/*
    	long numero=0;
		int numero2=2244, sumabytes=900;
		int i=0, bloque=0, s=0, reg=1, lin=0;
		String [] flujo  = new String[35000000];
		String [] lineas = new String[35000000];
		String cadena="";
		String subca="", subca2="";
		String cadenaprueba="";
		char[] flujocharprueba = new char [100000000];
		*/
    	
//		for(int k=0; k<35000000; k++) {
//			lineas[k]="";
//		}

//		InputStream ins = new FileInputStream("c:\\users\\Alldatum Business\\"+archivo+".dat");
		/*
		InputStream ins = file;
		Scanner obj = new Scanner(ins);
        Scanner entradaEscaner = new Scanner (System.in);
         
         while (obj.hasNextLine()) {
        	 flujo[i] = obj.nextLine();
        	 i++;
         }
         */
         
//         for(bloque=0; bloque<10; bloque++) {
//        	 for(int j=0; j<5000; j++) {//en vez del número debe ir i   //////////////////////////////////////////////////////////////////////////////////////
//        		cadenaprueba+=flujo[lin];
//         		lin++;
//        	 }
//         
//         cadenaprueba.getChars(0, cadenaprueba.length(), flujocharprueba, 0);
//         
//         for(int l=0; l<cadenaprueba.length(); l++) {
//        	 subca="";
//        	 
//        	// System.out.println(flujocharprueba[l]);
//        	 if(flujocharprueba[l]==0) {
//        		 if(flujocharprueba[l+1]==0x06) {
//        			 if(flujocharprueba[l+2]==0 &&flujocharprueba[l+3]==0 && flujocharprueba[l+4]==0 && flujocharprueba[l+5]==0) {
//        				 for(int m=l+12; m<l+sumabytes; m++) {
//        					 if(flujocharprueba[m+1]==255) {
//        						 System.out.println("\n \n \n Hay una yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
//        					 }
//        					 if((flujocharprueba[m+1]==0 && flujocharprueba[m+2]==2)) {
//        						 //System.out.println("lineas["+s+"]= "+lineas[s]);
//        						 s++;
//        						 
//        						 for(int n=m+6; n<m+sumabytes; n++) {
//        							 
//        							 if((flujocharprueba[n+1]==0 && (flujocharprueba[n+2]==2 || flujocharprueba[n+2]==3 || flujocharprueba[n+2]==4 || flujocharprueba[n+2]==5))/* || flujocharprueba[n+1]==255*/) {
//        								// System.out.print(flujocharprueba[n+1]);
//        								 break;
//        							 }
//        							 else {
//        								 lineas[s]+=flujocharprueba[n];
//        								//System.out.println("lineas["+s+"]= "+lineas[s]);
//        							 }
//        						 }
//        						 //bw.write("\n s= "+s+"   "+lineas[s]);
//        						 //System.out.println("subca= "+subca);
//        						 break;
//        					 }else {
//        					 lineas[s]+=flujocharprueba[m+1];
//        					 
//        					 }
//        				 //System.out.print(/*Integer.toHexString((int)*/flujocharprueba[m+1]+"");
//        				 
//        				 
//        				 //bw.write("   "+Integer.toHexString((int)flujocharprueba[m+1]));
//        				 }
//        				// bw.write("\n s= "+s+"   "+lineas[s]);
//        				//System.out.print("\n");
//        				 
//        				//System.out.println("lineas["+s+"]= "+lineas[s]);
//        				//flujo[s]=subca;
//        				 
//        				 s++;
//        				 subca=String.valueOf(stringComp3(subca,5,0));
//        				 //System.out.println("subca= "+subca);
//        			 }
//        		 }
//        	 }
//         }
//         cadenaprueba="";
//         }//////////////////////////////////////////////////////////////////////////////////////
//         
////         for(int k=0; k<s; k++) {
//// 			//System.out.println(lineas[k]);
////        	 bw.write("\n s= "+k+"  "+lineas[k]);
////        	 
//// 		}
//         
////         bw.close();
//         
//         for(int k = 0; k < 10; k++) {
//        	 System.out.println(lineas[k]);
//         }
//         
//         List<String> values = Arrays.asList(lineas);
		 return null;
	}

    
}

