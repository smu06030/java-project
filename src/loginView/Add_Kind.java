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

//<------------- ǰ�� ���� �߰� Ŭ���� ------------->
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
		typenum_l = new JLabel("�ڵ� : ");
		typename_l = new JLabel("�̸� : ");
		typenum_t = new JTextField(3);
		typename_t = new JTextField(10);
		submit = new JButton("�߰�");
		submit.addActionListener(this);
		cancel = new JButton("���");
		cancel.addActionListener(this);

		top.add(typenum_l);   top.add(typenum_t);
		top.add(typename_l);   top.add(typename_t);

		bottom.add(submit);   bottom.add(cancel);

		ct_Add.add(top,BorderLayout.CENTER);
		ct_Add.add(bottom,BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
//<------------- �߰� ------------->
		String s = e.getActionCommand();
		if(s.equals("�߰�")) {
			String code = typenum_t.getText();
			String name = typename_t.getText();
			//��ĭ�� ������ ����ó��
			if(code.equals("")||name.equals("")) {
				JOptionPane.showMessageDialog(null, "��ĭ�� ä���ּ���.");
			}
			else {
				try {
					Connection con = DriverManager.getConnection(url,user,pw);
					Statement dbSt = con.createStatement();
					String check_strSql;

					check_strSql = "SELECT * FROM ��ǰ�з� WHERE �з��ڵ� = '"+ code +"';";
					ResultSet check_result = dbSt.executeQuery(check_strSql);
					
					//��ǰ �з� �������� �ڵ尡 �ִ��� ������ Ȯ���ϰ� insert ���ش�.
					if(check_result.next()) {
						JOptionPane.showMessageDialog(null, "�̹� �����ϴ� ��ǰ ���� �ڵ��Դϴ�.");
						typenum_t.setText("");
						typename_t.setText("");
						typenum_t.requestFocus();
					}
					else {
						String add_strSql;
						add_strSql = "INSERT INTO ��ǰ�з� VALUES('"+ code +"','"+ name +"');";
						dbSt.executeUpdate(add_strSql);
						JOptionPane.showMessageDialog(null, "�߰��Ǿ����ϴ�.");
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
		else if(s.equals("���")) {
			typenum_t.setText("");
			typename_t.setText("");
			typenum_t.requestFocus();
		}
	}
}