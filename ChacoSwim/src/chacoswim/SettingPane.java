package chacoswim;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;
import net.proteanit.sql.DbUtils;

public class SettingPane implements ActionListener{

	private JButton termsAdd;
	private JButton termsUpdate;
	private JButton termsDelete;
	private JButton locationsAdd;
	private JButton locationsDelete;
	private JButton locationsUpdate;
	public SettingPane() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * Get a JPanel with titled border
	 * @param title
	 * @return
	 */
	private JPanel getPanel(String title){
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(title));
		return panel;
	}
	
	private void fillTable(JTable table,String request){
		Connection conn=sqliteConnection.dbConnector();
		PreparedStatement pst=null;
		ResultSet rs=null;
		try{
			
			if(request.equals("terms")){
				String query="select * from terms ORDER BY id DESC";
				pst=conn.prepareStatement(query);
				rs=pst.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(rs));
			}
			else if(request.equals("locations")){
				String query = "select * from location ORDER BY id DESC";
				pst=conn.prepareStatement(query);
				rs=pst.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(rs));
			}
			pst.close();
			rs.close();
			conn.close();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void manage(String key,String ...para){
		Connection conn=sqliteConnection.dbConnector();
		PreparedStatement pst=null;
		String name,type,id,query;
		try{
			switch(key){
			case "termsAdd":
				name = para[0];
				type = para[1];
				query = "INSERT INTO terms (name,type) VALUES (?,?)";
				pst=conn.prepareStatement(query);
				pst.setString(1, name);
				pst.setString(2, type);
				pst.execute();
				break;
			case "termsUpdate":
				name = para[0];
				type = para[1];
				id = para[2];
				query = "UPDATE terms SET name = ?, type = ? WHERE id = ?";
				pst=conn.prepareStatement(query);
				pst.setString(1, name);
				pst.setString(2, type);
				pst.setString(3, id);
				pst.execute();
				break;
			case "termsDelete":
				id = para[0];
				query = "DELETE FROM terms WHERE id = ?";
				pst=conn.prepareStatement(query);
				pst.setString(1, id);
				pst.execute();
				break;
				
			}//switch
			pst.close();
			conn.close();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	/**
	 * getTerms panel
	 * @return
	 */
	public JPanel getTerms(){
		JPanel p = new JPanel();
		p.setLayout(new MigLayout());
		
		
		JPanel panel = getPanel("Terms");
		panel.setLayout(new MigLayout("","[]10[]"));
		//create elements for term panel
		JTable table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		JLabel name = new JLabel("Term: ");
		JTextField tfname = new JTextField();
		JLabel type = new JLabel("Type: ");
		JComboBox<String> cbType = new JComboBox<String>();
		cbType.setModel(new DefaultComboBoxModel<String>(new String[]{"Class","Schedule"}));
		termsAdd  = new JButton("Add");
		termsUpdate = new JButton("Update");
		termsDelete = new JButton("Delete");
		fillTable(table,"terms");
		
		panel.add(scrollPane, "span");
		panel.add(name, "right,sg 1");
		panel.add(tfname,"width 10:150:200, span, pushx, growx, wrap");
		panel.add(type,"right,sg 1");
		panel.add(cbType,"width 10:150:200,span,pushx, growx, wrap");
		panel.add(termsAdd,"skip,split 3,width 70");
		panel.add(termsUpdate,"width 70");
		panel.add(termsDelete,"width 70");
		
		p.add(panel);
		//handlers
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = table.getSelectedRow();
				if(row!=-1){
					tfname.setText(table.getValueAt(row, 1).toString());
					cbType.setSelectedItem(table.getValueAt(row, 2).toString());
					
				}
			}
		});
		
		termsAdd.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				manage("termsAdd",tfname.getText(),cbType.getSelectedItem().toString());
				JOptionPane.showMessageDialog(null, "Done");
				fillTable(table,"terms");
			}
		});
		termsUpdate.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				if(row!=-1){
					String id = table.getValueAt(row, 0).toString();
					manage("termsUpdate",tfname.getText(),cbType.getSelectedItem().toString(),id);
					JOptionPane.showMessageDialog(null, "Done");
					fillTable(table,"terms");
				}
				else JOptionPane.showMessageDialog(null, "Select a valid row from table first");
			}
		});
		termsDelete.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				String id = table.getValueAt(row, 0).toString();
				manage("termsDelete",id);
				JOptionPane.showMessageDialog(null, "Done");
				fillTable(table,"terms");
			}
		});
		
		
		return p;
	}
	/**
	 * getLocations panel
	 * @return JPanel
	 * 
	 */
	public JPanel getLocations(){
		JPanel panel = new JPanel();
		panel.setLayout(new MigLayout());
		JPanel p = getPanel("Locations");
		p.setLayout(new MigLayout("","[]10[]"));
		//create elements for p
		JTable table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		fillTable(table,"locations");
		//add table
		p.add(scrollPane,"span, wrap");
		
		//name line
		JLabel name = new JLabel("Name: ");
		JTextField tfname = new JTextField();
		p.add(name,"right,sg 2");
		p.add(tfname,"width 10:150:200,span, pushx, growx, wrap");
		//address line
		JLabel add = new JLabel("Address: ");
		JTextField tfadd = new JTextField();
		p.add(add,"right,sg 2");
		p.add(tfadd,"width 10:150:200,span, pushx, growx, wrap");
		//phone line
		JLabel pho = new JLabel("Phone: ");
		JTextField tfpho = new JTextField();
		p.add(pho,"right,sg 2");
		p.add(tfpho,"width 10:150:200,span, pushx, growx, wrap");
		//buttons
		locationsAdd = new JButton("Add");
		locationsUpdate = new JButton("Update");
		locationsDelete = new JButton("Delete");
		p.add(locationsAdd,"skip, split 3, width 70");
		p.add(locationsUpdate,"width 70");
		p.add(locationsDelete,"width 70, wrap");
		
		panel.add(p);
		return panel;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	//	if(termsAdd==arg0.getSource()){
			
	//	}
	}
	
}
