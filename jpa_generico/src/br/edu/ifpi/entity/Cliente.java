package br.edu.ifpi.entity;

import java.util.List;
import javax.persistence.*;

import br.edu.ifpi.entity.Veiculo;

@Entity
@Table(name="clientes")
@NamedQueries({
	@NamedQuery(
		name="findByCpf",
		query="select c from Cliente c where c.cpf = :cpf"),
	@NamedQuery(
		name="findByNome",
		query="select c from Cliente c where upper(c.nome) like :nome")
})
public class Cliente {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(length=11)
	private String cpf;
	
	@Column(length=50)
	private String nome;

	@Column(length=10)
	private String fone;
	
	private double renda;

	@OneToMany(mappedBy="cliente", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Veiculo> veiculos;

	@ManyToOne(cascade=CascadeType.ALL)
	private TipoCliente tipoCliente;
	
	public Cliente() {}
	
	public Cliente(String cpf, String nome, String fone, double renda) {
		this.cpf = cpf;
		this.nome = nome;
		this.fone = fone;
		this.renda = renda;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public double getRenda() {
		return renda;
	}

	public void setRenda(double renda) {
		this.renda = renda;
	}

	public List<Veiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	public TipoCliente getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(TipoCliente tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public String toString() {
		String strVeiculos = "";
		//if (this.veiculos != null) {
		//	for (Veiculo v : this.veiculos) {
		//		strVeiculos = strVeiculos + "\n     " + v;
		//	}
		//}
		return "Id: " + this.id + " | CPF: " + this.cpf + " | Nome:" + this.nome 
			+ " | Fone:" + this.fone + " | Renda:" + this.renda 
			+ (this.tipoCliente == null ? "" :" | Descrição:" + this.tipoCliente.getDescricao() + " | Placa: ") 
			+ strVeiculos;
	}

}
