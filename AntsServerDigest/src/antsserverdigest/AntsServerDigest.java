package antsserverdigest;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AntsServerDigest 
{
    public static void main(String[] args) throws ClassNotFoundException, IOException 
    {
        ServerSocket sconn=null;
        Socket conn=null;
        gestore now;
        Thread thread;
        
        int port=3356;
        sconn=new ServerSocket(port);
        
        while(true)
        {
            try
            {
                conn=sconn.accept();
                now=new gestore(conn);
                thread=new Thread(now);
                thread.start();
            }
            catch(IOException e)
            {
                
            }
        }
    }   
}
