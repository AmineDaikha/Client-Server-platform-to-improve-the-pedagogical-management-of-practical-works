package Controller;

import java.sql.SQLException;

public class ErrEx {
	
	
	public  void errMang(SQLException e) {
		System.out.println("Error : "+e.getMessage());
		System.out.println("Error code : "+e.getErrorCode());
		System.out.println("Error state : "+e.getSQLState());
	}


}
