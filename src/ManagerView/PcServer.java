package ManagerView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PcServer {
	public static Vector<PcServer_client> clients = new Vector<PcServer_client>();
	
	private String ip = "127.0.0.1";
	private int port = 9999;
	private ServerSocket serversocket;
	private Socket socket;

	/*---------------------------- 서버 시작 ------------------------------*/
	
	public void startServer() {
		try {
			serversocket = new ServerSocket();
			serversocket.bind(new InetSocketAddress(ip,port));
			
		}catch(Exception e) {
			e.printStackTrace();
			if(!serversocket.isClosed()) {
				stopServer(); // 서버 작동 중지
			}
			return;
		}
		
		Thread thread = new Thread() {
			public void run() {
				while(true) {
					try {
						System.out.println("기다리는 중....");
						socket = serversocket.accept();
						System.out.println("연결 완료!");
	
						clients.add(new PcServer_client(socket));
						
					}catch(Exception e) { // 오류 발생
						if(!serversocket.isClosed()) {
							stopServer(); // 서버 작동 중지
						}
						break;
					}
				}
			}
		};
		thread.start();
	}
	
	/*---------------------------- 서버 종료 ------------------------------*/
	
	public void stopServer() {
		try {
			Iterator<PcServer_client> iterator = clients.iterator();
			while(iterator.hasNext()) { // 한명씩 접근 할 수 있도록 해준다. hasNext(읽어 올 요소가 남아 있는지 확인하는 메소드) 반환 값 : true, false
				PcServer_client client = iterator.next();
				client.socket.close(); // 그 클라이언트에 소켓을 닫아 버린다.
				iterator.remove(); // 그리고 iterator에서도 해당 연결이 끊긴 클라이언트를 제거해준다. remove(next로 읽어온 요소를 삭제한다)
			}
			// 서버 소켓 객체 닫기
			if(serversocket != null && !serversocket.isClosed()) {
				serversocket.close();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}

