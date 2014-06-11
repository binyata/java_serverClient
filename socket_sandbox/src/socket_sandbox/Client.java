package socket_sandbox;

import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;




import java.net.ServerSocket;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.net.*;
import java.io.*;

public class Client extends JFrame {
	
	
	
	
	// Text Field for receiving radius
	
	private JTextField jtf = new JTextField();
	
	// text area to display contents
	private JTextArea jta = new JTextArea();
	
	
	// IO streams
	
	private DataOutputStream toServer;
	private DataInputStream fromServer;
	
	
	public static void main(String[] args){
		
		new Client();
	}
	
	
	public Client() {
		
		// Panel p to hold the label and text field
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(new JLabel("Enter radius"), BorderLayout.WEST);
		p.add(jtf, BorderLayout.CENTER);
		jtf.setHorizontalAlignment(JTextField.RIGHT);
		
		setLayout(new BorderLayout());
		add(p, BorderLayout.NORTH);
		add(new JScrollPane(jta), BorderLayout.CENTER);
		
		jtf.addActionListener(new TextFieldListener());
		
		setTitle("Client");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true); //yep
		
		try {
			
			//create a socket to connect toe the server
			Socket socket = new Socket("localhost", 8000);
			// or "130.254.204.33"
			// or "liang.armstron.edu"
			
			//create an input stream to receive data from the server
			fromServer = new DataInputStream(socket.getInputStream());
			
			//create and output stream to send data to the server
			toServer = 
					new DataOutputStream(socket.getOutputStream());
			
		}
		
		catch(IOException ex) {
			jta.append(ex.toString() + '\n');
			
		}
	}
		
		private class TextFieldListener implements ActionListener {
			
			@Override
			public void actionPerformed(ActionEvent e){
				try {
					// get the radius from the text field
					double radius = Double.parseDouble(jtf.getText().trim());
					
					//Send the radius to the server
					toServer.writeDouble(radius);
					toServer.flush();
					
					// get area from the server
					double area = fromServer.readDouble();
					
					// Display to the text area
					jta.append("Radius is " + radius + "\n");
					jta.append("Area received from the server is " + area + '\n');
					
					
					
					
				}
				catch(IOException ex){
					
					System.err.println(ex);
				}
				
			}
			
			
		}
	
}
	

