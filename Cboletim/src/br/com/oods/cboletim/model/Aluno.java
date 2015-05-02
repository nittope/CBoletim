
package br.com.oods.cboletim.model;

/**
 * Classe de modelo que representa um Aluno. 
 * @author Vinicius
 */
public class Aluno extends Pessoa {
    private Integer num_matricula;
    private String datanascimento;
    private String nomemae;
    private String nomepai;
    
    public Aluno(){
        
	}
    public Aluno(Integer num_matricula, String nome_pessoa, String sexo, String datanascimento, String nomemae, String nomepai, String endereco, String fone){
        this.num_matricula = num_matricula;
        this.nome_pessoa = nome_pessoa;
        this.sexo = sexo;
        this.datanascimento = datanascimento;
        this.nomemae = nomemae;
        this.nomepai = nomepai;
        this.endereco = endereco;
        this.fone = fone;
    }
    
    public Integer getNum_matricula() {
	return num_matricula;
	}
	
    public void setNum_matricula(Integer num_matricula) {
	this.num_matricula = num_matricula;
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
    
    public String getDatanascimento(){
        return datanascimento;
    }
    public void setDatanascimento(String datanascimento){
        this.datanascimento = datanascimento;
    }
    public String getNomemae(){
        return nomemae;
    }
    public void setNomemae(String nomemae){
        this.nomemae = nomemae;
    }
    public String getNomepai(){
        return nomepai;
    }
    public void setNomepai(String nomepai){
        this.nomepai = nomepai;
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
    
    
    
    
    
    
    
}
