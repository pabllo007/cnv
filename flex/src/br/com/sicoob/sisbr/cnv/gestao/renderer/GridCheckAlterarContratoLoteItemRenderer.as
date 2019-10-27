package br.com.sicoob.sisbr.cnv.gestao.renderer
{
	import br.com.bancoob.componentes.renderes.CheckBoxRenderer;

	public class GridCheckAlterarContratoLoteItemRenderer extends CheckBoxRenderer{
		
		public function GridCheckAlterarContratoLoteItemRenderer() {
			super();		
			this.selected = true;
		}
		
		public override function set data(value:Object):void {
			this.selected = value.selecionado;
			super.data = value;
		}
	}
}