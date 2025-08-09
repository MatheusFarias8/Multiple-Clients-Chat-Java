
## O que é isso?

Um Chat Multiusuário é uma aplicação de bate-papo, que permite que várias pessoas se comuniquem entre si em tempo real, usando apenas o terminal (linha de comando).

## Objetivos do projeto

- Aprender programação de redes em especial WebSocket.
- Entender melhor como funciona a comunicação entre computadores (Cliente-Servidor).
- Trabalhar com a Interface de Linha de Comando (CLI).
- Praticar Programação Concorrente com Threads.
- Desenvolver a lógica de programação.

## Requisitos

- Java JDK 8+ instalado.
- Terminal ou prompt de comando.
- Arquivos `Server.java`, `Client.java` e `ClientHandler.java` compilados.

## Como rodar?

1. Clone ou baixe o projeto no seu computador.
2. Abra o terminal na pasta do projeto.
3. Compile todos os arquivos Java:
```
javac Server.java Client.java ClientHandler.java
```

4. Inicie o servidor (em uma aba/janela do terminal):
```
java Server
```

5. Abra outro terminal e execute um cliente:
```
java Client
```

6. Digite seu nome de usuário e comece a enviar mensagens para o grupo.
## **Fluxo de comunicação**

1. Cliente se conecta → manda o nome para o servidor.

2. Servidor cria um `ClientHandler` para ele.

3. Quando cliente envia mensagem:

- Vai para o servidor.

- Servidor repassa (`broadcast`) para todos os outros clientes.

4. Quando servidor envia algo para o cliente:

- A thread de `listenForMessage()` lê e imprime no terminal.

## Perigos

Se você abrir sua porta para a internet (por exemplo, fazendo port forwarding no seu roteador), qualquer pessoa com seu IP público e a porta poderá tentar se conectar ao seu servidor.

As mensagens trocadas entre os clientes e o servidor podem ser interceptadas se você estiver enviando dados em texto puro, sem criptografia.

Da forma como o projeto foi implementado (somente para estudo e testes), **deve apenas ser usado na sua rede local.**