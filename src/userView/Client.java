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
	
	// 현재 pc 번호
	int pcNumber = 0;
	private String name;
	private String ids;
	 
	data_check checking = new data_check();
	int random = checking.getSeatNum();
	
	public Client(){
		
	}
	
	/*---------------------------- 클라이언트 시작 ------------------------------*/
	
	public void startClient(String ip, int port, String id) {
		Thread thread = new Thread() {
			public void run() {
				try {
					socket = new Socket(ip,port);
					System.out.println("서버 접속 완료");
					
					// 받아온 아이디의 정보를 클라이언트가 저장한다.
					ids = id;
						
					in = new DataInputStream(socket.getInputStream());
					out = new DataOutputStream(socket.getOutputStream());
					// 받아온 pc 번호를 현재 클라이언트에 저장한다.
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
	
	/*------------------------------ 클라이언트 종료 --------------------------------*/
	
	public void stopClient() {
		try {
			if(socket != null && !socket.isClosed()) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*---------------------------- 서버로 메세지를 받는 곳 ------------------------------*/
	
	public void receive() {
		Thread thread = new Thread() {
			public void run() {
				while(true) {
					try {
						in = new DataInputStream(socket.getInputStream());	
						String msg = in.readUTF();
						int intmsg = in.readInt();
						
						if(msg.equals("랜덤재요청")) {
							intmsg = (int)(Math.random()*random)+1;
							pcNumber = intmsg;
							System.out.println("client pc에 저장된 번호 : "+pcNumber);
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
	
	/*---------------------------- 서버에서 메세지를 전송하는 곳 ------------------------------*/
	
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
