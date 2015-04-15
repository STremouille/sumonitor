package target;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import model.Milestone;
import model.StartUpSequence;
import conf.GeneralConfig;
import database.Database;

/**
 * 
 * @author S.Trémouille
 *
 */

public class TargetView extends JFrame {
	
    	private static final long serialVersionUID = 8448147308686396649L;
	JProgressBar pBar;
	Target t;
	Milestone m;
	HashMap<String,Object> data;
	TargetWorker task;
	TargetView tv = this;
	List<Database> oldDBConf;
	StartUpSequence model;
	
	/**
	 * @param milestone
	 * @param accuracy
	 * @param projectTarget 
	 * @param oldDbConf 
	 */
	public TargetView(final Milestone milestone,int accuracy,boolean projectTarget, List<Database> oldDbConf,StartUpSequence model){
	    	this.oldDBConf=oldDbConf;
	    	this.model=model;
		setIconImage(Toolkit.getDefaultToolkit().getImage(TargetView.class.getResource("/img/icone.png")));
		setFont(new Font("Arial", Font.PLAIN, 12));
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("Target's computing...");
		pBar=new JProgressBar(0,100);
		pBar.setStringPainted(true);
		pBar.setValue(0);
		this.m=milestone;
		
		this.setVisible(true);
		getContentPane().setLayout(new BorderLayout());
		if(!projectTarget){
		    if(GeneralConfig.cacheForTarget.get(milestone.getName())==null||(((Date) ((HashMap<String, Object>) GeneralConfig.cacheForTarget.get(milestone.getName())).get("targetDate")).getTime()!=milestone.getTargetDate().getTime())){
			task = new TargetWorker(milestone, accuracy,this,false,model);
			task.execute();
			task.addPropertyChangeListener(new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent e) {
					if(e.getPropertyName()=="progress"){
						pBar.setValue((Integer)e.getNewValue());
					} else if(e.getPropertyName()=="init"){
						if(((Integer)e.getNewValue())==0&&(Integer)e.getOldValue()==1){
							pBar.setIndeterminate(false);
							tv.setTitle("Loading data from databases");
						} else if (((Integer)e.getNewValue())==1&&(Integer)e.getOldValue()==0){
							tv.setTitle("Initialisation, please wait.");
							pBar.setIndeterminate(true);
						} 
					} else if(e.getPropertyName()=="checking"){
					    tv.setTitle("Checking if target is calculable...");
					    pBar.setIndeterminate(true);
					} else if(e.getPropertyName()=="abortTA"){
					    dispose();
					    JOptionPane.showMessageDialog(tv, "There is not a single one Task associated to milestone "+milestone.getName());
					} else if(e.getPropertyName()=="abortSSnumber"){
					    dispose();
					    JOptionPane.showMessageDialog(tv, "There is not a single one Task associated to milestone "+milestone.getName());
					} else if(e.getPropertyName()=="abortNotInTADAILYREPORT"){
					    dispose();
					    JOptionPane.showMessageDialog(tv, "There is no commissioning done recorded in database for milestone "+milestone.getName());
					}
				}
			});
        		} else {
        			end();
        		}
		} else {
		       	task = new TargetWorker(milestone, accuracy,this,true,model);
			task.execute();
			task.addPropertyChangeListener(new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent e) {
					if(e.getPropertyName()=="progress"){
						pBar.setValue((Integer)e.getNewValue());
					} else if(e.getPropertyName()=="init"){
						if(((Integer)e.getNewValue())==0&&(Integer)e.getOldValue()==1){
							pBar.setIndeterminate(false);
							tv.setTitle("Loading data from databases");
						} else if (((Integer)e.getNewValue())==1&&(Integer)e.getOldValue()==0){
							tv.setTitle("Initialisation, please wait.");
							pBar.setIndeterminate(true);
						} 
					} else if(e.getPropertyName()=="checking"){
					    tv.setTitle("Checking if target is calculable...");
					    pBar.setIndeterminate(true);
					}
					else if(e.getPropertyName()=="target"){
						    tv.setTitle("Computing target date...");
						    pBar.setIndeterminate(true);
					} else if(e.getPropertyName()=="abortTA"){
					    dispose();
					    JOptionPane.showMessageDialog(tv, "There is not a single one Task associated to milestone "+milestone.getName());
					} else if(e.getPropertyName()=="abortSSnumber"){
					    dispose();
					    JOptionPane.showMessageDialog(tv, "There is not a single one Task associated to milestone "+milestone.getName());
					} else if(e.getPropertyName()=="abortNotInTADAILYREPORT"){
					    dispose();
					    JOptionPane.showMessageDialog(tv, "There is no commissioning done recorded in database for milestone "+milestone.getName());
					}
				}
			});
		}
		getContentPane().add(pBar,BorderLayout.CENTER);
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JButton cancel = new JButton("Cancel");
		cancel.setFont(new Font("Arial", Font.PLAIN, 12));
		cancel.addActionListener(new CancelListener());
		
		GridBagConstraints gbc_cancel = new GridBagConstraints();
		gbc_cancel.gridx = 0;
		gbc_cancel.gridy = 0;
		panel.add(cancel, gbc_cancel);
		this.setSize(new Dimension(400,75));
	}
	
	/**
	 * Notify when all data from databases is fetched 
	 */
	public void end(){
		System.out.print("end");
		this.dispose();
		if(GeneralConfig.cacheForTarget.get(m.getName())==null||(((Date) ((HashMap<String, Object>) GeneralConfig.cacheForTarget.get(m.getName())).get("targetDate")).getTime()!=m.getTargetDate().getTime())){
			Object o = task.getResult();
			((HashMap<String, Object>) o).put("targetDate", m.getTargetDate());
			GeneralConfig.cacheForTarget.put(m.getName(), o);
		} 
		t = new Target(m,(HashMap<String, Object>) GeneralConfig.cacheForTarget.get(m.getName()));
		t.setVisible(true);
		
		GeneralConfig.databases = new ArrayList<Database>();
		Iterator<Database> it = oldDBConf.iterator();
		while(it.hasNext()){
		    GeneralConfig.databases.add(it.next());
		}
		
	}
	

	/**
	 * @param value
	 */
	public void setProgressValue(int value){
		this.pBar.setValue(value);
	}

	/**
	 * @return JProgressBar
	 */
	public JProgressBar getProgressBar() {
		return pBar;
	}
	
	class CancelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			task.cancel(true);
			GeneralConfig.databases = new ArrayList<Database>();
			Iterator<Database> it = oldDBConf.iterator();
			while(it.hasNext()){
			    GeneralConfig.databases.add(it.next());
			}
			//GeneralConfig.cacheForTarget=null;
			dispose();
		}
		
	}
	
}
