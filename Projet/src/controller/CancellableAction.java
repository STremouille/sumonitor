package controller;



import java.util.ArrayList;

import conf.GeneralConfig;
import controller.CancelFactory.CancellableActionLabel;
import model.Comment;
import model.Milestone;
import model.SequenceBar;
import model.StartUpStep;

public class CancellableAction{
		
		private CancellableActionLabel cal;
		private Object cancelAttr;
		private Controller controller;
		
		public CancellableAction(CancellableActionLabel cal,Object o, Controller controller){
			this.cal = cal;
			this.cancelAttr = o;
			this.controller = controller;
		}

		public void redo() {
			switch (cal) {
			case comment_creation:
				controller.getModel().addComment((Comment)cancelAttr);
				break;
			case connection_creation:
				break;
			case milestone_creation:
				controller.getModel().addMilestone((Milestone)cancelAttr);
				break;
			case sequence_creation:
				controller.getModel().addSequence((SequenceBar)cancelAttr);
				break;
			case step_creation:
				controller.getModel().addStartUpTask((StartUpStep)cancelAttr);
				break;
			case comment_deletion:
				controller.getModel().removeComment((Comment)cancelAttr);
				break;
			case connection_deletion:
				break;
			case milestone_deletion:
				controller.getModel().removeMilestone((Milestone)cancelAttr);
				break;
			case sequence_deletion:
				controller.getModel().removeSequence((SequenceBar)cancelAttr);
				break;
			case step_deletion:
				controller.getModel().removeStep((StartUpStep)cancelAttr);
				break;
			case zoom_out:
				if (GeneralConfig.updateForZoom((int) Math.round((GeneralConfig.zoom / GeneralConfig.coeffZoom)))) {
					controller.getView().updatePositionForZoom(-1);
					controller.getView().getDrawPanel().updateUI();
					controller.getView().getScrollPane().getHorizontalScrollBar().setValue((int) Math.round(((java.awt.Point) cancelAttr).getX()*(1/GeneralConfig.coeffZoom-1)+controller.getView().getScrollPane().getHorizontalScrollBar().getValue()));
					controller.getView().getScrollPane().getVerticalScrollBar().setValue((int) Math.round(((java.awt.Point) cancelAttr).getY()*(1/GeneralConfig.coeffZoom-1)+controller.getView().getScrollPane().getVerticalScrollBar().getValue()));
					controller.getView().repaint();
				}
				break;
			case zoom_in:
				if (GeneralConfig.updateForZoom((int) Math.round((GeneralConfig.zoom * GeneralConfig.coeffZoom)))) {
					controller.getView().updatePositionForZoom(1);
					controller.getView().getDrawPanel().updateUI();
					controller.getView().getScrollPane().getHorizontalScrollBar().setValue((int) Math.round(((java.awt.Point) cancelAttr).getX()*(GeneralConfig.coeffZoom-1)+controller.getView().getScrollPane().getHorizontalScrollBar().getValue()));
					controller.getView().getScrollPane().getVerticalScrollBar().setValue((int) Math.round(((java.awt.Point) cancelAttr).getY()*(GeneralConfig.coeffZoom-1)+controller.getView().getScrollPane().getVerticalScrollBar().getValue()));
					controller.getView().repaint();
				}
				break;
			case milestone_move:
				controller.getModel().getMilestone((Milestone)((ArrayList<Object>)cancelAttr).get(0)).setX((Double)((ArrayList<Object>)cancelAttr).get(3));
				controller.getModel().getMilestone((Milestone)((ArrayList<Object>)cancelAttr).get(0)).setY((Double)((ArrayList<Object>)cancelAttr).get(4));
				break;
			case sequence_move:
				controller.getModel().getSequence((SequenceBar)((ArrayList<Object>)cancelAttr).get(0)).setX((Double)((ArrayList<Object>)cancelAttr).get(3));
				controller.getModel().getSequence((SequenceBar)((ArrayList<Object>)cancelAttr).get(0)).setY((Double)((ArrayList<Object>)cancelAttr).get(4));
				break;
			case step_move:
				controller.getModel().getStartUpStep((StartUpStep)((ArrayList<Object>)cancelAttr).get(0)).setX((Double)((ArrayList<Object>)cancelAttr).get(3));
				controller.getModel().getStartUpStep((StartUpStep)((ArrayList<Object>)cancelAttr).get(0)).setY((Double)((ArrayList<Object>)cancelAttr).get(4));
				break;
			case comment_move:
				controller.getModel().getComment((Comment)((ArrayList<Object>)cancelAttr).get(0)).setX((Double)((ArrayList<Object>)cancelAttr).get(3));
				controller.getModel().getComment((Comment)((ArrayList<Object>)cancelAttr).get(0)).setY((Double)((ArrayList<Object>)cancelAttr).get(4));
				break;
			default:
				break;
			
			}
		}

		public void cancel() {
			switch (cal) {
			case comment_creation:
				controller.getModel().removeComment((Comment)cancelAttr);
				break;
			case connection_creation:
				break;
			case milestone_creation:
				controller.getModel().removeMilestone((Milestone)cancelAttr);
				break;
			case sequence_creation:
				controller.getModel().removeSequence((SequenceBar)cancelAttr);
				break;
			case step_creation:
				controller.getModel().removeStep((StartUpStep)cancelAttr);
				break;
			case comment_deletion:
				controller.getModel().addComment((Comment)cancelAttr);
				break;
			case sequence_deletion:
				controller.getModel().addSequence((SequenceBar)cancelAttr);
				break;
			case milestone_deletion:
				controller.getModel().addMilestone((Milestone)cancelAttr);
				break;
			case step_deletion:
				controller.getModel().addStartUpTask((StartUpStep)cancelAttr);
				break;
			case zoom_in:
				if (GeneralConfig.updateForZoom((int) Math.round((GeneralConfig.zoom / GeneralConfig.coeffZoom)))) {
					controller.getView().updatePositionForZoom(-1);
					controller.getView().getDrawPanel().updateUI();
					controller.getView().getScrollPane().getHorizontalScrollBar().setValue((int) Math.round(((java.awt.Point) cancelAttr).getX()*(1/GeneralConfig.coeffZoom-1)+controller.getView().getScrollPane().getHorizontalScrollBar().getValue()));
					controller.getView().getScrollPane().getVerticalScrollBar().setValue((int) Math.round(((java.awt.Point) cancelAttr).getY()*(1/GeneralConfig.coeffZoom-1)+controller.getView().getScrollPane().getVerticalScrollBar().getValue()));
					controller.getView().repaint();
				}
				break;
			case zoom_out:
				if (GeneralConfig.updateForZoom((int) Math.round((GeneralConfig.zoom * GeneralConfig.coeffZoom)))) {
					controller.getView().updatePositionForZoom(1);
					controller.getView().getDrawPanel().updateUI();
					controller.getView().getScrollPane().getHorizontalScrollBar().setValue((int) Math.round(((java.awt.Point) cancelAttr).getX()*(GeneralConfig.coeffZoom-1)+controller.getView().getScrollPane().getHorizontalScrollBar().getValue()));
					controller.getView().getScrollPane().getVerticalScrollBar().setValue((int) Math.round(((java.awt.Point) cancelAttr).getY()*(GeneralConfig.coeffZoom-1)+controller.getView().getScrollPane().getVerticalScrollBar().getValue()));
					controller.getView().repaint();
				}
				break;
			case milestone_move:
				controller.getModel().getMilestone((Milestone)((ArrayList<Object>)cancelAttr).get(0)).setX((Double)((ArrayList<Object>)cancelAttr).get(1));
				controller.getModel().getMilestone((Milestone)((ArrayList<Object>)cancelAttr).get(0)).setY((Double)((ArrayList<Object>)cancelAttr).get(2));
				break;
			case sequence_move:
				controller.getModel().getSequence((SequenceBar)((ArrayList<Object>)cancelAttr).get(0)).setX((Double)((ArrayList<Object>)cancelAttr).get(1));
				controller.getModel().getSequence((SequenceBar)((ArrayList<Object>)cancelAttr).get(0)).setY((Double)((ArrayList<Object>)cancelAttr).get(2));
				break;
			case step_move:
				controller.getModel().getStartUpStep((StartUpStep)((ArrayList<Object>)cancelAttr).get(0)).setX((Double)((ArrayList<Object>)cancelAttr).get(1));
				controller.getModel().getStartUpStep((StartUpStep)((ArrayList<Object>)cancelAttr).get(0)).setY((Double)((ArrayList<Object>)cancelAttr).get(2));
				break;
			case comment_move:
				controller.getModel().getComment((Comment)((ArrayList<Object>)cancelAttr).get(0)).setX((Double)((ArrayList<Object>)cancelAttr).get(1));
				controller.getModel().getComment((Comment)((ArrayList<Object>)cancelAttr).get(0)).setY((Double)((ArrayList<Object>)cancelAttr).get(2));
				break;
			default:
				break;
			
			}
		}

		public CancellableActionLabel getLabel() {
			return cal;
		}
}