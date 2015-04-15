package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import conf.GeneralConfig;
import database.Database;
import database.LINK_TYPE;
import database.Request;
import javax.swing.JCheckBox;
/**
 * 
 * @author S.Trémouille
 *
 */
public class DataBaseFrame extends JFrame {

	/**
     * 
     */
    private static final long serialVersionUID = 1197881465855757539L;
	private JPanel contentPane;
	private JTextField odbcLinkName;
	private final JRadioButton odbc,tcpIp,rdbtnWindowsAuth;
	private JTextField login;
	private JPasswordField passwordField;
	private JTextField milestoneColumn;
	private JTextField serverName;
	private JTextField databaseName;
	private JTextField instanceName;
	private int lineNumber;
	private SettingsFrame parent;
	private JFrame v = this;
	private JTextField alias;

	/**
	 * Create the frame.
	 * @param databaseConnexion 
	 * @param lineNumber 
	 * @param parentFrame 
	 */
	public DataBaseFrame(Database databaseConnexion,final int lineNumber,final SettingsFrame parentFrame) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Database connection's editor");
		setIconImage(Toolkit.getDefaultToolkit().getImage(RevisionView.class.getResource("/img/icone.png")));
		this.parent=parentFrame;
		this.lineNumber=lineNumber;
		setBounds(100, 100, 545, 412);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		JLabel lblAlias = new JLabel("Alias :");
		lblAlias.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(lblAlias, "2, 2, right, default");
		
		alias = new JTextField("");
		panel_1.add(alias, "4, 2, fill, default");
		alias.setColumns(10);
		
		JLabel label_7 = new JLabel("Connection Type :");
		panel_1.add(label_7, "2, 4, right, default");
		
		JPanel panel = new JPanel();
		panel_1.add(panel, "4, 4");
		
		odbc = new JRadioButton("ODBC");
		odbc.setEnabled(true);
		panel.add(odbc);
		
		tcpIp = new JRadioButton("TCP/IP");
		tcpIp.setEnabled(true);
		panel.add(tcpIp);
		
		rdbtnWindowsAuth = new JRadioButton("Windows Auth");
		rdbtnWindowsAuth.setEnabled(true);
		panel.add(rdbtnWindowsAuth);
		
		JLabel label = new JLabel("ODBC Link Name :");
		panel_1.add(label, "2, 6, right, default");
		
		odbcLinkName = new JTextField("");
		panel_1.add(odbcLinkName, "4, 6");
		odbcLinkName.setEnabled(true);
		odbcLinkName.setColumns(10);
		
		JLabel label_1 = new JLabel("Login :");
		panel_1.add(label_1, "2, 8, right, default");
		label_1.setFont(new Font("Arial", Font.PLAIN, 12));
		
		login = new JTextField("");
		panel_1.add(login, "4, 8");
		login.setFont(new Font("Arial", Font.PLAIN, 12));
		login.setEnabled(true);
		login.setColumns(10);
		
		JLabel label_2 = new JLabel("Password :");
		panel_1.add(label_2, "2, 10, right, default");
		label_2.setFont(new Font("Arial", Font.PLAIN, 12));
		
		passwordField = new JPasswordField("");
		panel_1.add(passwordField, "4, 10");
		passwordField.setFont(new Font("Arial", Font.PLAIN, 12));
		passwordField.setEnabled(true);
		passwordField.setColumns(10);
		
		JLabel lblMilestoneColumn = new JLabel("Milestone Column : (only for ICAPS 3.21 or earlier)");
		panel_1.add(lblMilestoneColumn, "2, 12, right, default");
		lblMilestoneColumn.setFont(new Font("Arial", Font.PLAIN, 12));
		
		milestoneColumn = new JTextField("Data2");
		panel_1.add(milestoneColumn, "4, 12");
		milestoneColumn.setFont(new Font("Arial", Font.PLAIN, 12));
		milestoneColumn.setEnabled(true);
		milestoneColumn.setColumns(10);
		
		JLabel label_4 = new JLabel("Server Name :");
		panel_1.add(label_4, "2, 14, right, default");
		label_4.setFont(new Font("Arial", Font.PLAIN, 12));
		
		serverName = new JTextField("");
		panel_1.add(serverName, "4, 14");
		serverName.setFont(new Font("Arial", Font.PLAIN, 12));
		serverName.setColumns(10);
		
		JLabel label_5 = new JLabel("Database Name :");
		panel_1.add(label_5, "2, 16, right, default");
		label_5.setFont(new Font("Arial", Font.PLAIN, 12));
		
		databaseName = new JTextField("");
		panel_1.add(databaseName, "4, 16");
		databaseName.setFont(new Font("Arial", Font.PLAIN, 12));
		databaseName.setColumns(10);
		
		JLabel lblInstanceNameoptional = new JLabel("Instance Name (optional) :");
		panel_1.add(lblInstanceNameoptional, "2, 18, right, default");
		lblInstanceNameoptional.setFont(new Font("Arial", Font.PLAIN, 12));
		
		instanceName = new JTextField("");
		instanceName.setToolTipText("");
		panel_1.add(instanceName, "4, 18");
		instanceName.setFont(new Font("Arial", Font.PLAIN, 12));
		instanceName.setColumns(10);
		
		JButton checkConnection = new JButton("Check Connection");
		panel_1.add(checkConnection, "4, 20, center, center");
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		
		JButton validate = new JButton("Validate");
		validate.addActionListener(new ValidateListener());
		panel_2.add(validate);
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		panel_2.add(cancel);
		
		odbc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tcpIp.setSelected(false);	
				rdbtnWindowsAuth.setSelected(false);
				odbc.setSelected(true);
				
				serverName.setEditable(false);
				instanceName.setEditable(false);
				databaseName.setEditable(false);
				
				odbcLinkName.setEditable(true);
			}
		});
		
		tcpIp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("TCPIP Action Listener");
				tcpIp.setSelected(true);	
				rdbtnWindowsAuth.setSelected(false);
				odbc.setSelected(false);
				
				serverName.setEditable(true);
				instanceName.setEditable(true);
				databaseName.setEditable(true);
				
				odbcLinkName.setEditable(false);
			}
		});
		
		rdbtnWindowsAuth.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("WA Action Listener");
				tcpIp.setSelected(false);	
				rdbtnWindowsAuth.setSelected(true);
				odbc.setSelected(false);
				
				serverName.setEditable(true);
				instanceName.setEditable(true);
				databaseName.setEditable(true);
				
				odbcLinkName.setEditable(false);
			}
		});
		
		checkConnection.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		if(databaseConnexion.getConnexionType()==LINK_TYPE.ODBC){
				odbc.setSelected(true);
				for(ActionListener a: odbc.getActionListeners()) {
				    a.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null) {

					/**
					 * 
					 */
					private static final long serialVersionUID = -1623241431730477833L;
				          //Every ActionListeners registered in the odbc radio button are invoked
				    });
				}
			} else if (databaseConnexion.getConnexionType()==LINK_TYPE.TCPIP) {
				tcpIp.setSelected(true);
				for(ActionListener a: tcpIp.getActionListeners()) {
				    a.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null) {

					/**
					 * 
					 */
					private static final long serialVersionUID = -639297056085871330L;
				          //Every ActionListeners registered in the TCP/IP radio button are invoked
				    });
				}
			} else if (databaseConnexion.getConnexionType()==LINK_TYPE.WINDOWS_AUTH) {
				rdbtnWindowsAuth.setSelected(true);
				for(ActionListener a: rdbtnWindowsAuth.getActionListeners()) {
				    a.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null) {

					/**
					 * 
					 */
					private static final long serialVersionUID = -639297056085871330L;
				          //Every ActionListeners registered in the Windows Auth radio button are invoked
				    });
				}
			
			}
		
		//fill the corresponding field
		alias.setText(databaseConnexion.getAlias());
		login.setText(databaseConnexion.getLogin());
		passwordField.setText(databaseConnexion.getPassword());
		milestoneColumn.setText(databaseConnexion.getMilestoneColumn());
		odbcLinkName.setText(databaseConnexion.getOdbcLinkName());
		instanceName.setText(databaseConnexion.getInstanceName());
		databaseName.setText(databaseConnexion.getDatabaseName());
		serverName.setText(databaseConnexion.getServerURL());
		checkConnection.addActionListener(new ConnectionCheckListerner());
	}
	
	class ConnectionCheckListerner implements ActionListener{

		Database db = new Database();
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(odbc.isSelected()){
				db.setConnexionType(LINK_TYPE.ODBC);
			}
			else if(tcpIp.isSelected()){
				db.setConnexionType(LINK_TYPE.TCPIP);
			} else if(rdbtnWindowsAuth.isSelected()){
				db.setConnexionType(LINK_TYPE.WINDOWS_AUTH);
			}
			db.setLogin(login.getText());
			db.setPassword(new String(passwordField.getPassword()));
			db.setMilestoneColumn(milestoneColumn.getText());
			db.setOdbcLinkName(odbcLinkName.getText());
			db.setInstanceName(instanceName.getText());
			db.setDatabaseName(databaseName.getText());
			db.setServerURL(serverName.getText());
			Request r = new Request();
			r.connect(db);
			if(r.getDataBaseLastUpdateDateForOneDB(db)==null){
				JOptionPane.showMessageDialog(v, "Please launch status & progress update in ICAPS before.");
			} else if(!r.isConnexionError()){
				//System.out.println();
				JOptionPane.showMessageDialog(v, "Connection is ok");
			}
			
		}
		
	}
	
	
	class ValidateListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Database db = new Database();
			db.setAlias(alias.getText());
			if(odbc.isSelected()){
				db.setConnexionType(LINK_TYPE.ODBC);
			}
			else if(tcpIp.isSelected()){
				db.setConnexionType(LINK_TYPE.TCPIP);
			} else if(rdbtnWindowsAuth.isSelected()){
				db.setConnexionType(LINK_TYPE.WINDOWS_AUTH);
			}
			db.setOdbcLinkName(odbcLinkName.getText());
			db.setLogin(login.getText());
			db.setPassword(new String(passwordField.getPassword()));
			db.setMilestoneColumn(milestoneColumn.getText());
			db.setServerURL(serverName.getText());
			db.setDatabaseName(databaseName.getText());
			db.setInstanceName(instanceName.getText());
			//System.out.println("line to remove in db : "+lineNumber);
			if(lineNumber>=0){
				GeneralConfig.databases.remove(lineNumber);
				GeneralConfig.databases.add(lineNumber,db);
			} else {
				GeneralConfig.databases.add(db);
			}
			parent.fireTableDataChanged();
			dispose();
		}
		
	}

	
}

