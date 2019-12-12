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

      id_l = new JLabel("아이디              :");      name_l = new JLabel("이름                  :");
      pwd_l = new JLabel("비밀번호          :");      Re_pwd_l = new JLabel("비밀번호 확인 :");
      Re_pwd_warn = new JLabel("비밀번호가 같지않습니다.");   position_l = new JLabel("직급                  :");
      ButtonGroup group = new ButtonGroup();
      ceo = new JRadioButton("사장");
      staff = new JRadioButton("직원");
      group.add(ceo);   group.add(staff);

      id_t = new JTextField(20);      name_t = new JTextField(20);
      pwd_t = new JPasswordField(20);      Re_pwd_t = new JPasswordField(20);

      id_chck = new JButton("아이디 중복 체크");
      save = new JButton("저장");
      cancel = new JButton("취소");

      id_chck.setFont(font);
      Re_pwd_warn.setFont(font);
      Re_pwd_warn.setForeground(Color.RED);
      Re_pwd_warn.setVisible(false);

      id_chck.addActionListener(this);
      save.addActionListener(this);
      cancel.addActionListener(this);
      pwd_t.addActionListener(this);

      //아이디 중복체크 전까지 비활성화
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
      //<------------- 아이디 중복 체크 ------------->
      if(str.equals("아이디 중복 체크")) {
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
            String id = id_t.getText();

            strSql="SELECT * FROM 관리자 WHERE 아이디='"+ id +"';";
            ResultSet result = dbSt.executeQuery(strSql);
            if(result.next()) {    //result값이 있으면 result.next()는 true
               JOptionPane.showMessageDialog(null, "이미 있는 ID입니다.");
               id_t.setText("");
               id_t.requestFocus();
            }
            else {
               JOptionPane.showMessageDialog(null, "사용 가능한 ID입니다.");
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

      //<------------- 저장 ------------->
      else if(str.equals("저장")) {
         String pwd1 = new String(pwd_t.getPassword());
         String pwd2 = new String(Re_pwd_t.getPassword());
         //"비밀번호"와 "비밀번호 확인"에 있는 값이 같지 않은 경우
         if(!pwd2.equals(pwd1)) {
            Re_pwd_t.setText("");
            Re_pwd_t.requestFocus();
            //다르다는 경고문
            Re_pwd_warn.setVisible(true);
         }
         else {
            try {
               Connection con = DriverManager.getConnection(url,user,pw);
               Statement dbSt = con.createStatement();
               String strSql;   
               String id = id_t.getText();      
               String name = name_t.getText();

               //사장을 선택했으면 관리자 데베에 0 저장, 직원을 선택했으면 관리자 데베에 1 저장
               int position = 0;
               if(ceo.isSelected())
                  ;
               else if(staff.isSelected())
                  position = 1;

               strSql="INSERT INTO 관리자 VALUES('"+ id +"','"+ pwd1 +"',"+ position +");";
               dbSt.executeUpdate(strSql);
               //경고문을 바꿔준다.
               Re_pwd_warn.setText("비밀번호가 같습니다.");
               Re_pwd_warn.setForeground(Color.GREEN);
               JOptionPane.showMessageDialog(null, "회원가입 되었습니다.");
               dispose();
               dbSt.close();
               con.close();
            }catch(SQLException e1) {
               System.out.println("SQLException : "+e1.getMessage());
            }
         }   
      }

      //<------------- 취소 ------------->
      else if(str.equals("취소")) {
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
