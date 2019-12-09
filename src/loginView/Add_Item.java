package loginView;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//ǰ�� �߰� Ŭ����
class Add_Item extends JFrame implements ActionListener{
	//String url = "jdbc:mysql://172.111.117.107:3306/pcbang?serverTimezone=UTC";
	String url = "jdbc:mysql://localhost:3306/pcbang?serverTimezone=UTC";
	String user = "root";
	String pw = "dlscjf158!A";
	
	Product_Management p = new Product_Management(); 
	JPanel top, bottom;
	JPanel kind_p, name_p, price_p, stock_p, img_p;
	JLabel kind_l, name_l, price_l, stock_l, img_l;
	JTextField name_t, price_t, stock_t, img_t;
	JComboBox kind_cb;
	JButton submit, cancel, add_img;

	Add_Item(String title){
		setTitle(title);
		Container ct_Add = getContentPane();
		ct_Add.setLayout(new BorderLayout());

		top = new JPanel(new FlowLayout(FlowLayout.CENTER,20,20));
		bottom = new JPanel();

		kind_p = new JPanel(new FlowLayout(FlowLayout.CENTER));
		name_p = new JPanel(new FlowLayout(FlowLayout.CENTER));
		price_p = new JPanel(new FlowLayout(FlowLayout.CENTER));
		stock_p = new JPanel(new FlowLayout(FlowLayout.CENTER));
		img_p = new JPanel(new FlowLayout(FlowLayout.CENTER));

		kind_l = new JLabel("ǰ�� ���� : ");
		name_l = new JLabel("ǰ�� : ");
		price_l = new JLabel("���� : ");
		stock_l = new JLabel("��� : ");
		img_l = new JLabel("�̹��� : ");
		name_t = new JTextField(10);
		price_t = new JTextField(10);
		stock_t = new JTextField(10);
		img_t = new JTextField(10);
		kind_cb = new JComboBox(p.V_kind);

		submit = new JButton("�߰�");
		submit.addActionListener(this);
		cancel = new JButton("���");
		cancel.addActionListener(this);
		add_img = new JButton("�̹��� �߰�");
		add_img.addActionListener(this);


		kind_p.add(kind_l);   kind_p.add(kind_cb);
		name_p.add(name_l);   name_p.add(name_t);
		price_p.add(price_l);   price_p.add(price_t);
		stock_p.add(stock_l);   stock_p.add(stock_t);
		img_p.add(img_l);	img_p.add(img_t);	img_p.add(add_img);

		top.add(kind_p);
		top.add(name_p);
		top.add(price_p);
		top.add(stock_p);
		top.add(img_p);

		bottom.add(submit);   bottom.add(cancel);

		ct_Add.add(top,BorderLayout.CENTER);
		ct_Add.add(bottom,BorderLayout.SOUTH);

	}
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		if(s.equals("�߰�")) {
			String kind = p.V_kind.get(kind_cb.getSelectedIndex());
			String type = null;
			String name = name_t.getText();
			String img = img_t.getText();
			String S_price = price_t.getText();
			String S_stock = stock_t.getText();
			int price = Integer.parseInt(S_price);
			int stock = Integer.parseInt(S_stock);
			if(name.equals("")||S_price.equals("")||S_stock.equals("")) {
				JOptionPane.showMessageDialog(null, "��ĭ�� ä���ּ���.");
			}
			else {
				try {
					Connection con = DriverManager.getConnection(url,user,pw);
					Statement dbSt = con.createStatement();

					String check_strSql;
					check_strSql = "SELECT * FROM ��ǰ WHERE ��ǰ�� = '"+ name +"';";
					ResultSet check_result = dbSt.executeQuery(check_strSql);

					if(check_result.next()) {
						JOptionPane.showMessageDialog(null, "�̹� �����ϴ� ��ǰ�� �Դϴ�.");
						name_t.setText("");
						name_t.requestFocus();
					}
					else {
						String get_kind;
						get_kind = "SELECT * FROM ��ǰ�з� WHERE �з��� = '"+ kind +"';";
						ResultSet get_result = dbSt.executeQuery(get_kind);
						if(get_result.next()) {
							type = get_result.getString("�з��ڵ�");	
						}
						String add_strSql;
						add_strSql = "INSERT INTO ��ǰ VALUES ('"+ name +"','"+ price +"','"+ stock +"','"+ img +"','"+ type +"');";
						dbSt.executeUpdate(add_strSql);
						JOptionPane.showMessageDialog(null, "�߰��Ǿ����ϴ�.");
					}

					dbSt.close();
					con.close();
				}catch(SQLException e1) {
					System.out.println("SQLException : "+e1.getMessage());
				}
			}	
		}
		else if(s.equals("���")) {
			kind_cb.setSelectedIndex(0);
			name_t.setText("");
			price_t.setText("");
			stock_t.setText("");
			img_t.setText("");
		}
		else if(s.equals("�̹��� �߰�")) {
			//������ ���� �Ͽ� ��ǻ�Ϳ��� �����ؼ� �̹����� ������� �Ѵ�.
			FileDialog fd = new FileDialog(this, "�̹��� �߰�", FileDialog.LOAD);
			fd.setVisible(true);
			//����� �̹����� �̸��� name������ �����ϰ� ȭ�鿡 �����ش�.
			String name = fd.getFile();
			img_t.setText(name);
		}

	}
}
