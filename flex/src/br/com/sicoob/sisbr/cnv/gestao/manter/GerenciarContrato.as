package br.com.sicoob.sisbr.cnv.gestao.manter
{
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.text.StyleSheet;
	import flash.utils.setTimeout;
	
	import mx.collections.ArrayCollection;
	import mx.events.FlexEvent;
	import mx.utils.StringUtil;
	
	import br.com.bancoob.componentes.Alerta;
	import br.com.bancoob.componentes.MostraCursor;
	import br.com.bancoob.util.DateUtils;
	import br.com.bancoob.util.StringUtils;
	import br.com.sicoob.sisbr.cnv.gestao.util.Constantes;
	import br.com.sicoob.sisbr.cnv.util.date.DateUtils;
	import br.com.sicoob.sisbr.cnv.util.rest.RestUtils;
	import br.com.sicoob.sisbr.util.rest.EventREST;

	public class GerenciarContrato extends GerenciarContratoView {
		
		private var restUtil:RestUtils;
		private var _contratoVO:Object;
		private var recuperaContratoVO:Object;
		public const SALVOU_EVENT:String = "TELA_SALVA";
		private var dataAssinaturaAnterior:Date = new Date();
		private var dataVigenciaAnterior:Date = new Date();
		private var dataRenovacaoAnterior:Date = new Date();
		private var dataTarifariaAnterior:Date = new Date();
		
		public function set contratoVO(value:Object):void
		{
			_contratoVO = value;
		}
		
		private function configurarServicoRest():void {
			restUtil = new RestUtils(false);
			restUtil.basic = Constantes.BASIC_GESTAO;
			restUtil.servico_rest=Constantes.SERVICO_REST_GESTAO;	
		}	
		
		public function GerenciarContrato() {
			super();
			this.addEventListener(FlexEvent.CREATION_COMPLETE, init);
		}
		
		private function init(ev:FlexEvent):void {
			dataAssinaturaAnterior = null;
			dataRenovacaoAnterior = null;
			dataTarifariaAnterior = null;
			dataVigenciaAnterior = null;
			configurarServicoRest();
			carregarListeners();
			setTimeout(carregaRest, 1500);
		}
		
		
		private function carregaRest():void {
			MostraCursor.setBusyCursor("Aguarde ...", this, MostraCursor.CURSOR_PROGRESSO);
			restUtil.callGET("dominio/periodicidade", carregarComboRenovacaoAtualizacao);
			restUtil.callGET("contratos/"+_contratoVO.id, carregaDadosContrato);
			MostraCursor.removeBusyCursor();
		}

		private function carregarListeners():void {
			checkIndeterminado.addEventListener(Event.CHANGE, focusChangeCheckBox);
			btnSalvar.addEventListener(MouseEvent.CLICK, btnSlavar_event);
			dataAssinaturaContrato.addEventListener(Event.CHANGE, focusOutVerificarData);
			dataVigencia.addEventListener(Event.CHANGE, focusOutVerificarData);
			dataRenovacao.addEventListener(Event.CHANGE, focusOutVerificarData);
			dataAtualizacaoTarifaria.addEventListener(Event.CHANGE, focusOutVerificarData);
		}
		
		private function btnSlavar_event(eventObj:MouseEvent):void {
			if(campoObrigatorio()) {
				
				var situacaoTarifaria:Object = new Object();
				situacaoTarifaria.id = recuperaContratoVO.resultado.situacaoTarifaria.id;
				var situacaoRenovacao:Object = new Object();
				situacaoRenovacao.id = recuperaContratoVO.resultado.situacaoRenovacao.id;
				var alertaRenovacao:Object = new Object();
				alertaRenovacao.id = idComboRenovacao.selectedIndex;
				var alertaTarifaria:Object = new Object();
				alertaTarifaria.id = idComboAtualizacao.selectedIndex;
				var lisConvenios:ArrayCollection = new ArrayCollection();
				for each (var conv:Object in recuperaContratoVO.resultado.convenios){
					var idConv:Object = new Object();
					idConv.id = conv.id;
					lisConvenios.addItem(idConv);
				}
				var contrato:Object =  new Object();
				contrato.id = _contratoVO.id;
				contrato.cnpj = _contratoVO.cnpj;
				contrato.sigla = _contratoVO.sigla;
				contrato.numero = _contratoVO.numero;
				contrato.convenios = lisConvenios.list.toArray();
				if (dataVigencia.selectedDate != null) {
					contrato.dataVigencia = dataVigencia.selectedDate.fullYear.toString()+"-"+(dataVigencia.selectedDate.month + 1).toString()+"-"+dataVigencia.selectedDate.date.toString();
				} else {
					contrato.dataVigencia = null;
				}
				contrato.dataAssinaturaContrato = dataAssinaturaContrato.selectedDate.fullYear.toString()+"-"+(dataAssinaturaContrato.selectedDate.month + 1).toString()+"-"+(dataAssinaturaContrato.selectedDate.date+1).toString();
				contrato.bolVigenciaIndeterminada = checkIndeterminado.selected;
				contrato.dataRenovacao = dataRenovacao.selectedDate.fullYear.toString()+"-"+(dataRenovacao.selectedDate.month + 1).toString()+"-"+(dataRenovacao.selectedDate.date+1).toString();
				contrato.dataAtualizacaoTarifario = dataAtualizacaoTarifaria.selectedDate.fullYear.toString()+"-"+(dataAtualizacaoTarifaria.selectedDate.month + 1).toString()+"-"+(dataAtualizacaoTarifaria.selectedDate.date+1).toString();
				contrato.alertaRenovacao = alertaRenovacao;
				contrato.alertaTarifaria = alertaTarifaria;

				contrato.dataRescisao = recuperaContratoVO.resultado.dataRescisao;
				contrato.descJustificativaRescisao = recuperaContratoVO.resultado.descJustificativaRescisao;
				contrato.situacaoRenovacao = situacaoRenovacao;
				contrato.situacaoTarifaria = situacaoTarifaria;
				carregaObjetoRadioGroup(contrato);
				
				restUtil.callPUT("contratos/",contrato,respostaAlterar);
			}
		}
		
		private function respostaAlterar(event:EventREST):void {
			var respostaVO:Object = event.result;
				if(event.status == 200){
				Alerta.show("Operação Realizada com Sucesso", "Informação", Alerta.ALERTA_INFORMACAO);
				var evento:Event = new Event(SALVOU_EVENT);
				this.dispatchEvent(evento);
				this.fecharJanela();				
			} else {
				Alerta.show(event.error.mensagens[0].mensagem, "Informação", Alerta.ALERTA_INFORMACAO);
			}
		}
		
		private function focusChangeCheckBox(eventObj:Event):void {
			if(checkIndeterminado.selected) {
				campoDataVigencia.enabled = false;
				dataVigencia.selectedDate = null;
			}else {
				campoDataVigencia.enabled = true;
			}
		}
		
		private function carregaObjetoRadioGroup(contrato:Object):void {
			if(rnIniciado.selected && !rnPendente.selected){
				contrato.acaoOperacionalRenovacao = true;
			} else if (rnIniciado.selected == false && rnPendente.selected == false){
				contrato.acaoOperacionalRenovacao = null;
			} else {
				contrato.acaoOperacionalRenovacao = false;
			}
			if(atIniciado.selected && !atPendente.selected){
				contrato.acaoOperacionalTarifaria = true;
			} else if (atIniciado.selected == false && atPendente.selected == false){
				contrato.acaoOperacionalTarifaria = null;
			} else {
				contrato.acaoOperacionalTarifaria = false;
			}
		}
		
		
		private function carregarComboRenovacaoAtualizacao(event:EventREST=null):void {
			var respostaVO:Object = event.result;
			if(respostaVO) {
				if(!event.error) {
					var filtroCombo:ArrayCollection = new ArrayCollection();
					for each ( var obj:Object in respostaVO.resultado) {
						filtroCombo.addItem(obj.dias);
					}
					idComboRenovacao.dataProvider = filtroCombo;
					idComboAtualizacao.dataProvider = filtroCombo;
				} else {
					Alerta.show(respostaVO.erro, "Informação", Alerta.ALERTA_INFORMACAO);
				}
			}
		}
	
		private function carregaDadosContrato(event:EventREST):void {
			recuperaContratoVO = event.result;
			if(recuperaContratoVO) {
				if(!event.error) {
					labelCNPJ.text = StringUtils.trim(_contratoVO.cnpj);
					labelSigla.text = StringUtils.trim(_contratoVO.sigla);
					inputRazao.text = StringUtils.trim(_contratoVO.razaoSocial);
					labelNome.text = StringUtils.trim(_contratoVO.fantasia);
					labelNumContrato.text = StringUtils.trim(_contratoVO.numero);
					labelSituacaoContrato.text = StringUtil.trim(_contratoVO.situacao);
					dataAssinaturaContrato.selectedDate = br.com.bancoob.util.DateUtils.stringToDate(recuperaContratoVO.resultado.dataAssinaturaContrato,"YYYY-MM-DD");
					dataAssinaturaAnterior = dataAssinaturaContrato.selectedDate;
					dataVigenciaEscolha(recuperaContratoVO.resultado.bolVigenciaIndeterminada, recuperaContratoVO.resultado.dataVigencia);
					dataVigenciaAnterior = dataVigencia.selectedDate;
					dataRenovacao.selectedDate = br.com.bancoob.util.DateUtils.stringToDate(recuperaContratoVO.resultado.dataRenovacao,"YYYY-MM-DD");
					dataRenovacaoAnterior = dataRenovacao.selectedDate;
					dataAtualizacaoTarifaria.selectedDate = br.com.bancoob.util.DateUtils.stringToDate(recuperaContratoVO.resultado.dataAtualizacaoTarifario,"YYYY-MM-DD");
					dataTarifariaAnterior = dataAtualizacaoTarifaria.selectedDate;
					idComboRenovacao.selectedIndex = recuperaContratoVO.resultado.alertaRenovacao.id;
					idComboAtualizacao.selectedIndex = recuperaContratoVO.resultado.alertaTarifaria.id;
					carregaRadioButtonSituacao(recuperaContratoVO.resultado.situacaoRenovacao.id as Number,1);
					carregaRadioButtonSituacao(recuperaContratoVO.resultado.situacaoTarifaria.id as Number,2);
					if(!(rnVigente.selected)){
						if (recuperaContratoVO.resultado.acaoOperacionalRenovacao) {
							rnIniciado.selected = true;
							rnPendente.selected = false;
						} else {
							rnIniciado.selected = false;
							rnPendente.selected = true;
						}
					}
					if(!(atVigente.selected)){
						if (recuperaContratoVO.resultado.acaoOperacionalTarifaria) {
							atIniciado.selected = true;
							atPendente.selected = false;
						} else {
							atIniciado.selected = false;
							atPendente.selected = true;
						}
					}
					if(recuperaContratoVO.resultado.prazoRenovacao < recuperaContratoVO.resultado.alertaRenovacao.dias){
						numeroRnPrazo.text = recuperaContratoVO.resultado.prazoRenovacao+" dias";
						if(recuperaContratoVO.resultado.prazoRenovacao < 0){
							labelNumRnPrazoObrigatorio.visible = true;
							numeroRnPrazoObrigatorio.visible = true;
							labelNumRnPrazo.visible = false;
							numeroRnPrazo.visible = false;
						}
					}
					if(recuperaContratoVO.resultado.prazoTarifario < recuperaContratoVO.resultado.alertaTarifaria.dias){
						numeroAtPrazo.text = recuperaContratoVO.resultado.prazoTarifario+" dias";
						if(recuperaContratoVO.resultado.prazoTarifario < 0){
							labelNumAtPrazoObrigatorio.visible = true;
							numeroAtPrazoObrigatorio.visible = true;
							labelNumAtPrazo.visible = false;
							numeroAtPrazo.visible = false;
						}
					}
					listaGrid.dataProvider = recuperaContratoVO.resultado.convenios;
				}else {
					Alerta.show(recuperaContratoVO.erro, "Informação", Alerta.ALERTA_INFORMACAO);
				}
			}
			numeroAtPrazoObrigatorio.text = numeroAtPrazo.text;
			numeroRnPrazoObrigatorio.text = numeroRnPrazo.text;
		}
		
		private function dataVigenciaEscolha(indeterminada:Boolean, dataVigenciaStr:String):void {
			if(indeterminada) {
				dataVigencia.selectedDate = null;
				campoDataVigencia.enabled = false;
				checkIndeterminado.selected = true;
			} else {
				dataVigencia.selectedDate = br.com.bancoob.util.DateUtils.stringToDate(dataVigenciaStr,"YYYY-MM-DD");
			}
		}
		
		private function carregaRadioButtonSituacao(situacao:Number, idRadio:Number):void {
			if(situacao == 1) {
				if(idRadio == 1){
					rnVigente.selected = true;
					rnPendente.selected = false
				}else {
					atVigente.selected = true;
					atPendente.selected = false;
				}
			} else {
				if(situacao == 3) {
					if(idRadio == 1){
						rnPeriodoRenovacao.selected = true;
						rnPendente.selected = true;
					}else {
						atPeriodoRenovacao.selected = true;
						atPendente.selected = true;
					}
				}else if(situacao == 2) {
					if(idRadio == 1) {
						rnVencido.selected = true;
						if(rnIniciado.selected == true){
						} else {
							rnPendente.selected = true;
						}
					}else {
						atVencido.selected = true;
						if(atIniciado.selected){
						} else {
							atPendente.selected = true;
						}
					}
				}
			}
		}
		
		private function focusOutVerificarData(eventObj:Event=null):void {
			if(dataAtualizacaoTarifaria.selectedDate < dataAssinaturaContrato.selectedDate &&
				dataAtualizacaoTarifaria.selectedDate != null &&
				dataAssinaturaContrato.selectedDate != null) {
				Alerta.show("A data da \"Atualização Tarifária\" deve ser maior que a data de \"Assinatura de Contrato\" ! ", "Informação", Alerta.ALERTA_INFORMACAO);
				dataAtualizacaoTarifaria.selectedDate = dataTarifariaAnterior;
			}
			if((dataVigencia.selectedDate < dataAssinaturaContrato.selectedDate) &&
				(dataVigencia.selectedDate != null) &&
				(dataAssinaturaContrato.selectedDate != null)){
				Alerta.show("A data de \"Vigência\" deve ser maior que a data de \"Assinatura do Contrato\" !", "Informação", Alerta.ALERTA_INFORMACAO);
				dataVigencia.selectedDate = dataVigenciaAnterior;
			} else {
				dataVigenciaAnterior = dataVigencia.selectedDate;
			}
			if ((dataVigencia.selectedDate < dataRenovacao.selectedDate) &&
				(dataVigencia.selectedDate != null) &&
				(dataRenovacao.selectedDate != null)){
				Alerta.show("A data de\"Renovação\" deve ser menor ou igual que a data de \"Vigência\" !", "Informação", Alerta.ALERTA_INFORMACAO);
				dataRenovacao.selectedDate = dataRenovacaoAnterior;
			}
			if((dataVigencia.selectedDate < dataAtualizacaoTarifaria.selectedDate) &&
				(dataVigencia.selectedDate != null) &&
				(dataAtualizacaoTarifaria.selectedDate != null)){
				Alerta.show("A data de \"Atualização Tarifária\" deve ser menor ou igual que a data de \"Vigência\" !", "Informação", Alerta.ALERTA_INFORMACAO);
				dataAtualizacaoTarifaria.selectedDate = dataTarifariaAnterior;
			}
			if(dataRenovacao.selectedDate <= dataAssinaturaContrato.selectedDate &&
				dataRenovacao.selectedDate != null &&
				dataAssinaturaContrato.selectedDate != null) {
				Alerta.show("A data de \"Renovação\" deve ser maior que a data de \"Assinatura do Contrato\" !", "Informação", Alerta.ALERTA_INFORMACAO);
				dataRenovacao.selectedDate = dataRenovacaoAnterior;
			}
			if(dataAtualizacaoTarifaria.selectedDate >= dataAssinaturaContrato.selectedDate || dataVigencia.selectedDate >= dataAtualizacaoTarifaria.selectedDate){
				dataTarifariaAnterior = dataAtualizacaoTarifaria.selectedDate;
			}
			if(dataVigencia.selectedDate >= dataRenovacao.selectedDate || dataRenovacao.selectedDate > dataAssinaturaContrato.selectedDate){
				dataRenovacaoAnterior = dataRenovacao.selectedDate;
			}
		}
		
		private function campoObrigatorio():Boolean {
			if (labelCNPJ.text == null 
				|| labelCNPJ.text == "" 
				|| labelNumContrato.text == null 
				|| labelNumContrato.text == "" 
				|| labelSigla.text == null 
				|| labelSigla.text == "" ) {
				Alerta.show("Preencher todos os campos obrigatórios !", "Informação", Alerta.ALERTA_INFORMACAO);
				return false;
			} else {			
				return true;
			}
		}
		
	}
}