package cancel;



import java.util.ArrayList;
import java.util.Iterator;

import cancel.CancelFactory.CancellableActionLabel;

import com.sun.java.swing.plaf.motif.resources.motif;

import conf.GeneralConfig;
import controller.Controller;
import model.Comment;
import model.Milestone;
import model.MovableItem;
import model.SequenceBar;
import model.StartUpStep;
import model.StartUpSequence.SelectedItems;

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
			case milestone_deletion:
				controller.getModel().removeMilestone((Milestone)((Object[])cancelAttr)[0]);
				Iterator<Milestone> it1 = ((ArrayList<Milestone>)((Object[])cancelAttr)[1]).iterator();
				while(it1.hasNext()){
					controller.getModel().getMilestone(it1.next()).getDestMilestone().remove(((Milestone)((Object[])cancelAttr)[0]));
				}
				Iterator<StartUpStep> itt1 = ((ArrayList<StartUpStep>)((Object[])cancelAttr)[2]).iterator();
				while(itt1.hasNext()){
					controller.getModel().getStartUpStep(itt1.next()).getDestMilestone().remove(((Milestone)((Object[])cancelAttr)[0]));
				}
				break;
			case sequence_deletion:
				controller.getModel().removeSequence((SequenceBar)cancelAttr);
				break;
			case step_deletion:
				controller.getModel().removeStep((StartUpStep)((Object[])cancelAttr)[0]);
				Iterator<Milestone> it2 = ((ArrayList<Milestone>)((Object[])cancelAttr)[1]).iterator();
				while(it2.hasNext()){
					controller.getModel().getMilestone(it2.next()).getDestSUT().remove(((StartUpStep)((Object[])cancelAttr)[0]));
				}
				Iterator<StartUpStep> itt2 = ((ArrayList<StartUpStep>)((Object[])cancelAttr)[2]).iterator();
				while(itt2.hasNext()){
					controller.getModel().getStartUpStep(itt2.next()).getDestSUT().remove(((StartUpStep)((Object[])cancelAttr)[0]));
				}
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
				controller.getModel().getSequence((SequenceBar)((ArrayList<Object>)cancelAttr).get(0)).setX((Double)((ArrayList<Object>)cancelAttr).get(6));
				controller.getModel().getSequence((SequenceBar)((ArrayList<Object>)cancelAttr).get(0)).setY((Double)((ArrayList<Object>)cancelAttr).get(7));
				controller.getModel().getSequence((SequenceBar)((ArrayList<Object>)cancelAttr).get(0)).setWidth((Double)((ArrayList<Object>)cancelAttr).get(8));
				controller.getModel().getSequence((SequenceBar)((ArrayList<Object>)cancelAttr).get(0)).setHeight((Double)((ArrayList<Object>)cancelAttr).get(9));
				controller.getModel().getSequence((SequenceBar)((ArrayList<Object>)cancelAttr).get(0)).setExtendedHeight((Double)((ArrayList<Object>)cancelAttr).get(10));
				break;
			case step_move:
				controller.getModel().getStartUpStep((StartUpStep)((ArrayList<Object>)cancelAttr).get(0)).setX((Double)((ArrayList<Object>)cancelAttr).get(5));
				controller.getModel().getStartUpStep((StartUpStep)((ArrayList<Object>)cancelAttr).get(0)).setY((Double)((ArrayList<Object>)cancelAttr).get(6));
				controller.getModel().getStartUpStep((StartUpStep)((ArrayList<Object>)cancelAttr).get(0)).setWidth((Double)((ArrayList<Object>)cancelAttr).get(7));
				controller.getModel().getStartUpStep((StartUpStep)((ArrayList<Object>)cancelAttr).get(0)).setHeight((Double)((ArrayList<Object>)cancelAttr).get(8));
				break;
			case comment_move:
				controller.getModel().getComment((Comment)((ArrayList<Object>)cancelAttr).get(0)).setX((Double)((ArrayList<Object>)cancelAttr).get(5));
				controller.getModel().getComment((Comment)((ArrayList<Object>)cancelAttr).get(0)).setY((Double)((ArrayList<Object>)cancelAttr).get(6));
				controller.getModel().getComment((Comment)((ArrayList<Object>)cancelAttr).get(0)).setWidth((Double)((ArrayList<Object>)cancelAttr).get(7));
				controller.getModel().getComment((Comment)((ArrayList<Object>)cancelAttr).get(0)).setHeight((Double)((ArrayList<Object>)cancelAttr).get(8));
				break;
			case connection_creation:
				Object o = ((ArrayList<Object>)cancelAttr).get(0);
				ArrayList<Milestone> mDest = (ArrayList<Milestone>) ((ArrayList<Object>)cancelAttr).get(1);
				ArrayList<StartUpStep> sDest = (ArrayList<StartUpStep>) ((ArrayList<Object>)cancelAttr).get(2);
				
				if(o.getClass()==(new Milestone("")).getClass()){
					Milestone m = controller.getModel().getMilestone((Milestone)o);
					Iterator<Milestone> it = mDest.iterator();
					while(it.hasNext()){
						m.addDest(it.next());
					}
					Iterator<StartUpStep> it3 = sDest.iterator();
					while(it3.hasNext()){
						m.addDestSUT(it3.next());
					}
				} else if (o.getClass()==(new StartUpStep("", 0, 0, 0, 0).getClass())){
					StartUpStep sut = controller.getModel().getStartUpStep((StartUpStep)o);
					Iterator<Milestone> it = mDest.iterator();
					while(it.hasNext()){
						sut.addDest(it.next());
					}
					Iterator<StartUpStep> it3 = sDest.iterator();
					while(it3.hasNext()){
						sut.addDestSUT(it3.next());
					}
				}
				break;
			case connection_deletion:
				if(((String[])cancelAttr)[0].equals("milestone")){
					if(((String[])cancelAttr)[1].equals("milestone")){
						controller.getModel().getMilestone(Integer.valueOf(((String[])cancelAttr)[2])).getDestMilestone().remove(controller.getModel().getMilestone(Integer.valueOf(((String[])cancelAttr)[3])));
					}
					else if(((String[])cancelAttr)[1].equals("step")){
						controller.getModel().getMilestone(Integer.valueOf(((String[])cancelAttr)[2])).getDestSUT().remove(controller.getModel().getStartUpTask(Integer.valueOf(((String[])cancelAttr)[3])));
					}
				} else if(((String[])cancelAttr)[0].equals("step")){
					if(((String[])cancelAttr)[1].equals("milestone")){
						controller.getModel().getStartUpTask(Integer.valueOf(((String[])cancelAttr)[2])).getDestMilestone().remove(controller.getModel().getMilestone(Integer.valueOf(((String[])cancelAttr)[3])));
					}
					else if(((String[])cancelAttr)[1].equals("step")){
						controller.getModel().getStartUpTask(Integer.valueOf(((String[])cancelAttr)[2])).getDestSUT().remove(controller.getModel().getStartUpTask(Integer.valueOf(((String[])cancelAttr)[3])));
					}
				}
				break;
			case group_move:
				int dx = ((Integer)((Object[])cancelAttr)[1]);
				int dy = ((Integer)((Object[])cancelAttr)[2]);
				Iterator<MovableItem> itMI = ((SelectedItems<MovableItem>)((Object[])cancelAttr)[0]).iterator();
				while(itMI.hasNext()){
					itMI.next().move(dx, dy);
				}
				break;
			case comment_resizing:
				controller.getModel().getComment((Integer)((Object[])cancelAttr)[0]).setX((Double)((Object[])cancelAttr)[5]);
				controller.getModel().getComment((Integer)((Object[])cancelAttr)[0]).setY((Double)((Object[])cancelAttr)[6]);
				controller.getModel().getComment((Integer)((Object[])cancelAttr)[0]).setWidth((Double)((Object[])cancelAttr)[7]);
				controller.getModel().getComment((Integer)((Object[])cancelAttr)[0]).setHeight((Double)((Object[])cancelAttr)[8]);
				break;
			case sequence_resizing:
				controller.getModel().getSequence((Integer)((Object[])cancelAttr)[0]).setX((Double)((Object[])cancelAttr)[6]);
				controller.getModel().getSequence((Integer)((Object[])cancelAttr)[0]).setY((Double)((Object[])cancelAttr)[7]);
				controller.getModel().getSequence((Integer)((Object[])cancelAttr)[0]).setWidth((Double)((Object[])cancelAttr)[8]);
				controller.getModel().getSequence((Integer)((Object[])cancelAttr)[0]).setHeight((Double)((Object[])cancelAttr)[9]);
				controller.getModel().getSequence((Integer)((Object[])cancelAttr)[0]).setExtendedHeight((Double)((Object[])cancelAttr)[10]);
				break;
			case step_resizing:
				controller.getModel().getStartUpTask((Integer)((Object[])cancelAttr)[0]).setX((Double)((Object[])cancelAttr)[5]);
				controller.getModel().getStartUpTask((Integer)((Object[])cancelAttr)[0]).setY((Double)((Object[])cancelAttr)[6]);
				controller.getModel().getStartUpTask((Integer)((Object[])cancelAttr)[0]).setWidth((Double)((Object[])cancelAttr)[7]);
				controller.getModel().getStartUpTask((Integer)((Object[])cancelAttr)[0]).setHeight((Double)((Object[])cancelAttr)[8]);
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
				controller.getModel().addMilestone((Milestone)((Object[])cancelAttr)[0]);
				Iterator<Milestone> it = ((ArrayList<Milestone>)((Object[])cancelAttr)[1]).iterator();
				while(it.hasNext()){
					controller.getModel().getMilestone(it.next()).addDest((Milestone)((Object[])cancelAttr)[0]);
				}
				Iterator<StartUpStep> itt = ((ArrayList<StartUpStep>)((Object[])cancelAttr)[2]).iterator();
				while(itt.hasNext()){
					controller.getModel().getStartUpStep(itt.next()).addDest((Milestone)((Object[])cancelAttr)[0]);
				}
				break;
			case step_deletion:
				controller.getModel().addStartUpTask((StartUpStep)((Object[])cancelAttr)[0]);
				Iterator<Milestone> it1 = ((ArrayList<Milestone>)((Object[])cancelAttr)[1]).iterator();
				while(it1.hasNext()){
					controller.getModel().getMilestone(it1.next()).addDestSUT(((StartUpStep)((Object[])cancelAttr)[0]));
				}
				Iterator<StartUpStep> itt1 = ((ArrayList<StartUpStep>)((Object[])cancelAttr)[2]).iterator();
				while(itt1.hasNext()){
					controller.getModel().getStartUpStep(itt1.next()).addDestSUT(((StartUpStep)((Object[])cancelAttr)[0]));
				}
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
				controller.getModel().getSequence((SequenceBar)((ArrayList<Object>)cancelAttr).get(0)).setWidth((Double)((ArrayList<Object>)cancelAttr).get(3));
				controller.getModel().getSequence((SequenceBar)((ArrayList<Object>)cancelAttr).get(0)).setHeight((Double)((ArrayList<Object>)cancelAttr).get(4));
				controller.getModel().getSequence((SequenceBar)((ArrayList<Object>)cancelAttr).get(0)).setExtendedHeight((Double)((ArrayList<Object>)cancelAttr).get(5));
				break;
			case step_move:
				controller.getModel().getStartUpStep((StartUpStep)((ArrayList<Object>)cancelAttr).get(0)).setX((Double)((ArrayList<Object>)cancelAttr).get(1));
				controller.getModel().getStartUpStep((StartUpStep)((ArrayList<Object>)cancelAttr).get(0)).setY((Double)((ArrayList<Object>)cancelAttr).get(2));
				controller.getModel().getStartUpStep((StartUpStep)((ArrayList<Object>)cancelAttr).get(0)).setWidth((Double)((ArrayList<Object>)cancelAttr).get(3));
				controller.getModel().getStartUpStep((StartUpStep)((ArrayList<Object>)cancelAttr).get(0)).setHeight((Double)((ArrayList<Object>)cancelAttr).get(4));
				break;
			case comment_move:
				controller.getModel().getComment((Comment)((ArrayList<Object>)cancelAttr).get(0)).setX((Double)((ArrayList<Object>)cancelAttr).get(1));
				controller.getModel().getComment((Comment)((ArrayList<Object>)cancelAttr).get(0)).setY((Double)((ArrayList<Object>)cancelAttr).get(2));
				controller.getModel().getComment((Comment)((ArrayList<Object>)cancelAttr).get(0)).setWidth((Double)((ArrayList<Object>)cancelAttr).get(3));
				controller.getModel().getComment((Comment)((ArrayList<Object>)cancelAttr).get(0)).setHeight((Double)((ArrayList<Object>)cancelAttr).get(4));
				break;
			case connection_creation:
				Object o = ((ArrayList<Object>)cancelAttr).get(0);
				ArrayList<Milestone> mDest = (ArrayList<Milestone>) ((ArrayList<Object>)cancelAttr).get(1);
				ArrayList<StartUpStep> sDest = (ArrayList<StartUpStep>) ((ArrayList<Object>)cancelAttr).get(2);
				
				ArrayList<Milestone> milestoneDestStackToRemove = new ArrayList<Milestone>();
				ArrayList<StartUpStep> startUpStepDestStackToRemove = new ArrayList<StartUpStep>();
				
				if(o.getClass()==(new Milestone("")).getClass()){
					Milestone m = controller.getModel().getMilestone((Milestone)o);
					System.out.println(m.getName());
					Iterator<Milestone> it2 = m.getDestMilestone().iterator();
					while(it2.hasNext()){
						Milestone dest = it2.next();
						System.out.println("\t-->"+dest.getName());
						Iterator<Milestone> itt2 = mDest.iterator();
						while(itt2.hasNext()){
							Milestone tmp = itt2.next();
							if(tmp.equal(dest)){
								milestoneDestStackToRemove.add(dest);
							}
						}
					}
					Iterator<StartUpStep> it3 = m.getDestSUT().iterator();
					while(it3.hasNext()){
						StartUpStep dest = it3.next();
						Iterator<StartUpStep> itt3 = sDest.iterator();
						while(itt3.hasNext()){
							StartUpStep tmp = itt3.next();
							if(tmp.equals(dest)){
								startUpStepDestStackToRemove.add(dest);
							}
						}
					}
					
					//Remove
					Iterator<Milestone> rmItM = milestoneDestStackToRemove.iterator();
					while(rmItM.hasNext()){
						m.getDestMilestone().remove(rmItM.next());
					}
					Iterator<StartUpStep> rmItS = startUpStepDestStackToRemove.iterator();
					while(rmItS.hasNext()){
						m.getDestSUT().remove(rmItS.next());
					}
					
				} else if (o.getClass()==(new StartUpStep("", 0, 0, 0, 0).getClass())){
					StartUpStep sut = controller.getModel().getStartUpStep((StartUpStep)o);
					Iterator<Milestone> it4 = sut.getDestMilestone().iterator();
					while(it4.hasNext()){
						Milestone dest = it4.next();
						Iterator<Milestone> itt4 = mDest.iterator();
						while(itt4.hasNext()){
							Milestone tmp = itt4.next();
							if(tmp.equal(dest)){
								milestoneDestStackToRemove.add(dest);
							}
						}
					}
					Iterator<StartUpStep> it5 = sut.getDestSUT().iterator();
					while(it5.hasNext()){
						StartUpStep dest = it5.next();
						Iterator<StartUpStep> itt5 = sDest.iterator();
						while(itt5.hasNext()){
							StartUpStep tmp = itt5.next();
							if(tmp.equals(dest)){
								startUpStepDestStackToRemove.add(dest);
							}
						}
					}
					//remove
					Iterator<Milestone> rmItM = milestoneDestStackToRemove.iterator();
					while(rmItM.hasNext()){
						sut.getDestMilestone().remove(rmItM.next());
					}
					Iterator<StartUpStep> rmItS = startUpStepDestStackToRemove.iterator();
					while(rmItS.hasNext()){
						sut.getDestSUT().remove(rmItS.next());
					}
				}
				break;
			case connection_deletion:
				if(((String[])cancelAttr)[0].equals("milestone")){
					if(((String[])cancelAttr)[1].equals("milestone")){
						controller.getModel().getMilestone(Integer.valueOf(((String[])cancelAttr)[2])).addDest(controller.getModel().getMilestone(Integer.valueOf(((String[])cancelAttr)[3])));
					}
					else if(((String[])cancelAttr)[1].equals("step")){
						controller.getModel().getMilestone(Integer.valueOf(((String[])cancelAttr)[2])).addDestSUT(controller.getModel().getStartUpTask(Integer.valueOf(((String[])cancelAttr)[3])));
					}
				} else if(((String[])cancelAttr)[0].equals("step")){
					if(((String[])cancelAttr)[1].equals("milestone")){
						controller.getModel().getStartUpTask(Integer.valueOf(((String[])cancelAttr)[2])).addDest(controller.getModel().getMilestone(Integer.valueOf(((String[])cancelAttr)[3])));
					}
					else if(((String[])cancelAttr)[1].equals("step")){
						controller.getModel().getStartUpTask(Integer.valueOf(((String[])cancelAttr)[2])).addDestSUT(controller.getModel().getStartUpTask(Integer.valueOf(((String[])cancelAttr)[3])));
					}
				}
				break;
			case group_move:
				int dx = ((Integer)((Object[])cancelAttr)[1]);
				int dy = ((Integer)((Object[])cancelAttr)[2]);
				Iterator<MovableItem> itMI = ((SelectedItems<MovableItem>)((Object[])cancelAttr)[0]).iterator();
				while(itMI.hasNext()){
					itMI.next().move(-dx, -dy);
				}
				break;
			case comment_resizing:
				controller.getModel().getComment((Integer)((Object[])cancelAttr)[0]).setX((Double)((Object[])cancelAttr)[1]);
				controller.getModel().getComment((Integer)((Object[])cancelAttr)[0]).setY((Double)((Object[])cancelAttr)[2]);
				controller.getModel().getComment((Integer)((Object[])cancelAttr)[0]).setWidth((Double)((Object[])cancelAttr)[3]);
				controller.getModel().getComment((Integer)((Object[])cancelAttr)[0]).setHeight((Double)((Object[])cancelAttr)[4]);
				break;
			case sequence_resizing:
				controller.getModel().getSequence((Integer)((Object[])cancelAttr)[0]).setX((Double)((Object[])cancelAttr)[1]);
				controller.getModel().getSequence((Integer)((Object[])cancelAttr)[0]).setY((Double)((Object[])cancelAttr)[2]);
				controller.getModel().getSequence((Integer)((Object[])cancelAttr)[0]).setWidth((Double)((Object[])cancelAttr)[3]);
				controller.getModel().getSequence((Integer)((Object[])cancelAttr)[0]).setHeight((Double)((Object[])cancelAttr)[4]);
				controller.getModel().getSequence((Integer)((Object[])cancelAttr)[0]).setExtendedHeight((Double)((Object[])cancelAttr)[5]);
				break;
			case step_resizing:
				controller.getModel().getStartUpTask((Integer)((Object[])cancelAttr)[0]).setX((Double)((Object[])cancelAttr)[1]);
				controller.getModel().getStartUpTask((Integer)((Object[])cancelAttr)[0]).setY((Double)((Object[])cancelAttr)[2]);
				controller.getModel().getStartUpTask((Integer)((Object[])cancelAttr)[0]).setWidth((Double)((Object[])cancelAttr)[3]);
				controller.getModel().getStartUpTask((Integer)((Object[])cancelAttr)[0]).setHeight((Double)((Object[])cancelAttr)[4]);
				break;
			default:
				break;
			
			}
		}

		public CancellableActionLabel getLabel() {
			return cal;
		}
}