
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
import br.com.oods.cboletim.model.Secretario;

/**
 * Tela principal do Cadastro de Secretários. Apresenta uma lista com os secretários cadastrados. 
 * 
 * <p>A partir dessa tela é possivel criar/editar ou pesquisar secretário.</p>
 * 
 * @author Vinicius
 */
public class ListaSecretariosFrame extends JFrame {
    
    private SecretarioTable tabela;
    private JScrollPane scrollPane;
    private JButton bNovoSecretario;
    private JButton bBuscarSecretario;
    private JButton bAtualizaLista;
    
    private IncluirSecretarioFrame incluirFrame;
    private EditarSecretarioFrame editarFrame;
    private BuscaSecretarioFrame buscaFrame;
    
    public ListaSecretariosFrame() {
	setTitle("Lista de Secretários");
	setSize(500,700);
	inicializaComponentes();
	adicionaComponentes();
		
	pack();
	setLocationRelativeTo(null);
	setResizable(false);
	setVisible(true);
    }
    private void inicializaComponentes() {
	tabela = new SecretarioTable();
	tabela.addMouseListener(new EditarSecretarioListener());
	scrollPane = new JScrollPane();
               
	scrollPane.setViewportView(tabela);

	bNovoSecretario = new JButton();
	bNovoSecretario.setText("Novo");
	bNovoSecretario.setMnemonic(KeyEvent.VK_N);
	bNovoSecretario.addActionListener(new IncluirSecretarioListener());

	bBuscarSecretario = new JButton();
	bBuscarSecretario.setText("Buscar");
	bBuscarSecretario.setMnemonic(KeyEvent.VK_B);
	bBuscarSecretario.addActionListener(new BuscarSecretarioListener());
		
	bAtualizaLista = new JButton();
	bAtualizaLista.setText("Atualizar");
	bAtualizaLista.setMnemonic(KeyEvent.VK_A);
	bAtualizaLista.addActionListener(new AtualizarListaListener());		
		
		
		
	incluirFrame = new IncluirSecretarioFrame(this);
	editarFrame = new EditarSecretarioFrame(this);
	buscaFrame = new BuscaSecretarioFrame(this);
		
	inicializaDB();
	}
    private void adicionaComponentes(){
	add(scrollPane);
	JPanel panel = new JPanel();
        panel.setSize(500, 700);
	panel.add(bNovoSecretario);
	panel.add(bBuscarSecretario);
	panel.add(bAtualizaLista);
	add(panel, BorderLayout.SOUTH);
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
    
    /**
     *
     * @return Runnable
     */
    public Runnable newAtualizaSecretarioAction() {
	return new Runnable() {
        @Override
	public void run() {
            try {
		SecretarioDAO dao = new SecretarioDAOJDBC();
		tabela.reload(dao.getAll());
            } catch (PersistenceException ex) {
		JOptionPane.showMessageDialog(ListaSecretariosFrame.this,
		ex.getMessage(), "Erro ao consultar Secretário", JOptionPane.ERROR_MESSAGE);
            }
	}
	};
    }
    
    public void refreshTable(List<Secretario> secretario) {
	tabela.reload(secretario);
    }
    
    private class AtualizarListaListener implements ActionListener {
        @Override
	public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(newAtualizaSecretarioAction());
	}
    }
    
    private class IncluirSecretarioListener implements ActionListener {
        @Override
	public void actionPerformed(ActionEvent e) {
            incluirFrame.setVisible(true);
	}
    }
    
    private class EditarSecretarioListener extends MouseAdapter {
	public void mouseClicked(MouseEvent event) {
            if (event.getClickCount() == 2) {
                Secretario s = tabela.getSecretarioSelected();
                if (s != null) {
                    editarFrame.setPessoa(s);
                    editarFrame.setVisible(true);
                }
            }
        }
    }
    
    private class BuscarSecretarioListener implements ActionListener {
        @Override
	public void actionPerformed(ActionEvent event) {
            buscaFrame.setVisible(true);
	}
    }
    
}
