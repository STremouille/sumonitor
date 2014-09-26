package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.TreeMap;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Milestone;
import conf.GeneralConfig;
import database.Database;
import database.DatabaseTableModel;

import javax.swing.JSlider;


import javax.swing.JToggleButton;
import javax.swing.BoxLayout;

import steps.StepsTableModel;

import java.awt.CardLayout;

/**
 * 
 * @author S.Trémouille
 *
 */

public class SettingsFrame extends JFrame {
	private static final long serialVersionUID = -7885642364601592533L;
	private JButton okButton,cancelButton;
	private SettingsFrame v = this;

	private TreeMap<Integer, Milestone> m;
	private JPanel panel_4;
	private JPanel panel;
	private JLabel label;
	private JTextField startUpDocumentationPath;
	private JButton browseButton;
	private JLabel label_1;
	private JComboBox<?> comboBox;
	private JLabel label_2;
	private JLabel label_3;
	private JCheckBox chckbxTitle;
	private JLabel label_4;
	private JPanel panel_2;
	private JRadioButton rdbtnLeft;
	private JRadioButton leftAligned;
	private JRadioButton rdbtnCenter;
	private JRadioButton rdbtnRight;
	private JLabel lblProjectTitleBlock_1;
	private JCheckBox chckbxNewCheckBox_1;
	private JRadioButton rightAligned;
	private JPanel panel_6;
	private JPanel panel_7;
	private JPanel panel_8;
	private JButton btnNew;
	private JButton btnDelete;
	private JButton btnEdit;
	private JScrollPane scrollPane;
	private JTable table;
	private DatabaseTableModel dtm;
	private SettingsFrame sf = this;
	private JLabel lblProjectTitleBlock;
	private JPanel panel_1;
	private JPanel expertPanel;
	private JLabel lblResetTargetCache;
	private JButton btnResetTargetCache;
	private JLabel lblResetSubsystemCache;
	private JButton btnResetSubsystemCache;
	private JLabel lblNewLabel;
	private JPanel panel_10;
	private JTabbedPane tabbedPane;
	private JTextField excelFileCell;
	private JLabel lblCellReferenceIn;
	private JPanel panel_9;
	private JButton btnSubsystem;
	private JButton btnTarget;
	private JLabel lblCellReference;
	private JTextField excelFileCellTextField;
	private JLabel lblGraphicQuality;
	private JPanel panel_11;
	private JToggleButton tglbtnSpeed;
	private JToggleButton tglbtnFancy;
	private JLabel lblPageSizeRatio;
	private JPanel panel_12;
	private JLabel lblWidth;
	private JButton button;
	private JButton button_1;
	private JLabel lblHeight;
	private JButton button_2;
	private JButton button_3;
	private JTextField textField;
	private JTextField textField_1;
	private JPanel milestonePanel;
	private JPanel panel_13;
	private JLabel label_5;
	private JPanel panel_3;
	private JCheckBox checkBox;
	private JCheckBox checkBox_1;
	private JCheckBox checkBox_2;
	private JCheckBox checkBox_3;
	private JLabel label_6;
	private JTextField textField_2;
	private JLabel label_7;
	private JSlider slider;
	private JPanel panel_14;
	private JButton btnAddField,btnDelField,btnEditField;
	private JTable stepsTable;
	private JCheckBox chckbxStepsProgress;
	private JPanel panel_5;
	private JLabel lblCharacterLimit;
	private JTextField characterLimit;
	
	

	/**
	 * Constructor
	 * @param model
	 */
	public SettingsFrame(TreeMap<Integer, Milestone> model) {
		this.m=model;
				
		setIconImage(Toolkit.getDefaultToolkit().getImage(MilestoneEditorFrame.class.getResource("/img/icone.png")));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setTitle("Settings");
		setBounds(100, 100, 781, 462);
		getContentPane().setLayout(new BorderLayout());
		{
			tabbedPane = new JTabbedPane(SwingConstants.TOP);
			tabbedPane.setFont(new Font("Arial", Font.PLAIN, 12));
			getContentPane().add(tabbedPane, BorderLayout.CENTER);
			panel_4 = new JPanel();
			tabbedPane.addTab("General", null, panel_4, null);
			panel_4.setLayout(new BorderLayout(0, 0));
			panel = new JPanel();
			panel_4.add(panel, BorderLayout.CENTER);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{0, 0, 0, 0};
			gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			gbl_panel.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				label = new JLabel("Start Up Documentation File Location :");
				GridBagConstraints gbc_label = new GridBagConstraints();
				gbc_label.insets = new Insets(0, 0, 5, 5);
				gbc_label.gridx = 0;
				gbc_label.gridy = 0;
				panel.add(label, gbc_label);
				label.setFont(new Font("Arial", Font.PLAIN, 12));
			}
			{
				startUpDocumentationPath = new JTextField(GeneralConfig.SUDocPath);
				GridBagConstraints gbc_startUpDocumentationPath = new GridBagConstraints();
				gbc_startUpDocumentationPath.insets = new Insets(0, 0, 5, 5);
				gbc_startUpDocumentationPath.gridx = 1;
				gbc_startUpDocumentationPath.gridy = 0;
				System.out.println(GeneralConfig.SUDocPath);
				panel.add(startUpDocumentationPath, gbc_startUpDocumentationPath);
				startUpDocumentationPath.setFont(new Font("Arial", Font.PLAIN, 14));
				startUpDocumentationPath.setEditable(true);
			}
			{
				browseButton = new JButton("Browse");
				GridBagConstraints gbc_browseButton = new GridBagConstraints();
				gbc_browseButton.insets = new Insets(0, 0, 5, 0);
				gbc_browseButton.gridx = 2;
				gbc_browseButton.gridy = 0;
				panel.add(browseButton, gbc_browseButton);
				browseButton.setFont(new Font("Arial", Font.PLAIN, 12));
			}
			{
				lblCellReference = new JLabel("Cell reference :");
				lblCellReference.setFont(new Font("Arial", Font.PLAIN, 12));
				GridBagConstraints gbc_lblCellReference = new GridBagConstraints();
				gbc_lblCellReference.anchor = GridBagConstraints.EAST;
				gbc_lblCellReference.insets = new Insets(0, 0, 5, 5);
				gbc_lblCellReference.gridx = 0;
				gbc_lblCellReference.gridy = 1;
				panel.add(lblCellReference, gbc_lblCellReference);
			}
			{
				excelFileCellTextField = new JTextField(GeneralConfig.excelFileCell);
				excelFileCellTextField.setFont(new Font("Arial", Font.PLAIN, 14));
				GridBagConstraints gbc_textField = new GridBagConstraints();
				gbc_textField.fill = GridBagConstraints.HORIZONTAL;
				gbc_textField.insets = new Insets(0, 0, 5, 5);
				gbc_textField.gridx = 1;
				gbc_textField.gridy = 1;
				panel.add(excelFileCellTextField, gbc_textField);
				excelFileCellTextField.setColumns(10);
			}
			{
				label_1 = new JLabel("Target Point Accuracy :");
				label_1.setFont(new Font("Arial", Font.PLAIN, 12));
				GridBagConstraints gbc_label_1 = new GridBagConstraints();
				gbc_label_1.anchor = GridBagConstraints.EAST;
				gbc_label_1.insets = new Insets(0, 0, 5, 5);
				gbc_label_1.gridx = 0;
				gbc_label_1.gridy = 2;
				panel.add(label_1, gbc_label_1);
			}
			Integer[] val = new Integer[]{100,150,200,500,1000,2000,5000,10000};
			int i=Arrays.binarySearch(val, GeneralConfig.targetAccuracy);
			comboBox = new JComboBox<Object>(val);
			comboBox.setSelectedIndex(i);
			comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.insets = new Insets(0, 0, 5, 5);
			gbc_comboBox.gridx = 1;
			gbc_comboBox.gridy = 2;
			panel.add(comboBox, gbc_comboBox);
			{
				{
					label_2 = new JLabel("(default 100)");
					GridBagConstraints gbc_label_2 = new GridBagConstraints();
					gbc_label_2.insets = new Insets(0, 0, 5, 0);
					gbc_label_2.gridx = 2;
					gbc_label_2.gridy = 2;
					panel.add(label_2, gbc_label_2);
				}
			}
			{
				label_3 = new JLabel("Title Enable :");
				label_3.setFont(new Font("Arial", Font.PLAIN, 12));
				GridBagConstraints gbc_label_3 = new GridBagConstraints();
				gbc_label_3.anchor = GridBagConstraints.EAST;
				gbc_label_3.insets = new Insets(0, 0, 5, 5);
				gbc_label_3.gridx = 0;
				gbc_label_3.gridy = 3;
				panel.add(label_3, gbc_label_3);
			}
			chckbxTitle = new JCheckBox("", GeneralConfig.titleEnable);
			chckbxTitle.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(chckbxTitle.isSelected()){
						rdbtnLeft.setEnabled(true);
						rdbtnCenter.setEnabled(true);
						rdbtnRight.setEnabled(true);
					} else {
						rdbtnLeft.setEnabled(false);
						rdbtnCenter.setEnabled(false);
						rdbtnRight.setEnabled(false);
					}
				}
			});
			GridBagConstraints gbc_chckbxTitle = new GridBagConstraints();
			gbc_chckbxTitle.fill = GridBagConstraints.VERTICAL;
			gbc_chckbxTitle.anchor = GridBagConstraints.WEST;
			gbc_chckbxTitle.insets = new Insets(0, 0, 5, 5);
			gbc_chckbxTitle.gridx = 1;
			gbc_chckbxTitle.gridy = 3;
			panel.add(chckbxTitle, gbc_chckbxTitle);
			{
				label_4 = new JLabel("Title alignement :");
				label_4.setFont(new Font("Arial", Font.PLAIN, 12));
				GridBagConstraints gbc_label_4 = new GridBagConstraints();
				gbc_label_4.anchor = GridBagConstraints.EAST;
				gbc_label_4.insets = new Insets(0, 0, 5, 5);
				gbc_label_4.gridx = 0;
				gbc_label_4.gridy = 4;
				panel.add(label_4, gbc_label_4);
			}
			panel_2 = new JPanel();
			GridBagConstraints gbc_panel_2 = new GridBagConstraints();
			gbc_panel_2.fill = GridBagConstraints.HORIZONTAL;
			gbc_panel_2.insets = new Insets(0, 0, 5, 5);
			gbc_panel_2.gridx = 1;
			gbc_panel_2.gridy = 4;
			panel.add(panel_2, gbc_panel_2);
			panel_2.setLayout(new GridLayout(0, 3, 0, 0));
			{
				rdbtnCenter = new JRadioButton("Center");
				rdbtnCenter.setFont(new Font("Arial", Font.PLAIN, 14));
				rdbtnCenter.setHorizontalAlignment(SwingConstants.LEFT);
				rdbtnCenter.setSelected(!GeneralConfig.leftAlignedTitleBar&&GeneralConfig.centeredTitleBar);
				rdbtnCenter.setEnabled(GeneralConfig.titleEnable);
				rdbtnCenter.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						rdbtnLeft.setSelected(false);
						rdbtnCenter.setSelected(true);
						rdbtnRight.setSelected(false);
					}
				});
				{
					rdbtnLeft = new JRadioButton("Left");
					rdbtnLeft.setFont(new Font("Arial", Font.PLAIN, 14));
					rdbtnLeft.setHorizontalAlignment(SwingConstants.LEFT);
					rdbtnLeft.setEnabled(GeneralConfig.titleEnable);
					rdbtnLeft.setSelected(GeneralConfig.leftAlignedTitleBar&&!GeneralConfig.centeredTitleBar);
					panel_2.add(rdbtnLeft);
					rdbtnLeft.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							rdbtnLeft.setSelected(true);
							rdbtnCenter.setSelected(false);
							rdbtnRight.setSelected(false);
						}
					});
				}
				panel_2.add(rdbtnCenter);
				
				rdbtnRight = new JRadioButton("Right");
				rdbtnRight.setFont(new Font("Arial", Font.PLAIN, 14));
				rdbtnRight.setHorizontalAlignment(SwingConstants.LEFT);
				rdbtnRight.setEnabled(GeneralConfig.titleEnable);
				rdbtnRight.setSelected(!GeneralConfig.leftAlignedTitleBar&&!GeneralConfig.centeredTitleBar);
				panel_2.add(rdbtnRight);
				rdbtnRight.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						rdbtnLeft.setSelected(false);
						rdbtnCenter.setSelected(false);
						rdbtnRight.setSelected(true);
					}
				});
				panel_2.add(rdbtnRight);
			}
			{
				{
					{
						lblProjectTitleBlock_1 = new JLabel("Project title block enable :");
						lblProjectTitleBlock_1.setFont(new Font("Arial", Font.PLAIN, 12));
						GridBagConstraints gbc_lblProjectTitleBlock_1 = new GridBagConstraints();
						gbc_lblProjectTitleBlock_1.anchor = GridBagConstraints.EAST;
						gbc_lblProjectTitleBlock_1.insets = new Insets(0, 0, 5, 5);
						gbc_lblProjectTitleBlock_1.gridx = 0;
						gbc_lblProjectTitleBlock_1.gridy = 5;
						panel.add(lblProjectTitleBlock_1, gbc_lblProjectTitleBlock_1);
					}
				}
			}
			{
				{
				}
			}
			chckbxNewCheckBox_1 = new JCheckBox("", GeneralConfig.titleBlockEnable);
			chckbxNewCheckBox_1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(chckbxNewCheckBox_1.isSelected()){
						leftAligned.setEnabled(true);
						rightAligned.setEnabled(true);
					} else {
						leftAligned.setEnabled(false);
						rightAligned.setEnabled(false);
					}
				}
			});
			GridBagConstraints gbc_chckbxNewCheckBox_1 = new GridBagConstraints();
			gbc_chckbxNewCheckBox_1.fill = GridBagConstraints.VERTICAL;
			gbc_chckbxNewCheckBox_1.anchor = GridBagConstraints.WEST;
			gbc_chckbxNewCheckBox_1.insets = new Insets(0, 0, 5, 5);
			gbc_chckbxNewCheckBox_1.gridx = 1;
			gbc_chckbxNewCheckBox_1.gridy = 5;
			panel.add(chckbxNewCheckBox_1, gbc_chckbxNewCheckBox_1);
			{
				lblProjectTitleBlock = new JLabel("Project Title Block alignement :");
				lblProjectTitleBlock.setFont(new Font("Arial", Font.PLAIN, 12));
				lblProjectTitleBlock.setHorizontalAlignment(SwingConstants.RIGHT);
				GridBagConstraints gbc_lblProjectTitleBlock = new GridBagConstraints();
				gbc_lblProjectTitleBlock.anchor = GridBagConstraints.EAST;
				gbc_lblProjectTitleBlock.insets = new Insets(0, 0, 5, 5);
				gbc_lblProjectTitleBlock.gridx = 0;
				gbc_lblProjectTitleBlock.gridy = 6;
				panel.add(lblProjectTitleBlock, gbc_lblProjectTitleBlock);
			}
			{
				panel_1 = new JPanel();
				GridBagConstraints gbc_panel_1 = new GridBagConstraints();
				gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
				gbc_panel_1.anchor = GridBagConstraints.EAST;
				gbc_panel_1.insets = new Insets(0, 0, 5, 5);
				gbc_panel_1.gridx = 1;
				gbc_panel_1.gridy = 6;
				panel.add(panel_1, gbc_panel_1);
				panel_1.setLayout(new GridLayout(0, 2, 0, 0));
				leftAligned = new JRadioButton("Left aligned");
				leftAligned.setFont(new Font("Arial", Font.PLAIN, 14));
				leftAligned.setHorizontalAlignment(SwingConstants.LEFT);
				panel_1.add(leftAligned);
				leftAligned.setSelected(!GeneralConfig.titleBlockRightAligned);
				leftAligned.setEnabled(GeneralConfig.titleBlockEnable);
				leftAligned.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						leftAligned.setSelected(true);
						rightAligned.setSelected(false);
					}
				});
				{
					leftAligned.setEnabled(GeneralConfig.titleBlockEnable);
				}
				rightAligned = new JRadioButton("Right aligned");
				rightAligned.setFont(new Font("Arial", Font.PLAIN, 14));
				rightAligned.setHorizontalAlignment(SwingConstants.LEFT);
				panel_1.add(rightAligned);
				rightAligned.setEnabled(GeneralConfig.titleBlockEnable);
				rightAligned.setSelected(GeneralConfig.titleBlockRightAligned);
				{
					lblNewLabel = new JLabel("Reset Cache :");
					lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 12));
					lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
					lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
					GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
					gbc_lblNewLabel.anchor = GridBagConstraints.NORTHEAST;
					gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
					gbc_lblNewLabel.gridx = 0;
					gbc_lblNewLabel.gridy = 7;
					panel.add(lblNewLabel, gbc_lblNewLabel);
				}
				{
					panel_10 = new JPanel();
					GridBagConstraints gbc_panel_10 = new GridBagConstraints();
					gbc_panel_10.insets = new Insets(0, 0, 5, 5);
					gbc_panel_10.fill = GridBagConstraints.BOTH;
					gbc_panel_10.gridx = 1;
					gbc_panel_10.gridy = 7;
					panel.add(panel_10, gbc_panel_10);
					panel_10.setLayout(new GridLayout(1, 0, 0, 0));
					{
						panel_9 = new JPanel();
						panel_10.add(panel_9);
						GridBagLayout gbl_panel_9 = new GridBagLayout();
						gbl_panel_9.columnWidths = new int[]{169, 0};
						gbl_panel_9.rowHeights = new int[]{25, 0, 0};
						gbl_panel_9.columnWeights = new double[]{0.0, Double.MIN_VALUE};
						gbl_panel_9.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
						panel_9.setLayout(gbl_panel_9);
						{
							btnSubsystem = new JButton("Sub-system list");
							btnSubsystem.setFont(new Font("Arial", Font.PLAIN, 14));
							GridBagConstraints gbc_btnSubsystem = new GridBagConstraints();
							gbc_btnSubsystem.fill = GridBagConstraints.HORIZONTAL;
							gbc_btnSubsystem.insets = new Insets(0, 0, 5, 0);
							gbc_btnSubsystem.gridx = 0;
							gbc_btnSubsystem.gridy = 0;
							panel_9.add(btnSubsystem, gbc_btnSubsystem);
							btnSubsystem.addActionListener(new ClearSubsystemCacheListener());
						}
						{
							btnTarget = new JButton("Target");
							btnTarget.setFont(new Font("Arial", Font.PLAIN, 14));
							GridBagConstraints gbc_btnTarget = new GridBagConstraints();
							gbc_btnTarget.fill = GridBagConstraints.HORIZONTAL;
							gbc_btnTarget.gridx = 0;
							gbc_btnTarget.gridy = 1;
							panel_9.add(btnTarget, gbc_btnTarget);
							{
								lblGraphicQuality = new JLabel("Graphic Quality :");
								lblGraphicQuality.setFont(new Font("Arial", Font.PLAIN, 12));
								GridBagConstraints gbc_lblGraphicQuality = new GridBagConstraints();
								gbc_lblGraphicQuality.anchor = GridBagConstraints.EAST;
								gbc_lblGraphicQuality.insets = new Insets(0, 0, 5, 5);
								gbc_lblGraphicQuality.gridx = 0;
								gbc_lblGraphicQuality.gridy = 8;
								panel.add(lblGraphicQuality, gbc_lblGraphicQuality);
							}
							{
								panel_11 = new JPanel();
								GridBagConstraints gbc_panel_11 = new GridBagConstraints();
								gbc_panel_11.insets = new Insets(0, 0, 5, 5);
								gbc_panel_11.fill = GridBagConstraints.BOTH;
								gbc_panel_11.gridx = 1;
								gbc_panel_11.gridy = 8;
								panel.add(panel_11, gbc_panel_11);
								panel_11.setLayout(new GridLayout(0, 2, 0, 0));
								{
									tglbtnFancy = new JToggleButton("High",GeneralConfig.graphicQuality);
									tglbtnFancy.addActionListener(new ActionListener() {
									    
									    @Override
									    public void actionPerformed(ActionEvent arg0) {
										GeneralConfig.graphicQuality=true;
										tglbtnFancy.setSelected(true);
										tglbtnSpeed.setSelected(false);
									    }
									});
									tglbtnFancy.setFont(new Font("Arial", Font.PLAIN, 14));
									panel_11.add(tglbtnFancy);
								}
								{
									tglbtnSpeed = new JToggleButton("Low",!GeneralConfig.graphicQuality);
									tglbtnSpeed.setFont(new Font("Arial", Font.PLAIN, 14));
									tglbtnSpeed.addActionListener(new ActionListener() {
									    
									    @Override
									    public void actionPerformed(ActionEvent arg0) {
										GeneralConfig.graphicQuality=false;
										tglbtnFancy.setSelected(false);
										tglbtnSpeed.setSelected(true);
									    }
									});
									panel_11.add(tglbtnSpeed);
								}
							}
							{
								lblPageSizeRatio = new JLabel("Page Size Ratio :");
								lblPageSizeRatio.setFont(new Font("Arial", Font.PLAIN, 12));
								GridBagConstraints gbc_lblPageSizeRatio = new GridBagConstraints();
								gbc_lblPageSizeRatio.anchor = GridBagConstraints.EAST;
								gbc_lblPageSizeRatio.insets = new Insets(0, 0, 0, 5);
								gbc_lblPageSizeRatio.gridx = 0;
								gbc_lblPageSizeRatio.gridy = 9;
								panel.add(lblPageSizeRatio, gbc_lblPageSizeRatio);
							}
							{
								panel_12 = new JPanel();
								GridBagConstraints gbc_panel_12 = new GridBagConstraints();
								gbc_panel_12.insets = new Insets(0, 0, 0, 5);
								gbc_panel_12.fill = GridBagConstraints.BOTH;
								gbc_panel_12.gridx = 1;
								gbc_panel_12.gridy = 9;
								panel.add(panel_12, gbc_panel_12);
								GridBagLayout gbl_panel_12 = new GridBagLayout();
								gbl_panel_12.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
								gbl_panel_12.rowHeights = new int[]{0, 0};
								gbl_panel_12.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
								gbl_panel_12.rowWeights = new double[]{0.0, Double.MIN_VALUE};
								panel_12.setLayout(gbl_panel_12);
								{
									lblWidth = new JLabel("Width :");
									GridBagConstraints gbc_lblWidth = new GridBagConstraints();
									gbc_lblWidth.insets = new Insets(0, 0, 0, 5);
									gbc_lblWidth.gridx = 0;
									gbc_lblWidth.gridy = 0;
									panel_12.add(lblWidth, gbc_lblWidth);
								}
								{
									button = new JButton("-");
									button.addActionListener(new ActionListener() {
										
										@Override
										public void actionPerformed(ActionEvent arg0) {
											if(Float.valueOf(textField.getText())>1){
												textField.setText(String.valueOf((float)(Float.valueOf(textField.getText())-1)));
											}
										}
									});
									GridBagConstraints gbc_button = new GridBagConstraints();
									gbc_button.insets = new Insets(0, 0, 0, 5);
									gbc_button.gridx = 1;
									gbc_button.gridy = 0;
									panel_12.add(button, gbc_button);
								}
								{
									textField = new JTextField(String.valueOf(GeneralConfig.pageWidthCoeff));
									GridBagConstraints gbc_textField = new GridBagConstraints();
									gbc_textField.insets = new Insets(0, 0, 0, 5);
									gbc_textField.fill = GridBagConstraints.HORIZONTAL;
									gbc_textField.gridx = 2;
									gbc_textField.gridy = 0;
									panel_12.add(textField, gbc_textField);
									textField.setColumns(10);
								}
								{
									button_1 = new JButton("+");
									button_1.addActionListener(new ActionListener() {
										
										@Override
										public void actionPerformed(ActionEvent arg0) {
											if(Float.valueOf(textField.getText())<10){
												textField.setText(String.valueOf((float)(Float.valueOf(textField.getText())+1)));
											}
										}
									});
									GridBagConstraints gbc_button_1 = new GridBagConstraints();
									gbc_button_1.insets = new Insets(0, 0, 0, 5);
									gbc_button_1.gridx = 3;
									gbc_button_1.gridy = 0;
									panel_12.add(button_1, gbc_button_1);
								}
								{
									lblHeight = new JLabel("Height :");
									GridBagConstraints gbc_lblHeight = new GridBagConstraints();
									gbc_lblHeight.insets = new Insets(0, 0, 0, 5);
									gbc_lblHeight.gridx = 4;
									gbc_lblHeight.gridy = 0;
									panel_12.add(lblHeight, gbc_lblHeight);
								}
								{
									button_2 = new JButton("-");
									button_2.addActionListener(new ActionListener() {
										
										@Override
										public void actionPerformed(ActionEvent arg0) {
											if(Float.valueOf(textField_1.getText())>1){
												textField_1.setText(String.valueOf((float)(Float.valueOf(textField_1.getText())-1)));
											}
										}
									});
									GridBagConstraints gbc_button_2 = new GridBagConstraints();
									gbc_button_2.insets = new Insets(0, 0, 0, 5);
									gbc_button_2.gridx = 5;
									gbc_button_2.gridy = 0;
									panel_12.add(button_2, gbc_button_2);
								}
								{
									textField_1 = new JTextField(String.valueOf(GeneralConfig.pageHeightCoeff));
									GridBagConstraints gbc_textField_1 = new GridBagConstraints();
									gbc_textField_1.insets = new Insets(0, 0, 0, 5);
									gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
									gbc_textField_1.gridx = 6;
									gbc_textField_1.gridy = 0;
									panel_12.add(textField_1, gbc_textField_1);
									textField_1.setColumns(10);
								}
								{
									button_3 = new JButton("+");
									button_3.addActionListener(new ActionListener() {
										
										@Override
										public void actionPerformed(ActionEvent arg0) {
											if(Float.valueOf(textField_1.getText())<10){
												textField_1.setText(String.valueOf((float)(Float.valueOf(textField_1.getText())+1)));
											}
										}
									});
									GridBagConstraints gbc_button_3 = new GridBagConstraints();
									gbc_button_3.gridx = 7;
									gbc_button_3.gridy = 0;
									panel_12.add(button_3, gbc_button_3);
								}
							}
							{
							}
							btnTarget.addActionListener(new ClearTargetCacheListener());
						}
					}
				}
				rightAligned.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						leftAligned.setSelected(false);
						rightAligned.setSelected(true);
					}
				});
			}
			{
				
				{
					{
						panel_6 = new JPanel();
						tabbedPane.addTab("Databases", null, panel_6, null);
						panel_6.setLayout(new BorderLayout(0, 0));
						{
							panel_7 = new JPanel();
							panel_6.add(panel_7, BorderLayout.CENTER);
							panel_7.setLayout(new BorderLayout(0, 0));
							{
								panel_8 = new JPanel();
								panel_7.add(panel_8, BorderLayout.SOUTH);
								{
									btnNew = new JButton("New");
									btnNew.addActionListener(new ActionListener() {
										
										@Override
										public void actionPerformed(ActionEvent arg0) {
											DataBaseFrame dbf = new DataBaseFrame(new Database(),-1,sf);
											dbf.setVisible(true);
										}
									});
									panel_8.add(btnNew);
								}
								{
									btnEdit = new JButton("Edit");
									btnEdit.addActionListener(new ActionListener() {
										
										@Override
										public void actionPerformed(ActionEvent arg0) {
											if(table.getSelectedRow()>=0){
												DataBaseFrame dbf = new DataBaseFrame(GeneralConfig.databases.get(table.getSelectedRow()),table.getSelectedRow(),sf);
												dbf.setVisible(true);
											}
										}
									});
									panel_8.add(btnEdit);
								}
								{
									btnDelete = new JButton("Delete");
									btnDelete.addActionListener(new ActionListener() {
										
										@Override
										public void actionPerformed(ActionEvent arg0) {
											if(table.getSelectedRow()>=0){
												GeneralConfig.databases.remove(table.getSelectedRow());
												dtm.fireTableDataChanged();
											}
										}
									});
									panel_8.add(btnDelete);
								}
							}
							{
								dtm = new DatabaseTableModel(GeneralConfig.databases);
								table = new JTable();
								table.setFont(new Font("Arial", Font.PLAIN, 12));
								table.setFillsViewportHeight(true);
								table.setModel(dtm);
								table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
								table.setAutoCreateRowSorter(true);
								table.setRowSelectionAllowed(true);
								scrollPane = new JScrollPane(table);
								panel_7.add(scrollPane, BorderLayout.CENTER);
							}
						}
					}
					//System.out.println(GeneralConfig.TCPIPConnection);
				}
			}
			{
				milestonePanel = new JPanel();
				tabbedPane.addTab("Milestone", null, milestonePanel, null);
				GridBagLayout gbl_milestonePanel = new GridBagLayout();
				gbl_milestonePanel.columnWidths = new int[]{0, 0, 0};
				gbl_milestonePanel.rowHeights = new int[]{0, 0, 0, 0};
				gbl_milestonePanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
				gbl_milestonePanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
				milestonePanel.setLayout(gbl_milestonePanel);
				{
					label_5 = new JLabel("Indicators on Milestones :");
					label_5.setVerticalAlignment(SwingConstants.TOP);
					label_5.setFont(new Font("Arial", Font.PLAIN, 12));
					GridBagConstraints gbc_label_5 = new GridBagConstraints();
					gbc_label_5.insets = new Insets(0, 0, 5, 5);
					gbc_label_5.gridx = 0;
					gbc_label_5.gridy = 0;
					milestonePanel.add(label_5, gbc_label_5);
				}
				{
					panel_3 = new JPanel();
					GridBagConstraints gbc_panel_3 = new GridBagConstraints();
					gbc_panel_3.insets = new Insets(0, 0, 5, 0);
					gbc_panel_3.fill = GridBagConstraints.BOTH;
					gbc_panel_3.gridx = 1;
					gbc_panel_3.gridy = 0;
					milestonePanel.add(panel_3, gbc_panel_3);
					panel_3.setLayout(new GridLayout(0, 2, 0, 0));
					{
						checkBox = new JCheckBox("Punch Items Opened");
						checkBox.setVerticalAlignment(SwingConstants.TOP);
						checkBox.setSelected(GeneralConfig.plDisplayed);
						checkBox.setFont(new Font("Arial", Font.PLAIN, 14));
						panel_3.add(checkBox);
					}
					{
						checkBox_1 = new JCheckBox("SubSystems AOC");
						checkBox_1.setVerticalAlignment(SwingConstants.TOP);
						checkBox_1.setSelected(GeneralConfig.ssAOCdisplayed);
						checkBox_1.setFont(new Font("Arial", Font.PLAIN, 14));
						panel_3.add(checkBox_1);
					}
					{
						checkBox_2 = new JCheckBox("Commissioning Progress");
						checkBox_2.setVerticalAlignment(SwingConstants.TOP);
						checkBox_2.setSelected(GeneralConfig.commProgressDisplayed);
						checkBox_2.setFont(new Font("Arial", Font.PLAIN, 14));
						panel_3.add(checkBox_2);
					}
					{
						checkBox_3 = new JCheckBox("Documentation Progress");
						checkBox_3.setVerticalAlignment(SwingConstants.TOP);
						checkBox_3.setSelected(GeneralConfig.docProgressDisplayed);
						checkBox_3.setFont(new Font("Arial", Font.PLAIN, 14));
						panel_3.add(checkBox_3);
					}
					{
						chckbxStepsProgress = new JCheckBox("Steps Progress");
						chckbxStepsProgress.setSelected(GeneralConfig.stepProgress);
						chckbxStepsProgress.setFont(new Font("Arial", Font.PLAIN, 14));
						panel_3.add(chckbxStepsProgress);
					}
				}
				{
					label_6 = new JLabel("Fourth label on Milestones :");
					label_6.setFont(new Font("Arial", Font.PLAIN, 12));
					GridBagConstraints gbc_label_6 = new GridBagConstraints();
					gbc_label_6.anchor = GridBagConstraints.EAST;
					gbc_label_6.insets = new Insets(0, 0, 5, 5);
					gbc_label_6.gridx = 0;
					gbc_label_6.gridy = 1;
					milestonePanel.add(label_6, gbc_label_6);
				}
				{
					textField_2 = new JTextField((String) GeneralConfig.labelForDoc);
					textField_2.setColumns(10);
					GridBagConstraints gbc_textField_2 = new GridBagConstraints();
					gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
					gbc_textField_2.insets = new Insets(0, 0, 5, 0);
					gbc_textField_2.gridx = 1;
					gbc_textField_2.gridy = 1;
					milestonePanel.add(textField_2, gbc_textField_2);
				}
				{
					label_7 = new JLabel("Milestone alignement grid size :");
					label_7.setFont(new Font("Arial", Font.PLAIN, 12));
					GridBagConstraints gbc_label_7 = new GridBagConstraints();
					gbc_label_7.insets = new Insets(0, 0, 0, 5);
					gbc_label_7.gridx = 0;
					gbc_label_7.gridy = 2;
					milestonePanel.add(label_7, gbc_label_7);
				}
				{
					final int initSize = GeneralConfig.milestoneAlignementGridSize;
					Dictionary<Integer, JLabel> dic = new Hashtable<Integer, JLabel>();
					dic.put(1, new JLabel("Large"));
					dic.put(5, new JLabel("Small"));
					dic.put(3, new JLabel("Medium"));
					slider = new JSlider(SwingConstants.HORIZONTAL, 1, 5, initSize);
					slider.setPaintTicks(true);
					slider.setPaintLabels(true);
					slider.setMinorTickSpacing(1);
					slider.setMajorTickSpacing(2);
					slider.setLabelTable(dic);
					slider.setValue(initSize);
					GridBagConstraints gbc_slider = new GridBagConstraints();
					gbc_slider.anchor = GridBagConstraints.WEST;
					gbc_slider.gridx = 1;
					gbc_slider.gridy = 2;
					milestonePanel.add(slider, gbc_slider);
				}
			}
			{
				panel_13 = new JPanel();
				tabbedPane.addTab("Step", null, panel_13, null);
				panel_13.setLayout(new BorderLayout(0, 0));
				{
					panel_14 = new JPanel();
					panel_13.add(panel_14, BorderLayout.SOUTH);
					{
						btnAddField = new JButton("Add Field");
						btnAddField.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								StepField sf = new StepField(-1,v);
								sf.setVisible(true);
							}
						});
						panel_14.add(btnAddField);
						
						btnEditField = new JButton("Edit Field");
						btnEditField.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								//System.out.println(stepsTable.getSelectedRow());
								if(stepsTable.getSelectedRow()>=0){
									StepField sf = new StepField(stepsTable.getSelectedRow(),v);
									sf.setVisible(true);
								} else {
									JOptionPane.showMessageDialog(v, "Please select a row");
								}
							}
						});
						panel_14.add(btnEditField);
						
						btnDelField = new JButton("Delete Field");
						btnDelField.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								int toDelete = stepsTable.getSelectedRow();
								if(GeneralConfig.stepsField.get(toDelete)!=null){
									GeneralConfig.stepsField.remove(toDelete);
								}
								stepsTable.repaint();
							}
						});
						panel_14.add(btnDelField);
					}
				}
				{
					stepsTable = new JTable();
					stepsTable.setModel(new StepsTableModel());
					JScrollPane pane = new JScrollPane(stepsTable);
					panel_13.add(pane, BorderLayout.CENTER);
				}
				{
					panel_5 = new JPanel();
					panel_13.add(panel_5, BorderLayout.NORTH);
					GridBagLayout gbl_panel_5 = new GridBagLayout();
					gbl_panel_5.columnWidths = new int[]{597, 0, 0};
					gbl_panel_5.rowHeights = new int[]{0, 0};
					gbl_panel_5.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
					gbl_panel_5.rowWeights = new double[]{1.0, Double.MIN_VALUE};
					panel_5.setLayout(gbl_panel_5);
					{
						lblCharacterLimit = new JLabel("Character limit :");
						GridBagConstraints gbc_lblCharacterLimit = new GridBagConstraints();
						gbc_lblCharacterLimit.anchor = GridBagConstraints.EAST;
						gbc_lblCharacterLimit.insets = new Insets(0, 0, 0, 5);
						gbc_lblCharacterLimit.gridx = 0;
						gbc_lblCharacterLimit.gridy = 0;
						panel_5.add(lblCharacterLimit, gbc_lblCharacterLimit);
					}
					{
						characterLimit = new JTextField(GeneralConfig.stepCharacterLimit+"");
						GridBagConstraints gbc_characterLimit = new GridBagConstraints();
						gbc_characterLimit.fill = GridBagConstraints.HORIZONTAL;
						gbc_characterLimit.gridx = 1;
						gbc_characterLimit.gridy = 0;
						panel_5.add(characterLimit, gbc_characterLimit);
						characterLimit.setColumns(10);
					}
				}
			}
			{
				
    				expertPanel = new JPanel();
    				GridBagLayout gbl_panel_9 = new GridBagLayout();
    				gbl_panel_9.columnWidths = new int[]{0, 0, 0};
    				gbl_panel_9.rowHeights = new int[]{0, 0, 0, 0};
    				gbl_panel_9.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
    				gbl_panel_9.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
    				expertPanel.setLayout(gbl_panel_9);
    				{
    					lblResetTargetCache = new JLabel("Reset target cache:");
    					GridBagConstraints gbc_lblResetTargetCache = new GridBagConstraints();
    					gbc_lblResetTargetCache.anchor = GridBagConstraints.EAST;
    					gbc_lblResetTargetCache.insets = new Insets(0, 0, 5, 5);
    					gbc_lblResetTargetCache.gridx = 0;
    					gbc_lblResetTargetCache.gridy = 0;
    					expertPanel.add(lblResetTargetCache, gbc_lblResetTargetCache);
    				}
    				{
    					btnResetTargetCache = new JButton("RESET");
    					GridBagConstraints gbc_btnReset = new GridBagConstraints();
    					gbc_btnReset.insets = new Insets(0, 0, 5, 0);
    					gbc_btnReset.gridx = 1;
    					gbc_btnReset.gridy = 0;
    					expertPanel.add(btnResetTargetCache, gbc_btnReset);
    					btnResetTargetCache.addActionListener(new ActionListener() {
					    
					    @Override
					    public void actionPerformed(ActionEvent arg0) {
						GeneralConfig.cacheForTarget=new HashMap<String, Object>();
						JOptionPane.showMessageDialog(v, "Target Cache Clear");
					    }
					});
    				}
    				{
    					lblResetSubsystemCache = new JLabel("Reset Subsystem Cache:");
    					GridBagConstraints gbc_lblResetSubsystemCache = new GridBagConstraints();
    					gbc_lblResetSubsystemCache.anchor = GridBagConstraints.EAST;
    					gbc_lblResetSubsystemCache.insets = new Insets(0, 0, 5, 5);
    					gbc_lblResetSubsystemCache.gridx = 0;
    					gbc_lblResetSubsystemCache.gridy = 1;
    					expertPanel.add(lblResetSubsystemCache, gbc_lblResetSubsystemCache);
    				}
    				{
    					btnResetSubsystemCache = new JButton("RESET");
    					GridBagConstraints gbc_btnReset_1 = new GridBagConstraints();
    					gbc_btnReset_1.insets = new Insets(0, 0, 5, 0);
    					gbc_btnReset_1.gridx = 1;
    					gbc_btnReset_1.gridy = 1;
    					expertPanel.add(btnResetSubsystemCache, gbc_btnReset_1);
    					btnResetSubsystemCache.addActionListener(new ActionListener() {
					    
					    @Override
					    public void actionPerformed(ActionEvent arg0) {
						GeneralConfig.cacheForSSlist=new HashMap<String, Object>();
						JOptionPane.showMessageDialog(v, "Sub-Systems Cache Clear");
					    }
					});
    				}
    				
        				{
        					lblCellReferenceIn = new JLabel("Cell Reference in Excel File for Documentation Progress :");
        					GridBagConstraints gbc_lblCellReferenceIn = new GridBagConstraints();
        					gbc_lblCellReferenceIn.insets = new Insets(0, 0, 0, 5);
        					gbc_lblCellReferenceIn.anchor = GridBagConstraints.EAST;
        					gbc_lblCellReferenceIn.gridx = 0;
        					gbc_lblCellReferenceIn.gridy = 2;
        					expertPanel.add(lblCellReferenceIn, gbc_lblCellReferenceIn);
        				}
        				{
        					excelFileCell = new JTextField(GeneralConfig.excelFileCell);
        					excelFileCell.setToolTipText("Type a cell referece (i.e. 'A2')");
        					GridBagConstraints gbc_textField = new GridBagConstraints();
        					gbc_textField.fill = GridBagConstraints.HORIZONTAL;
        					gbc_textField.gridx = 1;
        					gbc_textField.gridy = 2;
        					expertPanel.add(excelFileCell, gbc_textField);
        					excelFileCell.setColumns(10);
        				}
        			if(GeneralConfig.expertMode){
        				tabbedPane.addTab("Expert", null, expertPanel, null);
    				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		
		this.addBtnBrowseListener(new BrowseListener());
		this.addValidateListener(new ValidateListener());
		this.addCancelListener(new CancelListener());
				
		this.pack();
	}
	
	/**
	 * @param al
	 */
	public void addBtnBrowseListener(ActionListener al){
		browseButton.addActionListener(al);
	}
	
	/**
	 * @param al
	 */
	public void addICAPSLinkedListener(ActionListener al){
	}
	
	/**
	 * @param al
	 */
	public void addValidateListener(ActionListener al){
		this.okButton.addActionListener(al);
	}
	
	/**
	 * @param al
	 */
	public void addCancelListener(ActionListener al){
		this.cancelButton.addActionListener(al);
	}
	
	/**
	 * @param al
	 */
	public void addCheckConnectionListerner(ActionListener al){
	}
	
//	public class ICAPSLinkedListener implements ActionListener{
//
//		@Override
//		public void actionPerformed(ActionEvent arg0) {
//			GeneralConfig.isICAPSLinked=!GeneralConfig.isICAPSLinked;
//			tickleICAPSOption();
//		}
//		
//	}
	
//	public void tickleICAPSOption(){
//		if(GeneralConfig.TCPIPConnection){
//			serverName.setEnabled(GeneralConfig.isICAPSLinked);
//			instanceName.setEnabled(GeneralConfig.isICAPSLinked);
//			dataBaseName.setEnabled(GeneralConfig.isICAPSLinked);
//		} else {
//			odbcLinkName.setEnabled(GeneralConfig.isICAPSLinked);
//		}
//	}
	
	/**
	 * @author S.Trémouille
	 *
	 */
	public class BrowseListener implements ActionListener{

		JFrame f ;
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(m.size()>0){
				JFileChooser jfc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".xlsx", "xlsx");
				jfc.setFileFilter(filter);
				int returnVal = jfc.showOpenDialog(v);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File fops = jfc.getSelectedFile();
					String name = fops.getName();
					int ext = name.lastIndexOf('.');
					if (ext != -1) {
						String extension = name.substring(ext, name.length());
						if (extension != "xlsx") {
							name = name.substring(0, ext) + ".xlsx";
						}
					} else {
						name = name + ".xlsx";
					}
					//System.out.println(name);
					//System.out.println(fops.getPath());
					//set the new filename
					String folder = fops.getPath().substring(0, fops.getPath().lastIndexOf(File.separatorChar));
					File newfops = new File(folder + File.separatorChar + name);
					//System.out.println(folder + File.separatorChar + name);
					GeneralConfig.setDocPath(newfops.getPath(),m,this);
					startUpDocumentationPath.setText(GeneralConfig.SUDocPath);
					f = new JFrame();
					f.setIconImage(Toolkit.getDefaultToolkit().getImage(MilestoneEditorFrame.class.getResource("/img/icone.png")));
					f.setTitle("Initialisation");
					JProgressBar pb = new JProgressBar();
					pb.setIndeterminate(true); 
					f.getContentPane().add(pb);
					v.setEnabled(false);
					f.setSize(200, 50);
					f.setLocation(new Point(v.getX()+v.getWidth()/2+f.getWidth()/2,v.getY()+v.getHeight()/2+f.getHeight()/2));
					f.setVisible(true);
				}
			} else {
				JOptionPane.showMessageDialog(v, "Place a least one Milestone before initing the Start Up Documentation");
			}
		}
		
		/**
		 * Notify when initialization is ended
		 */
		public void initEnded(){
			v.setEnabled(true);
			f.dispose();
			v.pack();
		}
		
	}
	
	
	
	/**
	 * @author S.Trémouille
	 *
	 */
	public class ValidateListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			GeneralConfig.titleEnable=chckbxTitle.isSelected();
			//title bar alignement
			if(rdbtnLeft.isSelected()){
				GeneralConfig.leftAlignedTitleBar=true;
				GeneralConfig.centeredTitleBar=false;
				
				//titleBar.setBounds(new Rectangle(0,titleBar.getY(),titleBar.getWidth(),titleBar.getHeight()));
			} else if(rdbtnRight.isSelected()) {
				GeneralConfig.leftAlignedTitleBar=false;
				GeneralConfig.centeredTitleBar=false;
				
				//titleBar.setBounds(new Rectangle(GeneralConfig.pageWidth-titleBar.getWidth(),titleBar.getY(),titleBar.getWidth(),titleBar.getHeight()));
			} else if(rdbtnCenter.isSelected()){
				GeneralConfig.leftAlignedTitleBar=false;
				GeneralConfig.centeredTitleBar=true;
				
				//titleBar.setBounds(new Rectangle((int)Math.round(GeneralConfig.pageWidth/2.0-titleBar.getWidth()/2.0),titleBar.getY(),titleBar.getWidth(),titleBar.getHeight()));
			}
			if(chckbxNewCheckBox_1.isSelected()){
				GeneralConfig.titleBlockEnable=true;
				if(leftAligned.isSelected()){
					GeneralConfig.titleBlockRightAligned=false;
				} else {
					GeneralConfig.titleBlockRightAligned=true;
				}
			}
			
			GeneralConfig.plDisplayed=checkBox.isSelected();
			GeneralConfig.ssAOCdisplayed=checkBox_1.isSelected();
			GeneralConfig.commProgressDisplayed=checkBox_2.isSelected();
			GeneralConfig.docProgressDisplayed=checkBox_3.isSelected();
			GeneralConfig.titleEnable=chckbxTitle.isSelected();
			GeneralConfig.titleBlockEnable=chckbxNewCheckBox_1.isSelected();
			GeneralConfig.titleBlockRightAligned=rightAligned.isSelected();
			GeneralConfig.stepProgress=chckbxStepsProgress.isSelected();
			GeneralConfig.SUDocPath=startUpDocumentationPath.getText();
			GeneralConfig.targetAccuracy=(Integer) comboBox.getSelectedItem();
			
			GeneralConfig.excelFileCell=excelFileCellTextField.getText().trim().toUpperCase();
			
			if(GeneralConfig.milestoneAlignementGridSize!=slider.getValue()){
        			GeneralConfig.milestoneAlignementGridSize=slider.getValue();
        			GeneralConfig.updateForZoom(GeneralConfig.zoom);
			}
			//GeneralConfig.expertMode=expertModeCheckBox.isSelected();
			GeneralConfig.labelForDoc=textField_2.getText();
			
			GeneralConfig.pageHeightCoeff=Float.valueOf(textField_1.getText());
			GeneralConfig.pageWidthCoeff=Float.valueOf(textField.getText());
			GeneralConfig.updateForZoom(GeneralConfig.zoom);
			
			GeneralConfig.stepCharacterLimit=Integer.valueOf(characterLimit.getText());
			v.dispose();
		}
		
	}
	
	/**
	 * @author S.Trémouille
	 *
	 */
	public class CancelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			v.dispose();
		}
		
	}

	/**
	 * notify the table of a change
	 */
	public void fireTableDataChanged() {
		dtm.fireTableDataChanged();
	}
	
	/**
	 * @author S.Trémouille
	 *
	 */
	public class ClearTargetCacheListener implements ActionListener{

	    //Clear the cache of each targets in order to refresh graph's
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		GeneralConfig.cacheForTarget=new HashMap<String, Object>();
		JOptionPane.showMessageDialog(v, "Target Cache Clear");
	    }
	    
	}
	
	/**
	 * @author S.Trémouille
	 *
	 */
	public class ClearSubsystemCacheListener implements ActionListener{

	  //Clear the cache of each Sub-systems list in order to refresh
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		GeneralConfig.cacheForSSlist=new HashMap<String, Object>();
		JOptionPane.showMessageDialog(v, "Sub-Systems Cache Clear");
	    }
	    
	}
	
	public JTable getStepsTable(){
		return stepsTable;
	}
	
}
