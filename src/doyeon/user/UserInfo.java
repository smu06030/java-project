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

	private String id; //회원 ID
	private JButton changeBtn; //비밀번호변경버튼
	private JTextField inputPasswd; //비밀번호 입력 창
	private JPanel mainPanel; //메인패널
	
	public UserInfo(String id){
		DBConnection connection = new DBConnection(TABLE_MEMBER);
		
		this.id = id;

		changeBtn = new JButton("비밀번호 변경");
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
		
		if(e.getActionCommand().equals("비밀번호 변경")) {

			mainPanel.remove(5);
			mainPanel.add(inputPasswd, 5);
			mainPanel.revalidate();
			mainPanel.repaint();
			
		}else {
			if((inputPasswd.getText()).length()<9) {
				new MessageDialog(this, "경고", true, "비밀번호를 9자 이상 입력해주세요");
			}else {
				DBConnection conn = new DBConnection(TABLE_MEMBER);
				conn.updateString(ATTRIBUTE_MEMBER_PASSWD, "'"+inputPasswd.getText()+"'", ATTRIBUTE_MEMBER_ID+" = '"+id+"'");
				new MessageDialog(this, "알림", true, "비밀번호가 변경되었습니다.");
			}
		}
		
	}
	
	public static void main(String[] args) {
		UserInfo ui = new UserInfo("smu06030");
		ui.setVisible(true);
		ui.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
}
