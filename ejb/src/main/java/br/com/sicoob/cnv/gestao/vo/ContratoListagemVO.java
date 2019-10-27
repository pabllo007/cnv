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
 * VO para listagem de contratos.
 *
 * @author Bruno Santos
 */
@Entity
@SqlResultSetMapping
(
    entities = @EntityResult(entityClass = ContratoListagemVO.class),
    name = "ContratoListagemVO"
)
public class ContratoListagemVO implements Serializable
{
    @Id
    private long id;
	@Temporal(TemporalType.DATE)
    private Date assinatura;
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
    private final static long serialVersionUID = 2691935101048974304L;
    private int instituicao, pessoa;
    private String cnpj, fantasia, razaoSocial, numero, sigla, situacao;

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
     * @return the assinatura
     */
    public Date getAssinatura() {
        return assinatura;
    }

    /**
     * @param assinatura the assinatura to set
     */
    public void setAssinatura(Date assinatura) {
        this.assinatura = assinatura;
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
     * @return the fantasia
     */
    public String getFantasia() {
        return fantasia;
    }

    /**
     * @param fantasia the fantasia to set
     */
    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

    /**
     * @return the razaoSocial
     */
    public String getRazaoSocial() {
        return razaoSocial;
    }

    /**
     * @param razaoSocial the razaoSocial to set
     */
    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
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
     * Retorna a data em formato brasileiro encurtado.
     *
     * @return data formatada
     */
    public String getAssinaturaFormatada()
    {
        if (getAssinatura() instanceof java.sql.Date)
        {
            return ((java.sql.Date) getAssinatura()).toLocalDate().format(FORMATTER);
        }
        return getAssinatura().toInstant().atZone(ZoneId.systemDefault()).format(FORMATTER);
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
}