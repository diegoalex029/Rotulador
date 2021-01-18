package co.soaint.contingencia.operaciones;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class GetConnectionDB {		

	private Connection connection = null;
	public Connection getConnection() throws SQLException {
	try{
		String namejndi = DataConfigProperties.getPropietat("namejndi");
		
		Context initContext = new InitialContext();
		//Context webContext = (Context)initContext.lookup("java:/comp/env");
		DataSource ds = (DataSource) initContext.lookup(namejndi);
		connection = ds.getConnection();
		initContext.close();			
    	}catch(Exception e){
    		System.out.println("Error de conexión SQL");
		e.printStackTrace();
	}
	
	return this.connection;
	}
	
	public void  closeConnection(Connection connection) {
		try {
			if(connection != null) {
			connection.close();
			connection = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
