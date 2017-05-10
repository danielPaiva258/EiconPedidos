package com.eicon.pedidos.service;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.jboss.resteasy.annotations.providers.jaxb.Formatted;

import com.eicon.pedidos.business.PedidoBusiness;
import com.eicon.pedidos.model.Cliente;
import com.eicon.pedidos.model.Pedido;

@Path("/")
public class ServicePedidos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PedidoBusiness pedidoBus;

	@Path("/pedido")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response realizaPedido(ArrayList<Pedido> pedidos) throws ParseException {

		if (pedidos == null || pedidos.size() == 0) {
			return Response.status(403).entity("Numero mínimo de pedidos : 1").build();
		}

		if (pedidos.size() > 10) {
			return Response.status(403).entity("Número de pedidos máximo por vez excedido : 10.").build();
		}

//		PedidoBusiness pedidoBus = new PedidoBusiness();

		Cliente cliente = pedidoBus.existeTodosClientes(pedidos);
		if (pedidoBus.existeNumeroControle(pedidos)){
			return Response.status(403).entity("Número de controle ja existe.").build();
		}
		if (cliente != null) {
			return Response.status(403)
					.entity("Cliente : " + cliente.getNome() + " " + cliente.getCodigoCliente() + " não encontrado.")
					.build();
		}
		pedidoBus.efetuaPedido(pedidos);

		pedidos = new ArrayList<>();
		return Response.status(200).entity("Pedidos realizados com sucesso.").build();
	}
	
	

	@Path("/listaPedidos{codigoPedido:(/codigoPedido/[^/]+?)?}{dataCadastro:(/dataCadastro/[^/]+?)?}{codigoCliente:(/codigoCliente/[^/]+?)?}")
	@GET
	@Formatted
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Pedido> pedidos(@PathParam("codigoPedido") String codigoPedido,
			@PathParam("dataCadastro") String dataCadastro, @PathParam("codigoCliente") String codigoCliente)
			throws JsonGenerationException, JsonMappingException, IOException {
		ArrayList<Pedido> pedidos = new ArrayList<Pedido>();

		Pedido pedidoFilter = new Pedido();
		if (!codigoCliente.isEmpty()){
			codigoCliente = codigoCliente.substring(15, codigoCliente.length());
			pedidoFilter.setCliente(new Cliente());
			pedidoFilter.getCliente().setCodigoCliente(Long.parseLong(codigoCliente));
		}else{
			codigoCliente = null;
		}
		
		if (!dataCadastro.isEmpty()){
			dataCadastro = dataCadastro.substring(14, dataCadastro.length());
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, Integer.parseInt(dataCadastro.substring(6,dataCadastro.length())));
			calendar.set(Calendar.MONTH,Integer.parseInt(dataCadastro.substring(3,5))-1);
			calendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(dataCadastro.substring(0,2)));
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			Date dataCadastroPersed = calendar.getTime();
			pedidoFilter.setDataCadastro(dataCadastroPersed);
		}
		
		if(!codigoPedido.isEmpty()){
			codigoPedido = codigoPedido.substring(14, codigoPedido.length());
			pedidoFilter.setNumeroControle(Long.parseLong(codigoPedido));
		}
		
		// Pedido pedido1 = new Pedido(1L,new Date(),"Daniel1", 150D, 2, 123L);
		// pedidos.add(pedido1);
//		PedidoBusiness pedidoBus = new PedidoBusiness();
		pedidos = pedidoBus.listaPedidos(pedidoFilter);
		return pedidos;
	}
	
	@Path("/listaPedidos/jsf")
	@POST
	@Formatted
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<Pedido> pedidos(Pedido pedido)
			throws JsonGenerationException, JsonMappingException, IOException, ParseException {
		ArrayList<Pedido> pedidos = new ArrayList<Pedido>();

		if(pedido.getDataCadastroString() != null){
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			pedido.setDataCadastro(sdf.parse(pedido.getDataCadastroString()));
		}
		pedidos = pedidoBus.listaPedidos(pedido);
		return pedidos;
	}

	@Path("/testePostJson")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response testePostJson(String teste) {
		return Response.status(200).entity(teste).build();
	}
}
