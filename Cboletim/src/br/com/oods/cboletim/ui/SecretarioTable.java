
package br.com.oods.cboletim.ui;

import java.util.List;

import javax.swing.JTable;

import br.com.oods.cboletim.model.Secretario;


/**
 * <code>JTable</code> com operações customizadas para entidade <code>Secretario</code>.
 * 
 * @see br.com.oods.cboletim.ui.SecretarioTableModel
 * 
 * @author Vinicius
 */
public class SecretarioTable extends JTable {
    private SecretarioTableModel modelo;
	
	public SecretarioTable() {
		modelo = new SecretarioTableModel();
		setModel(modelo);
	}
	
	public Secretario getSecretarioSelected() {
		int i = getSelectedRow();
		if (i < 0) {
			return null;
		}
		return modelo.getSecretarioAt(i);
	}
	
	public void reload(List<Secretario> secretarios) {
		modelo.reload(secretarios);
	}
    
}
