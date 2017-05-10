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

Método: 		GET
Path: 	http://localhost:8080/EiconPedidos/rest/pedido

O body deve ser no formato de um Json contendo uma lista de pedidos:
Body :
  [
      {
        "numeroControle": 1,
        "valor": 10,
        "dataCadastroString": "28-10-2017",
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
        "dataCadastroString": "28-10-2017",
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
