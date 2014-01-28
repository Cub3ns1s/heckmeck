package heckmeck.client;

import heckmeck.server.LogonMessage;
import heckmeck.server.ServerMessage;
import heckmeck.server.WelcomeMessage;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable {

	private Socket mSocket;
	private String mName;

	public Client(String name) {
		mName = name;
	}

	public static void main(String[] args) {
		new Thread(new Client(args[0])).start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String ip = "127.0.0.1";
		try {
			mSocket = new Socket(ip, 23534);
			LogonMessage message = new LogonMessage(mName);

			ObjectOutputStream oos = new ObjectOutputStream(
					mSocket.getOutputStream());

			oos.writeObject(message);

			System.out.println("Client: Anmeldung abgeschickt");

			do {
			 ObjectInputStream ios =	new ObjectInputStream(mSocket.getInputStream());
			 ServerMessage serverMessage = (ServerMessage)ios.readObject( );
			 
			 processMessage( serverMessage);
			 
			 
			} while (true);
			// socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} // verbindet sich mit Server
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void processMessage(ServerMessage serverMessage) {
		// TODO Auto-generated method stub
		switch (serverMessage.getMessageType()) {
		case ServerMessage.WELCOME:
			processWelcomeMessage( serverMessage);
			break;


		default:
			break;
		}
	}

	private void processWelcomeMessage(ServerMessage serverMessage) {
		// TODO Auto-generated method stub
		WelcomeMessage message = (WelcomeMessage)serverMessage;
		System.out.println(message.getText());
	}

}