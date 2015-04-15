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

import model.StartUpStep;
import conf.GeneralConfig;

public class StartUpStepFieldEdit extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private StartUpStep sus;

	
	/**
	 * Create the frame.
	 */
	public StartUpStepFieldEdit(final String indexString,final StartUpStep sus) {
		this.sus=sus;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(StartUpStepFieldEdit.class.getResource("/img/icone.png")));
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
		
		JLabel lblField = new JLabel(indexString.replaceAll("_", " ")+" : ");
		GridBagConstraints gbc_lblField = new GridBagConstraints();
		gbc_lblField.insets = new Insets(0, 0, 5, 5);
		gbc_lblField.anchor = GridBagConstraints.EAST;
		gbc_lblField.gridx = 0;
		gbc_lblField.gridy = 0;
		contentPane.add(lblField, gbc_lblField);
		
		textField = new JTextField(sus.getAttr(indexString));
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		contentPane.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sus.putAttr(indexString.replaceAll(" ", "_"),textField.getText());
				dispose();
				
			}
		});
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.gridx = 1;
		gbc_btnOk.gridy = 1;
		contentPane.add(btnOk, gbc_btnOk);
		
		
	}

}
