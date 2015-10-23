package controller;


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
			default:
				break;
			
			}
		}
}