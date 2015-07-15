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

/**
* Implementa o contrato de persistência <code>PessoaDAO</code>, para resolver o cadastro da entidade <code>Pessoa</code>. 
* 
* <p>A integração com o banco de dados e o envio dos comandos SQL ocorre através da API <code>JDBC</code>.</p>
* 
* @see br.com.oods.cboletim.dao.PessoaDAO
* 
* @author Vinicius
*/

public class PessoaDAOJDBC implements PessoaDAO {

    //comandos SQL utilizados pelo DAO.
    private final static String INSERT_PESSOA = "INSERT INTO pessoa (tipoacesso_pessoa,nome_pessoa,sexo,endereco,fone,login,senha) VALUES (?,?,?,?,?,?,?)";
    private final static String UPDATE_PESSOA = "UPDATE pessoa SET tipoacesso_pessoa = ?,nome_pessoa = ?,sexo = ?,endereco = ?,fone = ?, login = ?, senha = ? WHERE id_pessoa = ?";
    private final static String DELETE_PESSOA = "DELETE FROM pessoa WHERE id_pessoa = ?";
    public final static String GET_ALL_PESSOAS = "SELECT * FROM pessoa";
    private final static String GET_PESSOAS_BY_NOME_PESSOA = "SELECT * FROM pessoa WHERE nome_pessoa like ?";
    private final static String GET_PESSOA_BY_ID_PESSOA = "SELECT * FROM pessoa WHERE id_pessoa = ?";

    private static Logger log = Logger.getLogger(PessoaDAOJDBC.class);

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
    public void save(Pessoa pessoa) throws PersistenceException {
        if (pessoa == null) {
                        throw new PersistenceException("Informe o Usuário para salvar!");
                }
                Connection conn = null;
                PreparedStatement stmt = null;
                try {
                        conn = ConnectionManager.getConnection();
                        if (pessoa.getId_pessoa()== null) {
                                stmt = getStatementInsert(conn, pessoa);
                        } else {
                                stmt = getStatementUpdate(conn, pessoa);
                        }
                        stmt.executeUpdate();
                        conn.commit();
                        log.debug("Usuário foi salvo");
                } catch (SQLException e) {
                        try { conn.rollback(); } catch (Exception sx) {}
                        String errorMsg = "Erro ao salvar Usuário!";
                        log.error(errorMsg, e);
                        throw new PersistenceException(errorMsg, e);
                } finally {
                        ConnectionManager.closeAll(conn, stmt);
                }

    }



    private PreparedStatement getStatementInsert(Connection conn, Pessoa p) throws SQLException {
            PreparedStatement stmt = createStatementWithLog(conn, INSERT_PESSOA);
            stmt.setInt(1, p.getTipoacesso_pessoa());
            stmt.setString(2, p.getNome_pessoa());
            stmt.setString(3, p.getSexo());
            stmt.setString(4, p.getEndereco());
            stmt.setString(5, p.getFone());
            stmt.setString(6, p.getLogin());
            stmt.setString(7, p.getSenha());
            return stmt;
    }

    private PreparedStatement getStatementUpdate(Connection conn, Pessoa p) throws SQLException {
            PreparedStatement stmt = createStatementWithLog(conn, UPDATE_PESSOA);                
            stmt.setInt(1, p.getTipoacesso_pessoa());
            stmt.setString(2, p.getNome_pessoa());
            stmt.setString(3, p.getSexo());
            stmt.setString(4, p.getEndereco());
            stmt.setString(5, p.getFone());
            stmt.setString(6, p.getLogin());
            stmt.setString(7, p.getSenha());
            stmt.setInt(8, p.getId_pessoa());
            return stmt;
    }

    @Override
    public void remove(Pessoa p) throws PersistenceException {
            if (p == null || p.getId_pessoa()== null) {
                    throw new PersistenceException("Informe o Usuário para exclusao!");
            }
            Connection conn = null;
            PreparedStatement stmt = null;
            try {
                    conn = ConnectionManager.getConnection();
                    stmt = createStatementWithLog(conn, DELETE_PESSOA);
                    stmt.setInt(1, p.getId_pessoa());
                    stmt.executeUpdate();
                    conn.commit();
                    log.debug("Usuário foi excluido");
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
    public Pessoa findById_pessoa(Integer id_pessoa) throws PersistenceException {
            if (id_pessoa == null || id_pessoa.intValue() <= 0) {
                    throw new PersistenceException("Informe o id válido para fazer a busca!");
            }
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;
            Pessoa p = null;

            try {
                    conn = ConnectionManager.getConnection();
                    stmt = createStatementWithLog(conn, GET_PESSOA_BY_ID_PESSOA);
                    stmt.setInt(1, id_pessoa);
                    rs = stmt.executeQuery();

                    if (rs.next()) {
                            int tipoacesso_pessoa = rs.getInt("tipoacesso_pessoa");
                            String nome_pessoa = rs.getString("nome_pessoa");
                            String sexo = rs.getString("sexo");
                            String endereco = rs.getString("endereco");
                            String fone = rs.getString("fone");
                            String login = rs.getString("login");
                            String senha = rs.getString("senha");


                            p = new Pessoa(id_pessoa, tipoacesso_pessoa, nome_pessoa, sexo, endereco, fone, login, senha);
                    }
                    return p;
            } catch (SQLException e) {
                    String errorMsg = "Erro ao consultar Usuário por id!";
                    log.error(errorMsg, e);
                    throw new PersistenceException(errorMsg, e);
            } finally {
                    ConnectionManager.closeAll(conn, stmt, rs);
            }
    }

    @Override
    public List<Pessoa> getAll() throws PersistenceException {
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                    conn = ConnectionManager.getConnection();
                    stmt = createStatementWithLog(conn, GET_ALL_PESSOAS);
                    rs = stmt.executeQuery();

                    return toPessoas(rs);
            } catch (SQLException e) {
                    String errorMsg = "Erro ao consultar todos os usuários!";
                    log.error(errorMsg, e);
                    throw new PersistenceException(errorMsg, e);
            } finally {
                    ConnectionManager.closeAll(conn, stmt, rs);
            }
    }
     
    @Override
    @SuppressWarnings("unchecked")
    public List<Pessoa> getPessoaByNome(String nome_pessoa) throws PersistenceException {
            if (nome_pessoa == null || nome_pessoa.isEmpty()) {
                    return Collections.EMPTY_LIST;
            }

            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                    conn = ConnectionManager.getConnection();
                    stmt = createStatementWithLog(conn, GET_PESSOAS_BY_NOME_PESSOA);
                    stmt.setString(1, nome_pessoa + "%");
                    rs = stmt.executeQuery();

                    return toPessoas(rs);
            } catch (SQLException e) {
                    String errorMsg = "Erro ao consultar Usuários por nome!";
                    log.error(errorMsg, e);
                    throw new PersistenceException(errorMsg, e);
            } finally {
                    ConnectionManager.closeAll(conn, stmt, rs);
            }
    }

    private List<Pessoa> toPessoas(ResultSet rs) throws SQLException {
            List<Pessoa> lista = new ArrayList<Pessoa>();
            while (rs.next()) {
                    int id_pessoa = rs.getInt("id_pessoa");
                    int tipoacesso_pessoa = rs.getInt("tipoacesso_pessoa");
                    String nome_pessoa = rs.getString("nome_pessoa");
                    String sexo = rs.getString("sexo");
                    String endereco = rs.getString("endereco");
                    String fone = rs.getString("fone");
                    String login = rs.getString("login");
                    String senha = rs.getString("senha");


                    lista.add(new Pessoa(id_pessoa, tipoacesso_pessoa, nome_pessoa, sexo, endereco, fone, login, senha));
            }
            return lista;
    }

    public static PreparedStatement createStatementWithLog(Connection conn, String sql) throws SQLException{
            if (conn == null)
                    return null;

            log.debug("SQL: "+sql);
            return conn.prepareStatement(sql);
    }

}