package br.edu.ifpi.entity;

import java.util.List;

import javax.persistence.*;

import javax.persistence.CascadeType;

@Entity
@Table(name="tipos_clientes")
public class TipoCliente {
	public TipoCliente() {}
	
	public TipoCliente(String descricao) {
		this.descricao = descricao;
	}
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToMany(mappedBy="tipoCliente", cascade = CascadeType.ALL)
	private List<Cliente> cliente;
	
	@Column(length=20)
	private String descricao;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
    
	public String toString() {
		return this.descricao;
	}

	public void setCliente(List<Cliente> cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getCliente() {
		return cliente;
	}
	
}