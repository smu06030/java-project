package doyeon.user;

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

public class UserInfo extends JFrame implements DBTable, ActionListener{

	private String id; //ȸ�� ID
	private JButton changeBtn; //��й�ȣ�����ư
	private JTextField inputPasswd; //��й�ȣ �Է� â
	private JPanel mainPanel; //�����г�
	
	public UserInfo(String id){
		DBConnection connection = new DBConnection(TABLE_MEMBER);
		
		this.id = id;

		changeBtn = new JButton("��й�ȣ ����");
		changeBtn.addActionListener(this);
		
		inputPasswd = new JTextField(20);
		inputPasswd.addActionListener(this);

		String Attributes[] = {
				ATTRIBUTE_MEMBER_NAME,
				ATTRIBUTE_MEMBER_ID, 
				ATTRIBUTE_MEMBER_PASSWD,
				ATTRIBUTE_MEMBER_BIRTH,
				ATTRIBUTE_MEMBER_PHONE,
				ATTRIBUTE_MEMBER_EMAIL
		};
		Vector<String> data = new Vector<String>();
		data = connection.fieldsDataStringVector(Attributes, ATTRIBUTE_MEMBER_ID+" = '"+id+"'");
		Vector<JLabel> JLabels = new Vector<JLabel>();
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0, 2));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		for(int i = 0; i<Attributes.length;i++) {
			
			mainPanel.add(new JLabel(Attributes[i]+": "));
			JLabels.add(new JLabel(data.get(i)));
			if(Attributes[i].equals(ATTRIBUTE_MEMBER_PASSWD)) {

				mainPanel.add(changeBtn, i*2+1);
			}else {

				mainPanel.add(JLabels.get(i));
			}
		}

		setSize(300, 300);
		
		add(mainPanel);

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getActionCommand().equals("��й�ȣ ����")) {

			mainPanel.remove(5);
			mainPanel.add(inputPasswd, 5);
			mainPanel.revalidate();
			mainPanel.repaint();
			
		}else {
			if((inputPasswd.getText()).length()<9) {
				new MessageDialog(this, "���", true, "��й�ȣ�� 9�� �̻� �Է����ּ���");
			}else {
				DBConnection conn = new DBConnection(TABLE_MEMBER);
				conn.updateString(ATTRIBUTE_MEMBER_PASSWD, "'"+inputPasswd.getText()+"'", ATTRIBUTE_MEMBER_ID+" = '"+id+"'");
				new MessageDialog(this, "�˸�", true, "��й�ȣ�� ����Ǿ����ϴ�.");
			}
		}
		
	}
	
	public static void main(String[] args) {
		UserInfo ui = new UserInfo("smu06030");
		ui.setVisible(true);
		ui.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
}
