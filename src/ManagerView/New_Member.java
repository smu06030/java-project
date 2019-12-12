package ManagerView;

import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout.Group;

import java.awt.event.*;
import java.sql.*;

class New_Member extends JFrame implements ActionListener{
   String url = "jdbc:mysql://localhost:3306/pcbang?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true&useSSL=false";
   String user = "root";
   String pw = "dlscjf158!A";

   JPanel id_panel, name_panel, pwd_panel, Re_pwd_panel, Re_pwd_warn_panel, position_panel, btn_panel;
   JPanel tot_panel;
   JLabel id_l, name_l, pwd_l, Re_pwd_l, position_l, Re_pwd_warn;
   JTextField  id_t, name_t, position_t;
   JPasswordField pwd_t, Re_pwd_t;
   JRadioButton ceo, staff;
   JButton id_chck, save, cancel;

   New_Member(String title){
      Container ct = getContentPane();
      ct.setLayout(new FlowLayout(FlowLayout.CENTER,90,10));
      setTitle(title);
      Font font = new Font("",Font.PLAIN,10);

      id_panel = new JPanel(new FlowLayout(FlowLayout.LEFT));   name_panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
      pwd_panel = new JPanel(new FlowLayout(FlowLayout.LEFT));   Re_pwd_panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
      Re_pwd_warn_panel = new JPanel(new FlowLayout(FlowLayout.LEFT));   position_panel = new JPanel(new FlowLayout(FlowLayout.LEFT));   
      btn_panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

      tot_panel = new JPanel();
      tot_panel.setLayout(new FlowLayout(FlowLayout.CENTER,0,10));
      tot_panel.setPreferredSize(new Dimension(500,500));

      id_l = new JLabel("���̵�              :");      name_l = new JLabel("�̸�                  :");
      pwd_l = new JLabel("��й�ȣ          :");      Re_pwd_l = new JLabel("��й�ȣ Ȯ�� :");
      Re_pwd_warn = new JLabel("��й�ȣ�� �����ʽ��ϴ�.");   position_l = new JLabel("����                  :");
      ButtonGroup group = new ButtonGroup();
      ceo = new JRadioButton("����");
      staff = new JRadioButton("����");
      group.add(ceo);   group.add(staff);

      id_t = new JTextField(20);      name_t = new JTextField(20);
      pwd_t = new JPasswordField(20);      Re_pwd_t = new JPasswordField(20);

      id_chck = new JButton("���̵� �ߺ� üũ");
      save = new JButton("����");
      cancel = new JButton("���");

      id_chck.setFont(font);
      Re_pwd_warn.setFont(font);
      Re_pwd_warn.setForeground(Color.RED);
      Re_pwd_warn.setVisible(false);

      id_chck.addActionListener(this);
      save.addActionListener(this);
      cancel.addActionListener(this);
      pwd_t.addActionListener(this);

      //���̵� �ߺ�üũ ������ ��Ȱ��ȭ
      name_t.setEnabled(false);
      pwd_t.setEnabled(false);
      Re_pwd_t.setEnabled(false);
      ceo.setEnabled(false);
      staff.setEnabled(false);
      save.setEnabled(false);
      cancel.setEnabled(false);

      id_panel.setPreferredSize(new Dimension(450,35));
      name_panel.setPreferredSize(new Dimension(450,35));
      pwd_panel.setPreferredSize(new Dimension(450,35));
      Re_pwd_panel.setPreferredSize(new Dimension(500,35));
      Re_pwd_l.setBorder(BorderFactory.createEmptyBorder(0,23,0,0));
      position_panel.setPreferredSize(new Dimension(450,35));
      btn_panel.setPreferredSize(new Dimension(450,35));


      id_panel.add(id_l,FlowLayout.LEFT);   id_panel.add(id_t);   id_panel.add(id_chck);
      name_panel.add(name_l);   name_panel.add(name_t);
      pwd_panel.add(pwd_l);   pwd_panel.add(pwd_t);
      Re_pwd_warn_panel.add(Re_pwd_warn);
      Re_pwd_panel.add(Re_pwd_l);   Re_pwd_panel.add(Re_pwd_t);   Re_pwd_panel.add(Re_pwd_warn_panel);
      position_panel.add(position_l);   position_panel.add(ceo);   position_panel.add(staff);
      btn_panel.add(save);   btn_panel.add(cancel);

      tot_panel.add(id_panel);
      tot_panel.add(name_panel);
      tot_panel.add(pwd_panel);
      tot_panel.add(Re_pwd_panel);
      tot_panel.add(position_panel);
      tot_panel.add(btn_panel);

      ct.add(tot_panel);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      String str = e.getActionCommand();
      //<------------- ���̵� �ߺ� üũ ------------->
      if(str.equals("���̵� �ߺ� üũ")) {
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
            String id = id_t.getText();

            strSql="SELECT * FROM ������ WHERE ���̵�='"+ id +"';";
            ResultSet result = dbSt.executeQuery(strSql);
            if(result.next()) {    //result���� ������ result.next()�� true
               JOptionPane.showMessageDialog(null, "�̹� �ִ� ID�Դϴ�.");
               id_t.setText("");
               id_t.requestFocus();
            }
            else {
               JOptionPane.showMessageDialog(null, "��� ������ ID�Դϴ�.");
               id_t.setEnabled(false);
               name_t.setEnabled(true);
               pwd_t.setEnabled(true);
               Re_pwd_t.setEnabled(true);
               ceo.setEnabled(true);
               staff.setEnabled(true);
               save.setEnabled(true);
               cancel.setEnabled(true);
            }
            dbSt.close();
            con.close();
         }catch(SQLException e1) {
            System.out.println("SQLException : "+e1.getMessage());
         }
      }

      //<------------- ���� ------------->
      else if(str.equals("����")) {
         String pwd1 = new String(pwd_t.getPassword());
         String pwd2 = new String(Re_pwd_t.getPassword());
         //"��й�ȣ"�� "��й�ȣ Ȯ��"�� �ִ� ���� ���� ���� ���
         if(!pwd2.equals(pwd1)) {
            Re_pwd_t.setText("");
            Re_pwd_t.requestFocus();
            //�ٸ��ٴ� ���
            Re_pwd_warn.setVisible(true);
         }
         else {
            try {
               Connection con = DriverManager.getConnection(url,user,pw);
               Statement dbSt = con.createStatement();
               String strSql;   
               String id = id_t.getText();      
               String name = name_t.getText();

               //������ ���������� ������ ������ 0 ����, ������ ���������� ������ ������ 1 ����
               int position = 0;
               if(ceo.isSelected())
                  ;
               else if(staff.isSelected())
                  position = 1;

               strSql="INSERT INTO ������ VALUES('"+ id +"','"+ pwd1 +"',"+ position +");";
               dbSt.executeUpdate(strSql);
               //����� �ٲ��ش�.
               Re_pwd_warn.setText("��й�ȣ�� �����ϴ�.");
               Re_pwd_warn.setForeground(Color.GREEN);
               JOptionPane.showMessageDialog(null, "ȸ������ �Ǿ����ϴ�.");
               dispose();
               dbSt.close();
               con.close();
            }catch(SQLException e1) {
               System.out.println("SQLException : "+e1.getMessage());
            }
         }   
      }

      //<------------- ��� ------------->
      else if(str.equals("���")) {
         id_t.setText("");
         name_t.setText("");
         pwd_t.setText("");
         Re_pwd_t.setText("");
         Re_pwd_warn.setVisible(false);
         ceo.setSelected(true);

         id_t.setEnabled(true);
         name_t.setEnabled(false);
         pwd_t.setEnabled(false);
         Re_pwd_t.setEnabled(false);
         ceo.setEnabled(false);
         staff.setEnabled(false);
         save.setEnabled(false);
         cancel.setEnabled(false);
      }
   }
}
