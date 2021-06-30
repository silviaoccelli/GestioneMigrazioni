package it.polito.tdp.borders.db;


import it.polito.tdp.borders.model.Adiacenza;
import it.polito.tdp.borders.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BordersDAO {
	
	public List<Country> loadAllCountries(Map<Integer,Country> idMap) {
		
		String sql = 
				"SELECT ccode,StateAbb,StateNme " +
				"FROM country " +
				"ORDER BY StateAbb " ;

		try {
			Connection conn = DBConnect.getConnection() ;
		PreparedStatement st = conn.prepareStatement(sql) ;
				ResultSet rs = st.executeQuery() ;
			List<Country> list = new LinkedList<Country>() ;
			
			while( rs.next() ) {
				
				if(idMap.get(rs.getInt("ccode")) == null){
				
					Country c = new Country(
							rs.getInt("ccode"),
							rs.getString("StateAbb"), 
							rs.getString("StateNme")) ;
					idMap.put(c.getcCode(), c);
					list.add(c);
				} else 
					list.add(idMap.get(rs.getInt("ccode")));
			}
			
			conn.close() ;
			return list ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null ;
	}
	
	public List<Country> getVertici (Integer anno, Map<Integer, Country> idMap){
		String sql = " SELECT * "
				+ "FROM country "
				+ "WHERE CCode IN ( "
				+ "SELECT state1no "
				+ "FROM contiguity "
				+ "WHERE year <= ? "
				+ "AND conttype = 1) " ;
		
		
		List<Country> result = new LinkedList<Country>() ;
		Connection conn = DBConnect.getConnection() ;

		try {

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery() ;
			
		while( rs.next() ) {
				if(idMap.get(rs.getInt("ccode")) == null){
				
				Country c = new Country(
						rs.getInt("ccode"),
						rs.getString("StateAbb"), 
						rs.getString("StateNme")) ;
				idMap.put(c.getcCode(), c);
			result.add(c);
			} else 
			result.add(idMap.get(rs.getInt("ccode")));
		}
		
		conn.close() ;
		return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null ;
	}


	public List<Adiacenza> getAdiacenze(Integer anno) {
		String sql="SELECT state1no, state2no "
				+ "FROM contiguity "
				+ "WHERE year <= ? "
				+ "AND conttype = 1 "
				+ "AND state1no < state2no ";
		List<Adiacenza> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection() ;

		try {

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery() ;
			
	
			while( rs.next() ) {
				result.add(new Adiacenza(rs.getInt("state1no"), rs.getInt("state2no")));
			}
			
			conn.close() ;
		   return result ;
              } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null ;
	}
	
	
	
	
	
}
