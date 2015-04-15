package detailedStartUpSequence;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

/**
 * @author Samuel Trémouille
 */
public class Controller {
    private Model model;
    private View view;
    
    private String ssClickedNumber="";
    private String ssClickedNumberForConnection="";
    private double ssClickedNumberxOffset,ssClickedNumberyOffset;
    
    private Point newSSLocation;
    

    public Controller(Model model, View view){
	this.view=view;
	this.model=model;
	
	view.addMyMouseListener(new MyMouseListener());
	view.addNewSubsystemListener(new NewSubsystemListener());
	view.addMyMouseMotionListener(new MyMouseMotionListener());
    }
    
    class MyMouseListener implements MouseListener{
	    
	    @Override
	    public void mouseClicked(MouseEvent e) {
		HashMap<String, SubsystemModel> list = model.getSubsystems();
		//System.out.println(e.getX()+":"+e.getY());
		for(String key : list.keySet()){
		    SubsystemModel ss = list.get(key);
		    if(ss.getBounds().contains(e.getPoint())){
			ss.setSelected(!ss.isSelected());
			System.out.println(ss.getSSNumber()+" touch");
			view.repaint();
		    }
		}
	    }

	    @Override
	    public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	    }

	    @Override
	    public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	    }

	    @Override
	    public void mousePressed(MouseEvent e) {
		System.out.println("CLICK COUNT :"+e.getClickCount());
		//CHECKING IF RIGHT CLICK MENU IS VISIBLE
		if(view.getRightClickMenuOnSubsystem().isVisible())
		    view.getRightClickMenuOnSubsystem().setVisible(false);
		
		if(view.getRightClickGeneral().isVisible())
		    view.getRightClickGeneral().setVisible(false);
		
		if(e.getButton()==MouseEvent.BUTTON1){
        		HashMap<String, SubsystemModel> list = model.getSubsystems();
        		//System.out.println(e.getX()+":"+e.getY());
        		for(String key : list.keySet()){
        		    SubsystemModel ss = list.get(key);
        		    if(ss.getBounds().contains(e.getPoint())){
        			//DOUBLE CLICK
        			if(e.getClickCount()>=2){
        			    SubsystemEditorFrame sef = new SubsystemEditorFrame(ss, model);
        			    sef.pack();
        			    sef.setLocationRelativeTo(view);
        			    sef.setVisible(true);
        			}
        			ssClickedNumber=ss.getSSNumber();
        			ssClickedNumberxOffset=e.getPoint().getX()-ss.getX();
        			ssClickedNumberyOffset=e.getPoint().getY()-ss.getY();
        		    }
        		}
		} else if(e.getButton()==MouseEvent.BUTTON3){
		    boolean match = false;
		    HashMap<String, SubsystemModel> list = model.getSubsystems();
        		    for(String key : list.keySet()){
                		    SubsystemModel ss = list.get(key);
                		    if(ss.getBounds().contains(e.getPoint())){
                			match=true;
                			ssClickedNumberForConnection=ss.getSSNumber();
                			view.getRightClickMenuOnSubsystem().setLocation(e.getLocationOnScreen());
                			view.getRightClickMenuOnSubsystem().setVisible(true);
                		    }
        		    }
        		    if(!match){
        			view.getRightClickGeneral().setLocation(e.getLocationOnScreen());
        			view.getRightClickGeneral().setVisible(true);
        			newSSLocation=e.getPoint();
        		    }
		}
	    }

	    @Override
	    public void mouseReleased(MouseEvent e) {
		if(ssClickedNumber!=""){
		    HashMap<String, SubsystemModel> list = model.getSubsystems();
		    list.get(ssClickedNumber).setPosition(new Point((int)Math.round(e.getX()-ssClickedNumberxOffset),(int)Math.round(e.getY()-ssClickedNumberyOffset)));
		    ssClickedNumber="";
		    ssClickedNumberxOffset=0;
		    ssClickedNumberyOffset=0;
		    view.repaint();
		}
		
		view.drawMovingSS(null, false);
	    }
    }
    
    class MyMouseMotionListener implements MouseMotionListener{
	    @Override
	    public void mouseDragged(MouseEvent e) {
		if(ssClickedNumber!=""){
		    view.drawMovingSS(new Point((int)(e.getX()-ssClickedNumberxOffset),(int)(e.getY()-ssClickedNumberyOffset)),true);
		    view.repaint();
		}
	    }

	    @Override
	    public void mouseMoved(MouseEvent e) {
		
	    }
	
    }
    class ConnectSubsystemListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent arg0) {
	    
	    //model.getSubsystems().put("", arg1)
	}
	
	
    }
    
    class NewSubsystemListener implements ActionListener{

	NewSubsystemDialog nsd;
	@Override
	public void actionPerformed(ActionEvent e){
	    nsd = new NewSubsystemDialog(this);
	    nsd.setLocationRelativeTo(view);
	    nsd.setVisible(true);
	    view.getRightClickGeneral().setVisible(false);
	}

	public void returnSubsystemNumber(String text) {
	    if(!model.getSubsystems().containsKey(text)){
        	    model.getSubsystems().put(text, new SubsystemModel(text, newSSLocation.getX(), newSSLocation.getY()));
        	    view.repaint();
	    } else {
		JOptionPane.showMessageDialog(view, "Sub-system already on the sequence");
		nsd = new NewSubsystemDialog(this);
		nsd.setLocationRelativeTo(view);
		nsd.setVisible(true);
	    }
	}
	
    }
    
}
