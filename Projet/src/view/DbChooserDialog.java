package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import javax.swing.JLabel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import conf.GeneralConfig;
import controller.Controller.ShowProjectResumeListener;
import controller.Controller.ShowProjectTargetListener;
import database.Database;

import javax.swing.JCheckBox;

import model.Milestone;
import model.StartUpSequence;

import java.awt.Font;

/**
 * @author Samuel Trémouille
 */
public class DbChooserDialog extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private ShowProjectTargetListener showProjectTargetListener;
    private ShowProjectResumeListener showProjectResumeListener;

    /**
     * Launch the application.
     
    public static void main(String[] args) {
	try {
	    DbChooserDialog dialog = new DbChooserDialog(null);
	    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	    dialog.setVisible(true);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
     */
    /**
     * Create the dialog.
     * @param showProjectTargetListener 
     */
    public DbChooserDialog(final ShowProjectTargetListener showProjectTargetListener, final ShowProjectResumeListener showProjectResumeListener ) {
    	setTitle("Database Picker");
	this.showProjectTargetListener=showProjectTargetListener;
	this.showProjectResumeListener = showProjectResumeListener;
    	setIconImage(Toolkit.getDefaultToolkit().getImage(DbChooserDialog.class.getResource("/img/icone.png")));
	setBounds(100, 100, 450, 300);
	
	final HashMap<String, JCheckBox> cb = new HashMap<String, JCheckBox>();
	Iterator<Database> it = GeneralConfig.databases.iterator();
	while(it.hasNext()){
	    String dbAlias=it.next().getAlias();
	    JCheckBox tmp = new JCheckBox(dbAlias);
	    tmp.setSelected(true);
	    cb.put(dbAlias, tmp);
	}
	getContentPane().setLayout(new BorderLayout());
	contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	getContentPane().add(contentPanel, BorderLayout.CENTER);
	contentPanel.setLayout(new BorderLayout(0, 0));
	{
		JLabel lblPleaseSelectDatabases = new JLabel("Please select databases:");
		lblPleaseSelectDatabases.setFont(new Font("Arial", Font.PLAIN, 14));
		contentPanel.add(lblPleaseSelectDatabases, BorderLayout.NORTH);
	}
	{
		JPanel panel = new JPanel();
		contentPanel.add(panel, BorderLayout.CENTER);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				}
			));
		int i = 0;
		it = GeneralConfig.databases.iterator();
		while(it.hasNext())
		{
			panel.add(cb.get(it.next().getAlias()), "2, "+String.valueOf(2*(i+1))+", left, default");
			i++;
		}
	}
	{
	    JPanel buttonPane = new JPanel();
	    buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
	    getContentPane().add(buttonPane, BorderLayout.SOUTH);
	    {
		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		okButton.addActionListener(new ActionListener() {
		    
		    @Override
		    public void actionPerformed(ActionEvent arg0) {
			
			
			
			ArrayList<Database> old = new ArrayList<Database>();
			//COPY
			Iterator<Database> it = GeneralConfig.databases.iterator();
			
			while(it.hasNext()){
			    Database db = it.next();
			    Database oldDb = new Database();
			    oldDb.copy(db);
			    old.add(oldDb);
			}
			
			// RESET ALL ACTIVATED
			it = GeneralConfig.databases.iterator();
			while(it.hasNext()){
			    it.next().setActivated(false);
			}
			
			//ACTIVATION OF SELECTED ONES
			for(String s : cb.keySet()){
			    if(cb.get(s).isSelected()){
				Iterator<Database> itDB = GeneralConfig.databases.iterator();
				while(itDB.hasNext()){
				    Database db = itDB.next();
				    if(db.getAlias().equals(s)){
		    			db.setActivated(true);
				    }
				}
			    }
			}
			if(showProjectTargetListener!=null)
			    showProjectTargetListener.dbChoosed(old);
			if(showProjectResumeListener!=null)
			    showProjectResumeListener.dbChoosed(old);
			dispose();
		    }
		});
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
	    }
	    {
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
		    
		    @Override
		    public void actionPerformed(ActionEvent arg0) {
			dispose();
		    }
		});
	    }
	}
    }

}
