package subsystem;

import java.awt.Color;
import java.awt.Component;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Cell renderer for date's cell
 * @author S.Trémouille
 *
 */

public class DateCellRenderer extends DefaultTableCellRenderer {

    	private static final long serialVersionUID = -6951676433311170698L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		 SimpleDateFormat format = new SimpleDateFormat("dd-MMMMM-yyyy");
		 if( value instanceof Date) {
			 value = format.format(value);
			 setBackground(new Color(128,255,0));
	     }
		 else if((column==8||column==11)&&(table.getValueAt(row, column+1) instanceof Date))
		 {
			 setBackground(new Color(128,255,0));
		 }
		 else {
			setBackground(Color.white);
		 }
	     return super.getTableCellRendererComponent(table, value, isSelected,hasFocus, row, column);
	}
}
