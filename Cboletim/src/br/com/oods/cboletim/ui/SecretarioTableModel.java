
package br.com.oods.cboletim.ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.oods.cboletim.model.Secretario;

/**
 * Define um TableModel para entidade <code>Secretario</code>
 * @author Vinicius
 */
public class SecretarioTableModel extends AbstractTableModel {
    private List<Secretario> secretarios;
	
	private String[] colNomes = { "ID Secretário", "ID Usuário", "Nome", "Sexo", "Endereco", "Telefone", "CPF", "Senha" };
	private Class<?>[] colTipos = { Integer.class, Integer.class, String.class, String.class, String.class, String.class};
	
	public SecretarioTableModel(){
	}
	
	public void reload(List<Secretario> secretarios) {
		this.secretarios = secretarios;
		fireTableDataChanged();
	}

	@Override
	public Class<?> getColumnClass(int coluna) {
		return colTipos[coluna];
	}

	@Override
	public int getColumnCount() {
		return 8;
	}

	@Override
	public String getColumnName(int coluna) {
		return colNomes[coluna];
	}

	@Override
	public int getRowCount() {
		if (secretarios == null){
			return 0;
		}
		return secretarios.size();
	}

	@Override
	public Object getValueAt(int linha, int coluna) {
		Secretario s = secretarios.get(linha);
		switch (coluna) {
                case 0:
			return s.getId_secretario();
		case 1:
			return s.getId_pessoa();
		case 2:
			return s.getNome_pessoa();
		case 3:
			return s.getSexo();
		case 4:
			return s.getEndereco();
                case 5:
			return s.getFone();
                case 6:
                        return s.getCpf();
                case 7:
                        return s.getEmail();
		default:
			return null;
		}
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	public Secretario getSecretarioAt(int index) {
		return secretarios.get(index);
	}
    
}
