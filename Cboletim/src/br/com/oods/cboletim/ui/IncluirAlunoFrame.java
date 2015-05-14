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
import br.com.oods.cboletim.dao.AlunoDAO;
import br.com.oods.cboletim.dao.AlunoDAOJDBC;
import br.com.oods.cboletim.exception.PersistenceException;
import br.com.oods.cboletim.model.Aluno;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
/**
 *
 * @author Vinicius
 */
public class IncluirAlunoFrame extends JFrame{
        
    private JTextField tfNome;    
    private JComboBox combosexo;
    private JFormattedTextField tfDatanascimento;
    private JTextField tfNomemae;
    private JTextField tfNomepai;
    private JTextField tfEndereco;
    private JFormattedTextField tfFone;
    private JFormattedTextField tfNum_matricula;        
    private JButton bSalvar;
    private JButton bCancelar;
    protected JButton bExcluir;	
    private ListaAlunosFrame framePrincipal;
    
    
    public IncluirAlunoFrame(ListaAlunosFrame framePrincipal) {
		this.framePrincipal = framePrincipal;
		setTitle("Incluir Aluno");
		setSize(400,350);
		setLocationRelativeTo(null);
		setResizable(false);
		inicializaComponentes();
		resetForm();
	}
	
	private void inicializaComponentes() {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.add(montaPanelEditarAluno(), BorderLayout.CENTER);
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
            bSalvar.addActionListener(new SalvarAlunoListener());
            panel.add(bSalvar);

            bCancelar = new JButton("Cancelar");
            bCancelar.setMnemonic(KeyEvent.VK_C);
            bCancelar.addActionListener(new CancelarListener());
            panel.add(bCancelar);
		
            bExcluir = new JButton();
            bExcluir.setText("Excluir");
            bExcluir.setMnemonic(KeyEvent.VK_E);
            bExcluir.addActionListener(new ExcluirAlunoListener());
            bExcluir.setVisible(false);
            panel.add(bExcluir);

            return panel;
	}

	private JPanel montaPanelEditarAluno() {
            JPanel painelEditarAluno = new JPanel();
            painelEditarAluno.setLayout(new GridLayout(8, 1));
                
		tfNome = new JTextField();
                combosexo = new JComboBox();
                tfDatanascimento = new JFormattedTextField(FormatoMascaraCampo());
                tfNomemae = new JTextField();
                tfNomepai = new JTextField();
		tfEndereco = new JTextField();
		tfFone = new JFormattedTextField();
		tfNum_matricula = new JFormattedTextField();
		tfNum_matricula.setEnabled(false);
                
               
		
		painelEditarAluno.add(new JLabel("Nome:"));
		painelEditarAluno.add(tfNome);
		painelEditarAluno.add(new JLabel("Sexo:"));
                combosexo.removeAllItems();
                combosexo.addItem("MASCULINO");
                combosexo.addItem("FEMININO");
                painelEditarAluno.add(combosexo);
                painelEditarAluno.add(new JLabel("Data de Nascimento:"));
		painelEditarAluno.add(tfDatanascimento);                
                painelEditarAluno.add(new JLabel("Nome da Mãe:"));
		painelEditarAluno.add(tfNomemae);
                painelEditarAluno.add(new JLabel("Nome do Pai:"));
		painelEditarAluno.add(tfNomepai);
		painelEditarAluno.add(new JLabel("Endereço:"));
		painelEditarAluno.add(tfEndereco);
		painelEditarAluno.add(new JLabel("Telefone:"));
		painelEditarAluno.add(tfFone);
		painelEditarAluno.add(new JLabel("Matrícula Nº:"));
		painelEditarAluno.add(tfNum_matricula);
		
		return painelEditarAluno;
	}
	
	private void resetForm() {
		tfNum_matricula.setValue(null);                
		tfNome.setText("");
		combosexo.setSelectedIndex(0);
                tfDatanascimento.setText("");
                tfNomemae.setText("");
                tfNomepai.setText("");
		tfEndereco.setText("");
		tfFone.setText("");
	}
	
	private void populaTextFields(Aluno a){
                tfNum_matricula.setValue(a.getNum_matricula());                
		tfNome.setText(a.getNome_pessoa());
                combosexo.setSelectedItem(a.getSexo());
                tfDatanascimento.setText(a.getDatanascimento());
                tfNomemae.setText(a.getNomemae());
                tfNomepai.setText(a.getNomepai());
		tfFone.setValue(a.getFone());
                tfEndereco.setText(a.getEndereco());		
	}
	
	protected Integer getNum_matricula(){
		try {
			return Integer.parseInt(tfNum_matricula.getText());
		} catch (Exception nex) {
			return null;
		}
	}
	
	private String validador() {
		StringBuilder sb = new StringBuilder();
		sb.append(tfNome.getText() == null || "".equals(tfNome.getText().trim()) ? "Nome, " : "");
                sb.append(combosexo.getSelectedItem()== null || "".equals(combosexo.getSelectedItem()) ? "Sexo, " : "");
                sb.append(tfDatanascimento.getText() == null || "".equals(tfDatanascimento.getText().trim()) ? "Data de Nascimento, " : "");
                sb.append(tfNomemae.getText() == null || "".equals(tfNomemae.getText().trim()) ? "Nome da Mãe, " : "");
        	sb.append(tfNomepai.getText() == null || "".equals(tfNomepai.getText().trim()) ? "Nome do Pai, " : "");
                sb.append(tfEndereco.getText() == null || "".equals(tfEndereco.getText().trim()) ? "Endereço, " : "");
		sb.append(tfFone.getText() == null || "".equals(tfFone.getText().trim()) ? "Telefone, " : "");
		
		if (!sb.toString().isEmpty()) {
			sb.delete(sb.toString().length()-2, sb.toString().length());
		}
		return sb.toString();
	}
	
	protected Aluno loadAlunoFromPanel() {
		String msg = validador();
		if (!msg.isEmpty()) {
			throw new RuntimeException("Informe o(s) campo(s): "+msg);
		}
                                   
		String nome_pessoa = tfNome.getText().trim();
		Object itemsexo = combosexo.getSelectedItem();
                String sexo = itemsexo.toString();
                String datanascimento = tfDatanascimento.getText().trim();
                String nomemae = tfNomemae.getText().trim();
                String nomepai = tfNomepai.getText().trim();
                String endereco = tfEndereco.getText().trim();
                String fone = tfFone.getText().trim();
		
		if (nome_pessoa.length() < 2) {
			throw new RuntimeException("O nome deve conter no mínimo 2 caracteres!");
		}	
		
		
		return new Aluno(null, nome_pessoa, sexo, datanascimento, nomemae, nomepai, endereco, fone);
	}
	
	public void setAluno(Aluno a){
		resetForm();
		if (a != null) {
			populaTextFields(a);
		}
	}
	
	private class CancelarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
			resetForm();
		}
	}
	
	private class SalvarAlunoListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				Aluno a = loadAlunoFromPanel();
				AlunoDAO dao = new AlunoDAOJDBC();
				dao.save(a);
				
				setVisible(false);
				resetForm();
				SwingUtilities.invokeLater(framePrincipal.newAtualizaAlunoAction());
				
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(IncluirAlunoFrame.this, 
						ex.getMessage(), "Erro ao incluir Aluno", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private class ExcluirAlunoListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Integer num_matricula = getNum_matricula();
			if (num_matricula != null) {
				try {
                                    
                                    
					AlunoDAO dao = new AlunoDAOJDBC();
					Aluno a = dao.findByNum_matricula(num_matricula);
					if (a != null) {
						dao.remove(a);
					}
					
					setVisible(false);
					resetForm();
					SwingUtilities.invokeLater(framePrincipal.newAtualizaAlunoAction());
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(IncluirAlunoFrame.this,
							ex.getMessage(), "Erro ao excluir Aluno", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
        public static MaskFormatter FormatoMascaraCampo() {  
  
            
            MaskFormatter formato = new MaskFormatter();  
  
            try{        
                formato.setMask("##-##-####");  
                formato.setPlaceholderCharacter('_'); 
                
            } 
            
            catch (Exception ex) {  
                 ex.printStackTrace();  
            }  
            return formato;  
        } 
   
}