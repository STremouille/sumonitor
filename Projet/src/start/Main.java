package start;

import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.transform.TransformerException;

import model.MovableItem;
import model.StartUpSequence;
import model.StartUpSequence.SelectedItems;
import model.StartUpStep;
import view.LaunchFrame;
import view.SplashScreen;
import view.View;
import worker.AutoSaveTask;
import conf.GeneralConfig;
import controller.Controller;

/**
 * @author S.Trémouille
 * Start point of the application
 *
 */
public class Main {
    	
    
    static private SplashScreen splashScreen;
	/**
	 * @param args
	 * @throws TransformerException
	 */
	public static void main(final String[] args) throws TransformerException {
	try {
		//Set the java virtual machine executing environment look & feel
		//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    	UIManager.setLookAndFeel("org.fife.plaf.Office2003.Office2003LookAndFeel");
		Locale.setDefault(Locale.ENGLISH);
	} catch (ClassNotFoundException e) {
		JOptionPane.showMessageDialog(null, e.getMessage());
		e.printStackTrace();
	} catch (InstantiationException e) {
		JOptionPane.showMessageDialog(null, e.getMessage());
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		JOptionPane.showMessageDialog(null, e.getMessage());
		e.printStackTrace();
	} catch (UnsupportedLookAndFeelException e) {
		JOptionPane.showMessageDialog(null, e.getMessage());
		e.printStackTrace();
	} 
	splashScreen = new view.SplashScreen();
	
			Runnable r = new Runnable() {
			       @Override
			    public void run() {
                			           	
				   		//init of config (static part)
				        	GeneralConfig.init();
				        	splashScreen.setProgressBarValue(10);
				        	Thread.yield();				        	
				        	//Init Model
						StartUpSequence sus = new StartUpSequence("New Project",true);
						splashScreen.setProgressBarValue(30);
						Thread.yield();
				
						//Init view
						View view = new View(sus);
						splashScreen.setProgressBarValue(45);
						Thread.yield();
						//System.out.println(view.getDrawPanel().isOptimizedDrawingEnabled());
						//Init Controller
						Controller c = new Controller(sus, view);
						splashScreen.setProgressBarValue(60);
						Thread.yield();
						
						//Autosave Init
						Timer t = new Timer();
						splashScreen.setProgressBarValue(65);
						Thread.yield();
						AutoSaveTask ast = new AutoSaveTask(sus,view);
						splashScreen.setProgressBarValue(70);
						Thread.yield();
						//Autosave is scheduled for each 60 seconds
						t.scheduleAtFixedRate(ast,Calendar.getInstance().getTime(),60000);
						splashScreen.setProgressBarValue(75);
						Thread.yield();
						
						LaunchFrame lf= new LaunchFrame(c);
						splashScreen.setProgressBarValue(85);
						Thread.yield();
						lf.addPropertyChangeListener(new PropertyChangeListener() {
						    
						    @Override
						    public void propertyChange(PropertyChangeEvent e) {
							if(e.getPropertyName()=="disposeSplashScreen")
							    splashScreen.dispose();
						    }
						});
						Thread.yield();
						lf.setLocation((int)Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2-lf.getWidth()/2), (int)Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2-lf.getHeight()/2));
						lf.setVisible(true);
						splashScreen.dispose();
						
						//DEBUG MODE
						if(args.length>0){
							if(args[0].equals("-debug")){
								SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy HH-mm");
								String tmp = sdf.format(Calendar.getInstance().getTime())+".log";
								File log = new File(tmp);
								System.out.println(sdf.format(Calendar.getInstance().getTime()));
								try {
									if(!log.exists())
										log.createNewFile();
									System.setOut(new PrintStream(log));
								} catch (FileNotFoundException e) {
									Thread.dumpStack();
									e.printStackTrace();
								} catch (IOException e) {
									Thread.dumpStack();
									e.printStackTrace();
								}
							}
						}
			       }
			    };
			r.run();
			
	}
	
}
