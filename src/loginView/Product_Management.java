package loginView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

public class Product_Management extends JFrame implements ActionListener, MouseListener{
	//String url = "jdbc:mysql://172.111.117.107:3306/pcbang?serverTimezone=UTC";
	String url = "jdbc:mysql://localhost:3306/pcbang?serverTimezone=UTC";
	String user = "root";
	String pw = "dlscjf158!A";

	static Vector<String> columnName, V_kind;
	Vector<Vector<String>> rowData;
	JTable table = null;
	DefaultTableModel model = null;
	JPanel search_panel, view_panel, kind_panel, name_panel, south;
	//SKL = South Kind Label = ���ʿ� �ִ� ���� ���̴�.
	JLabel sp_search_kind, sp_search_name, south_kind, SKL, SNL, SPL, SSL;
	JComboBox cb_kind, SKCB;
	JTextField txt_name, SNTF, SPTF, SSTF;
	JButton btn_kind, btn_name, btn_all, btn_add, btn_modify, btn_delete;

	JScrollPane tableSP;

	int row, column;

	public Product_Management(){
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout());

		Border blackline = BorderFactory.createLineBorder(Color.black);

		V_kind = new Vector<String>();

		//Vector �迭 V_kind�� ���� ���� ��ǰ�з� �������� ����´�.
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.err.println("JDBC-ODBC ����̹��� ���������� �ε���");
		}catch(ClassNotFoundException e1) {
			System.err.println("����̹� �ε忡 �����߽��ϴ�.");
		}
		try {
			Connection con = DriverManager.getConnection(url,user,pw);
			Statement dbSt = con.createStatement();
			String strSql;	

			strSql="SELECT * FROM ��ǰ�з�";
			ResultSet result = dbSt.executeQuery(strSql);
			while(result.next()) {
				V_kind.add(result.getString("�з���")); 
			}
			dbSt.close();
			con.close();
		}catch(SQLException e1) {
			System.out.println("SQLException : "+e1.getMessage());
		}

		//���ʿ� ��ġ�� �г�
		search_panel = new JPanel();
		search_panel.setPreferredSize(new Dimension(150,500));
		search_panel.setLayout(new FlowLayout(FlowLayout.CENTER,20,15));

		//search_panel �ȿ��ִ� ������ �˻��ϴ� �г�
		kind_panel = new JPanel();
		kind_panel.setPreferredSize(new Dimension(130,100));
		kind_panel.setBorder(blackline);

		sp_search_kind = new JLabel("������ �˻�");
		cb_kind = new JComboBox(V_kind);
		btn_kind = new JButton("���� �˻�");
		btn_kind.addActionListener(this);

		//search_panel �ȿ��ִ� �̸����� �˻��ϴ� �г�
		name_panel = new JPanel();
		name_panel.setPreferredSize(new Dimension(130,100));
		name_panel.setBorder(blackline);

		sp_search_name = new JLabel("�̸����� �˻�");
		txt_name = new JTextField(8);
		btn_name = new JButton("�̸� �˻�");
		btn_name.addActionListener(this);

		//�����ʿ� ��ġ�� ������ �����ִ� ���̺��� �ִ� �г�
		view_panel = new JPanel();
		view_panel.setLayout(new FlowLayout());

		columnName = new Vector<String>();
		columnName.add("ǰ�� ����");		columnName.add("ǰ��");
		columnName.add("����");		columnName.add("���");

		rowData = new Vector<Vector<String>>();
		model = new DefaultTableModel(rowData,columnName);
		table = new JTable(model);
		table.addMouseListener(this);
		tableSP = new JScrollPane(table);

		table.getColumn("ǰ�� ����").setPreferredWidth(30);
		table.getColumn("ǰ��").setPreferredWidth(50);
		table.getColumn("����").setPreferredWidth(40);
		table.getColumn("���").setPreferredWidth(30);
		tableSP.setPreferredSize(new Dimension(380,400));

		//�ؿ� ��ġ�� ����, ������ �Ҷ� ���Ǵ� �г�
		south = new JPanel();
		south.setBackground(Color.green);
		south.setPreferredSize(new Dimension(500,55));
		south.setLayout(new FlowLayout(FlowLayout.CENTER,10,15));

		SKL = new JLabel("ǰ�� ����:");
		SNL = new JLabel("ǰ��:");
		SPL = new JLabel("����:");
		SSL = new JLabel("���:");
		SKCB = new JComboBox(V_kind);
		SNTF = new JTextField(8);
		SPTF = new JTextField(6);
		SSTF = new JTextField(6);

		btn_all = new JButton("��� ��ȸ");
		btn_all.addActionListener(this);
		btn_add = new JButton("�߰�");
		btn_add.addActionListener(this);
		btn_modify = new JButton("����");
		btn_modify.addActionListener(this);
		btn_delete = new JButton("����");
		btn_delete.addActionListener(this);



		kind_panel.add(sp_search_kind);	kind_panel.add(cb_kind);	kind_panel.add(btn_kind);
		name_panel.add(sp_search_name);	name_panel.add(txt_name);	name_panel.add(btn_name);

		search_panel.add(kind_panel);
		search_panel.add(name_panel);
		search_panel.add(btn_all);
		search_panel.add(btn_add);
		search_panel.add(btn_modify);
		search_panel.add(btn_delete);

		view_panel.add(tableSP);

		south.add(SKL);	south.add(SKCB);
		south.add(SNL);	south.add(SNTF);
		south.add(SPL);	south.add(SPTF);
		south.add(SSL);	south.add(SSTF);

		ct.add(search_panel,BorderLayout.WEST);
		ct.add(view_panel,BorderLayout.CENTER);
		ct.add(south,BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();

		//<------------- �����˻� ------------->
		if(s.equals("���� �˻�")) {
			try {
				Connection con = DriverManager.getConnection(url,user,pw);
				Statement dbSt = con.createStatement();
				String strSql;	
				//�޺��ڽ����� ���õ� ���� �ε����� ������ͼ� ���͹迭 V_kind���� �ε��� ���� �̿��� ���ڸ� ������´�.
				String Selected_kind_name = V_kind.get(cb_kind.getSelectedIndex());

				//�μ����Ǹ� ����� ���� �з��ڵ带 �����ִ� �з����� select ���ش�.
				strSql="SELECT * FROM ��ǰ WHERE �з� = ALL (select �з��ڵ� from ��ǰ�з� where �з��� = '"+ Selected_kind_name +"');";
				ResultSet result = dbSt.executeQuery(strSql);

				int rowCount = table.getRowCount();

				//���� ���̴� row ������ Ȯ���Ͽ� �ƹ��͵� ������ �ٷ� ����ϰ�׷���������  Table�� ����ش�.
				if(rowCount == 0) ;
				else {
					for (int i = rowCount - 1; i >= 0; i--) {
						model.removeRow(i);
					}
				}

				while(result.next()) {
					//���� ������ ���ο� ���� �迭�� ���� ���̺� ������ ������ add ���ְ� ���̺� row�� ���� add���ش�.
					Vector addRow = new Vector();

					String name = result.getString("��ǰ��");
					int price = Integer.parseInt(result.getString("����"));
					int stock = Integer.parseInt(result.getString("���"));

					addRow.add(Selected_kind_name);   addRow.add(name);   
					addRow.add(price);   addRow.add(stock);	

					rowData.add(addRow);
				}
				table.updateUI();

				dbSt.close();
				con.close();
			}catch(SQLException e1) {
				System.out.println("SQLException : "+e1.getMessage());
			}
		}

		//<------------- �̸��˻� ------------->
		else if(s.equals("�̸� �˻�")) {
			try {
				Connection con = DriverManager.getConnection(url,user,pw);
				Statement dbSt1 = con.createStatement();
				String strSql;	
				String Input_name = txt_name.getText();

				strSql="SELECT * FROM ��ǰ WHERE ��ǰ�� like '%"+ Input_name +"%';";
				ResultSet result = dbSt1.executeQuery(strSql);

				int rowCount = table.getRowCount();

				if(rowCount == 0) ;
				else {
					for (int i = rowCount - 1; i >= 0; i--) {
						model.removeRow(i);
					}
				}
				int flag = 1;
				while(result.next()) {
		               String type = result.getString("�з�");
		               String typename = null;
		               String name = result.getString("��ǰ��");
		               int price = Integer.parseInt(result.getString("����"));
		               int stock = Integer.parseInt(result.getString("���"));

		               Statement dbSt2 = con.createStatement();
		               //��ǰ�з� �������� �з��ڵ带 �̿��� �з����� ������� ���� SQL��
		               String get_typename;
		               get_typename = "SELECT * FROM ��ǰ�з� WHERE �з��ڵ� = '"+ type +"';" ;
		               ResultSet typenameSQL = dbSt2.executeQuery(get_typename);
		               while(typenameSQL.next()) {
		                  typename = typenameSQL.getString("�з���");
		               }
		               Vector addRow = new Vector();
		               addRow.add(typename);   addRow.add(name);   
		               addRow.add(price);   addRow.add(stock);   

		               rowData.add(addRow);
		               
		               flag = 0;
		               dbSt2.close();
		            }
		            if(flag == 1){
		               JOptionPane.showMessageDialog(null, "�ش� ��ǰ�� �����ϴ�.");
		               txt_name.setText("");
		               txt_name.requestFocus();
		            }

				table.updateUI();

				dbSt1.close();
				con.close();
			}catch(SQLException e1) {
				System.out.println("SQLException : "+e1.getMessage());
			}
		}

		//<------------- ��� ��ȸ ------------->
		else if(s.equals("��� ��ȸ")) {
			try {
				Connection con = DriverManager.getConnection(url,user,pw);
				Statement dbSt1 = con.createStatement();
				String strSql;	

				strSql="SELECT * FROM ��ǰ;";
				ResultSet result = dbSt1.executeQuery(strSql);

				int rowCount = table.getRowCount();

				if(rowCount == 0) ;
				else {
					for (int i = rowCount - 1; i >= 0; i--) {
						model.removeRow(i);
					}
				}
				while(result.next()) {
					String type = result.getString("�з�");
					String typename = null;
					String name = result.getString("��ǰ��");
					int price = Integer.parseInt(result.getString("����"));
					int stock = Integer.parseInt(result.getString("���"));

					Statement dbSt2 = con.createStatement();
					String get_typename;
					get_typename = "SELECT * FROM ��ǰ�з� WHERE �з��ڵ� = '"+ type +"';" ;
					ResultSet typenameSQL = dbSt2.executeQuery(get_typename);
					while(typenameSQL.next()) {
						typename = typenameSQL.getString("�з���");
					}

					Vector addRow = new Vector();
					addRow.add(typename);   addRow.add(name);   
					addRow.add(price);   addRow.add(stock);	

					rowData.add(addRow);
					dbSt2.close();
				}
				table.updateUI();

				dbSt1.close();
				con.close();
			}catch(SQLException e1) {
				System.out.println("SQLException : "+e1.getMessage());
			}
		}

		//<------------- �߰� ------------->
		else if(s.equals("�߰�")) {
			String[] option = {"ǰ�� ���� �߰�","ǰ�� �߰�"};
			int selected_btn = JOptionPane.showOptionDialog(null, "�������ּ���.", "�߰�", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, option, option[0]);
			if(selected_btn == 0) {
				Add_Kind ak = new Add_Kind("ǰ�� ���� �߰�",table, V_kind);
				ak.setSize(360,120);
				ak.setLocationRelativeTo(null);
				ak.setVisible(true);
			}
			else if(selected_btn == 1) {
				Add_Item ai = new Add_Item("ǰ�� �߰�");
				ai.setSize(350,400);
				ai.setLocationRelativeTo(null);
				ai.setVisible(true);
			}
		}
		else if(s.equals("����")) {
			String kind = (String) SKCB.getSelectedItem();
			String name = SNTF.getText();
			String origin_name = (String)table.getValueAt(row, 1);
			String price = SPTF.getText();
			String stock = SSTF.getText();

			Vector <String> txt = new Vector <String>();
			txt.add(kind);	txt.add(name);
			txt.add(price);	txt.add(stock);

			rowData.remove(row); rowData.add(row, txt);

			try {
				Connection con = DriverManager.getConnection(url,user,pw);
				Statement dbSt = con.createStatement();
				String get_typenum;	
				String update_row;
				String typenum = null;
				String Selected_kind_name = V_kind.get(SKCB.getSelectedIndex());

				get_typenum = "SELECT * FROM ��ǰ�з� WHERE �з��� = '"+ Selected_kind_name +"';";
				ResultSet SQLtypenum = dbSt.executeQuery(get_typenum);
				while(SQLtypenum.next()) {
					typenum = SQLtypenum.getString("�з��ڵ�");
				}

				update_row = "UPDATE ��ǰ SET ��ǰ�� = '"+ name +"', ���� = "+ price +", ��� = "+ stock +", �з� = '"+ typenum +"' WHERE ��ǰ�� = '"+ origin_name +"';";
				dbSt.executeUpdate(update_row);

				table.updateUI();
				JOptionPane.showMessageDialog(null, "������ �Ϸ�Ǿ����ϴ�.");
				dbSt.close();
				con.close();
			}catch(SQLException e1) {
				System.out.println("SQLException : "+e1.getMessage());
			}
		}

		//<------------- ���� ------------->
		else if(s.equals("����")) {
			//���� �����Ұ����� Ȯ���Ѵ�.
			int check_remove = JOptionPane.showConfirmDialog(null, "���� �����Ͻðڽ��ϱ�?","����",JOptionPane.YES_NO_OPTION);
			if(check_remove == JOptionPane.NO_OPTION) {
				this.dispose();
			}
			else if(check_remove == JOptionPane.YES_OPTION) {
				String name = SNTF.getText();
				model.removeRow(row);

				try {
					Connection con = DriverManager.getConnection(url,user,pw);
					Statement dbSt = con.createStatement();
					String delete_row;	

					delete_row = "DELETE FROM ��ǰ WHERE ��ǰ�� = '"+ name +"';";
					dbSt.executeUpdate(delete_row);

					SKCB.setSelectedIndex(0);
					SNTF.setText("");
					SPTF.setText("");
					SSTF.setText("");

					table.updateUI();
					JOptionPane.showMessageDialog(null, "������ �Ϸ�Ǿ����ϴ�.");

					dbSt.close();
					con.close();
				}catch(SQLException e1) {
					System.out.println("SQLException : "+e1.getMessage());
				}

			}
		}
	}

	//<------------- ���콺Ŭ�� �̺�Ʈ �޼ҵ� ------------->
	public void mouseClicked(MouseEvent e) {
		row = table.getSelectedRow();
		column = table.getSelectedColumn();
		// ǥ���� ������ ���� ������ �Է�ĭ�� ���
		SKCB.setSelectedItem((String)model.getValueAt(row, 0));	
		SNTF.setText((String)model.getValueAt(row, 1));
		SPTF.setText(Integer.toString((int) model.getValueAt(row, 2)));
		SSTF.setText(Integer.toString((int) model.getValueAt(row, 3)));
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	public static void main(String[] args) {
		Product_Management p = new Product_Management();
		p.setTitle("�繫 ����");
		p.setSize(550, 500);
		p.setLocationRelativeTo(null);
		p.setVisible(true);
		p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

