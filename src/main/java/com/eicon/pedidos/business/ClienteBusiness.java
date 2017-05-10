package com.eicon.pedidos.business;

import java.io.Serializable;
import java.util.ArrayList;

import javax.inject.Inject;

import com.eicon.pedidos.DAO.ClienteDAO;
import com.eicon.pedidos.model.Cliente;

public class ClienteBusiness implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ClienteDAO dao;
	
	public Cliente findClienteById(Long codigo){
		return dao.buscaPorCodigo(codigo);
	}

	public ArrayList<Cliente> listaTodos() {
		return dao.listaTodos();
	}
	
}
