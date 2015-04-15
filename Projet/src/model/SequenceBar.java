package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import conf.GeneralConfig;
import conf.Utils;

/**
 * 
 * @author S.Tremouille
 * 
 */

public class SequenceBar extends InteractiveBar implements MovableItem{
    	private static final long serialVersionUID = 573555550772617901L;
	private double extendedHeight;
	private Rectangle extendedRectangle;
	private Color dottedLineColor;
	private boolean center,left;
	
	/**
	 * @param s
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param extendedHeight (this is the height of the dotted line)
	 */
	public SequenceBar(String s, int x, int y, int width, int height, int extendedHeight) {
		super(s, x, y, width, height);
		this.setColor(Color.lightGray);
		this.setDottedLineColor(Color.blue);
		this.extendedHeight=extendedHeight;
		initBorders();
		this.left=true;
		this.center=false;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		initBorders();
		Font f = new Font("Arial Unicode MS", Font.BOLD, (int) (GeneralConfig.pageHeightRatio/40.0));
		g.setFont(f);
		g.setColor(getColor());
		//g.fillRoundRect(getX(), (int)Math.round(getDoubleY()), (int)Math.round(getDoubleWidth()), (int)Math.round(getDoubleHeight()), GeneralConfig.roundCoeff, GeneralConfig.roundCoeff);
		g.fillRect(getX(), (int)Math.round(getDoubleY()), (int)Math.round(getDoubleWidth()), (int)Math.round(getDoubleHeight()));
		g.setColor(Color.black);
		//g.drawRoundRect(getX(), (int)Math.round(getDoubleY()), (int)Math.round(getDoubleWidth()), (int)Math.round(getDoubleHeight()), GeneralConfig.roundCoeff, GeneralConfig.roundCoeff);
		g.drawRect(getX(), (int)Math.round(getDoubleY()), (int)Math.round(getDoubleWidth()), (int)Math.round(getDoubleHeight()));
		
		//g.drawRect(getX(), (int)Math.round(getDoubleY()), (int)Math.round(getDoubleWidth()), (int)Math.round(getDoubleHeight()));
		double charL = g.getFontMetrics().getStringBounds(getName(), g).getWidth();
		if((int)charL>this.getWidth()){
			String s = (String) getName().subSequence(0, getName().length()-1);
			while(g.getFontMetrics().getStringBounds(s, g).getWidth()>this.getWidth()){
				s = (String) getName().subSequence(0, s.length()-1);
			}
			if(left&&!center){
				Utils.printSimpleStringLeftAligned(g, s,getWidth(),getX(),getY()+g.getFontMetrics().getHeight()/2);
			} else if(!left&&!center) {
				Utils.printSimpleStringRightAligned(g, s,getWidth(),getX(),getY()+g.getFontMetrics().getHeight()/2);
			} else {
				Utils.printSimpleStringCentered(g, s,getWidth(),getX(),getY()+g.getFontMetrics().getHeight()/2);
			}
		} else {
			if(left&&!center){
				Utils.printSimpleStringLeftAligned(g, getName(),getWidth(),getX(),getY()+g.getFontMetrics().getHeight()/2);
			} else if(!left&&!center) {
				Utils.printSimpleStringRightAligned(g, getName(),getWidth(),getX(),getY()+g.getFontMetrics().getHeight()/2);
			} else {
				Utils.printSimpleStringCentered(g, getName(),getWidth(),getX(),getY()+g.getFontMetrics().getHeight()/2);
			}
		}
		
		
		if(isSelected()){
			g.setColor(Color.red);
			g.fillRect(getX()-GeneralConfig.milestoneHeight/50, (int)Math.round(getDoubleY())-GeneralConfig.milestoneHeight/50,GeneralConfig.milestoneHeight/25 ,GeneralConfig.milestoneHeight/25 );
			g.fillRect(getX()-GeneralConfig.milestoneHeight/50+(int)Math.round(getDoubleWidth()), (int)Math.round(getDoubleY())-GeneralConfig.milestoneHeight/50,GeneralConfig.milestoneHeight/25 ,GeneralConfig.milestoneHeight/25 );
			g.fillRect(getX()-GeneralConfig.milestoneHeight/50,(int)Math.round(getDoubleY())-GeneralConfig.milestoneHeight/50+(int)Math.round(getDoubleHeight()),GeneralConfig.milestoneHeight/25 ,GeneralConfig.milestoneHeight/25 );
			g.fillRect(getX()-GeneralConfig.milestoneHeight/50+(int)Math.round(getDoubleWidth()), (int)Math.round(getDoubleY())-GeneralConfig.milestoneHeight/50+(int)Math.round(getDoubleHeight()),GeneralConfig.milestoneHeight/25 ,GeneralConfig.milestoneHeight/25 );
		}
		for(int i=getHeight();i<extendedHeight;i++){
			g.setColor(getDottedLineColor());
			g.drawLine(getX(), (int)Math.round(getDoubleY())+i, getX(), (int)Math.round(getDoubleY())+i+2);
			g.drawLine(getX()+(int)Math.round(getDoubleWidth()), (int)Math.round(getDoubleY())+i, getX()+(int)Math.round(getDoubleWidth()), (int)Math.round(getDoubleY())+i+2);
			i=i+9;
		}
		//g.drawRect(getX(), getY(),getWidth(), getHeight());
	}

	@Override
	public void initBorders() {
		super.initBorders();
		extendedRectangle = new Rectangle(getX(), (int)Math.round(getDoubleY()+extendedHeight*0.85), (int)Math.round(getDoubleWidth()), (int)Math.round(extendedHeight*0.15));
	}
	
	/**
	 * @return extendedHeight (this is the height of the dotted line)
	 */
	public double getExtendedHeight(){
		return extendedHeight;
	}

	/**
	 * @return extendedRectangle (this is the rectangle with the dotted line)
	 */
	public Rectangle getExtendedRectangle(){
		return extendedRectangle;
	}

	/**
	 * @param extendedHeight (this is the height of the dotted line)
	 */
	public void setExtendedHeight(double extendedHeight) {
		if(extendedHeight-this.getHeight()<GeneralConfig.milestoneHeight){
			this.extendedHeight=GeneralConfig.milestoneHeight;
		}
		else{
			this.extendedHeight=extendedHeight;
		}
		initBorders();
	}

	/**
	 * @return dottedLineColor
	 */
	public Color getDottedLineColor() {
		return dottedLineColor;
	}

	/**
	 * @param dottedLineColor
	 */
	public void setDottedLineColor(Color dottedLineColor) {
		this.dottedLineColor = dottedLineColor;
	}

	@Override
	public ResizeDirection getResizeDirection(Point p) {
		ResizeDirection res = super.getResizeDirection(p);
		if(res==null){
			if(extendedRectangle.getBounds().contains(p))
				return ResizeDirection.EXTENDEDSOUTH;
			else
				return null;
		}
		else
			return res;
	}

	/**
	 * @param rectangle
	 * @param extended
	 * It's better to use this method instead of setBounds inherited from InteractiveBar
	 */
	public void setBoundsWithExtended(Rectangle rectangle, int extended) {
		super.setX(rectangle.x);
		super.setY(rectangle.y);
		super.setWidth(rectangle.width);
		super.setHeight(rectangle.height);
		this.extendedHeight=extended;
		initBorders();
	}

	/**
	 * Set the sequence bar center aligned
	 */
	public void setCenterAligned() {
		left=false;
		center=true;
	}
	
	/**
	 * Set the sequence bar left aligned
	 */
	public void setLeftAligned() {
		left=true;
		center=false;
	}
	
	/**
	 * Set the sequence bar right aligned
	 */
	public void setRightAligned() {
		left=false;
		center=false;
	}
	
	/**
	 * @return boolean
	 */
	public boolean isLeftAligned(){
		return left;
	}
	
	/**
	 * @return Boolean
	 */
	public boolean isCenterAligned(){
		return center;
	}

	@Override
	public void move(double x, double y) {
		this.setX(this.getX()+x);
		this.setY(this.getY()+y);
	}

	
}
