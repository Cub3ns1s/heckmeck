package heckmeck.client;

import heckmeck.server.LogonMessage;

import java.io.*;
import java.net.UnknownHostException;
public class Client {

	public static void main(String[] args) {
		String ip = "127.0.0.1";
		LogonMessage message = new LogonMessage( args[0] );
        try {
			java.net.Socket socket = new java.net.Socket( ip ,23534);
			
			 ObjectOutputStream oos = new ObjectOutputStream( socket.getOutputStream( )); 

			 oos.writeObject( message );
			 oos.flush();
            
	        System.out.println( "Client: Anmeldung abgeschickt");
	            
	        socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} // verbindet sich mit Server
	}

}