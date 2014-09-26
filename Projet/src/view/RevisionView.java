package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import revision.RevisionTableModel;

/**
 * Project revision view
 * @author S.Trémouille
 *
 */

public class RevisionView extends JFrame {

	
    	private static final long serialVersionUID = 5890904518131710196L;
	private JPanel contentPane;
	private JButton btnValidate;
	private RevisionTableModel rtm;
	private JTable table;

	/**
	 * Create the frame.
	 * @param data 
	 */
	public RevisionView(ArrayList<TreeMap<String, String>> data) {
		rtm = new RevisionTableModel(data);
		setIconImage(Toolkit.getDefaultToolkit().getImage(RevisionView.class.getResource("/img/icone.png")));
		setTitle("Revision Edition");
		table = new JTable();
		table.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
		table.setFillsViewportHeight(true);
		table.setModel(rtm);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.setAutoCreateRowSorter(true);
		table.setRowSelectionAllowed(true);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(BorderLayout.CENTER, scrollPane);
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		
		JButton btnNewRev = new JButton("New Revision");
		btnNewRev.setIcon(new ImageIcon(RevisionView.class.getResource("/img/newRevision.png")));
		panel.add(btnNewRev);
		
		JButton btnDelete = new JButton("Delete Revision");
		btnDelete.setIcon(new ImageIcon(RevisionView.class.getResource("/img/delete-connection.png")));
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				rtm.deleteRow(table.getSelectedRow());
				rtm.fireTableDataChanged();
			}
		});
		btnDelete.setSelectedIcon(new ImageIcon(RevisionView.class.getResource("/img/delete-connection.png")));
		panel.add(btnDelete);
		
		btnValidate = new JButton("Close Window");
		panel.add(btnValidate);
		btnValidate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewRev.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TreeMap<String, String> rev = new TreeMap<String, String>();
				rtm.getModel().add(rev);
				rtm.fireTableDataChanged();
			}
		});
		
	}
	
}
