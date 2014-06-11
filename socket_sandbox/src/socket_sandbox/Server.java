package socket_sandbox;

import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.net.*;
import java.io.*;

import javax.swing.JScrollPane;

public class Server extends JFrame {
	
	//text area for displaying contents
	
	private JTextArea jta = new JTextArea();
	
	public static void main(String[] args){
		
		
		new Server();
	}
	
	
	public Server() {
		//Place text area on the frame
		
		
		setLayout(new BorderLayout());
		add(new JScrollPane(jta), BorderLayout.CENTER);
		
		
		setTitle("Server");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true); //it is necessary to show the frame
	
	
	try {
		
		
		//create a server socket
		ServerSocket serverSocket = new ServerSocket(8000);
		jta.append("Server started at " + new Date() + '\n');
		
		// listen for a connection request
		Socket socket = serverSocket.accept();
		
		// Create data input and output streams
		DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
		DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
		
		while (true){
			//Receive radius from the client
			double radius = inputFromClient.readDouble();
			// compute area
			
			double area = radius * radius * Math.PI;
			
			//send area back to the client
			
			outputToClient.writeDouble(area);
			
			jta.append("Radius received from client: " + radius + '\n');
			jta.append("Area found: " + area + '\n');
		}
	}
	catch(IOException ex) {
		
		System.err.println(ex);
			
		}
		
	}


	
	
	}
	

