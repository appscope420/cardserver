import java.util.Vector;

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
	private int playerTurn = 0; // 0 = both, 1 = player1, 2 = player2
	
	
	public Lobby(ConnectionHandler player1, ConnectionHandler player2)
	{
		this.p1 = player1;
		this.p2 = player2;
	}
	
	public void run()
	{
		startGame();
	}
	
	public void startGame()
	{
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
		
	}

	public void setDeckP1(Deck deck)
	{
		a_deck = deck;
	}
	
	public void setDeckP2(Deck deck)
	{
		b_deck = deck;
	}
	
	public void placeCard(Card card, int slotId, int playerId) // playerId is 1 or 2
	{
		if(playerId == 1)
		{
			if(slotId >= 0 && slotId < 8 && playerTurn != 2)
			{
				a[slotId] = card;
			}
			else
			{
				System.out.println("Invalid Slot ID: \"" + slotId + "\"" + " or not Player 1's turn.");
			}
		}
		else if(playerId == 2)
		{
			if(slotId >= 0 && slotId < 8 && playerTurn != 1)
			{
				b[slotId] = card;
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
	}
	
	public int getPlayerId(String ip)
	{
		if(p1.getIp() == ip)
			return 1;
		else 
			return 2;
	}
}
