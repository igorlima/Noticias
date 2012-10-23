var URL_PESQUISAR_NOTICIAS = "/Noticias/noticia/dataInicio/{dataInicio}/dataFim/{dataFim}.json";

/*
 *  The request can be 'get' and/or 'post'
 *  Função genérica para realizar os GETE os POST.
 */
function doRequest (strUrl,jsonData,typeRequest,onSuccessFunction,onFailureFunction,isAsync){
	if( $type(jsonData) == 'object'  ){ jsonData = JSON.encode(jsonData); }
	if( isAsync == null ){ isAsync = false; }
	
	
	var request = new Request.JSON({
		method: typeRequest, 
		url: strUrl,
		data: jsonData,
		urlEncoded: false,
		async: isAsync,
		headers: {		 	
			'Content-type': 'application/json; charset=utf8',
			'X-Request': 'JSON'
		},
		onSuccess: function(responseJSON, responseText){
			if( onSuccessFunction != null )
				onSuccessFunction(responseJSON, responseText);
		},
		onFailure: function(xhr){
			//Arguments: xhr (XMLHttpRequest) The transport instance.
			if( onFailureFunction != null )
				onFailureFunction(xhr);
	    }
	});
	
	request.send();
	
}
