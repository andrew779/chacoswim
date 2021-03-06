package chacoswim;

import java.awt.EventQueue;
import java.sql.*;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.awt.Color;

public class Login {

	private JFrame frameLogin;
	private static Connection connection=null;
	private JTextField tfusername;
	private JPasswordField tfpassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
			//UIManager.setLookAndFeel(new SyntheticaBlackEyeLookAndFeel());
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			UIManager.put("RootPane.setupButtonVisible",false);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frameLogin.setVisible(true);
					Image imglogo=new ImageIcon(this.getClass().getResource("/logo24.png")).getImage();
					window.frameLogin.setIconImage(imglogo);
					window.frameLogin.setTitle("Chaco Swim Club  (Powered by Wenzhong Zheng)");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		connection=sqliteConnection.dbConnector();
		frameLogin = new JFrame();
		frameLogin.setBounds(100, 100, 900, 520);
		frameLogin.setResizable(false);
		frameLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		JPanel panel = new JPanel();
		frameLogin.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblusername = new JLabel("UserName");
		lblusername.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblusername.setBounds(235, 220, 104, 36);
		panel.add(lblusername);
		
		JLabel lblpassword = new JLabel("Password");
		lblpassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblpassword.setBounds(235, 283, 104, 36);
		panel.add(lblpassword);
		
		tfusername = new JTextField();
		
		tfusername.setBounds(393, 222, 229, 36);
		panel.add(tfusername);
		tfusername.setColumns(10);
		
		tfpassword = new JPasswordField();
		tfpassword.setBounds(393, 285, 229, 36);
		panel.add(tfpassword);
		
		JButton btnlogin = new JButton(" Login");
		Image img=new ImageIcon(this.getClass().getResource("/ok.png")).getImage();
		btnlogin.setIcon(new ImageIcon(img));
		
		btnlogin.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnlogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					String query="select * from admin where username=? and password=?";
					PreparedStatement pst=connection.prepareStatement(query);
					pst.setString(1, tfusername.getText());
					pst.setString(2, new String(tfpassword.getPassword()));
					ResultSet rs=pst.executeQuery();
					if(!rs.next()){
						JOptionPane.showMessageDialog(null, "Wrong pwd or usrname");
					}
					else{
						JOptionPane.showMessageDialog(null, "Connection Successful");
						frameLogin.dispose();
						ChacoSwim.main(connection);
						//ChacoSwim cs= new ChacoSwim();
						//cs.setVisible(true);
					}
					pst.close();
					rs.close();
					
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, e);
					
				}
			}
		});
		btnlogin.setBounds(170, 355, 169, 36);
		//add default button
		frameLogin.getRootPane().setDefaultButton(btnlogin);
		
		panel.add(btnlogin);
		
		JLabel lbllogin = new JLabel("");
		lbllogin.setHorizontalAlignment(SwingConstants.CENTER);
		Image img2=new ImageIcon(this.getClass().getResource("/logoFull.png")).getImage();
		lbllogin.setIcon(new ImageIcon(img2));
		lbllogin.setBounds(22, 11, 789, 176);
		panel.add(lbllogin);
		
		JButton btnNewButton = new JButton("Change Passwd");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tfusername.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Please enter your username first.");
				}
				else{
					  JTextField old = new JTextField(5);
				      JTextField new1 = new JTextField(5);
				      JTextField new2 = new JTextField(5);
				      JLabel label=new JLabel();
				 
				      JPanel myPanel = new JPanel(new GridLayout(4,2,5,5));
				      myPanel.add(new JLabel("Current Passwd:"));
				      myPanel.add(old);
				      myPanel.add(Box.createHorizontalStrut(15)); // a spacer
				      myPanel.add(new JLabel("New Passwd:"));
				      myPanel.add(new1);
				      myPanel.add(Box.createHorizontalStrut(15)); // a spacer
				      myPanel.add(new JLabel("Comfirm New Passwd:"));
				      myPanel.add(new2);
				      myPanel.add(Box.createHorizontalStrut(15)); // a spacer
				      myPanel.add(label);
				      
				      new2.addKeyListener(new KeyAdapter() {
							@Override
							public void keyReleased(KeyEvent arg0) {
								if(!new1.getText().equals(new2.getText())){
									label.setText("Doesn't match!");
									label.setForeground(Color.RED);
								}
								else if(new1.getText().equals(new2.getText())){
									label.setText("Ready to go!");
									label.setForeground(Color.GREEN);
								}
							}
						});
				      int result = JOptionPane.showConfirmDialog(null, myPanel, 
				               "Please Enter Current and New Password", JOptionPane.OK_CANCEL_OPTION); 
				      if (result == JOptionPane.OK_OPTION) {
				         try{
				        	 String query="select * from admin where username=? and password=?";
								PreparedStatement pst=connection.prepareStatement(query);
								pst.setString(1, tfusername.getText());
								pst.setString(2, new String(old.getText()));
								ResultSet rs=pst.executeQuery();
								if(!rs.next()){
									JOptionPane.showMessageDialog(null, "Wrong pwd for the usrname");
									rs.close();
									pst.close();
								}
								else{
									rs.close();
									pst.close();
									if(new1.getText().equals(new2.getText())){
										String query2="update admin set password = ? where username = ?";
										PreparedStatement pst2=connection.prepareStatement(query2);
										pst2.setString(1, new2.getText());
										pst2.setString(2, tfusername.getText());
										pst2.execute();
										JOptionPane.showMessageDialog(null, "New Password updated!");
										pst2.close();
									}
									else JOptionPane.showMessageDialog(null, "Please make sure two new password are matched!");
									
								}
				         }catch(Exception ex){
				        	 JOptionPane.showMessageDialog(null, ex);
				         }
				      }
				}
				
				
			}
		});
		Image imgChange=new ImageIcon(this.getClass().getResource("/change.png")).getImage();
		btnNewButton.setIcon(new ImageIcon(imgChange));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBounds(372, 355, 182, 36);
		panel.add(btnNewButton);
		
		JButton btnRestoreDatabase = new JButton("Restore DataBase");
		btnRestoreDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String user = "admin";
					String pwd = "restore";
					if(!tfusername.getText().equals(user)&&!new String(tfpassword.getPassword()).equals(pwd)){
						JOptionPane.showMessageDialog(null, "Wrong pwd or usrname");
					}
					else{
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
								File des = new File("DontTouchMe.sqlite");
								int b = JOptionPane.showConfirmDialog(null, "Are you Sure to restore DataBase?","Restore database",JOptionPane.OK_CANCEL_OPTION);
								if(b==JOptionPane.OK_OPTION){
									
									
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
										
										JOptionPane.showMessageDialog(null, "Finished");
								}//if
							}//else
						}//if
					}//else
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}
						
					
				
			
				
			}
		});
		Image imgRestore=new ImageIcon(this.getClass().getResource("/restore.png")).getImage();
		btnRestoreDatabase.setIcon(new ImageIcon(imgRestore));
		btnRestoreDatabase.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnRestoreDatabase.setBounds(586, 355, 204, 36);
		panel.add(btnRestoreDatabase);
		
		//java.net.URL url = ClassLoader.getSystemResource("com/chacoswim/resources/logo24.png");
		//Toolkit kit = Toolkit.getDefaultToolkit();
		//Image imglogo = kit.createImage(url);
		
	}
}
