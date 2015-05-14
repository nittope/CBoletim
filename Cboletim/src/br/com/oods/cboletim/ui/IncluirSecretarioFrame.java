
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
import br.com.oods.cboletim.model.Secretario;
import java.awt.GridLayout;
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
        painelEditarSecretario.add(new JLabel("Data de Nascimento:"));
	painelEditarSecretario.add(tfDatanascimento);                
        painelEditarSecretario.add(new JLabel("Nome da Mãe:"));
	painelEditarSecretario.add(tfNomemae);
        painelEditarSecretario.add(new JLabel("Nome do Pai:"));
	painelEditarSecretario.add(tfNomepai);
	painelEditarSecretario.add(new JLabel("Endereço:"));
	painelEditarSecretario.add(tfEndereco);
	painelEditarSecretario.add(new JLabel("Telefone:"));
	painelEditarSecretario.add(tfFone);
	painelEditarSecretario.add(new JLabel("Matrícula Nº:"));
	painelEditarSecretario.add(tfNum_matricula);
		
	return painelEditarSecretario;
    }
    
    private class CarregarUsuariosSecretariosListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            frameTiposectretario.setVisible(true);
        }
    }
}
