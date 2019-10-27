package br.com.sicoob.sisbr.cnv.gestao.manter
{
	import flash.utils.setTimeout;
	
	import mx.collections.ArrayCollection;
	import mx.events.FlexEvent;
	import mx.utils.StringUtil;
	
	import br.com.bancoob.componentes.Alerta;
	import br.com.bancoob.componentes.MostraCursor;
	import br.com.bancoob.util.DateUtils;
	import br.com.bancoob.util.StringUtils;
	import br.com.sicoob.sisbr.cnv.gestao.util.Constantes;
	import br.com.sicoob.sisbr.cnv.util.rest.RestUtils;
	import br.com.sicoob.sisbr.util.rest.EventREST;

	public class VisualizarContrato extends VisualizarContratoView {

		private var restUtil:RestUtils;
		private var _contratoVO:Object;
		
		public function set contratoVO(value:Object):void
		{
			_contratoVO = value;
		}
		
		public function VisualizarContrato() {
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
			setTimeout(carregaRest, 1500);
		}
		
		private function carregarListeners():void {
			
		}
		
		private function carregaRest():void {
			MostraCursor.setBusyCursor("Aguarde ...", this, MostraCursor.CURSOR_PROGRESSO);
			restUtil.callGET("contratos/"+_contratoVO.id, carregaDadosContrato);
//			restUtil.callGET("/integracao/capes/"+_contratoVO.cnpj, carregaDadosDoCaps);
		}
		
		private function carregaDadosContrato(event:EventREST):void {
			var respostaVO:Object = event.result;
			if(respostaVO) {
				if(!event.error) {
					inputCNPJ.text = StringUtils.trim(_contratoVO.cnpj);
					inputSigla.text = StringUtils.trim(_contratoVO.sigla);
					inputNumContrato.text = StringUtils.trim(_contratoVO.numero);
					inputRazaoFiltro.text = StringUtils.trim(_contratoVO.razaoSocial);
					inputNome.text = StringUtils.trim(_contratoVO.fantasia);
					inputSituacaoContrato.text = StringUtil.trim(_contratoVO.situacao);
					dataAssinaturaContrato.selectedDate = DateUtils.stringToDate(respostaVO.resultado.dataAssinaturaContrato,"YYYY-MM-DD");
					dataVigencia.selectedDate = DateUtils.stringToDate(respostaVO.resultado.dataVigencia,"YYYY-MM-DD");
					if(respostaVO.resultado.dataVigencia == null){
						checkIndeterminado.enabled = true;
					}
					if(respostaVO.resultado.dataRescisao != null){
						dataEncerramento.selectedDate = DateUtils.stringToDate(respostaVO.resultado.dataRescisao,"YYYY-MM-DD");
					}
					dataRenovacao.selectedDate = DateUtils.stringToDate(respostaVO.resultado.dataRenovacao,"YYYY-MM-DD");
					dataAtualizacaoTarifaria.selectedDate = DateUtils.stringToDate(respostaVO.resultado.dataAtualizacaoTarifario,"YYYY-MM-DD");
					idComboRenovacao.text = respostaVO.resultado.alertaRenovacao.dias;
					idComboAtualizacao.text = respostaVO.resultado.alertaTarifaria.dias;
					var listaDatas:ArrayCollection = new ArrayCollection(respostaVO.resultado.convenios);
					if(listaDatas != null &&  listaDatas.length > 0) {
						grdResultado.dataProvider = listaDatas;
					}
				}else {
					Alerta.show(respostaVO.data, "Informação", Alerta.ALERTA_INFORMACAO);
				}
			}else {
				Alerta.show(event.error.message, "Informação", Alerta.ALERTA_INFORMACAO);
			}
			MostraCursor.removeBusyCursor();
		}
		
		
	}
}