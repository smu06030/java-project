package doyeon.admin;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import doyeon.DBConnection;
import doyeon.DBTable;
import doyeon.MessageDialog;

public class ChargeSet extends JFrame implements ActionListener, DBTable{
	
	private JTable table;
	
	private Vector<String> attribute;
	private Vector<Vector<Object>> data;
	private Vector<Object> primaryKey;
	DBConnection connection;
	
	public ChargeSet(){
		setTitle("���� ����_����");
		setLayout(new BorderLayout());
		connection = new DBConnection(TABLE);

		attribute = new Vector<String>();
		data = new Vector<Vector<Object>>();
		primaryKey = new Vector<Object>();
		//�Ӽ� ��
		for(int i = 0; i< ATTRIBUTE.length;i++) {
			attribute.add(ATTRIBUTE[i]);
		}
		
		if(!connection.importRow(attribute, data)); //new MessageDialog(this, "�˻�����", true, "���� �����Ͱ� �����ϴ�."); 

		for(int i=0;i<data.size();i++) {
			primaryKey.add(data.get(i).get(0));
		}
		
		table = new JTable(data, attribute);
		JPanel bottom = new JPanel();
		JButton addBtn, updateBtn, deleteBtn;
		addBtn = new JButton("�߰�");
		updateBtn = new JButton("����");
		deleteBtn = new JButton("����");
		addBtn.addActionListener(this);
		updateBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
		bottom.add(addBtn); bottom.add(updateBtn); bottom.add(deleteBtn);
		add(new JScrollPane(table), BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);
		
		setSize(500, 400);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent ae) {
		boolean check = true;
		if(ae.getActionCommand().equals("����")) {
			Vector<Object> primaryVector = new Vector<Object>();
			connection.fieldDataObjectVector(attribute.get(0), primaryVector);
			for(int i=0;i<data.size();i++) {
				Vector<Object> d = new Vector<Object>();
				for(int j=0;j<attribute.size();j++) {
					d.add(table.getValueAt(i,j));
				}
				if(!connection.isUpdateTupleAll(attribute, d, (String)primaryVector.get(i))) {
					//
					check = false;
				}//else 
			}
			
			if(check) {
				new MessageDialog(this, "�˸�", false, "���� ����");
				
			}
			else {
				new MessageDialog(this, "�˸�", false, "���� ����/�ùٸ� �����͸� �Է����ּ���.");
				reset();
			}
			
		}else if(ae.getActionCommand().equals("�߰�")) {

			addPanel ap = new addPanel(this);
			ap.setVisible(true);
			ap.setLocationRelativeTo(null);

		}else if(ae.getActionCommand().equals("����")) {
			
			int index = table.getSelectedRow();
			connection.deleteTuple(attribute.get(0), index);
			reset();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ChargeSet();
	}
	
	public Vector<String> getAttribute(){
		return attribute;
	}

	public void reset() {
		data.removeAllElements();
		connection.importRow(attribute, data);
		table.updateUI();
	}
	
}