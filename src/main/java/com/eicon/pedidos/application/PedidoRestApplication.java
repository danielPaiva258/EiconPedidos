package com.eicon.pedidos.application;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.eicon.pedidos.service.ServicePedidos;

public class PedidoRestApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> classes = new HashSet<>();
    
	public PedidoRestApplication() {
		classes.add(ServicePedidos.class);
    }
    
	@Override
    public Set<Object> getSingletons() {
        return singletons;
    }

	@Override
	public Set<Class<?>> getClasses() {
		return classes;
	}
}
