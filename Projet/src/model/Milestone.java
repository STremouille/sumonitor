package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import model.mode.MilestoneMode;
import model.mode.NormalMode;
import subsystem.SubSysOperatedSaver;
import conf.GeneralConfig;
import conf.Utils;
import connector.JConnector;
import database.Request;

/**
 * 
 * @author S.Trémouille
 *
 */

public class Milestone extends JComponent implements MovableItem{
    	private static final long serialVersionUID = -2111814139961408959L;
	private String name, description;
	private Date targetDate,scopeDoneDate;
	private Double commProgress, documentationProgress,AOCProgress,precommProgress,ssRFCProgress,ssOperatedProgress,stepProgress;
	private int punchOpened;
	private int punchAOpened,punchBOpened,punchCOpened;
	private int ssNumber;
	

	private double X, Y;
	private ArrayList<Milestone> destMilestone;
	private ArrayList<StartUpStep> destSUT;
	private ArrayList<JConnector> connectors;
	private boolean isSelected,isSelectedForConnection;
	private MilestoneMode mode;
	private int[] cellNumber;
	private ArrayList<String> content;
	private Graphics g;
	private ArrayList<String> subsytemOperated;
	private boolean operated;
	
	//If the milestone is empty => progress value = "N/A"
	private boolean indeterminated;
	//If no subsystem of the milestone has no progress
	private boolean inTADAILYREPORT;

	/**
	 * @param milestoneName
	 */
	public Milestone(String milestoneName) {
		this(milestoneName,0.0,0.0);
	}
	
	/**
	 * @param milestoneName
	 * @param x
	 * @param y
	 */
	public Milestone(String milestoneName,double x,double y) {
		this.cellNumber=new int[2];
		this.cellNumber[0]=-1;
		this.cellNumber[1]=-1;
		this.name = milestoneName;
		this.description = "";
		Calendar curDateCalendar = Calendar.getInstance();
		this.targetDate = curDateCalendar.getTime();
		this.X = x;
		this.Y = y;
		destMilestone = new ArrayList<Milestone>();
		destSUT = new ArrayList<StartUpStep>();
		mode = new NormalMode();
		this.operated=false;
		this.punchOpened=0;
		this.punchAOpened=0;this.punchBOpened=0;this.punchCOpened=0;
		this.precommProgress=0.0;
		this.ssRFCProgress=0.0;
		this.commProgress=0.0;
		this.documentationProgress=0.0;
		this.AOCProgress=0.0;
		this.setSsNumber(0);
		this.setSsOperatedProgress(0.0);
		this.subsytemOperated=new ArrayList<String>();
		this.setIndeterminated(true);
		this.setInTADAILYREPORT(false);
	}
	
	/**
	 * @param milestone
	 */
	public Milestone(Milestone milestone) {
	    this(milestone.getName(),0.0,0.0);
		this.description = milestone.getDescription();
		this.targetDate = milestone.getTargetDate();
		this.commProgress = milestone.getCommProgress();
		this.documentationProgress =milestone.getDocumentationProgress();
		this.X = milestone.getDoubleX();
		this.Y = milestone.getDoubleY();
		this.operated=milestone.isOperated();
		this.punchOpened=milestone.getPunchOpened();
		this.punchAOpened=milestone.getPunchAOpened();this.punchBOpened=milestone.getPunchBOpened();this.punchCOpened=milestone.getPunchCOpened();
		this.precommProgress=milestone.getPrecommProgress();
		this.ssRFCProgress=milestone.getSsRFCProgress();
		this.AOCProgress=milestone.getAOCProgress();
		this.setIndeterminated(milestone.isIndeterminated());
		this.setInTADAILYREPORT(milestone.isInTADAILYREPORT());
		this.setSsNumber(milestone.getSsNumber());
		this.setSsOperatedProgress(milestone.getSsOperatedProgress());
		//A routine to duplicate dest has to be done
	}

	@Override
	public String toString() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss");
		String lastUp = formatter.format(targetDate);
		StringBuffer sb = new StringBuffer();
		sb.append(this.getName() + '\n');
		sb.append("--------------------" + '\n');
		sb.append("last update : " + lastUp + '\n');
		sb.append("--------------------" + '\n');
		sb.append("desription : " + this.description + '\n');
		sb.append("--------------------" + '\n');
		sb.append("task : " + this.commProgress + '\n');
		sb.append("doc : " + this.documentationProgress + '\n');
		sb.append("--------------------" + '\n');
		return sb.toString();
	}

	/**
	 * @return PrecommProgress
	 */
	public Double getPrecommProgress() {
		return precommProgress;
	}

	/**
	 * @param precommProgress
	 */
	public void setPrecommProgress(Double precommProgress) {
		int taskProgressTmp = (int) (precommProgress*10);
		double taskPF = taskProgressTmp/10.0;
		this.precommProgress = taskPF;
	}

	/**
	 * @return Sub-system RFC Progress
	 */
	public Double getSsRFCProgress() {
		return ssRFCProgress;
	}

	/**
	 * @param ssRFCProgress
	 */
	public void setSsRFCProgress(Double ssRFCProgress) {
		int taskProgressTmp = (int) (ssRFCProgress*10);
		double taskPF = taskProgressTmp/10.0;
		this.ssRFCProgress = taskPF;
	}

	/**
	 * @return Punch Item A's number
	 */
	public int getPunchAOpened() {
		return punchAOpened;
	}

	/**
	 * @param punchAOpened
	 */
	public void setPunchAOpened(int punchAOpened) {
		this.punchAOpened = punchAOpened;
	}

	/**
	 * @return Punch Item B's number
	 */
	public int getPunchBOpened() {
		return punchBOpened;
	}

	/**
	 * @param punchBOpened
	 */
	public void setPunchBOpened(int punchBOpened) {
		this.punchBOpened = punchBOpened;
	}

	/**
	 * @return Punch Item C's number
	 */
	public int getPunchCOpened() {
		return punchCOpened;
	}

	/**
	 * @param punchCOpened
	 */
	public void setPunchCOpened(int punchCOpened) {
		this.punchCOpened = punchCOpened;
	}
	
	/**
	 * @param isOperated
	 */
	public void setOperated(boolean isOperated){
		this.operated=isOperated;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return String 
	 * milestone's description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Date
	 * return milestone's target date
	 */
	public Date getTargetDate() {
		return targetDate;
	}

	/**
	 * @param targetDate
	 */
	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}

	/**
	 * @return double
	 * Return Commissioning Progress
	 */
	public Double getCommProgress() {
		return commProgress;
	}

	/**
	 * @param commProgress
	 */
	public void setCommProgress(Double commProgress) {
		int taskProgressTmp = (int) (commProgress*10);
		double taskPF = taskProgressTmp/10.0;
		this.commProgress = taskPF;
	}

	/**
	 * @return Double
	 * Return Documentation Progress
	 */
	public Double getDocumentationProgress() {
		return documentationProgress;
	}

	/**
	 * @param documentationProgress
	 */
	public void setDocumentationProgress(Double documentationProgress) {
		int docProgressTmp = (int) (documentationProgress*10);
		double docPF = docProgressTmp/10.0;
		this.documentationProgress =docPF;
	}
	
	@Override
	public int getX() {
		return (int)Math.round(X);
	}
	
	/**
	 * @param x
	 */
	public void setX(double x) {
		X = x;
	}
	
	/**
	 * @return accurate X pos
	 */
	public double getDoubleX(){
		return X;
	}
	
	/**
	 * @return accurate Y pos
	 */
	public double getDoubleY(){
		return Y;
	}

	@Override
	public int getY() {
		return (int)Math.round(Y);
	}

	/**
	 * @param y
	 */
	public void setY(double y) {
		Y = y;
	}

	@Override
	public int getWidth() {
		return GeneralConfig.milestoneWidth;
	}

	/**
	 * @return ArrayList<Milestone> 
	 * Return the ArrayListe of milestone's destinators to connections
	 */
	public ArrayList<StartUpStep> getDestSUT() {
		return destSUT;
	}

	/**
	 * @param dest
	 */
	public void setDestSUT(ArrayList<StartUpStep> dest) {
		this.destSUT = dest;
	}
	
	
	/**
	 * @return ArrayList<Milestone> 
	 * Return the ArrayListe of milestone's destinators to connections
	 */
	public ArrayList<Milestone> getDestMilestone() {
		return destMilestone;
	}

	/**
	 * @param dest
	 */
	public void setDestMilestone(ArrayList<Milestone> dest) {
		this.destMilestone = dest;
	}
	
	public void addDestSUT(StartUpStep sut) {
			this.destSUT.add(sut);
	}

	/**
	 * @param milestone
	 */
	public void addDest(Milestone milestone) {
		if(!this.equals(milestone)){
			//System.out.println("Connection !");
			this.destMilestone.add(milestone);
		}
	}

	@Override
	public Rectangle getBounds() {
		if(GeneralConfig.stepProgress){
			if(!GeneralConfig.plDisplayed&&!GeneralConfig.commProgressDisplayed&&!GeneralConfig.ssAOCdisplayed&&!GeneralConfig.docProgressDisplayed){
				return new Rectangle((int)Math.round(X), (int)Math.round(Y), getWidth(), (int) Math.round(getHeight()*0.75));
			}
			//one line
			else if(((GeneralConfig.plDisplayed&&!GeneralConfig.commProgressDisplayed||!GeneralConfig.plDisplayed&&GeneralConfig.commProgressDisplayed)&&(GeneralConfig.ssAOCdisplayed&&!GeneralConfig.docProgressDisplayed||!GeneralConfig.ssAOCdisplayed&&GeneralConfig.docProgressDisplayed))||(GeneralConfig.docProgressDisplayed&&!GeneralConfig.ssAOCdisplayed&&!GeneralConfig.plDisplayed&&!GeneralConfig.commProgressDisplayed)||((!GeneralConfig.docProgressDisplayed&&GeneralConfig.ssAOCdisplayed&&!GeneralConfig.plDisplayed&&!GeneralConfig.commProgressDisplayed)||(!GeneralConfig.docProgressDisplayed&&!GeneralConfig.ssAOCdisplayed&&GeneralConfig.plDisplayed&&!GeneralConfig.commProgressDisplayed)||(!GeneralConfig.docProgressDisplayed&&!GeneralConfig.ssAOCdisplayed&&!GeneralConfig.plDisplayed&&GeneralConfig.commProgressDisplayed)))
			{
				return new Rectangle((int)Math.round(X), (int)Math.round(Y), getWidth(), (int) Math.round(getHeight()*0.88));
			} else /*two lines*/{
				return new Rectangle((int)Math.round(X), (int)Math.round(Y), getWidth(), getHeight());
			}
		} else {
			if(!GeneralConfig.plDisplayed&&!GeneralConfig.commProgressDisplayed&&!GeneralConfig.ssAOCdisplayed&&!GeneralConfig.docProgressDisplayed){
				return new Rectangle((int)Math.round(X), (int)Math.round(Y), getWidth(), (int) Math.round(getHeight()*0.75-getHeight()*0.125));
			}
			//one line
			else if(((GeneralConfig.plDisplayed&&!GeneralConfig.commProgressDisplayed||!GeneralConfig.plDisplayed&&GeneralConfig.commProgressDisplayed)&&(GeneralConfig.ssAOCdisplayed&&!GeneralConfig.docProgressDisplayed||!GeneralConfig.ssAOCdisplayed&&GeneralConfig.docProgressDisplayed))||(GeneralConfig.docProgressDisplayed&&!GeneralConfig.ssAOCdisplayed&&!GeneralConfig.plDisplayed&&!GeneralConfig.commProgressDisplayed)||((!GeneralConfig.docProgressDisplayed&&GeneralConfig.ssAOCdisplayed&&!GeneralConfig.plDisplayed&&!GeneralConfig.commProgressDisplayed)||(!GeneralConfig.docProgressDisplayed&&!GeneralConfig.ssAOCdisplayed&&GeneralConfig.plDisplayed&&!GeneralConfig.commProgressDisplayed)||(!GeneralConfig.docProgressDisplayed&&!GeneralConfig.ssAOCdisplayed&&!GeneralConfig.plDisplayed&&GeneralConfig.commProgressDisplayed)))
			{
				return new Rectangle((int)Math.round(X), (int)Math.round(Y), getWidth(), (int) Math.round(getHeight()*0.88-getHeight()*0.125));
			} else /*two lines*/{
				return new Rectangle((int)Math.round(X), (int)Math.round(Y), getWidth(), (int) Math.round(getHeight()-getHeight()*0.125));
			}
		}
	}

	@Override
	public int getHeight() {
		return GeneralConfig.milestoneHeight;
	}
	
	/**
	 * @return AOC Progress
	 */
	public Double getAOCProgress() {
		return AOCProgress;
	}

	
	/**
	 * @param aOCProgress
	 */
	public void setAOCProgress(Double aOCProgress) {
		AOCProgress = aOCProgress;
	}

	/**
	 * @return Punch Opened
	 */
	public int getPunchOpened() {
		return punchOpened;
	}

	/**
	 * @param punchOpened
	 */
	public void setPunchOpened(int punchOpened) {
		this.punchOpened = punchOpened;
	}
	
	/**
	 * @param tmp
	 * @return Boolean
	 */
	public boolean equal(JComponent tmp){
		if(tmp.getX()==this.getX()&&tmp.getY()==this.getY()&&tmp.getName()==this.getName())
			return true;
		return false;
	}
	

	@Override
	public void paint(Graphics g) {
		this.g=g;
		Graphics2D g2d = (Graphics2D) g.create();
		double x=getDoubleX();
		double y=getDoubleY();
		
		// Connector milestone
		ArrayList<Milestone> destM = this.getDestMilestone();
		ArrayList<StartUpStep> destSUT = this.getDestSUT();
		connectors = new ArrayList<JConnector>();
		Iterator<Milestone> itM = destM.iterator();
		Iterator<StartUpStep> itSUT = destSUT.iterator();
		if(operated){
			g.setColor(mode.getOperatedColor());
		} else{
			g.setColor(Color.black);
		}
		while (itM.hasNext()) {
			Milestone d = itM.next();
			JConnector connector = new JConnector(this, d, g.getColor());
			connectors.add(connector);
		}
		while (itSUT.hasNext()) {
			StartUpStep d = itSUT.next();
			JConnector connector = new JConnector(this, d, g.getColor());
			connectors.add(connector);
		}
		//g.setColor(Color.black);
		
		// Connector Drawing
		Iterator<JConnector> itConnector = connectors.iterator();
		while (itConnector.hasNext()) {
			//System.out.println("connecting "+this.name);
			itConnector.next().paint(g);
		}
		
		g.setFont(new Font("Arial Unicode MS", Font.TRUETYPE_FONT, (int) (GeneralConfig.pageHeightRatio/55.0)));
		
		// Draw the milestone shadow
		g2d.setColor(mode.getShadowColor());
		g2d.fillRoundRect((int)x+3,(int) y+3, getBounds().width, getBounds().height,GeneralConfig.roundCoeff,GeneralConfig.roundCoeff);
		//g2d.fillRoundRect((int)x+4,(int) y+4, getBounds().width-10, getBounds().height-10,10,10);
		//g.fillRect((int)x+4,(int) y+4, getBounds().width, getBounds().height);
		
		// Title rectangle
		if(this.operated){
			g2d.setColor(mode.getOperatedColor());
		} else {
			g2d.setColor(mode.getTitleColor());
		}
		//g.drawLine(arg0, arg1, arg2, arg3);
		g2d.fillRoundRect((int)x, (int)y, getWidth(),(int)(getHeight()*0.5),GeneralConfig.roundCoeff,GeneralConfig.roundCoeff);
		// Date rectangle
		Double relativePosDate = getHeight() * 0.125;
		//g.setColor(mode.getDateColor());
		if(this.scopeDoneDate!=null&&this.scopeDoneDate.getTime()<=targetDate.getTime()){
			g.setColor(mode.getOperatedColor());
		} else if(Calendar.getInstance().getTime().getTime()<=targetDate.getTime()+86399999/*amount of seconds in one day, in order to compare the actual computer hour to the milestone date at 11:59:59 PM*/){
			g.setColor(mode.getOperatedColor());
		} else {
			g.setColor(mode.getTitleColor());
		}
		if(!GeneralConfig.stepProgress){
			g.fillRect((int)x, (int) (y + relativePosDate), getWidth(), (int) Math.round(getHeight() * 0.125));
			g.setColor(mode.getTextColor());
			g.drawRect((int)x, (int) (y + relativePosDate), getWidth(), (int) Math.round(getHeight() * 0.125));
		} else {
			g.fillRect((int)x, (int) (y + relativePosDate), getWidth(), (int) Math.round(getHeight() * 0.25));
			g.setColor(mode.getTextColor());
			g.drawRect((int)x, (int) (y + relativePosDate), getWidth(), (int) Math.round(getHeight() * 0.25));
		}

		// Description rectangle
		Double relativePosDescription = relativePosDate+getHeight() * 0.125;
		if(GeneralConfig.stepProgress){
			relativePosDescription = relativePosDate+getHeight() * 0.25;
		}
		g.setColor(mode.getDescriptionColor());
		g.fillRect((int)x, (int) (y + relativePosDescription), getWidth(), (int) (getHeight() * 0.25));
		g.setColor(mode.getTextColor());
		g.drawRect((int)x, (int) (y + relativePosDescription), getWidth(), (int) (getHeight() * 0.25));

		// title bar decoration
		g.setColor(mode.getTextColor());
		//g.drawRect((int)x, (int)y, getWidth(), (int) (getHeight() * 0.125));
		//g.drawRect((int)x, (int)y, getWidth(), (int) (getHeight() * 0.125));

		// Text
		Utils.printSimpleStringCentered(g, this.getName().replaceAll("[%_^]", " "), getWidth(), (int)x, (int) (y + getHeight() * 0.0625));
		// Text:Date
		Utils.printSimpleStringLeftAligned(g, "Target Date :", getWidth(), (int)x, (int) (y + relativePosDate+g.getFontMetrics().getHeight()/2));
		if(GeneralConfig.stepProgress){
			Utils.printSimpleStringRightAligned(g, "Steps : "+getStepProgress()+"%", getWidth(), (int)x, (int) (y + relativePosDate+3*g.getFontMetrics().getHeight()/2));
		}
		// date format
		SimpleDateFormat formatter = new SimpleDateFormat("MMM/dd/yyyy");
		String lastUp = formatter.format(this.getTargetDate());
		Utils.printSimpleStringRightAligned(g, lastUp, getWidth(),(int) x, (int) (y + relativePosDate+g.getFontMetrics().getHeight()/2));
		
		g.setFont(new Font("Arial Unicode MS", Font.TRUETYPE_FONT, (int) Math.round(GeneralConfig.pageHeightRatio/65.0)));
		formatDescrition(g);
		// Text:Description
		int possibleLine = (int) (getHeight()*0.25/g.getFontMetrics().getMaxAscent());
		for(int i=0;i<possibleLine&&i<content.size();i++){
			Utils.printSimpleStringLeftAligned(g, this.content.get(i),getWidth(),(int) x, (int)(Math.round((y + relativePosDescription+g.getFontMetrics().getMaxAscent()/2)+i*(g.getFontMetrics().getMaxAscent()))));
		}
		
		g.setFont(new Font("Arial Unicode MS", Font.TRUETYPE_FONT, (int) (GeneralConfig.pageHeightRatio/55.0)));

		Double relativePosProgress = relativePosDescription + getHeight() * 0.25;
		Double relativePosSubsystemInfo;
		//nothing to display
		if(!GeneralConfig.plDisplayed&&!GeneralConfig.commProgressDisplayed&&!GeneralConfig.ssAOCdisplayed&&!GeneralConfig.docProgressDisplayed){
			// Draw the milestone border
		    	if(getSsOperatedProgress()!=100.0){
			    g2d.setColor(mode.getProgressBarBackgroundColor());
			} else {
			    g2d.setColor(mode.getOperatedColor());
			}
		    relativePosSubsystemInfo=relativePosProgress;
		    g2d.fillRect((int)Math.round(x),(int)Math.round(Y+relativePosSubsystemInfo), getBounds().width, (int)Math.round(getHeight()*0.08));
			g2d.fillRoundRect((int)Math.round(x),(int)Math.round(Y+relativePosSubsystemInfo), getBounds().width, (int)Math.round(getHeight()*0.125),GeneralConfig.roundCoeff,GeneralConfig.roundCoeff);
			g.setColor(mode.getTextColor());
			g.setFont(new Font("Calibri", Font.BOLD, (int)(GeneralConfig.pageHeightRatio/55.0)));
			if(this.isIndeterminated()){
			    Utils.printSimpleStringCentered(g, "N/A Sub-systems  N/A% Operated", getWidth(), (int)Math.round(x), (int)Math.round(Y+relativePosSubsystemInfo+g.getFontMetrics().getHeight()/2));
			}
			else{
			    Utils.printSimpleStringCentered(g, this.getSsNumber()+" Sub-systems  "+this.getSsOperatedProgress()+"% Operated", getWidth(), (int)Math.round(x), (int)Math.round(Y+relativePosSubsystemInfo+g.getFontMetrics().getHeight()/2));
			}
			//double relativePosSubsystemInfo2=getHeight() * 0.65;
			//Utils.printSimpleStringRightAligned(g, this.getSsNumber()+" Sub-systems", getWidth(), (int)Math.round(x), (int)Math.round(Y+relativePosSubsystemInfo2));
			g2d.setColor(mode.getBorderColor());
			g2d.drawLine((int)Math.round(x), (int) (Math.round(y)+relativePosProgress), (int)Math.round(x)+getWidth(), (int) (Math.round(y)+relativePosProgress));
			g2d.drawRoundRect((int)Math.round(x),(int) Math.round(y), getWidth(), (int)Math.round(relativePosSubsystemInfo+getHeight()*0.125),GeneralConfig.roundCoeff,GeneralConfig.roundCoeff);
		}
		//one line
		else if(((GeneralConfig.plDisplayed&&!GeneralConfig.commProgressDisplayed||!GeneralConfig.plDisplayed&&GeneralConfig.commProgressDisplayed)&&(GeneralConfig.ssAOCdisplayed&&!GeneralConfig.docProgressDisplayed||!GeneralConfig.ssAOCdisplayed&&GeneralConfig.docProgressDisplayed))||(GeneralConfig.docProgressDisplayed&&!GeneralConfig.ssAOCdisplayed&&!GeneralConfig.plDisplayed&&!GeneralConfig.commProgressDisplayed)||((!GeneralConfig.docProgressDisplayed&&GeneralConfig.ssAOCdisplayed&&!GeneralConfig.plDisplayed&&!GeneralConfig.commProgressDisplayed)||(!GeneralConfig.docProgressDisplayed&&!GeneralConfig.ssAOCdisplayed&&GeneralConfig.plDisplayed&&!GeneralConfig.commProgressDisplayed)||(!GeneralConfig.docProgressDisplayed&&!GeneralConfig.ssAOCdisplayed&&!GeneralConfig.plDisplayed&&GeneralConfig.commProgressDisplayed)))
		{
			// ProgressBar rectangle
			

			g.setColor(mode.getProgressRectangleBackgroundColor());
			g.fillRect((int)x, (int) (y + relativePosProgress), getWidth(), (int) (getHeight() * (0.25/2)));
			g.setColor(mode.getTextColor());
			g.drawRect((int)x, (int) (y + relativePosProgress), getWidth(), (int) (getHeight() * (0.25/2)));
			if(GeneralConfig.plDisplayed){
			    	drawPunchList(g,this,true,relativePosProgress);
			}
			if(GeneralConfig.ssAOCdisplayed){
				ProgressBar aoc = new ProgressBar(false,"AOC",getAOCProgress(), x + getWidth()/2, y + relativePosProgress, getWidth()/2, (int) (getHeight() * 0.25)/2,this);
				aoc.paintComponent(g);
			}
			if(GeneralConfig.commProgressDisplayed){
				ProgressBar comm = new ProgressBar(true,"Comm",getCommProgress(),x, y + relativePosProgress, getWidth()/2, (int) (getHeight() * 0.25)/2,this);
				comm.paintComponent(g);
			}
			if(GeneralConfig.docProgressDisplayed){
				ProgressBar doc = new ProgressBar(false,GeneralConfig.labelForDoc,getDocumentationProgress(), x + getWidth()/2, y + relativePosProgress,  getWidth()/2,(int) (getHeight() * 0.25)/2,this);
				doc.paintComponent(g);
			}
			if(getSsOperatedProgress()!=100.0){
			    g2d.setColor(mode.getProgressBarBackgroundColor());
			} else {
			    g2d.setColor(mode.getOperatedColor());
			}
			relativePosSubsystemInfo=relativePosProgress+getHeight()*0.125;
			g2d.fillRect((int)Math.round(x),(int)Math.round(Y+relativePosSubsystemInfo), getBounds().width, (int)Math.round(getHeight()*0.125/2));
			g2d.fillRoundRect((int)Math.round(x),(int)Math.round(Y+relativePosSubsystemInfo), getBounds().width, (int)Math.round(getHeight()*0.125),GeneralConfig.roundCoeff,GeneralConfig.roundCoeff);
			g.setColor(mode.getTextColor());
			
			g.setFont(new Font("Calibri", Font.BOLD, (int) (GeneralConfig.pageHeightRatio/55.0)));
			//g.drawLine((int)Math.round(X), (int)Math.round(relativePosSubsystemInfo), (int)Math.round(X+getWidth()), (int)Math.round(relativePosSubsystemInfo));
			if(this.isIndeterminated()){
			    Utils.printSimpleStringCentered(g, "N/A Sub-systems  N/A% Operated", getWidth(), (int)Math.round(x), (int)Math.round(Y+relativePosSubsystemInfo+Math.round(getHeight()*0.25)/2-g.getFontMetrics().getMaxAscent()));
			}
			else{
			    Utils.printSimpleStringCentered(g, this.getSsNumber()+" Sub-systems  "+this.getSsOperatedProgress()+"% Operated", getWidth(), (int)Math.round(x), (int)Math.round(Y+relativePosSubsystemInfo+Math.round(getHeight()*0.25)/2-g.getFontMetrics().getMaxAscent()));
			}
			//Utils.printSimpleStringRightAligned(g, this.getSsNumber()+" Sub-systems", getWidth(), (int)Math.round(x), (int)Math.round(Y+relativePosSubsystemInfo2));
			g2d.setColor(mode.getBorderColor());
			g2d.drawLine((int)Math.round(x),(int) Math.round(y+relativePosSubsystemInfo), (int)Math.round(x)+getWidth(), ((int) Math.round(y+relativePosSubsystemInfo)));
			g2d.drawRoundRect((int)Math.round(x),(int) Math.round(y), getWidth(), (int)Math.round(relativePosSubsystemInfo+getHeight()*0.125),GeneralConfig.roundCoeff,GeneralConfig.roundCoeff);
			//g.drawRect((int)Math.round(x),(int) Math.round(y+getHeight()*0.625), getWidth(), (int) (getHeight()*0.25));
		} else {
			// ProgressBar rectangle

			g.setColor(mode.getProgressRectangleBackgroundColor());
			g.fillRect((int)x, (int) (y + relativePosProgress), getWidth(), (int) (getHeight() * (0.25)));
			g.setColor(mode.getTextColor());
			g.drawRect((int)x, (int) (y + relativePosProgress), getWidth(), (int) (getHeight() * (0.25)));
			//P.L.
			if(GeneralConfig.plDisplayed){
			    	drawPunchList(g,this,false,relativePosProgress);
			}
			if(GeneralConfig.commProgressDisplayed){
				ProgressBar comm = new ProgressBar(true,"Comm",getCommProgress(),x, y + relativePosProgress+(0.25/2*getHeight()), getWidth()/2, (int) (getHeight() * 0.25)/2,this);
				comm.paintComponent(g);
			}
			if(GeneralConfig.ssAOCdisplayed){
				ProgressBar aoc = new ProgressBar(false,"AOC",getAOCProgress(), x + getWidth()/2, y +relativePosProgress, getWidth()/2, (int) (getHeight() * 0.25)/2,this);
				aoc.paintComponent(g);
			}
			if(GeneralConfig.docProgressDisplayed){
				ProgressBar doc = new ProgressBar(false,GeneralConfig.labelForDoc,getDocumentationProgress(), x + getWidth()/2, y + relativePosProgress+(0.25/2*getHeight()),  getWidth()/2,(int) (getHeight() * 0.25)/2,this);
				doc.paintComponent(g);
			}
			if(getSsOperatedProgress()!=100.0){
			    g2d.setColor(mode.getProgressBarBackgroundColor());
			} else {
			    g2d.setColor(mode.getOperatedColor());
			}
			relativePosSubsystemInfo=relativePosProgress+getHeight()*0.25;
			g2d.fillRect((int)Math.round(x),(int)Math.round(Y+relativePosSubsystemInfo), getBounds().width, (int)Math.round(getHeight()*0.08));
			g2d.fillRoundRect((int)Math.round(x),(int)Math.round(Y+relativePosSubsystemInfo), getBounds().width, (int)Math.round(getHeight()*0.125),GeneralConfig.roundCoeff,GeneralConfig.roundCoeff);
			g.setColor(mode.getTextColor());
			g.setFont(new Font("Calibri", Font.BOLD, (int) (GeneralConfig.pageHeightRatio/55.0)));
			if(this.isIndeterminated()){
			    Utils.printSimpleStringCentered(g, "N/A Sub-systems  N/A% Operated", getWidth(), (int)Math.round(x), (int)Math.round(Y+relativePosSubsystemInfo+Math.round(getHeight()*0.25)/2-g.getFontMetrics().getMaxAscent()));
			}
			else{
			    Utils.printSimpleStringCentered(g, this.getSsNumber()+" Sub-systems  "+this.getSsOperatedProgress()+"% Operated", getWidth(), (int)Math.round(x), (int)Math.round(Y+relativePosSubsystemInfo+Math.round(getHeight()*0.25)/2-g.getFontMetrics().getMaxAscent()));
			}
			//Utils.printSimpleStringRightAligned(g, this.getSsNumber()+" Sub-sys.", getWidth(), (int)Math.round(x), (int)Math.round(Y+relativePosSubsystemInfo2));
			//g.setColor(mode.getBorderColor());
			
			// Draw the milestone border
			g2d.setColor(mode.getBorderColor());
			g2d.drawLine((int)Math.round(x),(int) Math.round(y+relativePosSubsystemInfo),(int)Math.round(x)+ getWidth(), (int) Math.round(y+relativePosSubsystemInfo));
			g2d.drawRoundRect((int)x,(int) y, getWidth(), (int) Math.round(relativePosSubsystemInfo+getHeight()*0.125),GeneralConfig.roundCoeff,GeneralConfig.roundCoeff);
		}
		
		
		if(this.isSelected&&!GeneralConfig.printMode){
			g2d.setColor(Color.red);
			g2d.drawRoundRect((int)x,(int) y, getBounds().width, getBounds().height,GeneralConfig.roundCoeff, GeneralConfig.roundCoeff);
			/*g.fillRect((int)x-GeneralConfig.milestoneHeight/50, (int)y-GeneralConfig.milestoneHeight/50,GeneralConfig.milestoneHeight/25 ,GeneralConfig.milestoneHeight/25 );
			g.fillRect((int)x-GeneralConfig.milestoneHeight/50+GeneralConfig.milestoneWidth, (int)y-GeneralConfig.milestoneHeight/50,GeneralConfig.milestoneHeight/25 ,GeneralConfig.milestoneHeight/25 );
			g.fillRect((int)x-GeneralConfig.milestoneHeight/50,(int) y-GeneralConfig.milestoneHeight/50+getBounds().height,GeneralConfig.milestoneHeight/25 ,GeneralConfig.milestoneHeight/25 );
			g.fillRect((int)x-GeneralConfig.milestoneHeight/50+getBounds().width, (int)y-GeneralConfig.milestoneHeight/50+getBounds().height,GeneralConfig.milestoneHeight/25 ,GeneralConfig.milestoneHeight/25 );*/
			
		}
		
		
		
	    
		
	}
	
		
	private void drawPunchList(Graphics g,Milestone m,boolean oneLine,double relativePosProgress){
	    if(!m.isIndeterminated()){
	    	if(m.getPunchOpened()>0)
	    	    g.setColor(Color.red);
	    	else
	    	    g.setColor(Color.green);
	    	if(oneLine){
	    	    Utils.printSimpleStringCentered(g, "PL: "+m.getPunchOpened(), m.getWidth()/2,m.getX(), (int) (m.getY() + relativePosProgress + g.getFontMetrics().getMaxAscent()/2));
	    	    g.setColor(m.getMilestoneMode().getBorderColor());
	    	    g.drawRect(m.getX(), (int)(m.getY() + relativePosProgress), m.getWidth()/2, (int) (m.getHeight() * 0.25)/2);
	    	}
	    	else{
	    	    Utils.printSimpleStringCentered(g, "PL: "+m.getPunchOpened(), m.getWidth()/2,m.getX(), (int) (m.getY() + relativePosProgress + g.getFontMetrics().getMaxAscent()/2));
	    	    g.setColor(m.getMilestoneMode().getBorderColor());
	    	    g.drawRect(m.getX(), (int)(m.getY() +relativePosProgress), m.getWidth()/2, (int) (m.getHeight() * 0.25)/2);
	    	}
	    } else {
		if(oneLine){
		    g.setColor(Color.LIGHT_GRAY);
		    Utils.printSimpleStringCentered(g, "PL: N/A", m.getWidth()/2,m.getX(), (int)Math.round(m.getY()+relativePosProgress+g.getFontMetrics().getMaxAscent()/2));
	    	    g.setColor(m.getMilestoneMode().getBorderColor());
	    	    g.drawRect(m.getX(), (int)(m.getY() + relativePosProgress), m.getWidth()/2, (int) (m.getHeight() * 0.25)/2);
		}
		else {
		    g.setColor(Color.LIGHT_GRAY);
		    Utils.printSimpleStringCentered(g, "PL: N/A", m.getWidth()/2,m.getX(), (int)Math.round(m.getY()+relativePosProgress+g.getFontMetrics().getMaxAscent()/2));
	    	    g.setColor(m.getMilestoneMode().getBorderColor());
	    	    g.drawRect(m.getX(), (int)(m.getY() + relativePosProgress), m.getWidth()/2, (int) (m.getHeight() * 0.25)/2);
		}
	    }
	}
	
	/**
	 * Select the milestone
	 */
	public void select(){
		isSelected=true;	
	}

	/**
	 * Unselect the milestone
	 */
	public void unSelect() {
		isSelected=false;
	}
	
	/**
	 * @return Boolean
	 */
	public boolean isSelected(){
		return isSelected;
	}

	/**
	 * @return Boolean
	 * True if the milestone is selected for connection
	 */
	public boolean isSelectedForConnection() {
		return isSelectedForConnection;
	}

	/**
	 * @param isSelectedForConnection
	 */
	public void setSelectedForConnection(boolean isSelectedForConnection) {
		this.isSelectedForConnection = isSelectedForConnection;
	}

	/**
	 * @param mode
	 * refers to MilestoneMode
	 */
	public void setMode(MilestoneMode mode){
		this.mode=mode;
	}
	
	/**
	 * @return MilestoneMode
	 */
	public MilestoneMode getMilestoneMode(){
	    return this.mode;
	}
	
	/**
	 * @return operated
	 */
	public boolean isOperated() {
		return operated;
	}
	
	private void formatDescrition(Graphics g){
		int minWidth=0;
	//	System.out.println(name);
		Scanner scanner = new Scanner(description.trim()+" EOFL");
		scanner.useDelimiter(" ");
		content = new ArrayList<String>();
		int line=0;
		int accWith=0;
		boolean newLine=true;
		String s = "";
		
		while(scanner.hasNext()){
			if(!newLine){
				s = scanner.next();
			} else if(newLine){
				content.add("");
			}
			if(g.getFontMetrics().getStringBounds(s, g).getWidth()>minWidth){
				minWidth=(int) g.getFontMetrics().getStringBounds(s, g).getWidth()+1;
			}
			if(g.getFontMetrics().getStringBounds(s, g).getWidth()>=this.getWidth()){
				s="too long word";
			}
			if(g.getFontMetrics().getStringBounds(s, g).getWidth()<=((this.getWidth()-accWith))){
				if(!s.equals("EOFL")){
	//				System.out.println(s);
				    if(content.get(line).equals(""))
					content.set(line,s);
				    else
					content.set(line, content.get(line)+" "+s);
					accWith=(int) (accWith+g.getFontMetrics().getStringBounds(s+" ", g).getWidth());
					newLine=false;
				}
			}
			else{
	//			System.out.println("new line");
				accWith=0;
				line++;
				newLine=true;
				}
			}
		scanner.close();
	}
	
	/*public void update(boolean uniqueUpdate) {
		//Comm progress
		Request cp = new Request();
		TreeMap<String ,Object> dataCollected = cp.getGeneralProgress(this.getName());
		this.setCommProgress(new Double((int)((Double)(dataCollected.get("comm"))*10)/10));
		this.setAOCProgress(new Double((int)((Double)(dataCollected.get("ssAOC"))*10)/10));
		this.setPunchOpened((Integer) dataCollected.get("PL"));
		/*this.setCommProgress(cp.getMilestoneProgress(this.getName()));
		this.setPunchOpened(cp.getPLOpenForMilestone(this.getName(), new java.sql.Date(Calendar.getInstance().getTime().getTime())));
		if(cp.getSSNumberAOCForMilestone(this.getName(), new java.sql.Date(Calendar.getInstance().getTime().getTime()))>0){
//			System.out.println("AOC SS : "+(new Double(String.valueOf(cp.getSSNumberAOCForMilestone(this.getName(), new java.sql.Date(Calendar.getInstance().getTime().getTime()))))/cp.getSubSystemNumberForMilestone(this.getName()))*100.0);
			Double tmp = new Double(String.valueOf(cp.getSSNumberAOCForMilestone(this.getName(), new java.sql.Date(Calendar.getInstance().getTime().getTime()))))/cp.getSubSystemNumberForMilestone(this.getName())*100.0;
			this.setAOCProgress(new Double(String.valueOf(tmp).substring(0, String.valueOf(tmp).lastIndexOf(".")+1)));
		} else
		{
			this.setAOCProgress(0.0);
		}*/
		/*if(uniqueUpdate){
			//Doc progress
			GetSUDocProgressWorker worker = new GetSUDocProgressWorker(this);
			worker.execute();
		}*/
	//}*/
	
	/**
	 * Method called to notify that documentation processing is ended
	 */
	public void docProgressEnd() {
		Request r = new Request();
		SubSysOperatedSaver ssos = new SubSysOperatedSaver();
		ArrayList<String> ss = r.getSubsystemList(this.name);
		Iterator<String> it = ss.iterator();
		int witness = 0;
		while(it.hasNext()){
			if(!ssos.isAlreadyOperated(it.next())){
				witness++;
			}
		}
		if(commProgress==100&&AOCProgress==100&&punchOpened==0&&documentationProgress==100&&(witness==ss.size())){
			setOperated(true);
		} else {
			setOperated(false);
		}
		//System.out.println("Operated check passed");
	}
	
	/**
	 * @return ArrayList<JConnector>
	 */
	public ArrayList<JConnector> getConnectors(){
		return this.connectors;
	}

	/**
	 * @param number
	 */
	public void addSubSystemOperated(String number){
		subsytemOperated.add(number);
	}
	
	/**
	 * @param number
	 * @return Boolean
	 */
	public boolean removeSubsystemOperated(String number){
		if(subsytemOperated.contains(number)){
			subsytemOperated.remove(number);
			return true;
		} else
			return false;
	}

	/**
	 * @return Boolean
	 * True if milestone's subsystem are empty
	 */
	public boolean isIndeterminated() {
	    return indeterminated;
	}

	/**
	 * @param indeterminated
	 */
	public void setIndeterminated(boolean indeterminated) {
	    this.indeterminated = indeterminated;
	}

	/**
	 * @return Boolean
	 */
	public boolean isInTADAILYREPORT() {
	    return inTADAILYREPORT;
	}

	/**
	 * @param inTADAILYREPORT
	 */
	public void setInTADAILYREPORT(boolean inTADAILYREPORT) {
	    this.inTADAILYREPORT = inTADAILYREPORT;
	}

	public Double getSsOperatedProgress() {
	    return ssOperatedProgress;
	}

	public void setSsOperatedProgress(Double ssOperatedProgress) {
	    this.ssOperatedProgress = ssOperatedProgress;
	}

	public int getSsNumber() {
	    return ssNumber;
	}

	public void setSsNumber(int ssNumber) {
	    this.ssNumber = ssNumber;
	}

	public void updateSsOperatedProgress(ArrayList<String> list) {
	    int acc=0;
	    SubSysOperatedSaver ssos= new SubSysOperatedSaver();
	    Iterator<String> it = list.iterator();
	    while(it.hasNext()){
		if(ssos.isAlreadyOperated(it.next()))
		    acc++;
	    }
	    //Rounding & shorting to get 1 decimal
	    this.ssOperatedProgress=Math.round(((double)acc/(double)ssNumber)*1000)/10.0;
	    //System.out.println(acc+"/"+ssNumber+":"+acc/ssNumber);
	    //System.out.println("SS Operated Progress Calculation ->"+this.name+" :"+this.ssOperatedProgress+" %");
	}

	@Override
	public void move(double x, double y) {
		this.setX(this.getDoubleX()+x);
		this.setY(this.getDoubleX()+y);
	}

	public Date getScopeDoneDate() {
		return scopeDoneDate;
	}

	public void setScopeDoneDate(Date scopeDoneDate) {
		if(this.scopeDoneDate==null){
			this.scopeDoneDate = scopeDoneDate;
		}
	}

	public Double getStepProgress() {
		return stepProgress;
	}

	public void setStepProgress(Double stepProgress) {
		this.stepProgress = stepProgress;
	}

}
