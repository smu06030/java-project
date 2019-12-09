package doyeon.admin;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import doyeon.DBConnection;
import doyeon.DBTable;

public class OrderList extends JFrame implements DBTable, ActionListener{
//날짜, 아이디, 피시번호, 상품명, 수량, 가격, 
	
	private Vector<String> attribute;
	private Vector<Vector<Object>> tableData;
	private JTable table;
	private JLabel printTot;
	private Integer tot=0;
	
	private DateComboBox firstD, lastD;
	
	public OrderList() {
		setTitle("내역_도연");
		setLayout(new BorderLayout());

		DBConnection conn = new DBConnection(TABLE_LOG);
		tableData = new Vector<Vector<Object>>();
		attribute = new Vector<String>();
		
		attribute.add(ATTRIBUTE_LOG_DATE);
		attribute.add(ATTRIBUTE_LOG_PRODUCT);
		attribute.add(ATTRIBUTE_LOG_USER);
		attribute.add(ATTRIBUTE_LOG_PCNUM);
		attribute.add(ATTRIBUTE_LOG_COST);
		attribute.add(ATTRIBUTE_LOG_COUNT);
		attribute.add(ATTRIBUTE_LOG_METHOD);

		firstD = new DateComboBox(this);
		lastD = new DateComboBox(this);
		
		JPanel bottom = new JPanel();
		bottom.add(firstD);
		bottom.add(new JLabel("-"));
		bottom.add(lastD);
		printTot = new JLabel("0");

		bottom.add(new JLabel("총 매출: "));
		bottom.add(printTot);
		
		table = new JTable(tableData, attribute);
		conn.importRow(attribute, tableData, ATTRIBUTE_LOG_DATE+" = '"+firstD.getToday()+"' order by "+ATTRIBUTE_LOG_DATE+ " desc");
		table.updateUI();
		
		for(int i=0;i<tableData.size();i++) {
			tot+=Integer.parseInt((String)tableData.get(i).get(4));
		}printTot.setText(tot.toString());

		add(new JScrollPane(table), BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);
		setSize(500, 800);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}
	 

	public void actionPerformed(ActionEvent ae) {
		DateComboBox dc = (DateComboBox)(((JComponent)ae.getSource()).getParent());

		if(dc.checkAEventMonth(ae)) {
			dc.setDay();
		}
		String yearF = firstD.getYear();
		String monthF = firstD.getMonth();
		String dayF = firstD.getDay();
		
		String yearL = lastD.getYear();
		String monthL = lastD.getMonth();
		String dayL = lastD.getDay();

		String where = ATTRIBUTE_LOG_DATE + " >= '" + yearF+"-"+monthF+"-"+dayF+"' and "+ATTRIBUTE_LOG_DATE+" <= '"+yearL+"-"+monthL+"-"+dayL+"' order by "+ATTRIBUTE_LOG_DATE+ " desc";
		
		DBConnection conn = new DBConnection(TABLE_LOG);

		conn.importRow(attribute, tableData, where);
		
		tot = 0;
		for(int i=0;i<tableData.size();i++) {
			tot+=Integer.parseInt((String)tableData.get(i).get(4));
		}
		
		printTot.setText(tot.toString());
		table.updateUI();
	
	}
	
	public static void main(String[] args) {
		// 날짜 조회 - 기간으로 설정 / 12-01
		new OrderList();
		
	}
}

class DateComboBox extends JPanel{
	
	private JComboBox<String> year, month, day;
	private Calendar cal;
	private String today;
	
	DateComboBox(ActionListener parent){

		cal = Calendar.getInstance();
		cal.setTime(new Date());
		
		today = cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DATE);
		
		Vector<String> yyyy, mm, dd;
		yyyy = new Vector<String>();
		mm = new Vector<String>();
		dd = new Vector<String>();
		
		for(int i = 2010;i<=cal.get(Calendar.YEAR);i++) {
			yyyy.add(((Integer)i).toString());
		}
		for(int i = 1;i<=12;i++) {
			mm.add(((Integer)i).toString());
		}
		for(int i = 1;i<=cal.getActualMaximum(Calendar.DATE);i++) {
			dd.add(((Integer)i).toString());
		}
		
		year = new JComboBox<String>(yyyy);
		month = new JComboBox<String>(mm);
		day = new JComboBox<String>(dd);
		
		year.setSelectedIndex(yyyy.size()-1);
		month.setSelectedIndex(cal.get(Calendar.MONTH));
		day.setSelectedIndex(cal.get(Calendar.DATE)-1);	
		
		year.addActionListener(parent);
		month.addActionListener(parent);
		day.addActionListener(parent);
		
		add(year);
		add(month);
		add(day);
		
	}
	
	public void setDay() {
		
		cal.set(Calendar.YEAR, Integer.parseInt((String) year.getSelectedItem()));
		cal.set(Calendar.MONTH, Integer.parseInt((String) month.getSelectedItem())-1);
		cal.set(Calendar.DATE, 1);
		
		Vector<String> modelDay = new Vector<String>();
		for(int i = 1;i<=cal.getActualMaximum(Calendar.DAY_OF_MONTH);i++) {
			modelDay.add(((Integer)i).toString());
		}
		day.setModel(new DefaultComboBoxModel<String>(modelDay));
		
	}
	
	public String getYear() {
		return (String)year.getSelectedItem();
	}
	
	public String getMonth() {
		return (String)month.getSelectedItem();
	}
	
	public String getDay() {
		return (String)day.getSelectedItem();
	}

	public boolean checkAEventMonth(ActionEvent ae) {
		if(ae.getSource().equals(month)) return true;
		else return false;
	}
	
	public String getToday() {
		return today;
	}
	
}
