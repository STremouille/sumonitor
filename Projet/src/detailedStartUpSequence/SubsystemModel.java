package detailedStartUpSequence;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JComponent;

import conf.Utils;

/**
 * @author Samuel Trémouille
 */
public class SubsystemModel extends JComponent{
    
    private double x,y;
    private String subsystemNumber;
    public String getSubsystemNumber() {
        return subsystemNumber;
    }

    public void setSubsystemNumber(String subsystemNumber) {
        this.subsystemNumber = subsystemNumber;
    }

    private String subsystemDescription;
    private int width,height;
    private boolean isSelected;
    private ArrayList<String> destinatorForConnections;
    
    /**
     * @param subsystemNumber as refered in ICAPS
     * @param y 
     * @param x 
     */
    public SubsystemModel(String subsystemNumber, double x, double y){
	this.subsystemNumber=subsystemNumber;
	this.x=x;
	this.y=y;
	this.width=100;
	this.height=20;
	this.isSelected=false;
    }

    @Override
    public void paint(Graphics g) {
	super.paint(g);
	g.setColor(Color.lightGray);
	g.fillRect((int)Math.round(x), (int)Math.round(y), width, height);
	
	this.setBackground(Color.white);
	if(isSelected)
	    g.setColor(Color.blue);
	else
	    g.setColor(Color.black);
	g.drawRect((int)Math.round(x), (int)Math.round(y), width, height);
	
	
	g.setColor(Color.black);
	Utils.printSimpleStringCentered(g, subsystemNumber, 100, (int)Math.round(x), (int)Math.round(y+g.getFontMetrics().getHeight()/2));
    }
    
    public void setSelected(boolean selected){
	this.isSelected=selected;
    }
    
    @Override
    public Rectangle getBounds() {
	return new Rectangle((int)Math.round(x), (int)Math.round(y), width, height);
    }

    public String getDescription(){
	return subsystemDescription;
    }

    public boolean isSelected() {
	return isSelected;
    }

    public String getSSNumber() {
	return subsystemNumber;
    }
    
    @Override
    public int getX() {
	// TODO Auto-generated method stub
	return (int)Math.round(x);
    }

    @Override
    public int getY() {
	// TODO Auto-generated method stub
	return (int)Math.round(y);
    }

    public void setPosition(Point p){
	this.x=p.getX();
	this.y=p.getY();
    }

    public ArrayList<String> getDestinatorForConnections() {
	return destinatorForConnections;
    }

    public void setDestinatorForConnections(ArrayList<String> destinatorForConnections) {
	this.destinatorForConnections = destinatorForConnections;
    }
}
