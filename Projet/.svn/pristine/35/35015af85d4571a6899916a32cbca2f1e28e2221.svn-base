package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import conf.GeneralConfig;
import controller.Controller.ShowProjectResumeListener;

/**
 * Worker invoke to fetched all info needed in the database to display the project resume frame
 * @author S.Trémouille
 *
 */
public class ProjectResumeWorker extends SwingWorker<TreeMap<String,Object>,Boolean> {
    	private ResultSet rs = null;
    	private ArrayList<Database> oldConf;
    
    
	private ShowProjectResumeListener parent;
	
	/**
	 * @param parent
	 * @param oldConf 
	 */
	public ProjectResumeWorker(ShowProjectResumeListener parent,ArrayList<Database> oldConf){
		this.parent=parent;
		this.oldConf=oldConf;
	}
	
	@Override
	protected TreeMap<String,Object> doInBackground() throws Exception {
		String reqComm="SELECT sum(Earned), sum(Estimated)  FROM dbo.TA@DAILY1@REPORT where P_or_C='C'";
		String reqPreComm="SELECT sum(Earned), sum(Estimated)  FROM dbo.TA@DAILY1@REPORT where P_or_C='P'";
		String pLA="select count(PL.PL_Id) from PL inner join PLP on PL.PLP_Id=PLP.PLP_Id where PLP.PLP='A' and PL.Rev_Code='R' and PL.Approve_Date is null";
		String pLB="select count(PL.PL_Id) from PL inner join PLP on PL.PLP_Id=PLP.PLP_Id where PLP.PLP='B' and PL.Rev_Code='R' and PL.Approve_Date is null";
		String pLC="select count(PL.PL_Id) from PL inner join PLP on PL.PLP_Id=PLP.PLP_Id where PLP.PLP='C' and PL.Rev_Code='R' and PL.Approve_Date is null";
		String ssRFC="select count(SYS.SYS_Id) from SYS where RFC_Date is not null and Rev_Code='R'";
		String ssAOC="select count(SYS.SYS_Id) from SYS where RFSU_Date is not null and Rev_Code='R'";
		String ssNumber="select count(SYS.SYS_Id) from SYS where Rev_Code='R'";
		
		String reqCommV4="SELECT sum(Earned), sum(Estimated)  FROM dbo.TA@DAILY@REPORT where P_or_C='C'";
		String reqPreCommV4="SELECT sum(Earned), sum(Estimated)  FROM dbo.TA@DAILY@REPORT where P_or_C='P'";
		String pLAV4="select count(PUNCH_LIST.PL_Id) from PUNCH_LIST inner join PUNCH_LIST_CATEGORY on PUNCH_LIST.PLP_Id=PUNCH_LIST_CATEGORY.PLP_Id where PUNCH_LIST_CATEGORY.PLP='A' and PUNCH_LIST.Rev_Code='R' and PUNCH_LIST.Approve_Date is null";
		String pLBV4="select count(PUNCH_LIST.PL_Id) from PUNCH_LIST inner join PUNCH_LIST_CATEGORY on PUNCH_LIST.PLP_Id=PUNCH_LIST_CATEGORY.PLP_Id where PUNCH_LIST_CATEGORY.PLP='B' and PUNCH_LIST.Rev_Code='R' and PUNCH_LIST.Approve_Date is null";
		String pLCV4="select count(PUNCH_LIST.PL_Id) from PUNCH_LIST inner join PUNCH_LIST_CATEGORY on PUNCH_LIST.PLP_Id=PUNCH_LIST_CATEGORY.PLP_Id where PUNCH_LIST_CATEGORY.PLP='C' and PUNCH_LIST.Rev_Code='R' and PUNCH_LIST.Approve_Date is null";
		String ssRFCV4="select count(SUBSYSTEM.SYS_Id) from SUBSYSTEM where RFC_Date is not null and SUBSYSTEM.Rev_Code='R'";
		String ssAOCV4="select count(SUBSYSTEM.SYS_Id) from SUBSYSTEM where RFSU_Date is not null and SUBSYSTEM.Rev_Code='R'";
		String ssNumberV4="select count(SUBSYSTEM.SYS_Id) from SUBSYSTEM where SUBSYSTEM.Rev_Code='R'";
		
		Iterator<Database> it = GeneralConfig.databases.iterator();
		Integer accCommEarned = 0 ;
		Integer accCommEstimated = 0 ;
		Integer accPreCommEarned = 0 ;
		Integer accPreCommEstimated = 0 ;
		Integer accPLA = 0;
		Integer accPLB = 0;
		Integer accPLC = 0;
		Integer accSSRFC = 0;
		Integer accSSAOC = 0;
		Integer accSSNumber = 0;
		
		float step = (float) (100.0/(8*GeneralConfig.databases.size()));
		float progress = 0;
		
		boolean v4;
		Request r = new Request();
		while(it.hasNext()){
			if(r.connect(it.next())){
			java.sql.Statement stmt = r.getStatement();
			try {
				if(stmt!=null){
					if(r.isR4()){
						v4=true;
					} else {
						v4=false;
					}
					
					//Comm
					if(v4){
						rs= stmt.executeQuery(reqCommV4);
					} else {
						rs= stmt.executeQuery(reqComm);
					}
					while(rs.next()){
						accCommEarned=accCommEarned+rs.getInt(1);
						accCommEstimated=accCommEstimated+rs.getInt(2);
						progress=progress+step;
						setProgress((int) progress);
						firePropertyChange("progress", null, getProgress());
					}
					
					//Precomm
					if(v4){
						rs= stmt.executeQuery(reqPreCommV4);
					} else {
						rs= stmt.executeQuery(reqPreComm);
					}
					while(rs.next()){
						accPreCommEarned=accPreCommEarned+rs.getInt(1);
						accPreCommEstimated=accPreCommEstimated+rs.getInt(2);
						progress=progress+step;
						setProgress((int) progress);
						firePropertyChange("progress", null, getProgress());
					}
					
					//Punch List A
					if(v4){
						rs= stmt.executeQuery(pLAV4);
					} else {
						rs= stmt.executeQuery(pLA);
					}
					while(rs.next()){
						accPLA=accPLA+rs.getInt(1);
						progress=progress+step;
						setProgress((int) progress);
						firePropertyChange("progress", null, getProgress());
					}
					
					//Punch List B
					if(v4){
						rs= stmt.executeQuery(pLBV4);
					} else {
						rs= stmt.executeQuery(pLB);
					}					
					while(rs.next()){
						accPLB=accPLB+rs.getInt(1);
						progress=progress+step;
						setProgress((int) progress);
						firePropertyChange("progress", null, getProgress());
					}
					
					//Punch List C
					if(v4){
						rs= stmt.executeQuery(pLCV4);
					} else {
						rs= stmt.executeQuery(pLC);
					}
					while(rs.next()){
						accPLC=accPLC+rs.getInt(1);
						progress=progress+step;
						setProgress((int) progress);
						firePropertyChange("progress", null, getProgress());
					}
					
					//SubSystems RFC
					if(v4){
						rs= stmt.executeQuery(ssRFCV4);
					} else {
						rs= stmt.executeQuery(ssRFC);
					}
					while(rs.next()){
						accSSRFC=accSSRFC+rs.getInt(1);
						progress=progress+step;
						setProgress((int) progress);
						firePropertyChange("progress", null, getProgress());
					}
					
					//SubSystems AOC
					if(v4){
						rs= stmt.executeQuery(ssAOCV4);
					} else {
						rs= stmt.executeQuery(ssAOC);
					}
					while(rs.next()){
						accSSAOC=accSSAOC+rs.getInt(1);
						progress=progress+step;
						setProgress((int) progress);
						firePropertyChange("progress", null, getProgress());
					}
					
					//SubSystems Number
					if(v4){
						rs= stmt.executeQuery(ssNumberV4);
					} else {
						rs= stmt.executeQuery(ssNumber);
					}
					while(rs.next()){
						accSSNumber=accSSNumber+rs.getInt(1);
						progress=progress+step;
						setProgress((int) progress);
						firePropertyChange("progress", null, getProgress());
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		}
		
		TreeMap<String, Object> res = new TreeMap<String, Object>();
		double progressComm = new Double((int)((new Double(accCommEarned)/new Double(accCommEstimated))*1000.0))/10.0;
		double progressPreComm = new Double((int)((new Double(accPreCommEarned)/new Double(accPreCommEstimated))*1000.0))/10.0;
		res.put("comm", progressComm);
		res.put("precomm", progressPreComm);
		res.put("ssRfc", accSSRFC);
		res.put("ssAoc", accSSAOC);
		res.put("ssNumber", accSSNumber);
		res.put("pla", accPLA);
		res.put("plb", accPLB);
		res.put("plc", accPLC);
		res.put("commEarned", accCommEarned);
		res.put("precommEarned", accPreCommEarned);
		res.put("commEstimated", accCommEstimated);
		res.put("precommEstimated", accPreCommEstimated);
		
		return res;
	}

	@Override
	protected void done() {
	    	try {
		    rs.close();
		} catch (SQLException e) {
		    JOptionPane.showMessageDialog(null, e.getMessage());
		    e.printStackTrace();
		}
	    	GeneralConfig.databases = new ArrayList<Database>();
		Iterator<Database> it = oldConf.iterator();
		while(it.hasNext()){
		    GeneralConfig.databases.add(it.next());
		}
		parent.notifyWorkFinish();
		super.done();
	}
	
	

}
