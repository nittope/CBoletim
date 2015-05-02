
package br.com.oods.cboletim.dao;

import java.util.List;

import br.com.oods.cboletim.model.Aluno;

/**
 *
 * @author Vinicius
 */
public interface AlunoDAO {
   /**
     * Faz a inserção ou atualização do Aluno na base de dados.
     * @param aluno
     * @throws <code>PersistenceException</code> se algum problema ocorrer.
     */
    void save(Aluno aluno);
    
    /**
     * Faz a exclusão do Aluno na base de dados.
     * @param aluno
     * @throws <code>PersistenceException</code> se algum problema ocorrer.
    */
	
    void remove(Aluno aluno);
	
    /**
     * @return Lista com todas os alunos cadastrados no banco de dados.
     * @throws <code>PersistenceException</code> se algum problema ocorrer.
     */
	
    List<Aluno> getAll();
    
    /**
     * @param nome_aluno Filtro da pesquisa utilizando like.
     * @return Lista de Alunos com filtro em nome_pessoa.
     * @throws <code>PersistenceException</code> se algum problema ocorrer.
     */
	
    List<Aluno> getAlunoByNome(String nome_aluno);
	
    
    /**
     * @param num_matricula filtro da pesquisa.
     * @return Aluno com filtro no num_matricula.
     * @throws <code>PersistenceException</code> se algum problema ocorrer.
     */
	
    Aluno findByNum_matricula(Integer num_matricula);

	
    /**
     * Inicializa a interface.
     * @throws <code>PersistenceException</code> se algum problema ocorrer.
     */
    void init(); 
}
