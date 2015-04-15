package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.InteractiveBar;
import model.SequenceBar;
import view.InteractiveBarEditorView;
import view.SequenceBarEditorView;

/**
 * 
 * @author S.Tr�mouille
 *
 */

public class SequenceBarEditorController extends InteractiveBarEditorController {

	private Color backUpDashedLineColor;
	
	/**
	 * @param model
	 * @param view
	 */
	public SequenceBarEditorController(InteractiveBar model, InteractiveBarEditorView view) {
		super(model, view);
		backUpDashedLineColor = ((SequenceBar) model).getDottedLineColor();
		
		this.getView().addValidateListener(new ValidateSBListener());
		this.getView().addCancelListener(new CandelSBListener());
	}

	class ValidateSBListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			((SequenceBar) getModel()).setDottedLineColor(((SequenceBarEditorView) getView()).getDashedLineColor());
		}
		
	}

	class CandelSBListener implements ActionListener{
		 @Override
		public void actionPerformed(ActionEvent arg0) {
			((SequenceBar) getModel()).setDottedLineColor(backUpDashedLineColor);
		}
	}
}
