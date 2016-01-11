import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Deck 
{
	
	Card[] myDeck;
	Decoder dec;
	public Deck(int[] deck, Decoder dec)
	{
		createDeck(deck);
		this.dec = dec;
	}
	
	private void createDeck(int[] deck)
	{
		for(int i = 0; i < deck.length; i++)
		{
			myDeck[i] = dec.getCard(deck[i]);
		}
	}
	
	public boolean isValid()
	{
		for(int i = 0; i < myDeck.length; i++)
		{
			if(myDeck[i] == null)
			{
				return false;
			}
		}
		return true;
	}
	
	public void shuffle()
	{
		Random rnd = ThreadLocalRandom.current();
	    for (int i = 29; i > 0; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      Card x = myDeck[index];
	      myDeck[index] = myDeck[i];
	      myDeck[i] = x;
	    }
	}
}
