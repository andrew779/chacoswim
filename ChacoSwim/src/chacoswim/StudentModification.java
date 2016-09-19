package chacoswim;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.JComboBox;

public class StudentModification extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfFirstname;
	private JTextField tfLastname;
	private JTextField tfAge;
	private JTextField tfHomephone;
	private JTextField tfCell;
	private JTextField tfEmail;
	private JTextField tfAllergy;
	private JTextField tfHealthcard;
	private JTextField tfPs;
	private JCheckBox chckbxYes;
	private Connection conn=null;
	private JTextArea textArea;
	private static StudentModification frame1;
	private static String id = "";
	private JComboBox<String> comboBoxPreLevel,comboBoxCurLevel;
	private DefaultComboBoxModel<String> modelPreLevel,modelCurLevel;
	private static JButton jb;
	private JTextField tfComment;

	/**
	 * Launch the application.
	 */
	public static void main(String arg,JButton jbtn) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame1 = new StudentModification();
					frame1.setVisible(true);
					Image imglogo=new ImageIcon(this.getClass().getResource("/logo24.png")).getImage();
					frame1.setIconImage(imglogo);
					frame1.setTitle("Chaco Swim Club");
					jb=jbtn;
					if (!arg.isEmpty()){
						id=arg;
						frame1.updateInfo(id);
					}
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
	}
public String[] getLevel(){
		
		try {
			List<String> list = new ArrayList<String>();
			String query="select name from level";
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
	
	public void updateInfo(String id){
		try {
			String query = "select * from students where sid = ?";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, id);
			ResultSet rs = pst.executeQuery();
			
			tfFirstname.setText(rs.getString("FirstName"));
			tfLastname.setText(rs.getString("LastName"));
			tfAge.setText(rs.getString("Age"));
			if(modelPreLevel.getIndexOf(rs.getString("PreLevel"))!=-1)
			comboBoxPreLevel.setSelectedItem(rs.getString("PreLevel"));
			if(modelCurLevel.getIndexOf(rs.getString("CurLevel"))!=-1)
			comboBoxCurLevel.setSelectedItem(rs.getString("CurLevel"));
			tfHomephone.setText(rs.getString("HomePhone"));
			tfCell.setText(rs.getString("Cell"));
			tfEmail.setText(rs.getString("Email"));
			tfHealthcard.setText(rs.getString("HealthCard"));
			tfAllergy.setText(rs.getString("Allergy"));
			tfPs.setText(rs.getString("Ps"));
			if(rs.getString("Form").equals("Y"))
			chckbxYes.setSelected(true);
			else chckbxYes.setSelected(false);
			tfComment.setText(rs.getString("Comment"));
			pst.close();
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public void clearAll(){
		tfFirstname.setText("");
		tfLastname.setText("");
		tfAge.setText("");
		comboBoxPreLevel.setSelectedIndex(0);
		comboBoxCurLevel.setSelectedIndex(0);
		tfHomephone.setText("");
		tfCell.setText("");
		tfEmail.setText("");
		tfHealthcard.setText("");
		tfAllergy.setText("");
		tfPs.setText("");
		chckbxYes.setSelected(false);
		tfComment.setText("");
	}
	
	public String getInfo(){
		
		StringBuilder str=new StringBuilder("The Following info is updated:\n");
		str.append("\nFirstname: \t"+tfFirstname.getText());
		str.append("\nLasttname: \t"+tfLastname.getText());
		str.append("\nAge: \t"+tfAge.getText());
		str.append("\nPrelevel: \t"+comboBoxPreLevel.getSelectedItem().toString());
		str.append("\nCurlevel: \t"+comboBoxCurLevel.getSelectedItem().toString());
		str.append("\nHomePhone: \t"+tfHomephone.getText());
		str.append("\nCell: \t"+tfCell.getText());
		str.append("\nEmail: \t"+tfEmail.getText());
		str.append("\nForm: \t"+chckbxYes.isSelected());
		str.append("\nAllergy: \t"+tfAllergy.getText());
		str.append("\nHealthCard: \t"+tfHealthcard.getText());
		str.append("\nPS: \t"+tfPs.getText());
		str.append("\nComment: \t"+tfComment.getText());
		return str.toString();
		
	}
	
	public void updateNew(){
		int i = JOptionPane.showConfirmDialog(null, "Please comfirm the information you are going to update", "comfirmation",JOptionPane.YES_NO_OPTION);
		if(i==JOptionPane.YES_OPTION){
			try{
				
				String query="UPDATE 'main'.'students' SET 'FirstName' = ?,'LastName' = ?, 'PreLevel' = ?, 'CurLevel' = ?, 'HomePhone' = ?, 'Cell' = ?, 'Email' = ?, 'Form' = ?, 'HealthCard' = ?, 'Allergy' = ?, 'Age' = ?, 'Ps' = ?, 'Comment'=? WHERE  SID = "+id;
				PreparedStatement pst=conn.prepareStatement(query);
				pst.setString(1, tfFirstname.getText());
				pst.setString(2, tfLastname.getText());
				pst.setString(3, comboBoxPreLevel.getSelectedItem().toString());
				pst.setString(4, comboBoxCurLevel.getSelectedItem().toString());
				pst.setString(5, tfHomephone.getText());
				pst.setString(6, tfCell.getText());
				pst.setString(7, tfEmail.getText());
				if(chckbxYes.isSelected())pst.setString(8, "Y");
				else pst.setString(8, "N");
				pst.setString(9, tfHealthcard.getText());
				pst.setString(10, tfAllergy.getText());
				pst.setString(11, tfAge.getText());
				pst.setString(12, tfPs.getText());
				pst.setString(13, tfComment.getText());
				
				pst.execute();
				
				pst.close();
				textArea.setText(getInfo());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public void saveNew(){
		
		int i = JOptionPane.showConfirmDialog(null, "Please comfirm the information you are going to add", "comfirmation",JOptionPane.YES_NO_OPTION);
		if(i==JOptionPane.YES_OPTION){
			try{
				
				String query="INSERT INTO 'main'.'students' ('FirstName','LastName','Age','PreLevel','CurLevel','HomePhone','Cell','Email','Form','HealthCard','Allergy','Ps','Comment') VALUES (?1,?2,?3,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement pst=conn.prepareStatement(query);
				pst.setString(1, tfFirstname.getText());
				pst.setString(2, tfLastname.getText());
				pst.setString(3, tfAge.getText());
				pst.setString(4, comboBoxPreLevel.getSelectedItem().toString());
				pst.setString(5, comboBoxCurLevel.getSelectedItem().toString());
				pst.setString(6, tfHomephone.getText());
				pst.setString(7, tfCell.getText());
				pst.setString(8, tfEmail.getText());
				if(chckbxYes.isSelected())pst.setString(9, "Y");
				else pst.setString(9, "N");
				pst.setString(10, tfHealthcard.getText());
				pst.setString(11, tfAllergy.getText());
				pst.setString(12, tfPs.getText());
				pst.setString(13, tfComment.getText());
				pst.execute();
				pst.close();
				
				
				
				textArea.setText(getInfo());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	/**
	 * Create the frame.
	 */
	public StudentModification() {
		
		
		conn=sqliteConnection.dbConnector();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 696, 621);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblFirstname = new JLabel("FirstName: ");
		lblFirstname.setBounds(26, 30, 73, 14);
		contentPane.add(lblFirstname);
		
		tfFirstname = new JTextField();
		tfFirstname.setBounds(109, 27, 123, 20);
		contentPane.add(tfFirstname);
		tfFirstname.setColumns(10);
		
		JLabel lblLastname = new JLabel("LastName: ");
		lblLastname.setBounds(26, 71, 73, 14);
		contentPane.add(lblLastname);
		
		tfLastname = new JTextField();
		tfLastname.setColumns(10);
		tfLastname.setBounds(109, 68, 123, 20);
		contentPane.add(tfLastname);
		
		JLabel lblAge = new JLabel("Age: ");
		lblAge.setBounds(26, 110, 73, 14);
		contentPane.add(lblAge);
		
		tfAge = new JTextField();
		tfAge.setColumns(10);
		tfAge.setBounds(109, 107, 60, 20);
		contentPane.add(tfAge);
		
		JLabel lblPrelevel = new JLabel("PreLevel:");
		lblPrelevel.setBounds(26, 150, 73, 14);
		contentPane.add(lblPrelevel);
		
		JLabel lblCurlevel = new JLabel("CurLevel:");
		lblCurlevel.setBounds(26, 190, 73, 14);
		contentPane.add(lblCurlevel);
		
		JLabel lblHomephone = new JLabel("HomePhone: ");
		lblHomephone.setBounds(26, 230, 89, 14);
		contentPane.add(lblHomephone);
		
		JLabel lblCell = new JLabel("Cell:");
		lblCell.setBounds(26, 270, 73, 14);
		contentPane.add(lblCell);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(26, 310, 73, 14);
		contentPane.add(lblEmail);
		
		JLabel lblForm = new JLabel("Form:");
		lblForm.setBounds(26, 350, 46, 14);
		contentPane.add(lblForm);
		
		JLabel lblHealthcard = new JLabel("HealthCard:");
		lblHealthcard.setBounds(26, 390, 73, 14);
		contentPane.add(lblHealthcard);
		
		JLabel lblAllergy = new JLabel("Allergy:");
		lblAllergy.setBounds(26, 430, 73, 14);
		contentPane.add(lblAllergy);
		
		tfHomephone = new JTextField();
		tfHomephone.setColumns(10);
		tfHomephone.setBounds(109, 227, 123, 20);
		contentPane.add(tfHomephone);
		
		tfCell = new JTextField();
		tfCell.setColumns(10);
		tfCell.setBounds(109, 267, 123, 20);
		contentPane.add(tfCell);
		
		tfEmail = new JTextField();
		tfEmail.setColumns(10);
		tfEmail.setBounds(109, 310, 123, 20);
		contentPane.add(tfEmail);
		
		chckbxYes = new JCheckBox("Yes");
		chckbxYes.setBounds(109, 350, 97, 23);
		contentPane.add(chckbxYes);
		
		tfHealthcard = new JTextField();
		tfHealthcard.setColumns(10);
		tfHealthcard.setBounds(109, 390, 123, 20);
		contentPane.add(tfHealthcard);
		
		tfAllergy = new JTextField();
		tfAllergy.setColumns(10);
		tfAllergy.setBounds(109, 430, 123, 20);
		contentPane.add(tfAllergy);
		
		JLabel lblPs = new JLabel("PS:");
		lblPs.setBounds(26, 470, 73, 14);
		contentPane.add(lblPs);
		
		tfPs = new JTextField();
		tfPs.setColumns(10);
		tfPs.setBounds(109, 470, 123, 20);
		contentPane.add(tfPs);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(id.isEmpty()){
				saveNew();
				clearAll();
				jb.doClick();
				}
				else{
					updateNew();
					id="";
					JOptionPane.showMessageDialog(null, "Updated");
					jb.doClick();
					frame1.dispose();
				}
					
			}
		});
		btnSubmit.setBounds(317, 506, 89, 23);
		contentPane.add(btnSubmit);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearAll();
			}
		});
		btnClear.setBounds(427, 506, 89, 23);
		contentPane.add(btnClear);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(317, 65, 310, 405);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jb.doClick();
				frame1.dispose();
			}
		});
		btnCancel.setBounds(538, 506, 89, 23);
		contentPane.add(btnCancel);
		
		comboBoxPreLevel = new JComboBox<String>();
		comboBoxPreLevel.setBounds(109, 146, 111, 23);
		modelPreLevel = new DefaultComboBoxModel<String>(getLevel());
		comboBoxPreLevel.setModel(modelPreLevel);
		
		contentPane.add(comboBoxPreLevel);
		
		comboBoxCurLevel = new JComboBox<String>();
		comboBoxCurLevel.setBounds(109, 190, 111, 23);
		modelCurLevel = new DefaultComboBoxModel<String>(getLevel());
		comboBoxCurLevel.setModel(modelCurLevel);
		contentPane.add(comboBoxCurLevel);
		
		JLabel lblComment = new JLabel("Comment:");
		lblComment.setBounds(26, 510, 82, 14);
		contentPane.add(lblComment);
		
		tfComment = new JTextField();
		tfComment.setColumns(10);
		tfComment.setBounds(109, 507, 123, 20);
		contentPane.add(tfComment);
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{tfFirstname, tfLastname, tfAge, tfHomephone, tfCell, tfEmail, chckbxYes, tfHealthcard, tfAllergy, tfPs, btnSubmit, btnClear, btnCancel, lblFirstname, textArea, lblLastname, lblAge, lblPrelevel, lblCurlevel, lblHomephone, lblCell, lblEmail, lblForm, lblHealthcard, lblAllergy, lblPs, scrollPane}));
		
		
	}
}
