package subsystem;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

/**
 * Made to implement the behavior of subsystems operated
 * @author S.Trémouille
 * 
 */

public class BooleanCellRenderer extends JCheckBox implements TableCellRenderer{

	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = 6679387921299671889L;

	/**
	 * Constructor
	 */
	public BooleanCellRenderer() {
		setHorizontalAlignment(SwingConstants.CENTER);
		}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		//super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		SubSysOperatedSaver ssos = new SubSysOperatedSaver();
				
		 if (ssos.isAlreadyOperated((String)table.getValueAt(row, column-1))) {
             setForeground(table.getSelectionForeground());
             setBackground(new Color(128,255,0));
           } else {
        	   if(isSelected){
        		   setForeground(table.getSelectionForeground());
        		   setBackground(table.getSelectionBackground());
        	   } else {
        		   setForeground(table.getForeground());
        		   setBackground(table.getBackground());
        	   }
           }
		setSelected(ssos.isAlreadyOperated((String)table.getValueAt(row, column-1)));
		return this;
	}
	
}
