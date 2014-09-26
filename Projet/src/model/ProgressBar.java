package model;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import conf.Utils;

/**
 * 
 * @author S.Trémouille
 * This class is used to display a progress bar
 */

public class ProgressBar extends JComponent {
    private static final long serialVersionUID = 7292955466054560742L;
	private Double progress;
	private double x,y;
	private double width,height;
	private Milestone m;
	private boolean leftToRight;
	private String type;
	
	
	/**
	 * @param leftToRight
	 * @param type
	 * @param progress
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param milestone
	 */
	public ProgressBar(boolean leftToRight,String type,Double progress, double x, double y, double width, double height,Milestone milestone) {
		super();
		this.type=type;
		this.progress = progress;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.m=milestone;
		this.leftToRight=leftToRight;
	}
	
	@Override
	public void paintComponent(Graphics g){
		g.setColor(m.getMilestoneMode().getProgressBarBackgroundColor());
		if ((m.isIndeterminated()||(!m.isInTADAILYREPORT()&&type=="COMM"))&&type!="DOC"){
		    if(leftToRight){
			g.fillRect((int)x,(int)y,(int)width,(int)height);
        			
        			g.setColor(m.getMilestoneMode().getBorderColor());
        			g.drawRect((int)x,(int)y,(int)width,(int)height);
        			//Progress
        			g.setColor(Color.LIGHT_GRAY);
        			Utils.printSimpleStringLeftAligned(g, type+": "+"N/A", (int)width, (int)(x+Math.round(width*0.1)),(int) (y+height/3));
        		} else {
        			g.fillRect((int)x,(int)y,(int)width,(int)height);
        			g.setColor(m.getMilestoneMode().getBorderColor());
        			g.drawRect((int)x,(int)y,(int)width,(int)height);
        			//Progress
        			/*if((Double)progress!=0.0){
        				g.setColor(getBarColor());
        				g.fillRect((int)Math.round((x+1+((width*(1.0-progress/100.0))))),(int)y+1,(int)Math.round(width*progress/100.0)-1,(int)height-1);
        			}*/
        			g.setColor(Color.LIGHT_GRAY);
        			Utils.printSimpleStringRightAligned(g, "N/A"+" :"+type, (int)Math.round(width*0.9), (int)x ,(int) (y+height/3));
        		}
		}
		else if((!m.isIndeterminated()||type=="DOC")){
        		if(leftToRight){
        			g.fillRect((int)x,(int)y,(int)width,(int)height);
        			
        			g.setColor(m.getMilestoneMode().getBorderColor());
        			g.drawRect((int)x,(int)y,(int)width,(int)height);
        			//Progress
        			if(progress!=0.0){
        				g.setColor(getBarColor());
        				g.fillRect((int)x+1,(int)y+1,(int)(width*progress/100.0)-1,(int)height-1);
        			}
        			g.setColor(m.getMilestoneMode().getBorderColor());
        			Utils.printSimpleStringLeftAligned(g, type+": "+String.valueOf(progress)+"%", (int)width, (int)(x+Math.round(width*0.1)),(int) (y+height/3));
        		} else {
        			g.fillRect((int)x,(int)y,(int)width,(int)height);
        			g.setColor(m.getMilestoneMode().getBorderColor());
        			g.drawRect((int)x,(int)y,(int)width,(int)height);
        			//Progress
        			if(progress!=0.0){
        				g.setColor(getBarColor());
        				g.fillRect((int)Math.round((x+1+((width*(1.0-progress/100.0))))),(int)y+1,(int)Math.round(width*progress/100.0)-1,(int)height-1);
        			}
        			g.setColor(m.getMilestoneMode().getBorderColor());
        			Utils.printSimpleStringRightAligned(g, String.valueOf(progress)+"%"+" :"+type, (int)Math.round(width*0.9), (int)x ,(int) (y+height/3));
        		}
		}
	}
	
	/**
	 * @return Color
	 * Color is a function of the progress value
	 */
	public Color getBarColor(){
		if(progress<90)
			return new Color(255, (int)Math.round((222.0/100.0)*progress), 0);
		else
			return new Color((int)Math.round(255.0-10*(((255.0-171)/100.0)*(progress-90))), 255, (int)Math.round(9*(171/100.0)*(progress-90)));
	}
}
