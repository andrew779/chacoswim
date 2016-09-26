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
	
	public String gotId(String key,String para){
		String query = "";
		PreparedStatement pst=null;
		ResultSet rs = null;
		String str = "";
		try {
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
			default:
				break;
			}
		}catch (SQLException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
			//return "";
		}return str;
	}
}
