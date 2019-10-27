package br.com.sicoob.sisbr.cnv.gestao.relatorio
{
	import flash.events.Event;
	import flash.events.MouseEvent;
	
	import mx.collections.ArrayCollection;
	import mx.events.FlexEvent;
	
	import br.com.bancoob.sisbr.DadosLogin;
	import br.com.bancoob.sisbr.portal.PortalModel;
	import br.com.bancoob.sisbr.relatorios.componentes.preImpressao.PreImpressao;
	import br.com.bancoob.sisbr.relatorios.dto.ParametroDTO;
	import br.com.bancoob.sisbr.relatorios.util.RelatorioUtil;
	import br.com.bancoob.util.servico.ServicoJava;
	import br.com.bancoob.vo.DestinoVO;
	import br.com.sicoob.sisbr.cnv.gestao.util.Constantes;

	public class ImprimirRelatorio extends ImprimirRelatorioView {
		
		public var dataInicio:String;
		public var dataFim:String;
		public var numRelatorio:Number;
		private var destinoVO:DestinoVO;
		private var servico:ServicoJava = new ServicoJava();
		
		public function ImprimirRelatorio() {
			super();
			this.addEventListener(FlexEvent.CREATION_COMPLETE, init);
		}
		
		private function init(ev:FlexEvent):void {
			configurarServico();
			preencherCombo();
			carregarListeners();
		}
		
		private function configurarServico():void {
			PortalModel.portal.obterDefinicoesDestino(Constantes.SERVICO_JAVA_GESTAO, onDestinoRecuperado);
		}
		
		private function onDestinoRecuperado(destino:DestinoVO):void {
			destinoVO = destino;
			servico.configurarDestino(destinoVO);
		}
		
		private function preencherCombo():void {
			var filtroCombo:ArrayCollection = new ArrayCollection([
				{label:"PDF", data:0}, 
				{label:"XLS", data:1},
			]);
			cmbRelatorio.dataProvider = new ArrayCollection;
			cmbRelatorio.dataProvider.addAll(filtroCombo);
		}
		
		private function carregarListeners():void {
			btGerar.addEventListener(MouseEvent.CLICK, btGerar_change);
		}
		
		private function btGerar_change(eventObj:Event):void {
			if(cmbRelatorio.selectedItem.data == 0){
				btImprimir_event();
			}else {
				btImprimirXLS_event();
			}
			this.fecharJanela();
			
		}
		
		private function btImprimir_event():void {
			var relatorio:RelatorioUtil = RelatorioUtil.create();
			var dto:ParametroDTO = new ParametroDTO();
			dto.dados.numRelatorio = numRelatorio;
			dto.dados.numCooperativa = DadosLogin.coop;
			dto.dados.dataInicio = dataInicio;
			dto.dados.dataFim = dataFim;
			relatorio.emitirRelatorio("cnv/gestao/RelatorioContratoReportEJB", dto, 
				"CNV056RelatorioGestaoContrato", destinoVO, "Emitindo documento..." , PreImpressao.FORMATO_PDF, false);
		}
		
		private function btImprimirXLS_event():void {
			var relatorio:RelatorioUtil = RelatorioUtil.create();
			var dto:ParametroDTO = new ParametroDTO();
			dto.dados.numRelatorio = numRelatorio;
			dto.dados.numCooperativa = DadosLogin.coop;
			dto.dados.dataInicio = dataInicio;
			dto.dados.dataFim = dataFim;
			relatorio.emitirRelatorio("cnv/gestao/RelatorioContratoReportEJB", dto, 
				"CNV056RelatorioGestaoContrato", destinoVO, "Emitindo documento..." , PreImpressao.FORMATO_XLS, false);
		}
		
		
	}
}