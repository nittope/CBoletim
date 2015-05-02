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
import br.com.oods.cboletim.dao.PessoaDAO;
import br.com.oods.cboletim.dao.PessoaDAOJDBC;
import br.com.oods.cboletim.exception.PersistenceException;
import br.com.oods.cboletim.model.Pessoa;

/**
 * Tela principal do Cadastro de Usuários. Apresenta uma lista com os usuários cadastrados. 
 * 
 * <p>A partir dessa tela é possivel criar/editar ou pesquisar usuário.</p>
 * 
 * @author Vinicius
 */

public class ListaPessoasFrame extends JFrame {
	
	private PessoaTable tabela;
	private JScrollPane scrollPane;
	private JButton bNovoUsuario;
	private JButton bBuscarUsuario;
	private JButton bAtualizaLista;
	
	private IncluirPessoaFrame incluirFrame;
	private EditarPessoaFrame editarFrame;
	private BuscaPessoaFrame buscaFrame;	
	
	public ListaPessoasFrame() {
		setTitle("Lista de Usuários");
		setSize(500,700);
		inicializaComponentes();
		adicionaComponentes();
		
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	private void inicializaComponentes() {
		tabela = new PessoaTable();
		tabela.addMouseListener(new EditarPessoaListener());
		scrollPane = new JScrollPane();
               
		scrollPane.setViewportView(tabela);

		bNovoUsuario = new JButton();
		bNovoUsuario.setText("Novo");
		bNovoUsuario.setMnemonic(KeyEvent.VK_N);
		bNovoUsuario.addActionListener(new IncluirPessoaListener());

		bBuscarUsuario = new JButton();
		bBuscarUsuario.setText("Buscar");
		bBuscarUsuario.setMnemonic(KeyEvent.VK_B);
		bBuscarUsuario.addActionListener(new BuscarPessoaListener());
		
		bAtualizaLista = new JButton();
		bAtualizaLista.setText("Atualizar");
		bAtualizaLista.setMnemonic(KeyEvent.VK_A);
		bAtualizaLista.addActionListener(new AtualizarListaListener());		
		
		
		
		incluirFrame = new IncluirPessoaFrame(this);
		editarFrame = new EditarPessoaFrame(this);
		buscaFrame = new BuscaPessoaFrame(this);
		
		inicializaDB();
	}
	
	private void adicionaComponentes(){
		add(scrollPane);
		JPanel panel = new JPanel();
                panel.setSize(500, 700);
		panel.add(bNovoUsuario);
		panel.add(bBuscarUsuario);
		panel.add(bAtualizaLista);
		add(panel, BorderLayout.SOUTH);
	}
	
	private void inicializaDB() {
		try {
			new PessoaDAOJDBC().init();
			SwingUtilities.invokeLater(newAtualizaPessoaAction());
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
    public Runnable newAtualizaPessoaAction() {
		return new Runnable() {
                        @Override
			public void run() {
				try {
					PessoaDAO dao = new PessoaDAOJDBC();
					tabela.reload(dao.getAll());
				} catch (PersistenceException ex) {
					JOptionPane.showMessageDialog(ListaPessoasFrame.this,
							ex.getMessage(), "Erro ao consultar Usuário", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
	}
	
	public void refreshTable(List<Pessoa> pessoa) {
		tabela.reload(pessoa);
	}
	
	private class AtualizarListaListener implements ActionListener {
                @Override
		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(newAtualizaPessoaAction());
		}
	}
	
	private class IncluirPessoaListener implements ActionListener {
                @Override
		public void actionPerformed(ActionEvent e) {
			incluirFrame.setVisible(true);
		}
	}
	
	private class EditarPessoaListener extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			if (event.getClickCount() == 2) {
				Pessoa p = tabela.getPessoaSelected();
				if (p != null) {
					editarFrame.setPessoa(p);
					editarFrame.setVisible(true);
				}
			}
		}
	}
	
	private class BuscarPessoaListener implements ActionListener {
                @Override
		public void actionPerformed(ActionEvent event) {
			buscaFrame.setVisible(true);
		}
	}
	
	
	}

