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
    // ���̾�α׸� ��Ÿ���� Ŭ����
	JButton ok;
	public MessageDialog(JFrame p, String title, boolean mode, String msg)
	{    // ������ �޼ҵ带 �̿��Ͽ� ���̾�α� �ڽ� ����
		super(p, title, mode);   //   JDialog(parent, title, mode); �͵���
		JPanel pc = new JPanel();
		JLabel label = new JLabel(msg);
		pc.add(label);
		add(pc, BorderLayout.CENTER);// Panel�� �����Ͽ� ���̺��� �߰�
		JPanel ps = new JPanel();
		ok = new JButton("OK");
		ok.addActionListener(this);
		ps.add(ok);
		add(ps, BorderLayout.SOUTH);  // Panel�� �����Ͽ� ��ư�� �߰�
		pack();   // ������Ʈ�� ��ġ�ϰ� ���̾�α� �ڽ��� �ʱ� ũ�⸦ ����
		setLocationRelativeTo(getParent());
		setVisible(true);
		
	}
	public void actionPerformed(ActionEvent ae) {
		dispose();     // ok ��ư Ŭ���ϸ� â�ݱ�
	}

}  // MessageDialog Ŭ���� ��
