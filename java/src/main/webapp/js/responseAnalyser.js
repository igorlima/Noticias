/*
 * Classe que trata a resposta de cada requisição realizada.
 * 
 */

function responseAnalyser( responseJSON, responseText ){
	alert( responseJSON.message );
}

function alertWithReturnObjectAndMessage( responseJSON, responseText ){
	if( responseJSON.status == "SUCCESS" ){
		alert( responseJSON.returnObject );
	}
	
	alert( responseJSON.message );
}

function drawVisualization_tabela_do_resultado_das_pesquisa_das_noticias( responseJSON, responseText ){
    // Create and populate the data table.
    var data = new google.visualization.DataTable();
    data.addColumn('number', 'nº');
    data.addColumn('string', 'Data');
    data.addColumn('string', 'Notícia');
    data.addColumn('string', 'Link');
    
    if( responseJSON != null && responseJSON.status == "SUCCESS" ){
    	Array.each( responseJSON.returnObject, function(obj,index){
    		data.addRows(1);
    		data.setCell(index, 0, index+1); //column n.º
    		data.setCell(index, 1, getStrDate(obj.data) ); //column data
    		data.setCell(index, 2, obj.manchete); //column noticia
    		data.setCell(index, 3, obj.link); //column link
		});
    }
    
	// Create and draw the visualization.
	visualization = new google.visualization.Table(document.getElementById('table'));
	visualization.draw(data, null);
    
	function getStrDate( time ){
		var d = new Date(time);
		var curr_date = d.getDate();
	  	var curr_month = d.getMonth() + 1; //months are zero based
	  	var curr_year = d.getFullYear();
	  	
	  	return curr_date+'/'+curr_month+'/'+curr_year;
	}
}
google.setOnLoadCallback(drawVisualization_tabela_do_resultado_das_pesquisa_das_noticias);
