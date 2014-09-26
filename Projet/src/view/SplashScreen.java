package view;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

/**
 * @author S.Trémouille
 * 
 */
public class SplashScreen extends JFrame {

    private static final long serialVersionUID = 4034059822673461869L;
    private JPanel contentPane;
    private JProgressBar progressBar;

    /**
     * Create the frame.
     */
    public SplashScreen() {
    	setUndecorated(true);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 450, 300);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(new BorderLayout(0, 0));
	
	JLabel label = new JLabel("");
	label.setIcon(new ImageIcon(SplashScreen.class.getResource("/img/total.png")));
	contentPane.add(label, BorderLayout.CENTER);
	
	progressBar = new JProgressBar();
	progressBar.setIndeterminate(false);
	contentPane.add(progressBar, BorderLayout.SOUTH);
	pack();
	this.setLocation(new Point(Toolkit.getDefaultToolkit().getScreenSize().width/2-this.getWidth()/2, Toolkit.getDefaultToolkit().getScreenSize().height/2-this.getHeight()/2));
	this.setVisible(true);
    }
    
    /**
     * @param value
     */
    public void setProgressBarValue(int value){
	this.progressBar.setValue(value);
    }
    
}
