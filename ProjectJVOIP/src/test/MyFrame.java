package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MyFrame extends JFrame implements ActionListener{
	static JButton answerButton;
	JButton hangupButton;
	static JButton rejectButton;
	JButton callButton;
	JButton endSessionButton;
	JButton startSessionButton;
	JTextField destinationText;
	JTextField usernameText;
	JTextField pwdText;
	String recordsPath = "D:\\"; //specify the folder to save the call records
	public static String command = "";
	public static String myDestination = "";
	JCheckBox recordCheckbox;
	
	MyFrame(){
		
		JLabel text4 = new JLabel("For incoming calls: ");
		text4.setBounds(180,300,200,50);
		
		answerButton = new JButton();
		answerButton.setBounds(300,300,120,50);
		answerButton.setText("Answer call");
		answerButton.addActionListener(this);
		
		hangupButton = new JButton();
		hangupButton.setBounds(460,150,120,50);
		hangupButton.setText("Hangup");
		hangupButton.addActionListener(this);
		
		rejectButton = new JButton();
		rejectButton.setBounds(420,300,120,50);
		rejectButton.setText("Reject call");
		rejectButton.addActionListener(this);
		
		JLabel text1 = new JLabel("Destination: ");
		text1.setBounds(5,150,90,50);
		
		JLabel text2 = new JLabel("Username: ");
		text2.setBounds(5,10,90,50);
		
		JLabel text3 = new JLabel("Password: ");
		text3.setBounds(5,60,90,50);
		
		usernameText = new JTextField();
		usernameText.setPreferredSize(new Dimension(250,50));
		usernameText.setBounds(80, 10, 250, 50);
		usernameText.setText("jvoiptest");
		
		pwdText = new JTextField();
		pwdText.setPreferredSize(new Dimension(250,50));
		pwdText.setBounds(80, 60, 250, 50);
		pwdText.setText("jvoiptestpwd");
		
		destinationText = new JTextField();
		destinationText.setPreferredSize(new Dimension(250,50));
		destinationText.setBounds(90, 150, 250, 50);
		destinationText.setText("testivr3");
		
		callButton = new JButton();
		callButton.setBounds(340,150,120,50);
		callButton.setText("Call");
		callButton.addActionListener(this);
		
		endSessionButton = new JButton();
		endSessionButton.setBounds(300,400,200,50);
		endSessionButton.setText("End");
		endSessionButton.addActionListener(this);
		
		recordCheckbox = new JCheckBox();
		recordCheckbox.setText("Voice Record");
		recordCheckbox.setFocusable(false);
		recordCheckbox.setBounds(350,0,200,50);
		recordCheckbox.setSelected(true);
		
		startSessionButton = new JButton();
		startSessionButton.setBounds(330,60,200,50);
		startSessionButton.setText("Start");
		startSessionButton.addActionListener(this);
		
		
		
		answerButton.setBackground(Color.GREEN);
		answerButton.setOpaque(true);
		hangupButton.setBackground(Color.RED);
		hangupButton.setOpaque(true);
		rejectButton.setBackground(Color.RED);
		rejectButton.setOpaque(true);
		callButton.setBackground(Color.GREEN);
		callButton.setOpaque(true);
		endSessionButton.setBackground(Color.BLUE);
		endSessionButton.setOpaque(true);
		startSessionButton.setBackground(Color.BLUE);
		startSessionButton.setOpaque(true);
		
		//rejectButton.setEnabled(false);
		//answerButton.setEnabled(false);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(800,800);
		this.setVisible(true);
		this.setTitle("VOIP Project");
		this.add(answerButton);
		this.add(hangupButton);
		this.add(rejectButton);
		this.add(destinationText);
		this.add(callButton);
		this.add(endSessionButton);
		this.add(startSessionButton);
		this.add(text1);
		this.add(text2);
		this.add(text3);
		this.add(text4);
		this.add(usernameText);
		this.add(pwdText);
		this.add(recordCheckbox);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==answerButton && SIPNotifications.currentNotification == "Ringing") {
			System.out.println("Answer Button Clicked");
			VoIPProject.webphoneobj.API_Accept(-1,0); 
			System.out.println("Incoming call accepted");
			command = "accept";
		}
		if(e.getSource()==hangupButton && SIPNotifications.currentNotification == "Speaking") {
			System.out.println("Hangup Button Clicked");
			VoIPProject.webphoneobj.API_Hangup(-1);
			System.out.println("Call disconnected");
			command = "hangup call";
		}
		if(e.getSource()==rejectButton && SIPNotifications.currentNotification == "Ringing") {
			System.out.println("Reject Button Clicked");
			VoIPProject.webphoneobj.API_Reject(-1);
			System.out.println("Incoming call rejected");
			command = "reject call";
		}
		if(e.getSource()==callButton) {
			myDestination = destinationText.getText();
			VoIPProject.webphoneobj.API_CallEx( -1, myDestination, 0); 
			if(recordCheckbox.isSelected()) {
				VoIPProject.webphoneobj.API_VoiceRecord(1, 2, recordsPath, -2);
			}
		}
		if(e.getSource()==endSessionButton) {
			VoIPProject.webphoneobj.API_Stop(); //stop the sip stack (this will also unregister)
			VoIPProject.sipnotifications.Stop(); //stop the JVoIP notification listener
			System.out.println("Session Ended");
            System.exit(0); 
		}
		if(e.getSource()==startSessionButton) {
			String username = usernameText.getText();
			String pwd = pwdText.getText();
			VoIPProject.webphoneobj.API_SetParameter("username", username);
			VoIPProject.webphoneobj.API_SetParameter("password", pwd);
			//start the SIP stack
            System.out.println("start...");
            //webphoneobj.API_StartGUI(); //you might uncomment this line if you wish to use the built-in GUI
            VoIPProject.webphoneobj.API_Start();
            //Thread.sleep(200); 
		}
	}
}
