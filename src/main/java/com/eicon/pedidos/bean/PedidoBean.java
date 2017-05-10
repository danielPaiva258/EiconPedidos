package com.eicon.pedidos.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.eicon.pedidos.business.ClienteBusiness;
import com.eicon.pedidos.business.PedidoBusiness;
import com.eicon.pedidos.model.Cliente;
import com.eicon.pedidos.model.Pedido;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mysql.fabric.xmlrpc.base.Value;

@Named("pedidoBean")
@ApplicationScoped
public class PedidoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String teste = "teste";
	
	@Inject 
	private PedidoBusiness pedidoBusiness;
	
	@Inject
	private ClienteBusiness clienteBusiness;
	
	private ArrayList<Pedido> listaPedidos = new ArrayList<>();
	private ArrayList<Cliente> listaClientes = new ArrayList<>();
	private Long clienteFiltroId;
	private Long numPedidoFiltro;
	private Date dataCadastroFiltro;
	private String jsonPostPedidos;
	
	@PostConstruct
	public void init(){
		setListaPedidos(pedidoBusiness.listaTodos());
		setListaClientes(clienteBusiness.listaTodos());
	}
	
	public void filtrar () throws ParseException{
		Pedido pedido = new Pedido();
		
		Gson gson = new Gson();
		pedido.setCliente(new Cliente());
		if(numPedidoFiltro != null && numPedidoFiltro == 0)
			numPedidoFiltro = null;
		pedido.setNumeroControle(this.numPedidoFiltro);
		if (clienteFiltroId != null && clienteFiltroId == 0){
			clienteFiltroId = null;
			pedido.setCliente(null);
		}
		if(pedido.getCliente() != null && this.clienteFiltroId == null){
			pedido.setCliente(null);
		}else if (pedido.getCliente() != null){
			pedido.getCliente().setCodigoCliente(this.clienteFiltroId);
		}
		
		if ( this.dataCadastroFiltro != null ){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dataCadastroFiltro);
			Integer day  = calendar.get(Calendar.DAY_OF_MONTH);
			String dayStr = day.toString();
			if( day <10)
				dayStr = "0"+day;
			
			Integer month = calendar.get(Calendar.MONTH)+1;
			String monthStr = month.toString();
			if (month <10)
				monthStr = "0"+month;
			
			String strData = dayStr + "/" + monthStr + "/"+calendar.get(Calendar.YEAR);
			pedido.setDataCadastroString(strData);
		}
		
		String pedidoJson = gson.toJson(pedido);
		ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/EiconPedidos/rest/listaPedidos/jsf");
        Response response = target.request().post(Entity.entity(pedidoJson, MediaType.APPLICATION_JSON));
        String value = response.readEntity(String.class);
        
        
        listaPedidos = gson.fromJson(value, new TypeToken<List<Pedido>>(){}.getType());
        response.close();
        
		 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Lista Atualizada."));
	}
	
	public void salvarPedidos () throws ParseException{
		ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/EiconPedidos/rest/pedido");
        Gson gson = new Gson();
        gson.toJson(jsonPostPedidos);
        Response response = target.request().post(Entity.entity(jsonPostPedidos, MediaType.APPLICATION_JSON));
        if(response.getStatus() != 200){
        	 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", response.readEntity(String.class)));
        }else{
        	 this.filtrar();
        	 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro(s) salvo(s) com sucesso."));
        }
        response.close();
	}
	
	public String getTeste() {
		return teste;
	}
	public void setTeste(String teste) {
		this.teste = teste;
	}
	public ArrayList<Pedido> getListaPedidos() {
		return listaPedidos;
	}
	public void setListaPedidos(ArrayList<Pedido> listaPedidos) {
		this.listaPedidos = listaPedidos;
	}
	public ArrayList<Cliente> getListaClientes() {
		return listaClientes;
	}
	public void setListaClientes(ArrayList<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}
	public Long getNumPedidoFiltro() {
		return numPedidoFiltro;
	}
	public void setNumPedidoFiltro(Long numPedidoFiltro) {
		this.numPedidoFiltro = numPedidoFiltro;
	}
	public Date getDataCadastroFiltro() {
		return dataCadastroFiltro;
	}
	public void setDataCadastroFiltro(Date dataCadastroFiltro) {
		this.dataCadastroFiltro = dataCadastroFiltro;
	}

	public Long getClienteFiltroId() {
		return clienteFiltroId;
	}

	public void setClienteFiltroId(Long clienteFiltroId) {
		this.clienteFiltroId = clienteFiltroId;
	}

	public String getJsonPostPedidos() {
		return jsonPostPedidos;
	}

	public void setJsonPostPedidos(String jsonPostPedidos) {
		this.jsonPostPedidos = jsonPostPedidos;
	}
}
