import java.awt.BorderLayout;
import java.io.DataOutputStream;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CL {

 static String Name;

 static JTextArea ta;
 static JTextField tf;
	


  public class Edge extends JFrame{ //클라이언트 채팅방
	
	public Edge(){
		
		setSize(1000,1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("클라이언트");
		
		JLabel label = new JLabel("시작");
		
		
		setVisible(true);
	}
	  
  }	
}