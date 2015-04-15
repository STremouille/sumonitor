package conf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Show a frame in order to select a date
 * @author S.Trémouille
 *
 */
public class DatePicker {
	int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
	int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);;
	JLabel l = new JLabel("", JLabel.CENTER);
	String day = "";
	JDialog d;
	JButton[] button = new JButton[49];

	/**
	 * Constructor of the date picker dialog, needs the parent frame
	 * @param parent
	 */
	public DatePicker(JFrame parent) {
		d = new JDialog();
		d.setModal(true);
		String[] header = { "Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat" };
		JPanel p1 = new JPanel(new GridLayout(7, 7));
		p1.setPreferredSize(new Dimension(215, 107));	

		for (int x = 0; x < button.length; x++) {
			final int selection = x;
			button[x] = new JButton();
			button[x].setFocusPainted(false);
			button[x].setBackground(Color.white);
			if (x > 6)
				button[x].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						String res = button[selection].getActionCommand();
						if (res!=""){
							day = res;
							d.dispose();
						}
					}
				});
			if (x < 7) {
				button[x].setText(header[x]);
				button[x].setForeground(Color.blue);
			}
			p1.add(button[x]);
		}
		JPanel p2 = new JPanel(new GridLayout(1, 5));
		JButton previousMonth = new JButton("<< Month");
		previousMonth.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				month--;
				displayDate();
			}
		});
		JButton nextMonth = new JButton("Month >>");
		nextMonth.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				month++;
				displayDate();
			}
		});
		JButton previousYear = new JButton("<< Year");
		previousYear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				year--;
				displayDate();
			}
		});
		
		JButton nextYear = new JButton("Year >>");
		nextYear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				year++;
				displayDate();
			}
		});
		p2.add(previousYear);
		p2.add(previousMonth);
		p2.add(l);
		p2.add(nextMonth);
		p2.add(nextYear);
		d.add(p1, BorderLayout.CENTER);
		d.add(p2, BorderLayout.SOUTH);
		d.pack();
		d.setLocation(new Point(parent.getX()+parent.getWidth()/2-d.getWidth()/2,parent.getY()+parent.getHeight()/2-d.getHeight()/2));
		displayDate();
		d.setVisible(true);
	}

	private void displayDate() {
		for (int x = 7; x < button.length; x++)
			button[x].setText("");
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM yyyy");
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.set(year, month, 1);
		int dayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK);
		int daysInMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
		for (int x = 6 + dayOfWeek, day = 1; day <= daysInMonth; x++, day++)
			button[x].setText("" + day);
		l.setText(sdf.format(cal.getTime()));
		d.setTitle("Date Picker");
	}

	/**
	 * returns the formatted date selected in the date picker
	 * @return formattedDate
	 */
	public String setPickedDate() {
		if (day.equals(""))
			return day;
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"MMM/dd/yyyy");
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.set(year, month, Integer.parseInt(day));
		return sdf.format(cal.getTime());
	}
	
	
	
}

