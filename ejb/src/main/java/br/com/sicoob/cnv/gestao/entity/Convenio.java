package br.com.sicoob.cnv.gestao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.bancoob.negocio.entidades.BancoobEntidade;

/**
 * Convênio bancário.
 *
 * @author Bruno Santos
 */
@Entity
@Table(name = "CONVENIO", schema = "CNV")
public class Convenio extends BancoobEntidade
{
	@Column(name = "CODCONVENIO") private String numero;
	@Column(name = "IDCONVENIO")
	@Id
	private long id;
	@Column(name = "NUMCNPJCONVENIO") private String cnpj;
	@Column(name = "NUMCONVENIOFEBRABAN") private String febraban;
	@Column(name = "SIGLACONVENIO") private String sigla;
	@JoinColumn(name = "CODTIPOSEGMENTO")
	@ManyToOne
	private Segmento segmento;
	@JoinColumn(name = "CODTIPOSITUACAOCONVENIO")
	@ManyToOne
	private SituacaoConvenio situacao;
	@Transient private boolean selecionado;
	private final static long serialVersionUID = -1938019948145494989L;

	/**
	 * Instancia o convênio.
	 */
	public Convenio()
	{
		setSelecionado(true);
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
	 * @return the segmento
	 */
	public Segmento getSegmento() {
		return segmento;
	}

	/**
	 * @param segmento the segmento to set
	 */
	public void setSegmento(Segmento segmento) {
		this.segmento = segmento;
	}

	/**
	 * @return the situacao
	 */
	public SituacaoConvenio getSituacao() {
		return situacao;
	}

	/**
	 * @param situacao the situacao to set
	 */
	public void setSituacao(SituacaoConvenio situacao) {
		this.situacao = situacao;
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