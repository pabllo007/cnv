package br.com.sicoob.cnv.gestao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.bancoob.negocio.entidades.BancoobEntidade;

/**
 * Situação de um contrato.
 *
 * @author Bruno Santos
 */
@Entity
@Table(name = "TIPOSITUACAOCONVENIO", schema = "CNV")
public class SituacaoConvenio extends BancoobEntidade
{
    @Column(name = "CODTIPOSITUACAOCONVENIO")
	@Id
	private Long id;
	@Column(name = "DESCTIPOSITUACAOCONVENIO") private String nome;
	public final static SituacaoConvenio ATIVO = new SituacaoConvenio(0l), INATIVO = new SituacaoConvenio(1l),
		NAO_IMPLANTADO = new SituacaoConvenio(2l);
    private final static long serialVersionUID = 7629588620346933339L;

	/**
	 * Instancia uma situação.
	 */
	public SituacaoConvenio() { }
	/**
	 * Instancia uma situação com o ID.
	 *
	 * @param id ID da situação
	 */
	public SituacaoConvenio(Long id)
	{
		setId(id);
	}

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
}