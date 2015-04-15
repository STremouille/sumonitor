package view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import target.TargetView;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import conf.GeneralConfig;
import controller.Controller;
/**
 * 
 * @author S.Trémouille
 *
 */
public class ExitFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2379885349698174766L;
	private JPanel contentPane;
	private Controller controller;

	/**
	 * @param controller
	 */
	public ExitFrame(Controller controller) {
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(TargetView.class.getResource("/img/icone.png")));
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		this.controller=controller;
		this.setTitle("Exit");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		JButton btnSave = new JButton("Save & Quit");
		if(GeneralConfig.saveName!=""){
			btnSave.setEnabled(true);
		} else {
			btnSave.setEnabled(false);
		}
		btnSave.addActionListener(this.controller.getSaveProjectActionListener(false,true));
				panel.add(btnSave, "2, 2");
		
		JButton btnSaveAs = new JButton("Save As & Quit");
		btnSaveAs.addActionListener(controller.getSaveProjectActionListener(true,true));
			
		panel.add(btnSaveAs, "4, 2");
		
		JButton btnNo = new JButton("Quit");
		btnNo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				/*if(new File("companyLogo").exists())
					new File("companyLogo").delete();
				if(new File("contractorLogo").exists())
					new File("contractorLogo").delete();
				if(new File("projectLogo").exists())
					new File("projectLogo").delete();
				if(new File("logSUDoc.log").exists())
					new File("logSUDoc.log").delete();
				if(new File("SubSysOperated.sso").exists())
					new File("SubSysOperated.sso").delete();*/
				System.exit(0);
			}
		});
		panel.add(btnNo, "6, 2");
		
		JButton btnQuit = new JButton("Cancel");
		btnQuit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		panel.add(btnQuit, "8, 2");
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.NORTH);
		
		JLabel lblDoYouWant = new JLabel("Do you want to save before exit ?");
		lblDoYouWant.setFont(new Font("Arial", Font.PLAIN, 12));
		panel_1.add(lblDoYouWant);
		
		this.pack();
		this.setLocationRelativeTo(controller.getView());
	}
}
