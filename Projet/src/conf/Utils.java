package conf;

import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JComponent;

import org.apache.poi.hssf.record.PageBreakRecord.Break;

import model.Milestone;
import model.StartUpStep;

/**
 * 
 * @author S.Trémouille
 *
 */
public class Utils {
	private static FileInputStream fisSource;
	private static FileOutputStream fosDestination;

	/**
	 * This method is made to center a text
	 * @param graphics
	 * @param textToCenter
	 * @param width
	 * @param XPos
	 * @param YPos
	 */
	public static void printSimpleStringCentered(Graphics graphics, String textToCenter, int width, int XPos, int YPos) {
		int stringLen = (int) graphics.getFontMetrics().getStringBounds(textToCenter, graphics).getWidth();
		int stringHeight = (int) graphics.getFontMetrics().getStringBounds(textToCenter, graphics).getHeight() / 2;
		int start = width / 2 - stringLen / 2;
		graphics.drawString(textToCenter, start + XPos, YPos + stringHeight);
	}

	/**
	 * Left aligned the text
	 * @param graphics
	 * @param textToLeftAligned
	 * @param width
	 * @param XPos
	 * @param YPos
	 */
	public static void printSimpleStringRightAligned(Graphics graphics, String textToLeftAligned, int width, int XPos, int YPos) {
		int stringLen = (int) graphics.getFontMetrics().getStringBounds(textToLeftAligned, graphics).getWidth();
		int stringHeight = (int) graphics.getFontMetrics().getStringBounds(textToLeftAligned, graphics).getHeight() / 2;
		int start = width - (int) (width / 50.0) - stringLen;
		graphics.drawString(textToLeftAligned, start + XPos, YPos + stringHeight);
	}

	/**
	 * Right aligned the text
	 * @param graphics
	 * @param stringTiRightAligned
	 * @param width
	 * @param XPos
	 * @param YPos
	 */
	public static void printSimpleStringLeftAligned(Graphics graphics, String stringTiRightAligned, int width, int XPos, int YPos) {
		int start = (int) (width / 50.0);
		int stringHeight = (int) graphics.getFontMetrics().getStringBounds(stringTiRightAligned, graphics).getHeight() / 2;
		graphics.drawString(stringTiRightAligned, start + XPos, YPos + stringHeight);
	}

	/** 
	 * Operate a ROT13 translation for the text put in parameter
	 * @param textToROT13
	 * @return encodedString
	 */
	public static String rot13(String textToROT13) {
		int abyte = 0;
        StringBuffer tempReturn = new StringBuffer();


        for (int i=0; i<textToROT13.length(); i++) {

            abyte = textToROT13.charAt(i);
            int cap = abyte & 32;
            abyte &= ~cap;
            abyte = ( (abyte >= 'A') && (abyte <= 'Z') ? ((abyte - 'A' + 13) % 26 + 'A') : abyte) | cap;
            abyte = ( (abyte >= '0') && (abyte <= '9') ? ((abyte - '0' + 5) % 10 + '0') : abyte) | cap;
            tempReturn.append((char)abyte);
        }


        return tempReturn.toString();
        
    }
	
	/**
	 * Method use to copy a file, from Source to Destination File
	 * @param sourceFile
	 * @param destFile
	 * @throws IOException
	 */
	public static void copyFile(File sourceFile, File destFile) throws IOException {
	    if(!destFile.exists()) {
	        destFile.createNewFile();
	    }

	    fisSource = null;
	    fosDestination = null;
	    FileChannel source = null;
	    FileChannel destination = null;
	    try {
	    	fisSource = new FileInputStream(sourceFile);
	        source = fisSource.getChannel();
	        fosDestination = new FileOutputStream(destFile);
	        destination = fosDestination.getChannel();

	        // previous code: destination.transferFrom(source, 0, source.size());
	        // to avoid infinite loops, should be:
	        long count = 0;
	        long size = source.size();              
	        while((count += destination.transferFrom(source, count, size-count))<size);
	    }
	    finally {
	    	/*if(source.size()==0){
	    		fisSource.close();
	            source.close();
	            fosDestination.close();
	            destination.close();
	    		copyFile(sourceFile, destFile);
	    	}*/
	        if(source != null) {
	        	fisSource.close();
	            source.close();
	        }
	        if(destination != null) {
	        	fosDestination.close();
	            destination.close();
	        }
	    }
	}
	
	/**
	 * @param milestoneArrayListToDuplicate
	 * @return milestoneArrayListDuplicated
	 */
	public static TreeMap<Integer, Milestone> duplicateMilestoneMap(TreeMap<Integer, Milestone> milestoneArrayListToDuplicate,TreeMap<Integer, StartUpStep> startUpStepArraylistToDuplicate){
	    TreeMap<Integer, Milestone> res = new TreeMap<Integer, Milestone>();
	    for(int key : milestoneArrayListToDuplicate.keySet()){
	    	res.put(key, new Milestone(milestoneArrayListToDuplicate.get(key)));
	    }
	    
	    /*for(int key : startUpStepArraylistToDuplicate.keySet()){
	    	resStep.put(key, new StartUpStep(startUpStepArraylistToDuplicate.get(key)));
	    }*/
	    
	    for(int key : res.keySet()){
			Milestone m = res.get(key);
			Milestone mSrc = milestoneArrayListToDuplicate.get(key);
			Iterator<Milestone> it = mSrc.getDestMilestone().iterator();
			Iterator<StartUpStep> itt = mSrc.getDestSUT().iterator();
			while(it.hasNext()){
				Milestone tmp = it.next();
			    for(int key2 : res.keySet()){
	        		    if(res.get(key2).equal(tmp)){
	        		    	m.addDest(res.get(key2));
	        		    } 
			    }
			}
			while(itt.hasNext()){
				m.addDestSUT(itt.next());
			}
			
	    }
	    
	    for(int key : startUpStepArraylistToDuplicate.keySet()){
	    	ArrayList<Milestone> buffer = new ArrayList<Milestone>();
	    	StartUpStep sus = startUpStepArraylistToDuplicate.get(key);
	    	Iterator<Milestone> it = sus.getDestMilestone().iterator();
			while(it.hasNext()){
				Milestone tmp = it.next();
			    for(int key2 : res.keySet()){
	        		    if(res.get(key2).equal(tmp)){
	        		    	buffer.add(res.get(key2));
	        		    } 
			    }
			}
			
			
			sus.getDestMilestone().clear();
			Iterator<Milestone> itBuffer = buffer.iterator();
			while(itBuffer.hasNext()){
				sus.addDest(itBuffer.next());
			}
	    }
	    return res;
	}
	
	public static Integer getKeyForAValue(Map<Integer, String> map, String value) {
	    for (Integer entry : map.keySet()) {
	        if (value.equals(map.get(entry))) {
	        	return entry;
	        }
	    }
	    return -1;
	}

	public static Integer getIndexOf(TreeMap<Integer, Milestone> milestones,
			Milestone dest) {
		for(int i : milestones.keySet()){
			if(milestones.get(i).equals(dest))
				return i;
		}
		return -1;
	}
	
	public static Integer getIndexOf(TreeMap<Integer, StartUpStep> steps,
			StartUpStep dest) {
		for(int i : steps.keySet()){
			if(steps.get(i).equals(dest))
				return i;
		}
		return -1;
	}
	
}