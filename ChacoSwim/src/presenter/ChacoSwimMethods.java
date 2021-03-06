package presenter;

import java.awt.Component;
import java.io.Console;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.apache.commons.logging.Log;

import chacoswim.ChacoSwim;
import chacoswim.DataBaseManage;
import chacoswim.RetakeObject;
import model.ChacoSwimModel;
import net.proteanit.sql.DbUtils;

public class ChacoSwimMethods {
	private ChacoSwim cs;
	private ChacoSwimModel csm;
	private JTable targetTable;

	public ChacoSwimMethods(ChacoSwim cs, ChacoSwimModel csm) {
		this.cs = cs;
		this.csm = csm;
		csm.cspSetter(this);
	}

	public void resizeColumnWidth(JTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
		for (int column = 0; column < table.getColumnCount(); column++) {
			int width = 80; // Min width
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, column);
				Component comp = table.prepareRenderer(renderer, row, column);
				width = Math.max(comp.getPreferredSize().width + 1, width);
			}
			columnModel.getColumn(column).setPreferredWidth(width);
		}
	}

	public void deleteRow(String id, String table) {

		int ans = JOptionPane.showConfirmDialog(null, "Do you really want to delete selected record", "Deleting",
				JOptionPane.YES_NO_OPTION);
		if (ans == JOptionPane.YES_OPTION) {
			String query = "DELETE FROM " + table + " WHERE id = " + id;
			csm.executeWithoutRS(query);
			JOptionPane.showMessageDialog(null, "Deleted");
			cs.refreshTableTerm();

		}
	}

	public void refreshTable(String key) {
		String query = "";
		targetTable = cs.tableGetter(key);
		if (targetTable != null) {
			String term = cs.comboBoxGetter("comboBoxTerm").getSelectedItem().toString();
			String location = cs.comboBoxGetter("comboBoxLocation").getSelectedItem().toString();
			String day = cs.comboBoxGetter("comboBoxdays").getSelectedItem().toString();
			String line = cs.comboBoxGetter("comboBoxLine").getSelectedItem().toString();
			
			switch (key) {
			case "overtop":
				query = "select * from students where sid != 0";
				break;
			case "overbuttom":
			case "overbuttom2":
				
				if (day.equalsIgnoreCase("all"))
					day = "%";
				if (line.equalsIgnoreCase("all"))
					line = "%";
				if(key.equalsIgnoreCase("overbuttom"))
				query = "select a.id,s.SID,s.FirstName,s.LastName,level.name as Level,a.Time as Time,c.name as Coach,a.Line as Line, s.Cell,a.day from active_record a"
						+ " INNER JOIN students s ON s.sid=a.sid" + " INNER JOIN terms t ON t.id = a.termID"
						+ " INNER JOIN location l ON l.id = a.locationID" + " INNER JOIN coach c ON c.id = a.coachID"
						+ " INNER JOIN level ON level.id = a.levelID" + " where a.sid !=0 AND t.name = '" + term + "' AND l.name = '"
						+ location + "' AND a.day LIKE '" + day + "' AND a.line LIKE '" + line
						+ "' AND a.type = 'true' ORDER BY a.day,a.time, a.Line, a.levelID";
				else
					query = "select a.id,a.sid,level.name as Level,a.Time as Time,c.name as Coach,a.Line as Line, a.day from active_record a"
							+ " INNER JOIN terms t ON t.id = a.termID"
							+ " INNER JOIN location l ON l.id = a.locationID" + " INNER JOIN coach c ON c.id = a.coachID"
							+ " INNER JOIN level ON level.id = a.levelID" + " where a.sid = 0 AND t.name = '" + term + "' AND l.name = '"
							+ location + "' AND a.day LIKE '" + day + "' AND a.line LIKE '" + line
							+ "' AND a.type = 'true' ORDER BY a.day,a.time, a.Line, a.levelID";
				break;
			case "studentRetake":
				ArrayList<RetakeObject> list = new ArrayList<RetakeObject>();
				query = "SELECT sid,termID,levelID,count(levelID) from active_record WHERE sid != 0 GROUP by sid,levelID having count(*) >="+cs.getRetake();
				CachedRowSet tempcrs = csm.excuteWithRS(query);
				try {
						while(tempcrs.next()){
							list.add(new RetakeObject(tempcrs.getInt("sid"),tempcrs.getInt("termID"),tempcrs.getInt("levelID")));
						}
						for(int i = 0;i<list.size();i++){
							query = "SELECT sid,termID,levelID from active_record where sid = "+list.get(i).getSid()+" group by levelID";
							CachedRowSet filtercrs = csm.excuteWithRS(query);
							while(filtercrs.next()){
								int curlevel = filtercrs.getInt("levelID");
								if(curlevel<=9 && curlevel>list.get(i).getLevelID()){
									list.remove(i);
									i--;
								}
							}
						}
						StringBuilder sw = new StringBuilder();
						for(RetakeObject i:list){
							if(sw.length()==0) sw.append(" AND a.sid = "+i.getSid());
							else sw.append(" OR a.sid = "+i.getSid());
						}
						if(list.size()==0){
							JOptionPane.showMessageDialog(cs, "No one fits your retake number.");
							return;
						}
						query = "select a.id, a.sid, s.firstName, s.lastName, t.name as Term, l.name as Location, level.name as Level, a.payment_method, a.amount, a.date from active_record a"
								+" JOIN students s ON s.sid = a.sid"
								+" JOIN terms t ON t.id = a.termID"
								+" JOIN location l ON l.id = a.locationID"
								+" JOIN level ON level.id = a.levelID"
								+" WHERE a.sid != 0 "+sw.toString()
								+" ORDER BY a.sid,a.termID,a.levelID";
						
						
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
			case "jtTimeCount":
			case "jtTimeCount0":
				
//				if (day.equalsIgnoreCase("all"))
//					day = "%";
				
				if(key.equalsIgnoreCase("jtTimeCount")){
					if(day.equalsIgnoreCase("all")){
						query = "SELECT a.day,a.time, level.name as level,count(*)"
								+ " from active_record a"
								+ " INNER JOIN terms t ON t.id = a.termID"
								+ " INNER JOIN location l ON l.id = a.locationID"
								+ " INNER JOIN level ON level.id = a.levelID"
								+ " WHERE a.sid != 0 AND t.name = '" + term + "' AND l.name = '"+ location 
								+ "' group by level.name, a.day, a.time"
								+ " ORDER BY a.day,a.time,level.name";
					}
					else{
						query = "select a.day,a.time, level.name as level,count(*) from active_record a"
								+ " INNER JOIN terms t ON t.id = a.termID"
								+ " INNER JOIN location l ON l.id = a.locationID"
								+ " INNER JOIN level ON level.id = a.levelID"
								+ " where a.sid != 0 AND t.name = '" + term + "' AND l.name = '"
								+ location + "' AND a.day LIKE '" + day + "' group by level.name, a.day, a.time"
								+ " ORDER BY a.day,a.time,level.name";
					}
					
				}
					
				else{
					if(day.equalsIgnoreCase("all")){
						query = "SELECT a.day,a.time, level.name as level,count(*)"
								+ " from active_record a"
								+ " INNER JOIN terms t ON t.id = a.termID"
								+ " INNER JOIN location l ON l.id = a.locationID"
								+ " INNER JOIN level ON level.id = a.levelID"
								+ " WHERE a.sid = 0 AND t.name = '" + term + "' AND l.name = '"+ location 
								+ "' group by level.name, a.day, a.time"
								+ " ORDER BY a.day,a.time,level.name";
					}
					else{
						query = "select a.day,a.time, level.name as level,count(*) from active_record a"
								+ " INNER JOIN terms t ON t.id = a.termID"
								+ " INNER JOIN location l ON l.id = a.locationID"
								+ " INNER JOIN level ON level.id = a.levelID"
								+ " where a.sid = 0 AND t.name = '" + term + "' AND l.name = '"
								+ location + "' AND a.day LIKE '" + day + "' group by level.name, a.day, a.time"
								+ " ORDER BY a.day,a.time,level.name";
					}
				}
					
				break;
				
			case "jtLineCount":
			case "jtLineCount0":
				if (day.equalsIgnoreCase("all"))
					day = "%";
				if (line.equalsIgnoreCase("all"))
					line = "%";
				if(key.equalsIgnoreCase("jtLineCount"))
					query = "select a.line,count(a.line) as count from active_record a"
							+ " INNER JOIN terms t ON t.id = a.termID"
							+ " INNER JOIN location l ON l.id = a.locationID" 
							+ " where a.sid != 0 AND t.name = '" + term + "' AND l.name = '"
							+ location + "' AND a.day LIKE '" + day + "' group by a.line ORDER BY a.line";
				else
					query = "select a.line,count(a.line) as count from active_record a"
							+ " INNER JOIN terms t ON t.id = a.termID"
							+ " INNER JOIN location l ON l.id = a.locationID" 
							+ " where a.sid = 0 AND t.name = '" + term + "' AND l.name = '"
							+ location + "' AND a.day LIKE '" + day + "' group by a.line ORDER BY a.line";
				break;

			}
			CachedRowSet crs = csm.excuteWithRS(query);
			targetTable.setModel(DbUtils.resultSetToTableModel(crs));

			if (key.equalsIgnoreCase("overbuttom")||key.equalsIgnoreCase("overbuttom2")||key.equalsIgnoreCase("studentretake")) {
				// hide first column of 'ID'
				TableColumnModel tcm = targetTable.getColumnModel();
				tcm.removeColumn(tcm.getColumn(0));
			}
			resizeColumnWidth(targetTable);
			targetTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			targetTable.setAutoCreateRowSorter(true);
			if(key.equalsIgnoreCase("studentretake")||key.equals("jtLineCount")||key.equals("jtLineCount0")||key.equals("jtTimeCount")||key.equals("jtTimeCount0")){
				targetTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			}
			targetTable = null;
			
			// fillTable(rs);
		}
	}

	public String[] fillComboBox(String key, String which) {
		ArrayList<String> list = new ArrayList<String>();
		String query = "";
		try {
			if (key.equals("lines")) {
				query = "select lines from location where name = '"
						+ cs.comboBoxGetter(which).getSelectedItem().toString() + "'";
				CachedRowSet crs = csm.excuteWithRS(query);
				int i = 0;
				if (crs.next())
					i = crs.getInt("lines");
				list.add("All");
				for (int j = 1; j <= i; j++) {
					list.add(String.valueOf(j));
				}
			}

			if (key.equalsIgnoreCase("terms")) {
				query = "select name from terms ORDER BY id DESC";
				CachedRowSet crs = csm.excuteWithRS(query);
				while (crs.next()) {
					list.add(crs.getString("name").toString());
				}
			}
			if (key.equalsIgnoreCase("location")) {
				query = "select name from location ORDER BY id DESC";
				CachedRowSet crs = csm.excuteWithRS(query);
				while (crs.next()) {
					list.add(crs.getString("name").toString());
				}
			}
			if (key.equalsIgnoreCase("day")) {
				query = "select day from day_of_week ORDER BY id";
				CachedRowSet crs = csm.excuteWithRS(query);
				while (crs.next()) {
					list.add(crs.getString("day").toString());
				}
			}
			if (key.equalsIgnoreCase("coach")) {
				query = "select name from coach ORDER BY id";
				CachedRowSet crs = csm.excuteWithRS(query);
				while (crs.next()) {
					list.add(crs.getString("name").toString());
				}
			}
			if (key.equalsIgnoreCase("level")) {
				query = "select name from level ORDER BY id";
				CachedRowSet crs = csm.excuteWithRS(query);
				while (crs.next()) {
					list.add(crs.getString("name").toString());
				}
			}
			if (key.equalsIgnoreCase("time")) {
				query = "select name from time ORDER BY id";
				CachedRowSet crs = csm.excuteWithRS(query);
				while (crs.next()) {
					list.add(crs.getString("name").toString());
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		String[] str = new String[list.size() - 1];
		str = list.toArray(str);

		return str;
	}

	public void updateScheduleForm() {
		String c1 = cs.getSps1().gettfschLine2().getText();
		String l1 = cs.getSps1().gettfschlevel().getText();
		String c2 = cs.getSps2().gettfschLine2().getText();
		String l2 = cs.getSps2().gettfschlevel().getText();
		String c3 = cs.getSps3().gettfschLine2().getText();
		String l3 = cs.getSps3().gettfschlevel().getText();
		String c4 = cs.getSps4().gettfschLine2().getText();
		String l4 = cs.getSps4().gettfschlevel().getText();
		String c5 = cs.getSps5().gettfschLine2().getText();
		String l5 = cs.getSps5().gettfschlevel().getText();
		String c6 = cs.getSps6().gettfschLine2().getText();
		String l6 = cs.getSps6().gettfschlevel().getText();
		updateScheduleForm2(c1, l1, 1);
		updateScheduleForm2(c2, l2, 2);
		updateScheduleForm2(c3, l3, 3);
		updateScheduleForm2(c4, l4, 4);
		updateScheduleForm2(c5, l5, 5);
		updateScheduleForm2(c6, l6, 6);
		JOptionPane.showMessageDialog(cs, "Done");
		
		cs.getSps1().gettfschLine2().setText("");
		cs.getSps1().gettfschlevel().setText("");
		cs.getSps2().gettfschLine2().setText("");
		cs.getSps2().gettfschlevel().setText("");
		cs.getSps3().gettfschLine2().setText("");
		cs.getSps3().gettfschlevel().setText("");
		cs.getSps4().gettfschLine2().setText("");
		cs.getSps4().gettfschlevel().setText("");
		cs.getSps5().gettfschLine2().setText("");
		cs.getSps5().gettfschlevel().setText("");
		cs.getSps6().gettfschLine2().setText("");
		cs.getSps6().gettfschlevel().setText("");
		

	}
	

	public void updateScheduleForm2(String c, String l, int line) {

		DataBaseManage dbm = new DataBaseManage();
		String termID = dbm.gotId("terms", cs.getCbSchTerm().getSelectedItem().toString());
		String day = cs.getCbSchDay().getSelectedItem().toString();
		String locationID = dbm.gotId("location", cs.getCbSchLocation().getSelectedItem().toString());
		String time = cs.getCbSchStart().getSelectedItem().toString() + "-"
				+ cs.getCbSchEnd().getSelectedItem().toString();
		String coachID, levelID;
		if (!c.isEmpty() && !l.isEmpty()) {
			String[] coach = c.split(",");
			String[] level = l.split(",");
			if (coach.length == level.length) {
				for (int i = 0; i < coach.length; i++) {
					coachID = dbm.gotId("coach", coach[i]);
					levelID = dbm.gotId("level", level[i]);

					String query = "INSERT INTO active_record (sid,termID,locationID,day,coachID,levelID,line,time) VALUES (0,'"
							+ termID + "','" + locationID + "','" + day + "','" + coachID + "','" + levelID + "','" + line
							+ "','" + time + "')";
					csm.executeWithoutRS(query);
					
				}
				
			}
			else
				JOptionPane.showMessageDialog(cs, "In Line "+line+" Coach and Level are not pair up.");

		}
		else if (!c.isEmpty()&&l.isEmpty()){
			JOptionPane.showMessageDialog(cs, "Coach: "+c+"\nLevel: missing\nFail to add this one record,Please add it separatly");
		}
		else if (c.isEmpty()&&!l.isEmpty()){
			JOptionPane.showMessageDialog(cs, "Coach: missing\nLevel: "+l+"\nFail to add this one record,Please add it separatly");
		}
	}
}
