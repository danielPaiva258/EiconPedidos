package com.eicon.pedidos.business;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import com.eicon.pedidos.DAO.ClienteDAO;
import com.eicon.pedidos.DAO.PedidoDAO;
import com.eicon.pedidos.model.Cliente;
import com.eicon.pedidos.model.Pedido;

public class PedidoBusiness implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoDAO dao;
	@Inject
	private ClienteDAO clienteDAO;

	public void efetuaPedido(ArrayList<Pedido> pedidos) throws ParseException {
		for (Pedido pedido : pedidos) {
			if (pedido.getDataCadastroString() == null) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				pedido.setDataCadastro(sdf.parse(new Date().toString()));
			}else{
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				pedido.setDataCadastro(sdf.parse(pedido.getDataCadastroString()));
			}

			if (pedido.getQuantidade() == null) {
				pedido.setQuantidade(1);
			}

			if (pedido.getQuantidade() > 5 && pedido.getQuantidade() < 10) {
				pedido.setValorTotal(pedido.getValor() * pedido.getQuantidade()
						- (((pedido.getValor() * pedido.getQuantidade()) / 100) * 5));
			}

			if (pedido.getQuantidade() >= 10) {
				pedido.setValorTotal(pedido.getValor() * pedido.getQuantidade()
						- (((pedido.getValor() * pedido.getQuantidade()) / 100) * 10));
			}

			pedido.setCliente(clienteDAO.buscaPorCodigo(pedido.getCliente().getCodigoCliente()));
		}

		dao.salva(pedidos);
	}

	public ArrayList<Pedido> listaPedidos(Pedido pedidoFilter) {
		if (pedidoFilter.getCliente() == null && pedidoFilter.getNumeroControle() == null && pedidoFilter.getDataCadastro() == null)
			return dao.listaTodos();
		else
			return dao.listaByFiltro(pedidoFilter);
	}

	public Cliente existeTodosClientes(ArrayList<Pedido> pedidos) {

		for (Pedido pedido : pedidos) {
			Cliente cliente = clienteDAO.buscaPorCodigo(pedido.getCliente().getCodigoCliente());
			if (cliente == null)
				return pedido.getCliente();
		}
		return null;
	}

	public ArrayList<Pedido> listaTodos() {
		return dao.listaTodos();
	}

	public Boolean existeNumeroControle(ArrayList<Pedido> pedidos) {
		for (Pedido pedido : pedidos) {
			Pedido pedido2 = new Pedido();
			pedido2.setNumeroControle(pedido.getNumeroControle());
			ArrayList<Pedido> pedidoList = dao.listaByFiltro(pedido2);
			if (pedidoList != null && !pedidoList.isEmpty() )
				return true;
		}
		return false;
	}

}
