
import java.sql.*;

public class Database
{
	
	
	public static void getDatabase()
	{
		Connection c = null;
	      
	    try 
	    {
	         Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:FOOD.db");
	    }    
	    catch ( Exception e ) 
	    {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	    }
	      
	    System.out.println("Opened database successfully");
	}
	

}
