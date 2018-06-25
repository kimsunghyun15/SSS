
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;



class FrServer extends JFrame {
	JTextArea ta;
	JTextField tf;
	
	public FrServer() {
		setBounds(300,100,600,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("ä��â ����!");
		
		JLabel label = new JLabel("�غ��� ����~����~");              //���� ä�ù� ������
		ta = new JTextArea(25, 40);
		tf = new JTextField(25);
		
		add (label, BorderLayout.NORTH);
		add (ta, BorderLayout.CENTER);
		add (tf, BorderLayout.SOUTH);
		
		setVisible(true);
	}
}

public class Server {
	
	HashMap clients; //HashMap�� Map�� �����Ѵ�. Key�� value�� ���� �ϳ��� entry�� �����Ѵٴ� Ư¡�� ���´�. �׸��� hashing�� ����ϱ� ������ �������� �����͸� �˻��ϴµ� ����


	
	Server(){
		clients = new HashMap();
		Collections.synchronizedMap(clients);
	}
	
	public static void main(String[] args) {
		FrServer f = new FrServer();
		new 
		Server().start();
	}
	public void start() {
		ServerSocket serverSocket = null;
		Socket socket = null;
		
		try {
			serverSocket = new ServerSocket(7777);  //(���� �ּ� 7777�� �����ѰŶ� ����)
			System.out.println("������ ���۵Ǿ����ϴ�.");
			
			while(true) {
				socket = serverSocket.accept();
				System.out.println("[" + socket.getInetAddress() + "]" + "���� �����ϼ̽��ϴ�.");  //������ �ȿ� ����� �ְ� �׾ȿ� ������ ����?
				// ���� ���ù��� �������� �̸����� ����
				ServerReceiver thread = new ServerReceiver(socket);
				thread.start();
				
				System.out.println("������ ���� : " +thread.getName());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}//start
	
	void sendToAll(String msg) {
		Iterator it = clients.keySet().iterator();
		
		while(it.hasNext()) {
			try {
				DataOutputStream out = (DataOutputStream)clients.get(it.next());
				out.writeUTF(msg);
			} catch (IOException e) {
				System.out.println("sendToAll ����� ����");
			}
		} // while
	} // sendToAll
	
	class ServerReceiver extends Thread {
		Socket socket;
		DataInputStream in;
		DataOutputStream out;
		
		ServerReceiver(Socket socket){
			this.socket = socket;
			
			try {
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				System.out.println("���� ���ù� ���� IO ����");
			}
		}// ������ ������
		
		public void run() {
			String name ="";
			
			try {
				name = in.readUTF();
				sendToAll("#" + name + "���� �����̽��ϴ�.");
				
				clients.put(name, out);
				System.out.println("���� ������ ����" + clients.size() +"�Դϴ�");
				System.out.println("���� ������ ��� : ");
				
				while(in != null) {
					sendToAll(in.readUTF());
				}
			} catch (IOException e) {
				System.out.println("���ù� ���� IO ���� �߻�");
			} finally {
				sendToAll("#" + name + "���� �����ϼ̽��ϴ�.");
				clients.remove(name);
				System.out.println("[" + socket.getInetAddress() + " : " + socket.getPort() + "] ���� ������ �����߽��ϴ�.");
				System.out.println("���� ������ ����" + clients.size() + "�Դϴ�");
			} // try
		} // run
	} // ReceiverThread
} // Class
