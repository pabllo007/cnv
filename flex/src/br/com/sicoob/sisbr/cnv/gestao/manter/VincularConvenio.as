package br.com.sicoob.sisbr.cnv.gestao.manter
{
	import flash.events.Event;
	import flash.events.MouseEvent;
	
	import mx.collections.ArrayCollection;
	import mx.events.FlexEvent;
	
	import br.com.bancoob.componentes.Alerta;
	import br.com.bancoob.componentes.MostraCursor;
	import br.com.bancoob.componentes.tabelapaginada.TabelaPaginadaUtils;
	import br.com.sicoob.sisbr.cnv.gestao.util.Constantes;
	import br.com.sicoob.sisbr.cnv.util.rest.RestUtils;
	import br.com.sicoob.sisbr.util.rest.EventREST;
	

	public class VincularConvenio extends VincularConvenioView{
		
		private var restUtil:RestUtils;
		private var _listaConveniosSelecionados:ArrayCollection = new ArrayCollection();
		public const CONVENIO_EVENT:String = "CONVENIOS_VINCULADOS";
		
		public function VincularConvenio() {
			super();
			this.addEventListener(FlexEvent.CREATION_COMPLETE, init);
		}
		
		public function get listaConveniosSelecionados():ArrayCollection
		{
			return _listaConveniosSelecionados;
		}

		private function configurarServicoRest():void {
			restUtil = new RestUtils(false);
			restUtil.basic = Constantes.BASIC_GESTAO;
			restUtil.servico_rest=Constantes.SERVICO_REST_GESTAO;	
		}	
		
		private function init(ev:FlexEvent):void {
			configurarServicoRest();
			carregarCombo();
			carregarListeners();
		}
		
		private function carregarCombo():void {
			var filtroCombo:ArrayCollection = new ArrayCollection([
				{label:"SIGLA", data:1}, 
				{label:"CNPJ", data:2}, 
				{label:"N° CONVÊNIO", data:3},
			]);
			idComboProcura.dataProvider = new ArrayCollection;
			idComboProcura.dataProvider.addAll(filtroCombo);
		}
		
		private function carregarListeners():void {
			idComboProcura.addEventListener(Event.CHANGE, aplicarCampo);
			btnProcurar.addEventListener(MouseEvent.CLICK, btnProcurar_event);
			btIncluir.addEventListener(MouseEvent.CLICK, btIncluir_event);
		}
		
		private function aplicarCampo(eventObj:Event):void {
			inputFiltro.text = "";
			if (idComboProcura.value == 1) {
				inputFiltro.maxChars = 40;
			} else if (idComboProcura.value == 2) {
				inputFiltro.maxChars = 14;	
			} else if (idComboProcura.value == 3) {
				inputFiltro.maxChars = 30;
			}
		}
		
		private function btIncluir_event(eventObj:MouseEvent):void {
			var lista:ArrayCollection = grdResultado.recuperarLista() as ArrayCollection;
			for each(var convenio:Object in lista) {
				if(convenio.selecionado) {
					_listaConveniosSelecionados.addItem(convenio);
				}
			}
			var evento:Event = new Event(CONVENIO_EVENT);
			this.dispatchEvent(evento);
			this.fecharJanela();
			grdResultado.limparConteudo();
		}
		
		private function btnProcurar_event(eventObj:MouseEvent):void {
			if(idComboProcura.selectedItem == null){
				Alerta.show("Selecione um dado na Combo", "Campo obrigatório");
			} else {
				MostraCursor.setBusyCursor("Aguarde ...", this, MostraCursor.CURSOR_PROGRESSO);
				var painelFiltroGerenciaVO:Object =  new Object();
				painelFiltroGerenciaVO.filtroCombo = idComboProcura.selectedItem;
				if(inputFiltro.text == ""){
					restUtil.callGET("convenios",retornoPesquisaPainel);
				} else {
					painelFiltroGerenciaVO.filtroTexto = inputFiltro.text;
					if(idComboProcura.selectedIndex == 0){
						restUtil.callGET("convenios/sigla/"+inputFiltro.text,retornoPesquisaPainel);		
					} else if(idComboProcura.selectedIndex == 1) {
						restUtil.callGET("convenios/cnpj/"+inputFiltro.text,retornoPesquisaPainel);		
					} else if(idComboProcura.selectedIndex == 2) {
						restUtil.callGET("convenios/numero/"+inputFiltro.text,retornoPesquisaPainel);				
					}
				}
			}
		}
		
		private function retornoPesquisaPainel(event:EventREST):void {
			var respostaVO:Object = event.result;
			if(respostaVO) {
				if(!event.error) {
					var listaData:ArrayCollection =  new ArrayCollection(respostaVO.resultado as Array);
					if(listaData != null &&  listaData.length > 0) {
						for each (var removerConvenio:Object in listaConveniosSelecionados){
							for each (var convenio:Object in listaData){
								if (removerConvenio.id == convenio.id) {
									listaData.removeItemAt(listaData.getItemIndex(convenio));
								break;
								}
							}
						}
						grdResultado.montarConteudo(TabelaPaginadaUtils.criarConteudoTabela(listaData, grdResultado.tamanhoPagina));
					} else {
						Alerta.show("Não existe convênios para o filtro informado!", "Informação", Alerta.ALERTA_INFORMACAO);
						grdResultado.limparConteudo();
					}
				} else {
					Alerta.show(respostaVO.data, "Informação", Alerta.ALERTA_INFORMACAO);
				}
			} else {
				Alerta.show(event.error.message, "Informação", Alerta.ALERTA_INFORMACAO);
			}
			MostraCursor.removeBusyCursor();
		}
		
		
	}
}