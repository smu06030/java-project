package userView;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import DBCheck.data_check;

public class userMain extends JFrame implements ActionListener {
	// Client ��ü ����
	private Client client = new Client();
	// DB ���
	private data_check checking;
	private clock c;
	
	private String ip,id = null;
	private int port,rand,pcNumber,hour = 0,minute = 0,sec = 15;
	private String pcNum;
	
	private Container ct;
	private JLabel pclabel,idLabel,idview,hourLabel,hourview;
	private JButton close,food,member_info;
	
	userMain(String ip, int port,int rand, String id,int hour, int minute, int sec){
		this.ip = ip;
		this.port = port;
		this.rand = rand;
		this.id = id;
		this.hour = hour;
		this.minute = minute;
		this.sec = sec;
		
		client.startClient(ip,port,rand,id);
		// ������ �����ϴµ� �ð��� ���� �ɸ��� ������ ������ sleep���� ��� ���� ���״ٰ� �����Ѵ�.
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		start(hour,minute,sec);	
	}
	// �׽�Ʈ�� ������
	userMain(){
		start(hour,minute,sec);
	}

	public void start(int hour, int minute, int sec) {
		// ���� pc ��ȣ�� �޾ƿͼ� main ȭ�鿡 ���
		pcNumber = client.pcNumber;
		pcNum = Integer.toString(pcNumber);
		
		ct = getContentPane();
		ct.setLayout(null);
		ct.setBackground(new Color(22,28,24));
		
		// pc ��ȣ ��
		pclabel = new JLabel("No."+pcNum);
		pclabel.setBounds(25,30,100,30);
		pclabel.setFont(new Font("��������",Font.BOLD,30));
		pclabel.setForeground(Color.WHITE);
		ct.add(pclabel);
		
		close = new JButton("�������");
		close.setBounds(240,25,120,40);
		close.setFont(new Font("��������",Font.BOLD,20));
		close.setForeground(Color.WHITE);
		close.setBackground(new Color(57,58,42));
		close.setFocusPainted(false);
		close.setBorder(new LineBorder(Color.WHITE,2));
		ct.add(close);
		
		idLabel = new JLabel("���̵� : ");
		idLabel.setBounds(25,120,150,30);
		idLabel.setForeground(Color.WHITE);
		idLabel.setFont(new Font("��������",Font.BOLD,25));
		ct.add(idLabel);
		
		idview = new JLabel(""+id);
		idview.setBounds(135,120,200,30);
		idview.setForeground(new Color(182,183,177));
		idview.setFont(new Font("��������",Font.BOLD,25));
		ct.add(idview);
		
		hourLabel = new JLabel("�ܿ��ð� : ");
		hourLabel.setBounds(25,180,350,30);
		hourLabel.setForeground(Color.WHITE);
		hourLabel.setFont(new Font("��������",Font.BOLD,25));
		ct.add(hourLabel);
		
		hourview = new JLabel("");
		hourview.setBounds(160,180,350,30);
		hourview.setForeground(new Color(182,183,177));
		hourview.setFont(new Font("��������",Font.BOLD,25));
		ct.add(hourview);
		
		food = new JButton("�԰Ÿ� �ֹ�");
		food.setBounds(30,280,150,50);
		food.setFont(new Font("��������",Font.BOLD,20));
		food.setForeground(new Color(221,236,193));
		food.setBackground(new Color(42,63,22));
		food.setFocusPainted(false);
		food.setBorder(new LineBorder(new Color(42,63,22),2));
		ct.add(food);
		
		member_info = new JButton("ȸ�� ����");
		member_info.setBounds(200,280,150,50);
		member_info.setFont(new Font("��������",Font.BOLD,20));
		member_info.setForeground(new Color(255,232,179));
		member_info.setBackground(new Color(72,46,23));
		member_info.setFocusPainted(false);
		member_info.setBorder(new LineBorder(new Color(72,46,23),2));
		ct.add(member_info);
		
		// �޾ƿ� �ð����� �����带 ������.
		c = new clock(hourview);
		c.start();
		
		// ��ư�� ������ �� �̺�Ʈ
		close.addActionListener(this);
		food.addActionListener(this);
		member_info.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("�������")) {
			checking = new data_check();
			
			client.send("����",pcNumber);
			
			System.out.println("���� �ð� "+hour+" ���� �� "+minute+" ���� �� "+sec+" id "+id);
			try {
				checking.timeInsert(hour,minute,sec,id);
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			}
			this.dispose();
		}else if(e.getActionCommand().equals("�԰Ÿ� �ֹ�")) {
			
		}else if(e.getActionCommand().equals("ȸ�� ����")) {
			
		}
	}
	// �ð�
	class clock extends Thread{
		JLabel cl;
		
		clock(JLabel cl){
			this.cl = cl;
		}
		public void run() {
			//Calendar time = Calendar.getInstance();
			while(true) {
				cl.setText((Integer.toString(hour))+"�ð�"+(Integer.toString(minute))+"��"+(Integer.toString(sec))+"��");

				// ���� ����� �� ��
				if(hour == 0 && minute == 0 && sec == 0) {
					break;
				}
				sec--;
				
				if(hour >= 1 && minute == 0 && sec == 0) {
					minute = 60;
					hour--;	
				}
				
				if(minute >= 1 && sec == 0) {
					sec = 59;
					minute--;
				}
				
				try {
					Thread.sleep(1000);
				}catch(InterruptedException e) {
					
				}
			}
		}
	}
	
	public static void main(String[] args) {
		// �ٷ� ������ �׽�Ʈ��
		userMain main = new userMain();
		main.setVisible(true);
		main.setSize(400,400);
		main.setLocationRelativeTo(null);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}