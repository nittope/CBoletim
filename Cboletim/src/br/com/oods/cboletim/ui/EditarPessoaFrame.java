package br.com.oods.cboletim.ui;

import br.com.oods.cboletim.model.Pessoa;

/**
 * Tela para editar o registro de usuário.
 * 
 * @author Vinicius
 */

public class EditarPessoaFrame extends IncluirPessoaFrame {

	public EditarPessoaFrame(ListaPessoasFrame framePrincipal) {
		super(framePrincipal);
		setTitle("Editar Usuário");
		bExcluir.setVisible(true);
	}
	
        
	protected Pessoa loadPessoaFromPanel() {
		Pessoa p = super.loadPessoaFromPanel();
                p.setId_pessoa(getIdPessoa());
		//p.setId_pessoa(getIdPessoa());
		return p;
	}
	
}
