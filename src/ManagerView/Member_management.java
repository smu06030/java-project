package ManagerView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class Member_management extends JFrame implements ActionListener{
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
		top.setBorder(BorderFactory.createEmptyBorder(15,0,0,0));
		
		JButton insert = new JButton("���");
		insert.setFont(new Font("����������",Font.BOLD,20));
		insert.setFocusPainted(false);
		insert.setBackground(new Color(86,42,57));
		insert.setForeground(Color.WHITE);
		insert.setPreferredSize(new Dimension(100,40));
		insert.setBorder(new LineBorder(Color.WHITE,2));
		JButton update = new JButton("����");
		update.setFont(new Font("����������",Font.BOLD,20));
		update.setFocusPainted(false);
		update.setBackground(new Color(86,42,57));
		update.setForeground(Color.WHITE);
		update.setPreferredSize(new Dimension(100,40));
		update.setBorder(new LineBorder(Color.WHITE,2));
		JButton delete = new JButton("����");
		delete.setFont(new Font("����������",Font.BOLD,20));
		delete.setFocusPainted(false);
		delete.setBackground(new Color(86,42,57));
		delete.setForeground(Color.WHITE);
		delete.setPreferredSize(new Dimension(100,40));
		delete.setBorder(new LineBorder(Color.WHITE,2));
		
		top.add(insert);
		top.add(update);
		top.add(delete);
		
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
		
		model = new DefaultTableModel(content,title);
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
	
	/*------------------------------- ���� ---------------------------------*/
	
	public static void main(String[] args) {
		// DB����
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("����̹� ���� ����");	
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pcbang?serverTimezone=UTC","root","dlscjf158!A");
					
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
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pcbang?serverTimezone=UTC","root","dlscjf158!A");
			
			String sql = "select * from ȸ��  where �̸� like ?";
			
			pstmt = conn.prepareStatement(sql); 
			
			pstmt.setString(1,"%"+name+"%");
			
			rs = pstmt.executeQuery();
			// �޾ƿ� ������ input���ٰ� �����Ѵ�.
			String input[] = new String[6];
			
			// table�� �ʱ�ȭ ��Ų��.
			model.setNumRows(0);
			
			while(rs.next()) {
				input[0] = rs.getString("���̵�");
				input[1] = rs.getString("��й�ȣ");
				input[2] = rs.getString("�̸�");
				input[3] = rs.getString("�������");
				input[4] = rs.getString("�޴���");
				input[5] = rs.getString("�̸���");
				model.addRow(input);	
			}
			
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
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pcbang?serverTimezone=UTC","root","dlscjf158!A");
			
			String sql = "select * from ȸ��  where ���̵� like ?";
			
			pstmt = conn.prepareStatement(sql); 
			
			pstmt.setString(1,"%"+id+"%");
			
			rs = pstmt.executeQuery();
			// �޾ƿ� ������ input���ٰ� �����Ѵ�.
			String input[] = new String[6];
			
			// table�� �ʱ�ȭ ��Ų��.
			model.setNumRows(0);
			
			while(rs.next()) {
				input[0] = rs.getString("���̵�");
				input[1] = rs.getString("��й�ȣ");
				input[2] = rs.getString("�̸�");
				input[3] = rs.getString("�������");
				input[4] = rs.getString("�޴���");
				input[5] = rs.getString("�̸���");
				model.addRow(input);	
			}
			
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
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pcbang?serverTimezone=UTC","root","dlscjf158!A");
			
			String sql = "select * from ȸ��";
			
			pstmt = conn.prepareStatement(sql); 
			
			rs = pstmt.executeQuery();
			// �޾ƿ� ������ input���ٰ� �����Ѵ�.
			String input[] = new String[6];
			// table�� �ʱ�ȭ ��Ų��.
			model.setNumRows(0);
			
			while(rs.next()) {
				input[0] = rs.getString("���̵�");
				input[1] = rs.getString("��й�ȣ");
				input[2] = rs.getString("�̸�");
				input[3] = rs.getString("�������");
				input[4] = rs.getString("�޴���");
				input[5] = rs.getString("�̸���");
				model.addRow(input);	
			}

			pstmt.close();
			conn.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
