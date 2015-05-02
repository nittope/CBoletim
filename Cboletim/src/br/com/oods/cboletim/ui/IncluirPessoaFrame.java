package br.com.oods.cboletim.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JComboBox;
import br.com.oods.cboletim.dao.PessoaDAO;
import br.com.oods.cboletim.dao.PessoaDAOJDBC;
import br.com.oods.cboletim.model.Pessoa;

/**
 * Tela para incluir o registro de <code>Pessoa</code>.
 * 
 * @author Vinicius
 */


public class IncluirPessoaFrame extends JFrame {
        
        private JComboBox combofuncao;      
	private JTextField tfNome;
	private JFormattedTextField tfFone;
        private JComboBox combosexo;	
	private JTextField tfEndereco;
	private JFormattedTextField tfId;        
	private JButton bSalvar;
	private JButton bCancelar;
	protected JButton bExcluir;	
	private ListaPessoasFrame framePrincipal;

	public IncluirPessoaFrame(ListaPessoasFrame framePrincipal) {
		this.framePrincipal = framePrincipal;
		setTitle("Incluir Usuário");
		setSize(400,350);
		setLocationRelativeTo(null);
		setResizable(false);
		inicializaComponentes();
		resetForm();
	}
	
	private void inicializaComponentes() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(montaPanelEditarPessoa(), BorderLayout.CENTER);
		panel.add(montaPanelBotoesEditar(), BorderLayout.SOUTH);
		add(panel);
		
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
	
	private JPanel montaPanelBotoesEditar() {
		JPanel panel = new JPanel();

		bSalvar = new JButton("Salvar");
		bSalvar.setMnemonic(KeyEvent.VK_S);
		bSalvar.addActionListener(new SalvarPessoaListener());
		panel.add(bSalvar);

		bCancelar = new JButton("Cancelar");
		bCancelar.setMnemonic(KeyEvent.VK_C);
		bCancelar.addActionListener(new CancelarListener());
		panel.add(bCancelar);
		
		bExcluir = new JButton();
		bExcluir.setText("Excluir");
		bExcluir.setMnemonic(KeyEvent.VK_E);
		bExcluir.addActionListener(new ExcluirPessoaListener());
		bExcluir.setVisible(false);
		panel.add(bExcluir);

		return panel;
	}

	private JPanel montaPanelEditarPessoa() {
		JPanel painelEditarPessoa = new JPanel();
		painelEditarPessoa.setLayout(new GridLayout(8, 1));
                
		combofuncao = new JComboBox();
		tfNome = new JTextField();
                combosexo = new JComboBox();
		//tfSexo = new JTextField();
		tfEndereco = new JTextField();
		tfFone = new JFormattedTextField();
		tfId = new JFormattedTextField();
		tfId.setEnabled(false);
                
                painelEditarPessoa.add(new JLabel("Função:"));
                combofuncao.removeAllItems();
                combofuncao.addItem("COORDENADOR");
                combofuncao.addItem("DIRETOR");
                combofuncao.addItem("SECRETÁRIO");
                painelEditarPessoa.add(combofuncao);
		//painelEditarPessoa.add(tfFuncao);
		painelEditarPessoa.add(new JLabel("Nome:"));
		painelEditarPessoa.add(tfNome);
		painelEditarPessoa.add(new JLabel("Sexo:"));
                combosexo.removeAllItems();
                combosexo.addItem("MASCULINO");
                combosexo.addItem("FEMININO");
                painelEditarPessoa.add(combosexo);
		//painelEditarPessoa.add(tfSexo);
		painelEditarPessoa.add(new JLabel("Endereço:"));
		painelEditarPessoa.add(tfEndereco);
		painelEditarPessoa.add(new JLabel("Telefone:"));
		painelEditarPessoa.add(tfFone);
		painelEditarPessoa.add(new JLabel("Id:"));
		painelEditarPessoa.add(tfId);
		
		return painelEditarPessoa;
	}
	
	private void resetForm() {
		tfId.setValue(null);
                combofuncao.setSelectedIndex(0);
		tfNome.setText("");
		combosexo.setSelectedIndex(0);
		tfEndereco.setText("");
		tfFone.setText("");
	}
	
	private void populaTextFields(Pessoa p){
                tfId.setValue(p.getId_pessoa());
                combofuncao.setSelectedIndex(p.getTipoacesso_pessoa());
		//tfFuncao.setValue(p.getTipoacesso_pessoa());
		tfNome.setText(p.getNome_pessoa());
		//tfSexo.setText(p.getSexo());
                combosexo.setSelectedItem(p.getSexo());
		tfFone.setValue(p.getFone());
                tfEndereco.setText(p.getEndereco());		
	}
	
	protected Integer getIdPessoa(){
		try {
			return Integer.parseInt(tfId.getText());
		} catch (Exception nex) {
			return null;
		}
	}
	
	private String validador() {
		StringBuilder sb = new StringBuilder();
                sb.append(combofuncao.getSelectedItem()== null || "".equals(combofuncao.getSelectedItem()) ? "Função, " : "");
		sb.append(tfNome.getText() == null || "".equals(tfNome.getText().trim()) ? "Nome, " : "");
                sb.append(combosexo.getSelectedItem()== null || "".equals(combosexo.getSelectedItem()) ? "Sexo, " : "");
                sb.append(tfEndereco.getText() == null || "".equals(tfEndereco.getText().trim()) ? "Endereço, " : "");
		sb.append(tfFone.getText() == null || "".equals(tfFone.getText().trim()) ? "Telefone, " : "");
		
		if (!sb.toString().isEmpty()) {
			sb.delete(sb.toString().length()-2, sb.toString().length());
		}
		return sb.toString();
	}
	
	protected Pessoa loadPessoaFromPanel() {
		String msg = validador();
		if (!msg.isEmpty()) {
			throw new RuntimeException("Informe o(s) campo(s): "+msg);
		}
                Integer tipoacesso_pessoa = null;
                try{
                    tipoacesso_pessoa = combofuncao.getSelectedIndex();
                }catch (NumberFormatException nex) {
			throw new RuntimeException("Erro durante a conversão do campo Função (Integer).\nConteudo inválido!");
		}
                //Integer tipoacesso_pessoa = combofuncao.getSelectedIndex();
		//Integer.parseInt(valorfuncao);
                //int tipoacesso_pessoa = parseInt(valorfuncao);
		String nome_pessoa = tfNome.getText().trim();
		Object itemsexo = combosexo.getSelectedItem();
                String sexo = itemsexo.toString();
                String endereco = tfEndereco.getText().trim();
                String fone = tfFone.getText().trim();
		
		if (nome_pessoa.length() < 2) {
			throw new RuntimeException("O nome deve conter no mínimo 2 caracteres!");
		}	
		
		
		return new Pessoa(null, tipoacesso_pessoa, nome_pessoa, sexo, endereco, fone);
	}
	
	public void setPessoa(Pessoa p){
		resetForm();
		if (p != null) {
			populaTextFields(p);
		}
	}
	
	private class CancelarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
			resetForm();
		}
	}
	
	private class SalvarPessoaListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				Pessoa p = loadPessoaFromPanel();
				PessoaDAO dao = new PessoaDAOJDBC();
				dao.save(p);
				
				setVisible(false);
				resetForm();
				SwingUtilities.invokeLater(framePrincipal.newAtualizaPessoaAction());
				
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(IncluirPessoaFrame.this, 
						ex.getMessage(), "Erro ao incluir Usuário", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private class ExcluirPessoaListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Integer id = getIdPessoa();
			if (id != null) {
				try {
                                    
                                    
					PessoaDAO dao = new PessoaDAOJDBC();
					Pessoa p = dao.findById_pessoa(id);
					if (p != null) {
						dao.remove(p);
					}
					
					setVisible(false);
					resetForm();
					SwingUtilities.invokeLater(framePrincipal.newAtualizaPessoaAction());
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(IncluirPessoaFrame.this,
							ex.getMessage(), "Erro ao excluir Usuário", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}
