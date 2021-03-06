package ManagerView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class Member_management extends JFrame implements ActionListener, MouseListener{
	private static String url = "jdbc:mysql://localhost:3306/pcbang?serverTimezone=UTC";
	private static String user = "root";
	private static String pw = "dlscjf158!A";
	private static Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs = null;
	
	private JPanel top = new JPanel();
	private JPanel left = new JPanel();
	private JPanel right = new JPanel();
	private Container ct;
	private JTextField name_field,id_field;
	private JButton name_search, id_search, all_search;
	private Vector<String>title = new Vector<String>();
	private Vector<Vector<String>> content = new Vector<Vector<String>>();
	private String member[] = {"아이디","비밀번호","이름","생년월일","휴대폰","이메일"};
	private DefaultTableModel model = null;
	private JTable table;
	private JScrollPane scroll;
	
	Member_management(){
		ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		/*---------------------------- top 버튼 추가 ------------------------------*/
		
		top.setBackground(new Color(22,28,24)); //new Color(22,28,24)
		top.setLayout(new FlowLayout());
		top.setPreferredSize(new Dimension(1000,80));
		top.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
		
		JLabel label = new JLabel("< 회원정보 >");
		label.setFont(new Font("나눔스퀘어",Font.BOLD,30));
		label.setForeground(Color.WHITE);
		
		top.add(label);
		
		/*---------------------------- 왼쪽 검색 기능------------------------------*/
		
		left.setBackground(new Color(57,56,54)); 
		left.setLayout(null);
		left.setPreferredSize(new Dimension(200,920));
		
		// 이름으로 검색
		JLabel name_label = new JLabel("이름으로 검색");
		name_label.setBorder(BorderFactory.createEmptyBorder(20,0,20,0));
		name_label.setForeground(Color.WHITE);
		name_label.setFont(new Font("나눔스퀘어",Font.BOLD,20));
		name_label.setBounds(40,30,150,50);
		name_field = new JTextField(8);
		name_field.setHorizontalAlignment(name_field.CENTER);
		name_field.setPreferredSize(new Dimension(50,30));
		name_field.setFont(new Font("나눔스퀘어",Font.BOLD,20));
		name_field.setBounds(17,90,165,32);
		name_search = new JButton("검색(N)");
		name_search.setBackground(new Color(243,97,185));
		name_search.setForeground(Color.WHITE);
		name_search.setFont(new Font("나눔스퀘어",Font.BOLD,20));
		name_search.setFocusPainted(false);
		name_search.setBorder(new LineBorder(Color.white,2));
		name_search.setPreferredSize(new Dimension(156,40));
		name_search.setBounds(17,140,165,42);
		
		// ID로 검색
		JLabel id_label = new JLabel("ID으로 검색");
		id_label.setBorder(BorderFactory.createEmptyBorder(60,0,20,0));
		id_label.setForeground(Color.WHITE);
		id_label.setFont(new Font("나눔스퀘어",Font.BOLD,20));
		id_label.setBounds(47,220,150,60);
		id_field = new JTextField(8);
		id_field.setHorizontalAlignment(id_field.CENTER);
		id_field.setPreferredSize(new Dimension(50,30));
		id_field.setFont(new Font("나눔스퀘어",Font.BOLD,20));
		id_field.setBounds(17,305,165,32);
		id_search = new JButton("검색(I)");
		id_search.setBackground(new Color(243,97,185));
		id_search.setForeground(Color.WHITE);
		id_search.setFont(new Font("나눔스퀘어",Font.BOLD,20));
		id_search.setFocusPainted(false);
		id_search.setBorder(new LineBorder(Color.white,2));
		id_search.setPreferredSize(new Dimension(156,40));
		id_search.setBounds(17,352,165,42);
		
		// 모두 검색
		all_search = new JButton("모두 조회");
		all_search.setBackground(new Color(243,97,185));
		all_search.setForeground(Color.WHITE);
		all_search.setFont(new Font("나눔스퀘어",Font.BOLD,20));
		all_search.setFocusPainted(false);
		all_search.setBorder(new LineBorder(Color.white,2));
		all_search.setPreferredSize(new Dimension(156,40));
		all_search.setBounds(17,502,165,42);
		
		left.add(name_label);
		left.add(name_field);
		left.add(name_search);
		left.add(id_label);
		left.add(id_field);
		left.add(id_search);
		left.add(all_search);
		
		/*---------------------------- 오른쪽 조회 ------------------------------*/
		
		right.setBackground(new Color(57,56,54));
		right.setPreferredSize(new Dimension(800,920));
		
		// 테이블 생성
		for(int i = 0;i<member.length;i++)
			title.add(member[i]);
		
		model = new DefaultTableModel(content,title) {
			public boolean isCellEditable(int row, int column) {  // 테이블 수정, 입력 불가
				return false;
			}	
		};
		// defaultTableCellRenderer 객체 생성
		DefaultTableCellRenderer dr = new DefaultTableCellRenderer();
		// 랜더러의 가로 정렬을 center로 지정
		dr.setHorizontalAlignment(SwingConstants.CENTER);
		table = new JTable(model);
		// 정렬할 테이블의  컬럼 모델을 가져온다.
		TableColumnModel tm = table.getColumnModel();
		// 모델 컬럼의 갯수만큼 가운데 정렬 해준다.
		for(int i = 0; i<table.getColumnCount();i++)
			tm.getColumn(i).setCellRenderer(dr);
		table.getTableHeader().setFont(new Font("나눔고딕",Font.BOLD,15));
		table.getTableHeader().setBackground(Color.LIGHT_GRAY);
		table.setFont(new Font("나눔고딕",Font.BOLD,14));
		table.getColumn("아이디").setPreferredWidth(20);
		table.getColumn("비밀번호").setPreferredWidth(20);
		table.getColumn("이름").setPreferredWidth(10);
		table.getColumn("생년월일").setPreferredWidth(30);
		table.getColumn("휴대폰").setPreferredWidth(30);
		table.getColumn("이메일").setPreferredWidth(50);
		table.setRowHeight(30);
		scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(780,570));
		
		right.add(scroll);
		
		/*---------------------------- 컨테이너에 추가 ------------------------------*/
		
		ct.add(top,BorderLayout.NORTH);
		ct.add(left,BorderLayout.WEST);
		ct.add(right,BorderLayout.EAST);
		
		/*------------------------------- 이벤트 ---------------------------------*/
		
		name_search.addActionListener(this);
		name_field.addActionListener(this);
		id_search.addActionListener(this);
		id_field.addActionListener(this);
		all_search.addActionListener(this);
		table.addMouseListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("검색(N)") || e.getSource() == name_field) {
			if(name_field.getText().length() == 0) { // 예외처리
				JOptionPane.showMessageDialog(null, "값이 입력되지 않았습니다.");
			}else {
				// 입력된 이름을 검색해 회원DB에서 조회해서 테이블에 보여준다.
				name_sh(name_field.getText());
				name_field.setText("");
			}
		}else if(e.getActionCommand().equals("검색(I)") || e.getSource() == id_field) {
			if(id_field.getText().length() == 0) { // 예외처리
				JOptionPane.showMessageDialog(null, "값이 입력되지 않았습니다.");
			}else {
				// 입력된 아이디을 검색해 회원DB에서 조회해서 테이블에 보여준다.
				id_sh(id_field.getText());
				id_field.setText("");	
			}
		}else if(e.getActionCommand().equals("모두 조회")) {
			all_sh();
		}
	}
	

	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2) {
			int row = table.getSelectedRow(); // 현재 선택된 행
			String id = (String)table.getValueAt(row, 0); // 아이디
			String pass = (String)table.getValueAt(row, 1); // 비밀번호
			String name = (String)table.getValueAt(row, 2); // 이름
			
			String birth = (String)table.getValueAt(row, 3); // 생년월일
			String year = birth.substring(0,4); // 생년월일을 년도, 월, 일 별로 분류
			String month = birth.substring(5,7);
			int month_int = Integer.parseInt(month); // 숫자로 변환
			String day = birth.substring(8,10);
			int day_int = Integer.parseInt(day); // 숫자로 변환
			
			String phone = (String)table.getValueAt(row, 4); // 휴대폰
			String first_p = phone.substring(0,3); // 휴대폰 번호를 앞에번호 뒤에번호로 분류
			String last_p = phone.substring(3,11);
			
			String email = (String)table.getValueAt(row, 5); // 이메일
			String first_e[] = email.split("@"); // 이메일을 @로 구분하여 분류

			Member_UpAndDel member = new Member_UpAndDel(id,pass,name,year,month_int,day_int,first_p,last_p,first_e[0],first_e[1]);
			member.setVisible(true);
			member.setTitle("회원정보");
			member.setSize(500,550);
			member.setResizable(false);
			member.setLocationRelativeTo(null);
			member.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	
	
	/*------------------------------- 메인 ---------------------------------*/
	
	public static void main(String[] args) {
		// DB연동
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 연결 성공");	
			
			conn = DriverManager.getConnection(url,user,pw);
					
		}catch(ClassNotFoundException e) {
			System.out.println("JDBC 드라이버 로드 에러");
		}catch(SQLException e) {
			System.out.println("DB연결 에러\n"+e.getMessage());
		}
		
		Member_management member = new Member_management();
		
		member.setVisible(true);
		member.setSize(1000,700);
		member.setTitle("회원정보");
		member.setLocationRelativeTo(null);
		member.setResizable(false);
		member.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/*------------------------------- DB ---------------------------------*/
	
	// 이름으로 회원 조회
	public void name_sh(String name) {
		try {
			conn = DriverManager.getConnection(url,user,pw);
			
			String sql = "select * from 회원  where 이름 like ?";
			
			pstmt = conn.prepareStatement(sql); 
			
			pstmt.setString(1,name);
			
			rs = pstmt.executeQuery();
			// 받아온 정보를 input에다가 저장한다.
			Vector<String> inputs = new Vector<String>();
			// table을 초기화 시킨다.
			model.setNumRows(0);
			int i = 0;
			
			while(rs.next()) {
				for(int j = 0;j<member.length;j++)
					inputs.add(rs.getString(member[i++]));
			}
			model.addRow(inputs);
			
			pstmt.close();
			conn.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	// 아이디로 회원 조회
	public void id_sh(String id) {
		try {
			conn = DriverManager.getConnection(url,user,pw);
			
			String sql = "select * from 회원  where 아이디 like ?";
			
			pstmt = conn.prepareStatement(sql); 
			
			pstmt.setString(1,id);
			
			rs = pstmt.executeQuery();
			// 받아온 정보를 input에다가 저장한다.
			Vector<String> inputs = new Vector<String>();
			// table을 초기화 시킨다.
			model.setNumRows(0);
			int i = 0;
			
			while(rs.next()) {
				for(int j = 0;j<member.length;j++)
					inputs.add(rs.getString(member[i++]));
			}
			model.addRow(inputs);
			
			pstmt.close();
			conn.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	// 모든 회원 조회
	public void all_sh() {
		try {
			conn = DriverManager.getConnection(url,user,pw);
			
			String sql = "select * from 회원";
			
			pstmt = conn.prepareStatement(sql); 
			
			rs = pstmt.executeQuery();
			// 받아온 정보를 input에다가 저장한다.
			String inputs[] = new String[6];
			// table을 초기화 시킨다.
			model.setNumRows(0);
			int i;
			
			while(rs.next()) {
				i = 0;
				for(int j = 0;j<member.length;j++,i++)
					inputs[i] = rs.getString(member[i]);
				model.addRow(inputs);
			}

			pstmt.close();
			conn.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
