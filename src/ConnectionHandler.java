import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import org.json.*;

public class ConnectionHandler extends Thread 
{
	private BufferedReader in;
	private BufferedWriter out;
	private Socket Client;
	private String IPg = "Could not detect IP";
	Lobby lobby;
	Server server;
	Deck deck;
	int pId;

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
			String JSONdeck = in.readLine();
			if (checkDeckValidity(JSONdeck)) 
			{
				if (server.Lobbys.lastElement().p2 == null) 
				{
					lobby = server.Lobbys.lastElement();
					server.Lobbys.lastElement().p2 = this;
					lobby.setDeckP2(deck);
					pId = 2;
					lobby.start();
				} 
				else 
				{
					lobby = new Lobby(this, null);
					server.Lobbys.addElement(lobby);
					lobby.setDeckP1(deck);
					pId = 1;
				}

				while ((string = in.readLine()) != null) 
				{
					System.out.println(IPg + ":" + string);
					process(string);
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

	public boolean checkDeckValidity(String JSON) 
	{
		Decoder dec = new Decoder();
		int[] deckint = dec.getDeck(JSON);
		Deck cdeck = new Deck(deckint, dec);
		if(cdeck.isValid())
		{
			System.out.println("Deck valid");
			deck = cdeck;
			return true;
		}
		else
		{
			System.out.println("Deck invalid");
			return false;
		}
		
	}

	public void send(String code, String json) 
	{
		switch(code)
		{
			case "GAME_START":
				write("{\"code\":\"GAME_START\"}");
				break;
			case "REFRESH_HAND":
				write(json);
				break;
			case "REFRESH":
				write(json); // not done yet; needs json-creation method  {code:refresh, slot:int, id:int}
				break;
			case "GAME_OVER_WIN":
				write("{\"code\":\"GAME_OVER_WIN\"}");
				break;
			case "GAME_OVER_LOSS":
				write("{\"code\":\"GAME_OVER_LOSS\"}");
				break;
		}
		      
	}
	
	public void write(String msg)
	{
		try 
	       {
	    	   out.write(msg);
	       }
	       catch(Exception e) 
	       {
	    	   System.out.println("rip BufferedWriter");
	       }
	}
	
	public void process(String json)
	{
		Decoder dec = new Decoder();
		String code = dec.getCode(json);
		
		switch(code)
		{
			case "READY_GAME_START": 
				
				break;
			case "CARD_PLACED":
				int id = dec.getCodeExtra(json, "id");
				int slot = dec.getCodeExtra(json, "slot");
				Card card = dec.getCard(id);
				int player = pId;
				lobby.placeCard(card, slot, player);
				break;
			case "FOLD_TURN":
				lobby.readyState(pId);
				break;
			default: 
				break;
		}
	}
	
	public String getIp()
	{
		return IPg;
	}
	
	public void waitForCardsPlaced()
	{
		String string;
		try 
		{
			while ((string = in.readLine()) != null) 
			{
				if(string.equals("Cards_placed"))
				{
					//
				}	
			}
		}
		catch(Exception e) {}
	}
	
}