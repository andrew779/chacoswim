package chacoswim;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import presenter.ChacoSwimMethods;
import view.WaitingListView;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;

import model.ChacoSwimModel;
import model.CommonModel;
import model.WaitingListModel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.SystemColor;
import javax.swing.JToggleButton;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;

import net.miginfocom.swing.MigLayout;
import javax.swing.JCheckBox;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ChacoSwim extends JFrame {

	private static final long serialVersionUID = 1L;
	private ChacoSwimMethods csp;
	private JPanel contentPane;
	private JTable table;
	private Connection conn=null;
	private JComboBox<String> comboBoxtable;
	private JComboBox<String> comboBoxTerm;
	JComboBox<String> comboBoxEmailDay;
	JComboBox<String> comboBoxEmailTerm;
	JComboBox<String> comboBoxExDay;
	private JComboBox<Object> comboBoxdays;
	JComboBox<String> comboBoxExTerm;
	private JTextField tfSearch;
	private JTable tableTerm;
	private JTable tableExport;
	private JTextField tfTo;
	private JTextField tfSubject;
	private JTextArea taBody;
	private JRadioButton rdbtnAllCoaches,rdbtnStudents,rdbtnSingleRecipient,rdbtnMultipleRecipients;
	private File f=null;
	private ArrayList<String> attachment_PathList = new ArrayList<String>();
	private JList<String> listAttachment;
	
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private JButton btnAttachmentDelete;
	private DefaultListModel<String> listModel;
	private JLabel lblTotalreg;
	private JPanel StatisticsTab;
	private JList<String> listCoach;
	private JTable StaticTerm_Coach;
	private static ChacoSwim frame;
	private JTable jtCoaches;
	private JTextField tfCName;
	private JTextField tfCCell;
	private JTextField tfCEmail;
	private JButton btnRefreshAll;
	private JComboBox<String> comboBoxStaCoach;
	private JToggleButton tglbtnSchedule;
	private JComboBox<String> comboBoxLocation;
	private JComboBox<String> comboBoxExLoc;
	private JComboBox<String> comboBoxEmailLoc;
	private JComboBox<String> comboBoxStKey;
	private JTextField tfStKey;
	private JComboBox<String> comboBoxStTerm;
	private JComboBox<String> comboBoxStLoc;
	private JComboBox<String> comboBoxStDay;
	private JTable jtSt;
	private JTextField stSID;
	private JTextField stFN;
	private JTextField stLN;
	private JTextField stAmount;
	private JComboBox<String> stMethod;
	private JTextField stDate;
	private JButton stUpdate;
	private JButton stDelete;
	private JTextField rtTR;
	private JTextField rtTA;
	private JTextField rtUN;
	private JTextField rtRC;
	private JButton rtRList;
	private JComboBox<String> comboBoxLine;
	private JComboBox<String> cbSchTerm;
	private JComboBox<String> cbSchLocation;
	private JComboBox<String> cbSchDay;
	private SchedulePanels sps2;
	
	private SchedulePanels sps3;
	private SchedulePanels sps4;
	private SchedulePanels sps5;
	private SchedulePanels sps6;
	private SchedulePanels sps1;
	private JComboBox<String> cbSchStart;
	private JComboBox<String> cbSchEnd;
	private JComboBox<String> cbSchHistory;
	private JCheckBox chckCondition;
	private JPanel schLine6;
	private JPanel ScheduleTab;
	private int retake;
	private JLabel lblTotal;
	private JButton btnReload;
	private JPanel CoachTab;
	private JTable jtTimeCount;
	private JTable jtLineCount;
	private JPanel WaitingListTab;
	private WaitingListModel wlModel;
	private WaitingListView wlView;
	/**
	 * TODO Launch the application.
	 */
	public static void main(Connection connection) {
		EventQueue.invokeLater(new Runnable() {
			

			public void run() {
				try {
					
					frame = new ChacoSwim();
					ChacoSwimModel csm = new ChacoSwimModel();
					ChacoSwimMethods csp = new ChacoSwimMethods(frame,csm);
					frame.setCSP(csp);
					frame.initialValues();
					frame.setVisible(true);
					Image imglogo=new ImageIcon(this.getClass().getResource("/logo24.png")).getImage();
					frame.setIconImage(imglogo);
					frame.setTitle("Chaco Swim Club  (Powered by Wenzhong Zheng)");
					
					
					
					//frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void setCSP(ChacoSwimMethods csp){
		this.csp = csp;
	}
	
	//SchedulePanels getters
	public SchedulePanels getSps2() {
		return sps2;
	}

	public SchedulePanels getSps3() {
		return sps3;
	}

	public SchedulePanels getSps4() {
		return sps4;
	}

	public SchedulePanels getSps5() {
		return sps5;
	}

	public SchedulePanels getSps6() {
		return sps6;
	}

	public SchedulePanels getSps1() {
		return sps1;
	}
	
	public JComboBox<String> getCbSchStart() {
		return cbSchStart;
	}

	public JComboBox<String> getCbSchEnd() {
		return cbSchEnd;
	}

	public JComboBox<String> getCbSchTerm() {
		return cbSchTerm;
	}

	public JComboBox<String> getCbSchLocation() {
		return cbSchLocation;
	}

	public JComboBox<String> getCbSchDay() {
		return cbSchDay;
	}
	public int getRetake(){
		return retake;
	}

	/**
	 * TODO table getter
	 * @param key
	 * @return
	 */
	public JTable tableGetter(String key){
		JTable table=null;
		switch(key.toLowerCase()){
			case "overtop":
				table = this.table;
				break;
			case "overbuttom":
			case "overbuttom2":
				table = tableTerm;
				break;
			case "students":
				table = jtSt;
				break;
			case "coaches":
				table = jtCoaches;
				break;
			case "studentretake":
				table = jtSt;
				break;
			case "jttimecount":
			case "jttimecount0":
				table = jtTimeCount;
				break;
			case "jtlinecount":
			case "jtlinecount0":
				table = jtLineCount;
				break;
			
		}
		
		return table;
	}
	
	public void resizeColumnWidth(JTable table,int size) {
	    final TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 1; column < table.getColumnCount(); column++) {
	        int width = size; // Min width
	        for (int row = 0; row < table.getRowCount(); row++) {
	            TableCellRenderer renderer = table.getCellRenderer(row, column);
	            Component comp = table.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width +1 , width);
	        } 
	        columnModel.getColumn(column).setPreferredWidth(width);
	    } 
	} 
	
	
	public String[] getTables(String key){
		List<String> list = new ArrayList<String>();
		String query="";
		try {
			PreparedStatement pst=null;
			ResultSet rs=null;
			 
			switch(key){
			case "terms":
				query = "Select name from "+key+" order by id DESC";
				pst=conn.prepareStatement(query);
				rs=pst.executeQuery();
				while(rs.next()){
					list.add(rs.getString("name"));
				}
				String[] str= new String[list.size()];
				list.toArray(str);
				pst.close();
				rs.close();
				return str;
			case "location":
				query = "Select name from "+key+" order by id DESC";
				pst=conn.prepareStatement(query);
				rs=pst.executeQuery();
				while(rs.next()){
					list.add(rs.getString("name"));
				}
				String[] location= new String[list.size()];
				list.toArray(location);
				pst.close();
				rs.close();
				return location;
		
			}//switch
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	//refreshing two tables----------------------------------------
	//TODO refreshTable
	public void refreshTable(JTable table,String key){
		PreparedStatement pst=null;
		ResultSet rs=null;
		String query = "";
		
		try{
			if(key.equalsIgnoreCase("overall")){
				if(tglbtnSchedule.isSelected())
					query="select * from students where sid < 16";
					else query="select * from students where sid>15";
					pst=conn.prepareStatement(query);
					rs=pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
			}
			else if(key.equalsIgnoreCase("students")){
				String str = tfStKey.getText()+"%";
				String StTerm = comboBoxStTerm.getSelectedItem().toString();
				String Stloc = comboBoxStLoc.getSelectedItem().toString();
				String StDay = comboBoxStDay.getSelectedItem().toString();
				String StKey = comboBoxStKey.getSelectedItem().toString();
				if(StKey.equalsIgnoreCase("sid")||StKey.equalsIgnoreCase("amount"))StKey = "a."+StKey;
				else if(StKey.equalsIgnoreCase("firstname")||StKey.equalsIgnoreCase("lastname")||StKey.equalsIgnoreCase("cell"))StKey = "s."+StKey;
				if(StDay.equalsIgnoreCase("all")) StDay = "%";
				
				query = "select a.id, a.sid, s.firstName, s.lastName, t.name as Term, l.name as Location, level.name as Level, a.payment_method, a.amount, a.date from active_record a"
						+" JOIN students s ON s.sid = a.sid"
						+" JOIN terms t ON t.id = a.termID"
						+" JOIN location l ON l.id = a.locationID"
						+" JOIN level ON level.id = a.levelID"
						+" WHERE t.name = ?1 AND l.name = ?2 AND a.day LIKE '"+StDay+"' AND "+StKey+" LIKE ?4 AND a.sid != 0 COLLATE NOCASE ORDER BY a.sid, t.name, level.name"; 
				//StKey can't be surrounded by ''
				pst = conn.prepareStatement(query);
				pst.setString(1, StTerm);
				pst.setString(2, Stloc);
				//pst.setString(3, StDay);
				pst.setString(4, str);
				rs=pst.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(rs));
				
				//hide first column of 'ID'
				TableColumnModel tcm1 = table.getColumnModel();
				tcm1.removeColumn(tcm1.getColumn(0));
				
				//fill the top right textfields
				int total = table.getRowCount();
				rtTR.setText(String.valueOf(total));
				double sum = 0.0;
				String value = "";
				int unpaidCount = 0;
				for(int i = 0;i<total;i++){
					value = table.getValueAt(i, 7).toString();
					if(!value.isEmpty())
					sum += Double.valueOf(value);
					if(table.getValueAt(i, 6).toString().equalsIgnoreCase("unpaid"))
						unpaidCount++;
				}
				rtTA.setText(String.valueOf(sum));
				rtUN.setText(String.valueOf(unpaidCount));
				
			}
			
			
			pst.close();
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void updateData(String key){
		PreparedStatement pst=null;
		String query = "";
		try {
			switch(key){
			case "financial":
				query = "UPDATE active_record SET amount = ?1, payment_method = ?2, date = ?3 WHERE id = ?4";
				pst = conn.prepareStatement(query);
				pst.setString(1, stAmount.getText());
				pst.setString(2,stMethod.getSelectedItem().toString());
				pst.setString(3, stDate.getText());
				pst.setString(4, jtSt.getModel().getValueAt(jtSt.getSelectedRow(), 0).toString());
				
				pst.execute();
				pst.close();
				break;
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void refreshExTableTerm(){
		ResultSet rs=null;
		try{
			String term = comboBoxExTerm.getSelectedItem().toString();
			String day = comboBoxExDay.getSelectedItem().toString();
			String location = comboBoxExLoc.getSelectedItem().toString();
			String query="";
			DataBaseManage dbm = new DataBaseManage();
			String termID = dbm.gotId("terms", term);
			String locationID = dbm.gotId("location", location);
			if(!chckCondition.isSelected()){
					
					query = "select a.sid, s.firstName, s.lastName, level.name as CurLevel, a.day, a.time, a.line, c.name as Coach from"
							+" active_record a JOIN students s ON s.sid=a.sid"
							+" JOIN level ON a.levelID = level.id"
							+" JOIN coach c ON a.coachID = c.id"
							+" WHERE a.sid != 0 and a.day = '"+day+"' AND a.termID = '"+termID+"' AND a.locationID = '"+locationID+"'"
							+" ORDER BY a.day, a.time, a.line, level.name";
			}
			else{
					
					query = "select a.sid, s.firstName, s.lastName, level.name as CurLevel, a.day, a.time, a.line, c.name as Coach from"
							+" active_record a JOIN students s ON s.sid=a.sid"
							+" JOIN level ON a.levelID = level.id"
							+" JOIN coach c ON a.coachID = c.id"
							+" WHERE a.sid = 0 and a.day = '"+day+"' AND a.termID = '"+termID+"' AND a.locationID = '"+locationID+"'"
							+" ORDER BY a.day, a.time, a.line, level.name";
			}
			PreparedStatement pst=conn.prepareStatement(query);
			rs = pst.executeQuery();
			tableExport.setModel(DbUtils.resultSetToTableModel(rs));
			
			//hide first column of 'ID'
			TableColumnModel tcm = tableExport.getColumnModel();
			tcm.removeColumn(tcm.getColumn(0));
			pst.close();
			rs.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public JComboBox<?> comboBoxGetter(String key){
		switch(key){
		case "comboBoxTerm":
			return comboBoxTerm;
		case "comboBoxLocation":
			return comboBoxLocation;
		case "comboBoxdays":
			return comboBoxdays;
		case "comboBoxLine":
			return comboBoxLine;
		case "cbSchLocation":
			return cbSchLocation;
		}
		return null;
	}
	
	public void refreshTableTerm(){
		ResultSet rs=null;
		try{
			DataBaseManage dbm = new DataBaseManage();
			String termId = dbm.gotId("terms", comboBoxTerm.getSelectedItem().toString());
			String locationId = dbm.gotId("location",comboBoxLocation.getSelectedItem().toString());
			String day = comboBoxdays.getSelectedItem().toString();
			String query="";
			if(day.equals("All")){
				query = "select a.id,s.SID,s.FirstName,s.LastName,level.name as Level,a.Time as Time,c.name as Coach,a.Line as Line,A.Day as Day from active_record a"
						+ " INNER JOIN students s ON s.sid=a.sid"
						+ " INNER JOIN terms t ON t.id = a.termID"
						+ " INNER JOIN location l ON l.id = a.locationID"
						+ " INNER JOIN coach c ON c.id = a.coachID"
						+ " INNER JOIN level ON level.id = a.levelID"
						+ " where termID = ? AND locationID = ?";
				
			}
			else{
				query = "select a.id,s.SID,s.FirstName,s.LastName,level.name as Level,a.Time as Time,c.name as Coach,a.Line as Line, s.Cell from active_record a"
						+ " INNER JOIN students s ON s.sid=a.sid"
						+ " INNER JOIN terms t ON t.id = a.termID"
						+ " INNER JOIN location l ON l.id = a.locationID"
						+ " INNER JOIN coach c ON c.id = a.coachID"
						+ " INNER JOIN level ON level.id = a.levelID"
						+ " where termID = ? AND locationID = ? AND day = '"+day+"'";
				}
			PreparedStatement pst=conn.prepareStatement(query);
			pst.setString(1, termId);
			pst.setString(2, locationId);
			rs = pst.executeQuery();
			tableTerm.setModel(DbUtils.resultSetToTableModel(rs));
			//comboBoxEmailTerm.setModel(DbUtils.resultSetToTableModel(rs));
			
			
			//hide first column of 'ID'
			TableColumnModel tcm = tableTerm.getColumnModel();
			tcm.removeColumn(tcm.getColumn(0));
			
			pst.close();
			rs.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	//--------------------------------------------------------------
	public void newFont(JLabel lbl){
		lbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbl.setForeground(Color.RED);
	}
	public void oldFont(JLabel l){
		l.setForeground(Color.black);
		l.setFont(new Font("Tahoma", Font.PLAIN, 11));
	}
	
//	public void seatsLeft(){
//		String term = comboBoxTerm.getSelectedItem().toString();
//		String day = comboBoxdays.getSelectedItem().toString();
//		int l1=0,l2=0,l3=0,l4=0,l5=0,l6=0,l7=0,l8=0,splash=0,crash_front=0,crash_breast=0,team=0,adult=0;
//		StringBuilder lvl01 = new StringBuilder("--------"+day+"------\n");
//		StringBuilder lvl0 = new StringBuilder("Level 0:\n");
//		StringBuilder lvl1 = new StringBuilder("Level 1:\n");
//		StringBuilder lvl2 = new StringBuilder("Level 2:\n");
//		StringBuilder lvl3 = new StringBuilder("Level 3:\n");
//		StringBuilder lvl4 = new StringBuilder("Level 4:\n");
//		StringBuilder lvl5 = new StringBuilder("Level 5:\n");
//		StringBuilder lvl6 = new StringBuilder("Level 6:\n");
//		StringBuilder lvl7 = new StringBuilder("Level 7:\n");
//		StringBuilder lvl8 = new StringBuilder("Level 8:\n");
//		StringBuilder lvl9 = new StringBuilder("Level Splash:\n");
//		StringBuilder lvl10 = new StringBuilder("Level Crash_Front:\n");
//		StringBuilder lvl11 = new StringBuilder("Level Crash_Breast:\n");
//		StringBuilder lvl12 = new StringBuilder("Level Adult:\n");
//		StringBuilder lvl13 = new StringBuilder("Level Team:\n");
//		try {
//			String query = "select t.sid, s.FirstName, s.LastName, s.CurLevel, t.day, t.time, t.line, t.coach from '"+term+"' t JOIN students s on s.sid = t.sid WHERE t.day = '"+day+ 
//					"' ORDER BY t.day, t.time";
//			PreparedStatement pst=conn.prepareStatement(query);
//			ResultSet rs = pst.executeQuery();
//			while(rs.next()){
//				switch(rs.getString("CurLevel")){
//				case "1_Swimmer 1": 
//					l1++;
//					lvl1.append("      "+rs.getString("sid")+"  "+rs.getString("FirstName")+"  "+rs.getString("LastName")+"\n");
//					break;
//				case "2_Swimmer 2": 
//					l2++;
//					lvl2.append("      "+rs.getString("sid")+"  "+rs.getString("FirstName")+" "+rs.getString("LastName")+"\n");
//					break;
//				case "3_Swimmer 3": 
//					l3++;
//					lvl3.append("      "+rs.getString("sid")+"  "+rs.getString("FirstName")+" "+rs.getString("LastName")+"\n");
//					break;
//				case "4_Swimmer 4": 
//					l4++;
//					lvl4.append("      "+rs.getString("sid")+"  "+rs.getString("FirstName")+" "+rs.getString("LastName")+"\n");
//					break;
//				case "5_Swimmer 5": 
//					l5++;
//					lvl5.append("      "+rs.getString("sid")+"  "+rs.getString("FirstName")+" "+rs.getString("LastName")+"\n");
//					break;
//				case "6_Swimmer 6": 
//					l6++;
//					lvl6.append("      "+rs.getString("sid")+"  "+rs.getString("FirstName")+" "+rs.getString("LastName")+"\n");
//					break;
//				case "7_Swimmer 7": 
//					l7++;
//					lvl7.append("      "+rs.getString("sid")+"  "+rs.getString("FirstName")+" "+rs.getString("LastName")+"\n");
//					break;
//				case "8_Swimmer 8": 
//					l8++;
//					lvl8.append("      "+rs.getString("sid")+"  "+rs.getString("FirstName")+" "+rs.getString("LastName")+"\n");
//					break;
//				case "0_Swimmer 0":
//					lvl0.append("      "+rs.getString("sid")+"  "+rs.getString("FirstName")+" "+rs.getString("LastName")+"\n");
//					break;
//				case "9_Splash":
//					splash++;
//					lvl9.append("      "+rs.getString("sid")+"  "+rs.getString("FirstName")+" "+rs.getString("LastName")+"\n");
//					break;
//				case "10_Crash-Front":
//					crash_front++;
//					lvl10.append("     "+rs.getString("sid")+"  "+rs.getString("FirstName")+" "+rs.getString("LastName")+"\n");
//					break;
//				case "11_Crash-Breast":
//					crash_breast++;
//					lvl11.append("     "+rs.getString("sid")+"  "+rs.getString("FirstName")+" "+rs.getString("LastName")+"\n");
//					break;
//				case "12_Adult":
//					adult++;
//					lvl12.append("     "+rs.getString("sid")+"  "+rs.getString("FirstName")+" "+rs.getString("LastName")+"\n");
//					break;
//				case "13_Team":
//					team++;
//					lvl13.append("     "+rs.getString("sid")+"  "+rs.getString("FirstName")+" "+rs.getString("LastName")+"\n");
//					break;
//				default:
//					break;				
//				}//switch
//			}//while
//			lvl01.append(lvl0);
//			lvl01.append(lvl1);
//			lvl01.append(lvl2);
//			lvl01.append(lvl3);
//			lvl01.append(lvl4);
//			lvl01.append(lvl5);
//			lvl01.append(lvl6);
//			lvl01.append(lvl7);
//			lvl01.append(lvl8);lvl01.append(lvl9);lvl01.append(lvl10);lvl01.append(lvl11);lvl01.append(lvl12);lvl01.append(lvl13);
//			taInfo.setText(lvl01.toString());
//			
//			String warn="Exceed Class, and you won't be able to export an EXCEL File";
//			if(splash>=4){if(splash>6){JOptionPane.showMessageDialog(null, warn);}lblSplash.setText("Splash: "+splash);	newFont(lblSplash);}
//			else{lblSplash.setText("Splash: "+splash); oldFont(lblSplash);}
//			
//			if(crash_front>=4){if(crash_front>6){JOptionPane.showMessageDialog(null, warn);}lblCrashfront.setText("Crashfront: "+crash_front);newFont(lblCrashfront);}
//			else{lblCrashfront.setText("Crashfront: "+crash_front); oldFont(lblCrashfront);}
//			
//			if(crash_breast>=4){if(crash_breast>6){JOptionPane.showMessageDialog(null, warn);}lblCrashbreast.setText("Crashbreast: "+crash_breast);newFont(lblCrashbreast);}
//			else{lblCrashbreast.setText("Crashbreast: "+crash_breast); oldFont(lblCrashbreast);}
//			
//			if(adult>=4){if(adult>6){JOptionPane.showMessageDialog(null, warn);}lblAdult.setText("Adult: "+adult);newFont(lblAdult);}
//			else{lblAdult.setText("Adult: "+adult); oldFont(lblAdult);}
//			
//			if(team>=4){if(team>6){JOptionPane.showMessageDialog(null, warn);}lblTeam.setText("Team: "+team);newFont(lblTeam);}
//			else{lblTeam.setText("Team: "+team); oldFont(lblTeam);}
//			
//			if(l1>=4){if(l1>6){JOptionPane.showMessageDialog(null, warn);}lblL1.setText("L1: "+l1);	newFont(lblL1);}
//			else{lblL1.setText("L1: "+l1); oldFont(lblL1);}
//			
//			if(l2>=4){if(l2>6){JOptionPane.showMessageDialog(null, warn);}lblL2.setText("L2: "+l2);	newFont(lblL2);}
//			else{lblL2.setText("L2: "+l2); oldFont(lblL2);}
//			
//			if(l3>=4){if(l3>6){JOptionPane.showMessageDialog(null, warn);}lblL3.setText("L3: "+l3);	newFont(lblL3);}
//			else{lblL3.setText("L3: "+l3); oldFont(lblL3);}
//
//			if(l4>=4){if(l4>6){JOptionPane.showMessageDialog(null, warn);}lblL4.setText("L4: "+l4);	newFont(lblL4);}
//			else{lblL4.setText("L4: "+l4); oldFont(lblL4);}
//			
//			if(l5>=4){if(l5>6){JOptionPane.showMessageDialog(null, warn);}lblL5.setText("L5: "+l5);	newFont(lblL5);}
//			else{lblL5.setText("L5: "+l5); oldFont(lblL5);}
//			
//			if(l6>=4){if(l6>6){JOptionPane.showMessageDialog(null, warn);}lblL6.setText("L6: "+l6);	newFont(lblL6);}
//			else{lblL6.setText("L6: "+l6); oldFont(lblL6);}
//			
//			if(l7>=4){if(l7>6){JOptionPane.showMessageDialog(null, warn);}lblL7.setText("L7: "+l7);	newFont(lblL7);}
//			else{lblL7.setText("L7: "+l7); oldFont(lblL7);}
//			
//			if(l8>=4){if(l8>6){JOptionPane.showMessageDialog(null, warn);}lblL8.setText("L8: "+l8);	newFont(lblL8);}
//			else{lblL8.setText("L8: "+l8); oldFont(lblL8);}
//			
//			pst.close();
//			rs.close();
//			
//		} catch (SQLException e) {
//			JOptionPane.showMessageDialog(null, "Line 418: "+e);
//		}
	
//	}
	public void refreshSearch(){
		try{
			String key = comboBoxtable.getSelectedItem().toString();
			if(!tfSearch.getText().isEmpty()){
			String query="select * from students where "+key+" LIKE '"+tfSearch.getText()+"%' COLLATE NOCASE ORDER BY "+key+",sid";
			PreparedStatement pst=conn.prepareStatement(query);
			//pst.setString(1, tfSearch.getText());
			ResultSet rs=pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			pst.close();
			rs.close();
			}
			else refreshTable(table,"overall");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String updateSID(){
		
			int row = table.getSelectedRow();
			if(row!=-1){
			String id = table.getModel().getValueAt(row, 0).toString();
			return id;
			}
			return "";
			
			
	}
	
	public void deleteStudent(){
		try {
			/*
			int row = table.getSelectedRow();
			String id = table.getModel().getValueAt(row, 0).toString();
			if (id!=null)SID_=id;
			*/
			String id = updateSID();
			String query = "delete from students where sid = ?";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, id);
			pst.execute();
			JOptionPane.showMessageDialog(null, "Deleted");
			pst.close();
		}catch(ArrayIndexOutOfBoundsException ex){
			JOptionPane.showMessageDialog(null, "You Didn't Select Any of the Record");
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Line471: "+e);
		}
		
	}
	
	public void fillListStudents(){
		try{
			String query="select sid,firstname,lastname from students";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			
			DefaultListModel<String> DLM=new DefaultListModel<String>();
			
			while(rs.next()){
				String str= rs.getString("sid")+" "+rs.getString("firstname")+" "+rs.getString("lastname");
				DLM.addElement(str);
			}
			//liststudents.setModel(DLM);
			
			pst.close();
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public String[] getRecipient(){
		List<String> address = new ArrayList<String>();
		String term = "";
		String location = "";
		String day = "";
		String query="";
		PreparedStatement pst;
		ResultSet rs;
		try{
			if(rdbtnSingleRecipient.isSelected()){
				if(tfTo.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Please enter a Email address");
					return null;
				}
				address.add(tfTo.getText().toString());
				
			}
			else if(rdbtnMultipleRecipients.isSelected()&&rdbtnAllCoaches.isSelected()){
				query="select email from coach";
				pst=conn.prepareStatement(query);
				rs=pst.executeQuery();
				while(rs.next())address.add(rs.getString("email"));
				pst.close();
				rs.close();
				
			}
			else{
				term = comboBoxEmailTerm.getSelectedItem().toString();
				day = comboBoxEmailDay.getSelectedItem().toString();
				location = comboBoxEmailLoc.getSelectedItem().toString();
				DataBaseManage dbm = new DataBaseManage();
				String termID = dbm.gotId("terms", term);
				String locationID = dbm.gotId("location", location);
				if(day.equals("All"))query="select s.email from 'active_record' a JOIN students s ON s.sid=a.sid WHERE a.termID = "+termID+" AND a.locationID = "+locationID+
						" and s.email LIKE '%@%' GROUP BY a.sid";
				else query="select s.email from 'active_record' a JOIN students s ON s.sid=a.sid WHERE a.day='"+day+"' AND a.termID = "+termID+" AND a.locationID = "+locationID+
						" and s.email LIKE '%@%' GROUP BY a.sid";
				pst=conn.prepareStatement(query);
				rs=pst.executeQuery();
				while(rs.next()){
					address.add(rs.getString("email"));
				}
				pst.close();
				rs.close();
			}
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Line536: "+e);
		}
		String[] ans = new String[address.size()];
		address.toArray(ans);
		return ans;
	}
	
	public void refreshListAttachment(){
		listModel = new DefaultListModel<String>();
		for(String i:attachment_PathList)
		listModel.addElement(i);
		listAttachment.setModel(listModel);
	}
	
	
	/**
	 * Create the frame.
	 */
	
	public ChacoSwim() {
		wlModel = new WaitingListModel();
		wlView = new WaitingListView();
		createView();
		
	}
	
	public void refreshOverTables(){
		if(tglbtnSchedule.isSelected()){
			csp.refreshTable("overbuttom2");
			csp.refreshTable("jtTimeCount0");
//			csp.refreshTable("jtLineCount0");
			
		}
		else {
			csp.refreshTable("overbuttom");
			csp.refreshTable("jtTimeCount");
//			csp.refreshTable("jtLineCount");
		}
	}
	
	//TODO waiting list methods
	public void refreshWaitingListTable(){
		wlModel.updateList();
		wlView.listTable.setModel(DbUtils.resultSetToTableModel(wlModel.getList()));
		TableColumnModel tcm = wlView.listTable.getColumnModel();
		tcm.removeColumn(tcm.getColumn(0));
		resizeColumnWidth(wlView.listTable,100);
	}
	public void deleteWaitingListRecord(int row){
		String id = wlView.listTable.getModel().getValueAt(row, 0).toString();
		String query = "DELETE FROM waiting_list where id = "+id;
		new ChacoSwimModel().executeWithoutRS(query);
	}
	
	
	public void initialValues(){
		
		
		cbSchTerm.setModel(new DefaultComboBoxModel<String>(csp.fillComboBox("terms", "cbSchTerm")));
		cbSchLocation.setModel(new DefaultComboBoxModel<String>(csp.fillComboBox("location", "")));
		cbSchDay.setModel(new DefaultComboBoxModel<String>(csp.fillComboBox("day", "")));
		//schedule tab
		int i = csp.fillComboBox("lines","cbSchLocation").length-1;
		if(i<6) ScheduleTab.remove(schLine6);
		else if(schLine6.getParent()==null)ScheduleTab.add(schLine6, "sg panels");
		
		//terms
		comboBoxTerm.setModel(new DefaultComboBoxModel<String>(csp.fillComboBox("terms", "")));
		comboBoxStTerm.setModel(new DefaultComboBoxModel<String>(csp.fillComboBox("terms", "")));
		comboBoxEmailTerm.setModel(new DefaultComboBoxModel<String>(csp.fillComboBox("terms", "")));
		comboBoxExTerm.setModel(new DefaultComboBoxModel<String>(csp.fillComboBox("terms", "")));
		//location
		comboBoxLocation.setModel(new DefaultComboBoxModel<String>(csp.fillComboBox("location", "")));
		comboBoxStLoc.setModel(new DefaultComboBoxModel<String>(csp.fillComboBox("location", "")));
		comboBoxEmailLoc.setModel(new DefaultComboBoxModel<String>(csp.fillComboBox("location", "")));
		comboBoxExLoc.setModel(new DefaultComboBoxModel<String>(csp.fillComboBox("location", "")));
		
		//lines
		comboBoxLine.setModel(new DefaultComboBoxModel<String>(csp.fillComboBox("lines","comboBoxLocation")));
		
		sps1.getComboBox().setModel(new DefaultComboBoxModel<String>(csp.fillComboBox("coach", "")));
		sps2.getComboBox().setModel(new DefaultComboBoxModel<String>(csp.fillComboBox("coach", "")));
		sps3.getComboBox().setModel(new DefaultComboBoxModel<String>(csp.fillComboBox("coach", "")));
		sps4.getComboBox().setModel(new DefaultComboBoxModel<String>(csp.fillComboBox("coach", "")));
		sps5.getComboBox().setModel(new DefaultComboBoxModel<String>(csp.fillComboBox("coach", "")));
		sps6.getComboBox().setModel(new DefaultComboBoxModel<String>(csp.fillComboBox("coach", "")));
		
		sps1.getComboBoxLevel().setModel(new DefaultComboBoxModel<String>(csp.fillComboBox("level", "")));
		sps2.getComboBoxLevel().setModel(new DefaultComboBoxModel<String>(csp.fillComboBox("level", "")));
		sps3.getComboBoxLevel().setModel(new DefaultComboBoxModel<String>(csp.fillComboBox("level", "")));
		sps4.getComboBoxLevel().setModel(new DefaultComboBoxModel<String>(csp.fillComboBox("level", "")));
		sps5.getComboBoxLevel().setModel(new DefaultComboBoxModel<String>(csp.fillComboBox("level", "")));
		sps6.getComboBoxLevel().setModel(new DefaultComboBoxModel<String>(csp.fillComboBox("level", "")));
		
		cbSchStart.setModel(new DefaultComboBoxModel<String>(csp.fillComboBox("time", "")));
		cbSchEnd.setModel(new DefaultComboBoxModel<String>(csp.fillComboBox("time", "")));
	}
	
	/**
	 * TODO initiate view
	 */
	private void createView(){
		setResizable(true);
		conn=sqliteConnection.dbConnector();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(0, -10, 1300, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
				if(sourceTabbedPane.getSelectedComponent()==StatisticsTab){
					lblTotalreg.setText(new StatisticMethod().getTotal());
				}
			}
		});
		contentPane.add(tabbedPane);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Overall Management", null, panel_2, null);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel_2.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 57, 1182, 207);
		panel.add(scrollPane);
		
		//table event listener
		table = new JTable();
		table.setAutoCreateRowSorter(true);
		
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//updateSID();
			}
		});
		scrollPane.setViewportView(table);
		
		comboBoxtable = new JComboBox<String>();
		
		comboBoxtable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				refreshSearch();
			}
		});
		
		
		
		comboBoxtable.setModel(new DefaultComboBoxModel<String>(new String[] {"SID", "FirstName", "LastName","Cell", "CurLevel"}));
		comboBoxtable.setBounds(882, 26, 107, 20);
		panel.add(comboBoxtable);
		
		JButton btnAddStudent = new JButton("New");
		btnAddStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//StudentModification sm = new StudentModification();
				StudentModification.main("",btnRefreshAll);
				
			}
		});
		
		//Image pencil=new ImageIcon(this.getClass().getResource("/24pencil.png")).getImage();
		btnAddStudent.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/24contacts.png")).getImage()));
		btnAddStudent.setBounds(22, 23, 117, 25);
		panel.add(btnAddStudent);
		
		comboBoxdays = new JComboBox<Object>();
		comboBoxdays.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				if(tglbtnSchedule.isSelected())csp.refreshTable("overbuttom2");
//				else csp.refreshTable("overbuttom");
				refreshOverTables();
				lblTotal.setText("Total: "+String.valueOf(tableTerm.getRowCount()));
			}
		});
		comboBoxdays.setModel(new DefaultComboBoxModel<Object>(new String[] {"All", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}));
		comboBoxdays.setBounds(335, 314, 119, 20);
		panel.add(comboBoxdays);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String id = updateSID();
				if(!id.isEmpty()){
					CourseModification.main(new String[]{"add",id,comboBoxTerm.getSelectedItem().toString(),comboBoxLocation.getSelectedItem().toString()},btnRefreshAll);
					lblTotal.setText("Total: "+String.valueOf(tableTerm.getRowCount()));
				}
				else
					JOptionPane.showMessageDialog(null, "Please select a record first");
				}
		});
		
		btnAdd.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/24pencil.png")).getImage()));
		btnAdd.setBounds(83, 583, 119, 30);
		panel.add(btnAdd);
		
		JButton btnDeleteStudent = new JButton("Delete");
		btnDeleteStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int row = table.getSelectedRow();
				if(row!=-1){
					int a = JOptionPane.showConfirmDialog(null, "Do You Really Want to Delete the Selected Record?","Delete",JOptionPane.YES_NO_OPTION);
					if (a==JOptionPane.YES_OPTION){
						deleteStudent();
						csp.refreshTable("overtop");
						
					}
				}
				else
					JOptionPane.showMessageDialog(null, "Please select a valid row first");
			}
		});
		btnDeleteStudent.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/24delete.png")).getImage()));
		btnDeleteStudent.setBounds(149, 23, 129, 25);
		panel.add(btnDeleteStudent);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = tableTerm.getSelectedRow();
				if(row!=-1){
					String id = tableTerm.getModel().getValueAt(row, 0).toString();
					if(tglbtnSchedule.isSelected()){
						CourseModification.main(new String[]{"update",id,comboBoxTerm.getSelectedItem().toString(),comboBoxLocation.getSelectedItem().toString()},btnRefreshAll);
					}
					else{
						
						CourseModification.main(new String[]{"update",id,comboBoxTerm.getSelectedItem().toString(),comboBoxLocation.getSelectedItem().toString()},btnRefreshAll);						
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "Please select a record from course table first");
				}
			}
		});
		
		btnEdit.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/24magicwand.png")).getImage()));
		btnEdit.setBounds(248, 583, 119, 30);
		panel.add(btnEdit);
		
		JButton btnEditStudent = new JButton("Edit");
		btnEditStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String id = updateSID();
				if(!id.isEmpty()){
					StudentModification.main(id,btnRefreshAll);
				}
				else
					JOptionPane.showMessageDialog(null, "Please select a record first");
			}
		});
		
		btnEditStudent.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/24compose.png")).getImage()));
		btnEditStudent.setBounds(296, 23, 129, 25);
		panel.add(btnEditStudent);
		
		tfSearch = new JTextField();
		tfSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				refreshSearch();
				
			}
		});
		tfSearch.setBounds(1010, 23, 192, 23);
		panel.add(tfSearch);
		tfSearch.setColumns(10);
		
		JLabel lblSearchBy = new JLabel("Search By:");
		lblSearchBy.setBounds(882, 11, 107, 14);
		panel.add(lblSearchBy);
		
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		resizeColumnWidth(table,100);
		
		
		comboBoxTerm = new JComboBox<String>();
		comboBoxTerm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				if(tglbtnSchedule.isSelected())csp.refreshTable("overbuttom2");
//				else csp.refreshTable("overbuttom");
				refreshOverTables();
				
				lblTotal.setText("Total: "+String.valueOf(tableTerm.getRowCount()));
			}
		});
//		comboBoxTerm.setModel(new DefaultComboBoxModel<Object>(getTables("terms")));
		comboBoxTerm.setBounds(20, 314, 119, 20);
		
		panel.add(comboBoxTerm);
		
		JLabel lblSelectTerm = new JLabel("Term:");
		lblSelectTerm.setBounds(20, 290, 117, 20);
		panel.add(lblSelectTerm);
		
		JLabel lblCourseInfo = new JLabel("Course Info: ");
		lblCourseInfo.setBounds(20, 356, 117, 20);
		panel.add(lblCourseInfo);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(20, 380, 769, 192);
		panel.add(scrollPane_1);
		
		tableTerm = new JTable();
		tableTerm.setAutoCreateRowSorter(true);
		scrollPane_1.setViewportView(tableTerm);
		//tableTerm.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//resizeColumnWidth(tableTerm);
		
		JLabel lblByDay = new JLabel("Day:");
		lblByDay.setBounds(335, 293, 46, 14);
		panel.add(lblByDay);
		
		
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/24delete.png")).getImage()));
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//String term = comboBoxTerm.getSelectedItem().toString();
				//PreparedStatement pst=null;
				//String query = "DELETE FROM 'active_record' WHERE ID = ?1";
				int row = tableTerm.getSelectedRow();
				if(row!=-1){
					String id = tableTerm.getModel().getValueAt(row, 0).toString();
					csp.deleteRow(id, "active_record");
					if(tglbtnSchedule.isSelected())csp.refreshTable("overbuttom2");
					else csp.refreshTable("overbuttom");
					lblTotal.setText("Total: "+String.valueOf(tableTerm.getRowCount()));
				}
				else
					JOptionPane.showMessageDialog(null, "Please select a valid row first");
				
				//JOptionPane.showMessageDialog(null, id);
				/*
				int ans = JOptionPane.showConfirmDialog(null, "Do you really want to delete selected record","Deleting",JOptionPane.YES_NO_OPTION);
				if(ans==JOptionPane.YES_OPTION){
					try {

						pst = conn.prepareStatement(query);
						pst.setString(1, id);
						pst.execute();
						pst.close();
						JOptionPane.showMessageDialog(null, "Deleted");
						btnRefreshAll.doClick();
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null,"Line912: "+ e);
					}
					
				
				}*/
				
				
			}
		});
		btnDelete.setBounds(422, 583, 119, 30);
		panel.add(btnDelete);
		/**
		 * TODO refreshAll btn
		 */
		btnRefreshAll = new JButton("Refresh All");
		btnRefreshAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				refreshTable(table,"overall");
//				refreshTableTerm();
				csp.refreshTable("overtop");
				
				refreshOverTables();
				
				lblTotal.setText("Total: "+String.valueOf(tableTerm.getRowCount()));
				//seatsLeft();
			}
		});
		
		Image img=new ImageIcon(this.getClass().getResource("/refresh-all.png")).getImage();
		btnRefreshAll.setIcon(new ImageIcon(img));
		btnRefreshAll.setBounds(619, 276, 145, 37);
		panel.add(btnRefreshAll);
		
		tglbtnSchedule = new JToggleButton("   Schedule");
		tglbtnSchedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tglbtnSchedule.isSelected()){
					tglbtnSchedule.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/32megaphone.png")).getImage()));
					tglbtnSchedule.setText("Class");
				}
				else {
					tglbtnSchedule.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/32megaphone2.png")).getImage()));
					tglbtnSchedule.setText("Students");
				}
			}
		});
		tglbtnSchedule.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/32megaphone2.png")).getImage()));
		tglbtnSchedule.setBounds(619, 322, 145, 37);
		panel.add(tglbtnSchedule);
		
		JButton btnLevelup = new JButton("Level Up");
		btnLevelup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				if(row!=-1){
					String name = table.getValueAt(row, 1).toString()+" "+table.getValueAt(row, 2).toString();
					String cur = table.getValueAt(row, 4).toString();
					int id = (int) table.getValueAt(row, 0);
					int lvl=0;
					if((lvl=Integer.parseInt(cur.substring(0, 1)))<8){
						String now = String.valueOf(lvl+1)+"_Swimmer "+String.valueOf(lvl+1);
						int i = JOptionPane.showConfirmDialog(null, "Level up: "+name+"\nFrom:    "+cur+"\nTo:         "+now+" ", "Level Up ?",JOptionPane.YES_NO_OPTION);
						if(i==JOptionPane.YES_OPTION){
							try{
							String query="UPDATE 'main'.'students' SET 'PreLevel' = ?, 'CurLevel' = ? WHERE  SID = "+id;
							PreparedStatement pst=conn.prepareStatement(query);
							pst.setString(1, cur);
							pst.setString(2, now);
							pst.execute();
							pst.close();
							JOptionPane.showMessageDialog(null, "Done!");
							btnRefreshAll.doClick();
							}catch(Exception e){
								e.printStackTrace();
							}
						}
					}
					else{
						JOptionPane.showMessageDialog(null, name+" is out of level up range(1-8)");
					}
					
				}
				
				
			}
		});
		
		btnLevelup.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/24check.png")).getImage()));
		btnLevelup.setBounds(448, 23, 129, 25);
		panel.add(btnLevelup);
		
		JLabel lblLocation = new JLabel("Location:");
		lblLocation.setBounds(176, 293, 64, 14);
		panel.add(lblLocation);
		
		comboBoxLocation = new JComboBox<String>();
//		comboBoxLocation.setModel(new DefaultComboBoxModel<String>(getTables("location")));
		comboBoxLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				if(tglbtnSchedule.isSelected())csp.refreshTable("overbuttom2");
//				else csp.refreshTable("overbuttom");
				refreshOverTables();
				
				comboBoxLine.setModel(new DefaultComboBoxModel<String>(csp.fillComboBox("lines","comboBoxLocation")));
//				initialValues();
				lblTotal.setText("Total: "+String.valueOf(tableTerm.getRowCount()));
			}
		});
		comboBoxLocation.setBounds(176, 314, 129, 20);
		panel.add(comboBoxLocation);
		
		JLabel labelLine = new JLabel("Line:");
		labelLine.setBounds(478, 292, 46, 14);
		panel.add(labelLine);
		
		comboBoxLine = new JComboBox<String>();
		
		comboBoxLine.setBounds(478, 311, 119, 20);
		comboBoxLine.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
//				if(tglbtnSchedule.isSelected())csp.refreshTable("overbuttom2");
//				else csp.refreshTable("overbuttom");
				refreshOverTables();
				lblTotal.setText("Total: "+String.valueOf(tableTerm.getRowCount()));
			}
			
		});
		panel.add(comboBoxLine);
		
		lblTotal = new JLabel("Total: ");
		lblTotal.setBounds(640, 589, 149, 16);
		panel.add(lblTotal);
		
		btnReload = new JButton("Reload All");
		btnReload.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int ans = JOptionPane.showConfirmDialog(null, "Do you want to reinitial all elements?\n(Click YES when you add or delete any terms or locations or imported new database)", "Confirmation", JOptionPane.YES_NO_OPTION);
				if(ans == JOptionPane.YES_OPTION){
					initialValues();
				}
				
				
			}
			
		});
		btnReload.setBounds(635, 8, 129, 37);
		//panel.add(btnReload);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(826, 276, 377, 296);
		panel.add(scrollPane_3);
		
		jtTimeCount = new JTable();
		scrollPane_3.setViewportView(jtTimeCount);
		
//		JScrollPane scrollPane_11 = new JScrollPane();
//		scrollPane_11.setBounds(825, 444, 378, 128);
//		panel.add(scrollPane_11);
//		
//		jtLineCount = new JTable();
//		scrollPane_11.setViewportView(jtLineCount);

//Start Students Tab-----------------------TODO StudentTab
		JPanel StudentsTab = new JPanel();
		tabbedPane.addTab("Students", null, StudentsTab, null);
		StudentsTab.setLayout(new MigLayout("", "", ""));
		
		JPanel panelSt = new JPanel();
		panelSt.setBorder(BorderFactory.createTitledBorder("Students"));
		panelSt.setLayout(new MigLayout("", "", ""));
		
		//ROW#1
		panelSt.add(new JLabel("Search By: "),"left,split 3");
		
		comboBoxStKey = new JComboBox<String>();
		comboBoxStKey.setModel(new DefaultComboBoxModel<String>(new String[] {"SID", "FirstName", "LastName","Cell","Amount"}));
		panelSt.add(comboBoxStKey, "gapleft 10");
		tfStKey = new JTextField();
		tfStKey.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				refreshTable(jtSt,"students");
			}
		});
		panelSt.add(tfStKey,"gapleft 20, width 150,gaptop 5,wrap");
		
		//ROW#2
		panelSt.add(new JLabel("Term:"),"left,sg 2,split 3");
		panelSt.add(new JLabel("Location:"),"gapleft 30,sg 2");
		panelSt.add(new JLabel("Day:"),"gapleft 30,sg 2,wrap");
		
		//ROW#3
		comboBoxStTerm = new JComboBox<String>();
		comboBoxStTerm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshTable(jtSt,"students");
			}
		});
//		comboBoxStTerm.setModel(new DefaultComboBoxModel<String>(getTables("terms")));
		comboBoxStLoc = new JComboBox<String>();
		comboBoxStLoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshTable(jtSt,"students");
			}
		});
//		comboBoxStLoc.setModel(new DefaultComboBoxModel<String>(getTables("location")));
		comboBoxStDay = new JComboBox<String>();
		comboBoxStDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshTable(jtSt,"students");
			}
		});
		comboBoxStDay.setModel(new DefaultComboBoxModel<String>(new String[]{"All","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"}));
		panelSt.add(comboBoxStTerm,"left,split 3,sg 2");
		panelSt.add(comboBoxStLoc,"gapleft 30,sg 2");
		panelSt.add(comboBoxStDay,"gapleft 30,sg 2");
		
		//right top pane -- report info
		JPanel rt = new JPanel();
		rt.setBorder(BorderFactory.createTitledBorder("Report"));
		rt.setLayout(new MigLayout("", "", ""));
		//rt #1
		rt.add(new JLabel("Total records: "),"left,sg rt1");
		rtTR = new JTextField();
		rtTR.setEditable(false);
		rt.add(rtTR,"sg rttf,w :70:,growx");
		
		rt.add(new JLabel("Retake count:"),"left,sg rt1,gapleft 30");
		rtRC = new JTextField();
		rt.add(rtRC,"sg rttf,w :70:,growx,wrap");
		//rt #2
		rt.add(new JLabel("Total amount: "),"left,sg rt1");
		rtTA = new JTextField();
		rtTA.setEditable(false);
		rt.add(rtTA,"sg rttf,w :70:,growx");
		rtRList = new JButton("Show Detail");
		rtRList.addActionListener(new ActionListener(){
			

			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					retake = Integer.parseInt(rtRC.getText());
					if(retake>0){
						csp.refreshTable("studentRetake");
					}
					
				}catch(NumberFormatException ex){
					JOptionPane.showMessageDialog(null, "Please enter a valid number");
				}
			}
			
		});
		rt.add(rtRList,"wrap,w 100!,skip");
		//rt #3
		rt.add(new JLabel("Unpaid number:"));
		rtUN = new JTextField();
		rtUN.setEditable(false);
		rt.add(rtUN,"sg rttf,w :70:,growx,wrap");
		
		//SELECT sid,levelID,count(levelID) from active_record GROUP by sid,levelID having count(*)>2
		
		
		
		
		//middle panel -- table
		JPanel middle = new JPanel();
		middle.setBorder(BorderFactory.createTitledBorder("Table"));
		middle.setLayout(new MigLayout());
		
		jtSt = new JTable();
		jtSt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = jtSt.getSelectedRow();
			//	JOptionPane.showMessageDialog(null, jtSt.getValueAt(row, 0));
				if(row!=-1){
					stSID.setText(jtSt.getValueAt(row, 0).toString());
					if(jtSt.getValueAt(row, 1)!=null)
					stFN.setText(jtSt.getValueAt(row, 1).toString());
					if(jtSt.getValueAt(row, 2)!=null)
					stLN.setText(jtSt.getValueAt(row, 2).toString());
					if(jtSt.getValueAt(row,7)!=null)
						stAmount.setText(jtSt.getValueAt(row,7).toString());
					if(jtSt.getValueAt(row, 6)!=null)
						stMethod.setSelectedItem(jtSt.getValueAt(row, 6));
					if(jtSt.getValueAt(row, 8)!=null)
						stDate.setText(jtSt.getValueAt(row, 8).toString());
				}
			}
		});
		
		jtSt.setFillsViewportHeight(true);
		jtSt.setAutoCreateRowSorter(true);
		
		jtSt.setModel(new DefaultTableModel());
		//jtSt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		//resizeColumnWidth(jtSt);
		JScrollPane scrollPane_10 = new JScrollPane();
		scrollPane_10.setViewportView(jtSt);
		//panelSt.add(scrollPane_10, "w 200:1000:,h 100:300:,span,grow,gaptop 10,pushx");
		middle.add(scrollPane_10,"w 200:1000:,h 100:300:,span,grow,gaptop 10");
		
		//second panel
		JPanel panelStEdit = new JPanel();
		panelStEdit.setBorder(BorderFactory.createTitledBorder("Edit"));
		panelStEdit.setLayout(new MigLayout("", "[]30[]", ""));
		//ROW#1 2nd
		//gp1
		panelStEdit.add(new JLabel("SID: "),"sg sid, left,split 2");
		stSID = new JTextField();
		stSID.setEnabled(false);
		panelStEdit.add(stSID,"left,growx,w 70!");
		//gp2
		panelStEdit.add(new JLabel("FirstName: "),"left,sg lbl1,split 2");
		stFN = new JTextField();
		stFN.setEnabled(false);
		panelStEdit.add(stFN,"left,sg stedit,growx,w :170:");
		//gp3
		panelStEdit.add(new JLabel("LastName: "),"left,sg lbl1,split 2");
		stLN = new JTextField();
		stLN.setEnabled(false);
		panelStEdit.add(stLN,"left,sg stedit,growx,w :170:");
		
		stUpdate = new JButton("Update");
		stUpdate.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO DOING STBUTTONS
				updateData("financial");
				JOptionPane.showMessageDialog(null, "updated");
				refreshTable(jtSt,"students");
				
				
			}
		});
		panelStEdit.add(stUpdate,"skip, right,span,w 100!,wrap");
		//ROW#2
		panelStEdit.add(new JLabel("Amount: "),"sg sid, left, split 2");
		stAmount = new JTextField();
		panelStEdit.add(stAmount,"left,growx,w 70!");
		panelStEdit.add(new JLabel("Method: "),"left,sg lbl1,split 2");
		stMethod = new JComboBox<String>();
		stMethod.setModel(new DefaultComboBoxModel<String>(new String[] {"Unpaid", "Cash", "Cheque", "EMT", "CreditCard", "BankDraft", "N/A"}));
		panelStEdit.add(stMethod,"left,sg stedit,growx,w :170:");
		panelStEdit.add(new JLabel("Date:(yyyy-mm-dd) "),"left,sg lbl1,split 2");
		stDate = new JTextField();
		panelStEdit.add(stDate,"left,sg stedit,growx,w :170:");
		
		
		stDelete = new JButton("Delete");
		stDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PreparedStatement pst=null;
				String query = "DELETE FROM 'active_record' WHERE ID = ?1";
				int row = jtSt.getSelectedRow();
				if (row != -1){
					String id = jtSt.getModel().getValueAt(row, 0).toString();
					csp.deleteRow(id, "active_record");
				}
				
				
				refreshTable(jtSt,"students");
			}
		});
				
			
		panelStEdit.add(stDelete,"w 100!,span,right,wrap");
		
		
		//TODO adding sub panels
		StudentsTab.add(panelSt, "");
		StudentsTab.add(rt,"span,grow,wrap");
		StudentsTab.add(middle,"wrap,span,grow");
		StudentsTab.add(panelStEdit,"span,grow");
	
//End Students Tab------------------------		
		
//TODO Schedule Panel-----------------
		ScheduleTab = new JPanel();
		tabbedPane.addTab("Schedule",null,ScheduleTab,null);
		ScheduleTab.setLayout(new MigLayout());
		
		//TOP sch pane
		JPanel schTop = new JPanel(new MigLayout("","[]10[]",""));
		schTop.setBorder(BorderFactory.createTitledBorder("Setting"));
		ScheduleTab.add(schTop, "growx,span,wrap");
		//TOP ROW #1
		schTop.add(new JLabel("Term: "),"sg schlbl");
		schTop.add(new JLabel("Location: "),"sg schlbl");
		schTop.add(new JLabel("Day: "),"sg schlbl");
		schTop.add(new JLabel("Start: "),"sg time");
		cbSchStart = new JComboBox<String>();
		schTop.add(cbSchStart);
		
		schTop.add(new JLabel("History: "),"gapleft 20");
		cbSchHistory = new JComboBox<String>();
		schTop.add(cbSchHistory,"wrap");
		
		cbSchTerm = new JComboBox<String>();
		cbSchLocation = new JComboBox<String>();
		cbSchLocation.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = csp.fillComboBox("lines","cbSchLocation").length-1;
				if(i<6) 
					ScheduleTab.remove(schLine6);
				else if(schLine6.getParent()==null)
					ScheduleTab.add(schLine6, "sg panels");
				JOptionPane.showMessageDialog(null, i+" lines in this location");
				
				
			}
			
		});
		cbSchDay = new JComboBox<String>();
		
		schTop.add(cbSchTerm,"sg schlbl");
		schTop.add(cbSchLocation,"sg schlbl");
		schTop.add(cbSchDay,"sg schlbl");
		schTop.add(new JLabel("End: "),"sg time");
		cbSchEnd = new JComboBox<String>();
		schTop.add(cbSchEnd);
		
		JButton schLoad = new JButton("Load");
		schLoad.setEnabled(false);
		schTop.add(schLoad,"width 80!,gapleft 20");
		
		JButton schUpdate = new JButton("Update");
		schUpdate.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int a = JOptionPane.showConfirmDialog(null, "Please confirm the schedule is correctly setted", "Confirmation", JOptionPane.YES_NO_OPTION);
				if(a == JOptionPane.YES_OPTION){
					csp.updateScheduleForm();
					
				}
				
			}
			
		});
		schTop.add(schUpdate,"alignx right,width 80!");
		
		//Line #1 panel
				JPanel schLine1 = new JPanel(new MigLayout("","",""));
				schLine1.setBorder(BorderFactory.createTitledBorder("Line #1"));
				ScheduleTab.add(schLine1,"sg panels,split 3");
				sps1 = new SchedulePanels(schLine1);
		
		
		//Line #2 panel
				JPanel schLine2 = new JPanel(new MigLayout("","",""));
				schLine2.setBorder(BorderFactory.createTitledBorder("Line #2"));
				ScheduleTab.add(schLine2,"sg panels");
				sps2 = new SchedulePanels(schLine2);
				
		//Line #3 panel
				JPanel schLine3 = new JPanel(new MigLayout("","",""));
				schLine3.setBorder(BorderFactory.createTitledBorder("Line #3"));
				ScheduleTab.add(schLine3,"sg panels,wrap");
				sps3 = new SchedulePanels(schLine3);
				
		//Line #4		
				JPanel schLine4 = new JPanel(new MigLayout("","",""));
				schLine4.setBorder(BorderFactory.createTitledBorder("Line #4"));
				ScheduleTab.add(schLine4,"sg panels,split 3");
				sps4 = new SchedulePanels(schLine4);
		//Line #5		
				JPanel schLine5 = new JPanel(new MigLayout("","",""));
				schLine5.setBorder(BorderFactory.createTitledBorder("Line #5"));
				ScheduleTab.add(schLine5,"sg panels");
				sps5 = new SchedulePanels(schLine5);
		//Line #6		
				schLine6 = new JPanel(new MigLayout("","",""));
				schLine6.setBorder(BorderFactory.createTitledBorder("Line #6"));
				ScheduleTab.add(schLine6,"sg panels");
				sps6 = new SchedulePanels(schLine6);
		
				
				
		
//END Schedule Panel-------------------		
				
//Waiting List Panel==============================
				//TODO waiting list Tab
				WaitingListTab = new JPanel();
				tabbedPane.addTab("WaitingList",null,WaitingListTab,null);
				WaitingListTab.setLayout(new MigLayout());
//				WaitingListView wlView = new WaitingListView();
				JPanel wlPanel = wlView.getView();
				WaitingListTab.add(wlPanel);
//				WaitingListModel wlModel = new WaitingListModel();
				wlView.listTable.setModel(DbUtils.resultSetToTableModel(wlModel.getList()));
				//hide first column of 'ID'
				TableColumnModel tcm = wlView.listTable.getColumnModel();
				tcm.removeColumn(tcm.getColumn(0));
				resizeColumnWidth(wlView.listTable,100);
				
				wlView.cbTerm.setModel(new DefaultComboBoxModel<String>(CommonModel.fillComboBox("terms")));
				wlView.cbLocation.setModel(new DefaultComboBoxModel<String>(CommonModel.fillComboBox("location")));
				wlView.cbDay.setModel(new DefaultComboBoxModel<String>(CommonModel.fillComboBox("day")));
				wlView.cbLevel.setModel(new DefaultComboBoxModel<String>(CommonModel.fillComboBox("level")));
				wlView.cbStart.setModel(new DefaultComboBoxModel<String>(CommonModel.fillComboBox("time")));
				wlView.cbEnd.setModel(new DefaultComboBoxModel<String>(CommonModel.fillComboBox("time")));
				
//				String mterm="",location="",day="",level="",start="",end="";
				wlView.listTable.addMouseListener(new MouseAdapter(){
					@Override
					public void mouseClicked(MouseEvent arg0) {
						int row = wlView.listTable.getSelectedRow();
						if(row != -1){
							wlView.tfFN.setText(wlView.listTable.getValueAt(row, 1).toString());
							wlView.tfLN.setText(wlView.listTable.getValueAt(row, 2).toString());
							if(wlView.listTable.getValueAt(row, 3) != null){
								String term = wlView.listTable.getValueAt(row, 3).toString();
								wlView.cbTerm.setSelectedItem(term);
							}
							if(wlView.listTable.getValueAt(row, 4) != null){
								String location = wlView.listTable.getValueAt(row, 4).toString();
								wlView.cbLocation.setSelectedItem(location);
							}
							if(wlView.listTable.getValueAt(row, 5) != null){
								String level = wlView.listTable.getValueAt(row, 5).toString();
								wlView.cbLevel.setSelectedItem(level);
							}
							if(wlView.listTable.getValueAt(row, 6) != null){
								String day = wlView.listTable.getValueAt(row, 6).toString();
								wlView.cbDay.setSelectedItem(day);
							}
							if(wlView.listTable.getValueAt(row, 7) != null){
								String time = wlView.listTable.getValueAt(row, 7).toString();
								String sTime = time.substring(0, time.indexOf("-"));
								String eTime = time.substring(time.indexOf("-")+1);
								wlView.cbStart.setSelectedItem(sTime);
								wlView.cbEnd.setSelectedItem(eTime);
							}
						}
					}
				});

				wlView.Add.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						String fname = wlView.tfFN.getText();
						String lname = wlView.tfLN.getText();
						String term = wlView.cbTerm.getSelectedItem().toString();
						String location = wlView.cbLocation.getSelectedItem().toString();
						String level = wlView.cbLevel.getSelectedItem().toString();
						String day = wlView.cbDay.getSelectedItem().toString();
						String time = wlView.cbStart.getSelectedItem().toString()+"-"+wlView.cbEnd.getSelectedItem().toString();
						String query = "INSERT INTO waiting_list (FirstName,LastName,Term,Location,Level,Day,Time) VALUES ('"+fname+"','"+lname+"','"+term+"','"+location+"','"
								+level+"','"+day+"','"+time+"')";
						if(fname.isEmpty()&&lname.isEmpty()){
							JOptionPane.showMessageDialog(null, "Please enter a name first");
						}
						else{
							new ChacoSwimModel().executeWithoutRS(query);
							refreshWaitingListTable();
							JOptionPane.showMessageDialog(null, "Added a new record");
						}
					}
					
				});
				
				wlView.Update.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						int row = wlView.listTable.getSelectedRow();
						if(row != -1){
							String fname = wlView.tfFN.getText();
							String lname = wlView.tfLN.getText();
							String term = wlView.cbTerm.getSelectedItem().toString();
							String location = wlView.cbLocation.getSelectedItem().toString();
							String level = wlView.cbLevel.getSelectedItem().toString();
							String day = wlView.cbDay.getSelectedItem().toString();
							String time = wlView.cbStart.getSelectedItem().toString()+"-"+wlView.cbEnd.getSelectedItem().toString();
							String query = "UPDATE waiting_list SET firstName = '"+fname+"',"
									+ "lastName = '"+lname+"',"
									+ "term = '"+term+"',"
									+ "location = '"+location+"',"
									+ "level = '"+level+"',"
									+ "day = '"+day+"',"
									+ "time = '"+time+"'"
											+ " WHERE id = '"+wlView.listTable.getModel().getValueAt(row, 0)+"'";
							if(fname.isEmpty()&&lname.isEmpty()){
								JOptionPane.showMessageDialog(null, "Please enter a name first");
							}
							else{
								new ChacoSwimModel().executeWithoutRS(query);
								refreshWaitingListTable();
								JOptionPane.showMessageDialog(null, "Updated");
							}
							
						}
						else{
							JOptionPane.showMessageDialog(null, "Please select a row first");
						}
						
					}
					
				});
				
				wlView.Reset.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						wlView.tfFN.setText("");
						wlView.tfLN.setText("");
						wlView.cbTerm.setSelectedIndex(0);
						wlView.cbLocation.setSelectedIndex(0);
						wlView.cbLevel.setSelectedIndex(0);
						wlView.cbDay.setSelectedIndex(0);
						wlView.cbStart.setSelectedIndex(0);
						wlView.cbEnd.setSelectedIndex(0);
					}
				});
				
				wlView.Remove.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						int row = wlView.listTable.getSelectedRow();
						if(row != -1){
//							String id = wlView.listTable.getModel().getValueAt(row, 0).toString();
//							String query = "DELETE FROM waiting_list where id = "+id;
//							new ChacoSwimModel().executeWithoutRS(query);
							deleteWaitingListRecord(row);
							refreshWaitingListTable();
							JOptionPane.showMessageDialog(null, "Deleted");
						}
						else{
							JOptionPane.showMessageDialog(null, "Select a record first");
						}
					}
				});
				
				wlView.Refresh.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						refreshWaitingListTable();
						JOptionPane.showMessageDialog(null, "Refreshed");
					}
					
				});
				
				wlView.CheckIn.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						int row = wlView.listTable.getSelectedRow();
						if( row != -1){
							String status = wlView.listTable.getValueAt(row, 0).toString();
							if (status.equalsIgnoreCase("ok")){
								int ans = JOptionPane.showConfirmDialog(null, "Is the selected user already a member?", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
								if( ans == JOptionPane.YES_OPTION){
									String fn = wlView.listTable.getValueAt(row, 1).toString();
									String ln = wlView.listTable.getValueAt(row, 2).toString();
									
									String[] sidArray = wlModel.checkInClicked(fn, ln);
									
									//check if any record matches the fn,ln
									if(sidArray != null){
										JFrame frame = new JFrame("sid result");
										String sid = (String) JOptionPane.showInputDialog(frame, "Found "+(sidArray.length)+" sid match(es) \n"+fn+" "+ln+"\nPlease"
												+ " select the correct sid\n", "Select the right sid", JOptionPane.QUESTION_MESSAGE, null, sidArray, sidArray[0]);
										if(sid != null){
											if(!sid.isEmpty()){
												wlModel.checkInWithSID(sid, wlView.listTable,row);
												deleteWaitingListRecord(row);
												refreshWaitingListTable();
											}
										}
									}else{
										int regNew = JOptionPane.showConfirmDialog(null, "Can't find any registration info that matches with the waiting list record,"
												+ "\nDo you want to register a new record with basic info(if yes, please complete it later)?","Register a new record?",
												JOptionPane.YES_NO_OPTION);
										if( regNew == JOptionPane.YES_OPTION){
											String tempSID = wlModel.registerNew(wlView.listTable, row);
											wlModel.checkInWithSID(tempSID, wlView.listTable, row);
											deleteWaitingListRecord(row);
											refreshWaitingListTable();
										}
									}
									//ask for sid next
								}
								
								else if ( ans == JOptionPane.NO_OPTION){
									int regNew = JOptionPane.showConfirmDialog(null,"Do you want to register a new record with basic info(if yes, please complete it later)?","Register a new record?",
											JOptionPane.YES_NO_OPTION);
									if( regNew == JOptionPane.YES_OPTION){
										String tempSID = wlModel.registerNew(wlView.listTable, row);
										wlModel.checkInWithSID(tempSID, wlView.listTable, row);
										deleteWaitingListRecord(row);
										refreshWaitingListTable();
									}
								}
							}
							else{
								JOptionPane.showMessageDialog(null, "Current selection is not availble yet");
							}
						}
						else{
							JOptionPane.showMessageDialog(null, "Select a record first");
						}
						
					}
					
				});
//END Waiting List Panel ============================
				
				
//Start Tab Coach~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		CoachTab = new JPanel();
		tabbedPane.addTab("Coaches", null, CoachTab, null);
		CoachTab.setLayout(null);
		
		JScrollPane scrollPane_9 = new JScrollPane();
		scrollPane_9.setBounds(167, 33, 732, 242);
		CoachTab.add(scrollPane_9);
		
		jtCoaches = new JTable();
		jtCoaches.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = jtCoaches.getSelectedRow();
				if(row!=-1){
					
					tfCName.setText(jtCoaches.getValueAt(row, 1).toString());
					if(jtCoaches.getValueAt(row, 2)!=null)
					tfCCell.setText(jtCoaches.getValueAt(row, 2).toString());
					if(jtCoaches.getValueAt(row, 3)!=null)
					tfCEmail.setText(jtCoaches.getValueAt(row, 3).toString());
				}
			}
		});
		scrollPane_9.setViewportView(jtCoaches);
		
		JLabel lblCoachesManagement = new JLabel("Coaches Management:");
		lblCoachesManagement.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCoachesManagement.setBounds(168, 298, 254, 24);
		CoachTab.add(lblCoachesManagement);
		
		JLabel lblCName = new JLabel("Name:");
		lblCName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCName.setBounds(168, 345, 88, 24);
		CoachTab.add(lblCName);
		
		JLabel lblCCell = new JLabel("Cell:");
		lblCCell.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCCell.setBounds(168, 393, 88, 24);
		CoachTab.add(lblCCell);
		
		JLabel lblCEmail = new JLabel("Email:");
		lblCEmail.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCEmail.setBounds(168, 439, 88, 24);
		CoachTab.add(lblCEmail);
		
		tfCName = new JTextField();
		tfCName.setBounds(235, 348, 214, 20);
		CoachTab.add(tfCName);
		tfCName.setColumns(10);
		
		tfCCell = new JTextField();
		tfCCell.setColumns(10);
		tfCCell.setBounds(235, 396, 214, 20);
		CoachTab.add(tfCCell);
		
		tfCEmail = new JTextField();
		tfCEmail.setColumns(10);
		tfCEmail.setBounds(235, 442, 214, 20);
		CoachTab.add(tfCEmail);
		
		JButton btnCAdd = new JButton(" New ");
		btnCAdd.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/24contacts.png")).getImage()));
		
		btnCAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(tfCName.getText()!=null&&!tfCName.getText().equals("")){
					new TabCoaches(jtCoaches).addCNew(tfCName.getText(), tfCCell.getText(), tfCEmail.getText());
					tfCName.setText("");
					tfCCell.setText("");
					tfCEmail.setText("");
					comboBoxStaCoach.setModel(new DefaultComboBoxModel<String>(new StatisticMethod().getCoaches()));
				}
				else JOptionPane.showMessageDialog(null, "Give a coach name at least please.");
				
			}
		});
		btnCAdd.setBounds(626, 347, 126, 23);
		CoachTab.add(btnCAdd);
		
		JButton btnCEdit = new JButton(" Edit ");
		btnCEdit.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/24compose.png")).getImage()));
		
		btnCEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = jtCoaches.getSelectedRow();
				if(row!=-1){
					String id = jtCoaches.getValueAt(row, 0).toString();
					if(tfCName.getText()!=null&&!tfCName.getText().equals("")){
						new TabCoaches(jtCoaches).editeC(id,tfCName.getText(), tfCCell.getText(), tfCEmail.getText());
						tfCName.setText("");
						tfCCell.setText("");
						tfCEmail.setText("");
						comboBoxStaCoach.setModel(new DefaultComboBoxModel<String>(new StatisticMethod().getCoaches()));
					}
					else JOptionPane.showMessageDialog(null, "Give a coach name at least please.");
					
				}
				
			}
		});
		btnCEdit.setBounds(626, 395, 126, 23);
		CoachTab.add(btnCEdit);
		
		JButton btnCDelete = new JButton("Delete");
		btnCDelete.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/24delete.png")).getImage()));
		btnCDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = jtCoaches.getSelectedRow();
				if(row!=-1){
					String id = jtCoaches.getValueAt(row, 0).toString();
					new TabCoaches(jtCoaches).deleteC(id);
					comboBoxStaCoach.setModel(new DefaultComboBoxModel<String>(new StatisticMethod().getCoaches()));
				}
			}
		});
		btnCDelete.setBounds(626, 441, 126, 23);
		CoachTab.add(btnCDelete);
		//initial coach table
		new TabCoaches(jtCoaches).refreshTable();
		//end Tab Coach~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		JPanel EmailTab = new JPanel();
		EmailTab.setForeground(Color.WHITE);
		tabbedPane.addTab("Email", null, EmailTab, null);
		EmailTab.setLayout(null);
		
		
		
		//Email part
		JButton btnSendMail = new JButton("Send Mail");
		btnSendMail.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/24rocket.png")).getImage()));
		btnSendMail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String subject = tfSubject.getText();
				String body = taBody.getText();
				String[] address = getRecipient();
				String addressString="";
				
				Properties props= new Properties();
				
// Gmail
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.socketFactory.port", "465");
				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.port", "465");
				

//hotmail
//				props.setProperty("mail.transport.protocol", "stmp");
//				props.setProperty("mail.host", "smtp.live.com");
//			    props.put("mail.smtp.starttls.enable", "true");
//			    props.put("mail.smtp.auth", "true");
//			    props.put("mail.smtp.port", "587");
			    
				Session session = Session.getDefaultInstance(props,
						new javax.mail.Authenticator(){
							protected PasswordAuthentication getPasswordAuthentication(){
								return new PasswordAuthentication("andrew198712@gmail.com","zz208516");
//								return new PasswordAuthentication("wenzhong.zheng@hotmail.com","208516Zz");
							}
						}
						);
				
				
				
				
				try{
					Message message=new MimeMessage(session);
					//message.setFrom(new InternetAddress("andrew198712@gmail.com","Wenzhong zzz"));
					message.setFrom(new InternetAddress("info@chacoswim.com","Wenzhong zzz"));
					if(address.length==1)message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address[0].toString()));
					else if(address.length>1){
						for(String i:address){
							if(addressString.isEmpty())addressString = i;
							else addressString=addressString+","+i;
						}
						message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(addressString));
					}
					
					message.setSubject(subject);
					message.setText(body);
					
					//check attachment
					if(!attachment_PathList.isEmpty()){
						Multipart multipart = new MimeMultipart("mixed");
						for (String str : attachment_PathList) {
						    MimeBodyPart messageBodyPart = new MimeBodyPart();
						    DataSource source = new FileDataSource(str);
						    messageBodyPart.setDataHandler(new DataHandler(source));
						    messageBodyPart.setFileName(source.getName());
						    multipart.addBodyPart(messageBodyPart);
						} 
						message.setContent(multipart);
						attachment_PathList.clear();
						refreshListAttachment();
					}
					int ans = JOptionPane.showConfirmDialog(null, "Do you want to send this email?", "Confirmation", JOptionPane.YES_NO_OPTION);
					if(ans == JOptionPane.YES_OPTION){
						JOptionPane.showMessageDialog(null, "This process may take up to a few minutes depends on the number of recipients,Please be patient.");
						Transport.send(message);
						JOptionPane.showMessageDialog(null, "Message sent");
					}
				}catch(Exception e){
					JOptionPane.showMessageDialog(null,"Line1198: "+ e);
				}
				
				
			}
		});
		btnSendMail.setBounds(845, 596, 124, 30);
		EmailTab.add(btnSendMail);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBackground(Color.WHITE);
		separator.setForeground(Color.BLACK);
		separator.setBounds(550, 11, 3, 685);
		EmailTab.add(separator);
		
		JLabel lblTo = new JLabel("To:");
		lblTo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTo.setBounds(639, 41, 65, 23);
		EmailTab.add(lblTo);
		
		JLabel lblSubject = new JLabel("Subject:");
		lblSubject.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSubject.setBounds(639, 71, 65, 23);
		EmailTab.add(lblSubject);
		
		tfTo = new JTextField();
		tfTo.setBounds(722, 41, 300, 23);
		EmailTab.add(tfTo);
		tfTo.setColumns(10);
		
		tfSubject = new JTextField();
		tfSubject.setColumns(10);
		tfSubject.setBounds(722, 71, 300, 23);
		EmailTab.add(tfSubject);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(639, 104, 510, 446);
		EmailTab.add(scrollPane_4);
		
		taBody = new JTextArea();
		scrollPane_4.setViewportView(taBody);
		
		rdbtnSingleRecipient = new JRadioButton("Single Recipient");
		
		rdbtnSingleRecipient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tfTo.setEnabled(true);
				rdbtnStudents.setSelected(false);
				rdbtnAllCoaches.setSelected(false);
				rdbtnStudents.setEnabled(false);
				rdbtnAllCoaches.setEnabled(false);
				comboBoxEmailTerm.setEnabled(false);
				comboBoxEmailDay.setEnabled(false);
				comboBoxEmailLoc.setEnabled(false);
			}
		});
		rdbtnSingleRecipient.setSelected(true);
		buttonGroup.add(rdbtnSingleRecipient);
		rdbtnSingleRecipient.setBounds(87, 20, 138, 23);
		EmailTab.add(rdbtnSingleRecipient);
		
		rdbtnMultipleRecipients = new JRadioButton("Multiple Recipients");
		rdbtnMultipleRecipients.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfTo.setEnabled(false);
				rdbtnStudents.setEnabled(true);
				rdbtnAllCoaches.setEnabled(true);
				
				
				
			}
		});
		buttonGroup.add(rdbtnMultipleRecipients);
		rdbtnMultipleRecipients.setBounds(303, 20, 160, 23);
		EmailTab.add(rdbtnMultipleRecipients);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(69, 50, 484, 2);
		EmailTab.add(separator_1);
		
		rdbtnAllCoaches = new JRadioButton("All Coaches");
		rdbtnAllCoaches.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBoxEmailTerm.setEnabled(false);
				comboBoxEmailDay.setEnabled(false);
				comboBoxEmailLoc.setEnabled(false);
			}
		});
		buttonGroup_1.add(rdbtnAllCoaches);
		rdbtnAllCoaches.setBounds(87, 91, 138, 23);
		EmailTab.add(rdbtnAllCoaches);
		
		rdbtnStudents = new JRadioButton("Students");
		rdbtnStudents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBoxEmailTerm.setEnabled(true);
				comboBoxEmailDay.setEnabled(true);
				comboBoxEmailLoc.setEnabled(true);
			}
		});
		buttonGroup_1.add(rdbtnStudents);
		rdbtnStudents.setBounds(303, 91, 138, 23);
		EmailTab.add(rdbtnStudents);
		
		JLabel lblsec2 = new JLabel("When multiple recipients applied: ");
		lblsec2.setBounds(79, 63, 258, 14);
		EmailTab.add(lblsec2);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(69, 121, 484, 2);
		EmailTab.add(separator_2);
		
		JLabel lblWhenStudentsApplied = new JLabel("When students applied: ");
		lblWhenStudentsApplied.setBounds(79, 134, 196, 14);
		EmailTab.add(lblWhenStudentsApplied);
		
		comboBoxEmailTerm = new JComboBox<String>();
		comboBoxEmailTerm.setBounds(217, 159, 183, 23);
//		comboBoxEmailTerm.setModel(new DefaultComboBoxModel<String>(getTables("terms")));
		EmailTab.add(comboBoxEmailTerm);
		
		comboBoxEmailLoc = new JComboBox<String>();
		comboBoxEmailLoc.setEnabled(false);
		comboBoxEmailLoc.setBounds(217, 193, 183, 23);
		EmailTab.add(comboBoxEmailLoc);
		
		comboBoxEmailDay = new JComboBox<String>();
		comboBoxEmailDay.setModel(new DefaultComboBoxModel<String>(new String[] {"All", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}));
		comboBoxEmailDay.setBounds(217, 223, 120, 23);
		EmailTab.add(comboBoxEmailDay);
		
		JLabel lblSelectTerm_2 = new JLabel("Select Term: ");
		lblSelectTerm_2.setBounds(79, 163, 128, 14);
		EmailTab.add(lblSelectTerm_2);
		
		JLabel lblSelectDay_1 = new JLabel("Select Day: ");
		lblSelectDay_1.setBounds(79, 227, 128, 14);
		EmailTab.add(lblSelectDay_1);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(69, 252, 484, 2);
		EmailTab.add(separator_3);
		
		JButton btnAttachFile = new JButton("Attach File");
		btnAttachFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int a = chooser.showOpenDialog(null);
				if(a==JFileChooser.APPROVE_OPTION){
					 f = chooser.getSelectedFile();
					 if(attachment_PathList.contains(f.getAbsolutePath())){
						 JOptionPane.showMessageDialog(null, "Duplicated File Attachment."+f.getName());
					 }
					 else{
						 attachment_PathList.add(f.getAbsolutePath());
						 refreshListAttachment();
					 }
					 
				}
				
				
				
			}
		});
		btnAttachFile.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/24dolly.png")).getImage()));
		btnAttachFile.setBounds(80, 302, 116, 23);
		EmailTab.add(btnAttachFile);
		
		JLabel lblAttachmentManage = new JLabel("Attachment Manage:");
		lblAttachmentManage.setBounds(79, 265, 183, 14);
		EmailTab.add(lblAttachmentManage);
		
		rdbtnStudents.setEnabled(false);
		rdbtnAllCoaches.setEnabled(false);
		comboBoxEmailTerm.setEnabled(false);
		comboBoxEmailDay.setEnabled(false);
		comboBoxEmailLoc.setEnabled(false);
		
		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setBounds(80, 339, 460, 156);
		EmailTab.add(scrollPane_5);
		
		listAttachment = new JList<String>();
		listAttachment.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()==false){
					if(listAttachment.getSelectedIndex()==-1){
						//No selection, disable fire button
						btnAttachmentDelete.setVisible(false);
						btnAttachmentDelete.setEnabled(false);
					}
					else{
						//Selection, enable the fire button.
						btnAttachmentDelete.setVisible(true);
						btnAttachmentDelete.setEnabled(true);
					}
				}
				
			}
		});
		scrollPane_5.setViewportView(listAttachment);
		
		btnAttachmentDelete = new JButton("Delete File");
		btnAttachmentDelete.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/24delete.png")).getImage()));
		btnAttachmentDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = listAttachment.getSelectedIndex();
				listModel.remove(index);
				attachment_PathList.remove(index);
				int size = listModel.size();
				if(size==0){
					btnAttachmentDelete.setEnabled(false);
					btnAttachmentDelete.setVisible(false);
				}
				else{
					if(index==listModel.getSize()){
						index--;
					}
					
					listAttachment.setSelectedIndex(index);
					listAttachment.ensureIndexIsVisible(index);
				}
				
			}
		});
		
		btnAttachmentDelete.setEnabled(false);
		btnAttachmentDelete.setVisible(false);
		btnAttachmentDelete.setBounds(217, 302, 116, 23);
		EmailTab.add(btnAttachmentDelete);
		
		JButton btnCopy = new JButton("Copy");
		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String[] address = getRecipient();
				String add="";
				if(address.length==1) add=address[0];
				else if(address.length>1){
					for(String i:address){
						add=add+","+i;
					}
				}
				StringSelection stringSelection = new StringSelection(add);
				Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
				clpbrd.setContents(stringSelection, null);
				JOptionPane.showMessageDialog(null, "Copied");
			}
		});
		
		btnCopy.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/24polaroids.png")).getImage()));
		btnCopy.setBounds(80, 577, 97, 23);
		EmailTab.add(btnCopy);
		
		JLabel lblOrYouCould = new JLabel("Or you could copy the selected address to cliperboard");
		lblOrYouCould.setBounds(80, 537, 371, 29);
		EmailTab.add(lblOrYouCould);
		
		JSeparator separator_6 = new JSeparator();
		separator_6.setBounds(69, 524, 484, 2);
		EmailTab.add(separator_6);
		
		JLabel lblSelectLocation_1 = new JLabel("Select Location: ");
		lblSelectLocation_1.setBounds(79, 195, 128, 14);
		EmailTab.add(lblSelectLocation_1);
		
		
		
		
		//Statistics Part
		
		
		StatisticsTab = new JPanel();
		tabbedPane.addTab("Statistics", null, StatisticsTab, null);
		StatisticsTab.setLayout(null);
		
		JLabel lblTotalRegistedStudents = new JLabel("Total Registed Students:");
		lblTotalRegistedStudents.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTotalRegistedStudents.setBounds(49, 49, 215, 23);
		StatisticsTab.add(lblTotalRegistedStudents);
		
		JLabel lblStudentsNumberBased_1 = new JLabel("Students number based on term and coach: ");
		lblStudentsNumberBased_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblStudentsNumberBased_1.setBounds(49, 84, 294, 23);
		StatisticsTab.add(lblStudentsNumberBased_1);
		
		comboBoxStaCoach = new JComboBox<String>();
		comboBoxStaCoach.setModel(new DefaultComboBoxModel<String>(new StatisticMethod().getCoaches()));
		comboBoxStaCoach.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel<String> listCoachModel = new DefaultListModel<String>();
				String[] coachListData = new StatisticMethod().getCoaches_Term(comboBoxStaCoach.getSelectedItem().toString());
				for(String i:coachListData){
					listCoachModel.addElement(i);
				}
				listCoach.setModel(listCoachModel);
				
			}
		});
		comboBoxStaCoach.setBounds(341, 85, 148, 23);
		StatisticsTab.add(comboBoxStaCoach);
		
		JScrollPane scrollPane_6 = new JScrollPane();
		scrollPane_6.setBounds(49, 114, 440, 321);
		StatisticsTab.add(scrollPane_6);
		
		listCoach = new JList<String>();
		listCoach.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String str = listCoach.getSelectedValue().toString();
				String sub = str.substring(0, str.indexOf(" :"));
				new StatisticMethod().fillTerm_Coach(StaticTerm_Coach,sub,comboBoxStaCoach.getSelectedItem().toString());
			}
		});
		//String[] coachModel = 
		//listCoachModel.addElement();
		//list.setModel(new DefaultListModel(new StatisticMethod().getCoaches_Term(comboBoxStaCoach.getSelectedItem().toString())));
		scrollPane_6.setViewportView(listCoach);
		
		lblTotalreg = new JLabel("");
		lblTotalreg.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTotalreg.setBounds(254, 49, 77, 23);
		StatisticsTab.add(lblTotalreg);
		
		JLabel lblTermcoach = new JLabel("Term&Coach:");
		lblTermcoach.setBounds(578, 86, 86, 14);
		StatisticsTab.add(lblTermcoach);
		
		JScrollPane scrollPane_8 = new JScrollPane();
		scrollPane_8.setBounds(578, 114, 473, 321);
		StatisticsTab.add(scrollPane_8);
		
		StaticTerm_Coach = new JTable();
		scrollPane_8.setViewportView(StaticTerm_Coach);
		

		
		
		
		JPanel Export = new JPanel();
		tabbedPane.addTab("Export", null, Export, null);
		Export.setLayout(null);
		
		JButton btnExport = new JButton("Export Excel");
		btnExport.setForeground(new Color(50, 205, 50));
		btnExport.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("Save a File");
				chooser.setFileFilter(new FileTypeFilter(".xlsx","Excel File"));
				int c = JOptionPane.showConfirmDialog(null, "Please comfirm you have closed the target file if you want to overwrite to it","Confirm",JOptionPane.YES_NO_OPTION);
				if(c==JOptionPane.YES_OPTION){
					int ans = chooser.showSaveDialog(null);
					if(ans==JFileChooser.APPROVE_OPTION){
						File file = chooser.getSelectedFile();
						if(file.getName().endsWith(".xlsx")){						
							new WriteToExcel(file,comboBoxExTerm.getSelectedItem().toString(),comboBoxExLoc.getSelectedItem().toString(),comboBoxExDay.getSelectedItem().toString(),chckCondition.isSelected()).startWrite();
						}
						else{
							File file1 = new File(file.getAbsolutePath()+".xlsx");
							new WriteToExcel(file1,comboBoxExTerm.getSelectedItem().toString(),comboBoxExLoc.getSelectedItem().toString(),comboBoxExDay.getSelectedItem().toString(),chckCondition.isSelected()).startWrite();
						}
						//then write to excel file
						
					}
				}
				//fileChooser.setDialogTitle("Specify a file to save");
				
			//	File f = chooser.getCurrentDirectory();
			//	String fn = f.getAbsolutePath();
				
			/*	
				int userSelection = fileChooser.showSaveDialog();
				 
				if (userSelection == JFileChooser.APPROVE_OPTION) {
				    File fileToSave = fileChooser.getSelectedFile();
				    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
				}
				*/
			}
		});
		btnExport.setBounds(423, 292, 140, 41);
		Export.add(btnExport);
		
		comboBoxExTerm = new JComboBox<String>();
		comboBoxExTerm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshExTableTerm();
			}
		});
//		comboBoxExTerm.setModel(new DefaultComboBoxModel<String>(getTables("terms")));
		comboBoxExTerm.setBounds(981, 89, 175, 23);
		Export.add(comboBoxExTerm);
		
		JLabel lblSelectLocation = new JLabel("Select Location:");
		lblSelectLocation.setBounds(863, 123, 108, 23);
		Export.add(lblSelectLocation);
		
		comboBoxExLoc = new JComboBox<String>();
		comboBoxExLoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshExTableTerm();
			}
		});
		comboBoxExLoc.setBounds(981, 124, 175, 23);
		Export.add(comboBoxExLoc);
		
		
		comboBoxExDay = new JComboBox<String>();
		comboBoxExDay.setModel(new DefaultComboBoxModel<String>(new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}));
		comboBoxExDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshExTableTerm();
			}
		});
		comboBoxExDay.setBounds(982, 157, 120, 23);
		Export.add(comboBoxExDay);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(106, 92, 747, 176);
		Export.add(scrollPane_2);
		
		tableExport = new JTable();
		scrollPane_2.setViewportView(tableExport);
		
		JLabel lblSelectTerm_1 = new JLabel("Select Term:");
		lblSelectTerm_1.setBounds(863, 89, 108, 23);
		Export.add(lblSelectTerm_1);
		
		JLabel lblSelectDay = new JLabel("Select Day:");
		lblSelectDay.setBounds(863, 158, 108, 23);
		Export.add(lblSelectDay);
		
		JSeparator separator_7 = new JSeparator();
		separator_7.setBounds(113, 378, 1043, 2);
		Export.add(separator_7);
		
		JButton btnExportSqliteFile = new JButton("Export SQLite File");
		btnExportSqliteFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("Save DataBase File");
				chooser.setFileFilter(new FileTypeFilter(".sqlite","SQLite File"));
				int ans = chooser.showSaveDialog(null);
					if(ans==JFileChooser.APPROVE_OPTION){
						File file = chooser.getSelectedFile();
						if(!file.getName().endsWith(".sqlite")){
							file = new File(file.getAbsolutePath()+".sqlite");
						}
						//ClassLoader c1 = sqliteConnection.class.getClassLoader();
						
						//String workingDir = c1.getResource("chacoswim.sqlite").toString().substring(5);
						
						//File src =new File(workingDir);
						try {
							//Files.copy(src.toString(), file.toPath(),REPLACE_EXISTING);
							//InputStream is = c1.getResourceAsStream("chacoswim.sqlite");
							
							FileOutputStream os = new FileOutputStream(file);
							FileInputStream is = new FileInputStream("DontTouchMe.sqlite");
							byte[] buffer = new byte[1024];
							int bytesRead;
							//read from is to buffer
							while((bytesRead = is.read(buffer))!=-1){
								os.write(buffer, 0, bytesRead);
							}
							is.close();
							os.flush();
							os.close();
							
							JOptionPane.showMessageDialog(null, "Finished");
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
			}
		});
		btnExportSqliteFile.setForeground(Color.RED);
		btnExportSqliteFile.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnExportSqliteFile.setBounds(173, 441, 153, 33);
		Export.add(btnExportSqliteFile);
		
		JButton btnImportSqliteFile = new JButton("Import SQLite File");
		btnImportSqliteFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(new FileTypeFilter(".sqlite","SQLite File"));
				chooser.setDialogTitle("Select a Backed up SQLite File");
				int ans = chooser.showOpenDialog(null);
				if(ans==JFileChooser.APPROVE_OPTION){
					File file = chooser.getSelectedFile();
					if(!file.getName().endsWith(".sqlite")){
						JOptionPane.showMessageDialog(null, "Not a correct sqlite file");
					}
					else{
						//ClassLoader c1 = sqliteConnection.class.getClassLoader();
						//URL in = c1.getResource("chacoswim.sqlite");
						
								//c1.getResourceAsStream("chacoswim.sqlite");
						
						//String workingDir = in.getPath();
						File des = new File("DontTouchMe.sqlite");
						int b = JOptionPane.showConfirmDialog(null, "Do you Really Want to Restore Database?","Restore database",JOptionPane.OK_CANCEL_OPTION);
						if(b==JOptionPane.OK_OPTION){
							
							try {
								
								
								FileOutputStream os = new FileOutputStream(des);
								FileInputStream is = new FileInputStream(file);
								byte[] buffer = new byte[1024];
								int bytesRead;
								//read from is to buffer
								while((bytesRead = is.read(buffer))!=-1){
									os.write(buffer, 0, bytesRead);
								}
								is.close();
								os.flush();
								os.close();
								
								comboBoxTerm.setModel(new DefaultComboBoxModel<String>(getTables("terms")));
								comboBoxEmailTerm.setModel(new DefaultComboBoxModel<String>(getTables("terms")));
								comboBoxExTerm.setModel(new DefaultComboBoxModel<String>(getTables("terms")));
								comboBoxStaCoach.setModel(new DefaultComboBoxModel<String>(new StatisticMethod().getCoaches()));
								new TabCoaches(jtCoaches).refreshTable();
								JOptionPane.showMessageDialog(null, "Finished");
								
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
						
					}
				}
				
			}
		});
		btnImportSqliteFile.setForeground(SystemColor.textHighlight);
		btnImportSqliteFile.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnImportSqliteFile.setBounds(646, 441, 153, 33);
		Export.add(btnImportSqliteFile);
		
		JLabel lblSchedule = new JLabel("Schedule: ");
		lblSchedule.setBounds(863, 193, 108, 16);
		Export.add(lblSchedule);
		
		chckCondition = new JCheckBox("Empty Class Only");
		chckCondition.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				refreshExTableTerm();
				
			}
			
		});
		chckCondition.setBounds(981, 192, 175, 23);
		Export.add(chckCondition);
		
		
		
		JPanel SettingTab = new JPanel();
		tabbedPane.addTab("Settings", null, SettingTab, null);
		SettingTab.setLayout(null);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.15);
		splitPane.setBounds(0, 0, 1269, 701);
		SettingTab.add(splitPane);
		
		JList list = new JList();
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Terms", "Locations"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		list.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				String str = list.getSelectedValue().toString();
				switch(str){
				case "Terms":
					/* Set right pane for terms */
					splitPane.setRightComponent(new SettingPane(ChacoSwim.this).getTerms());
					break;
				case "Locations":
					splitPane.setRightComponent(new SettingPane(ChacoSwim.this).getLocations());
					break;
				default:
					break;
				}
			}
			
		});
		
		splitPane.setLeftComponent(list);
		splitPane.setRightComponent(new SettingPane(this).getTerms());
	
		
	}
	/**
	 * TODO SchedulePanels
	 * @author wenzhongzheng
	 *
	 */
	public class SchedulePanels{
		private JPanel panel;
		private JComboBox<String> cbschLine2;
		private JTextField tfschLine2;
		
		private JComboBox<String> cbschlevel;
		private JTextField tfschlevel;
		public SchedulePanels(JPanel panel){
			this.panel = panel;
			initialPanel();
		}
		
		public JTextField gettfschlevel() {
			return tfschlevel;
		}
		public JTextField gettfschLine2() {
			return tfschLine2;
		}
		public JComboBox<String> getComboBox(){
			return cbschLine2;
		}
		public JComboBox<String> getComboBoxLevel(){
			return cbschlevel;
		}
		private void initialPanel(){
			//ROW #1
			JLabel schlbl1 = new JLabel("Coach: ");
			panel.add(schlbl1,"sg line1");
			
			cbschLine2 = new JComboBox<String>();
			panel.add(cbschLine2,"growx,wrap");
			
			JLabel schlbl2 = new JLabel("Selected Coach(es): ");
			panel.add(schlbl2,"sg line1");
			
			tfschLine2 = new JTextField();
			tfschLine2.setEditable(false);
			panel.add(tfschLine2,"growx,width 80:150:,wrap");
			
			//ROW #2
			JButton schLine2Add = new JButton("Add");
			schLine2Add.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					if(tfschLine2.getText().isEmpty())
						tfschLine2.setText(cbschLine2.getSelectedItem().toString());
//					else if(!tfschLine2.getText().contains((cbschLine2.getSelectedItem().toString())))
					else
						tfschLine2.setText(tfschLine2.getText()+","+cbschLine2.getSelectedItem().toString());
//					else
//						JOptionPane.showMessageDialog(null, cbschLine2.getSelectedItem().toString()+" already exist");
				}
			});
			panel.add(schLine2Add,"sg line1btn,width 30:80:,span,split 2");
			JButton schLine2Del = new JButton("Clear");
			schLine2Del.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					tfschLine2.setText("");
				}
			});
			panel.add(schLine2Del,"sg line1btn,width 30:80:,gapleft 15");
			
			//ROW #3
			JLabel schlbl3 = new JLabel("Level: ");
			panel.add(schlbl3,"sg line1");
			
			cbschlevel = new JComboBox<String>();
			panel.add(cbschlevel,"growx,wrap");
			
			JLabel schlbl4 = new JLabel("Selected Level(s): ");
			panel.add(schlbl4,"sg line1");
			
			tfschlevel = new JTextField();
			tfschlevel.setEditable(false);
			panel.add(tfschlevel,"growx,width 80:150:,wrap");
			
			//ROW #4
			JButton schlevelAdd = new JButton("Add");
			schlevelAdd.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					if(tfschlevel.getText().isEmpty())
						tfschlevel.setText(cbschlevel.getSelectedItem().toString());
//					else if(!tfschlevel.getText().contains((cbschlevel.getSelectedItem().toString())))
					else
						tfschlevel.setText(tfschlevel.getText()+","+cbschlevel.getSelectedItem().toString());
//					else
//						JOptionPane.showMessageDialog(null, cbschlevel.getSelectedItem().toString()+" already exist");
				}
			});
			panel.add(schlevelAdd,"sg line1btn,width 30:80:,span,split 2");
			JButton schlevelDel = new JButton("Clear");
			schlevelDel.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					tfschlevel.setText("");
				}
			});
			panel.add(schlevelDel,"sg line1btn,width 30:80:,gapleft 15");
		}
	}
}
