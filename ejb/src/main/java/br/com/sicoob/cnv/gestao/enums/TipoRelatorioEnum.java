package br.com.sicoob.cnv.gestao.enums;

public enum TipoRelatorioEnum {

	TODOS(0), VIGENTES(1), ENCERRADOS(2), PERIODORENOVACAO(3), VENCIDOS(4), ATUALIZACAOTARIFARIA(5);

	private Integer codigo;

	private TipoRelatorioEnum(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCodigo() {
		return codigo;
	}
	
	public static TipoRelatorioEnum getTipoRelatorio(Integer codigo) {
		TipoRelatorioEnum tipo = null;
		for (TipoRelatorioEnum tipoR : TipoRelatorioEnum.values()) {
			if(codigo.intValue() == tipoR.getCodigo().intValue()) {
				tipo = tipoR;
				break;
			}
		}
		return tipo;
	}

}
