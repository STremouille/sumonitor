package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.colorchooser.ColorChooserComponentFactory;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import java.awt.Toolkit;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JTable;

import model.StartUpStep;
import conf.GeneralConfig;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JTextField;
import javax.swing.JTextArea;

import java.awt.Font;

import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ListSelectionModel;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

import subsystem.DataCellRenderer;

import javax.swing.UIManager;
import javax.swing.JSpinner;
import javax.swing.JComboBox;

public class StepEditorView extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private StartUpStep step;
	private JTextField shortDescription;
	private JTextField relatedMilestone;
//	private JColorChooser colorChooser;
	private JComboBox comboBox;
	private JTextField shortDescription2;

	

	/**
	 * Create the frame.
	 */
	public StepEditorView(final StartUpStep step) {
		setTitle("Step Editor Frame");
		this.step=step;
		setIconImage(Toolkit.getDefaultToolkit().getImage(StepEditorView.class.getResource("/img/icone.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 850, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Property", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(5, 5));
		
		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_1.add(panel_3, BorderLayout.NORTH);
		
		final JCheckBox chckbxLocalRule = new JCheckBox("Local Rule");
		chckbxLocalRule.setSelected(step.isLocalAttrRule());
		chckbxLocalRule.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				step.setLocalAttrRule(chckbxLocalRule.isSelected());
				table.repaint();
			}
		});
		chckbxLocalRule.setHorizontalAlignment(SwingConstants.TRAILING);
		panel_3.add(chckbxLocalRule);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setToolTipText("Double Click to edit - To change field value ( Settings -> Preferences -> Step )");
		table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		table.setFillsViewportHeight(true);
		table.setModel(new StepEditorTableModel());
		//panel_1.add(table);
		/*table.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent me) {
		        JTable table =(JTable) me.getSource();
		        Point p = me.getPoint();
		        int row = table.rowAtPoint(p);
		        if (me.getClickCount() == 2) {
		        	StartUpStepFieldEdit susfe = new StartUpStepFieldEdit((String) table.getValueAt(row, 0), step);
					susfe.setVisible(true);
		        }
		    }
		});*/
		table.setDefaultRenderer(Boolean.class, new CellRenderer());
		
		JScrollPane scrollPane = new JScrollPane(table);
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "General Description", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel_2, BorderLayout.NORTH);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblCharacterLimit = new JLabel("Character Limit : "+GeneralConfig.stepCharacterLimit);
		GridBagConstraints gbc_lblCharacterLimit = new GridBagConstraints();
		gbc_lblCharacterLimit.anchor = GridBagConstraints.EAST;
		gbc_lblCharacterLimit.insets = new Insets(0, 0, 5, 0);
		gbc_lblCharacterLimit.gridx = 1;
		gbc_lblCharacterLimit.gridy = 0;
		panel_2.add(lblCharacterLimit, gbc_lblCharacterLimit);
		
		JLabel lblNewLabel = new JLabel("Short Description 1 :");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		panel_2.add(lblNewLabel, gbc_lblNewLabel);
		
		shortDescription = new JTextField(step.getName());
		shortDescription.setDocument(new JTextFieldLimit(GeneralConfig.stepCharacterLimit));
		shortDescription.setText(step.getName());
		GridBagConstraints gbc_shortDescription = new GridBagConstraints();
		gbc_shortDescription.insets = new Insets(0, 0, 5, 0);
		gbc_shortDescription.fill = GridBagConstraints.HORIZONTAL;
		gbc_shortDescription.gridx = 1;
		gbc_shortDescription.gridy = 1;
		panel_2.add(shortDescription, gbc_shortDescription);
		shortDescription.setColumns(10);
		
		JLabel lblLine = new JLabel("Short Description 2 :");
		GridBagConstraints gbc_lblLine = new GridBagConstraints();
		gbc_lblLine.anchor = GridBagConstraints.EAST;
		gbc_lblLine.insets = new Insets(0, 0, 5, 5);
		gbc_lblLine.gridx = 0;
		gbc_lblLine.gridy = 2;
		panel_2.add(lblLine, gbc_lblLine);
		
		shortDescription2 = new JTextField(step.getSecondLine());
		shortDescription2.setDocument(new JTextFieldLimit(GeneralConfig.stepCharacterLimit));
		shortDescription2.setText(step.getSecondLine());
		GridBagConstraints gbc_shortDescription2 = new GridBagConstraints();
		gbc_shortDescription2.insets = new Insets(0, 0, 5, 0);
		gbc_shortDescription2.fill = GridBagConstraints.HORIZONTAL;
		gbc_shortDescription2.gridx = 1;
		gbc_shortDescription2.gridy = 2;
		panel_2.add(shortDescription2, gbc_shortDescription2);
		shortDescription2.setColumns(10);
		
		JLabel lblRelatedMilestone = new JLabel("Related Milestone :");
		GridBagConstraints gbc_lblRelatedMilestone = new GridBagConstraints();
		gbc_lblRelatedMilestone.anchor = GridBagConstraints.EAST;
		gbc_lblRelatedMilestone.insets = new Insets(0, 0, 5, 5);
		gbc_lblRelatedMilestone.gridx = 0;
		gbc_lblRelatedMilestone.gridy = 3;
		panel_2.add(lblRelatedMilestone, gbc_lblRelatedMilestone);
		
		relatedMilestone = new JTextField(step.getRelatedToThisMilestone());
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 3;
		panel_2.add(relatedMilestone, gbc_textField_1);
		relatedMilestone.setColumns(10);
		
		JLabel lblLongDescription = new JLabel("Long Description :");
		GridBagConstraints gbc_lblLongDescription = new GridBagConstraints();
		gbc_lblLongDescription.anchor = GridBagConstraints.NORTH;
		gbc_lblLongDescription.insets = new Insets(0, 0, 0, 5);
		gbc_lblLongDescription.gridx = 0;
		gbc_lblLongDescription.gridy = 4;
		panel_2.add(lblLongDescription, gbc_lblLongDescription);
		
		final JTextArea longDescription = new JTextArea(step.getLongDescription());
		longDescription.setRows(5);
		longDescription.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 4;
		panel_2.add(longDescription, gbc_textArea);
		AbstractColorChooserPanel[] accp = {ColorChooserComponentFactory.getDefaultChooserPanels()[0]};
		
		
		JPanel panel_4 = new JPanel();
		contentPane.add(panel_4, BorderLayout.EAST);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JPanel backGroundColorPanel = new JPanel();
		panel_4.add(backGroundColorPanel, BorderLayout.NORTH);
		backGroundColorPanel.setBorder(new TitledBorder(null, "Background Color", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		GeneralConfig.colorChooser.getSelectionModel().setSelectedColor(step.getColor());
		backGroundColorPanel.add(GeneralConfig.colorChooser);
		
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "Shape", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.add(panel_5, BorderLayout.CENTER);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[]{0, 0, 0};
		gbl_panel_5.rowHeights = new int[]{0, 0, 0};
		gbl_panel_5.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_5.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panel_5.setLayout(gbl_panel_5);
		
		JLabel lblChooseAShape = new JLabel("Select a shape :");
		GridBagConstraints gbc_lblChooseAShape = new GridBagConstraints();
		gbc_lblChooseAShape.insets = new Insets(0, 0, 5, 5);
		gbc_lblChooseAShape.anchor = GridBagConstraints.EAST;
		gbc_lblChooseAShape.gridx = 0;
		gbc_lblChooseAShape.gridy = 0;
		panel_5.add(lblChooseAShape, gbc_lblChooseAShape);
		
		
		String[] shapesForComboBox = new String[GeneralConfig.shapesName.size()];
		Iterator<String> it = GeneralConfig.shapesName.iterator();
		int acc=0;
		while(it.hasNext()){
			shapesForComboBox[acc]=it.next();
			acc++;
		}
		comboBox = new JComboBox(shapesForComboBox);
		comboBox.setSelectedIndex(GeneralConfig.shapesName.indexOf(step.getShapeName()));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		panel_5.add(comboBox, gbc_comboBox);
		
		
		final PreviewPanel shapePreviewPanel = new PreviewPanel(step);
		GridBagConstraints gbc_shapePreviewPanel = new GridBagConstraints();
		gbc_shapePreviewPanel.fill = GridBagConstraints.BOTH;
		gbc_shapePreviewPanel.gridx = 1;
		gbc_shapePreviewPanel.gridy = 1;
		panel_5.add(shapePreviewPanel, gbc_shapePreviewPanel);
		
		comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				shapePreviewPanel.setPreviewShape(comboBox.getSelectedItem()+"");
			}
		});
		
		GeneralConfig.colorChooser.getSelectionModel().addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				shapePreviewPanel.setPreviewColor(GeneralConfig.colorChooser.getColor());
			}
		});
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				step.setName(shortDescription.getText());
				step.setSecondLine(shortDescription2.getText());
				step.setLongDescription(longDescription.getText());
				step.setRelatedToThisMilestone(relatedMilestone.getText());
				step.setColor(GeneralConfig.colorChooser.getColor());
				step.setShapeName((String) comboBox.getSelectedItem());
				dispose();
			}
		});
		
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel.add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		panel.add(btnCancel);
	}
	
	class StepEditorTableModel extends AbstractTableModel{

		@Override
		public java.lang.Class<?> getColumnClass(int arg0) {
			switch (arg0) {
			case 0:
				return String.class;
			case 1:
				return String.class;
			case 2:
				return Boolean.class;
			default:
				return String.class;
			}
		};
		
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			switch (columnIndex) {
			case 0:
				return false;
			case 1:
				return true;
			case 2:
				if(step.isLocalAttrRule()){
					return true;
				} else {
					return false;
				}
			default:
				return false;
			}
		};
		
		@Override
		public int getColumnCount() {
			return 3;
		}
		
		

		@Override
		public int getRowCount() {
			return GeneralConfig.stepsField.size();
		}

		@Override
		public Object getValueAt(int arg0, int arg1) {
			switch (arg1) {
			case 0:
				return GeneralConfig.stepsField.get(arg0).replaceAll("_", " ");
			case 1:
				return step.getAttr(GeneralConfig.stepsField.get(arg0));
			case 2:
				return step.getLocalAttrToDisplay().get(GeneralConfig.stepsField.get(arg0));
			default:
				return "";
			}
		}
		
		
		@Override
		public String getColumnName(int col) {
			switch (col) {
			case 0:
				return "Field";
			case 1:
				return "Value";
			case 2:
				return "Local Display";
			default:
				return "Faux";
			}
		}

		@Override
		public void setValueAt(Object value, int row, int col){
			switch (col) {
			case 1:
				step.getAttr().put(((String) (getValueAt(row, col-1))).replaceAll(" ", "_"), (String)value);
				break;
			case 2:
				if(step.isLocalAttrRule()){
					//System.out.println(((String) (getValueAt(row, col-2))).replaceAll(" ", "_")+","+ (Boolean)value);
					step.getLocalAttrToDisplay().put(((String) (getValueAt(row, col-2))).replaceAll(" ", "_"), (Boolean)value);
				}
				break;
			default:
				break;
			}		
		}
		
	}
	
	class CellRenderer extends JCheckBox implements TableCellRenderer{
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			setHorizontalAlignment(SwingConstants.CENTER);
			if(column==2)
					setEnabled(step.isLocalAttrRule());
			if(step.isLocalAttrRule()){
				if(step.getLocalAttrToDisplay().get(((String) table.getValueAt(row, column-2)).replaceAll(" ","_")) != null 
						&& step.getLocalAttrToDisplay().get(((String) table.getValueAt(row, column-2)).replaceAll(" ","_"))){
						setSelected(true);
				}
				else{
					setSelected(false);
				}
			}
			return this;
		
		}
	}
	
	class JTextFieldLimit extends PlainDocument {
		  private int limit;

		  JTextFieldLimit(int limit) {
		   super();
		   this.limit = limit;
		   }

		  public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException {
		    if (str == null) return;

		    if ((getLength() + str.length()) <= limit) {
		      super.insertString(offset, str, attr);
		    }
		  }
		}

	class PreviewPanel extends JPanel{
		private StartUpStep step;
		
		public PreviewPanel(StartUpStep sus) {
			step = new StartUpStep("", 0, 0, 100, 50);
			step.setShapeName(sus.getShapeName());
			step.setColor(sus.getColor());
		}
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			step.paint(g);
		}
		
		public void setPreviewShape(String s){
			step.setShapeName(s);
			repaint();
		}
		
		public void setPreviewColor(Color c){
			step.setColor(c);
			repaint();
		}
	}
	
}
