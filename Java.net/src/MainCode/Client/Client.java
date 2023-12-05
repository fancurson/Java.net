package MainCode.Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import MainCode.*;

public class Client
{
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * Конструктор с настройкой подключения клиента
     *
     */
    Client()
    {
        try {
            clientSocket = new Socket(InetAddress.getLocalHost(), Protocol.PORT);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Connected to the server.");
        }
        catch (Exception e) {
            System.out.println(e);

        }
    }

    /**
     * @param serverAdress пользовательский сокет
     * @param serverPort пользовательский адресс порта
     */
    Client(String serverAdress, int serverPort)
    {
        try {
            clientSocket = new Socket(serverAdress, serverPort);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
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
            out.println(request);
            String response = in.readLine();
            System.out.println("Server response: " + response);

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
            in.close();
            out.close();
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
        if (args.length != 0) {
            System.err.println("Invalid number of arguments\n" + "Usage without arguments ");
            warningBeforeLeaveProgram();
            return;
        }
        Client mainClient = new Client();
        if (mainClient.clientSocket == null)
        {
            warningBeforeLeaveProgram();
            return;
        }
        try {
            BufferedReader consileInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter a request (or enter exit  to close connection)");
            while (true) {
                String request = consileInput.readLine();
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
