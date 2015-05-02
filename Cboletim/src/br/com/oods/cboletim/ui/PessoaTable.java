package br.com.oods.cboletim.ui;

import java.util.List;

import javax.swing.JTable;

import br.com.oods.cboletim.model.Pessoa;

/**
 * <code>JTable</code> com operações customizadas para entidade <code>Pessoa</code>.
 * 
 * @see br.com.oods.cboletim.ui.PessoaTableModel
 * 
 * @author Vinicius
 */
public class PessoaTable extends JTable {

	private PessoaTableModel modelo;
	
	public PessoaTable() {
		modelo = new PessoaTableModel();
		setModel(modelo);
	}
	
	public Pessoa getPessoaSelected() {
		int i = getSelectedRow();
		if (i < 0) {
			return null;
		}
		return modelo.getPessoaAt(i);
	}
	
	public void reload(List<Pessoa> pessoas) {
		modelo.reload(pessoas);
	}
}
