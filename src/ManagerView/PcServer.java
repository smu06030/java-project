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

	/*---------------------------- ���� ���� ------------------------------*/
	
	public void startServer() {
		try {
			serversocket = new ServerSocket();
			serversocket.bind(new InetSocketAddress(ip,port));
			
		}catch(Exception e) {
			e.printStackTrace();
			if(!serversocket.isClosed()) {
				stopServer(); // ���� �۵� ����
			}
			return;
		}
		
		Thread thread = new Thread() {
			public void run() {
				while(true) {
					try {
						System.out.println("��ٸ��� ��....");
						socket = serversocket.accept();
						System.out.println("���� �Ϸ�!");
	
						clients.add(new PcServer_client(socket));
						
					}catch(Exception e) { // ���� �߻�
						if(!serversocket.isClosed()) {
							stopServer(); // ���� �۵� ����
						}
						break;
					}
				}
			}
		};
		thread.start();
	}
	
	/*---------------------------- ���� ���� ------------------------------*/
	
	public void stopServer() {
		try {
			Iterator<PcServer_client> iterator = clients.iterator();
			while(iterator.hasNext()) { // �Ѹ� ���� �� �� �ֵ��� ���ش�. hasNext(�о� �� ��Ұ� ���� �ִ��� Ȯ���ϴ� �޼ҵ�) ��ȯ �� : true, false
				PcServer_client client = iterator.next();
				client.socket.close(); // �� Ŭ���̾�Ʈ�� ������ �ݾ� ������.
				iterator.remove(); // �׸��� iterator������ �ش� ������ ���� Ŭ���̾�Ʈ�� �������ش�. remove(next�� �о�� ��Ҹ� �����Ѵ�)
			}
			// ���� ���� ��ü �ݱ�
			if(serversocket != null && !serversocket.isClosed()) {
				serversocket.close();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}

