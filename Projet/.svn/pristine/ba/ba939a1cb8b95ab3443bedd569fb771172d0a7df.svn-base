package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableColumn;

import model.Milestone;
import subsystem.BooleanCellRenderer;
import subsystem.DataCellRenderer;
import subsystem.DataMilestoneTableModel;
import subsystem.DateCellRenderer;
import subsystem.MultiLineHeaderRenderer;
import subsystem.OtpPvp;
import subsystem.OtpPvpCellRenderer;
import subsystem.TableColumnAdjuster;

import javax.swing.ImageIcon;

import com.packenius.library.xspdf.XSPDF;

import conf.GeneralConfig;

/**
 * 
 * @author S.Trémouille
 *
 */

public class SubsytemFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2345686771473508775L;
	
	
	ArrayList<HashMap<String, Object>> data;
	private JTable table;
	private JFrame view = this;

	/**
	 * @param milestone
	 * @param data
	 */
	public SubsytemFrame(final Milestone milestone,final ArrayList<HashMap<String, Object>> data) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MilestoneEditorFrame.class.getResource("/img/icone.png")));
		this.setTitle(milestone.getName());
//		table.setBorder(UIManager.getBorder("InternalFrame.border"));


		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		JLabel header = new JLabel(milestone.getName()+" : "+milestone.getDescription());
		getContentPane().add(header,BorderLayout.NORTH);
		
		this.data=data;
		MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer();
		
		Toolkit tk = Toolkit.getDefaultToolkit();  
		int ySize = ((int) tk.getScreenSize().getHeight());
		header.setFont(new Font("Arial Unicode MS", Font.PLAIN, 15));
		
		JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		table = new JTable();
		table.setBorder(null);
		table.setFont(new Font("Arial Unicode MS", Font.PLAIN, 11));
		table.setDefaultRenderer(Integer.class, new DataCellRenderer());
		table.setDefaultRenderer(Double.class, new DataCellRenderer());
		table.setDefaultRenderer(String.class, new DataCellRenderer());
		table.setDefaultRenderer(Boolean.class, new BooleanCellRenderer());
		table.setDefaultRenderer(Date.class, new DateCellRenderer());
		table.setDefaultRenderer(OtpPvp.class, new OtpPvpCellRenderer());
		table.setModel(new DataMilestoneTableModel(data));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setAutoCreateRowSorter(true);
		table.setRowSelectionAllowed(true);
		table.getTableHeader().setReorderingAllowed(false);
		Enumeration<TableColumn> enumeration = table.getColumnModel().getColumns();
		while (enumeration.hasMoreElements()) {
		    enumeration.nextElement().setHeaderRenderer(renderer);
    	    	} 
		final JScrollPane scrollPane = new JScrollPane(table);
		tabbedPane.addTab("List", null, scrollPane, null);
		//ResizeColumnListener rcl = new ResizeColumnListener(table);
		TableColumnAdjuster tca = new TableColumnAdjuster(table);
		
		final JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{92, 0};
		gbl_panel.rowHeights = new int[]{17, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel ssNumber;
		if(data!= null)
		    ssNumber = new JLabel(data.size()+" sub-systems      ");
		else
		    ssNumber = new JLabel("No sub-systems      ");
		ssNumber.setFont(new Font("Arial Unicode MS", Font.BOLD, 12));
		ssNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_ssNumber = new GridBagConstraints();
		gbc_ssNumber.anchor = GridBagConstraints.SOUTHEAST;
		gbc_ssNumber.gridx = 0;
		gbc_ssNumber.gridy = 0;
		panel.add(ssNumber, gbc_ssNumber);
		this.setSize(tca.adjustColumns(), ySize );
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExportToExcel = new JMenuItem("Export to Excel");
		mntmExportToExcel.setIcon(new ImageIcon(SubsytemFrame.class.getResource("/img/docPath.png")));
		mntmExportToExcel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ExportSubsystemListToXLSXFrame f = new ExportSubsystemListToXLSXFrame(data);
				f.setVisible(true);
			}
		});
		mnFile.add(mntmExportToExcel);
		
		JMenuItem mntmPrintToPdf = new JMenuItem("Print to PDF");
		mntmPrintToPdf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					view.repaint();
					MessageFormat header = new MessageFormat(milestone.getName()+" : "+milestone.getDescription());
					MessageFormat footer = new MessageFormat("Page {0,number,integer}");
					PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
					pras.add(OrientationRequested.LANDSCAPE);
					table.print(JTable.PrintMode.FIT_WIDTH, header, footer, true, pras, true);

				} catch (PrinterException e1) {
					e1.printStackTrace();
				}
				//Place where pdf is created
				/*JFileChooser jfcForPDF = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".pdf", "pdf");
				jfcForPDF.setFileFilter(filter);
				int returnVal = jfcForPDF.showOpenDialog(view);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File pdf = jfcForPDF.getSelectedFile();
					String name = pdf.getName();
					int ext = name.lastIndexOf('.');
					if (ext != -1) {
						String extension = name.substring(ext, name.length());
						if (extension != "pdf") {
							name = name.substring(0, ext) + ".pdf";
						}
					} else {
						name = name + ".pdf";
					}
					//System.out.println(name);
					//System.out.println(pdf.getPath());
					//set the new filename
					String folder = pdf.getPath().substring(0, pdf.getPath().lastIndexOf(File.separatorChar));
					File newpdf = new File(folder + File.separatorChar + name);
					//System.out.println(folder + File.separatorChar + name);

					//Create PDF
					int w = getContentPane().getWidth() + 4;
					int h = getContentPane().getHeight()+table.getRowCount()*table.getRowHeight()-scrollPane.getHeight() + 4;
					getContentPane().setPreferredSize(new Dimension(w, h));
					getContentPane().repaint();
					
					BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
					Graphics2D g2d = bi.createGraphics();
					getContentPane().paint(g2d);
					g2d.dispose();
					try {
						XSPDF xspdf = XSPDF.getInstance();
						xspdf.setImageCompressionQuality((float) 1.0);
						xspdf.setPageSize(w, h).setPageMargin(2.0).setImage(bi, 0, 0, w, h, 0).createPdf(newpdf);
						//System.out.println(XSPDF.getInstance().getContentEncoding());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}*/
			}
		});
		mntmPrintToPdf.setIcon(new ImageIcon(SubsytemFrame.class.getResource("/img/print.png")));
		mnFile.add(mntmPrintToPdf);
	}

}