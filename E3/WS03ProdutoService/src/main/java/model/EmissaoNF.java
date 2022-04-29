package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class EmissaoNF {
	private int id;
	private String nomeCliente;
	private float precoProd;
	private int quantProd;
	private LocalDateTime dataPedido;	
	private LocalDate dataPagamento;
	
	public EmissaoNF() {
		id = -1;
		nomeCliente = "";
		precoProd = 0.00F;
		quantProd = 0;
		dataPedido = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
		dataPagamento = LocalDate.now().plusMonths(6); // o default é uma validade de 6 meses.
	}

	public EmissaoNF(int id, String nomeCliente, float precoProd, int quantProd, LocalDateTime fabricacao, LocalDate v) {
		setId(id);
		setNomeCliente(nomeCliente);
		setPrecoProd(precoProd);
		setQuantProd(quantProd);
		setDataPedido(fabricacao);
		setDataPagamento(v);
	}		
	
	public int getID() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public float getPrecoProd() {
		return precoProd;
	}

	public void setPrecoProd(float precoProd) {
		this.precoProd = precoProd;
	}

	public int getQuantProd() {
		return quantProd;
	}
	
	public void setQuantProd(int quantProd) {
		this.quantProd = quantProd;
	}
	
	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public LocalDateTime getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(LocalDateTime dataPedido) {
		// Pega a Data Atual
		LocalDateTime agora = LocalDateTime.now();
		// Garante que a data de fabricação não pode ser futura
		if (agora.compareTo(dataPedido) >= 0)
			this.dataPedido = dataPedido;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		// a data de fabricação deve ser anterior é data de validade.
		if (getDataPedido().isBefore(dataPagamento.atStartOfDay()))
			this.dataPagamento = dataPagamento;
	}

	public boolean emValidade() {
		return LocalDateTime.now().isBefore(this.getDataPagamento().atTime(23, 59));
	}


	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Nome cliente: " + nomeCliente + "   Preço: R$" + precoProd + "   Quantidade: " + quantProd + "   Data do pedido: "
				+ dataPedido  + "   Data do Pagamento: " + dataPagamento;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getID() == ((EmissaoNF) obj).getID());
	}	
}