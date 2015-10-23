package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import model.Comment;
import model.InteractiveBar;
import model.InteractiveBar.ResizeDirection;
import model.Milestone;
import model.MovableItem;
import model.SequenceBar;
import model.StartUpSequence;
import model.StartUpStep;
import model.TitleBar;
import model.TitleBar.Style;
import model.mode.Mode;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import target.TargetView;
import view.CommentEditorView;
import view.DbChooserDialog;
import view.ExitFrame;
import view.InteractiveBarEditorView;
import view.LaunchFrame;
import view.MilestoneEditorFrame;
import view.ProjectResume;
import view.ProjectTitleBlockView;
import view.RevisionView;
import view.SequenceBarEditorView;
import view.SettingsFrame;
import view.StepEditorView;
import view.View;
import xml.LoadXMLFile;
import xml.SaveXMLFile;

import com.packenius.library.xspdf.XSPDF;

import conf.DatePicker;
import conf.GeneralConfig;
import conf.Utils;
import connector.JConnector;
import controller.CancelFactory.CancellableActionLabel;
import database.Database;
import database.ProjectResumeWorker;
import database.UpdateAllMilestoneWorkerOptimised;

/**
 * Main controller of the application
 * @author S.Trémouille
 *
 */

public class Controller {
	private View view;
	private StartUpSequence model;
	private Controller controller;

	private InteractiveBar ibModel;
	private InteractiveBarEditorView ibView;
	private InteractiveBarEditorController ibController;

	private int xdecal = 0;
	private int ydecal = 0;
	private Integer milestoneRef = 0;
	private Integer sequenceBarRef = 0;
	private Integer commentRef = 0;
	private Integer startUpTaskRef = 0;
	private int milestoneRefSelected=0;
	private int sequenceBarRefSelected = 0;
	private int commentRefSelected = 0;
	private int startUpTaskRefSelected = 0;
	
	//right click on connector
	//this right click menu is special 'cause is generated each time you right click on a connector
	private boolean rightClickOnConnector;
	private int srcIndexM;
	private ArrayList<Integer> destIndexM;
	private int srcIndexS;
	private ArrayList<Integer> destIndexS;

	private int previousX = 0;
	private int previousY = 0;

	private boolean ctrlDown = false;
	private boolean shiftDown = false;
	private boolean initShiftDown = false;

	private boolean leftClick = false;
	private boolean willMove = false;

	private boolean titleSelected = false;
	private boolean isResizing = false;
	// variable for connection mode
	private boolean connect = false;
	private int connectFromRefM;
	private int connectFromRefSUT;
	private ArrayList<Integer> connectToRefM;
	private ArrayList<Integer> connectToRefSUT;
	
	//variable relative to tool bar
	private boolean newMilestone;
	private boolean newComment;
	private boolean newSequence;
	private boolean newStartUpTask;
	private boolean editButton;
	private boolean deleteButton;
	private boolean cisorsButton;
	
	//variable relatives to copy & paste
	private StartUpStep startUpTaskCopied; 
	private Milestone milestoneCopied;
	
	//right click menu
	private int whereRightClickHappenX;
	private int whereRightClickHappenY;
	
	// resize interactive bar
	private ResizeDirection resizeTitleDirection = null;
	private ResizeDirection resizeSequenceDirection = null;
	private ResizeDirection resizeCommentDirection = null;
	private ResizeDirection resizeStartUpTaskDirection = null;
	
	private Cursor cursor;
	
	private CancelFactory cancelFactory;
	
	/**
	 * Classical constructor of the controller 
	 * @param model
	 * @param view
	 */
	public Controller(StartUpSequence model, View view) {
		JOptionPane.setDefaultLocale(Locale.ENGLISH);
		
		this.setModel(model);
		this.setView(view);
		this.controller = this;

		this.view.addPanelMouseListener(new PanelMouseListener());
		this.view.addPanelMouseMotionListener(new PanelMotionListener());
		this.view.addPanelMouseWheelListener(new PanelMouseWheelListener());
		this.view.addToolBarMouseListener(new ToolBarMouseListener());
		this.view.addRightClickMouseAdaptater(new RightClickMenuListener());
		this.view.addEditMilestoneListener(new EditMenuItemListener());
		this.view.addDeleteMilestoneListener(new DeleteMenuItemListener());
		this.view.addOperatedStartUpTaskListener(new OperatedSUTListener());
		this.view.addConnectMilestoneListener(new ConnectMenuItemListener());
		this.view.addFinalizeConnectionListener(new FinalizeConnectionListener());
		this.view.addCancelConnectionListener(new CancelConnectionListener());
		this.view.addKeyListener(new ViewKeyListener());
		this.view.addUpdateListener(new UpdateButtonListener());
		this.view.addPrintMenuItemListener(new PrintMenuItemListener());
		this.view.addSaveAsListener(new SaveMenuListener(true,false));
		this.view.addSaveListener(new SaveMenuListener(false,false));
		this.view.addLoadListener(new LoadMenuListener(false));
		this.view.addPreferenceListener(new PreferenceListener());
		this.view.addLoadAutosaveListener(new LoadMenuListener("autosave.xml",false));
		this.view.addNewMenuItemListener(new NewMenuItemListener(false));
		this.view.addKeyListener(new SupprKeyListerner());
		this.view.addSetupTitleBlock(new SetupTitleBlockListener());
		this.view.addManageRevisionListener(new ManageRevisionsListener());
		this.view.addQuitListener(new QuitListener());
		this.view.addWindowListener(new QuitListener());
		this.view.addShowProjectResumeListener(new ShowProjectResumeListener());
		this.view.addProjectTargetListener(new ShowProjectTargetListener());
		this.view.addMoinsZoomListener(new DecreaseZoomListener());
		this.view.addPlusZoomListener(new IncreaseZoomListener());
		this.view.addCopyPasteListener(new CopyPasteListener());
		this.view.addCancelListener(new CancelListener());
		this.view.addRedoListener(new RedoListener());
		
		//toolbar
		this.view.getToolBar().addMilestoneButtonListener(new MilestoneButtonListener());
		this.view.getToolBar().addSequenceButtonListener(new SequenceButtonListener());
		this.view.getToolBar().addCommentButtonListener(new CommentButtonListener());
		this.view.getToolBar().addStartUpTaskButtonListener(new StartUpButtonListener());
		//this.view.getToolBar().addEditButtonListener(new EditButtonListener());
		//this.view.getToolBar().addDeleteButtonListener(new DeleteButtonListener());
		//this.view.getToolBar().addCisorsButtonListener(new CisorsButtonListener());
		PanelMotionListener pml = new PanelMotionListener();
		this.view.getToolBar().getNewMilestoneButton().addMouseMotionListener(pml);
		this.view.getToolBar().getNewSequenceButton().addMouseMotionListener(pml);
		this.view.getToolBar().getNewCommentButton().addMouseMotionListener(pml);
		this.view.getToolBar().getNewStartUpTaskButton().addMouseMotionListener(pml);
		//Drag & Drop
		this.view.getToolBar().getNewMilestoneButton().addMouseListener(new DragAndDropDetector(0));
		this.view.getToolBar().getNewSequenceButton().addMouseListener(new DragAndDropDetector(1));
		this.view.getToolBar().getNewCommentButton().addMouseListener(new DragAndDropDetector(2));
		this.view.getToolBar().getNewStartUpTaskButton().addMouseListener(new DragAndDropDetector(3));
		this.view.getDrawPanel().addMouseListener(new DragAndDropDetector(4));
		
		this.view.addNewCommentMenuItemListener(new NewCommentItemMenuListener());
		this.view.addNewMilestoneMenuItemListener(new NewMilestoneItemMenuListener());
		this.view.addNewSequenceMenuItemListener(new NewSequenceItemMenuListener());
		this.view.addNewStartUpTaskMenuItemListener(new NewStartUpTaskItemMenuListener());
		
		
		//grabbing cursor
		Toolkit tk = Toolkit.getDefaultToolkit();
		try {
			cursor = tk.createCustomCursor(ImageIO.read(ClassLoader.getSystemResource("img/grabbing.gif")), new java.awt.Point(16, 16),"Grabbing");
		} catch (HeadlessException e) {
			JOptionPane.showMessageDialog(view, e.getMessage());
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(view, e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(view, e.getMessage());
			e.printStackTrace();
		}
		
	this.cancelFactory = new CancelFactory();
	
	}

	/**
	 * View getter
	 * @return view
	 */
	public View getView() {
		return view;
	}

	/**
	 * View setter
	 * @param view 
	 */
	public void setView(View view) {
		this.view = view;
	}
	

	/**
	 * Model getter
	 * @return model
	 */
	public StartUpSequence getModel() {
		return model;
	}

	/**
	 * Model setter
	 * @param model 
	 */
	public void setModel(StartUpSequence model) {
		this.model = model;
	}

	/**
	 * Detect if there is a milestone on the draw panel under (x,y) point
	 * @param x
	 * @param y
	 * @return Milestone
	 */
	public Milestone detectMilestone(int x, int y) {
		Milestone res = null;
		for (Integer i : model.getMilestones().keySet()) {
			if (model.getMilestone(i).getBounds().contains(new Point(x, y))) {
				res = model.getMilestone(i);
			}
		}
		return res;
	}
	
	/**
	 * Detect if there is a start up task on the draw panel under (x,y) point
	 * @param x
	 * @param y
	 * @return Milestone
	 */
	public StartUpStep detectStartUpTask(int x, int y) {
		StartUpStep res = null;
		for (Integer i : model.getStartUpTasks().keySet()) {
			if (model.getStartUpTask(i).getBounds().contains(new Point(x, y))) {
				res = model.getStartUpTask(i);
			}
		}
		return res;
	}

	/**
	 * Detect if there is a sequence on the draw panel under (x,y) point
	 * @param x
	 * @param y
	 * @return SequenceBar
	 */
	public SequenceBar detectSequenceBar(int x, int y) {
		SequenceBar res = null;
		for (Integer i : model.getSequences().keySet()) {
			if (model.getSequence(i).getBounds().contains(new Point(x, y))) {
				res = model.getSequence(i);
			} else if (model.getSequence(i).getExtendedRectangle().getBounds().contains(new Point(x, y))) {
				res = model.getSequence(i);
			}
		}
		return res;
	}

	/**
	 * Detect if there is a comment on the draw panel under (x,y) point
	 * @param x
	 * @param y
	 * @return Comment
	 */
	public Comment detectComment(int x, int y) {
		Comment res = null;
		for (Integer i : model.getComments().keySet()) {
			if (model.getComment(i).getBounds().contains(new Point(x, y))) {
				res = model.getComment(i);
			}
		}
		return res;
	}

	class PanelMotionListener implements MouseMotionListener {
		
		@Override
		public void mouseDragged(final MouseEvent arg0) {
			if(newMilestone){
				view.getDrawPanel().drawMovingMilestone(view.getDrawPanel().getVisibleRect().x+arg0.getX() - GeneralConfig.milestoneWidth/2-view.getToolBar().getWidth(), view.getDrawPanel().getVisibleRect().y+arg0.getY() - GeneralConfig.milestoneHeight/2);
			} else if(newSequence){
				view.getDrawPanel().drawMovingSequenceBar(view.getDrawPanel().getVisibleRect().x+arg0.getX()-view.getToolBar().getWidth() - 200,
						view.getDrawPanel().getVisibleRect().y+arg0.getY() +70,
						GeneralConfig.milestoneWidth*4,
						GeneralConfig.milestoneHeight/2);
			} else if(newComment){
				view.getDrawPanel().drawMovingComment(view.getDrawPanel().getVisibleRect().x+arg0.getX() -view.getToolBar().getWidth()-40,view.getDrawPanel().getVisibleRect().y+arg0.getY()+120,
						(int)(GeneralConfig.milestoneWidth/1.3),
						(int)(GeneralConfig.milestoneHeight/1.5));
			} else if(newStartUpTask){
				view.getDrawPanel().drawMovingStartUpTask(view.getDrawPanel().getVisibleRect().x+arg0.getX() -view.getToolBar().getWidth()-GeneralConfig.milestoneWidth/4,
						view.getDrawPanel().getVisibleRect().y+arg0.getY()-GeneralConfig.milestoneHeight/8+250,
						(int)(GeneralConfig.milestoneWidth/2),
						(int)(GeneralConfig.milestoneHeight/4));
			}
			
			if (leftClick) {
				if (!isResizing) {
					view.setCursor(cursor);
				}
				// Comment dragging
				if (commentRef != 0 && resizeCommentDirection == null && willMove && !isResizing) {
					Comment c = model.getComment(commentRef);
					view.setCursor(new Cursor(Cursor.MOVE_CURSOR));
					view.getDrawPanel().drawMovingComment(arg0.getX() - xdecal, arg0.getY() - ydecal, c.getWidth(), c.getHeight());
				}
				// Milestone dragging
				else if (milestoneRef != 0) {
					view.getDrawPanel().drawMovingMilestone(arg0.getX() - xdecal, arg0.getY() - ydecal);
				}
				// Sequence Bar dragging
				else if (sequenceBarRef != 0 && resizeSequenceDirection == null && willMove && !isResizing) {
					SequenceBar sb = model.getSequence(sequenceBarRef);
					view.setCursor(new Cursor(Cursor.MOVE_CURSOR));
					view.getDrawPanel().drawMovingSequenceBar(arg0.getX() - xdecal, arg0.getY() - ydecal, sb.getWidth(), sb.getHeight());
				}
				// Start Up Task dragging
				else if (startUpTaskRef != 0 && resizeSequenceDirection == null && willMove && !isResizing) {
					StartUpStep sut = model.getStartUpTask(startUpTaskRef);
					view.setCursor(new Cursor(Cursor.MOVE_CURSOR));
					view.getDrawPanel().drawMovingStartUpTask(arg0.getX() - xdecal, arg0.getY() - ydecal, sut.getWidth(), sut.getHeight());
				}

				// Dragging the sidebars of the drawpanel
				else if (startUpTaskRef==0 && milestoneRef == 0 && sequenceBarRef == 0 && commentRef == 0 && !titleSelected && !isResizing) {
					/*Graphics g = view.getGraphics();
					g.setColor(Color.lightGray);
					g.fillOval(previousX, previousY, 10, 10);
					g.setColor(Color.gray);
					g.drawOval(previousX, previousY, 10, 10);
					if (previousX - arg0.getXOnScreen() < 0) {view.getScrollPane().getHorizontalScrollBar().setValue(view.getScrollPane().getHorizontalScrollBar().getValue() + 10);
					} else if (previousX - arg0.getXOnScreen() > 0) {
						view.getScrollPane().getHorizontalScrollBar().setValue(view.getScrollPane().getHorizontalScrollBar().getValue() - 10);
					}
					if (previousY - arg0.getYOnScreen() < 0) {
						view.getScrollPane().getVerticalScrollBar().setValue(view.getScrollPane().getVerticalScrollBar().getValue() + 10);
					} else if (previousY - arg0.getYOnScreen() > 0) {
						view.getScrollPane().getVerticalScrollBar().setValue(view.getScrollPane().getVerticalScrollBar().getValue() - 10);
					}*/
					view.getScrollPane().getHorizontalScrollBar().setValue(view.getScrollPane().getHorizontalScrollBar().getValue() + (previousX-arg0.getX()));
					view.getScrollPane().getVerticalScrollBar().setValue(view.getScrollPane().getVerticalScrollBar().getValue() + (previousY-arg0.getY()));
					return ;
				}
				if (titleSelected && resizeTitleDirection != null && GeneralConfig.titleEnable) {
					view.setResizing(true);
					Rectangle oldBounds = view.getTitleBar().getBounds();
					switch (resizeTitleDirection) {
						case SOUTH:
							view.getTitleBar().setBounds(new Rectangle(oldBounds.x, oldBounds.y, oldBounds.width, arg0.getY()));
							break;
						case WEST:
							if(!GeneralConfig.centeredTitleBar){
								view.getTitleBar().setBounds(new Rectangle(arg0.getX(), oldBounds.y, oldBounds.width + oldBounds.x - arg0.getX(), oldBounds.height));
							} else {
								view.getTitleBar().setBounds(new Rectangle(arg0.getX(), oldBounds.y, oldBounds.width + 2 * (oldBounds.x - arg0.getX()), oldBounds.height));
							}
							break;
						case EAST:
							if(!GeneralConfig.centeredTitleBar){
								view.getTitleBar().setBounds(new Rectangle(oldBounds.x, oldBounds.y, arg0.getX(), oldBounds.height));
							} else {
								view.getTitleBar().setBounds(new Rectangle(Math.round(oldBounds.x - (arg0.getX() - (oldBounds.x + oldBounds.width))), oldBounds.y, oldBounds.width + 2 * (arg0.getX() - (oldBounds.x + oldBounds.width)), oldBounds.height));
							}
							break;
						// case NORTH:view.getTitleBar().setBounds(new
						// Rectangle(oldBounds.x,arg0.getY(),oldBounds.width,oldBounds.height));break;
						case SOUTHEAST:
							if(!GeneralConfig.centeredTitleBar){
								view.getTitleBar().setBounds(new Rectangle(oldBounds.x, oldBounds.y, arg0.getX(), arg0.getY()));
							} else {
								view.getTitleBar().setBounds(new Rectangle(Math.round(oldBounds.x - (arg0.getX() - (oldBounds.x + oldBounds.width))), oldBounds.y, oldBounds.width + 2 * (arg0.getX() - (oldBounds.x + oldBounds.width)), arg0.getY()));
							}
							break;
						case SOUTHWEST:
							if(!GeneralConfig.centeredTitleBar){
								view.getTitleBar().setBounds(new Rectangle(arg0.getX(), oldBounds.y, oldBounds.width + oldBounds.x - arg0.getX(), arg0.getY()));
							} else {
							view.getTitleBar().setBounds(new Rectangle(arg0.getX(), oldBounds.y, oldBounds.width + 2 * (oldBounds.x - arg0.getX()), arg0.getY()));
							}
							break;
						default:
							break;
					}
				}
				if (sequenceBarRefSelected != 0 && resizeSequenceDirection != null) {
					view.setResizing(true);
					SequenceBar sb = model.getSequence(sequenceBarRefSelected);
					isResizing = true;
					int oldX = sb.getX();
					int oldY = sb.getY();
					switch (resizeSequenceDirection) {
						case NORTH:
							if (sb.setHeight(sb.getHeight() - (arg0.getY() - oldY))) {
								sb.setY(arg0.getY());
								sb.setExtendedHeight(sb.getExtendedHeight() - (arg0.getY() - oldY));
							}
							break;
						case SOUTH:
							if (sb.setHeight(arg0.getY() - oldY)) {
								sb.setExtendedHeight(sb.getHeight() + 20);
							}
							break;
						case WEST:
							if (sb.setWidth(sb.getWidth() - (arg0.getX() - oldX))) {
								sb.setX(arg0.getX());
							}
							break;
						case EAST:
							sb.setWidth(arg0.getX() - oldX);
							break;
						case EXTENDEDSOUTH:
							sb.setExtendedHeight(arg0.getY() - oldY);
							break;
						case SOUTHEAST:
							// SOUTH
							if (sb.setHeight(arg0.getY() - oldY)) {
								sb.setExtendedHeight(sb.getHeight() + 20);
							}
							// EAST
							sb.setWidth(arg0.getX() - oldX);
							break;
						case SOUTHWEST:
							// SOUTH
							if (sb.setHeight(arg0.getY() - oldY)) {
								sb.setExtendedHeight(sb.getHeight() + 20);
							}
							// WEST
							if (sb.setWidth(sb.getWidth() - (arg0.getX() - oldX))) {
								sb.setX(arg0.getX());
							}
							break;
						case NORTHEAST:
							// NORTH
							if (sb.setHeight(sb.getHeight() - (arg0.getY() - oldY))) {
								sb.setY(arg0.getY());
								sb.setExtendedHeight(sb.getExtendedHeight() - (arg0.getY() - oldY));
							}
							// EAST
							sb.setWidth(arg0.getX() - oldX);
							break;
						case NORTHWEST:
							// NORTH
							if (sb.setHeight(sb.getHeight() - (arg0.getY() - oldY))) {
								sb.setY(arg0.getY());
								sb.setExtendedHeight(sb.getExtendedHeight() - (arg0.getY() - oldY));
							}
							// WEST
							if (sb.setWidth(sb.getWidth() - (arg0.getX() - oldX))) {
								sb.setX(arg0.getX());
							}
							break;
						default:
							break;
					}
				}
				if (commentRefSelected != 0 && resizeCommentDirection != null) {
					view.setResizing(true);
					Comment c = model.getComment(commentRefSelected);
					isResizing = true;
					int oldX = c.getX();
					int oldY = c.getY();
					switch (resizeCommentDirection) {
						case NORTH:
							if (c.setHeight(c.getHeight() - (arg0.getY() - oldY))) {
								c.setY(arg0.getY());
							}
							break;
						case SOUTH:
							c.setHeight(arg0.getY() - oldY);
							break;
						case WEST:
							if (c.setWidth(c.getWidth() - (arg0.getX() - oldX))) {
								c.setX(arg0.getX());
							}
							break;
						case EAST:
							c.setWidth(arg0.getX() - oldX);
							break;
						case EXTENDEDSOUTH:
							break;
						case SOUTHEAST:
							// SOUTH
							c.setHeight(arg0.getY() - oldY);
							// EAST
							c.setWidth(arg0.getX() - oldX);
							break;
						case SOUTHWEST:
							// SOUTH
							c.setHeight(arg0.getY() - oldY);
							// WEST
							if (c.setWidth(c.getWidth() - (arg0.getX() - oldX))) {
								c.setX(arg0.getX());
							}
							break;
						case NORTHEAST:
							// NORTH
							if (c.setHeight(c.getHeight() - (arg0.getY() - oldY))) {
								c.setY(arg0.getY());
							}
							// EAST
							c.setWidth(arg0.getX() - oldX);
							break;
						case NORTHWEST:
							// NORTH
							if (c.setHeight(c.getHeight() - (arg0.getY() - oldY))) {
								c.setY(arg0.getY());
							}
							// WEST
							if (c.setWidth(c.getWidth() - (arg0.getX() - oldX))) {
								c.setX(arg0.getX());
							}
							break;
						default:
							break;
					}
				}
				if (startUpTaskRefSelected != 0 && resizeStartUpTaskDirection != null) {
					view.setResizing(true);
					StartUpStep sut = model.getStartUpTask(startUpTaskRefSelected);
					isResizing = true;
					int oldX = sut.getX();
					int oldY = sut.getY();
					switch (resizeStartUpTaskDirection) {
						case NORTH:
							if (sut.setHeight(sut.getHeight() - (arg0.getY() - oldY))) {
								sut.setY(arg0.getY());
							}
							break;
						case SOUTH:
							sut.setHeight(arg0.getY() - oldY);
							break;
						case WEST:
							if (sut.setWidth(sut.getWidth() - (arg0.getX() - oldX))) {
								sut.setX(arg0.getX());
							}
							break;
						case EAST:
							sut.setWidth(arg0.getX() - oldX);
							break;
						case EXTENDEDSOUTH:
							break;
						case SOUTHEAST:
							// SOUTH
							sut.setHeight(arg0.getY() - oldY);
							// EAST
							sut.setWidth(arg0.getX() - oldX);
							break;
						case SOUTHWEST:
							// SOUTH
							sut.setHeight(arg0.getY() - oldY);
							// WEST
							if (sut.setWidth(sut.getWidth() - (arg0.getX() - oldX))) {
								sut.setX(arg0.getX());
							}
							break;
						case NORTHEAST:
							// NORTH
							if (sut.setHeight(sut.getHeight() - (arg0.getY() - oldY))) {
								sut.setY(arg0.getY());
							}
							// EAST
							sut.setWidth(arg0.getX() - oldX);
							break;
						case NORTHWEST:
							// NORTH
							if (sut.setHeight(sut.getHeight() - (arg0.getY() - oldY))) {
								sut.setY(arg0.getY());
							}
							// WEST
							if (sut.setWidth(sut.getWidth() - (arg0.getX() - oldX))) {
								sut.setX(arg0.getX());
							}
							break;
						default:
							break;
					}
				}
			}
			view.repaint();

		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			if(model.getStartUpTasks().size()>0){
				for(int key : model.getStartUpTasks().keySet()){
					if(model.getStartUpTask(key).getBounds().contains(arg0.getPoint())){
						view.displayHint(model.getStartUpTask(key));
						view.repaint();
						break;
					} else {
						view.notDisplayHint();
					}
				}
			} else {
				view.notDisplayHint();
			}
			
			
			if (titleSelected && GeneralConfig.titleEnable) {
				resizeTitleDirection = model.getTitleBar().getResizeDirection(new Point(arg0.getX(), arg0.getY()));
				if (resizeTitleDirection != null) {
					switch (resizeTitleDirection) {
						case NORTH:
							view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
							break;
						case SOUTH:
							view.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
							break;
						case WEST:
							if(model.getTitleBar().getStyle()!=Style.LARGE){
								view.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
							} else {
								view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
							}
							break;
						case EAST:
							if(model.getTitleBar().getStyle()!=Style.LARGE){
								view.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
							} else {
								view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
							}
							break;
						case NORTHEAST:
							view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
							break;
						case NORTHWEST:
							view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
							break;
						case SOUTHEAST:
							if(model.getTitleBar().getStyle()!=Style.LARGE){
								view.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
							} else {
								view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
							}
							break;
						case SOUTHWEST:
							if(model.getTitleBar().getStyle()!=Style.LARGE){
								view.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
							} else {
								view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
							}
							break;
						default:
							break;
					}
				} else {
					view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			} else if (sequenceBarRefSelected != 0 /*&& model.getSequence(sequenceBarRef)!=null*/) {
				SequenceBar sb = model.getSequence(sequenceBarRefSelected);
				Rectangle extendedSequenceBarBounds = new Rectangle(sb.getX(), sb.getY(), sb.getWidth(),(int) sb.getExtendedHeight());
				resizeSequenceDirection = model.getSequence(sequenceBarRefSelected).getResizeDirection(new Point(arg0.getX(), arg0.getY()));
				if (resizeSequenceDirection != null && extendedSequenceBarBounds.getBounds().contains(new Point(arg0.getX(), arg0.getY()))) {
					switch (resizeSequenceDirection) {
						case NORTH:
							view.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
							break;
						case SOUTH:
							view.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
							break;
						case WEST:
							view.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
							break;
						case EAST:
							view.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
							break;
						case NORTHEAST:
							view.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
							break;
						case NORTHWEST:
							view.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
							break;
						case SOUTHEAST:
							view.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
							break;
						case SOUTHWEST:
							view.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
							break;
						case EXTENDEDSOUTH:
							view.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
							break;
						default:
							break;
					}
				} else {
					view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			} else if (commentRefSelected != 0) {
				resizeCommentDirection = model.getComment(commentRefSelected).getResizeDirection(new Point(arg0.getX(), arg0.getY()));
				if (resizeCommentDirection != null) {
					switch (resizeCommentDirection) {
						case NORTH:
							view.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
							break;
						case SOUTH:
							view.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
							break;
						case WEST:
							view.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
							break;
						case EAST:
							view.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
							break;
						case NORTHEAST:
							view.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
							break;
						case NORTHWEST:
							view.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
							break;
						case SOUTHEAST:
							view.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
							break;
						case SOUTHWEST:
							view.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
							break;
						case EXTENDEDSOUTH:
							break;
						default:
						    	break;
					}
				} else {
					view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			} else if (startUpTaskRefSelected != 0) {
				resizeStartUpTaskDirection = model.getStartUpTask(startUpTaskRefSelected).getResizeDirection(new Point(arg0.getX(), arg0.getY()));
				if (resizeStartUpTaskDirection != null) {
					switch (resizeStartUpTaskDirection) {
						case NORTH:
							view.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
							break;
						case SOUTH:
							view.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
							break;
						case WEST:
							view.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
							break;
						case EAST:
							view.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
							break;
						case NORTHEAST:
							view.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
							break;
						case NORTHWEST:
							view.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
							break;
						case SOUTHEAST:
							view.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
							break;
						case SOUTHWEST:
							view.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
							break;
						case EXTENDEDSOUTH:
							break;
						default:
						    	break;
					}
				} else {
					view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			}
			view.repaint();
		}
	}

	class ViewKeyListener implements KeyListener {

		
		
		@Override
		public void keyPressed(KeyEvent arg0) {
			if (arg0.getKeyCode() == KeyEvent.VK_CONTROL) {
				ctrlDown = true;
			} else if(arg0.getKeyCode() == KeyEvent.VK_SHIFT){
				shiftDown=true;
				if(!initShiftDown){
					if(milestoneRefSelected!=0){
						model.getSelectedItems().add(model.getMilestone(milestoneRefSelected));
					} else if(sequenceBarRefSelected!=0) {
						model.getSelectedItems().add(model.getSequence(sequenceBarRefSelected));
					} else if(commentRefSelected!=0){
						model.getSelectedItems().add(model.getComment(commentRefSelected));
					} else if(startUpTaskRefSelected!=0){
						model.getSelectedItems().add(model.getStartUpTask(startUpTaskRefSelected));
					}
					initShiftDown=true;
					System.out.println(model.getSelectedItems().size());
				}
				
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			if (arg0.getKeyCode() == KeyEvent.VK_CONTROL) {
				ctrlDown = false;
			} else if(arg0.getKeyCode() == KeyEvent.VK_SHIFT){
				shiftDown = false;
			}
		}
		
		@Override
		public void keyTyped(KeyEvent arg0) {

		}

	}

	class PanelMouseWheelListener implements MouseWheelListener {
		

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			if (ctrlDown) {
				if (e.getWheelRotation() < 0) {
					//ZOOM IN
					if (GeneralConfig.updateForZoom((int) Math.round((GeneralConfig.zoom * GeneralConfig.coeffZoom)))) {
						view.updatePositionForZoom(1);
						view.getDrawPanel().updateUI();
						view.getScrollPane().getHorizontalScrollBar().setValue((int) Math.round(e.getX()*(GeneralConfig.coeffZoom-1)+view.getScrollPane().getHorizontalScrollBar().getValue()));
						view.getScrollPane().getVerticalScrollBar().setValue((int) Math.round(e.getY()*(GeneralConfig.coeffZoom-1)+view.getScrollPane().getVerticalScrollBar().getValue()));
						view.repaint();
					}
				} else if (e.getWheelRotation() > 0) {
					//ZOOM OUT
					if (GeneralConfig.updateForZoom((int) Math.round((GeneralConfig.zoom / GeneralConfig.coeffZoom)))) {
						view.updatePositionForZoom(-1);
						view.getDrawPanel().updateUI();
						view.getScrollPane().getHorizontalScrollBar().setValue((int) Math.round(e.getX()*(1/GeneralConfig.coeffZoom-1)+view.getScrollPane().getHorizontalScrollBar().getValue()));
						view.getScrollPane().getVerticalScrollBar().setValue((int) Math.round(e.getY()*(1/GeneralConfig.coeffZoom-1)+view.getScrollPane().getVerticalScrollBar().getValue()));
						view.repaint();
					}
				}
			} else {
				if (e.getWheelRotation() < 0) {
					view.getScrollPane().getVerticalScrollBar().setValue(view.getScrollPane().getVerticalScrollBar().getValue()-(view.getScrollPane().getVerticalScrollBar().getMaximum()-view.getScrollPane().getVerticalScrollBar().getMinimum())/20);
				} else if (e.getWheelRotation() > 0) {
					view.getScrollPane().getVerticalScrollBar().setValue(view.getScrollPane().getVerticalScrollBar().getValue()+(view.getScrollPane().getVerticalScrollBar().getMaximum()-view.getScrollPane().getVerticalScrollBar().getMinimum())/20);
				}
			}
			view.repaint();
		}
		

	}

	class PanelMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {

		}

		@Override
		public void mouseExited(MouseEvent arg0) {

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		    unselectAllInToolbar();
			view.requestFocusInWindow();
			
			//************************************************************************************\\
			// Right click
			//************************************************************************************\\
			if (arg0.getButton() == MouseEvent.BUTTON3) {
				//Connectors
				//From Milestone
				for(int i : model.getMilestones().keySet()){
					boolean match=false;
					srcIndexM = i;
					destIndexM = new ArrayList<Integer>();
					destIndexS = new ArrayList<Integer>();
					Iterator<JConnector> it = model.getMilestone(i).getConnectors().iterator();
					while(it.hasNext()){
						JConnector dest = it.next();
						if(dest.belongsTo(arg0.getPoint())){
							rightClickOnConnector=true;
							if(dest.getDest().getClass().equals(new Milestone("").getClass())){
								destIndexM.add(Utils.getIndexOf(model.getMilestones(),(Milestone) dest.getDest()));
							} else if (dest.getDest().getClass().equals(new StartUpStep("",0,0,0,0).getClass())){
								destIndexS.add(Utils.getIndexOf(model.getStartUpTasks(),(StartUpStep) dest.getDest()));
							}
							match=true;
							
						}
					}
					if(match){
						srcIndexS=0;
						return ;
					}
				}
				//From Step
					for(int i : model.getStartUpTasks().keySet()){
						boolean match=false;
						srcIndexS = i;
						destIndexM = new ArrayList<Integer>();
						destIndexS = new ArrayList<Integer>();
						Iterator<JConnector> it = model.getStartUpTask(i).getConnectors().iterator();
						while(it.hasNext()){
							JConnector dest = it.next();
							if(dest.belongsTo(arg0.getPoint())){
								rightClickOnConnector=true;
								if(dest.getDest().getClass().equals(new Milestone("").getClass())){
									destIndexM.add(Utils.getIndexOf(model.getMilestones(),(Milestone) dest.getDest()));
								} else if (dest.getDest().getClass().equals(new StartUpStep("",0,0,0,0).getClass())){
									destIndexS.add(Utils.getIndexOf(model.getStartUpTasks(),(StartUpStep) dest.getDest()));
								}
								match=true;
							}
						}	
						if(match){
							srcIndexM=0;
							return ;
						}
					}
				//Sequence Bar
				for (Integer s : view.getModel().getSequences().keySet()) {
					SequenceBar sb = view.getModel().getSequence(s);
					
					if ((sequenceBarRef == 0 || s!=sequenceBarRef) && sb.getBounds().contains(arg0.getPoint())) {
						for(int i : model.getSequences().keySet()){
							model.getSequence(i).setSelected(false);
						}
						sb.setSelected(true);
						xdecal = arg0.getX() - sb.getX();
						ydecal = arg0.getY() - sb.getY();
						sequenceBarRef = s;
						titleSelected = false;
						commentRef = 0;
						milestoneRef = 0;
						milestoneRefSelected=0;
						startUpTaskRef = 0;
					}
				}
				//Milestone 
				for (Integer s : view.getModel().getMilestones().keySet()) {
					Milestone milestone = view.getModel().getMilestone(s);

					if ((milestoneRef == 0 ||s!=milestoneRef)&& milestone.getBounds().contains(arg0.getPoint())) {
						for(int i : model.getMilestones().keySet()){
							model.getMilestone(i).unSelect();
						}
						milestone.select();
						xdecal = arg0.getX() - milestone.getX();
						ydecal = arg0.getY() - milestone.getY();
						milestoneRef = s;
						milestoneRefSelected =s;
						titleSelected = false;
						sequenceBarRef = 0;
						commentRef = 0;
						startUpTaskRef = 0;
					}
				}
				
				//Start Up Task
				for (Integer s : view.getModel().getStartUpTasks().keySet()) {
					StartUpStep sut = view.getModel().getStartUpTask(s);

					if ((startUpTaskRef == 0 || s!=startUpTaskRef)&& sut.getBounds().contains(arg0.getPoint())) {
						for(int i : model.getStartUpTasks().keySet()){
							model.getStartUpTask(i).setSelected(false);
						}
						sut.setSelected(true);
						xdecal = arg0.getX() - sut.getX();
						ydecal = arg0.getY() - sut.getY();
						startUpTaskRef = s;
						titleSelected = false;
						sequenceBarRef = 0;
						commentRef = 0;
						milestoneRef = 0;
						milestoneRefSelected =0;
					}
				}
				
				//Comments
				for (Integer s : view.getModel().getComments().keySet()) {
					Comment comm = view.getModel().getComment(s);

					if ((commentRef == 0 || s!=commentRef)&& comm.getBounds().contains(arg0.getPoint())) {
						for(int i : model.getComments().keySet()){
							model.getComment(i).setSelected(false);
						}
						comm.setSelected(true);
						xdecal = arg0.getX() - comm.getX();
						ydecal = arg0.getY() - comm.getY();
						commentRef = s;
						titleSelected = false;
						sequenceBarRef = 0;
						milestoneRef = 0;
						milestoneRefSelected=0;
						startUpTaskRef = 0;
					}
				}
				
				
				
			}
			//************************************************************************************\\
			// Left click
			//************************************************************************************\\
			else if (arg0.getButton() == MouseEvent.BUTTON1) {
				view.getGeneralRightClickMenu().setVisible(false);
				view.getMilestoneRightClickMenu().setVisible(false);
				view.getConnectorRightClickMenu(-1, null,-1,null, false).setVisible(false);//it put invisible itself
				view.getSequenceBarRightClickMenu().setVisible(false);
				view.getCommentRightClickMenu().setVisible(false);
				view.getStartUpTaskRightClickMenu().setVisible(false);
				leftClick = true;
				if (cisorsButton){
					int index=-1;
					for(int i : model.getMilestones().keySet()){
						index=0;
						Iterator<JConnector> it = model.getMilestone(i).getConnectors().iterator();
						while(it.hasNext()){
							if(it.next().belongsTo(arg0.getPoint())){
								model.getMilestone(i).getDestMilestone().remove(index);
								view.repaint();
							}
							index++;
						}
					}
					return ;
				}
				/*if(newMilestone){
					Milestone m = new Milestone("New Milestone");
					model.addMilestone(m);
					m.setX(arg0.getX()-GeneralConfig.milestoneWidth/2);m.setY(arg0.getY()-GeneralConfig.milestoneHeight/2);
					MilestoneEditorDialog v = new MilestoneEditorDialog(m, view.getWidth(), view.getHeight());
					v.setLocation(view.getWidth()/2-v.getWidth()/2, view.getHeight()/2-v.getHeight()/2);
					MilestoneEditorController c = new MilestoneEditorController(v, m, controller);
					c.addRepaintListener(new RepaintOnActionListener());
					unselectAllInToolbar();
					return ;
				} else if(newSequence){
					SequenceBar sb = new SequenceBar("New Sequence", arg0.getX()-200, arg0.getY()-20, 400, 40, 100);
					ibView = new SequenceBarEditorView(new Point(view.getWidth()/2,view.getHeight()/2), sb);
					ibController = new SequenceBarEditorController(sb, ibView);
					ibView.addColorChooserChangeListener(new ColorChooserChangeListener());
					ibView.setLocation(view.getWidth()/2-ibView.getWidth()/2, view.getHeight()/2-ibView.getHeight()/2);
					ibView.addRepaintListener(new RepaintOnActionListener());
					model.addSequence(sb);
					unselectAllInToolbar();
					return ;
				} else if (newComment){
					Comment c = new Comment("Write your note here", arg0.getX()-75, arg0.getY()-75, 150, 100);
					ibView = new CommentEditorView(new Point(view.getWidth()/2,view.getHeight()/2), c);
					ibController = new InteractiveBarEditorController(c, ibView);
					ibView.setLocation(view.getWidth()/2-ibView.getWidth()/2, view.getHeight()/2-ibView.getHeight()/2);
					model.addComment(c);
					unselectAllInToolbar();
					return ;
				}*/
				// Start Up Task detection
				for (int i : model.getStartUpTasks().keySet()) {
					StartUpStep sut = model.getStartUpTask(i);
					if (startUpTaskRef == 0 && sut.getBounds().contains(new Point(arg0.getX(), arg0.getY()))) {
						xdecal = arg0.getX() - sut.getX();
						ydecal = arg0.getY() - sut.getY();
						startUpTaskRef = i;
						startUpTaskRefSelected = startUpTaskRef;
						commentRef = 0;
						commentRefSelected = 0;
						sequenceBarRef=0;
						sequenceBarRefSelected=0;
						milestoneRefSelected=0;
						sut.setSelected(sut.isSelected());
						titleSelected = false;
						willMove = true;
						resizeTitleDirection = null;
						if(shiftDown&&!model.getSelectedItems().contains(startUpTaskRefSelected)){
							model.getSelectedItems().add(model.getStartUpTask(startUpTaskRefSelected));
							model.getStartUpTask(startUpTaskRefSelected).setSelected(!model.getStartUpTask(startUpTaskRefSelected).isSelected());
							startUpTaskRefSelected=0;
						}
					}
				}
					
				// Resizing detection
				for (int i : model.getStartUpTasks().keySet()) {
					if (model.getStartUpTask(i).getResizeDirection(new Point(arg0.getX(), arg0.getY())) != null) {
						isResizing = true;
					}
				}
				
				// Comment detection
				if (startUpTaskRef == 0) {
					for (int i : model.getComments().keySet()) {
						Comment c = model.getComment(i);
						if (commentRef == 0 && c.getBounds().contains(new Point(arg0.getX(), arg0.getY()))) {
							xdecal = arg0.getX() - c.getX();
							ydecal = arg0.getY() - c.getY();
							commentRef = i;
							commentRefSelected = commentRef;
							sequenceBarRef = 0;
							sequenceBarRefSelected = 0;
							startUpTaskRef=0;
							startUpTaskRefSelected=0;
							milestoneRefSelected=0;
							c.setSelected(c.isSelected());
							titleSelected = false;
							willMove = true;
							resizeTitleDirection = null;
							
							if(shiftDown&&!model.getSelectedItems().contains(commentRefSelected)){
								model.getSelectedItems().add(model.getComment(commentRefSelected));
								c.setSelected(!c.isSelected());
								commentRefSelected=0;
							}
						}
					}
					// Resizing detection
					for (int i : model.getComments().keySet()) {
						if (model.getComment(i).getResizeDirection(new Point(arg0.getX(), arg0.getY())) != null) {
							isResizing = true;
						}
					}
					
					if(editButton==true&&commentRef!=0&&isResizing==false){
						ibModel = model.getComment(commentRef);
						ibView = new CommentEditorView(new Point(view.getWidth()/2,view.getHeight()/2), ibModel);
						ibController = new InteractiveBarEditorController(ibModel, ibView);
						ibView.addColorChooserChangeListener(new ColorChooserChangeListener());
						commentRef=0;
						ibController.setEnable(true);
						return ;
					} else if(deleteButton==true&&commentRef!=0&&isResizing==false){
						model.getComments().remove(commentRef);
						view.repaint();
						commentRef=0;
						return ;
					}
				}
				// Milestone detection
				if (startUpTaskRef == 0 && commentRef == 0) {
					for (Integer s : model.getMilestones().keySet()) {
						Milestone milestone = view.getModel().getMilestone(s);
						if (milestoneRef == 0 && milestone.getBounds().contains(arg0.getPoint())) {

							xdecal = arg0.getX() - milestone.getX();
							ydecal = arg0.getY() - milestone.getY();
							milestoneRef = s;
							milestoneRefSelected = s;
							sequenceBarRef = 0;
							commentRef = 0;
							startUpTaskRef=0;
							sequenceBarRefSelected=0;
							titleSelected = false;
							willMove = true;
							resizeSequenceDirection = null;
							resizeTitleDirection = null;
							resizeCommentDirection = null;
							
							if(shiftDown&&!model.getSelectedItems().contains(milestoneRefSelected)){
								model.getSelectedItems().add(milestone);
								milestone.select(!milestone.isSelected());
								milestoneRefSelected=0;
							}
							
							/*if(editButton==true){
								Milestone m = model.getMilestone(milestoneRef);
								MilestoneEditorFrame v = new MilestoneEditorFrame(m,view.getWidth()/2,view.getHeight()/2);
								MilestoneEditorController c = new MilestoneEditorController(v, m, controller);
								c.setEnable(true);
								milestoneRef=0;
								return ;
							} else if(deleteButton==true){
								model.getMilestones().remove(milestoneRef);
								view.repaint();
								milestoneRef=0;
								return ;
							}*/
						}
					}
				}
				
				

				// Sequence detection
				if (startUpTaskRef == 0 && milestoneRef == 0 && commentRef == 0) {
					for (int i : model.getSequences().keySet()) {
						SequenceBar sb = model.getSequence(i);
						if (sequenceBarRef == 0 && sb.getBounds().contains(new Point(arg0.getX(), arg0.getY()))) {
							xdecal = arg0.getX() - sb.getX();
							ydecal = arg0.getY() - sb.getY();
							sequenceBarRef = i;
							sequenceBarRefSelected = sequenceBarRef;
							commentRef = 0;
							commentRefSelected = 0;
							startUpTaskRef=0;
							startUpTaskRefSelected=0;
							milestoneRefSelected=0;
							sb.setSelected(sb.isSelected());
							titleSelected = false;
							willMove = true;
							resizeTitleDirection = null;
							if(shiftDown&&!model.getSelectedItems().contains(sequenceBarRefSelected)){
								model.getSelectedItems().add(sb);
								sb.setSelected(!sb.isSelected());
								sequenceBarRefSelected=0;
							}
						}
					}
					// Resizing detection
					for (int i : model.getSequences().keySet()) {
						if (model.getSequence(i).getResizeDirection(new Point(arg0.getX(), arg0.getY())) != null) {
							isResizing = true;
						}
					}
					
					/*if(editButton==true&&!isResizing&&sequenceBarRef!=0){
						ibModel = model.getSequence(sequenceBarRef);
						ibView = new SequenceBarEditorView(new Point(view.getWidth()/2,view.getHeight()/2), ibModel);
						ibController = new SequenceBarEditorController(ibModel, ibView);
						ibView.addColorChooserChangeListener(new ColorChooserChangeListener());
						sequenceBarRef=0;
						return ;
					} else if(deleteButton==true&&!isResizing&&sequenceBarRef!=0){
						model.getSequences().remove(sequenceBarRef);
						sequenceBarRef=0;
						view.repaint();
						return ;
					}*/
				}
				
				
					/*if(editButton==true&&!isResizing&&startUpTaskRef!=0){
						ibModel = model.getSequence(startUpTaskRef);
						ibView = new SequenceBarEditorView(new Point(view.getWidth()/2,view.getHeight()/2), ibModel);
						ibController = new SequenceBarEditorController(ibModel, ibView);
						ibView.addColorChooserChangeListener(new ColorChooserChangeListener());
						sequenceBarRef=0;
						return ;
					} else if(deleteButton==true&&!isResizing&&sequenceBarRef!=0){
						model.getSequences().remove(sequenceBarRef);
						sequenceBarRef=0;
						view.repaint();
						return ;
					}*/

				// Title bar detection
				if (milestoneRef == 0 && sequenceBarRef == 0 && commentRef == 0 && GeneralConfig.titleEnable && startUpTaskRef==0) {
					if (model.getTitleBar().getBounds().contains(new Point(arg0.getX(), arg0.getY()))) {
						titleSelected = true;
						milestoneRef = 0;
						sequenceBarRef = 0;
						resizeSequenceDirection = null;
					}
					if(titleSelected&&editButton==true){
						ibModel = model.getTitleBar();
						ibView = new InteractiveBarEditorView(new Point(view.getWidth()/2,view.getHeight()/2), ibModel);
						ibController = new InteractiveBarEditorController(ibModel, ibView);
						ibView.addColorChooserChangeListener(new ColorChooserChangeListener());
						return ;
					}

				}
				// Void click detection
				if (detectMilestone(arg0.getX(), arg0.getY()) == null && detectSequenceBar(arg0.getX(), arg0.getY()) == null && detectComment(arg0.getX(), arg0.getY()) == null && detectStartUpTask(arg0.getX(), arg0.getY()) == null) {
					sequenceBarRef = 0;
					sequenceBarRefSelected=0;
					startUpTaskRef =0;
					startUpTaskRefSelected=0;
					milestoneRef = 0;
					milestoneRefSelected =0;
					commentRef = 0;
					commentRefSelected=0;
					resizeSequenceDirection = null;
					if (!model.getTitleBar().getBounds().contains(arg0.getPoint())) {
						titleSelected = false;
					}
					if(!shiftDown){
						model.getSelectedItems().clear();
						initShiftDown=false;
					}
					view.repaint();
				}

				// To scroll by keeping the left click down
				previousX = arg0.getX();
				previousY = arg0.getY();
				/////////////////////////////////////////////////////////////////////////
				// Implementation of double click                                      //
				/////////////////////////////////////////////////////////////////////////
				if (arg0.getClickCount() > 1) {
					// double click on title bar
					if (model.getTitleBar().getBounds().contains(new Point(arg0.getX(), arg0.getY())) && titleSelected && GeneralConfig.titleEnable) {
						
						ibModel = model.getTitleBar();
						ibView = new InteractiveBarEditorView(new Point(arg0.getXOnScreen(), arg0.getYOnScreen()), ibModel);
						ibController = new InteractiveBarEditorController(ibModel, ibView);
						ibView.addColorChooserChangeListener(new ColorChooserChangeListener());
						ibView.setLocation(view.getWidth()/2-ibView.getWidth()/2, view.getHeight()/2-ibView.getHeight()/2);
						ibView.addRepaintListener(new RepaintOnActionListener());
						// add
					}
					// double click on milestone
					if (milestoneRef != 0) {
						Milestone m = model.getMilestone(milestoneRef);
						MilestoneEditorFrame v = new MilestoneEditorFrame(m,arg0.getXOnScreen()-m.getWidth()/2,arg0.getYOnScreen()-m.getHeight()/2);
						v.setLocation(view.getWidth()/2-v.getWidth()/2, view.getHeight()/2-v.getHeight()/2);
						MilestoneEditorController c = new MilestoneEditorController(v, m, controller);
						c.addRepaintListener(new RepaintOnActionListener());
					}
					// double click on sequence bar
					if (sequenceBarRef != 0) {
						ibModel = model.getSequence(sequenceBarRef);
						ibView = new SequenceBarEditorView(new Point(arg0.getXOnScreen(), arg0.getYOnScreen()), ibModel);
						ibView.setLocation(view.getWidth()/2-ibView.getWidth()/2, view.getHeight()/2-ibView.getHeight()/2);
						ibController = new SequenceBarEditorController(ibModel, ibView);
						ibView.addColorChooserChangeListener(new ColorChooserChangeListener());
						ibView.addRepaintListener(new RepaintOnActionListener());
					}
					// double click on comment
					if (commentRef != 0) {
						ibModel = model.getComment(commentRef);
						ibView = new CommentEditorView(new Point(arg0.getXOnScreen(), arg0.getYOnScreen()), ibModel);
						ibView.setLocation(view.getWidth()/2-ibView.getWidth()/2, view.getHeight()/2-ibView.getHeight()/2);
						ibController = new InteractiveBarEditorController(ibModel, ibView);
						ibView.addColorChooserChangeListener(new ColorChooserChangeListener());
						ibView.addRepaintListener(new RepaintOnActionListener());
					}
					
					// double click on start up task
					if (startUpTaskRef != 0) {
						StepEditorView sev = new StepEditorView(model.getStartUpTask(startUpTaskRef));
						sev.setVisible(true);
						/*ibModel = model.getStartUpTask(startUpTaskRef);
						ibView = new InteractiveBarEditorView(new Point(arg0.getXOnScreen(), arg0.getYOnScreen()), ibModel);
						ibController = new InteractiveBarEditorController(ibModel, ibView);
						ibView.addColorChooserChangeListener(new ColorChooserChangeListener());
						ibView.setLocation(view.getWidth()/2-ibView.getWidth()/2, view.getHeight()/2-ibView.getHeight()/2);
						ibView.addRepaintListener(new RepaintOnActionListener());*/
					}
					
					// double click on project title block
					if(GeneralConfig.titleBlockEnable&&model.getProjectTitleBlock().belongsTo(arg0.getPoint())=="titleBlock"){
						ProjectTitleBlockView ptbv = new ProjectTitleBlockView(model.getProjectTitleBlock());
						ProjectTitleBlockController ptbc = new ProjectTitleBlockController(ptbv, model.getProjectTitleBlock(), view);
						ptbc.setVisible(true);
						ptbv.setLocationRelativeTo(view);
					} else if(GeneralConfig.titleBlockEnable&&model.getProjectTitleBlock().belongsTo(arg0.getPoint())=="revision"){
						RevisionView rv = new RevisionView(model.getRevisions());
						rv.setVisible(true);
						rv.setLocationRelativeTo(view);
					}
					view.repaint();
				}

				// unselect all sequences before selecting one
				if(!shiftDown&&model.getSelectedItems().size()<1){
					for (Integer s : view.getModel().getSequences().keySet()) {
						if (s != sequenceBarRef)
							view.getModel().getSequence(s).setSelected(false);
					}
					if (sequenceBarRef != 0){
						view.getModel().getSequence(sequenceBarRef).setSelected(true);
						view.repaint();
					}
					
					// unselect all start up tasks before selecting one
					for (Integer s : view.getModel().getStartUpTasks().keySet()) {
						if (s != startUpTaskRef)
							view.getModel().getStartUpTask(s).setSelected(false);
					}
					if (startUpTaskRef != 0){
						view.getModel().getStartUpTask(startUpTaskRef).setSelected(true);
						view.repaint();
					}
					
					// unselect all comments before selecting one
					for (Integer s : view.getModel().getComments().keySet()) {
						if (s != commentRef)
							view.getModel().getComment(s).setSelected(false);
					}
					if (commentRef != 0){
						view.getModel().getComment(commentRef).setSelected(true);
						view.repaint();
					}
	
					// unselect all milestones before selecting one
					for (Integer s : view.getModel().getMilestones().keySet()) {
						if (s != milestoneRef)
							view.getModel().getMilestone(s).unSelect();
					}
					if (milestoneRef != 0){
						view.getModel().getMilestone(milestoneRef).select();
						view.repaint();
					}
				}
			}
			view.repaint();
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			view.setResizing(false);
			
			System.out.println(model.getSelectedItems().size());
			
			int x = arg0.getX();
			int y = arg0.getY();
			if(arg0.getButton() == MouseEvent.BUTTON1&&!editButton&&!deleteButton&&!newComment&&!newSequence&&!newMilestone) {
				leftClick = false;
				if (willMove) {
					willMove = false;
					if(!shiftDown&&model.getSelectedItems().size()>0){
						int dx = x-previousX;
						int dy = y-previousY;
						Iterator<MovableItem> it = model.getSelectedItems().iterator();
						while(it.hasNext()){
							it.next().move(dx, dy);
						}
					}
					else {
						if (milestoneRef != 0) {
							view.getModel().getMilestone(milestoneRef).setX(x - xdecal);
							view.getModel().getMilestone(milestoneRef).setY(y - ydecal);
							if (connect) {
        							if(!connectToRefM.contains(milestoneRef))
        							    connectToRefM.add(milestoneRef);
        							else
        							    connectToRefM.remove(milestoneRef);
								model.getMilestone(milestoneRef).setSelectedForConnection(!model.getMilestone(milestoneRef).isSelectedForConnection());
							}
						}
						if (commentRef != 0 && !isResizing) {
							model.getComment(commentRef).setX(x - xdecal);
							model.getComment(commentRef).setY(y - ydecal);
							view.repaint();
						}
						
						if (sequenceBarRef != 0 && !isResizing) {
							
							model.getSequence(sequenceBarRef).setX(x - xdecal);
							model.getSequence(sequenceBarRef).setY(y - ydecal);
							//view.sequenceBarAlignement(model.getSequence(sequenceBarRef));
						}
						
						if (startUpTaskRef != 0 && !isResizing) {
							model.getStartUpTask(startUpTaskRef).setX(x - xdecal);
							model.getStartUpTask(startUpTaskRef).setY(y - ydecal);
							//view.sequenceBarAlignement(model.getSequence(sequenceBarRef));
							if (connect) {
								if(!connectToRefSUT.contains(startUpTaskRef))
								    connectToRefSUT.add(startUpTaskRef);
								else
								    connectToRefSUT.remove(startUpTaskRef);
								model.getStartUpTask(startUpTaskRef).setSelectedForConnection(!model.getStartUpTask(startUpTaskRef).isSelectedForConnection());
							}
						}
					}
					view.repaint();
				}
				previousX = 0;
				previousY = 0;
				view.getDrawPanel().notAnyMoreMoving();
				milestoneRef = 0;
				sequenceBarRef = 0;
				commentRef = 0;
				startUpTaskRef = 0;
				// Out of all D&D
				if (previousX != 0 && previousY != 0) {
					previousX = 0;
					previousY = 0;
				}
				
			}
			view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			// Liberate the resizing
			isResizing = false;
			//view.followTheGrid();
			view.repaint();
			
		}
	}

	class ToolBarMouseListener implements MouseListener {
		private String select = "";

		@Override
		public void mouseClicked(MouseEvent arg0) {
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {

		}

		@Override
		public void mouseExited(MouseEvent arg0) {

		}

		@Override
		public void mousePressed(MouseEvent arg0) {

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			if (arg0.getX() > view.getToolBar().getWidth() && arg0.getY() > 0) {
				if (select == "m") {
					model.addMilestone(new Milestone("Unnamed-" + model.getMilestones().size(), arg0.getX() - view.getToolBar().getWidth() - GeneralConfig.milestoneWidth / 2, arg0.getY()));
				}

				view.followTheGrid();
				view.sequenceBarAlignement();
				view.startUpTaskAlignement();
				view.repaint();
			}
			view.repaint();
		}
	}

	class RightClickMenuListener extends MouseAdapter {
		
		@Override
		public void mousePressed(MouseEvent arg0) {
			if (arg0.isPopupTrigger()) {
				//doPop(arg0);
			}
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			if (arg0.isPopupTrigger()) {
				doPop(arg0);
			}
		}

		private void doPop(MouseEvent e) {
			if (milestoneRef != 0) {
				view.getMilestoneRightClickMenu().setLocation(e.getXOnScreen(), e.getYOnScreen());
				view.getMilestoneRightClickMenu().setVisible(!view.getMilestoneRightClickMenu().isVisible());
			} else if (sequenceBarRef != 0) {
				view.getSequenceBarRightClickMenu().setLocation(e.getXOnScreen(), e.getYOnScreen());
				view.getSequenceBarRightClickMenu().setVisible(!view.getSequenceBarRightClickMenu().isVisible());
			} else if (commentRef != 0) {
				view.getCommentRightClickMenu().setLocation(e.getXOnScreen(), e.getYOnScreen());
				view.getCommentRightClickMenu().setVisible(!view.getCommentRightClickMenu().isVisible());
			} else if (startUpTaskRef != 0) {
				if(!model.getStartUpTask(startUpTaskRef).isOperated()){
					view.getOperatedMenuItem().setText("Done");
				} else {
					view.getOperatedMenuItem().setText("Undone");
				}
				view.getStartUpTaskRightClickMenu().setLocation(e.getXOnScreen(), e.getYOnScreen());
				view.getStartUpTaskRightClickMenu().setVisible(!view.getStartUpTaskRightClickMenu().isVisible());
			}else if(rightClickOnConnector){
				view.getConnectorRightClickMenu(srcIndexM,destIndexM,srcIndexS,destIndexS,true).setLocation(e.getXOnScreen(), e.getYOnScreen());
				view.getConnectorRightClickMenu(0,null,0,null,false).setVisible(!view.getConnectorRightClickMenu(0,null,0,null,false).isVisible());
			} else if(milestoneRef==0&&sequenceBarRef==0&&!rightClickOnConnector&&startUpTaskRef==0&&!titleSelected){
				whereRightClickHappenX=e.getX();
				whereRightClickHappenY=e.getY();
				view.getGeneralRightClickMenu().setLocation(e.getXOnScreen(), e.getYOnScreen());
				view.getGeneralRightClickMenu().setVisible(!view.getGeneralRightClickMenu().isVisible());
			}
		}
	}

	class EditMenuItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			/*
			 * if(milestoneRef!=0){ Milestone m =
			 * model.getMilestone(milestoneRef); MilestoneEditorView v = new
			 * MilestoneEditorView(m, m.getX() + view.getX(), m.getY() +
			 * view.getY()); MilestoneEditorController c = new
			 * MilestoneEditorController(v, m);
			 * view.getMilestoneRightClickMenu().setVisible(false); milestoneRef
			 * = 0; }
			 */
			// milestone
			if (milestoneRef != 0) {
				Milestone m = model.getMilestone(milestoneRef);
				MilestoneEditorFrame v = new MilestoneEditorFrame(m,view.getWidth()/2,view.getHeight()/2);
				MilestoneEditorController c = new MilestoneEditorController(v, m, controller);
				view.getMilestoneRightClickMenu().setVisible(false);
				c.setEnable(true);
			}
			// sequence bar
			if (sequenceBarRef != 0) {
				ibModel = model.getSequence(sequenceBarRef);
				ibView = new SequenceBarEditorView(new Point(view.getWidth()/2,view.getHeight()/2), ibModel);
				ibController = new SequenceBarEditorController(ibModel, ibView);
				ibView.addColorChooserChangeListener(new ColorChooserChangeListener());
				view.getSequenceBarRightClickMenu().setVisible(false);
				ibController.setEnable(true);
			}
			// start up task
			if (startUpTaskRef != 0) {
				ibModel = model.getStartUpTask(startUpTaskRef);
				ibView = new InteractiveBarEditorView(new Point(view.getWidth()/2,view.getHeight()/2), ibModel);
				ibController = new InteractiveBarEditorController(ibModel, ibView);
				ibView.addColorChooserChangeListener(new ColorChooserChangeListener());
				view.getStartUpTaskRightClickMenu().setVisible(false);
				ibController.setEnable(true);
			}
			// double click on comment
			if (commentRef != 0) {
				ibModel = model.getComment(commentRef);
				ibView = new CommentEditorView(new Point(view.getWidth()/2,view.getHeight()/2), ibModel);
				ibController = new InteractiveBarEditorController(ibModel, ibView);
				ibView.addColorChooserChangeListener(new ColorChooserChangeListener());
				view.getCommentRightClickMenu().setVisible(false);
				ibController.setEnable(true);
			}
			view.repaint();
		}

	}

	class DeleteMenuItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (milestoneRef != 0) {
				// suppress all input connectors from Milestones
				for (Integer i : model.getMilestones().keySet()) {
					if (model.getMilestone(i).getDestMilestone().contains(model.getMilestone(milestoneRef))) {
						model.getMilestone(i).getDestMilestone().remove(model.getMilestone(milestoneRef));
					}
				}
				// suppress all input connectors from Steps
				for (Integer i : model.getStartUpTasks().keySet()) {
					if (model.getStartUpTask(i).getDestMilestone().contains(model.getMilestone(milestoneRef))) {
						model.getStartUpTask(i).getDestMilestone().remove(model.getMilestone(milestoneRef));
					}
				}
				model.getMilestones().remove(milestoneRef);
				view.getMilestoneRightClickMenu().setVisible(false);
				milestoneRef = 0;
				milestoneRefSelected =0;
			} else if (sequenceBarRef != 0) {
				model.getSequences().remove(sequenceBarRef);
				view.getSequenceBarRightClickMenu().setVisible(false);
				sequenceBarRef = 0;
				sequenceBarRefSelected = 0;
			} else if (commentRef != 0) {
				model.getComments().remove(commentRef);
				view.getCommentRightClickMenu().setVisible(false);
				commentRef = 0;
				commentRefSelected = 0;
			} else if (startUpTaskRef != 0) {
				// suppress all input connectors from Milestones
				for (Integer i : model.getMilestones().keySet()) {
					if (model.getMilestone(i).getDestSUT().contains(model.getStartUpTask(startUpTaskRef))) {
						model.getMilestone(i).getDestSUT().remove(model.getStartUpTask(startUpTaskRef));
					}
				}
				// suppress all input connectors from Steps
				for (Integer i : model.getStartUpTasks().keySet()) {
					if (model.getStartUpTask(i).getDestSUT().contains(model.getStartUpTask(startUpTaskRef))) {
						model.getStartUpTask(i).getDestSUT().remove(model.getStartUpTask(startUpTaskRef));
					}
				}
				model.getStartUpTasks().remove(startUpTaskRef);
				view.getStartUpTaskRightClickMenu().setVisible(false);
				startUpTaskRef = 0;
				startUpTaskRefSelected = 0;
			}
			view.repaint();
		}
		
		public void deleteInputConnection(Object o){
			
		}

	}
	
	class OperatedSUTListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(startUpTaskRef!=0){
				model.getStartUpTask(startUpTaskRef).setOperated(!model.getStartUpTask(startUpTaskRef).isOperated());
				view.getStartUpTaskRightClickMenu().setVisible(false);
			}
		}
		
	}

	class ConnectMenuItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			if (milestoneRef!=0 && model.getMilestone(milestoneRef).isSelected()) {
				connectFromRefM = milestoneRef;
				model.getMilestone(connectFromRefM).setSelectedForConnection(true);
				connect = true;
				connectToRefM = new ArrayList<Integer>();
				connectToRefSUT = new ArrayList<Integer>();
				view.setMode(Mode.CONNECTION);
				view.getMilestoneRightClickMenu().setVisible(false);
				milestoneRef = 0;
				milestoneRefSelected =0;
				
			} else if(model.getStartUpTask(startUpTaskRef).isSelected()){
				connectFromRefSUT=startUpTaskRef;
				model.getStartUpTask(connectFromRefSUT).setSelectedForConnection(true);
				connect = true;
				connectToRefM = new ArrayList<Integer>();
				connectToRefSUT = new ArrayList<Integer>();
				view.setMode(Mode.CONNECTION);
				view.getStartUpTaskRightClickMenu().setVisible(false);
				milestoneRef = 0;
				milestoneRefSelected =0;
			}
			view.repaint();
		}

	}

	class FinalizeConnectionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(connectFromRefM!=0){
				Milestone src = model.getMilestone(connectFromRefM);
				Iterator<Integer> it = connectToRefM.iterator();
				while (it.hasNext()) {
					int next = it.next();
					src.addDest(model.getMilestone(next));
				}
				Iterator<Integer> itt = connectToRefSUT.iterator();
				while (itt.hasNext()) {
					int next = itt.next();
					src.addDestSUT(model.getStartUpTask(next));
				}
			} else if(connectFromRefSUT!=0){
				StartUpStep src = model.getStartUpTask(connectFromRefSUT);
				Iterator<Integer> it = connectToRefM.iterator();
				while (it.hasNext()) {
					int next = it.next();
					src.addDest(model.getMilestone(next));
				}
				Iterator<Integer> itt = connectToRefSUT.iterator();
				while (itt.hasNext()) {
					int next = itt.next();
					src.addDestSUT(model.getStartUpTask(next));
				}
			}
			
			for (int i : model.getMilestones().keySet()) {
				model.getMilestone(i).setSelectedForConnection(false);
			}
			for (int i : model.getStartUpTasks().keySet()) {
				model.getStartUpTask(i).setSelectedForConnection(false);
			}
			
			connectFromRefSUT=0;
			connectFromRefM=0;
			view.setMode(Mode.NORMAL);
			connect = false;
			
			view.repaint();
		}

	}
	
	class CancelConnectionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(connectFromRefM!=0)
				model.getMilestone(connectFromRefM).setSelectedForConnection(false);
			if(connectFromRefSUT!=0)
				model.getStartUpTask(connectFromRefSUT).setSelectedForConnection(false);;
			connectFromRefM = 0;
			connectFromRefSUT = 0;
			connect = false;
			connectToRefM.clear();
			connectToRefSUT.clear();
			view.getMilestoneRightClickMenu().setVisible(false);
			view.getStartUpTaskRightClickMenu().setVisible(false);
			milestoneRef = 0;
			milestoneRefSelected =0;
			view.setMode(Mode.NORMAL);
			view.repaint();
		}
		
	}

	class ColorChooserChangeListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent arg0) {
			Color c = ibView.getColorChooser().getColor();
			if (titleSelected) {
				model.getTitleBar().setColor(c);
				view.repaint();
			} else if (sequenceBarRefSelected != 0 && model.getSequence(sequenceBarRef)!=null) {
				SequenceBar bar = model.getSequence(sequenceBarRefSelected);
				bar.setColor(c);
				Color dlc = ((SequenceBarEditorView) ibView).getDashedLineColor();
				bar.setDottedLineColor(dlc);
				view.repaint();
			} else if (commentRefSelected != 0) {
				Comment comment = model.getComment(commentRefSelected);
				comment.setColor(c);
				view.repaint();
			}
			view.repaint();
		}

	}

	
	
	//Update all button
	/**
	 * @author S.Trémouille
	 * Listener implementing UpdateAllMilestone behavior
	 *
	 */
	public class UpdateButtonListener implements ActionListener {
		JFrame f;
		JButton cancel;
		@Override
		public void actionPerformed(ActionEvent arg0) {
		    if(GeneralConfig.databases.size()>0){
			Iterator<Database> it = GeneralConfig.databases.iterator();
			boolean tmp1 = false;
			while(it.hasNext()){
			    if(it.next().isActivated()){
				tmp1=true;
			    }
			}
			if(tmp1){
        		    	view.setEnabled(false);
        		    	final TreeMap<Integer, Milestone> tmp = Utils.duplicateMilestoneMap(model.getMilestones());
        				f = new JFrame();
        				cancel = new JButton("Stop Update");
        				final JProgressBar p = new JProgressBar(0,100);
        				f.setTitle("Update in progress (Requesting on "+GeneralConfig.databases.size()+" databases)");
        				f.setIconImage(Toolkit.getDefaultToolkit().getImage(TargetView.class.getResource("/img/icone.png")));
        				f.add(p);
        				f.add(cancel,BorderLayout.SOUTH);
        				f.pack();
        				f.setSize(400, f.getSize().height);
        				f.setVisible(true);
        				f.setLocationRelativeTo(view);
        				final Executor executor = Executors.newCachedThreadPool();
        				//final UpdateAllMilestoneWorker uamw = new UpdateAllMilestoneWorker(model, this);
        				final UpdateAllMilestoneWorkerOptimised uamw = new UpdateAllMilestoneWorkerOptimised(model, this);
        				executor.execute(uamw);
        				f.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        				WindowListener exitListener = new WindowAdapter() {
        					
        					@Override
        					public void windowClosing(WindowEvent e){
        						uamw.cancel(true);
        						model.setMiletones(tmp);
        						f.dispose();
        						view.setEnabled(true);
        					}
        					
        				};
        				f.addWindowListener(exitListener);
        				uamw.addPropertyChangeListener(new PropertyChangeListener() {
        					
        					@Override
        					public void propertyChange(PropertyChangeEvent e) {
        						if(e.getPropertyName()=="progress"){
        							p.setValue((Integer) e.getNewValue());
        						}
        					}
        				});
        				cancel.addActionListener(new ActionListener() {
        				    
        				    @Override
        				    public void actionPerformed(ActionEvent arg0) {
        					uamw.cancel(true);
        					model.setMiletones(tmp);
        					f.dispose();
        					view.setEnabled(true);
        					view.toFront();
        				    }
        				});
        			} else {
        				JOptionPane.showMessageDialog(view, "Please specify a path for F.O.Documentation (Settings -> Preferences \n -> Start-Up Documentation File Location)");
        				view.setEnabled(true);
        			}
        			view.repaint();
			} else {
			    JOptionPane.showMessageDialog(view, "No connection activated");
			}
        			
		}

		/**
		 * Notify when work is ended
		 */
		public void notifyFinish() {
			f.dispose();
			view.repaint();
			view.setEnabled(true);
			view.toFront();
		}

	}

	class PrintMenuItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int previousZoom = (int) GeneralConfig.zoom;
			adaptZoom(133);
			GeneralConfig.printMode = true;
			model.unselectAll();

			//Place where pdf is created
			JFileChooser jfcForPDF = new JFileChooser();
			jfcForPDF.setLocale(Locale.getDefault());
			jfcForPDF.updateUI();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(".pdf", "pdf");
			jfcForPDF.setFileFilter(filter);
			int returnVal = jfcForPDF.showOpenDialog(view);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File pdf = jfcForPDF.getSelectedFile();
				String name = pdf.getName();
				int ext = name.lastIndexOf('.');
				if (ext != -1) {
					String extension = name.substring(ext, name.length());
					if (extension != "pdf") {
						name = name.substring(0, ext) + ".pdf";
					}
				} else {
					name = name + ".pdf";
				}
				//set the new filename
				String folder = pdf.getPath().substring(0, pdf.getPath().lastIndexOf(File.separatorChar));
				File newpdf = new File(folder + File.separatorChar + name);

				//Create PDF
				int w = GeneralConfig.pageWidth + 4;
				int h = GeneralConfig.pageHeight + 4;
				BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
				Graphics2D g2d = bi.createGraphics();
				view.getDrawPanel().paint(g2d);
				g2d.dispose();
				try {
					XSPDF xspdf = XSPDF.getInstance();
					xspdf.setImageCompressionQuality((float) 1.0);
					xspdf.setPageSize(w, h).setPageMargin(2.0).setImage(bi, 0, 0, w, h, 0).createPdf(newpdf);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			GeneralConfig.printMode = false;
			adaptZoom(previousZoom);
		}

	}

	/*public class SelectDocPathListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser jfc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(".xls", "xls");
			jfc.setFileFilter(filter);
			int returnVal = jfc.showOpenDialog(view);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File fops = jfc.getSelectedFile();
				String name = fops.getName();
				int ext = name.lastIndexOf('.');
				if (ext != -1) {
					String extension = name.substring(ext, name.length());
					if (extension != "xls") {
						name = name.substring(0, ext) + ".xls";
					}
				} else {
					name = name + ".xls";
				}
				//set the new filename
				String folder = fops.getPath().substring(0, fops.getPath().lastIndexOf(File.separatorChar));
				File newfops = new File(folder + File.separatorChar + name);
				GeneralConfig.setDocPath(newfops.getPath(),model.getMilestones(),this);
				
				JProgressBar pb = new JProgressBar();
				pb.setIndeterminate(true);
				dial.setLayout(new BorderLayout());
				dial.add(pb,BorderLayout.CENTER);
				dial.add(new JLabel("Please wait init ended"),BorderLayout.NORTH);
				dial.setVisible(true);
			}
		}

		public void initEnded(){
			dial.dispose();
		}
		
	}*/

	class RepaintOnActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			view.repaint();
		}

	}
	
	class SaveMenuListener implements ActionListener{
		
		boolean saveAs;
		boolean quit;
		
		public SaveMenuListener(boolean saveAs,boolean quit){
			this.saveAs=saveAs;
			this.quit=quit;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			////////////////////////////////////////
			//Before saving a log doc is performed//
			////////////////////////////////////////
			DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
			Calendar cal = Calendar.getInstance();
			String newLog = "";
			newLog = newLog+dateFormat.format(cal.getTime())+" -EOHEADER- ";
			for(int i : model.getMilestones().keySet()){
				//here we put the name to identify the milestone in order to not have to refer to the startupmodel in the target data loading
				newLog = newLog+model.getMilestones().get(i).getName()+" : "+model.getMilestones().get(i).getDocumentationProgress()+" -EOM- ";
			}
			newLog = newLog+" -EOLOG- ";
			GeneralConfig.logSUDoc = GeneralConfig.logSUDoc + newLog;
			//Append last doc update
			GeneralConfig.lastDocUpdateDate=Calendar.getInstance().getTime();
			
			/////////////////
			//SAVE FUNCTION//
			/////////////////
			File newfops = null;
			if(saveAs){
				//Select file
				JFileChooser jfc = new JFileChooser();
				jfc.setLocale(Locale.getDefault());
				jfc.updateUI();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".xml", "xml");
				jfc.setFileFilter(filter);
				//putting the last folder used
				File f = new File("last");
				if(f.exists()){
					try {
						Scanner s = new Scanner(f);
						String fileName = s.next();
						File fToLoad = new File(fileName);
						if(fToLoad.exists()){
							jfc.setCurrentDirectory(new File(fToLoad.getParent()));
							int firstRoundBrackect = fToLoad.getName().lastIndexOf('(');
							int secondRoundBracket = fToLoad.getName().lastIndexOf(')');
							File incrementedVersion;
							if(firstRoundBrackect>0&&secondRoundBracket>0&&firstRoundBrackect<secondRoundBracket){
								int version = Integer.valueOf(fToLoad.getName().substring(firstRoundBrackect+1, secondRoundBracket))+1;
								incrementedVersion = new File(fToLoad.getParent()+File.separator+fToLoad.getName().substring(0, firstRoundBrackect+1)+version+fToLoad.getName().substring(secondRoundBracket, fToLoad.getName().length()));
							} else {
								incrementedVersion = new File(fToLoad.getParent()+File.separator+fToLoad.getName().substring(0,fToLoad.getName().lastIndexOf('.'))+"-(1)"+fToLoad.getName().substring(fToLoad.getName().lastIndexOf('.'),fToLoad.getName().length()));
							}
							jfc.setSelectedFile(new File(incrementedVersion.getAbsolutePath()));
						s.close();
						} else {
							//Nothing to do
						}
					} catch (FileNotFoundException e) {
						JOptionPane.showMessageDialog(view, e.getMessage());
						e.printStackTrace();
					}
				} else {
					//Nothing to do
				}
				
				int returnVal = jfc.showSaveDialog(view);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File fops = jfc.getSelectedFile();
					String name = fops.getName();
					name = name.replaceAll("\\s", "-");
					int ext = name.lastIndexOf('.');
					if (ext != -1) {
						String extension = name.substring(ext, name.length());
						if (extension != "xml") {
							name = name.substring(0, ext) + ".xml";
						}
					} else {
						name = name + ".xml";
					}
					//set the new filename
					String folder = fops.getPath().substring(0, fops.getPath().lastIndexOf(File.separatorChar));
					newfops = new File(folder + File.separatorChar + name);
				}
			} else {
				//set the new filename
				File fops = new File(GeneralConfig.saveName);
				if(fops.getPath().lastIndexOf(File.separatorChar)>0){
					String folder = fops.getPath().substring(0, fops.getPath().lastIndexOf(File.separatorChar));
					newfops = new File(folder + File.separatorChar + fops.getName());
				} else {
					newfops = new File(fops.getName());
				}
			}
			if(newfops!=null&&!newfops.getPath().equals("")){
				//Save
				SaveXMLFile save = new SaveXMLFile();
				try {
					save.doIt(model, newfops.getPath());
				} catch (ParserConfigurationException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				} catch (TransformerException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				}
				GeneralConfig.saveName=newfops.getPath();
				view.getSaveMenuItem().setEnabled(true);
				JOptionPane.showMessageDialog(view, "Save sucessfull");
				if(!newfops.getPath().equals("autosave.xml"))
					writeLastSave(newfops.getPath());
			}
			
			if(quit){
				System.exit(0);
			}
			
			//putting the last file use for the view title
			File f = new File("last");
			if(f.exists()){
				try {
					Scanner s = new Scanner(f);
					String fileName = s.next();
					File fToLoad = new File(fileName);
					if(fToLoad.exists()){
						view.setTitle("Start-Up Sequence - "+fToLoad.getAbsolutePath());
					} else {
						//Nothing to do
					}
					s.close();
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(view, e.getMessage());
					e.printStackTrace();
				}
			} else {
				//Nothing to do
			}
			
			/////////////////////////////////////////////
			//After saving a back up of su doc id done//
			/////////////////////////////////////////////
			if(GeneralConfig.SUDocPath!=""&&new File(GeneralConfig.SUDocPath).exists()){
				SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy HH-mm");
				String tmp = sdf.format(Calendar.getInstance().getTime());
				File folder = new File(newfops.getParent()+File.separatorChar+"BUSUD");
				if(folder.exists()){
					File fSUDOC = new File(newfops.getParent()+File.separatorChar+"BUSUD"+File.separatorChar+"BUSUD-"+tmp+".str");
					try {
						f.createNewFile();
						Utils.copyFile(new File(GeneralConfig.SUDocPath), fSUDOC);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(view, e.getMessage());
						e.printStackTrace();
					}
				}
				else if(folder.mkdir()){
					File fSUDOC = new File(newfops.getParent()+File.separatorChar+"BUSUD"+File.separatorChar+"BUSUD-"+tmp+".str");
					try {
						f.createNewFile();
						Utils.copyFile(new File(GeneralConfig.SUDocPath), fSUDOC);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(view, e.getMessage());
						e.printStackTrace();
					}
				}	
			}
		}
		
	}

	class LoadMenuListener implements ActionListener {
		
		String fileName;
		boolean launchFrameFlag;
		
		public LoadMenuListener(boolean launchFrameFlag){
			fileName="";
			this.launchFrameFlag=launchFrameFlag;
		}
		
		public LoadMenuListener(String s,boolean launchFrameFlag){
			fileName=s;
			this.launchFrameFlag=launchFrameFlag;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			File fops = new File(fileName);
			boolean SaveAs=false;
			if(fileName.equals("")){
				SaveAs=true;
				//Select file
				JFileChooser jfc = new JFileChooser();
				jfc.setLocale(Locale.getDefault());
				jfc.updateUI();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".xml", "xml");
				jfc.setFileFilter(filter);
				
				//putting the last folder used
				File f = new File("last");
				if(f.exists()){
					try {
						Scanner s = new Scanner(f);
						String fileName = s.next();
						File fToLoad = new File(fileName);
						if(fToLoad.exists()){
							jfc.setCurrentDirectory(new File(fToLoad.getParent()));
						} else {
							//Nothing to do
						}
						s.close();
					} catch (FileNotFoundException e) {
						JOptionPane.showMessageDialog(view, e.getMessage());
						e.printStackTrace();
					}
				} else {
					//Nothing to do
				}
				
				int returnVal = jfc.showOpenDialog(view);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					fops = jfc.getSelectedFile();
					fileName = fops.getName();
					int ext = fileName.lastIndexOf('.');
					if (ext != -1) {
						String extension = fileName.substring(ext, fileName.length());
						if (extension != "xml") {
							fileName = fileName.substring(0, ext) + ".xml";
						}
					} else {
						fileName = fileName + ".xml";
					}
					
				}
			} else {
				if(fileName!="autosave.xml"){
					SaveAs=true;
				}
				fileName = fops.getName();
			}
			//set the new filename
			File newfops = null;
			if(fops.getPath().lastIndexOf(File.separatorChar)>0){
				String folder = fops.getPath().substring(0, fops.getPath().lastIndexOf(File.separatorChar));
				newfops = new File(folder + File.separatorChar + fileName);
			} else {
				newfops = new File(fileName);
			}
			
			if(newfops.exists()){
				//Load
				LoadXMLFile load = new LoadXMLFile();
				try {
					StartUpSequence newModel = load.doIt(newfops.getPath());
					//Adapt view zoom to the save zoom
					int saveZoom = (int)load.getZoomValue();
					adaptZoom(saveZoom);
					
					//Instantiate loaded value
					model.setTitleBar(newModel.getTitleBar());
					if(newModel.getMilestones().size()>0){
						model.setMiletones(newModel.getMilestones());
					} else {
						//model.setMiletones(new TreeMap<Integer, Milestone>());
					}
					if(newModel.getStartUpTasks().size()>0){
						model.setStartUpTasks(newModel.getStartUpTasks());
					} else {
						//model.setMiletones(new TreeMap<Integer, Milestone>());
					}
					if(newModel.getSequences().size()>0){
						model.setSequences(newModel.getSequences());
					} else {
						//model.setSequences(new TreeMap<Integer, SequenceBar>());
					}
					if(newModel.getComments().size()>0){
						model.setComments(newModel.getComments());
					} else {
						//model.setComments(new TreeMap<Integer, Comment>());
					}
					if(newModel.getCartouche().size()>0){
						model.setCartouche(newModel.getCartouche());
					}
					if(newModel.getRevisions().size()>0){
						model.setRevisions(newModel.getRevisions());
					}
					
					GeneralConfig.milestoneAlignementGridSize=load.getMilestoneGridSize();
					GeneralConfig.graphicQuality=load.getGraphicQuality();
					GeneralConfig.pageHeightCoeff=load.getPageHeightCoeff();
					GeneralConfig.stepCharacterLimit=load.getStepCharacterLimit();
					GeneralConfig.pageWidthCoeff=load.getPageWidthCoeff();
					GeneralConfig.updateForZoom(GeneralConfig.zoom);
					GeneralConfig.stepsField = new ArrayList<String>(load.getStepFields());
					GeneralConfig.stepFieldVisible = new TreeMap<Integer, Boolean>(load.getStepsFieldVisible());
					view.repaint();
				} catch (DOMException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				} catch (SAXException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				}
				if(SaveAs){
					GeneralConfig.saveName=newfops.getPath();
					view.getSaveMenuItem().setEnabled(true);
				}
				adaptZoom(56);
				JOptionPane.showMessageDialog(view, "Load sucessfull");
			} else if(launchFrameFlag){
			    LaunchFrame lf= new LaunchFrame(controller);
			    lf.setLocation((int)Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2-lf.getWidth()/2), (int)Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2-lf.getHeight()/2));
			    lf.setVisible(true);
			}
			
			//putting the load file name in the view title
			view.setTitle("Start-Up Sequence - "+newfops.getAbsolutePath());
			fileName="";
		}
	}
	
	//Zoom adaptation algos
	private void zoom(int i){
		while(i!=0){
			if(i>0){
				if (GeneralConfig.updateForZoom((int) Math.round((GeneralConfig.zoom * GeneralConfig.coeffZoom)))) {
					view.updatePositionForZoom(1);
					view.getDrawPanel().updateUI();
					view.repaint();
					i--;
				}
				
			} else if(i<0){
				if (GeneralConfig.updateForZoom((int) Math.round((GeneralConfig.zoom / GeneralConfig.coeffZoom)))) {
					view.updatePositionForZoom(-1);
					view.getDrawPanel().updateUI();
					view.repaint();
					i++;
				}
			}
		}
	}
	
	private void adaptZoom(int saveZoom) {
		int currentZoom=(int)Math.round(GeneralConfig.zoom);
		switch (saveZoom) {
			case 133:
				switch (currentZoom) {
					case 133:
						break;
					case 100:
						zoom(1);
						break;
					case 75:
						zoom(2);
						break;
					case 56:
						zoom(3);
						break;
					default:
						break;
				}
				break;
			case 100:
				switch (currentZoom) {
					case 133:
						zoom(-1);
						break;
					case 100:
						break;
					case 75:
						zoom(1);
						break;
					case 56:
						zoom(2);
						break;
					default:
						break;
				}
				break;
			case 75:
				switch (currentZoom) {
					case 133:
						zoom(-2);
						break;
					case 100:
						zoom(-1);
						break;
					case 75:
						break;
					case 56:
						zoom(1);
						break;
					default:
						break;
				}
				break;
			case 56:
				switch (currentZoom) {
					case 133:
						zoom(-3);
						break;
					case 100:
						zoom(-2);
						break;
					case 75:
						zoom(-1);
						break;
					case 56:
						break;
					default:
						break;
				}
				break;
			default:
				break;
		}
	}
	
	class MilestoneButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(newMilestone==true){
				unselectAllInToolbar();
			} else {
				unselectAllInToolbar();
				newMilestone=true;
				
				view.getToolBar().getNewMilestoneButton().setSelected(true);
			}
			view.repaint();
		}
		
	}
	
	class SequenceButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(newSequence==true){
				unselectAllInToolbar();
			} else {
				unselectAllInToolbar();
				newSequence=true;
				
				view.getToolBar().getNewSequenceButton().setSelected(true);
			}
			view.repaint();
		}
		
	}
	
	class CommentButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(newComment==true){
				unselectAllInToolbar();
			} else {
				unselectAllInToolbar();
				newComment=true;
	
				view.getToolBar().getNewCommentButton().setSelected(true);
			}
		}
		
	}
	
	class StartUpButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(newStartUpTask==true){
				unselectAllInToolbar();
			} else {
				unselectAllInToolbar();
				newStartUpTask=true;
	
				view.getToolBar().getNewStartUpTaskButton().setSelected(true);
			}
		}
		
	}
	
	class EditButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(editButton==true){
				unselectAllInToolbar();
			} else {
				unselectAllInToolbar();
				editButton=true;
	
				view.getToolBar().getBtnModify().setSelected(true);
			}
			view.repaint();
		}
		
	}
	
	class DeleteButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(deleteButton==true){
				unselectAllInToolbar();
			} else {
				unselectAllInToolbar();
				deleteButton=true;
				view.getToolBar().getBtnErase().setSelected(true);
			}
			
		}
	}
	
	class CisorsButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(cisorsButton==true){
				unselectAllInToolbar();
			} else {
				unselectAllInToolbar();
				cisorsButton=true;
				view.getToolBar().getBtnCisors().setSelected(true);
			}
		}
		
	}
	
	class NewMilestoneItemMenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Milestone m = new Milestone("New Milestone",whereRightClickHappenX, whereRightClickHappenY);
			model.addMilestone(m);
			view.getGeneralRightClickMenu().setVisible(false);
			view.repaint();
			cancelFactory.addAction(new CancellableAction(CancellableActionLabel.milestone_creation, m, controller));
		}
		
	}
	
	class NewSequenceItemMenuListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			SequenceBar sb = new SequenceBar("New Sequence", whereRightClickHappenX, whereRightClickHappenY, 400, 40, 100);
			model.addSequence(sb);
			view.getGeneralRightClickMenu().setVisible(false);
			cancelFactory.addAction(new CancellableAction(CancellableActionLabel.sequence_creation, sb, controller));
			view.repaint();
		}
		
	}
	
	class NewStartUpTaskItemMenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			StartUpStep sus = new StartUpStep("New Step", whereRightClickHappenX, whereRightClickHappenY, GeneralConfig.milestoneWidth/2, GeneralConfig.milestoneHeight/4);
			model.addStartUpTask(sus);
			view.getGeneralRightClickMenu().setVisible(false);
			cancelFactory.addAction(new CancellableAction(CancellableActionLabel.step_creation, sus, controller));
			view.repaint();
		}
		
	}
	
	class NewCommentItemMenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Comment c = new Comment("New Comment", whereRightClickHappenX, whereRightClickHappenY, 150, 100);
			model.addComment(c);
			view.getGeneralRightClickMenu().setVisible(false);
			cancelFactory.addAction(new CancellableAction(CancellableActionLabel.comment_creation, c, controller));
			view.repaint();
		}
		
	}
	
	/**
	 * Deselect everything in the toolbar
	 */
	public void unselectAllInToolbar(){
		editButton=false;
		deleteButton=false;
		cisorsButton=false;
		newMilestone=false;
		newComment=false;
		newSequence=false;
		newStartUpTask=false;
		
		view.getToolBar().getNewMilestoneButton().setSelected(false);
		view.getToolBar().getNewCommentButton().setSelected(false);
		view.getToolBar().getNewSequenceButton().setSelected(false);
		view.getToolBar().getNewStartUpTaskButton().setSelected(false);
		//view.getToolBar().getBtnModify().setSelected(false);
		//view.getToolBar().getBtnErase().setSelected(false);
		//view.getToolBar().getBtnCisors().setSelected(false);
	}

	/**
	 * Repaint the view
	 */
	public void repaintView() {
		view.repaint();
	}
	
	class PreferenceListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			SettingsFrame sd = new SettingsFrame(model.getMilestones());
			sd.setEnabled(true);
		}
		
	}
	
	class DragAndDropDetector implements MouseListener{

		int type;
		
		public DragAndDropDetector(int s){
			type=s;
		}
		@Override
		public void mouseClicked(MouseEvent arg0) {
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			switch (this.type) {
			case 0:
				newMilestone=true;
				break;
			case 1:
				newSequence=true;
				break;
			case 2:
				newComment=true;
				break;
			case 3:
				newStartUpTask=true;
				break;
			default:
				break;
			}
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			//End Drag & Drop
			Point p = arg0.getPoint();
			if(newMilestone){
				p.setLocation(new Point(p.x-view.getToolBar().getWidth(), p.y));
			} else if(newSequence){
				p.setLocation(new Point(p.x-view.getToolBar().getWidth(), p.y+100));
			} else if(newComment){
				p.setLocation(new Point(p.x-view.getToolBar().getWidth(), p.y+175));
			} else if(newStartUpTask){
				p.setLocation(new Point(p.x-view.getToolBar().getWidth(), p.y+250));
			}
			
			if (view.getDrawPanel().contains(p)&&arg0.getButton() == MouseEvent.BUTTON1&&(newComment||newSequence||newMilestone||newStartUpTask)){
				if(newMilestone){
					Milestone m = new Milestone("New Milestone");
					model.addMilestone(m);
					m.setX(view.getDrawPanel().getVisibleRect().x+p.getX()-GeneralConfig.milestoneWidth/2);
					m.setY(view.getDrawPanel().getVisibleRect().y+p.getY()-GeneralConfig.milestoneHeight/2);
					cancelFactory.addAction(new CancellableAction(CancellableActionLabel.milestone_creation, m, controller));
				} else if(newSequence){
					SequenceBar sb = new SequenceBar("New Sequence", 0	,0 , GeneralConfig.milestoneWidth*4, GeneralConfig.milestoneHeight/4, GeneralConfig.milestoneHeight);
					model.addSequence(sb);
					sb.setX(view.getDrawPanel().getVisibleRect().x+p.getX()-GeneralConfig.milestoneWidth*2);
					sb.setY(view.getDrawPanel().getVisibleRect().y+p.getY()-GeneralConfig.milestoneHeight/2);
					cancelFactory.addAction(new CancellableAction(CancellableActionLabel.sequence_creation, sb, controller));
				} else if(newComment){
					Comment c = new Comment("Write your note here", 0, 0, (int)(GeneralConfig.milestoneWidth/1.3), (int)(GeneralConfig.milestoneHeight/1.5));
					model.addComment(c);
					c.setX(view.getDrawPanel().getVisibleRect().x+p.getX()-(int)(GeneralConfig.milestoneWidth/2.6));
					c.setY(view.getDrawPanel().getVisibleRect().y+p.getY()-(int)(GeneralConfig.milestoneHeight/3));	
					cancelFactory.addAction(new CancellableAction(CancellableActionLabel.comment_creation, c, controller));
				} else if(newStartUpTask){
					StartUpStep sut = new StartUpStep("New Step", 0, 0, (int)(GeneralConfig.milestoneWidth/2), (int)(GeneralConfig.milestoneHeight/8));
					model.addStartUpTask(sut);
					sut.setX(view.getDrawPanel().getVisibleRect().x+p.getX()-(int)(GeneralConfig.milestoneWidth/4));
					sut.setY(view.getDrawPanel().getVisibleRect().y+p.getY()-(int)(GeneralConfig.milestoneHeight/8));
					cancelFactory.addAction(new CancellableAction(CancellableActionLabel.step_creation, sut, controller));
				}
				view.getDrawPanel().notAnyMoreMoving();
				unselectAllInToolbar();
				view.repaint();
				
			}
		}
		
	}
	
	class NewMenuItemListener implements ActionListener{

	    private boolean withLaunchFrameReturn;
	    
	    public NewMenuItemListener(boolean withLaunchFrameReturn){
		this.withLaunchFrameReturn=withLaunchFrameReturn;
	    }
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			int confirm = JOptionPane.showOptionDialog(view, "Are you sure you want to create a new one ?", "New Start-Up Sequence Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if(confirm==0){
				GeneralConfig.init();
				model.getMilestones().clear();
				model.getSequences().clear();
				model.getComments().clear();
				model.getProjectTitleBlock().initCartouche();
				model.getRevisions().clear();
				model.getStartUpTasks().clear();
				model.setTitleBar(new TitleBar("New Project", (int)(GeneralConfig.pageWidth*0.333)/2, 0, (int)(GeneralConfig.pageWidth*0.666), (int)(GeneralConfig.pageHeight*0.05)));
				view.repaint();
			} else {
			    if(withLaunchFrameReturn){
        			    LaunchFrame lf= new LaunchFrame(controller);
        			    lf.setLocation((int)Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2-lf.getWidth()/2), (int)Math.round(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2-lf.getHeight()/2));
        			    lf.setVisible(true); 
			    }
			}
		}
		
	}
	
	class SupprKeyListerner implements KeyListener{

		@Override
		public void keyPressed(KeyEvent arg0) {
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			if(e.getKeyChar()==KeyEvent.VK_DELETE){
				milestoneRef=milestoneRefSelected;
				startUpTaskRef=startUpTaskRefSelected;
				commentRef=commentRefSelected;
				sequenceBarRef=sequenceBarRefSelected;
				
				DeleteMenuItemListener dmil = new DeleteMenuItemListener();
				ActionEvent ae = new ActionEvent(this, 0, "");
				dmil.actionPerformed(ae);
			}
		}
		
	}
	
	class SetupTitleBlockListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			ProjectTitleBlockView ptbv = new ProjectTitleBlockView(model.getProjectTitleBlock());
			ProjectTitleBlockController ptbc = new ProjectTitleBlockController(ptbv, model.getProjectTitleBlock(), view);
			ptbc.setVisible(true);
			ptbv.setLocationRelativeTo(view);
		}
		
	}
	
	class ManageRevisionsListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			RevisionView rv = new RevisionView(model.getRevisions());
			rv.setVisible(true);
			rv.setLocationRelativeTo(view);
		}
		
	}
	
	class QuitListener implements ActionListener,WindowListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			ExitFrame ef = new ExitFrame(controller);
			ef.setVisible(true);
		}

		@Override
		public void windowActivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent arg0) {
			ExitFrame ef = new ExitFrame(controller);
			ef.setVisible(true);
		}

		@Override
		public void windowClosing(WindowEvent arg0) {
			ExitFrame ef = new ExitFrame(controller);
			ef.setVisible(true);
		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowOpened(WindowEvent arg0) {
		}
	}
	
	
	/**
	 * return the action listener associated to new project action
	 * @return ActionListener
	 */
	public ActionListener getNewProjectActionListener(){
		return new NewMenuItemListener(true);
	}
	/**
	 * return the action listener associated to load project action
	 * @param fileName 
	 * @return ActionListener
	 */
	public ActionListener getLoadProjectActionListener(String fileName){
		return new LoadMenuListener(fileName,true);
	}
	/**
	 * return the action listener associated to save project action
	 * @param saveAs 
	 * @param quit 
	 * @return ActionListener
	 */
	public ActionListener getSaveProjectActionListener(boolean saveAs,boolean quit){
		return new SaveMenuListener(saveAs,quit);
		
	}
	/**
	 * it write the path of last project used in the file last at the project root 
	 * @param s 
	 */
	public void writeLastSave(String s){
		File f = new File("last");
		try {
			f.createNewFile();
			PrintWriter pw = null;
			try {
				pw = new PrintWriter(new BufferedWriter(new FileWriter(f, false)));
				pw.write(s);
				pw.close();
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(view, e.getMessage());
				e.printStackTrace();
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(view, e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * @author S.Trémouille
	 * Listener invoke to show the project resume
	 *
	 */
	public class ShowProjectResumeListener implements ActionListener{

		ProjectResumeWorker prw;
		JDialog f;
		JProgressBar p;
		ArrayList<Database> old;
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
		    if(GeneralConfig.databases.size()>0){
			DbChooserDialog dbChooser = new DbChooserDialog(null,this);
			dbChooser.setVisible(true);
        		dbChooser.pack();
			} else {
			    JOptionPane.showMessageDialog(view, "Please set-up connection first");
			}
		}

		/**
		 * Invoke to notify the request have fetched all the data from databases
		 */
		public void notifyWorkFinish() {
			TreeMap<String, Object> tmp = null;
			try {
				tmp = prw.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ProjectResume pr = new ProjectResume(tmp);
			pr.setVisible(true);
			f.dispose();
		}

		public void dbChoosed(ArrayList<Database> old) {
		    	f= new JDialog(view);
			f.setTitle("Fetching data from databases");
			p = new JProgressBar(0,100);
			p.setValue(0);
			f.setVisible(true);
			f.add(p);
			f.pack();
			f.setSize(new Dimension(f.getWidth()*2,f.getHeight()));
			f.setLocation(new Point(view.getX()+view.getWidth()/2-f.getWidth()/2, view.getY()+view.getHeight()/2-2*f.getHeight()));
			prw = new ProjectResumeWorker(this,old);
			prw.addPropertyChangeListener(new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent e) {
					if(e.getPropertyName()=="progress"){
						p.setValue((Integer)e.getNewValue());
					}
				}
			});
			prw.execute();
		}
		
	}

	
	/**
	 * Disable view associated to the controller
	 */
	public void disableView() {
		view.setVisible(false);
	}
	
	/**
	 * Enable view associated to the controller
	 */
	public void enableView() {
		view.setVisible(true);
	}
	
	/**
	 * @author S.Trémouille
	 *
	 */
	public class ShowProjectTargetListener implements ActionListener{

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		 if(GeneralConfig.databases.size()>0){
			
				DbChooserDialog dbChooser = new DbChooserDialog(this,null);
                		dbChooser.setVisible(true);
                		dbChooser.pack();
			
		    } else {
		    	JOptionPane.showMessageDialog(view, "Set up databases connection first");
		    }

		
	    }
	    
	    /**
	     * @param oldDbConf
	     */
	    public void dbChoosed(List<Database> oldDbConf){
		Milestone m = new Milestone("%");
		Date targetDate = new Date(0);
		
		if(model.getMilestones().size()>=1){
        		for(int mRef : model.getMilestones().keySet()){
        		    if(targetDate.getTime()<model.getMilestone(mRef).getTargetDate().getTime())
        			targetDate = new Date(model.getMilestone(mRef).getTargetDate().getTime());
        		}
		} else {
        		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
        			"MMM/dd/yyyy");
        		String res = new DatePicker(view).setPickedDate();
        		if(res!=""){
        		    try {
        			//m.setTargetDate(sdf.parse(res));
        		    	targetDate = new Date(sdf.parse(res).getTime());
        		    } catch (ParseException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		    }
        		}
		}
		m.setTargetDate(targetDate);
		TargetView v = new TargetView(m, 100, true, oldDbConf,model);
		v.setLocation(new Point(view.getX()+view.getWidth()/2-v.getWidth()/2,view.getY()+view.getHeight()/2-v.getHeight()/2));
	    }
	    
	}
	
	/**
	 * @author S.Trémouille
	 * Listener to decrease the zoom value
	 */
	public class DecreaseZoomListener implements ActionListener{

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		Point e=new Point();
		e.setLocation(view.getDrawPanel().getVisibleRect().getCenterX(), view.getDrawPanel().getVisibleRect().getCenterY());
		//ZOOM OUT
		if (GeneralConfig.updateForZoom((int) Math.round((GeneralConfig.zoom / GeneralConfig.coeffZoom)))) {
			view.updatePositionForZoom(-1);
			view.getDrawPanel().updateUI();
			view.getScrollPane().getHorizontalScrollBar().setValue((int) Math.round(e.getX()*(1/GeneralConfig.coeffZoom-1)+view.getScrollPane().getHorizontalScrollBar().getValue()));
			view.getScrollPane().getVerticalScrollBar().setValue((int) Math.round(e.getY()*(1/GeneralConfig.coeffZoom-1)+view.getScrollPane().getVerticalScrollBar().getValue()));
			view.repaint();
		}
	    }
	    
	}
	
	/**
	 * @author S.Tr�mouille
	 * Listener to increase the zoom value
	 */
	public class IncreaseZoomListener implements ActionListener{

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		//ZOOM IN
		Point e=new Point();
		e.setLocation(view.getDrawPanel().getVisibleRect().getCenterX(), view.getDrawPanel().getVisibleRect().getCenterY());
		if (GeneralConfig.updateForZoom((int) Math.round((GeneralConfig.zoom * GeneralConfig.coeffZoom)))) {
			view.updatePositionForZoom(1);
			view.getDrawPanel().updateUI();
			view.getScrollPane().getHorizontalScrollBar().setValue((int) Math.round(e.getX()*(GeneralConfig.coeffZoom-1)+view.getScrollPane().getHorizontalScrollBar().getValue()));
			view.getScrollPane().getVerticalScrollBar().setValue((int) Math.round(e.getY()*(GeneralConfig.coeffZoom-1)+view.getScrollPane().getVerticalScrollBar().getValue()));
			view.repaint();
		}
	    }
	    
	}
	
	public class CopyPasteListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("milestoneRefSelected "+milestoneRefSelected);
			System.out.println("startUpTaskRefSelected "+startUpTaskRefSelected);
			System.out.println("startUpTaskCopied "+startUpTaskCopied);
			if ((e.getKeyCode() == KeyEvent.VK_C) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
				if(startUpTaskRefSelected!=0){
					startUpTaskCopied = model.getStartUpTask(startUpTaskRefSelected);
					milestoneCopied = null;
				} else if(milestoneRefSelected!=0){
					milestoneCopied = model.getMilestone(milestoneRefSelected);
					startUpTaskCopied = null;
				}
			}else if ((e.getKeyCode() == KeyEvent.VK_V) && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0){
				if(startUpTaskCopied != null){
					StartUpStep sus = new StartUpStep(startUpTaskCopied.getName(),startUpTaskCopied.getX()+startUpTaskCopied.getWidth() , startUpTaskCopied.getY()+startUpTaskCopied.getHeight(), startUpTaskCopied.getWidth(), startUpTaskCopied.getHeight());
					sus.setAttr(startUpTaskCopied.getAttr());
					sus.setColor(startUpTaskCopied.getColor());
					sus.setLocalAttrRule(startUpTaskCopied.isLocalAttrRule());
					sus.setShapeName(startUpTaskCopied.getShapeName());
					sus.setRelatedToThisMilestone(startUpTaskCopied.getRelatedToThisMilestone());
					sus.setSecondLine(startUpTaskCopied.getSecondLine());
					sus.setLocalAttrToDisplay(startUpTaskCopied.getLocalAttrToDisplay());
					model.addStartUpTask(sus);
					startUpTaskCopied=sus;
				} else if(milestoneCopied != null){
					Milestone m = new Milestone(milestoneCopied.getName(), milestoneCopied.getX()+50, milestoneCopied.getY()+50);
					m.setTargetDate(milestoneCopied.getTargetDate());
					m.setDescription(milestoneCopied.getDescription());
					model.addMilestone(m);
					milestoneCopied=m;
				}
				view.repaint();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
		
	}
	
	class CancelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			cancelFactory.cancel();
			view.repaint();
		}
		
	}
	
	class RedoListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			cancelFactory.redo();
			view.repaint();
		}
		
	}
	
}
