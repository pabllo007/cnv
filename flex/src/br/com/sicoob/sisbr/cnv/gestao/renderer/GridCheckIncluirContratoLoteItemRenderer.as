package br.com.sicoob.sisbr.cnv.gestao.renderer
{
	import br.com.bancoob.componentes.renderes.CheckBoxRenderer;

	public class GridCheckIncluirContratoLoteItemRenderer extends CheckBoxRenderer {
		
		public function GridCheckIncluirContratoLoteItemRenderer() {
			super();
		}
	
		public override function set data(value:Object):void {
			this.selected = value.selecionado;
			super.data = value;
		}
	}
}