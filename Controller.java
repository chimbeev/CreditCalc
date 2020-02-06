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

public class Controller //����� ���������� - ���� ������� �� ������������;������ �������; ����� ���������� �������� �������, �������������� ����������� ������� 
//(��������, �������� ������� ������ ��������� �������).
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
        List<String> dataListIn = View.ReadCrd2(); //���������� ������ �� ����� ������ �� �������� � �������� ������ � ���� list
        //��� ��������� ������� �������� ��������� �������������.
        int lList = dataListIn.size(); //����� �����
        int NumElem = lList/10; //����� ���� �� 1000 ������
        int endCount = 0;
        View.ClearJson();
        System.out.println("������� ������ �������� ...");
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
                System.out.println("��������� ����� " + i);
            }
                if (future.isDone()) 
            {
                System.out.println("����� " + i + "��������");
                View.AddWriteJson(future.get());
                System.out.println("��������� ������� � json ����");
            } else exit = true;
            i = i + 1;
            //View.AddWriteJson(future.get());//���������� ��������� ������� � json ����
            //System.out.println("�������� ���������� � ����");
        } while (exit = false);
        
        
        //System.out.println("��������� ������� � json ����");
        /*
        List<String> subList = new ArrayList<>();
        Runnable[] runnables = new Runnable[100];
        //Thread[] ths = new Thread[5];
        for (int i = 0;i<10;i++) //��������� 100 �������
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
            System.out.println("��");
        }
        for (Future f : futures) { 
            f.get(); 
        }
        /*
        List<String> list1 = dataListIn.subList(0, NumElem); //������ ����
        List<String> list2 = dataListIn.subList(NumElem-1, 2*NumElem); //������ ����
        List<String> list3 = dataListIn.subList(2*NumElem-1, 3*NumElem); //������ ����
        List<String> list4 = dataListIn.subList(3*NumElem-1, dataListIn.size()); //��������� ����
        //Model.getTest(list1);
        
        //�������� list � ������� �� �������� � �������� ���������� ��������
        
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
    
        
        
        //List<String> dataListOut =  Model.getCrd(dataListIn); //�������� list � ������� �� �������� � �������� ���������� ��������
        //View.ShowList(dataListOut);
        //View.PrintToFile(lineStreamOut); //���������� ���������� � ����
        //View.WriteJson(dataListOut);
        //HTTPServer3.main(args); //��������� http ������
        //ClientSocket2.main(args);//���������� ��������� ������� ��� �������
        //SrvSocket srv = new SrvSocket();
        //Thread childThread = new Thread(srv);
        //childThread.start(); //���������� ������ � ��������� ����
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


