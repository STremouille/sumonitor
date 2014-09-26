package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.coobird.thumbnailator.Thumbnails;
import controller.Controller;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import java.awt.GridLayout;
import java.awt.Window.Type;

/**
 * 
 * @author S.Trémouille
 *
 */

public class LaunchFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3789127402041783026L;
	private JPanel contentPane,panel;
	private Controller c;
	
	
	/**
	 * Constructor
	 * @param controller
	 */
	public LaunchFrame(final Controller controller) {
		this.c=controller;
		c.disableView();
		setIconImage(Toolkit.getDefaultToolkit().getImage(LaunchFrame.class.getResource("/img/icone.png")));
		setTitle("Launch");
		setFont(new Font("Arial", Font.PLAIN, 14));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		JButton btnCreateANew_1 = new JButton("Create a new Project");
		btnCreateANew_1.addActionListener(c.getNewProjectActionListener());
		btnCreateANew_1.addActionListener(new CreateListener());
		panel_1.setLayout(new GridLayout(0, 3, 0, 0));
		panel_1.add(btnCreateANew_1);
		
		JButton btnLoadAProject = new JButton("Load a project");
		btnLoadAProject.addActionListener(c.getLoadProjectActionListener(""));
		btnLoadAProject.addActionListener(new LoadListener());
		panel_1.add(btnLoadAProject);
		
		JButton btnLoadLastOne = new JButton("Load last one used");
		panel_1.add(btnLoadLastOne);
		
		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2, BorderLayout.CENTER);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblNewLabel = new JLabel("");
		
		ImageIcon launchImg = new ImageIcon(LaunchFrame.class.getResource("/img/launch.png"));
		//BufferedImage launchImgResized = Thumbnails.of(launchImg).asBufferedImage();
		//lblNewLabel.setIcon(new ImageIcon(launchImgResized));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double scaleFactor = 0;
		
		if(launchImg.getIconWidth()/screenSize.getWidth()<=launchImg.getIconHeight()/screenSize.getHeight()){
		    scaleFactor =  (Double.valueOf(launchImg.getIconWidth())/screenSize.getWidth())*0.5;
		} else {
		    scaleFactor =  (Double.valueOf(launchImg.getIconHeight())/screenSize.getHeight())*0.5;
		}
		System.out.println("ImageIcon Heigth :"+launchImg.getIconHeight());
		System.out.println("Scale Factor :"+scaleFactor);
		BufferedImage bi = new BufferedImage((int)Math.round(scaleFactor*launchImg.getIconWidth()), (int)Math.round(scaleFactor*launchImg.getIconHeight()), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bi.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g.scale(scaleFactor, scaleFactor);
		launchImg.paintIcon(null, g, 0, 0);
		g.dispose();
		
		lblNewLabel.setIcon(new ImageIcon(bi));
		
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel_2.add(lblNewLabel, gbc_lblNewLabel);

		File f = new File("last");
		if(f.exists()){
			try {
				BufferedReader br = new BufferedReader(new FileReader(f));
				String fileName = br.readLine();
				File fToLoad = new File(fileName);
				if(fToLoad.exists()){
					btnLoadLastOne.setEnabled(true);
					btnLoadLastOne.addActionListener(c.getLoadProjectActionListener(fileName));
					btnLoadLastOne.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							dispose();
							c.enableView();
						}
					});
				} else {
					btnLoadLastOne.setEnabled(false);
				}
				
				    br.close();
				
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(c.getView(), e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
			    	JOptionPane.showMessageDialog(c.getView(), e.getMessage());
				e.printStackTrace();
			}
		} else {
			btnLoadLastOne.setEnabled(false);
		}
		
		this.setLocationRelativeTo(c.getView());
		
		this.pack();firePropertyChange("disposeSplashScreen", 1, 1);
	}
	
	
	
	/**
	 * @author S.Trémouille
	 *
	 */
	public class CreateListener implements ActionListener{

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		dispose();
		c.enableView();		
	    }
	    
	}
	
	/**
	 * @author S.Trémouille
	 *
	 */
	public class LoadListener implements ActionListener{

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		dispose();
		c.enableView();		
	    }
	    
	}
	
	/**
	 * @author S.Trémouille
	 *
	 */
	public class loadlastListener implements ActionListener{

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	    }
	    
	}
}
