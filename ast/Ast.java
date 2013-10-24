package compiler.ast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import compiler.scanner.*;
import compiler.parser.*;
import compiler.semantic.*;

import java.util.List;
import java.util.*;
import java.util.ArrayList;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;

public class  Ast {

	public String arch;
	public Hashtable tabla = new Hashtable(); 
	public LinkedList astTree = new LinkedList();	
	private LinkedList<simbolo> tablaSimbolos = new LinkedList<simbolo>();
	private LinkedList<variablesMetodos> variables = new LinkedList<variablesMetodos>();

	public LinkedList<simbolo> getTablaSimbolos(){
		return tablaSimbolos;
	}

	public LinkedList<variablesMetodos> getVariables(){
		return variables;
	}

	public Ast(String archivoEntrada, File archivo, int targeting, int encontroScan, int encontroParse, int encontroAST, int encontroSemantic, int encontroIRT, int encontroCodegen){
		CC4Parser parser = new CC4Parser(archivoEntrada, archivo, targeting, encontroScan, encontroParse, encontroAST, encontroSemantic, encontroIRT, encontroCodegen);
		FileWriter escribir;
		try {
			escribir = new FileWriter(archivo,true);
			escribir.write("Debug:Ast\n");
			escribir.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		arch=archivoEntrada;
		try{
			start();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void start() throws IOException{
		DecafLexer lexer = new DecafLexer(new ANTLRFileStream(arch));
		CommonTokenStream ctsTokens = new CommonTokenStream (lexer);
		DecafParser parser_ = new DecafParser(ctsTokens);
		ParseTree tree = parser_.program();
		System.out.println();

		Node nodo = new Node();
		nodo.setData("Program");
		nodo.setParent(null);
		nodo.setType("class");
		astTree.add(nodo);

		printTree(tree,0);

		System.out.println("");
		System.out.println("*****************SIMBOLOS********************");
		for(int i = 0; i< tablaSimbolos.size(); i++){
			simbolo coso;
			coso = (simbolo) tablaSimbolos.get(i);
			System.out.println("Nombre " + coso.getNombre() + " Tipo " + coso.getTipo() + " Largo "  + coso.getLargo() + " Parametros " + coso.getParametros() + " Papa " + coso.getPadre());
		}

		System.out.println("");

		revisionTipos(tree);
		System.out.println("*****************VARIABLES*******************");
		for(int i = 0; i< variables.size(); i++){
			variablesMetodos coso;
			coso = (variablesMetodos) variables.get(i);
			System.out.println("Nombre " + coso.getNombre() + " Tipo " + coso.getTipo() + " Recibe "  + coso.getRecibe() + " Parametros " + coso.getParametros() + " Papa " + coso.getPadre() + " Encontro " + coso.getEncontro());
		}
		System.out.println("");

	}

	public void crea_tabla(){
		tabla.put(48, "method_decl");
		tabla.put(77, "block");
		tabla.put(81, "field_decl");
		tabla.put(87, "statement_");
		tabla.put(117, "expr");
		tabla.put(173, "location");
		tabla.put(183, "bin_op");
		tabla.put(189, "bin_op");
		tabla.put(184, "exp");
		tabla.put(119, "block");
		tabla.put(80, "var_decl");
		tabla.put(88, "assign");
		tabla.put(107, "location");
		tabla.put(109, "location");
		tabla.put(141, "block");
		tabla.put(18, "exp");
		tabla.put(136, "exp");
		tabla.put(187, "exp");
		tabla.put(190, "exp");
		tabla.put(41, "var_global_decl");
		tabla.put(42, "var_global_decl");
		//tabla.put(, "");
	}

	public void revisionTipos(ParseTree t){
		for ( int i = 0; i < t.getChildCount(); i++ ){
			String palabra = "";
			crea_tabla();					
			if ((t.getChild(i).toString().charAt(0) == '[') && (t.getChild(i).toString().length()>1)){
				String numStr = "";
				numStr = numStr + t.getChild(i).toString().charAt(1);
				numStr = numStr + t.getChild(i).toString().charAt(2);
				if (t.getChild(i).toString().charAt(3) != ']' && t.getChild(i).toString().charAt(3) != ' '){
					numStr = numStr + t.getChild(i).toString().charAt(3);						
				}
				System.out.println(numStr); //da el numero de la hash
				if (tabla.get(Integer.parseInt(numStr)) == null ){
					palabra = "---block---";
				}else{
					palabra = tabla.get(Integer.parseInt(numStr)).toString();
				}
						
			}else{
				palabra = t.getChild(i).toString();
			}
			System.out.println("*"+palabra);
			if((palabra == "var_decl") || (palabra == "field_decl") || (palabra == "var_global_decl")){
	
				String tipoA = t.getChild(i).getChild(0).toString();
				String nombreA = t.getChild(i).getChild(1).toString();
				System.out.println("nombre "+nombreA);

							for(int p=0; p < t.getChild(i).getChildCount();p = p + 1){
								System.out.println("THIS"+t.getChild(i).getChild(p).toString());
								if(t.getChild(i).getChild(p).toString().length()>1){
									//System.out.println("THIS"+t.getChild(p).toString().substring(1,3));
									if(t.getChild(i).getChild(p).toString().substring(1,3).equals("88")){
										variablesMetodos vm = new variablesMetodos();
										vm.setEncontro(true);
										vm.setTipo(tipoA);
										String id = "";
										String recibe = "";
										String operacion = t.getChild(p).getText();
										System.out.println(operacion);

										int index = operacion.indexOf('=');
										id = operacion.substring(0,index);
										recibe = operacion.substring(index+1,operacion.length()-1);

										vm.setNombre(id);
										vm.setRecibe(recibe);
										vm.setPadre(t.getChild(p).toString());
										System.out.println("ESTOOOO"+nombreA+"ESTOOOO"+id+"ESTOOO");
										if(nombreA.equals(id)){
											variables.add(vm);
										}
										
									}
								}
							}
														
			}//cierra cond
		}			
	}

	public void printTree(ParseTree t, int indent) {
		if ( t != null ) {
			StringBuffer sb = new StringBuffer(indent);
			for ( int i = 0; i < indent; i++ ) 
				sb = sb.append("   "); 
			for ( int i = 0; i < t.getChildCount(); i++ ){
				if (!(t.getChild(i).toString().equals(";")) && !(t.getChild(i).toString().equals("(")) && !(t.getChild(i).toString().equals(")")) && !(t.getChild(i).toString().equals("{")) && !(t.getChild(i).toString().equals("}")) && !(t.getChild(i).toString().equals(","))){
					String palabra = "";
					crea_tabla();					
					if ((t.getChild(i).toString().charAt(0) == '[') && (t.getChild(i).toString().length()>1)){
						String numStr = "";
						numStr = numStr + t.getChild(i).toString().charAt(1);
						numStr = numStr + t.getChild(i).toString().charAt(2);
						if (t.getChild(i).toString().charAt(3) != ']' && t.getChild(i).toString().charAt(3) != ' '){
							numStr = numStr + t.getChild(i).toString().charAt(3);						}
						System.out.println(numStr); //da el numero de la hash
						if (tabla.get(Integer.parseInt(numStr)) == null ){
							palabra = "---block---";
						}else{
							palabra = tabla.get(Integer.parseInt(numStr)).toString();
						}
						
					}else{
						palabra = t.getChild(i).toString();
					}
					//palabra = t.getChild(i).toString();
					System.out.println(sb.toString() + palabra); 
					printTree((ParseTree)t.getChild(i), indent+1); 

					Node nodo2 = new Node();
					nodo2.setData(palabra);
					nodo2.setParent(t.getParent());
					nodo2.setType(null);
					astTree.add(nodo2);

					if((palabra == "var_decl") || (palabra == "field_decl") || (palabra == "method_decl") || (palabra == "var_global_decl")){
						if (t.getChild(i).getChild(0).toString().charAt(0) != '{'){
							simbolo sim = new simbolo();
							sim.setNombre(t.getChild(i).getChild(1).toString());
							sim.setTipo(t.getChild(i).getChild(0).toString());
	
							if(t.getChild(i).getChild(2).toString().equals("[")){
								sim.setLargo(Integer.parseInt(t.getChild(i).getChild(3).toString()));
							}else{
								sim.setLargo(0);
							}

							sim.setPadre(t.getChild(i).toString());
							
							if( (t.getChild(i).getChildCount()>3) && (t.getChild(i).getChild(2).toString().charAt(0) != '[') ){
								List<String> parametros = new ArrayList<String>();
								for(int j=3; j<(t.getChild(i).getChildCount()-2); j = j + 3){
									simbolo sim2 = new simbolo();
									sim2.setNombre(t.getChild(i).getChild(j+1).toString());
									sim2.setTipo(t.getChild(i).getChild(j).toString());
									sim2.setLargo(0);
									sim2.setPadre(t.getChild(i).getChild(1).toString());
									tablaSimbolos.add(sim2);
									parametros.add(t.getChild(i).getChild(j).toString());
								}
								sim.setParametros(parametros);
							}else{
								sim.setParametros(null);
							}

							tablaSimbolos.add(sim);
						}	
									
					}
				}
			} 
		}
 	}
		
	
}
