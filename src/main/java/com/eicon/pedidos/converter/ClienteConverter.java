package com.eicon.pedidos.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

import com.eicon.pedidos.business.ClienteBusiness;
import com.eicon.pedidos.model.Cliente;

@Named("converter.clienteConverter")
public class ClienteConverter implements Converter{
	
	@Inject
	private ClienteBusiness clienteBusiness;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

	    if (value == null || value.isEmpty()) {return null;}
	    try {             
	         return clienteBusiness.findClienteById(Long.parseLong(value));// here's where should be retreived the desired selected instance
	    } catch (NumberFormatException e) {
	        throw new ConverterException(new FacesMessage(("Cliente inválido")), e);
	    }         
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null)
			return null;
		Cliente cliente  =  (Cliente) value;
		if (cliente.getCodigoCliente() != null)
			return String.valueOf(cliente.getCodigoCliente());
		else
			return null;
	}
}
