
package br.com.oods.cboletim.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.oods.cboletim.exception.PersistenceException;
import br.com.oods.cboletim.model.Pessoa;
import br.com.oods.cboletim.model.Secretario;

/**
* Implementa o contrato de persistência <code>SecretarioDAO</code>, para resolver o cadastro da entidade <code>Secretario</code>. 
* 
* <p>A integração com o banco de dados e o envio dos comandos SQL ocorre através da API <code>JDBC</code>.</p>
* 
* @see br.com.oods.cboletim.dao.SecretarioDAO
* 
* @author Vinicius
*/
public class SecretarioDAOJDBC implements SecretarioDAO {
    //comandos SQL utilizados pelo DAO.
    private final static String INSERT_SECRETARIO = "INSERT INTO secretario (id_pessoa,nome_secretario,sexo,endereco,fone,cpf,email) VALUES (?,?,?,?,?,?,?)";
    private final static String UPDATE_SECRETARIO = "UPDATE secretario SET id_pessoa = ?,nome_secretario = ?,sexo = ?,endereco = ?,fone = ?,cpf = ?,email = ? WHERE id_secretario = ?";
    private final static String DELETE_SECRETARIO = "DELETE FROM secretario WHERE id_secretario = ?";
    private final static String GET_ALL_SECRETARIOS = "SELECT * FROM secretario";
    private final static String GET_SECRETARIO_BY_NOME_SECRETARIO = "SELECT * FROM secretario WHERE nome_secretario like ?";
    private final static String GET_SECRETARIO_BY_ID_SECRETARIO = "SELECT * FROM secretario WHERE id_secretario = ?";
    private final static String GET_PESSOA_BY_TIPO_ACESSO_2 = "SELECT * FROM pessoa WHERE tipoacesso_pessoa=2";
    
    
    private static Logger log = Logger.getLogger(SecretarioDAOJDBC.class);
    
    @Override
    public void init() throws PersistenceException {
            Connection conn = null;
            Statement stmt = null;
            try {
                    conn = ConnectionManager.getConnection();
                    stmt = conn.createStatement();			


            } catch (SQLException e) {
                    log.error(e);
                    throw new PersistenceException("Não foi possivel inicializar o banco de dados: " +  e);
            } finally {
                    ConnectionManager.closeAll(conn, stmt);
            }
    }
    
    @Override
    public void save(Secretario secretario) throws PersistenceException {
        if (secretario == null) {
                        throw new PersistenceException("Informe o Secretário para salvar!");
                }
                Connection conn = null;
                PreparedStatement stmt = null;
                try {
                        conn = ConnectionManager.getConnection();
                        if (secretario.getId_secretario()== null) {
                                stmt = getStatementInsert(conn, secretario);
                        } else {
                                stmt = getStatementUpdate(conn, secretario);
                        }
                        stmt.executeUpdate();
                        conn.commit();
                        log.debug("Secretário foi salvo");
                } catch (SQLException e) {
                        try { conn.rollback(); } catch (Exception sx) {}
                        String errorMsg = "Erro ao salvar Secretário!";
                        log.error(errorMsg, e);
                        throw new PersistenceException(errorMsg, e);
                } finally {
                        ConnectionManager.closeAll(conn, stmt);
                }
    }
     private PreparedStatement getStatementInsert(Connection conn, Secretario s) throws SQLException {
            PreparedStatement stmt = createStatementWithLog(conn, INSERT_SECRETARIO);
            stmt.setInt(1, s.getId_pessoa());
            stmt.setString(2, s.getNome_pessoa());
            stmt.setString(3, s.getSexo());
            stmt.setString(4, s.getEndereco());
            stmt.setString(5, s.getFone());
            stmt.setString(6, s.getCpf());
            stmt.setString(7, s.getEmail());            
            return stmt;
    }
     private PreparedStatement getStatementUpdate(Connection conn, Secretario s) throws SQLException {
            PreparedStatement stmt = createStatementWithLog(conn, UPDATE_SECRETARIO);                
            stmt.setInt(1, s.getId_pessoa());
            stmt.setString(2, s.getNome_pessoa());
            stmt.setString(3, s.getSexo());
            stmt.setString(4, s.getEndereco());
            stmt.setString(5, s.getFone());
            stmt.setString(6, s.getCpf());
            stmt.setString(7, s.getEmail()); 
            stmt.setInt(8, s.getId_secretario());
            return stmt;
    }
     
      @Override
    public void remove(Secretario s) throws PersistenceException {
            if (s == null || s.getId_secretario()== null) {
                    throw new PersistenceException("Informe o Secretário para exclusao!");
            }
            Connection conn = null;
            PreparedStatement stmt = null;
            try {
                    conn = ConnectionManager.getConnection();
                    stmt = createStatementWithLog(conn, DELETE_SECRETARIO);
                    stmt.setInt(1, s.getId_secretario());
                    stmt.executeUpdate();
                    conn.commit();
                    log.debug("Secretário foi excluido");
            } catch (SQLException e) {
                    try { conn.rollback(); } catch (Exception sx) {}
                    String errorMsg = "Erro ao excluir Usuário!";
                    log.error(errorMsg, e);
                    throw new PersistenceException(errorMsg, e);
            }finally{
                    ConnectionManager.closeAll(conn, stmt);
            }
    }
    
    @Override
    public Secretario findById_secretario(Integer id_secretario) throws PersistenceException {
            if (id_secretario == null || id_secretario.intValue() <= 0) {
                    throw new PersistenceException("Informe o id válido para fazer a busca!");
            }
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;
            Secretario s = null;

            try {
                    conn = ConnectionManager.getConnection();
                    stmt = createStatementWithLog(conn, GET_SECRETARIO_BY_ID_SECRETARIO);
                    stmt.setInt(1, id_secretario);
                    rs = stmt.executeQuery();

                    if (rs.next()) {
                            int id_pessoa = rs.getInt("id_pessoa");
                            String nome_secretario = rs.getString("nome_secretario");
                            String sexo = rs.getString("sexo");
                            String endereco = rs.getString("endereco");
                            String fone = rs.getString("fone");
                            String cpf = rs.getString("cpf");
                            String email = rs.getString("email");

                            s = new Secretario(id_secretario, id_pessoa, nome_secretario, sexo, endereco, fone, cpf, email);
                    }
                    return s;
            } catch (SQLException e) {
                    String errorMsg = "Erro ao consultar Secretario por id!";
                    log.error(errorMsg, e);
                    throw new PersistenceException(errorMsg, e);
            } finally {
                    ConnectionManager.closeAll(conn, stmt, rs);
            }
    }
    
     @Override
    public List<Secretario> getAll() throws PersistenceException {
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                    conn = ConnectionManager.getConnection();
                    stmt = createStatementWithLog(conn, GET_ALL_SECRETARIOS);
                    rs = stmt.executeQuery();

                    return toSecretarios(rs);
            } catch (SQLException e) {
                    String errorMsg = "Erro ao consultar todos os Secretarios!";
                    log.error(errorMsg, e);
                    throw new PersistenceException(errorMsg, e);
            } finally {
                    ConnectionManager.closeAll(conn, stmt, rs);
            }
    }
    
    public List<Pessoa> getAcessoSecretario() throws PersistenceException {
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                    conn = ConnectionManager.getConnection();
                    stmt = createStatementWithLog(conn, GET_PESSOA_BY_TIPO_ACESSO_2);
                    rs = stmt.executeQuery();

                    return toPessoasSecretarios(rs);
            } catch (SQLException e) {
                    String errorMsg = "Erro ao consultar todos os usuários onde o tipo de acessó é Secretário!";
                    log.error(errorMsg, e);
                    throw new PersistenceException(errorMsg, e);
            } finally {
                    ConnectionManager.closeAll(conn, stmt, rs);
            }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<Secretario> getSecretarioByNome(String nome_secretario) throws PersistenceException {
            if (nome_secretario == null || nome_secretario.isEmpty()) {
                    return Collections.EMPTY_LIST;
            }

            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                    conn = ConnectionManager.getConnection();
                    stmt = createStatementWithLog(conn, GET_SECRETARIO_BY_NOME_SECRETARIO);
                    stmt.setString(1, nome_secretario + "%");
                    rs = stmt.executeQuery();

                    return toSecretarios(rs);
            } catch (SQLException e) {
                    String errorMsg = "Erro ao consultar Secretários por nome!";
                    log.error(errorMsg, e);
                    throw new PersistenceException(errorMsg, e);
            } finally {
                    ConnectionManager.closeAll(conn, stmt, rs);
            }
    }
    
    private List<Secretario> toSecretarios(ResultSet rs) throws SQLException {
            List<Secretario> lista = new ArrayList<Secretario>();
            while (rs.next()) {
                
                int id_secretario = rs.getInt("id_secretario");
                int id_pessoa = rs.getInt("id_pessoa");
                String nome_secretario = rs.getString("nome_secretario");
                String sexo = rs.getString("sexo");
                String endereco = rs.getString("endereco");
                String fone = rs.getString("fone");
                String cpf = rs.getString("cpf");
                String email = rs.getString("email");

                lista.add(new Secretario(id_secretario, id_pessoa, nome_secretario, sexo, endereco, fone, cpf, email));
            }
            return lista;
    }
    private List<Pessoa> toPessoasSecretarios(ResultSet rs) throws SQLException {
            List<Pessoa> listaps = new ArrayList<Pessoa>();
            while (rs.next()) {
                
                int id_pessoa = rs.getInt("id_pessoa");
                int tipoacesso_pessoa = rs.getInt("tipoacesso_pessoa");
                String nome_pessoa = rs.getString("nome_pessoa");
                String sexo = rs.getString("sexo");
                String endereco = rs.getString("endereco");
                String fone = rs.getString("fone");

                listaps.add(new Pessoa(id_pessoa, tipoacesso_pessoa, nome_pessoa, sexo, endereco, fone));
            }
            return listaps;
    }
    
    
    private static PreparedStatement createStatementWithLog(Connection conn, String sql) throws SQLException{
            if (conn == null)
                    return null;

            log.debug("SQL: "+sql);
            return conn.prepareStatement(sql);
    }

}
