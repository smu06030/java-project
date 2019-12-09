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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
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
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import DBCheck.data_check;
import doyeon.admin.ChargeSet;
import doyeon.admin.OrderList;
import loginView.Product_Management;

public class PcServer_Main extends JFrame implements ActionListener {
	private static data_check checking = new data_check();
	private static clock c[] = new clock[checking.getSeatNum()];
	private static JPanel pcFrame[] = new pcFrame[checking.getSeatNum()];
	private static JLabel label1 = new JLabel();
	private static String column[] = {"��¥","�ֹ���ȣ","��ǰ��","���̵�","����","����","�Ǿ���ȣ","��������"};
	private static JScrollPane scroll;
	private static DefaultTableModel model = null;
	private JTable table;
	private String btnName[] = {"���ϳ���","ȸ������","��ǰ����","�������","��������"};
	private JTextArea txt = new JTextArea(5,8);
	private Container ct;
	private JPanel top = new JPanel();
	private JPanel center = new JPanel();
	private JPanel bottom = new JPanel();
	private JLabel label[] = new JLabel[5];
	private JButton btn[] = new JButton[5];
	private JTabbedPane tabbed_log = new JTabbedPane();
	private JTabbedPane tabbed_memo = new JTabbedPane();
	private Vector<String> title;
	private Vector<Vector<String>> content = new Vector<Vector<String>>();
	
	private static int count = checking.getSeatNum();
	private static int cnt = 0;
	private static int pcbun = 0;
	private int x = 200, y = 100;
	private int seat;

	PcServer_Main(String position){		
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
		if(position.equals("1")) {
			btn[3].setEnabled(false);
			btn[4].setEnabled(false);
		}
		
		/*---------------------------- pc�ڸ� ------------------------------*/
		
		// pc�ڸ� ����
		center.setLayout(new GridLayout(0,5,0,0));
		if(checking.getSeatNum() <= 10)
			center.setPreferredSize(new Dimension(1500,400));
		else if(checking.getSeatNum() <= 15)
			center.setPreferredSize(new Dimension(1500,700));
		else if(checking.getSeatNum() <= 25)
			center.setPreferredSize(new Dimension(1500,1000));
		else if(checking.getSeatNum() <= 30)
			center.setPreferredSize(new Dimension(1500,1300));
		center.setBackground(new Color(22,28,24));
		center.setBorder(null);

		for(seat = 0; seat < checking.getSeatNum();seat++) {
			pcFrame[seat] = new pcFrame(seat);
			pcFrame[seat].setBackground(new Color(22,28,24));
			center.add(pcFrame[seat]);
		}
		JScrollPane sc = new JScrollPane(center);
		sc.setBorder(null);
		
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
		model = new DefaultTableModel(content,title) {
			public boolean isCellEditable(int row, int column) {  // ���̺� ����, �Է� �Ұ�
				return false;
			}	
		};
		// defaultTableCellRenderer ��ü ����
		DefaultTableCellRenderer dr = new DefaultTableCellRenderer();
		// �������� ���� ������ center�� ����
		dr.setHorizontalAlignment(SwingConstants.CENTER);
		table = new JTable(model);
		// ������ ���̺���  �÷� ���� �����´�.
		TableColumnModel tm = table.getColumnModel();
		// �� �÷��� ������ŭ ��� ���� ���ش�.
		for(int i = 0; i<table.getColumnCount();i++)
			tm.getColumn(i).setCellRenderer(dr);
		table.getTableHeader().setFont(new Font("�������",Font.BOLD,18));
		table.getTableHeader().setBackground(Color.LIGHT_GRAY);
		table.setFont(new Font("�������",Font.BOLD,18));
		table.setRowHeight(30);
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
		ct.add(sc,BorderLayout.CENTER);
		ct.add(bottom,BorderLayout.SOUTH);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("���ϳ���")) {
			OrderList ol = new OrderList();
			ol.setLocationRelativeTo(null);
			ol.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
		}else if(e.getActionCommand().equals("ȸ������")) {
			Member_management member = new Member_management();
			
			member.setVisible(true);
			member.setSize(1000,700);
			member.setTitle("ȸ������");
			member.setLocationRelativeTo(null);
			member.setResizable(false);
			member.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}else if(e.getActionCommand().equals("��ǰ����")) {
			Product_Management p = new Product_Management();
			p.setTitle("�繫 ����");
			p.setSize(550, 500);
			p.setVisible(true);
			p.setLocationRelativeTo(null);
			p.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}else if(e.getActionCommand().equals("�������")) {
			Store_Management sm = new Store_Management();
			sm.setVisible(true);
			sm.setSize(350,300);
			sm.setTitle("�������");
			sm.setLocationRelativeTo(null);
			sm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}else if(e.getActionCommand().equals("��������")) {
			ChargeSet charge = new ChargeSet();
			charge.setVisible(true);
			charge.setTitle("��������");
			charge.setLocationRelativeTo(null);
			charge.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
			//textarea.setPreferredSize(new Dimension(100,100));
			int y = 20;

			for(int i = 0;i<5;i++) {
				if(i == 0)
					label[i] = new JLabel("PC "+(numSeat+1)+"��");
				else
					label[i] = new JLabel("");
				label[i].setBounds(85,y,143,20);// 82,y,146,20
				label[i].setForeground(new Color(221,228,236));
				y+= 20;
				//label[i].setOpaque(true);
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
			
			JLabel l2 = (JLabel)pcFrame[pc-1].getComponent(2);
			JLabel l3 = (JLabel)pcFrame[pc-1].getComponent(3);
			JLabel l4 = (JLabel)pcFrame[pc-1].getComponent(4);
			
			c[pc-1].interrupt();
			
			cnt--;
			label1.setText("��� ��Ȳ "+cnt+" : "+count);
			
			l2.setText("");l3.setText("");l4.setText("");
		}else if(msg.equals("�ֹ�")){
			order();
		}else {
			// �޾ƿ� id�� ȸ��DB�� �̸� ��������
			String name = checking.id_search(msg);
			
			pcFrame[pc-1].getComponent(5).setBackground(new Color(23,103,0));
	
			JLabel l2 = (JLabel)pcFrame[pc-1].getComponent(2);
			JLabel l3 = (JLabel)pcFrame[pc-1].getComponent(3);
			JLabel l4 = (JLabel)pcFrame[pc-1].getComponent(4);
			
			c[pc-1] = new clock(l4,msg);
			c[pc-1].start();
			
			cnt++;
			label1.setText("��� ��Ȳ "+cnt+" : "+count);
		
			l2.setText("ID : "+msg);
			l3.setText("�̸� : "+name);
		}
	}
	
	/*---------------------------- �ð� ������ ------------------------------*/
	
	// �ð�
	static class clock extends Thread{
		
		JLabel cl;
		int hour =0, minute=0, sec=10;
		String id;
		clock(JLabel cl,String id){
			this.cl = cl;
			this.id = id;
		}
		
		public void run() {
			try {
				while(!Thread.currentThread().isInterrupted()){
					hour = checking.time_hour(id);
					minute = checking.time_minute(id);
					sec = checking.time_sec(id);
					
					cl.setText("�ð� : "+(Integer.toString(hour))+"�ð�"+(Integer.toString(minute))+"��"+(Integer.toString(sec))+"��");		
					
					Thread.sleep(1000);
				}
			}catch(InterruptedException e) {

			}	
		}
	}
	
	/*---------------------------- ���� ------------------------------*/
	
	public static void main(String[] args) {
		PcServer_Main pc = new PcServer_Main("0");
		pc.setVisible(true);
		pc.setSize(1600,900);
		pc.setTitle("������ ����");
		pc.setLocationRelativeTo(null);
		pc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		PcServer pcServer = new PcServer();
		pcServer.startServer();
		
		// �����ͺ��̽� ����
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			System.out.println("JDBC ����̹� �ε� ����");
		}
	}
	
	
	/*---------------------------- DB ------------------------------*/
	private static Connection conn;
	private static PreparedStatement pstmt;
	private static String url = "jdbc:mysql://localhost:3306/pcbang?serverTimezone=UTC";
	private static String user = "root";
	private static String pw = "dlscjf158!A";
	
	// �԰Ÿ� �ֹ��� ���� �� ������ �����´�.
	public static void order() {
		try {
			conn = DriverManager.getConnection(url,user,pw);
		    
			Calendar cal = Calendar.getInstance();
		    cal.setTime(new Date());
		    String today;
		    today = cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DATE);
		      
			
			String sql = "select * from ���� where ��¥ = ? order by �ֹ���ȣ desc";
			pstmt = conn.prepareStatement(sql); 
			
			pstmt.setString(1, today);
			
			ResultSet rs = pstmt.executeQuery();
			// �޾ƿ� ������ input���ٰ� �����Ѵ�.
			String inputs[] = new String[8];
			// table�� �ʱ�ȭ ��Ų��.
			model.setNumRows(0);
			int i;
						
			while(rs.next()) {
				i = 0;
				for(int j = 0;j<column.length;j++,i++)
					inputs[i] = rs.getString(column[i]);
				model.addRow(inputs);
			}
			scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
}
