package loginView;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class FindPage extends JFrame  {
	public FindPage() {
		Container ct = getContentPane();
		ct.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
		
		JTabbedPane pane = new JTabbedPane();

		pane.setPreferredSize(new Dimension(400,300));
		pane.setFont(new Font("",Font.BOLD,20));
		
		pane.addTab("아이디", new ID());
		pane.addTab("비밀번호", new Password());
		
		pane.setBackground(new Color(0xe6f2dd));
		
		ct.add(pane);
	}
}