
public class Lobby extends Thread {
	
	ConnectionHandler Player1;
	ConnectionHandler Player2;
	
	public Lobby(ConnectionHandler Player1, ConnectionHandler Player2)
	{
		this.Player1 = Player1;
		this.Player2 = Player2;
	}
	
	public void run()
	{
		startGame();
	}
	
	public void startGame()
	{
		Player1.begin();
		Player2.begin();
	}

}
