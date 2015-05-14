
package br.com.oods.cboletim.model;

/**
 * Classe de modelo que representa um Secretario. 
 * @author Vinicius
 */
public class Secretario extends Pessoa {
    private Integer id_secretario;
    private String cpf;
    private String email;
    
    public Secretario(){
        
    }
    public Secretario(Integer id_secretario, Integer id_pessoa, String nome_pessoa, String sexo, String endereco, String fone, String cpf, String email){
        this.id_secretario = id_secretario;
        this.nome_pessoa = nome_pessoa;
        this.sexo = sexo;        
        this.endereco = endereco;
        this.fone = fone;
        this.cpf = cpf;
        this.email = email;
    }
    
    //getters and setters
    
     public Integer getId_secretario() {
	return id_secretario;
	}
	
    public void setId_secretario(Integer id_secretario) {
	this.id_secretario = id_secretario;
	}
    
    
    @Override
    public Integer getId_pessoa() {
	return super.getId_pessoa();
	}
	
    @Override
    public void setId_pessoa(Integer id_pessoa) {
	 super.setId_pessoa(id_pessoa);
	}
    @Override
    public String getNome_pessoa() {
        return super.getNome_pessoa(); //To change body of generated methods, choose Tools | Templates.
    }    

    @Override
    public void setNome_pessoa(String nome_pessoa) {
        super.setNome_pessoa(nome_pessoa); //To change body of generated methods, choose Tools | Templates.
    }   

    @Override
    public String getSexo() {
        return super.getSexo(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setSexo(String sexo) {
        super.setSexo(sexo); //To change body of generated methods, choose Tools | Templates.
    }    
    @Override
    public String getEndereco() {
        return super.getEndereco(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setEndereco(String endereco) {
        super.setEndereco(endereco); //To change body of generated methods, choose Tools | Templates.
    }  
     @Override
    public String getFone() {
        return super.getFone();
    }

    @Override
    public void setFone(String fone) {
        super.setFone(fone); 
    }
    public String getCpf(){
        return cpf;
    }
    public void setCpf(String cpf){
        this.cpf = cpf;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
}
