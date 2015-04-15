package worker;

import java.io.File;
import java.io.IOException;
import java.util.TimerTask;

import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import model.StartUpSequence;
import view.View;
import xml.SaveXMLFile;

/**
 * 
 * @author S.Trémouille
 *
 */

public class AutoSaveTask extends TimerTask{
	
	StartUpSequence model;
	View view;
	
	/**
	 * Constructor
	 * @param model
	 * @param view
	 */
	public AutoSaveTask(StartUpSequence model, View view){
		this.model=model;
		this.view=view;
	}
	
	@Override
	public void run() {
		//cancel if too late for execution
		if ((System.currentTimeMillis() - scheduledExecutionTime() >= 5*scheduledExecutionTime())||(model.getMilestones().size()==0&&model.getSequences().size()==0&&model.getComments().size()==0))
		               return;
		File newfops = new File("autosave.xml");

		//Save
		SaveXMLFile save = new SaveXMLFile();
		try {
			save.doIt(model, newfops.getPath());
		} catch (ParserConfigurationException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (TransformerException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}

	

}
