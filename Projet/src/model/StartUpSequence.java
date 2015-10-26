package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.w3c.dom.DOMException;

import conf.GeneralConfig;

/**
 * The model of the start-up sequence
 * @author S.Trémouille
 * 
 */

public class StartUpSequence {
	private TreeMap<Integer, SequenceBar> sequences;
	private TreeMap<Integer, Milestone> milestones;
	private TreeMap<Integer, Comment> comments;
	private TreeMap<Integer, StartUpStep> startUpTasks;
	private SelectedItems<MovableItem> selectedItems;
	private ProjectTitleBlock titleBlock;
	private TitleBar title;
	
	/**
	 * @param titleOfStartUpSequence
	 * @param isItANewLaunch
	 */
	public StartUpSequence(String titleOfStartUpSequence,Boolean isItANewLaunch){
		this.title=new TitleBar(titleOfStartUpSequence, (int)(GeneralConfig.pageWidth*0.333)/2, 0, (int)(GeneralConfig.pageWidth*0.666), (int)(GeneralConfig.pageHeight*0.05));
		this.sequences = new TreeMap<Integer, SequenceBar>();
		this.milestones = new TreeMap<Integer, Milestone>();
		this.comments = new TreeMap<Integer, Comment>();
		this.setStartUpTasks(new TreeMap<Integer, StartUpStep>());
		this.titleBlock = new ProjectTitleBlock();
		this.setSelectedItems(new SelectedItems<MovableItem>());
		if(isItANewLaunch){
			GeneralConfig.init();
		}
	}
	
	/**
	 * @param modelToLoad
	 */
	public StartUpSequence(StartUpSequence modelToLoad) {
		this.setTitleBar(modelToLoad.getTitleBar());
		this.setMiletones((TreeMap<Integer, Milestone>) modelToLoad.getMilestones().clone());
		this.setSequences((TreeMap<Integer, SequenceBar>) modelToLoad.getSequences().clone());
		this.setComments((TreeMap<Integer, Comment>) modelToLoad.getComments().clone());
		this.setCartouche((TreeMap<String, String>) modelToLoad.getCartouche().clone());
		this.setRevisions((ArrayList<TreeMap<String, String>>) modelToLoad.getRevisions().clone());
		this.setStartUpTasks((TreeMap<Integer, StartUpStep>) modelToLoad.getStartUpTasks().clone());
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Title : "+title.getName()+'\n');
		for(Integer s : sequences.keySet()){
			sb.append('\t'+sequences.get(s).toString()+'\n');
		}
		return sb.toString();
	}

		/**
		 * @return TitleBar
		 */
		public TitleBar getTitleBar() {
			return title;
		}
		
	/**
	 * @return SequenceBar TreeMap
	 */
	public TreeMap<Integer, SequenceBar> getSequences() {
		return sequences;
	}
	
	/**
	 * @param indexInSequenceBarTreeMap
	 * @return SequenceBar
	 */
	public SequenceBar getSequence(Integer indexInSequenceBarTreeMap){
		return sequences.get(indexInSequenceBarTreeMap);
	}
	
	/**
	 * @param sequenceBarToAdd
	 */
	public void addSequence(SequenceBar sequenceBarToAdd){
		if(!sequences.isEmpty()){
			this.sequences.put(sequences.lastKey()+1, sequenceBarToAdd);
		}else{
			this.sequences.put(1, sequenceBarToAdd);
		}
	}
	
	/**
	 * @param sequenceBar
	 */
	public void setSequences(TreeMap<Integer, SequenceBar> sequenceBar){
		this.sequences = (TreeMap<Integer, SequenceBar>) new TreeMap<Integer, SequenceBar>(sequenceBar).clone();
	}
	
	//Milestones
	/**
	 * @param milestone
	 */
	public void addMilestone(Milestone milestone){
		if(!milestones.isEmpty()){
			this.milestones.put(milestones.lastKey()+1, milestone);
			
		}else{
			this.milestones.put(1, milestone);
		}
	}
	
	/**
	 * @param indexInMilestoneTreeMap
	 * @return Milestone
	 */
	public Milestone getMilestone(Integer indexInMilestoneTreeMap){
		return this.milestones.get(indexInMilestoneTreeMap);
	}
	
	/**
	 * @return Milestone TreeMap
	 */
	public TreeMap<Integer, Milestone> getMilestones(){
		return milestones;
	}
	
	/**
	 * @param milestone
	 */
	public void setMiletones(TreeMap<Integer, Milestone> milestone) {
		this.milestones=(TreeMap<Integer, Milestone>) new TreeMap<Integer, Milestone>(milestone).clone();
	}
	
	//Comments
	/**
	 * @param commentToAdd
	 */
	public void addComment(Comment commentToAdd){
		if(!comments.isEmpty()){
			this.comments.put(comments.lastKey()+1, commentToAdd);
		} else {
			this.comments.put(1, commentToAdd);
		}
	}
	
	/**
	 * @param indexInCommentTreeMap
	 * @return Comment
	 */
	public Comment getComment(Integer indexInCommentTreeMap){
		return comments.get(indexInCommentTreeMap);
	}
	
	/**
	 * @return Comment TreeMap
	 */
	public TreeMap<Integer, Comment> getComments(){
		return comments;
	}

	/**
	 * @return Start Up Sequence Title
	 */
	public String getProjectName() {
		return this.title.getName();
	}

	/**
	 * @param titleBar
	 */
	public void setTitleBar(TitleBar titleBar) {
		this.title=new TitleBar(titleBar.getName(), titleBar.getX(), titleBar.getY(), titleBar.getWidth(), titleBar.getHeight(),titleBar.getColor());
		//System.out.println("Style : "+tbar.getStyle());
		this.title.setStyle(titleBar.getStyle());
	}

	/**
	 * @param comments
	 */
	public void setComments(TreeMap<Integer, Comment> comments){
		this.comments=(TreeMap<Integer, Comment>) new TreeMap<Integer, Comment>(comments).clone();
	}
	
	/**
	 * @param modelToLoad
	 */
	public void loadModel(StartUpSequence modelToLoad){
		try {
			//Adapt view zoom to the save zoom
			//instanciate loaded value
			this.setTitleBar(modelToLoad.getTitleBar());
			
			//milestones
			this.milestones.clear();
			for(int i : modelToLoad.getMilestones().keySet()){
				this.addMilestone(modelToLoad.getMilestone(i));
			}
			
			//sequences
			this.sequences.clear();
			for(int i : modelToLoad.getSequences().keySet()){
				this.addSequence(modelToLoad.getSequence(i));
			}
			//System.out.println(sequences);
			//comments
			this.comments.clear();
			for(int i : modelToLoad.getComments().keySet()){
				this.addComment(modelToLoad.getComment(i));
			}
			
			//startuprasks
			this.startUpTasks.clear();
			for(int i : modelToLoad.getStartUpTasks().keySet()){
				this.addStartUpTask(modelToLoad.getStartUpTask(i));
			}
		} catch (DOMException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}

	public void addStartUpTask(StartUpStep startUpTask) {
		if(!startUpTasks.isEmpty()){
			this.startUpTasks.put(startUpTasks.lastKey()+1, startUpTask);
		} else {
			this.startUpTasks.put(1, startUpTask);
		}
	}

	public StartUpStep getStartUpTask(int i) {
		return startUpTasks.get(i);
	}

	/**
	 * Unselect All Objects
	 */
	public void unselectAll() {
		for(int key : milestones.keySet()){
			milestones.get(key).unSelect();
		}
		for(int key : sequences.keySet()){
			sequences.get(key).setSelected(false);
		}
		for(int key : comments.keySet()){
			comments.get(key).setSelected(false);
		}
		for(int key : startUpTasks.keySet()){
			startUpTasks.get(key).setSelected(false);
		}
	}

	/**
	 * @return cartouche
	 */
	public TreeMap<String,String> getCartouche() {
		return titleBlock.getCartouche();
	}

	/**
	 * @param cartouche
	 */
	public void setCartouche(TreeMap<String,String> cartouche) {
		this.titleBlock.setCartouche((TreeMap<String, String>) cartouche.clone());
	}

	/**
	 * @return revisions
	 */
	public ArrayList<TreeMap<String, String>> getRevisions() {
		return titleBlock.getRevisions();
	}

	/**
	 * @param revisions
	 */
	public void setRevisions(ArrayList<TreeMap<String, String>> revisions) {
		this.titleBlock.setRevisions((ArrayList<TreeMap<String, String>>) revisions.clone());
	}

	/**
	 * @return project title block
	 */
	public ProjectTitleBlock getProjectTitleBlock() {
		return titleBlock;
	}

	public TreeMap<Integer, StartUpStep> getStartUpTasks() {
		return startUpTasks;
	}

	public void setStartUpTasks(TreeMap<Integer, StartUpStep> startUpTasks) {
		this.startUpTasks = (TreeMap<Integer, StartUpStep>) new TreeMap<Integer, StartUpStep>(startUpTasks).clone();
	}

	public SelectedItems<MovableItem> getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(SelectedItems<MovableItem> arrayList) {
		this.selectedItems = arrayList;
	}
	
	
	public class SelectedItems<T> extends ArrayList<T>{


		@Override
		public boolean add(T e) {
			System.out.println("Contains : "+contains(e));
			if(!contains(e)){
				System.out.println("Add "+e);
				return super.add(e);
			} else {
				System.out.println("Remove "+e);
				remove(indexOf(e));
				return false;
			}
		}

		@Override
		public boolean contains(Object o) {
			boolean res = false;
			Iterator<MovableItem> it = (Iterator<MovableItem>) this.iterator();
			while(it.hasNext()){
				if(it.next().equals(o)){
					res=true;
				}
			}
			
			return res;
		}
		
	}


	public void removeComment(Comment c) {
		for(int i : comments.keySet()){
			Comment c1 = comments.get(i);
			if(c1.equals(c)){
				comments.remove(i);
			}
		}
	}

	public void removeMilestone(Milestone cancelAttr) {
		for(int i : milestones.keySet()){
			Milestone m = milestones.get(i);
			if(m.equals(cancelAttr)){
				milestones.remove(i);
			}
		}
	}

	public void removeSequence(SequenceBar cancelAttr) {
		for(int i : sequences.keySet()){
			SequenceBar sb = sequences.get(i);
			if(sb.equals(cancelAttr)){
				sequences.remove(i);
			}
		}
	}

	public void removeStep(StartUpStep cancelAttr) {
		for(int i : startUpTasks.keySet()){
			StartUpStep sb = startUpTasks.get(i);
			if(sb.equals(cancelAttr)){
				startUpTasks.remove(i);
			}
		}
	}

	public Milestone getMilestone(Milestone cancelAttr) {
		for(int i : milestones.keySet()){
			Milestone m = milestones.get(i);
			if(m.equals(cancelAttr)){
				return m;
			}
		}
		return null;
	}
	
	public SequenceBar getSequence(SequenceBar cancelAttr) {
		for(int i : sequences.keySet()){
			SequenceBar m = sequences.get(i);
			if(m.equals(cancelAttr)){
				return m;
			}
		}
		return null;
	}
	
	public StartUpStep getStartUpStep(StartUpStep cancelAttr) {
		for(int i : startUpTasks.keySet()){
			StartUpStep m = startUpTasks.get(i);
			if(m.equals(cancelAttr)){
				return m;
			}
		}
		return null;
	}

	public Comment getComment(Comment cancelAttr) {
		for(int i : comments.keySet()){
			Comment c1 = comments.get(i);
			if(c1.equals(cancelAttr)){
				return c1;
			}
		}
		return null;
	}


}
