package com.eicon.pedidos.DAO;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import com.eicon.pedidos.model.Cliente;
import com.eicon.pedidos.model.Pedido;

public class ClienteDAO implements Serializable{
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
	
	public Cliente buscaPorCodigo(Long codigo) {
		EntityManager em = new JPAUtil().getEntityManager();
		Cliente resultado ;
		TypedQuery<Cliente> query = em.createQuery(
				  " select c from Cliente c "
				+ " where c.codigoCliente = :pCodigo", Cliente.class);
		
		query.setParameter("pCodigo", codigo);
		try {
			resultado =  query.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
		
		em.close();
		
		return resultado;
	}
	
	public ArrayList<Cliente> listaTodos() {
		EntityManager em = new JPAUtil().getEntityManager();
		CriteriaQuery<Cliente> query = em.getCriteriaBuilder().createQuery(Cliente.class);
		query.select(query.from(Cliente.class));

		ArrayList<Cliente> lista = (ArrayList<Cliente>) em.createQuery(query).getResultList();

		em.close();
		return lista;
	}

}
