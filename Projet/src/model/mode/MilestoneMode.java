package model.mode;

import java.awt.Color;

/**
 * 
 * @author S.Trémouille
 *
 */

public abstract class MilestoneMode {
	/**
	 * @return BorderColor
	 */
	public abstract Color getBorderColor();
	/**
	 * @return TitleColor
	 */ 
	public abstract Color getTitleColor();
	/**
	 * @return MilestoneMode
	 */
	public abstract Color getDescriptionColor();
	/**
	 * @return ProgressRectangleBackgroundColor
	 */
	public abstract Color getProgressRectangleBackgroundColor();
	/**
	 * @return DateColor
	 */
	public abstract Color getDateColor();
	/**
	 * @return extColor
	 */
	public abstract Color getTextColor();
	/**
	 * @return MilestoneMode
	 */
	public abstract Color getShadowColor();
	/**
	 * @return OperatedColor
	 */
	public abstract Color getOperatedColor();
	/**
	 * @return ProgressBarBackgroundColor
	 */
	public abstract Color getProgressBarBackgroundColor();
}
