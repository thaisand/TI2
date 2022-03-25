package com.ti2cc;

public class Aluno {
	private int codigo;
	private String email;
	private String senha;
	private char telefone;
	
	public Aluno() {
		this.matricula = -1;
		this.email = "";
		this.senha = "";
		this.telefone = '*';
	}
	
	public Aluno(int matricula, String email, String senha, char telefone) {
		this.matricula = matricula;
		this.email = email;
		this.senha = senha;
		this.telefone = telefone;
	}

	public int getMatricula() {
		return matricula;
	}

	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public char getTelefone() {
		return telefone;
	}

	public void setTelefone(char telefone) {
		this.telefone = telefone;
	}

	@Override
	public String toString() {
		return "Aluno [codigo=" + codigo + ", email=" + email + ", senha=" + senha + ", telefone=" + telefone + "]";
	}	
}