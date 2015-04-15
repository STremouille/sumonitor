package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.text.JTextComponent;

import model.InteractiveBar;
import model.StartUpStep;
import model.TitleBar;
import view.InteractiveBarEditorView;


/**
 * 
 * @author S.Tr�mouille
 *
 */
public class InteractiveBarEditorController {
	private InteractiveBar model;
	private InteractiveBarEditorView view;
	private Color backUpColor;
	
	/**
	 * Constructor following MVC pattern
	 * @param model	
	 * @param view
	 */
	public InteractiveBarEditorController(InteractiveBar model,InteractiveBarEditorView view){
		this.model=model;
		this.view=view;
		this.backUpColor=model.getColor();
		
		this.view.addValidateListener(new ValidateListener());
		this.view.addCancelListener(new CancelListener());
	}
	
	protected InteractiveBar getModel(){
		return model;
	}
	
	protected InteractiveBarEditorView getView() {
		return view;
	}
	class ValidateListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(model.getClass().equals(new TitleBar("", 0, 0, 0, 0).getClass())){
				((TitleBar) model).setStyle(view.getStyle());
			}
			if(model.getClass().equals(new StartUpStep("", 0, 0, 0, 0).getClass())){
				((StartUpStep) model).setRelatedToThisMilestone(view.getMilestoneRelated());
			}
			model.setName(((JTextComponent) view.getTitleArea()).getText());
			model.setColor(view.getColor());
			
			//cheat to invoke the change listener and refresh the interactive bar in the parent view
			Color c= view.getColorChooser().getSelectionModel().getSelectedColor();
			view.getColorChooser().getSelectionModel().setSelectedColor(Color.white);
			view.getColorChooser().getSelectionModel().setSelectedColor(c);
			view.dispose();
		}
		
	}
	
	class CancelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			model.setColor(backUpColor);
			//It only invoke the change listener if you have input a new color in the color chooser
			view.getColorChooser().getSelectionModel().setSelectedColor(backUpColor);
			view.dispose();
		}
		
	}

	/**
	 * enable or not the view
	 * @param b
	 */
	public void setEnable(boolean b) {
		view.setEnabled(b);
		
	}
}
