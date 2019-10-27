package br.com.sicoob.cnv.gestao.vo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

/**
 * VO para listagem de convênios.
 *
 * @author Bruno Santos
 */
@Entity
@SqlResultSetMapping
(
    entities = @EntityResult(entityClass = ConvenioListagemVO.class),
    name = "ConvenioListagemVO"
)
public class ConvenioListagemVO implements Serializable
{
    @Id
    private long id;
    private boolean selecionado;
    private static final long serialVersionUID = 2691935101048974304L;
    private String cnpj, febraban, numero, segmento, sigla, situacao;

	/**
	 * Instancia o convênio.
	 */
	public ConvenioListagemVO()
	{
		setSelecionado(true);
	}
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the selecionado
     */
    public boolean isSelecionado() {
        return selecionado;
    }

    /**
     * @param selecionado the selecionado to set
     */
    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }

    /**
     * @return the cnpj
     */
    public String getCnpj() {
        return cnpj;
    }

    /**
     * @param cnpj the cnpj to set
     */
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    /**
     * @return the febraban
     */
    public String getFebraban() {
        return febraban;
    }

    /**
     * @param febraban the febraban to set
     */
    public void setFebraban(String febraban) {
        this.febraban = febraban;
    }

    /**
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * @return the segmento
     */
    public String getSegmento() {
        return segmento;
    }

    /**
     * @param segmento the segmento to set
     */
    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }

    /**
     * @return the sigla
     */
    public String getSigla() {
        return sigla;
    }

    /**
     * @param sigla the sigla to set
     */
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    /**
     * @return the situacao
     */
    public String getSituacao() {
        return situacao;
    }

    /**
     * @param situacao the situacao to set
     */
    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
    /**
     * Retorna o CNPJ formatado.
     *
     * @return CNPJ formatado
     */
    public String getCnpjFormatado()
    {
        if (getCnpj().length() == 14)
        {
            return getCnpj().substring(0, 2) + "." + getCnpj().substring(2, 5) + "." + getCnpj().substring(5, 8) + "/" +
                getCnpj().substring(8, 12) + "-" + getCnpj().substring(12, 14);
        }
        return "";
    }
}