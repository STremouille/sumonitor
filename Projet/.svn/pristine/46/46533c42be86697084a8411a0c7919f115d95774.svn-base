package subsystem;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.SwingWorker;

import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * Worker which implements export to XLSX file behavior
 * @author S.Trémouille
 *
 */
public class ExportSubsystemsToXLSXWorker extends SwingWorker<Boolean, Object> {
	
	private ArrayList<HashMap<String, Object>> data;
	private String path;

	/**
	 * Constructor
	 * @param data
	 * @param path
	 */
	public ExportSubsystemsToXLSXWorker(ArrayList<HashMap<String, Object>> data,String path) {
		this.data=data;
		this.path=path;
	}

	@Override
	protected Boolean doInBackground() throws Exception {
	    System.out.println("Start Exporting");
		if(data==null)
			return false;
		
		File destFile = new File(path);
		if(!destFile.exists()) {
	        destFile.createNewFile();
		}
		FileOutputStream out = new FileOutputStream(path);
		Workbook wb = new XSSFWorkbook();
		FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
		evaluator.evaluateAll();
		Sheet s = wb.createSheet("EXPORT");
		Iterator<HashMap<String, Object>> it = data.iterator();
		double step = 100.0/(data.size()+1);
		double progress = 0.0;
		int i = 1;
		SubSysOperatedSaver ssos=null;
		
		if(!isCancelled()){
        		ssos = new SubSysOperatedSaver();
        		org.apache.poi.ss.usermodel.Row headerRow = s.createRow(0);
        		org.apache.poi.ss.usermodel.Cell sys = headerRow.createCell(0);
        		sys.setCellValue("SYS");
        		org.apache.poi.ss.usermodel.Cell operated = headerRow.createCell(1);
        		operated.setCellValue("Operated");
        		org.apache.poi.ss.usermodel.Cell des = headerRow.createCell(2);
        		des.setCellValue("Description");
        		org.apache.poi.ss.usermodel.Cell todo = headerRow.createCell(3);
        		todo.setCellValue("TO DO (Mhrs)");
        		org.apache.poi.ss.usermodel.Cell done = headerRow.createCell(4);
        		done.setCellValue("Done (Mhrs)");
        		org.apache.poi.ss.usermodel.Cell percentage = headerRow.createCell(5);
        		percentage.setCellValue("Percentage (Mhrs)");
        		org.apache.poi.ss.usermodel.Cell PLA = headerRow.createCell(6);
        		PLA.setCellValue("Remaining Punch Item before RFC");
        		org.apache.poi.ss.usermodel.Cell PLB = headerRow.createCell(7);
        		PLB.setCellValue("Remaining Punch Item before AOC");
        		org.apache.poi.ss.usermodel.Cell RFCDateP = headerRow.createCell(8);
        		RFCDateP.setCellValue("RFC Date Partial (yyyy-mm-dd)");
        		org.apache.poi.ss.usermodel.Cell RFCDate = headerRow.createCell(9);
        		RFCDate.setCellValue("RFC Date (yyyy-mm-dd)");
        		org.apache.poi.ss.usermodel.Cell OtpCompleted = headerRow.createCell(10);
        		OtpCompleted.setCellValue("OTP/PVP Completed");
        		org.apache.poi.ss.usermodel.Cell otpToComplete = headerRow.createCell(11);
        		otpToComplete.setCellValue("Total OTP/PVP Number");
        		org.apache.poi.ss.usermodel.Cell AOCDateP = headerRow.createCell(12);
        		AOCDateP.setCellValue("AOC Date Partial (yyyy-mm-dd)");
        		org.apache.poi.ss.usermodel.Cell AOCDate = headerRow.createCell(13);
        		AOCDate.setCellValue("AOC Date (yyyy-mm-dd)");
        		org.apache.poi.ss.usermodel.Cell Database = headerRow.createCell(14);
        		Database.setCellValue("Database Source");

        		while(it.hasNext()&&!isCancelled()){
        		    
        			HashMap<String, Object> line = it.next();
        			progress=progress+step;
        			firePropertyChange("progress", null, (int)Math.round(progress));
        			org.apache.poi.ss.usermodel.Row row = s.createRow(i);
        			i++;
        			
        			//SYS
        			org.apache.poi.ss.usermodel.Cell cSYS = row.createCell(0);
        			cSYS.setCellValue((String)line.get("SYS"));
        			
        			//Operated
        			org.apache.poi.ss.usermodel.Cell cOperated = row.createCell(1);
        			cOperated.setCellValue(ssos.isAlreadyOperated((String) line.get("SYS")));
        			
        			//Description
        			org.apache.poi.ss.usermodel.Cell cDescription = row.createCell(2);
        			cDescription.setCellValue(line.get("Description").toString());
        			
        			//ToDo
        			org.apache.poi.ss.usermodel.Cell cToDo = row.createCell(3);
        			
        			if(line.get("ToDo")!=null)
        			    cToDo.setCellValue((Integer)line.get("ToDo"));
        			else
        			    cToDo.setCellValue(0);
        			
        			//Done
        			org.apache.poi.ss.usermodel.Cell cDone = row.createCell(4);
        			if(line.get("Done")!=null)
        			    cDone.setCellValue((Integer)line.get("Done"));
        			else
        			    cDone.setCellValue(0);
        			
        			//%
        			org.apache.poi.ss.usermodel.Cell cPercentage = row.createCell(5);
        			cPercentage.setCellValue((Double)line.get("%"));
        			
        			//PLA
        			org.apache.poi.ss.usermodel.Cell cPLA = row.createCell(6);
        			cPLA.setCellValue((Integer)line.get("PLA"));
        			
        			//PLB
        			org.apache.poi.ss.usermodel.Cell cPLB = row.createCell(7);
        			cPLB.setCellValue((Integer)line.get("PLB"));
        			
        			//RFCDateP
        			if(line.get("RFCDateP")!=null){
        				org.apache.poi.ss.usermodel.Cell cRFCDateP = row.createCell(8);
        				cRFCDateP.setCellValue(line.get("RFCDateP").toString());
        			}
        			
        			//RFCDate
        			if(line.get("RFCDate")!=null){
        				org.apache.poi.ss.usermodel.Cell cRFCDate = row.createCell(9);
        				cRFCDate.setCellValue(line.get("RFCDate").toString());
        			}
        			
        			
        			//OTP
        			if(line.get("otpCompleted").equals("N/A")){
        				org.apache.poi.ss.usermodel.Cell cOTP = row.createCell(10);
        				cOTP.setCellValue("N/A");
        				org.apache.poi.ss.usermodel.Cell cOTP1 = row.createCell(11);
        				cOTP1.setCellValue("N/A");
        			} else {
        				org.apache.poi.ss.usermodel.Cell cOTP = row.createCell(10);
        				cOTP.setCellValue((Integer)line.get("otpCompleted"));
        				org.apache.poi.ss.usermodel.Cell cOTP1 = row.createCell(11);
        				cOTP1.setCellValue((Integer)line.get("otpToComplete"));
        			}
        			
        			//AOCDateP
        			if(line.get("AOCDateP")!=null){
        				org.apache.poi.ss.usermodel.Cell cAOCDateP = row.createCell(12);
        				cAOCDateP.setCellValue(line.get("AOCDateP").toString());
        			}
        			
        			//AOCDate
        			if(line.get("AOCDate")!=null){
        				org.apache.poi.ss.usermodel.Cell cAOCDate = row.createCell(13);
        				cAOCDate.setCellValue(line.get("AOCDate").toString());
        			}
        			
        			//Db Alias
        			org.apache.poi.ss.usermodel.Cell cDbAlias = row.createCell(14);
        			cDbAlias.setCellValue(line.get("dbAlias").toString());
			
        		}
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
