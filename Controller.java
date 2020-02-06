package credcalc2;


import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
        List<String> dataListIn = View.ReadCrd2(); //производим чтение из файла данных по кредитам и получаем данные в виде list
        //Для ускорения расчета кредитов применяем мнопоточность.
        int lList = dataListIn.size(); //длина листа
        int NumElem = lList/10; //делим лист на 1000 листов
        int endCount = 0;
        View.ClearJson();
        System.out.println("Ведется расчет кредитов ...");
        //ArrayList<List<String>> a = new ArrayList<List<String>>();
        
        int i = 0; boolean exit = false;
        do
        {
            if (i <= 10)
            {
                endCount = (i+1)*NumElem;
                if (endCount > lList) endCount = lList-1;
                List<String> subList = dataListIn.subList(i*NumElem, endCount);
                Callable task = () -> {return Model.getCrd(subList);};
                FutureTask <List<String>> future = new FutureTask<>(task);
                Thread Thread1 = new Thread(future);
                Thread1.start();
                System.out.println("Запустили поток " + i);
            }
                if (future.isDone()) 
            {
                System.out.println("поток " + i + "завершен");
                View.AddWriteJson(future.get());
                System.out.println("Результат записан в json файл");
            } else exit = true;
            i = i + 1;
            //View.AddWriteJson(future.get());//Записываем результат расчета в json файл
            //System.out.println("Записали результаты в файл");
        } while (exit = false);
        
        
        //System.out.println("Результат записан в json файл");
        /*
        List<String> subList = new ArrayList<>();
        Runnable[] runnables = new Runnable[100];
        //Thread[] ths = new Thread[5];
        for (int i = 0;i<10;i++) //Запускаем 100 потоков
        {   
            endCount = (i+1)*NumElem;
            if (endCount > lList) endCount = lList-1;
            subList = dataListIn.subList(i*NumElem, endCount);
            runnables[i] = new ThExecute(subList);
        }
        ExecutorService service = Executors.newFixedThreadPool(50);
        
        List<Future> futures = new ArrayList<>();
        for (Runnable r : runnables) {
            futures.add(service.submit(r));
            System.out.println("Ре");
        }
        for (Future f : futures) { 
            f.get(); 
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
}

   class ThExecute implements Runnable 
   {
        List<String> lis;
        public ThExecute (List<String> lis) throws IOException 
        {
            this.lis = lis;
            Model.getCrd(lis);
        }

        @Override
        public void run() 
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    
    }
  


//-------------------------------------Controller End------------------------------------------------------------


