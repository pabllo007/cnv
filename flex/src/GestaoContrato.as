package
{
	import flash.events.Event;
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	
	import mx.collections.ArrayCollection;
	import mx.events.FlexEvent;
	
	import br.com.bancoob.componentes.Alerta;
	import br.com.bancoob.componentes.MostraCursor;
	import br.com.bancoob.componentes.containers.Janela;
	import br.com.bancoob.componentes.tabelapaginada.TabelaPaginadaUtils;
	import br.com.sicoob.sisbr.cnv.gestao.util.Constantes;
	import br.com.sicoob.sisbr.cnv.util.rest.RestUtils;
	import br.com.sicoob.sisbr.util.rest.EventREST;
	import br.com.sicoob.sisbr.cnv.gestao.manter.GerenciarContrato;
	import br.com.sicoob.sisbr.cnv.gestao.manter.AlterarContrato;
	import br.com.sicoob.sisbr.cnv.gestao.manter.IncluirContrato;
	import br.com.sicoob.sisbr.cnv.gestao.manter.VisualizarContrato;

	public class GestaoContrato extends GestaoContratoView {
		
		private var restUtil:RestUtils;
		private var janela:Janela;
		private var incluirContrato:IncluirContrato;
		private var visualizarContrato:VisualizarContrato;
		private var gerenciarContrato:GerenciarContrato = new GerenciarContrato();
		private var alterarContrato:AlterarContrato = new AlterarContrato();
		
		public function GestaoContrato() {
			super();
			this.addEventListener(FlexEvent.CREATION_COMPLETE, init);
		}
		
		private function init(ev:FlexEvent):void {
			configurarServicoRest();
			carregarCombos();	
			carregarListeners();
		}
		
		private function configurarServicoRest():void {
			restUtil = new RestUtils(false);
			restUtil.basic = Constantes.BASIC_GESTAO;
			restUtil.servico_rest=Constantes.SERVICO_REST_GESTAO;	
		}
		
		private function carregarCombos():void {
			var filtroCombo:ArrayCollection = new ArrayCollection([
				{label:"SIGLA", data:1}, 
				{label:"CNPJ", data:2}, 
				{label:"N° CONTRATO", data:3},
			]);
			cmbFiltro.dataProvider = new ArrayCollection;
			cmbFiltro.dataProvider.addAll(filtroCombo);
			inputFiltro.maxChars = 40;
		}
		
		private function carregarListeners():void {
			cmbFiltro.addEventListener(Event.CHANGE, aplicarCampo);
			btnProcurar.addEventListener(MouseEvent.CLICK, btnProcurar_event);
			btVisualizar.addEventListener(MouseEvent.CLICK, btnVisualizar_event);
			btIncluir.addEventListener(MouseEvent.CLICK, btnIncluir_event);
			btAlterar.addEventListener(MouseEvent.CLICK, btnAlterar_event);
			btGerenciar.addEventListener(MouseEvent.CLICK, btnGereciar_event);
			grdResultado.addEventListener(MouseEvent.CLICK, registroSelecionado);
			grdResultado.addEventListener(KeyboardEvent.KEY_UP, registroSelecionado);
			alterarContrato.addEventListener(alterarContrato.SALVOU_EVENT, recarregarTela);
			gerenciarContrato.addEventListener(gerenciarContrato.SALVOU_EVENT, recarregarTela);
		}
		
		private function recarregarTela(event:Event):void {
			pesquisarContratos();
		}
		
		private function aplicarCampo(eventObj:Event):void {
			inputFiltro.text = "";
			if (cmbFiltro.value == 1) {
				inputFiltro.maxChars = 40;
			} else if (cmbFiltro.value == 2) {
				inputFiltro.maxChars = 14;	
			} else if (cmbFiltro.value == 3) {
				inputFiltro.maxChars = 30;
			}
		}
		
		private function btnProcurar_event(eventObj:MouseEvent):void {
			pesquisarContratos();
		}
		
		private function btnVisualizar_event(eventObj:MouseEvent):void {
			janela = new Janela();
			visualizarContrato = new VisualizarContrato();
			visualizarContrato.contratoVO = grdResultado.grdDados.selectedItem;
			janela.addChild(visualizarContrato);
			janela.title="Visualizar Contrato";
			janela.abrir(this.parent.parent,true,true);
		}
		
		private function btnIncluir_event(eventObj:MouseEvent):void {
			janela = new Janela();
			incluirContrato = new IncluirContrato();
			incluirContrato.contratoVO = grdResultado.grdDados.selectedItem;
			janela.addChild(incluirContrato);
			janela.title="Incluir Contrato";
			janela.abrir(this.parent.parent,true,true);
		}
		
		private function btnAlterar_event(eventObj:MouseEvent):void {
			janela = new Janela();
			alterarContrato = new AlterarContrato();
			alterarContrato.addEventListener(alterarContrato.SALVOU_EVENT, recarregarTela);
			alterarContrato.contratoVO = grdResultado.grdDados.selectedItem;
			janela.addChild(alterarContrato);
			janela.title="Alterar Contrato";
			janela.abrir(this.parent.parent,true,true);
		}
		
		private function btnGereciar_event(eventObj:MouseEvent):void {
			janela = new Janela();
			gerenciarContrato = new GerenciarContrato();
			gerenciarContrato.addEventListener(gerenciarContrato.SALVOU_EVENT, recarregarTela);
			gerenciarContrato.contratoVO = grdResultado.grdDados.selectedItem;
			janela.addChild(gerenciarContrato);
			janela.title="Gerenciar Contrato";
			janela.x = 330;
			janela.y = 11;
			janela.abrir(this.parent,true,false);
		}
		
		private function registroSelecionado(ev:Event):void {
			if(grdResultado.recuperarItemSelecionado()) {
				btVisualizar.enabled = true;
				btAlterar.enabled = true;
				if( grdResultado.recuperarItemSelecionado().situacao == "ENCERRADO"){
					btGerenciar.enabled = false;		
				} else {
					btGerenciar.enabled = true;
				}
			}
		}
		
		private function retornoPesquisaPainel(event:EventREST):void {
			var respostaVO:Object = event.result;
			if(respostaVO) {
				if(!event.error) {
					var listaData:ArrayCollection =  new ArrayCollection(respostaVO.resultado as Array);
					if(listaData != null &&  listaData.length > 0) {
						grdResultado.montarConteudo(TabelaPaginadaUtils.criarConteudoTabela(listaData, grdResultado.tamanhoPagina));
					} else {
						Alerta.show("Não existe contratos para o filtro informado!", "Informação", Alerta.ALERTA_INFORMACAO);
						inputFiltro.text="";
					}
				} else {
					Alerta.show(respostaVO.data, "Informação", Alerta.ALERTA_INFORMACAO);
				}
			} else {
				Alerta.show(event.error.mensagens[0].mensagem, "Informação", Alerta.ALERTA_INFORMACAO);
			}
			MostraCursor.removeBusyCursor();
			
		}
		
		private function pesquisarContratos():void {
			btVisualizar.enabled = false;
			btAlterar.enabled = false;
			btGerenciar.enabled = false;
			if(cmbFiltro.selectedItem == null){
				Alerta.show("Selecione um dado na Combo", "Campo obrigatório");
			} else {
				MostraCursor.setBusyCursor("Aguarde ...", this, MostraCursor.CURSOR_PROGRESSO);
				var painelFiltroGerenciaVO:Object =  new Object();
				painelFiltroGerenciaVO.filtroCombo = cmbFiltro.selectedItem;
				if(inputFiltro.text == ""){
					restUtil.callGET("contratos",retornoPesquisaPainel);
				} else {
					painelFiltroGerenciaVO.filtroTexto = inputFiltro.text;
					if(cmbFiltro.selectedIndex == 0){
						restUtil.callGET("contratos/sigla/"+inputFiltro.text,retornoPesquisaPainel);		
					} else if(cmbFiltro.selectedIndex == 1) {
						restUtil.callGET("contratos/cnpj/"+inputFiltro.text,retornoPesquisaPainel);		
					} else if(cmbFiltro.selectedIndex == 2) {
						restUtil.callGET("contratos/numero/"+inputFiltro.text,retornoPesquisaPainel);				
					}
				}
			}
		}
		
		
	}
}