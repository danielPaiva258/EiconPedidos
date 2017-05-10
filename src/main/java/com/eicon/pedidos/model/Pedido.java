package com.eicon.pedidos.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class Pedido {

	private Long numeroControle;
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;
	private String dataCadastroString;
	@NotNull
	@ManyToOne(targetEntity=Cliente.class)
	private Cliente cliente;
	private Double valor;
	private Integer quantidade;
	private Double valorTotal;
	
	public Pedido(){
		
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	public Pedido(Long numeroControle, Date dataCadastro, String nome, Double valor, Integer quantidade, 
			Long codigoCliente) {

		Cliente cliente = new Cliente();
		cliente.setCodigoCliente(codigoCliente);
		cliente.setNome(nome);
		this.cliente = cliente;
		this.numeroControle = numeroControle;
		this.dataCadastro = dataCadastro;
		this.valor = valor;
		this.quantidade = quantidade;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public Long getNumeroControle() {
		return numeroControle;
	}
	public void setNumeroControle(Long numeroControle) {
		this.numeroControle = numeroControle;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getDataCadastroString() {
		return dataCadastroString;
	}

	public void setDataCadastroString(String dataCadastroString) {
		this.dataCadastroString = dataCadastroString;
	}
}
