# Desafio Java - Reserva de Hoteis

## Tecnologias

* **Java 17**
* **Spring Boot 3**
* **API REST**
* **PostgreSQL (Container)**
* **Docker**
* **docker-compose**

## Arquitetura Proposta

Pensando em escalabilade, o sistema foi desenvolvido a partir de uma arquitetura baseada em microsserviços, onde cada serviço possui seu próprio banco dados relacional.

O sistema é composto por dois serviços:

* **hotel_api**: responsável por gerenciar os dados de cada hotel, seus quartos e avaliações.
* **reserva_api**: responsável pelo gerenciamento de reservas, seus respectivos hóspedes e envio de emails de notificação.

Toda a arquitetura foi implementada em containers docker via docker-compose.

* Uma reserva precisa ser relacionada à um hospede e um quarto.
* Cada quarto precisa ser relacionado à um hotel.
* Cada hotel pode ter varias avaliações, que podem se ligar à um hospede ou ser anônima.

Para garantir a persistência e forte relacionamento entre os dados, é utilizado um banco de dados relacional PostgreSQL.

### Fluxo de gerenciamento do hotel
* 01 - O primeiro passo é criar os hoteis, informando o nome do hotel, país, cidade, quantidade de estrelas e quantidade de quartos.
* 02 - Depois é necessário cadastrar os quartos de cada hotel, informando o id do hotel relacionado, número, capacidade de hóspedes, valor da diária e disponibilidade.

### Fluxo de execução de uma reserva
* 01 - O hóspede consulta os quartos disponíveis por hotel, podendo realizar a busca por hotel, país, cidade ou quantidade de estrelas.
* 02 - O hóspede se cadastra informando seu nome e email.
* 03 - Para criar a reserva, o hóspede seleciona o quarto, método de pagamento (pix, cartão de crédito ou boleto), datas de check-in e check-out.
* 04 - O valor da diária será calculado de acordo com o período de estadia.
* 05 - Ao confirmar a reserva, um email é enviado ao endereço cadastrado, o hóspede não conseguirá realizar a reserva caso o quarto já esteja ocupado no período informado.
* 06 - O hóspede pode avaliar o hotel, de forma anônima ou informando seu nome.

# Documentação dos endpoints

# hotel_api

## Base URL: http://localhost:8080

### **POST** - **http://localhost:8080/api/hotel** - Cadastra um novo hotel

Body:

```json
{
	"nome":"nt-inn",
	"pais":"Brasil",
	"cidade":"Porto Alegre",
	"qtdEstrelas":"5",
	"qtdQuartos":"30"
}
```

### **GET** - **http://localhost:8080/api/hotel** - Consulta todos os hoteis

Resposta:

```json
[
	{
		"id": 1,
		"nome": "nt-inn",
		"pais": "Brasil",
		"cidade": "Porto Alegre",
		"qtdEstrelas": 5,
		"qtdQuartos": 30
	},
	{
		"id": 2,
		"nome": "nt-plaza",
		"pais": "Estados Unidos",
		"cidade": "Miami",
		"qtdEstrelas": 4,
		"qtdQuartos": 25
	},
	{
		"id": 3,
		"nome": "nt resort",
		"pais": "Brasil",
		"cidade": "São Paulo",
		"qtdEstrelas": 3,
		"qtdQuartos": 10
	}
]
```

### **GET** - **http://localhost:8080/api/hotel/1** - Consulta hotel por um ID

Resposta:
```json
{
	"id": 1,
	"nome": "nt-inn",
	"pais": "Brasil",
	"cidade": "Porto Alegre",
	"qtdEstrelas": 5,
	"qtdQuartos": 30
}
```

### **GET** - **http://localhost:8080/api/hotel/nome_hotel?nome=pla** - Busca hoteis pelo nome

A consulta será feita como um LIKE '%nome_hotel%', buscando o texto informado em qualquer posição da string e ignorando caracteres maíusculos ou minúsculos.

Resposta:
```json
[
	{
		"id": 2,
		"nome": "nt-plaza",
		"pais": "Estados Unidos",
		"cidade": "Miami",
		"qtdEstrelas": 4,
		"qtdQuartos": 25
	}
]
```

### **GET** - **http://localhost:8080/api/hotel/pais_destino?pais=bra** - Busca hoteis pelo país de destino

A consulta será feita como um LIKE '%pais%', buscando o texto informado em qualquer posição da string e ignorando caracteres maíusculos ou minúsculos.

Resposta:
```json
[
	{
		"id": 1,
		"nome": "nt-inn",
		"pais": "Brasil",
		"cidade": "Porto Alegre",
		"qtdEstrelas": 5,
		"qtdQuartos": 30
	},
	{
		"id": 3,
		"nome": "nt resort",
		"pais": "Brasil",
		"cidade": "São Paulo",
		"qtdEstrelas": 3,
		"qtdQuartos": 10
	}
]
```

### **GET** - **http://localhost:8080/api/hotel/cidade_destino?cidade=miami** - Busca hoteis pela cidade de destino

A consulta será feita como um LIKE '%cidade%', buscando o texto informado em qualquer posição da string e ignorando caracteres maíusculos ou minúsculos.

Resposta:
```json
[
	{
		"id": 2,
		"nome": "nt-plaza",
		"pais": "Estados Unidos",
		"cidade": "Miami",
		"qtdEstrelas": 4,
		"qtdQuartos": 25
	}
]
```

### **GET** - **http://localhost:8080/api/hotel/qtd_estrelas?qtdEstrelas=5** - Busca hoteis pela quantidade de estrelas

Resposta:
```json
[
	{
		"id": 1,
		"nome": "nt-inn",
		"pais": "Brasil",
		"cidade": "Porto Alegre",
		"qtdEstrelas": 5,
		"qtdQuartos": 30
	}
]
```

### **POST** - **http://localhost:8080/api/quarto** - Cadastra um novo quarto

Body:

```json
{
	"id_hotel":1,
	"numero":11,
	"capacidadeHospedes":3,
	"vlrDiaria":250.80,
	"disponivel":true
}
```

Resposta:

```json
{
	"id": 1,
	"hotel": {
		"id": 1,
		"nome": "nt-inn",
		"pais": "Brasil",
		"cidade": "Porto Alegre",
		"qtdEstrelas": 5,
		"qtdQuartos": 30
	},
	"numero": 11,
	"capacidadeHospedes": 3,
	"vlrDiaria": 250.8,
	"disponivel": true
}
```

### **GET** - **http://localhost:8080/api/quarto** - Retorna todos os quartos

Resposta:

```json
[
	{
		"id": 1,
		"hotel": {
			"id": 1,
			"nome": "nt-inn",
			"pais": "Brasil",
			"cidade": "Porto Alegre",
			"qtdEstrelas": 5,
			"qtdQuartos": 30
		},
		"numero": 11,
		"capacidadeHospedes": 3,
		"vlrDiaria": 250.8,
		"disponivel": true
	},
	{
		"id": 2,
		"hotel": {
			"id": 2,
			"nome": "nt-plaza",
			"pais": "Estados Unidos",
			"cidade": "Miami",
			"qtdEstrelas": 4,
			"qtdQuartos": 25
		},
		"numero": 5,
		"capacidadeHospedes": 2,
		"vlrDiaria": 187.0,
		"disponivel": true
	},
]    
```

### **GET** - **http://localhost:8080/api/quarto/2** - Busca um quarto por ID
Resposta:

```json
{
	"id": 2,
	"hotel": {
		"id": 2,
		"nome": "nt-plaza",
		"pais": "Estados Unidos",
		"cidade": "Miami",
		"qtdEstrelas": 4,
		"qtdQuartos": 25
	},
	"numero": 5,
	"capacidadeHospedes": 2,
	"vlrDiaria": 187.0,
	"disponivel": true
}
```

### **GET** - **http://localhost:8080/api/quarto/hotel/1** - Busca quartos por um hotel específico

Resposta:
```json
[
	{
		"id": 1,
		"hotel": {
			"id": 1,
			"nome": "nt-inn",
			"pais": "Brasil",
			"cidade": "Porto Alegre",
			"qtdEstrelas": 5,
			"qtdQuartos": 30
		},
		"numero": 11,
		"capacidadeHospedes": 3,
		"vlrDiaria": 250.8,
		"disponivel": true
	}
]
```

### **GET** - **http://localhost:8080/api/quarto/capacidade_hospedes?capacidadeHospedes=1** - Busca quartos por capacidade de hóspedes

Resposta:
```json
[
	{
		"id": 3,
		"hotel": {
			"id": 3,
			"nome": "nt resort",
			"pais": "Brasil",
			"cidade": "São Paulo",
			"qtdEstrelas": 3,
			"qtdQuartos": 10
		},
		"numero": 7,
		"capacidadeHospedes": 1,
		"vlrDiaria": 80.0,
		"disponivel": false
	}
]
```

### **GET** - **http://localhost:8080/api/quarto/valor_diaria?vlrDiariaMin=100&vlrDiariaMax=200** - Busca quartos por valor da diária

Resposta:
```json
[
	{
		"id": 2,
		"hotel": {
			"id": 2,
			"nome": "nt-plaza",
			"pais": "Estados Unidos",
			"cidade": "Miami",
			"qtdEstrelas": 4,
			"qtdQuartos": 25
		},
		"numero": 5,
		"capacidadeHospedes": 2,
		"vlrDiaria": 187.0,
		"disponivel": true
	}
]
```

### **POST** - **http://localhost:8080/api/avaliacao** - Lança uma nova avaliação

Body:

```json
{
	"id_hotel":3,
	"nomeAvaliador":"Fulano da Silva",
	"nota":4,
	"comentarioAvaliacao": "Bom demais"
}

```

Resposta:

```json
{
	"id": 1,
	"hotel": {
		"id": 3,
		"nome": "nt resort",
		"pais": "Brasil",
		"cidade": "São Paulo",
		"qtdEstrelas": 3,
		"qtdQuartos": 10
	},
	"nomeAvaliador": "Fulano da Silva",
	"nota": 4.0,
	"comentarioAvaliacao": "Bom demais"
}
```
### **GET** - **http://localhost:8080/api/avaliacao** - Busca todas as avaliações

Resposta:

```json
[
	{
		"id": 1,
		"hotel": {
			"id": 3,
			"nome": "nt resort",
			"pais": "Brasil",
			"cidade": "São Paulo",
			"qtdEstrelas": 3,
			"qtdQuartos": 10
		},
		"nomeAvaliador": "Fulano da Silva",
		"nota": 4.0,
		"comentarioAvaliacao": "Bom demais"
	}
]
```

### **GET** - **http://localhost:8080/api/avaliacao/nota?nota=4** - Busca avaliações por nota

Resposta:

```json
[
	{
		"id": 1,
		"hotel": {
			"id": 3,
			"nome": "nt resort",
			"pais": "Brasil",
			"cidade": "São Paulo",
			"qtdEstrelas": 3,
			"qtdQuartos": 10
		},
		"nomeAvaliador": "Fulano da Silva",
		"nota": 4.0,
		"comentarioAvaliacao": "Bom demais"
	}
]
```

# reserva_api

## Base URL: http://localhost:8081

### **POST** - **http://localhost:8081/api/hospede** - Cadastra um novo hóspede

Body:

```json
{
	"nome":"Daniel",
	"email":"teste@gmail.com"
}
```

Resposta:

```json
{
	"id": 2,
	"nome": "Daniel",
	"email": "teste@gmail.com"
}
```

### **GET** - **http://localhost:8081/api/hospede** - Busca todos os hóspedes

Resposta:

```json
[
	{
		"id": 2,
		"nome": "Daniel",
		"email": "teste@gmail.com"
	}
]
```

### **GET** - **http://localhost:8081/api/hospede/nome?nome=daniel** - Busca hóspedes pelo nome
Resposta:

```json
[
	{
		"id": 2,
		"nome": "Daniel",
		"email": "teste@gmail.com"
	}
]
```

### **GET** - **http://localhost:8081/api/hospede/email?email=teste** - Busca hóspedes pelo email

Resposta:

```json
[
	{
		"id": 2,
		"nome": "Daniel",
		"email": "teste@gmail.com"
	}
]
```

### **POST** - **http://localhost:8081/api/reserva** - Lança uma nova reserva

Ao realizar o lançamento, um email de confirmação é enviado para o endereço de email cadastrado ao hóspede correspondente.

Body:

```json
{
	"idQuarto":1,
	"id_hospede":2,
	"metodo_pagamento":"PIX",
    "checkIn": "2024-11-05",
    "checkOut": "2024-11-10"
}
```

Resposta:

```json
{
	"id": 1,
	"quarto": {
		"id": 1,
		"numero": 11,
		"capacidadeHospedes": 3,
		"vlrDiaria": 250.8,
		"disponivel": true,
		"hotel": {
			"id": 1,
			"nome": "nt-inn",
			"pais": "Brasil",
			"cidade": "Porto Alegre",
			"qtdEstrelas": 5,
			"qtdQuartos": 30
		}
	},
	"hospede": {
		"id": 2,
		"nome": "Daniel",
		"email": "teste@gmail.com"
	},
	"metodo_pagamento": {
		"descricao": "pix"
	},
	"vlrTotal": 1254.0,
	"checkIn": "2024-11-05",
	"checkOut": "2024-11-10"
}
```

# Collection do Insomnia
Caso prefira, na pasta raiz do projeto está anexado o arquivo "collection_insomnia", com todas as requisições dos end-points, basta importar para o seu insomnia.

# Instruções de instalação e execução

* 1° - Instalar o Docker
* 2° - Caso seu computador tenha algum SGBD PostgreSQL instalado, como por exemplo, o PgAdmin, recomendo que finalize todos os processos do mesmo que estejam rodando em segundo plano. É possível que haja conflito com a execução do Docker. Se houver conflito, ao inicializar o projeto, no terminal haverá uma mensagem de falha de autenticação da senha do banco de dados.
* 3° Baixar o projeto e abrir na pasta raiz em uma IDE de sua preferência.
* 4° Iniciar o docker - crie um novo terminal com diretório apontado para pasta raíz do projeto (seu_caminho\nt_inn), entre com o seguinte comando: docker-compose up --build
* 5° Aguarde o projeto compilar, os bancos de dados serão criados e logo após os websservices serão inicializados.
* 6° Abra o Docker e confira se o nt_inn foi criado, se tudo ocorreu bem, 4 serviços estarão em execução (hotel-api, reserva-api, hotel_db, reserva_db)
* 7° Se os serviços estiverem rodando, então o projeto já está em execução.

* Caso queira consultar as tabelas diretamente pelo banco de dados, recomendo usar o DBeaver, é facil de instalar e configurar. Basta criar as conexões com as propriedades disponíveis no arquivo docker-compose.yml disponível no diretório raíz do projeto

## Observação
* Por falta de tempo, não consegui implementar todas as funcionalidades que eu gostaria, como por exemplo, o RabbitMq para gerenciar as filas e serviços de mensageria. Mas espero que gostem da solução apresentada!

# Autor
Daniel Martins
## Informações de contato:
email: danieljjmartins@gmail.com
tel: (16) 99110-2451