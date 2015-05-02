
package br.com.oods.cboletim.ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.oods.cboletim.model.Aluno;
/**
 *
 * @author Vinicius
 */
public class AlunoTableModel extends AbstractTableModel {
    private List<Aluno> alunos;
	
    private String[] colNomes = { "Matrícula Nº", "Nome", "Sexo","Data de Nascimento","Nome da mãe","Nome do pai", "Endereco", "Telefone" };
    private Class<?>[] colTipos = { Integer.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class};
	
    public AlunoTableModel(){
    }
	
    public void reload(List<Aluno> alunos) {

        this.alunos = alunos;
	fireTableDataChanged();
    }

    @Override
    public Class<?> getColumnClass(int coluna) {
	return colTipos[coluna];
    }

    @Override
    public int getColumnCount() {
    	return 4;
    }

    @Override
    public String getColumnName(int coluna) {
	return colNomes[coluna];
    }

    @Override
    public int getRowCount() {
	if (alunos == null){
        	return 0;
	}
	return alunos.size();
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
	Aluno a = alunos.get(linha);
	switch (coluna) {
            case 0:
		return a.getNum_matricula();
            case 1:
		return a.getNome_pessoa();
            case 2:
		return a.getSexo();
            case 3:
		return a.getDatanascimento();
            case 4:
		return a.getNomemae();
            case 5:
		return a.getNomepai();
            case 6:
		return a.getEndereco();
            case 7:
		return a.getFone();   
            default:
		return null;
	}
    }
	
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
	return false;
    }
	
    public Aluno getAlunoAt(int index) {
	return alunos.get(index);
    }
    
}
