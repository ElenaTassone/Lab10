package it.polito.tdp.porto.model;

import java.util.HashMap;
import java.util.Map;

public class AuthorIdMap {

private Map<Integer,Author> map ;
	
	public AuthorIdMap() {
		map = new HashMap<>() ;
	}
	
	public Author get(Integer id) {
		return map.get(id) ;
	}
	
	public Author put(Author a) {
		Author old = map.get(a.getId()) ; 
		if(old==null) {
			map.put(a.getId(), a) ;
			return a ;
		} else {
			return old ;
		}
	}
	
	public String toString(){
		String r = "" ;
		for(Author a : map.values())
			r+= a.toString() ;
		return r ;
	}
	
}
