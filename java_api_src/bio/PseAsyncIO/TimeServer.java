package bio.PseAsyncIO;

import bio.SyncBlockingIO.TimeServerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: 陈健
 * @date: 2019/1/26 20:33
 * @description: 伪异步式IO
 */
public class TimeServer {

    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {

            }
        }

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("The time server is start in port : " + port);
            Socket socket = null;
            TimeServerHandlerExecutePool singleExecute = new TimeServerHandlerExecutePool(50, 10000);
            while (true) {
                socket = serverSocket.accept();
                singleExecute.execute(new TimeServerHandler(socket));
            }
        } finally {
            if(serverSocket != null) {
                System.out.println("The time server close");
                serverSocket.close();;
                serverSocket = null;
            }
        }

    }
}
