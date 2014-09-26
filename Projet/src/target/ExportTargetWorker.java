package target;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import javax.swing.SwingWorker;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import subsystem.SubSysOperatedSaver;


/**
 * @author Samuel Trémouille
 */
public class ExportTargetWorker extends SwingWorker<Boolean, Object> {
	
    	private ArrayList<Date> date;
	private ArrayList<Integer> progress, nbreSSRFSU, punchlistOpened;
	TreeMap<Date, Integer> docProgress;
	private String path;

	/**
	 * Constructor
	 * @param date 
	 * @param progress 
	 * @param nbreSSRFSU 
	 * @param punchlistOpened 
	 * @param path
	 */
	public ExportTargetWorker(ArrayList<Date> date,ArrayList<Integer> progress,ArrayList<Integer> nbreSSRFSU,ArrayList<Integer> punchlistOpened,TreeMap<Date, Integer> docProgress2,String path) {
		this.date=date;
		this.progress=progress;
		this.nbreSSRFSU=nbreSSRFSU;
		this.punchlistOpened=punchlistOpened;
		this.docProgress=docProgress2;
		this.path=path;
	}

	@Override
	protected Boolean doInBackground() throws Exception {
	    System.out.println("Start Exporting");
		if(date==null)
			return false;
		
		File destFile = new File(path);
		if(!destFile.exists()) {
	        destFile.createNewFile();
		}
		FileOutputStream out = new FileOutputStream(path);
		Workbook wb = new XSSFWorkbook();
		FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
		evaluator.evaluateAll();
		Sheet comm = wb.createSheet("Comm. EXPORT");
		Sheet aoc = wb.createSheet("AOC EXPORT");
		Sheet PL = wb.createSheet("PL EXPORT");
		Sheet doc = wb.createSheet("Doc EXPORT");
		Iterator<Date> itDate = date.iterator();
		Iterator<Integer> itProgress = progress.iterator();
		Iterator<Integer> itPL = punchlistOpened.iterator();
		Iterator<Integer> itSSAOC = nbreSSRFSU.iterator();
		Iterator<Date> itDocProgressDate = docProgress.keySet().iterator();
		
		double step = 100.0/(date.size()+1);
		double progress = 0.0;
		int i = 1;
		
		if(!isCancelled()){
    		org.apache.poi.ss.usermodel.Row headerRowComm = comm.createRow(0);
    		org.apache.poi.ss.usermodel.Row headerRowAOC = aoc.createRow(0);
    		org.apache.poi.ss.usermodel.Row headerRowPL = PL.createRow(0);
    		org.apache.poi.ss.usermodel.Row headerRowDOC = doc.createRow(0);
    		
    		org.apache.poi.ss.usermodel.Cell dateComm = headerRowComm.createCell(0);
    		dateComm.setCellValue("Date");
    		org.apache.poi.ss.usermodel.Cell commHeader = headerRowComm.createCell(1);
    		commHeader.setCellValue("Comm Progress");
    		
    		org.apache.poi.ss.usermodel.Cell dateAOC = headerRowAOC.createCell(0);
    		dateAOC.setCellValue("Date");
    		org.apache.poi.ss.usermodel.Cell AOCHeader = headerRowAOC.createCell(1);
    		AOCHeader.setCellValue("SS AOC");
    		
    		org.apache.poi.ss.usermodel.Cell datePL = headerRowPL.createCell(0);
    		datePL.setCellValue("Date");
    		org.apache.poi.ss.usermodel.Cell PLHeader = headerRowPL.createCell(1);
    		PLHeader.setCellValue("Punch Items Opened");
    		
    		org.apache.poi.ss.usermodel.Cell dateDOC = headerRowDOC.createCell(0);
    		dateDOC.setCellValue("Date");
    		org.apache.poi.ss.usermodel.Cell DOC = headerRowDOC.createCell(1);
    		DOC.setCellValue("Doc Progress");
    		
    		while(itDate.hasNext()&&!isCancelled()){
    		    
    			progress=progress+step;
    			firePropertyChange("progress", null, (int)Math.round(progress));
    			org.apache.poi.ss.usermodel.Row rowComm = comm.createRow(i);
    			org.apache.poi.ss.usermodel.Row rowAOC = aoc.createRow(i);
    			org.apache.poi.ss.usermodel.Row rowPL = PL.createRow(i);
    			i++;
    			
    			//DATE
    			Date currentDate = itDate.next();
    			org.apache.poi.ss.usermodel.Cell cDATEComm = rowComm.createCell(0);
    			cDATEComm.setCellValue(currentDate);
    			cDATEComm.setCellType(Cell.CELL_TYPE_NUMERIC);
    			org.apache.poi.ss.usermodel.Cell cDATEAOC = rowAOC.createCell(0);
    			cDATEAOC.setCellValue(currentDate);
    			cDATEAOC.setCellType(Cell.CELL_TYPE_NUMERIC);
    			org.apache.poi.ss.usermodel.Cell cDATEPL = rowPL.createCell(0);
    			cDATEPL.setCellValue(currentDate);
    			cDATEPL.setCellType(Cell.CELL_TYPE_NUMERIC);
    			
    			
    			//PROGRESS
    			org.apache.poi.ss.usermodel.Cell cProgress = rowComm.createCell(1);
    			cProgress.setCellValue(itProgress.next());
    			
    			//SS AOC
    			org.apache.poi.ss.usermodel.Cell cSSAOC = rowAOC.createCell(1);
    			cSSAOC.setCellValue(itSSAOC.next());
    			
    			//PL
    			org.apache.poi.ss.usermodel.Cell cPL = rowPL.createCell(1);
    			cPL.setCellValue(itPL.next());
    			
    		}
		}
		int j = 1;
		for(Date d : docProgress.keySet()){
		    org.apache.poi.ss.usermodel.Row rowDoc = doc.createRow(j);
		    org.apache.poi.ss.usermodel.Cell cDATEDoc = rowDoc.createCell(0);
 		    cDATEDoc.setCellValue(d);
 		    cDATEDoc.setCellType(Cell.CELL_TYPE_NUMERIC);
		    org.apache.poi.ss.usermodel.Cell cDoc = rowDoc.createCell(1);
		    cDoc.setCellValue(docProgress.get(d));
		    j++;
		}
		
		wb.write(out);
		out.close();
		firePropertyChange("end", null, null);
		System.out.println("End Exporting");
		return true;
	}

	@Override
	protected void done() {
		super.done();
	}
	
	

}