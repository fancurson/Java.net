package MainCode.Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import MainCode.*;
import csdev.Protocol;

public class Client
{
    // Сокет клиента
    private Socket clientSocket;
    // Для записи/считывания
    private PrintWriter out;
    private BufferedReader in;


    //Конструктор с настройкой подключения клиента
    // P.S( Мне тут все сокеты находит само, но можно спараметрами где будут пользовательские сокеты)
    Client(/*String serverAdress, int serverPort*/)
    {
        try {
            clientSocket = new Socket(InetAddress.getLocalHost(), Protocol.PORT);
            //clientSocket = new Socket(serverAdress, serverPort);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            /////// Cообщение чтобы знать, смысловой нагрузки не несет
            System.out.println("Connected to the server.");
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }

    // Параметр - это данные отправляемые на сервер
    // Функция как отправляет данные, так и принимает их
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

    // Закрытие связи
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

    // Функция для оповещения перед закрытием программы если все "cломалось"
    // P.S используется перед завершением всей программы
    // P.S.2 после нее в коде нужно использовать return для закрытия программы, сама она ее не закроет
    public void WarningBeforeLeaveProgram()
    {
        System.out.println("Please press any key to end programm...");
        try{
            System.in.read();
        }catch (Exception e)
        {
            System.out.println(e);
        }
    }


    //////////////////////////// Запуск клмента
    static public void main(String args[])
    {
        /*
        //Я не знаю что должно лежать в массиве Args, кол-во аргументов настрой сам
        if (args.length < 2 || args.length > 3) {
			System.err.println(	"Invalid number of arguments\n");
			WarningBeforeLeaveProgram();
			return;
		}
         */
        Client mainClient = new Client();
        try{
            BufferedReader consileInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter a request (or enter exit  to close connection)");
            while (true)
            {
                String request = consileInput.readLine();
                if (request.equalsIgnoreCase("exit"))
                {
                    break;
                }
                mainClient.sendRequestGetResponse(request);
            }
        }catch (Exception e)
        {
            System.out.println(e);
        }finally {
            mainClient.closeConnection();
        }
    }
}
