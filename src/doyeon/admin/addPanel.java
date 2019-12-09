package doyeon.admin;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import doyeon.DBConnection;
import doyeon.DBTable;
import doyeon.MessageDialog;

public class addPanel extends JFrame implements ActionListener, DBTable{
	
	JButton addBtn;
	JTextField name, time, price;
	Vector<String> attribute;
	ChargeSet parent;
	
	addPanel(ChargeSet p){
		setTitle("충전 상품 추가_도연");
		parent = p;
		Container ct = getContentPane();
		
		ct.setLayout(new BorderLayout());

		attribute = new Vector<String>();
		for(int i = 0; i< ATTRIBUTE.length;i++) {
			attribute.add(ATTRIBUTE[i]);
		}
		
		name = new JTextField(20);
		time = new JTextField(20);
		price = new JTextField(10);
		addBtn = new JButton("추가");
		addBtn.addActionListener(this);
		name.addActionListener(this);
		
		JPanel center = new JPanel();
		center.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		center.setLayout(new GridLayout(3, 2, 10,20));
		center.add(new JLabel("상품명: ")); center.add(name);
		center.add(new JLabel("시간: ")); center.add(time);
		center.add(new JLabel("단가: ")); center.add(price);
		ct.add(center, BorderLayout.CENTER);
		ct.add(addBtn, BorderLayout.SOUTH);
		setSize(250, 200);
		ct.setVisible(true);

	}
	
	public void actionPerformed(ActionEvent ae) {
		System.out.println(name.getDragEnabled());
		DBConnection connection = new DBConnection(TABLE);
		Vector<Object> tuple = new Vector<Object>();
		tuple.add(name.getText());
		tuple.add(time.getText());
		tuple.add(price.getText());
		if(connection.isInsertObject(tuple, attribute)) {
			new MessageDialog(this, "알림", false, "추가 성공");
			parent.reset();
		}else new MessageDialog(this, "알림", false, "추가 실패/입력데이터를 확인해주세요.");
	}
	
}
