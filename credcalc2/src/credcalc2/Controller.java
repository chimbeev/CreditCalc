package credcalc2;


import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

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

    
    public static void main(String[] args) throws IOException, ParseException, Exception
       {
        //List<String> dataListIn = View.ReadCrd(); //производим чтение из файла данных по кредитам и получаем данные в виде list
        List<String> dataListIn = View.ReadCrd2(); //производим чтение из файла данных по кредитам и получаем данные в виде list
        //View.ShowList(dataListIn);
        //List<String> dataListOut =  Model.getCrd(dataListIn);
        int lList = dataListIn.size(); //длина листа
        int NumElem = lList/1000; //делим лист на 1000 листов
        int endCount = 0;
        //List<String> dataListOut = null;
        for (int i = 0;i<1000;i++)
        {   
            endCount = (i+1)*NumElem;
            if (endCount > lList) endCount = lList-1;
            List<String> subList = dataListIn.subList(i*NumElem, endCount);
            Callable task = () -> {return Model.getCrd(subList);};
            FutureTask <List<String>> future = new FutureTask<>(task);
            new Thread(future).start();
            //dataListOut = future.get();
        }
        /*
        List<String> list1 = dataListIn.subList(0, NumElem); //первый лист
        List<String> list2 = dataListIn.subList(NumElem-1, 2*NumElem); //второй лист
        List<String> list3 = dataListIn.subList(2*NumElem-1, 3*NumElem); //третий лист
        List<String> list4 = dataListIn.subList(3*NumElem-1, dataListIn.size()); //четвертый лист
        //Model.getTest(list1);
        
        //передаем list с данными по кредитам и получаем результаты расчетов
        
        Callable task1 = () -> {return Model.getCrd(list1);};
        FutureTask <List<String>> future1 = new FutureTask<>(task1);
        new Thread(future1).start();
        //View.ShowList(future1.get());
       
        Callable task2 = () -> {return Model.getCrd(list2);};
        FutureTask <List<String>> future2 = new FutureTask<>(task2);
        new Thread(future2).start();
        View.ShowList(future2.get());
        
        Callable task3 = () -> {return Model.getCrd(list3);};
        FutureTask <List<String>> future3 = new FutureTask<>(task3);
        new Thread(future3).start();
        View.ShowList(future3.get());
        
        Callable task4 = () -> {return Model.getCrd(list4);};
        FutureTask <List<String>> future4 = new FutureTask<>(task4);
        new Thread(future4).start();
        View.ShowList(future4.get());*/
    }
        
        
        //List<String> dataListOut =  Model.getCrd(dataListIn); //передаем list с данными по кредитам и получаем результаты расчетов
        //View.ShowList(dataListOut);
        //View.PrintToFile(lineStreamOut); //записываем результаты в файл
        //View.WriteJson(dataListOut);
        //HTTPServer3.main(args); //запускаем http сервер
        //ClientSocket2.main(args);//отправляем параметры кредита для расчета
        //SrvSocket srv = new SrvSocket();
        //Thread childThread = new Thread(srv);
        //childThread.start(); //стартовали сервер в отдельной нити
          //ClientSocket.main(args);
        //Test1.main(args);
           
           
        
       }    

   

//-------------------------------------Controller End------------------------------------------------------------


