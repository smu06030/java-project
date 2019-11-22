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
	
	/*---------------------------- pc번호 Vector ------------------------------*/
	
	public static Vector<Integer> random = new Vector<Integer>(10);
	
	/*---------------------------- 클라이언트로부터 메세지를 받는 곳 ------------------------------*/
	
	public void receive() {
		Thread thread = new Thread() {
			public void run() {
					try {
						while(true) {
							in = new DataInputStream(socket.getInputStream());
							
							String msg = in.readUTF(); // 아이디 
							int pc = in.readInt(); // pc 번호를 받아
							System.out.println("client 받은 메세지 : "+msg);
							System.out.println("client 받은 Pc번호: "+pc);
							if(msg.equals("종료")) {
								for(int i = 0; i<random.size();i++) {
									if(random.get(i) == pc) {
										random.remove(i);
									}
								}
								System.out.println("지울 PC번호 : "+pc);
								gui.insert(msg,pc); // 받아온 아이디, pc번호를 server_main으로 넘긴다.
							}else {
								for(int i = 0;i<random.size();i++) {
									if(random.get(i) == pc) {
										// 만약 받아온 값이 중복이 된다면 다시 요청한다.
										send("랜덤재요청",pc);
										randCheck = 1; // 그리고 변수 값을 1로 바꾼다.
										break;
									}
									randCheck = 0; // 중복이 없다면 변수 값을 0 으로 바꾼다.
								}
								if(randCheck == 0) {
									random.add(pc);	
									for(int i = 0;i<random.size();i++)
										System.out.println("현재 실행중인 pc 번호 : "+random.get(i));
			
									gui.insert(msg,pc);  // 받아온 아이디, pc번호를 server_main으로 넘긴다.
								}
							}
						}
					}catch(IOException e) {
						try {
							System.out.println("[메세지 수신 오류]");
							socket.close();
						}catch(Exception e2) {
							e2.printStackTrace();
						}
					}
			}
		};
		thread.start();
	}
	
	/*---------------------------- 클라이언트로 메세지를 보내는 곳 ------------------------------*/
	
	public void send(String msg, int intmsg) {
		Thread thread = new Thread() {
			public void run() {
				try {
					out = new DataOutputStream(socket.getOutputStream());
					out.writeUTF(msg);
					out.writeInt(intmsg);
					
				}catch(Exception e) {
					try {
						// 모든 클라이언트 들의 정보를 가지고 있는 clients에 현재 클라이언트를 지워준다.
						PcServer.clients.remove(PcServer_client.this);
						// 그리고 소켓 종료
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
