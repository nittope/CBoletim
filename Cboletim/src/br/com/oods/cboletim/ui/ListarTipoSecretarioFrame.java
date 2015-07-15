
package br.com.oods.cboletim.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import br.com.oods.cboletim.dao.SecretarioDAO;
import br.com.oods.cboletim.dao.SecretarioDAOJDBC;
import br.com.oods.cboletim.exception.PersistenceException;
import br.com.oods.cboletim.model.Pessoa;
import br.com.oods.cboletim.model.Secretario;

/**
 *
 * @author Vinicius
 */
public class ListarTipoSecretarioFrame extends JFrame {
    private PessoaTable tabelapessoatiposecretario;
    private JScrollPane scrollPane;
    
    public ListarTipoSecretarioFrame() {
            setTitle("Lista de Usuários do Tipo Secretário");
            setSize(500,700);
            inicializaComponentes();            
            pack();
            setLocationRelativeTo(null);
            setResizable(false);
            setVisible(true);
	}
    
    private void inicializaComponentes() {
		tabelapessoatiposecretario = new PessoaTable();		
		scrollPane = new JScrollPane();
               
		scrollPane.setViewportView(tabelapessoatiposecretario);		
		
		inicializaDB();
	}
    
    private void inicializaDB() {
		try {
			new SecretarioDAOJDBC().init();
			SwingUtilities.invokeLater(newAtualizaSecretarioAction());
		} catch (PersistenceException ex) {
			JOptionPane.showMessageDialog(this, "Não foi possível inicializar o Banco de dados: "+
					ex.getMessage()+"\nVerifique a dependência do driver ou configurações do banco!", "Erro", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
    }
    
    public Runnable newAtualizaSecretarioAction() {
		return new Runnable() {
                        @Override
			public void run() {
				try {
					SecretarioDAO dao = new SecretarioDAOJDBC();
					tabelapessoatiposecretario.reload(dao.getAcessoSecretario());
				} catch (PersistenceException ex) {
					JOptionPane.showMessageDialog(ListarTipoSecretarioFrame.this,
							ex.getMessage(), "Erro ao consultar Tipo de Usuário Secretário", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
	}
    public void refreshTable(List<Pessoa> pessoa) {
		tabelapessoatiposecretario.reload(pessoa);
	}
    private class EscolhaUsuario extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			if (event.getClickCount() == 2) {
				Pessoa p = tabelapessoatiposecretario.getPessoaSelected();
				if (p != null) {                                    
					//p.setId_pessoa(p.getId_pessoa());
                                        IncluirSecretarioFrame frm = null;
                                        frm.exportarUsuario(p);
                                        ListarTipoSecretarioFrame.this.dispose();
                                        frm.setVisible(true);
                                        
                                        
                                        
				}
			}
		}
	}
}
