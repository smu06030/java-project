package userView;

import java.awt.Container;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import DBCheck.data_check;

public class Client {

	Socket socket;
	private Container ct;
	private DataInputStream in;
	private DataOutputStream out;
	
	// ���� pc ��ȣ
	int pcNumber = 0;
	private String name;
	private String ids;
	 
	data_check checking = new data_check();
	int random = checking.getSeatNum();
	
	public Client(){
		
	}
	
	/*---------------------------- Ŭ���̾�Ʈ ���� ------------------------------*/
	
	public void startClient(String ip, int port, String id) {
		Thread thread = new Thread() {
			public void run() {
				try {
					socket = new Socket(ip,port);
					System.out.println("���� ���� �Ϸ�");
					
					// �޾ƿ� ���̵��� ������ Ŭ���̾�Ʈ�� �����Ѵ�.
					ids = id;
						
					in = new DataInputStream(socket.getInputStream());
					out = new DataOutputStream(socket.getOutputStream());
					// �޾ƿ� pc ��ȣ�� ���� Ŭ���̾�Ʈ�� �����Ѵ�.
					int rand = (int)(Math.random()*random)+1;
					pcNumber = rand;
					
					out.writeUTF(id);
					out.writeInt(rand);
					receive();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}
	
	/*------------------------------ Ŭ���̾�Ʈ ���� --------------------------------*/
	
	public void stopClient() {
		try {
			if(socket != null && !socket.isClosed()) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*---------------------------- ������ �޼����� �޴� �� ------------------------------*/
	
	public void receive() {
		Thread thread = new Thread() {
			public void run() {
				while(true) {
					try {
						in = new DataInputStream(socket.getInputStream());	
						String msg = in.readUTF();
						int intmsg = in.readInt();
						
						if(msg.equals("�������û")) {
							intmsg = (int)(Math.random()*random)+1;
							pcNumber = intmsg;
							System.out.println("client pc�� ����� ��ȣ : "+pcNumber);
							send(ids,pcNumber);
						}
											
					}catch(Exception e) {
						stopClient();
					}
				}
			}
		};
		thread.start();
	}
	
	/*---------------------------- �������� �޼����� �����ϴ� �� ------------------------------*/
	
	public void send(String msg, int intmsg) {
		try {
			out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF(msg);
			out.writeInt(intmsg);			
		}catch(Exception e) {
			stopClient();
			System.out.println(e.getMessage());
		}
	}
}
