package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.JOptionPane;

import conf.GeneralConfig;

/**
 * 
 * @author S.Trémouille
 *
 */

public class Request {
	private int connexionError;
	
	// Connection Parameters
	String USER = "";
	String PASS = "";
	String DATABASENAME = "";
	String COMPUTERNAME = "";
	String URL = "jdbc:sqlserver://";
	String ODBCDRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	private Database db;
	
	boolean icapsV4;
	
	// SQL Request
	// R3
	//String requestAllMilestonesProgress = "SELECT SYS."+milestoneColumnInSys+",(100*(1-sum(TA@DAILY1@REPORT.RemainToDo)/SUM( TA@DAILY1@REPORT.Estimated))) as Progress FROM TA@DAILY1@REPORT inner join SYS on TA@DAILY1@REPORT.SYS_Id=SYS.SYS_Id where (P_or_C='C') group by sys.Data11 order by sys.Data11;";
	
	
	
	// R4
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	Properties props = null;
	
	/**
	 * @return Statement
	 */
	public Statement getStatement(){
		return stmt;
	}

	/**
	 * @return Connection
	 */
	public Connection getConnection() {
		return conn;
	}

	/**
	 * @param db (Database)
	 * @return boolean
	 */
	public boolean connect(Database db){
		connexionError=0;
		this.db=db;
		if(db.isActivated()){
			//System.out.println("Icaps linked");
			props = new Properties();
			props.setProperty("user",db.getLogin());
			props.setProperty("password",db.getPassword());
			
			try {
				  props.setProperty("autoReconnect", "true");
				if(db.getConnexionType()==LINK_TYPE.ODBC){
					try {
						Class.forName(ODBCDRIVER).newInstance();
						URL = "jdbc:odbc:"+db.getOdbcLinkName();
						conn = DriverManager.getConnection(URL,props);
					} catch (InstantiationException e) {
						//System.out.println("Error 1");
						incError();
						JOptionPane.showMessageDialog(null, e.getMessage());
						e.printStackTrace(System.out);
					} catch (IllegalAccessException e) {
						//System.out.println("Error 2");
						incError();
						JOptionPane.showMessageDialog(null, e.getMessage());
						e.printStackTrace(System.out);
					} catch (ClassNotFoundException e) {
						//System.out.println("Error 3");
						incError();
						JOptionPane.showMessageDialog(null, e.getMessage());
						e.printStackTrace(System.out);
					}
					} else if(db.getConnexionType()==LINK_TYPE.TCPIP){
					    	URL="jdbc:sqlserver://"+db.getServerURL()+"\\"+db.getInstanceName();
					    	conn = DriverManager.getConnection(URL+";databaseName="+db.getDatabaseName()+";",props);
					} else if(db.getConnexionType()==LINK_TYPE.WINDOWS_AUTH){
						System.out.println("Windows Auth connection");
						URL="jdbc:sqlserver://"+db.getServerURL()+"\\"+db.getInstanceName();
				    	conn = DriverManager.getConnection(URL+";databaseName="+db.getDatabaseName()+";integratedSecurity=true;",props);
						
					}
				stmt = conn.createStatement();
			} catch (SQLException e) {
				incError();
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
				return false;
			}
		} else {
		    //JOptionPane.showMessageDialog(null, db.getAlias()+" database isn't linked in Settings");
		    return false;
		}
		this.seeIfR4();
		this.getDataBaseLastUpdateDateForOneDB(db);
		return true;
	}
	
	/**
	 * @return true if database is R4
	 */
	public boolean isR4(){
	    return icapsV4;
	}
	
	/**
	 * function checking version of the database
	 */
	public void seeIfR4(){
	    String isICAPSversion4 = "IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='Project') SELECT 1 AS res ELSE SELECT 0 AS res;";
	    
		int res = -1;
		if(stmt!=null){
			try {
				//System.out.println(isICAPSversion4);
				
				this.rs= stmt.executeQuery(isICAPSversion4);
				while(rs.next()){
					res=rs.getInt("res");
				}
			} catch (SQLException e) {
				incError();
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		if(res==0){
			icapsV4=false;
			//System.out.println("Icaps not V4");
		} else if(res==1){
			icapsV4=true;
			//System.out.println("Icaps V4 detected");
		} 
	}
	
	private void incError(){
		connexionError++;
	}
	
	/**
	 * @param name
	 * @return MilestoneCommissioningProgress
	 */
	public Double getMilestoneCommissioningProgress(String name) {
		
		Double estimatedTotal = 0.0;
		Double remainTotal = 0.0;
		Iterator<Database> dbIterator = GeneralConfig.databases.iterator();
		while(dbIterator.hasNext()){
			
			if(this.connect(dbIterator.next())){
			String request= "SELECT sum(TA@DAILY1@REPORT.RemainToDo) as Remain,sum(TA@DAILY1@REPORT.Estimated) as Estimated FROM TA@DAILY1@REPORT inner join SYS on TA@DAILY1@REPORT.SYS_Id=SYS.SYS_Id where (P_or_C='C') and (sys."+db.getMilestoneColumn()+" like '"+name+"') ";
			String requestV4= "SELECT sum(TA@DAILY@REPORT.RemainToDo) as Remain,sum(TA@DAILY@REPORT.Estimated) as Estimated FROM TA@DAILY@REPORT inner join SUBSYSTEM on TA@DAILY@REPORT.SYS=SUBSYSTEM.SYS where (P_or_C='C') and (SUBSYSTEM.START_UP_TEST like '"+name+"') ";
			try {
				if(stmt!=null){
					//System.out.println("HOP");
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4);
					} else {
						this.rs= stmt.executeQuery(request);
					}
					while(rs.next()){
						remainTotal=remainTotal+rs.getDouble(1);
						estimatedTotal=estimatedTotal+rs.getDouble(2);
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		}
		return ((estimatedTotal-remainTotal)/estimatedTotal)*100.0;
	}
	
	/**
	 * @param name
	 * @return MilestoneCommissioningProgressForOneDB
	 */
	public int getMilestoneCommissioningProgressForOneDB(String name) {
		
	    int estimatedTotal = 0;
	    int remainTotal = 0;
			String request= "SELECT sum(TA@DAILY1@REPORT.RemainToDo) as Remain,sum(TA@DAILY1@REPORT.Estimated) as Estimated FROM TA@DAILY1@REPORT inner join SYS on TA@DAILY1@REPORT.SYS_Id=SYS.SYS_Id where (P_or_C='C') and (sys."+db.getMilestoneColumn()+" like '"+name+"') ";
			String requestV4= "SELECT sum(TA@DAILY@REPORT.RemainToDo) as Remain,sum(TA@DAILY@REPORT.Estimated) as Estimated FROM TA@DAILY@REPORT inner join SUBSYSTEM on TA@DAILY@REPORT.SYS=SUBSYSTEM.SYS where (P_or_C='C') and (SUBSYSTEM.START_UP_TEST like '"+name+"') ";
			try {
				if(stmt!=null){
					//System.out.println("HOP");
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4);
					} else {
						this.rs= stmt.executeQuery(request);
					}
					while(rs.next()){
						remainTotal=remainTotal+rs.getInt(1);
						estimatedTotal=estimatedTotal+rs.getInt(2);
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		return estimatedTotal-remainTotal;
	}
	
	/**
	 * @param name
	 * @return FirstDateOfMilestone (first date in database where the milestone have progress)
	 */
	public Date getFirstDateOfMilestone(String name){
		
		//System.out.println(request);
		Date finalRes = null;
		Iterator<Database> it = GeneralConfig.databases.iterator(); 
		while(it.hasNext()){
		    if(this.connect(it.next())){
			String request = "select  min(TA.Completion_Date) as mintask FROM TA INNER JOIN "+
	                    "TAA ON TA.TAA_ID = TAA.TAA_Id INNER JOIN "+
	                    "SYS ON TA.SYS_Id = SYS.SYS_Id "+
	                    "Where TAA.TAA in ('PRC','FTS','PVP','OTP') and SYS."+db.getMilestoneColumn()+" like '"+name+"' and TA.Rev_Code='R'"+
	                    " union "+
	                    "select  min(OT.Completion_Date) as mintask "+
	                    "FROM  OT INNER JOIN"+
	                    " SYS ON OT.SYS_Id = SYS.SYS_Id"+
	                    " Where OT.Rev_Code='R' and SYS."+db.getMilestoneColumn()+" like '"+name+"'"+
	                    " union "+
	                    "select  min(PL.Issue_Date) as mintask "+
	                    "from PL INNER JOIN "+
	                    "SYS ON PL.SYS_Id = SYS.SYS_Id "+
	                    "where PL.Rev_Code='R' and SYS."+db.getMilestoneColumn()+" like '"+name+"'"+
	                    " union "+
	                    "select  min(SYS.RFSU_Date) as mintask "+
	                    "from SYS "+
	                    "where SYS.Rev_Code='R' and SYS."+db.getMilestoneColumn()+" like '"+name+"';";
			String requestV4 = "select  min(TASK_PCOM_COM_NOT_OTP_PVP.Completion_Date) as mintask FROM TASK_PCOM_COM_NOT_OTP_PVP INNER JOIN "+
		              "FORM ON TASK_PCOM_COM_NOT_OTP_PVP.SHT_ID = FORM.SHT_Id "+
						" INNER JOIN ACTIVITY on FORM.TAA_ID=ACTIVITY.TAA_ID "+
						"INNER JOIN SUBSYSTEM ON TASK_PCOM_COM_NOT_OTP_PVP.SYS_Id = SUBSYSTEM.SYS_Id "+
		              "Where ACTIVITY.TAA in ('PRC','FTS','PVP','OTP') and SUBSYSTEM.START_UP_TEST like '"+name+"' and TASK_PCOM_COM_NOT_OTP_PVP.Rev_Code='R'"+
		              " union "+
		              "select  min(TASK_OTP_PVP.Completion_Date) as mintask "+
		              "FROM  TASK_OTP_PVP INNER JOIN"+
		              " SUBSYSTEM ON TASK_OTP_PVP.SYS_Id = SUBSYSTEM.SYS_Id"+
		              " Where TASK_OTP_PVP.Rev_Code='R' and SUBSYSTEM.START_UP_TEST like '"+name+"'"+
		              " union "+
		              "select  min(PUNCH_LIST.Issue_Date) as mintask "+
		              "from PUNCH_LIST INNER JOIN "+
		              "SUBSYSTEM ON PUNCH_LIST.SYS_Id = SUBSYSTEM.SYS_Id "+
		              "where PUNCH_LIST.Rev_Code='R' and SUBSYSTEM.START_UP_TEST like '"+name+"'"+
		              " union "+
		              "select  min(SUBSYSTEM.RFSU_Date) as mintask "+
		              "from SUBSYSTEM "+
		              "where SUBSYSTEM.Rev_Code='R' and SUBSYSTEM.START_UP_TEST like '"+name+"';";
			System.out.println(request);
			Date res = null;
			try {
				if(stmt!=null){
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4);
					} else {
						this.rs= stmt.executeQuery(request);
						System.out.println("First date rq : \n "+request);
					}
					
					while(rs.next()){
						Date tmp = rs.getDate(1);
						if(tmp!=null){
							if(res==null || res.getTime()>tmp.getTime()){
								res=tmp;
							}
						}
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
			if((finalRes==null&&res!=null)||(finalRes!=null&&res!=null&&finalRes.getTime()>res.getTime())){
				finalRes=res;
			}
		}
		}
		return finalRes;
	}
	
	/**
	 * @param milestoneName
	 * @return SubSystemNumberForMilestone
	 */
	public int getSubSystemNumberForMilestone(String milestoneName){
		int acc = 0;
		
		Iterator<Database> it = GeneralConfig.databases.iterator(); 
		while(it.hasNext()){
		    if(this.connect(it.next())){
			String request = "select count(SYS) from dbo.SYS where "+db.getMilestoneColumn()+" like '"+milestoneName+"' and SYS.Rev_Code='R'";
			String requestV4 = "select count(SYS) from dbo.SUBSYSTEM where START_UP_TEST like '"+milestoneName+"' and SUBSYSTEM.Rev_Code='R'";
			try {
				if(stmt!=null){
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4);
					} else {
						this.rs= stmt.executeQuery(request);
					}
					while(rs.next()){
						if(!rs.wasNull()){
							acc=acc+rs.getInt(1);
						} else {
							//System.out.println("Noule");
						}
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		}
		return acc;
	}
	
	/**
	 * @param milestoneName
	 * @return SubSystemNumberForMilestoneForOneDB
	 */
	public int getSubSystemNumberForMilestoneForOneDB(String milestoneName){
			String request = "select count(SYS) from dbo.SYS where "+db.getMilestoneColumn()+" like '"+milestoneName+"' and SYS.Rev_Code='R'";
			String requestV4 = "select count(SYS) from dbo.SUBSYSTEM where START_UP_TEST like '"+milestoneName+"' and SUBSYSTEM.Rev_Code='R'";
			try {
				if(stmt!=null){
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4);
					} else {
						this.rs= stmt.executeQuery(request);
					}
					while(rs.next()){
						if(!rs.wasNull()){
							return rs.getInt(1);
						} else {
							//System.out.println("Noule");
						}
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		return -1;
	}
	
	/**
	 * @param milestoneName
	 * @param db
	 * @return SubSystemNumberForMilestoneWithoutConnection
	 */
	public int getSubSystemNumberForMilestoneWithoutConnection(String milestoneName,Database db){
		String request = "select count(SYS) from dbo.SYS where "+db.getMilestoneColumn()+" like '"+milestoneName+"' and SYS.Rev_Code='R'";
		String requestV4 = "select count(SYS) from dbo.SUBSYSTEM where START_UP_TEST like '"+milestoneName+"' and SUBSYSTEM.Rev_Code='R'";
		try {
			if(stmt!=null){
				if(icapsV4){
					this.rs= stmt.executeQuery(requestV4);
				} else {
					this.rs= stmt.executeQuery(request);
				}
				while(rs.next()){
					if(!rs.wasNull()){
						return rs.getInt(1);
					} else {
						//System.out.println("Noule");
					}
				}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace(System.out);
		}
		return 0;
	}
	
	/**
	 * @param milestoneName
	 * @return SubsystemList
	 */
	public ArrayList<String> getSubsystemList(String milestoneName){
		
		ArrayList<String> result = new ArrayList<String>(); 
		
		Iterator<Database> it = GeneralConfig.databases.iterator(); 
		while(it.hasNext()){
		    if(this.connect(it.next())){
			String request = "select SYS.SYS FROM SYS where sys."+db.getMilestoneColumn()+" like '"+milestoneName+"';";
			String requestV4 = "select SUBSYSTEM.SYS FROM SUBSYSTEM where SUBSYSTEM.START_UP_TEST like '"+milestoneName+"';";
			try {
				if(stmt!=null){
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4);
					} else {
						this.rs= stmt.executeQuery(request);
					}
					while(rs.next()){
						result.add(rs.getString(1));
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		}
		return result;
	}
	
	/**
	 * @param milestoneName
	 * @return SubSystemInfoForMilestone
	 */
	public ArrayList<HashMap<String,Object>> getSubSystemInfoForMilestone(String milestoneName){
		
		//System.out.println(request);
		
		ArrayList<HashMap<String,Object>> res = new ArrayList<HashMap<String,Object>>();
		
		Iterator<Database> dbIt = GeneralConfig.databases.iterator(); 
		while(dbIt.hasNext()){
			Database db = dbIt.next();
			if(this.connect(db)){
			String request = "SELECT SYS.SYS,sum(TA@DAILY1@REPORT.RemainToDo) as remaintodo,sum(TA@DAILY1@REPORT.Estimated) as estimated,(100*(1-sum(TA@DAILY1@REPORT.RemainToDo)/SUM( TA@DAILY1@REPORT.Estimated))) as Progress,SYS.RFSU_Date as RFSUDate , SYS.Description FROM TA@DAILY1@REPORT inner join SYS on TA@DAILY1@REPORT.SYS_Id=SYS.SYS_Id where (P_or_C='C') and (SYS."+db.getMilestoneColumn()+" like '"+milestoneName+"') group by SYS, RFSU_Date, SYS.Description order by SYS";
			String requestV4 = "SELECT SUBSYSTEM.SYS,sum(TA@DAILY@REPORT.RemainToDo) as remaintodo,sum(TA@DAILY@REPORT.Estimated) as estimated,(100*(1-sum(TA@DAILY@REPORT.RemainToDo)/SUM( TA@DAILY@REPORT.Estimated))) as Progress,SUBSYSTEM.RFSU_Date as RFSUDate , SUBSYSTEM.Description FROM TA@DAILY@REPORT inner join SUBSYSTEM on TA@DAILY@REPORT.SYS=SUBSYSTEM.SYS where (P_or_C='C') and (SUBSYSTEM.START_UP_TEST like '"+milestoneName+"') group by SUBSYSTEM.SYS, RFSU_Date, SUBSYSTEM.Description order by SYS";
			try {
				if(stmt!=null){
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4);
					} else {
						this.rs= stmt.executeQuery(request);
					}
					
					while(rs.next()){
						HashMap<String,Object> subsystem = new HashMap<String, Object>();
						String SYS = rs.getString(1);
						subsystem.put("SYS", SYS);
						subsystem.put("ToDo", rs.getInt(2));
						subsystem.put("Done", rs.getInt(3));
						subsystem.put("%", rs.getInt(4));
						Date tmp = rs.getDate(5);
						if(tmp!=null){
							subsystem.put("AOCDate", tmp);
						} else {
							subsystem.put("AOCDate", null);
						}
						subsystem.put("Description", rs.getString(6));
						res.add(subsystem);
					}
					
					//Request for PL Opened
					Iterator<HashMap<String, Object>> it=res.iterator();
					while(it.hasNext()){
						HashMap<String, Object> line = it.next();
						int plBOpened = getPunchBOpenedForSubsys((String)line.get("SYS"),db);
						line.put("PLB", plBOpened);
					}
					Iterator<HashMap<String, Object>> itt=res.iterator();
					while(itt.hasNext()){
						HashMap<String, Object> line = itt.next();
						int plAOpened = getPunchAOpenedForSubsys((String)line.get("SYS"),db);
						line.put("PLA", plAOpened);
					}
				}
				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
			}
		}
		return res;
	}
	
	/**
	 * @param subsysNumber
	 * @param db
	 * @return PunchAOpenedForSubsys
	 */
	public int getPunchAOpenedForSubsys(String subsysNumber, Database db){
		String request = "select COUNT(PL_Id) from dbo.PL inner join SYS on dbo.PL.SYS_Id = SYS.SYS_Id Inner Join dbo.PLP on PL.PLP_Id=PLP.PLP_Id where SYS.SYS = '"+subsysNumber+"' and (dbo.PL.Rev_Code = 'r') AND  (PLP.PLP in ('A')) AND dbo.PL.Approve_Date is null";
		String requestV4 = "select COUNT(PL_Id) from dbo.PUNCH_LIST inner join SUBSYSTEM on dbo.PUNCH_LIST.SYS_Id = SUBSYSTEM.SYS_Id Inner Join dbo.PUNCH_LIST_CATEGORY on PUNCH_LIST.PLP_Id=PUNCH_LIST_CATEGORY.PLP_Id where SUBSYSTEM.SYS = '"+subsysNumber+"' and (dbo.PUNCH_LIST.Rev_Code = 'r') AND  (PUNCH_LIST_CATEGORY.CLEARED_FOR_RFC = 1) AND dbo.PUNCH_LIST.Approve_Date is null";
		//System.out.println(request);
		try {
			if(stmt!=null){
				ResultSet rsTmp;
				if(icapsV4){
					rsTmp= stmt.executeQuery(requestV4);
				} else {
					rsTmp= stmt.executeQuery(request);
				}
				
				while(rsTmp.next()){
					return rsTmp.getInt(1);
				}
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace(System.out);
		}
		return 0;
	}
	
	/**
	 * @param subsysNumber
	 * @param db
	 * @return PunchBOpenedForSubsys
	 */
	public int getPunchBOpenedForSubsys(String subsysNumber, Database db){
		String request = "select COUNT(PL_Id) from dbo.PL inner join SYS on dbo.PL.SYS_Id = SYS.SYS_Id Inner Join dbo.PLP on PL.PLP_Id=PLP.PLP_Id where SYS.SYS = '"+subsysNumber+"' and (dbo.PL.Rev_Code = 'r') AND  (PLP.PLP in ('B')) AND dbo.PL.Approve_Date is null";
		String requestV4 = "select COUNT(PL_Id) from dbo.PUNCH_LIST inner join SUBSYSTEM on dbo.PUNCH_LIST.SYS_Id = SUBSYSTEM.SYS_Id Inner Join dbo.PUNCH_LIST_CATEGORY on PUNCH_LIST.PLP_Id=PUNCH_LIST_CATEGORY.PLP_Id where SUBSYSTEM.SYS = '"+subsysNumber+"' and (dbo.PUNCH_LIST.Rev_Code = 'r') AND  (PUNCH_LIST_CATEGORY.CLEARED_FOR_RFC = 0) AND  (PUNCH_LIST_CATEGORY.CLEARED_FOR_AOC = 1)  AND dbo.PUNCH_LIST.Approve_Date is null";
		//System.out.println(request);
		try {
			if(stmt!=null){
				ResultSet rsTmp;
				if(icapsV4){
					rsTmp= stmt.executeQuery(requestV4);
				} else {
					rsTmp= stmt.executeQuery(request);
				}
				while(rsTmp.next()){
					return rsTmp.getInt(1);
				}
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace(System.out);
		}
		return 0;
	}
	
	/**
	 * @param milestone
	 * @return PunchAOpenedForMilestone
	 */
	public int getPunchAOpenedForMilestone(String milestone){
		
		//System.out.println(request);
		Iterator<Database> it = GeneralConfig.databases.iterator();
		int acc=0;
		while(it.hasNext()){
		    if(this.connect(it.next())){
			String request = "select COUNT(PL_Id) from dbo.PL inner join SYS on dbo.PL.SYS_Id = SYS.SYS_Id Inner Join dbo.PLP on PL.PLP_Id=PLP.PLP_Id where SYS."+db.getMilestoneColumn()+" like '"+milestone+"' and (dbo.PL.Rev_Code = 'r') AND  (PLP.PLP in ('A')) AND dbo.PL.Approve_Date is null";
			String requestV4 = "select COUNT(PL_Id) from dbo.PUNCH_LIST inner join SUBSYSTEM on dbo.PUNCH_LIST.SYS_Id = SUBSYSTEM.SYS_Id Inner Join dbo.PUNCH_LIST_CATEGORY on PUNCH_LIST.PLP_Id=PUNCH_LIST_CATEGORY.PLP_Id where SUBSYSTEM.START_UP_TEST like '" + milestone + "' and (dbo.PUNCH_LIST.Rev_Code = 'r') AND  (PUNCH_LIST_CATEGORY.CLEARED_FOR_RFC = 1) AND (dbo.PUNCH_LIST.Rev_Code = 'r') AND dbo.PUNCH_LIST.Approve_Date is null";
			try {
				if(stmt!=null){
					ResultSet rsTmp;
					if(icapsV4){
						rsTmp= stmt.executeQuery(requestV4);
					} else {
						rsTmp= stmt.executeQuery(request);
					}
					
					while(rsTmp.next()){
						acc = acc + rsTmp.getInt(1);
					}
				}
				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		}
		return acc;
	}
	
	/**
	 * @param milestone
	 * @return PunchAOpenedForMilestoneForOneDB
	 */
	public int getPunchAOpenedForMilestoneForOneDB(String milestone){
		
		//System.out.println(request);
			String request = "select COUNT(PL_Id) from dbo.PL inner join SYS on dbo.PL.SYS_Id = SYS.SYS_Id Inner Join dbo.PLP on PL.PLP_Id=PLP.PLP_Id where SYS."+db.getMilestoneColumn()+" like '"+milestone+"' and (dbo.PL.Rev_Code = 'r') AND  (PLP.PLP in ('A')) AND dbo.PL.Approve_Date is null";
			String requestV4 = "select COUNT(PL_Id) from dbo.PUNCH_LIST inner join SUBSYSTEM on dbo.PUNCH_LIST.SYS_Id = SUBSYSTEM.SYS_Id Inner Join dbo.PUNCH_LIST_CATEGORY on PUNCH_LIST.PLP_Id=PUNCH_LIST_CATEGORY.PLP_Id where SUBSYSTEM.START_UP_TEST like '" + milestone + "' and (dbo.PUNCH_LIST.Rev_Code = 'r') AND  (PUNCH_LIST_CATEGORY.CLEARED_FOR_RFC = 1) AND (dbo.PUNCH_LIST.Rev_Code = 'r') AND dbo.PUNCH_LIST.Approve_Date is null";
			try {
				if(stmt!=null){
					ResultSet rsTmp;
					if(icapsV4){
						rsTmp= stmt.executeQuery(requestV4);
					} else {
						rsTmp= stmt.executeQuery(request);
					}
					
					while(rsTmp.next()){
						return rsTmp.getInt(1);
					}
				}
				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
			return -1;
		}
	
	/**
	 * @param milestone
	 * @return PunchCOpenedForMilestone
	 */
	public int getPunchCOpenedForMilestone(String milestone){
		
		//System.out.println(request);
		Iterator<Database> it = GeneralConfig.databases.iterator();
		int acc=0;
		while(it.hasNext()){
		    if(this.connect(it.next())){
			String request = "select COUNT(PL_Id) from dbo.PL inner join SYS on dbo.PL.SYS_Id = SYS.SYS_Id Inner Join dbo.PLP on PL.PLP_Id=PLP.PLP_Id where SYS."+db.getMilestoneColumn()+" like '"+milestone+"' and (dbo.PL.Rev_Code = 'r') AND  (PLP.PLP in ('C')) AND (dbo.PL.Rev_Code = 'r') AND dbo.PL.Approve_Date is null";
			String requestV4 = "select COUNT(PL_Id) from dbo.PUNCH_LIST inner join SUBSYSTEM on dbo.PUNCH_LIST.SYS_Id = SUBSYSTEM.SYS_Id Inner Join dbo.PUNCH_LIST_CATEGORY on PUNCH_LIST.PLP_Id=PUNCH_LIST_CATEGORY.PLP_Id where SUBSYSTEM.START_UP_TEST like '" + milestone + "' and (dbo.PUNCH_LIST.Rev_Code = 'r') AND  (PUNCH_LIST_CATEGORY.CLEARED_FOR_RFC = 0) AND  (PUNCH_LIST_CATEGORY.CLEARED_FOR_AOC = 0) AND (dbo.PUNCH_LIST.Rev_Code = 'r') AND dbo.PUNCH_LIST.Approve_Date is null";
			try {
				if(stmt!=null){
					ResultSet rsTmp;
					if(icapsV4){
						rsTmp= stmt.executeQuery(requestV4);
					} else {
						rsTmp= stmt.executeQuery(request);
					}
					
					while(rsTmp.next()){
						acc = acc + rsTmp.getInt(1);
					}
				}
				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		}
		return acc;
	}
	
	/**
	 * @param milestone
	 * @return PunchCOpenedForMilestoneForOneDB
	 */
	public int getPunchCOpenedForMilestoneForOneDB(String milestone){
		
		//System.out.println(request);
			String request = "select COUNT(PL_Id) from dbo.PL inner join SYS on dbo.PL.SYS_Id = SYS.SYS_Id Inner Join dbo.PLP on PL.PLP_Id=PLP.PLP_Id where SYS."+db.getMilestoneColumn()+" like '"+milestone+"' and (dbo.PL.Rev_Code = 'r') AND  (PLP.PLP in ('C')) AND (dbo.PL.Rev_Code = 'r') AND dbo.PL.Approve_Date is null";
			String requestV4 = "select COUNT(PL_Id) from dbo.PUNCH_LIST inner join SUBSYSTEM on dbo.PUNCH_LIST.SYS_Id = SUBSYSTEM.SYS_Id Inner Join dbo.PUNCH_LIST_CATEGORY on PUNCH_LIST.PLP_Id=PUNCH_LIST_CATEGORY.PLP_Id where SUBSYSTEM.START_UP_TEST like '" + milestone + "' and (dbo.PUNCH_LIST.Rev_Code = 'r') AND  (PUNCH_LIST_CATEGORY.CLEARED_FOR_RFC = 0) AND  (PUNCH_LIST_CATEGORY.CLEARED_FOR_AOC = 0) AND (dbo.PUNCH_LIST.Rev_Code = 'r') AND dbo.PUNCH_LIST.Approve_Date is null";
			try {
				if(stmt!=null){
					ResultSet rsTmp;
					if(icapsV4){
						rsTmp= stmt.executeQuery(requestV4);
					} else {
						rsTmp= stmt.executeQuery(request);
					}
					
					while(rsTmp.next()){
						return rsTmp.getInt(1);
					}
				}
				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		return -1;
	}
	
	/**
	 * @param milestone
	 * @return PunchBOpenedForMilestone
	 */
	public int getPunchBOpenedForMilestone(String milestone){
		
		//System.out.println(request);
		Iterator<Database> it = GeneralConfig.databases.iterator();
		int acc=0;
		while(it.hasNext()){
		    if(this.connect(it.next())){
			String request = "select COUNT(PL_Id) from dbo.PL inner join SYS on dbo.PL.SYS_Id = SYS.SYS_Id Inner Join dbo.PLP on PL.PLP_Id=PLP.PLP_Id where SYS."+db.getMilestoneColumn()+" like '"+milestone+"' and (dbo.PL.Rev_Code = 'r') AND  (PLP.PLP in ('B')) AND (dbo.PL.Rev_Code = 'r') AND dbo.PL.Approve_Date is null";
			String requestV4 = "select COUNT(PL_Id) from dbo.PUNCH_LIST inner join SUBSYSTEM on dbo.PUNCH_LIST.SYS_Id = SUBSYSTEM.SYS_Id Inner Join dbo.PUNCH_LIST_CATEGORY on PUNCH_LIST.PLP_Id=PUNCH_LIST_CATEGORY.PLP_Id where SUBSYSTEM.START_UP_TEST like '" + milestone + "' and (dbo.PUNCH_LIST.Rev_Code = 'r') AND  (PUNCH_LIST_CATEGORY.CLEARED_FOR_RFC = 0) AND  (PUNCH_LIST_CATEGORY.CLEARED_FOR_AOC = 1) AND (dbo.PUNCH_LIST.Rev_Code = 'r') AND dbo.PUNCH_LIST.Approve_Date is null";
			try {
				if(stmt!=null){
					ResultSet rsTmp;
					if(icapsV4){
						rsTmp= stmt.executeQuery(requestV4);
					} else {
						rsTmp= stmt.executeQuery(request);
					}
					
					while(rsTmp.next()){
						acc=acc+ rsTmp.getInt(1);
					}
				}
				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		}
		return acc;
	}
	
	/**
	 * @param milestone
	 * @return PunchBOpenedForMilestoneForOneDB
	 */
	public int getPunchBOpenedForMilestoneForOneDB(String milestone){
		
			String request = "select COUNT(PL_Id) from dbo.PL inner join SYS on dbo.PL.SYS_Id = SYS.SYS_Id Inner Join dbo.PLP on PL.PLP_Id=PLP.PLP_Id where SYS."+db.getMilestoneColumn()+" like '"+milestone+"' and (dbo.PL.Rev_Code = 'r') AND  (PLP.PLP in ('B')) AND (dbo.PL.Rev_Code = 'r') AND dbo.PL.Approve_Date is null";
			String requestV4 = "select COUNT(PL_Id) from dbo.PUNCH_LIST inner join SUBSYSTEM on dbo.PUNCH_LIST.SYS_Id = SUBSYSTEM.SYS_Id Inner Join dbo.PUNCH_LIST_CATEGORY on PUNCH_LIST.PLP_Id=PUNCH_LIST_CATEGORY.PLP_Id where SUBSYSTEM.START_UP_TEST like '" + milestone + "' and (dbo.PUNCH_LIST.Rev_Code = 'r') AND  (PUNCH_LIST_CATEGORY.CLEARED_FOR_RFC = 0) AND  (PUNCH_LIST_CATEGORY.CLEARED_FOR_AOC = 1) AND (dbo.PUNCH_LIST.Rev_Code = 'r') AND dbo.PUNCH_LIST.Approve_Date is null";
			try {
				if(stmt!=null){
					ResultSet rsTmp;
					if(icapsV4){
						rsTmp= stmt.executeQuery(requestV4);
					} else {
						rsTmp= stmt.executeQuery(request);
					}
					
					while(rsTmp.next()){
						return rsTmp.getInt(1);
					}
				}
				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		return -1;
	}
	
	/**
	 * @param milestoneName
	 * @param date
	 * @return CommProgressForMilestone
	 */
	public int getCommProgressForMilestone(String milestoneName,Date date){
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String jourstrg=format.format(date);
		int acc=0;
		//System.out.println("format"+jourstrg);
		Iterator<Database> it = GeneralConfig.databases.iterator();
		while(it.hasNext()){
			Database db = it.next();
			if(this.connect(db)){
			String request = "SELECT sum(TA.Estimated_Man_Hours) as SumMhrs FROM TA INNER JOIN SYS ON TA.SYS_Id = SYS.SYS_Id INNER JOIN TAA ON TA.TAA_ID = TAA.TAA_Id  WHERE (TA.Rev_Code = 'r') AND (TA.Completion_Date <=CONVERT(DATETIME, '" + jourstrg + "', 103))and (sys."+db.getMilestoneColumn()+" like '" + milestoneName + "')and TAA IN ('FTS', 'OTS', 'PVP', 'PRC') group by  sys."+db.getMilestoneColumn()+" union  SELECT   sum(OT.Estimated_Man_Hours) as SumMhrs FROM OT INNER JOIN SYS ON OT.SYS_Id = SYS.SYS_Id WHERE (OT.Rev_Code = 'r') AND (OT.Completion_Date <=CONVERT(DATETIME, '" + jourstrg + "', 103)) and (sys."+db.getMilestoneColumn()+" like '" + milestoneName + "') group by sys."+db.getMilestoneColumn();
			String requestV4= "SELECT sum(TASK_PCOM_COM_NOT_OTP_PVP.Estimated_Man_Hours) as SumMhrs FROM TASK_PCOM_COM_NOT_OTP_PVP INNER JOIN SUBSYSTEM ON TASK_PCOM_COM_NOT_OTP_PVP.SYS_Id=SUBSYSTEM.SYS_Id INNER JOIN FORM ON TASK_PCOM_COM_NOT_OTP_PVP.SHT_Id=FORM.SHT_Id INNER JOIN ACTIVITY ON FORM.TAA_ID = ACTIVITY.TAA_Id  WHERE (TASK_PCOM_COM_NOT_OTP_PVP.Rev_Code = 'r') AND (TASK_PCOM_COM_NOT_OTP_PVP.Completion_Date <=CONVERT(DATETIME, '" + jourstrg + "', 103))and (SUBSYSTEM.START_UP_TEST like '" + milestoneName + "')and ACTIVITY.TAA IN ('FTS', 'OTS', 'PVP', 'PRC') group by  SUBSYSTEM.START_UP_TEST union  SELECT  sum(TASK_OTP_PVP.Estimated_Man_Hours) as SumMhrs FROM TASK_OTP_PVP INNER JOIN SUBSYSTEM ON TASK_OTP_PVP.SYS_Id = SUBSYSTEM.SYS_Id WHERE (TASK_OTP_PVP.Rev_Code = 'r') AND (TASK_OTP_PVP.Completion_Date <=CONVERT(DATETIME, '" + jourstrg + "', 103)) and (SUBSYSTEM.START_UP_TEST like '" + milestoneName + "') group by SUBSYSTEM.START_UP_TEST";
			try {
				if(stmt!=null){
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4);
					} else {
						this.rs= stmt.executeQuery(request);
					}
					while(rs.next()){
						acc=acc+ rs.getInt(1);
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		}
		return acc;
	}
	
	/**
	 * @param milestoneName
	 * @return TaskNumberForMilestone
	 */
	public int getTaskNumberForMilestone(String milestoneName){
	    int acc=0;
		//System.out.println("format"+jourstrg);
		Iterator<Database> it = GeneralConfig.databases.iterator();
		while(it.hasNext()){
			Database db = it.next();
			if(this.connect(db)){
			String request = "SELECT count(TA.TAA_ID) as CountTAA FROM TA INNER JOIN SYS ON TA.SYS_Id = SYS.SYS_Id INNER JOIN TAA ON TA.TAA_ID = TAA.TAA_Id  WHERE (TA.Rev_Code = 'r') and (sys."+db.getMilestoneColumn()+" like '" + milestoneName + "')and TAA IN ('FTS', 'OTS', 'PVP', 'PRC') group by  sys."+db.getMilestoneColumn()+" union  SELECT   sum(OT.Estimated_Man_Hours) as SumMhrs FROM OT INNER JOIN SYS ON OT.SYS_Id = SYS.SYS_Id WHERE (OT.Rev_Code = 'r') and (sys."+db.getMilestoneColumn()+" like '" + milestoneName + "') group by sys."+db.getMilestoneColumn();
			String requestV4= "SELECT count(TASK_PCOM_COM_NOT_OTP_PVP.TA_ID) as CountTAA FROM TASK_PCOM_COM_NOT_OTP_PVP INNER JOIN SUBSYSTEM ON TASK_PCOM_COM_NOT_OTP_PVP.SYS_Id=SUBSYSTEM.SYS_Id INNER JOIN FORM ON TASK_PCOM_COM_NOT_OTP_PVP.SHT_Id=FORM.SHT_Id INNER JOIN ACTIVITY ON FORM.TAA_ID = ACTIVITY.TAA_Id  WHERE (TASK_PCOM_COM_NOT_OTP_PVP.Rev_Code = 'r') and (SUBSYSTEM.START_UP_TEST like '" + milestoneName + "') group by SUBSYSTEM.START_UP_TEST";
			try {
				if(stmt!=null){
					if(icapsV4){
					    	System.out.println(requestV4);
						this.rs= stmt.executeQuery(requestV4);
					} else {
					    	System.out.println(request);
						this.rs= stmt.executeQuery(request);
					}
					while(rs.next()){
						acc=acc+ rs.getInt(1);
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		}
		return acc;
	}
	
	/**
	 * @param milestoneName
	 * @param date
	 * @param db
	 * @return CommProgressForMilestoneForOneDB
	 */
	public int getCommProgressForMilestoneForOneDB(String milestoneName,Date date,Database db){
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String jourstrg=format.format(date);
		//System.out.println("format"+jourstrg);
			String request = "SELECT sum(TA.Estimated_Man_Hours) as SumMhrs FROM TA INNER JOIN SYS ON TA.SYS_Id = SYS.SYS_Id INNER JOIN TAA ON TA.TAA_ID = TAA.TAA_Id  WHERE (TA.Rev_Code = 'r') AND (TA.Completion_Date <=CONVERT(DATETIME, '" + jourstrg + "', 103))and (sys."+db.getMilestoneColumn()+"  like '" + milestoneName + "')and TAA IN ('FTS', 'OTS', 'PVP', 'PRC') group by  sys."+db.getMilestoneColumn()+" union  SELECT   sum(OT.Estimated_Man_Hours) as SumMhrs FROM OT INNER JOIN SYS ON OT.SYS_Id = SYS.SYS_Id WHERE (OT.Rev_Code = 'r') AND (OT.Completion_Date <=CONVERT(DATETIME, '" + jourstrg + "', 103)) and (sys."+db.getMilestoneColumn()+"  like '" + milestoneName + "') group by sys."+db.getMilestoneColumn();
			String requestV4= "SELECT sum(TASK_PCOM_COM_NOT_OTP_PVP.Estimated_Man_Hours) as SumMhrs FROM TASK_PCOM_COM_NOT_OTP_PVP INNER JOIN SUBSYSTEM ON TASK_PCOM_COM_NOT_OTP_PVP.SYS_Id=SUBSYSTEM.SYS_Id INNER JOIN FORM ON TASK_PCOM_COM_NOT_OTP_PVP.SHT_Id=FORM.SHT_Id INNER JOIN ACTIVITY ON FORM.TAA_ID = ACTIVITY.TAA_Id  WHERE (TASK_PCOM_COM_NOT_OTP_PVP.Rev_Code = 'r') AND (TASK_PCOM_COM_NOT_OTP_PVP.Completion_Date <=CONVERT(DATETIME, '" + jourstrg + "', 103))and (SUBSYSTEM.START_UP_TEST  like '" + milestoneName + "')and ACTIVITY.TAA IN ('FTS', 'OTS', 'PVP', 'PRC') group by  SUBSYSTEM.START_UP_TEST union  SELECT  sum(TASK_OTP_PVP.Estimated_Man_Hours) as SumMhrs FROM TASK_OTP_PVP INNER JOIN SUBSYSTEM ON TASK_OTP_PVP.SYS_Id = SUBSYSTEM.SYS_Id WHERE (TASK_OTP_PVP.Rev_Code = 'r') AND (TASK_OTP_PVP.Completion_Date <=CONVERT(DATETIME, '" + jourstrg + "', 103)) and (SUBSYSTEM.START_UP_TEST  like '" + milestoneName + "') group by SUBSYSTEM.START_UP_TEST";
			try {
				if(stmt!=null){
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4);
					} else {
						this.rs= stmt.executeQuery(request);
					}
					while(rs.next()){
						return rs.getInt(1);
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
			return 0;
	}
	
	/**
	 * @param milestoneName
	 * @param date
	 * @return PLOpenForMilestone
	 */
	public int getPLOpenForMilestone(String milestoneName,Date date){
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String jourstrg=format.format(date);
		
		int acc=0;
		//System.out.println("format"+jourstrg);
		Iterator<Database> it = GeneralConfig.databases.iterator();
		while(it.hasNext()){
			Database db = it.next();
			if(this.connect(db)){
		
			String request="SELECT count(dbo.PL.PL)as NbrPLopen FROM dbo.PL INNER JOIN dbo.SYS ON dbo.PL.SYS_Id = dbo.SYS.SYS_Id Inner Join dbo.PLP on PL.PLP_Id=PLP.PLP_Id WHERE (dbo.PL.Rev_Code = 'r') AND  (PLP.PLP in ('A','B')) AND (dbo.SYS."+db.getMilestoneColumn()+" like '" + milestoneName + "') AND (dbo.PL.Issue_Date <= CONVERT(DATETIME, '" + jourstrg + "', 103))AND (dbo.PL.Approve_Date is null or (dbo.PL.Approve_Date >= CONVERT(DATETIME, '" + jourstrg + "', 103)))";
			String requestV4="SELECT count(dbo.PUNCH_LIST.PL)as NbrPLopen FROM dbo.PUNCH_LIST INNER JOIN dbo.SUBSYSTEM ON dbo.PUNCH_LIST.SYS_Id = dbo.SUBSYSTEM.SYS_Id Inner Join dbo.PUNCH_LIST_CATEGORY on PUNCH_LIST.PLP_Id=PUNCH_LIST_CATEGORY.PLP_Id WHERE (dbo.PUNCH_LIST.Rev_Code = 'r') AND  ((PUNCH_LIST_CATEGORY.CLEARED_FOR_RFC = 1) OR (PUNCH_LIST_CATEGORY.CLEARED_FOR_AOC = 1)) AND (dbo.SUBSYSTEM.START_UP_TEST like '" + milestoneName + "') AND (dbo.PUNCH_LIST.Issue_Date <= CONVERT(DATETIME, '" + jourstrg + "', 103))AND (dbo.PUNCH_LIST.Approve_Date is null or (dbo.PUNCH_LIST.Approve_Date >= CONVERT(DATETIME, '" + jourstrg + "', 103)))";
			try {
				if(stmt!=null){
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4);
					} else {
						this.rs= stmt.executeQuery(request);
					}
					while(rs.next()){
						acc= acc+ rs.getInt(1);
					}
				}
				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		}
		return acc;
	}
	
	/**
	 * @param milestoneName
	 * @param date
	 * @param db
	 * @return PLOpenForMilestoneForOneDB
	 */
	public int getPLOpenForMilestoneForOneDB(String milestoneName,Date date,Database db){
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String jourstrg=format.format(date);
		
			String request="SELECT count(dbo.PL.PL)as NbrPLopen FROM dbo.PL INNER JOIN dbo.SYS ON dbo.PL.SYS_Id = dbo.SYS.SYS_Id Inner Join dbo.PLP on PL.PLP_Id=PLP.PLP_Id WHERE (dbo.PL.Rev_Code = 'r') AND  (PLP.PLP in ('A','B')) AND (dbo.SYS."+db.getMilestoneColumn()+" like '" + milestoneName + "') AND (dbo.PL.Issue_Date <= CONVERT(DATETIME, '" + jourstrg + "', 103))AND (dbo.PL.Approve_Date is null or (dbo.PL.Approve_Date >= CONVERT(DATETIME, '" + jourstrg + "', 103)))";
			String requestV4="SELECT count(dbo.PUNCH_LIST.PL)as NbrPLopen FROM dbo.PUNCH_LIST INNER JOIN dbo.SUBSYSTEM ON dbo.PUNCH_LIST.SYS_Id = dbo.SUBSYSTEM.SYS_Id Inner Join dbo.PUNCH_LIST_CATEGORY on PUNCH_LIST.PLP_Id=PUNCH_LIST_CATEGORY.PLP_Id WHERE (dbo.PUNCH_LIST.Rev_Code = 'r') AND  ((PUNCH_LIST_CATEGORY.CLEARED_FOR_RFC = 1) OR (PUNCH_LIST_CATEGORY.CLEARED_FOR_AOC = 1)) AND (dbo.SUBSYSTEM.START_UP_TEST like '" + milestoneName + "') AND (dbo.PUNCH_LIST.Issue_Date <= CONVERT(DATETIME, '" + jourstrg + "', 103))AND (dbo.PUNCH_LIST.Approve_Date is null or (dbo.PUNCH_LIST.Approve_Date >= CONVERT(DATETIME, '" + jourstrg + "', 103)))";
			try {
				if(stmt!=null){
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4);
					} else {
						this.rs= stmt.executeQuery(request);
					}
					while(rs.next()){
						return rs.getInt(1);
					}
				}
				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		return 0;
	}
	
	/**
	 * @param milestoneName
	 * @param date
	 * @return SubSystemNumberAOCForMilestone
	 */
	public int getSubSystemNumberAOCForMilestone(String milestoneName,Date date){
		//former name of AOC is RFSU (Ready For Start Up)
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String jourstrg=format.format(date);
		int acc=0;
		//System.out.println("format"+jourstrg);
		Iterator<Database> it = GeneralConfig.databases.iterator();
		while(it.hasNext()){
			Database db = it.next();
			if(this.connect(db)){
			String request="SELECT count(RFSU_Date) as NbrSSRFSU FROM SYS WHERE (Rev_Code = 'r')and (SYS.SYS_Id>0)and (RFSU_Date<=CONVERT(datetime,'" + jourstrg + "',103))and (SYS."+db.getMilestoneColumn()+" like '" + milestoneName + "')";
			String requestV4="SELECT count(RFSU_Date) as NbrSSRFSU FROM SUBSYSTEM WHERE (Rev_Code = 'r')and (SUBSYSTEM.SYS_Id>0)and (RFSU_Date<=CONVERT(datetime,'" + jourstrg + "',103))and (SUBSYSTEM.START_UP_TEST like '" + milestoneName + "')";
			try {
				if(stmt!=null){
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4);
					} else {
						this.rs= stmt.executeQuery(request);
					}
					while(rs.next()){
						acc = acc + rs.getInt(1);
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		}
		return acc;
	}
	
	/**
	 * @param milestoneName
	 * @param date
	 * @return SubSystemNumberAOCForMilestoneForOneDB
	 */
	public int getSubSystemNumberAOCForMilestoneForOneDB(String milestoneName,Date date){
		//former name of AOC is RFSU (Ready For Start Up)
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String jourstrg=format.format(date);
		
		String request="SELECT count(RFSU_Date) as NbrSSRFSU FROM SYS WHERE (Rev_Code = 'r')and (SYS.SYS_Id>0)and (RFSU_Date<=CONVERT(datetime,'" + jourstrg + "',103))and (SYS."+db.getMilestoneColumn()+" like '" + milestoneName + "')";
		String requestV4="SELECT count(RFSU_Date) as NbrSSRFSU FROM SUBSYSTEM WHERE (Rev_Code = 'r')and (SUBSYSTEM.SYS_Id>0)and (RFSU_Date<=CONVERT(datetime,'" + jourstrg + "',103))and (SUBSYSTEM.START_UP_TEST like '" + milestoneName + "')";
		try {
			if(stmt!=null){
				if(icapsV4){
					this.rs= stmt.executeQuery(requestV4);
				} else {
					this.rs= stmt.executeQuery(request);
				}
				while(rs.next()){
					return rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace(System.out);
		}
		return 0;
	}
	
	/**
	 * @param milestoneName
	 * @return TotalCommToDo
	 */
	public int getTotalCommToDo(String milestoneName){
		
		Iterator<Database> it = GeneralConfig.databases.iterator();
		int acc =0 ;
		while(it.hasNext()){
		    if(this.connect(it.next())){
			String request="SELECT sum(TA@DAILY1@REPORT.Estimated) as TotalMhrsComm FROM SYS INNER JOIN TA@DAILY1@REPORT ON SYS.SYS_Id = TA@DAILY1@REPORT.SYS_Id WHERE (SYS."+db.getMilestoneColumn()+" like '" + milestoneName + "') AND (TA@DAILY1@REPORT.P_or_C = 'C')and SYS.Rev_Code='R' ";
			String requestV4="SELECT sum(TA@DAILY@REPORT.Estimated) as TotalMhrsComm FROM SUBSYSTEM INNER JOIN TA@DAILY@REPORT ON SUBSYSTEM.SYS = TA@DAILY@REPORT.SYS WHERE (SUBSYSTEM.START_UP_TEST like '" + milestoneName + "') AND (TA@DAILY@REPORT.P_or_C = 'C')and SUBSYSTEM.Rev_Code='R' ";
			try {
				if(stmt!=null){
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4);
					} else {
						this.rs= stmt.executeQuery(request);
					}
					while(rs.next()){
						acc=acc+rs.getInt(1);
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		}
		return acc;
	}
	
	/**
	 * @param milestoneName
	 * @return boolean : doesMilestoneHaveOneSubsystemInTADAILYREPORT
	 */
	public boolean doesMilestoneHaveOneSubsystemInTADAILYREPORT(String milestoneName){
		Iterator<Database> it = GeneralConfig.databases.iterator();
		while(it.hasNext()){
		    	Database db = it.next();
		    	if(this.connect(db)){
        			String request= "SELECT count(TA@DAILY1@REPORT.SYS_Id) FROM TA@DAILY1@REPORT inner join SYS on TA@DAILY1@REPORT.SYS_Id=SYS.SYS_Id where (P_or_C='C') and (sys."+db.getMilestoneColumn()+" like '"+milestoneName+"') ";
        			String requestV4= "SELECT TA@DAILY@REPORT.SYS FROM TA@DAILY@REPORT inner join SUBSYSTEM on TA@DAILY@REPORT.SYS=SUBSYSTEM.SYS where (P_or_C='C') and (SUBSYSTEM.START_UP_TEST like '"+milestoneName+"') ";
        			System.out.println(request);
        			try {
        				if(stmt!=null){
        					if(icapsV4){
        					    	System.out.println(requestV4);
        						this.rs= stmt.executeQuery(requestV4);
        					} else {
        						this.rs= stmt.executeQuery(request);
        					}
        					System.out.println(milestoneName+" in "+db.getDatabaseName());
        					while(rs.next()){
        						if(rs.getInt(1)>0){
        						    	System.out.println(milestoneName+" is in TA@DAILY1REPORT");
        							return true;
        						}
        					}
        				}
        			} catch (SQLException e) {
        				JOptionPane.showMessageDialog(null, e.getMessage());
        				e.printStackTrace(System.out);
        			}
		    	}
		}
		//JOptionPane.showMessageDialog(null, milestoneName+" isn't in TA@DAILY1REPORT of "+db.getAlias());
		return false;
	}
	
	/**
	 * @param milestoneName
	 * @return boolean : doesMilestoneHaveOneSubsystemInTADAILYREPORTinOneDB
	 */
	public boolean doesMilestoneHaveOneSubsystemInTADAILYREPORTinOneDB(String milestoneName){
			String request= "SELECT count(TA@DAILY1@REPORT.SYS_Id) FROM TA@DAILY1@REPORT inner join SYS on TA@DAILY1@REPORT.SYS_Id=SYS.SYS_Id where (P_or_C='C') and (sys."+db.getMilestoneColumn()+" like '"+milestoneName+"') ";
			String requestV4= "SELECT TA@DAILY@REPORT.SYS FROM TA@DAILY@REPORT inner join SUBSYSTEM on TA@DAILY@REPORT.SYS=SUBSYSTEM.SYS where (P_or_C='C') and (SUBSYSTEM.START_UP_TEST like '"+milestoneName+"') ";
			try {
				if(stmt!=null){
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4);
					} else {
						this.rs= stmt.executeQuery(request);
					}
					while(rs.next()){
						if(rs.getInt(1)>0){
							return true;
						}
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
			return false;
	}
	
	/**
	 * @param milestoneName
	 * @return boolean : isMilestoneInDatabase
	 */
	public boolean isMilestoneInDatabase(String milestoneName){
		Iterator<Database> it = GeneralConfig.databases.iterator();
		int acc =0 ;
		while(it.hasNext()){
		    Database db = it.next();
		    if(this.connect(db)){
			String request = "SELECT count(SYS.SYS_Id) FROM SYS where SYS."+db.getMilestoneColumn()+" like '"+milestoneName+"';";
			String requestV4 = "SELECT count(SUBSYSTEM.SYS) FROM SUBSYSTEM where SUBSYSTEM.START_UP_TEST like '"+milestoneName+"';";
			try {
				if(stmt!=null){
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4);
					} else {
						this.rs= stmt.executeQuery(request);
					}
					while(rs.next()){
						acc=rs.getInt(1);
						if(acc!=0){
							return true;
						}
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		}
		return false;
	}
	
	/**
	 * @param milestoneName
	 * @return boolean : isMilestoneInOneDatabase
	 */
	public boolean isMilestoneInOneDatabase(String milestoneName){
		String request = "SELECT count(SYS.SYS_Id) FROM SYS where SYS."+db.getMilestoneColumn()+" like '"+milestoneName+"';";
		String requestV4 = "SELECT count(SUBSYSTEM.SYS_Id) FROM SUBSYSTEM where SUBSYSTEM.START_UP_TEST like '"+milestoneName+"';";
		try {
			if(stmt!=null){
				if(icapsV4){
					this.rs= stmt.executeQuery(requestV4);
				} else {
					this.rs= stmt.executeQuery(request);
				}
				while(rs.next()){
					if(rs.getInt(1)!=0)
						return true;
					   
				}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace(System.out);
		}
		return false;
	}
	
	/**
	 * @return HashMap<String,Date> : DataBasesLastUpdateDates
	 */
	public HashMap<String,Date> getDataBaseLastUpdateDates(){
		
		HashMap<String,Date> res = new HashMap<String,Date>();
		
		Iterator<Database> it = GeneralConfig.databases.iterator();
		while(it.hasNext()){
			Database db = it.next();
			if(this.connect(db)){
			String request = "SELECT max(ExecutionDate) FROM TA@DAILY1@REPORT;";
			String requestV4 = "SELECT max(ExecutionDate) FROM TA@DAILY@REPORT;";
			try {
				if(stmt!=null){
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4);
					} else {
						this.rs= stmt.executeQuery(request);
					}
					while(rs.next()){
					    	Date d= rs.getDate(1);
						res.put(db.getAlias(),d);
						db.setLastUpdateDate(d);
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		}
		return res;
	}
	
	/**
	 * @param db
	 * @return Date DataBaseLastUpdateDateForOneDB
	 */
	public Date getDataBaseLastUpdateDateForOneDB(Database db){
		String request = "SELECT max(ExecutionDate) FROM TA@DAILY1@REPORT;";
		String requestV4 = "SELECT max(ExecutionDate) FROM TA@DAILY@REPORT;";
		
		
		try {
			if(stmt!=null){
				if(icapsV4){
					this.rs= stmt.executeQuery(requestV4);
				} else {
					this.rs= stmt.executeQuery(request);
				}
				while(rs.next()){
				    	Date tmp=rs.getDate(1);
				    	if(tmp!=null){
				    	    java.util.Date d = new java.util.Date(tmp.getTime());
				    	    db.setLastUpdateDate(d);
				    	}
					return tmp;
				}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace(System.out);
		}
		return null;
	}
	
	/**
	 * @return currentDatabaseLastUpdateDate
	 */
	public Date getDataBaseLastUpdateDate(){
		String request = "SELECT max(ExecutionDate) FROM TA@DAILY1@REPORT;";
		String requestV4 = "SELECT max(ExecutionDate) FROM TA@DAILY@REPORT";//"SELECT LastSYSstatusUpdate_Date FROM dbo.Project;";
		
		Date res = new Date(0);
		Iterator<Database> it = GeneralConfig.databases.iterator();
		while(it.hasNext()){
			Database db = it.next();
			if(this.connect(db)){
			try {
				if(stmt!=null){
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4);
					} else {
						this.rs= stmt.executeQuery(request);
					}
					while(rs.next()){
						Date d = rs.getDate(1);
						if(res==null || res.getTime()<d.getTime()){
							res=d;
						}
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		}
		return res;
	}
	
	/*public TreeMap<String, Object> getGeneralProgress(String milestoneName) {
		
		
		TreeMap<String, Object> res = new TreeMap<String, Object>();
		
		double remainTotal=0; 
		double estimatedTotal=0;
		
		double accSSNumber=0;
		double accSSAOC=0;
		int accPunchOpen=0;
		
		Iterator<Database> itDB = GeneralConfig.databases.iterator();
		while(itDB.hasNext()){
			this.connect(itDB.next());
			
			String requestSSAOC="SELECT count(RFSU_Date) as NbrSSRFSU FROM SYS WHERE (Rev_Code = 'r')and (SYS.SYS_Id>0)and (RFSU_Date<=CONVERT(datetime,'" + new java.sql.Date(Calendar.getInstance().getTime().getTime()) + "',103))and (SYS."+db.getMilestoneColumn()+"='" + milestoneName + "')";
			String requestV4SSAOC="SELECT count(RFSU_Date) as NbrSSRFSU FROM SUBSYSTEM WHERE (Rev_Code = 'r')and (SUBSYSTEM.SYS_Id>0)and (RFSU_Date<=CONVERT(datetime,'" + new java.sql.Date(Calendar.getInstance().getTime().getTime()) + "',103))and (SUBSYSTEM.START_UP_TEST='" + milestoneName + "')";
			String requestPLOpen="SELECT count(dbo.PL.PL)as NbrPLopen FROM dbo.PL INNER JOIN dbo.SYS ON dbo.PL.SYS_Id = dbo.SYS.SYS_Id Inner Join dbo.PLP on PL.PLP_Id=PLP.PLP_Id WHERE (dbo.PL.Rev_Code = 'r') AND  (PLP.PLP in ('A','B')) AND (dbo.SYS."+db.getMilestoneColumn()+" = '" + milestoneName + "') AND (dbo.PL.Issue_Date <= CONVERT(DATETIME, '" + new java.sql.Date(Calendar.getInstance().getTime().getTime()) + "', 103))AND (dbo.PL.Approve_Date is null or (dbo.PL.Approve_Date >= CONVERT(DATETIME, '" + new java.sql.Date(Calendar.getInstance().getTime().getTime()) + "', 103)))";
			String requestV4PLOpen="SELECT count(dbo.PUNCH_LIST.PL)as NbrPLopen FROM dbo.PUNCH_LIST INNER JOIN dbo.SUBSYSTEM ON dbo.PUNCH_LIST.SYS_Id = dbo.SUBSYSTEM.SYS_Id Inner Join dbo.PUNCH_LIST_CATEGORY on PUNCH_LIST.PLP_Id=PUNCH_LIST_CATEGORY.PLP_Id WHERE (dbo.PUNCH_LIST.Rev_Code = 'r') AND  ((PUNCH_LIST_CATEGORY.CLEARED_FOR_RFC = 1) OR (PUNCH_LIST_CATEGORY.CLEARED_FOR_AOC = 1)) AND (dbo.SUBSYSTEM.START_UP_TEST = '" + milestoneName + "') AND (dbo.PUNCH_LIST.Issue_Date <= CONVERT(DATETIME, '" + new java.sql.Date(Calendar.getInstance().getTime().getTime()) + "', 103))AND (dbo.PUNCH_LIST.Approve_Date is null or (dbo.PUNCH_LIST.Approve_Date >= CONVERT(DATETIME, '" + new java.sql.Date(Calendar.getInstance().getTime().getTime()) + "', 103)))";
			String requestCommProgress= "SELECT sum(TA@DAILY1@REPORT.RemainToDo) as Remain,sum(TA@DAILY1@REPORT.Estimated) as Estimated FROM TA@DAILY1@REPORT inner join SYS on TA@DAILY1@REPORT.SYS_Id=SYS.SYS_Id where (P_or_C='C') and (sys."+db.getMilestoneColumn()+"='"+milestoneName+"') ";
			String requestV4CommProgress= "SELECT sum(TA@DAILY1@REPORT.RemainToDo) as Remain,sum(TA@DAILY1@REPORT.Estimated) as Estimated FROM TA@DAILY@REPORT inner join SUBSYSTEM on TA@DAILY@REPORT.SYS=SUBSYSTEM.SYS where (P_or_C='C') and (SUBSYSTEM.START_UP_TEST='"+milestoneName+"') ";
			String requestSSNumber = "select count(SYS) from dbo.SYS where "+db.getMilestoneColumn()+"='"+milestoneName+"' and SYS.Rev_Code='R'";
			String requestV4SSNumber = "select count(SYS) from dbo.SUBSYSTEM where START_UP_TEST='"+milestoneName+"' and SUBSYSTEM.Rev_Code='R'";
			
			try {
				if(stmt!=null){
					//Comm Progress
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4CommProgress);
					} else {
						this.rs= stmt.executeQuery(requestCommProgress);
					}
					while(rs.next()){
						remainTotal=remainTotal+rs.getDouble(1);
						estimatedTotal=estimatedTotal+rs.getDouble(2);
					}
					//SS Number
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4SSNumber);
					} else {
						this.rs= stmt.executeQuery(requestSSNumber);
					}
					while(rs.next()){
						if(!rs.wasNull()){
							accSSNumber=accSSNumber+rs.getInt(1);
						} else {
							//System.out.println("Noule");
						}
					}
					//PUNCH
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4PLOpen);
					} else {
						this.rs= stmt.executeQuery(requestPLOpen);
					}
					while(rs.next()){
						accPunchOpen=accPunchOpen+rs.getInt(1);
					}
					//SS AOC
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4SSAOC);
					} else {
						this.rs= stmt.executeQuery(requestSSAOC);
						//System.out.println("->>>>>>>>>>>>>>>>>>"+requestSSAOC);
					}
					while(rs.next()){
						accSSAOC=accSSAOC+rs.getInt(1);
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		res.put("comm", (((estimatedTotal-remainTotal)/estimatedTotal)*100.0));
		res.put("ssAOC", (accSSAOC/accSSNumber)*100.0);
		res.put("PL", accPunchOpen);
		return res;
	}*/
	
	/**
	 * @return boolean : isConnexionError
	 */
	public boolean isConnexionError(){
		if(connexionError>0)
			return true;
		else
			return false;
	}

	/**
	 * @param name
	 * @return MilestonePrecommProgress
	 */
	public Double getMilestonePrecommProgress(String name) {
		
		Double estimatedTotal = 0.0;
		Double remainTotal = 0.0;
		Iterator<Database> dbIterator = GeneralConfig.databases.iterator();
		while(dbIterator.hasNext()){
			
			if(this.connect(dbIterator.next())){
			String request= "SELECT sum(TA@DAILY1@REPORT.RemainToDo) as Remain,sum(TA@DAILY1@REPORT.Estimated) as Estimated FROM TA@DAILY1@REPORT inner join SYS on TA@DAILY1@REPORT.SYS_Id=SYS.SYS_Id where (P_or_C='P') and (sys."+db.getMilestoneColumn()+" like '"+name+"') ";
			String requestV4= "SELECT sum(TA@DAILY@REPORT.RemainToDo) as Remain,sum(TA@DAILY@REPORT.Estimated) as Estimated FROM TA@DAILY@REPORT inner join SUBSYSTEM on TA@DAILY@REPORT.SYS=SUBSYSTEM.SYS where (P_or_C='P') and (SUBSYSTEM.START_UP_TEST like '"+name+"') ";
			try {
				if(stmt!=null){
					//System.out.println("HOP");
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4);
					} else {
						this.rs= stmt.executeQuery(request);
					}
					while(rs.next()){
						remainTotal=remainTotal+rs.getDouble(1);
						estimatedTotal=estimatedTotal+rs.getDouble(2);
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		}
		System.out.println("("+name+")Precomm : "+((estimatedTotal-remainTotal)/estimatedTotal)*100.0);
		return ((estimatedTotal-remainTotal)/estimatedTotal)*100.0;
	}
	
	/**
	 * @param name
	 * @return MilestonePrecommDoneForOneDB
	 */
	public int getMilestonePrecommDoneForOneDB(String name) {
		
		int estimatedTotal = 0;
		int remainTotal = 0;
			String request= "SELECT sum(TA@DAILY1@REPORT.RemainToDo) as Remain,sum(TA@DAILY1@REPORT.Estimated) as Estimated FROM TA@DAILY1@REPORT inner join SYS on TA@DAILY1@REPORT.SYS_Id=SYS.SYS_Id where (P_or_C='P') and (sys."+db.getMilestoneColumn()+" like '"+name+"') ";
			String requestV4= "SELECT sum(TA@DAILY@REPORT.RemainToDo) as Remain,sum(TA@DAILY@REPORT.Estimated) as Estimated FROM TA@DAILY@REPORT inner join SUBSYSTEM on TA@DAILY@REPORT.SYS=SUBSYSTEM.SYS where (P_or_C='P') and (SUBSYSTEM.START_UP_TEST like '"+name+"') ";
			try {
				if(stmt!=null){
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4);
					} else {
						this.rs= stmt.executeQuery(request);
					}
					while(rs.next()){
						remainTotal=remainTotal+rs.getInt(1);
						estimatedTotal=estimatedTotal+rs.getInt(2);
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		return estimatedTotal-remainTotal;
	}
	
	/**
	 * @param name
	 * @return MilestonePrecommTotalForOneDB
	 */
	public int getMilestonePrecommTotalForOneDB(String name) {
		
		int estimatedTotal = 0;
		int remainTotal = 0;
			String request= "SELECT sum(TA@DAILY1@REPORT.RemainToDo) as Remain,sum(TA@DAILY1@REPORT.Estimated) as Estimated FROM TA@DAILY1@REPORT inner join SYS on TA@DAILY1@REPORT.SYS_Id=SYS.SYS_Id where (P_or_C='C') and (sys."+db.getMilestoneColumn()+" like '"+name+"') ";
			String requestV4= "SELECT sum(TA@DAILY@REPORT.RemainToDo) as Remain,sum(TA@DAILY@REPORT.Estimated) as Estimated FROM TA@DAILY@REPORT inner join SUBSYSTEM on TA@DAILY@REPORT.SYS=SUBSYSTEM.SYS where (P_or_C='C') and (SUBSYSTEM.START_UP_TEST like '"+name+"') ";
			try {
				if(stmt!=null){
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4);
					} else {
						this.rs= stmt.executeQuery(request);
					}
					while(rs.next()){
						remainTotal=remainTotal+rs.getInt(1);
						estimatedTotal=estimatedTotal+rs.getInt(2);
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		return estimatedTotal;
	}
	
	/**
	 * @param name
	 * @return MilestoneCommTotalForOneDB
	 */
	public int getMilestoneCommTotalForOneDB(String name) {
		
		int estimatedTotal = 0;
		int remainTotal = 0;
			String request= "SELECT sum(TA@DAILY1@REPORT.RemainToDo) as Remain,sum(TA@DAILY1@REPORT.Estimated) as Estimated FROM TA@DAILY1@REPORT inner join SYS on TA@DAILY1@REPORT.SYS_Id=SYS.SYS_Id where (P_or_C='P') and (sys."+db.getMilestoneColumn()+" like '"+name+"') ";
			String requestV4= "SELECT sum(TA@DAILY@REPORT.RemainToDo) as Remain,sum(TA@DAILY@REPORT.Estimated) as Estimated FROM TA@DAILY@REPORT inner join SUBSYSTEM on TA@DAILY@REPORT.SYS=SUBSYSTEM.SYS where (P_or_C='P') and (SUBSYSTEM.START_UP_TEST like '"+name+"') ";
			try {
				if(stmt!=null){
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4);
					} else {
						this.rs= stmt.executeQuery(request);
					}
					while(rs.next()){
						remainTotal=remainTotal+rs.getInt(1);
						estimatedTotal=estimatedTotal+rs.getInt(2);
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		return estimatedTotal;
	}
	
	/**
	 * @param milestone
	 * @return SubSystemNumberRFCForMilestone
	 */
	public int getSubSystemNumberRFCForMilestone(String milestone){
		
		
		Iterator<Database> it = GeneralConfig.databases.iterator();
		int acc =0 ;
		while(it.hasNext()){
		    if(this.connect(it.next())){
			String request="SELECT count(RFC_Date) as NbrSSRFC FROM SYS WHERE (Rev_Code = 'r')and (SYS.SYS_Id>0) and (SYS."+db.getMilestoneColumn()+" like '" + milestone + "')";
			String requestV4="SELECT count(RFC_Date) as NbrSSRFC FROM SUBSYSTEM WHERE (Rev_Code = 'r')and (SUBSYSTEM.SYS_Id>0) and (SUBSYSTEM.START_UP_TEST like '" + milestone + "')";
			try {
				if(stmt!=null){
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4);
					} else {
						this.rs= stmt.executeQuery(request);
					}
					while(rs.next()){
						acc=acc+rs.getInt(1);
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		}
		return acc;
	}
	
	/**
	 * @param milestone
	 * @return SubSystemNumberRFCForMilestoneForOneDB
	 */
	public int getSubSystemNumberRFCForMilestoneForOneDB(String milestone){
		String request="SELECT count(RFC_Date) as NbrSSRFC FROM SYS WHERE (Rev_Code = 'r')and (SYS.SYS_Id>0) and (SYS."+db.getMilestoneColumn()+" like '" + milestone + "')";
		String requestV4="SELECT count(RFC_Date) as NbrSSRFC FROM SUBSYSTEM WHERE (Rev_Code = 'r')and (SUBSYSTEM.SYS_Id>0) and (SUBSYSTEM.START_UP_TEST like '" + milestone + "')";
		try {
			if(stmt!=null){
				if(icapsV4){
					this.rs= stmt.executeQuery(requestV4);
				} else {
					this.rs= stmt.executeQuery(request);
				}
				while(rs.next()){
					return rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace(System.out);
		}
		return -1;
	}

	/**
	 * @param milestoneName
	 * @param db
	 * @return TotalCommToDoWithoutConnection
	 */
	public Integer getTotalCommToDoWithoutConnection(String milestoneName, Database db) {
			String request="SELECT sum(TA@DAILY1@REPORT.Estimated) as TotalMhrsComm FROM SYS INNER JOIN TA@DAILY1@REPORT ON SYS.SYS_Id = TA@DAILY1@REPORT.SYS_Id WHERE (SYS."+db.getMilestoneColumn()+" like '" + milestoneName + "') AND (TA@DAILY1@REPORT.P_or_C = 'C')and SYS.Rev_Code='R' ";
			String requestV4="SELECT sum(TA@DAILY@REPORT.Estimated) as TotalMhrsComm FROM SUBSYSTEM INNER JOIN TA@DAILY@REPORT ON SUBSYSTEM.SYS = TA@DAILY@REPORT.SYS WHERE (SUBSYSTEM.START_UP_TEST like '" + milestoneName + "') AND (TA@DAILY@REPORT.P_or_C = 'C')and SUBSYSTEM.Rev_Code='R' ";
			try {
				if(stmt!=null){
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4);
					} else {
						this.rs= stmt.executeQuery(request);
					}
					while(rs.next()){
						return rs.getInt(1);
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		return 0;
	}

	/** return the number of Sub-systems belonging to a milestone
	 * @param name
	 * @return integer
	 */
	public int getSsNumberForMilestone(String name) {
	    Iterator<Database> it = GeneralConfig.databases.iterator();
		int acc =0 ;
		while(it.hasNext()){
		    if(this.connect(it.next())){
			String request="SELECT count(SYS) FROM SYS WHERE (Rev_Code = 'r')and (SYS.SYS_Id>0) and (SYS."+db.getMilestoneColumn()+" like '" + name + "')";
			String requestV4="SELECT count(SYS) FROM SUBSYSTEM WHERE (Rev_Code = 'r')and (SUBSYSTEM.SYS_Id>0) and (SUBSYSTEM.START_UP_TEST like '" + name + "')";
			try {
				if(stmt!=null){
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4);
					} else {
						this.rs= stmt.executeQuery(request);
					}
					while(rs.next()){
						acc=acc+rs.getInt(1);
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		}
		return acc;
	}
	
	/**
	 * Does a Sub-system belongs to a milestone
	 * @param subsystemNumber
	 * @param milestoneName
	 * @return boolean
	 */
	public boolean doesSubsystemBelongsToMilestone(String subsystemNumber, String milestoneName){
	    Iterator<Database> it = GeneralConfig.databases.iterator();
		while(it.hasNext()){
		    if(this.connect(it.next())){
			String request="SELECT count(SYS) FROM SYS WHERE (Rev_Code = 'r')and (SYS.SYS='"+subsystemNumber+"') and (SYS."+db.getMilestoneColumn()+" like '" + milestoneName + "')";
			String requestV4="SELECT count(SYS) FROM SUBSYSTEM WHERE (Rev_Code = 'r')and (SUBSYSTEM.SYS='"+subsystemNumber+"') and (SUBSYSTEM.START_UP_TEST like '" + milestoneName + "')";
			try {
				if(stmt!=null){
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4);
					} else {
						this.rs= stmt.executeQuery(request);
					}
					while(rs.next()){
						if(rs.getInt(0)>0)
						    return true;
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		}
		return false;
	}
	
	/**Return the Sub-system list belonging to a milestone
	 * @param milestoneName
	 * @return SubsystemList
	 */
	public ArrayList<String> subsystemListForAMilestone(String milestoneName){
	    ArrayList<String> result = new ArrayList<String>();
	    Iterator<Database> it = GeneralConfig.databases.iterator();
		while(it.hasNext()){
		    if(this.connect(it.next())){
			String request="SELECT SYS FROM SYS WHERE (Rev_Code = 'r')and (SYS.SYS_Id>0) and (SYS."+db.getMilestoneColumn()+" like '" + milestoneName + "')";
			String requestV4="SELECT SYS FROM SUBSYSTEM WHERE (Rev_Code = 'r')and (SUBSYSTEM.SYS_Id>0) and (SUBSYSTEM.START_UP_TEST like '" + milestoneName + "')";
			try {
				if(stmt!=null){
					if(icapsV4){
						this.rs= stmt.executeQuery(requestV4);
					} else {
						this.rs= stmt.executeQuery(request);
					}
					while(rs.next()){
						result.add(rs.getString(0));
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		}
		return result;
	}
}
