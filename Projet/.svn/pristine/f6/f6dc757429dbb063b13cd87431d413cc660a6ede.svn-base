package target;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Milestone;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.event.AxisChangeEvent;
import org.jfree.chart.event.AxisChangeListener;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.VerticalAlignment;

import com.packenius.library.xspdf.XSPDF;

import conf.GeneralConfig;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 * 
 * @author S.Trémouille
 *
 */
public class Target extends JFrame {

    	private static final long serialVersionUID = 3413403619491360620L;
	private ArrayList<Date> date;
	private ArrayList<Integer> progress;
	private ArrayList<Integer> nbreSSRFSU;
	private ArrayList<Integer> punchlistOpened;
	private TreeMap<Date,Integer> docProgress;
	private Integer totalProgress;
	private Integer totalSS;
	private Date lastDate;
	private DateAxis rangeAxis,rangeAxisInverted;
	private NumberAxis pourcentageAxis,pourcentageAxisInverted,punchlistAxis;
	private boolean nochange;
	private Font chartFont;
	private Milestone m;
	private Target view;
	
	/**
	 * @param milestone
	 * @param dataForTarget
	 */
	public Target(Milestone milestone,HashMap<String,Object> dataForTarget) {
	    super();
	    this.view=this;
	    if(milestone.getName().equals("%")){
		this.setTitle("Project Target");
	    } else {
		this.setTitle(milestone.getName());
	    }
		
		this.m=milestone;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JLabel milestoneName;
		if(milestone.getName().equals("%")){
		    milestoneName = new JLabel("Project Target",SwingConstants.CENTER);
		    } else {
			milestoneName = new JLabel(milestone.getName()+" : "+milestone.getDescription(),SwingConstants.CENTER);
		    }
		
		milestoneName.setFont(new Font("Arial Unicode MS", Font.BOLD, 25));
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(milestoneName,BorderLayout.NORTH);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(TargetView.class.getResource("/img/icone.png")));
		date=(ArrayList<Date>) dataForTarget.get("date");
		progress = (ArrayList<Integer>) dataForTarget.get("progress");
		nbreSSRFSU = (ArrayList<Integer>) dataForTarget.get("nbreSSRFSU");
		punchlistOpened = (ArrayList<Integer>) dataForTarget.get("punchlistOpened");
		totalProgress = (Integer) dataForTarget.get("totalProgress");
		totalSS = (Integer) dataForTarget.get("totalSS");
		lastDate = (Date) dataForTarget.get("lastDate");
		chartFont =  new Font("Arial Unicode MS", Font.ROMAN_BASELINE, 16);
		
		//Axis
		pourcentageAxis = new NumberAxis("");
		pourcentageAxis.setRange(0.0, 100.0);
		pourcentageAxis.setAutoRange(false);
		pourcentageAxis.addChangeListener(new AxisChangeListener() {
			boolean change = false;
			@Override
			public void axisChanged(AxisChangeEvent arg0) {
				if(!change){
						change=true;
						pourcentageAxis.setRange(0.0,100.0);
				} else {
					change = false;
				}
			}
		});
		
		pourcentageAxisInverted = new NumberAxis("");
		pourcentageAxisInverted.setRange(0.0,100.0);
		pourcentageAxisInverted.setAutoRange(false);
		pourcentageAxisInverted.setInverted(true);
		pourcentageAxisInverted.addChangeListener(new AxisChangeListener() {
			boolean change = false;
			@Override
			public void axisChanged(AxisChangeEvent arg0) {
				if(!change){
					change=true;
					pourcentageAxisInverted.setRange(0.0,100.0);
				} else {
					change = false;
				}
			}
		});
		
		
		punchlistAxis = new NumberAxis("");
		punchlistAxis.setRange(0.0,getMaxRangeForPunchlist());
		punchlistAxis.setAutoRange(false);
		punchlistAxis.addChangeListener(new AxisChangeListener() {
			boolean change = false;
			@Override
			public void axisChanged(AxisChangeEvent arg0) {
				if(!change){
					change=true;
					punchlistAxis.setRange(0.0,getMaxRangeForPunchlist());
				} else {
					change = false;
				}
			}
		});
		
		//Date Axis
		rangeAxis = new DateAxis("");
		rangeAxisInverted = new DateAxis("");
		rangeAxisInverted.setInverted(true);
		rangeAxis.setRange(date.get(0), lastDate);
		rangeAxisInverted.setRange(date.get(0), lastDate);
		nochange=false;
		
		rangeAxisInverted.addChangeListener(new AxisChangeListener() {
			
			

			@Override
			public void axisChanged(AxisChangeEvent arg0) {
				if(!nochange){
					nochange = true;
					rangeAxis.setRange(rangeAxisInverted.getRange());
				} else {
					nochange=false;
					
				}
			}
		});
		
		rangeAxis.addChangeListener(new AxisChangeListener() {
			
			@Override
			public void axisChanged(AxisChangeEvent arg0) {
//				rangeAxisInverted = new DateAxis("");
				if(!nochange){
					nochange=true;
					rangeAxisInverted.setRange(rangeAxis.getRange());
				} else {
					nochange=false;
				}
			}
		});
		//Chart
		JFreeChart punchlistChart = createPunchListChart();
//		punchlistChart.setBorderVisible(false);
		JFreeChart rFSUProgressChart = createRFSUChart();
		JFreeChart commProgressChart = createCommChart();
		JFreeChart docProgressChart = createDocChart();
		
		JMenuBar menuBar = new JMenuBar();
		getContentPane().add(menuBar, BorderLayout.NORTH);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmExportToExcel = new JMenuItem(" Export To Excel");
		mntmExportToExcel.setIcon(new ImageIcon(Target.class.getResource("/img/newRevision.png")));
		mntmExportToExcel.addActionListener(new ActionListener() {
		    
		    @Override
		    public void actionPerformed(ActionEvent arg0) {
			ExportTargetFrame f = new ExportTargetFrame(date,progress,nbreSSRFSU,punchlistOpened,docProgress);
			f.setVisible(true);
		    }
		});
		mnNewMenu.add(mntmExportToExcel);
		
		JMenuItem mntmPrintToPdf = new JMenuItem("Print to PDF");
		mntmPrintToPdf.setIcon(new ImageIcon(Target.class.getResource("/img/print.png")));
		mnNewMenu.add(mntmPrintToPdf);
		
		final JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		//Panel
		TargetChartPanel punchlistPanel = new TargetChartPanel(punchlistChart,this, false, false , false, false,false);
		TargetChartPanel rFSUProgressPanel = new TargetChartPanel(rFSUProgressChart,this, false, false , false, false,false);
		TargetChartPanel commProgressPanel = new TargetChartPanel(commProgressChart,this, false, false , false, false,false);
		TargetChartPanel docProgressPanel = new TargetChartPanel(docProgressChart,this, false, false , false, false,false);
		punchlistPanel.setPreferredSize(new java.awt.Dimension(200, 200));
		rFSUProgressPanel.setPreferredSize(new java.awt.Dimension(200, 200));
		commProgressPanel.setPreferredSize(new Dimension(200,200));
		docProgressPanel.setPreferredSize(new Dimension(200,200));
		
		final JPanel graphPanel = new JPanel();
		panel.add(graphPanel, BorderLayout.CENTER);
		graphPanel.setLayout(new GridLayout(2, 2));
		graphPanel.add(punchlistPanel);
		graphPanel.add(rFSUProgressPanel);
		graphPanel.add(commProgressPanel);
		graphPanel.add(docProgressPanel);
		
		
		JLabel lblNewLabel;
		if(milestone.getName().equals("%")){
			lblNewLabel = new JLabel("Project Target");
	    } else {
	    	lblNewLabel = new JLabel(m.getName());
	    }
		
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		panel.add(lblNewLabel, BorderLayout.NORTH);
		System.out.println(punchlistPanel.getHeight());
		mntmPrintToPdf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Place where pdf is created
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
					//set the new filename
					String folder = pdf.getPath().substring(0, pdf.getPath().lastIndexOf(File.separatorChar));
					File newpdf = new File(folder + File.separatorChar + name);
					//System.out.println(folder + File.separatorChar + name);

					//Create PDF
					//TODO
					int w = panel.getWidth() + 4;
					int h = panel.getHeight() + 4;
					BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
					Graphics2D g2d = bi.createGraphics();
					panel.paint(g2d);
					g2d.dispose();
					try {
						XSPDF xspdf = XSPDF.getInstance();
						xspdf.setImageCompressionQuality((float) 1.0);
						xspdf.setPageSize(w, h).setPageMargin(2.0).setImage(bi, 0, 0, w, h, 0).createPdf(newpdf);
						//System.out.println(XSPDF.getInstance().getContentEncoding());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		this.repaint();
		this.pack();
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
	}

	private double getMaxRangeForPunchlist() {
		ArrayList<Integer> tmp ;
		if(punchlistOpened!=null){
			tmp = new ArrayList<Integer>(punchlistOpened);
		} else {
			tmp = new ArrayList<Integer>();
		}
		//we sort the list
		Collections.sort(tmp);
		if(tmp.size()==0){
			return 2500;
		}
		else if(tmp.get(tmp.size()-1)<2500){
			return 2500;
		} else {
			return tmp.get(tmp.size()-1);
		}
	}




	private JFreeChart createPunchListChart() {
		// Punchlist plot
		XYDataset data1 = createPunchlistProgressSet();
		XYItemRenderer renderer1 = new StandardXYItemRenderer();
		
		
		XYPlot plot = new XYPlot(data1, rangeAxis, punchlistAxis, renderer1);
		plot.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);
		JFreeChart res = new JFreeChart("",JFreeChart.DEFAULT_TITLE_FONT, plot, false);
		res.setTitle(new TextTitle("Punchlist Remaining (Shall be cleared before AOC)",chartFont, Color.black	, RectangleEdge.TOP, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, RectangleInsets.ZERO_INSETS));
		return res; 

	}
	private JFreeChart createRFSUChart() {
		
		// RFSU Progress subplot ...
		final XYDataset data2 = createRFSUProgress();
		final XYItemRenderer renderer2 = new StandardXYItemRenderer();
		final XYPlot plot = new XYPlot(data2, rangeAxisInverted, pourcentageAxisInverted, renderer2);
		plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
		
		// return a new chart containing the overlaid plot...
		JFreeChart res = new JFreeChart("",JFreeChart.DEFAULT_TITLE_FONT, plot, false);
		res.setTitle(new TextTitle("AOC Certificats Signature Progress",chartFont, Color.black	, RectangleEdge.TOP, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, RectangleInsets.ZERO_INSETS));
		return res;

	}

	private JFreeChart createCommChart() {
		
		// progress plot...
		final XYItemRenderer renderer1 = new StandardXYItemRenderer();
		
		final XYDataset data1 = createProgressSet();
		final XYPlot plot = new XYPlot(data1,  rangeAxis,pourcentageAxis ,renderer1);
		
		JFreeChart res = new JFreeChart("",JFreeChart.DEFAULT_TITLE_FONT, plot, false);
		res.setTitle(new TextTitle("Commissioning Progress (Mhrs)", chartFont, Color.black	, RectangleEdge.BOTTOM, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, RectangleInsets.ZERO_INSETS));
		return res;

	}
	
	private JFreeChart createDocChart(){
		//fetching of log file
		File f = new File("logSUDoc.log");
		String log="";
		{
			if(f.exists()){
				try {
				    	BufferedReader br = new BufferedReader(new FileReader(f));
					log=br.readLine();
					br.close();
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				}
				
			}
		}
		
		
		// documentation progress...
		XYDataset data = createDocProgress(log);
		XYItemRenderer renderer = new StandardXYItemRenderer();
		XYPlot plot = new XYPlot(data, rangeAxisInverted, pourcentageAxis, renderer);
		plot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
		
		// return a new chart containing the overlaid plot...
		JFreeChart res = new JFreeChart("",JFreeChart.DEFAULT_TITLE_FONT, plot, false);
		res.setTitle(new TextTitle("Start Up Preparation Progress", chartFont, Color.black	, RectangleEdge.BOTTOM, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, RectangleInsets.ZERO_INSETS));
		return res;

	}
	
	private XYDataset createProgressSet() {
		
		XYSeries series1 = new XYSeries("");
		for(int i=0;i<date.size();i++){
			double p = progress.get(i);
			double d = date.get(i).getTime();
			double totalP = totalProgress;
			series1.add(d,(p/totalP)*100);
		}
		return new XYSeriesCollection(series1);
	}

	private XYDataset createRFSUProgress() {
		XYSeries series1 = new XYSeries("");
		for(int i=0;i<date.size();i++){
			double p = nbreSSRFSU.get(i);
			double d = date.get(i).getTime();
			double totalP = totalSS;
			series1.add(d,(p/totalP)*100);
		}

		return new XYSeriesCollection(series1);
	}
	
	private XYDataset createPunchlistProgressSet() {
		XYSeries series1 = new XYSeries("");
		for(int i=0;i<date.size();i++){
			double p = punchlistOpened.get(i);
			double d = date.get(i).getTime();
			series1.add(d,p);
		}

		return new XYSeriesCollection(series1);
	}
	
	private XYDataset createDocProgress(String s){
	    	docProgress=new TreeMap<Date, Integer>();
		XYSeries series1 = new XYSeries("");
		Date d = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
		//parsing the log file
		String logs=GeneralConfig.logSUDoc;
		Scanner sc = new Scanner(logs);
		sc.useDelimiter(" -EOLOG- ");
		while(sc.hasNext()){
			String log = sc.next();
			Scanner headerSeparator = new Scanner(log);
			headerSeparator.useDelimiter(" -EOHEADER- ");
			String header = headerSeparator.next();
			//processing date parsing
			try {
				d = formatter.parse(header);
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace();
			}
			String content = headerSeparator.next();
			Scanner milestoneSeparator = new Scanner(content);
			milestoneSeparator.useDelimiter(" -EOM- ");
			
			int acc=0;
			Float progressAcc=(float) 0.0;
			while(milestoneSeparator.hasNext()){
				String milestoneLog = milestoneSeparator.next();
				Scanner finaleScanner = new Scanner(milestoneLog);
				finaleScanner.useDelimiter(" : ");
				//Milestone name
				String milestoneName = finaleScanner.next();
				//milestone doc progress
				float progressValue = Float.valueOf(finaleScanner.next());
				//System.out.println("*"+milestoneName+"*<>*"+m.getName()+"*");
				if(m.getName()!="%"){
        				if(m.getName().equals(milestoneName)){
        					//System.out.println("match for "+milestoneName+"->"+progressValue);
        					double p = progressValue;
        					double date = d.getTime();
        					docProgress.put(d, (int)Math.round(progressValue));
        					series1.add(date,p);
        				}
				} else {
				    acc++;
				    progressAcc=progressAcc+progressValue;
				}
				finaleScanner.close();
			}
			if(m.getName()=="%")
			{
			    series1.add(d.getTime(),progressAcc/acc);
			}
			headerSeparator.close();
			milestoneSeparator.close();
		}
		
		sc.close();
		return new XYSeriesCollection(series1);
	}

	

	/**
	 * Method invoke when zoom out
	 */
	public void notifyZoomOut(){
		this.rangeAxis.setRange(date.get(0), lastDate);
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
