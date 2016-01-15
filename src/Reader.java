import java.io.BufferedReader;
import java.io.IOException;

public class Reader extends Thread {
	
	// GET REFACTORED
	BufferedReader br;
	
	public Reader(BufferedReader br)
	{
		this.br = br;
		this.start();
	}
	
	public void run()
	{
		try 
		{
			String s = "";
			while((s = br.readLine()) != null)
			{
				System.out.println("Received: " + s);
			}
		} 
		catch (IOException e) 
		{
			System.out.println("RIP");
		}
	}

}
