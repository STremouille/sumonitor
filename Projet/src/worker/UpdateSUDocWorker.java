package worker;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TreeMap;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import model.Milestone;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import view.SettingsFrame.BrowseListener;
import conf.GeneralConfig;
import conf.Utils;
import controller.MilestoneEditorController.OpenSUSDocListener;
import controller.MilestoneEditorController.UpdateDocListener;

/**
 * 
 * @author S.Trémouille
 *
 */

public class UpdateSUDocWorker extends SwingWorker<Boolean, String> {
	TreeMap<Integer, Milestone> milestones;
	boolean writeLogForDocumentationProgress;
	private ActionListener listener;
	
	/**
	 * @param milestonesTreeMap
	 * @param actionListener
	 * @param writeLogForDocumentationProgress
	 */
	public UpdateSUDocWorker(TreeMap<Integer, Milestone> milestonesTreeMap,ActionListener actionListener,boolean writeLogForDocumentationProgress){
		milestones=milestonesTreeMap;
		listener=actionListener;
		this.writeLogForDocumentationProgress=writeLogForDocumentationProgress;
	}
	
	/**
	 * @param milestonesTreeMap
	 * @param writeLogForDocumentationProgress
	 */
	public UpdateSUDocWorker(TreeMap<Integer, Milestone> milestonesTreeMap,boolean writeLogForDocumentationProgress){
		milestones=milestonesTreeMap;
		this.writeLogForDocumentationProgress=writeLogForDocumentationProgress;
	}
	
	@Override
	protected Boolean doInBackground() throws Exception {
		float step = (float) (100.0/(milestones.keySet().size()+1));
		float progress = (float) 0.0;
		try {
			setProgress(0);
			//Work on a copy
			File copy = new File("tmp");
			Utils.copyFile(new File(GeneralConfig.SUDocPath), copy);
			if(copy.exists()){
				InputStream is = new FileInputStream(copy);
				XSSFWorkbook wb = new XSSFWorkbook(is);
				for(Integer i : milestones.keySet()){
					progress=progress+step;
					setProgress((int)progress);
					firePropertyChange("progress", 0, getProgress());
					Milestone m = milestones.get(i);
					
					if(wb.getSheet(m.getName())!=null){
						System.out.println("Redondance : "+m.getName());
					} else {
						Sheet s = wb.cloneSheet(wb.getSheetIndex(wb.getSheet("PATTERN")));
						s.setForceFormulaRecalculation(true);
						wb.setSheetName(wb.getSheetIndex(s),m.getName());
						//STEPS
						/*Sheet steps = wb.cloneSheet(wb.getSheetIndex(wb.getSheet("PATTERN-STEPS")));
						steps.setForceFormulaRecalculation(true);
						wb.setSheetName(wb.getSheetIndex(steps), m.getName()+"-steps");*/
					}
					
				}
				FileOutputStream fileOut;
				fileOut = new FileOutputStream(copy);
				wb.write(fileOut);
				fileOut.close();
				is.close();
				Utils.copyFile(copy, new File(GeneralConfig.SUDocPath));
			}
			
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage()+"-- Please redo an error occur");
			e.printStackTrace();
			GeneralConfig.SUDocPath="";
		}
		if(writeLogForDocumentationProgress){
			DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
			Calendar cal = Calendar.getInstance();
			String newLog = "";
			newLog = newLog+dateFormat.format(cal.getTime())+" -EOHEADER- ";
			for(int i : milestones.keySet()){
				//here we put the name to identify the milestone in order to not have to refer to the startupmodel in the target data loading
				progress=progress+step;
				setProgress((int)progress);
				firePropertyChange("progress", 0, getProgress());
				newLog = newLog+milestones.get(i).getName()+" : "+milestones.get(i).getDocumentationProgress()+" -EOM- ";
			}
			newLog = newLog+" -EOLOG- ";
			GeneralConfig.logSUDoc = GeneralConfig.logSUDoc + newLog;
			//Append last doc update
			GeneralConfig.lastDocUpdateDate=Calendar.getInstance().getTime();
		}
		
		System.out.println("Update end");
		return true;
	}

	@Override
	protected void done() {
		super.done();
		setProgress(100);
		firePropertyChange("progress", 0, getProgress());
		if(listener instanceof OpenSUSDocListener){
			((OpenSUSDocListener) listener).notifyWorkFinish();
		}
		if(listener instanceof UpdateDocListener){
			((OpenSUSDocListener) listener).notifyWorkFinish();
		}
		if(listener instanceof BrowseListener){
			((BrowseListener) listener).initEnded();
		}
	}
	
	
}
