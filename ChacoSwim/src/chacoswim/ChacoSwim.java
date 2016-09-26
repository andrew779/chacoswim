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
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

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
import net.miginfocom.swing.MigLayout;

public class ChacoSwim extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private Connection conn=null;
	private JComboBox<String> comboBoxtable;
	private JComboBox<Object> comboBoxTerm;
	JComboBox<String> comboBoxEmailDay;
	JComboBox<String> comboBoxEmailTerm;
	JComboBox<String> comboBoxExDay;
	private JComboBox<Object> comboBoxdays;
	JComboBox<String> comboBoxExTerm;
	private JTextField tfSearch;
	//private String SID_="";
	private JTextArea taInfo;
	JLabel lblL1,lblL2,lblL3,lblL4,lblL5,lblL6,lblL7,lblL8,lblSplash,lblCrashfront,lblCrashbreast,lblAdult,lblTeam;
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
	private JComboBox<String> comboBoxStaTerm;
	private JButton btnMonday;
	private JButton btnTuesday;
	private JButton btnWednesday;
	private JButton btnThursday;
	private JButton btnFriday;
	private JButton btnSaturday;
	private JButton btnSunday;
	private JLabel lblTotalreg;
	private JPanel StatisticsTab;
	private JList<String> listCoach;
	private JTable StaticTerm_Day;
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
	/**
	 * Launch the application.
	 */
	public static void main(Connection connection) {
		EventQueue.invokeLater(new Runnable() {
			

			public void run() {
				try {
					frame = new ChacoSwim();
					frame.setVisible(true);
					Image imglogo=new ImageIcon(this.getClass().getResource("/logo24.png")).getImage();
					frame.setIconImage(imglogo);
					frame.setTitle("Chaco Swim Club");
					//frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void resizeColumnWidth(JTable table) {
	    final TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 0; column < table.getColumnCount(); column++) {
	        int width = 100; // Min width
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
				query = "Select name from "+key;
				pst=conn.prepareStatement(query);
				rs=pst.executeQuery();
				while(rs.next()){
					list.add(0,rs.getString("name"));
				}
				String[] str= new String[list.size()];
				list.toArray(str);
				pst.close();
				rs.close();
				return str;
			case "location":
				query = "Select name from "+key;
				pst=conn.prepareStatement(query);
				rs=pst.executeQuery();
				while(rs.next()){
					list.add(0,rs.getString("name"));
				}
				String[] location= new String[list.size()];
				list.toArray(location);
				pst.close();
				rs.close();
				return location;
			}//switch
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	//refreshing two tables----------------------------------------
	public void refreshTable(){
		try{
			String query="";
			if(tglbtnSchedule.isSelected())
			query="select * from students where sid < 16";
			else query="select * from students where sid>15";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			pst.close();
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void refreshExTableTerm(){
		ResultSet rs=null;
		try{
			String term = comboBoxExTerm.getSelectedItem().toString();
			String day = comboBoxExDay.getSelectedItem().toString();
			String query="";
			if(day.equals("All")){
				query = "select t.ID, t.SID, s.FirstName, s.LastName, s.CurLevel, t.Day, t.Time, t.Line, t.Coach from '"+term+"' t JOIN students s on s.sid = t.sid ORDER BY t.day, t.time, t.line, s.CurLevel";
			}
			else{
				query = "select t.sid, s.FirstName, s.LastName, s.CurLevel, t.day, t.time, t.line, t.coach from '"+term+"' t JOIN students s on s.sid = t.sid WHERE t.day = '"+day+ 
					"' ORDER BY t.day, t.time, t.line, s.CurLevel";
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
	
	public void refreshTableTerm(){
		ResultSet rs=null;
		try{
			DataBaseManage dbm = new DataBaseManage();
			String termId = dbm.gotId("terms", comboBoxTerm.getSelectedItem().toString());
			String locationId = dbm.gotId("location",comboBoxLocation.getSelectedItem().toString());
			String day = comboBoxdays.getSelectedItem().toString();
			String query="";
			if(day.equals("All")){
				query = "select * from active_record where termID = ? AND locationID = ?";
				
			}
			else{
				query = "select * from active_record where termID = ? AND locationID = ? AND day = "+day;
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
	
	public void seatsLeft(){
		String term = comboBoxTerm.getSelectedItem().toString();
		String day = comboBoxdays.getSelectedItem().toString();
		int l1=0,l2=0,l3=0,l4=0,l5=0,l6=0,l7=0,l8=0,splash=0,crash_front=0,crash_breast=0,team=0,adult=0;
		StringBuilder lvl01 = new StringBuilder("--------"+day+"------\n");
		StringBuilder lvl0 = new StringBuilder("Level 0:\n");
		StringBuilder lvl1 = new StringBuilder("Level 1:\n");
		StringBuilder lvl2 = new StringBuilder("Level 2:\n");
		StringBuilder lvl3 = new StringBuilder("Level 3:\n");
		StringBuilder lvl4 = new StringBuilder("Level 4:\n");
		StringBuilder lvl5 = new StringBuilder("Level 5:\n");
		StringBuilder lvl6 = new StringBuilder("Level 6:\n");
		StringBuilder lvl7 = new StringBuilder("Level 7:\n");
		StringBuilder lvl8 = new StringBuilder("Level 8:\n");
		StringBuilder lvl9 = new StringBuilder("Level Splash:\n");
		StringBuilder lvl10 = new StringBuilder("Level Crash_Front:\n");
		StringBuilder lvl11 = new StringBuilder("Level Crash_Breast:\n");
		StringBuilder lvl12 = new StringBuilder("Level Adult:\n");
		StringBuilder lvl13 = new StringBuilder("Level Team:\n");
		try {
			String query = "select t.sid, s.FirstName, s.LastName, s.CurLevel, t.day, t.time, t.line, t.coach from '"+term+"' t JOIN students s on s.sid = t.sid WHERE t.day = '"+day+ 
					"' ORDER BY t.day, t.time";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				switch(rs.getString("CurLevel")){
				case "1_Swimmer 1": 
					l1++;
					lvl1.append("      "+rs.getString("sid")+"  "+rs.getString("FirstName")+"  "+rs.getString("LastName")+"\n");
					break;
				case "2_Swimmer 2": 
					l2++;
					lvl2.append("      "+rs.getString("sid")+"  "+rs.getString("FirstName")+" "+rs.getString("LastName")+"\n");
					break;
				case "3_Swimmer 3": 
					l3++;
					lvl3.append("      "+rs.getString("sid")+"  "+rs.getString("FirstName")+" "+rs.getString("LastName")+"\n");
					break;
				case "4_Swimmer 4": 
					l4++;
					lvl4.append("      "+rs.getString("sid")+"  "+rs.getString("FirstName")+" "+rs.getString("LastName")+"\n");
					break;
				case "5_Swimmer 5": 
					l5++;
					lvl5.append("      "+rs.getString("sid")+"  "+rs.getString("FirstName")+" "+rs.getString("LastName")+"\n");
					break;
				case "6_Swimmer 6": 
					l6++;
					lvl6.append("      "+rs.getString("sid")+"  "+rs.getString("FirstName")+" "+rs.getString("LastName")+"\n");
					break;
				case "7_Swimmer 7": 
					l7++;
					lvl7.append("      "+rs.getString("sid")+"  "+rs.getString("FirstName")+" "+rs.getString("LastName")+"\n");
					break;
				case "8_Swimmer 8": 
					l8++;
					lvl8.append("      "+rs.getString("sid")+"  "+rs.getString("FirstName")+" "+rs.getString("LastName")+"\n");
					break;
				case "0_Swimmer 0":
					lvl0.append("      "+rs.getString("sid")+"  "+rs.getString("FirstName")+" "+rs.getString("LastName")+"\n");
					break;
				case "9_Splash":
					splash++;
					lvl9.append("      "+rs.getString("sid")+"  "+rs.getString("FirstName")+" "+rs.getString("LastName")+"\n");
					break;
				case "10_Crash-Front":
					crash_front++;
					lvl10.append("     "+rs.getString("sid")+"  "+rs.getString("FirstName")+" "+rs.getString("LastName")+"\n");
					break;
				case "11_Crash-Breast":
					crash_breast++;
					lvl11.append("     "+rs.getString("sid")+"  "+rs.getString("FirstName")+" "+rs.getString("LastName")+"\n");
					break;
				case "12_Adult":
					adult++;
					lvl12.append("     "+rs.getString("sid")+"  "+rs.getString("FirstName")+" "+rs.getString("LastName")+"\n");
					break;
				case "13_Team":
					team++;
					lvl13.append("     "+rs.getString("sid")+"  "+rs.getString("FirstName")+" "+rs.getString("LastName")+"\n");
					break;
				default:
					break;				
				}//switch
			}//while
			lvl01.append(lvl0);
			lvl01.append(lvl1);
			lvl01.append(lvl2);
			lvl01.append(lvl3);
			lvl01.append(lvl4);
			lvl01.append(lvl5);
			lvl01.append(lvl6);
			lvl01.append(lvl7);
			lvl01.append(lvl8);lvl01.append(lvl9);lvl01.append(lvl10);lvl01.append(lvl11);lvl01.append(lvl12);lvl01.append(lvl13);
			taInfo.setText(lvl01.toString());
			
			String warn="Exceed Class, and you won't be able to export an EXCEL File";
			if(splash>=4){if(splash>6){JOptionPane.showMessageDialog(null, warn);}lblSplash.setText("Splash: "+splash);	newFont(lblSplash);}
			else{lblSplash.setText("Splash: "+splash); oldFont(lblSplash);}
			
			if(crash_front>=4){if(crash_front>6){JOptionPane.showMessageDialog(null, warn);}lblCrashfront.setText("Crashfront: "+crash_front);newFont(lblCrashfront);}
			else{lblCrashfront.setText("Crashfront: "+crash_front); oldFont(lblCrashfront);}
			
			if(crash_breast>=4){if(crash_breast>6){JOptionPane.showMessageDialog(null, warn);}lblCrashbreast.setText("Crashbreast: "+crash_breast);newFont(lblCrashbreast);}
			else{lblCrashbreast.setText("Crashbreast: "+crash_breast); oldFont(lblCrashbreast);}
			
			if(adult>=4){if(adult>6){JOptionPane.showMessageDialog(null, warn);}lblAdult.setText("Adult: "+adult);newFont(lblAdult);}
			else{lblAdult.setText("Adult: "+adult); oldFont(lblAdult);}
			
			if(team>=4){if(team>6){JOptionPane.showMessageDialog(null, warn);}lblTeam.setText("Team: "+team);newFont(lblTeam);}
			else{lblTeam.setText("Team: "+team); oldFont(lblTeam);}
			
			if(l1>=4){if(l1>6){JOptionPane.showMessageDialog(null, warn);}lblL1.setText("L1: "+l1);	newFont(lblL1);}
			else{lblL1.setText("L1: "+l1); oldFont(lblL1);}
			
			if(l2>=4){if(l2>6){JOptionPane.showMessageDialog(null, warn);}lblL2.setText("L2: "+l2);	newFont(lblL2);}
			else{lblL2.setText("L2: "+l2); oldFont(lblL2);}
			
			if(l3>=4){if(l3>6){JOptionPane.showMessageDialog(null, warn);}lblL3.setText("L3: "+l3);	newFont(lblL3);}
			else{lblL3.setText("L3: "+l3); oldFont(lblL3);}

			if(l4>=4){if(l4>6){JOptionPane.showMessageDialog(null, warn);}lblL4.setText("L4: "+l4);	newFont(lblL4);}
			else{lblL4.setText("L4: "+l4); oldFont(lblL4);}
			
			if(l5>=4){if(l5>6){JOptionPane.showMessageDialog(null, warn);}lblL5.setText("L5: "+l5);	newFont(lblL5);}
			else{lblL5.setText("L5: "+l5); oldFont(lblL5);}
			
			if(l6>=4){if(l6>6){JOptionPane.showMessageDialog(null, warn);}lblL6.setText("L6: "+l6);	newFont(lblL6);}
			else{lblL6.setText("L6: "+l6); oldFont(lblL6);}
			
			if(l7>=4){if(l7>6){JOptionPane.showMessageDialog(null, warn);}lblL7.setText("L7: "+l7);	newFont(lblL7);}
			else{lblL7.setText("L7: "+l7); oldFont(lblL7);}
			
			if(l8>=4){if(l8>6){JOptionPane.showMessageDialog(null, warn);}lblL8.setText("L8: "+l8);	newFont(lblL8);}
			else{lblL8.setText("L8: "+l8); oldFont(lblL8);}
			
			pst.close();
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Line 418: "+e);
		}
	}
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
			else refreshTable();
			
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
			// TODO Auto-generated catch block
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
				if(day.equals("All"))query="select s.email from '"+term+"' t JOIN students s ON s.sid=t.sid";
				else query="select s.email from '"+term+"' t JOIN students s ON s.sid=t.sid WHERE t.day='"+day+"' and s.email LIKE '%@%'";
				pst=conn.prepareStatement(query);
				rs=pst.executeQuery();
				while(rs.next())address.add(rs.getString("email"));
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
				refreshTableTerm();
				seatsLeft();
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
					CourseModification.main(new String[]{"add",id,comboBoxTerm.getSelectedItem().toString()},btnRefreshAll);
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
				int a = JOptionPane.showConfirmDialog(null, "Do You Really Want to Delete the Selected Record?","Delete",JOptionPane.YES_NO_OPTION);
				if (a==JOptionPane.YES_OPTION){
					deleteStudent();
					refreshTable();
				}
			}
		});
		btnDeleteStudent.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/24delete.png")).getImage()));
		btnDeleteStudent.setBounds(149, 23, 129, 25);
		panel.add(btnDeleteStudent);
		
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
		resizeColumnWidth(table);
		
		
		comboBoxTerm = new JComboBox<Object>();
		comboBoxTerm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshTableTerm();
			}
		});
		comboBoxTerm.setModel(new DefaultComboBoxModel<Object>(getTables("terms")));
		comboBoxTerm.setBounds(20, 314, 119, 20);
		
		panel.add(comboBoxTerm);
		
		JLabel lblSelectTerm = new JLabel("Term:");
		lblSelectTerm.setBounds(20, 290, 117, 20);
		panel.add(lblSelectTerm);
		
		JLabel lblCourseInfo = new JLabel("Course Info: ");
		lblCourseInfo.setBounds(20, 356, 117, 20);
		panel.add(lblCourseInfo);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(968, 280, 234, 295);
		panel.add(scrollPane_3);
		
		taInfo = new JTextArea();
		taInfo.setEditable(false);
		scrollPane_3.setViewportView(taInfo);
		
		lblL1 = new JLabel("L1:");
		lblL1.setForeground(Color.BLACK);
		lblL1.setBounds(796, 316, 64, 17);
		panel.add(lblL1);
		
		lblL2 = new JLabel("L2:");
		lblL2.setBounds(854, 316, 64, 17);
		panel.add(lblL2);
		
		lblL3 = new JLabel("L3:");
		lblL3.setBounds(909, 316, 64, 17);
		panel.add(lblL3);
		
		lblL4 = new JLabel("L4:");
		lblL4.setBounds(796, 344, 64, 17);
		panel.add(lblL4);
		
		lblL5 = new JLabel("L5:");
		lblL5.setBounds(854, 344, 64, 17);
		panel.add(lblL5);
		
		lblL6 = new JLabel("L6:");
		lblL6.setBounds(909, 344, 64, 17);
		panel.add(lblL6);
		
		lblL7 = new JLabel("L7:");
		lblL7.setBounds(796, 372, 64, 17);
		panel.add(lblL7);
		
		lblL8 = new JLabel("L8:");
		lblL8.setBounds(854, 372, 64, 17);
		panel.add(lblL8);
		
		lblSplash = new JLabel("Splash:");
		lblSplash.setForeground(Color.BLACK);
		lblSplash.setBounds(796, 400, 64, 17);
		panel.add(lblSplash);
		
		lblCrashfront = new JLabel("Crash_Front:");
		lblCrashfront.setForeground(Color.BLACK);
		lblCrashfront.setBounds(796, 430, 105, 17);
		panel.add(lblCrashfront);
		
		lblCrashbreast = new JLabel("Crash_Breast:");
		lblCrashbreast.setForeground(Color.BLACK);
		lblCrashbreast.setBounds(796, 458, 107, 17);
		panel.add(lblCrashbreast);
		
		lblAdult = new JLabel("Adult:");
		lblAdult.setForeground(Color.BLACK);
		lblAdult.setBounds(882, 400, 64, 17);
		panel.add(lblAdult);
		
		lblTeam = new JLabel("Team:");
		lblTeam.setForeground(Color.BLACK);
		lblTeam.setBounds(796, 486, 64, 17);
		panel.add(lblTeam);
		
		
		
		JLabel lblSeatsRemaining = new JLabel("Seats Info:");
		lblSeatsRemaining.setBounds(796, 286, 100, 14);
		panel.add(lblSeatsRemaining);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(20, 380, 769, 192);
		panel.add(scrollPane_1);
		
		tableTerm = new JTable();
		scrollPane_1.setViewportView(tableTerm);
		//tableTerm.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//resizeColumnWidth(tableTerm);
		
		JLabel lblByDay = new JLabel("Day:");
		lblByDay.setBounds(335, 293, 46, 14);
		panel.add(lblByDay);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = tableTerm.getSelectedRow();
				if(row!=-1){
					String id = tableTerm.getModel().getValueAt(row, 0).toString();
					CourseModification.main(new String[]{"update",id,comboBoxTerm.getSelectedItem().toString()},btnRefreshAll);
				}
				else{
					JOptionPane.showMessageDialog(null, "Please select a record from course table first");
				}
			}
		});
		
		btnEdit.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/24magicwand.png")).getImage()));
		btnEdit.setBounds(248, 583, 119, 30);
		panel.add(btnEdit);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/24delete.png")).getImage()));
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String term = comboBoxTerm.getSelectedItem().toString();
				String query = "DELETE FROM '"+term+"' WHERE ID = ?;";
				int row = tableTerm.getSelectedRow();
				String id = tableTerm.getModel().getValueAt(row, 0).toString();
				int ans = JOptionPane.showConfirmDialog(null, "Do you really want to delete selected record","Deleting",JOptionPane.YES_NO_OPTION);
				if(ans==JOptionPane.YES_OPTION){
					try {
						PreparedStatement pst = conn.prepareStatement(query);
						pst.setString(1, id);
						pst.execute();
						pst.close();
						JOptionPane.showMessageDialog(null, "Deleted");
						btnRefreshAll.doClick();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null,"Line912: "+ e);
					}
				
				}
				
			}
		});
		btnDelete.setBounds(422, 583, 119, 30);
		panel.add(btnDelete);
		
		btnRefreshAll = new JButton("Refresh All");
		btnRefreshAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshTable();
				refreshTableTerm();
				seatsLeft();
			}
		});
		
		Image img=new ImageIcon(this.getClass().getResource("/refresh-all.png")).getImage();
		btnRefreshAll.setIcon(new ImageIcon(img));
		btnRefreshAll.setBounds(625, 297, 145, 37);
		panel.add(btnRefreshAll);
		
		tglbtnSchedule = new JToggleButton("Schedule");
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
				
				refreshTable();
			}
		});
		tglbtnSchedule.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/32megaphone2.png")).getImage()));
		tglbtnSchedule.setBounds(682, 11, 107, 35);
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
		comboBoxLocation.setModel(new DefaultComboBoxModel<String>(getTables("location")));
		//TODO
		comboBoxLocation.setBounds(176, 314, 129, 20);
		panel.add(comboBoxLocation);
		
		
//Start Tab Coach~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		JPanel CoachTab = new JPanel();
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
				
/* Gmail
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.socketFactory.port", "465");
				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.port", "465");
				
*/
				props.setProperty("mail.transport.protocol", "stmp");
				props.setProperty("mail.host", "smtp.live.com");
			    props.put("mail.smtp.starttls.enable", "true");
			    props.put("mail.smtp.auth", "true");
			    props.put("mail.smtp.port", "587");
			    
				Session session = Session.getDefaultInstance(props,
						new javax.mail.Authenticator(){
							protected PasswordAuthentication getPasswordAuthentication(){
								//return new PasswordAuthentication("andrew198712@gmail.com","zz208516");
								return new PasswordAuthentication("wenzhong.zheng@hotmail.com","208516Zz");
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
							addressString=addressString+","+i;
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
					
					JOptionPane.showMessageDialog(null, "This process may take up to a few minutes depends on the number of recipients,Please be patient.");
					Transport.send(message);
					JOptionPane.showMessageDialog(null, "Message sent");
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
		rdbtnMultipleRecipients.setBounds(303, 20, 138, 23);
		EmailTab.add(rdbtnMultipleRecipients);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(69, 50, 484, 2);
		EmailTab.add(separator_1);
		
		rdbtnAllCoaches = new JRadioButton("All Coaches");
		rdbtnAllCoaches.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBoxEmailTerm.setEnabled(false);
				comboBoxEmailDay.setEnabled(false);
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
			}
		});
		buttonGroup_1.add(rdbtnStudents);
		rdbtnStudents.setBounds(303, 91, 138, 23);
		EmailTab.add(rdbtnStudents);
		
		JLabel lblsec2 = new JLabel("When multiple recipients applied: ");
		lblsec2.setBounds(79, 63, 196, 14);
		EmailTab.add(lblsec2);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(69, 121, 484, 2);
		EmailTab.add(separator_2);
		
		JLabel lblWhenStudentsApplied = new JLabel("When students applied: ");
		lblWhenStudentsApplied.setBounds(79, 134, 196, 14);
		EmailTab.add(lblWhenStudentsApplied);
		
		comboBoxEmailTerm = new JComboBox<String>();
		comboBoxEmailTerm.setBounds(217, 159, 183, 23);
		comboBoxEmailTerm.setModel(new DefaultComboBoxModel<String>(getTables("terms")));
		EmailTab.add(comboBoxEmailTerm);
		
		comboBoxEmailDay = new JComboBox<String>();
		comboBoxEmailDay.setModel(new DefaultComboBoxModel<String>(new String[] {"All", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}));
		comboBoxEmailDay.setBounds(217, 207, 120, 23);
		EmailTab.add(comboBoxEmailDay);
		
		JLabel lblSelectTerm_2 = new JLabel("Select Term: ");
		lblSelectTerm_2.setBounds(79, 163, 128, 14);
		EmailTab.add(lblSelectTerm_2);
		
		JLabel lblSelectDay_1 = new JLabel("Select Day: ");
		lblSelectDay_1.setBounds(79, 211, 128, 14);
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
		lblAttachmentManage.setBounds(79, 265, 128, 14);
		EmailTab.add(lblAttachmentManage);
		
		rdbtnStudents.setEnabled(false);
		rdbtnAllCoaches.setEnabled(false);
		comboBoxEmailTerm.setEnabled(false);
		comboBoxEmailDay.setEnabled(false);
		
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
		lblOrYouCould.setBounds(80, 537, 320, 29);
		EmailTab.add(lblOrYouCould);
		
		JSeparator separator_6 = new JSeparator();
		separator_6.setBounds(69, 524, 484, 2);
		EmailTab.add(separator_6);
		
		
		//Statistics Part
		
		
		StatisticsTab = new JPanel();
		tabbedPane.addTab("Statistics", null, StatisticsTab, null);
		StatisticsTab.setLayout(null);
		
		JLabel lblTotalRegistedStudents = new JLabel("Total Registed Students:");
		lblTotalRegistedStudents.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTotalRegistedStudents.setBounds(49, 49, 215, 23);
		StatisticsTab.add(lblTotalRegistedStudents);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(10, 98, 1043, 2);
		StatisticsTab.add(separator_4);
		
		JLabel lblStudentsNumberBased = new JLabel("Students number based on term: ");
		lblStudentsNumberBased.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblStudentsNumberBased.setBounds(49, 128, 251, 23);
		StatisticsTab.add(lblStudentsNumberBased);
		
		comboBoxStaTerm = new JComboBox<String>();
		comboBoxStaTerm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StatisticMethod sm = new StatisticMethod(comboBoxStaTerm.getSelectedItem().toString());
				btnMonday.setText(sm.getDays("Monday"));
				btnTuesday.setText(sm.getDays("Tuesday"));
				btnWednesday.setText(sm.getDays("Wednesday"));
				btnThursday.setText(sm.getDays("Thursday"));
				btnFriday.setText(sm.getDays("Friday"));
				btnSaturday.setText(sm.getDays("Saturday"));
				btnSunday.setText(sm.getDays("Sunday"));
			}
		});
		comboBoxStaTerm.setBounds(311, 129, 178, 23);
		comboBoxStaTerm.setModel(new DefaultComboBoxModel<String>(getTables("terms")));
		StatisticsTab.add(comboBoxStaTerm);
		
		btnMonday = new JButton("Monday:");
		btnMonday.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new StatisticMethod().fillTerm_Day(StaticTerm_Day, comboBoxStaTerm.getSelectedItem().toString(), "Monday");
			}
		});
		btnMonday.setBounds(49, 185, 129, 23);
		StatisticsTab.add(btnMonday);
		
		btnTuesday = new JButton("Tuesday:");
		btnTuesday.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new StatisticMethod().fillTerm_Day(StaticTerm_Day, comboBoxStaTerm.getSelectedItem().toString(), "Tuesday");
			}
		});
		btnTuesday.setBounds(202, 185, 129, 23);
		StatisticsTab.add(btnTuesday);
		
		btnWednesday = new JButton("Wednesday:");
		btnWednesday.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new StatisticMethod().fillTerm_Day(StaticTerm_Day, comboBoxStaTerm.getSelectedItem().toString(), "Wednesday");
			}
		});
		btnWednesday.setBounds(360, 185, 129, 23);
		StatisticsTab.add(btnWednesday);
		
		btnThursday = new JButton("Thursday:");
		btnThursday.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new StatisticMethod().fillTerm_Day(StaticTerm_Day, comboBoxStaTerm.getSelectedItem().toString(), "Thursday");
			}
		});
		btnThursday.setBounds(49, 237, 129, 23);
		StatisticsTab.add(btnThursday);
		
		btnFriday = new JButton("Friday:");
		btnFriday.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new StatisticMethod().fillTerm_Day(StaticTerm_Day, comboBoxStaTerm.getSelectedItem().toString(), "Friday");
			}
		});
		btnFriday.setBounds(202, 237, 129, 23);
		StatisticsTab.add(btnFriday);
		
		btnSaturday = new JButton("Saturday");
		btnSaturday.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new StatisticMethod().fillTerm_Day(StaticTerm_Day, comboBoxStaTerm.getSelectedItem().toString(), "Saturday");
			}
		});
		btnSaturday.setBounds(360, 237, 129, 23);
		StatisticsTab.add(btnSaturday);
		

		btnSunday = new JButton("Sunday:");
		btnSunday.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new StatisticMethod().fillTerm_Day(StaticTerm_Day, comboBoxStaTerm.getSelectedItem().toString(), "Sunday");
			}
		});
		btnSunday.setBounds(49, 282, 129, 23);
		StatisticsTab.add(btnSunday);
		
		JLabel lblStudentsNumberBased_1 = new JLabel("Students number based on term and coach: ");
		lblStudentsNumberBased_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblStudentsNumberBased_1.setBounds(49, 340, 294, 23);
		StatisticsTab.add(lblStudentsNumberBased_1);
		
		comboBoxStaCoach = new JComboBox<String>();
		comboBoxStaCoach.setModel(new DefaultComboBoxModel<String>(new StatisticMethod().getCoaches()));
		comboBoxStaCoach.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel<String> listCoachModel = new DefaultListModel<String>();
				String[] coachListData = new StatisticMethod().getCoaches_Term(comboBoxStaCoach.getSelectedItem().toString());
				for(String i:coachListData){
					listCoachModel.addElement(i);
					listCoach.setModel(listCoachModel);
				}
				
			}
		});
		comboBoxStaCoach.setBounds(341, 340, 148, 23);
		StatisticsTab.add(comboBoxStaCoach);
		
		JScrollPane scrollPane_6 = new JScrollPane();
		scrollPane_6.setBounds(49, 380, 440, 173);
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
		
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(10, 327, 1043, 2);
		StatisticsTab.add(separator_5);
		
		JLabel lblTermday = new JLabel("Term&Day:");
		lblTermday.setBounds(578, 111, 86, 14);
		StatisticsTab.add(lblTermday);
		
		JScrollPane scrollPane_7 = new JScrollPane();
		scrollPane_7.setBounds(580, 133, 473, 181);
		StatisticsTab.add(scrollPane_7);
		
		StaticTerm_Day = new JTable();
		scrollPane_7.setViewportView(StaticTerm_Day);
		
		JLabel lblTermcoach = new JLabel("Term&Coach:");
		lblTermcoach.setBounds(578, 345, 86, 14);
		StatisticsTab.add(lblTermcoach);
		
		JScrollPane scrollPane_8 = new JScrollPane();
		scrollPane_8.setBounds(578, 370, 473, 181);
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
							new WriteToExcel(file,comboBoxExTerm.getSelectedItem().toString(),comboBoxExDay.getSelectedItem().toString()).startWrite();
						}
						else{
							File file1 = new File(file.getAbsolutePath()+".xlsx");
							new WriteToExcel(file1,comboBoxExTerm.getSelectedItem().toString(),comboBoxExDay.getSelectedItem().toString()).startWrite();
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
		comboBoxExTerm.setModel(new DefaultComboBoxModel<String>(getTables("terms")));
		comboBoxExTerm.setBounds(981, 89, 175, 23);
		Export.add(comboBoxExTerm);
		
		comboBoxExDay = new JComboBox<String>();
		comboBoxExDay.setModel(new DefaultComboBoxModel<String>(new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}));
		comboBoxExDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshExTableTerm();
			}
		});
		comboBoxExDay.setBounds(981, 150, 120, 23);
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
		lblSelectDay.setBounds(863, 150, 108, 23);
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
							// TODO Auto-generated catch block
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
								
								comboBoxTerm.setModel(new DefaultComboBoxModel<Object>(getTables("terms")));
								comboBoxEmailTerm.setModel(new DefaultComboBoxModel<String>(getTables("terms")));
								comboBoxStaTerm.setModel(new DefaultComboBoxModel<String>(getTables("terms")));
								comboBoxExTerm.setModel(new DefaultComboBoxModel<String>(getTables("terms")));
								comboBoxStaCoach.setModel(new DefaultComboBoxModel<String>(new StatisticMethod().getCoaches()));
								new TabCoaches(jtCoaches).refreshTable();
								JOptionPane.showMessageDialog(null, "Finished");
								
							} catch (Exception e1) {
								// TODO Auto-generated catch block
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
				// TODO Auto-generated method stub
				String str = list.getSelectedValue().toString();
				switch(str){
				case "Terms":
					/* Set right pane for terms */
					splitPane.setRightComponent(new SettingPane().getTerms());
					break;
				case "Locations":
					splitPane.setRightComponent(new SettingPane().getLocations());
					break;
				default:
					break;
				}
			}
			
		});
		
		splitPane.setLeftComponent(list);
		splitPane.setRightComponent(new SettingPane().getTerms());
		
		


/* Set right pane for locations */	
		
		
//		refreshTable();
//		refreshTableTerm();
	
//		seatsLeft();
		
		
	}
}
