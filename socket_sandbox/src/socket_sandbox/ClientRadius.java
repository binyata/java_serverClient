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

import org.quickconnectfamily.json.JSONException;
import org.quickconnectfamily.json.JSONInputStream;
import org.quickconnectfamily.json.JSONOutputStream;
import org.quickconnectfamily.json.JSONUtilities;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.net.*;
import java.io.*;

public class ClientRadius extends JFrame {
	// Text Field for receiving radius
	private JTextField jtf = new JTextField();

	// text area to display contents
	private JTextArea jta = new JTextArea();

	// IO streams
	private JSONOutputStream out;
	private JSONInputStream in;


	public static void main(String[] args){

		new ClientRadius();
	}


	public ClientRadius() {

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
			Socket socket = new Socket("localhost", 9292);

			//create an input stream to receive data from the server (pull)
			in = new JSONInputStream(socket.getInputStream());

			//create and output stream to send data to the server (push)
			out = new JSONOutputStream(socket.getOutputStream());

		}

		catch(IOException ex) {
			jta.append(ex.toString() + '\n');

		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		
	}

	private class TextFieldListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
		try{
			// get the radius from the text field
			double radius = Double.parseDouble(jtf.getText().trim());
			
			// Creates a CircleBean
			CircleBean circleBean = new CircleBean(radius);
			
			String jsonString = new JSONUtilities().stringify(circleBean);
			System.out.println(jsonString);

			//Send the radius to the server
			out.writeObject(circleBean);

			// get area from the server
			HashMap map = (HashMap)in.readObject();
			double area = (Double) map.get("area");
			radius = (Double) map.get("radius");
			
			// Display to the text area
			jta.append("Radius is " + radius + "\n");
			jta.append("Area received from the server is " + area + '\n');
			
			// Close stream
			
		}catch (JSONException ex){
			ex.printStackTrace();
		}catch (Exception ex){
			ex.printStackTrace();
		}

	}
	



}

}