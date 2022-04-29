package service;

import java.util.Scanner;
import java.time.LocalDate;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import dao.EmissaoNFDAO;
import model.EmissaoNF;
import spark.Request;
import spark.Response;


public class EmissaoNFService {

	private EmissaoNFDAO EmissaoNFDAO = new EmissaoNFDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_nomeCliente = 2;
	private final int FORM_ORDERBY_PRECO = 3;
	
	
	public EmissaoNFService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new EmissaoNF(), FORM_ORDERBY_nomeCliente);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new EmissaoNF(), orderBy);
	}

	
	public void makeForm(int tipo, EmissaoNF EmissaoNF, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umEmissaoNF = "";
		if(tipo != FORM_INSERT) {
			umEmissaoNF += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umEmissaoNF += "\t\t<tr>";
			umEmissaoNF += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/EmissaoNF/list/1\">Novo EmissaoNF</a></b></font></td>";
			umEmissaoNF += "\t\t</tr>";
			umEmissaoNF += "\t</table>";
			umEmissaoNF += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/EmissaoNF/";
			String name, nomeCliente, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir nome do Cliente";
				nomeCliente = "Nome completo:";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + EmissaoNF.getID();
				name = "Atualizar EmissaoNF (ID " + EmissaoNF.getID() + ")";
				nomeCliente = EmissaoNF.getNomeCliente();
				buttonLabel = "Atualizar";
			}
			umEmissaoNF += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umEmissaoNF += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umEmissaoNF += "\t\t<tr>";
			umEmissaoNF += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umEmissaoNF += "\t\t</tr>";
			umEmissaoNF += "\t\t<tr>";
			umEmissaoNF += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umEmissaoNF += "\t\t</tr>";
			umEmissaoNF += "\t\t<tr>";
			umEmissaoNF += "\t\t\t<td>&nbsp;Nome do cliente: <input class=\"input--register\" type=\"text\" name=\"nomeCliente\" value=\""+ nomeCliente +"\"></td>";
			umEmissaoNF += "\t\t\t<td>Preco do produto: <input class=\"input--register\" type=\"text\" name=\"preco\" value=\""+ EmissaoNF.getPrecoProd() +"\"></td>";
			umEmissaoNF += "\t\t\t<td>Quantidade do produto: <input class=\"input--register\" type=\"text\" name=\"quantidade\" value=\""+ EmissaoNF.getQuantProd() +"\"></td>";
			umEmissaoNF += "\t\t</tr>";
			umEmissaoNF += "\t\t<tr>";
			umEmissaoNF += "\t\t\t<td>&nbsp;Data do pedido: <input class=\"input--register\" type=\"text\" name=\"dataFabricacao\" value=\""+ EmissaoNF.getDataPedido().toString() + "\"></td>";
			umEmissaoNF += "\t\t\t<td>Data do pagamento: <input class=\"input--register\" type=\"text\" name=\"dataValidade\" value=\""+ EmissaoNF.getDataPagamento().toString() + "\"></td>";
			umEmissaoNF += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umEmissaoNF += "\t\t</tr>";
			umEmissaoNF += "\t</table>";
			umEmissaoNF += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umEmissaoNF += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umEmissaoNF += "\t\t<tr>";
			umEmissaoNF += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar EmissaoNF (ID " + EmissaoNF.getID() + ")</b></font></td>";
			umEmissaoNF += "\t\t</tr>";
			umEmissaoNF += "\t\t<tr>";
			umEmissaoNF += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umEmissaoNF += "\t\t</tr>";
			umEmissaoNF += "\t\t<tr>";
			umEmissaoNF += "\t\t\t<td>&nbsp;Nome do cliente: "+ EmissaoNF.getNomeCliente() +"</td>";
			umEmissaoNF += "\t\t\t<td>Preco do produto: "+ EmissaoNF.getPrecoProd() +"</td>";
			umEmissaoNF += "\t\t\t<td>Quantidade do produto: "+ EmissaoNF.getQuantProd() +"</td>";
			umEmissaoNF += "\t\t</tr>";
			umEmissaoNF += "\t\t<tr>";
			umEmissaoNF += "\t\t\t<td>&nbsp;Data deo pedido: "+ EmissaoNF.getDataPedido().toString() + "</td>";
			umEmissaoNF += "\t\t\t<td>Data do pagamento: "+ EmissaoNF.getDataPagamento().toString() + "</td>";
			umEmissaoNF += "\t\t\t<td>&nbsp;</td>";
			umEmissaoNF += "\t\t</tr>";
			umEmissaoNF += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-EmissaoNF>", umEmissaoNF);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de EmissaoNFs</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/EmissaoNF/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/EmissaoNF/list/" + FORM_ORDERBY_nomeCliente + "\"><b>Nome do cliente</b></a></td>\n" +
        		"\t<td><a href=\"/EmissaoNF/list/" + FORM_ORDERBY_PRECO + "\"><b>Preço do produto</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<EmissaoNF> EmissaoNFs;
		if (orderBy == FORM_ORDERBY_ID) {                 	EmissaoNFs = EmissaoNFDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_nomeCliente) {		EmissaoNFs = EmissaoNFDAO.getOrderByNomeCliente();
		} else if (orderBy == FORM_ORDERBY_PRECO) {			EmissaoNFs = EmissaoNFDAO.getOrderByPrecoProd();
		} else {											EmissaoNFs = EmissaoNFDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (EmissaoNF p : EmissaoNFs) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getID() + "</td>\n" +
            		  "\t<td>" + p.getNomeCliente() + "</td>\n" +
            		  "\t<td>" + p.getPrecoProd() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/EmissaoNF/" + p.getID() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/EmissaoNF/update/" + p.getID() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteEmissaoNF('" + p.getID() + "', '" + p.getNomeCliente() + "', '" + p.getPrecoProd() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-EmissaoNF>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String nomeCliente = request.queryParams("nomeCliente");
		float precoProd = Float.parseFloat(request.queryParams("precoproduto"));
		int quantProd = Integer.parseInt(request.queryParams("quantidadeproduto"));
		LocalDateTime dataPedido = LocalDateTime.parse(request.queryParams("dataPedido"));
		LocalDate dataPagamento = LocalDate.parse(request.queryParams("dataPagamento"));
		
		String resp = "";
		
		EmissaoNF EmissaoNF = new EmissaoNF(-1, nomeCliente, precoProd, quantProd, dataPedido, dataPagamento);
		
		if(EmissaoNFDAO.insert(EmissaoNF) == true) {
            resp = "EmissaoNF (" + nomeCliente + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "EmissaoNF (" + nomeCliente + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		EmissaoNF EmissaoNF = (EmissaoNF) EmissaoNFDAO.get(id);
		
		if (EmissaoNF != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, EmissaoNF, FORM_ORDERBY_nomeCliente);
        } else {
            response.status(404); // 404 Not found
            String resp = "EmissaoNF " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		EmissaoNF EmissaoNF = (EmissaoNF) EmissaoNFDAO.get(id);
		
		if (EmissaoNF != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, EmissaoNF, FORM_ORDERBY_nomeCliente);
        } else {
            response.status(404); // 404 Not found
            String resp = "EmissaoNF " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		EmissaoNF EmissaoNF = EmissaoNFDAO.get(id);
        String resp = "";       

        if (EmissaoNF != null) {
        	EmissaoNF.setNomeCliente(request.queryParams("nomeCliente"));
        	EmissaoNF.setPrecoProd(Float.parseFloat(request.queryParams("precoProd")));
        	EmissaoNF.setQuantProd(Integer.parseInt(request.queryParams("quantProd")));
        	EmissaoNF.setDataPedido(LocalDateTime.parse(request.queryParams("dataPedido")));
        	EmissaoNF.setDataPagamento(LocalDate.parse(request.queryParams("dataPagamento")));
        	EmissaoNFDAO.update(EmissaoNF);
        	response.status(200); // success
            resp = "EmissaoNF (ID " + EmissaoNF.getID() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "EmissaoNF (ID \" + EmissaoNF.getId() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        EmissaoNF EmissaoNF = EmissaoNFDAO.get(id);
        String resp = "";       

        if (EmissaoNF != null) {
            EmissaoNFDAO.delete(id);
            response.status(200); // success
            resp = "EmissaoNF (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "EmissaoNF (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}