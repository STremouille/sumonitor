package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.ProjectTitleBlock;
import view.ImagePreview;
import view.ProjectTitleBlockView;
import view.View;
import conf.GeneralConfig;

/**
 * 
 * @author S.Tr�mouille
 *
 */

public class ProjectTitleBlockController {
	private ProjectTitleBlock model;
	private ProjectTitleBlockView view;
	private View parentView;
	
	/**
	 * @param view
	 * @param model
	 * @param parentView
	 */
	public ProjectTitleBlockController(ProjectTitleBlockView view, ProjectTitleBlock model,View parentView){
		this.view=view;
		this.model=model;
		this.parentView=parentView;
		
		this.view.addValidateListener(new ValidateListener());
		this.view.addCancelListener(new CancelListener());
		
		this.view.addBrowseCompanyLogoListener(new LogoChooserListener(this.view.getCompanyLogoTextField(),this.view.getCompanyLogoLabel()));
		this.view.addBrowseProjectLogoListener(new LogoChooserListener(this.view.getProjectLogoTextField(),this.view.getProjectLogoLabel()));
		this.view.addBrowseContractorLogoListener(new LogoChooserListener(this.view.getContractorLogoTextField(),this.view.getContractorLogoLabel()));
	}
	
	class ValidateListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			model.setCartouche(view.getOutingTitleBlock());
			view.dispose();
			parentView.getDrawPanel().repaint();
		}
	}
	
	class CancelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			view.dispose();
			parentView.getDrawPanel().repaint();
		}
		
	}
	
	class LogoChooserListener implements ActionListener{

		private JTextField field;
		private JLabel correspondingField;
		
		public LogoChooserListener(JTextField f,JLabel correspondingField){
			field=f;
			this.correspondingField=correspondingField;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser jfc = new JFileChooser();
			jfc.setAccessory(new ImagePreview(jfc));
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Image file","jpg","jpeg","png" ,"gif","bmp");
			jfc.setFileFilter(filter);
			int returnVal = jfc.showOpenDialog(view);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File fops = jfc.getSelectedFile();
				String name = fops.getName();
				//checking the file size
				try {
					if(ImageIO.read(fops).getHeight()>500 || ImageIO.read(fops).getWidth()>500){
						JOptionPane.showMessageDialog(view, "Please select an image file with heigth and width beneath 500 px");
						return ;
					}
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(view, e1.getMessage());
					e1.printStackTrace();
				}

				field.setText(jfc.getCurrentDirectory().getPath()+File.separatorChar+name);
				
				//creation of a copy of the logo file in order to create a better portability of the logo
				if(this.correspondingField.getText().equals("Company Logo :")){
					FileImageInputStream fos = null;
					try {
						fos = new FileImageInputStream(fops);
						GeneralConfig.companyLogo = new byte[(int) fos.length()];
						fos.read(GeneralConfig.companyLogo, 0	,(int) fos.length());
					} catch (FileNotFoundException e) {
					    	JOptionPane.showMessageDialog(view, e.getMessage());
						e.printStackTrace();
					} catch (IOException e) {
					    	JOptionPane.showMessageDialog(view, e.getMessage());
						e.printStackTrace();
					}
				} else if(this.correspondingField.getText().equals("Project Logo :")){
					FileImageInputStream fos = null;
					try {
						fos = new FileImageInputStream(fops);
						GeneralConfig.projectLogo = new byte[(int) fos.length()];
						fos.read(GeneralConfig.projectLogo, 0	,(int) fos.length());
					} catch (FileNotFoundException e) {
					    	JOptionPane.showMessageDialog(view, e.getMessage());
						e.printStackTrace();
					} catch (IOException e) {
					    	JOptionPane.showMessageDialog(view, e.getMessage());
						e.printStackTrace();
					}
				} else if(this.correspondingField.getText().equals("Contrator Logo :")){
					FileImageInputStream fos = null;
					try {
						fos = new FileImageInputStream(fops);
						GeneralConfig.contractorLogo = new byte[(int) fos.length()];
						fos.read(GeneralConfig.contractorLogo, 0	,(int) fos.length());
					} catch (FileNotFoundException e) {
					    	JOptionPane.showMessageDialog(view, e.getMessage());
						e.printStackTrace();
					} catch (IOException e) {
					    	JOptionPane.showMessageDialog(view, e.getMessage());
						e.printStackTrace();
					}
				}
			}
			
		}
	}

	/**
	 * @param isVisible
	 */
	public void setVisible(boolean isVisible) {
		view.setVisible(isVisible);
	}
}
