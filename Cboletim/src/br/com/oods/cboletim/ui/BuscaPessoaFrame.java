package br.com.oods.cboletim.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.oods.cboletim.dao.PessoaDAO;
import br.com.oods.cboletim.dao.PessoaDAOJDBC;
import br.com.oods.cboletim.model.Pessoa;

/**
 * Tela utilizada para realizar a pesquisa de <code>Pessoa</code> 
 * com filtro no campo <code>nome_pessoa</code>.  
 * 
 * @author Vinicius
 */

public class BuscaPessoaFrame extends JFrame {
	
	private JTextField tfNome;
	private JButton bBuscar;
	
	private ListaPessoasFrame framePrincipal;
	
	public BuscaPessoaFrame(ListaPessoasFrame framePrincipal) {
		this.framePrincipal = framePrincipal;
		setTitle("Buscar");
		setSize(450, 450);
		setLocationRelativeTo(null);
		setResizable(false);
		inicializaComponentes();
	}
	
	private void inicializaComponentes() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(montaPanelBuscaPessoa(), BorderLayout.CENTER);
		panel.add(montaPanelBotoesBusca(), BorderLayout.SOUTH);
		this.add(panel);
		
		GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
	}
	
	private JPanel montaPanelBotoesBusca() {
		JPanel panel = new JPanel();
		
		bBuscar = new JButton("Buscar");
		bBuscar.setMnemonic(KeyEvent.VK_B);
		bBuscar.addActionListener(new BuscarPorNomeListener());
		panel.add(bBuscar);
		
		return panel;
	}
	
	private void resetForm() {
		tfNome.setText("");
	}

	private JPanel montaPanelBuscaPessoa() {
		JPanel painel = new JPanel();
		GridLayout layout = new GridLayout(8, 1);
		painel.setLayout(layout);

		tfNome = new JTextField();
		painel.add(new JLabel("Nome:"));
		painel.add(tfNome);
		
		return painel;
	}
	
	private class BuscarPorNomeListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String nome = tfNome.getText();
			if (nome.isEmpty()) {
				resetForm();
				setVisible(false);
				return;
			}
			try {
				PessoaDAO dao = new PessoaDAOJDBC();
				final List<Pessoa> pessoas = dao.getPessoaByNome(tfNome.getText());
				resetForm();
				setVisible(false);
				
				framePrincipal.refreshTable(pessoas);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(BuscaPessoaFrame.this,
						ex.getMessage(), "Erro ao buscar Usuário", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
}
