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
		this.server = server;
	}

	public void run() 
	{
		try 
		{
			in = new BufferedReader(new InputStreamReader(Client.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(Client.getOutputStream()));
			String string;
			InetAddress IP = Client.getInetAddress();
			String sIP = IP.toString();
			sIP = sIP.substring(1);
			IPg = sIP;
			System.out.println(IPg + " has connected");
			//String JSONdeck = in.readLine();
			// Let client connect and send Deck JSON immediately
			String JSONdeck = "{\"c1\": \"1\",\"c2\": \"1\",\"c3\": \"2\",\"c4\": \"2\",\"c5\": \"3\",\"c6\": \"3\",\"c7\": \"4\",\"c8\": \"4\",\"c9\": \"5\",\"c10\": \"5\",\"c11\": \"6\",\"c12\": \"6\",\"c13\": \"7\",\"c14\": \"7\",\"c15\": \"8\",\"c16\": \"8\",\"c17\": \"9\",\"c18\":\"9\",\"c19\": \"10\",\"c20\": \"10\",\"c21\": \"11\",\"c22\": \"11\",\"c23\": \"12\",\"c24\": \"26\",\"c25\":\"13\",\"c26\": \"13\",\"c27\": \"14\",\"c28\": \"14\",\"c29\": \"15\",\"c30\": \"15\"}";
			if (checkDeckValidity(JSONdeck)) 
			{
				if(server.Lobbys.isEmpty())
				{
					lobby = new Lobby(this, null);
					server.Lobbys.addElement(lobby);
					lobby.setDeckP1(deck);
					pId = 1;
				}
				else
				{			
					if(server.Lobbys.lastElement().p2 == null) 
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
				}
				
				while ((string = in.readLine()) != null) 
				{
					System.out.println(IPg + "(" + pId + "):" + string);
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
			case "CONNECTED":
				
				break;
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
		String code = "";
		try 
		{
			code = dec.getCode(json);
		}
		catch(JSONException je)
		{
			System.out.println("JSON or bye");
		}
		
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