package chacoswim;
import java.sql.*;
import javax.swing.*;

	
public class sqliteConnection {
	Connection conn=null;
	public  static Connection dbConnector(){
		try{
			Class.forName("org.sqlite.JDBC");
			//ClassLoader c1 = sqliteConnection.class.getClassLoader();
			//String str = c1.getResource("chacoswim.sqlite").toString();
			
			Connection conn = DriverManager.getConnection("jdbc:sqlite:DontTouchMe.sqlite");
					//+ "::resource:chacoswim.sqlite");
			//JOptionPane.showMessageDialog(null, "sqliteConnection:("+c1.getResource("chacoswim.sqlite"));		
	
			return conn;
		}catch(Exception e){
			//JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
}
