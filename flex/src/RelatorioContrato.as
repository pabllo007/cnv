package
{
	import flash.events.Event;
	import flash.events.MouseEvent;
	
	import mx.collections.ArrayCollection;
	import mx.effects.Resize;
	import mx.events.FlexEvent;
	
	import br.com.bancoob.componentes.Alerta;
	import br.com.bancoob.componentes.MostraCursor;
	import br.com.bancoob.componentes.containers.Janela;
	import br.com.bancoob.componentes.input.Data;
	import br.com.bancoob.componentes.tabelapaginada.TabelaPaginadaUtils;
	import br.com.bancoob.sisbr.portal.PortalModel;
	import br.com.bancoob.util.servico.ServicoJava;
	import br.com.bancoob.vo.DestinoVO;
	import br.com.sicoob.sisbr.cnv.gestao.relatorio.ImprimirRelatorio;
	import br.com.sicoob.sisbr.cnv.gestao.util.Constantes;
	import br.com.sicoob.sisbr.cnv.util.rest.RestUtils;
	import br.com.sicoob.sisbr.util.rest.EventREST;

	public class RelatorioContrato extends RelatorioContratoView {
		
		private var janela:Janela;
		private var imprimirRelatorio: ImprimirRelatorio;
		private var restUtil:RestUtils;
		private var destinoVO:DestinoVO;
		private var servico:ServicoJava = new ServicoJava();
		private var myResize:Resize = new Resize();
		private var bolFiltros:Boolean = false;
		private var filtroCombo:ArrayCollection = new ArrayCollection([
			{label:"Todos", data:0,  situacao: true, dtRescisaoFrmtd: true, numero: true, cnpj: true, sigla: true, dtAssContratoFrmtd: true,dtVigenciaFrmtd: true, 
				dtRenovacaoFrmtd: true, descSitRenovacao: true, alertaRenovacao: true, dtAtualTarifarioFrmtd: true, descSitTarifaria: true, alrtTarifariaDias: true, x:1260}, 
			
			{label:"Vigentes", data:1, numero: true, cnpj: true, sigla: true, dtAssContratoFrmtd: true, dtVigenciaFrmtd: true, dtRenovacaoFrmtd: true, x:650},
			
			{label:"Encerrados", data:2, dtRescisaoFrmtd: true, numero: true, cnpj: true, sigla: true, dtAssContratoFrmtd: true, x:600},
			
			{label:"Renovação", data:3, numero: true, cnpj: true, sigla: true, dtAssContratoFrmtd: true, dtVigenciaFrmtd: true, dtRenovacaoFrmtd: true, alertaRenovacao: true, acaoOpRenovacao: true, x:850},
			
			{label:"Vencidos", data:4, numero: true, cnpj: true, sigla: true, dtAssContratoFrmtd: true, dtVigenciaFrmtd: true,dtRenovacaoFrmtd: true, alertaRenovacao: true, acaoOpRenovacao: true, x:850},
			
			{label:"Atualização Tarifária", data:5, numero: true, cnpj: true, sigla: true, dtAssContratoFrmtd: true, dtVigenciaFrmtd: true, dtAtualTarifarioFrmtd: true, 
				alrtTarifariaDias: true, acaoOpTarifaria: true, x:900},
		]);
		
		public function RelatorioContrato() {
			super();
			this.addEventListener(FlexEvent.CREATION_COMPLETE, init);
		}
	
		private function init(ev:FlexEvent):void {
			configurarServicoRest();
			configurarServico();
			carregarListeners();
			moverFiltroRelatorio();
		}
	
		private function configurarServicoRest():void {
			restUtil = new RestUtils(false);
			restUtil.basic = Constantes.BASIC_GESTAO;
			restUtil.servico_rest=Constantes.SERVICO_REST_GESTAO;	
		}
		
		private function configurarServico():void {
			PortalModel.portal.obterDefinicoesDestino(Constantes.SERVICO_JAVA_GESTAO, onDestinoRecuperado);
		}
		
		private function onDestinoRecuperado(destino:DestinoVO):void {
			destinoVO = destino;
			servico.configurarDestino(destinoVO);
			
		}
		
		private function moverFiltroRelatorio():void {			
			(bolFiltros == false) ? bolFiltros = false : bolFiltros = true;
			btnFiltros_event(null);
			
		}
		
		private function carregarListeners():void {
			btnPesquisar.addEventListener(MouseEvent.CLICK,btnPesquisar_event);
			btnFiltros.addEventListener(MouseEvent.CLICK, btnFiltros_event);
			btnLimpar.addEventListener(MouseEvent.CLICK, btnLimpar_event);
			btImprimir.addEventListener(MouseEvent.CLICK, btImprimir_event);
			tipoRelatorio.addEventListener(Event.CHANGE, cmbRelatorio_change);
		}
		
		private function btnPesquisar_event(eventObj:MouseEvent):void {
			MostraCursor.setBusyCursor("Aguarde ...", this, MostraCursor.CURSOR_PROGRESSO);
			var itemSelecionado:Object = filtroCombo.getItemAt(validarRadioButton() as int);
			if(itemSelecionado != null){
				itemSelecionado.situacao == true ? situacao.visible = itemSelecionado.situacao : situacao.visible = false;
				itemSelecionado.dtRescisaoFrmtd == true ? dtRescisaoFrmtd.visible = itemSelecionado.dtRescisaoFrmtd : dtRescisaoFrmtd.visible = false;
				itemSelecionado.numero == true ? numero.visible = itemSelecionado.numero : numero.visible = false;
				itemSelecionado.cnpj == true ? cnpj.visible = itemSelecionado.cnpj : cnpj.visible == false;
				itemSelecionado.sigla == true ? sigla.visible = itemSelecionado.sigla : sigla.visible = false;
				itemSelecionado.dtAssContratoFrmtd == true ? dtAssContratoFrmtd.visible = itemSelecionado.dtAssContratoFrmtd : dtAssContratoFrmtd.visible = false;
				itemSelecionado.dtVigenciaFrmtd == true ? dtVigenciaFrmtd.visible = itemSelecionado.dtVigenciaFrmtd : dtVigenciaFrmtd.visible = false;
				itemSelecionado.dtRenovacaoFrmtd == true ? dtRenovacaoFrmtd.visible = itemSelecionado.dtRenovacaoFrmtd : dtRenovacaoFrmtd.visible = false;
				itemSelecionado.descSitRenovacao == true ? descSitRenovacao.visible = itemSelecionado.descSitRenovacao : descSitRenovacao.visible = false;
				itemSelecionado.alertaRenovacao == true ? alertaRenovacao.visible = itemSelecionado.alertaRenovacao : alertaRenovacao.visible = false;
				itemSelecionado.dtAtualTarifarioFrmtd == true ? dtAtualTarifarioFrmtd.visible = itemSelecionado.dtAtualTarifarioFrmtd : dtAtualTarifarioFrmtd.visible = false;
				itemSelecionado.descSitTarifaria == true ? descSitTarifaria.visible = itemSelecionado.descSitTarifaria : descSitTarifaria.visible = false;
				itemSelecionado.alrtTarifariaDias == true ? alrtTarifariaDias.visible = itemSelecionado.alrtTarifariaDias : alrtTarifariaDias.visible = false;
				itemSelecionado.acaoOpRenovacao == true ? acaoOpRenovacao.visible = itemSelecionado.acaoOpRenovacao : acaoOpRenovacao.visible = false;
				itemSelecionado.acaoOpTarifaria == true ? acaoOpTarifaria.visible = itemSelecionado.acaoOpTarifaria : acaoOpTarifaria.visible = false;
				this.width = itemSelecionado.x as Number;

			}
			if(validarCampos()){
				var ObjetoParaServidor:Object =  new Object();
				if(dataInicio.selectedDate != null && dataFim.selectedDate != null){
					ObjetoParaServidor.dataInicioPeriodo = dataInicio.selectedDate.fullYear.toString()+"-"+(dataInicio.selectedDate.month + 1).toString()+"-"+dataInicio.selectedDate.date.toString();
					ObjetoParaServidor.dataFimPeriodo = dataFim.selectedDate.fullYear.toString()+"-"+(dataFim.selectedDate.month + 1).toString()+"-"+dataFim.selectedDate.date.toString();
					if(dataInicio.selectedDate > dataFim.selectedDate){
						Alerta.show("A Data Inicial deve ser maior que a Data Final !", "Informação", Alerta.ALERTA_INFORMACAO);
						dataInicio.selectedDate = null;
						dataFim.selectedDate = null;
					} else {
						restUtil.callPOST("relatorio-contrato/"+itemSelecionado.data.toString(), ObjetoParaServidor, retornoPesquisaPainel);
					}
				} else {
					restUtil.callPOST("relatorio-contrato/"+itemSelecionado.data.toString(), ObjetoParaServidor, retornoPesquisaPainel);
				}
			}
			
		}
		
		private function validarRadioButton():Number {
			if(rTodos.selected){
				grdResultado.titulo = "Relatório - Todos";
				return 0;
			}else if(rVigente.selected){
				grdResultado.titulo = "Relatório - Vigente";
				return 1;
			}else if(rEncerrados.selected){
				grdResultado.titulo = "Relatório - Encerrado";
				return 2;
			}else if(rRenovacao.selected){
				grdResultado.titulo = "Relatório - Renovação";
				return 3;
			}else if(rVencidos.selected){
				grdResultado.titulo = "Relatório - Vencidos";
				return 4;
			}else if(rTarifaria.selected){
				grdResultado.titulo = "Relatório - Atualização Tarifária";
				return 5;
			}
			return Number.NaN;
		}
		
		private function validarCampos():Boolean {
			if( rTodos.selected || rVigente.selected || rEncerrados.selected || rVencidos.selected){
				grdResultado.limparConteudo();
				return true;
			} else {
				if((rRenovacao.selected || rTarifaria.selected) && (dataInicio.selectedDate != null && dataFim.selectedDate != null)){
					grdResultado.limparConteudo();
					return true;
				} else if(dataInicio.selectedDate == null || dataFim.selectedDate == null){
					Alerta.show("A Data Incial e Data Final são de preenchimento obrigatório !", "Informação", Alerta.ALERTA_INFORMACAO);
				}
			}
			return false;
		}
		
		private function retornoPesquisaPainel(event:EventREST):void {
			var respostaVO:Object = event.result;
			if(respostaVO) {
				moverFiltroRelatorio();
				btImprimir.enabled = true;
				if(!event.error) {
					var listaData:ArrayCollection =  new ArrayCollection(respostaVO.resultado as Array);
					if(listaData.length == 0){
						Alerta.show("Nenhum registro encontrado !", "Informação", Alerta.ALERTA_INFORMACAO);
					}
					var listaGrid:ArrayCollection = new ArrayCollection();
					for each(var contrato:Object in listaData){
						var contratoResult:Object = new Object();
						contratoResult.situacao = contrato.situacao;
						contratoResult.dataRescisaoFormatada = contrato.dataRescisaoFormatada != "" ? contrato.dataRescisaoFormatada : "-";
						contratoResult.numero = contrato.numero;
						contratoResult.cnpj = contrato.cnpj;
						contratoResult.sigla = contrato.sigla;
						contratoResult.dataAssinaturaContratoFormatada = contrato.dataAssinaturaContratoFormatada != "" ? contrato.dataAssinaturaContratoFormatada : "-";
						contratoResult.dataVigenciaFormatada = contrato.dataVigenciaFormatada != "" ? contrato.dataVigenciaFormatada : "-";
						contratoResult.dataRenovacaoFormatada = contrato.dataRenovacaoFormatada != "" ? contrato.dataRenovacaoFormatada : "-"; 
						var acaoOperacionalRenovacaoString:String = contrato.acaoOperacionalRenovacao ;
						contratoResult.acaoOperacionalRenovacao = classificarOperacional(acaoOperacionalRenovacaoString);
						contratoResult.alertaRenovacaoDias = contrato.alertaRenovacaoDias;
						contratoResult.dataAtualizacaoTarifarioFormatada = contrato.dataAtualizacaoTarifarioFormatada != "" ? contrato.dataAtualizacaoTarifarioFormatada : "-";
						var acaoOperacionalTarifariaString:String = contrato.acaoOperacionalTarifaria ;
						contratoResult.acaoOperacionalTarifaria = classificarOperacional(acaoOperacionalTarifariaString);
						contratoResult.alertaTarifariaDias = contrato.alertaTarifariaDias;
						contratoResult.descSituacaoRenovacao = contrato.descSituacaoRenovacao;
						contratoResult.descSituacaoTarifaria = contrato.descSituacaoTarifaria;

						listaGrid.addItem(contratoResult);
					}
					grdResultado.montarConteudo(TabelaPaginadaUtils.criarConteudoTabela(listaGrid, grdResultado.tamanhoPagina));

				}else {
					Alerta.show(respostaVO.data, "Informação", Alerta.ALERTA_INFORMACAO);
				}
			}else {
				Alerta.show(event.error.mensagens[0].mensagem, "Informação", Alerta.ALERTA_INFORMACAO);
			}
			MostraCursor.removeBusyCursor();
		}
		
		private function classificarOperacional( operacional:String=null ) :String {
			if(operacional == null) {
				return "-";
			} else {
				if(operacional == "true"){
					return "Iniciado";
				} else {
					return "Pendente";
				}
			}
		}
		
		private function btnFiltros_event(eventObj:MouseEvent):void {
			myResize.target = pnMenu;
			if(!bolFiltros){
				bolFiltros = true;
				myResize.widthBy = 400;
				habilitarEstilos();
				myResize.play([pnMenu]);
			}else{
				bolFiltros = false;
				myResize.widthBy = -400;
				desabilitarEstilos();
				myResize.play([pnMenu]);
			}
		}
		
		private function btnLimpar_event(eventObj:MouseEvent):void {
			dataInicio.selectedDate = null;
			dataFim.selectedDate = null;
		}
		
		private function btImprimir_event(eventObj:MouseEvent):void {
			var diaInicio:String = "";
			var mesInicio:String = "";
			var diaFim:String = "";
			var mesFim:String = "";
			janela = new Janela();
			imprimirRelatorio = new ImprimirRelatorio();
			imprimirRelatorio.numRelatorio = validarRadioButton();
			imprimirRelatorio.dataInicio = dataInicio.selectedDate != null ? convertDataToString(dataInicio)+"/"+ dataInicio.selectedDate.fullYear.toString() : null;
			imprimirRelatorio.dataFim = dataFim.selectedDate != null ? convertDataToString(dataFim)+"/"+ dataFim.selectedDate.fullYear.toString() : null;
			janela.icone = "br/com/bancoob/imagens/imagens/icos/modulos/cadastroClientes_peq.png";
			janela.addChild(imprimirRelatorio);
			janela.title="Opções de Impressão";
			janela.abrir(this.parent,false,true);
		}
		
		private function convertDataToString(data:Data):String {
			var dia:String = "";
			var mes:String = "";
			if(data.selectedDate.date < 10){
				dia = "0" + data.selectedDate.date.toString();
			}else {
				dia = data.selectedDate.date.toString();
			}
			if((data.selectedDate.month + 1) < 10){
				mes = "0" + (data.selectedDate.month + 1).toString();
			}else {
				mes = (data.selectedDate.month + 1).toString();
			}
			return dia+"/"+mes;
		}
		
		private function cmbRelatorio_change(event:Event):void {
			if (rTodos.selected || rVigente.selected || rEncerrados.selected || rVencidos.selected){
				subtituloPeriodo.visible = false;
				periodoContainer.visible = false;
				dataInicio.selectedDate = null;
				dataFim.selectedDate = null;
			} else if( rRenovacao.selected || rTarifaria.selected){
				subtituloPeriodo.visible = true;
				periodoContainer.visible = true;
			} 
		}
		
		private function habilitarEstilos():void{
			btnFiltros.setStyle("icon", Recolher);
			pnMenu.setStyle("modalTransparency", 0.0);
			pnMenu.setStyle("dropShadowEnabled",true);
			btnPesquisar.setStyle("dropShadowEnabled",true);
			btnPesquisar.visible = true;
		}
		
		private function desabilitarEstilos():void{
			btnFiltros.setStyle("icon", Mostrar);
			pnMenu.setStyle("dropShadowEnabled",false);
			btnPesquisar.setStyle("dropShadowEnabled",false);
			btnPesquisar.visible = false;			
		}
		
	}
	
}