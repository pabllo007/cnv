
package br.com.sicoob.cnv.gestao.util.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Conversor de CNPJ.
 *
 * @author 
 */
@Converter
public class CnpjConverter implements AttributeConverter<String, String> {

	@Override
	public String convertToDatabaseColumn(String attribute) {
		/*
		 * Irá converter CNPJ formatado para um sem pontos, traço e barra. Ex.:
		 * 07.374.998/0001-33 torna-se 07374998000133.
		 */
		String cnpj = attribute;
		if (attribute != null && !attribute.equals("")) {
			cnpj = attribute.replaceAll("\\.", "").replaceAll("\\-", "").replaceAll("/", "");
		}
		return cnpj;
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		/*
		 * Irá converter CNPJ não formatado para um com pontos, traço e barra. Ex.:
		 * 07374998000133 torna-se 07.374.998/0001-33.
		 */
		String cnpj = dbData;
		if (cnpj != null && cnpj.length() == 14) {
			cnpj = cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." + cnpj.substring(5, 8) + "/"
					+ cnpj.substring(8, 12) + "-" + cnpj.substring(12, 14);
		}
		return cnpj;
	}

}