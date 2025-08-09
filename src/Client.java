import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String username;

    public Client(Socket socket, String username) {

        try {

            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;

        } catch (IOException e) {

            closeEverything(socket, bufferedReader, bufferedWriter);

        }

    }

    private volatile boolean running = true;

    public void sendMessage() {

        try {

            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);

            while (running) {

                System.out.print(username + ": ");
                String messageToSend = scanner.nextLine();

                if (messageToSend.trim().isEmpty()) {
                    continue;
                }

                if (messageToSend.equalsIgnoreCase("/exit")) {
                    running = false;
                    bufferedWriter.write(username + " saiu do chat.");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    closeEverything(socket, bufferedReader, bufferedWriter);
                }

                bufferedWriter.write(username + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();

            }

        } catch (IOException e) {

            closeEverything(socket, bufferedReader, bufferedWriter);

        }

    }


    public void listenForMessage() {

        new Thread(() -> {

            String msgFromGroupChat;

            while (running) {

                try {

                    msgFromGroupChat = bufferedReader.readLine();

                    if (msgFromGroupChat == null) {
                        System.out.println("Servidor desconectado.");
                        closeEverything(socket, bufferedReader, bufferedWriter);
                        break;
                    }

                    System.out.println("\n" + msgFromGroupChat);

                    System.out.print(username + ": ");

                } catch (IOException e) {

                    closeEverything(socket, bufferedReader, bufferedWriter);
                    break;
                }

            }
            closeEverything(socket, bufferedReader, bufferedWriter);
            System.exit(0);

        }).start();

    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {

        try {

            if (bufferedReader != null) bufferedReader.close();
            if (bufferedWriter != null) bufferedWriter.close();
            if (socket != null) socket.close();
            System.exit(0);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public static void main(String[] args) throws IOException {

        System.out.println("-=-=-=-= GROUP CHAT -=-=-=-=-");
        System.out.println();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();

        System.out.println("\n Bem-vindo ao chat, " + username + "!");
        System.out.println("Digite suas mensagens e pressione Enter para enviar.");
        System.out.println("Digite /exit para sair do chat.");
        System.out.println("---------------------------------\n");

        String ip = "localhost";
        int port = 1234;
        Socket socket = new Socket(ip, port);

        Client client = new Client(socket, username);
        client.listenForMessage();
        client.sendMessage();

    }

}
