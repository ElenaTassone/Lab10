package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {

	private UndirectedGraph <Author, DefaultEdge> grafo ;
	private PortoDAO dao ;
	private List <Author> autori ; 
	private AuthorIdMap idMap ;
	
	
	public Model() {
		this.dao = new PortoDAO();
		idMap = new AuthorIdMap () ; 
		
		}
	
	public AuthorIdMap getIdMap(){
		if(idMap==null)
			this.addAutori() ;
		return idMap ;
	}

	public void creaGrafo (){
		grafo = new SimpleGraph <Author, DefaultEdge>(DefaultEdge.class) ;
 		autori = dao.getTuttiAutori(idMap);
		Graphs.addAllVertices(grafo,autori ) ;
		for(Author a : autori){
			List<Paper> papers = dao.getAllPubblicazionePerAutore(a.getId());
			Set<Author> coautori  = dao.getCoautori(a.getId(), idMap, papers) ;
			for(Author c : coautori){
				if(!grafo.containsEdge(this.getIdMap().get(c.getId()), this.getIdMap().get(a.getId()) ))
					grafo.addEdge(a, c) ;
			}
		}
		
	}

	public UndirectedGraph <Author, DefaultEdge> getGrafo(){
		if(grafo==null){
			this.creaGrafo() ;
		}
		return grafo ;
	}
	public List<Author> addAutori (){
		if(autori==null){
			autori = new ArrayList <Author> (dao.getTuttiAutori(idMap)) ;
		}
			return autori ;
		
	}
	
	public List <Author> getCoautori(Author a){
		Set<DefaultEdge> collaborazioni = grafo.edgesOf(a) ;
		List<Author> coautori = new ArrayList <Author> () ;
		for(DefaultEdge c : collaborazioni){
			Author a1 = grafo.getEdgeSource(c);
			Author a2 = grafo.getEdgeTarget(c);
			if(a1!=a){
				if(!coautori.contains(a1))
				coautori.add(a1);
			}
			if(a2!=a){
				if(!coautori.contains(a2))
				coautori.add(a2);
				}
		}
		return coautori ;
	}
	
	
	public List<Paper> getPubb (Author a1, Author a2){
	
		DijkstraShortestPath<Author, DefaultEdge> dsp = new DijkstraShortestPath<Author, DefaultEdge> (grafo, a1, a2) ; 
		List <DefaultEdge> edges= new ArrayList<DefaultEdge> (dsp.findPathBetween(grafo, a1, a2)) ;
		List<Paper> sequenza = new ArrayList<Paper> () ;
		for(DefaultEdge d : edges){
			Author s = grafo.getEdgeSource(d) ;
			Author t = grafo.getEdgeTarget(d) ;
			sequenza.add(dao.getPubblicazionePer2Autori(s.getId(), t.getId()));
		}
		return sequenza ;
	}

	public List<Author> getNonCoautori(List<Author> coautori, Author primo) {
		List<Author> nonCoautori = new ArrayList<Author> () ;
		for(Author a : autori){
			if(!coautori.contains(a) && a!=primo)
				nonCoautori.add(a) ;
		}
		return nonCoautori;
	}
	
	
}

