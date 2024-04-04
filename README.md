# Order-Processor
## Sistema de Processamento de Pedidos de Marktplace

Esta é uma aplicação responsável por processar mensagens postadas em um tópico Kafka referente
a pedidos de compra realizados em um Marktplace. Após realizar o consumo das mensagens do tópico, a aplicação
irá armazenar as informações em um banco de dados NoSQL, MongoDB, e disponibilizará uma API para busca dos pedidos
por meio de filtros.
A aplicação contempla também a implementação de uma validação do Token JWT, visando garantir que 
somente serão retornados os pedidos de compra do vendedor(a) cujo sellerId deve ser informado através do payload do JWT
\
Buscando permitir uma simulação em ambiente de desenvolvimento, foi realizada a implementação do produtor de mensagens no tópico Kafka, 
simulando um Marktplace, visando criar uma massa de dados para testes de integração.



## Documentação da API

Disponível em `/swagger-ui/index.html#/`

![documentacao_api_order-processor](https://github.com/matheushso/order-processor/assets/51098870/921b9dfb-7d69-4e14-b056-ffdb3030619b)
![documentacao_api_order-processor_2](https://github.com/matheushso/order-processor/assets/51098870/7933a1f6-957c-4553-8d4c-0c05e8e32c7e)
![documentacao_api_order-processor_3](https://github.com/matheushso/order-processor/assets/51098870/0ead6f26-85fd-4dc3-8fb8-1ec9b385deea)
![documentacao_api_order-processor_4](https://github.com/matheushso/order-processor/assets/51098870/152d1555-8834-418e-ba7c-994ee48d1f66)

## ✔️ Tecnologias utilizadas
- Java 17
- Spring Boot
- Spring Data JPA
- Mensageria [Kafka]
- Testes automatizados
- JUnit5
- Mockito
- JaCoCo
- Maven
- MongoDB
- Docker
- Swagger

### Testes Automatizados
- Para assegurar a qualidade do código e prevenir bugs em produção,
  foram implementados testes unitários no projeto, abrangendo diferentes partes do código.
  Durante o desenvolvimento dos testes, foi dada ênfase especial às classes que envolvem a lógica de
  negócios, resultando em uma cobertura de 100% em todas as classes que possuem regras de negocio
da aplicação.
- Adicionalmente, para avaliar a eficácia dos testes, foi utilizada a ferramenta JaCoCo,
  que fornece métricas de cobertura de código, permitindo uma análise detalhada da extensão
  dos testes em relação ao código fonte. O JaCoCo é uma ferramenta amplamente empregada para
  medir a cobertura de testes em projetos Java, auxiliando a garantir uma 
cobertura abrangente e eficiente.

![order-processor-coverage](https://github.com/matheushso/order-processor/assets/51098870/2738dc26-c07a-4734-b309-769073da28d4)

# Explicação do projeto

### Kafka

Apache Kafka é uma plataforma avançada de mensageria e streaming de dados, projetada para 
oferecer alta capacidade de processamento, escalabilidade e durabilidade. 
O Kafka facilita a construção de pipelines de dados e a integração entre microserviços.
Ao invés de depender de APIs ponto a ponto, o uso de Kafka permite uma comunicação mais 
fluida e eficiente, suportando grandes volumes de dados com baixa latência.


Kafka opera com o conceito de **tópicos**, onde as mensagens são publicadas. 
Os consumidores podem se inscrever em um ou mais tópicos para processar as mensagens
conforme elas chegam. 
Devido à sua arquitetura distribuída, o Kafka garante alta disponibilidade e resistência a falhas.

## Simulação de Produção de Mensagens

No contexto deste projeto, estou simulando a produção de mensagens para um tópico Kafka
denominado `order-post-topic`. A simulação consiste na criação ou atualização de um pedido (`Order`)
para cada um dos 5 `sellerIds` informados a baixo a cada 1 minuto.

- `"66080feaf1152b798d470aee"`
- `"660810848a754e0c32d6ca2a"`
- `"66080f72f1152b798d470ae8"`
- `"66080ebef1152b798d470adf"`
- `"6608139e49bab06504dfd48b"`

Cada mensagem é um objeto do tipo `Order`, que contém informações sobre o pedido,
incluindo detalhes do item, comprador, endereços de entrega e cobrança, e informações de pagamento.
O tipo `Order` está documentado na API Swagger, oferecendo uma visão detalhada de sua estrutura e campos.


### Exemplo de Mensagem `Order` em JSON postado no tópico Kafka

Aqui está um exemplo de como a mensagem `Order` é enviada para o tópico:

```json
{
  "orderId": "<id gerado>",
  "createdAt": "<timestamp>",
  "updatedAt": "<timestamp>",
  "status": "<NEW/APPROVED/FINISHED/CANCELLED>",
  "items": [
    {
      "itemId": "ABC0123456789",
      "sku": "SKU_DO_SELLER_123",
      "name": "Desempenador de pipa",
      "description": "O melhor desempenador de pipa que você verá na sua vida.\nTração 4x4 e efeitos sonoros ideais para o seu churrasco",
      "price": 1499.99,
      "url": "https://www.minha-lojinha.com.br/products/ABC0123456789"
    }
  ],
  "seller": "<sellerId>",
  "buyer": {
    "id": "BCD2F2CCBA",
    "name": "Pietro Alcantara",
    "email": "p2alcantara_teste@gmail.com"
  },
  "shippingAddress": {
    "postalCode": "04540-010",
    "streetName": "Rua do bobos",
    "number": "456 A",
    "additionalInfo": "Apto 37"
  },
  "billingAddress": {
    "postalCode": "03333-310",
    "streetName": "Rua do sellers",
    "number": "333",
    "additionalInfo": "Próximo ao metrô cacimbas"
  },
  "payment": {
    "method": "<CREDIT/DEBIT/GIFT_CARD/OTHER>",
    "amount": 1499.99,
    "status": "<PENDING/APPROVED/REFUSED>"
  }
}
```

## Consumo de Mensagens e Armazenamento de Dados

Após a simulação da produção de mensagens no tópico Kafka `order-post-topic`,
a aplicação é responsável pelo consumo dessas mensagens.
Cada mensagem, representando um pedido, é processada e armazenada em um banco de dados MongoDB.
Este processo não só garante a persistência dos dados para consultas futuras, mas 
também a integração contínua e eficiente entre a produção de mensagens e a persistência dos dados.

### Melhorando a Performance com Índices Compostos no MongoDB

Para otimizar a performance da busca dos dados armazenados, a aplicação utiliza índices compostos no MongoDB.

### O que são Índices Compostos?

Índices compostos no MongoDB são estruturas que permitem a organização eficiente dos dados
de documentos baseados em múltiplas chaves ou campos. Ao criar um índice composto, você
pode acelerar as operações de busca e consulta que correspondem à ordem e à direção 
(ascendente ou descendente) dos campos no índice.

### Por Que Usar Índices Compostos?

Os índices compostos são essenciais para melhorar a performance de consultas que filtram ou 
ordenam dados baseados em mais de um campo. Por exemplo, se as consultas frequentemente 
buscam pedidos por `sellerId` e `createdAt`, um índice composto nesses dois campos pode 
reduzir significativamente o tempo de busca, pois o MongoDB pode rapidamente localizar 
e retornar os documentos relevantes sem a necessidade de percorrer toda a coleção.
Ao implementar índices compostos que se alinham com os padrões de consulta da aplicação, é possível
garantir uma recuperação de dados rápida e eficaz, melhorando a experiência do usuário e a escalabilidade do sistema.


## API de Busca de Pedidos

É por meio deste endpoint que é possível realizar a busca de pedidos com base em critérios 
de filtragem opcionais, autenticando o usuário através de um JWT com `sellerId` no payload.
Somente pedidos relacionados ao `sellerId` informado no JWT serão retornados, garantindo 
a segurança e a relevância dos dados acessados.

### Método HTTP

`GET /api/v1/orders`

### Autenticação

- **Cabeçalho de Autorização (obrigatório):** Um JWT deve ser fornecido através do cabeçalho 
`Authorization` para autenticação. O `sellerId` incluído no payload do JWT determina os 
pedidos que serão acessíveis.

```plaintext
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzZWxsZXJJZCI6IjY2MDgxMDg0OGE3NTRlMGMzMmQ2Y2EyYSJ9.Me30gMGf-T15zr6FoPRLc6QnPT2KDn7JFA4Rg0Jte0c
```

### Gerar um JWT para Testes com JWT.io

Para gerar um JWT para testes, siga os passos abaixo usando a ferramenta online [JWT.io](https://jwt.io/).

### Passo 1: Acessar o Site

- Abra o site [JWT.io](https://jwt.io/) em seu navegador web.

### Passo 2: Configurar o Payload

- Na seção **PAYLOAD:DATA** do site, insira os dados que deseja incluir no payload do JWT. 
Certifique-se de incluir um `sellerId` válido. Por exemplo:

  ```json
  {
    "sellerId": "seuSellerIdValidoAqui"
  }
  ```
  
Substitua `"seuSellerIdValidoAqui"` pelo `sellerId` que desejar usar para testes.

### Passo 3: Inserir a Chave Secreta de Teste

- Desloque-se até a seção **VERIFY SIGNATURE**.
- No campo que diz `your-256-bit-secret`, insira a seguinte 
chave secreta de teste: `SsyucmjONjVmLtMbQUjIJFfjEF83RfDUU19zUHIYRUg=`.
- Esta chave é fornecida apenas para propósitos de teste.

### Passo 4: Selecionar o Algoritmo de Assinatura

- Certifique-se de que o algoritmo `HS256` está selecionado. Este algoritmo deve ser 
compatível com a chave secreta de teste fornecida.

### Passo 5: Gerar o JWT

- JWT.io irá gerar automaticamente a assinatura e exibir o token JWT completo na área 
**Encoded** na parte superior da página, assim que você inserir os dados do payload e a chave secreta.
- Copie o token JWT da área **Encoded** para utilizá-lo em suas requisições de teste.


### Parâmetros de Filtragem (opcionais)

Os seguintes parâmetros de filtragem são opcionais e podem ser usados para refinar a busca dos pedidos:

- **Data Inicial e Data Final:** Para filtrar pedidos dentro de um intervalo específico de datas,
ambos `startDate` e `endDate` devem ser fornecidos. 
Isso permite buscar todos os pedidos realizados entre essas datas.

    ```plaintext
    startDate=02/04/2024&endDate=03/04/2024
    ```

- **Status do Pedido:** Filtra os pedidos pelo seu status. Exemplos de valores possíveis incluem
`NEW`, `APPROVED`, `FINISHED` ou `CANCELLED`.

    ```plaintext
    orderStatus=APPROVED
    ```

- **Método de Pagamento:** Permite filtrar pedidos pelo método de pagamento. 
Exemplos de valores possíveis são `CREDIT`, `DEBIT`, `GIFT_CARD` ou `OTHER`.

    ```plaintext
    paymentMethod=CREDIT
    ```

- **Status de Pagamento:** Busca por pedidos com um determinado status de pagamento,
como `PENDING`, `APPROVED` ou `REFUSED`.

    ```plaintext
    paymentStatus=APPROVED
    ```

### Regras de Uso

- O cabeçalho de autorização é o único campo obrigatório; todos os outros parâmetros 
de filtragem são opcionais.
- Quando utilizar filtros de data (`startDate` e `endDate`), ambos os parâmetros 
devem ser fornecidos para que a busca seja efetiva.
- Os filtros podem ser combinados para obter resultados mais específicos, adequando-se 
às necessidades particulares de cada busca.

Este endpoint oferece flexibilidade na obtenção de informações detalhadas sobre pedidos de 
um vendedor específico, utilizando a autenticação via JWT para garantir a 
segurança e relevância dos dados acessados.



### Para informações adicionais, incluindo detalhes sobre mensagens de erro e outros aspectostécnicos relevantes, consulte a [Documentação da API](#documentação-da-api).
