/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Model;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.mycompany.principal.Conexao;
import com.sun.jdi.PathSearchingVirtualMachine;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 *
 * @author megazzzmata
 */

public class Tabela {
    public String nomeTabela;
    public List<String> listaAtributos = new ArrayList();
    public List<String> buckets = new ArrayList();
    
    static int a=9;
    static int b=3;
    static int p=92;
    static int m=10;
    
    String fileSeparator = System.getProperty("file.separator");
    
    public Tabela(){}
    
    public Tabela(String nomeTabela,List<String> listaCriteriosSelecao, boolean Salvo){
        this.nomeTabela = nomeTabela;
        Connection conn = Conexao.abrirConexao();
        String sql = "SELECT col.column_name\n" +
                    "FROM INFORMATION_SCHEMA.COLUMNS col,INFORMATION_SCHEMA.TABLES tbl\n" +
                    "where col.table_name = tbl.table_name and "
                    + "tbl.table_name like '"+nomeTabela+"'";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs != null){
                
                while(rs.next()){
                    listaAtributos.add( rs.getString(1) );
                }
            }
            
            String campoSelecao = null;
            String tipo = null;
            boolean Selecao = false;
            int valor = 0;
            String[] valores;
            String texto;
            String att;
            int indice = 0;
            for(String i:listaCriteriosSelecao){
                texto = i;
                if(texto.contains(">")){
                    tipo = ">";
                }else if(texto.contains("<")){
                    tipo = "<";
                }
                valores = texto.split(tipo);
                att = valores[0];
                if(this.hasAtributo(att)){
                    Selecao=true;
                    indice = listaAtributos.indexOf(att) ;
                    valor = Integer.parseInt(valores[1]);
                }
            }
            
            File fileAUX = new File(nomeTabela +"AUX.data");
            fileAUX.createNewFile();
            
            FileInputStream fstream = new FileInputStream(nomeTabela +".data");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String tuplaArquivo;
            
            String fileData;
            String[] atributos;
            while ((tuplaArquivo = br.readLine()) != null)   {
                fileData = "";
                atributos = tuplaArquivo.split(";");
                    if(Selecao){
                        salvarDadoLista(tipo, atributos, indice, valor,fileAUX,fileData);
                    }else{
                        for(int j=0;j<listaAtributos.size();j++){
                            fileData += atributos[j].toString() +';' ;
                        }
                        fileData+='\n';
                        Files.write(Paths.get(nomeTabela +"AUX.data"),fileData.getBytes(), StandardOpenOption.APPEND);
                    }
            }
            fstream.close();
            
            Files.deleteIfExists(Paths.get(nomeTabela +".data"));
            Files.copy(Paths.get(nomeTabela +"AUX.data"), Paths.get(nomeTabela +".data"));

            Files.deleteIfExists(Paths.get(nomeTabela +"AUX.data"));
            
        }catch(Exception e){
            System.out.println("Falha criacao da tabela"+e);
        }
    }
    
    public Tabela(String nomeTabela,List<String> listaCriteriosSelecao){
        this.nomeTabela = nomeTabela;
        Connection conn = Conexao.abrirConexao();
        String sql = "SELECT col.column_name\n" +
                    "FROM INFORMATION_SCHEMA.COLUMNS col,INFORMATION_SCHEMA.TABLES tbl\n" +
                    "where col.table_name = tbl.table_name and "
                    + "tbl.table_name like '"+nomeTabela+"'";
        String sqlColuna = "SELECT ";
        
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs != null){
                
                while(rs.next()){
                    listaAtributos.add( rs.getString(1) );
                }
                
                for(String i :listaAtributos){
                    sqlColuna= sqlColuna+i+" ";
                    if(i!= listaAtributos.get( listaAtributos.size()-1 )){
                        sqlColuna= sqlColuna+", ";
                    }
                    
                }
                
                String absoluteFilePath = nomeTabela + ".data";
                File file = new File(absoluteFilePath);
                file.createNewFile();
                
                sqlColuna = sqlColuna + "FROM "+nomeTabela;
                ps = conn.prepareStatement(sqlColuna);
                rs = ps.executeQuery();
                
                String campoSelecao = null;
                String tipo = null;
                boolean Selecao = false;
                int valor = 0;
                String[] valores;
                String texto;
                String att;
                int indice = 0;
                for(String i:listaCriteriosSelecao){
                    texto = i;
                    if(texto.contains(">")){
                        tipo = ">";
                    }else if(texto.contains("<")){
                        tipo = "<";
                    }
                    valores = texto.split(tipo);
                    att = valores[0];
                    if(this.hasAtributo(att)){
                        Selecao=true;
                        indice = listaAtributos.indexOf(att) + 1 ;
                        valor = Integer.parseInt(valores[1]);
                    }
                }
                
                String fileData;
                
                if(rs != null){
                    while(rs.next()){
                        fileData = "";
                        if(Selecao){
                            salvarDado(tipo, rs, indice, valor,file,fileData);
                        }else{
                            for(int j=0;j<listaAtributos.size();j++){
                                fileData += rs.getObject(j+1).toString() +';' ;
                            }
                            fileData+='\n';
                            Files.write(Paths.get(nomeTabela +".data"),fileData.getBytes(), StandardOpenOption.APPEND);
                        }
                    }
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
    
    public Tabela(Tabela t1, Tabela t2,int indiceT1,int indiceT2) throws FileNotFoundException, IOException{
        this.nomeTabela = t1.nomeTabela+"_"+t2.nomeTabela;
        
        this.listaAtributos.addAll(t1.listaAtributos);
        this.listaAtributos.addAll(t2.listaAtributos);
        
        String fileData = "";
        String absoluteFilePath = nomeTabela + ".data";
        File file = new File(absoluteFilePath);
        file.createNewFile();
        
        for(int i=0;i<m;i++){
            List<String> lines = Files.readAllLines(Paths.get(t1.nomeTabela+"/"+i+".bkt"));
            List<String> lines2 = Files.readAllLines(Paths.get(t2.nomeTabela+"/"+i+".bkt"));
            
            for(int a=0;a<lines.size();a++){
                for(int b=0;b<lines2.size();b++){
                    fileData = "";
                    
                    String[] splitaA = lines.get(a).split(";");
                    String[] splitaB = lines2.get(b).split(";");
                    
                    
                    if(splitaA[indiceT1].equals( splitaB[indiceT2] )){
                        
                        for(int dd=0;dd<splitaA.length;dd++){
                            fileData += splitaA[dd].toString()+ ";";
                        }
                        for(int dd=0;dd<splitaB.length;dd++){
                            fileData += splitaB[dd].toString()+ ";";
                        }
                        
                        fileData+='\n';
                        Files.write(Paths.get(nomeTabela +".data"),fileData.getBytes(), StandardOpenOption.APPEND);

                    }
                }
            }
            
        }
        
    }
    
    public void DeleteNoUsedColluns( List<String> ColunasProjecao) throws IOException{
        File file = new File(nomeTabela +"AUX.data");
        file.createNewFile();
        
        List<String> listaAtributosNovos = new ArrayList();
        if(ColunasProjecao.get(0).equals( "*" )){
            System.out.println("Nenhum atibuto foi desconsiderado");
        }else{
            Set<Integer> colunasValidas = new HashSet<Integer>();
            for(int i=0; i<listaAtributos.size();i++){
                for(String interadoProjecao : ColunasProjecao){
                    if(interadoProjecao.equals( listaAtributos.get(i) )){
                        colunasValidas.add(i);
                        listaAtributosNovos.add(listaAtributos.get(i));
                        break;
                    }
                }
                
            }
            
            String novaTupla = "";
            List<String> tuplasTabela = Files.readAllLines(Paths.get(nomeTabela+".data"));
            for(int i=0;i<tuplasTabela.size();i++){
                novaTupla = "";
                String[] splitTupla = tuplasTabela.get(i).split(";");
                for(int indiceElementoTupla: colunasValidas){
                    novaTupla+= splitTupla[indiceElementoTupla] + ";";
                }
                novaTupla+='\n';
                Files.write(Paths.get(nomeTabela +"AUX.data"),novaTupla.getBytes(), StandardOpenOption.APPEND);
            }
            Files.deleteIfExists(Paths.get(nomeTabela +".data"));
            Files.copy(Paths.get(nomeTabela +"AUX.data"), Paths.get(nomeTabela +".data"));

            Files.deleteIfExists(Paths.get(nomeTabela +"AUX.data"));
            
            listaAtributos = listaAtributosNovos;
        }
        
    }
    
    public Tabela HashJoin(Tabela t, String atr1, String atr2) throws IOException{
        this.Bucketizar(atr1 );
        t.Bucketizar(atr2 );
        
        int indice1 = this.listaAtributos.indexOf(atr1);
        int indice2 = t.listaAtributos.indexOf(atr2);
        
        return new Tabela(this, t, indice1, indice2);
    }
    
    public List<String> Bucketizar(String Atributo ) throws IOException{
        
        List<String> ListaCaminhosBucket = new ArrayList();
        (new File(nomeTabela)).mkdirs();
        
        for(int i=0;i<m;i++){
            String absoluteFilePath = nomeTabela + fileSeparator + i + ".bkt";
            File file = new File(absoluteFilePath);
            file.createNewFile();
            ListaCaminhosBucket.add(absoluteFilePath);
        }
        String fileData = "";
        int indice = listaAtributos.indexOf(Atributo);
        
        List<String> dadosTabela = Files.readAllLines(Paths.get(nomeTabela+".data"));
        
        for(int indiceTupla=0;indiceTupla<dadosTabela.size();indiceTupla++){//percorre linha
            Object x = dadosTabela.get(indiceTupla).split (";")[indice];
            int nBucket = FuncaoHash( (int)Integer.parseInt(x.toString()));
            
            fileData = fileData + dadosTabela.get(indiceTupla);
            fileData = fileData+'\n';
            
            Files.write(Paths.get(nomeTabela + fileSeparator + nBucket + ".bkt"), 
                    fileData.getBytes(), StandardOpenOption.APPEND);
            fileData = "";
        }
        
        buckets = ListaCaminhosBucket;
        return ListaCaminhosBucket;
    }
    
    private int FuncaoHash(int x){
        return ((a*x+b)%p)%m;
    }
    
    public boolean hasAtributo(String atributoBusca){
        for(String i:listaAtributos){
            if(i.equals(atributoBusca)){
                return true;
            }
        }
        return false;
    }
    
    public void salvarDado(String tipo,ResultSet rs,int indice,int valor,File file,String fileData){
        try{
            if(tipo.equals(">")){
                if((int)rs.getObject(indice)>valor){
                    for(int j=0;j<listaAtributos.size();j++){
                        fileData += rs.getObject(j+1).toString() + ";";
                    }
                    fileData+='\n';
                    Files.write(Paths.get(nomeTabela +".data"),fileData.getBytes(), StandardOpenOption.APPEND);
                }
            }else{
                if((int)rs.getObject(indice)<valor){
                    for(int j=0;j<listaAtributos.size();j++){
                        fileData += rs.getObject(j+1).toString() + ";";
                    }
                    fileData+='\n';
                    Files.write(Paths.get(nomeTabela +".data"),fileData.getBytes(), StandardOpenOption.APPEND);
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void salvarDadoLista(String tipo, String[] listaValoresAtributo,int indice,int valor,File file,String fileData){
        try{
            if(tipo.equals(">")){
                if( (Integer.parseInt(listaValoresAtributo[indice])) > valor){
                    for(int j=0;j<listaAtributos.size();j++){
                        fileData += listaValoresAtributo[j].toString() + ";";
                    }
                    fileData+='\n';
                    Files.write(Paths.get(nomeTabela +"AUX.data"),fileData.getBytes(), StandardOpenOption.APPEND);
                }
            }else{
                if( (Integer.parseInt(listaValoresAtributo[indice])) < valor ){
                    for(int j=0;j<listaAtributos.size();j++){
                        fileData += listaValoresAtributo[j].toString() + ";";
                    }
                    fileData+='\n';
                    Files.write(Paths.get(nomeTabela +"AUX.data"),fileData.getBytes(), StandardOpenOption.APPEND);
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
