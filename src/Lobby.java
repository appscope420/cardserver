import java.util.Vector;
import java.util.concurrent.CountDownLatch;

public class Lobby extends Thread {
	
	ConnectionHandler p1;
	ConnectionHandler p2;
	
	private Card[] a = new Card[8];
	private Card[] b = new Card[8];
	// 0 = active, 1 = first mn, 2 = second mn, 3 = third mn, 4 = reserve mn, 5 = spell1, 6 = spell2, 7 = spell3
	
	private Deck a_hand;
	private Deck a_deck;
	private Deck a_used;
	private Deck b_hand;
	private Deck b_deck;
	private Deck b_used;
	
	private int a_hp = 100;
	private int b_hp = 100;
	
	CountDownLatch cd = new CountDownLatch(0);
	
	private int playerTurn = 0; // 0 = both, 1 = player1, 2 = player2
	boolean p1rdy = false;
	boolean p2rdy = false;
	boolean gameStarted = false;
	boolean gameOver = false;
	boolean battlePhase = false;
	boolean cardPhase = false;
	int count = 0;
	int aCanPlace = 3;
	int bCanPlace = 3;
	
	
	public Lobby(ConnectionHandler player1, ConnectionHandler player2)
	{
		this.p1 = player1;
		this.p2 = player2;
	}
	
	public void run()
	{
		try 
		{
			System.out.println("Game start");
			startGame();
			cd.await();
			System.out.println("Both players placed cards and folded");

			for(int i = 1; i < 4; i++)
			{
				if(a[i] != null)
					System.out.println(a[i].getId());
				else
					System.out.println("a[i] is null");
			}
			for(int i = 1; i < 4; i++)
			{
				if(b[i] != null)
					System.out.println(b[i].getId());
				else
					System.out.println("b[i] is null");
			}
			
			while(!gameOver)
			{
				
			}
		}
		catch(Exception e)
		{
			
		}
			
		
	}
	
	public synchronized void startGame()
	{
		Decoder decoder = new Decoder();
		a_hand = new Deck(decoder);
		b_hand = new Deck(decoder);
		a_used = new Deck(decoder);
		b_used = new Deck(decoder);
		p1.send("GAME_START", "");
		p2.send("GAME_START", "");
		
		// draw 5 cards each
		for(int i = 0; i < 5; i++)
		{
			Card draw1 = a_deck.drawCard();
			a_hand.addCard(draw1);
			Card draw2 = b_deck.drawCard();
			b_hand.addCard(draw2);
		}
		
		String hand1 = a_hand.deckToJson();
		String hand2 = b_hand.deckToJson();		
		p1.send("REFRESH_HAND", hand1);
		p2.send("REFRESH_HAND", hand2);
		
		//wait for both players "CARD_PLACED" (max 3)
		//then start game
		cardPhase = true;
	}
	
	public synchronized void readyState(int id)
	{
		if(!gameStarted)
		{
			if(id == 1)
				p1rdy = true;
			else
				p2rdy = true;
			if(p1rdy && p2rdy)
			{
				gameStarted = true;
				p1rdy = false; p2rdy = false;
				cd.countDown();
			}
		}
		else if(true)
		{
			
		}
	}

	public void setDeckP1(Deck deck)
	{
		a_deck = deck;
	}
	
	public void setDeckP2(Deck deck)
	{
		b_deck = deck;
	}
	
	public synchronized void placeCard(Card card, int slotId, int playerId) // playerId is 1 or 2
	{
		if(cardPhase)
		{
			if(playerId == 1 && aCanPlace > 0)
			{
				if(slotId >= 0 && slotId < 8 && playerTurn != 2)
				{
					if(a[slotId] == null && inHand(card, 1))
					{
						a[slotId] = card;
						a_hand.removeCard(card.getId());
						aCanPlace--;
					}
					else
						System.out.println("Slot \"" + slotId + "\" occupied.");
				}
				else
				{
					System.out.println("Invalid Slot ID: \"" + slotId + "\"" + " or not Player 1's turn.");
				}
			}
			else if(playerId == 2 && bCanPlace > 0)
			{
				if(slotId >= 0 && slotId < 8 && playerTurn != 1)
				{
					if(b[slotId] == null && inHand(card, 2))
					{
						b[slotId] = card;
						b_hand.removeCard(card.getId());
						bCanPlace--;
					}
					else
						System.out.println("Slot \"" + slotId + "\" occupied.");
				}
				else
				{
					System.out.println("Invalid Slot ID: \"" + slotId + "\"" + " or not Player 2's turn.");
				}
			}
			else 
			{
				System.out.println("Invalid Player ID: \"" + playerId + "\"");
			}
			
			if(aCanPlace == 0 && bCanPlace == 0)
			{
				cardPhase = false;
				
			}
			
		}
	}
		
	public int getPlayerId(String ip)
	{
		if(p1.getIp() == ip)
			return 1;
		else 
			return 2;
	}
	
	public void setTurn(int p)
	{
		playerTurn = p;
	}
	public int getTurn()
	{
		return playerTurn;
	}
	
	public boolean inHand(Card card, int pId) //check for mistakes Kappa
	{
		int cardid = card.getId();
		if(pId == 1)
		{
			Card[] array = a_hand.getDeck();
			for(int i = 0; i < array.length; i++)
			{
				if(array[i] != null)
				{
					int handid = array[i].getId();
					if(handid == cardid)
					{
						return true;
					}
				}
			}
		}
		else
		{
			Card[] array = b_hand.getDeck();
			for(int i = 0; i < array.length; i++)
			{
				if(array[i] != null)
				{
					int handid = array[i].getId();
					if(handid == cardid)
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void clearBoard()
	{
		for(int i = 0; i < a.length; i++)
		{
			if(i == 0 || i == 5 || i == 6 || i == 7)
			{
				if(a[i] != null)
				{
					Card temp = a[i];
					a_used.addCard(temp);
					a[i] = null;
				}
			}
		}
	}
	
	
}
