package credcalc2;


import java.io.IOException;
import java.text.ParseException;
import java.util.List;

//---------------------------Controller Begin ------------------------------

public class Controller //класс контроллер - приём запроса от пользователя;анализ запроса; выбор следующего действия системы, соответственно результатам анализа 
//(например, передача запроса другим элементам системы).
{
    int id_client;
    int size;
    int percent;
    int first_pay_size;
    char typeOfPayment;
    int term;
    String termOfFirstPayment;

    
    public static void main(String[] args) throws IOException, ParseException
       {
        //List<String> dataListIn = View.ReadCrd(); //производим чтение из файла данных по кредитам и получаем данные в виде list
        //View.ShowStream(lineStream);
        //List<String> dataListOut =  Model.getCrd(dataListIn); //передаем list с данными по кредитам и получаем результаты расчетов
        //View.PrintToFile(lineStreamOut); //записываем результаты в файл
        //View.WriteJson(dataListOut);
        SrvSocket srv = new SrvSocket();
        Thread childThread = new Thread(srv);
        childThread.start(); //стартовали сервер в отдельной нити
        ClientSocket.main(args);
        
       }    

   

//-------------------------------------Controller End------------------------------------------------------------

}
