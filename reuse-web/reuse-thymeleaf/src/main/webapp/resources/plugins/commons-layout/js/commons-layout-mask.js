//################ MASKMWH - Mascara do tipo MWH ###################################################################	
$('input[maskMWH=true]').each(function(index) {
	inserirMaskMWH($(this));
});

function inserirMaskMWH(input){
	if ($(input).val() != "" && $(input).val().split('.').length > 1 && $(input).val().split('.')[1].length == 1) {
		$(input).val($(input).val() + "0");
	}
	$(input).maskMoney({
		allowZero: true,
	    suffix : ' MWh',
	    thousands : '.',
	    decimal : ','
	});
		
	$(input).maskMoney('mask');
}

//################ MASKV - Mascara do tipo VALOR UNITARIO ###################################################################	
$('input[maskV=true]').each(function(index) {
	inserirMaskV($(this));
});

function inserirMaskV(input){
	if ($(input).val() != "" && $(input).val().split('.').length > 1 && $(input).val().split('.')[1].length == 1) {
		$(input).val($(input).val() + "0");
	}
	$(input).maskMoney({
		allowZero: true,
	    suffix : '',
	    thousands : '.',
	    decimal : ',',
	    precision : 0
	});
		
	$(input).maskMoney('mask');
}

//################ MASKD - Mascara do tipo DOLAR ###################################################################	
$('input[maskD=true]').each(function(index) {
	inserirMaskD($(this));
});

function inserirMaskD(input){
	if ($(this).val() != "" && $(this).val().split('.')[1].length == 1) {
		$(this).val($(this).val() + "0");
	}
	$(this).maskMoney({
	    prefix : '$ ',
	    thousands : ',',
	    decimal : '.'
	});
	$(this).maskMoney('mask');
}

//################ MASKP - Mascara do tipo PERCENTUAL ###################################################################	
$('input[maskP=true]').each(function(index) {
	inserirMaskP($(this));
});

function inserirMaskP(input){
	if ($(input).val() != "" && $(input).val().split('.').length > 1 && $(input).val().split('.')[1].length == 1) {
		$(input).val($(input).val() + "0");
    }
	
    $(input).maskMoney({
    	allowZero: true,
	    suffix : ' %',
	    thousands : '.',
	    decimal : ','
    });
    
    $(input).maskMoney('mask');
}

//################ MASKR - Mascara do tipo REAL ###################################################################	

//MASCARA MOEDA REAL(R$)
$('input[maskR=true]').each(function(index) {
	inserirMaskR($(this));
});

//INSERE A MASCARA REAL NO CAMPO DESEJADO
function inserirMaskR(input){
	if ($(input).val() != "" && $(input).val().split('.').length > 1 && $(input).val().split('.')[1].length == 1) {
	        $(input).val($(input).val() + "0");
	    }
    $(input).maskMoney({
        prefix : 'R$ ',
        thousands : '.',
        decimal : ',',
        allowZero: true
    });
    $(input).maskMoney('mask');
}


//MASCARA DE TELEFONE - EVENTO DE KEYDOWN PARA TRATAR O NUMERO DE DIGITOS DO TELEFONE
$('input[maskTEL=true]').each(function(index) {
    $(this).mask("(99) 9999-9999?9").keydown(function () {
        var $elem = $(this);
        var tamanhoAnterior = this.value.replace(/\D/g, '').length;
        
        setTimeout(function() { 
            var novoTamanho = $elem.val().replace(/\D/g, '').length;
            if (novoTamanho !== tamanhoAnterior) {
                if (novoTamanho === 11) {  
                    $elem.unmask();  
                    $elem.mask("(99) 99999-9999");  
                } else if (novoTamanho === 10) {  
                    $elem.unmask();  
                    $elem.mask("(99) 9999-9999?9");  
                }
            }
        }, 1);
    });
});

//MASCARA DE TELEFONE - VERIFICA O NUMERO DE DIGITOS DO TELEFONE E COLOCA A MASCARA ESPECIFICA
$('input[maskTEL=true]').each(function(index) {
	inserirMaskTEL($(this));
});

function inserirMaskTEL(input){
	var $elem = $(input);
    var tamanho = $(input).val().replace(/\D/g, '').length;
	
	if (tamanho === 11) {
        $elem.unmask();  
        $elem.mask("(99) 99999-9999");  
    } else if (tamanho === 10) {  
        $elem.unmask();  
        $elem.mask("(99) 9999-9999?9");  
    }
}

//MASCARA DE CPF
$('input[maskCPF=true]').each(function(index) {
	$(this).mask("999.999.999-99");
});

/**
 * Função responsavel por retirar todas as mascaras no momento do submit
 */
//$("form.smart-form").each(function(){
//	$(this).submit(function(e) { 
//		if($(this).valid()){
//	        $('input[maskD=true],input[maskR=true]').each(function(index) {
//	        	if( $(this).val().indexOf("$") > -1){
//	        		$(this).val($(this).maskMoney('unmasked')[0]);
//	        	}
//	        });
//	        
//	        $('input[maskV=true]').each(function(index) {
//	        	//if( $(this).val().indexOf("$") > -1){
//	        		$(this).val($(this).maskMoney('unmasked')[0]);
//	        	//}
//	        });
//
//	        $('input[maskMWH=true]').each(function(index) {
//	        	if( $(this).val().indexOf("MWh") > -1){
//	        		$(this).val($(this).maskMoney('unmasked')[0]);
//	        	}
//	        });
//	        
//	        $('input[maskP=true]').each(function(index) {
//	        	//Nas telas onde ocorre o recarregamento dos arquivos javascript,
//	        	//estava removendo a mascara duas vezes.
//	        	//Agora so remove se tiver o caracter 
//	        	if( $(this).val().indexOf("%") > -1){
//	        		$(this).val($(this).maskMoney('unmasked')[0]);
//	        	}
//	        });
//	        
//	        $('input[maskCPF=true]').each(function(index) {
//	        	$(this).val($(this).val().replace(/\D/g,''));
//	        });
//	        
//	        $('input[maskCNPJ=true]').each(function(index) {
//	        	$(this).val($(this).val().replace(/\D/g,''));
//	        });
//
//	        //Insere mascara de telefone para campos com o atributo maskTEL
//	        $('input[maskTEL=true]').each(function(index) {
//	        	var tel = parseFloat($(this).val().replace(/\D/g,''));
//	        	if( !$.isNumeric(tel) ){
//	        		$(this).val("");
//	        	}else{ 
//		        	$(this).val(tel);
//	        	}
//	        });
//	    }else{
//	        return false;
//	    }
//	});
//});