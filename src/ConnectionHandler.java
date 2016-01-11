import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ConnectionHandler extends Thread
{
	private BufferedReader in;
	private BufferedWriter out;
	private Socket Client;
	private String IPg = "Could not detect IP";
	Lobby Lobby;
	Server server;
	
	public ConnectionHandler(Socket s, Server server)
	{
		Client = s;
	}
	public void run()
	{
		try 
		{
			System.out.println(IPg + " has connected");
			in = new BufferedReader(new InputStreamReader(Client.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(Client.getOutputStream()));
			String string;
			InetAddress IP = Client.getInetAddress();
        	String sIP = IP.toString();
        	sIP = sIP.substring(1);
        	IPg = sIP;
        	if(checkDeckValidity())
        	{
        		if(server.Lobbys.lastElement().Player2 == null)
        		{
        			Lobby = server.Lobbys.lastElement();
    				server.Lobbys.lastElement().Player2 = this;
    				Lobby.start();
        		}
        		else
        		{
        			Lobby = new Lobby(this, null);
        			server.Lobbys.addElement(Lobby);
        		}
        		
        		while((string = in.readLine()) != null)
    	        {	
    	        	System.out.println(IPg + ":" + string);
    	        }
        	}
        	else
        	{
        		Client.close();
        	}
		} 
		catch (IOException e) 
		{
			System.out.println(IPg + " has disconnected");
		}
	}
	
	public boolean checkDeckValidity()
	{
		return true;
	}
	
	public void begin()
	{
		
	}
}