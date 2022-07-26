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
import com.alldatum.coboltojava.app.pojo.*;
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
    public String extractString(String cadena, int caracteres, int posicion, boolean occurs, int bait, int vcampos, int comp3) {
    	String subca="", subca2="";
		int numOccurs =10;
		char[] flujochar= new char[2000000];
		int avanzar=0, o=0;
		
		cadena.getChars(0, cadena.length(), flujochar, 0);
			
			if(posicion>0) {
				avanzar = (int)flujochar[posicion-1];
			}
			//System.out.println("avanzar= "+ avanzar);
			
			
			if(occurs) {
				for(int l=posicion; l<posicion+caracteres; l++) {
					Variables.bait=l;
					o=l;
					if(flujochar[l]=='¿') {
						break;
					}
					subca+=flujochar[l];
					//System.out.println("subca= "+ subca);
					//System.out.println("l= "+ l);
					
			}
				for(int j=0; j<=numOccurs; j++) {
					if(flujochar[o+1]=='¿') {
						Variables.bait++;
					}
					else {
						break;
					}
					o++;
				}
				Variables.bait+=2;
				//System.out.println("subca= "+ subca);
			}
			else {
			if(Variables.comp3==0) {
			if(avanzar>=caracteres) {//Avanzar es mayor que los caracteres
				Variables.vcampos=1;
				for(int l=posicion; l<posicion+caracteres; l++) {
					if(flujochar[l]!=32 && flujochar[l]!=46 &&flujochar[l]!=44 && flujochar[l]!=40 && flujochar[l]!=41 &&flujochar[l]!=38 && flujochar[l]!=45 && (flujochar[l]<48 &&flujochar[l]>57) && (flujochar[l]<65) || flujochar[l]>90) {
						break;
					}
						subca+=flujochar[l];
						//System.out.println("subca= "+ subca);
						//System.out.println("l= "+ l);
						Variables.bait=l;
				}
			Variables.bait+=1;
				//System.out.println("subca= "+ subca);
				/*for(int l=variables.bait+1; l<posicion+avanzar; l++) {
					subca2+=flujochar[l];
					System.out.println("subca2= "+ subca2);
					System.out.println("l= "+ l);
					variables.bait=l;
				}*/
				//System.out.println("subca2= "+ subca2);
			}
			else { //Avanzar es menor a los caracteres
				
				for(int l=posicion; l<posicion+avanzar; l++) {
					if(flujochar[l]!=32 && flujochar[l]!=46 &&flujochar[l]!=44 && flujochar[l]!=40 && flujochar[l]!=41 &&flujochar[l]!=38 && flujochar[l]!=45 && (flujochar[l]<48 &&flujochar[l]>57) && (flujochar[l]<65) || flujochar[l]>90) {
						break;
					}
						subca+=flujochar[l];
						//System.out.println("subca= "+ subca);
						//System.out.println("l= "+ l);
						Variables.bait=l;
						//System.out.println(variables.bait);	
				}Variables.bait++;
				//System.out.println("subca= "+ subca);
				//System.out.println("flujochar[variables.bait+1]= "+ flujochar[variables.bait+1]);
				if(flujochar[Variables.bait]==192) {
					subca+='0';
					subca+='0';
					Variables.bait++;
					for(int l=Variables.bait+1; l<posicion+caracteres; l++) {
						
						if(flujochar[l]=='?' || flujochar[l]=='Š') {
							break;
						}
						else {
						subca+=flujochar[l];
						//System.out.println("subca= "+ subca);
						//System.out.println("l= "+ l);
						Variables.bait=l;
						//System.out.println(variables.bait);	
						}
				} //System.out.println("subca= "+ subca);
					if(subca.length()==caracteres) {
						Variables.bait--;
					}else {
					Variables.bait++;
					}
				}
				if(flujochar[Variables.bait]==232) {
					subca+=flujochar[Variables.bait+1];
					subca+=flujochar[Variables.bait+1];
					subca+=flujochar[Variables.bait+1];
					Variables.bait+=3;
					
					for(int l=Variables.bait; l<posicion+caracteres; l++) {
						
						if(flujochar[l]=='?' || flujochar[l]=='Š') {
							break;
						}
						else {
						subca+=flujochar[l];
						//System.out.println("subca= "+ subca);
						//System.out.println("l= "+ l);
						//variables.bait=l;
						//System.out.println(variables.bait);	
						}
				}
					if(subca.length()==caracteres) {
						Variables.bait--;
					}else {
					Variables.bait++;
					}
				}
				//System.out.println("subca= "+ subca);
				//variables.bait++;
			}
			}else {
				for(int l=posicion; l<posicion+caracteres; l++) {
					if(flujochar[l]!=32 && (flujochar[l]>0 &&flujochar[l]<48) || ((flujochar[l]>57 &&flujochar[l]<65)) || (flujochar[l]>90)) {
						Variables.bait=l;
						break; 
					}else {
					subca+=flujochar[l];
					//System.out.println("subca= "+ subca);
					//System.out.println("l= "+ l);
					Variables.bait=l;
					}
			}
			}

			}
		
			Variables.comp3=0;
			return subca;
    }
    
    @Override
    public double comp3decimal (String cadena, int digitoss9, int digitosv9, int posicion ) throws Exception {
		int numbytess9=0, numbytesv9=0;
		char[] flujochar= new char[2000000];
		String subca="", strings9="", stringv9="";
		long s9=0, v9=0;
		double comp3=0;
		long saida = 0;
		final int Negativo = 0x0D;// Valor convertido 
	    int digito1 = 0;                 // Guarda el valor del primer nibble
	    int digito2 = 0;
	    final int GetHO    = 0x0F;      // para obter los High Order bits
	    final int GetLO    = 0x0F;
		
	    
	    
		cadena.getChars(0, cadena.length(), flujochar, 0);
		numbytesv9=(int)bytesCalculate(digitosv9);
		numbytess9=(int)bytesCalculate(digitoss9);
		
		s9=stringComp3(cadena,digitoss9,posicion);
		subca=cadena.substring(posicion+numbytess9, posicion+numbytess9+numbytesv9);
		byte[] entrada = subca.getBytes();
		
		for(int i=0; i < entrada.length; i++) {
		       digito1 = (entrada[i] >> 4) & GetHO;
		       System.out.println(digito1);
			   saida = (saida * 10) + digito1;
			   digito2 = entrada[i] & GetLO;        // Obtiene el último nibble
		       saida = (saida * 10) + digito2;
	    }
		v9=saida;
		
		strings9=String.valueOf(s9);
		stringv9=String.valueOf(v9);
		subca=strings9+"."+stringv9;
	
		comp3=Double.parseDouble(subca);
		return comp3;
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
    public  double bytesCalculate(float digits) {
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

