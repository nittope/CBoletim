
package br.com.oods.cboletim.dao;
import java.util.List;

import br.com.oods.cboletim.model.Secretario;
import br.com.oods.cboletim.model.Pessoa;


/**
 * <p>Define as operações basicas de cadastro (CRUD), seguindo o design pattern <code>Data Access Object</code>.</p>
 * 
 * @author Vinicius
 */
public interface SecretarioDAO {
    
    /**
     * Faz a inserção ou atualização do secretario na base de dados.
     * @param pessoa
     * @throws <code>PersistenceException</code> se algum problema ocorrer.
     */
    void save(Secretario secretario);
    
    /**
     * Faz a exclusão do secretario na base de dados.
     * @param secretario
     * @throws <code>PersistenceException</code> se algum problema ocorrer.
    */
	
    void remove(Secretario secretario);
	
    /**
     * @return Lista com todas os secretario cadastrados no banco de dados.
     * @throws <code>PersistenceException</code> se algum problema ocorrer.
     */
	
    List<Secretario> getAll();
    
    /**
     * @return Lista com todas os usuários onde o tipo de acesso retorna 2 no banco de dados.
     * @throws <code>PersistenceException</code> se algum problema ocorrer.
     */
	
    List<Pessoa> getAcessoSecretario();
    /**
     * @param nome_secretario Filtro da pesquisa utilizando like.
     * @return Lista de secretarios com filtro em nome_secretario.
     * @throws <code>PersistenceException</code> se algum problema ocorrer.
     */
	
    List<Secretario> getSecretarioByNome(String nome_pessoa);
	
    
    /**
     * @param id_secretario filtro da pesquisa.
     * @return Secretario com filtro no id_secretario.
     * @throws <code>PersistenceException</code> se algum problema ocorrer.
     */
	
    Secretario findById_secretario(Integer id_secretario);

	
    /**
     * Inicializa a interface.
     * @throws <code>PersistenceException</code> se algum problema ocorrer.
     */
    void init();
}
