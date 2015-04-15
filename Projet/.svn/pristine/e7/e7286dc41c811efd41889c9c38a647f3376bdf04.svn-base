package view;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

import model.InteractiveBar;

/**
 * 
 * @author S.Trémouille
 *
 */

public class CommentEditorView extends InteractiveBarEditorView {

	private static final long serialVersionUID = -7350217954491296332L;

	/**
	 * @param point
	 * @param barToEdit
	 */
	public CommentEditorView(Point point, InteractiveBar barToEdit) {
		super(point, barToEdit);
		((JTextArea) this.getTitleArea()).setPreferredSize(new Dimension(200, 200));
		((JTextArea) this.getTitleArea()).setLineWrap(true);
		((JTextArea) this.getTitleArea()).setWrapStyleWord(true);
		this.getTitlePanel().setBorder(BorderFactory.createTitledBorder("Content"));
		this.pack();
		
	}
	
}
