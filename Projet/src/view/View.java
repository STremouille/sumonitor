package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import model.Comment;
import model.Milestone;
import model.SequenceBar;
import model.StartUpSequence;
import model.StartUpStep;
import model.TitleBar;
import model.mode.AvailableMode;
import model.mode.Mode;
import model.mode.NormalMode;
import model.mode.SelectedMode;
import conf.GeneralConfig;

import java.awt.SystemColor;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JSlider;
import javax.swing.JLabel;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Label;

/**
 * View associated to the model
 * @author S.Trémouille
 *
 */

public class View extends JFrame{

    	private static final long serialVersionUID = -1639032993721578481L;
	StartUpSequence model;
	View v = this;

	private BorderLayout borderLayout;
	private JMenuBar menuBar;
	private JMenu fileMenu,settingMenu;
	private JMenuItem newMenuItem, loadMenuItem, saveMenuItem, selectDocPath, updateMenuItem, printMenuItem, deleteMenuItem, editMenuItem, connectMenuItem,connectMenuItem1, editMenuItem1,editMenuItem2,editMenuItem3,deleteMenuItem1,deleteMenuItem2,deleteMenuItem3;
	private JMenuItem newMilestoneMenuItem,newSequenceMenuItem,newCommentMenuItem,newStartUpTaskMenuItem,operated;
	private JScrollPane scrollPane;
	// JToolBar toolBar;
	private JPanel toolBar, drawPanelPlusConnectButton;
	private DrawPanel panel;
	private JPopupMenu milestoneRightClickMenu,sequenceBarRightClickMenu,commentRightClickMenu,startUpTaskRightClickMenu;
	private JPopupMenu connectorRightClickMenu,generalRightClickMenu;
	private JButton finalizeConnection,cancelConnection;
	private Mode mode;
	

	private int[][] alignementCells;
	private JMenuItem preferences;
	private JMenu mnUtility;
	private JMenuItem loadLastAutosave;
	private JMenuItem mntmSetupTitleBlock;
	private JMenuItem mntmManageRevisions;
	private JMenuItem mntmSave;
	private JMenuItem mntmQuitStartUp;
	private JMenu mnHelp;
	private JMenuItem mntmAbout;
	private JMenuItem mntmProjectResume;
	private JMenuItem mntmShowProjectTarget;
	private JPanel panel_1;
	private JLabel zoomValueLabel;
	private JButton lblMoins;
	private JButton lblPlus;
	private JPanel panel_2;
	private JMenuItem mntmShowStartUp;
	private JPanel panel_3;
	private JLabel message;
	
	private boolean resizing;

	
	/**
	 * @param model
	 */
	public View(StartUpSequence model) {
		setBackground(Color.WHITE);
		getContentPane().setBackground(Color.WHITE);
		this.model = model;
		this.mode = Mode.NORMAL;
		
		// Toogle screen size
		Toolkit tk = Toolkit.getDefaultToolkit();  
		int xSize = ((int) tk.getScreenSize().getWidth());  
		int ySize = ((int) tk.getScreenSize().getHeight());
		setIconImage(Toolkit.getDefaultToolkit().getImage(MilestoneEditorFrame.class.getResource("/img/icone.png")));
		this.setTitle("Start Up Monitor");

		borderLayout = new BorderLayout();
		getContentPane().setLayout(borderLayout);
		init();

		this.setSize(xSize, ySize);
		this.setExtendedState(Frame.MAXIMIZED_BOTH); 
		//this.setSize(new Dimension(800, 600));
		//this.setVisible(true);
		this.scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
	}

	/**
	 * Initialization
	 */
	public void init() {

		// ScrollPane
		panel = new DrawPanel(this);
		panel.setBackground(Color.WHITE);
		panel.setPreferredSize(new Dimension(GeneralConfig.pageWidth, GeneralConfig.pageHeight));

		scrollPane = new JScrollPane(panel);
		scrollPane.setViewportBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setWheelScrollingEnabled(true);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		// Menu
		newMenuItem = new JMenuItem("New");
		newMenuItem.setIcon(new ImageIcon(View.class.getResource("/icons/org/fife/plaf/Office2003/new.gif")));
		newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		loadMenuItem = new JMenuItem("Load");
		loadMenuItem.setIcon(new ImageIcon(View.class.getResource("/icons/org/fife/plaf/Office2003/open.gif")));
		loadMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		saveMenuItem = new JMenuItem("Save As (export to .xml)");
		saveMenuItem.setIcon(new ImageIcon(View.class.getResource("/img/saveas.png")));
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		printMenuItem = new JMenuItem("Print Start Up Sequence to PDF");
		printMenuItem.setIcon(new ImageIcon(View.class.getResource("/img/print.png")));
		printMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);
		fileMenu = new JMenu("  File  ");
		fileMenu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		fileMenu.add(newMenuItem);
		fileMenu.add(loadMenuItem);
		
		mntmSave = new JMenuItem("Save");
		mntmSave.setIcon(new ImageIcon(View.class.getResource("/img/save.png")));
		mntmSave.setSelectedIcon(null);
		if(GeneralConfig.saveName!=""){
			mntmSave.setEnabled(true);
		} else {
			mntmSave.setEnabled(false);
		}
		mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		fileMenu.addSeparator();
		fileMenu.add(mntmSave);
		fileMenu.add(saveMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(printMenuItem);
		fileMenu.addSeparator();
		menuBar.add(fileMenu);
		
		mntmQuitStartUp = new JMenuItem("Exit Start Up Sequence");
		mntmQuitStartUp.setHorizontalAlignment(SwingConstants.LEFT);
		mntmQuitStartUp.setIcon(new ImageIcon(View.class.getResource("/icons/org/fife/plaf/Office2003/delete.gif")));
		mntmQuitStartUp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
		fileMenu.add(mntmQuitStartUp);

		this.setJMenuBar(menuBar);
		
		mnUtility = new JMenu("  Utility  ");
		mnUtility.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		menuBar.add(mnUtility);
		updateMenuItem = new JMenuItem("Update all milestones progress & FO actions");
		updateMenuItem.setIcon(new ImageIcon(View.class.getResource("/img/updateAll.png")));
		updateMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		mnUtility.add(updateMenuItem);
		
		mntmProjectResume = new JMenuItem("Show Project Resume");
		mntmProjectResume.setIcon(new ImageIcon(View.class.getResource("/icons/org/fife/plaf/Office2003/printpreview.gif")));
		mnUtility.add(mntmProjectResume);
		
		mntmShowStartUp = new JMenuItem("Show List of Start Up Steps");
		mnUtility.add(mntmShowStartUp);
		
		mntmShowProjectTarget = new JMenuItem("Show Project Target");
		mntmShowProjectTarget.setIcon(new ImageIcon(View.class.getResource("/org/apache/log4j/lf5/viewer/images/channelexplorer_satellite.gif")));
		mnUtility.add(mntmShowProjectTarget);
		mnUtility.addSeparator();
		
		loadLastAutosave = new JMenuItem("Load last autosave");
		loadLastAutosave.setIcon(new ImageIcon(View.class.getResource("/icons/org/fife/plaf/Office2003/open.gif")));
		loadLastAutosave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mnUtility.add(loadLastAutosave);
		selectDocPath = new JMenuItem("Set Documentation Path");
		selectDocPath.setIcon(new ImageIcon(View.class.getResource("/img/docPath.png")));
		selectDocPath.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		settingMenu = new JMenu("  Settings  ");
		settingMenu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		//settingMenu.add(selectDocPath);
		menuBar.add(settingMenu);
		
		preferences = new JMenuItem("Preferences");
		preferences.setIcon(new ImageIcon(View.class.getResource("/img/settings.png")));
		preferences.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		settingMenu.add(preferences);
		settingMenu.addSeparator();
		
		mntmSetupTitleBlock = new JMenuItem("Setup Title Block");
		settingMenu.add(mntmSetupTitleBlock);
		
		mntmManageRevisions = new JMenuItem("Manage Revisions");
		settingMenu.add(mntmManageRevisions);
		
		mnHelp = new JMenu("  Help  ");
		mnHelp.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		menuBar.add(mnHelp);
		
		mntmAbout = new JMenuItem("About");
		mntmAbout.setIcon(new ImageIcon(View.class.getResource("/icons/org/fife/plaf/Office2003/about.gif")));
		mntmAbout.addActionListener(new ActionListener() {
		    
		    @Override
		    public void actionPerformed(ActionEvent arg0) {
			AboutView dialog = new AboutView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.pack();
		    }
		});
		mnHelp.add(mntmAbout);

		// Toolbar
		toolBar = new ToolBarPanel();
		toolBar.setBackground(Color.WHITE);
		this.getContentPane().add(toolBar, BorderLayout.WEST);

		// Popup Menu for the right click
		//Milestone Right Click Menu
		milestoneRightClickMenu = new JPopupMenu();
		deleteMenuItem = new JMenuItem("Delete");
		editMenuItem = new JMenuItem("Edit");
		connectMenuItem = new JMenuItem("Connect");
		milestoneRightClickMenu.add(editMenuItem);
		milestoneRightClickMenu.add(deleteMenuItem);
		milestoneRightClickMenu.add(connectMenuItem);
		
		//Sequence Bar Right Click Menu
		deleteMenuItem1 = new JMenuItem("Delete");
		editMenuItem1 = new JMenuItem("Edit");
		sequenceBarRightClickMenu = new JPopupMenu();
		sequenceBarRightClickMenu.add(editMenuItem1);
		sequenceBarRightClickMenu.add(deleteMenuItem1);
		
		//Comment Right Click Menu
		deleteMenuItem2 = new JMenuItem("Delete");
		editMenuItem2 = new JMenuItem("Edit");
		commentRightClickMenu = new JPopupMenu();
		commentRightClickMenu.add(editMenuItem2);
		commentRightClickMenu.add(deleteMenuItem2);
		
		//StartUpTask
		startUpTaskRightClickMenu = new JPopupMenu();
		connectMenuItem1 = new JMenuItem("Connect"); 
		editMenuItem3 = new JMenuItem("Edit");
		deleteMenuItem3 = new JMenuItem("Delete");
		operated = new JMenuItem("Done");
		startUpTaskRightClickMenu.add(operated);
		startUpTaskRightClickMenu.add(editMenuItem3);
		startUpTaskRightClickMenu.add(deleteMenuItem3);
		startUpTaskRightClickMenu.add(connectMenuItem1);
		//General Right Click Menu
		generalRightClickMenu = new JPopupMenu();
		newMilestoneMenuItem = new JMenuItem("New Milestone");
		newSequenceMenuItem = new JMenuItem("New Sequence");
		newCommentMenuItem = new JMenuItem("New Comment");
		newStartUpTaskMenuItem = new JMenuItem("New Step");
		generalRightClickMenu.add(newMilestoneMenuItem);
		generalRightClickMenu.add(newSequenceMenuItem);
		generalRightClickMenu.add(newCommentMenuItem);
		generalRightClickMenu.add(newStartUpTaskMenuItem);
		
		//Connector Right Click Menu
		//generated dynamically when getter invocated
		connectorRightClickMenu = new JPopupMenu();
		drawPanelPlusConnectButton = new JPanel(new BorderLayout());
		drawPanelPlusConnectButton.setBackground(new Color(153, 204, 255));
		drawPanelPlusConnectButton.add(scrollPane, BorderLayout.CENTER);
		JPanel connectionFinalizationPanel = new JPanel();
		connectionFinalizationPanel.setBackground(Color.WHITE);
		drawPanelPlusConnectButton.add(connectionFinalizationPanel,BorderLayout.NORTH);
		connectionFinalizationPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		finalizeConnection = new JButton("Connect");
		finalizeConnection.setBackground(Color.WHITE);
		finalizeConnection.setFont(new Font("Tahoma", Font.PLAIN, 15));
		finalizeConnection.setIcon(new ImageIcon(View.class.getResource("/icons/org/fife/plaf/Office2003/forward.gif")));
		finalizeConnection.setForeground(new Color(60, 179, 113));
		connectionFinalizationPanel.add(finalizeConnection);
		cancelConnection = new JButton("Cancel");
		cancelConnection.setBackground(Color.WHITE);
		cancelConnection.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cancelConnection.setIcon(new ImageIcon(View.class.getResource("/icons/org/fife/plaf/Office2003/delete.gif")));
		cancelConnection.setForeground(new Color(165, 42, 42));
		connectionFinalizationPanel.add(cancelConnection);

		this.getContentPane().add(drawPanelPlusConnectButton, BorderLayout.CENTER);
		
		panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		panel_3 = new JPanel();
		panel_1.add(panel_3);
		
		message = new JLabel("");
		message.setFont(new Font("Arial", Font.BOLD, 12));
		message.setForeground(Color.RED);
		panel_3.add(message);
		
		panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		lblMoins = new JButton("-");
		panel_2.add(lblMoins, BorderLayout.WEST);
		lblMoins.setToolTipText("CTRL + MOUSE WHEEL DOWN");
		lblMoins.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		zoomValueLabel = new JLabel((int)GeneralConfig.zoom+" %");
		zoomValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		zoomValueLabel.setPreferredSize(new Dimension(50, 16));
		zoomValueLabel.setSize(new Dimension(50, 16));
		panel_2.add(zoomValueLabel, BorderLayout.CENTER);
		zoomValueLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		zoomValueLabel.setSize(48, 16);
		
		lblPlus = new JButton("+");
		panel_2.add(lblPlus, BorderLayout.EAST);
		lblPlus.setToolTipText("CTRL + MOUSE WHEEL UP");
		lblPlus.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		updateMilestonesForConnection();
		updateBackgroundForConnection();
		updateConnectButton();
		
		//Override the default close operation
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.pack();
	}

	//During the connect mode draw milestones with the right color set if it is selected or not
	private void updateMilestonesForConnection() {
		// Strategy Pattern is implemented for milestone's mode
		for (Integer i : model.getMilestones().keySet()) {
			Milestone m = model.getMilestone(i);
			if (mode == Mode.CONNECTION) {
				if (m.isSelectedForConnection()) {
					m.setMode(new SelectedMode());
				} else {
					m.setMode(new AvailableMode());
				}
			} else {
				m.setMode(new NormalMode());
			}
			
		}
	}
	
	public void setWarningMessage(String s,Color c){
		this.message.setText(s);
		this.message.setForeground(c);
	}
	
	private void updateStartUpTasksForConnection(){
		// Strategy Pattern is implemented for milestone's mode
				for (Integer i : model.getStartUpTasks().keySet()) {
					StartUpStep sut = model.getStartUpTask(i);
					if (mode == Mode.CONNECTION) {
						if (sut.isSelectedForConnection()) {
							sut.setMode(new SelectedMode());
						} else {
							sut.setMode(new AvailableMode());
						}
					} else {
						sut.setMode(new NormalMode());
					}
					
				}
	}
	/**
	 * Recalculate every position and dimension to adapt for a zoom
	 * @param i
	 */
	public void updatePositionForZoom(int i) {
		//System.out.println("Scale :"+model.getProjectTitleBlock().getCartouche().get("scale"));
		//System.out.println(GeneralConfig.pageWidth);
		//System.out.println("Model :"+GeneralConfig.pageWidth);
		//System.out.println("Viewport Width :"+ scrollPane.getPreferredSize().getWidth());
		/*if(GeneralConfig.pageWidth>scrollPane.getPreferredSize().getWidth()&&GeneralConfig.pageHeight>scrollPane.getPreferredSize().getHeight()){
			System.out.println("Enlargement W+H");
			this.panel.setPreferredSize(new Dimension(GeneralConfig.pageWidth+5, GeneralConfig.pageHeight+5));
		}
		else if(GeneralConfig.pageWidth>scrollPane.getPreferredSize().getWidth()){
			System.out.println("Enlargement W");
			this.panel.setPreferredSize(new Dimension(GeneralConfig.pageWidth+5, GeneralConfig.pageHeight));
		} else if(GeneralConfig.pageHeight>scrollPane.getPreferredSize().getHeight()){
			System.out.println("Enlargement H");
			this.panel.setPreferredSize(new Dimension(GeneralConfig.pageWidth, GeneralConfig.pageHeight+5));
		} else {
			this.panel.setPreferredSize(new Dimension(GeneralConfig.pageWidth, GeneralConfig.pageHeight));
		}*/
		this.panel.setPreferredSize(new Dimension(GeneralConfig.pageWidth+GeneralConfig.pageWidth/2, GeneralConfig.pageHeight+GeneralConfig.pageHeight/2));
		//TitleBar
		if (i == -1) {
			model.getTitleBar().setBounds(new Rectangle((int)Math.round(model.getTitleBar().getX() / GeneralConfig.coeffZoom),(int)Math.round(model.getTitleBar().getY() / GeneralConfig.coeffZoom),(int)Math.round(model.getTitleBar().getWidth()/ GeneralConfig.coeffZoom),(int)Math.round( model.getTitleBar().getHeight()/ GeneralConfig.coeffZoom)));
		}
		else if (i == 1){
			model.getTitleBar().setBounds(new Rectangle((int)Math.round(model.getTitleBar().getX() * GeneralConfig.coeffZoom),(int)Math.round(model.getTitleBar().getY() * GeneralConfig.coeffZoom),(int)Math.round(model.getTitleBar().getWidth()* GeneralConfig.coeffZoom),(int)Math.round( model.getTitleBar().getHeight()* GeneralConfig.coeffZoom)));
		}
		
		//SequenceBar
		if (i == -1) {
			for(Integer id : model.getSequences().keySet()){
				SequenceBar sb = model.getSequence(id);
				sb.setBoundsWithExtended(new Rectangle((int)Math.round(sb.getX() / GeneralConfig.coeffZoom),(int)Math.round(sb.getY() / GeneralConfig.coeffZoom),(int)Math.round(sb.getWidth()/ GeneralConfig.coeffZoom),(int)Math.round(sb.getHeight()/ GeneralConfig.coeffZoom)),(int)Math.round(sb.getExtendedHeight()/GeneralConfig.coeffZoom));
			}
			sequenceBarAlignement();
		}
		else if (i == 1){
			for(Integer id : model.getSequences().keySet()){
				SequenceBar sb = model.getSequence(id);
				sb.setBoundsWithExtended(new Rectangle((int)Math.round(sb.getX() * GeneralConfig.coeffZoom),(int)Math.round(sb.getY() * GeneralConfig.coeffZoom),(int)Math.round(sb.getWidth()* GeneralConfig.coeffZoom),(int)Math.round(sb.getHeight()* GeneralConfig.coeffZoom)),(int)Math.round(sb.getExtendedHeight()*GeneralConfig.coeffZoom));
				}
			sequenceBarAlignement();
		}
		//System.out.println(model.getComments().size());
		//Comments
		if (i == -1) {
			for(Integer id : model.getComments().keySet()){
				Comment c = model.getComment(id);
				c.setBounds(new Rectangle((int)Math.round(c.getX() / GeneralConfig.coeffZoom),(int)Math.round(c.getY() / GeneralConfig.coeffZoom),(int)Math.round(c.getWidth()/ GeneralConfig.coeffZoom),(int)Math.round(c.getHeight()/ GeneralConfig.coeffZoom)));
			}
		}
		else if (i == 1){
			for(Integer id : model.getComments().keySet()){
				Comment c = model.getComment(id);
				c.setBounds(new Rectangle((int)Math.round(c.getX() * GeneralConfig.coeffZoom),(int)Math.round(c.getY() * GeneralConfig.coeffZoom),(int)Math.round(c.getWidth()* GeneralConfig.coeffZoom),(int)Math.round(c.getHeight()* GeneralConfig.coeffZoom)));
				}
		}
		
		//Start Up Tasks
		if (i == -1) {
			for(Integer id : model.getStartUpTasks().keySet()){
				StartUpStep sut = model.getStartUpTask(id);
				sut.setBounds(new Rectangle((int)Math.round(sut.getX() / GeneralConfig.coeffZoom),(int)Math.round(sut.getY() / GeneralConfig.coeffZoom),(int)Math.round(sut.getWidth()/ GeneralConfig.coeffZoom),(int)Math.round(sut.getHeight()/ GeneralConfig.coeffZoom)));
			}
			startUpTaskAlignement();
		}
		else if (i == 1){
			for(Integer id : model.getStartUpTasks().keySet()){
				StartUpStep sut = model.getStartUpTask(id);
				sut.setBounds(new Rectangle((int)Math.round(sut.getX() * GeneralConfig.coeffZoom),(int)Math.round(sut.getY() * GeneralConfig.coeffZoom),(int)Math.round(sut.getWidth()* GeneralConfig.coeffZoom),(int)Math.round(sut.getHeight()* GeneralConfig.coeffZoom)));
				}
			startUpTaskAlignement();
		}
		
		//Milestones
		for (Integer id : model.getMilestones().keySet()) {
			Milestone m = model.getMilestone(id);
			if (i == -1) {
				m.setX(m.getDoubleX() / GeneralConfig.coeffZoom);
				m.setY(m.getDoubleY() / GeneralConfig.coeffZoom);
			} else if (i == 1) {
				m.setX(m.getDoubleX() * GeneralConfig.coeffZoom);
				m.setY(m.getDoubleY() * GeneralConfig.coeffZoom);
			}
		}
		followTheGrid();
	}

	@Override
	public void paint(Graphics g) {
		updateMilestonesForConnection();
		updateBackgroundForConnection();
		updateConnectButton();
		super.paintComponents(g);
		//System.out.println("M : "+model.getMilestones().size());
		//System.out.println("S : "+model.getSequences().size());
		//System.out.println("C : "+model.getComments().size());
	}

	/**
	 * Update Background Color When Connection Mode Invoke
	 */
	public void updateBackgroundForConnection() {
		if (mode != Mode.NORMAL) {
			panel.setBackground(new Color(2,23,181));
		} else {
			panel.setBackground(Color.WHITE);
		}
	}

	/**
	 * Set visible or not button related to the connection mode 
	 */
	public void updateConnectButton() {
		if (mode == Mode.CONNECTION) {
			finalizeConnection.setVisible(true);
			cancelConnection.setVisible(true);
		} else {
			finalizeConnection.setVisible(false);
			cancelConnection.setVisible(false);
		}
	}

	// Getters & Setters
	/**
	 * @return model
	 */
	public StartUpSequence getModel() {
		return model;
	}

	/**
	 * @param model
	 */
	public void setModel(StartUpSequence model) {
		this.model = model;
	}


	/**
	 * @return Draw Panel
	 */
	public DrawPanel getDrawPanel() {
		return panel;
	}

	/**
	 * @return MilestoneRightClickMenu
	 */
	public JPopupMenu getMilestoneRightClickMenu() {
		return milestoneRightClickMenu;
	}
	
	public JMenuItem getOperatedMenuItem(){
		return operated;
	}
	
	/**
	 * @return StartUpTaskRightClickMenu
	 */
	public JPopupMenu getStartUpTaskRightClickMenu() {
		return startUpTaskRightClickMenu;
	}
	/**
	 * @return GeneralRightClickMenu
	 */
	public JPopupMenu getGeneralRightClickMenu() {
		return generalRightClickMenu;
	}

	
	/**
	 * @return SequenceBarRightClickMenu
	 */
	public JPopupMenu getSequenceBarRightClickMenu() {
		return sequenceBarRightClickMenu;
	}

	/**
	 * @return CommentRightClickMenu
	 */
	public JPopupMenu getCommentRightClickMenu(){
		return commentRightClickMenu;
	}
	
	
	/**
	 * @param milestoneSrcForTheConnection
	 * @param milestonesDestForTheConnection
	 * @param menuNotDisplayedYet
	 * @return ConnectorRightClickMenu
	 */
	public JPopupMenu getConnectorRightClickMenu(final int milestoneSrcForTheConnection, final ArrayList<Integer> milestonesDestForTheConnection,final int startUpStepSrcForTheConnection,final ArrayList<Integer> startUpStepsDestForTheConnection,boolean menuNotDisplayedYet){
		ImageIcon deleteImg = new ImageIcon(View.class.getResource("/img/delete-connection.png"));
		if(menuNotDisplayedYet){
			connectorRightClickMenu.setVisible(false);
			connectorRightClickMenu = new JPopupMenu();
			if(milestoneSrcForTheConnection!=0)
			{
				System.out.println("milestoneSrcForTheConnection");
				System.out.println("milestonesDestForTheConnection" + milestonesDestForTheConnection.size());
				System.out.println("startUpStepsDestForTheConnection" + startUpStepsDestForTheConnection.size());
				for(int i = 0 ; i < milestonesDestForTheConnection.size() ; i++){
					final int finalI=i;
					JMenuItem line = new JMenuItem(model.getMilestone(milestoneSrcForTheConnection).getName()+"----->"+model.getMilestone(milestonesDestForTheConnection.get(finalI)).getName());
					line.setIcon(deleteImg);
					connectorRightClickMenu.add(line);
					line.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							model.getMilestone(milestoneSrcForTheConnection).getDestMilestone().remove(model.getMilestone(milestonesDestForTheConnection.get(finalI)));
							getConnectorRightClickMenu(0, null,0,null, false).setVisible(false);
							v.getDrawPanel().repaint();
						}
					});
				}
				for(int i = 0 ; i < startUpStepsDestForTheConnection.size() ; i++){
					final int finalI=i;
					JMenuItem line = new JMenuItem(model.getMilestone(milestoneSrcForTheConnection).getName()+"----->"+model.getStartUpTask(startUpStepsDestForTheConnection.get(finalI)).getName());
					line.setIcon(deleteImg);
					connectorRightClickMenu.add(line);
					line.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							model.getMilestone(milestoneSrcForTheConnection).getDestSUT().remove(model.getStartUpTask(startUpStepsDestForTheConnection.get(finalI)));
							getConnectorRightClickMenu(0, null,0,null, false).setVisible(false);
							v.getDrawPanel().repaint();
						}
					});
				}
			}
			if(startUpStepSrcForTheConnection!=0)
			{
				System.out.println("SUSSrcForTheConnection");
				System.out.println("milestonesDestForTheConnection" + milestonesDestForTheConnection.size());
				System.out.println("startUpStepsDestForTheConnection" + startUpStepsDestForTheConnection.size());
				for(int i = 0 ; i < milestonesDestForTheConnection.size() ; i++){
					final int finalI=i;
					System.out.println(finalI);
					JMenuItem line = new JMenuItem(model.getStartUpTask(startUpStepSrcForTheConnection).getName()+"----->"+model.getMilestone(milestonesDestForTheConnection.get(finalI)).getName());
					line.setIcon(deleteImg);
					connectorRightClickMenu.add(line);
					line.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							model.getStartUpTask(startUpStepSrcForTheConnection).getDestMilestone().remove(model.getMilestone(milestonesDestForTheConnection.get(finalI)));
							getConnectorRightClickMenu(0, null,0,null, false).setVisible(false);
							v.getDrawPanel().repaint();
						}
					});
				}
				for(int i = 0 ; i < startUpStepsDestForTheConnection.size() ; i++){
					final int finalI=i;
					JMenuItem line = new JMenuItem(model.getStartUpTask(startUpStepSrcForTheConnection).getName()+"----->"+model.getStartUpTask(startUpStepsDestForTheConnection.get(finalI)).getName());
					line.setIcon(deleteImg);
					connectorRightClickMenu.add(line);
					line.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							model.getStartUpTask(startUpStepSrcForTheConnection).getDestSUT().remove(model.getStartUpTask(startUpStepsDestForTheConnection.get(finalI)));
							getConnectorRightClickMenu(0, null,0,null, false).setVisible(false);
							v.getDrawPanel().repaint();
						}
					});
				}
			}
		} 
		return connectorRightClickMenu;
	}

	/**
	 * @return ToolBarPanel
	 */
	public ToolBarPanel getToolBar() {
		return (ToolBarPanel) toolBar;
	}

	/**
	 * @param mode
	 */
	public void setMode(Mode mode) {
		this.mode = mode;
		updateMilestonesForConnection();
		updateStartUpTasksForConnection();
		updateBackgroundForConnection();
		updateConnectButton();
	}

	/**
	 * @param startUpSequenceTitle
	 */
	public void setSUSTitle(String startUpSequenceTitle) {
		this.model.getTitleBar().setName(startUpSequenceTitle);
	}

	/**
	 * @return startUpSequenceTitle
	 */
	public String getSUSTitle() {
		return this.model.getTitleBar().getName();
	}
	
	/**
	 * @return scrollPane
	 */
	public JScrollPane getScrollPane(){
		return scrollPane;
	}
	
	/**
	 * @return TitleBar
	 */
	public TitleBar getTitleBar() {
		return model.getTitleBar();
	}
	

	/**
	 * @return SaveMenuItem
	 */
	public JMenuItem getSaveMenuItem(){
		return mntmSave;
	}
	
	
	// Add of listeners
	/**
	 * @param mouseListener
	 */
	public void addPanelMouseListener(MouseListener mouseListener) {
		this.panel.addMouseListener(mouseListener);
	}

	/**
	 * @param mouseListener
	 */
	public void addToolBarListener(MouseListener mouseListener) {
		this.toolBar.addMouseListener(mouseListener);
	}

	/**
	 * @param mouseMotionListener
	 */
	public void addPanelMouseMotionListener(MouseMotionListener mouseMotionListener) {
		this.panel.addMouseMotionListener(mouseMotionListener);
	}

	/**
	 * @param mouseWheelListener
	 */
	public void addPanelMouseWheelListener(MouseWheelListener mouseWheelListener) {
		this.panel.addMouseWheelListener(mouseWheelListener);
	}

	/**
	 * @param mouseListener
	 */
	public void addToolBarMouseListener(MouseListener mouseListener) {
		this.toolBar.addMouseListener(mouseListener);
	}

	/**
	 * @param ma
	 */
	public void addRightClickMouseAdaptater(MouseAdapter ma) {
		this.panel.addMouseListener(ma);
	}

	/**
	 * @param al
	 */
	public void addDeleteMilestoneListener(ActionListener al) {
		this.deleteMenuItem.addActionListener(al);
		this.deleteMenuItem1.addActionListener(al);
		this.deleteMenuItem2.addActionListener(al);
		this.deleteMenuItem3.addActionListener(al);
	}

	/**
	 * @param al
	 */
	public void addEditMilestoneListener(ActionListener al) {
		this.editMenuItem.addActionListener(al);
		this.editMenuItem1.addActionListener(al);
		this.editMenuItem2.addActionListener(al);
		this.editMenuItem3.addActionListener(al);
	}

	public void addOperatedStartUpTaskListener(ActionListener al){
		this.operated.addActionListener(al);
	}
	/**
	 * @param al
	 */
	public void addConnectMilestoneListener(ActionListener al) {
		this.connectMenuItem.addActionListener(al);
		this.connectMenuItem1.addActionListener(al);
	}

	/**
	 * @param al
	 */
	public void addFinalizeConnectionListener(ActionListener al) {
		this.finalizeConnection.addActionListener(al);
	}
	
	/**
	 * @param al
	 */
	public void addCancelConnectionListener(ActionListener al){
		this.cancelConnection.addActionListener(al);
	}
	
	/**
	 * @param al
	 */
	public void addUpdateListener(ActionListener al){
		this.updateMenuItem.addActionListener(al);
	}
	
	/**
	 * @param al
	 */
	public void addPrintMenuItemListener(ActionListener al){
		this.printMenuItem.addActionListener(al);
	}
	
	/**
	 * @param al
	 */
	public void addSelectDocPath(ActionListener al){
		this.selectDocPath.addActionListener(al);
	}
	
	/**
	 * @param al
	 */
	public void addSaveAsListener(ActionListener al){
		this.saveMenuItem.addActionListener(al);
	}
	
	/**
	 * @param al
	 */
	public void addLoadListener(ActionListener al){
		this.loadMenuItem.addActionListener(al);
	}
	/**
	 * @param al
	 */
	public void addPreferenceListener(ActionListener al){
		this.preferences.addActionListener(al);
	}
	
	/**
	 * @param al
	 */
	public void addLoadAutosaveListener(ActionListener al){
		this.loadLastAutosave.addActionListener(al);
	}
	
	/**
	 * @param al
	 */
	public void addNewMilestoneMenuItemListener(ActionListener al){
		this.newMilestoneMenuItem.addActionListener(al);
	}
	
	/**
	 * @param al
	 */
	public void addNewSequenceMenuItemListener(ActionListener al){
		this.newSequenceMenuItem.addActionListener(al);
	}
	
	public void addNewStartUpTaskMenuItemListener(ActionListener al){
		this.newStartUpTaskMenuItem.addActionListener(al);
	}
	/**
	 * @param al
	 */
	public void addNewCommentMenuItemListener(ActionListener al){
		this.newCommentMenuItem.addActionListener(al);
	}
	
	/**
	 * @param al
	 */
	public void addNewMenuItemListener(ActionListener al){
		this.newMenuItem.addActionListener(al);
	}
	
	/**
	 * @param al
	 */
	public void addSetupTitleBlock(ActionListener al){
		this.mntmSetupTitleBlock.addActionListener(al);
	}
	
	/**
	 * @param al
	 */
	public void addManageRevisionListener(ActionListener al){
		this.mntmManageRevisions.addActionListener(al);
	}
	
	/**
	 * @param al
	 */
	public void addSaveListener(ActionListener al){
		this.mntmSave.addActionListener(al);
	}
	
	/**
	 * @param al
	 */
	public void addQuitListener(ActionListener al){
		this.mntmQuitStartUp.addActionListener(al);
	}
	
	/**
	 * @param al
	 */
	public void addProjectTargetListener(ActionListener al){
	    this.mntmShowProjectTarget.addActionListener(al);
	}
	
	@Override
	public void repaint() {
	    super.repaint();
	}

	/**
	 * @param al
	 */
	public void addShowProjectResumeListener(ActionListener al){
		this.mntmProjectResume.addActionListener(al);
	}
	
	/**
	 * @param al
	 */
	public void addPlusZoomListener(ActionListener al){
	    this.lblPlus.addActionListener(al);
	}
	
	/**
	 * @param al
	 */
	public void addMoinsZoomListener(ActionListener al){
	    this.lblMoins.addActionListener(al);
	}
	/**
	 * Alignement
	 * Method invoke to align milestones
	 */
	public void followTheGrid() {
		for (Integer milestoneId : model.getMilestones().keySet()) {
			Milestone m = model.getMilestone(milestoneId);
			// determine to which cell the milestone belongs
			int row = (int) Math.round(m.getDoubleX() / GeneralConfig.cellWidth);
			int col = (int) Math.round(m.getDoubleY() / GeneralConfig.cellHeight);
//			System.out.println(row + "-" + col);
			m.setX((int) Math.round(row * GeneralConfig.cellWidth) );
			m.setY((int) Math.round(col * GeneralConfig.cellHeight));

			//TODO i don't remember if the line below is necessary could be if a outofbound error raises on alignementCells array
			//alignementCells[row][col] = alignementCells[row][col] + 2;
		}
	}




	@Override
	public void repaint(long arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		super.repaint(arg0, arg1, arg2, arg3, arg4);
		followTheGrid();
		sequenceBarAlignement();
		startUpTaskAlignement();
		zoomValueLabel.setText((int)GeneralConfig.zoom+" %"); ;
	}

	/**
	 * Method to magnetized the sequence bar
	 */
	public void sequenceBarAlignement(){
		if(!resizing){
			for (int sbID : model.getSequences().keySet()){ 
				SequenceBar sb = model.getSequence(sbID);
				int row = (int) Math.round(sb.getDoubleX() / (GeneralConfig.cellWidth/2.0));
				int col =  (int) Math.round(sb.getDoubleY() / (GeneralConfig.cellHeight/2.0));
				//System.out.println(sb.getName()+" : "+row+"-"+col);
				sb.setX(row * (GeneralConfig.cellWidth/2.0));
				sb.setY(col * (GeneralConfig.cellHeight/2.0));
				//System.out.println(sb.getName()+" : "+(rowW-row) * (GeneralConfig.pageWidth/40.0)+"-"+(colH-col) * (GeneralConfig.pageHeight/60.0));
				double width = Math.round(sb.getWidth() / (GeneralConfig.cellWidth/2.0));
				double height = Math.round(sb.getHeight() / (GeneralConfig.cellHeight/2.0));
				sb.setWidth(width * (GeneralConfig.cellWidth/2.0));
				sb.setHeight(height * (GeneralConfig.cellHeight/2.0));
				sb.initBorders();
			}
		}
	}
	
	public void startUpTaskAlignement(){
		if(!resizing){
			for (int sbID : model.getStartUpTasks().keySet()){ 
				StartUpStep sb = model.getStartUpTask(sbID);
				int row = (int) Math.round(sb.getDoubleX() / (GeneralConfig.cellWidth/2.0));
				int col =  (int) Math.round(sb.getDoubleY() / (GeneralConfig.cellHeight/2.0));
				//System.out.println(sb.getName()+" : "+row+"-"+col);
				sb.setX(row * (GeneralConfig.cellWidth/2.0));
				sb.setY(col * (GeneralConfig.cellHeight/2.0));
				//System.out.println(sb.getName()+" : "+width+"/"+height);
				int width = (int)Math.round(sb.getWidth() / (GeneralConfig.cellWidth/2.0));
				int height = (int)Math.round(sb.getHeight() / (GeneralConfig.cellHeight/2.0));
				//sb.setWidth(width * (GeneralConfig.cellWidth/2.0));
				sb.setHeight(height * (GeneralConfig.cellHeight/2.0));
				sb.initBorders();
			}
		}
	}

	public void addShowStartUpStepListener(ActionListener showStartUpStepListener) {
		this.mntmShowStartUp.addActionListener(showStartUpStepListener);
		
	}

	
	public boolean isResizing() {
		return resizing;
	}

	public void setResizing(boolean resizing) {
		this.resizing = resizing;
	}
	
}
