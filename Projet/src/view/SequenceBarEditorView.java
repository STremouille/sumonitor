package view;

import java.awt.Color;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.colorchooser.ColorChooserComponentFactory;

import model.InteractiveBar;
import model.SequenceBar;

/**
 * Sequence editor view
 * @author S.Trémouille
 *
 */

public class SequenceBarEditorView extends InteractiveBarEditorView {

	private static final long serialVersionUID = -8540084445481851293L;
	private JColorChooser dashedLineColorChooser;
	private Color oldDashedLineColor;
	/**
	 * @param pos
	 * @param barToEdit
	 */
	public SequenceBarEditorView(Point pos, InteractiveBar barToEdit) {
		super(pos, barToEdit);
		oldDashedLineColor = ((SequenceBar) barToEdit).getDottedLineColor();
		JPanel dashedLineColorChooserPanel = new JPanel();
		dashedLineColorChooserPanel.setBorder(BorderFactory.createTitledBorder("Dashed Line Color"));
		dashedLineColorChooser = new JColorChooser(((SequenceBar) getModel()).getDottedLineColor());
		AbstractColorChooserPanel[] accp = {ColorChooserComponentFactory.getDefaultChooserPanels()[0]};
		dashedLineColorChooser.setChooserPanels(accp);
		dashedLineColorChooser.setPreviewPanel(new JPanel());
		
		dashedLineColorChooserPanel.add(dashedLineColorChooser);
		addColorChoosersPanel(dashedLineColorChooserPanel);
		pack();
	}
	
	/**
	 * @return DashedLineColor
	 */
	public Color getDashedLineColor(){
		return dashedLineColorChooser.getColor();
	}
	
	/**
	 * @return OldDashedLineColor
	 */
	public Color getOldDashedLineColor(){
		return oldDashedLineColor;
	}
}
