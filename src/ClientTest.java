import java.net.*;
import java.io.*;

public class ClientTest extends Thread
{
    private Socket socket;
    private int port;
    private String ip;
    private String name;
    
    public ClientTest(int port, String ip, String name)
    {
        this.port = port;
        this.ip = ip;
        this.name = name;
    }
    
    public void run()
    {
    	 boolean IOE = false;
         while(IOE == false)
         {
             try
             {
                 socket = new Socket(ip, port);
                 BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                 out.flush();
                 IOE = true;
                 System.out.println("Connection Established");
                 out.write(name + "\n");
                 out.flush();
             }
             catch (UnknownHostException e)
             {
                 IOE = false;
                 System.out.println("Connection Failed");
             }
             catch (IOException e)
             {
                 IOE = false;
                 System.out.println("IOE");
             }
         }
    }
}