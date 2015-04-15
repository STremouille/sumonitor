package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Toolkit;
import java.awt.GridBagLayout;

import javax.swing.JTextField;

import java.awt.GridBagConstraints;

import javax.swing.JLabel;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import steps.StepsTableModel;
import conf.GeneralConfig;

public class StepField extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	
	/**
	 * Create the frame.
	 */
	public StepField(final int index,final SettingsFrame sf) {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(StepField.class.getResource("/img/icone.png")));
		setTitle("Step Field");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 321, 96);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblField = new JLabel("Field :");
		GridBagConstraints gbc_lblField = new GridBagConstraints();
		gbc_lblField.insets = new Insets(0, 0, 5, 5);
		gbc_lblField.anchor = GridBagConstraints.EAST;
		gbc_lblField.gridx = 0;
		gbc_lblField.gridy = 0;
		contentPane.add(lblField, gbc_lblField);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		contentPane.add(textField, gbc_textField);
		textField.setColumns(10);
		
		if(index!=-1)
			textField.setText(GeneralConfig.stepsField.get(index));
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(index==-1){
					if(textField.getText()!=""){
						GeneralConfig.stepsField.add(textField.getText().replaceAll(" ", "_"));
						GeneralConfig.stepFieldVisible.put(GeneralConfig.stepsField.indexOf(textField.getText().replaceAll(" ", "_")), false);
					}
				} else {
					GeneralConfig.stepsField.set(index, textField.getText().replaceAll(" ", "_"));
					GeneralConfig.stepFieldVisible.put(GeneralConfig.stepsField.indexOf(textField.getText().replaceAll(" ", "_")), false);
				}
				((StepsTableModel) sf.getStepsTable().getModel()).fireTableDataChange();
				sf.repaint();
				dispose();
				
			}
		});
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.gridx = 1;
		gbc_btnOk.gridy = 1;
		contentPane.add(btnOk, gbc_btnOk);
		
		
	}

}
