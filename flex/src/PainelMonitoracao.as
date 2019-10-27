package
{
	import flash.events.MouseEvent;
	
	import mx.effects.Resize;
	import mx.events.FlexEvent;
	
	import br.com.sicoob.sisbr.cnv.gestao.util.Constantes;
	import br.com.sicoob.sisbr.cnv.util.rest.RestUtils;

	public class PainelMonitoracao extends PainelMonitoracaoView {
		
		private var restUtil:RestUtils;
		private var myResize:Resize = new Resize();
		private var bolFiltros:Boolean = false;
		
		public function PainelMonitoracao() {
			super();
			this.addEventListener(FlexEvent.CREATION_COMPLETE, init);
		}
	
		private function init(ev:FlexEvent):void {
			configurarServicoRest();
			carregarListeners();
		}
	
		private function configurarServicoRest():void {
			restUtil = new RestUtils(false);
			restUtil.basic = Constantes.BASIC_GESTAO;
			restUtil.servico_rest=Constantes.SERVICO_REST_GESTAO;	
		}
		
		private function carregarListeners():void {
			btnPesquisar.addEventListener(MouseEvent.CLICK,btnPesquisar_event);
		}
		
		private function btnPesquisar_event(eventObj:MouseEvent):void {
			
		}
		
		private function btnFiltros_event(eventObj:MouseEvent):void {
			myResize.target = pnMenu;
			if(!bolFiltros){
				bolFiltros = true;
				myResize.widthBy = 350;
				habilitarEstilos();
				myResize.play([pnMenu]);
			}else{
				bolFiltros = false;
				myResize.widthBy = -350;
				desabilitarEstilos();
				myResize.play([pnMenu]);
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