
<h1>Como configurar</h1>

1 - Baixe o projeto no GIthub

2- Importe no Eclipse IDE

3- Adicione no servidor Tomcat 7 configurado com porta 8080

4- Crie o Banco mysql com nome pedidos  login: root senha:admin porta 3306 conforme configurado no persistence.xml

5 - Execute o script de criação das tabelas (presente no final deste arquivo)

Obs: War presente dentro do projeto target/EiconPedidos.war
<h1>Como utilizar</h1>

Segue uma lista de paths disponíveis com o método para sua utilização e um exemplo de request.

Lista todos os pedidos:
Método: 		GET
Path: 			http://localhost:8080/EiconPedidos/rest/listaPedidos

Lista pedidos com filtro:

Método: 		GET
Path: 			http://localhost:8080/EiconPedidos/rest/listaPedidos/codigoPedido/INSERIRCODIGODOPEDIDO/dataCadastro/DATANOFORMATO01-03-2017/codigoCliente/INSERIRCODIGODOCLIENTE

os parâmetros na url são opcionais, por exemplo:
http://localhost:8080/EiconPedidos/rest/listaPedidos/codigoPedido/123
ou
http://localhost:8080/EiconPedidos/rest/listaPedidos/dataCadastro/01-03-2017
e assim por diante

Método: 		POST
Path: 	http://localhost:8080/EiconPedidos/rest/pedido

O body deve ser no formato de um Json contendo uma lista de pedidos (maximo 10 pedidos):
Body :
  [
      {
        "numeroControle": 1,
        "valor": 10,
        "dataCadastroString": "10-10-2017",
        "quantidade": 10,
        "valorTotal": null,
        "cliente": {
	              "id": null,
	              "codigoCliente": 1,
	              "nome": "Jonas"
              }
       },
       {
        "numeroControle": 2,
        "valor": 20,
        "dataCadastroString": "05-04-2017",
        "quantidade": 10,
        "valorTotal": null,
        "cliente": {
	              "id": null,
	              "codigoCliente": 2,
	              "nome": "Jonas"
              }
       }
]

Há também uma interface feita em JSF e Primefaces onde é possivel cadastrar novos pedidos (Através de um Json no mesmo formato)
e exibir uma lista com filtro para os pedidos. A interface se comunica com os services exclusivamente através de JSON.

A url da interface é:

http://localhost:8080/EiconPedidos/faces/listaPedido.xhtml


*******************************SCRIPT DE CRIAÇÂO DAS TABELAS**********************
create database pedidos;
use pedidos;

create  table Cliente (
	id bigint not null auto_increment,
	nome varchar(255),
    codigoCliente bigint,
    primary key (id)
);

 create table Pedido (
        id bigint not null auto_increment,
        dataCadastro date,
        dataCadastroString varchar(255),
        numeroControle bigint,
        quantidade integer,
        valor double precision,
        valorTotal double precision,
        cliente_id bigint,
        primary key (id)
    );
alter table Pedido 
        add constraint FK1jq79bpskcvo2krkbee2qdqpr 
        foreign key (cliente_id) 
        references Cliente (id);
        
insert into Cliente (nome, codigoCliente) values ('Joao', 1);
insert into Cliente (nome, codigoCliente) values ('Maria', 2);
insert into Cliente (nome, codigoCliente) values ('Ana', 3);
insert into Cliente (nome, codigoCliente) values ('Suelen', 4);
insert into Cliente (nome, codigoCliente) values ('Silas', 5);
insert into Cliente (nome, codigoCliente) values ('Gervásio', 6);
insert into Cliente (nome, codigoCliente) values ('Jonas', 7);
insert into Cliente (nome, codigoCliente) values ('Rafael', 8);
insert into Cliente (nome, codigoCliente) values ('Ricardo', 9);
insert into Cliente (nome, codigoCliente) values ('Nadia', 10);

***************************************************************************

