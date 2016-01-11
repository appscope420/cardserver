import java.net.*;
import java.util.Vector;
import java.io.*;

public class Server extends Thread
{
    private ServerSocket server;
    private int port;
    volatile Vector<Lobby> Lobbys;
    
    public Server(int port)
    {
        this.port = port;
    }
    
    public void run()
    {
    	Lobbys = new Vector<Lobby>();
        try 
        {
            server = new ServerSocket(port);
            Socket client;
            while(true)
            {
                System.out.println("Server: Waiting for connection");
                client = server.accept();
                ConnectionHandler c = new ConnectionHandler(client, this);
                c.start();
            }
        } 
        catch (UnknownHostException e) 
        {
            System.out.println("UNKNOWN HOST");
        } 
        catch (IOException e) 
        {
        	System.out.println("IOE");
        }
        
    }
}