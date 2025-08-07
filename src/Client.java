import javax.imageio.IIOException;
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

    public void sendMessage() {

        try {

            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);

            while (socket.isConnected()) {

                String messageToSend = scanner.nextLine();
                bufferedWriter.write(username + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();

            }

        } catch (IOException e) {

            closeEverything(socket, bufferedReader, bufferedWriter);

        }

    }

    public void listenForMessage() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                String msgFromGroupChat;

                while (socket.isConnected()) {

                    try {

                        msgFromGroupChat = bufferedReader.readLine();
                        System.out.println(msgFromGroupChat);

                    } catch (IOException e) {

                        closeEverything(socket, bufferedReader, bufferedWriter);

                    }

                }

            }
        }).start();

    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {

        try {

            if (bufferedReader != null) {

                bufferedReader.close();

            }

            if (bufferedWriter != null) {

                bufferedWriter.close();

            }

            if (socket != null) {

                socket.close();

            }

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();

        String ip = "localhost";
        int port = 1234;
        Socket socket = new Socket(ip, port);
        Client client = new Client(socket, username);

        client.listenForMessage();
        client.sendMessage();

    }

}
