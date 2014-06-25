import java.awt.BorderLayout;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.*;

import java.util.*;
import java.io.*;

import javax.swing.JScrollPane;

import org.quickconnectfamily.json.JSONException;
import org.quickconnectfamily.json.JSONInputStream;
import org.quickconnectfamily.json.JSONOutputStream;

public class ServerRadius extends JFrame {

	//text area for displaying contents

	private JTextArea jta = new JTextArea();

	public static void main(String[] args){
		new ServerRadius();
	}

	public ServerRadius() {
		//Place text area on the frame
		setLayout(new BorderLayout());
		add(new JScrollPane(jta), BorderLayout.CENTER);
		setTitle("Server");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true); //it is necessary to show the frame
		try {
			//create a server socket
			ServerSocket serverSocket = new ServerSocket(9292);
			jta.append("Server started at " + new Date() + '\n');

			// listen for a connection request
			Socket socket = serverSocket.accept();

			// Create data input and output streams
			JSONInputStream in = new JSONInputStream(socket.getInputStream());
			JSONOutputStream out = new JSONOutputStream(socket.getOutputStream());

			while (true){
				// Converts the stream to an HasMap Object
				HashMap aMap = (HashMap)in.readObject();
				System.out.println("got object from client");
				double radius = (Double) aMap.get("radius");
				
				// compute area
				double area = radius * radius * Math.PI;
				//send area back to the client
				CircleBean circleBean = new CircleBean(radius);
				circleBean.setArea(area);
				out.writeObject(circleBean);

				jta.append("Radius received from client: " + circleBean.getRadius() + '\n');
				jta.append("Area found: " + circleBean.getArea() + '\n');
				
				// Close stream
			}

		}catch(IOException ex) {
			ex.printStackTrace();
		}catch(JSONException ex){
			ex.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}

	}




}