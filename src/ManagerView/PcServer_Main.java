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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import DBCheck.data_check;

public class PcServer_Main extends JFrame implements MouseListener, ActionListener {
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

	private static int count = 10;
	private static int cnt = 0;
	private static int pcbun = 0;
	
	private int x = 200, y = 100;
	private int seat;
	
	PcServer_Main(){
		ct = getContentPane();		
		ct.setLayout(new BorderLayout());
		
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
			btn[i].setPreferredSize(new Dimension(200,70));
			btn[i].addActionListener(this);
			top.add(btn[i]);
		}
	
		center.setLayout(new GridLayout(2,5,0,0));
		center.setBorder(null);
	
		for(seat = 0; seat < 10;seat++) {
			pcFrame[seat] = new pcFrame(seat);
			pcFrame[seat].getComponent(5).addMouseListener(this);
			pcFrame[seat].setBackground(new Color(22,28,24));
			center.add(pcFrame[seat]);
		}
		
		bottom.setLayout(new FlowLayout());
		bottom.setBackground(new Color(22,28,24));
		JTextArea ta = new JTextArea(10,20);
		JTextArea memo = new JTextArea(10,20);
		bottom.add(ta);
		bottom.add(memo);

		
		ct.add(top,BorderLayout.NORTH);
		ct.add(center,BorderLayout.CENTER);
		ct.add(bottom,BorderLayout.SOUTH);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("당일내역")) {
			
		}else if(e.getActionCommand().equals("회원관리")) {
			
		}else if(e.getActionCommand().equals("상품관리")) {
			
		}else if(e.getActionCommand().equals("지출")) {
			
		}
		
	}
	public void mouseClicked(MouseEvent e) {
		System.out.println("ㅗㅑ");
		JFrame j = new JFrame();
		j.setVisible(true);
		j.setSize(500,500);
		j.setLocationRelativeTo(null);
		j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JLabel l1 = (JLabel)pcFrame[pcbun-1].getComponent(1);
		j.add(l1);
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	
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
			while(true) {
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
