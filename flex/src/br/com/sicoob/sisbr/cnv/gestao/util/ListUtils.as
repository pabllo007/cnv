package br.com.sicoob.sisbr.cnv.gestao.util {
	import mx.collections.ArrayCollection;
	import mx.collections.Sort;
	import mx.collections.SortField;

	public class ListUtils {
		public function ListUtils() {
		
		}
		
		public static function sort(lista:ArrayCollection, nomeCampo:String):void {
			var dataSortField:SortField = new SortField();
			dataSortField.name = nomeCampo;
			dataSortField.numeric = false;
			dataSortField.descending = true;
			
			var sort:Sort = new Sort();
			sort.fields = [dataSortField];
			lista.sort = sort;
			lista.refresh();
		} 
		
	}
}