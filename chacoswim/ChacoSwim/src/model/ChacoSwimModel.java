package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;
import chacoswim.sqliteConnection;
import presenter.ChacoSwimMethods;

public class ChacoSwimModel {
	public static final String ROWSET_IMPL_CLASS = "com.sun.rowset.CachedRowSetImpl";
	ChacoSwimMethods csp;
	Connection conn;
	public ChacoSwimModel(){
		conn = sqliteConnection.dbConnector();
	}
	public ChacoSwimModel(ChacoSwimMethods csp){
		
	}
	public void cspSetter(ChacoSwimMethods csp){
		this.csp = csp;
	}
	public void executeWithoutRS(String query){
		try {
			PreparedStatement pst=conn.prepareStatement(query);
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public CachedRowSet excuteWithRS(String query){

		CachedRowSet crs;
		try {
			Class<?> c = Class.forName(ROWSET_IMPL_CLASS);
			crs = (CachedRowSet) c.newInstance();
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			crs.populate(rs);
			rs.close();
			pst.close();
			return crs;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
		
		
	}
}
