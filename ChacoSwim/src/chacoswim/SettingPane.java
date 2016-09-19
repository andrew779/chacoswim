package chacoswim;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;
import net.proteanit.sql.DbUtils;

public class SettingPane implements ActionListener{

	private JButton add;
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
			pst.close();
			rs.close();
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * getTerms panel
	 * @return
	 */
	public JPanel getTerms(){
		//Connection conn=sqliteConnection.dbConnector();
		JPanel panel = getPanel("Terms");
		panel.setLayout(new MigLayout("","[]10[]"));
		//create elements for term panel
		JTable table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		JLabel name = new JLabel("Term: ");
		JTextField tfname = new JTextField();
		JLabel type = new JLabel("Type: ");
		JTextField tftype = new JTextField();
		add  = new JButton("Add");
		JButton update = new JButton("Update");
		JButton delete = new JButton("Delete");
		fillTable(table,"terms");
		
		panel.add(scrollPane, "span");
		panel.add(name, "right,sg 1");
		panel.add(tfname,"width 10:150:200, span, pushx, growx, wrap");
		panel.add(type,"right,sg 1");
		panel.add(tftype,"width 10:150:200,span,pushx, growx, wrap");
		panel.add(add,"skip,split 3,width 70");
		panel.add(update,"width 70");
		panel.add(delete,"width 70");
		
		add.addActionListener(this);
		return panel;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(add==arg0.getSource()){
			JOptionPane.showMessageDialog(null, "Add button");
		}
	}
	
}
