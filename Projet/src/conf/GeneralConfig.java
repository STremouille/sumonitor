package conf;

import java.awt.Shape;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.colorchooser.ColorChooserComponentFactory;

import model.Milestone;
import view.SettingsFrame.BrowseListener;
import worker.InitSUDocWorker;
import worker.UpdateSUDocWorker;
import database.Database;

/**
 * 
 * @author S.Trémouille
 * test GIT
 */
public class GeneralConfig {
	
	public static String version = "1.0.7";
	
	public static boolean loadMode;
	/**
	 * Milestone Height
	 */
	public static int milestoneHeight = 120;
	/**
	 * Milestone Width
	 */
	public static int milestoneWidth = 150;
	/**
	 * Page Width Ratio
	 */
	public static int pageWidthRatio;
	/**
	 * Page Height Ratio
	 */
	public static int pageHeightRatio;
	/**
	 * Page Width
	 */
	public static int pageWidth;
	/**
	 * Page Height
	 */
	public static int pageHeight;
	/**
	 * Current value of zoom
	 */
	public static double zoom = 100.0;
	/**
	 * Zoom coefficient use to multiply/divide the current zoom value in order to zoom in/out
	 */
	public static double coeffZoom = 1.3333;
	/**
	 * Cell Width, cell are used to aligned milestones
	 */
	public static double cellWidth;
	/**
	 * Cell Height, cell are used to aligned milestones
	 */
	public static double cellHeight;
	/**
	 * Is title bar centered ?
	 */
	public static boolean centeredTitleBar=true;
	/**
	 * Is title bar left aligned ?
	 */
	public static boolean leftAlignedTitleBar=false;
	/**
	 * Documentation path
	 */
	public static String SUDocPath="";
	/**
	 * Print mode, activated when printing the draw panel
	 */
	public static boolean printMode=false;
	
	/**
	 * Databases
	 */
	public static ArrayList<Database> databases;
	/**
	 * Is ICAPS connection uses TCP/IP, if not it uses ODBC link
	 */
	public static boolean TCPIPConnection = false;
	/**
	 * ICAPS database user login
	 */
	public static String login="sa";
	/**
	 * ICAPS database user password
	 */
	public static String password="Pvision_2008";
	/**
	 * Column name in the database where milestones are referenced
	 */
	public static String milestoneColumn="Data2";
	/**
	 * ICAPS database server name
	 */
	public static String serverName="FREP-L107796";
	/**
	 * ICAPS database name
	 */
	public static String dbName="ICAPS";
	/**
	 * ICAPS database instance
	 */
	public static String instanceName="SQLEXPRESS";
	
	/**
	 * Number of point on a target
	 */
	public static int targetAccuracy;
	/**
	 * Specify the cell of the excel sheet where the documentation progress is referenced
	 */
	public static String excelFileCell="i3";
	
	/**
	 * is title enable on the draw
	 */
	public static boolean titleEnable=true;
	/**
	 * is title block enable on the draw
	 */
	public static boolean titleBlockEnable=true;
	/**
	 * is title block right aligned
	 */
	public static boolean titleBlockRightAligned=false;
	
	/**
	 * last time the documentation has been updated
	 */
	public static Date lastDocUpdateDate ;
	/**
	 * last time the database has been updated
	 */
	public static ArrayList<java.sql.Date> lastDBUpdateDate ;
	
	/**
	 * name of the last save, used for the action save
	 */
	public static String saveName="";
	/**
	 * ODBC link name
	 */
	public static String odbcLinkName="";
	
	/**
	 * Define the number of pixel necessary to aligned width and height between two sequence bar
	 */
	public static int magnetization = 20;
	
	/**
	 * logSUDoc , where documentation progress is stored
	 */
	public static String logSUDoc;
	
	/**
	 * where operated subsystems number is stored
	 */
	public static String subSysOperated;
	
	
	
	/**
	 * Cache for milestone's target
	 */
	public static HashMap<String,Object> cacheForTarget;
	
	/**
	 * cache for sub-system list 
	 */
	public static HashMap<String,Object> cacheForSSlist;
	
	/**
	 * PunchList displayed on milestone
	 */
	public static boolean plDisplayed;
	/**
	 * Commissioning Progress displayed
	 */
	public static boolean commProgressDisplayed;
	/**
	 * Documentation progress displayed
	 */
	public static boolean docProgressDisplayed;
	/**
	 * Sub-System AOC progress displayed
	 */
	public static boolean ssAOCdisplayed;
	
	public static boolean stepProgress;
	
	/**
	 * Expert Mode activated
	 */
	public static boolean expertMode;
	
	/**
	 * use to store contractor logo of the project title block
	 */
	public static byte[] contractorLogo;
	/**
	 * use to store company logo of the project title block
	 */
	public static byte[] companyLogo;
	/**
	 * use to store project logo of the project title block
	 */
	public static byte[] projectLogo;
	
	/**
	 * last update dates display
	 */
	public static boolean titleBarDisplayDates=false;
	
	/**
	 * Alignment grid size for milestone
	 */
	public static int milestoneAlignementGridSize;
	/**
	 * graphic quality 0 : speed , 1 : big quality
	 */
	public static boolean graphicQuality;
	
	/**
	 * Label for doc progress bar on milestone
	 */
	public static String labelForDoc;
	
	public static float pageWidthCoeff;
	public static float pageHeightCoeff;
	public static int roundCoeff;
	public static int roundCoeffSUT;
	
	public static ArrayList<String> stepsField;
	public static TreeMap<Integer, Boolean> stepFieldVisible;
	
	public static ArrayList<String> shapesName ;
	public static int stepCharacterLimit;
	
	public static JColorChooser colorChooser;
	
	/**
	 * Invoke this method to make a zoom in order to recalculate the width of elements on the drawing
	 * @param newZoomValue
	 * @return true if everything work well
	 */
	public static boolean updateForZoom(double newZoomValue) {
		if (newZoomValue >= 56  && newZoomValue <= 133 ) {

			zoom = newZoomValue;
			//System.out.print(zoom);
			pageHeightRatio = (int)Math.round(zoom * 6.0);
			pageWidthRatio =(int)Math.round(zoom * 8.0);
			
			//pageHeight=(int)Math.round(zoom * 6.0*2.35);
			//pageWidth= (int)Math.round(zoom * 8.0*2.5);
			
			pageHeight=(int)Math.round(zoom * 6.0*2.35*pageHeightCoeff);
			pageWidth= (int)Math.round(zoom * 8.0*2.5*pageWidthCoeff);
			
			milestoneHeight = (int) Math.round(zoom * 6.0 * (12.0 / 60.0));
			
			//System.out.println("Milestone Height : "+milestoneHeight);
			milestoneWidth = (int) Math.round(zoom * 8.0 * (15.0 / 80.0));
			cellWidth = (int)Math.round((zoom * 8.0 * (15.0 / 80.0))/milestoneAlignementGridSize);
			cellHeight = (int) Math.round((zoom * 6.0 * (12.0 / 60.0))/milestoneAlignementGridSize);
			roundCoeff=(int)Math.round(GeneralConfig.milestoneHeight/7);
			roundCoeffSUT=(int)Math.round(roundCoeff/2.0);
			return true;
		}
		return false;	
	}
	
	/**
	 * Set the documentation path and generate the excel file using the milestones given in parameter
	 * @param path
	 * @param milestones	
	 * @param browseListener 
	 */
	public static void setDocPath(String path,TreeMap<Integer, Milestone> milestones,BrowseListener browseListener){
			final Executor exec = Executors.newCachedThreadPool();
			//System.out.println(path);
			SUDocPath=path;
			//int result = -2;
			//if(new File(GeneralConfig.SUDocPath).exists()){
			//	result = JOptionPane.showConfirmDialog( null, "Would you mean to overwrite the xls file", "alert", JOptionPane.OK_OPTION);
			//}
			InitSUDocWorker worker=null;
			//if(result==0){
				//worker = new InitSUDocWorker(true,m,e);
				//JOptionPane.showMessageDialog(null, "Overwriting");
			//} else{
				worker = new InitSUDocWorker(false,milestones,exec,browseListener);
				//JOptionPane.showMessageDialog(null, "Not Overwriting");
			//}
			exec.execute(worker);
	}
	
	/**
	 * Method invoked when doc initialization is ended
	 * @param milestones
	 * @param executor
	 * @param browseListener 
	 */
	public static void initEnded(TreeMap<Integer, Milestone> milestones,Executor executor,BrowseListener browseListener){
		UpdateSUDocWorker w = new UpdateSUDocWorker(milestones,browseListener,false);
		executor.execute(w);
	}
	
	/**
	 * Initialization
	 */
	public static void init() {
		pageHeightCoeff=(float) 1.0;
		pageWidthCoeff=(float) 1.0;
		updateForZoom(100);
		databases = new ArrayList<Database>();
		expertMode=false;
		login="";
		password="";
		milestoneColumn="Data2";
		serverName="";
		dbName="";
		instanceName="";
		targetAccuracy=100;
		SUDocPath="";
		titleEnable=true;
		titleBlockEnable=false;
		titleBlockRightAligned=true;
		
		graphicQuality=true;
		
		plDisplayed=false;
		commProgressDisplayed=true;
		docProgressDisplayed=true;
		ssAOCdisplayed=false;
		stepProgress=false;
		
		cacheForSSlist=new HashMap<String, Object>();
		cacheForTarget=new HashMap<String, Object>();
		
		logSUDoc="";
		subSysOperated="";
		
		milestoneAlignementGridSize=2;
		labelForDoc="DOC";
		//System.out.println("End Init Configuration");
		
		stepsField = new ArrayList<String>();
		stepsField.add("Date");
		stepsField.add("Procedure");
		stepsField.add("Group");
		stepsField.add("Estimated_Man_Hours");
		stepsField.add("Weight");
		stepsField.add("Planning_Id");
		stepsField.add("Equipment");
		
		stepFieldVisible = new TreeMap<Integer, Boolean>();
		stepFieldVisible.put(stepsField.indexOf("Date"), false);
		stepFieldVisible.put(stepsField.indexOf("Procedure"), false);
		stepFieldVisible.put(stepsField.indexOf("Group"), false);
		stepFieldVisible.put(stepsField.indexOf("Estimated_Man_Hours"), false);
		stepFieldVisible.put(stepsField.indexOf("Weight"), false);
		stepFieldVisible.put(stepsField.indexOf("Planning_Id"), false);
		stepFieldVisible.put(stepsField.indexOf("Equipment"), false);
		
		shapesName = new ArrayList<String>();
		shapesName.add("Rounded Rectangle");
		shapesName.add("Rectangle");
		shapesName.add("Hexagon");
		shapesName.add("Document");
		shapesName.add("Octogone");
		
		stepCharacterLimit=12;
		
		colorChooser = new JColorChooser();
		AbstractColorChooserPanel[] accp = {ColorChooserComponentFactory.getDefaultChooserPanels()[0]};
		colorChooser.setChooserPanels(accp);
		colorChooser.setPreviewPanel(new JPanel());
	}
	
}
