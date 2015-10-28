package cancel;

import java.util.ArrayList;


public class CancelFactory {
	public enum CancellableActionLabel {
		milestone_creation,
		step_creation,
		sequence_creation,
		comment_creation,
		connection_creation,
		milestone_deletion,
		step_deletion,
		sequence_deletion,
		comment_deletion,
		connection_deletion,
		zoom_in,
		zoom_out,
		milestone_move,
		step_move,
		sequence_move,
		comment_move, 
		group_move, 
		sequence_resizing,
		comment_resizing,
		step_resizing
	}
	
	private ArrayList<CancellableAction> stackToCancel;
	private ArrayList<CancellableAction> stackCancelled;
	
	public CancelFactory(){
		this.stackToCancel = new ArrayList<CancellableAction>();
		this.stackCancelled = new ArrayList<CancellableAction>();
	}
	
	public void addAction(CancellableAction cancellableAction) {
		if(stackToCancel.size()>999){
			stackToCancel.remove(0);
		}
		this.stackToCancel.add(cancellableAction);
		this.stackCancelled.clear();
	}
	
	public void cancel(){
		if(stackToCancel.size()>0){
			CancellableAction ca = stackToCancel.get(stackToCancel.size()-1);
			stackToCancel.remove(stackToCancel.size()-1);
			ca.cancel();
			stackCancelled.add(ca);
		}
	}
	
	public void redo(){
		if(stackCancelled.size()>0){
			CancellableAction ca = stackCancelled.get(stackCancelled.size()-1);
			stackCancelled.remove(stackCancelled.size()-1);
			ca.redo();
			stackToCancel.add(ca);
		}
	}
	
	public void flush(){
		this.stackCancelled.clear();
		this.stackToCancel.clear();
	}
	
}
