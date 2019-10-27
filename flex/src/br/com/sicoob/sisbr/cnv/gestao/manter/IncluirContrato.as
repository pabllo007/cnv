package br.com.sicoob.sisbr.cnv.gestao.manter
{
	import flash.events.Event;
	import flash.events.FocusEvent;
	import flash.events.MouseEvent;
	import flash.utils.setTimeout;
	
	import mx.collections.ArrayCollection;
	import mx.events.FlexEvent;
	
	import br.com.bancoob.componentes.Alerta;
	import br.com.bancoob.componentes.MostraCursor;
	import br.com.bancoob.componentes.containers.Janela;
	import br.com.bancoob.componentes.tabelapaginada.TabelaPaginadaUtils;
	import br.com.bancoob.util.StringUtils;
	import br.com.sicoob.sisbr.cnv.gestao.util.Constantes;
	import br.com.sicoob.sisbr.cnv.gestao.util.ListUtils;
	import br.com.sicoob.sisbr.cnv.util.rest.RestUtils;
	import br.com.sicoob.sisbr.util.rest.EventREST;

	public class IncluirContrato extends IncluirContratoView {
		
		private var listaConvenios:ArrayCollection = new ArrayCollection();
		private var _contratoVO:Object;
		private var restUtil:RestUtils;
		private var janelaVincular:Janela;
		private var vincularConvenio:VincularConvenio = new VincularConvenio();
		private var dataAssinaturaAnterior:Date = new Date();
		private var dataVigenciaAnterior:Date = new Date();
		private var dataRenovacaoAnterior:Date = new Date();
		private var dataTarifariaAnterior:Date = new Date();

		public function set contratoVO(value:Object):void
		{
			_contratoVO = value;
		}

		public function IncluirContrato() {
			super();
			this.addEventListener(FlexEvent.CREATION_COMPLETE, init);
		}

		private function init(ev:FlexEvent):void {
			configurarServicoRest();
			dataAssinaturaAnterior = null;
			dataVigenciaAnterior = null;
			dataRenovacaoAnterior = null;
			dataTarifariaAnterior = null;
			carregarListeners();
			setTimeout(carregaRest, 1500);
		}

		private function configurarServicoRest():void {
			restUtil = new RestUtils(false);
			restUtil.basic = Constantes.BASIC_GESTAO;
			restUtil.servico_rest=Constantes.SERVICO_REST_GESTAO;
		}
		
		private function carregarListeners():void {
			checkIndeterminado.addEventListener(Event.CHANGE, focusChangeCheckBox);
			dataAssinaturaContrato.addEventListener(Event.CHANGE, focusOutVerificarData);
			dataVigencia.addEventListener(Event.CHANGE, focusOutVerificarData);
			dataRenovacao.addEventListener(Event.CHANGE, focusOutVerificarData);
			dataAtualizacaoTarifaria.addEventListener(Event.CHANGE, focusOutVerificarData);
			btBuscar.addEventListener(MouseEvent.CLICK, btnBuscar_event);
			btSalvar.addEventListener(MouseEvent.CLICK, btnSlavar_event);
			inputCNPJ.addEventListener(FocusEvent.FOCUS_OUT, focusOutCNPJ_event);
			vincularConvenio.addEventListener(vincularConvenio.CONVENIO_EVENT, recuperaListaConveniosVinculados);
		}

		private function carregaRest():void {
			MostraCursor.setBusyCursor("Aguarde ...", this, MostraCursor.CURSOR_PROGRESSO);
			restUtil.callGET("dominio/periodicidade", carregarComboRenovacaoAtualizacao);

		}

		private function carregarComboRenovacaoAtualizacao(event:EventREST):void {
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
			MostraCursor.removeBusyCursor();
		}
		
		private function focusChangeCheckBox(eventObj:Event):void {
			if(checkIndeterminado.selected) {
				campoDataVigencia.enabled = false;
				dataVigencia.selectedDate = null;
			}else {
				campoDataVigencia.enabled = true;
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

		private function btnBuscar_event(eventObj:MouseEvent):void {
			if(vincularConvenio.listaConveniosSelecionados != null && listaConvenios.length > 0) {
				vincularConvenio.listaConveniosSelecionados.removeAll();
				vincularConvenio.listaConveniosSelecionados.addAll(listaConvenios);
			}
			janelaVincular = new Janela();
			janelaVincular.addChild(vincularConvenio);
			janelaVincular.title="Vincular Convênio";
			janelaVincular.abrir(this.parent,true,true);
		}
		
		private function btnSlavar_event(eventObj:MouseEvent):void {
			if(campoObrigatorio()) {
				if(grdResultado.grdDados.dataProvider.length >0 && grdResultado.grdDados.dataProvider != null) {
					var lista:ArrayCollection = grdResultado.grdDados.dataProvider as ArrayCollection;
					listaConvenios.removeAll();
					for each(var convenio:Object in lista) {
						if(convenio.selecionado) {
							var conv:Object = new Object();
							conv.id = convenio.id
							listaConvenios.addItem(conv);
						}
					}
				}
				var situacao:Object = new Object();
				situacao.id = 1;
				var alertaRenovacao:Object = new Object();
				alertaRenovacao.id = idComboRenovacao.selectedIndex;
				var alertaTarifaria:Object = new Object();
				alertaTarifaria.id = idComboAtualizacao.selectedIndex
				
				var contrato:Object =  new Object();
				contrato.acaoOperacionalRenovacao = null;
				contrato.acaoOperacionalTarifaria = null;
				contrato.alertaRenovacao = alertaRenovacao;
				contrato.alertaTarifaria = alertaTarifaria;
				contrato.cnpj = inputCNPJ.text;
				contrato.convenios = listaConvenios.list.toArray();
				contrato.dataAssinaturaContrato = dataAssinaturaContrato.selectedDate.fullYear.toString()+"-"+(dataAssinaturaContrato.selectedDate.month + 1).toString()+"-"+dataAssinaturaContrato.selectedDate.date.toString();
				contrato.dataAtualizacaoTarifario = dataAtualizacaoTarifaria.selectedDate.fullYear.toString()+"-"+(dataAtualizacaoTarifaria.selectedDate.month + 1).toString()+"-"+dataAtualizacaoTarifaria.selectedDate.date.toString();
				contrato.dataRescisao = null;
				contrato.dataRenovacao =  dataRenovacao.selectedDate.fullYear.toString()+"-"+(dataRenovacao.selectedDate.month + 1).toString()+"-"+dataRenovacao.selectedDate.date.toString();
				if(checkIndeterminado.selected) {
					contrato.bolVigenciaIndeterminada = checkIndeterminado.selected;
					contrato.dataVigencia = null;
				} else {
					contrato.dataVigencia = dataVigencia.selectedDate.fullYear.toString()+"-"+(dataVigencia.selectedDate.month + 1).toString()+"-"+dataVigencia.selectedDate.date.toString();
					contrato.bolVigenciaIndeterminada = false;
				}
				contrato.descJustificativaRescisao = null;
				contrato.numero = inputNumContrato.text;
				contrato.sigla = inputSigla.text;
				contrato.situacaoRenovacao = situacao;
				contrato.situacaoTarifaria = situacao;
				restUtil.callPOST("contratos",contrato,respostaIncluir);
			}
		}

		private function focusOutCNPJ_event(eventObj:FocusEvent):void {
			var cnpjNumero:String = StringUtils.trim(inputCNPJ.text.replace(new RegExp(/[.-]/g),""));
			if(StringUtils.emptyToNull(cnpjNumero) && cnpjNumero.length == 14){
				restUtil.callGET("convenios/cnpj/"+cnpjNumero, preencheContrato);
			} else {
				inputCNPJ.text = "";
				inputCNPJ.inputMask;
			}
		}
		
		protected function recuperaListaConveniosVinculados(event:Event):void {
			listaConvenios.removeAll();
			ListUtils.sort(vincularConvenio.listaConveniosSelecionados, "selecionado");
			listaConvenios.addAll(vincularConvenio.listaConveniosSelecionados);
			grdResultado.limparConteudo();
			grdResultado.montarConteudo(TabelaPaginadaUtils.criarConteudoTabela(listaConvenios, grdResultado.tamanhoPagina));
			hdGrid.visible=true;
		}
		




		private function preencherContratoCAPES(event:EventREST):void {
			var respostaVO:Object = event.result;
			if(respostaVO) {
				if(!event.error) {
					inputRazao.text = StringUtils.trim(respostaVO.resultado.nomePessoa);
					inputNome.text = StringUtils.trim(respostaVO.resultado.nomeCompleto);

				}else {
					Alerta.show(respostaVO.erro, "Informação", Alerta.ALERTA_INFORMACAO);
				}
			} else {
				Alerta.show("Não foi possível encontrar nenhum resultado no CAPES para esse CNPJ", "Informação", Alerta.ALERTA_INFORMACAO);
				inputRazao.text = "";
				inputNome.text = "";
			}
		}

		private function preencheContrato(event:EventREST):void {
			var respostaVO:Object = event.result;
			if(respostaVO) {
				if(!event.error) {
					var listaResultado:ArrayCollection = new ArrayCollection(respostaVO.resultado);
					listaConvenios.addAll(listaResultado);
					var cnpjNumero:String = StringUtils.trim(inputCNPJ.text.replace(new RegExp(/[.-]/g),""));
					if(listaConvenios != null &&  listaConvenios.length > 0) {
						grdResultado.montarConteudo(TabelaPaginadaUtils.criarConteudoTabela(listaConvenios, grdResultado.tamanhoPagina));
						hdGrid.visible=true;
						idBotaoBuscar.visible=true;
					} else {
						grdResultado.limparConteudo();
						hdGrid.visible=false;
						idBotaoBuscar.visible=true;
					}
					restUtil.callGET("integracao/capes/"+cnpjNumero, preencherContratoCAPES);
				}else {
					Alerta.show(respostaVO.erro, "Informação", Alerta.ALERTA_INFORMACAO);
					inputCNPJ.text = "";
					inputCNPJ.inputMask;
				}
			} else {
				Alerta.show(event.error.mensagens[0].mensagem, "Informação", Alerta.ALERTA_INFORMACAO);
				inputCNPJ.text = "";
				inputCNPJ.inputMask;
			}
		}

		private function respostaIncluir(event:EventREST):void {
			var respostaVO:Object = event.result;
			if(event.status == 200){
				Alerta.show("Operação Realizada com Sucesso", "Informação", Alerta.ALERTA_INFORMACAO);
				this.fecharJanela();
			} else {
				Alerta.show(event.error.mensagens[0].mensagem, "Informação", Alerta.ALERTA_INFORMACAO);
			}
		}

		private function campoObrigatorio():Boolean {
				if (inputCNPJ.text == null || StringUtils.trim(inputCNPJ.text.replace(new RegExp(/[.-]/g),"")) == ""){
					Alerta.show("O CNPJ é de preenchimento obrigatório !", "Informação", Alerta.ALERTA_INFORMACAO);
					return false;
				}
				if (inputSigla.text == null || inputSigla.text == "") {
					Alerta.show("A Sigla do Contrato é de preenchimento obrigatório !", "Informação", Alerta.ALERTA_INFORMACAO);
					return false;
				}
				if (inputNumContrato.text == null || inputNumContrato.text == "") {
					Alerta.show("O Nº do Contrato é de preenchimento obrigatório !", "Informação", Alerta.ALERTA_INFORMACAO);
					return false;
				}
				if (dataAssinaturaContrato.selectedDate == null || dataRenovacao.selectedDate == null || dataAtualizacaoTarifaria.selectedDate == null) {
					Alerta.show("A Data é de preenchimento obrigatório !", "Informação", Alerta.ALERTA_INFORMACAO);
					return false;
				}
				if (!(dataVigencia.selectedDate != null || checkIndeterminado.enabled)) {
					Alerta.show("A", "Informação", Alerta.ALERTA_INFORMACAO);
					return false;
				}
			return true;
		}

	}
}