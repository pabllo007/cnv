package br.com.sicoob.cnv.gestao.ejb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import br.com.sicoob.cnv.framework.persistencia.ejb.CnvBaseEJB;
import br.com.sicoob.cnv.framework.persistencia.excecao.CnvException;
import br.com.sicoob.cnv.gestao.entity.Convenio;
import br.com.sicoob.cnv.gestao.factory.ConvenioDB2FactoryDAO;
import br.com.sicoob.cnv.gestao.validador.ValidadorCpfCnpj;
import br.com.sicoob.cnv.gestao.vo.ConvenioListagemVO;

/**
 * EJB de convênios.
 *
 * @author Bruno Santos
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class GestaoConvenioEJB extends CnvBaseEJB
{
	/** DAO de convênios. */
	@Inject
	private ConvenioDB2FactoryDAO<Convenio> convenioDao;

	/**
	 * Busca todos os convênios não vinculados a um contrato..
	 *
	 * @return lista de convênios
	 * @throws CnvException exceção de gestão
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConvenioListagemVO> buscarLivres() throws CnvException
    {
        try
        {
            return (List<ConvenioListagemVO>) convenioDao.consultarRegistrosVO("CONVENIOS_LIVRES", null, ConvenioListagemVO.class);
        }
        catch (final CnvException e)
        {
			throw new CnvException(e);
		}
	}
	/**
	 * Consulta convênios por CNPJ.
	 *
	 * @param cnpj CNPJ da empresa.
	 * @return lista de convênios
	 * @throws CnvException exceção de gestão
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConvenioListagemVO> buscarLivresPorCNPJ(String cnpj) throws CnvException
    {
		if (!ValidadorCpfCnpj.validarCpfCnpj(cnpj))
		{
			throw new CnvException("MSG_E004", "CNPJ");
		}
        try
        {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("cnpj", cnpj);
			return (List<ConvenioListagemVO>) convenioDao.consultarRegistrosVO("CONVENIOS_LIVRES_POR_CNPJ", parametros,
				ConvenioListagemVO.class);
        }
        catch (final CnvException e)
        {
			throw new CnvException(e);
		}
	}
	/**
	 * Consulta convênios por número.
	 *
	 * @param numero número do contrato
	 * @return lista de convênios
	 * @throws CnvException exceção de gestão
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConvenioListagemVO> buscarLivresPorNumero(String numero) throws CnvException
    {
        try
        {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("numero", numero);
			return (List<ConvenioListagemVO>) convenioDao.consultarRegistrosVO("CONVENIOS_LIVRES_POR_NUMERO", parametros,
				ConvenioListagemVO.class);
        }
        catch (final CnvException e)
        {
			throw new CnvException(e);
		}
	}
	/**
	 * Consulta convênios por sigla
	 *
	 * @param sigla sigla do contrato
	 * @return lista de convênios
	 * @throws CnvException exceção de gestão
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ConvenioListagemVO> buscarLivresPorSigla(String sigla) throws CnvException
    {
        try
        {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("sigla", sigla);
			return (List<ConvenioListagemVO>) convenioDao.consultarRegistrosVO("CONVENIOS_LIVRES_POR_SIGLA", parametros,
				ConvenioListagemVO.class);
        }
        catch (final CnvException e)
        {
			throw new CnvException(e);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object> listaConveniosSelecaoGeral(String nomeColuna, String valorColuna) throws CnvException{
		return convenioDao.listaConveniosSelecaoGeral(nomeColuna, valorColuna);	
	}
	
}