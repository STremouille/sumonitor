package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Toolkit;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;

import javax.swing.ImageIcon;

import java.awt.Insets;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingConstants;

import conf.GeneralConfig;
import main.Clock;
import main.Controller;
import main.View;

/**
 * @author Samuel Trémouille
 */
public class AboutView extends JDialog{

    private final JPanel contentPanel = new JPanel();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	try {
	    AboutView dialog = new AboutView();
	    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	    dialog.setVisible(true);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /**
     * Create the dialog.
     */
    public AboutView() {
    	setTitle("About Start-Up Monitor");
    	setIconImage(Toolkit.getDefaultToolkit().getImage(AboutView.class.getResource("/img/icone.png")));
	setBounds(100, 100, 519, 403);
	getContentPane().setLayout(new BorderLayout());
	contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	getContentPane().add(contentPanel, BorderLayout.CENTER);
	GridBagLayout gbl_contentPanel = new GridBagLayout();
	gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
	gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
	gbl_contentPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
	gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
	contentPanel.setLayout(gbl_contentPanel);
	{
		JLabel lblPropertyOfTotal = new JLabel("Property of TOTAL SA.");
		lblPropertyOfTotal.setFont(new Font("Arial", Font.PLAIN, 13));
		GridBagConstraints gbc_lblPropertyOfTotal = new GridBagConstraints();
		gbc_lblPropertyOfTotal.anchor = GridBagConstraints.WEST;
		gbc_lblPropertyOfTotal.insets = new Insets(0, 0, 5, 0);
		gbc_lblPropertyOfTotal.gridx = 1;
		gbc_lblPropertyOfTotal.gridy = 0;
		contentPanel.add(lblPropertyOfTotal, gbc_lblPropertyOfTotal);
	}
	{
		JLabel lblAllRightReserved = new JLabel("All right reserved.");
		lblAllRightReserved.setFont(new Font("Arial", Font.PLAIN, 13));
		GridBagConstraints gbc_lblAllRightReserved = new GridBagConstraints();
		gbc_lblAllRightReserved.anchor = GridBagConstraints.WEST;
		gbc_lblAllRightReserved.insets = new Insets(0, 0, 5, 0);
		gbc_lblAllRightReserved.gridx = 1;
		gbc_lblAllRightReserved.gridy = 1;
		contentPanel.add(lblAllRightReserved, gbc_lblAllRightReserved);
	}
	{
		JLabel lblTechnicalSupport = new JLabel("Technical support :");
		lblTechnicalSupport.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_lblTechnicalSupport = new GridBagConstraints();
		gbc_lblTechnicalSupport.anchor = GridBagConstraints.EAST;
		gbc_lblTechnicalSupport.insets = new Insets(0, 0, 5, 5);
		gbc_lblTechnicalSupport.gridx = 0;
		gbc_lblTechnicalSupport.gridy = 2;
		contentPanel.add(lblTechnicalSupport, gbc_lblTechnicalSupport);
	}
	{
		JLabel lblIcapstotalcom = new JLabel("icaps@total.com");
		lblIcapstotalcom.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_lblIcapstotalcom = new GridBagConstraints();
		gbc_lblIcapstotalcom.insets = new Insets(0, 0, 5, 0);
		gbc_lblIcapstotalcom.anchor = GridBagConstraints.WEST;
		gbc_lblIcapstotalcom.gridx = 1;
		gbc_lblIcapstotalcom.gridy = 2;
		contentPanel.add(lblIcapstotalcom, gbc_lblIcapstotalcom);
	}
	{
		JLabel lblRelease = new JLabel("Version "+GeneralConfig.version);
		lblRelease.setFont(new Font("Arial", Font.PLAIN, 13));
		GridBagConstraints gbc_lblRelease = new GridBagConstraints();
		gbc_lblRelease.anchor = GridBagConstraints.WEST;
		gbc_lblRelease.gridx = 1;
		gbc_lblRelease.gridy = 3;
		contentPanel.add(lblRelease, gbc_lblRelease);
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
			dispose();
		    }
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
	    }
	}
	{
		JLabel lblSum = new JLabel("");
		lblSum.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblSum, BorderLayout.NORTH);
		lblSum.setIcon(new ImageIcon(AboutView.class.getResource("/img/sum.png")));
		lblSum.setForeground(Color.BLUE);
		lblSum.setFont(new Font("Arial Black", Font.PLAIN, 50));
	}
	
    }


}
