package br.com.oods.cboletim.model;

/**
 * Classe de modelo que representa um usuário. 
 * @author Vinicius
 */


public class Pessoa {

	protected Integer id_pessoa;
        protected Integer tipoacesso_pessoa;
	protected String nome_pessoa;
	protected String sexo;
	protected String endereco;
	protected String fone;
        protected String login;
	protected String senha;

    
        
	
	
	
	public Pessoa(){
	}
	
	public Pessoa(Integer id_pessoa, Integer tipoacesso_pessoa, String nome_pessoa, String sexo, String endereco, String fone, String login, String senha) {
		this.id_pessoa = id_pessoa;
		this.tipoacesso_pessoa = tipoacesso_pessoa;
		this.nome_pessoa = nome_pessoa;
                this.sexo = sexo;
		this.endereco = endereco;
		this.fone = fone;
                this.login = login;
		this.senha = senha;
	}
	//Getters and Setters
	public Integer getId_pessoa() {
		return id_pessoa;
	}
	
	public void setId_pessoa(Integer id_pessoa) {
		this.id_pessoa = id_pessoa;
	}
	
	public Integer getTipoacesso_pessoa() {
		return tipoacesso_pessoa;
	}
	
	public void setTipoacesso_pessoa(Integer tipoacesso_pessoa) {
		this.tipoacesso_pessoa = tipoacesso_pessoa;
	}
	
	public String getNome_pessoa() {
		return nome_pessoa;
	}
	
	public void setNome_pessoa(String nome_pessoa) {
		this.nome_pessoa = nome_pessoa;
	}
	
	public String getSexo() {
		return sexo;
	}
	
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
        public String getEndereco() {
		return endereco;
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}
        
        public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

	
}
