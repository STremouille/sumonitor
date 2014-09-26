package detailedStartUpSequence;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * @author Samuel Trémouille
 */
public class Draw extends JPanel {
    
    private Model model;
    private boolean moving;
    private Point movingPoint;

    public Draw(Model model){
	this.model=model;
    }
    @Override
    public void paint(Graphics g) {
	super.paint(g);
	
	this.setBackground(Color.white);
	
	subsystemsAlignement();
	
	HashMap<String, SubsystemModel> ss = model.getSubsystems();
	for(String key : ss.keySet()){
	    ss.get(key).paint(g);
	}
	if(moving){
	    g.setColor(Color.black);
	    System.out.println("moving");
	    g.drawRect((int)movingPoint.getX(), (int)movingPoint.getY(),100 , 20);
	}
    }
    public void drawMovingSS(Point e,boolean b) {
	moving=b;
	movingPoint=e;
    }
    
    public void subsystemsAlignement(){
	double scaleX = this.getWidth()/40.0;
	double scaleY = this.getHeight()/200.0;
	HashMap<String, SubsystemModel> ss = model.getSubsystems();
	for(String key : ss.keySet()){
	    ss.get(key).setPosition(new Point((int)Math.round(Math.round(ss.get(key).getX()/scaleX)*scaleX),(int)Math.round(Math.round(ss.get(key).getY()/scaleY)*scaleY)));
	}
    }

}
