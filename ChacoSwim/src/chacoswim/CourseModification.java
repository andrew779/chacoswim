package chacoswim;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void updateNew(){
		try {
			String query = "select * from '"+term+"' where id = ?";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, id);
			ResultSet rs = pst.executeQuery();
			//if true, use update .
			if(rs.next()){
				String update="UPDATE 'main'.'"+term+"' SET 'day' = ?, 'time' = ?, 'line' = ?, 'coach' = ? WHERE  ID = "+id;
				PreparedStatement pstUp = conn.prepareStatement(update);
				pstUp.setString(1, comboBoxDays.getSelectedItem().toString());
				String time = comboBoxStart.getSelectedItem().toString()+"-"+comboBoxEnd.getSelectedItem().toString();
				pstUp.setString(2, time);
				pstUp.setString(3, comboBoxLine.getSelectedItem().toString());
				pstUp.setString(4, comboBoxCoach.getSelectedItem().toString());
				pstUp.execute();
				JOptionPane.showMessageDialog(null, "updated");
				pstUp.close();
				rs.close();
			}
			//if false, use insert.
			else
				JOptionPane.showMessageDialog(null, "No existing record");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void addNew(){
		String query="INSERT INTO 'main'.'"+term+"' ('sid','day','time','line','coach') VALUES (?1,?2,?3,?4,?5)";
		try {
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1,tfSID.getText().toString());
			pst.setString(2, comboBoxDays.getSelectedItem().toString());
			String time = comboBoxStart.getSelectedItem().toString()+"-"+comboBoxEnd.getSelectedItem().toString();
			pst.setString(3, time);
			pst.setString(4, comboBoxLine.getSelectedItem().toString());
			pst.setString(5, comboBoxCoach.getSelectedItem().toString());
			pst.execute();
			//JOptionPane.showMessageDialog(null, "New record added.");
			
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e);
		}
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
				String query = "select t.id, s.sid, s.firstname, s.lastname, t.day, t.time, t.coach, t.line from '"+term+"' t JOIN students s on s.sid=t.sid where t.id= ?;";
				PreparedStatement pst = conn.prepareStatement(query);
				pst.setString(1, id);
				ResultSet rs = pst.executeQuery();
				
				if(rs.next()){
					String day = rs.getString("day").toString();
					String time = rs.getString("time").toString();
					String timeStart = time.substring(0, 5);
					String timeEnd = time.substring(6);
					String line = rs.getString("line").toString();
					String coach = rs.getString("coach").toString();
					
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
				}
					
				rs.close();
				pst.close();
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e);
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
		tfSID.setBounds(52, 81, 103, 20);
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
		textFieldFirstName.setBounds(262, 81, 103, 20);
		panel.add(textFieldFirstName);
		
		JLabel lblLastname = new JLabel("LastName:");
		lblLastname.setBounds(417, 82, 75, 19);
		panel.add(lblLastname);
		
		textFieldLastName = new JTextField();
		textFieldLastName.setEditable(false);
		textFieldLastName.setColumns(10);
		textFieldLastName.setBounds(479, 81, 103, 20);
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
		comboBoxDays.setBounds(52, 181, 103, 20);
		panel.add(comboBoxDays);
		
		
		
		
		JLabel lblCoach = new JLabel("Coach:");
		lblCoach.setBounds(191, 179, 66, 19);
		panel.add(lblCoach);
		
		modelCoach = new DefaultComboBoxModel<String>(getCoach());
		comboBoxCoach = new JComboBox<String>(modelCoach);
		comboBoxCoach.setBounds(262, 179, 103, 20);
		panel.add(comboBoxCoach);
		
		JLabel lblLine = new JLabel("Line:");
		lblLine.setBounds(417, 182, 66, 19);
		panel.add(lblLine);
		
		modelLine = new DefaultComboBoxModel<String>(new String[] {"1", "2", "3", "4", "5","6"});
		comboBoxLine = new JComboBox<String>(new DefaultComboBoxModel<String>(new String[] {"1", "2", "3", "4", "5", "6"}));
		comboBoxLine.setBounds(479, 181, 103, 20);
		panel.add(comboBoxLine);
		
		JLabel lblStartTime = new JLabel("Start Time:");
		lblStartTime.setBounds(57, 246, 66, 19);
		panel.add(lblStartTime);
		
		modelStart = new DefaultComboBoxModel<String>(new String[] {"N/A", "10:00", "10:30", "11:00", "11:30", "12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30"});
		comboBoxStart = new JComboBox<String>(modelStart);
		comboBoxStart.setBounds(133, 245, 103, 20);
		panel.add(comboBoxStart);
		
		JLabel lblEndTime = new JLabel("End Time:");
		lblEndTime.setBounds(358, 246, 66, 19);
		panel.add(lblEndTime);
		
		modelEnd = new DefaultComboBoxModel<String>(new String[]{"N/A", "10:00", "10:30", "11:00", "11:30", "12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30"});
		comboBoxEnd = new JComboBox<String>(modelEnd);
		comboBoxEnd.setBounds(434, 245, 103, 20);
		panel.add(comboBoxEnd);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(action.equals("update")){
					updateNew();
					jbtn2.doClick();
				}
				else if (action.equals("add")){
					addNew();
					JOptionPane.showMessageDialog(null, "New record added");
					jbtn2.doClick();
					frameCourse.dispose();
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
				jbtn2.doClick();
				frameCourse.dispose();
			}
		});
		btnCancel.setBounds(394, 370, 89, 23);
		panel.add(btnCancel);
		
		lblTerm = new JLabel("Term:");
		lblTerm.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTerm.setForeground(SystemColor.textHighlight);
		lblTerm.setBounds(10, 37, 602, 23);
		panel.add(lblTerm);
	}
}
