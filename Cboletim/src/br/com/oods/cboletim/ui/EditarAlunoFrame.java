
package br.com.oods.cboletim.ui;

import br.com.oods.cboletim.model.Aluno;
/**
 *
 * @author Vinicius
 */
public class EditarAlunoFrame extends IncluirAlunoFrame {
    
    public EditarAlunoFrame(ListaAlunosFrame framePrincipal) {
        super(framePrincipal);
	setTitle("Editar Usuário");
	bExcluir.setVisible(true);
    }
	
        
    protected Aluno loadAlunoFromPanel() {
	Aluno a = super.loadAlunoFromPanel();
        a.setNum_matricula(getNum_matricula());	
	return a;
	}
    
}
