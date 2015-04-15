package steps;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import subsystem.OtpPvp;
import subsystem.SubSysOperatedSaver;
import conf.GeneralConfig;

public class StepsTableModel extends AbstractTableModel {

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		if(GeneralConfig.stepsField!=null)
			return GeneralConfig.stepsField.size();
		else return 0;
	}

	
	@Override
	public Object getValueAt(int arg0, int arg1) {
		switch (arg1) {
		case 0:
			return GeneralConfig.stepsField.get(arg0).replaceAll("_", " ");
		case 1:
			return GeneralConfig.stepFieldVisible.get(arg0);
		default:
			return "";
		}
		
	}
	
	@Override
	public Class<?> getColumnClass(int arg0) {
		switch (arg0) {
			case 0:
				return String.class;
			case 1:
				return Boolean.class;
			default:
				return Object.class;
		}
	}
	
	@Override
	public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col == 1) {
            return true;
        } else {
            return false;
        }
    }
	
	@Override
	public String getColumnName(int arg0) {
		switch (arg0) {
			case 0:
				return "Field Name";
			case 1:
				return "Visible";
			default:
				return "Faux !";
		}
	}
	
	@Override
	public void setValueAt(Object value, int row, int col){
		switch (col) {
		
		case 1:
			GeneralConfig.stepFieldVisible.put(row, (Boolean) value);
		
		default:
			;
		}		
		
		fireTableCellUpdated(row,col);
		
	}

	public void fireTableDataChange() {
		fireTableDataChanged();
	}
	
}
