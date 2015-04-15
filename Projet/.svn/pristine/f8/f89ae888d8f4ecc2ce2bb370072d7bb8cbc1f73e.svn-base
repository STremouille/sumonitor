package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.TreeMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DialShape;
import org.jfree.chart.plot.MeterInterval;
import org.jfree.chart.plot.MeterPlot;
import org.jfree.data.Range;
import org.jfree.data.general.DefaultValueDataset;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.packenius.library.xspdf.XSPDF;

import java.awt.Component;
import java.awt.Insets;
import java.awt.FlowLayout;
/**
 * Project Resume View
 * @author S.Trémouille
 *
 */
public class ProjectResume extends JFrame {

    	private static final long serialVersionUID = -8328257831807024814L;
	private JPanel contentPane;
	private JFrame view = this;
	private JButton btnOk;
	private Font font;
	/**
	 * Launch the application in order to debug.
	 * @param args 
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
				    	TreeMap<String,Object> data = new TreeMap<String, Object>();
				    	data.put("progressMhrs","150000.0/200000.0");
				    	data.put("precomm",95.2);
				    	data.put("comm",51.51);
				    	data.put("ssRfc",12);
				    	data.put("ssAoc",2);
				    	data.put("ssNumber",15);
				    	data.put("pla",15);
				    	data.put("plb",16);
				    	data.put("plc",17);
					ProjectResume frame = new ProjectResume(data);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param data 
	 */
	public ProjectResume(TreeMap<String, Object> data) {
	    	font = new Font("Arial", 1, 14);
		setForeground(Color.WHITE);
		DecimalFormat format = new DecimalFormat("###,###,###");
		DecimalFormatSymbols dfs = format.getDecimalFormatSymbols();
		dfs.setGroupingSeparator(' ');
		setTitle("Project Resume");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProjectResume.class.getResource("/img/icone.png")));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 614, 580);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmPrint = new JMenuItem("Print");
		mntmPrint.addActionListener(new PrintResumeListener());
		mntmPrint.setIcon(new ImageIcon(ProjectResume.class.getResource("/img/print.png")));
		mntmPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		mnFile.add(mntmPrint);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		mntmClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
		mnFile.add(mntmClose);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_3.setOpaque(false);
		panel_3.setBackground(Color.WHITE);
		contentPane.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(31dlu;default):grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		/*JProgressBar progressBarPrecomm = new JProgressBar();
		progressBarPrecomm.setBackground(Color.WHITE);
		panel_3.add(progressBarPrecomm, "4, 2, default, center");
		progressBarPrecomm.setFont(new Font("Arial", Font.BOLD, 11));
		progressBarPrecomm.setForeground(new Color(50, 205, 50));
		progressBarPrecomm.setStringPainted(true);
		progressBarPrecomm.setValue(((Double) data.get("precomm")).intValue());*/
		MeterPlot mp = new MeterPlot(new DefaultValueDataset(((Double) data.get("precomm")).intValue()));
		mp.setRange(new Range(0.0D, 100D));   
		/*mp.addInterval(new MeterInterval("Normal", new Range(0.0D, 50D), Color.gray, new BasicStroke(0.5F), null));   
		mp.addInterval(new MeterInterval("Warning", new Range(50D, 95D), Color.gray, new BasicStroke(0.5F), null));   
		mp.addInterval(new MeterInterval("Critical", new Range(95D, 100D), Color.gray, new BasicStroke(0.5F), new Color(0, 255, 0, 64)));  */ 
		mp.setNeedlePaint(new Color(184,240,158));  
		this.drawInterval(mp);
		mp.setDialBackgroundPaint(Color.lightGray);   
		mp.setDialOutlinePaint(Color.green);  
		mp.setOutlineStroke(new BasicStroke(0.1f));
		mp.setMeterAngle(260);   
		mp.setTickLabelsVisible(true);   
		mp.setTickLabelFont(new Font("Dialog", 1, 10));   
		mp.setTickLabelPaint(Color.black);   
		mp.setTickSize(5D);   
		mp.setUnits("%");
		mp.setTickPaint(Color.black);   
		mp.setValuePaint(Color.white); 
		mp.setDialShape(DialShape.CHORD);
		//mp.setTickLabelFormat(new DecimalFormat("#,##0.0"));
		mp.setValueFont(font);   
		mp.setBackgroundAlpha(0.5f);
		
        JFreeChart jfreechart = new JFreeChart("Pre-comm Progress", font, mp, false);
        
        JPanel panel_4 = new JPanel();
        panel_4.setBackground(new Color(0, 255, 127));
        panel_3.add(panel_4, "2, 2, fill, fill");
        
        JLabel lblSubsystemRfc = new JLabel("Pre-commissioning");
        lblSubsystemRfc.setFont(new Font("Arial", Font.BOLD, 14));
        lblSubsystemRfc.setBackground(Color.WHITE);
        panel_4.add(lblSubsystemRfc);
		
		/*JProgressBar progressBarComm = new JProgressBar();
		progressBarComm.setBackground(Color.WHITE);
		panel_3.add(progressBarComm, "4, 4, fill, center");
		progressBarComm.setFont(new Font("Arial", Font.BOLD, 11));
		progressBarComm.setForeground(Color.BLUE);
		progressBarComm.setStringPainted(true);
		progressBarComm.setValue(((Double) data.get("comm")).intValue());*/
		
		MeterPlot mp1 = new MeterPlot(new DefaultValueDataset(((Double) data.get("comm")).intValue()));
		mp1.setRange(new Range(0.0D, 100D));   
		/*mp1.addInterval(new MeterInterval("Normal", new Range(0.0D, 50D), Color.gray, new BasicStroke(0.5F), null));   
		mp1.addInterval(new MeterInterval("Warning", new Range(50D, 95D), Color.gray, new BasicStroke(0.5F), null));   
		mp1.addInterval(new MeterInterval("Critical", new Range(95D, 100D), Color.gray, new BasicStroke(0.5F), new Color(0, 255, 0, 64)));  */ 
		mp1.setNeedlePaint(new Color(158, 170, 240));
		this.drawInterval(mp1);  
		mp1.setDialBackgroundPaint(Color.lightGray);   
		mp1.setDialOutlinePaint(Color.blue);   
		mp1.setMeterAngle(260); 
		mp1.setOutlineStroke(new BasicStroke(0.1f));
		mp1.setTickLabelsVisible(true);   
		mp1.setTickLabelFont(new Font("Dialog", 1, 10));   
		mp1.setTickLabelPaint(Color.black);   
		mp1.setTickSize(5D); 
		mp1.setUnits("%");
		mp1.setTickPaint(Color.black);
		//mp1.setTickLabelFormat(new DecimalFormat("#,##0.0"));
		mp1.setValuePaint(Color.white);  
		mp1.setDialShape(DialShape.CHORD);
		mp1.setBackgroundAlpha(0.5f);
		mp1.setValueFont(font);
		
        JFreeChart jfreechart1 = new JFreeChart("Comm Progress", font, mp1, false);
        
        JPanel panel_1 = new JPanel();
        panel_3.add(panel_1, "2, 4, fill, fill");
        GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.columnWidths = new int[]{250, 0, 0};
        gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        gbl_panel_1.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel_1.setLayout(gbl_panel_1);
        
        JLabel lblEarnedEstimated = new JLabel(" Earned / Estimated (Mhrs) :");
        GridBagConstraints gbc_lblEarnedEstimated = new GridBagConstraints();
        gbc_lblEarnedEstimated.anchor = GridBagConstraints.EAST;
        gbc_lblEarnedEstimated.insets = new Insets(0, 0, 5, 5);
        gbc_lblEarnedEstimated.gridx = 0;
        gbc_lblEarnedEstimated.gridy = 0;
        panel_1.add(lblEarnedEstimated, gbc_lblEarnedEstimated);
        
        JLabel ratioPrecom = new JLabel(data.get("precommEarned")+" / "+data.get("precommEstimated"));
        ratioPrecom.setFont(new Font("Arial", Font.BOLD, 12));
        GridBagConstraints gbc_ratioPrecom = new GridBagConstraints();
        gbc_ratioPrecom.anchor = GridBagConstraints.WEST;
        gbc_ratioPrecom.insets = new Insets(0, 0, 5, 0);
        gbc_ratioPrecom.gridx = 1;
        gbc_ratioPrecom.gridy = 0;
        panel_1.add(ratioPrecom, gbc_ratioPrecom);
        
        JLabel lblProgress = new JLabel("Progress :");
        GridBagConstraints gbc_lblProgress = new GridBagConstraints();
        gbc_lblProgress.anchor = GridBagConstraints.EAST;
        gbc_lblProgress.insets = new Insets(0, 0, 5, 5);
        gbc_lblProgress.gridx = 0;
        gbc_lblProgress.gridy = 1;
        panel_1.add(lblProgress, gbc_lblProgress);
        
        JLabel precommProgress = new JLabel(data.get("precomm")+"%");
        precommProgress.setFont(new Font("Arial", Font.BOLD, 12));
        GridBagConstraints gbc_precommProgress = new GridBagConstraints();
        gbc_precommProgress.anchor = GridBagConstraints.WEST;
        gbc_precommProgress.insets = new Insets(0, 0, 5, 0);
        gbc_precommProgress.gridx = 1;
        gbc_precommProgress.gridy = 1;
        panel_1.add(precommProgress, gbc_precommProgress);
        
        JLabel lblSubsystemsRfc = new JLabel("Sub-systems RFC :");
        GridBagConstraints gbc_lblSubsystemsRfc = new GridBagConstraints();
        gbc_lblSubsystemsRfc.anchor = GridBagConstraints.EAST;
        gbc_lblSubsystemsRfc.insets = new Insets(0, 0, 5, 5);
        gbc_lblSubsystemsRfc.gridx = 0;
        gbc_lblSubsystemsRfc.gridy = 2;
        panel_1.add(lblSubsystemsRfc, gbc_lblSubsystemsRfc);
        
        Integer ssRFC=(Integer) data.get("ssRfc");
        JLabel ratioRFC = new JLabel(ssRFC+"/"+data.get("ssNumber"));
        ratioRFC.setFont(new Font("Arial", Font.BOLD, 12));
        GridBagConstraints gbc_ratioRFC = new GridBagConstraints();
        gbc_ratioRFC.anchor = GridBagConstraints.WEST;
        gbc_ratioRFC.insets = new Insets(0, 0, 5, 0);
        gbc_ratioRFC.gridx = 1;
        gbc_ratioRFC.gridy = 2;
        panel_1.add(ratioRFC, gbc_ratioRFC);
        
        JLabel lblSussystemsRfcProgress = new JLabel(" Sus-systems RFC Progress :");
        GridBagConstraints gbc_lblSussystemsRfcProgress = new GridBagConstraints();
        gbc_lblSussystemsRfcProgress.anchor = GridBagConstraints.EAST;
        gbc_lblSussystemsRfcProgress.insets = new Insets(0, 0, 5, 5);
        gbc_lblSussystemsRfcProgress.gridx = 0;
        gbc_lblSussystemsRfcProgress.gridy = 3;
        panel_1.add(lblSussystemsRfcProgress, gbc_lblSussystemsRfcProgress);
        
        JLabel ssRFCProgress = new JLabel(new Double((int)
        	((new Double(ssRFC)/
        		new Double((Integer)data.get("ssNumber")))*1000.0))/10.0+"%");
        ssRFCProgress.setFont(new Font("Arial", Font.BOLD, 12));
        GridBagConstraints gbc_ssRFCProgress = new GridBagConstraints();
        gbc_ssRFCProgress.anchor = GridBagConstraints.WEST;
        gbc_ssRFCProgress.insets = new Insets(0, 0, 5, 0);
        gbc_ssRFCProgress.gridx = 1;
        gbc_ssRFCProgress.gridy = 3;
        panel_1.add(ssRFCProgress, gbc_ssRFCProgress);
        
        JLabel lblPunchA = new JLabel("Punch A Opened :");
        GridBagConstraints gbc_lblPunchA = new GridBagConstraints();
        gbc_lblPunchA.anchor = GridBagConstraints.EAST;
        gbc_lblPunchA.insets = new Insets(0, 0, 0, 5);
        gbc_lblPunchA.gridx = 0;
        gbc_lblPunchA.gridy = 4;
        panel_1.add(lblPunchA, gbc_lblPunchA);
        lblPunchA.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel label_3 = new JLabel(format.format(data.get("pla")));
        GridBagConstraints gbc_label_3 = new GridBagConstraints();
        gbc_label_3.anchor = GridBagConstraints.WEST;
        gbc_label_3.gridx = 1;
        gbc_label_3.gridy = 4;
        panel_1.add(label_3, gbc_label_3);
        label_3.setFont(new Font("Arial", Font.BOLD, 12));
        
        JPanel panel_6 = new JPanel();
        panel_6.setBackground(new Color(0,149,255));
        panel_3.add(panel_6, "2, 6, fill, fill");
        
        JLabel lblCommissioning = new JLabel("Commissioning");
        lblCommissioning.setHorizontalTextPosition(SwingConstants.LEADING);
        lblCommissioning.setHorizontalAlignment(SwingConstants.LEFT);
        lblCommissioning.setFont(new Font("Arial", Font.BOLD, 14));
        panel_6.add(lblCommissioning);
		
		JPanel panel_2 = new JPanel();
		panel_3.add(panel_2, "2, 8, fill, fill");
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{250, 0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblEarnedEstimated_1 = new JLabel(" Earned / Estimated (Mhrs) :");
		GridBagConstraints gbc_lblEarnedEstimated_1 = new GridBagConstraints();
		gbc_lblEarnedEstimated_1.anchor = GridBagConstraints.EAST;
		gbc_lblEarnedEstimated_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblEarnedEstimated_1.gridx = 0;
		gbc_lblEarnedEstimated_1.gridy = 0;
		panel_2.add(lblEarnedEstimated_1, gbc_lblEarnedEstimated_1);
		
		JLabel ratioComm = new JLabel(data.get("commEarned")+" / "+data.get("commEstimated"));
		ratioComm.setFont(new Font("Arial", Font.BOLD, 12));
		GridBagConstraints gbc_ratioComm = new GridBagConstraints();
		gbc_ratioComm.anchor = GridBagConstraints.WEST;
		gbc_ratioComm.insets = new Insets(0, 0, 5, 0);
		gbc_ratioComm.gridx = 1;
		gbc_ratioComm.gridy = 0;
		panel_2.add(ratioComm, gbc_ratioComm);
		
		JLabel label = new JLabel("Progress :");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 1;
		panel_2.add(label, gbc_label);
		
		JLabel commProgress = new JLabel(data.get("comm")+"%");
		commProgress.setFont(new Font("Arial", Font.BOLD, 12));
		GridBagConstraints gbc_commProgress = new GridBagConstraints();
		gbc_commProgress.anchor = GridBagConstraints.WEST;
		gbc_commProgress.insets = new Insets(0, 0, 5, 0);
		gbc_commProgress.gridx = 1;
		gbc_commProgress.gridy = 1;
		panel_2.add(commProgress, gbc_commProgress);
		
		JLabel lblSubsystemsAoc = new JLabel("Sub-systems AOC :");
		GridBagConstraints gbc_lblSubsystemsAoc = new GridBagConstraints();
		gbc_lblSubsystemsAoc.anchor = GridBagConstraints.EAST;
		gbc_lblSubsystemsAoc.insets = new Insets(0, 0, 5, 5);
		gbc_lblSubsystemsAoc.gridx = 0;
		gbc_lblSubsystemsAoc.gridy = 2;
		panel_2.add(lblSubsystemsAoc, gbc_lblSubsystemsAoc);
		
		JLabel ratioSSAOC = new JLabel(data.get("ssAoc")+"/"+data.get("ssNumber"));
		ratioSSAOC.setFont(new Font("Arial", Font.BOLD, 12));
		GridBagConstraints gbc_ratioSSAOC = new GridBagConstraints();
		gbc_ratioSSAOC.anchor = GridBagConstraints.WEST;
		gbc_ratioSSAOC.insets = new Insets(0, 0, 5, 0);
		gbc_ratioSSAOC.gridx = 1;
		gbc_ratioSSAOC.gridy = 2;
		panel_2.add(ratioSSAOC, gbc_ratioSSAOC);
		
		JLabel lblSubsystemsAocProgress = new JLabel(" Sub-systems AOC Progress :");
		GridBagConstraints gbc_lblSubsystemsAocProgress = new GridBagConstraints();
		gbc_lblSubsystemsAocProgress.anchor = GridBagConstraints.EAST;
		gbc_lblSubsystemsAocProgress.insets = new Insets(0, 0, 5, 5);
		gbc_lblSubsystemsAocProgress.gridx = 0;
		gbc_lblSubsystemsAocProgress.gridy = 3;
		panel_2.add(lblSubsystemsAocProgress, gbc_lblSubsystemsAocProgress);
		
		JLabel ssAOCProgress = new JLabel(new Double((int)((new Double((Integer)data.get("ssAoc"))/new Double((Integer)data.get("ssNumber")))*1000.0))/10.0+"%");
		ssAOCProgress.setFont(new Font("Arial", Font.BOLD, 12));
		GridBagConstraints gbc_ssAOCProgress = new GridBagConstraints();
		gbc_ssAOCProgress.anchor = GridBagConstraints.WEST;
		gbc_ssAOCProgress.insets = new Insets(0, 0, 5, 0);
		gbc_ssAOCProgress.gridx = 1;
		gbc_ssAOCProgress.gridy = 3;
		panel_2.add(ssAOCProgress, gbc_ssAOCProgress);
		
		JLabel lblPunchB = new JLabel("Punch B Opened:");
		GridBagConstraints gbc_lblPunchB = new GridBagConstraints();
		gbc_lblPunchB.anchor = GridBagConstraints.EAST;
		gbc_lblPunchB.insets = new Insets(0, 0, 0, 5);
		gbc_lblPunchB.gridx = 0;
		gbc_lblPunchB.gridy = 4;
		panel_2.add(lblPunchB, gbc_lblPunchB);
		lblPunchB.setFont(new Font("Arial", Font.PLAIN, 12));
		
		
		JLabel label_4 = new JLabel(format.format(data.get("plb")));
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.anchor = GridBagConstraints.WEST;
		gbc_label_4.gridx = 1;
		gbc_label_4.gridy = 4;
		panel_2.add(label_4, gbc_label_4);
		label_4.setFont(new Font("Arial", Font.BOLD, 12));
		
		JPanel panel_8 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_8.getLayout();
		panel_8.setBackground(new Color(255, 215, 0));
		panel_3.add(panel_8, "2, 10, fill, fill");
		
		JLabel lblNewLabel = new JLabel("Start-Up");
		lblNewLabel.setHorizontalTextPosition(SwingConstants.LEADING);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel_8.add(lblNewLabel);
		
		JPanel panel_9 = new JPanel();
		panel_3.add(panel_9, "2, 12, fill, fill");
		GridBagLayout gbl_panel_9 = new GridBagLayout();
		gbl_panel_9.columnWidths = new int[]{250, 0, 0};
		gbl_panel_9.rowHeights = new int[]{0, 0};
		gbl_panel_9.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_9.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_9.setLayout(gbl_panel_9);
		
		JLabel lblPunchC = new JLabel("Punch C Opened:");
		GridBagConstraints gbc_lblPunchC = new GridBagConstraints();
		gbc_lblPunchC.anchor = GridBagConstraints.EAST;
		gbc_lblPunchC.insets = new Insets(0, 0, 0, 5);
		gbc_lblPunchC.gridx = 0;
		gbc_lblPunchC.gridy = 0;
		panel_9.add(lblPunchC, gbc_lblPunchC);
		lblPunchC.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel label_5 = new JLabel(format.format(data.get("plc")));
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.gridx = 1;
		gbc_label_5.gridy = 0;
		panel_9.add(label_5, gbc_label_5);
		label_5.setFont(new Font("Arial", Font.BOLD, 12));
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBackground(Color.WHITE);
		contentPane.add(panel, BorderLayout.SOUTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		btnOk = new JButton("Close");
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.gridx = 0;
		gbc_btnOk.gridy = 0;
		panel.add(btnOk, gbc_btnOk);
	}
	
	/**
	 * @param meterPlot
	 */
	public void drawInterval(MeterPlot meterPlot){
		int i = 0;
		while(i<10){
		    MeterInterval mi = new MeterInterval("", new Range(i*10, (i+1)*10),Color.black, new BasicStroke(0.5f), new Color(240,240,240)) ;
			meterPlot.addInterval(mi);
			i++;
		}
	}
	
	class PrintResumeListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//Place where pdf is created
		    	btnOk.setVisible(false);
			JFileChooser jfcForPDF = new JFileChooser();
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
				int w = contentPane.getWidth();
				int h = contentPane.getHeight();
				BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
				Graphics2D g2d = bi.createGraphics();
				contentPane.paint(g2d);
				g2d.dispose();
				try {
					XSPDF xspdf = XSPDF.getInstance();
					xspdf.setImageCompressionQuality((float) 1.0);
					xspdf.setPageSize(w, h).setPageMargin(2.0).setImage(bi, 0, 0, w, h, 0).createPdf(newpdf);
					//System.out.println(XSPDF.getInstance().getContentEncoding());
				} catch (IOException e) {
				    JOptionPane.showMessageDialog(view, e.getMessage());
					e.printStackTrace();
				}
			}
			btnOk.setVisible(true);
		}
		
	}
}
