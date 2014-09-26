package subsystem;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Cell renderer for cells containing OTP/PVP
 * @author S.Trémouille
 *
 */
public class OtpPvpCellRenderer extends DefaultTableCellRenderer {
    	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if(value.getClass().equals(new String().getClass())){
			setBackground(Color.gray);
			setText("N/A");
		} else {
			if(((OtpPvp) value).getOtpToComplete()>0){
				if(((OtpPvp) value).getOtpCompleted()==0){
					setBackground(Color.white);
				} else if(((OtpPvp) value).getOtpCompleted()>0 && ((OtpPvp) value).getOtpCompleted()<((OtpPvp) value).getOtpToComplete()) {
					setBackground(Color.orange);
					setValue(value.toString());
				} else if(((OtpPvp) value).getOtpCompleted()>0 && ((OtpPvp) value).getOtpCompleted()==((OtpPvp) value).getOtpToComplete()){
					setBackground(new Color(128,255,0));
					setValue(value.toString());
				} else {
					setBackground(new Color(128,255,0));
				}
			}
		}
		setHorizontalAlignment(SwingConstants.CENTER);
		return super.getTableCellRendererComponent(table, value, isSelected,hasFocus, row, column);
	}
}
