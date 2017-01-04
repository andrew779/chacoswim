package chacoswim;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;

public class StatisticMethod {

	private String term="";
	
	private Connection conn = sqliteConnection.dbConnector();
	public StatisticMethod() {
		
	}
	public StatisticMethod(String term) {
		super();
		this.term = term;
	}
	public String getTotal(){
		String total="";
		try{
			String query="select count(*) from students where sid != 0";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			
			
			
			while(rs.next()){
				total= rs.getString(1);
			}
			
			pst.close();
			rs.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return total;
	}
	
	public String getDays(String day){
		String str="";
		try{
			String query="select count(*) from active_record WHERE day='"+day+"' AND sid != 0";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			
			
			
			while(rs.next()){
				str= day+": "+rs.getString(1);
			}
			
			pst.close();
			rs.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return str;
	}
	
	public String[] getCoaches(){
		ArrayList<String> list = new ArrayList<String>();
		try{
			String query="select name from coach";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			
			
			
			while(rs.next()){
				list.add(rs.getString("name"));
			}
			
			pst.close();
			rs.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		String[] str = new String[list.size()];
		list.toArray(str);
		return str;
	}
	
	private String[] getTerms(){
		
		try {
			List<String> list = new ArrayList<String>();
			String query="select name from terms";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				list.add(rs.getString("name"));
			}
			String[] str= new String[list.size()];
			list.toArray(str);
			return str;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public String[] getCoaches_Term(String name){
		ArrayList<String> list = new ArrayList<String>();
		String[] terms = getTerms();
		
		try{
			for(String i:terms){
				
				String query = "select count(*) from active_record a JOIN coach c ON c.id = a.coachID "
						+ "JOIN terms t ON t.id = a.termID WHERE a.sid != 0 AND c.name='"+name+"' AND t.name = '"+i+"'";
				PreparedStatement pst=conn.prepareStatement(query);
				ResultSet rs=pst.executeQuery();
				while(rs.next())list.add(i+" : "+rs.getString(1));			
			}
			String[] str=new String[list.size()];
			str = list.toArray(str);
			return str;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
		
	}
	
	public void fillTerm_Day(JTable table,String term,String day){
		ResultSet rs=null;
		try{
			String query="";
			if(day.equals("All")){
				query = "select a.SID, s.FirstName, s.LastName, level.name as CurLevel, a.Time, c.name as Coach from 'active_record' a"
						+ " JOIN students s on s.sid = t.sid"
						+ " JOIN coach c on c.id = s.coachID ORDER BY t.day, t.time, t.line, a.CurLevel";
			}
			else{
				query = "select t.sid, s.FirstName, s.LastName, s.CurLevel, t.time, t.coach from '"+term+"' t JOIN students s on s.sid = t.sid WHERE t.day = '"+day+ 
					"' ORDER BY t.day, t.time, t.line, s.CurLevel";
				}
			PreparedStatement pst=conn.prepareStatement(query);
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			rs.close();
			pst.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void fillTerm_Coach(JTable table,String term,String coach){
		ResultSet rs=null;
		try{
			String query="";
			query = "select a.sid, l.name as Location, s.FirstName, s.LastName, level.name as CurLevel, a.time,c.name as Coach from 'active_record' a"
					+ " JOIN students s on s.sid = a.sid"
					+ " JOIN terms t on t.id = a.termID"
					+ " JOIN coach c on c.id = a.coachID"
					+ " JOIN level on level.id = a.levelID"
					+ " JOIN location l on l.id = a.locationID"
					+ " WHERE a.sid != 0 and c.name = '"+coach+ 
					"' AND t.name = '"+term
					+ "' ORDER BY a.sid, a.day, a.time";
				
			PreparedStatement pst=conn.prepareStatement(query);
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.setAutoCreateRowSorter(true);
			rs.close();
			pst.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
}
