import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {

        try {

            while (!serverSocket.isClosed()) {

                Socket socket = serverSocket.accept();
                System.out.println("A new client has connected!");

                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();

            }

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public void closeServerSocket() {
        try {

            if (serverSocket != null) serverSocket.close();


        } catch (IOException e) {

            e.printStackTrace();

        }
    }

    public static void main(String[] args) throws IOException{

        int port = 1234;
        ServerSocket serverSocket = new ServerSocket(port);
        Server server = new Server(serverSocket);
        System.out.println("Server started in Port: " + port);
        System.out.println("Listening...");
        server.startServer();

    }
}
