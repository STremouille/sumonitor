package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * ToolBar Panel
 * @author S.Trémouille
 *
 */

public class ToolBarPanel extends JPanel {
    	private static final long serialVersionUID = 5679047459697377629L;
	private JButton newMilestoneButton;
	private JButton newSequenceButton;
	private JButton newCommentButton;
	private JButton newStartUpTaskButton;
	private JButton btnModify;
	private JButton btnErase;
	private JLabel lblMilestone;
	private JLabel lblSequence;
	private JLabel lblNote;
	private JButton btnCisors;
	private JLabel lblToolbar;
	private JLabel lblTask;

	/**
	 * Create the panel.
	 */
	public ToolBarPanel() {
		setBackground(Color.WHITE);
		setBorder(null);
		setForeground(Color.BLACK);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{89, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 23, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		
		lblToolbar = new JLabel(" ");
		lblToolbar.setFont(new Font("Segoe UI Light", Font.BOLD, 15));
		GridBagConstraints gbc_lblToolbar = new GridBagConstraints();
		gbc_lblToolbar.insets = new Insets(0, 0, 5, 0);
		gbc_lblToolbar.gridx = 0;
		gbc_lblToolbar.gridy = 0;
		add(lblToolbar, gbc_lblToolbar);
		
		lblMilestone = new JLabel(" Milestone");
		lblMilestone.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_lblMilestone = new GridBagConstraints();
		gbc_lblMilestone.insets = new Insets(0, 0, 5, 0);
		gbc_lblMilestone.gridx = 0;
		gbc_lblMilestone.gridy = 1;
		add(lblMilestone, gbc_lblMilestone);
		newMilestoneButton = new JButton(new ImageIcon(ToolBarPanel.class.getResource("/img/milestone.png")));
		newMilestoneButton.setBackground(Color.WHITE);
		newMilestoneButton.setToolTipText("Drag & Drop to create a milestone");
		GridBagConstraints gbc_newMilestoneButton = new GridBagConstraints();
		gbc_newMilestoneButton.anchor = GridBagConstraints.NORTH;
		gbc_newMilestoneButton.insets = new Insets(0, 0, 5, 0);
		gbc_newMilestoneButton.gridx = 0;
		gbc_newMilestoneButton.gridy = 2;
		add(newMilestoneButton, gbc_newMilestoneButton);
		
		
		lblSequence = new JLabel(" Sequence");
		lblSequence.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_lblSequence = new GridBagConstraints();
		gbc_lblSequence.insets = new Insets(0, 0, 5, 0);
		gbc_lblSequence.gridx = 0;
		gbc_lblSequence.gridy = 3;
		add(lblSequence, gbc_lblSequence);
		newSequenceButton = new JButton(new ImageIcon(ToolBarPanel.class.getResource("/img/sequence.png")));
		newSequenceButton.setBackground(Color.WHITE);
		newSequenceButton.setToolTipText("Drag & Drop to create a sequence");
		GridBagConstraints gbc_newSequenceButton = new GridBagConstraints();
		gbc_newSequenceButton.anchor = GridBagConstraints.NORTH;
		gbc_newSequenceButton.insets = new Insets(0, 0, 5, 0);
		gbc_newSequenceButton.gridx = 0;
		gbc_newSequenceButton.gridy = 4;
		add(newSequenceButton, gbc_newSequenceButton);
		
		
		lblNote = new JLabel(" Note");
		lblNote.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_lblNote = new GridBagConstraints();
		gbc_lblNote.insets = new Insets(0, 0, 5, 0);
		gbc_lblNote.gridx = 0;
		gbc_lblNote.gridy = 5;
		add(lblNote, gbc_lblNote);
		newCommentButton = new JButton(new ImageIcon(ToolBarPanel.class.getResource("/img/comment.png")));
		newCommentButton.setBackground(Color.WHITE);
		newCommentButton.setToolTipText("Drag & Drop to create a note");
		GridBagConstraints gbc_newCommentButton = new GridBagConstraints();
		gbc_newCommentButton.insets = new Insets(0, 0, 5, 0);
		gbc_newCommentButton.gridx = 0;
		gbc_newCommentButton.gridy = 6;
		add(newCommentButton, gbc_newCommentButton);
		
		lblTask = new JLabel("Step");
		lblTask.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_lblTask = new GridBagConstraints();
		gbc_lblTask.insets = new Insets(0, 0, 5, 0);
		gbc_lblTask.gridx = 0;
		gbc_lblTask.gridy = 7;
		add(lblTask, gbc_lblTask);
		
		newStartUpTaskButton = new JButton("");
		newStartUpTaskButton.setIcon(new ImageIcon(ToolBarPanel.class.getResource("/img/task.png")));
		GridBagConstraints gbc_newStartUpTaskButton = new GridBagConstraints();
		gbc_newStartUpTaskButton.insets = new Insets(0, 0, 5, 0);
		gbc_newStartUpTaskButton.gridx = 0;
		gbc_newStartUpTaskButton.gridy = 8;
		add(newStartUpTaskButton, gbc_newStartUpTaskButton);
		
		/*imgCisors = new ImageIcon("image"+File.separator+"cisors-original.png");
		btnCisors = new JButton(imgCisors);
		GridBagConstraints gbc_btnCisors = new GridBagConstraints();
		gbc_btnCisors.insets = new Insets(0, 0, 0, 5);
		gbc_btnCisors.gridx = 0;
		gbc_btnCisors.gridy = 0;*/
		//panel_1.add(btnCisors, gbc_btnCisors);
		
		/*btnNewButton_1 = new JButton("New button");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 0;
		panel_1.add(btnNewButton_1, gbc_btnNewButton_1);*/
		

	}

	/**
	 * @return newMilestoneButton
	 */
	public JButton getNewStartUpTaskButton() {
		return newStartUpTaskButton;
	}
	/**
	 * @return newMilestoneButton
	 */
	public JButton getNewMilestoneButton() {
		return newMilestoneButton;
	}
	/**
	 * @return newSequenceButton
	 */
	public JButton getNewSequenceButton() {
		return newSequenceButton;
	}
	/**
	 * @return newCommentButton
	 */
	public JButton getNewCommentButton() {
		return newCommentButton;
	}
	/**
	 * @return btnModify (not displayed)
	 */
	public JButton getBtnModify() {
		return btnModify;
	}
	/**
	 * @return btnErase (not displayed)
	 */
	public JButton getBtnErase() {
		return btnErase;
	}
	
	/**
	 * @return btnCisors (not displayed)
	 */
	public JButton getBtnCisors(){
		return btnCisors;
	}
	
	/**
	 * @param actionListener
	 */
	public void addMilestoneButtonListener(ActionListener actionListener){
		this.newMilestoneButton.addActionListener(actionListener);
	}
	
	/**
	 * @param actionListener
	 */
	public void addStartUpTaskButtonListener(ActionListener actionListener){
		this.newStartUpTaskButton.addActionListener(actionListener);
	}
	/**
	 * @param actionListener
	 */
	public void addSequenceButtonListener(ActionListener actionListener){
		this.newSequenceButton.addActionListener(actionListener);
	}
	
	/**
	 * @param actionListener
	 */
	public void addCommentButtonListener(ActionListener actionListener){
		this.newCommentButton.addActionListener(actionListener);
	}
	
	/**
	 * @param actionListener
	 */
	public void addDeleteButtonListener(ActionListener actionListener){
		this.btnErase.addActionListener(actionListener);
	}
	
	/**
	 * @param actionListener
	 */
	public void addEditButtonListener(ActionListener actionListener){
		this.btnModify.addActionListener(actionListener);
	}
	
	/**
	 * @param actionListener
	 */
	public void addCisorsButtonListener(ActionListener actionListener){
		this.btnCisors.addActionListener(actionListener);
	}
	
	@Override
	public int getWidth(){
		return 89;
	}
	
		
}
