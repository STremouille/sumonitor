package worker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
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
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import conf.GeneralConfig;
import conf.Utils;

/**
 * 
 * @author S.Trémouille
 *
 */

public class GetSUDocProgressWorker extends SwingWorker<Boolean, Milestone> {
	private Milestone m;
	private StartUpSequence model;
	/**
	 * Constructor
	 * @param milestone
	 */
	public GetSUDocProgressWorker(Milestone milestone,StartUpSequence model){
		this.m=milestone;
	}
	
	@Override
	protected Boolean doInBackground() throws Exception {
			
		try {
			System.out.println("SU-Doc-Worker");
			File copy = new File("tmp");
			Utils.copyFile(new File(GeneralConfig.SUDocPath), copy);
			if(copy.exists()){
				InputStream is = new FileInputStream(copy);
				Workbook wb = WorkbookFactory.create(is);
				FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
				Sheet s = wb.getSheet(m.getName());
				if(s!=null){
					//cell reference is the cell where to pick the progress
					CellReference cellReference = new CellReference(GeneralConfig.excelFileCell); 
					CellValue cv = evaluator.evaluate(s.getRow(cellReference.getRow()).getCell(cellReference.getCol()));
					System.out.println("Doc :"+Double.valueOf(cv.formatAsString())*100.0);
					m.setDocumentationProgress(Double.valueOf(cv.formatAsString())*100.0);
					
					System.out.println(Thread.currentThread().getId()+" end.");
					return true;
				}
				
				
				
										
					System.out.println(Thread.currentThread().getId()+" end.");
					return true;
				} 
			
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		
		return false;
	}
	

	@Override
	protected void done() {
		m.docProgressEnd();
		super.done();
	}
	
	

}
