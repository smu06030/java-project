package ManagerView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PcServer_client extends JFrame {
	
	private DataInputStream in;
	private DataOutputStream out;
	private PcServer_Main gui;
	private int randCheck = 0; 
	
	Socket socket;
	PcServer_client(Socket socket){
		this.socket = socket;
		
		receive();
	}
	
	/*---------------------------- pc��ȣ Vector ------------------------------*/
	
	public static Vector<Integer> random = new Vector<Integer>(10);
	
	/*---------------------------- Ŭ���̾�Ʈ�κ��� �޼����� �޴� �� ------------------------------*/
	
	public void receive() {
		Thread thread = new Thread() {
			public void run() {
					try {
						while(true) {
							in = new DataInputStream(socket.getInputStream());
							
							String msg = in.readUTF(); // ���̵� 
							int pc = in.readInt(); // pc ��ȣ�� �޾�
							System.out.println("client ���� �޼��� : "+msg);
							System.out.println("client ���� Pc��ȣ: "+pc);
							if(msg.equals("����")) {
								for(int i = 0; i<random.size();i++) {
									if(random.get(i) == pc) {
										random.remove(i);
									}
								}
								System.out.println("���� PC��ȣ : "+pc);
								gui.insert(msg,pc); // �޾ƿ� ���̵�, pc��ȣ�� server_main���� �ѱ��.
							}else {
								for(int i = 0;i<random.size();i++) {
									if(random.get(i) == pc) {
										// ���� �޾ƿ� ���� �ߺ��� �ȴٸ� �ٽ� ��û�Ѵ�.
										send("�������û",pc);
										randCheck = 1; // �׸��� ���� ���� 1�� �ٲ۴�.
										break;
									}
									randCheck = 0; // �ߺ��� ���ٸ� ���� ���� 0 ���� �ٲ۴�.
								}
								if(randCheck == 0) {
									random.add(pc);	
									for(int i = 0;i<random.size();i++)
										System.out.println("���� �������� pc ��ȣ : "+random.get(i));
			
									gui.insert(msg,pc);  // �޾ƿ� ���̵�, pc��ȣ�� server_main���� �ѱ��.
								}
							}
						}
					}catch(IOException e) {
						try {
							System.out.println("[�޼��� ���� ����]");
							socket.close();
						}catch(Exception e2) {
							e2.printStackTrace();
						}
					}
			}
		};
		thread.start();
	}
	
	/*---------------------------- Ŭ���̾�Ʈ�� �޼����� ������ �� ------------------------------*/
	
	public void send(String msg, int intmsg) {
		Thread thread = new Thread() {
			public void run() {
				try {
					out = new DataOutputStream(socket.getOutputStream());
					out.writeUTF(msg);
					out.writeInt(intmsg);
					
				}catch(Exception e) {
					try {
						// ��� Ŭ���̾�Ʈ ���� ������ ������ �ִ� clients�� ���� Ŭ���̾�Ʈ�� �����ش�.
						PcServer.clients.remove(PcServer_client.this);
						// �׸��� ���� ����
						socket.close();
					}catch(Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		};
		thread.start();	
	}
}
