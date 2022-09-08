package Main;

import java.awt.EventQueue;


import javax.swing.JFrame;



public class Main extends JFrame {
	public Main() {
		 add(new GamePanel()); // noi dung
	      pack();  // di kem voi setPreferredSize(); tao khung
	      setResizable(false);
	      setTitle("Magic Cat");
	      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      setLocationRelativeTo(null);
	}
	
	 public static void main(String[] args) {
		   EventQueue.invokeLater(()->{
			   Main main = new Main();
			   main.setVisible(true);
		   });
	   }



}