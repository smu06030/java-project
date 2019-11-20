package userView;

import java.awt.Container;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JTextField;

import DBCheck.data_check;
import ManagerView.PcServer_Main;

public class Client {

	Socket socket;
	Container ct;
	DataInputStream in;
	DataOutputStream out;
	
	// ���� pc ��ȣ
	int pcNumber = 0;
	String name;
	String ids;
	
	PcServer_Main pcTest;
	data_check checking;
	
	Client(){
		
	}
	public void startClient(String ip, int port,int rand, String id) {
		Thread thread = new Thread() {
			public void run() {
				try {
					socket = new Socket(ip,port);
					System.out.println("���� ���� �Ϸ�");
					
					// �޾ƿ� ���̵��� ������ Ŭ���̾�Ʈ�� �����Ѵ�.
					ids = id;
					// data_check ��ü ����
					checking = new data_check();
					
					try {// �޾ƿ� id�� ȸ��DB�� �̸� ��������
						name = checking.id_search(id);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					in = new DataInputStream(socket.getInputStream());
					out = new DataOutputStream(socket.getOutputStream());
					// �޾ƿ� pc ��ȣ�� ���� Ŭ���̾�Ʈ�� �����Ѵ�.
					pcNumber = rand;
					
					out.writeUTF("<html>id : "+id+"<br>�̸� : "+name+"</html>");
					out.writeInt(rand);
					receive();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}
	public void stopClient() {
		try {
			if(socket != null && !socket.isClosed()) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void receive() {
		Thread thread = new Thread() {
			public void run() {
				while(true) {
					try {
						in = new DataInputStream(socket.getInputStream());	
						String msg = in.readUTF();
						int intmsg = in.readInt();
						
						if(msg.equals("�������û")) {
							intmsg = (int)(Math.random()*10)+1;
							pcNumber = intmsg;
							System.out.println("client pc�� ����� ��ȣ : "+pcNumber);
							send("<html>id : "+ids+"<br>�̸� : "+name+"</html>",pcNumber);
						}
											
					}catch(Exception e) {
						stopClient();
					}
				}
			}
		};
		thread.start();
	}
	
	public void send(String msg, int intmsg) {
		try {
			out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF(msg);
			out.writeInt(intmsg);
						
		}catch(Exception e) {
			stopClient();
		}
	}

	public static void main(String[] args) {
		Client pc = new Client();
	}
}
