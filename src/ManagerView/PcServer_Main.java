package ManagerView;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import DBCheck.data_check;

public class PcServer_Main extends JFrame implements ActionListener {
	private static clock c[] = new clock[10];
	private static data_check checking = new data_check();
	private JTextArea txt = new JTextArea(5,8);
	private String btnName[] = {"당일내역","회원관리","상품관리","지출"};
	private Container ct;
	private static JPanel pcFrame[] = new pcFrame[10];
	private JPanel top = new JPanel();
	private JPanel center = new JPanel();
	private JPanel bottom = new JPanel();
	private static JLabel label1 = new JLabel();
	private JLabel label[] = new JLabel[5];
	private JButton btn[] = new JButton[4];
	private JTabbedPane tabbed_log = new JTabbedPane();
	private JTabbedPane tabbed_memo = new JTabbedPane();
	private String column[] = {"날짜","주문번호","상품명","아이디","수량","가격","피씨번호"};
	private Vector<String> title;
	private Vector<Vector<String>> content = new Vector<Vector<String>>();
	private DefaultTableModel model = null;
	private JTable table;
	private JScrollPane scroll;
	
	private static int count = 10;
	private static int cnt = 0;
	private static int pcbun = 0;
	private int x = 200, y = 100;
	private int seat;
	
	PcServer_Main(){
		ct = getContentPane();		
		ct.setLayout(new BorderLayout());
		
		/*------------------------------ 메뉴 --------------------------------*/
		
		// 사용현황, 메뉴 버튼 생성
		top.setLayout(new FlowLayout());
		top.setBackground(new Color(22,28,24)); //(57,56,54)
		label1.setText("사용 현황 "+cnt+" : "+count);
		label1.setFont(new Font("나눔고딕",Font.BOLD,30));
		label1.setForeground(Color.WHITE);
		label1.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
		top.add(label1);
		for(int i = 0;i<btn.length;i++) {
			btn[i] = new JButton(btnName[i]);
			btn[i].setFont(new Font("나눔스퀘어",Font.BOLD,30));
			btn[i].setForeground(Color.white);
			btn[i].setBackground(new Color(72,72,72));
			btn[i].setBorder(new LineBorder(Color.WHITE,2));
			btn[i].setFocusPainted(false);
			btn[i].setPreferredSize(new Dimension(180,60));
			btn[i].addActionListener(this);
			top.add(btn[i]);
		}
		
		/*---------------------------- pc자리 ------------------------------*/
		
		// pc자리 세팅
		center.setLayout(new GridLayout(2,5,0,0));
		center.setBorder(null);

		for(seat = 0; seat < 10;seat++) {
			pcFrame[seat] = new pcFrame(seat);
			pcFrame[seat].setBackground(new Color(22,28,24));
			center.add(pcFrame[seat]);
		}
		
		/*---------------------------- 로그 ------------------------------*/
		
		// 로그 메모장 세팅
		bottom.setLayout(new BorderLayout());
		bottom.setBackground(new Color(22,28,24));
		
		// 로그 패널 생성
		JPanel log = new JPanel();
		log.setLayout(new FlowLayout());	
		
		title = new Vector<String>();
		
		for(int i = 0;i < column.length; i++) {
			title.add(column[i]);
		}
		
		// 테이블 세팅
		model = new DefaultTableModel(content,title);
		table = new JTable(model);
		table.getTableHeader().setFont(new Font("나눔고딕",Font.BOLD,18));
		table.setFont(new Font("나눔고딕",Font.BOLD,18));
		scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(900,200));
		log.add(scroll);
		
		// 탭에 로그 테이블 추가
		tabbed_log.addTab("   로그   ",log);
		tabbed_log.setBorder(BorderFactory.createEmptyBorder(0,20,20,0));
		tabbed_log.setFont(new Font("나눔스퀘어",Font.BOLD,20));
		
		/*----------------------------- 메모장 -------------------------------*/
		
		// 메모장 패널 세팅
		JPanel memo = new JPanel();
		memo.setLayout(new FlowLayout());	
		JTextArea textarea = new JTextArea(8,30);
		textarea.setFont(new Font("나눔스퀘어",Font.BOLD,20));
		textarea.append("<당일 공지>\n\n1. 쓰레기 잘 버리기\n2. 자리 청소 깨끗히\n3. 인사 잘하기");
		memo.add(textarea);
		
		// 탭에 메모 추가
		tabbed_memo.addTab("   메모   ",memo);
		tabbed_memo.setBorder(BorderFactory.createEmptyBorder(0,0,20,20));
		tabbed_memo.setFont(new Font("나눔스퀘어",Font.BOLD,20));
		
		bottom.add(tabbed_log,BorderLayout.WEST);
		bottom.add(tabbed_memo,BorderLayout.EAST);
		
		/*---------------------------- 컨테이너 추가 ------------------------------*/
		
		// 컨테이너에 top, center, bottom 추가
		ct.add(top,BorderLayout.NORTH);
		ct.add(center,BorderLayout.CENTER);
		ct.add(bottom,BorderLayout.SOUTH);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("당일내역")) {
			
		}else if(e.getActionCommand().equals("회원관리")) {
			Member_management member = new Member_management();
			
			member.setVisible(true);
			member.setSize(1000,700);
			member.setTitle("회원정보");
			member.setLocationRelativeTo(null);
			member.setResizable(false);
			member.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}else if(e.getActionCommand().equals("상품관리")) {
			
		}else if(e.getActionCommand().equals("지출")) {
			
		}	
	}
	
	/*---------------------------- pc좌석 panel ------------------------------*/
	
	// pc좌석 Panel
	class pcFrame extends JPanel{
		JTextArea textarea = new JTextArea(5,8);
		int numSeat;
		
		pcFrame(int numSeat){
			this.numSeat = numSeat;
			setLayout(null);
			
			textarea.setEditable(false);
			textarea.setBounds(80,50,150,150);
			textarea.setBackground(new Color(57,56,54));
			textarea.setBorder(new LineBorder(Color.BLACK,2));
			
			int y = -10;
			
			for(int i = 0;i<5;i++) {
				if(i == 0)
					label[i] = new JLabel("PC "+(numSeat+1)+"번");
				else
					label[i] = new JLabel("");
				
				label[i].setBounds(85,y,150,150);
				y+= 25;
				label[i].setForeground(new Color(221,228,236));
				add(label[i], i);
			}
			
			add(textarea,5);
		}
	}
	
	/*---------------------------- 사용자 접속 후 화면 갱신 ------------------------------*/
	
	public static void insert(String msg, int pc) {
		pcbun = pc;

		if(msg.equals("종료")){
			pcFrame[pc-1].getComponent(5).setBackground(new Color(57,56,54));
			JLabel l1 = (JLabel)pcFrame[pc-1].getComponent(2);
			JLabel l2 = (JLabel)pcFrame[pc-1].getComponent(3);
			JLabel l3 = (JLabel)pcFrame[pc-1].getComponent(4);
			
			c[pc].interrupt();
			
			cnt--;
			label1.setText("사용 현황 "+cnt+" : "+count);
			l1.setText("");
			l2.setText("");
			l3.setText("");
		
		}else {
			// 받아온 id로 회원DB에 이름 가져오기
			String name = checking.id_search(msg);
			int hour = checking.time_hour(msg);
			int minute = checking.time_minute(msg);
			int sec = checking.time_sec(msg);
			
			pcFrame[pc-1].getComponent(5).setBackground(new Color(23,103,0));
			JLabel l1 = (JLabel)pcFrame[pc-1].getComponent(2);
			JLabel l2 = (JLabel)pcFrame[pc-1].getComponent(3);
			JLabel l3 = (JLabel)pcFrame[pc-1].getComponent(4);
			
			c[pc] = new clock(l3,hour,minute,sec);
			c[pc].start();
			
			cnt++;
			label1.setText("사용 현황 "+cnt+" : "+count);
			
			l1.setText("ID : "+msg);
			l2.setText("이름 : "+name);
	
		}
	}
	
	/*---------------------------- 시계 스레드 ------------------------------*/
	
	// 시계
	static class clock extends Thread{
		
		JLabel cl;
		int hour,minute,sec;
		clock(JLabel cl,int hour, int minute, int sec){
			this.cl = cl;
			this.hour = hour;
			this.minute = minute;
			this.sec = sec;
		}
		
		public void run() {
			while(!Thread.currentThread().isInterrupted()){
				cl.setText("잔여시간 : "+(Integer.toString(hour))+"시간"+(Integer.toString(minute))+"분"+(Integer.toString(sec))+"초");		
				// 선불 요금제 일 때
				if(hour == 0 && minute == 0 && sec == 0) {
					break;
				}
			
				if(hour >= 1 && minute == 0 && sec == 0) {
					minute = 60;
					hour--;	
				}
				
				if(minute >= 1 && sec == 0) {
					sec = 59;
					minute--;
				}
				sec--;
				try {
					Thread.sleep(1000);
				}catch(InterruptedException e) {
					cl.setText("");
					break;
				}
			}
		}
	}
	
	/*---------------------------- 메인 ------------------------------*/
	
	public static void main(String[] args) {
		PcServer_Main pc = new PcServer_Main();
		pc.setVisible(true);
		pc.setSize(1600,900);
		pc.setTitle("관리자 메인");
		pc.setLocationRelativeTo(null);
		pc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		PcServer pcServer = new PcServer();
		pcServer.startServer();
	}
}
