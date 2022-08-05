package com.alldatum.coboltojava.app.services;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.alldatum.coboltojava.app.pojo.*;

@Service
public class IFileCBLImpl implements IFileCBL {
	
	private System p;
	private int position;

    @Override
    public List<Attribute> attributes(InputStream file) {//---------------- FUNCTION THAT RETURN THE CBL ATTRIBUTES
        List<Attribute> attributesList = new ArrayList<>();
        Attribute attribute            = null;
        Scanner obj                    = new Scanner(file);
        boolean endName                = false;
        boolean startBytes             = false;
        Pattern patternWithoutSpace    = Pattern.compile("^[\\S]");
        String  nomTemp                = "";
        boolean withCompTemp           = false;
        boolean withDecimalTemp        = false;
        Integer bytesTemp              = null;
        Integer bytesDecimalTemp       = null;
        int basta                      = 0;

        while (obj.hasNextLine()) { //--- Read file row
            boolean withDecimal  = false;
            boolean withComp     = false;
            boolean wasArray     = false;
            String row           = obj.nextLine().trim();
            String attributeAux  = "";
            String name          = "";
            String dataType      = "";
            Integer bytes        = null;
            Integer bytesDecimal = null;
            
<<<<<<< HEAD
            if(row.startsWith("02") || row.startsWith("04") || row.startsWith("05") || row.startsWith("OCCURS")){
            	if(row.startsWith("02")) {
            		basta++;
            	} else {
                char characters[] = row.toCharArray();//--- Convert row to character array
=======
            if(row.startsWith("04") || row.startsWith("05") || row.startsWith("02") || row.startsWith("OCCURS")){
            	if(row.startsWith("02")) {
            		basta++;
            	} else {
            	char characters[] = row.toCharArray();//--- Convert row to character array
>>>>>>> cf25d50bdbab5559fd57b3f2c978e5b7f09bb638
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
                                } else if ((characters[i] == '(' || characters[i] == ')')) {
                                	if (characters[i] == '(') {
                                        startBytes = true;
                                        attributeAux = "";
                                    } else if (characters[i] == ')'){
                                        String bytesString = attributeAux.substring(0, attributeAux.length() - 1);
                                        if(startBytes && !withDecimal) bytes = Integer.parseInt(bytesString);//--- Guardar bytes(S9)
                                        else bytesDecimal = Integer.parseInt(bytesString);//--- Guardar bytes(V9)
                                        attributeAux = "";
                                        startBytes = false;
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
                    withCompTemp     = withComp;
                    withDecimalTemp  = withDecimal;
                    bytesTemp        = bytes;
                    bytesDecimalTemp = bytesDecimal;
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
                        attribute.setBytesDecimal(bytesDecimalTemp);
                        wasArray = false;
                    } else {
                        attribute.setName(name);
                        attribute.setBytes(bytes);
                        attribute.setWithComp(withComp);
                        attribute.setWithDecimal(withDecimal);
                        attribute.setBytesDecimal(bytesDecimal);
                    }
                    attributesList.add(attribute);
                }
                endName = false;
<<<<<<< HEAD
            }//--- End else
        }//--- End validacion row
            if(basta == 2) break;
        }//--- end file row
=======
            }//--- End read row
        }//--- End read file row
            if(basta==2) {
            	break;
            }
        }
>>>>>>> cf25d50bdbab5559fd57b3f2c978e5b7f09bb638
        return attributesList;
        
    }

    @Override
<<<<<<< HEAD
    public String extractString(String cadena, int caracteres, int posicion, boolean occurs, int vcampos) {
    	
=======
    public String extractString(String cadena, int caracteres, int posicion, boolean occurs, int vcampos, String tipoDeDato) throws Exception {
>>>>>>> cf25d50bdbab5559fd57b3f2c978e5b7f09bb638
    	Variables.todoscaracteres=0;
    	Variables.decimal=0;
    	Variables.listaint=0;
		Variables.listasin=0;
		if(Variables.vcampos==0) {
<<<<<<< HEAD
			Variables.subca2="";
			int aplicaavanzar=1;
			String subca="";
			int numOccurs =5;
			char[] flujochar= new char[2000000];
			int avanzar=0, o=0;
=======
		Variables.subca2="";
		int aplicaavanzar=1;
		int inte=0;
		String subca="";
		int numOccurs =5;
		char[] flujochar= new char[2000000];
		int avanzar=0, o=0;
>>>>>>> cf25d50bdbab5559fd57b3f2c978e5b7f09bb638
		
			cadena.getChars(0, cadena.length(), flujochar, 0);
			
			if(posicion>0) {
				avanzar = (int)flujochar[posicion-1];
			}
			//System.out.println("avanzar= "+ avanzar);
			
			/*for(int l=60; l<140; l++) {
				System.out.println("flujochar["+l+"]= "+flujochar[l]);
			}*/
			
			if(occurs) {
				
				for(int l=posicion; l<posicion+caracteres; l++) {
					Variables.bait=l;
					o=l;
					if(flujochar[l]=='¿' || flujochar[l]!=32 && flujochar[l]!=46 &&flujochar[l]!=44 && flujochar[l]!=40 && flujochar[l]!=41 &&flujochar[l]!=38 && flujochar[l]!=45 && (flujochar[l]<48 &&flujochar[l]>57) && (flujochar[l]<65) || flujochar[l]>90) {
						break;
					}
					subca+=flujochar[l];
					//System.out.println("subca= "+ subca);
					//System.out.println("l= "+ l);
					
			}
				for(int j=0; j<=numOccurs; j++) {
					if(flujochar[o+1]=='¿') {
						Variables.listaint=1;
						inte=1;
						Variables.bait++;
					}
					else {
						if(inte==1) {
						break;
						}
						else {
							Variables.bait+=1;
							Variables.listasin=1;
							break;
						}
					}
					o++;
				}
				Variables.bait+=1;
				if(tipoDeDato.equals("Integer")) {
					subca=String.valueOf(comp3(subca.getBytes()));
				}
				//System.out.println("subca= "+ subca);
			}
			else {
			if(Variables.comp3==0) {
				//System.out.println("avanzar= "+avanzar);
				if(avanzar>caracteres) {
					for(int l=posicion; l<avanzar; l++) {
						//System.out.println("flujochar["+l+"]= "+flujochar[l]);
						if(flujochar[l]!=32 && flujochar[l]!=46 &&flujochar[l]!=44 && flujochar[l]!=40 && flujochar[l]!=41 &&flujochar[l]!=38 && flujochar[l]!=45 && (flujochar[l]<48 ||flujochar[l]>57) && (flujochar[l]<65 || flujochar[l]>90)) {
							aplicaavanzar=0;
							break;
						}
					}
				}
			if(avanzar>caracteres && aplicaavanzar==1) {//Avanzar es mayor que los caracteres
				Variables.vcampos=1;
				for(int l=posicion; l<posicion+caracteres; l++) {
					if(flujochar[l]!=32 && flujochar[l]!=46 &&flujochar[l]!=44 && flujochar[l]!=40 && flujochar[l]!=41 &&flujochar[l]!=38 && flujochar[l]!=45 && (flujochar[l]<48 ||flujochar[l]>57) && (flujochar[l]<65 || flujochar[l]>90)) {
						break;
					}
						subca+=flujochar[l];
						//System.out.println("subca= "+ subca);
						//System.out.println("l= "+ l);
						Variables.bait=l;
				}
			Variables.bait+=2;
				//System.out.println("subca= "+ subca);
				for(int l=Variables.bait-1; l<posicion+avanzar; l++) {
					if(flujochar[l]!=32 && flujochar[l]!=46 &&flujochar[l]!=44 && flujochar[l]!=40 && flujochar[l]!=41 &&flujochar[l]!=38 && flujochar[l]!=45 && (flujochar[l]<48 ||flujochar[l]>57) && (flujochar[l]<65 || flujochar[l]>90)) {
						break;
					}else {
					Variables.subca2+=flujochar[l];
					//System.out.println("subca2= "+ variables.subca2);
					//System.out.println("l= "+ l);
					Variables.bait=l;
					}
				} Variables.bait+=1;
				
				//System.out.println("subca2= "+ subca2);
			}
			else { //Avanzar es menor a los caracteres
				
				for(int l=posicion; l<posicion+avanzar; l++) {
					if(flujochar[l]!=32 && flujochar[l]!=46 &&flujochar[l]!=44 && flujochar[l]!=40 && flujochar[l]!=41 &&flujochar[l]!=38 && flujochar[l]!=45 && (flujochar[l]<48 ||flujochar[l]>57) && (flujochar[l]<65 || flujochar[l]>90)) {
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
					if(flujochar[l]!=32 && flujochar[l]!=46 &&flujochar[l]!=44 && flujochar[l]!=40 && flujochar[l]!=41 &&flujochar[l]!=38 && flujochar[l]!=45 && (flujochar[l]<48 ||flujochar[l]>57) && (flujochar[l]<65 || flujochar[l]>90)) {
						Variables.bait=l;
						break; 
					}else {
					subca+=flujochar[l];
					//System.out.println("subca= "+ subca);
					//System.out.println("l= "+ l);
					Variables.bait=l;
					}
					Variables.todoscaracteres=1;
			}
			}

			}
		
			Variables.comp3=0;
<<<<<<< HEAD
			return subca;
			
		}//--
=======
			
			return subca;}
>>>>>>> cf25d50bdbab5559fd57b3f2c978e5b7f09bb638
			else {
				if(Variables.subca2.length() == caracteres) {
					Variables.todoscaracteres=1;
				}
				Variables.vcampos=0;
				return Variables.subca2;
			}
			
	}
    
    @Override
<<<<<<< HEAD
    public double comp3decimal(String attr, String cadena, int digitoss9, int digitosv9, int posicion ) throws Exception {
=======
    public String comp3decimal (String cadena, int digitoss9, int digitosv9, int posicion ) throws Exception {
    	Variables.decimal=1;
>>>>>>> cf25d50bdbab5559fd57b3f2c978e5b7f09bb638
    	int numbytess9=0, numbytesv9=0;
		char[] flujochar= new char[2000000];
		String subca="", strings9="", stringv9="";
		long s9=0, v9=0;
		String comp3="";
		long saida = 0;
		final int Negativo = 0x0D;// Valor convertido 
	    int digito1 = 0;                 // Guarda el valor del primer nibble
	    int digito2 = 0;
	    final int GetHO    = 0x0F;      // para obter los High Order bits
	    final int GetLO    = 0x0F;
		
	    
	    
		cadena.getChars(0, cadena.length(), flujochar, 0);
<<<<<<< HEAD
		
		numbytesv9=(int)bytesCalculate(digitosv9);
=======
		numbytesv9=(int) Math.ceil((digitosv9)/2);
		//System.out.println("numbytesv9= "+numbytesv9);
>>>>>>> cf25d50bdbab5559fd57b3f2c978e5b7f09bb638
		numbytess9=(int)bytesCalculate(digitoss9);
		//System.out.println("numbytess9= "+numbytess9);
		
		s9=stringComp3(attr, cadena,digitoss9,posicion);
		subca=cadena.substring(posicion+numbytess9, posicion+numbytess9+numbytesv9);
		byte[] entrada = subca.getBytes();
		
		for(int i=0; i < entrada.length; i++) {
		       digito1 = (entrada[i] >> 4) & GetHO;
<<<<<<< HEAD
//		       System.out.println(digito1);
=======
		       //System.out.println(digito1);
>>>>>>> cf25d50bdbab5559fd57b3f2c978e5b7f09bb638
			   saida = (saida * 10) + digito1;
			   digito2 = entrada[i] & GetLO;        // Obtiene el último nibble
		       saida = (saida * 10) + digito2;
	    }
		v9=saida;
		
		strings9=String.valueOf(s9);
		stringv9=String.valueOf(v9);
		subca=strings9+"."+stringv9;
	
		comp3=subca;
		//System.out.println("comp3= "+comp3);
		return comp3;
	}
    
    public double decimal(byte[] bytes, String stringS9) {
    	final int Negativo  = 0x0D;// Valor convertido 
    	final int GetHO     = 0x0F;      // para obter los High Order bits
    	final int GetLO     = 0x0F;
    	String salidaString = "";
    	String stringV9     = "";  
    	long salida          = 0;
    	long s9             = 0;
    	long v9             = 0;
	    int digito1         = 0;                 // Guarda el valor del primer nibble
	    int digito2         = 0;
	    double comp         = 0;
	    
	    for(int i=0; i < bytes.length; i++) {
	    	digito1 = (bytes[i] >> 4) & GetHO;
//	    	System.out.println(digito1);
	    	salida = (salida * 10) + digito1;
	    	digito2 = bytes[i] & GetLO;        // Obtiene el último nibble
		    salida = (salida * 10) + digito2;
	    }
	    
	    v9           = salida;
		stringV9     = String.valueOf(v9);
		salidaString = stringS9 + "." + stringV9;
		comp         = Double.parseDouble(salidaString);
    	return comp; 
    }
    

    @Override
<<<<<<< HEAD
    public Long stringComp3(String att, String cadena, int digitos, int posicion) throws Exception {
=======
    public Long stringComp3(String cadena, int digitos, int posicion) throws Exception {
    	Variables.decimal=0;
>>>>>>> cf25d50bdbab5559fd57b3f2c978e5b7f09bb638
    	if(Variables.todoscaracteres==0) {
			posicion+=2;
		}
		else {
			if(Variables.todoscaracteres==1) {
				posicion++;
			}
		}
		int numbytes=0;
		char[] flujochar = new char[2000000];
		String subca="";
		long comp3=0;
		
		cadena.getChars(0, cadena.length(), flujochar, 0);
		numbytes=(int)bytesCalculate(digitos);
	
		
		
		for(int l=posicion; l<posicion+numbytes; l++) {
			subca+=flujochar[l];
			Variables.bait=l;
			
		}
<<<<<<< HEAD
//		System.out.println("Subca= "+subca);
=======
		//System.out.println("Subca= "+subca);
>>>>>>> cf25d50bdbab5559fd57b3f2c978e5b7f09bb638
		comp3=comp3(subca.getBytes());
		
		Variables.bait++;
		Variables.todoscaracteres=3;
//		System.out.println("Variables bait = " + Variables.bait);
//		System.out.println(att);
		return comp3;
	}

    @Override
    public Long comp3(byte[] input) throws Exception {
<<<<<<< HEAD
    	Variables.comp3 = 1;
        final int Positivo = 0x0C;      // ultimo nibble del campo positivo
        final int Negativo = 0x0D;      // ultimo nibble del campo negativo
        final int SemSinal = 0x0F;      // ultimo nibble del campo sin signo
        final int GetHO    = 0x0F;      // para obter los High Order bits
        final int GetLO    = 0x0F;      // para obter los Low Order bits
        long saida  = 0;                 // Valor convertido
        int digito1 = 0;                 // Guarda el valor del primer nibble
        int digito2 = 0;                  // Guarda el valor del segundo nibble
=======
    	Variables.listaint=0;
   	 	Variables.listasin=0;
    	Variables.comp3=1;
	    final int Positivo = 0x0C;      // ultimo nibble del campo positivo
	    final int Negativo = 0x0D;      // ultimo nibble del campo negativo
	    final int SemSinal = 0x0F;      // ultimo nibble del campo sin signo
	    final int GetHO    = 0x0F;      // para obter los High Order bits
	    final int GetLO    = 0x0F;      // para obter los Low Order bits
	    
	    long saida = 0;                 // Valor convertido 
	    int digito1 = 0;                 // Guarda el valor del primer nibble
	    int digito2 = 0;                  // Guarda el valor del segundo nibble
>>>>>>> cf25d50bdbab5559fd57b3f2c978e5b7f09bb638

	    for(int i=0; i < input.length; i++) {
	       digito1 = (input[i] >> 4) & GetHO;
	       if (i == input.length - 1) {     
	          saida = (saida * 10) + digito1; 
	          digito2 = input[i] & GetLO;  
	          if (digito2 == Negativo) {
	             saida = -saida;
	          } else {
	             if(digito2 != Positivo && digito2 != SemSinal) {
	                //System.out.println("El campo no es comp-3");
	            	 saida=10000000;
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
    	double bytes=Math.ceil((digits+1)/2);
		
		return bytes;
	}
    

    @Override
	public List<String> values(InputStream fileDat) throws Exception {
    	//---Variables
    	boolean count       = false;
    	boolean leer        = false;
    	boolean detener     = false;
    	int length          = 0;
    	int baitTemp        = -1;
    	int bait            = fileDat.read();
    	String cadena       = new String();
    	List<String> values = new ArrayList<>();
		//---Leer archivo 
    	while(bait != -1) {
//    		p.out.println((char)bait + " " + bait + " --------");
    		if((bait == 6 || bait == 70 || bait == 71) && !count && !leer) {//--- Encuentra byte 6, 70, 71
//    			p.out.println(bait + " --- " + (char)bait);
    			count = true;
    		} else if(count && !leer) {//--- Comienza a contar cuatro veces 0
    			if(bait != baitTemp) {
    				length = 0;
    				count  = false;
//    				p.out.println("Longitud es 0");
    			}
//    			p.out.println("Estoy validando si " + bait + " es == 0");
    			if(bait == 0) {
    				length++;
//    				p.out.println(bait + " Bait es 0 --- " + length);
    				if(length == 4) {
    					leer   = true;
    					count  = false;
    					length = 0;
//    					p.out.println("Ya son cuatro veces 0");
    				}
    			}//--- end (bait == 0)
    			if(!count) {
    				length = 0;
//    				count = true;
//    				if(leer) {
//    					count = false;
//    				}
    			}
    			baitTemp = bait;
//    			p.out.println(baitTemp);
    		} else if(leer && !count) {//--- Comienza a guardar bytes en la cadena
//    			p.out.println("Espezar a guardar" + " : " +bait + " *-- " + (char)bait);
    			if(bait != baitTemp) length = 0;
    			if(bait == 255) {
//    				p.out.println("Es 255 " + length);
    				length++;
    				if(length == 10) { 
    					detener = true;
//    					p.out.println("Ya son diez veces 255");
    				}
    			}//--- end (bait == 255)
    			if(!detener) {
//    				p.out.println(" GUARDANDO...--- " + (char)bait);
    				cadena   += (char)bait;
    				baitTemp = bait;
    			} else {
//    				p.out.println("AGREGAR CADENA---");
    				values.add(cadena);
    				count   = false;
    				leer    = false;
    				detener = false;
    				cadena  = "";
    				if(values.size() == 1) break; 
    			}
    		}//--- end (!detener)
    		bait = fileDat.read();
    	}
    	return values;
	}

	@Override
	public HashMap<String, ValuesAttribute> mapKeysCBL(List<Attribute> attributes) {
		HashMap<String, ValuesAttribute> mapKeysCBL = new HashMap<>();
		attributes.forEach(attribute -> {
			mapKeysCBL.put(attribute.getName(), new ValuesAttribute());
		});
		return mapKeysCBL;
	}

	@Override
	public HashMap<String, ValuesAttribute> mapValues(List<Attribute> attributes, List<String> blocks,
			HashMap<String, ValuesAttribute> mapKeysCBL){
		position = 7;
		HashMap<String, ValuesAttribute> mapValues = mapKeysCBL;
		blocks.forEach(block -> {
			attributes.forEach(attribute ->{
				mapValues.entrySet().forEach(entrySet -> {
					switch(attribute.getDataType()) {
						case Integer:
							if(entrySet.getKey() == attribute.getName()) {
								try {
									double bytesAttribute = bytesCalculate(attribute.getBytes());
									String valueComp = block.substring(position, (int)(position + bytesAttribute));
									String value     = String.valueOf(comp3(valueComp.getBytes()));
									entrySet.getValue().addValue(value);
									position += (int) bytesCalculate(attribute.getBytes());
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							break;
						case String:
							if(entrySet.getKey() == attribute.getName()) {
								try {
									double bytesAttribute = bytesCalculate(attribute.getBytes());
									String value          = block.substring(position, (int)(position + bytesAttribute));
									entrySet.getValue().addValue(value);
									position += (int) bytesCalculate(attribute.getBytes());
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							break;
						case Double:
							if(entrySet.getKey() == attribute.getName()) {
								try {
									int bytesAttributeS9    = (int) bytesCalculate(attribute.getBytes());
									int bytesAttributeV9    = (int) bytesCalculate(attribute.getBytesDecimal());
									String blockComp        = block.substring(position, (int) (position + bytesAttributeS9));
									String valueComp3       = String.valueOf(comp3(blockComp.getBytes()));
									String blockCompDecimal = block.substring((position + (int) bytesAttributeS9), (position + (int) bytesAttributeS9 + (int) bytesAttributeV9));
									String value            = String.valueOf(decimal(blockCompDecimal.getBytes(), valueComp3));
									entrySet.getValue().addValue(value);
									position += (bytesAttributeS9 + bytesAttributeV9);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							break;
					default:
						break;
					}
				});				
			});
		});
		return mapValues;
	}

	@Override
	public void readFileDAT(InputStream fileDAT) throws IOException {
		boolean count       = false;
    	boolean save        = false;
    	boolean stop        = false;
    	int length          = 0;
    	int baitAnt         = -1;
    	int bait            = fileDAT.read();
    	String registro     = new String();
    	List<String> values = new ArrayList<>();
    	
    	while(bait != -1) {
    		p.out.println(length + ".-" + bait + ": " +(char)bait);
    		bait = fileDAT.read();
    		length++;
    	}
	}
}

