package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class preView extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					preView frame = new preView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	/**
	 * Create the frame.
	 */
	public preView() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 550, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		contentPane.add(waitingListView(),BorderLayout.CENTER);
	}
	
	public JTable listTable;
	public JButton CheckIn,Remove,Refresh,Add,Reset;
	public JTextField tfFN,tfLN;
	public JComboBox<String> cbTerm,cbLocation,cbLevel,cbDay,cbStart,cbEnd;
	private JScrollPane scrollPane;
	
	
	public JPanel waitingListView(){
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new MigLayout());
		//#listPanel
		JPanel listPanel = new JPanel();
		listPanel.setLayout(new MigLayout("", "[]", "[]"));
		listPanel.setBorder(BorderFactory.createTitledBorder("Waiting List"));
		
		scrollPane = new JScrollPane();
		listPanel.add(scrollPane, "cell 0 0,grow");
		//waiting list table
		listTable = new JTable();
		scrollPane.setViewportView(listTable);
		
		//add listPanel to mainPanel
		mainPanel.add(listPanel,"sg leftPanel,growx");
		
		//#listPanel Controller
		JPanel listPC = new JPanel();
		listPC.setLayout(new MigLayout());
		listPC.setBorder(BorderFactory.createTitledBorder("Controller"));
		CheckIn = new JButton("CheckIn");
		Remove = new JButton("Remove");
		Refresh = new JButton("Refresh");
		listPC.add(CheckIn,"sg controller,wrap");
		listPC.add(Remove,"sg controller,wrap");
		listPC.add(Refresh,"sg controller,wrap");
		//add listPC to mainPanel
		mainPanel.add(listPC,"sg rightPanel,wrap");
		
		//#Editing Fields Panel
		JPanel efPanel = new JPanel();
		efPanel.setLayout(new MigLayout("","[]10[]",""));
		efPanel.setBorder(BorderFactory.createTitledBorder("Editing Fields"));
		JLabel fName = new JLabel("FirstName:");
		JLabel lName = new JLabel("LastName:");
		JLabel term = new JLabel("Term:");
		JLabel location = new JLabel("Location:");
		JLabel level = new JLabel("Level:");
		JLabel day = new JLabel("Day:");
		JLabel sTime = new JLabel("StartTime:");
		JLabel eTime = new JLabel("EndTime:");
		
		tfFN = new JTextField();
		tfLN = new JTextField();
		cbTerm = new JComboBox<String>();
		cbLocation = new JComboBox<String>();
		cbLevel = new JComboBox<String>();
		cbDay = new JComboBox<String>();
		cbStart = new JComboBox<String>();
		cbEnd = new JComboBox<String>();
		
		//adding elements
		efPanel.add(fName,"sg ef");
		efPanel.add(lName,"sg ef");
		efPanel.add(term,"sg ef");
		efPanel.add(location,"sg ef,wrap");
		
		efPanel.add(tfFN,"sg ef");
		efPanel.add(tfLN,"sg ef");
		efPanel.add(cbTerm,"sg ef");
		efPanel.add(cbLocation,"sg ef,wrap");
		
		efPanel.add(level,"sg ef");
		efPanel.add(day,"sg ef");
		efPanel.add(sTime,"sg ef");
		efPanel.add(eTime,"sg ef,wrap");
		
		efPanel.add(cbLevel,"sg ef");
		efPanel.add(cbDay,"sg ef");
		efPanel.add(cbStart,"sg ef");
		efPanel.add(cbEnd,"sg ef,wrap");
		
		mainPanel.add(efPanel,"sg leftPanel,growx");
		
		//#Editing Button Panel
		JPanel ebPanel = new JPanel();
		ebPanel.setLayout(new MigLayout());
		ebPanel.setBorder(BorderFactory.createTitledBorder("Editing"));
		Add = new JButton("Add");
		Reset = new JButton("Reset");
		ebPanel.add(Add,"sg controller,wrap");
		ebPanel.add(Reset,"sg controller,wrap");
		
		mainPanel.add(ebPanel,"sg rightPanel");
		
		return mainPanel;
	}

}
