package subsystem;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * Table Model for subsytem list of a milestone
 * @author S.Tr√©mouille
 *
 */

public class DataMilestoneTableModel extends AbstractTableModel{
    	private static final long serialVersionUID = -2060458691987193015L;
	ArrayList<HashMap<String, Object>> model;
	SubSysOperatedSaver ssos;
	
	/**
	 * Constructor
	 * @param data
	 */
	public DataMilestoneTableModel(ArrayList<HashMap<String, Object>> data){
		this.model=data;
		this.ssos=new SubSysOperatedSaver();
		this.addTableModelListener(new CheckBoxListener());
		
	}
	@Override
	public int getColumnCount() {
		return 14;
	}

	@Override
	public int getRowCount() {
		return model.size();
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
		switch (arg1) {
			case 0:
				return model.get(arg0).get("SYS");
			case 1:
				if(ssos.isAlreadyOperated((String)model.get(arg0).get("SYS"))){
					return true;
				}
				else {
					return false;
				}
			case 2:
				return model.get(arg0).get("Description");
			case 3:
				return model.get(arg0).get("ToDo");
			case 4:
				return model.get(arg0).get("Done");
			case 5:
				return model.get(arg0).get("%");
			case 6:
				return model.get(arg0).get("PLA");
			case 7:
				return model.get(arg0).get("PLB");
			case 8 :
				return model.get(arg0).get("RFCDateP");
			case 9:
				return model.get(arg0).get("RFCDate");
			case 10 :
				if(model.get(arg0).get("otpCompleted").getClass().equals((new String()).getClass())){
					return model.get(arg0).get("otpCompleted");
				} else {
					return new OtpPvp((Integer)model.get(arg0).get("otpCompleted"),(Integer)model.get(arg0).get("otpToComplete"));
				}
			case 11 :
				return model.get(arg0).get("AOCDateP");
			case 12:
				return model.get(arg0).get("AOCDate");
			case 13:
				return model.get(arg0).get("dbAlias");
			default:
				return null;
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
				return Integer.class;
			case 4:
				return Integer.class;
			case 5:
				return Double.class;
			case 6:
				return Integer.class;
			case 7:
				return Integer.class;
			case 8:
				return Date.class;
			case 9:
				return Date.class;
			case 10:
				return OtpPvp.class;
			case 11:
				return Date.class;
			case 12:
				return Date.class;
			case 13:
				return String.class;
			default:
				return Object.class;
		}
	}
	
	@Override
	public String getColumnName(int arg0) {
		switch (arg0) {
			case 0:
				return "Sub-System N∞";
			case 1:
				return "Operated";
			case 2:
				return "Desciption";
			case 3:
				return "Remaining (MHrs)";
			case 4:
				return "Total (MHrs)";
			case 5:
				return "Precom./Comm. \n Progress (MHrs)";
			case 6:
				return "P.L. \n before RFC";
			case 7:
				return "P.L. \n before AOC";
			case 8:
				return "RFC Partial \n Date";
			case 9:
				return "RFC Date";
			case 10:
				return "OTS & PVP \n Completion";
			case 11:
				return "AOC Partial \n Date";
			case 12:
				return "AOC Date";
			case 13:
				return "Database";
			default:
				return "Faux !";
		}
	}
	
	@Override
	public void setValueAt(Object value, int row, int col){
		/*HashMap<String, Object> tmp = model.get(row);
		tmp.put(model.get(row).get(arg0), arg1)
		model.set(row, arg1)*/
		switch (col) {
		/*case 0:
			model.get(row).put("SYS",value);*/
		case 1:
			model.get(row).put("Operated",value.toString());
		/*case 2:
			model.get(row).put("Description",value);
		case 3:
			model.get(row).put("ToDo",value);
		case 4:
			model.get(row).put("Done",value);
		case 5:
			model.get(row).put("%",value);
		case 6:
			model.get(row).put("AOC Date",value);*/
		default:
			;
		}		
		String ssNumber = (String) getValueAt(row, col-1);
		ArrayList<Integer> ssList = new ArrayList<Integer>();
		for(int i=0;i<this.getRowCount();i++){
			if(getValueAt(i, col-1).equals(ssNumber)){
				ssList.add(i);
			}
		}
		Iterator<Integer> it = ssList.iterator();
		while(it.hasNext()){
			fireTableCellUpdated(it.next(), col);
		}
	}
	
	class CheckBoxListener implements TableModelListener{

		@Override
		public void tableChanged(TableModelEvent e) {
        	        System.out.println("TableModelListener");
        	        System.out.println(e.getFirstRow()+"->"+e.getLastRow()+" @ "+e.getColumn());
        	        if(e.getColumn()==1 && e.getType()==TableModelEvent.UPDATE){
        		        TableModel model = (TableModel)e.getSource();
        		        Object data = model.getValueAt(e.getFirstRow(), e.getColumn()-1);
        		        
        		        //append in the subsys file the subsys number
        		        SubSysOperatedSaver ssos = new SubSysOperatedSaver();
        		        if(ssos.isAlreadyOperated(data.toString())){
        		        	//System.out.println("Subsystem "+data+" is already operated, removing it.");
        		        	ssos.removeOperatedSS(data.toString());
        		        } else {
        		        	//System.out.println("Subsystem "+data+" is now operated.");
        		        	ssos.appendOperatedSS(data.toString());
        		        }
        	        }
		}
		
	}
	
}
