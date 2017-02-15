$(document).ready(function(){
	$.validator.setDefaults({
	  debug: true
	});	
	
	if(isIE()){
		$.extend(jQuery.validator.methods, { 
	       number: function(value, element) { 
	    	   return this.optional(element) 
	    	   || /^-?(?:\d+|\d{1,3}(?:\.\d{3})+)(?:,\d+)?$/.test(value)
	       },
	       max: function(value, element) {
	    	   if(parseFloat(value.replace(',','.')) <= parseFloat(element.max.replace(',','.'))){
	    		   return true;
	    	   }else{
	    		   return false;
	    	   }
	       },
	       min: function(value, element) {
	    	   if(parseFloat(value.replace(',','.')) >= parseFloat(element.min.replace(',','.'))){
	    		   return true;
	    	   }else{ 
	    		   return false;
	    	   }
	       },
	       range:function(value, element) {
	    	   if(parseFloat(value.replace(',','.')) >= parseFloat(element.min.replace(',','.')) && parseFloat(value.replace(',','.')) <= parseFloat(element.max.replace(',','.'))){
	    		   return true;
	    	   }else{
	    		   return false;
	    	   }
	       }
		});
		
		$("form").find("input").each(function(){
			if(this.value.indexOf(".")!=-1 && this.type == "number"){
				this.value = this.value.replace(".",",");
			}
		});
	}	
	
	$("form").each(function(){
		$(this).validate({
			ignore: ".novalidate",
			errorClass : "invalid",
			errorPlacement : function(error, element) {
				if( $(element).prop('class').indexOf("select2") >= 0 ){
					 var label = document.createElement("label");
					 label.setAttribute("id", "saida-error" );
					 label.setAttribute("class", "invalid" );
					 label.setAttribute("for", $(element).attr('id') );
					 label.innerHTML = "Campo é de preenchimento obrigatório.";
					 
					 $(label).insertAfter( $( element ).siblings( "span" ) );
					 $( element ).siblings( "span" ).find('.select2-selection').addClass("invalid");
				} else{
					if( $(element).prop('class').indexOf("hasDatepicker") >= 0 ){
						
						 var label = document.createElement("label");
						 label.setAttribute("id", "saida-error" );
						 label.setAttribute("class", "invalidDatepicker" );
						 label.setAttribute("for", $(element).attr('id') );
						 label.innerHTML = "Campo é de preenchimento obrigatório.";
						 
						 $(label).insertAfter(element);
					}else{
						error.insertAfter(element);
					}
				}
			}, 
			submitHandler: function(form) { 
				$(form).find('input[maskD],input[maskR],input[maskP],input[maskV]').each(function(){
					if( this.value.indexOf(",") !=- 1) {
						this.value = this.value.replace(",",".");
					}
				});
				
			 	$('body').loading({
					stoppable : false,
					message : '<div class="loading" style="z-index:1900!important"> <span>Processando...</span></div>'
				}); 
			 	
	 			if($("form.smart-form").valid()){
	 		        $('input[maskD=true],input[maskR=true]').each(function(index) {
	 		        	if( $(this).val().indexOf("$") > -1){
	 		        		$(this).val($(this).maskMoney('unmasked')[0]);
	 		        	}
	 		        });
	 		        
	 		        $('input[maskV=true]').each(function(index) {
	 		        	//if( $(this).val().indexOf("$") > -1){
	 		        		$(this).val($(this).maskMoney('unmasked')[0]);
	 		        	//}
	 		        });

	 		        $('input[maskMWH=true]').each(function(index) {
	 		        	if( $(this).val().indexOf("MWh") > -1){
	 		        		$(this).val($(this).maskMoney('unmasked')[0]);
	 		        	}
	 		        });
	 		        
	 		        $('input[maskP=true]').each(function(index) {
	 		        	//Nas telas onde ocorre o recarregamento dos arquivos javascript,
	 		        	//estava removendo a mascara duas vezes.
	 		        	//Agora so remove se tiver o caracter 
	 		        	if( $(this).val().indexOf("%") > -1){
	 		        		$(this).val($(this).maskMoney('unmasked')[0]);
	 		        	}
	 		        });
	 		        
	 		        $('input[maskCPF=true]').each(function(index) {
	 		        	$(this).val($(this).val().replace(/\D/g,''));
	 		        });
	 		        
	 		        $('input[maskCNPJ=true]').each(function(index) {
	 		        	$(this).val($(this).val().replace(/\D/g,''));
	 		        });

	 		        //Insere mascara de telefone para campos com o atributo maskTEL
	 		        $('input[maskTEL=true]').each(function(index) {
	 		        	var tel = parseFloat($(this).val().replace(/\D/g,''));
	 		        	if( !$.isNumeric(tel) ){
	 		        		$(this).val("");
	 		        	}else{ 
	 			        	$(this).val(tel);
	 		        	}
	 		        });
	 		        
	 		       //Caso o valor do input seja vazio("") seta o valor null
	 		        $.each( $(form).find('input') , function(index ,value){ 
	 		    	   if( $(this).val() == "" ){
	 		    		  $(this).prop( "disabled", true );
	 		    	   }
	 		    	});
	 		        
	 		       //Caso o valor do input seja vazio("") seta o valor null
	 		       $.each( $(form).find('select') , function(index ,value){ 
	 		    	   if( $(this).val() == "" ){
	 		    		  $(this).prop('selectedIndex' , -1);
	 		    	   }
	 		    	});
	 		        
	 		       form.submit();
	 		    }else{
	 		    	$('body').loading('stop');
	 		        return false;
	 		    }
			},
			rules:{
				inputRadioValidation:{
			       required: function() {
					    return $('[optionRadio=true]:checked').length === 0; 
					}
			    },
			},
			messages:{
				inputRadioValidation:{
			        required:"Campo é de preenchimento obrigatório."
			    },
			}
		}); 
	});

//########## REQUIRED - INPUTS SEM MASCARA ###############################################################
	//REQUIRED - INPUTS TYPE TEXT SEM MASCARA
	$("input[required]:not([maskR]):not([maskP]):not([maskD]):not([maskV])").each(function(index) {
		validarInputsSemMascara($(this));
	});
	
//################ REQUIRED - INPUTS TYPE SELECT ###################################################################	
	
	//REQUIRED - INPUTS TYPE SELECT
	$("select[required]").each(function(index) {
		validarSelectRequired($(this));
	});
	
//################ REQUIRED - TEXTAREA ###################################################################	
	
	//REQUIRED - INPUTS TYPE TEXT SEM MASCARA
	$("textarea[required]").each(function(index) {
		validarTextAreaSemMascara($(this));
	});
	
	//FUNCTION DE VALIDAÇÃO INPUTS TYPE TEXT SEM MASCARA
	function validarTextAreaSemMascara(input){
		/* Utiliza por padrão o placeholder caso não encontre, utiliza o texto do label */
		var field = $(input).attr("placeholder") != null ? $(input).attr("placeholder") : $(input).parent().find("label").text();
		
		$(input).rules("add", {
			required : function(element) {
				if( !$(element).val().trim()) {
					$(element).val("");
					return true;
				}
		        return false;
		      },
			messages : {
				required : "Campo é de preenchimento obrigatório."
			}
		});
	}
	
//################ INPUTS TYPE EMAIL - VALIDACAO FORMATO ###################################################################	
	
	//INPUTS TYPE EMAIL
	$("input[type=email]").each(function(index) {
		validarEmailFormato($(this));
	});	
	
	function validarEmailFormato(input){
		/* Utiliza por padrão o placeholder caso não encontre, utiliza o texto do label */
		$(input).rules("add", {
			email: true,
			messages : {
				email : "O e-mail informado não é válido, Favor verificar."
			}
		});
	}
	
//################ INPUTS TYPE NUMBER ###################################################################	
	
	//INPUT TYPE NUMBER VALORES VALIDOS
	$("input[type=number]").each(function(index) {
		adicionarRegraTypeNumber($(this));
	});
	
	//INPUTS TYPE NUMBER COM MIN E MAX
	$("input[type=number][min][max]").each(function(index) {
		var field = $(this).attr("placeholder") != null ? $(this).attr("placeholder") : $(this).parent().find("label").text();
		var min=$(this).attr("min");
		var max=$(this).attr("max");
		if(min != undefined && max != undefined){
			$(this).rules("add", {
				range : [min,max],
				messages : {
					range : "Valor do campo deve estar entre " + min + " e " + max	+ ".",
					max : "Valor do campo deve estar entre " + min + " e " + max	+ ".",
					min : "Valor do campo deve estar entre " + min + " e " + max	+ "."
				}
			});
		}
	});
//	
	//VALIDACAO DE DIGITACAO CAMPOS TYPE NUMBER - ACEITA SOMENTE NUMEROS
	$("input[type=number]").keydown(function(e) {
		// Allow: backspace, delete, tab, escape, enter and .
		if ($.inArray(e.keyCode,[46, 8, 9, 27, 13]) !== -1 || // [46, 8, 9, 27, 13, 194]) !== -1 ||
//		// Allow: Ctrl+A
		(e.keyCode == 65 && e.ctrlKey === true) ||
//		// Allow: home, end, left, right, down, up
		(e.keyCode >= 35 && e.keyCode <= 40)) {
//			// let it happen, don't do anything
			return;
		}
		// Ensure that it is a number and stop the keypress
		if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
			e.preventDefault();
		}
	});
	
//################ VALIDAÇÕES DE CAMPOS QUE CONTENHAM MASCARAS(MONETARIA,PERCENTUAL,UNITARIO) #################################	
	
	//REQUIRED - INPUTS TYPE TEXT COM MASCARAS(MASKR,MASKD,MASKP)
	$("input[required][maskR] , input[required][maskD] , input[required][maskP] , input[required][maskMWH]").each(function(index) {
		/* Utiliza por padrão o placeholder caso não encontre, utiliza o texto do label */
		$(this).rules("add", {
			maskValidation: true		
		});
	});
	
	//INPUTS TYPE TEXT COM MIN E MAX
	$("input[type=text][min][max]").each(function(index) {
		adicionarRegraRangeInputsMascara($(this));
	});
	
	//INPUTS TYPE TEXT SOMENTE COM MIN
	$("input[type=text][maiorZero='true']").each(function(index) {
		adicionarRegraMaiorZero($(this));
	});
	
	//VALIDACAO REQUIRED PARA CAMPOS COM MASCARA
	$.validator.addMethod("maskValidation", function(value, element , params) {
		var valorCampo = $(element).maskMoney('unmasked')[0];
		
		if((valorCampo == "" && value == "")){ //|| valorCampo == 0){
			return false;
		}else{
			var permiteZero = $(element).attr('permiteZero');
			if(valorCampo == 0 && (permiteZero == "false" || permiteZero == undefined )){
				return false;
			}else{
				return true;
			}
		}
	}, "Campo é de preenchimento obrigatório.");
	
	//VALIDACAO RANGE PARA CAMPOS COM MASCARA
	$.validator.addMethod("maskValidationRange", function(value, element, param) {
	    var valorCampo = parseFloat($(element).maskMoney('unmasked')[0]); 
	    
	    var min = parseFloat($(element).attr("min").replace(",","."));
		var max = parseFloat($(element).attr("max").replace(",","."));
		
	    if(valorCampo < min || valorCampo > max){
	        return false;
	    }else{
	        return true;
	    }
	},"Valor do campo deve estar entre {0} e {1}.");
	
	//VALIDACAO RANGE PARA CAMPOS COM MASCARA
	$.validator.addMethod("maskValidationRangeMaiorZero", function(value, element, param) {
	    var valorCampo = parseFloat($(element).maskMoney('unmasked')[0]); 
		
	    if(valorCampo <= 0){
	        return false;
	    }else{
	        return true;
	    }
	},"Valor deve ser maior que 0.");
	
	
//################ VALIDAÇÕES EM GERAL #################################	
	//VALIDACAO DE INPUTS LAT/LONG
	$("input[latlong=true]").each(function(index) {
		$(this).rules("add", {
			latLongValidation: true
		});
	});
	
	//VALIDACAO DE MAXIMO DE CARACTERES NO CAMPO
	$("input[maxlength]").keypress(function(event) {
		var key = event.which;
		// all keys including return.
		if (key >= 33 || key == 13 || key == 32) {
			var maxLength = $(this).attr("maxlength");
			var length = this.value.length;
			if (length >= maxLength) {
				event.preventDefault();
			}
		}
	});
	
	//REMOÇÃO DA VALIDACAO DE ACCEPT(EXTENSAO DO ARQUIVO ACEITO NO INPUT).
	//A VALIDACAO É FEITA PELO IMPORTADOR JAVA
	$("input[type=file]").each(function(index) {
		/* Utiliza por padrão o placeholder caso não encontre, utiliza o texto do label */
		$(this).rules("add", {
			accept : false
		});
	});
	
	//VALIDACAO CAMPOS LATITUDE/LONGITUDE
	$.validator.addMethod("latLongValidation", function(value, element, param) {
		var valorCampo = value;
	    if($.isNumeric(valorCampo) && (-90 <= valorCampo) && (valorCampo <= 90)){
	    	return true;
	    }else{
	    	return false;
	    }
	}, "Informe uma coordenada válida.");
});

function validarSelectRequired(input){
	/* Utiliza por padrão o placeholder caso não encontre, utiliza o texto do label */
	var field = $(input).attr("placeholder") != null ? $(input).attr("placeholder") : $(input).parent().find("label").text();
	$(input).rules("add", {
		required : true,
		messages : { 
			required : "Campo é de preenchimento obrigatório."
		}
	});
}

function adicionarRegraMaiorZero(input){
	
	//desabilita as validações do tipo range,min,max
	$(input).rules("add", {
		min: false,
		max: false,
		range: false
	});
	
	$(input).rules("add", {
		maskValidationRangeMaiorZero:true		
	});
}

function adicionarRegraRangeInputsMascara(input){
	var min = $(input).attr("min");
	var max = $(input).attr("max");
	//desabilita as validações do tipo range,min,max
	$(input).rules("add", {
		min: false,
		max: false,
		range: false
	});
	
	$(input).rules("add", {
		maskValidationRange: [min,max]		
	});
}

function adicionarRegraTypeNumber(input){
	$(input).rules("add", {
		number : true,
		messages : {
			number : "Favor informar um valor numérico válido."
		}
	});
}

function adicionarRegraMask(input){
	$(input).rules("add", {
		maskValidation: true		
	});
}

//FUNCTION DE VALIDAÇÃO INPUTS TYPE TEXT SEM MASCARA
function validarInputsSemMascara(input){
	/* Utiliza por padrão o placeholder caso não encontre, utiliza o texto do label */
	var field = $(input).attr("placeholder") != null ? $(input).attr("placeholder") : $(input).parent().find("label").text();
	
	$(input).rules("add", {
		required : function(element) {
			if( !$(element).val().trim() && $(element).attr('required') != undefined ) {
				$(element).val("");
				return true;
			}
	        return false;
	      },
		messages : {
			required : "Campo é de preenchimento obrigatório."
		}
	});
}