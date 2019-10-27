package br.com.sicoob.sisbr.cnv.gestao.manter
{
	import flash.events.Event;
	import flash.events.FocusEvent;
	import flash.events.MouseEvent;
	import flash.net.registerClassAlias;
	
	import mx.collections.ArrayCollection;
	import mx.events.FlexEvent;
	
	import br.com.bancoob.componentes.Alerta;
	import br.com.bancoob.componentes.MostraCursor;
	import br.com.bancoob.componentes.containers.Janela;
	import br.com.bancoob.componentes.tabelapaginada.TabelaPaginadaUtils;
	import br.com.bancoob.util.DateUtils;
	import br.com.bancoob.util.StringUtils;
	import br.com.sicoob.sisbr.cnv.gestao.util.Constantes;
	import br.com.sicoob.sisbr.cnv.gestao.util.ListUtils;
	import br.com.sicoob.sisbr.cnv.util.rest.RestUtils;
	import br.com.sicoob.sisbr.util.rest.EventREST;

	public class AlterarContrato extends AlterarContratoView {
		
		private var restUtil:RestUtils;
		private var _contratoVO:Object;
		private var vincularConvenio:VincularConvenio = new VincularConvenio();
		private var janelaVincular:Janela;
		private var recuperaContratoVO:Object;
		private var listaGrid:ArrayCollection = new ArrayCollection();
		private var contrato:Object =  new Object();
		public const SALVOU_EVENT:String = "TELA_SALVA";
		private var dataRecisaoAnterior:Date = new Date();
		
		
		public function set contratoVO(value:Object):void
		{
			_contratoVO = value;
		}
		
		public function AlterarContrato() {
			super();
			this.addEventListener(FlexEvent.CREATION_COMPLETE, init);
		}

		private function configurarServicoRest():void {
			restUtil = new RestUtils(false);
			restUtil.basic = Constantes.BASIC_GESTAO;
			restUtil.servico_rest=Constantes.SERVICO_REST_GESTAO;	
		}

		private function init(ev:FlexEvent):void {
			configurarServicoRest();
			carregarListeners();
			txtAreaJustificativa.enabled = false;
			txtAreaJustificativa.editable = false;
			dataRecisaoAnterior = null;
			carregaRest();
		}
		
		private function carregarListeners():void {
			dataRescisaoContrato.addEventListener(Event.CHANGE, dataRescisaoContrato_event);
			btSalvar.addEventListener(MouseEvent.CLICK, btnSlavar_event);
			btnBuscar.addEventListener(MouseEvent.CLICK, btnBuscar_event);
			inputCNPJ.addEventListener(FocusEvent.FOCUS_OUT, focusOutCNPJ_event);
			vincularConvenio.addEventListener(vincularConvenio.CONVENIO_EVENT, recuperaListaConveniosVinculados);
		}
		
		private function carregaRest():void {
			MostraCursor.setBusyCursor("Aguarde ...", this, MostraCursor.CURSOR_PROGRESSO);
			restUtil.callGET("contratos/"+_contratoVO.id, carregaDadosContrato);
		}
		
		private function dataRescisaoContrato_event(eventObj:Event):void {
			var dataVigencia:Date = DateUtils.stringToDate(recuperaContratoVO.resultado.dataVigencia,"YYYY-MM-DD");
			var dataAssinaturaContrato:Date = DateUtils.stringToDate(recuperaContratoVO.resultado.dataAssinaturaContrato,"YYYY-MM-DD");
			if (dataRescisaoContrato.selectedDate == null) {
				txtAreaJustificativa.enabled = false;
			} 
			if(dataRescisaoContrato.selectedDate >= dataAssinaturaContrato){
				dataRecisaoAnterior = dataRescisaoContrato.selectedDate;
				txtAreaJustificativa.enabled = true;
				txtAreaJustificativa.editable = true;
			} else {
				Alerta.show("A data de \"Rescisão\" deve ser Maior ou Igual que a data de \"Assinatuda do Contrato\"", "Informação", Alerta.ALERTA_INFORMACAO);
				dataRescisaoContrato.selectedDate = dataRecisaoAnterior;
				if(dataRescisaoContrato.selectedDate == null){
					txtAreaJustificativa.text = "";
					txtAreaJustificativa.enabled = false;
					txtAreaJustificativa.editable = false;
				}
			}
		}
		
		private function btnSlavar_event(eventObj:MouseEvent):void {
			if(campoObrigatorio()) {
				if(grdResultado.grdDados.dataProvider.length >0) {
					var lista:ArrayCollection = grdResultado.recuperarLista() as ArrayCollection;
					listaGrid.removeAll();
					for each(var convenio:Object in lista) {
						if(convenio.selecionado) {
							var conv:Object = new Object();
							conv.id = convenio.id
							listaGrid.addItem(conv);
						}
					}
				}
				var situacaoTarifaria:Object = new Object();
				situacaoTarifaria.id = recuperaContratoVO.resultado.situacaoTarifaria.id;
				var situacaoRenovacao:Object = new Object();
				situacaoRenovacao.id = recuperaContratoVO.resultado.situacaoRenovacao.id;
				var alertaRenovacao:Object = new Object();
				alertaRenovacao.id = recuperaContratoVO.resultado.alertaRenovacao.id;
				var alertaTarifaria:Object = new Object();
				alertaTarifaria.id = recuperaContratoVO.resultado.alertaTarifaria.id;
				
				contrato.id = _contratoVO.id;
				contrato.numero = inputNumContrato.text;
				contrato.dataAssinaturaContrato = recuperaContratoVO.resultado.dataAssinaturaContrato;
				contrato.dataVigencia = recuperaContratoVO.resultado.dataVigencia;
				contrato.bolVigenciaIndeterminada = recuperaContratoVO.resultado.bolVigenciaIndeterminada;
				contrato.dataRenovacao = recuperaContratoVO.resultado.dataRenovacao;
				contrato.dataAtualizacaoTarifario = recuperaContratoVO.resultado.dataAtualizacaoTarifario;
				contrato.alertaRenovacao = alertaRenovacao;
				contrato.alertaTarifaria = alertaTarifaria;
				contrato.situacaoRenovacao = situacaoRenovacao;
				contrato.situacaoTarifaria = situacaoTarifaria;
				contrato.sigla = inputSigla.text;
				contrato.convenios = listaGrid.list.toArray();
				contrato.cnpj = inputCNPJ.text;
				contrato.acaoOperacionalRenovacao = null;
				contrato.acaoOperacionalTarifaria = null;
				
				if(dataRescisaoContrato.selectedDate != null) {
					contrato.dataRescisao = dataRescisaoContrato.selectedDate.fullYear.toString()+"-"+(dataRescisaoContrato.selectedDate.month +1).toString()+"-"+dataRescisaoContrato.selectedDate.date.toString();
					contrato.descJustificativaRescisao = txtAreaJustificativa.text;
				} else {
					contrato.dataRescisao = null;
					contrato.descJustificativaRescisao = null;
				}
				if(dataRescisaoContrato.selectedDate != null && contrato.descJustificativaRescisao != null) {
					Alerta.show("Tem certeza que deseja encerrar este contrato?", "Atenção", Alerta.ALERTA_PERGUNTA, null, salvarContrato);
					return;
				} else {
					salvarContrato(null);
				}
			}
		}
		
		private function salvarContrato(ev:Event):void {
			restUtil.callPUT("contratos/",contrato,respostaAlterar);
		}
		
		private function btnBuscar_event(eventObj:MouseEvent):void {
			if(vincularConvenio.listaConveniosSelecionados != null && listaGrid.length > 0) {
				vincularConvenio.listaConveniosSelecionados.removeAll();
				vincularConvenio.listaConveniosSelecionados.addAll(listaGrid);
			}
			janelaVincular = new Janela();
			janelaVincular.addChild(vincularConvenio);
			janelaVincular.title="Vincular Convênio";
			janelaVincular.abrir(this.parent,true,true);
		}
		
		private function focusOutCNPJ_event(eventObj:FocusEvent):void {
			var cnpjNumero:String = StringUtils.trim(inputCNPJ.text.replace(new RegExp(/[.-]/g),""));
			if(StringUtils.emptyToNull(cnpjNumero) && cnpjNumero.length == 14){
				restUtil.callGET("convenios/cnpj/"+inputCNPJ.text, carregaConveniosCnpj);
			} else {
				inputCNPJ.text = "";
				inputCNPJ.inputMask;
			}
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
		
		protected function recuperaListaConveniosVinculados(event:Event):void {
			listaGrid.removeAll();
			ListUtils.sort(vincularConvenio.listaConveniosSelecionados, "selecionado");
			listaGrid.addAll(vincularConvenio.listaConveniosSelecionados);
			grdResultado.limparConteudo();
			grdResultado.montarConteudo(TabelaPaginadaUtils.criarConteudoTabela(listaGrid, grdResultado.tamanhoPagina));
			hdGrid.visible=true;
		}
	

		private function carregaDadosContrato(event:EventREST):void {
			recuperaContratoVO = event.result;
			if(recuperaContratoVO) {
				if(!event.error) {
					inputCNPJ.text = StringUtils.trim(_contratoVO.cnpj);
					inputSigla.text = StringUtils.trim(_contratoVO.sigla);
					inputNumContrato.text = StringUtils.trim(_contratoVO.numero);
					if(recuperaContratoVO.resultado.dataRescisao != null){
						dataRescisaoContrato.selectedDate = DateUtils.stringToDate(recuperaContratoVO.resultado.dataRescisao,"YYYY-MM-DD");
						txtAreaJustificativa.text = recuperaContratoVO.resultado.descJustificativaRescisao;
						txtAreaJustificativa.enabled = true;
						txtAreaJustificativa.editable = true;
						dataRecisaoAnterior = dataRescisaoContrato.selectedDate;
					}
					var listaDatas:ArrayCollection = new ArrayCollection(recuperaContratoVO.resultado.convenios);
					if(listaDatas != null &&  listaDatas.length > 0) {
						listaGrid = listaDatas;
					}
					restUtil.callGET("convenios/cnpj/"+_contratoVO.cnpj, carregaConvenios);
				}
			}
			MostraCursor.removeBusyCursor();
		}
		
		private function carregaConveniosCnpj(event:EventREST):void {
			carregaGridConvenios(event, inputCNPJ.text);
		}
		
		private function carregaConvenios(event:EventREST):void {
			carregaGridConvenios(event, _contratoVO.cnpj);
		}
		
		private function carregaGridConvenios(event:Object, inputCnpj:String):void{
			var recuperaConvenio:Object = event.result;
			if(recuperaConvenio) {
				if(!event.error) {
					var listaConvenios:ArrayCollection = new ArrayCollection(recuperaConvenio.resultado);
					if(listaConvenios != null &&  listaConvenios.length > 0) {
						listaGrid.addAll(listaConvenios);
					}
					restUtil.callGET("/integracao/capes/"+inputCnpj, preencherContratoCAPES);
				}
			}
			if(listaGrid.length > 0) {
				grdResultado.montarConteudo(TabelaPaginadaUtils.criarConteudoTabela(listaGrid, grdResultado.tamanhoPagina));
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
		
		private function campoObrigatorio():Boolean {
			if (inputCNPJ.text == null 
				|| inputCNPJ.text == "" 
				|| inputNumContrato.text == null 
				|| inputNumContrato.text == "" 
				|| inputSigla.text == null 
				|| inputSigla.text == "" ) {
				Alerta.show("Preencher todos os campos obrigatórios !", "Informação", Alerta.ALERTA_INFORMACAO);
				return false;
			} else if (dataRescisaoContrato.selectedDate != null && (txtAreaJustificativa.text == "" || txtAreaJustificativa.text == null)){
				Alerta.show("A justificativa é de preenchimento obrigatório", "Informação", Alerta.ALERTA_INFORMACAO);
				return false;
			} else {
				return true;
			}
		}
		
		
	}
}