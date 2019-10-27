package br.com.sicoob.cnv.gestao.base;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import br.com.sicoob.rest.aplicacao.SicoobRestAplicacao;

/**
 * Classe base de instancia para Application.
 *
 * @author luis.fernandez
 */
public class AplicacaoBase extends SicoobRestAplicacao {

	/**
	 * Instantiates a new aplicacao base.
	 *
	 * @param servletContext the servlet context
	 */
	public AplicacaoBase(@Context ServletContext servletContext) {
		super(servletContext);
	}

}
