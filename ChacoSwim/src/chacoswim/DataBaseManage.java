package chacoswim;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class DataBaseManage extends ChacoSwim{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection conn = null;
	public DataBaseManage() {
		super();
		conn = sqliteConnection.dbConnector();
	}
	
	public String getCurLevel(String sid){
		
		String query = "";
		PreparedStatement pst=null;
		ResultSet rs = null;
		String str = "";
		try{
			query = "select CurLevel from students where sid = "+sid;
			pst = conn.prepareStatement(query);
			rs = pst.executeQuery();
			if(!rs.next()){
				return null;
			}
			str = rs.getString("CurLevel");
			pst.close();
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return str;
	}
	
	public String gotId(String key,String para){
		String query = "";
		PreparedStatement pst=null;
		ResultSet rs = null;
		String str = "";
		try {
			if(key.equals("terms")||key.equals("location")||key.equals("coach")||key.equals("level")){
				query = "select id from '"+key+"' where name = '"+para+"'";
				pst = conn.prepareStatement(query);
				rs = pst.executeQuery();
				str = rs.getString("id");
				pst.close();
				rs.close();
			}
			else{
				JOptionPane.showMessageDialog(null, "Couldn't find ID for "+para+" from "+key);
				return null;
			}
			/*
			switch (key) {
			case "terms":
				query = "select id from '"+key+"' where name = '"+para+"'";
				pst = conn.prepareStatement(query);
				rs = pst.executeQuery();
				str = rs.getString("id");
				break;
			case "location":
				query = "select id from '"+key+"' where name = '"+para+"'";
				pst = conn.prepareStatement(query);
				rs = pst.executeQuery();
				str = rs.getString("id");
				break;
			case "coach":
				
			default:
				break;
			}*/
		}catch (SQLException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
			//return "";
		}
		return str;
	}
}