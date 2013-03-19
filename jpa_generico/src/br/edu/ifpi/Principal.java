package br.edu.ifpi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import br.edu.ifpi.dao.*;
import br.edu.ifpi.entity.*;

public class Principal {
	private static GenericDAO dao = new GenericJPADAO();

	public static void main(String[] args) {
		String menu = new StringBuilder("Escolha uma opção:\n")
			.append("1 - Inserir Cliente\n")
			.append("2 - Atualizar Cliente por CPF\n")
			.append("3 - Remover Cliente por CPF\n")
			.append("4 - Exibir Cliente por CPF\n")
			.append("5 - Exibir Cliente por id\n")
			.append("6 - Exibir todos os clientes com veículos\n")
			.append("7 - Exibir todos os clientes que contem determinado nome\n")
			.append("8 - Inserir Tipo de Cliente\n")
			.append("9 - Remover Tipo de Cliente\n")
			.append("a - Sair").toString();
		String opcao;
		do {
			Cliente cl;
			String cpf;
			
			opcao = JOptionPane.showInputDialog(menu);
			if (opcao == null) {
				return;
			}
			switch (opcao.charAt(0)) {
			case '1':     // Inserir
				cl = alteraCliente(new Cliente());
				if (cl != null) {
					try {
						dao.save(cl);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Problema ao realizar operação.\n" + e.getMessage());
					}
				}
				break;
			case '2':     // Atualizar por CPF
				cpf = JOptionPane.showInputDialog("Digite o CPF do cliente a ser alterado");
				Map params = new HashMap();
				params.put("cpf", cpf);
				List result = dao.find("findByCpf", params);
				cl = result != null && result.size() > 0 ? (Cliente) result.get(0) : null;
				cl = alteraCliente(cl);
				if (cl != null) {
					dao.save(cl);
				}
				break;
			case '3':     // Remover por CPF
				cpf = JOptionPane.showInputDialog("CPF");
				params = new HashMap();
				params.put("cpf", cpf);
				result = dao.find("findByCpf", params);
				cl = result != null && result.size() > 0 ? (Cliente) result.get(0) : null;
				if (cl != null) {
					dao.delete(cl);
				} else {
					JOptionPane.showMessageDialog(null, "Não foi possível remover, pois o cliente não encontrado.");
				}
				break;
			case '4':     // Exibir por CPF
				cpf = JOptionPane.showInputDialog("CPF");
				params = new HashMap();
				params.put("cpf", cpf);
				result = dao.find("findByCpf", params);
				cl = result != null && result.size() > 0 ? (Cliente) result.get(0) : null;
				listaCliente(cl);
				break;
			case '5':     // Exibir por id
				int id = Integer.parseInt(JOptionPane.showInputDialog("Id"));
				cl = (Cliente) dao.find(Cliente.class, id);
				listaCliente(cl);
				break;
			case '6':     // Exibir todos
				listaClientes(dao.find(Cliente.class));
				break;
			case '7':     // Exibir todos que contem determinado nome
				String nome = JOptionPane.showInputDialog("Nome");
				params = new HashMap();
				params.put("nome", "%" + nome.toUpperCase() + "%");
				List<Cliente> clientes = dao.find("findByNome", params);
				listaClientes(clientes);
				break;
			case '8':     // Inserir tipo de cliente
				String descricao = JOptionPane.showInputDialog("Tipo de cliente");
				TipoCliente tp = new TipoCliente(descricao);
				dao.save(tp);
				break;
			case '9':     // Remover tipo de cliente
				List<TipoCliente> tipos_clientes = dao.find(TipoCliente.class);
				StringBuilder str = new StringBuilder("Digite o índice do tipo de cliente a ser removido:\n");
				for (int i=0; i < tipos_clientes.size(); i++) {
					str.append(i).append(" - ")
						.append(tipos_clientes.get(i)).append("\n");
				}
				String escolha = JOptionPane.showInputDialog(str);
				if (escolha != null) {
					try {
						dao.delete(tipos_clientes.get(Integer.parseInt(escolha)));
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Problema ao executar operação.\nPode ser que exista algum cliente com este tipo de cliente");
					}
				}
				break;
			case 'a':     // Sair
				break;
			default:
				JOptionPane.showMessageDialog(null, "Opção Inválida");
				break;
			}
			
		} while(! opcao.equals("a"));
	}

	public static void listaClientes(List<Cliente> clientes) {
		String listagem = "";
		
		for (Cliente cl : clientes) {
			for (Veiculo v : cl.getVeiculos()){
				listagem = listagem +cl + v + "\n";
			}
		}
		JOptionPane.showMessageDialog(null, listagem.length() == 0 ? "Nenhum cliente encontrado" : listagem);
	}

	public static void listaCliente(Cliente cl) {
		JOptionPane.showMessageDialog(null, cl == null ? "Nenhum cliente encontrado" : cl);
	}
	
	public static Cliente alteraCliente(Cliente cl) {
		if (cl == null) {
			JOptionPane.showMessageDialog(null, "Cliente não encontrado.");
			return null;
		}
		String opcao;
		do {
			StringBuilder menu = new StringBuilder();
			menu.append("Cliente: ").append(cl);
			menu.append("\nDigite o número do campo que deseja preencher ou modificar:\n")
				.append("1-CPF\n2-Nome\n")
				.append("3-Fone\n4-Renda\n")
				.append("5-Tipo Cliente\n6-Inserir Veículo\n")
				.append("7-Apagar Veículo\n")
				.append("8-Sair e Salvar");
			opcao = JOptionPane.showInputDialog(menu);
			if (opcao == null) {
				return null;
			}
			switch (opcao.charAt(0)) {
			case '1':
				String cpf = JOptionPane.showInputDialog("CPF", cl.getCpf());
				cl.setCpf(cpf);
				break;
			case '2':
				String nome = JOptionPane.showInputDialog("Nome", cl.getNome());
				cl.setNome(nome);
				break;
			case '3':
				String fone = JOptionPane.showInputDialog("Fone", cl.getFone());
				cl.setFone(fone);
				break;
			case '4':
				double renda = Double.parseDouble(JOptionPane.showInputDialog("Renda", cl.getRenda()));
				cl.setRenda(renda);
				break;
			case '5':
				if (cl.getTipoCliente() == null) {
					cl.setTipoCliente(new TipoCliente());
				}
				TipoCliente tc = new TipoCliente();
				tc.setDescricao(JOptionPane.showInputDialog("Descrição"));
			
				// Como a associação é bidirecional, precisamos setar os
				// dois lados.
				cl.setTipoCliente(tc);
				break;
			case '6':
				if (cl.getVeiculos() == null) {
					cl.setVeiculos(new ArrayList<Veiculo>());
				}
				Veiculo v = new Veiculo();
				v.setPlaca(JOptionPane.showInputDialog("Placa"));
				v.setAno(Integer.parseInt(JOptionPane.showInputDialog("Ano")));
				// Como a associação é bidirecional, precisamos setar os
				// dois lados.
				v.setCliente(cl);
				cl.getVeiculos().add(v);
				break;
			case '7':
				StringBuilder msg = new StringBuilder("Digite o número do veículo a ser removido:");
				for (int i=0; i < cl.getVeiculos().size(); i++) {
					v = cl.getVeiculos().get(i);
					msg.append("\n").append(i).append(" - ").append(v);
				}
				String s = JOptionPane.showInputDialog(msg);
				if (s != null) {
					// Apaga o veículo da lista e também da base de dados
					dao.delete(cl.getVeiculos().remove(Integer.parseInt(s)));
				}
				break;
			case '8':
				break;
			default:
				JOptionPane.showMessageDialog(null, "Opção Inválida");
				break;
			}
		} while (! opcao.equals("8"));
		return cl;
	}
	
	public static void alteraTipoCliente(Cliente c) {
		Object[] options = dao.find(TipoCliente.class).toArray();
		if (options != null && options.length > 0) {
			c.setTipoCliente((TipoCliente)JOptionPane.showInputDialog(null, "Tipo Cliente", "Tipo Cliente", JOptionPane.PLAIN_MESSAGE,null,options,c.getTipoCliente()));
		}
	}
	
}
