import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;


public class Server1 {
    public static void main(String[] args) throws IOException {
        try (var listener = new ServerSocket(59090)) {
            System.out.println("The date server is running...");
            while (true) {
                try (var socket = listener.accept()) {
                    var out = new PrintWriter(socket.getOutputStream(), true);
                    var in = new Scanner(socket.getInputStream());
                    // out.println(new Date().toString());
                    out.println(-8.05196654315168);
                    out.println(2.2041696123711283);

                    while(true) {
                      System.out.println(in.nextLine());
                      System.out.println(in.nextLine());
                      System.out.println(in.nextLine());
                    }


                }
            }
        }
    }
}
