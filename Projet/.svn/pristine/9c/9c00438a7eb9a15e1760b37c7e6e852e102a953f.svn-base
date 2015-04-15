package target;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

import javax.swing.SwingWorker;

import model.Milestone;
import model.StartUpSequence;
import database.Request;

/**
 * Worker which fetched all data required for the target from databases
 * @author S.Trémouille
 *
 */

public class TargetWorker extends SwingWorker<HashMap<String,Object>, String> {
	HashMap<String,Object> res;
	Milestone m;
	int accuracy;
	TargetView parent;
	StartUpSequence sus;
	
	/**
	 * Constructor
	 * @param m
	 * @param accuracy
	 * @param parent
	 * @param projectTarget 
	 * @param sus 
	 */
	public TargetWorker(Milestone m,int accuracy,TargetView parent,boolean projectTarget,StartUpSequence sus){
	    this.m=m;
	    
	    res=new HashMap<String, Object>();
	    this.accuracy=accuracy;
	    this.parent=parent;
	    this.sus=sus;
	    setProgress(0);
		
	}

	@Override
	protected HashMap<String, Object> doInBackground() throws Exception {
	    	if(m.getName()=="%"){
	    	    firePropertyChange("targetDate", 0, 1);
	    	    TreeMap<Date, Milestone> milestoneSortByTargetDate = new TreeMap<Date, Milestone>();
	    	    for(int i : sus.getMilestones().keySet()){
	    		milestoneSortByTargetDate.put(sus.getMilestone(i).getTargetDate(), sus.getMilestone(i));
	    	    }
	    	    
	    	    boolean endLoop=false;
	    	    for(Date date : milestoneSortByTargetDate.descendingKeySet()){
	    		Request r = new Request();
	    		if(!endLoop){
        	    		if(r.isMilestoneInDatabase(milestoneSortByTargetDate.get(date).getName())){
        	    		    m.setTargetDate(date);
        	    		    endLoop=true;
        	    		}
	    		}
	    	    }
	    	}
	    	firePropertyChange("checking", 1, 1);
		Thread.yield();
		Request r = new Request();
		//Initialisation of data
		ArrayList<Date> date = new ArrayList<Date>();
		ArrayList<Integer> progress = new ArrayList<Integer>();
		ArrayList<Integer> nbreSSRFSU = new ArrayList<Integer>();
		ArrayList<Integer> punchlistOpened = new ArrayList<Integer>();
		
		long step = 100;
		long acc=0;
		Date lastDateG = new Date(),firstDate = new Date();
		if(r.getTaskNumberForMilestone(m.getName())<0){
		    this.cancel(true);
		    firePropertyChange("abortTA", 0, 1);
		    Thread.yield();
		}
		if(r.getSubSystemNumberForMilestone(m.getName())<0){
		    this.cancel(true);
		    firePropertyChange("abortSSnumber", 0, 1);
		    Thread.yield();
		}
		if(!r.doesMilestoneHaveOneSubsystemInTADAILYREPORT(m.getName())){
		    this.cancel(true);
		    firePropertyChange("abortNotInTADAILYREPORT", 0, 1);
		    Thread.yield();
		}
		firePropertyChange("init", 0, 1);
		Thread.yield();
		if(!this.isCancelled()){
        		res.put("totalProgress", r.getTotalCommToDo(m.getName()));
        		res.put("totalSS", r.getSubSystemNumberForMilestone(m.getName()));
        		res.put("lastDate", m.getTargetDate());
        		System.out.println("Target Date  --> " + m.getTargetDate());
        		
        		//Here we are doing the step for each point of the target
        		firstDate = r.getFirstDateOfMilestone(m.getName());
        		//HashMap<String,java.sql.Date> lastDates = r.getDataBaseLastUpdateDates();
        		lastDateG = r.getDataBaseLastUpdateDate();
        		
        		//System.out.println(firstDate.getTime()+"->"+m.getTargetDate().getTime());
        		firePropertyChange("init", 1, 0);
        		this.setProgress(0);
        		
        		step = (lastDateG.getTime()-firstDate.getTime())/accuracy;
        		acc=firstDate.getTime();
		}
		while(acc < lastDateG.getTime()&&!this.isCancelled()){
			
			if(Math.round(Float.valueOf((acc-firstDate.getTime()))/(step*(accuracy/100)))<100){
				setProgress(Math.round(Float.valueOf((acc-firstDate.getTime()))/(step*(accuracy/100))));
			}
			acc=acc+step;
			firePropertyChange("progress", 0, getProgress());
			//System.out.println(getProgress() + "% with acc="+acc);
			date.add(new Date(acc));
			progress.add(r.getCommProgressForMilestone(m.getName(), new java.sql.Date(acc)));
			nbreSSRFSU.add(r.getSubSystemNumberAOCForMilestone(m.getName(), new java.sql.Date(acc)));
			punchlistOpened.add(r.getPLOpenForMilestone(m.getName(), new java.sql.Date(acc)));
		}
		//System.out.println("ACC : "+new Date(acc-step));
		//this.setProgress(100);
		
		res.put("date", date);
		res.put("progress", progress);
		res.put("nbreSSRFSU", nbreSSRFSU);
		res.put("punchlistOpened", punchlistOpened);
		return res;
		////////////////////////////////////////////////////////////////////
		//SAVE FOR MULTIPLE SHAPES
		////////////////////////////////////////////////////////////////////
		/*firePropertyChange("init", 0, 1);
		Request r = new Request();
		firePropertyChange("progress", 0, 0);
		res.put("totalProgress", r.getTotalCommToDo(m.getName()));
		firePropertyChange("progress", 0, 16);
		res.put("totalSS", r.getSubSystemNumberForMilestone(m.getName()));
		firePropertyChange("progress", 0, 33);
		res.put("lastDate", m.getTargetDate());
		firePropertyChange("progress", 0, 50);
		
		//Initialisation of data
		HashMap<String,ArrayList<Date>> date = new HashMap<String,ArrayList<Date>>();
		HashMap<String,ArrayList<Integer>> progress = new HashMap<String, ArrayList<Integer>>();
		HashMap<String,ArrayList<Integer>> nbreSSRFSU = new HashMap<String,ArrayList<Integer>>();
		HashMap<String,ArrayList<Integer>> punchlistOpened = new HashMap<String,ArrayList<Integer>>();
		HashMap<String,Integer> totalSS = new HashMap<String, Integer>();
		HashMap<String,Integer> totalProgress = new HashMap<String, Integer>();
		
		//Here we are doing the step for each point of the target
		
		Date firstDate = r.getFirstDateOfMilestone(m.getName());
		HashMap<String,java.sql.Date> lastDates = r.getDataBaseLastUpdateDate();
		Date lastDateG = null;
		for(Date tmp : lastDates.values()){
			if(lastDateG==null||tmp.getTime()<lastDateG.getTime()){
				lastDateG = new Date(tmp.getTime());
			}
		}
		
		res.put("firstDate", firstDate);
		res.put("lastDate", lastDateG);
		
		firePropertyChange("init", 1, 0);
		
		
		this.setProgress(0);
		Iterator<Database> itDB = GeneralConfig.databases.iterator();
		
		while (itDB.hasNext()){
			Database db = itDB.next();
			r.connect(db);
			
			long acc=firstDate.getTime();
			long step = (lastDateG.getTime()-firstDate.getTime())/accuracy;
			long p = (long) 0.0;
			
			ArrayList<Date> dateForOneDb = new ArrayList<Date>();
			ArrayList<Integer> progressForOneDB = new ArrayList<Integer>();
			ArrayList<Integer> nbreSSRFSUForOneDB = new ArrayList<Integer>();
			ArrayList<Integer> punchListOpenedForOneDB = new ArrayList<Integer>();
			
			
			while(acc < lastDateG.getTime()&&!this.isCancelled()){
				//if(Math.round(Float.valueOf((acc-firstDate.getTime()))/(step*(accuracy/100)))<100);setProgress(Math.round(Float.valueOf((acc-firstDate.getTime()))/(step*(accuracy/100))));
				dateForOneDb.add(new Date(acc));
				progressForOneDB.add(r.getCommProgressForMilestoneForOneDB(m.getName(), new java.sql.Date(acc),db));
				nbreSSRFSUForOneDB.add(r.getSSNumberAOCForMilestoneForOneDB(m.getName(), new java.sql.Date(acc),db));
				punchListOpenedForOneDB.add(r.getPLOpenForMilestoneForOneDB(m.getName(), new java.sql.Date(acc),db));
				acc=acc+step;
				firePropertyChange("progress", 0, Math.round(Float.valueOf((acc-firstDate.getTime()))/(step*(accuracy/100))));
				System.out.println(getProgress() + "% with acc="+acc);
			}
			
			
			date.put(db.getAlias(), dateForOneDb);
			progress.put(db.getAlias(), progressForOneDB);
			nbreSSRFSU.put(db.getAlias(), nbreSSRFSUForOneDB);
			punchlistOpened.put(db.getAlias(), punchListOpenedForOneDB);
			totalSS.put(db.getAlias(), r.getSubSystemNumberForMilestoneWithoutConnection(m.getName(),db));
			totalProgress.put(db.getAlias(), r.getTotalCommToDoWithoutConnection(m.getName(),db));
		}
		
		
		
		//this.setProgress(100);
		
		res.put("date", date);
		res.put("progress", progress);
		res.put("nbreSSRFSU", nbreSSRFSU);
		res.put("punchlistOpened", punchlistOpened);
		res.put("totalSS", totalSS);
		res.put("totalProgress", totalProgress);
		return res;*/
	}

	@Override
	protected void done() {
		if(!isCancelled()){
			super.done();
			parent.end();
		}
	}
	
	/**
	 * @return Result of the Worker
	 */
	public HashMap<String, Object> getResult(){
		return res;
	}
	

}
