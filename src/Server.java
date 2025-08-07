import javax.imageio.IIOException;
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

        } catch (IIOException e) {

        }

    }

    public void closeServerSocket() {
        try {

            if (serverSocket != null) {

                serverSocket.close();

            }

        } catch (IIOException e) {

            e.printStackTrace();

        }
    }

    public static void main(String[] args) throws IIOException{

        int port = 1234;
        ServerSocket serverSocket = new ServerSocke(port);
        Server server = new Server(serverSocket);
        server.startServer();

    }
}
