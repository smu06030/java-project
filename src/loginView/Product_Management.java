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
	//SKL = South Kind Label = 남쪽에 있는 종류 라벨이다.
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

		//Vector 배열 V_kind에 넣을 값을 상품분류 데베에서 갖고온다.
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
		}catch(ClassNotFoundException e1) {
			System.err.println("드라이버 로드에 실패했습니다.");
		}
		try {
			Connection con = DriverManager.getConnection(url,user,pw);
			Statement dbSt = con.createStatement();
			String strSql;	

			strSql="SELECT * FROM 상품분류";
			ResultSet result = dbSt.executeQuery(strSql);
			while(result.next()) {
				V_kind.add(result.getString("분류명")); 
			}
			dbSt.close();
			con.close();
		}catch(SQLException e1) {
			System.out.println("SQLException : "+e1.getMessage());
		}

		//왼쪽에 위치한 패널
		search_panel = new JPanel();
		search_panel.setPreferredSize(new Dimension(150,500));
		search_panel.setLayout(new FlowLayout(FlowLayout.CENTER,20,15));

		//search_panel 안에있는 종류로 검색하는 패널
		kind_panel = new JPanel();
		kind_panel.setPreferredSize(new Dimension(130,100));
		kind_panel.setBorder(blackline);

		sp_search_kind = new JLabel("종류로 검색");
		cb_kind = new JComboBox(V_kind);
		btn_kind = new JButton("종류 검색");
		btn_kind.addActionListener(this);

		//search_panel 안에있는 이름으로 검색하는 패널
		name_panel = new JPanel();
		name_panel.setPreferredSize(new Dimension(130,100));
		name_panel.setBorder(blackline);

		sp_search_name = new JLabel("이름으로 검색");
		txt_name = new JTextField(8);
		btn_name = new JButton("이름 검색");
		btn_name.addActionListener(this);

		//오른쪽에 위치한 값들을 보여주는 테이블이 있는 패널
		view_panel = new JPanel();
		view_panel.setLayout(new FlowLayout());

		columnName = new Vector<String>();
		columnName.add("품목 종류");		columnName.add("품목");
		columnName.add("가격");		columnName.add("재고");

		rowData = new Vector<Vector<String>>();
		model = new DefaultTableModel(rowData,columnName);
		table = new JTable(model);
		table.addMouseListener(this);
		tableSP = new JScrollPane(table);

		table.getColumn("품목 종류").setPreferredWidth(30);
		table.getColumn("품목").setPreferredWidth(50);
		table.getColumn("가격").setPreferredWidth(40);
		table.getColumn("재고").setPreferredWidth(30);
		tableSP.setPreferredSize(new Dimension(380,400));

		//밑에 위치한 수정, 삭제를 할때 사용되는 패널
		south = new JPanel();
		south.setBackground(Color.green);
		south.setPreferredSize(new Dimension(500,55));
		south.setLayout(new FlowLayout(FlowLayout.CENTER,10,15));

		SKL = new JLabel("품목 종류:");
		SNL = new JLabel("품목:");
		SPL = new JLabel("가격:");
		SSL = new JLabel("재고:");
		SKCB = new JComboBox(V_kind);
		SNTF = new JTextField(8);
		SPTF = new JTextField(6);
		SSTF = new JTextField(6);

		btn_all = new JButton("모두 조회");
		btn_all.addActionListener(this);
		btn_add = new JButton("추가");
		btn_add.addActionListener(this);
		btn_modify = new JButton("수정");
		btn_modify.addActionListener(this);
		btn_delete = new JButton("삭제");
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

		//<------------- 종류검색 ------------->
		if(s.equals("종류 검색")) {
			try {
				Connection con = DriverManager.getConnection(url,user,pw);
				Statement dbSt = con.createStatement();
				String strSql;	
				//콤보박스에서 선택된 값에 인덱스를 가지고와서 벡터배열 V_kind에서 인덱스 값을 이용해 문자를 가지고온다.
				String Selected_kind_name = V_kind.get(cb_kind.getSelectedIndex());

				//부속질의를 사용해 같은 분류코드를 갖고있는 분류들을 select 해준다.
				strSql="SELECT * FROM 상품 WHERE 분류 = ALL (select 분류코드 from 상품분류 where 분류명 = '"+ Selected_kind_name +"');";
				ResultSet result = dbSt.executeQuery(strSql);

				int rowCount = table.getRowCount();

				//현재 보이는 row 갯수를 확인하여 아무것도 없으면 바로 출력하고그렇지않으면  Table을 비워준다.
				if(rowCount == 0) ;
				else {
					for (int i = rowCount - 1; i >= 0; i--) {
						model.removeRow(i);
					}
				}

				while(result.next()) {
					//값이 있으면 새로운 백터 배열을 만들어서 테이블에 보여줄 값들으 add 해주고 테이블에 row열 값에 add해준다.
					Vector addRow = new Vector();

					String name = result.getString("상품명");
					int price = Integer.parseInt(result.getString("가격"));
					int stock = Integer.parseInt(result.getString("재고량"));

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

		//<------------- 이름검색 ------------->
		else if(s.equals("이름 검색")) {
			try {
				Connection con = DriverManager.getConnection(url,user,pw);
				Statement dbSt1 = con.createStatement();
				String strSql;	
				String Input_name = txt_name.getText();

				strSql="SELECT * FROM 상품 WHERE 상품명 like '%"+ Input_name +"%';";
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
		               String type = result.getString("분류");
		               String typename = null;
		               String name = result.getString("상품명");
		               int price = Integer.parseInt(result.getString("가격"));
		               int stock = Integer.parseInt(result.getString("재고량"));

		               Statement dbSt2 = con.createStatement();
		               //상품분류 데베에서 분류코드를 이용해 분류명을 갖고오기 위한 SQL문
		               String get_typename;
		               get_typename = "SELECT * FROM 상품분류 WHERE 분류코드 = '"+ type +"';" ;
		               ResultSet typenameSQL = dbSt2.executeQuery(get_typename);
		               while(typenameSQL.next()) {
		                  typename = typenameSQL.getString("분류명");
		               }
		               Vector addRow = new Vector();
		               addRow.add(typename);   addRow.add(name);   
		               addRow.add(price);   addRow.add(stock);   

		               rowData.add(addRow);
		               
		               flag = 0;
		               dbSt2.close();
		            }
		            if(flag == 1){
		               JOptionPane.showMessageDialog(null, "해당 상품은 없습니다.");
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

		//<------------- 모두 조회 ------------->
		else if(s.equals("모두 조회")) {
			try {
				Connection con = DriverManager.getConnection(url,user,pw);
				Statement dbSt1 = con.createStatement();
				String strSql;	

				strSql="SELECT * FROM 상품;";
				ResultSet result = dbSt1.executeQuery(strSql);

				int rowCount = table.getRowCount();

				if(rowCount == 0) ;
				else {
					for (int i = rowCount - 1; i >= 0; i--) {
						model.removeRow(i);
					}
				}
				while(result.next()) {
					String type = result.getString("분류");
					String typename = null;
					String name = result.getString("상품명");
					int price = Integer.parseInt(result.getString("가격"));
					int stock = Integer.parseInt(result.getString("재고량"));

					Statement dbSt2 = con.createStatement();
					String get_typename;
					get_typename = "SELECT * FROM 상품분류 WHERE 분류코드 = '"+ type +"';" ;
					ResultSet typenameSQL = dbSt2.executeQuery(get_typename);
					while(typenameSQL.next()) {
						typename = typenameSQL.getString("분류명");
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

		//<------------- 추가 ------------->
		else if(s.equals("추가")) {
			String[] option = {"품목 종류 추가","품목 추가"};
			int selected_btn = JOptionPane.showOptionDialog(null, "선택해주세요.", "추가", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, option, option[0]);
			if(selected_btn == 0) {
				Add_Kind ak = new Add_Kind("품목 종류 추가",table, V_kind);
				ak.setSize(360,120);
				ak.setLocationRelativeTo(null);
				ak.setVisible(true);
			}
			else if(selected_btn == 1) {
				Add_Item ai = new Add_Item("품목 추가");
				ai.setSize(350,400);
				ai.setLocationRelativeTo(null);
				ai.setVisible(true);
			}
		}
		else if(s.equals("수정")) {
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

				get_typenum = "SELECT * FROM 상품분류 WHERE 분류명 = '"+ Selected_kind_name +"';";
				ResultSet SQLtypenum = dbSt.executeQuery(get_typenum);
				while(SQLtypenum.next()) {
					typenum = SQLtypenum.getString("분류코드");
				}

				update_row = "UPDATE 상품 SET 상품명 = '"+ name +"', 가격 = "+ price +", 재고량 = "+ stock +", 분류 = '"+ typenum +"' WHERE 상품명 = '"+ origin_name +"';";
				dbSt.executeUpdate(update_row);

				table.updateUI();
				JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.");
				dbSt.close();
				con.close();
			}catch(SQLException e1) {
				System.out.println("SQLException : "+e1.getMessage());
			}
		}

		//<------------- 삭제 ------------->
		else if(s.equals("삭제")) {
			//정말 삭제할것인지 확인한다.
			int check_remove = JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까?","삭제",JOptionPane.YES_NO_OPTION);
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

					delete_row = "DELETE FROM 상품 WHERE 상품명 = '"+ name +"';";
					dbSt.executeUpdate(delete_row);

					SKCB.setSelectedIndex(0);
					SNTF.setText("");
					SPTF.setText("");
					SSTF.setText("");

					table.updateUI();
					JOptionPane.showMessageDialog(null, "삭제가 완료되었습니다.");

					dbSt.close();
					con.close();
				}catch(SQLException e1) {
					System.out.println("SQLException : "+e1.getMessage());
				}

			}
		}
	}

	//<------------- 마우스클릭 이벤트 메소드 ------------->
	public void mouseClicked(MouseEvent e) {
		row = table.getSelectedRow();
		column = table.getSelectedColumn();
		// 표에서 선택한 행의 정보를 입력칸에 출력
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
		p.setTitle("재무 관리");
		p.setSize(550, 500);
		p.setLocationRelativeTo(null);
		p.setVisible(true);
		p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

