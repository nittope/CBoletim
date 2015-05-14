package br.com.oods.cboletim.ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.oods.cboletim.model.Pessoa;
/**
 * Define um TableModel para entidade <code>Pessoa</code>
 * @author Vinicius
 */

public class PessoaTableModel extends AbstractTableModel {

	private List<Pessoa> pessoas;
	
	private String[] colNomes = { "ID","Função", "Nome", "Sexo", "Endereco", "Telefone" };
	private Class<?>[] colTipos = { Integer.class, Integer.class, String.class, String.class, String.class, String.class};
	
	public PessoaTableModel(){
	}
	
	public void reload(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
		fireTableDataChanged();
	}

	@Override
	public Class<?> getColumnClass(int coluna) {
		return colTipos[coluna];
	}

	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public String getColumnName(int coluna) {
		return colNomes[coluna];
	}

	@Override
	public int getRowCount() {
		if (pessoas == null){
			return 0;
		}
		return pessoas.size();
	}

	@Override
	public Object getValueAt(int linha, int coluna) {
		Pessoa p = pessoas.get(linha);
		switch (coluna) {
                case 0:
			return p.getId_pessoa();
		case 1:
			return p.getTipoacesso_pessoa();
		case 2:
			return p.getNome_pessoa();
		case 3:
			return p.getSexo();
		case 4:
			return p.getEndereco();
                case 5:
			return p.getFone();
		default:
			return null;
		}
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	public Pessoa getPessoaAt(int index) {
		return pessoas.get(index);
	}
}
