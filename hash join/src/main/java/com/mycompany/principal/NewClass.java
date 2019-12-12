package com.mycompany.principal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import com.mycompany.Model.Tabela;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewClass {
    public static void main(String args[]){
        
        try {
            //new Thread() {
            //Process p = Runtime.getRuntime().exec("python3 /home/megazzzmata/NetBeansProjects/Principal/DeepThree/arvore.py");
            //}.start();
            String SQL = "select nome_bolsa , nome_alu,id_alu from alunos,bolsa,nota where id_alu=id_aluno_bolsista and id_alu=id_aluno and nota_aluno>9".replaceAll(" ", "");
            SQL = "select r_regionkey, n_regionkey from nation,region where r_regionkey= n_regionkey and n_regionkey>2 ".replaceAll(" ", "");
            SQL = "select l_quantity from lineitem, partsupp, part where l_partkey = p_partkey and l_suppkey = ps_suppkey and ps_partkey<40 and p_partkey<400 and l_partkey<5".replaceAll(" ", "");
            String[] sql = fragmentarSQL(SQL);
            
            List<String> listaCamposNecessarios = CamposNecessariosConsulta(sql[0],sql[2]);
            List<String> listaCamposProjecao = CamposProjecao(sql[0]);
            List<String> listaCriteriosSelecao = CriteriosSelecaoTuplas(sql[2]);
            
            String[] listaTabelasSQL = sql[1].split(",");
            Tabela[] listaTabelas = criarTabelas(listaTabelasSQL,listaCamposNecessarios,listaCriteriosSelecao);
            
            List<String> listaCriterioJuncao = retornaCriteriosJuncao(sql[2]);
            
            List<Tabela> listaTabelasJuntas = new ArrayList();
            List<Tabela> listaTabelasSeparadas = new ArrayList();
            
            for(Tabela i:listaTabelas){
                listaTabelasSeparadas.add(i);
            }
            
            Tabela resultado = listaTabelasSeparadas.remove(0);
            Tabela u =null;
            String atributo;
            String atr1,atr2;
            String atributo1 = null ,atributo2 = null;
            while(!listaTabelasSeparadas.isEmpty()){
                for(String i:listaCriterioJuncao){
                    
                    atr1 = i.split("=")[0];
                    atr2 = i.split("=")[1];
                    if(resultado.hasAtributo(atr1) || resultado.hasAtributo(atr2)){
                        if(resultado.hasAtributo(atr1)){
                            atributo1 = atr1;
                            atributo2 = atr2;
                            u = retornaTabelaComAtributo(listaTabelasSeparadas, atributo2);
                        }else if(resultado.hasAtributo(atr2)){
                            atributo1 = atr2;
                            atributo2 = atr1;
                            u = retornaTabelaComAtributo(listaTabelasSeparadas, atributo2);
                        }
                        listaTabelasSeparadas.remove(u);
                        listaTabelasJuntas.add(u);
                        resultado.DeleteNoUsedColluns(listaCamposNecessarios);
                        u.DeleteNoUsedColluns(listaCamposNecessarios);
                        
                        resultado = resultado.HashJoin(u, atributo1, atributo2);
                        
                        listaCamposNecessarios.remove(atributo1);
                        listaCamposNecessarios.remove(atributo2);
                        
                    }
                }
            }
            resultado.DeleteNoUsedColluns(listaCamposProjecao);
            
            
        } catch (Exception e){//IOException ex) {
            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, e);
        }
            
    }
    
    public static Tabela retornaTabelaComAtributo(List<Tabela> listaTabelas, String atributo){
        for(Tabela i: listaTabelas){
            if(i.hasAtributo(atributo)){
                return i;
            }
        }
        return null;
    }
    
    public static Tabela[] criarTabelas(String[] Tabelas,List<String> atributosNecessarios,List<String> listaCriteriosSelecao) throws IOException{
        Tabela[] listaTabelas = new Tabela[Tabelas.length];
        for(int i=0;i<Tabelas.length;i++){
            listaTabelas[i] = new Tabela(Tabelas[i],listaCriteriosSelecao,true);
//            listaTabelas[i].DeleteNoUsedColluns(atributosNecessarios);
        }
        return listaTabelas;
    }
    
    public static String[] fragmentarSQL(String consultaSQL){
        String[] from = consultaSQL.split("from");
        String[] sql = new String[3];
        sql[0] = from[0].split("select")[1];
        
        sql[1] = from[1].split("where")[0];
        sql[2] = from[1].split("where")[1];
        return sql;
    }
    
    public static List<String> CamposNecessariosConsulta(String projecao, String selecao){
        List<String> ListaCamposNecessarios = new ArrayList();
        for(String i:projecao.split(",")){
            ListaCamposNecessarios.add(i);
        }
        for(String i:selecao.split("and")){
            
            if(i.contains("=")){
                ListaCamposNecessarios.add(i.split("=")[0]);
                ListaCamposNecessarios.add(i.split("=")[1]);
            }else if(i.contains("<") || i.contains(">")){
                String texto = i;
                texto = texto.replaceFirst("<", "@");
                texto = texto.replaceFirst(">", "@");
                ListaCamposNecessarios.add(texto.split("@")[0]);
            }
        }
        return ListaCamposNecessarios;
    }
    
    public static List<String> CamposProjecao(String projecao){
        List<String> ListaCamposProjecao = new ArrayList();
        for(String i:projecao.split(",")){
            ListaCamposProjecao.add(i);
        }
        return ListaCamposProjecao;
    }

    public static List<String> CriteriosSelecaoTuplas(String clausulaWhere){
        List<String> ListaCriterioSelecaoTuplas = new ArrayList();
        for(String i: clausulaWhere.split("and")){
            if(i.contains("<") || i.contains(">")){
                String texto = i;
                ListaCriterioSelecaoTuplas.add(i);
            }
        }
        return ListaCriterioSelecaoTuplas;
    }
    
    public static List<String> retornaCriteriosJuncao(String juncoes){
        List<String> listaCriterioJuncao = new ArrayList();
            for(String i:juncoes.split("and")){
                if(i.contains("=")){
                    listaCriterioJuncao.add(i);
                }
            }
        return listaCriterioJuncao;
    }
}
