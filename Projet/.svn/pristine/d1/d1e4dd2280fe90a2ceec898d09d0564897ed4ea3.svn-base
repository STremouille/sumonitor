package worker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.TreeMap;
import java.util.concurrent.Executor;

import javax.swing.SwingWorker;

import model.Milestone;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import view.SettingsFrame.BrowseListener;
import conf.GeneralConfig;
import conf.Utils;

/**
 * 
 * @author S.Trémouille
 *
 */

public class InitSUDocWorker extends SwingWorker<Byte, Byte>{
	boolean overwrite = false;
	TreeMap<Integer, Milestone> m;
	Executor e;
	BrowseListener sdp;
	
	/**
	 * Constructor
	 * @param overwriting
	 * @param milestonesTreeMap
	 * @param executor
	 * @param browseListener
	 */
	public InitSUDocWorker(boolean overwriting,TreeMap<Integer, Milestone> milestonesTreeMap,Executor executor,BrowseListener browseListener){
		this.overwrite=overwriting;
		this.m=milestonesTreeMap;
		this.e=executor;
		this.sdp=browseListener;
	}
	
	@Override
	protected Byte doInBackground() throws Exception {
		if(overwrite||!(new File(GeneralConfig.SUDocPath).exists())){
			File copy = new File(GeneralConfig.SUDocPath);
			Utils.copyFile(new File(GeneralConfig.SUDocPath), copy);
			if(copy.exists()){
				java.io.InputStream is = new FileInputStream(new File("FOPS-Template.xlsx"));
				OPCPackage opc = OPCPackage.open(is);
				Workbook workbook =WorkbookFactory.create(opc);
				
				
				FileOutputStream fileOut = new FileOutputStream(copy);
				workbook.write(fileOut);
			    fileOut.close();
				
			}
		}
		System.out.println("End Init");
		this.done();
		return 0x01;
	}

	@Override
	protected void done() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		GeneralConfig.initEnded(m,e,sdp);
	}
	
	

}
