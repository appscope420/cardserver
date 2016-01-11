import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.*;

public class Decoder 
{
	private String database;
	
	public Decoder()
	{
		try { database = readDatabase("database.json"); }
		catch(Exception e) {}
	}
	
	public Decoder(String dbname)
	{
		try { database = readDatabase(dbname); }
		catch(Exception e) {}
	}
	
	public static void main(String [] args) throws Exception
	{
		Card kappa = testmethode(101);
		System.out.println(kappa.name + " " + kappa.type);
	}
	
	public static Card testmethode(int cardid) throws FileNotFoundException, IOException
	{
		Card returnThis = null;
		
		JSONObject db = new JSONObject("use readDatabase() with static context here");
		JSONArray cards = db.getJSONArray("cards");
		
		for(int i = 0; i < cards.length(); i++)
		{
				JSONObject temp = (JSONObject) cards.get(i);
				int compare = temp.getInt("id");
				if(cardid == compare)
				{
					String name = temp.getString("name"); 
					String type = temp.getString("type"); 
					int def = temp.getInt("def");
					int atk = temp.getInt("attack");
					int cost = temp.getInt("cost");
					returnThis = new Card(cardid, name, type, def, atk, cost);
				}
		}
			
		return returnThis;
	}
	
	public String readDatabase(String filename) throws FileNotFoundException, IOException
	{
		File txt = new File(filename);
		String json = "";
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(txt)));
		try 
		{
		    String line;
		    while ((line = br.readLine()) != null) 
		    {
		        json = json + line;
		    }
		} 
		finally 
		{
		    br.close();
		}
		return json;
	}
	
	
	public int[] getDeck(String json)
	{		
		JSONObject obj = new JSONObject(json);
		int[] array = new int[30];

		for(int i = 0; i < 30; i++)
		{
			try
			{
				String keyName = "c" + (i+1);
				int value = obj.getInt(keyName);
				array[i] = value;
			}
			catch(Exception e)
			{
				array[i] = 0;
			}
		}
			
		return array;
	}
	
	public Card getCard(int cardid)
	{
		String json = database;
		Card returnThis = null;
				
		JSONObject db = new JSONObject(json);
		JSONArray cards = db.getJSONArray("cards");
		
		for(int i = 0; i < cards.length(); i++)
		{
				JSONObject temp = (JSONObject) cards.get(i);
				int compare = temp.getInt("id");
				if(cardid == compare)
				{
					String name = temp.getString("name"); 
					String type = temp.getString("type"); 
					int def = temp.getInt("def");
					int atk = temp.getInt("attack");
					int cost = temp.getInt("cost");
					returnThis = new Card(cardid, name, type, def, atk, cost);
				}
		}
			
		return returnThis;
	}
	
}
