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
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 Reader rrr = new Reader(in);
                 out.flush();
                 IOE = true;
                 System.out.println("Connection Established");
                 out.write("{\"c1\": \"1\",\"c2\": \"1\",\"c3\": \"2\",\"c4\": \"2\",\"c5\": \"3\",\"c6\": \"3\",\"c7\": \"4\",\"c8\": \"4\",\"c9\": \"5\",\"c10\": \"5\",\"c11\": \"6\",\"c12\": \"6\",\"c13\": \"7\",\"c14\": \"7\",\"c15\": \"8\",\"c16\": \"8\",\"c17\": \"9\",\"c18\":\"9\",\"c19\": \"10\",\"c20\": \"10\",\"c21\": \"11\",\"c22\": \"11\",\"c23\": \"12\",\"c24\": \"26\",\"c25\":\"13\",\"c26\": \"13\",\"c27\": \"14\",\"c28\": \"14\",\"c29\": \"15\",\"c30\": \"15\"}" + "\n");
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