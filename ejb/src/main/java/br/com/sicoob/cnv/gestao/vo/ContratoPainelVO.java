package br.com.sicoob.cnv.gestao.vo;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * VO para o painel de renovação de contratos.
 *
 * @author Bruno Santos
 */
@Entity
@SqlResultSetMapping
(
    entities = @EntityResult(entityClass = ContratoPainelVO.class),
    name = "ContratoPainelVO"
)
public class ContratoPainelVO implements Serializable
{
    @Id
    private long id;
	@Temporal(TemporalType.DATE)
    private Date vigencia;
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
    private final static long serialVersionUID = -3079380526291350114L;
    private Boolean acao;
    private int instituicao, pessoa, prazo;
    private String cnpj, numero, sigla, situacao;

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
     * @return the vigencia
     */
    public Date getVigencia() {
        return vigencia;
    }

    /**
     * @param vigencia the vigencia to set
     */
    public void setVigencia(Date vigencia) {
        this.vigencia = vigencia;
    }

    /**
     * @return the acao
     */
    public Boolean getAcao() {
        return acao;
    }

    /**
     * @param acao the acao to set
     */
    public void setAcao(Boolean acao) {
        this.acao = acao;
    }

    /**
     * @return the instituicao
     */
    public int getInstituicao() {
        return instituicao;
    }

    /**
     * @param instituicao the instituicao to set
     */
    public void setInstituicao(int instituicao) {
        this.instituicao = instituicao;
    }

    /**
     * @return the pessoa
     */
    public int getPessoa() {
        return pessoa;
    }

    /**
     * @param pessoa the pessoa to set
     */
    public void setPessoa(int pessoa) {
        this.pessoa = pessoa;
    }

    /**
     * @return the prazo
     */
    public int getPrazo() {
        return prazo;
    }

    /**
     * @param prazo the prazo to set
     */
    public void setPrazo(int prazo) {
        this.prazo = prazo;
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
     * @return data formatada
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
    /**
     * Retorna a data em formato brasileiro encurtado.
     *
     * @return data formatada
     */
    public String getVigenciaFormatada()
    {
        if (getVigencia() instanceof java.sql.Date)
        {
            return ((java.sql.Date) getVigencia()).toLocalDate().format(FORMATTER);
        }
        return getVigencia().toInstant().atZone(ZoneId.systemDefault()).format(FORMATTER);
    }
}