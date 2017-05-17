package it.polito.tdp.porto.db;

import it.polito.tdp.porto.model.Model;

public class TestPortoDAO {
	
	public static void main(String args[]) {
		PortoDAO pd = new PortoDAO() ; 
		Model m  = new Model ( );;
		
//		System.out.println(pd.getAutore(85));
//		System.out.println(pd.getArticolo(2293546));
//		System.out.println(pd.getArticolo(1941144));
//		
		System.out.println(pd.getTuttiAutori(m.getIdMap())) ;
//		System.out.println(pd.getAllPubblicazionePerAutore(85)) ;

//		System.out.println(m.getIdMap().toString());
		
//		//System.out.println(pd.getCoautori(85, m.getIdMap()));
//	System.out.println(pd.getAllPubblicazioniPer2Autori(85, 2185));
	}

}
