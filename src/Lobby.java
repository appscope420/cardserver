import java.util.Vector;

public class Lobby extends Thread {
	
	ConnectionHandler p1;
	ConnectionHandler p2;
	
	private Card a_active;
	private Card a_mn1;
	private Card a_mn2;
	private Card a_mn3;
	private Card a_backup;
	private Card a_sp1;
	private Card a_sp2;
	private Card a_sp3;
	
	private Card b_active;
	private Card b_mn1;
	private Card b_mn2;
	private Card b_mn3;
	private Card b_backup;
	private Card b_sp1;
	private Card b_sp2;
	private Card b_sp3;
	
	private Vector a_hand = new Vector<Card>();
	private Vector a_deck = new Vector<Card>();
	private Vector b_hand = new Vector<Card>();
	private Vector b_deck = new Vector<Card>();
	
	
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
		p1.send("rofl");
		p2.send("lol");
	}

}
