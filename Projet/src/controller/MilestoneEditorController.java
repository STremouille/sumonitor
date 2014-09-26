package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRootPane;
import javax.swing.WindowConstants;

import model.Milestone;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import target.TargetView;
import view.MilestoneEditorFrame;
import view.SubsytemFrame;
import worker.GetSUDocProgressWorker;
import worker.UpdateSUDocWorker;
import conf.DatePicker;
import conf.GeneralConfig;
import database.Request;
import database.ShowSubsystemRequestWorker;

/**
 * 
 * @author S.Trémouille
 * Controller associated with the milestone view and milestone model
 *
 */

public class MilestoneEditorController {
	MilestoneEditorFrame view;
	Milestone model;
	Controller parent;
	
	
	/**
	 * Constructor following MVC pattern
	 * @param view
	 * @param model
	 * @param parent
	 */
	public MilestoneEditorController(MilestoneEditorFrame view,Milestone model,Controller parent){
		this.view=view;
		this.model=model;
		this.parent=parent;
		
		view.addTargetDatePickerListener(new TargetDatePickerListener());
		view.addCancelListener(new CancelListener());
		view.addValidateListener(new ValidateListener());
		//view.addUpdateCommProgressListener(new UpdateCommProgressListener());
		view.addUpdateSUDocListener(new UpdateDocListener());
		view.addOpenTargetListener(new OpenTargetListener());
		view.addOpenSUSDocListerner(new OpenSUSDocListener());
		view.addShowSubSystemlistener(new ShowSubSystemListener());
	}
	
	
	class TargetDatePickerListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String res = new DatePicker(view).setPickedDate();
			if(res!="")
				view.getTargetDateJTextField().setText(res);
		}
	
	}
	class ValidateListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			validateEdition();
			view.dispose();
		}
		
	}
	
	class CancelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			view.dispose();
		}
	}

	/**
	 * 
	 * @author S.Trémouille
	 *
	 */
	public class UpdateDocListener implements ActionListener{
		JFrame progressFrame = new JFrame();
		JProgressBar bar = new JProgressBar();
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			validateEdition();
			if(GeneralConfig.SUDocPath==""){
				JOptionPane.showMessageDialog(view, "Please specify a path to your .xls file \nEdit -> Settings -> General", "Error",JOptionPane.ERROR_MESSAGE);
			} else {
				UpdateSUDocWorker worker = new UpdateSUDocWorker(parent.getModel().getMilestones(),this,true);
				//System.out.println("Start Update");
				final Executor executor = Executors.newCachedThreadPool();
				executor.execute(worker);
				bar.setIndeterminate(true);
//				progressFrame.setUndecorated(true);
				progressFrame.getContentPane().add(bar);
				bar.setBackground(Color.darkGray);
				bar.setForeground(Color.red);
				progressFrame.pack();
				progressFrame.setLocation(view.getLocation().x+view.getWidth()/2-bar.getWidth()/2,view.getLocation().y+view.getHeight()/2-bar.getHeight()/2);
				progressFrame.setVisible(true);
				view.dispose();
				//GetSUDocProgressWorker docWorker = new GetSUDocProgressWorker(model,modelStartUpSequence);
				//executor.execute(docWorker);
			}
		}

		/**
		 * invoke when update
		 */
		public void notifyWorkFinish() {
			progressFrame.dispose();
			parent.repaintView();
		}
		
	}
	
	/**
	 * @author S.Trémouille
	 * Deprecated
	 *
	 */
	/*public class UpdateCommProgressListener implements ActionListener{
		JProgressBar pBar;
		JDialog dial;
		final UpdateMilestoneWorker task = new UpdateMilestoneWorker(model.getName(), this);
		TreeMap<String, Object> data;
		@Override
		public void actionPerformed(ActionEvent arg0) {
			validateEdition();
			Request r = new Request();
			if(!GeneralConfig.isICAPSLinked){
				JOptionPane.showMessageDialog(view, "ICAPS Link is not setup \nEdit -> Settings -> ICAPS", "Error",JOptionPane.ERROR_MESSAGE);
			} else if(!r.isMilestoneInDatabase(model.getName())){
				JOptionPane.showMessageDialog(view, "Milestone \""+model.getName()+"\" has no subsystem in the database", "Error",JOptionPane.ERROR_MESSAGE);
				model.setIndeterminated(true);
			} else {
			    	model.setIndeterminated(false);
			    	model.setInTADAILYREPORT(r.doesMilestoneHaveOneSubsystemInTADAILYREPORT(model.getName()));
			    	
				dial = new JDialog(view);
				pBar = new JProgressBar(0,100);
				dial.getContentPane().add(pBar);
				dial.setTitle("Loading data from database");
				dial.pack();
				dial.setVisible(true);
				//dial.setSize(view.getWidth(), dial.getHeight());
				dial.setLocation(new Point(view.getX()+view.getWidth()/2-dial.getWidth()/2, view.getY()+view.getHeight()/2-2*dial.getHeight()));
				task.addPropertyChangeListener(new PropertyChangeListener() {
					
					@Override
					public void propertyChange(PropertyChangeEvent e) {
						if(e.getPropertyName()=="progress"){
							pBar.setValue((Integer)e.getNewValue());
						}
					}
				});
				task.execute();
				dial.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
				WindowListener exitListener = new WindowAdapter() {
					
					@Override
					public void windowClosing(WindowEvent e){
						task.cancel(true);
						dial.dispose();
					}
					
				};
				dial.addWindowListener(exitListener);
			}
		}
		public void notifyWorkFinish() {
			try {
				data=task.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			model.setCommProgress((Double) data.get("comm"));
			model.setAOCProgress((Double) data.get("ssAOC"));
			model.setPunchOpened((Integer) data.get("PL"));
			//System.out.println("comm:"+data.get("comm")+"|AOC:"+(Double) data.get("ssAOC")+"|PL"+(Integer) data.get("PL"));
			dial.dispose();
		}
		
	}*/
	
	class OpenTargetListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0){
			validateEdition();
			Request r = new Request();
			if(!r.isMilestoneInDatabase(model.getName())){
				JOptionPane.showMessageDialog(view, "Milestone \""+model.getName()+"\" has no subsystem in the database", "Error",JOptionPane.ERROR_MESSAGE);
				model.setIndeterminated(true);
			} else {
			    	model.setIndeterminated(false);
			    	model.setInTADAILYREPORT(r.doesMilestoneHaveOneSubsystemInTADAILYREPORT(model.getName()));
				TargetView v = new TargetView(model,GeneralConfig.targetAccuracy,false,GeneralConfig.databases,null);
				v.setLocation(new Point(view.getX()+view.getWidth()/2-v.getWidth()/2,view.getY()+view.getHeight()/2-v.getHeight()/2));
			}
		}
		
	}
	
	/**
	 * @author S.Trémouille
	 * Update documentation associated to each milestones & Open it 
	 *
	 */
	public class OpenSUSDocListener implements ActionListener{
		JFrame progressFrame = new JFrame();
		JProgressBar bar = new JProgressBar();
		@Override
		public void actionPerformed(ActionEvent arg0) {
			validateEdition();
			if(GeneralConfig.SUDocPath==""){
				JOptionPane.showMessageDialog(view, "Please specify a path to your .xls file \nEdit -> Settings -> General", "Error",JOptionPane.ERROR_MESSAGE);
			} else {
			UpdateSUDocWorker worker = new UpdateSUDocWorker(parent.getModel().getMilestones(),this,false);
			final Executor executor = Executors.newCachedThreadPool();
			executor.execute(worker);
			progressFrame.getContentPane().add(bar);
			bar.setIndeterminate(true);
			progressFrame.pack();
			progressFrame.setLocation(view.getLocation().x+view.getWidth()/2-bar.getWidth()/2,view.getLocation().y+view.getHeight()/2-bar.getHeight()/2);
			progressFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(TargetView.class.getResource("/img/icone.png")));
			progressFrame.setTitle("Please wait while Excel File is opened");
			
			progressFrame.setVisible(true);
			view.dispose();
			}
		}

		/**
		 * Notify when update is over
		 */
		public void notifyWorkFinish() {
			
			progressFrame.dispose();
			InputStream is;
			try {
				is = new FileInputStream(new File(GeneralConfig.SUDocPath));
				XSSFWorkbook wb = new XSSFWorkbook(is);
				//System.out.println("it should put active the sheet number : "+wb.getSheetIndex(model.getName()));
				wb.setActiveSheet(wb.getSheetIndex(model.getName()));
				wb.setSelectedTab(wb.getSheetIndex(model.getName()));
				is.close();
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace();
			}
			Desktop desktop = Desktop.getDesktop();
			  if (desktop.isSupported(Desktop.Action.OPEN)) {
				  try {
					desktop.open(new File(GeneralConfig.SUDocPath));
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				}
			  }
		}
		
	}

	/**
	 * @param actionListener
	 */
	public void addRepaintListener(ActionListener actionListener) {
		this.view.getValidateButton().addActionListener(actionListener);
		this.view.getCancelButton().addActionListener(actionListener);
	}
	
	
	/**
	 * @author S.Trémouille
	 * ActionListener invoke in order to display progress bar and launch ShowSubSystemRequestWorker
	 */
	public class ShowSubSystemListener implements ActionListener{

		JDialog dial;
		JProgressBar pBar;
		JButton cancelBtn;
		ShowSubsystemRequestWorker task;
		@Override
		public void actionPerformed(ActionEvent arg0) {
			validateEdition();
			Request r = new Request();
			JPanel pan = new JPanel();
			dial = new JDialog(view);
			if(!r.isMilestoneInDatabase(model.getName())){
				JOptionPane.showMessageDialog(view, "Milestone \""+model.getName()+"\" has no subsystem in the database", "Error",JOptionPane.ERROR_MESSAGE);
				model.setIndeterminated(true);
			} else {
			    	model.setIndeterminated(false);
			    	model.setInTADAILYREPORT(r.doesMilestoneHaveOneSubsystemInTADAILYREPORT(model.getName()));
				//dial.setUndecorated(true);
				pBar = new JProgressBar(0,100);
				pan.setLayout(new BorderLayout());
				pan.add(pBar,BorderLayout.CENTER);
				dial.getContentPane().add(pan);
				dial.setTitle("Loading data from database");
				dial.setVisible(true);
				dial.setSize(view.getWidth(), 75);
				dial.setLocation(new Point(view.getX()+view.getWidth()/2-dial.getWidth()/2, view.getY()+view.getHeight()/2-dial.getHeight()/2));
				dial.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
				dial.getRootPane().setWindowDecorationStyle(JRootPane.WARNING_DIALOG);
				if(GeneralConfig.cacheForSSlist.get(model.getName())==null){
					task = new ShowSubsystemRequestWorker(model.getName(),this,false);
					task.addPropertyChangeListener(new PropertyChangeListener() {
						
						@Override
						public void propertyChange(PropertyChangeEvent e) {
							if(e.getPropertyName()=="progress"){
								pBar.setValue((Integer)e.getNewValue());
							}
						}
					});
					task.addPropertyChangeListener(new PropertyChangeListener() {
						
						@Override
						public void propertyChange(PropertyChangeEvent e) {
							if(e.getPropertyName()=="db"){
								dial.setTitle("Loading data from database "+e.getNewValue());
							}
						}
					});
					task.execute();
				} else {
					task = new ShowSubsystemRequestWorker(model.getName(),this,true);
					task.execute();
				}
			}
			
			cancelBtn = new JButton("Cancel");
			cancelBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					task.cancel(true);
					dial.dispose();
				}
			});
			
			JPanel panel = new JPanel();
			dial.add(panel, BorderLayout.SOUTH);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{0, 0};
			gbl_panel.rowHeights = new int[]{0, 0};
			gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			
			GridBagConstraints gbc_cancel = new GridBagConstraints();
			gbc_cancel.gridx = 0;
			gbc_cancel.gridy = 0;
			panel.add(cancelBtn, gbc_cancel);
			
			pan.add(panel,BorderLayout.SOUTH);
			//dial.pack();
			}
		
		
		
		/**
		 * Invoke when worker have done is work
		 * @param dataForSubsystem
		 */
		
		public void notifyWorkFinish(ArrayList<HashMap<String, Object>> dataForSubsystem){
			dial.dispose();
			if(GeneralConfig.cacheForSSlist.get(model.getName())==null){
				GeneralConfig.cacheForSSlist.put(model.getName(), dataForSubsystem);
			}
			SubsytemFrame ssd = new SubsytemFrame(model,(ArrayList<HashMap<String, Object>>) GeneralConfig.cacheForSSlist.get(model.getName()));
			ssd.setVisible(true);
		}
		
	}
	
	/**
	 * Validate Edition
	 */
	public void validateEdition(){
		model.setName(view.getMilestoneNameJTextField().getText());
		model.setDescription(view.getDescriptionJTextArea().getText());
		String rfsuString = view.getTargetDateJTextField().getText();
		SimpleDateFormat f = new SimpleDateFormat("MMM/dd/yyyy");
		try {
			model.setTargetDate(f.parse(rfsuString));
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @param isEnable 
	 * 
	 */
	public void setEnable(boolean isEnable) {
		view.setVisible(isEnable);
	}

}


