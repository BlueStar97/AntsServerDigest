package antsserverdigest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class gestore implements Runnable
{
    private Socket conn;
    private Connection db;
    Statement smt;
    String query;
    ResultSet rset;
    private String toSend="";
    private String Rec="";
    
    Boolean found;
    
    String IP;
    String Port;
    
    BufferedReader in;
    PrintWriter out;
    
    public gestore(Socket mConn) throws ClassNotFoundException
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            db=DriverManager.getConnection("jdbc:mysql://localhost:3306/Ants", "root", "");
            smt=db.createStatement();
            conn=mConn;
            query="";
            out = new PrintWriter(conn.getOutputStream());
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            found=false;
            IP="";
            Port="";
        }
        catch(IOException e)
        {
            
        }
        catch(SQLException e)
        {
            
        }
    }
    
    @Override
    public void run()
    {
        try
        {
            Rec=in.readLine();
            query = "SELECT * FROM dig";
            
            while(Rec!="finished")
            {
            
                rset=smt.executeQuery(query);

                while(rset.next()&&!found)
                {
                    if(Rec.equals(rset.getString("digest")))
                    {
                        found=true;
                    }
                }

                if(found)
                {
                    out.println("found");
                    out.flush();
                }
                else
                {
                    out.println("notFound");
                    out.flush();
                }
                Rec=in.readLine();
            }
            
            out.close();
            conn.close();
        }
        
        catch(IOException e)
        {
        
        } catch (SQLException ex) {
            Logger.getLogger(gestore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
