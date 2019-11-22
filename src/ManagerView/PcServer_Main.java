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
	private String btnName[] = {"���ϳ���","ȸ������","��ǰ����","����"};
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
	private String column[] = {"��¥","�ֹ���ȣ","��ǰ��","���̵�","����","����","�Ǿ���ȣ"};
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
		
		/*------------------------------ �޴� --------------------------------*/
		
		// �����Ȳ, �޴� ��ư ����
		top.setLayout(new FlowLayout());
		top.setBackground(new Color(22,28,24)); //(57,56,54)
		label1.setText("��� ��Ȳ "+cnt+" : "+count);
		label1.setFont(new Font("�������",Font.BOLD,30));
		label1.setForeground(Color.WHITE);
		label1.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
		top.add(label1);
		for(int i = 0;i<btn.length;i++) {
			btn[i] = new JButton(btnName[i]);
			btn[i].setFont(new Font("����������",Font.BOLD,30));
			btn[i].setForeground(Color.white);
			btn[i].setBackground(new Color(72,72,72));
			btn[i].setBorder(new LineBorder(Color.WHITE,2));
			btn[i].setFocusPainted(false);
			btn[i].setPreferredSize(new Dimension(180,60));
			btn[i].addActionListener(this);
			top.add(btn[i]);
		}
		
		/*---------------------------- pc�ڸ� ------------------------------*/
		
		// pc�ڸ� ����
		center.setLayout(new GridLayout(2,5,0,0));
		center.setBorder(null);

		for(seat = 0; seat < 10;seat++) {
			pcFrame[seat] = new pcFrame(seat);
			pcFrame[seat].setBackground(new Color(22,28,24));
			center.add(pcFrame[seat]);
		}
		
		/*---------------------------- �α� ------------------------------*/
		
		// �α� �޸��� ����
		bottom.setLayout(new BorderLayout());
		bottom.setBackground(new Color(22,28,24));
		
		// �α� �г� ����
		JPanel log = new JPanel();
		log.setLayout(new FlowLayout());	
		
		title = new Vector<String>();
		
		for(int i = 0;i < column.length; i++) {
			title.add(column[i]);
		}
		
		// ���̺� ����
		model = new DefaultTableModel(content,title);
		table = new JTable(model);
		table.getTableHeader().setFont(new Font("�������",Font.BOLD,18));
		table.setFont(new Font("�������",Font.BOLD,18));
		scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(900,200));
		log.add(scroll);
		
		// �ǿ� �α� ���̺� �߰�
		tabbed_log.addTab("   �α�   ",log);
		tabbed_log.setBorder(BorderFactory.createEmptyBorder(0,20,20,0));
		tabbed_log.setFont(new Font("����������",Font.BOLD,20));
		
		/*----------------------------- �޸��� -------------------------------*/
		
		// �޸��� �г� ����
		JPanel memo = new JPanel();
		memo.setLayout(new FlowLayout());	
		JTextArea textarea = new JTextArea(8,30);
		textarea.setFont(new Font("����������",Font.BOLD,20));
		textarea.append("<���� ����>\n\n1. ������ �� ������\n2. �ڸ� û�� ������\n3. �λ� ���ϱ�");
		memo.add(textarea);
		
		// �ǿ� �޸� �߰�
		tabbed_memo.addTab("   �޸�   ",memo);
		tabbed_memo.setBorder(BorderFactory.createEmptyBorder(0,0,20,20));
		tabbed_memo.setFont(new Font("����������",Font.BOLD,20));
		
		bottom.add(tabbed_log,BorderLayout.WEST);
		bottom.add(tabbed_memo,BorderLayout.EAST);
		
		/*---------------------------- �����̳� �߰� ------------------------------*/
		
		// �����̳ʿ� top, center, bottom �߰�
		ct.add(top,BorderLayout.NORTH);
		ct.add(center,BorderLayout.CENTER);
		ct.add(bottom,BorderLayout.SOUTH);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("���ϳ���")) {
			
		}else if(e.getActionCommand().equals("ȸ������")) {
			Member_management member = new Member_management();
			
			member.setVisible(true);
			member.setSize(1000,700);
			member.setTitle("ȸ������");
			member.setLocationRelativeTo(null);
			member.setResizable(false);
			member.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}else if(e.getActionCommand().equals("��ǰ����")) {
			
		}else if(e.getActionCommand().equals("����")) {
			
		}	
	}
	
	/*---------------------------- pc�¼� panel ------------------------------*/
	
	// pc�¼� Panel
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
					label[i] = new JLabel("PC "+(numSeat+1)+"��");
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
	
	/*---------------------------- ����� ���� �� ȭ�� ���� ------------------------------*/
	
	public static void insert(String msg, int pc) {
		pcbun = pc;

		if(msg.equals("����")){
			pcFrame[pc-1].getComponent(5).setBackground(new Color(57,56,54));
			JLabel l1 = (JLabel)pcFrame[pc-1].getComponent(2);
			JLabel l2 = (JLabel)pcFrame[pc-1].getComponent(3);
			JLabel l3 = (JLabel)pcFrame[pc-1].getComponent(4);
			
			c[pc].interrupt();
			
			cnt--;
			label1.setText("��� ��Ȳ "+cnt+" : "+count);
			l1.setText("");
			l2.setText("");
			l3.setText("");
		
		}else {
			// �޾ƿ� id�� ȸ��DB�� �̸� ��������
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
			label1.setText("��� ��Ȳ "+cnt+" : "+count);
			
			l1.setText("ID : "+msg);
			l2.setText("�̸� : "+name);
	
		}
	}
	
	/*---------------------------- �ð� ������ ------------------------------*/
	
	// �ð�
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
				cl.setText("�ܿ��ð� : "+(Integer.toString(hour))+"�ð�"+(Integer.toString(minute))+"��"+(Integer.toString(sec))+"��");		
				// ���� ����� �� ��
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
	
	/*---------------------------- ���� ------------------------------*/
	
	public static void main(String[] args) {
		PcServer_Main pc = new PcServer_Main();
		pc.setVisible(true);
		pc.setSize(1600,900);
		pc.setTitle("������ ����");
		pc.setLocationRelativeTo(null);
		pc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		PcServer pcServer = new PcServer();
		pcServer.startServer();
	}
}
