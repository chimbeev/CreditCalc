package credcalc2;


import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

//---------------------------Controller Begin ------------------------------

public class Controller //класс контроллер - приЄм запроса от пользовател€;анализ запроса; выбор следующего действи€ системы, соответственно результатам анализа 
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
        List<String> dataListIn = View.ReadCrd2(); //производим чтение из файла данных по кредитам и получаем данные в виде list
        //ƒл€ ускорени€ расчета кредитов примен€ем мнопоточность.
        int lList = dataListIn.size(); //длина листа
        int NumElem = lList/1000; //делим лист на 1000 листов
        int endCount = 0;
        View.ClearJson();
        System.out.println("¬едетс€ расчет кредитов ...");
        for (int i = 0;i<1000;i++) //«апускаем 1000 потоков
        {   
            endCount = (i+1)*NumElem;
            if (endCount > lList) endCount = lList-1;
            List<String> subList = dataListIn.subList(i*NumElem, endCount);
            Callable task = () -> {return Model.getCrd(subList);};
            FutureTask <List<String>> future = new FutureTask<>(task);
            new Thread(future).start();
            View.AddWriteJson(future.get());//«аписываем результат расчета в json файл
        }
        System.out.println("–езультат записан в json файл");
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
        //ClientSocket2.main(args);//отправл€ем параметры кредита дл€ расчета
        //SrvSocket srv = new SrvSocket();
        //Thread childThread = new Thread(srv);
        //childThread.start(); //стартовали сервер в отдельной нити
          //ClientSocket.main(args);
        //Test1.main(args);
           
           
        
       }    

   

//-------------------------------------Controller End------------------------------------------------------------


