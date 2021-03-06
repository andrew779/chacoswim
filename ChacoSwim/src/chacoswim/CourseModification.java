package chacoswim;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.ChacoSwimModel;

import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

import javax.sql.rowset.CachedRowSet;
import javax.swing.ComboBoxModel;
import javax.swing.JSeparator;

public class CourseModification extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfSID;
	private JTextField textFieldFirstName;
	private JTextField textFieldLastName;
	private JLabel lblTerm;
	private static CourseModification frameCourse;
	private static String id="";
	private static String term="";
	private static String location="";
	private static String action = "";
	private Connection conn=null;
	private JComboBox<String> comboBoxDays,comboBoxCoach,comboBoxLine,comboBoxStart,comboBoxEnd;
	private DefaultComboBoxModel<String> modelDays,modelCoach,modelLine,modelStart,modelEnd;
	private static JButton jbtn2;
	private JTextField tfAmount;
	private JComboBox<String> comboBoxPay;
	private DefaultComboBoxModel<String> modelPay;
	private String targetLevel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args,JButton jbtn) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frameCourse = new CourseModification();
					frameCourse.setVisible(true);
					jbtn2=jbtn;
					Image imglogo=new ImageIcon(this.getClass().getResource("/logo24.png")).getImage();
					frameCourse.setIconImage(imglogo);
					frameCourse.setTitle("Chaco Swim Club");
					if (args[0].equals("add")){
						action=args[0];
						id=args[1];
						term=args[2];
						location=args[3];
						frameCourse.updateCourseInfo(id,term);
					}
					else if(args[0].equals("update")){
						action=args[0];
						id=args[1];
						term=args[2];
						location=args[3];
						frameCourse.updateCourseInfo(id,term);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * TODO get coachName
	 * @return
	 */
	public String[] getCoach(){
		try {
			List<String> list = new ArrayList<String>();
			String query="select name from coach";
			PreparedStatement pst=conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				list.add(rs.getString("name"));
			}
			String[] str= new String[list.size()];
			list.toArray(str);
			rs.close();
			pst.close();
			return str;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * TODO UpdateNew Info
	 */
	public void updateNew(){
		try {
			String query = "select * from 'active_record' where id = ?";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, id);
			ResultSet rs = pst.executeQuery();
			//if true, use update .
			if(rs.next()){
				String update="UPDATE 'main'.'active_record' SET 'day' = ?1, 'time' = ?2, 'line' = ?3, 'coachID' = ?4,'payment_method' = ?5,'amount' =  ?6 WHERE  id = "+id;
				PreparedStatement pstUp = conn.prepareStatement(update);
				pstUp.setString(1, comboBoxDays.getSelectedItem().toString());
				String time = comboBoxStart.getSelectedItem().toString()+"-"+comboBoxEnd.getSelectedItem().toString();
				pstUp.setString(2, time);
				pstUp.setString(3, comboBoxLine.getSelectedItem().toString());
				String coachID = new DataBaseManage().gotId("coach", comboBoxCoach.getSelectedItem().toString());
				pstUp.setString(4, coachID);
				pstUp.setString(5, comboBoxPay.getSelectedItem().toString());
				pstUp.setString(6, tfAmount.getText());
				pstUp.execute();
				pstUp.close();
				rs.close();
			}
			//if false, use insert.
			else
				JOptionPane.showMessageDialog(null, "No existing record");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	/**
	 * TODO addNew record
	 */
	public void addNew(String level){
		String query="INSERT INTO 'main'.'active_record' ('termID','sid','locationID','day','time','line','coachID','levelID','payment_method','amount') VALUES (?1,?2,?3,?4,?5,?6,?7,?8,?9,?10)";
		try {
			PreparedStatement pst = conn.prepareStatement(query);
			DataBaseManage dbm = new DataBaseManage();
			pst.setString(1, dbm.gotId("terms", term));
			pst.setString(2,tfSID.getText().toString());
			pst.setString(3, dbm.gotId("location", location));
			pst.setString(4, comboBoxDays.getSelectedItem().toString());
			String time = comboBoxStart.getSelectedItem().toString()+"-"+comboBoxEnd.getSelectedItem().toString();
			pst.setString(5, time);
			pst.setString(6, comboBoxLine.getSelectedItem().toString());
			pst.setString(7,dbm.gotId("coach", comboBoxCoach.getSelectedItem().toString()));
			//get current level from student record
			pst.setString(8, dbm.gotId("level", level));
			//get current level from comboBox selection
			pst.setString(9, comboBoxPay.getSelectedItem().toString());
			pst.setString(10, tfAmount.getText());
			pst.execute();
			
			pst.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	public void addNew(){
		String query="INSERT INTO 'main'.'active_record' ('termID','sid','locationID','day','time','line','coachID','levelID','payment_method','amount') VALUES (?1,?2,?3,?4,?5,?6,?7,?8,?9,?10)";
		try {
			PreparedStatement pst = conn.prepareStatement(query);
			DataBaseManage dbm = new DataBaseManage();
			pst.setString(1, dbm.gotId("terms", term));
			pst.setString(2,tfSID.getText().toString());
			pst.setString(3, dbm.gotId("location", location));
			pst.setString(4, comboBoxDays.getSelectedItem().toString());
			String time = comboBoxStart.getSelectedItem().toString()+"-"+comboBoxEnd.getSelectedItem().toString();
			pst.setString(5, time);
			pst.setString(6, comboBoxLine.getSelectedItem().toString());
			pst.setString(7,dbm.gotId("coach", comboBoxCoach.getSelectedItem().toString()));
			//get current level from student record
			pst.setString(8, dbm.gotId("level", dbm.getCurLevel(tfSID.getText().toString())));
			//get current level from comboBox selection
			pst.setString(9, comboBoxPay.getSelectedItem().toString());
			pst.setString(10, tfAmount.getText());
			pst.execute();
			
			pst.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	
	
	public void updateWaitingListCheckIn(String sid,String term,String location,String level,String day, String time){
		action = "waitingListAdd";
		targetLevel = level;
		this.term = term;
		this.location = location;
		lblTerm.setText("Term: "+term+"              Location: "+location);
		ChacoSwimModel csm = new ChacoSwimModel();
		String query = "SELECT * FROM students where sid = "+sid;
		CachedRowSet crs = csm.excuteWithRS(query);
		tfSID.setText(sid);
		try {
			while(crs.next()){
				textFieldFirstName.setText(crs.getString("firstname"));
				textFieldLastName.setText(crs.getString("lastname"));
				String curLevel = crs.getString("CurLevel");
				if(!curLevel.equalsIgnoreCase(level)){
					JOptionPane.showMessageDialog(null, "Please pay attention that current level ("+curLevel+") in waiting list doesn't match it's record in student reg info("
							+level+ ")");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String timeStart = time.substring(0, time.indexOf("-"));
		String timeEnd = time.substring(time.indexOf("-")+1);
		comboBoxDays.setSelectedItem(day);
		comboBoxStart.setSelectedItem(timeStart);
		comboBoxEnd.setSelectedItem(timeEnd);
		
	}
	
	public void updateCourseInfo(String id,String term){
		try {
			lblTerm.setText("Term: "+term+"              Location: "+location);
			if(action.equals("add")){
				String query0 = "select * from students where sid = "+id;
				PreparedStatement pst0 = conn.prepareStatement(query0);
				ResultSet rs0 = pst0.executeQuery();
				tfSID.setText(id);
				textFieldFirstName.setText(rs0.getString("firstname"));
				textFieldLastName.setText(rs0.getString("lastname"));
				rs0.close();
				pst0.close();
			}
			else if(action.equals("update")){
				String query = "select a.id, s.sid, s.firstname, s.lastname, a.day, a.time, c.name as coach, a.line, a.payment_method, a.amount"
						+" FROM active_record a JOIN students s ON a.sid=s.sid"+
						" JOIN coach c ON a.coachID=c.id WHERE a.id = "+id;
				PreparedStatement pst = conn.prepareStatement(query);
				ResultSet rs = pst.executeQuery();
				
				if(rs.next()){
					String day = rs.getString("day").toString();
					String time = rs.getString("time").toString();
					String timeStart = time.substring(0, 5);
					String timeEnd = time.substring(6);
					String line = rs.getString("line").toString();
					String coach = rs.getString("coach").toString();
					String pay = "",amount="";
					if(rs.getString("payment_method")!=null&&rs.getString("amount")!=null){
						pay = rs.getString("payment_method").toString();
						amount = rs.getString("amount").toString();
					}
					
					
					tfSID.setText(rs.getString("sid"));
					textFieldFirstName.setText(rs.getString("firstname"));
					textFieldLastName.setText(rs.getString("lastname"));
				
					if(modelDays.getIndexOf(day)!=-1){
						comboBoxDays.setSelectedItem(day);
					}
					if(modelStart.getIndexOf(timeStart)!=-1){
						comboBoxStart.setSelectedItem(timeStart);
					}
					if(modelEnd.getIndexOf(timeEnd)!=-1){
						comboBoxEnd.setSelectedItem(timeEnd);
					}
					if(modelLine.getIndexOf(line)!=-1){
						comboBoxLine.setSelectedItem(line);
					}
					if(modelCoach.getIndexOf(coach)!=-1){
						comboBoxCoach.setSelectedItem(coach);
					}
					if(modelPay.getIndexOf(pay)!=-1){
						comboBoxPay.setSelectedItem("pay");
					}
					tfAmount.setText(amount);
					
				}
					
				rs.close();
				pst.close();
			}
		}catch (Exception e) {
			//JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the frame.
	 */
	public CourseModification() {
		conn=sqliteConnection.dbConnector();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 648, 494);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblSid = new JLabel("SID:");
		lblSid.setBounds(10, 82, 66, 19);
		panel.add(lblSid);
		
		tfSID = new JTextField();
		lblSid.setLabelFor(tfSID);
		tfSID.setEditable(false);
		tfSID.setBounds(52, 81, 103, 25);
		panel.add(tfSID);
		tfSID.setColumns(10);
		
		JLabel lblStudentInfomation = new JLabel("Student Infomation");
		lblStudentInfomation.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStudentInfomation.setHorizontalAlignment(SwingConstants.CENTER);
		lblStudentInfomation.setBounds(191, 11, 194, 19);
		panel.add(lblStudentInfomation);
		
		JLabel lblNewLabel = new JLabel("FirstName:");
		lblNewLabel.setBounds(191, 82, 75, 19);
		panel.add(lblNewLabel);
		
		textFieldFirstName = new JTextField();
		textFieldFirstName.setEditable(false);
		textFieldFirstName.setColumns(10);
		textFieldFirstName.setBounds(262, 81, 103, 25);
		panel.add(textFieldFirstName);
		
		JLabel lblLastname = new JLabel("LastName:");
		lblLastname.setBounds(417, 82, 75, 19);
		panel.add(lblLastname);
		
		textFieldLastName = new JTextField();
		textFieldLastName.setEditable(false);
		textFieldLastName.setColumns(10);
		textFieldLastName.setBounds(479, 81, 103, 25);
		panel.add(textFieldLastName);
		
		JLabel lblCourseInfomation = new JLabel("Course Infomation");
		lblCourseInfomation.setHorizontalAlignment(SwingConstants.CENTER);
		lblCourseInfomation.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCourseInfomation.setBounds(191, 143, 194, 19);
		panel.add(lblCourseInfomation);
		
		JLabel lblDays = new JLabel("Days:");
		lblDays.setBounds(10, 182, 66, 19);
		panel.add(lblDays);
		
		modelDays = new DefaultComboBoxModel<String>(new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"});
		comboBoxDays = new JComboBox<String>(modelDays);
		comboBoxDays.setBounds(52, 181, 103, 25);
		panel.add(comboBoxDays);
		
		
		
		
		JLabel lblCoach = new JLabel("Coach:");
		lblCoach.setBounds(191, 179, 66, 19);
		panel.add(lblCoach);
		
		modelCoach = new DefaultComboBoxModel<String>(getCoach());
		comboBoxCoach = new JComboBox<String>(modelCoach);
		comboBoxCoach.setBounds(262, 179, 103, 25);
		panel.add(comboBoxCoach);
		
		JLabel lblLine = new JLabel("Line:");
		lblLine.setBounds(417, 182, 66, 19);
		panel.add(lblLine);
		
		modelLine = new DefaultComboBoxModel<String>(new String[] {"1", "2", "3", "4", "5","6"});
		comboBoxLine = new JComboBox<String>(new DefaultComboBoxModel<String>(new String[] {"1", "2", "3", "4", "5", "6"}));
		comboBoxLine.setBounds(479, 181, 103, 25);
		panel.add(comboBoxLine);
		
		JLabel lblStartTime = new JLabel("Start Time:");
		lblStartTime.setBounds(57, 246, 66, 19);
		panel.add(lblStartTime);
		
		modelStart = new DefaultComboBoxModel<String>(new String[] {"N/A", "10:00", "10:30", "11:00", "11:30", "12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30"});
		comboBoxStart = new JComboBox<String>(modelStart);
		comboBoxStart.setBounds(133, 245, 103, 25);
		panel.add(comboBoxStart);
		
		JLabel lblEndTime = new JLabel("End Time:");
		lblEndTime.setBounds(358, 246, 66, 19);
		panel.add(lblEndTime);
		
		modelEnd = new DefaultComboBoxModel<String>(new String[]{"N/A", "10:00", "10:30", "11:00", "11:30", "12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30"});
		comboBoxEnd = new JComboBox<String>(modelEnd);
		comboBoxEnd.setBounds(434, 245, 103, 25);
		panel.add(comboBoxEnd);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(action.equals("update")){
					updateNew();
					JOptionPane.showMessageDialog(null, "Updated");
					jbtn2.doClick();
					frameCourse.dispose();
				}
				else if (action.equals("add")){
					addNew();
					JOptionPane.showMessageDialog(null, "New record added");
					jbtn2.doClick();
					frameCourse.dispose();
				}
				else if (action.equals("waitingListAdd")){
					addNew(targetLevel);
					JOptionPane.showMessageDialog(null, "New record added");
					dispose();
				}
				else
					JOptionPane.showMessageDialog(null, "No action string detected");
			}
		});
		btnUpdate.setBounds(133, 370, 89, 23);
		panel.add(btnUpdate);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (action.equals("waitingListAdd")){
					dispose();
				}
				else{
					jbtn2.doClick();
					frameCourse.dispose();
				}
				
			}
		});
		btnCancel.setBounds(394, 370, 89, 23);
		panel.add(btnCancel);
		
		lblTerm = new JLabel("Term:");
		lblTerm.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTerm.setForeground(SystemColor.textHighlight);
		lblTerm.setBounds(10, 37, 602, 23);
		panel.add(lblTerm);
		
		JLabel lblPay = new JLabel("Payment:");
		lblPay.setBounds(57, 299, 66, 19);
		panel.add(lblPay);
		
		modelPay = new DefaultComboBoxModel<String>(new String[] {"Unpaid", "Cash", "Cheque", "EMT", "CreditCard", "BankDraft", "N/A"});
		comboBoxPay = new JComboBox<String>(modelPay);
		comboBoxPay.setBounds(133, 298, 103, 25);
		panel.add(comboBoxPay);
		
		JLabel lblAmount = new JLabel("Amount:");
		lblAmount.setBounds(358, 301, 66, 19);
		panel.add(lblAmount);
		
		tfAmount = new JTextField();
		tfAmount.setBounds(434, 298, 103, 25);
		panel.add(tfAmount);
		tfAmount.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 283, 602, 2);
		panel.add(separator);
	}
}
