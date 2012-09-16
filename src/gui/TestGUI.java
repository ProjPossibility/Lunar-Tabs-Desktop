package gui;
import java.awt.*;
import javax.swing.*;

public class TestGUI extends JFrame implements Runnable {
	
	private JTextField field;
	
	public TestGUI() {
		field = new JTextField(10);
		field.setEditable(false);
		add(field);		
		setSize(100,100);
		setVisible(true);
	}
	
	public void run() {
		try {
			while(true) {
				field.setText("Hello\nPrateek");
				Thread.sleep(2000);
				field.setText("World\nNewb");
				Thread.sleep(2000);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Thread t = new Thread(new TestGUI());
		t.start();		
	}
	
}
