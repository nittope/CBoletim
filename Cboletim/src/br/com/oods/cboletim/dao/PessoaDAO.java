package br.com.oods.cboletim.dao;

import java.util.List;

import br.com.oods.cboletim.model.Pessoa;

/**
 * <p>Define as operações basicas de cadastro (CRUD), seguindo o design pattern <code>Data Access Object</code>.</p>
 * 
 * @author Vinicius
 */


public interface PessoaDAO {

    /**
     * Faz a inserção ou atualização do usuário na base de dados.
     * @param pessoa
     * @throws <code>PersistenceException</code> se algum problema ocorrer.
     */
    void save(Pessoa pessoa);
    
    /**
     * Faz a exclusão do usuário na base de dados.
     * @param pessoa
     * @throws <code>PersistenceException</code> se algum problema ocorrer.
    */
	
    void remove(Pessoa pessoa);
	
    /**
     * @return Lista com todas os usuários cadastrados no banco de dados.
     * @throws <code>PersistenceException</code> se algum problema ocorrer.
     */
	
    List<Pessoa> getAll();
    
    /**
     * @param nome_pessoa Filtro da pesquisa utilizando like.
     * @return Lista de usuários com filtro em nome_pessoa.
     * @throws <code>PersistenceException</code> se algum problema ocorrer.
     */
	
    List<Pessoa> getPessoaByNome(String nome_pessoa);
	
    
    /**
     * @param id_pessoa filtro da pesquisa.
     * @return Usuário com filtro no id_pessoa.
     * @throws <code>PersistenceException</code> se algum problema ocorrer.
     */
	
    Pessoa findById_pessoa(Integer id_pessoa);

	
    /**
     * Inicializa a interface.
     * @throws <code>PersistenceException</code> se algum problema ocorrer.
     */
    void init();
}
