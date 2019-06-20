package it.polito.tdp.ufo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.ufo.model.Anno;
import it.polito.tdp.ufo.model.Sighting;

public class SightingsDAO {
	
	public List<Sighting> getSightings() {
		String sql = "SELECT * FROM sighting" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Sighting> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Sighting(res.getInt("id"),
							res.getTimestamp("datetime").toLocalDateTime(),
							res.getString("city"), 
							res.getString("state"), 
							res.getString("country"),
							res.getString("shape"),
							res.getInt("duration"),
							res.getString("duration_hm"),
							res.getString("comments"),
							res.getDate("date_posted").toLocalDate(),
							res.getDouble("latitude"), 
							res.getDouble("longitude"))) ;
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Anno> getAllAnni() {
		String sql = "SELECT datetime FROM sighting" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Anno> list = new LinkedList<Anno>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					Anno anno = new Anno(res.getTimestamp("datetime").toLocalDateTime().getYear());
					if(list.contains(anno))
						list.get(list.indexOf(anno)).setAvvistasmenti(list.get(list.indexOf(anno)).getAvvistasmenti() + 1);
					else {
						anno.setAvvistasmenti(1);
						list.add(anno);
					}
					
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			Collections.sort(list);
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<String> getAllStati(int anno) {
		String sql = "SELECT distinct state FROM sighting WHERE year(datetime) = ?" ;
		List<String> list = new ArrayList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(res.getString("state"));
					
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<String> statiCollegati(String s1, int anno) {
		String sql = "SELECT distinct s2.state FROM sighting AS s1, sighting AS s2 WHERE s1.DATETIME < s2.DATETIME AND s1.state=? AND s2.state!=? AND s2.country='us' AND s1.year(datetime) =? AND s2.year(datetime)  =?";
		List<String> list = new ArrayList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, s1);
			st.setString(2, s1);
			st.setInt(3, anno);
			st.setInt(4, anno);
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(res.getString("s2.state"));
					
				} catch (Throwable t) {
					t.printStackTrace();
					return null;
				}
			}
			
			conn.close();
			return list;
			
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
