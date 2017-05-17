package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.AuthorIdMap;
import it.polito.tdp.porto.model.Paper;

public class PortoDAO {

	/*
	 * Dato l'id ottengo l'autore.
	 */
	public Author getAutore(int id) {

		final String sql = "SELECT * FROM author where id=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				rs.close();
				conn.close();
				
				return autore;
			}
			rs.close();
			conn.close();
			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getArticolo(int eprintid) {

		final String sql = "SELECT * FROM paper where eprintid=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, eprintid);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				rs.close();
				conn.close();
				return paper;
			}
			rs.close();
			conn.close();
			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	public List<Author> getTuttiAutori(AuthorIdMap idMap) {

		final String sql = "SELECT * FROM author ORDER BY lastname ASC";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			List<Author> autori = new ArrayList<Author> () ;
			
			while (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				autore = idMap.put(autore) ;
				autori.add(autore) ;				
				}
			rs.close();
			conn.close();
			return autori;
			
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	public List<Paper> getAllPubblicazionePerAutore (int id){
		
		final String sql = "SELECT eprintid FROM creator WHERE authorid=? ORDER BY eprintid ASC";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();
			
			List <Paper> papers = new ArrayList<Paper> () ;

			while (rs.next()) {
				Paper p = this.getArticolo(rs.getInt("eprintid")) ; 
				papers.add(p) ;
				
			}
			rs.close();
			conn.close();
			return papers;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	
	}
	
	public Set<Author> getCoautori(int idautore, AuthorIdMap map, List<Paper> papers ) {
		
		final String sql = "SELECT authorid FROM creator WHERE eprintid=? " ;
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			Set<Author> coautori = new HashSet<Author> () ;
			
			
			for(Paper p : papers){
				st.setInt(1, p.getEprintid());
				ResultSet rs = st.executeQuery();
				
				while (rs.next()) {
					if(rs.getInt("authorid") != idautore){
						Author a = map.get(rs.getInt("authorid")) ;
						coautori.add(a) ;
					}
				}
			rs.close();}
			
			conn.close();
			
			return coautori ;
		}
		catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
public Paper getPubblicazionePer2Autori (int id1, int id2){
		
		final String sql = "SELECT c1.eprintid as id "
				+ " FROM creator c1, creator c2 "
				+ " WHERE c1.eprintid=c2.eprintid AND C1.authorid=? AND c2.authorid=? ";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id1);
			st.setInt(2, id2);

			ResultSet rs = st.executeQuery();
			
			Paper p ;

			if (rs.next()) {
				p = this.getArticolo(rs.getInt("id")) ; 
				rs.close();
				conn.close();

				return p ;
				
			}
			rs.close();
			conn.close();
			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	
	}
}