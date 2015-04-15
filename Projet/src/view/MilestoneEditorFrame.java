package view;

import java.awt.AWTKeyStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import model.Milestone;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * Milestone editor view
 * @author S.Trémouille
 *
 */

public class MilestoneEditorFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4450375718948856321L;
	private Milestone model;
	private final JPanel contentPanel = new JPanel();
	private JTabbedPane showTarget;
	private JPanel monitorPanel;
	private JPanel buttonPane;
	private JButton okButton;
	private JButton cancelButton;
	private JPanel editPanel;
	private JLabel lblNewLabel;
	private JTextField name;
	private JTextArea description;
	private JLabel lblDescription;
	private JLabel lblTargetDate;
	private JPanel panel;
	private JButton targetDatePicker;
	private JTextField targetDate;
	private JButton showSubSysButton;
	private JPanel targetPanel;
	private JButton showTargetButton;
	private JPanel panel_1;
	private JScrollPane scrollPane;
	private JButton button;
	private JButton button_1;
	private JButton btnShowTarget;
	private JPanel panel_2;
	private JLabel precomProgress;
	private JLabel commProgress;
	private JLabel ssRFC;
	private JLabel ssAOC;
	private JLabel doc;
	private JLabel PLA;
	private JLabel PLB;
	private JLabel PLC;
	private JLabel lblPunchItemto;
	private JLabel lblRemainingPunchItem_1;
	private JLabel lblSubsystemsAoc;
	private JLabel lblRemainingPunchItem;
	private JLabel lblSubsystemsRfc;
	private JLabel lblFieldOpDocumentation;
	private JLabel lblCommissioningProgressmhrs;
	private JLabel lblPrecommissioning;
	private JLabel lblProgressmhrs;
	private JLabel lblCommissioning;
	private JPanel panel_3;
	private JLabel label;
	private JLabel label_1;

	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		try {
			MilestoneEditorDialog dialog = new MilestoneEditorDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 * @param milestone 
	 * @param x 
	 * @param y 
	 */
	public MilestoneEditorFrame(Milestone milestone,int x, int y) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MilestoneEditorFrame.class.getResource("/img/icone.png")));
		this.model=milestone;
		this.setTitle("Milestone Edition");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setFont(new Font("Arial", Font.PLAIN, 12));
		setBounds(x, y, 820, 434);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			showTarget = new JTabbedPane(SwingConstants.TOP);
			showTarget.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Milestone Control Panel", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			showTarget.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
			showTarget.setFont(new Font("Arial", Font.PLAIN, 11));
			contentPanel.add(showTarget);
			{
				editPanel = new JPanel();
				editPanel.setLayout(new FormLayout(new ColumnSpec[] {
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_COLSPEC,},
					new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,}));
				{
					lblNewLabel = new JLabel("Name :");
					lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 12));
					editPanel.add(lblNewLabel, "2, 2, right, default");
				}
				{
					name = new JTextField(milestone.getName());
					name.setFont(new Font("Arial", Font.PLAIN, 13));
					editPanel.add(name, "4, 2, fill, default");
					name.setColumns(10);
				}
				{
					lblDescription = new JLabel("Description :");
					lblDescription.setFont(new Font("Arial", Font.PLAIN, 12));
					editPanel.add(lblDescription, "2, 4, right, default");
				}
				{
					panel_1 = new JPanel();
					panel_1.setForeground(Color.LIGHT_GRAY);
					panel_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
					editPanel.add(panel_1, "4, 4, fill, fill");
					panel_1.setLayout(new FormLayout(new ColumnSpec[] {
							ColumnSpec.decode("default:grow"),},
						new RowSpec[] {
							RowSpec.decode("default:grow"),}));
					{
						scrollPane = new JScrollPane();
						panel_1.add(scrollPane, "1, 1, fill, fill");
						{
							description = new JTextArea(milestone.getDescription());
							scrollPane.setViewportView(description);
							description.setWrapStyleWord(true);
							description.setLineWrap(true);
							description.setForeground(Color.BLACK);
							description.setBackground(Color.WHITE);
							description.setFont(new Font("Arial Unicode MS", Font.PLAIN, 14));
						}
						editPanel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{name, description, targetDatePicker}));
						contentPanel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{monitorPanel, showSubSysButton, targetPanel, showTargetButton, editPanel, showTarget, lblDescription, lblTargetDate, targetDate, panel,  targetDatePicker, description, panel_1, name, lblNewLabel}));
						getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{contentPanel, showTarget, editPanel, name, panel_1, description, panel, targetDatePicker,  buttonPane, okButton, cancelButton}));
					}
				}
				{
					lblTargetDate = new JLabel("Target Date :");
					lblTargetDate.setFont(new Font("Arial", Font.PLAIN, 12));
					editPanel.add(lblTargetDate, "2, 6, right, default");
				}
				{
					panel = new JPanel();
					editPanel.add(panel, "4, 6, fill, center");
					panel.setLayout(new FormLayout(new ColumnSpec[] {
							FormFactory.RELATED_GAP_COLSPEC,
							ColumnSpec.decode("default:grow"),
							FormFactory.RELATED_GAP_COLSPEC,
							FormFactory.DEFAULT_COLSPEC,},
						new RowSpec[] {
							FormFactory.RELATED_GAP_ROWSPEC,
							FormFactory.DEFAULT_ROWSPEC,}));
					{
						SimpleDateFormat f = new SimpleDateFormat("MMM/dd/yyyy");
						targetDate = new JTextField(f.format(milestone.getTargetDate()));
						targetDate.setEditable(false);
						targetDate.setFont(new Font("Arial", Font.PLAIN, 12));
						panel.add(targetDate, "1, 2, 2, 1, fill, center");
						targetDate.setColumns(10);
					}
					{
						targetDatePicker = new JButton("Pick a date");
						targetDatePicker.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent arg0) {
							}
						});
						panel.add(targetDatePicker, "4, 2");
					}
				}
				{
					{
						Set<AWTKeyStroke> set = new HashSet<AWTKeyStroke>(editPanel.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
						set.add(AWTKeyStroke.getAWTKeyStroke("TAB"));
						editPanel.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, set);
						editPanel.setFocusTraversalKeysEnabled(true);
						showTarget.addTab("Edit Milestone", null, editPanel, null);
					}
				}
				{
					button_1 = new JButton("Update Progress");
					button_1.setVisible(false);
					button_1.setEnabled(false);
					button_1.setFont(new Font("Arial", Font.PLAIN, 11));
					editPanel.add(button_1, "4, 8, left, default");
				}
			}
			{
				monitorPanel = new JPanel();
				monitorPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
				showTarget.addTab("Monitor Milestone", null, monitorPanel, null);
				monitorPanel.setLayout(new FormLayout(new ColumnSpec[] {
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("left:default:grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("max(43dlu;default)"),
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("max(27dlu;default)"),},
					new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("max(25dlu;default):grow"),
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("max(25dlu;default):grow"),
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("max(24dlu;default):grow"),
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("max(27dlu;default):grow"),
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("bottom:25dlu:grow"),
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("max(26dlu;default):grow"),
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("max(28dlu;default):grow"),}));
				{
					showSubSysButton = new JButton("Show Sub-Systems");
					showSubSysButton.setPreferredSize(new Dimension(179, 23));
					monitorPanel.add(showSubSysButton, "2, 2, center, center");
				}
				{
					button = new JButton("Open Field Op. Documentation");
					button.setHorizontalTextPosition(SwingConstants.CENTER);
					button.setFont(new Font("Arial", Font.PLAIN, 11));
					monitorPanel.add(button, "6, 2, center, center");
				}
				JPanel pan2 = new JPanel();
				pan2.setFont(new Font("Arial", Font.PLAIN, 15));
				pan2.setBackground(new Color(255, 153, 204));
				{
					lblFieldOpDocumentation = new JLabel("Field Op. Documentation Progress :");
					lblFieldOpDocumentation.setFont(new Font("Arial", Font.PLAIN, 12));
					lblFieldOpDocumentation.setPreferredSize(new Dimension(200, 14));
					lblFieldOpDocumentation.setHorizontalAlignment(SwingConstants.RIGHT);
					lblFieldOpDocumentation.setBorder(null);
					lblFieldOpDocumentation.setBackground(new Color(255, 204, 204));
					lblFieldOpDocumentation.setAlignmentX(0.5f);
					pan2.add(lblFieldOpDocumentation);
				}
				{
					doc = new JLabel(milestone.getDocumentationProgress()+" %");
					doc.setFont(new Font("Arial", Font.PLAIN, 12));
					pan2.add(doc);
				}
				{
					panel_3 = new JPanel();
					panel_3.setBackground(new Color(204, 204, 255));
					monitorPanel.add(panel_3, "6, 4, fill, fill");
					{
						label = new JLabel("Step Progress :");
						label.setFont(new Font("Arial", Font.PLAIN, 12));
						panel_3.add(label);
					}
					{
						label_1 = new JLabel(milestone.getStepProgress()+" %");
						label_1.setFont(new Font("Arial", Font.PLAIN, 12));
						panel_3.add(label_1);
					}
				}
				monitorPanel.add(pan2, "6, 6, fill, center");
				{
					lblPrecommissioning = new JLabel("Pre-Commissioning");
					lblPrecommissioning.setFont(new Font("Arial", Font.BOLD, 15));
					lblPrecommissioning.setHorizontalTextPosition(SwingConstants.CENTER);
					lblPrecommissioning.setPreferredSize(new Dimension(200, 14));
					lblPrecommissioning.setHorizontalAlignment(SwingConstants.CENTER);
					lblPrecommissioning.setOpaque(true);
					lblPrecommissioning.setBorder(null);
					lblPrecommissioning.setBackground(new Color(153, 255, 153));
					lblPrecommissioning.setAlignmentX(0.5f);
					monitorPanel.add(lblPrecommissioning, "2, 8, fill, fill");
				}
				{
					lblCommissioning = new JLabel("Commissioning ");
					lblCommissioning.setFont(new Font("Arial", Font.BOLD, 15));
					lblCommissioning.setPreferredSize(new Dimension(170, 14));
					lblCommissioning.setOpaque(true);
					lblCommissioning.setHorizontalAlignment(SwingConstants.CENTER);
					lblCommissioning.setBorder(null);
					lblCommissioning.setBackground(new Color(153, 204, 255));
					lblCommissioning.setAlignmentX(0.5f);
					monitorPanel.add(lblCommissioning, "6, 8, fill, fill");
				}
				JPanel pan6 = new JPanel();
				{
					lblProgressmhrs = new JLabel("Pre-Commissioning Progress (Mhrs) :");
					lblProgressmhrs.setHorizontalTextPosition(SwingConstants.RIGHT);
					lblProgressmhrs.setHorizontalAlignment(SwingConstants.CENTER);
					pan6.add(lblProgressmhrs);
				}
				{
					precomProgress = new JLabel(milestone.getPrecommProgress()+" %");
					pan6.add(precomProgress);
				}
				monitorPanel.add(pan6, "2, 10, right, default");
				
				JPanel pan7 = new JPanel();
				{
					lblSubsystemsRfc = new JLabel("Sub-Systems RFC :");
					lblSubsystemsRfc.setHorizontalAlignment(SwingConstants.RIGHT);
					lblSubsystemsRfc.setBorder(null);
					lblSubsystemsRfc.setBackground(new Color(153, 255, 153));
					lblSubsystemsRfc.setAlignmentX(0.5f);
					pan7.add(lblSubsystemsRfc);
				}
				{
					ssRFC = new JLabel(milestone.getSsRFCProgress()+" %");
					pan7.add(ssRFC);
				}
				monitorPanel.add(pan7, "2, 12, right, center");
				
				JPanel pan8 = new JPanel();
				{
					lblRemainingPunchItem = new JLabel("Remaining Punch Item (To be cleared before RFC) :");
					lblRemainingPunchItem.setBorder(null);
					lblRemainingPunchItem.setBackground(Color.WHITE);
					lblRemainingPunchItem.setAlignmentX(0.5f);
					pan8.add(lblRemainingPunchItem);
				}
				PLA = new JLabel(milestone.getPunchAOpened()+"");
				if(Integer.valueOf(PLA.getText())>0)
					PLA.setForeground(Color.red);
				pan8.add(PLA);
				PLA.setFont(new Font("Tahoma", Font.BOLD, 11));
				monitorPanel.add(pan8, "2, 14, right, center");
				
				JPanel pan1 = new JPanel();
				{
					lblCommissioningProgressmhrs = new JLabel("Commissioning Progress (Mhrs) :");
					lblCommissioningProgressmhrs.setMaximumSize(new Dimension(250, 16));
					lblCommissioningProgressmhrs.setMinimumSize(new Dimension(250, 16));
					lblCommissioningProgressmhrs.setHorizontalTextPosition(SwingConstants.RIGHT);
					lblCommissioningProgressmhrs.setPreferredSize(new Dimension(200, 14));
					lblCommissioningProgressmhrs.setHorizontalAlignment(SwingConstants.CENTER);
					lblCommissioningProgressmhrs.setBorder(null);
					lblCommissioningProgressmhrs.setBackground(new Color(153, 204, 255));
					lblCommissioningProgressmhrs.setAlignmentX(0.5f);
					pan1.add(lblCommissioningProgressmhrs);
				}
				{
					commProgress = new JLabel(milestone.getCommProgress()+" %");
					pan1.add(commProgress);
				}
				monitorPanel.add(pan1, "6, 10, right, center");
				JPanel pan3 = new JPanel();
				{
					lblSubsystemsAoc = new JLabel("Sub-Systems AOC :");
					lblSubsystemsAoc.setHorizontalAlignment(SwingConstants.RIGHT);
					lblSubsystemsAoc.setBorder(null);
					lblSubsystemsAoc.setBackground(new Color(153, 204, 255));
					lblSubsystemsAoc.setAlignmentX(0.5f);
					pan3.add(lblSubsystemsAoc);
				}
				{
					ssAOC = new JLabel(milestone.getAOCProgress()+" %");
					pan3.add(ssAOC);
				}
				monitorPanel.add(pan3, "6, 12, right, center");
				JPanel pan4 = new JPanel();
				{
					lblRemainingPunchItem_1 = new JLabel("Remaining Punch Item (To be cleared before AOC) :");
					lblRemainingPunchItem_1.setBorder(null);
					lblRemainingPunchItem_1.setBackground(new Color(255, 255, 153));
					lblRemainingPunchItem_1.setAlignmentX(0.5f);
					pan4.add(lblRemainingPunchItem_1);
				}
				PLB = new JLabel(milestone.getPunchBOpened()+"");
				if(Integer.valueOf(PLB.getText())>0)
					PLB.setForeground(Color.red);
				pan4.add(PLB);
				PLB.setFont(new Font("Tahoma", Font.BOLD, 11));
				monitorPanel.add(pan4, "6, 14, right, default");				
				
				JPanel pan5 = new JPanel();
				{
					lblPunchItemto = new JLabel("Remaining Punch Item (To be cleared before CC) :");
					lblPunchItemto.setBorder(null);
					lblPunchItemto.setBackground(new Color(255, 255, 153));
					lblPunchItemto.setAlignmentX(0.5f);
					pan5.add(lblPunchItemto);
				}
				{
					{
						PLC = new JLabel(milestone.getPunchCOpened()+"");
						pan5.add(PLC);
						PLC.setFont(new Font("Tahoma", Font.BOLD, 11));
						if(Integer.valueOf(PLC.getText())>0)
							PLC.setForeground(Color.red);
					}
				}
				monitorPanel.add(pan5, "6, 16, right, default");
			}
			{
				panel_2 = new JPanel();
				panel_2.setBorder(new TitledBorder(null, "Target", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				contentPanel.add(panel_2, BorderLayout.EAST);
				panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
				{
					btnShowTarget = new JButton("Show Target");
					panel_2.add(btnShowTarget);
					btnShowTarget.setFont(new Font("Arial", Font.PLAIN, 11));
				}
			}
			{
				targetPanel = new JPanel();
				//showTarget.addTab("Target", null, targetPanel, null);
				targetPanel.setLayout(new FormLayout(new ColumnSpec[] {
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),},
					new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"),}));
				{
					showTargetButton = new JButton("Show Target");
					showTargetButton.setFont(new Font("Arial", Font.PLAIN, 11));
					targetPanel.add(showTargetButton, "2, 2, center, default");
				}
			}
		}
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setFont(new Font("Arial", Font.PLAIN, 12));
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
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{getContentPane(), contentPanel, showTarget, monitorPanel, buttonPane, okButton, cancelButton}));
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.pack();
	}
	
	/**
	 * @return TargetDateJTextField
	 */
	public JTextField getTargetDateJTextField(){
		return targetDate;
	}
	
	//getters & setters
	/**
	 * @return Milestone
	 */
	public Milestone getModel(){
		return this.model;
	}
	
	/**
	 * @param milestone
	 */
	public void setModel(Milestone milestone){
		this.model=milestone;
	}
	/**
	 * @return DescriptionJTextArea
	 */
	public JTextArea getDescriptionJTextArea() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(JTextArea description) {
		this.description = description;
	}

	/**
	 * @return MilestoneNameJTextField
	 */
	public JTextField getMilestoneNameJTextField() {
		return name;
	}

	/**
	 * @param jtextFieldContainingName
	 */
	public void setName(JTextField jtextFieldContainingName) {
		this.name = jtextFieldContainingName;
	}

	/**
	 * @param jTextFieldContainingTargetDate
	 */
	public void setTargetDate(JTextField jTextFieldContainingTargetDate) {
		targetDate = jTextFieldContainingTargetDate;
	}
	
	/**
	 * @return ValidateButton
	 */
	public JButton getValidateButton(){
		return this.okButton;
	}
	
	/**
	 * @return CancelButton
	 */
	public JButton getCancelButton(){
		return this.cancelButton;
	}
	
	//Listerners
	/**
	 * @param actionListener
	 */
	public void addTargetDatePickerListener(ActionListener actionListener){
		this.targetDatePicker.addActionListener(actionListener);
	}
	
	/**
	 * @param actionListener
	 */
	public void addValidateListener(ActionListener actionListener){
		this.okButton.addActionListener(actionListener);
	}
	
	/**
	 * @param actionListener
	 */
	public void addCancelListener(ActionListener actionListener){
		this.cancelButton.addActionListener(actionListener);
	}

	/**
	 * @param actionListener
	 */
	public void addUpdateSUDocListener(ActionListener actionListener){
		this.button_1.addActionListener(actionListener);
	}
	
	/**
	 * @param actionListener
	 */
	public void addUpdateCommProgressListener(ActionListener actionListener){
		this.button_1.addActionListener(actionListener);
	}
	/**
	 * @param actionListener
	 */
	public void addOpenTargetListener(ActionListener actionListener){
		this.showTargetButton.addActionListener(actionListener);
		this.btnShowTarget.addActionListener(actionListener);
	}

	/**
	 * @param actionListener
	 */
	public void addOpenSUSDocListerner(ActionListener actionListener) {
		this.button.addActionListener(actionListener);
	}

	/**
	 * @param actionListener
	 */
	public void addShowSubSystemlistener(ActionListener actionListener) {
		this.showSubSysButton.addActionListener(actionListener);
	}


}
