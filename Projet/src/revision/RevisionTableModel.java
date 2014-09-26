package revision;

import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.table.AbstractTableModel;

/**
 * Table Model for revisions
 * @author S.Trémouille
 *
 */

public class RevisionTableModel extends AbstractTableModel{
    	private static final long serialVersionUID = -5129635648757809096L;
	ArrayList<TreeMap<String, String>> model;
	//ArrayList<ImageIcon> delete;
	
	/**
	 * @param data
	 */
	public RevisionTableModel(ArrayList<TreeMap<String, String>> data){
		this.model=data;
		/*this.delete = new ArrayList<ImageIcon>();
		updateDeleteIcon();*/
	}
	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public int getRowCount() {
		return model.size();
	}

	@Override
	public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
		if(col>=0&&col<7){
			return true;
		} else {
			return false;
		}
    }
	
	@Override
	public Object getValueAt(int arg0, int arg1) {
		switch (arg1) {
			case 0:
				return model.get(arg0).get("rev");
			case 1:
				return model.get(arg0).get("date");
			case 2:
				return model.get(arg0).get("status");
			case 3:
				return model.get(arg0).get("revisionMemo");
			case 4:
				return model.get(arg0).get("issued");
			case 5:
				return model.get(arg0).get("checked");
			case 6:
				return model.get(arg0).get("approved");
			/*case 7:
				return delete.get(arg0);*/
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
				return String.class;
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
			/*case 7:
				return ImageIcon.class;*/
			default:
				return Object.class;
		}
	}
	
	@Override
	public String getColumnName(int arg0) {
		switch (arg0) {
			case 0:
				return "Revision";
			case 1:
				return "Date";
			case 2:
				return "Status";
			case 3:
				return "Revision Memo";
			case 4:
				return "Issued By";
			case 5:
				return "Checked By";
			case 6:
				return "Approved By";
			/*case 7:
				return "Delete";*/
			default:
				return "Faux !";
		}
	}
	
	@Override
	public void setValueAt(Object value, int row, int col){
		switch (col) {
		case 0:
			model.get(row).put("rev",(String) value);
			break;
		case 1:
			model.get(row).put("date",(String) value);
			break;
		case 2:
			model.get(row).put("status",(String) value);
			break;
		case 3:
			model.get(row).put("revisionMemo",(String) value);
			break;
		case 4:
			model.get(row).put("issued",(String) value);
			break;
		case 5:
			model.get(row).put("checked",(String) value);
			break;
		case 6:
			model.get(row).put("approved",(String) value);
			break;
		default:
			break;
		}	
		fireTableCellUpdated(row, col);
	}
	
	
	/**
	 * @param index
	 */
	public void deleteRow(int index){
		model.remove(index);
	}
	
	@Override
	public void fireTableDataChanged() {
		super.fireTableDataChanged();
		//updateDeleteIcon();
	}
	/*public void updateDeleteIcon(){
		this.delete.clear();
		for(int i=0;i<model.size();i++){
			final int line=i;
			ImageIcon b =new ImageIcon(new ImageIcon(RevisionTableModel.class.getResource("/img/delete-connection.png")).getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT));
			this.delete.add(b);
		}
		System.out.println("update end "+model.size());
	}*/
	/**
	 * @return model
	 */
	public ArrayList<TreeMap<String, String>> getModel() {
		return model;
	}
}
	
	
