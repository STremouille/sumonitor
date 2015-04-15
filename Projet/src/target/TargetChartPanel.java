package target;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

/**
 * 
 * @author S.Trémouille
 *
 */
public class TargetChartPanel extends ChartPanel {
    	private static final long serialVersionUID = -3501973014449238887L;
	Target parent;
	
	/**
	 * Constructor
	 * @param chart
	 * @param parent
	 * @param properties
	 * @param save
	 * @param print
	 * @param zoom
	 * @param tooltips
	 */
	public TargetChartPanel(JFreeChart chart,Target parent, boolean properties, boolean save,
			boolean print, boolean zoom, boolean tooltips) {
		super(chart, properties, save, print, zoom, tooltips);
		this.parent=parent;
	}

	/**
	 * Constructor
	 * @param chart
	 */
	public TargetChartPanel(JFreeChart chart) {
		super(chart);
	}

	@Override
	protected JPopupMenu createPopupMenu(boolean arg0, boolean arg1,
			boolean arg2, boolean arg3, boolean arg4) {
		JPopupMenu res = super.createPopupMenu(arg0, false, arg2, arg3, arg4);
		JMenuItem resetZoom = new JMenuItem("Zoom Out");
		res.add(resetZoom);
		resetZoom.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				parent.notifyZoomOut();
			}
		});
		return res;
		
	}

	

}
