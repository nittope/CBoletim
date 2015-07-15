
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
import javax.swing.SwingUtilities;
import br.com.oods.cboletim.dao.SecretarioDAO;
import br.com.oods.cboletim.dao.SecretarioDAOJDBC;
import br.com.oods.cboletim.exception.PersistenceException;
import br.com.oods.cboletim.model.Pessoa;
import br.com.oods.cboletim.model.Secretario;
import java.awt.GridLayout;
import javafx.scene.control.TextField;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Vinicius
 */
public class IncluirSecretarioFrame extends JFrame {
    
    private JTextField tfIdpessoa;
    private JTextField tfNome;    
    private JComboBox combosexo;   
    private JTextField tfEndereco;
    private JFormattedTextField tfFone;
    private JFormattedTextField tfCpf;
    private JTextField tfEmail;
    private JButton bSalvar;
    private JButton bCancelar;
    private JButton bPesquisa;
    protected JButton bExcluir;	
    private ListaSecretariosFrame framePrincipal;
    private ListarTipoSecretarioFrame frameTiposectretario;
    
    public IncluirSecretarioFrame(ListaSecretariosFrame framePrincipal) {
	this.framePrincipal = framePrincipal;
	setTitle("Incluir Secretário");
	setSize(400,350);
	setLocationRelativeTo(null);
	setResizable(false);
	inicializaComponentes();
	resetForm();
	}
    
    private void inicializaComponentes() {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.add(montaPanelEditarSecretario(), BorderLayout.CENTER);
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
        bSalvar.addActionListener(new SalvarSecretarioListener());
        panel.add(bSalvar);

        bCancelar = new JButton("Cancelar");
        bCancelar.setMnemonic(KeyEvent.VK_C);
        bCancelar.addActionListener(new CancelarListener());
        panel.add(bCancelar);
		
        bExcluir = new JButton();
        bExcluir.setText("Excluir");
        bExcluir.setMnemonic(KeyEvent.VK_E);
        bExcluir.addActionListener(new ExcluirSecretarioListener());
        bExcluir.setVisible(false);
        panel.add(bExcluir);
        return panel;
    }
    
    private JPanel montaPanelEditarSecretario() {
        JPanel painelEditarSecretario = new JPanel();
        painelEditarSecretario.setLayout(new GridLayout(8, 1));
         
        bPesquisa = new JButton();
        tfIdpessoa = new JTextField();
	tfNome = new JTextField();
        combosexo = new JComboBox();
        tfEndereco = new JTextField();
	tfFone = new JFormattedTextField();
        tfCpf = new JFormattedTextField();
	tfEmail = new JTextField();
        
        
        painelEditarSecretario.add(new JLabel("Id Usuário:"));
	painelEditarSecretario.add(tfIdpessoa);
        bPesquisa.setText("Pesquisar Usuário");
        bPesquisa.addActionListener(new CarregarUsuariosSecretariosListener());
	painelEditarSecretario.add(bPesquisa);	
	painelEditarSecretario.add(new JLabel("Nome:"));
	painelEditarSecretario.add(tfNome);
	painelEditarSecretario.add(new JLabel("Sexo:"));
        combosexo.removeAllItems();
        combosexo.addItem("MASCULINO");
        combosexo.addItem("FEMININO");
        painelEditarSecretario.add(combosexo);        
	painelEditarSecretario.add(new JLabel("Endereço:"));
	painelEditarSecretario.add(tfEndereco);
	painelEditarSecretario.add(new JLabel("Telefone:"));
	painelEditarSecretario.add(tfFone);
        painelEditarSecretario.add(new JLabel("Cpf:"));
	painelEditarSecretario.add(tfCpf);
	painelEditarSecretario.add(new JLabel("Email:"));
	painelEditarSecretario.add(tfEmail);
	
		
	return painelEditarSecretario;
    }
    
    private void resetForm() {
		tfIdpessoa.setText("");                
		tfNome.setText("");
		combosexo.setSelectedIndex(0);                
		tfEndereco.setText("");
		tfFone.setText("");
                tfCpf.setText("");
                tfEmail.setText("");
	}
    
    private void populaTextFields(Secretario s){
                tfIdpessoa.setText(String.valueOf(s.getId_pessoa()));             
		tfNome.setText(s.getNome_pessoa());
                combosexo.setSelectedItem(s.getSexo());
                tfEndereco.setText(s.getEndereco());
                tfFone.setText(s.getFone());
                tfCpf.setText(s.getCpf());
		tfEmail.setText(s.getEmail());
                		
	}
    
    protected Integer getId_pessoa(){
		try {
			return Integer.parseInt(tfIdpessoa.getText());
		} catch (Exception nex) {
			return null;
		}
	}
    
    private String validador() {
		StringBuilder sb = new StringBuilder();
                sb.append(tfIdpessoa.getText() == null || "".equals(tfIdpessoa.getText().trim()) ? "ID Usuário, " : "");
		sb.append(tfNome.getText() == null || "".equals(tfNome.getText().trim()) ? "Nome, " : "");
                sb.append(combosexo.getSelectedItem()== null || "".equals(combosexo.getSelectedItem()) ? "Sexo, " : "");                
                sb.append(tfEndereco.getText() == null || "".equals(tfEndereco.getText().trim()) ? "Endereço, " : "");
		sb.append(tfFone.getText() == null || "".equals(tfFone.getText().trim()) ? "Telefone, " : "");
                sb.append(tfCpf.getText() == null || "".equals(tfCpf.getText().trim()) ? "Endereço, " : "");
		sb.append(tfEmail.getText() == null || "".equals(tfEmail.getText().trim()) ? "Telefone, " : "");
		
		if (!sb.toString().isEmpty()) {
			sb.delete(sb.toString().length()-2, sb.toString().length());
		}
		return sb.toString();
	}
    
    protected Secretario loadSecretarioFromPanel() {
		String msg = validador();
		if (!msg.isEmpty()) {
			throw new RuntimeException("Informe o(s) campo(s): "+msg);
		}
                int id_pessoa = Integer.parseInt(tfIdpessoa.getText().trim());
		String nome_pessoa = tfNome.getText().trim();
		Object itemsexo = combosexo.getSelectedItem();
                String sexo = itemsexo.toString();                
                String endereco = tfEndereco.getText().trim();
                String fone = tfFone.getText().trim();
                String cpf = tfCpf.getText().trim();
                String email = tfEmail.getText().trim();
		
		if (nome_pessoa.length() < 2) {
			throw new RuntimeException("O nome deve conter no mínimo 2 caracteres!");
		}	
		
		
		return new Secretario(null, id_pessoa, nome_pessoa, sexo, endereco, fone, cpf, email);
	}
    
    public void setSecretario(Secretario s){
		resetForm();
		if (s != null) {
			populaTextFields(s);
		}
	}
    private class CancelarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
			resetForm();
		}
	}
    private class SalvarSecretarioListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				Secretario s = loadSecretarioFromPanel();
				SecretarioDAO dao = new SecretarioDAOJDBC();
				dao.save(s);
				
				setVisible(false);
				resetForm();
				SwingUtilities.invokeLater(framePrincipal.newAtualizaSecretarioAction());
				
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(IncluirSecretarioFrame.this, 
						ex.getMessage(), "Erro ao incluir Secretário", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	private class ExcluirSecretarioListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Integer id_pessoa = getId_pessoa();
			if (id_pessoa != null) {
				try {
                                    
                                    
					SecretarioDAO dao = new SecretarioDAOJDBC();
					Secretario s = dao.findById_secretario(id_pessoa);
					if (s != null) {
						dao.remove(s);
					}
					
					setVisible(false);
					resetForm();
					SwingUtilities.invokeLater(framePrincipal.newAtualizaSecretarioAction());
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(IncluirSecretarioFrame.this,
							ex.getMessage(), "Erro ao excluir Secretario", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
    public void exportarUsuario(Pessoa p){
        tfIdpessoa.setText(String.valueOf(p.getId_pessoa()));
        tfNome.setText(p.getNome_pessoa());
        combosexo.setSelectedItem(p);
        tfEndereco.setText(p.getEndereco());
        tfFone.setText(p.getFone());
        //tfIdpessoa.setText(p.getId_pessoa());
    
    }
    
    
    private class CarregarUsuariosSecretariosListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            frameTiposectretario.setVisible(true);
            
        }
    }
}
