package ManagerView;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

class M_Login extends JFrame implements ActionListener{
	String url = "jdbc:mysql://localhost:3306/pcbang?serverTimezone=UTC";
	String user = "root";
	String pw = "dlscjf158!A";

   JLabel Id_l, Password_l, Position_l;
   JTextField Id_t;
   JPasswordField Password_t;
   JButton Login_btn, cancel_btn, SignUp_btn;
   ButtonGroup group;
   JRadioButton ceo, staff;

   JPanel Id_panel, Password_panel, Position_panel, btn_panel;
   Font font;

   M_Login(){
      Container ct = getContentPane();
      ct.setLayout(new FlowLayout(FlowLayout.CENTER));

      Id_l = new JLabel("���̵�      : ");
      Id_t = new JTextField(12);
      Password_l = new JLabel("��й�ȣ  : ");
      Password_t = new JPasswordField(12);
      Position_l = new JLabel("����    : ");
      group = new ButtonGroup();
      ceo = new JRadioButton("����");
      staff = new JRadioButton("����");
      group.add(ceo);      group.add(staff);

      Login_btn = new JButton("�α���");
      Login_btn.addActionListener(this);
      cancel_btn = new JButton("���");
      cancel_btn.addActionListener(this);
      SignUp_btn = new JButton("ȸ������");
      SignUp_btn.addActionListener(this);

      Id_panel = new JPanel();
      Password_panel = new JPanel();
      Position_panel = new JPanel();
      btn_panel = new JPanel();
      Id_panel.setPreferredSize(new Dimension(300,70));
      Password_panel.setPreferredSize(new Dimension(300,70));
      btn_panel.setPreferredSize(new Dimension(500,70));

      font = new Font("",Font.BOLD,20);
      Id_l.setFont(font);
      Password_l.setFont(font);
      Position_l.setFont(font);
      Login_btn.setFont(font);
      cancel_btn.setFont(font);
      SignUp_btn.setFont(font);
      ceo.setFont(font);
      staff.setFont(font);

      Id_panel.add(Id_l);
      Id_panel.add(Id_t);
      Password_panel.add(Password_l);
      Password_panel.add(Password_t);
      Position_panel.add(Position_l);
      Position_panel.add(ceo);
      Position_panel.add(staff);
      btn_panel.add(Login_btn);
      btn_panel.add(cancel_btn);
      btn_panel.add(SignUp_btn);

      Id_panel.setLayout(new FlowLayout(FlowLayout.CENTER,0,40));
      Password_panel.setLayout(new FlowLayout(FlowLayout.CENTER,0,25));
      Position_panel.setLayout(new FlowLayout(FlowLayout.CENTER,5,0));
      btn_panel.setLayout(new FlowLayout(FlowLayout.CENTER,20,30));

      this.add(Id_panel);
      this.add(Password_panel);
      this.add(Position_panel);
      this.add(btn_panel);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      String s = e.getActionCommand();

      //<------------- �α��� ------------->
      if(s.equals("�α���")) {
         try{
            Class.forName("com.mysql.jdbc.Driver");
            System.err.println("JDBC-ODBC ����̹��� ���������� �ε���");
         }catch(ClassNotFoundException e1) {
            System.err.println("����̹� �ε忡 �����߽��ϴ�.");
         }
         try {
            Connection con = DriverManager.getConnection(url,user,pw);
            Statement dbSt = con.createStatement();
            String strSQL;   
            String id = Id_t.getText();
            String password = new String(Password_t.getPassword());
            String position = null;
               if(ceo.isSelected()) position = "0";
               else if(staff.isSelected()) position = "1";

            strSQL = "SELECT * FROM ������ WHERE ���̵� = '"+ id +"' and ��й�ȣ = '"+ password +"' and ���� = '"+ position +"';";
            ResultSet result = dbSt.executeQuery(strSQL);
            if(result.next()) {
            	JOptionPane.showMessageDialog(null, "�α��� ����!");
            	PcServer_Main pc = new PcServer_Main(position);
        		pc.setVisible(true);
        		pc.setSize(1600,900);
        		pc.setTitle("������ ����");
        		pc.setLocationRelativeTo(null);
        		pc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        		
        		PcServer pcServer = new PcServer();
        		pcServer.startServer();
        		
        		this.dispose();
            }
            else {
            	JOptionPane.showMessageDialog(null, "�������� ���� ���̵��̰ų�, �߸��� ��й�ȣ�Դϴ�.");   
            }
            dbSt.close();
            con.close();
         }catch(SQLException e1) {
            System.out.println("SQLException : "+e1.getMessage());
         }
      }
      //<------------- ��� ------------->
      else if(s.equals("���")) {
         Id_t.setText("");
         Password_t.setText("");
         ceo.setSelected(true);
      }
      else if(s.equals("ȸ������")) {
         New_Member nm = new New_Member("ȸ������ â");
         nm.setTitle("ȸ������ â");
         nm.setSize(500, 400);
         nm.setLocationRelativeTo(null);
         nm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         nm.setVisible(true);
      }
   }
}
public class Manager_Login {
   public static void main(String[] args) {
      M_Login win = new M_Login();
      win.setTitle("������ �α��� â");
      win.setSize(500, 400);
      win.setLocationRelativeTo(null);
      win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      win.show();
   }
}