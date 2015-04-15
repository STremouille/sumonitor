package database;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.SwingWorker;

import conf.GeneralConfig;
import controller.MilestoneEditorController.ShowSubSystemListener;

/**
 * 
 * @author S.Trémouille
 * 
 * This class is a swing worker which fetch information from databases and after treatment return a subsystem.
 *
 */

public class ShowSubsystemRequestWorker extends SwingWorker<ArrayList<HashMap<String,Object>>, String>{
	boolean v4;
	ResultSet rs = null;	
	String milestoneName ="";
	ShowSubSystemListener parent;
	ArrayList<HashMap<String,Object>> data;
	boolean cacheReady;
	
	/**
	 * @param mildestoneName
	 * @param parent
	 * @param cacheReady
	 */
	public ShowSubsystemRequestWorker(String mildestoneName,ShowSubSystemListener parent,boolean cacheReady){
		this.cacheReady=cacheReady;
		this.milestoneName=mildestoneName;
		this.parent=parent;
		setProgress(0);
	}
	
	@Override
	protected ArrayList<HashMap<String, Object>> doInBackground() throws Exception {
		if(cacheReady){
		    	System.out.println("Cache used");
		    	return (ArrayList<HashMap<String, Object>>) GeneralConfig.cacheForSSlist.get(milestoneName);
		}
		ArrayList<HashMap<String,Object>> res = new ArrayList<HashMap<String,Object>>();
		Request r = new Request();
		Iterator<Database> itDB = GeneralConfig.databases.iterator();
		while(itDB.hasNext()&&!isCancelled()&&!cacheReady){
			Database db = itDB.next();
			firePropertyChange("db", 0, db.getAlias());
			firePropertyChange("progress", 0, getProgress());
			Thread.yield();
			setProgress(0);
			if(r.connect(db)){
        			String request = "SELECT SYS.SYS,sum(TA@DAILY1@REPORT.RemainToDo) as remaintodo,sum(TA@DAILY1@REPORT.Estimated) as estimated,SYS.RFC_Date_Partiel as RFCPartiel,SYS.RFC_Date as RFCDate,SYS.RFSU_Date_Partiel, SYS.RFSU_Date as RFSUDate, SYS.Description, OT.Completion_Date as OT, OT.Ot_Id as OTId, TAA.TAA as TAA, OT.Rev_Code  FROM SYS left join TA@DAILY1@REPORT on SYS.SYS_Id=TA@DAILY1@REPORT.SYS_Id left join OT on SYS.SYS_Id=OT.SYS_Id left join TAA on OT.TAA_ID=TAA.TAA_ID where (SYS."+db.getMilestoneColumn()+" like '"+milestoneName+"') and SYS.Rev_Code='R'  group by SYS,RFC_Date_Partiel,RFC_Date,RFSU_Date_Partiel, RFSU_Date, SYS.Description, OT.Completion_Date,OT.OT_Id,TAA.TAA,OT.Rev_Code order by SYS,OT.Rev_Code DESC , TAA";
        			//TODO Tester en V4
        			//OLD String requestV4 = "SELECT SUBSYSTEM.SYS,sum(TA@DAILY@REPORT.RemainToDo) as remaintodo,sum(TA@DAILY@REPORT.Estimated) as estimated, SUBSYSTEM.RFC_Date_Partiel as RFCPartiel,SUBSYSTEM.RFC_Date as RFCDate,SUBSYSTEM.RFSU_Date_Partiel, SUBSYSTEM.RFSU_Date as RFSUDate , SUBSYSTEM.Description,dbo.TASK_OTP_PVP.Completion_Date, dbo.TASK_OTP_PVP.OT_Id as OTId, dbo.ACTIVITY.TAA as Activity,TASK_OTP_PVP.Rev_Code FROM SUBSYSTEM left join TA@DAILY@REPORT on TA@DAILY@REPORT.SYS=SUBSYSTEM.SYS left join dbo.TASK_OTP_PVP on SUBSYSTEM.SYS_Id=dbo.TASK_OTP_PVP.SYS_Id left join dbo.FORM on dbo.TASK_OTP_PVP.SHT_Id=dbo.FORM.SHT_Id left join dbo.ACTIVITY on dbo.FORM.TAA_ID=dbo.ACTIVITY.TAA_ID where (P_or_C='C') and (SUBSYSTEM.START_UP_TEST='"+milestoneName+"') and SUBSYSTEM.Rev_Code='R' group by SUBSYSTEM.SYS, RFC_Date_Partiel,RFC_Date,RFSU_Date_Partiel, RFSU_Date, SUBSYSTEM.Description,dbo.TASK_OTP_PVP.Completion_Date, dbo.TASK_OTP_PVP.OT_Id,dbo.ACTIVITY.TAA,TASK_OTP_PVP.Rev_Code order by SUBSYSTEM.SYS,dbo.ACTIVITY.TAA,TASK_OTP_PVP.Rev_Code DESC";
        			String requestV4 = "SELECT SUBSYSTEM.SYS,sum(TA@DAILY@REPORT.RemainToDo) as remaintodo,sum(TA@DAILY@REPORT.Estimated) as estimated, SUBSYSTEM.RFC_Date_Partiel as RFCPartiel,SUBSYSTEM.RFC_Date as RFCDate,SUBSYSTEM.RFSU_Date_Partiel, SUBSYSTEM.RFSU_Date as RFSUDate , SUBSYSTEM.Description,dbo.TASK_OTP_PVP.Completion_Date, dbo.TASK_OTP_PVP.OT_Id as OTId, dbo.ACTIVITY.TAA as Activity,TASK_OTP_PVP.Rev_Code FROM SUBSYSTEM left join TA@DAILY@REPORT on TA@DAILY@REPORT.SYS=SUBSYSTEM.SYS left join dbo.TASK_OTP_PVP on SUBSYSTEM.SYS_Id=dbo.TASK_OTP_PVP.SYS_Id left join dbo.FORM on dbo.TASK_OTP_PVP.SHT_Id=dbo.FORM.SHT_Id left join dbo.ACTIVITY on dbo.FORM.TAA_ID=dbo.ACTIVITY.TAA_ID where (SUBSYSTEM.START_UP_TEST like '"+milestoneName+"') and SUBSYSTEM.Rev_Code='R' group by SUBSYSTEM.SYS, RFC_Date_Partiel,RFC_Date,RFSU_Date_Partiel, RFSU_Date, SUBSYSTEM.Description,dbo.TASK_OTP_PVP.Completion_Date, dbo.TASK_OTP_PVP.OT_Id,dbo.ACTIVITY.TAA,TASK_OTP_PVP.Rev_Code order by SUBSYSTEM.SYS,TASK_OTP_PVP.Rev_Code DESC, dbo.ACTIVITY.TAA";
        			Statement stmt=r.getStatement();
        			try {
        				if(stmt!=null){
        					if(r.isR4()){
        					    	System.out.println(requestV4);
        						this.rs= stmt.executeQuery(requestV4);
        						
        					} else {
        						System.out.println(request);
        						this.rs= stmt.executeQuery(request);
        					}
        					
        					String previousSSnumber ="";
        					HashMap<String,Object> subsystem = new HashMap<String, Object>();
        					String currentSSnumber="";
        					while(rs.next() &&!isCancelled()){
        					    
        						String SYS = rs.getString(1);
        						currentSSnumber = SYS;
        						if(!previousSSnumber.equals(currentSSnumber)){
        							subsystem = new HashMap<String, Object>();
        							subsystem.put("SYS", SYS);
        							int toDo=rs.getInt(2);
        							subsystem.put("ToDo", toDo);
        							int estimated = rs.getInt(3);
        							subsystem.put("Done", estimated);
        							if(estimated!=0){
        							    System.out.println("ToDo : "+toDo+"   estimated: "+estimated);
        							    subsystem.put("%", (double)Math.round(((((double)estimated-(double)toDo)/(double)estimated)*100.0) * 10) / 10);
        							    
        							}
        							else
        							    subsystem.put("%", 0.0);
        							Date rfcP = rs.getDate(4);
        							if(rfcP!=null){
        								subsystem.put("RFCDateP", rfcP);
        							} else {
        								subsystem.put("RFCDateP", null);
        							}
        							Date rfc = rs.getDate(5);
        							if(rfc!=null){
        								subsystem.put("RFCDate", rfc);
        							} else {
        								subsystem.put("RFCDate", null);
        							}
        							Date rfsuP = rs.getDate(6);
        							if(rfsuP!=null){
        								subsystem.put("AOCDateP", rfsuP);
        							} else {
        								subsystem.put("AOCDateP", null);
        							}
        							Date rfsu = rs.getDate(7);
        							if(rfsu!=null){
        								subsystem.put("AOCDate", rfsu);
        							} else {
        								subsystem.put("AOCDate", null);
        							}
        							subsystem.put("Description", rs.getString(8));
        						}
        						Date otpCompletion = rs.getDate(9);
        						int otpId = rs.getInt(10);
        						//String taa = rs.getString(11);
        						String otpRevCode = rs.getString(12);
        						/*if(otpRevCode!=null)
        						    System.out.println(otpId + " ----> "+otpRevCode.equals("R"));
        						*/
        						
        						if(otpId>0 /*&& taa.equals(GeneralConfig.OTSFollowUp)*/ && otpRevCode!=null){
                						    if(otpRevCode.equals("R")){
                							if(previousSSnumber.equals(currentSSnumber)){
                							    	System.out.println("multipleOTP for subsystem :"+subsystem.get("SYS")+" because "+previousSSnumber+"="+currentSSnumber );
                							    	subsystem.put("otpToComplete", ((Integer) subsystem.get("otpToComplete"))+1);
                							    if(otpCompletion!=null){
                								System.out.println("Avant le crash!!!!!!!");
                								if(subsystem.get("otpCompleted")==("N/A"))
                								{
                								    System.out.println("Critique");
                								    subsystem.put("otpCompleted", 0);
                								}
                								subsystem.put("otpCompleted", ((Integer) subsystem.get("otpCompleted"))+1);
                							    }
                							} else {
                							    subsystem.put("otpToComplete", 1);
                							    if(otpCompletion!=null){
                								subsystem.put("otpCompleted", 1);
                							    } else {
                								subsystem.put("otpCompleted", 0);
                							    }
                							}
                						    } else if(subsystem.get("otpToComplete")==null){
                							subsystem.put("otpCompleted", "N/A");
                						    }
                						} else{
                							subsystem.put("otpCompleted", "N/A");
                						}
        						subsystem.put("dbAlias", db.getAlias());
        						/*if(previousSSnumber.equals(currentSSnumber)){
        							Iterator<HashMap<String, Object>> it = res.iterator();
        							while(it.hasNext()){
        								it.next();
        							}
        							it.remove();
        						}*/
        						if(!previousSSnumber.equals(currentSSnumber)){
        								res.add(subsystem);
        						}
        						previousSSnumber=(String) subsystem.get("SYS");
        					}
        					float step = (float) (100.0/(res.size()*2+1));
        					System.out.println("pas :"+step);
        					float realP = (float) 0.0;
        					//Request for PL Opened
        					Iterator<HashMap<String, Object>> it=res.iterator();
        					while(it.hasNext()&&!isCancelled()){
        						//System.out.println("1");
        						HashMap<String, Object> line = it.next();
        						int plBOpened = r.getPunchBOpenedForSubsys((String)line.get("SYS"),null);
        						line.put("PLB", plBOpened);
        						realP=realP+step;
        						setProgress(Math.round(realP+step));
        						firePropertyChange("progress", 0, getProgress());
        					}
        					Iterator<HashMap<String, Object>> itt=res.iterator();
        					while(itt.hasNext()&&!isCancelled()){
        						//System.out.println("2");
        						HashMap<String, Object> line = itt.next();
        						int plAOpened = r.getPunchAOpenedForSubsys((String)line.get("SYS"),null);
        						line.put("PLA", plAOpened);
        						realP=realP+step;
        						setProgress(Math.round(realP+step));
        						firePropertyChange("progress", 0, getProgress());
        					}
        				}
        				
        			} catch (SQLException e) {
        				e.printStackTrace();
        			}
        		}
		}
		if(!isCancelled()){
			data=res;
			setProgress(0);
			return res;
		} else {
			return null;
		}
	}

	
	@Override
	protected void done() {
		if(!isCancelled()){
			super.done();
			parent.notifyWorkFinish(data);
		}
	}
}