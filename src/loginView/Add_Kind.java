package loginView;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

//<------------- 품목 종류 추가 클래스 ------------->
public class Add_Kind extends JFrame implements ActionListener{
	//String url = "jdbc:mysql://172.111.117.107:3306/pcbang?serverTimezone=UTC";
	String url = "jdbc:mysql://localhost:3306/pcbang?serverTimezone=UTC";
	String user = "root";
	String pw = "dlscjf158!A";
	
	JPanel top, bottom;
	JLabel typenum_l, typename_l;
	JTextField typenum_t, typename_t;
	JButton submit, cancel;

	JTable table;
	Vector V_kind;
	
	Add_Kind(String title, JTable table, Vector V_kind){
		this.table = table;
		this.V_kind = V_kind;
		System.out.println(V_kind);
		setTitle(title);
		Container ct_Add = getContentPane();
		ct_Add.setLayout(new BorderLayout());

		top = new JPanel();
		bottom = new JPanel();
		typenum_l = new JLabel("코드 : ");
		typename_l = new JLabel("이름 : ");
		typenum_t = new JTextField(3);
		typename_t = new JTextField(10);
		submit = new JButton("추가");
		submit.addActionListener(this);
		cancel = new JButton("취소");
		cancel.addActionListener(this);

		top.add(typenum_l);   top.add(typenum_t);
		top.add(typename_l);   top.add(typename_t);

		bottom.add(submit);   bottom.add(cancel);

		ct_Add.add(top,BorderLayout.CENTER);
		ct_Add.add(bottom,BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
//<------------- 추가 ------------->
		String s = e.getActionCommand();
		if(s.equals("추가")) {
			String code = typenum_t.getText();
			String name = typename_t.getText();
			//빈칸이 있을때 예외처리
			if(code.equals("")||name.equals("")) {
				JOptionPane.showMessageDialog(null, "빈칸을 채워주세요.");
			}
			else {
				try {
					Connection con = DriverManager.getConnection(url,user,pw);
					Statement dbSt = con.createStatement();
					String check_strSql;

					check_strSql = "SELECT * FROM 상품분류 WHERE 분류코드 = '"+ code +"';";
					ResultSet check_result = dbSt.executeQuery(check_strSql);
					
					//상품 분류 데베에서 코드가 있는지 없는지 확인하고 insert 해준다.
					if(check_result.next()) {
						JOptionPane.showMessageDialog(null, "이미 존재하는 상품 종류 코드입니다.");
						typenum_t.setText("");
						typename_t.setText("");
						typenum_t.requestFocus();
					}
					else {
						String add_strSql;
						add_strSql = "INSERT INTO 상품분류 VALUES('"+ code +"','"+ name +"');";
						dbSt.executeUpdate(add_strSql);
						JOptionPane.showMessageDialog(null, "추가되었습니다.");
						V_kind.add(name);
						table.updateUI();
					}

					dbSt.close();
					con.close();
				}catch(SQLException e1) {
					System.out.println("SQLException : "+e1.getMessage());
				}
			}
		}
		else if(s.equals("취소")) {
			typenum_t.setText("");
			typename_t.setText("");
			typenum_t.requestFocus();
		}
	}
}