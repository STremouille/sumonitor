package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.colorchooser.ColorChooserComponentFactory;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.JTextComponent;

import model.Comment;
import model.InteractiveBar;
import model.SequenceBar;
import model.StartUpStep;
import model.TitleBar;
import model.TitleBar.Style;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import conf.GeneralConfig;

/**
 * 
 * @author S.Trémouille
 *
 */
public class InteractiveBarEditorView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5230958789858362675L;

	private InteractiveBar bar;
	
	private JTextComponent title;
	private JColorChooser colorChooser;
	private JPanel titlePanel,colorChooserPanels;
	private JButton validateButton;
	private JButton cancelButton;
	private JPanel alignementPanel,printDatePanel;
	private JLabel lblAlignement;
	private JRadioButton rdbtnLeft;
	private JRadioButton rdbtnRight;
	private JRadioButton rdbtnCenter;
	private JPanel stylePanel;
	private JComboBox<?> comboBox;
	private JTextField textField;
	private JTextArea milestoneRelatedTextArea;
	
	/**
	 * @param point
	 * @param barToEdit
	 */
	public InteractiveBarEditorView(Point point,InteractiveBar barToEdit){
		setIconImage(Toolkit.getDefaultToolkit().getImage(InteractiveBarEditorView.class.getResource("/img/icone.png")));
		this.bar=barToEdit;
		this.setTitle("Editor "+barToEdit.getName());
		this.setLocation(point);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		//this disable the top-level elements when editing
		
		if(bar.getClass().equals(new Comment("", 0, 0, 0, 0).getClass())){
			title = new JTextArea(barToEdit.getName());
		} else {
			title = new JTextField(barToEdit.getName());
			title.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent arg0) {
				}
				
				@Override
				public void keyReleased(KeyEvent arg0) {
				}
				
				@Override
				public void keyPressed(KeyEvent arg0) {
					if(title.getText().length()>=100)
				    {
						title.setText(title.getText().substring(0, 99));
				    }
				}
			});
		}
		title.setFont(new Font("Arial Unicode MS", Font.PLAIN, 11));
		titlePanel = new JPanel();
		titlePanel.setLayout(new BorderLayout());
		titlePanel.setBorder(BorderFactory.createTitledBorder("Title"));
		titlePanel.add(title,BorderLayout.NORTH);
		colorChooserPanels = new JPanel(new GridLayout(1,2));
		JPanel colorChooserPanel = new JPanel();
		colorChooserPanel.setBorder(BorderFactory.createTitledBorder("Background Color"));
		colorChooser = new JColorChooser(bar.getColor());
		colorChooserPanel.add(colorChooser);
		colorChooserPanels.add(colorChooserPanel);
		
		AbstractColorChooserPanel[] accp = {ColorChooserComponentFactory.getDefaultChooserPanels()[0]};
		colorChooser.setChooserPanels(accp);
		colorChooser.setPreviewPanel(new JPanel());
		
		JPanel buttons = new JPanel();
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(titlePanel,BorderLayout.NORTH);
		titlePanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		getContentPane().add(buttons,BorderLayout.SOUTH);
		titlePanel.add(title,"3,1");
		////////////////////////////////////////////////////////////////////
		JPanel milestonePanel = new JPanel();
		if(bar.getClass().equals(new StartUpStep("", 0, 0, 0, 0).getClass())){
			//milestonePanel.setBorder(BorderFactory.createTitledBorder("Milestone related"));
			milestoneRelatedTextArea = new JTextArea(((StartUpStep) barToEdit).getRelatedToThisMilestone());
			milestonePanel.add(milestoneRelatedTextArea);
		}
		titlePanel.add(milestonePanel, "5,1");
		buttons.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("25px:grow"),
				ColumnSpec.decode("6px"),
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				ColumnSpec.decode("6px"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("23px"),}));
		validateButton = new JButton("Validate");
		buttons.add(validateButton, "3, 1, default, top");
		
		cancelButton = new JButton("Cancel");
		buttons.add(cancelButton, "5, 1, fill, default");
		getContentPane().add(colorChooserPanels,BorderLayout.CENTER);
		
		alignementPanel = new JPanel();
		getContentPane().add(alignementPanel, BorderLayout.EAST);
		GridBagLayout gbl_alignementPanel = new GridBagLayout();
		gbl_alignementPanel.columnWidths = new int[]{0, 0};
		gbl_alignementPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_alignementPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_alignementPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		alignementPanel.setLayout(gbl_alignementPanel);
		
		stylePanel = new JPanel();
		GridBagConstraints gbc_stylePanel = new GridBagConstraints();
		gbc_stylePanel.gridx = 0;
		gbc_stylePanel.gridy = 4;
		alignementPanel.add(stylePanel, gbc_stylePanel);
		
		
		//Title Bar edit
		if(barToEdit.getClass().equals(new TitleBar("", 0, 0, 0, 0).getClass())){
			//Style
			Style[] val = Style.values();
			int i=Arrays.binarySearch(val, ((TitleBar) bar).getStyle());
			comboBox = new JComboBox<Object>(val);
			comboBox.setSelectedIndex(i);
			comboBox.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.insets = new Insets(0, 0, 5, 0);
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.gridx = 1;
			gbc_comboBox.gridy = 2;
			stylePanel.add(comboBox, gbc_comboBox);
			
			//Alingnement
			lblAlignement = new JLabel("Alignement :");
			GridBagConstraints gbc_lblAlignement = new GridBagConstraints();
			gbc_lblAlignement.insets = new Insets(0, 0, 5, 0);
			gbc_lblAlignement.gridx = 0;
			gbc_lblAlignement.gridy = 0;
			alignementPanel.add(lblAlignement, gbc_lblAlignement);
			
			
			rdbtnLeft = new JRadioButton("left");
			if(GeneralConfig.leftAlignedTitleBar){
				rdbtnLeft.setSelected(true);
			}
			GridBagConstraints gbc_rdbtnLeft = new GridBagConstraints();
			gbc_rdbtnLeft.anchor = GridBagConstraints.WEST;
			gbc_rdbtnLeft.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnLeft.gridx = 0;
			gbc_rdbtnLeft.gridy = 1;
			alignementPanel.add(rdbtnLeft, gbc_rdbtnLeft);
			rdbtnLeft.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					rdbtnLeft.setSelected(true);
					rdbtnCenter.setSelected(false);
					rdbtnRight.setSelected(false);
					
					GeneralConfig.leftAlignedTitleBar=true;
					GeneralConfig.centeredTitleBar=false;
				}
			});
			
			rdbtnCenter = new JRadioButton("center");
			if(GeneralConfig.centeredTitleBar){
				rdbtnCenter.setSelected(true);
			}
			rdbtnCenter.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					rdbtnLeft.setSelected(false);
					rdbtnCenter.setSelected(true);
					rdbtnRight.setSelected(false);
					
					GeneralConfig.leftAlignedTitleBar=false;
					GeneralConfig.centeredTitleBar=true;
				}
			});
			GridBagConstraints gbc_rdbtnCenter = new GridBagConstraints();
			gbc_rdbtnCenter.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnCenter.anchor = GridBagConstraints.WEST;
			gbc_rdbtnCenter.gridx = 0;
			gbc_rdbtnCenter.gridy = 2;
			alignementPanel.add(rdbtnCenter, gbc_rdbtnCenter);
		
		
			rdbtnRight = new JRadioButton("right");
			
			if(!GeneralConfig.leftAlignedTitleBar&&!GeneralConfig.centeredTitleBar){
				rdbtnRight.setSelected(true);
			}
			rdbtnRight.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					rdbtnLeft.setSelected(false);
					rdbtnCenter.setSelected(false);
					rdbtnRight.setSelected(true);
					
					GeneralConfig.leftAlignedTitleBar=false;
					GeneralConfig.centeredTitleBar=false;
				}
			});
			GridBagConstraints gbc_rdbtnRight = new GridBagConstraints();
			gbc_rdbtnRight.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnRight.anchor = GridBagConstraints.WEST;
			gbc_rdbtnRight.gridx = 0;
			gbc_rdbtnRight.gridy = 3;
			alignementPanel.add(rdbtnRight, gbc_rdbtnRight);
			
			printDatePanel = new JPanel();
			GridBagConstraints gbc_printDatePanel = new GridBagConstraints();
			gbc_printDatePanel.insets = new Insets(0, 0, 5, 0);
			gbc_printDatePanel.gridx = 0;
			gbc_printDatePanel.gridy = 5;
			alignementPanel.add(printDatePanel, gbc_printDatePanel);
			
			final JCheckBox displayDates = new JCheckBox(" Display Print & Update dates");
			displayDates.setSelected(GeneralConfig.titleBarDisplayDates);
			printDatePanel.add(displayDates);
			displayDates.addActionListener(new ActionListener() {
			    
			    @Override
			    public void actionPerformed(ActionEvent arg0) {
				GeneralConfig.titleBarDisplayDates=!GeneralConfig.titleBarDisplayDates;
				displayDates.setSelected(GeneralConfig.titleBarDisplayDates);
			    }
			});
			
		}
		
		//Sequence Bar edit
		if(barToEdit.getClass().equals(new SequenceBar("", 0, 0, 0, 0, 0).getClass())){
			lblAlignement = new JLabel("Alignement :");
			GridBagConstraints gbc_lblAlignement = new GridBagConstraints();
			gbc_lblAlignement.insets = new Insets(0, 0, 5, 0);
			gbc_lblAlignement.gridx = 0;
			gbc_lblAlignement.gridy = 0;
			alignementPanel.add(lblAlignement, gbc_lblAlignement);
			
			
			rdbtnLeft = new JRadioButton("left");
			if(((SequenceBar) bar).isLeftAligned()){
				rdbtnLeft.setSelected(true);
			}
			rdbtnLeft.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					rdbtnLeft.setSelected(true);
					rdbtnCenter.setSelected(false);
					rdbtnRight.setSelected(false);
				}
			});
			GridBagConstraints gbc_rdbtnLeft = new GridBagConstraints();
			gbc_rdbtnLeft.anchor = GridBagConstraints.WEST;
			gbc_rdbtnLeft.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnLeft.gridx = 0;
			gbc_rdbtnLeft.gridy = 1;
			alignementPanel.add(rdbtnLeft, gbc_rdbtnLeft);
			rdbtnLeft.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					rdbtnLeft.setSelected(true);
					rdbtnCenter.setSelected(false);
					rdbtnRight.setSelected(false);
					
					((SequenceBar)bar).setLeftAligned();
				}
			});
			
			rdbtnCenter = new JRadioButton("center");
			if(((SequenceBar) bar).isCenterAligned()){
				rdbtnCenter.setSelected(true);
			}
			rdbtnCenter.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					rdbtnLeft.setSelected(false);
					rdbtnCenter.setSelected(true);
					rdbtnRight.setSelected(false);
					
					((SequenceBar)bar).setCenterAligned();
				}
			});
			GridBagConstraints gbc_rdbtnCenter = new GridBagConstraints();
			gbc_rdbtnCenter.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnCenter.anchor = GridBagConstraints.WEST;
			gbc_rdbtnCenter.gridx = 0;
			gbc_rdbtnCenter.gridy = 2;
			alignementPanel.add(rdbtnCenter, gbc_rdbtnCenter);
		
		
			rdbtnRight = new JRadioButton("right");
			
			if(!((SequenceBar) bar).isLeftAligned()&&!((SequenceBar) bar).isCenterAligned()){
				rdbtnRight.setSelected(true);
			}
			rdbtnRight.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					rdbtnLeft.setSelected(false);
					rdbtnCenter.setSelected(false);
					rdbtnRight.setSelected(true);
					
					((SequenceBar)bar).setRightAligned();
				}
			});
			GridBagConstraints gbc_rdbtnRight = new GridBagConstraints();
			gbc_rdbtnRight.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnRight.anchor = GridBagConstraints.WEST;
			gbc_rdbtnRight.gridx = 0;
			gbc_rdbtnRight.gridy = 3;
			alignementPanel.add(rdbtnRight, gbc_rdbtnRight);
		}
		this.pack();
		this.setVisible(true);
	}
	
	@Override
	public void paintComponents(Graphics g) {
		super.paintComponents(g);
		bar.paintComponents(g);
	}
	
	/**
	 * @return jTextComponent
	 */
	public Object getTitleArea() {
		return title;
	}
		
	/**
	 * @param jTextComponent
	 */
	public void setTitleArea(JTextComponent jTextComponent){
		this.title=jTextComponent;
	}
	
	/**
	 * @return color
	 */
	public Color getColor(){
		return colorChooser.getColor();
	}

	/**
	 * @return colorChooser
	 */
	public JColorChooser getColorChooser() {
		return colorChooser;
	}
	
	/**
	 * @return titlePanel
	 */
	public JPanel getTitlePanel(){
		return titlePanel;
	}
	
	//Registering listeners
	/**
	 * @param al
	 */
	public void addValidateListener(ActionListener al){
		this.validateButton.addActionListener(al);
	}
	
	/**
	 * @param al
	 */
	public void addCancelListener(ActionListener al){
		this.cancelButton.addActionListener(al);
	}

	/**
	 * @param cl
	 */
	public void addColorChooserChangeListener(ChangeListener cl){
		this.colorChooser.getSelectionModel().addChangeListener(cl);
	}

	
	/**
	 * @return InteractiveBarName
	 */
	public String getInteractiveBarName(){
		return bar.getName();
	}
	
	/**
	 * @param jPanel
	 */
	public void addColorChoosersPanel(JPanel jPanel){
		colorChooserPanels.add(jPanel);
	}

	/**
	 * @return InteractiveBar
	 */
	public InteractiveBar getModel() {
		return bar;
	}

	/**
	 * @param al
	 */
	public void addRepaintListener(ActionListener al) {
		validateButton.addActionListener(al);
		cancelButton.addActionListener(al);
	}

	/**
	 * @return Style
	 */
	public Style getStyle() {
		if(bar.getClass().equals(new TitleBar("", 0, 0, 0, 0).getClass())){
		    return  (Style) comboBox.getSelectedItem();
		}
		else
			return null;
	}
	
	/**
	 * @return Style
	 */
	public String getMilestoneRelated() {
		if(bar.getClass().equals(new StartUpStep("", 0, 0, 0, 0).getClass())){
		    return milestoneRelatedTextArea.getText();
		}
		else
			return null;
	}
	
	class StepEditorTableModel extends AbstractTableModel{

		@Override
		public int getColumnCount() {
			return 2;
		}

		@Override
		public int getRowCount() {
			return GeneralConfig.stepsField.size();
		}

		@Override
		public Object getValueAt(int arg0, int arg1) {
			switch (arg1) {
			case 0:
				return GeneralConfig.stepsField.get(arg0);
			case 1:
				return ((StartUpStep) bar).getAttr(GeneralConfig.stepsField.get(arg0));
			default:
				return "";
			}
		}
		
	}
	
}
