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
	private String member[] = {"���̵�","��й�ȣ","�̸�","�������","�޴���","�̸���"};
	private DefaultTableModel model = null;
	private JTable table;
	private JScrollPane scroll;
	
	Member_management(){
		ct = getContentPane();
		ct.setLayout(new BorderLayout());
		
		/*---------------------------- top ��ư �߰� ------------------------------*/
		
		top.setBackground(new Color(22,28,24)); //new Color(22,28,24)
		top.setLayout(new FlowLayout());
		top.setPreferredSize(new Dimension(1000,80));
		top.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
		
		JLabel label = new JLabel("< ȸ������ >");
		label.setFont(new Font("����������",Font.BOLD,30));
		label.setForeground(Color.WHITE);
		
		top.add(label);
		
		/*---------------------------- ���� �˻� ���------------------------------*/
		
		left.setBackground(new Color(57,56,54)); 
		left.setLayout(null);
		left.setPreferredSize(new Dimension(200,920));
		
		// �̸����� �˻�
		JLabel name_label = new JLabel("�̸����� �˻�");
		name_label.setBorder(BorderFactory.createEmptyBorder(20,0,20,0));
		name_label.setForeground(Color.WHITE);
		name_label.setFont(new Font("����������",Font.BOLD,20));
		name_label.setBounds(40,30,150,50);
		name_field = new JTextField(8);
		name_field.setHorizontalAlignment(name_field.CENTER);
		name_field.setPreferredSize(new Dimension(50,30));
		name_field.setFont(new Font("����������",Font.BOLD,20));
		name_field.setBounds(17,90,165,32);
		name_search = new JButton("�˻�(N)");
		name_search.setBackground(new Color(243,97,185));
		name_search.setForeground(Color.WHITE);
		name_search.setFont(new Font("����������",Font.BOLD,20));
		name_search.setFocusPainted(false);
		name_search.setBorder(new LineBorder(Color.white,2));
		name_search.setPreferredSize(new Dimension(156,40));
		name_search.setBounds(17,140,165,42);
		
		// ID�� �˻�
		JLabel id_label = new JLabel("ID���� �˻�");
		id_label.setBorder(BorderFactory.createEmptyBorder(60,0,20,0));
		id_label.setForeground(Color.WHITE);
		id_label.setFont(new Font("����������",Font.BOLD,20));
		id_label.setBounds(47,220,150,60);
		id_field = new JTextField(8);
		id_field.setHorizontalAlignment(id_field.CENTER);
		id_field.setPreferredSize(new Dimension(50,30));
		id_field.setFont(new Font("����������",Font.BOLD,20));
		id_field.setBounds(17,305,165,32);
		id_search = new JButton("�˻�(I)");
		id_search.setBackground(new Color(243,97,185));
		id_search.setForeground(Color.WHITE);
		id_search.setFont(new Font("����������",Font.BOLD,20));
		id_search.setFocusPainted(false);
		id_search.setBorder(new LineBorder(Color.white,2));
		id_search.setPreferredSize(new Dimension(156,40));
		id_search.setBounds(17,352,165,42);
		
		// ��� �˻�
		all_search = new JButton("��� ��ȸ");
		all_search.setBackground(new Color(243,97,185));
		all_search.setForeground(Color.WHITE);
		all_search.setFont(new Font("����������",Font.BOLD,20));
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
		
		/*---------------------------- ������ ��ȸ ------------------------------*/
		
		right.setBackground(new Color(57,56,54));
		right.setPreferredSize(new Dimension(800,920));
		
		// ���̺� ����
		for(int i = 0;i<member.length;i++)
			title.add(member[i]);
		
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
		table.getTableHeader().setFont(new Font("�������",Font.BOLD,15));
		table.getTableHeader().setBackground(Color.LIGHT_GRAY);
		table.setFont(new Font("�������",Font.BOLD,14));
		table.getColumn("���̵�").setPreferredWidth(20);
		table.getColumn("��й�ȣ").setPreferredWidth(20);
		table.getColumn("�̸�").setPreferredWidth(10);
		table.getColumn("�������").setPreferredWidth(30);
		table.getColumn("�޴���").setPreferredWidth(30);
		table.getColumn("�̸���").setPreferredWidth(50);
		table.setRowHeight(30);
		scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(780,570));
		
		right.add(scroll);
		
		/*---------------------------- �����̳ʿ� �߰� ------------------------------*/
		
		ct.add(top,BorderLayout.NORTH);
		ct.add(left,BorderLayout.WEST);
		ct.add(right,BorderLayout.EAST);
		
		/*------------------------------- �̺�Ʈ ---------------------------------*/
		
		name_search.addActionListener(this);
		name_field.addActionListener(this);
		id_search.addActionListener(this);
		id_field.addActionListener(this);
		all_search.addActionListener(this);
		table.addMouseListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("�˻�(N)") || e.getSource() == name_field) {
			if(name_field.getText().length() == 0) { // ����ó��
				JOptionPane.showMessageDialog(null, "���� �Էµ��� �ʾҽ��ϴ�.");
			}else {
				// �Էµ� �̸��� �˻��� ȸ��DB���� ��ȸ�ؼ� ���̺� �����ش�.
				name_sh(name_field.getText());
				name_field.setText("");
			}
		}else if(e.getActionCommand().equals("�˻�(I)") || e.getSource() == id_field) {
			if(id_field.getText().length() == 0) { // ����ó��
				JOptionPane.showMessageDialog(null, "���� �Էµ��� �ʾҽ��ϴ�.");
			}else {
				// �Էµ� ���̵��� �˻��� ȸ��DB���� ��ȸ�ؼ� ���̺� �����ش�.
				id_sh(id_field.getText());
				id_field.setText("");	
			}
		}else if(e.getActionCommand().equals("��� ��ȸ")) {
			all_sh();
		}
	}
	

	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2) {
			int row = table.getSelectedRow(); // ���� ���õ� ��
			String id = (String)table.getValueAt(row, 0); // ���̵�
			String pass = (String)table.getValueAt(row, 1); // ��й�ȣ
			String name = (String)table.getValueAt(row, 2); // �̸�
			
			String birth = (String)table.getValueAt(row, 3); // �������
			String year = birth.substring(0,4); // ��������� �⵵, ��, �� ���� �з�
			String month = birth.substring(5,7);
			int month_int = Integer.parseInt(month); // ���ڷ� ��ȯ
			String day = birth.substring(8,10);
			int day_int = Integer.parseInt(day); // ���ڷ� ��ȯ
			
			String phone = (String)table.getValueAt(row, 4); // �޴���
			String first_p = phone.substring(0,3); // �޴��� ��ȣ�� �տ���ȣ �ڿ���ȣ�� �з�
			String last_p = phone.substring(3,11);
			
			String email = (String)table.getValueAt(row, 5); // �̸���
			String first_e[] = email.split("@"); // �̸����� @�� �����Ͽ� �з�

			Member_UpAndDel member = new Member_UpAndDel(id,pass,name,year,month_int,day_int,first_p,last_p,first_e[0],first_e[1]);
			member.setVisible(true);
			member.setTitle("ȸ������");
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
	
	
	/*------------------------------- ���� ---------------------------------*/
	
	public static void main(String[] args) {
		// DB����
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("����̹� ���� ����");	
			
			conn = DriverManager.getConnection(url,user,pw);
					
		}catch(ClassNotFoundException e) {
			System.out.println("JDBC ����̹� �ε� ����");
		}catch(SQLException e) {
			System.out.println("DB���� ����\n"+e.getMessage());
		}
		
		Member_management member = new Member_management();
		
		member.setVisible(true);
		member.setSize(1000,700);
		member.setTitle("ȸ������");
		member.setLocationRelativeTo(null);
		member.setResizable(false);
		member.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/*------------------------------- DB ---------------------------------*/
	
	// �̸����� ȸ�� ��ȸ
	public void name_sh(String name) {
		try {
			conn = DriverManager.getConnection(url,user,pw);
			
			String sql = "select * from ȸ��  where �̸� like ?";
			
			pstmt = conn.prepareStatement(sql); 
			
			pstmt.setString(1,name);
			
			rs = pstmt.executeQuery();
			// �޾ƿ� ������ input���ٰ� �����Ѵ�.
			Vector<String> inputs = new Vector<String>();
			// table�� �ʱ�ȭ ��Ų��.
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
	// ���̵�� ȸ�� ��ȸ
	public void id_sh(String id) {
		try {
			conn = DriverManager.getConnection(url,user,pw);
			
			String sql = "select * from ȸ��  where ���̵� like ?";
			
			pstmt = conn.prepareStatement(sql); 
			
			pstmt.setString(1,id);
			
			rs = pstmt.executeQuery();
			// �޾ƿ� ������ input���ٰ� �����Ѵ�.
			Vector<String> inputs = new Vector<String>();
			// table�� �ʱ�ȭ ��Ų��.
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
	
	// ��� ȸ�� ��ȸ
	public void all_sh() {
		try {
			conn = DriverManager.getConnection(url,user,pw);
			
			String sql = "select * from ȸ��";
			
			pstmt = conn.prepareStatement(sql); 
			
			rs = pstmt.executeQuery();
			// �޾ƿ� ������ input���ٰ� �����Ѵ�.
			String inputs[] = new String[6];
			// table�� �ʱ�ȭ ��Ų��.
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
