package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.xslf.model.geom.Path;

import model.mode.AvailableMode;
import model.mode.MilestoneMode;
import model.mode.Mode;
import model.mode.NormalMode;
import model.mode.SelectedMode;

import com.sun.org.apache.bcel.internal.generic.NEW;

import conf.GeneralConfig;
import conf.Utils;
import connector.JConnector;

public class StartUpStep extends InteractiveBar implements MovableItem{
	
	private boolean operated;
	private boolean selectedForConnection;
	private MilestoneMode mode;
	private String longDescription;
	private ArrayList<Milestone> destMilestone;
	private ArrayList<StartUpStep> destSUT;
	private ArrayList<JConnector> connectors;
	private String relatedToThisMilestone;
	private TreeMap<String, String> attr;
	private TreeMap<String, Boolean> localAttrToDisplay;
	private String shape;
	private Font font;
	
	private String secondLine;
	
	
	public TreeMap<String, Boolean> getLocalAttrToDisplay() {
		return localAttrToDisplay;
	}

	private boolean localAttrRules;

	public String getRelatedToThisMilestone() {
		return relatedToThisMilestone;
	}

	public void setRelatedToThisMilestone(String relatedToThisMilestone) {
		this.relatedToThisMilestone = relatedToThisMilestone;
	}

	public StartUpStep(String title, int x, int y, int width, int height) {
		super(title, x, y, width, height);
		attr = new TreeMap<String, String>();
		localAttrToDisplay = new TreeMap<String, Boolean>();
		localAttrRules = false;
		this.selectedForConnection=false;
		this.shape="Rounded Rectangle";
		this.mode=new NormalMode();
		this.destMilestone=new ArrayList<Milestone>();
		this.destSUT=new ArrayList<StartUpStep>();
		this.setRelatedToThisMilestone("Choose A Milestone");
		setSecondLine("");
	}
	
	@Override
	public void paint(Graphics g){
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
		
		// Connector Drawing
		Iterator<JConnector> itConnector = connectors.iterator();
		while (itConnector.hasNext()) {
			//System.out.println("connecting "+this.name);
			itConnector.next().paint(g);
		}
		
		
//		shapesName.add("Rounded Rectangle");
//		shapesName.add("Simple Rectangle");
//		shapesName.add("Saphir");
//		shapesName.add("Shifted Rectangle");
		
		initBorders();
		font = new Font("Arial", Font.BOLD, (int) (GeneralConfig.milestoneHeight/13.1));
		Map<TextAttribute, Object> map =
			    new Hashtable<TextAttribute, Object>();
		map.put(TextAttribute.UNDERLINE,
			    TextAttribute.UNDERLINE_ON);
		font = font.deriveFont(map);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setFont(font);
		if(operated){
			g2d.setColor(mode.getOperatedColor());
		} else{
			g2d.setColor(getColor());
		}
		//g2d.setColor(getColor());
		
		computeWidth(g);
		
		switch (GeneralConfig.shapesName.indexOf(shape)) {
		case 0:	
			//Rounded Rectangle
			g2d.fillRoundRect((int)Math.round(getDoubleX()), (int)Math.round(getDoubleY()), (int)Math.round(getDoubleWidth()), (int)Math.round(getDoubleHeight()),GeneralConfig.roundCoeffSUT,GeneralConfig.roundCoeffSUT);
			g2d.setColor(mode.getTextColor());
			g2d.drawRoundRect((int)Math.round(getDoubleX()), (int)Math.round(getDoubleY()), (int)Math.round(getDoubleWidth()), (int)Math.round(getDoubleHeight()),GeneralConfig.roundCoeffSUT,GeneralConfig.roundCoeffSUT);
			break;
		case 1:	
			//Simple Rectangle
			g2d.fillRect((int)Math.round(getDoubleX()), (int)Math.round(getDoubleY()), (int)Math.round(getDoubleWidth()), (int)Math.round(getDoubleHeight()));
			g2d.setColor(mode.getTextColor());
			g2d.drawRect((int)Math.round(getDoubleX()), (int)Math.round(getDoubleY()), (int)Math.round(getDoubleWidth()), (int)Math.round(getDoubleHeight()));
			break;
		case 2:
			//Saphir
			// draw GeneralPath (polygon)
			int x1Points[] = {(int)Math.round(getDoubleX()+getHeight()/4), (int)Math.round(getDoubleX()+getWidth()-getHeight()/4), (int)Math.round(getDoubleX()+getWidth()), (int)Math.round(getDoubleX()+getWidth()-getHeight()/4),(int)Math.round(getDoubleX()+getHeight()/4),(int)Math.round(getDoubleX())};
			int y1Points[] = {(int)Math.round(getDoubleY()), (int)Math.round(getDoubleY()), (int)Math.round(getDoubleY())+getHeight()/2, (int)Math.round(getDoubleY()+getHeight()),(int)Math.round(getDoubleY()+getHeight()),(int)Math.round(getDoubleY()+getHeight()/2)};
			GeneralPath polygon = 
			        new GeneralPath(GeneralPath.WIND_EVEN_ODD,
			                        x1Points.length);
			polygon.moveTo(x1Points[0], y1Points[0]);

			for (int index = 1; index < x1Points.length; index++) {
			        polygon.lineTo(x1Points[index], y1Points[index]);
			};

			polygon.closePath();
			g2d.fill(polygon);
			
			g2d.setColor(mode.getTextColor());
			g2d.draw(polygon);
			break;
		case 3:
			//Document
			// draw GeneralPath (polygon)
			// create new CubicCurve2D.Double
			CubicCurve2D c = new CubicCurve2D.Double();
			// draw CubicCurve2D.Double with set coordinates
			c.setCurve((int)Math.round(getDoubleX()), (int)Math.round(getDoubleY()+getDoubleHeight()),
					(int)Math.round(getDoubleX()+getWidth()/4),(int)Math.round(getDoubleY()+getDoubleHeight()+getDoubleHeight()/4),
					(int)Math.round(getDoubleX()+getDoubleWidth()-getDoubleWidth()/4), (int)Math.round(getDoubleY()+getDoubleHeight()-getDoubleHeight()/4),
						(int)Math.round(getDoubleX()+getDoubleWidth()), (int)Math.round(getDoubleY()+getDoubleHeight()));
			int xPoints[] = {(int)Math.round(getDoubleX()+getWidth()), (int)Math.round(getDoubleX()+getWidth()), (int)Math.round(getDoubleX())};
			int yPoints[] = {(int)Math.round(getDoubleY()+getHeight()), (int)Math.round(getDoubleY()), (int)Math.round(getDoubleY()), (int)Math.round(getDoubleY()+getHeight())};
			Path2D p2d = new Path2D.Double();
			GeneralPath polygonn = 
			        new GeneralPath(GeneralPath.WIND_EVEN_ODD,
			                        xPoints.length);
			polygonn.moveTo(xPoints[0], yPoints[0]);

			for (int index = 1; index < xPoints.length; index++) {
			        polygonn.lineTo(xPoints[index], yPoints[index]);
			};
			p2d.append(polygonn, true);
			p2d.append(c, true);
			p2d.closePath();
			g2d.fill(p2d);
			
			g2d.setColor(mode.getTextColor());
			
			g2d.draw(p2d);
			break;
			
		case 4:
			//OCTOGONE
			int x2Points[] = {(int)Math.round(getDoubleX()+getWidth()/4), (int)Math.round(getDoubleX()+getWidth()-getWidth()/4), (int)Math.round(getDoubleX()+getWidth()),(int)Math.round(getDoubleX()+getWidth()) ,(int)Math.round(getDoubleX()+getWidth()-getWidth()/4),(int)Math.round(getDoubleX()+getWidth()/4),(int)Math.round(getDoubleX()),(int)Math.round(getDoubleX())};
			int y2Points[] = {(int)Math.round(getDoubleY()), (int)Math.round(getDoubleY()), (int)Math.round(getDoubleY()+getHeight()/4), (int)Math.round(getDoubleY()+getHeight()-getHeight()/4),(int)Math.round(getDoubleY()+getHeight()),(int)Math.round(getDoubleY()+getHeight()),(int)Math.round(getDoubleY()+getHeight()-getHeight()/4),(int)Math.round(getDoubleY()+getHeight()/4)};
			GeneralPath octogon = 
			        new GeneralPath(GeneralPath.WIND_EVEN_ODD,
			                        x2Points.length);
			octogon.moveTo(x2Points[0], y2Points[0]);

			for (int index = 1; index < x2Points.length; index++) {
			        octogon.lineTo(x2Points[index], y2Points[index]);
			};

			octogon.closePath();
			g2d.fill(octogon);
			
			g2d.setColor(mode.getTextColor());
			g2d.draw(octogon);
			break;
		default:
			break;
		}
		
		g2d.setColor(mode.getTextColor());
		if(!getName().equals(""))
			Utils.printSimpleStringCentered(g2d, getName(),(int)Math.round(getDoubleWidth()),(int)Math.round(getDoubleX()), (int)Math.round(getDoubleY()+g2d.getFontMetrics().getHeight()));
		if(!getSecondLine().equals(""))
			Utils.printSimpleStringCentered(g2d, getSecondLine(),(int)Math.round(getDoubleWidth()),(int)Math.round(getDoubleX()), (int)Math.round(getDoubleY()+2*g2d.getFontMetrics().getHeight()));
		int acc = 2;
		int coeff = g2d.getFontMetrics().getHeight();
		if(!localAttrRules){
			for(int i : GeneralConfig.stepFieldVisible.keySet()){
				if(GeneralConfig.stepFieldVisible.get(i)){
					acc++;
					font = new Font("Arial", Font.PLAIN, (int) (GeneralConfig.milestoneHeight/16.0));
					g2d.setFont(font);
					Utils.printSimpleStringLeftAligned(g2d, GeneralConfig.stepsField.get(i).replaceAll("_", " ")+" : ",(int)Math.round(getDoubleWidth()),(int)Math.round(getDoubleX()), (int)Math.round(getDoubleY()+acc*coeff));
					int offset = g2d.getFontMetrics().stringWidth(GeneralConfig.stepsField.get(i).replaceAll("_", " ")+" : ");
					font = new Font("Arial", Font.BOLD, (int) (GeneralConfig.milestoneHeight/16.0));
					g2d.setFont(font);
					Utils.printSimpleStringLeftAligned(g2d,getAttr(GeneralConfig.stepsField.get(i)),(int)Math.round(getDoubleWidth()),(int)Math.round(getDoubleX()+offset), (int)Math.round(getDoubleY()+acc*coeff));
				}
			}
		} else {
			for(int i : GeneralConfig.stepFieldVisible.keySet()){
				if(localAttrToDisplay
						.get(GeneralConfig.stepsField.get(i))!=null && localAttrToDisplay
								.get(GeneralConfig.stepsField.get(i))){
					acc++;
					font = new Font("Arial", Font.PLAIN, (int) (GeneralConfig.milestoneHeight/16.0));
					g2d.setFont(font);
					Utils.printSimpleStringLeftAligned(g2d, GeneralConfig.stepsField.get(i).replaceAll("_", " ")+" : ",(int)Math.round(getDoubleWidth()),(int)Math.round(getDoubleX()), (int)Math.round(getDoubleY()+acc*coeff));
					int offset = g2d.getFontMetrics().stringWidth(GeneralConfig.stepsField.get(i).replaceAll("_", " ")+" : ");
					font = new Font("Arial", Font.BOLD, (int) (GeneralConfig.milestoneHeight/16.0));
					g2d.setFont(font);
					Utils.printSimpleStringLeftAligned(g2d,getAttr(GeneralConfig.stepsField.get(i)),(int)Math.round(getDoubleWidth()),(int)Math.round(getDoubleX()+offset), (int)Math.round(getDoubleY()+acc*coeff));
				}
			}
		}
		
		
		
		//g.fillOval((int)Math.round(getDoubleX()+getDoubleHeight()/8.0), (int)Math.round(getDoubleY()+getDoubleHeight()/4.0), (int)Math.round(getDoubleHeight()/2.0), (int)Math.round(getDoubleHeight()/2.0));
		
		if(isSelected()){
			g2d.setColor(Color.red);
			g2d.drawRect((int)Math.round(getDoubleX()), (int)Math.round(getDoubleY()), (int)Math.round(getDoubleWidth()), (int)Math.round(getDoubleHeight()));
		}
		
	}
		

	public ArrayList<StartUpStep> getDestSUT() {
		return destSUT;
	}

	public ArrayList<Milestone> getDestMilestone() {
		return destMilestone;
	}

	public ArrayList<JConnector> getConnectors(){
		return connectors;
	}
	public boolean isSelectedForConnection() {
		return selectedForConnection;
	}

	public void setSelectedForConnection(boolean b) {
		if(b)
			mode = new SelectedMode();
		else
			mode = new AvailableMode();
		this.selectedForConnection=b;
	}

	public void setMode(MilestoneMode mode) {
		this.mode=mode;
	}

	public void addDest(Milestone milestone) {
		this.destMilestone.add(milestone);
	}

	public void addDestSUT(StartUpStep startUpTask) {
		this.destSUT.add(startUpTask);
	}

	public void setOperated(boolean b) {
		operated=b;
	}

	public boolean isOperated() {
		return operated;
	}

	@Override
	public boolean equals(Object arg0) {
		if(arg0.getClass()==StartUpStep.class){
			StartUpStep sut = (StartUpStep) arg0;
			if(sut.getName().equals(this.getName()) 
					&& sut.getDoubleX().equals(this.getDoubleX()) 
					&& sut.getDoubleY().equals(this.getDoubleY()) 
					&& this.getDoubleHeight().equals(sut.getDoubleHeight()) 
					&& this.getDoubleWidth().equals(sut.getDoubleWidth()))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public void move(double x, double y) {
		this.setX(this.getX()+x);
		this.setY(this.getY()+y);
	}

	public String getAttr(String string) {
		if(attr.containsKey(string)){
			return attr.get(string);
		} else {
			
			return "";
		}
	}
	
	public TreeMap<String, String> getAttr() {
		return attr;
	}

	public void putAttr(String indexString, String text) {
		attr.put(indexString, text);
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public boolean isLocalAttrRule() {
		return localAttrRules;
	}
	
	public void setLocalAttrRule(boolean b) {
		this.localAttrRules=b;
	}

	public String getShapeName() {
		return shape;
	}
	
	public void setShapeName(String sn) {
		this.shape=sn;
	}
	
	public void computeWidth(Graphics g){
		int resWidth = getWidth();
		Iterator<String> it = GeneralConfig.stepsField.iterator();
		while(it.hasNext()){
			String key = it.next();
			if((GeneralConfig.stepFieldVisible.get(GeneralConfig.stepsField.indexOf(key))
					&&!localAttrRules)
					||(localAttrRules
					&&localAttrToDisplay.get(key)!=null
					&&localAttrToDisplay.get(key))){
				resWidth=Math.max(resWidth,g.getFontMetrics().stringWidth(key+attr.get(key)));
				System.out.println(key+attr.get(key) + " : " +g.getFontMetrics().stringWidth(key+attr.get(key)));
			}
		}
		if(resWidth!=0)
			setWidth(resWidth);
	}

	public String getSecondLine() {
		return secondLine;
	}

	public void setSecondLine(String secondLine) {
		this.secondLine = secondLine;
	}
	
}
