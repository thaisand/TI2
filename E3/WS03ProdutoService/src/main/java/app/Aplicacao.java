package app;

import static spark.Spark.*;
import service.EmissaoNFService;


public class Aplicacao {
	
	private static EmissaoNFService EmissaoNFService = new EmissaoNFService();
	
    public static void main(String[] args) {
        port(6789);
        
        staticFiles.location("/public");
        
        post("/EmissaoNF/insert", (request, response) -> EmissaoNFService.insert(request, response));

        get("/EmissaoNF/:id", (request, response) -> EmissaoNFService.get(request, response));
        
        get("/EmissaoNF/list/:orderby", (request, response) -> EmissaoNFService.getAll(request, response));

        get("/EmissaoNF/update/:id", (request, response) -> EmissaoNFService.getToUpdate(request, response));
        
        post("/EmissaoNF/update/:id", (request, response) -> EmissaoNFService.update(request, response));
           
        get("/EmissaoNF/delete/:id", (request, response) -> EmissaoNFService.delete(request, response));

             
    }
}