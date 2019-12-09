package doyeon;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MessageDialog extends JDialog implements ActionListener {
    // 다이어로그를 나타내는 클래스
	JButton ok;
	public MessageDialog(JFrame p, String title, boolean mode, String msg)
	{    // 생성자 메소드를 이용하여 다이어로그 박스 구성
		super(p, title, mode);   //   JDialog(parent, title, mode); 와동일
		JPanel pc = new JPanel();
		JLabel label = new JLabel(msg);
		pc.add(label);
		add(pc, BorderLayout.CENTER);// Panel을 생성하여 레이블을 추가
		JPanel ps = new JPanel();
		ok = new JButton("OK");
		ok.addActionListener(this);
		ps.add(ok);
		add(ps, BorderLayout.SOUTH);  // Panel을 생성하여 버튼을 추가
		pack();   // 컴포넌트를 배치하고 다이어로그 박스의 초기 크기를 설정
		setLocationRelativeTo(getParent());
		setVisible(true);
		
	}
	public void actionPerformed(ActionEvent ae) {
		dispose();     // ok 버튼 클릭하면 창닫기
	}

}  // MessageDialog 클래스 끝
