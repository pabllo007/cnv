package br.com.sicoob.cnv.gestao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.bancoob.negocio.entidades.BancoobEntidade;

/**
 * Segmento de um convênio.
 *
 * @author Bruno Santos
 */
@Entity
@Table(name = "TIPOSEGMENTOCONCESSIONARIA", schema = "CNV")
public class Segmento extends BancoobEntidade
{
    @Column(name = "CODTIPOSEGMENTOCONCESSIONARIA")
	@Id
	private Integer id;
	@Column(name = "DESCTIPOSEGMENTOCONCESSIONARIA")
	private String nome;
	private final static long serialVersionUID = -6614708722030861229L;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
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