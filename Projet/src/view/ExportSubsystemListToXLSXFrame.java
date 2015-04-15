package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import subsystem.ExportSubsystemsToXLSXWorker;
/**
 * 
 * @author S.Trémouille
 *
 */
public class ExportSubsystemListToXLSXFrame extends JFrame{
	
    	private static final long serialVersionUID = 5489342094208890983L;
	private String path="empty";
	private JFrame v = this;
	private JProgressBar progressBar;
	private ArrayList<HashMap<String, Object>> data;
	private JLabel pathLbl;
	private JButton btnCancel,btnBrowse;
	private ExportSubsystemsToXLSXWorker worker;
	
	/**
	 * @param data
	 */
	public ExportSubsystemListToXLSXFrame(ArrayList<HashMap<String, Object>> data) {
		setTitle("Export to .xlsx");
		
		this.data=data;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(ExportSubsystemListToXLSXFrame.class.getResource("/img/icone.png")));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblPathOfThe = new JLabel("Path of the export file :");
		GridBagConstraints gbc_lblPathOfThe = new GridBagConstraints();
		gbc_lblPathOfThe.insets = new Insets(0, 0, 0, 5);
		gbc_lblPathOfThe.gridx = 1;
		gbc_lblPathOfThe.gridy = 0;
		panel.add(lblPathOfThe, gbc_lblPathOfThe);
		
		pathLbl = new JLabel(path);
		GridBagConstraints gbc_path = new GridBagConstraints();
		gbc_path.insets = new Insets(0, 0, 0, 5);
		gbc_path.gridx = 2;
		gbc_path.gridy = 0;
		panel.add(pathLbl, gbc_path);
		
		btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new BrowseListener());
		
		progressBar = new JProgressBar();
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.insets = new Insets(0, 0, 0, 5);
		gbc_progressBar.gridx = 3;
		gbc_progressBar.gridy = 0;
		panel.add(progressBar, gbc_progressBar);
		progressBar.setVisible(false);
		GridBagConstraints gbc_btnBrowse = new GridBagConstraints();
		gbc_btnBrowse.insets = new Insets(0, 0, 0, 5);
		gbc_btnBrowse.gridx = 4;
		gbc_btnBrowse.gridy = 0;
		panel.add(btnBrowse, gbc_btnBrowse);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setVisible(false);
		btnCancel.addActionListener(new CancelExportListener());
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.gridx = 5;
		gbc_btnCancel.gridy = 0;
		panel.add(btnCancel, gbc_btnCancel);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		JButton btnExport = new JButton("Export");
		btnExport.addActionListener(new ExportListener());
		panel_1.add(btnExport);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new CloseListener());
		panel_1.add(btnClose);
		
		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2, BorderLayout.NORTH);
		pack();
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{btnBrowse, btnExport}));
	}
	
	class CloseListener implements ActionListener{

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		worker.cancel(true);
		v.dispose();
	    }
	    
	}
	
	class BrowseListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser jfc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(".xlsx", "xlsx");
			jfc.setFileFilter(filter);
			int returnVal = jfc.showOpenDialog(v);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File exportFile = jfc.getSelectedFile();
				String name = exportFile.getName();
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
				String folder = exportFile.getPath().substring(0, exportFile.getPath().lastIndexOf(File.separatorChar));
				File newfops = new File(folder + File.separatorChar + name);
				path = newfops.getAbsolutePath();
				pathLbl.setText(path);
				v.pack();
			}
		}
	}
	
	class CancelExportListener implements ActionListener{

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		if(worker!=null){
		    worker.cancel(true);
		}
	    }
	    
	}
	
	class ExportListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			progressBar.setVisible(true);
			pathLbl.setVisible(false);
			btnCancel.setVisible(true);
			btnBrowse.setVisible(false);
			
			worker = new ExportSubsystemsToXLSXWorker(data,path);
			worker.addPropertyChangeListener(new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent e) {
					if(e.getPropertyName()=="progress"){
						progressBar.setValue((Integer) e.getNewValue());
					}
				}
			});
			worker.addPropertyChangeListener(new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent e) {
					if(e.getPropertyName()=="end"){
						progressBar.setVisible(false);
						pathLbl.setVisible(true);
						btnCancel.setVisible(false);
						btnBrowse.setVisible(true);
						v.pack();
						v.dispose();
					}
				}
			});
			worker.execute();
			v.pack();
		}
		
	}
}
