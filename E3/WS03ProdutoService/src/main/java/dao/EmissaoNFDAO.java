package dao;

import model.EmissaoNF;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class EmissaoNFDAO extends DAO {	
	public EmissaoNFDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert(EmissaoNF EmissaoNF) {
		boolean status = false;
		try {
			String sql = "INSERT INTO EmissaoNF (nomecliente, precoproduto, quantidadeproduto, datapedido, datapagamento) "
		               + "VALUES ('" + EmissaoNF.getNomeCliente() + "', "
		               + EmissaoNF.getPrecoProd() + ", " + EmissaoNF.getQuantProd() + ", ?, ?);";
			PreparedStatement st = conexao.prepareStatement(sql);
		    st.setTimestamp(1, Timestamp.valueOf(EmissaoNF.getDataPedido()));
			st.setDate(2, Date.valueOf(EmissaoNF.getDataPagamento()));
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public EmissaoNF get(int id) {
		EmissaoNF EmissaoNF = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM EmissaoNF WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 EmissaoNF = new EmissaoNF(rs.getInt("id"), rs.getString("nomecliente"), (float)rs.getDouble("precoproduto"), 
	                				   rs.getInt("quantidadeproduto"), 
	        			               rs.getTimestamp("datapedido").toLocalDateTime(),
	        			               rs.getDate("datapagamento").toLocalDate());
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return EmissaoNF;
	}
	
	
	public List<EmissaoNF> get() {
		return get("");
	}

	
	public List<EmissaoNF> getOrderByID() {
		return get("id");		
	}
	
	
	public List<EmissaoNF> getOrderByNomeCliente() {
		return get("nomecliente");		
	}
	
	
	public List<EmissaoNF> getOrderByPrecoProd() {
		return get("precoproduto");		
	}
	
	
	private List<EmissaoNF> get(String orderBy) {
		List<EmissaoNF> EmissaoNFs = new ArrayList<EmissaoNF>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM EmissaoNF" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	EmissaoNF p = new EmissaoNF(rs.getInt("id"), rs.getString("nomecliente"), (float)rs.getDouble("precoproduto"), 
	        			                rs.getInt("quantidadeproduto"),
	        			                rs.getTimestamp("datapedido").toLocalDateTime(),
	        			                rs.getDate("datapagamento").toLocalDate());
	            EmissaoNFs.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return EmissaoNFs;
	}
	
	
	public boolean update(EmissaoNF EmissaoNF) {
		boolean status = false;
		try {  
			String sql = "UPDATE EmissaoNF SET nomecliente = '" + EmissaoNF.getNomeCliente() + "', "
					   + "precoproduto = " + EmissaoNF.getPrecoProd() + ", " 
					   + "quantidadeproduto = " + EmissaoNF.getQuantProd() + ","
					   + "datapedido = ?, " 
					   + "datapagamento = ? WHERE id = " + EmissaoNF.getID();
			PreparedStatement st = conexao.prepareStatement(sql);
		    st.setTimestamp(1, Timestamp.valueOf(EmissaoNF.getDataPedido()));
			st.setDate(2, Date.valueOf(EmissaoNF.getDataPagamento()));
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean delete(int id) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM EmissaoNF WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
}