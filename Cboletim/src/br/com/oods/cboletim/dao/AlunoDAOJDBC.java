package br.com.oods.cboletim.dao;

import br.com.oods.cboletim.exception.PersistenceException;
import br.com.oods.cboletim.model.Aluno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

/**
* Implementa o contrato de persistência <code>AlunoDAO</code>, para resolver o cadastro da entidade <code>Aluno</code>. 
* 
* <p>A integração com o banco de dados e o envio dos comandos SQL ocorre através da API <code>JDBC</code>.</p>
* 
* @see br.com.oods.cboletim.dao.AlunoDAO
* 
* @author Vinicius
*/
public class AlunoDAOJDBC  implements AlunoDAO{
    //comandos SQL utilizados pelo DAO.
    private final static String INSERT_ALUNO = "INSERT INTO aluno (nome_aluno,sexo,datanascimento,nomemae,nomepai,endereco,fone) VALUES (?,?,?,?,?,?,?)";
    private final static String UPDATE_ALUNO = "UPDATE aluno SET nome_aluno = ?,sexo = ?,datanascimento = ?,nomemae = ?,nomepai = ?,endereco = ?,fone = ? WHERE num_matricula = ?";
    private final static String DELETE_ALUNO = "DELETE FROM aluno WHERE num_matricula = ?";
    private final static String GET_ALL_ALUNOS = "SELECT * FROM aluno";
    private final static String GET_ALUNO_BY_NOME_ALUNO = "SELECT * FROM aluno WHERE nome_aluno like ?";
    private final static String GET_ALUNO_BY_NUM_MATRICULA = "SELECT * FROM aluno WHERE num_matricula = ?";

    private static Logger log = Logger.getLogger(AlunoDAOJDBC.class);

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
    public void save(Aluno aluno) throws PersistenceException {
        if (aluno == null) {
                        throw new PersistenceException("Informe o Aluno para salvar!");
                }
                Connection conn = null;
                PreparedStatement stmt = null;
                try {
                        conn = ConnectionManager.getConnection();
                        if (aluno.getNum_matricula()== null) {
                                stmt = getStatementInsert(conn, aluno);
                        } else {
                                stmt = getStatementUpdate(conn, aluno);
                        }
                        stmt.executeUpdate();
                        conn.commit();
                        log.debug("Aluno foi salvo");
                } catch (SQLException e) {
                        try { conn.rollback(); } catch (Exception sx) {}
                        String errorMsg = "Erro ao salvar Aluno!";
                        log.error(errorMsg, e);
                        throw new PersistenceException(errorMsg, e);
                } finally {
                        ConnectionManager.closeAll(conn, stmt);
                }

    }



    private PreparedStatement getStatementInsert(Connection conn, Aluno a) throws SQLException {
            PreparedStatement stmt = createStatementWithLog(conn, INSERT_ALUNO);
            stmt.setString(1, a.getNome_pessoa());
            stmt.setString(2, a.getSexo());
            stmt.setString(3, a.getDatanascimento());
            stmt.setString(4, a.getNomemae());
            stmt.setString(5, a.getNomepai());
            stmt.setString(6, a.getEndereco());
            stmt.setString(7, a.getFone());
            return stmt;
    }

    private PreparedStatement getStatementUpdate(Connection conn, Aluno a) throws SQLException {
            PreparedStatement stmt = createStatementWithLog(conn, UPDATE_ALUNO);                
            stmt.setString(1, a.getNome_pessoa());
            stmt.setString(2, a.getSexo());
            stmt.setString(3, a.getDatanascimento());
            stmt.setString(4, a.getNomemae());
            stmt.setString(5, a.getNomepai());
            stmt.setString(6, a.getEndereco());
            stmt.setString(7, a.getFone());
            stmt.setInt(8, a.getNum_matricula());
            return stmt;
    }

    @Override
    public void remove(Aluno a) throws PersistenceException {
            if (a == null || a.getId_pessoa()== null) {
                    throw new PersistenceException("Informe o Aluno para exclusao!");
            }
            Connection conn = null;
            PreparedStatement stmt = null;
            try {
                    conn = ConnectionManager.getConnection();
                    stmt = createStatementWithLog(conn, DELETE_ALUNO);
                    stmt.setInt(1, a.getNum_matricula());
                    stmt.executeUpdate();
                    conn.commit();
                    log.debug("Aluno foi excluido");
            } catch (SQLException e) {
                    try { conn.rollback(); } catch (Exception sx) {}
                    String errorMsg = "Erro ao excluir Aluno!";
                    log.error(errorMsg, e);
                    throw new PersistenceException(errorMsg, e);
            }finally{
                    ConnectionManager.closeAll(conn, stmt);
            }
    }

    @Override
    public Aluno findByNum_matricula(Integer num_matricula) throws PersistenceException {
            if (num_matricula == null || num_matricula.intValue() <= 0) {
                    throw new PersistenceException("Informe o número de matricula válido para fazer a busca!");
            }
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;
            Aluno a = null;

            try {
                    conn = ConnectionManager.getConnection();
                    stmt = createStatementWithLog(conn, GET_ALUNO_BY_NUM_MATRICULA);
                    stmt.setInt(1, num_matricula);
                    rs = stmt.executeQuery();

                    if (rs.next()) {                            
                            String nome_aluno = rs.getString("nome_aluno");
                            String sexo = rs.getString("sexo");
                            String datanascimento = rs.getString("datanascimento");
                            String nomemae = rs.getString("nomemae");
                            String nomepai = rs.getString("nomepai");
                            String endereco = rs.getString("endereco");
                            String fone = rs.getString("fone");
                            

                            a = new Aluno(num_matricula, nome_aluno, sexo, datanascimento, nomemae, nomepai,endereco, fone);
                    }
                    return a;
            } catch (SQLException e) {
                    String errorMsg = "Erro ao consultar Aluno pelo Número da Matricula!";
                    log.error(errorMsg, e);
                    throw new PersistenceException(errorMsg, e);
            } finally {
                    ConnectionManager.closeAll(conn, stmt, rs);
            }
    }

    @Override
    public List<Aluno> getAll() throws PersistenceException {
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                    conn = ConnectionManager.getConnection();
                    stmt = createStatementWithLog(conn, GET_ALL_ALUNOS);
                    rs = stmt.executeQuery();

                    return toAlunos(rs);
            } catch (SQLException e) {
                    String errorMsg = "Erro ao consultar todos os alunos!";
                    log.error(errorMsg, e);
                    throw new PersistenceException(errorMsg, e);
            } finally {
                    ConnectionManager.closeAll(conn, stmt, rs);
            }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Aluno> getAlunoByNome(String nome_aluno) throws PersistenceException {
            if (nome_aluno == null || nome_aluno.isEmpty()) {
                    return Collections.EMPTY_LIST;
            }

            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                    conn = ConnectionManager.getConnection();
                    stmt = createStatementWithLog(conn, GET_ALUNO_BY_NOME_ALUNO);
                    stmt.setString(1, nome_aluno + "%");
                    rs = stmt.executeQuery();

                    return toAlunos(rs);
            } catch (SQLException e) {
                    String errorMsg = "Erro ao consultar Aluno por nome!";
                    log.error(errorMsg, e);
                    throw new PersistenceException(errorMsg, e);
            } finally {
                    ConnectionManager.closeAll(conn, stmt, rs);
            }
    }

    private List<Aluno> toAlunos(ResultSet rs) throws SQLException {
            List<Aluno> lista = new ArrayList<Aluno>();
            while (rs.next()) {
                int num_matricula = rs.getInt("num_matricula");
                String nome_aluno = rs.getString("nome_aluno");
                String sexo = rs.getString("sexo");
                String datanascimento = rs.getString("datanascimento");
                String nomemae = rs.getString("nomemae");
                String nomepai = rs.getString("nomepai");
                String endereco = rs.getString("endereco");
                String fone = rs.getString("fone");

                    lista.add(new Aluno(num_matricula, nome_aluno, sexo, datanascimento, nomemae, nomepai, endereco, fone));
            }
            return lista;
    }

    private static PreparedStatement createStatementWithLog(Connection conn, String sql) throws SQLException{
            if (conn == null)
                    return null;

            log.debug("SQL: "+sql);
            return conn.prepareStatement(sql);
    }

    
}
