package model.mode;

import java.awt.Color;

/**
 * 
 * @author S.Trémouille
 *
 */

public class SelectedMode extends MilestoneMode {

	Color secondColor = new Color(0, 255, 0);
	@Override
	public Color getBorderColor() {
		return secondColor;
	}

	@Override
	public Color getTitleColor() {
		return new Color(2,23,181);
	}

	@Override
	public Color getDescriptionColor() {
		return new Color(2,23,181);
	}

	@Override
	public Color getProgressRectangleBackgroundColor() {
		return new Color(2,23,181);
	}


	@Override
	public Color getDateColor() {
		return new Color(2,23,181);
	}

	@Override
	public Color getTextColor() {
		return secondColor;
	}

	@Override
	public Color getShadowColor() {
		return secondColor;
	}
	
	@Override
	public Color getProgressBarBackgroundColor() {
		return getProgressRectangleBackgroundColor();
	}


	@Override
	public Color getOperatedColor() {
		return Color.white;
	}

}
