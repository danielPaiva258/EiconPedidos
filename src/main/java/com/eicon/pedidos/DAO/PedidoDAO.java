package com.eicon.pedidos.DAO;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import com.eicon.pedidos.model.Cliente;
import com.eicon.pedidos.model.Pedido;

public class PedidoDAO implements Serializable {

	// public boolean existe(Usuario usuario) {
	//
	// EntityManager em = new JPAUtil().getEntityManager();
	// TypedQuery<Usuario> query = em.createQuery(
	// " select u from Usuario u "
	// + " where u.email = :pEmail and u.senha = :pSenha", Usuario.class);
	//
	// query.setParameter("pEmail", usuario.getEmail());
	// query.setParameter("pSenha", usuario.getSenha());
	// try {
	// Usuario resultado = query.getSingleResult();
	// } catch (NoResultException ex) {
	// return false;
	// }
	//
	// em.close();
	//
	// return true;
	// }

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void salva(ArrayList<Pedido> pedidos) {

		EntityManager em = new JPAUtil().getEntityManager();

		em.getTransaction().begin();
		for (Pedido pedido : pedidos) {
			em.persist(pedido);
		}
		em.getTransaction().commit();
		em.close();
	}
	
	public ArrayList<Pedido> listaTodos() {
		EntityManager em = new JPAUtil().getEntityManager();
		CriteriaQuery<Pedido> query = em.getCriteriaBuilder().createQuery(Pedido.class);
		query.select(query.from(Pedido.class));

		ArrayList<Pedido> lista = (ArrayList<Pedido>) em.createQuery(query).getResultList();

		em.close();
		return lista;
	}

	public ArrayList<Pedido> listaByFiltro(Pedido pedidoFilter) {
		EntityManager em = new JPAUtil().getEntityManager();
		ArrayList<Pedido> resultado ;
		
		String queryStr = " select p from Pedido p where ";
		
		if(pedidoFilter.getNumeroControle() != null){
			queryStr = queryStr + "p.numeroControle = :pNumeroControle ";
			if (pedidoFilter.getDataCadastro() != null|| pedidoFilter.getCliente() != null){
				queryStr = queryStr + "and ";
			}
		}
		
		if(pedidoFilter.getDataCadastro() != null){
			queryStr = queryStr + "p.dataCadastro = :pDataCadastro ";
			
			if (pedidoFilter.getCliente() != null){
				queryStr = queryStr + "and ";
			}			
		}
			
		if(pedidoFilter.getCliente() != null)
			queryStr = queryStr + "p.cliente.codigoCliente = :pCodigoCliente";
			
		TypedQuery<Pedido> query = em.createQuery(queryStr, Pedido.class);
		
		
		
		if(pedidoFilter.getNumeroControle() != null)
			query.setParameter("pNumeroControle", pedidoFilter.getNumeroControle());
		if(pedidoFilter.getCliente() != null)
			query.setParameter("pCodigoCliente", pedidoFilter.getCliente().getCodigoCliente());
		
		if(pedidoFilter.getDataCadastro() != null)
			query.setParameter("pDataCadastro", pedidoFilter.getDataCadastro());
		
		
		
		try {
			resultado =  (ArrayList<Pedido>) query.getResultList();
		} catch (NoResultException ex) {
			return null;
		}
		
		em.close();
		
		return resultado;
	}

}
