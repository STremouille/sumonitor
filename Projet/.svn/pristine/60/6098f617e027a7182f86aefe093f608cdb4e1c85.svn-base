package detailedStartUpSequence;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;

import java.awt.ScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;

/**
 * @author Samuel Trémouille
 */
public class View extends JFrame {

    private Model model;
    
    private JPanel contentPane;
    private Draw draw;
    private JPanel panel;
    private JButton btnUpdate;
    private JPopupMenu rightClickOnSSPopupMenu,rightClickGeneral;
    private JMenuItem connectSubsystem,newSubsystem;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    Model model = new Model();
		    model.getSubsystems().put("0011",new SubsystemModel("0011",14.0,35.0));
		    model.getSubsystems().put("0012",new SubsystemModel("0012",140.0,315.0));
		    View frame = new View(model);
		    Controller c = new Controller(model, frame);
		    frame.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the frame.
     */
    public View(Model model) {
	this.model=model;
	
    	setTitle("Detailed");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 450, 300);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	contentPane.setLayout(new BorderLayout(0, 0));
	setContentPane(contentPane);
	
	draw = new Draw(model);
	draw.setPreferredSize(new Dimension(1000,2000));
	JScrollPane scrollPane = new JScrollPane(draw);
	contentPane.add(scrollPane, BorderLayout.CENTER);
	
	panel = new JPanel();
	contentPane.add(panel, BorderLayout.NORTH);
	
	btnUpdate = new JButton("UPDATE");
	panel.add(btnUpdate);
	
	rightClickOnSSPopupMenu = new JPopupMenu();
	connectSubsystem = new JMenuItem("LINK");
	rightClickOnSSPopupMenu.add(connectSubsystem);
	
	rightClickGeneral = new JPopupMenu();
	newSubsystem = new JMenuItem("New Sub-system");
	rightClickGeneral.add(newSubsystem);
	
	pack();
    }

    public void addMyMouseListener(MouseListener myMouseListener) {
	draw.addMouseListener(myMouseListener);
    }
    
    public void addMyMouseMotionListener(MouseMotionListener mml){
	draw.addMouseMotionListener(mml);
    }
    
    public Draw getDraw(){
	return draw;
    }

    public void drawMovingSS(Point e,boolean b) {
	draw.drawMovingSS(e,b);
    }
    
    public void addConnectSubsystemListener(ActionListener al){
	this.connectSubsystem.addActionListener(al);
    }

    public void addNewSubsystemListener(ActionListener al){
	this.newSubsystem.addActionListener(al);
    }
    
    public JPopupMenu getRightClickMenuOnSubsystem() {
	return rightClickOnSSPopupMenu;
    }
    
    public JPopupMenu getRightClickGeneral() {
	return rightClickGeneral;
    }
}
