package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import model.Milestone;
import model.StartUpSequence;
import model.StartUpStep;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import conf.GeneralConfig;
import conf.Utils;
import controller.Controller.UpdateButtonListener;

/**
 * Update All 
 * @author S.Trémouille
 *	
 */

public class UpdateAllMilestoneWorker extends SwingWorker<Boolean, Boolean>{

	StartUpSequence model;
	UpdateButtonListener ubl;
	File copy;
	
	/**
	 * @param model
	 * @param updateButtonLister
	 */
	public UpdateAllMilestoneWorker(StartUpSequence model,UpdateButtonListener updateButtonLister){
		this.model=model;
		this.ubl=updateButtonLister;
		//Doc
		copy = new File("tmp");
		
	}
	
	@Override
	protected Boolean doInBackground() throws Exception {
	    float step = (float) (100.0/(14.0*(model.getMilestones().size())+1));
	    float p = (float) 0.0;
	    
		if (!isCancelled() && GeneralConfig.SUDocPath != ""	&& new File(GeneralConfig.SUDocPath).exists()) {
			try {
				
				copy.createNewFile();
				try {
					Utils.copyFile(new File(GeneralConfig.SUDocPath), copy);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				}
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
				e1.printStackTrace();
			}
			
			// Documentation
			for (int mi : model.getMilestones().keySet()) {
				if (!isCancelled() && model.getMilestones().size() > 0) {
					// model.getMilestones().get(mi).update(false);
					if (copy.exists()) {
						InputStream is = new FileInputStream(copy);
						XSSFWorkbook wb = new XSSFWorkbook(is);
						FormulaEvaluator evaluator = wb.getCreationHelper()
								.createFormulaEvaluator();
						Sheet s = wb.getSheet(model.getMilestones().get(mi)
								.getName());
						if (s != null) {
							// cellrefence is the cell where to pick the
							// progress
							CellReference cellReference = new CellReference(
									GeneralConfig.excelFileCell);
							CellValue cv = evaluator.evaluate(s.getRow(
									cellReference.getRow()).getCell(
									cellReference.getCol()));
							// System.out.println("Doc :"+Double.valueOf(cv.formatAsString())*100.0);
							model.getMilestones()
									.get(mi)
									.setDocumentationProgress(
											Double.valueOf(cv.formatAsString()) * 100.0);
						}
						FileOutputStream fileOut = new FileOutputStream(copy);
						wb.write(fileOut);
						fileOut.close();
						Utils.copyFile(copy, new File(GeneralConfig.SUDocPath));
					}

					p = p + step;
					System.out.println("pr -> "+p);
					setProgress(Math.round(p));
					firePropertyChange("progress", 0, getProgress());

				} else {
					return false;
				}

				if (model.getMilestone(mi).isOperated()) {
					Calendar curDateCalendar = Calendar.getInstance();
					model.getMilestone(mi).setScopeDoneDate(
							new Date(curDateCalendar.getTime().getTime()));
				}
			}

			System.out.println("End Doc Update All");
		}

	    if(!isCancelled()&&GeneralConfig.SUDocPath!=""&&new File(GeneralConfig.SUDocPath).exists()){
	    	System.out.println("Log Doc Update All");
			//Append log doc
			DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
			Calendar cal = Calendar.getInstance();
			String newLog = "";
			newLog = newLog+dateFormat.format(cal.getTime())+" -EOHEADER- ";
			for(int i : model.getMilestones().keySet()){
				//here we put the name to identify the milestone in order to not have to refer to the startupmodel in the target data loading
				newLog = newLog+model.getMilestones().get(i).getName()+" : "+model.getMilestones().get(i).getDocumentationProgress()+" -EOM- ";
			}
			newLog = newLog+" -EOLOG- ";
			GeneralConfig.logSUDoc = GeneralConfig.logSUDoc + newLog;
			//Append last doc update
			GeneralConfig.lastDocUpdateDate=Calendar.getInstance().getTime();
	    }
	    
		//ICAPS
		Request r = new Request();
		for(int mi : model.getMilestones().keySet()){
		    	if(!isCancelled()){
        			Milestone m = model.getMilestone(mi);
        			System.out.println("Requesting DB for "+m.getName());
        			ArrayList<Integer> matchingSteps = new ArrayList<Integer>();
        			for(int keyStep : model.getStartUpTasks().keySet()){
        				if(model.getStartUpTask(keyStep).getRelatedToThisMilestone().equals(m.getName())){
        					matchingSteps.add(keyStep);
        				}
        			}
        			if(matchingSteps.size()>0){
	        			Iterator<Integer> it = matchingSteps.iterator();
	        			int accDONE=0;
	        			while(it.hasNext()){
	        				if(model.getStartUpTask(it.next()).isOperated()){
	        					accDONE++;
	        				}
	        			}  
	        			m.setStepProgress((double) Math.round((Double.valueOf(accDONE)/Double.valueOf(matchingSteps.size()))*100.0));
        			} else {
        				m.setStepProgress(0.0);
        			}
        			
        			m.setIndeterminated(!r.isMilestoneInDatabase(m.getName()));
        				p=p+step;setProgress(Math.round(p));firePropertyChange("progress", 0, getProgress());
        			m.setInTADAILYREPORT(r.doesMilestoneHaveOneSubsystemInTADAILYREPORT(m.getName()));
        				p=p+step;setProgress(Math.round(p));firePropertyChange("progress", 0, getProgress());
        			m.setPrecommProgress(r.getMilestonePrecommProgress(m.getName()));
        				p=p+step;setProgress(Math.round(p));firePropertyChange("progress", 0, getProgress());
        			m.setCommProgress(r.getMilestoneCommissioningProgress(m.getName()));
        				p=p+step;setProgress(Math.round(p));firePropertyChange("progress", 0, getProgress());	
        			m.setPunchAOpened(r.getPunchAOpenedForMilestone(m.getName()));
        				p=p+step;setProgress(Math.round(p));firePropertyChange("progress", 0, getProgress());
        			m.setPunchBOpened(r.getPunchBOpenedForMilestone(m.getName()));
        				p=p+step;setProgress(Math.round(p));firePropertyChange("progress", 0, getProgress());
        			m.setPunchCOpened(r.getPunchCOpenedForMilestone(m.getName()));
        				p=p+step;setProgress(Math.round(p));firePropertyChange("progress", 0, getProgress());
        			m.setPunchOpened(m.getPunchAOpened()+m.getPunchBOpened()+m.getPunchCOpened());
        				p=p+step;setProgress(Math.round(p));firePropertyChange("progress", 0, getProgress());
        			double d1 = r.getSubSystemNumberAOCForMilestone(m.getName(), new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        			double d2 = r.getSubSystemNumberForMilestone(m.getName());
        			int i = (int) Math.round(((d1/d2)*1000.0));
        			
        			double d3 = i/10.0;
        			m.setAOCProgress(d3);
        				p=p+step;setProgress(Math.round(p));firePropertyChange("progress", 0, getProgress());
        			double e1 = r.getSubSystemNumberRFCForMilestone(m.getName());
        			int j = (int)Math.round(((e1/d2)*1000));
        			double e3 = j/10.0;
        			System.out.println(j);
        			m.setSsRFCProgress(e3);
        				p=p+step;setProgress(Math.round(p));firePropertyChange("progress", 0, getProgress());
        				
    				if(m.getCommProgress()==100&&m.getAOCProgress()==100&&m.getPunchOpened()==0&&m.getDocumentationProgress()==100){
    					m.setOperated(true);
    				} else {
    					m.setOperated(false);
    				}
    				
    				m.setSsNumber(r.getSsNumberForMilestone(m.getName()));
    					p=p+step;setProgress(Math.round(p));firePropertyChange("progress", 0, getProgress());
    					
    				
    					
    				m.updateSsOperatedProgress(r.getSubsystemList(m.getName()));
    					p=p+step;setProgress(Math.round(p));firePropertyChange("progress", 0, getProgress());
		    	}
		    }
		
	    
	    if(isCancelled()){
	    	firePropertyChange("cancel", 0, 1);
	    }
		return true;
	}

	@Override
	protected void done() {
		ubl.notifyFinish();
		super.done();
	}

}
