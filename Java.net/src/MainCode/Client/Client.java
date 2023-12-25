package MainCode.Client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import MainCode.*;

public class Client
{
    private Socket clientSocket;
    ObjectOutputStream os;
    ObjectInputStream is;

    /**
     * Конструктор с настройкой подключения клиента
     *
     */
    Client()
    {
        try {
            clientSocket = new Socket(InetAddress.getLocalHost(), Protocol.PORT);
            os = new ObjectOutputStream(clientSocket.getOutputStream());
            is = new ObjectInputStream(clientSocket.getInputStream());
            System.out.println("Connected to the server.");
        }
        catch (Exception e) {
            System.out.println(e);

        }
    }

    /**
     * @param serverAddress пользовательский сокет
     */
    Client(String serverAddress)
    {
        try {
            clientSocket = new Socket(serverAddress, Protocol.PORT);
            os = new ObjectOutputStream(clientSocket.getOutputStream());
            is = new ObjectInputStream(clientSocket.getInputStream());
            System.out.println("Connected to the server.");
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }

    /**
     * Функция как отправляет данные, так и принимает их
     * @param request это данные отправляемые на сервер
     */
    public void sendRequestGetResponse(String request)
    {
        try{
            CommandInputMessage cmdRequest = new CommandInputMessage(request);
            os.writeObject(cmdRequest);
            CommandOutputMessage cmdOut = (CommandOutputMessage) is.readObject();
            System.out.println("Server response:\n" + cmdOut.getOutput() + "End of server response:");
        }catch (Exception e)
        {
            System.out.println(e);
        }
    }

    /**
     * Закрытие клиента
     */
    public void closeConnection()
    {
        try{
            clientSocket.close();
            is.close();
            os.close();
        }catch (Exception e)
        {
            System.out.println(e);
        }
    }

    /**
     * Запуск клиента
     * @param args NULL
     */
    static public void main(String args[]) {
        if (args.length > 1) {
            System.err.println("Invalid number of arguments\n" + "Usage: command <Ip> ");
            warningBeforeLeaveProgram();
            return;
        }
        Client mainClient;
        if (args.length == 0) {
            mainClient = new Client();
        } else {
            mainClient = new Client(args[0]);
        }
        if (mainClient.clientSocket == null)
        {
            warningBeforeLeaveProgram();
            return;
        }
        try {
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter a request (or enter exit  to close connection)");
            while (true) {
                String request = consoleInput.readLine();
                if (request.equalsIgnoreCase("exit")) {
                    break;
                }
                mainClient.sendRequestGetResponse(request);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            mainClient.closeConnection();
        }
    }

    /**
     * Функция для оповещения перед закрытием программы если все "cломалось"
     * <p> P.S используется перед завершением всей программы
     * <p> P.S.2 после нее в коде нужно использовать return для закрытия программы, сама она ее не закроет
     */
    static public void warningBeforeLeaveProgram()
    {
        System.out.println("Please press any key to end programm...");
        try{
            System.in.read();
        }catch (Exception e)
        {
            System.out.println(e);
        }
    }

}
