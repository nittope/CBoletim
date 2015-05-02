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
import br.com.oods.cboletim.dao.AlunoDAO;
import br.com.oods.cboletim.dao.AlunoDAOJDBC;
import br.com.oods.cboletim.exception.PersistenceException;
import br.com.oods.cboletim.model.Aluno;


/**
 *
 * @author Vinicius
 */
public class ListaAlunosFrame extends JFrame{
    private AlunoTable tabela;
    private JScrollPane scrollPane;
    private JButton bNovoAluno;
    private JButton bBuscarAluno;
    private JButton bAtualizaLista;
    
    private IncluirAlunoFrame incluirFrame;
    private EditarAlunoFrame editarFrame;
    private BuscaAlunoFrame buscaFrame;
    
        public ListaAlunosFrame() {
            setTitle("Lista de Alunos");
            setSize(500,700);
            inicializaComponentes();
            adicionaComponentes();	
            pack();
            setLocationRelativeTo(null);
            setResizable(false);
            setVisible(true);
	}

	private void inicializaComponentes() {
		tabela = new AlunoTable();
		tabela.addMouseListener(new EditarAlunoListener());
		scrollPane = new JScrollPane();
               
		scrollPane.setViewportView(tabela);

		bNovoAluno = new JButton();
		bNovoAluno.setText("Novo");
		bNovoAluno.setMnemonic(KeyEvent.VK_N);
		bNovoAluno.addActionListener(new IncluirAlunoListener());

		bBuscarAluno = new JButton();
		bBuscarAluno.setText("Buscar");
		bBuscarAluno.setMnemonic(KeyEvent.VK_B);
		bBuscarAluno.addActionListener(new BuscarAlunoListener());
		
		bAtualizaLista = new JButton();
		bAtualizaLista.setText("Atualizar");
		bAtualizaLista.setMnemonic(KeyEvent.VK_A);
		bAtualizaLista.addActionListener(new AtualizarListaListener());		
		
		
		
		incluirFrame = new IncluirAlunoFrame(this);
		editarFrame = new EditarAlunoFrame(this);
		buscaFrame = new BuscaAlunoFrame(this);
		
		inicializaDB();
	}
	
	private void adicionaComponentes(){
		add(scrollPane);
		JPanel panel = new JPanel();
                panel.setSize(500, 700);
		panel.add(bNovoAluno);
		panel.add(bBuscarAluno);
		panel.add(bAtualizaLista);
		add(panel, BorderLayout.SOUTH);
	}
	
	private void inicializaDB() {
		try {
			new AlunoDAOJDBC().init();
			SwingUtilities.invokeLater(newAtualizaAlunoAction());
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
    public Runnable newAtualizaAlunoAction() {
		return new Runnable() {
                        @Override
			public void run() {
				try {
					AlunoDAO dao = new AlunoDAOJDBC();
					tabela.reload(dao.getAll());
				} catch (PersistenceException ex) {
					JOptionPane.showMessageDialog(ListaAlunosFrame.this,
							ex.getMessage(), "Erro ao consultar Aluno", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
	}
	
	public void refreshTable(List<Aluno> aluno) {
		tabela.reload(aluno);
	}
	
	private class AtualizarListaListener implements ActionListener {
                @Override
		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(newAtualizaAlunoAction());
		}
	}
	
	private class IncluirAlunoListener implements ActionListener {
                @Override
		public void actionPerformed(ActionEvent e) {
			incluirFrame.setVisible(true);
		}
	}
	
	private class EditarAlunoListener extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			if (event.getClickCount() == 2) {
				Aluno a = tabela.getAlunoSelected();
				if (a != null) {
					editarFrame.setAluno(a);
					editarFrame.setVisible(true);
				}
			}
		}
	}
	
	private class BuscarAlunoListener implements ActionListener {
                @Override
		public void actionPerformed(ActionEvent event) {
			buscaFrame.setVisible(true);
		}
	}
	
	
}

