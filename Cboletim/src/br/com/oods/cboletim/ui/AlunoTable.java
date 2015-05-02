
package br.com.oods.cboletim.ui;

import java.util.List;

import javax.swing.JTable;

import br.com.oods.cboletim.model.Aluno;

/**
 *
 * @author Vinicius
 */
public class AlunoTable extends JTable {
    private AlunoTableModel modelo;
	
    public AlunoTable() {
        modelo = new AlunoTableModel();
	setModel(modelo);
    }
	
    public Aluno getAlunoSelected() {
	int i = getSelectedRow();
	if (i < 0) {
            return null;
	}
        return modelo.getAlunoAt(i);
    }
	
    public void reload(List<Aluno> alunos) {
	modelo.reload(alunos);
    }
}
