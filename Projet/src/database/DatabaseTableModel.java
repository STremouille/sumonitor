package database;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.table.AbstractTableModel;

/**
 * @author S.Trémouille
 * Database Table Model of databases table
 *
 */
public class DatabaseTableModel extends AbstractTableModel{

    	private static final long serialVersionUID = -4909281620213415180L;
	private ArrayList<Database> databases;
	
	/**
	 * @param databases
	 */
	public DatabaseTableModel(ArrayList<Database> databases){
		this.databases=databases;
	}
	
	@Override
	public int getColumnCount() {
		return 9;
	}

	@Override
	public int getRowCount() {
		if(databases!=null)
			return databases.size();
		else
			return 1;
	}
	
	

	@Override
	public void setValueAt(Object value, int row, int col){
		switch (col) {
		case 1:
			databases.get(row).setActivated((Boolean) value);
		default:
			;
		}
		fireTableDataChanged();
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
	public Object getValueAt(int arg0, int arg1) {
		if(databases!=null)
		{
			switch (arg1) {
			case 2:
				return databases.get(arg0).getConnexionType();
			case 0:
				return databases.get(arg0).getAlias();
			case 1:
			    	return databases.get(arg0).isActivated();
			case 3:
				return databases.get(arg0).getOdbcLinkName();
			case 4:
				return databases.get(arg0).getLogin();
			case 5:
				return databases.get(arg0).getMilestoneColumn();
			case 6:
				return databases.get(arg0).getServerURL();
			case 7:
				return databases.get(arg0).getDatabaseName();
			case 8:
				return databases.get(arg0).getInstanceName();
			default:
				return null;
			}
		} else {
        		    switch (arg1) {
        			case 1:
        		    		return true;
        		    	default:
        		    	    return "void";
        		    }
		}
	}

	@Override
	public Class<?> getColumnClass(int arg0) {
		switch (arg0) {
			case 0:
				return String.class;
			case 1:
				return Boolean.class;
			case 2:
				return String.class;
			case 3:
				return String.class;
			case 4:
				return String.class;
			case 5:
				return String.class;
			case 6:
				return String.class;
			case 7:
				return String.class;
			case 8:
				return String.class;
			
			default:
				return Object.class;
		}
	}
	
	@Override
	public String getColumnName(int arg0) {
		switch (arg0) {
			case 0:
			    	return "Alias";
			case 1:
				return "Activated";
			case 2:
				return "Connexion";
			case 3:
				return "ODBC Link";
			case 4:
				return "Login";
			case 5:
				return "Milestone Column";
			case 6:
				return "Server";
			case 7:
				return "Database";
			case 8:
				return "Instance";
			default:
				return "Faux !";
		}
	}
	
	
}
