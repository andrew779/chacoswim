package chacoswim;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;

public class TabCoaches {
	private Connection conn=null;
	private JTable table;
	public TabCoaches(JTable table) {
		super();
		this.table=table;
		// TODO Auto-generated constructor stub
		conn=sqliteConnection.dbConnector();
	}
	
	public void refreshTable(){
		String query = "select * from coach";
		try {
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			pst.close();
			rs.close();
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void addCNew(String name,String cell,String email){
		String query = "INSERT INTO coach (name,cell,email) VALUES (?,?,?)";
		try {
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, name);
			pst.setString(2, cell);
			pst.setString(3, email);
			pst.execute();
			pst.close();
			refreshTable();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e);
		}
		
	}
	
	public void editeC(String id,String name,String cell,String email){
		
		String query = "UPDATE coach SET name = ?, cell = ?, email = ? where id = ?";
		try {
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, name);
			pst.setString(2, cell);
			pst.setString(3, email);
			pst.setString(4, id);
			pst.execute();
			pst.close();
			refreshTable();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void deleteC(String id){
		String query = "DELETE FROM coach where id = ?";
		try {
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, id);
			pst.execute();
			pst.close();
			refreshTable();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e);
		}
	}

}
