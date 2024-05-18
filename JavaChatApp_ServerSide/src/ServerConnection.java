import java.io.*;
import java.net.*;

public class ServerConnection {

    public static void FindClient() {
        int port = 1234;

        //Connects clients to the server
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Listening to for connections on port: " + port );

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New Client Succesfully connected");
                new ServerThread(socket).start();

            }

        }

        //Catches errors and prints the specific error
        catch(IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();

        }

    }

}

class ServerThread extends Thread {

    private Socket socket;

    public ServerThread(Socket socket){
        this.socket = socket;
    }

    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            String clientMessage;

            while ((clientMessage = reader.readLine()) != null) {
                System.out.println("Client: " + clientMessage);
                writer.println("Server: " + clientMessage);
            }

            socket.close();
        }

        catch(IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();

        }

    }

}

