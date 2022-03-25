package com.ti2cc;

import java.sql.*;

public class DAO {
	private Connection conexao;
	
	public DAO() {
		conexao = null;
	}
	
	public boolean conectar() {
		String driverName = "org.postgresql.Driver";                    
		String serverName = "localhost";
		String mydatabase = "teste";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
		String username = "ti2cc";
		String password = "ti@cc";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efetuada com o postgres!");
		} catch (ClassNotFoundException e) { 
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}
	
	public boolean close() {
		boolean status = false;
		
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
	
	public boolean inserirMatricula(Matricula matricula) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO matricula (codigo, login, senha, sexo) "
					       + "VALUES ("+matricula.getCodigo()+ ", '" + matricula.getLogin() + "', '"  
					       + matricula.getSenha() + "', '" + matricula.getSexo() + "');");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean atualizarMatricula(Matricula matricula) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE matricula SET login = '" + matricula.getLogin() + "', senha = '"  
				       + matricula.getSenha() + "', sexo = '" + matricula.getSexo() + "'"
					   + " WHERE codigo = " + matricula.getCodigo();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean excluirMatricula(int matricula) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM matricula WHERE matricula = " + matricula);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public Matricula[] getMatriculas() {
		Matricula[] matriculas = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM matricula");		
	         if(rs.next()){
	             rs.last();
	             matriculas = new Matricula[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                matriculas[i] = new Matricula(rs.getInt("matricula"), rs.getString("login"), 
	                		                  rs.getString("senha"), rs.getString("sexo").charAt(0));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return matriculas;
	}

	
	public Email[] getEmailsMasculinos() {
		Email[] emails = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM email WHERE email.sexo LIKE 'M'");		
	         if(rs.next()){
	             rs.last();
	             emails = new Email[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
		                emails[i] = new Email(rs.getInt("codigo"), rs.getString("login"), 
                         		                  rs.getString("senha"), rs.getString("sexo").charAt(0));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return emails;
	}
    public boolean inserirEmail(Email email) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO email (codigo, login, senha, sexo) "
					       + "VALUES ("+email.getCodigo()+ ", '" + email.getLogin() + "', '"  
					       + email.getSenha() + "', '" + email.getSexo() + "');");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean atualizarEmail(Email email) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE email SET login = '" + email.getLogin() + "', senha = '"  
				       + email.getSenha() + "', sexo = '" + email.getSexo() + "'"
					   + " WHERE codigo = " + email.getCodigo();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean excluirEmail(int email) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM email WHERE email = " + email);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public Email[] getEmails() {
		Email[] emails = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM email");		
	         if(rs.next()){
	             rs.last();
	             emails = new email[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                emails[i] = new email(rs.getInt("email"), rs.getString("login"), 
	                		                  rs.getString("senha"), rs.getString("sexo").charAt(0));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return emails;
	}

	
	public Email[] getEmailsMasculinos() {
		Email[] emails = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM email WHERE email.sexo LIKE 'M'");		
	         if(rs.next()){
	             rs.last();
	             emails = new email[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
		                emails[i] = new email(rs.getInt("codigo"), rs.getString("login"), 
                         		                  rs.getString("senha"), rs.getString("sexo").charAt(0));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return emails;
	}
}