package userView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import DBCheck.data_check;

public class newMember extends JFrame implements ActionListener {
	private data_check checking = new data_check();
	private String phones[] = {"010","011","017","019"};
	private String emails[] = {"naver.com","google.com","nate.com","daum.net","hanmail.com"};
	private Vector<String> year = new Vector<String>();
	private Vector<String> month = new Vector<String>();
	private Vector<String> day = new Vector<String>();
	
	private JLabel id,password,password_check,name,birth,phone,email,email2;
	private JTextField id_field,name_field,birth_field,phone_field,email_field;
	private JPasswordField p_field,pCheck_field;
	private JButton check,join,close;
	private JComboBox year_box, month_box, day_box, phone_box, email_box;
	private Container ct;
	
	newMember(){
		ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		/*---------------------------- 생년월일 콤보박스 내용 추가 ------------------------------*/
		
		//년,월,일을 벡터로 콤보 박스에 저장
		for(int i = 1990; i<=2010; i++) {
			year.add(Integer.toString(i));
		}
		for(int i = 1; i<=12; i++) {
			month.add(Integer.toString(i));
		}
		for(int i = 1;i <=31;i++) {
			day.add(Integer.toString(i));
		}
		
		/*---------------------------- 회원정보 패널 ------------------------------*/
		
		// 회원 정보 입력 패널
		JPanel top = new JPanel();
		top.setLayout(null);
		top.setBackground(new Color(22,28,24));
		
		// 배경색
		JPanel bg = new JPanel();
		bg.setLayout(null);
		bg.setBounds(17,17,450,400);
		bg.setBackground(new Color(74,73,71));
				
		// 아이디 라벨
		id = new JLabel("아이디 : ");
		id.setFont(new Font("나눔고딕",Font.BOLD,19));
		id.setBounds(45,20,150,50);
		id.setForeground(Color.WHITE);
		top.add(id);
		
		// 아이디 필드
		id_field = new JTextField(20);
		id_field.setFont(new Font("나눔고딕",Font.BOLD,20));
		id_field.setBounds(130,25,200,40);
		top.add(id_field);
		
		// 중복체크 버튼
		check = new JButton("중복 확인");
		check.setFont(new Font("나눔고딕",Font.BOLD,15));
		check.setBounds(350,25,100,40);
		check.setBackground(new Color(86,42,57));
		check.setFocusPainted(false);
		check.setBorder(null);
		check.setForeground(Color.WHITE);
		top.add(check);
		
		// 비밀번호 라벨
		password = new JLabel("비밀번호 : ");
		password.setFont(new Font("나눔고딕",Font.BOLD,19));
		password.setBounds(26,75, 150, 50);
		password.setForeground(Color.white);
		top.add(password);
		
		// 패스워드 필드
		p_field = new JPasswordField(20);
		p_field.setFont(new Font("나눔고딕",Font.BOLD,20));
		p_field.setBounds(130,80,200,40);
		top.add(p_field);
		
		// 비밀번호 체크 라벨
		password_check = new JLabel("비번 확인 : ");
		password_check.setFont(new Font("나눔고딕",Font.BOLD,19));
		password_check.setBounds(21,130, 150, 50);
		password_check.setForeground(Color.white);
		top.add(password_check);
		
		// 비밀번호 체크 필드
		pCheck_field = new JPasswordField(20);
		pCheck_field.setFont(new Font("나눔고딕",Font.BOLD,20));
		pCheck_field.setBounds(130,135,200,40);
		top.add(pCheck_field);
		
		// 이름 라벨
		name = new JLabel("이름 : ");
		name.setFont(new Font("나눔고딕",Font.BOLD,19));
		name.setBounds(61,185,150,50);
		name.setForeground(Color.WHITE);
		top.add(name);
		
		// 이름 필드
		name_field = new JTextField(20);
		name_field.setFont(new Font("나눔고딕",Font.BOLD,20));
		name_field.setBounds(130,190,200,40);
		top.add(name_field);
		
		// 생일 라벨
		birth = new JLabel("생년월일 : ");
		birth.setFont(new Font("나눔고딕",Font.BOLD,19));
		birth.setBounds(26,240,150,50);
		birth.setForeground(Color.WHITE);
		top.add(birth);
		
		// 년도
		year_box = new JComboBox(year);
		year_box.setBounds(130,250,70,30);
		year_box.setBackground(Color.WHITE);
		year_box.setForeground(Color.BLACK);
		top.add(year_box);
		
		// 월
		month_box = new JComboBox(month);
		month_box.setBounds(215,250,70,30);
		month_box.setBackground(Color.WHITE);
		month_box.setForeground(Color.BLACK);
		top.add(month_box);
		
		// 일
		day_box = new JComboBox(day);
		day_box.setBounds(300,250,70,30);
		day_box.setBackground(Color.WHITE);
		day_box.setForeground(Color.BLACK);
		top.add(day_box);
		
		// 휴대폰 라벨
		phone = new JLabel("휴대폰 : ");
		phone.setFont(new Font("나눔고딕",Font.BOLD,19));
		phone.setBounds(45,295,150,50);
		phone.setForeground(Color.WHITE);
		top.add(phone);
		
		// 휴대폰 콤보 박스
		phone_box = new JComboBox(phones);
		phone_box.setBounds(130,305,70,30);
		phone_box.setBackground(Color.WHITE);
		phone_box.setForeground(Color.BLACK);
		top.add(phone_box);
		
		// 휴대폰 필드
		phone_field = new JTextField(20);
		phone_field.setBounds(215,305,155,30);
		phone_field.setForeground(Color.BLACK);
		phone_field.setFont(new Font("나눔고딕",Font.BOLD,16));
		top.add(phone_field);
		
		// 이메일 라벨
		email = new JLabel("이메일 : ");
		email.setFont(new Font("나눔고딕",Font.BOLD,19));
		email.setBounds(45,350,150,50);
		email.setForeground(Color.WHITE);
		top.add(email);
		
		// 이메일
		email_field = new JTextField(20);
		email_field.setBounds(130,360,100,30);
		email_field.setForeground(Color.BLACK);
		email_field.setFont(new Font("나눔고딕",Font.BOLD,16));
		top.add(email_field);
		
		// @
		email2 = new JLabel("@");
		email2.setFont(new Font("나눔고딕",Font.BOLD,15));
		email2.setBounds(235,350,150,50);
		email2.setForeground(Color.WHITE);
		top.add(email2);
		
		// 이메일 콤보 박스
		email_box = new JComboBox(emails);
		email_box.setBounds(255,360,115,30);
		email_box.setBackground(Color.WHITE);
		email_box.setForeground(Color.BLACK);
		top.add(email_box);
		
		// 가입 버튼
		join = new JButton("가입");
		join.setBounds(130,435,100,40);
		join.setFont(new Font("나눔고딕",Font.BOLD,20));
		join.setBackground(new Color(212,84,169));
		join.setForeground(Color.WHITE);
		join.setBorder(null);
		join.setFocusPainted(false);
		join.setEnabled(false);
		top.add(join);
		
		// 닫기 버튼
		close = new JButton("닫기");
		close.setBounds(240,435,100,40);
		close.setFont(new Font("나눔고딕",Font.BOLD,20));
		close.setBackground(new Color(212,84,169));
		close.setForeground(Color.WHITE);
		close.setBorder(null);
		close.setFocusPainted(false);
		top.add(close);
		top.add(bg);
		
		/*---------------------------- 컨테이너 추가 ------------------------------*/
		
		ct.add(top,BorderLayout.CENTER);
		
		/*---------------------------- 이벤트 ------------------------------*/
		
		check.addActionListener(this);
		join.addActionListener(this);
		close.addActionListener(this);
	}
	
	// 닫기,가입, 중복확인 버튼을 클릭했을 때 이벤트
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("중복 확인")) {
			if(checking.id_check(id_field.getText())) {
				JOptionPane.showMessageDialog(null, "사용가능한 ID 입니다.");
				join.setEnabled(true);
				id_field.setEditable(false);
			}else {
				JOptionPane.showMessageDialog(null, "중복된 ID 입니다.");
			}
		}else if(e.getActionCommand().equals("가입")) {
			if(id_field.getText().equals("") || new String(p_field.getPassword()).equals("") || new String(pCheck_field.getPassword()).equals("")
			 ||name_field.getText().equals("") || phone_field.getText().equals("") || email_field.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "빈칸이 있습니다.");
			}else {
				String pass = new String(p_field.getPassword());
				String passCheck = new String(pCheck_field.getPassword());
				if(!pass.equals(passCheck)) {
					JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.");
				}else {
					// 생년월일, 휴대폰, 이메일을 변수를 설정하고 회원DB에 보낸다.
					String birth = (String)year_box.getSelectedItem()+"-"+(String)month_box.getSelectedItem()+"-"+(String)day_box.getSelectedItem();
					String phone = (String)phone_box.getSelectedItem()+phone_field.getText();
					String email = email_field.getText()+"@"+(String)email_box.getSelectedItem();
					
					checking.member_join(id_field.getText(),new String(p_field.getPassword()),name_field.getText(),birth,0,0,phone,email,0);
					JOptionPane.showMessageDialog(null, "회원가입이 되었습니다.");
					this.dispose();
				}
			}
		}else if(e.getActionCommand().equals("닫기")) {
			this.dispose();
		}
	}
	
	/*---------------------------- 메인 ------------------------------*/
	
	public static void main(String[] args) {
		newMember member = new newMember();
		member.setVisible(true);
		member.setTitle("회원가입");
		member.setSize(500,550);
		member.setLocationRelativeTo(null);
		member.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
