package detailedStartUpSequence;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;

import javax.swing.JTextField;

import detailedStartUpSequence.Controller.NewSubsystemListener;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Samuel Trémouille
 */
public class NewSubsystemDialog extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField textField;


    /**
     * Create the dialog.
     */
    public NewSubsystemDialog(final NewSubsystemListener listener) {
	setBounds(100, 100, 450, 300);
	getContentPane().setLayout(new BorderLayout());
	contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	getContentPane().add(contentPanel, BorderLayout.CENTER);
	GridBagLayout gbl_contentPanel = new GridBagLayout();
	gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
	gbl_contentPanel.rowHeights = new int[]{0, 0};
	gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
	gbl_contentPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
	contentPanel.setLayout(gbl_contentPanel);
	{
		JLabel lblSubsystemNumber = new JLabel("Subsystem number :");
		GridBagConstraints gbc_lblSubsystemNumber = new GridBagConstraints();
		gbc_lblSubsystemNumber.insets = new Insets(0, 0, 0, 5);
		gbc_lblSubsystemNumber.anchor = GridBagConstraints.EAST;
		gbc_lblSubsystemNumber.gridx = 0;
		gbc_lblSubsystemNumber.gridy = 0;
		contentPanel.add(lblSubsystemNumber, gbc_lblSubsystemNumber);
	}
	{
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		contentPanel.add(textField, gbc_textField);
		textField.setColumns(10);
	}
	{
	    JPanel buttonPane = new JPanel();
	    buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
	    getContentPane().add(buttonPane, BorderLayout.SOUTH);
	    {
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
		    
		    @Override
		    public void actionPerformed(ActionEvent arg0) {
			if(textField.getText()!=""){
			    listener.returnSubsystemNumber(textField.getText());
			    dispose();
			}
		    }
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
	    }
	    {
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		cancelButton.addActionListener(new ActionListener() {
		    
		    @Override
		    public void actionPerformed(ActionEvent arg0) {
			dispose();
		    }
		});
		buttonPane.add(cancelButton);
	    }
	}
	this.pack();
    }

}
