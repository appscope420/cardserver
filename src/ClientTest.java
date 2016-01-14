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
                 int i = 0;
                 while(true)
                 {
                	 if(i % 1000 == 0)
                	 {
                		 out.write("{\"code\":\"FOLD_TURN\"}" + " \n"); 
                		 out.flush(); 
                		 i++; 
                	 }
                	 else if(i % 5 == 1)
                	 {
                		 out.write("{\"code\":\"CARD_PLACED\",\"slot\":1, \"id\":4}" + " \n"); 
                		 out.flush(); 
                		 i++; 
                	 }
                	 else if(i % 5 == 2)
                	 {
                		 out.write("{\"code\":\"CARD_PLACED\",\"slot\":2, \"id\":3}" + " \n"); 
                		 out.flush(); 
                		 i++; 
                	 }
                	 else if(i % 5 == 3)
                	 {
                		 out.write("{\"code\":\"CARD_PLACED\",\"slot\":3, \"id\":7}" + " \n"); 
                		 out.flush(); 
                		 i++; 
                	 }
                 }
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