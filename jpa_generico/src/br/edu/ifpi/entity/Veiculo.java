package br.edu.ifpi.entity;

import javax.persistence.*;

@Entity
@Table(name="veiculos")
public class Veiculo {
	public Veiculo() {
		
	}
	
	public Veiculo(String placa) {
		this.placa = placa;
	}
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(length=7)
	private String placa;

	private int ano;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Cliente cliente;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getPlaca() {
		return placa;
	}
	
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public int getAno() {
		return ano;
	}
	
	public void setAno(int ano) {
		this.ano = ano;
	}
	
	public String toString() {
		return this.placa + " | Ano:" + this.ano;
	}
    
}