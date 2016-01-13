import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONStringer;

public class Deck 
{
	private Vector<Card> deck;
	Decoder dec;
	
	public Deck(Decoder dec)
	{
		deck = new Vector<Card>();
		this.dec = dec;
	}
	
	public Deck(int[] pDeck, Decoder dec)
	{
		deck = new Vector<Card>();
		this.dec = dec;
		createDeck(pDeck);
	}
	
	public Card[] getDeck()
	{
		Card[] toReturn = new Card[deck.size()];
		for(int i = 0; i < deck.size(); i++)
		{
			toReturn[i] = deck.elementAt(i);
		}
		return toReturn;
	}
	
	public String deckToJson()
	{
		Card[] array = getDeck();
		String json = "{\"code\":\"REFRESH_HAND\",\"cards\":[";
		
		for(int i = 0; i < array.length; i++)
		{
			Card temp = array[i];
			json = json + "{\"id\":" + temp.getId() + "}";
			if(i < (array.length - 1))
				json = json + ",";
		}
		
		json = json + "]}";		
		
		return json;
	}
	
	private void createDeck(int[] pDeck)
	{
		for(int i = 0; i < pDeck.length; i++)
		{
			Card toAdd = dec.getCard(pDeck[i]);
			deck.add(toAdd);
		}
	}
	
	public boolean isValid()
	{
		for(int i = 0; i < deck.size(); i++)
		{
			if(deck.elementAt(i) == null)
			{
				return false;
			}
		}
		return true;
	}
	
	// for Array-based structure; is now Vector-based
	/*public void shuffle()
	{
		Random rnd = ThreadLocalRandom.current();
	    for (int i = 29; i > 0; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      Card x = myDeck[index];
	      myDeck[index] = myDeck[i];
	      myDeck[i] = x;
	    }
	}*/
	
	public int random(int i)
	{
	    Random gen = ThreadLocalRandom.current();
	    int rn = gen.nextInt(i);
	    return rn;
	}
	
	public Card drawCard()
	{
		int temp = random(deck.size());
		Card card = deck.elementAt(temp);
		deck.removeElementAt(temp);
		return card;
	}
	
	public void addCard(Card pCard)
	{
		deck.addElement(pCard);
	}
	
	public int getSize()
	{
		return deck.size();
	}
}
