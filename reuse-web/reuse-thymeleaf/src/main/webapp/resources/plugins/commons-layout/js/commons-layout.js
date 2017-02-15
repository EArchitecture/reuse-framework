
$(document).on('click', '[data-toggle="confirm_deletion"]', function(e) {
	var $this = $(this);
	var url = $this.data('href');
	var message = $this.data('message');
	bootbox.dialog({
		message : message,
		title : "Confirmação",
		language : "br",
		buttons : {
			success : {
				label : "Confirma",
				className : "btn-success",
				callback : function() {
					window.location = url;
				}
			},
			danger : {
				label : "Cancela",
				className : "btn-danger",
				callback : function() {

				}
			}
		}
	});

});

// SCRIPTS

// //////// BOOTSTRAP /////////

$('[data-toggle="dropdown"]').dropdown();
$('[data-toggle="tooltip"], .hastooltip').tooltip({
	container : 'body',
	delay : 500
});
/* AFFIX */

$('thead[data-spy="affix"]').on('affix.bs.affix', function() {
	var width = $(this).parents('table').width();
	$(this).css('width', width);
});

// //////// DROPDOWNS /////////

$('.dropdown-menu a[role="tab"]').click(function(e) {
	e.preventDefault();
	$(this).tab('show');
	return false
});
$('.dropdown-menu').on(
		'hide.bs.dropdown',
		function() {
			$(this).parents('.dropdown').find('.dropdown-toggle').removeClass(
					'active');
		});
// dropdown select
$('.dropdown-select').on(
		'click',
		'.dropdown-menu li a',
		function() {
			var target = $(this).html();

			// Adds active class to selected item
			$(this).parents('.dropdown-menu').find('li').removeClass('active');
			$(this).parent('li').addClass('active');

			// Displays selected text on dropdown-toggle button
			$(this).parents('.dropdown-select').find(
					'.dropdown-toggle span:first-child').html(target);

			// Closes dropdown
			$(this).parents('.dropdown-select').find('.dropdown-toggle')
					.dropdown('toggle');

			return false
		});

$('.fix-menu').click(function() {
	$("body").removeClass('menu-auto').addClass('menu-fix');
	DataCommonsLayout.put("menu-project", "menu-fix");
});

$('.auto-menu').click(function() {
	$("body").removeClass('menu-fix').addClass('menu-auto');
	DataCommonsLayout.put("menu-project", "menu-auto");
});

// Carrega definição de menu do usuario.
$(document).ready(function() {
	if (DataCommonsLayout.get("menu-project") == 'menu-auto') {
		$("body").removeClass('menu-fix').addClass('menu-auto');
	} else if (DataCommonsLayout.get("menu-project") == 'menu-fix') {
		$("body").removeClass('menu-auto').addClass('menu-fix');
	}else {
		$("body").removeClass('menu-auto').addClass('menu-fix');
	}
});

// //////// BUTTONS /////////

// BOOKMARK BTN
$('.bookmark-button').click(
		function() {
			if ($(this).hasClass('marked')) {
				$(this).removeClass('marked');
				$(this).find('.fa').removeClass('fa-bookmark').addClass(
						'fa-bookmark-o');
			} else {
				$(this).addClass('marked');
				$(this).find('.fa').removeClass('fa-bookmark-o').addClass(
						'fa-bookmark');
			}
		});

// SHOW-MORE BTN (...)
$('.show-more').click(
		function() {
			$(this).parents('.expandable-container').find('.hidden-item')
					.slideToggle();
			return false
		});

// REFRESH BUTTON
$('.refresh-btn').click(function() {
	$(this).addClass('remaining').delay(3000).queue(function() {
		$(this).removeClass('remaining').dequeue();
	});
	$(this).addClass('finishing').delay(3000).queue(function() {
		$(this).removeClass('finishing').dequeue();
	});
});

// PDA AND ALARM BUTTONS
$('.alarm-btn, .pda-btn').click(function() {
	$(this).toggleClass('active');
	return false
});

// ENTITY CLICK
$('.entity-button').click(
		function(event) {
			$(this).parents('.dropdown').find('.dropdown-menu').first().stop(
					true, true).slideToggle();
			return false;
		});
$('.entity-button').on('contextmenu', function(event) {
	console.log('right Mouse button pressed.');
	event.preventDefault();
});
$('.map-bg').click(function(event) {
	$('.dropdown-entity').slideUp();
});

// CLICKABLE AREA
// WHEREVER THERE ARE TWO ICONS TOO CLOSE ON THE MAP
$('.click-area .dropdown-menu > li > a').hover(
		function() {
			var icontarget = $(this).attr('href') + "-icon";
			$(this).parents('.click-area').find('.click-area-icon')
					.removeClass('active');
			$(this).parents('.click-area').find(icontarget).addClass('active');
		});
$('.click-area .dropdown-menu').on(
		'hide.bs.dropdown',
		function() {
			$(this).parents('.click-area').find('.click-area-icon')
					.removeClass('active');
		});

// HELP BUTTON
$('.help-container .help-btn').click(function() {
	$(this).parents('.help-container').find('.help-content').slideToggle();
	return false
});

// //////// NAVBAR /////////

// TOGGLE MENU
$('.menus-container').on("click", ".toggle-menu-btn", function() {
	var btn = $('.menus-container .toggle-menu-btn');
	var menus = $('.menus-container .togglable-menus');

	if (btn.hasClass('disabled')) {
		return false
	} else {
		if (menus.find('nav').hasClass('hidden-item')) {
			btn.text('Esconder menu');
			menus.find('nav').removeClass('hidden-item');
			$('#content').addClass('hasmenu');
		} else {
			btn.text('Exibir menu');
			menus.find('nav').addClass('hidden-item');
			$('#content').removeClass('hasmenu');
		}
	}
});

// AUTOMATIC-MENU
$('.menus-container').on("click", ".automatic-menu-btn", function() {
	var menus = $('.menus-container .togglable-menus');
	var menubtn = $('.menus-container .toggle-menu-btn');
	var btn = $('.menus-container .automatic-menu-btn');

	if (!menus.hasClass('automatic')) {
		btn.prepend('<i class="fa fa-check"></i> &nbsp;');
		menubtn.addClass('disabled');
		menus.addClass('automatic');
		if (!menus.find('nav').hasClass('.hidden-item')) {
			menus.find('nav').addClass('hidden-item');
			menubtn.text('Exibir menu');
			$('#content').removeClass('hasmenu');
		} else {
		}
	} else {
		btn.text('Exibir menu automaticamente');
		menubtn.removeClass('disabled');
		menus.removeClass('automatic');
		menus.find('nav').removeClass('hidden-item');
		menubtn.text('Esconder menu');
		$('#content').addClass('hasmenu');
	}
});

// USER MENU
$('.user-menu .user-menu-link').click(
		function() {
			var target = $(this).attr('href');

			$(this).parents('.nav').find('.user-menu > .dropdown-menu > li')
					.removeClass('active');
			$(this).parent().addClass('active');
			$(this).parents('.user-menu').find('.user-menu-content').addClass(
					'open');
			$(this).parents('.user-menu').find('.tab').not(target).removeClass(
					'open');
			$(this).parents('.user-menu').find(target).addClass('open');
			return false
		});
$('.user-menu').on(
		'hide.bs.dropdown',
		function() {
			$(this).find('.user-menu-content').removeClass('open');
			$(this).removeClass('active');
			$(this).parent().find('.user-menu > .dropdown-menu > li')
					.removeClass('active');
		});
$('.user-menu-content *').click(function(e) {
	e.preventDefault();
	$(this).tab('show');
	return false
});

// //////// FORMS /////////

// DATE PICKER
// $('.datepicker').datetimepicker({
// lang : 'pt-BR',
// timepicker : false,
// format : 'd/m/Y',
// closeOnDateSelect : true
// });

// DATE TIME PICKER
$('.datetimepicker').datetimepicker({
	lang : 'pt-BR',
	format : 'd/m/Y H:i',
});

// ALPHANUMERIC
$(".input-alphanumeric-container > input[type='text']").numeric({
	allowMinus : false,
	allowThouSep : false,
	allowDecSep : false
});

// NO SPECIAL CHAR
$(
		".input-latin-container > input[type='text'], .input-latin-container > textarea")
		.alphanum({
			allow : '!?@":.,*-+/()[]=$#'
		});

// MONEY INPUT
$(".input-precision-container > input[type='text']").maskMoney({
	thousands : '.',
	decimal : ',',
	affixesStay : false
});

// //MAXLENGTH
// //IMPORTANT! though that if you are setting the maxlength via JavaScript,
// someone else could just as easily change the maxlength to whatever they like.
// You
// will still need to validate the submitted data on the server.
$('input[type="text"]').not('.input-counter-container .form-control').keypress(
		limitMe);
function limitMe(e) {
	if ($(this).attr("maxLength") != null) {
		if (e.keyCode == 8) {
			return true;
		}
		return this.value.length < $(this).attr("maxLength");
	}
}

// CHARACTERES COUNTER
$('.input-counter-container .form-control').keyup(function() {
	var len = $(this).val().length;
	if (len >= 31) {
		$(this).val($(this).val().substring(0, 30));
	} else {
		$('.input-counter').text(30 - len);
		if (len > 24) {
			$('.input-counter').css('color', 'red');
		} else {
			$('.input-counter').css('color', 'inherit');
		}
	}
});

// EDITABLE FIELDS
$('.editable-field-btn').click(
		function() {
			$(this).parents('.editable-field-container').find(
					'.editable-field-form').slideDown();
			$(this).parents('.editable-field-container')
					.find('.editable-value').slideUp();
			$(this).parents('.editable-field-container').addClass(
					'editing-field');
			return false
		});
$('.editable-field-container .cancel, .editable-field-container .save').click(
		function() {
			$(this).parents('.editable-field-container').find(
					'.editable-field-form').slideUp();
			$(this).parents('.editable-field-container')
					.find('.editable-value').slideDown();
			$(this).parents('.editable-field-container').removeClass(
					'editing-field');
			return false
		});

// SEARCH BOX
$("#navigation .contextual-search")
		.bind(
				'focus',
				function() {
					var menu = $(this).parents('.search-form').find(
							'.search-options');
					if (!menu.hasClass('open')) {
						menu.slideDown('fast').addClass('open');
					} else {
					}
					$(document)
							.bind(
									'mousedown',
									function(e) {
										if (!$(e.target)
												.closest(
														'.search-options, #navigation .contextual-search').length) {
											menu
													.removeClass('open')
													.slideUp(
															'fast',
															function() {
																$(
																		'#navigation .expand-menu')
																		.show();
																$(
																		'#navigation .hidden-item')
																		.hide();
															});
											$(document).unbind('mousedown',
													arguments.callee);
											$("#navigation .contextual-search")
													.css('width', 215);
										}
									})
				});

// SEARCH EXPAND MENU
$('#navigation .expand-menu').click(function() {
	var totalWidth = 0;
	$(this).hide();
	$(this).parents('.nav').find('.hidden-item').show();
	$(this).parents('.nav').find('li').each(function(index) {
		totalWidth += parseInt($(this).width(), 10);
	});
	$("#navigation .contextual-search").css('width', totalWidth);
	return false
});

// LAST SEARCHED ITEMS
$('.last-results-container .contextual-search').focus(
		function() {
			$(this).parents('.last-results-container').find('.last-results')
					.slideDown('fast');
		}).blur(
		function() {
			$(this).parents('.last-results-container').find('.last-results')
					.slideUp('fast');
		});

// DROPNAV
$('.dropnav-toggle').click(
		function() {
			var target = $(this).attr('href');
			$(this).parents('.nav').find('li').removeClass('active');
			$(this).parent().addClass('active');
			$(this).parents('.dropnav-container').find('.dropnav .nav')
					.removeClass('active');
			$(this).parents('.dropnav-container').find(target).addClass(
					'active');
			return false
		});

// //////// TABLES /////////

// TABLE CHECKBOX
$('.check-table-row').change(function() {
	var tablerow = $(this).parents('tr');
	if (this.checked) {
		tablerow.addClass('active')
	} else {
		tablerow.removeClass('active')
	}

});

// SLIDE OPTIONS TD ON HOVER
$('.list-table tr').hover(function() {
	$(this).find('.options').stop(true).slideDown(200);
}, function() {
	$(this).find('.options').stop(true).slideUp(200);
});

// ABSOLUTE BUTTONS INSIDE TABLES
$.fn.InsideTableBtns = function() {
	var $el;
	return this.each(function() {
		$el = $(this);
		var newDiv = $("<div />", {
			"class" : "innerWrapper",
			"css" : {
				"height" : $el.height(),
				"width" : "100%",
				"position" : "relative"
			}
		});
		$el.wrapInner(newDiv);
	});
};
$("td.hover-buttons-container").InsideTableBtns();

// //////// OTHER COMPONENTS /////////

// DRAGGABLE MODAL
$(".entity-window").draggable({
	scroll : false,
	handle : ".entity-window-header",
	containment : "#content"
});
$('.show-window-btn').click(function() {
	var target = $(this).attr('href');
	var offset = $(this).offset();
	var height = $(this).parents('dropdown-menu').outerHeight();
	var width = $(this).parents('dropdown-menu').width();
	var top = offset.top;
	var right = offset.left + width + "px";

	$(target).css({
		'position' : 'absolute',
		'left' : right,
		'top' : top
	});
	$(target).slideDown('fast');

	return false
});
$('.entity-window .btn-close').click(function() {
	$(this).parents('.entity-window').slideUp('fast');
	return false
});

// PAGE LOADER
$('.load-page-btn').click(function() {
	$('body').addClass('loading');
	setTimeout(function() {
		$('body').removeClass('loading');
	}, 2000);
	return false
});

// DRAG AND DROP
$(function() {
	$(".sortable-list").sortable({
		connectWith : ".sortable-list",
		handle : ".sortable-handler"
	}).disableSelection();
	$(".sortable-columns-container").sortable({
		handle : ".sortable-column-handler"
	}).disableSelection();
});

function mensagemCamposObrigatorios() {
	var elements = document.getElementsByTagName("INPUT");
	for (var i = 0; i < elements.length; i++) {

		elements[i].oninvalid = function(e) {
			e.target.setCustomValidity("");

			if (!e.target.validity.valid) {
				if (e.srcElement.validity.valueMissing
						&& !e.srcElement.validity.badInput) {
					e.target.setCustomValidity("Campo " + e.target.placeholder
							+ " é de preechimento obrigatório.");
				} else if (e.srcElement.validity.badInput) {
					if (e.srcElement.type.toUpperCase() == "NUMBER") {
						if (e.srcElement.validity.valueMissing == true) {
							e.target
									.setCustomValidity("Favor informar um valor numérico válido.");
						}
					}
				} else {
					if (e.srcElement.type.toUpperCase() == "NUMBER") {
						if (e.srcElement.validity.rangeOverflow
								|| e.srcElement.validity.rangeUnderflow) {
							e.target.setCustomValidity("Valor do campo "
									+ e.target.placeholder
									+ " deve estar entre " + e.target.min
									+ " e " + e.target.max + ".");
						}
					}
				}
			} else {
				try {
					funcaoValidar();
				} catch (e) {

				}
			}
		};
		elements[i].oninput = function(e) {
			e.target.setCustomValidity("");
		};
	}
}

formatCNPJ = function(data, type, full) {
	data = data.substr(0, 2) + '.' + 
					data.substr(2, 3) + '.' + 
					data.substr(5, 3) + '/' + 
					data.substr(8, 4) + '-' + 
					data.substr(12, 2);
	
	return data;
};

//function formatCNPJ(data,type,full){
//	data = data.replace( /\D/g , ""); //Remove tudo o que não é dígito
//	data = data.replace( /^(\d{2})(\d)/ , "$1.$2"); //Coloca ponto entre o segundo e o terceiro dígitos
//	data = data.replace( /^(\d{2})\.(\d{3})(\d)/ , "$1.$2.$3"); //Coloca ponto entre o quinto e o sexto dígitos
//	data = data.replace( /\.(\d{3})(\d)/ , ".$1/$2"); //Coloca uma barra entre o oitavo e o nono dígitos
//	data = data.replace( /(\d{4})(\d)/ , "$1-$2"); //Coloca um hífen depois do bloco de quatro dígitos
//	return data;
//}

formatCurrency = function(data, type, full) {
	data = $.formatNumber(data, {
		format : "#,###.00",
		locale : "us"
	});
	return ("R$ " + data).replace('.', ',');
};

function formatInputMonetario(data) {
	if (data.split(" ").length == 1) {
		var number = $.formatNumber(data, {
			format : "#,###.00",
			locale : "br"
		});
	} else {
		var number = $.formatNumber(data.split(" ")[1], {
			format : "#,###.00",
			locale : "br"
		});
	}
	return "R$ " + number;
}

enumAltura = function(data, type, full) {
	switch (data) {
	case "P":
		return "Pequena";
	case "M":
		return "Média";
	case "G":
		return "Grande";
	}
	return "";
};

enumSimNao = function(data, type, full) {
	switch (data) {
	case "S":
		return "Sim";
	case "N":
		return "Não";
	}
	return "";
};


/** 
 * Confirme com pré-validação
 */
function postConfirm(listener, message, funcaoValidar, funcaoCallBack) {
	$('button[name=' + listener + ']').on('click', function(e) {
		var $form = $(this).closest('form');

		if (funcaoValidar == undefined || window[funcaoValidar]()) {
			bootbox.dialog({
				message : message,
				title : "Confirmação",
				buttons : {
					success : {
						label : "Sim",
						className : "btn-success",
						callback : function() {
							$form.trigger('submit');
						}
					},
					cancel : {
						label : "Não",
						className : "btn-danger",
						callback : function() {
							if (funcaoCallBack != undefined)
								funcaoCallBack();
						}
					}
				}
			});
			e.preventDefault();
		}
	});
}

function alertaErroSimples(mensagem) {
	bootbox.dialog({
		message : mensagem,
		title : "Erro",
		type : "error",
		buttons : {
			cancel : {
				label : "OK",
				className : "btn-danger"
			}
		}
	});
}

function alertaSucessCallBack(mensagem, funcaoCallBack) {
	bootbox.dialog({
		message : mensagem,
		title : "Sucesso",
		type : "sucess",
		buttons : {
			cancel : {
				label : "OK",
				className : "btn-primary", 
				callback : function() {
					if (funcaoCallBack != undefined)
						window[funcaoCallBack]();
				}
			}
		}
	});
}

function breadcrumbResize() {
	var ellipses2 = $(".btn-breadcrumb div:nth-child(2)");
	var elements = $("div.btn.menu").filter(function() {
		if ($.browser.chrome) {
			return $(this).is(":not(:visible)");
		} else {
			return $(this).is(":hidden");
		}
	});
	if (elements.length > 0) {
		ellipses2.show();
	} else {
		ellipses2.hide();
	}
}

$(window).resize(function() {
	breadcrumbResize();
});

breadcrumbResize();

$.datepicker.regional['ptBR'] = {
	closeText : 'Fechar',
	prevText : '<Anterior',
	nextText : 'Próximo>',
	currentText : 'Agora',
	monthNames : [ 'Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
			'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro' ],
	monthNamesShort : [ 'Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago',
			'Set', 'Out', 'Nov', 'Dez' ],
	dayNames : [ 'Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta',
			'Sabado' ],
	dayNamesShort : [ 'DOM', 'SEG', 'TER', 'QUA', 'QUI', 'SEX', 'SAB' ],
	dayNamesMin : [ 'DOM', 'SEG', 'TER', 'QUA', 'QUI', 'SEX', 'SAB' ],
	weekHeader : 'Не',
	dateFormat : 'dd/MM/yyyy',
	firstDay : 0,
	isRTL : false,
	showMonthAfterYear : false,
	yearSuffix : ''
};
$.datepicker.setDefaults($.datepicker.regional['ptBR']);

function ajax(data) {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});
	
	return $.ajax(data);
}


function addSucess(mensagem) {
	var mens = "<div class='alert alert-success' style='margin-top: 15px'>";
	mens += "<button type='button' class='close' data-dismiss='alert'>x</button><div>";
	mens += mensagem;
	mens += "</div></div>";
	$(mens).insertAfter(".help-container");
}

function addErro(erro) {
	
	if($(".alert-danger").find('div:contains('+erro+')').length == 0 ){
		var mens = "<div class='alert alert-danger' style='margin-top: 15px'>";
		mens += "<button type='button' class='close' data-dismiss='alert'>x</button><div>";
		mens += erro;
		mens += "</div></div>";
		$(mens).insertAfter(".help-container");
		window.scrollTo(0,0);
	}
}
function isIE() {
	var ua = window.navigator.userAgent;
	var msie = ua.indexOf("MSIE ");
	var trid = navigator.appVersion.indexOf('Trident/')
	if (msie > 0 || trid > 0) {
		return true;
	} else {
		return false;
	}
}