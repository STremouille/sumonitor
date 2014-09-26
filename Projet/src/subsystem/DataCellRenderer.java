package subsystem;


import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * This class is made to make a custom render the subsystem table
 * @author S.Trémouille
 *
 */

public class DataCellRenderer extends DefaultTableCellRenderer{

	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = 2403383876525475245L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		setHorizontalAlignment(SwingConstants.CENTER);
		if(column==5){
			setForeground(Color.black);
			double progress = Double.valueOf(value+"");
			System.out.println("Progress renderer :"+progress);
			setText(progress+"");
			if (progress < 10.0) {
				setBackground(new Color(255,0,0));
			} else if(progress>=10.0 && progress<50.0){
				setBackground(new Color(255,179,0));
			} else if(progress>=50.0&&progress<100.0){
				setBackground(new Color(192,255,0));
			} else if(progress==100.0){
				setBackground(new Color(128,255,0));
				
			}
		} else if(column==6||column==7){
			setBackground(Color.white);
			if(!isSelected) {
				int pl =  (Integer) value;
				setText(pl+"");
				if (pl > 0) {
					setForeground(Color.red);
				} else {
					setForeground(Color.green);
				}
			}
		} else {
			if(!isSelected){
				setBackground(Color.white);
				setForeground(Color.black);
			}
		}
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}

}


