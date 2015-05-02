
package br.com.oods.cboletim.dao;

import java.sql.*;
import org.apache.log4j.Logger;
import br.com.oods.cboletim.exception.PersistenceException;

/**
 * Classe responsável por abrir e encerrar a conexão com o banco de dados.
 * 
 * @ Vinicius
 */

public class ConnectionManager {
        //Informacões para conexão com banco de dados Oracle
	private static final String STR_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String DATABASE = "cboletim";
	private static final String STR_CON = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String USER = "cboletim";
	private static final String PASSWORD = "cboletim";
	
	private static Logger log = Logger.getLogger(ConnectionManager.class);
	

	public static Connection getConnection() throws PersistenceException {
		Connection conn = null;
		try {
			Class.forName(STR_DRIVER);
			conn = DriverManager.getConnection(STR_CON, USER, PASSWORD);
			conn.setAutoCommit(false);
			
			log.debug("Aberta a conexão com banco de dados!");
			return conn;
		} catch (ClassNotFoundException e) {
			String errorMsg = "Driver (JDBC) não encontrado";
			log.error(errorMsg, e);
			throw new PersistenceException(errorMsg, e);
		} catch (SQLException e) {
			String errorMsg = "Erro ao obter a conexão";
			log.error(errorMsg, e);
			throw new PersistenceException(errorMsg, e);
		}
	}

	public static void closeAll(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
				log.debug("Fechada a conexão com banco de dados!");
			}
		} catch (Exception e) {
			log.error("Não foi possivel fechar a conexão com o banco de dados!",e);
		}
	}

	public static void closeAll(Connection conn, Statement stmt) {
		try {
			if (conn != null) {
				closeAll(conn);
			}
			if (stmt != null) {
				stmt.close();
			}
		} catch (Exception e) {
			log.error("Não foi possivel fechar o statement!",e);
		}
	}

	public static void closeAll(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (conn != null || stmt != null) {
				closeAll(conn, stmt);
			}
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			log.error("Não foi possivel fechar o resultSet!",e);
		}
	}

}
