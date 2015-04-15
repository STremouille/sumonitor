package subsystem;

import java.util.Scanner;

import conf.GeneralConfig;

/**
 * 
 * @author S.Trémouille
 *
 */

public class SubSysOperatedSaver {
	
	
	/**
	 * @param subsystemNumber
	 * @return isAlreadyOperated
	 */
	public boolean isAlreadyOperated(String subsystemNumber){
		Scanner s = new Scanner(GeneralConfig.subSysOperated);
		s.useDelimiter(" - ");
		while(s.hasNext()){
			String tmp = s.next();
			if(subsystemNumber.equals(tmp)){
				s.close();
				return true;
			}
		}
		s.close();
		return false;
	}
	
	/**
	 * @param subSystemNumber
	 */
	public void appendOperatedSS(String subSystemNumber){
	    	System.out.println("Before ADD " + GeneralConfig.subSysOperated);
		GeneralConfig.subSysOperated = GeneralConfig.subSysOperated + subSystemNumber + " - " ;
		System.out.println("After  ADD " + GeneralConfig.subSysOperated);
	}
	
	/**
	 * @param subSystemNumber
	 */
	public void removeOperatedSS(String subSystemNumber){
		System.out.println("Before RM " + GeneralConfig.subSysOperated);
		String tmpSSO = "";
		Scanner scanner = new Scanner(GeneralConfig.subSysOperated);
		scanner.useDelimiter(" - ");
		while(scanner.hasNext()){
			String tmp = scanner.next();
			if(!(tmp.equals(subSystemNumber))){
				tmpSSO=tmpSSO + tmp + " - ";
			}
		}
		
		//recopy the tmp 
		scanner.close();
		GeneralConfig.subSysOperated=tmpSSO;
		System.out.println("After  RM " + GeneralConfig.subSysOperated);
	}
	
	
	/**
	 * remove all SubSystem Operated
	 */
	public void resetSubSystemOperated(){
		GeneralConfig.subSysOperated="";
	}
	
}
