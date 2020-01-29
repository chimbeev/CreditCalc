package credcalc2;


import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.Callable;
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
        //List<String> dataListIn = View.ReadCrd(); //���������� ������ �� ����� ������ �� �������� � �������� ������ � ���� list
        List<String> dataListIn = View.ReadCrd2(); //���������� ������ �� ����� ������ �� �������� � �������� ������ � ���� list
        //View.ShowList(dataListIn);
        int NumElem = dataListIn.size()/4; //����� ���� �� 4 �����
        List<String> list1 = dataListIn.subList(0, NumElem); //������ ����
        List<String> list2 = dataListIn.subList(NumElem-1, 2*NumElem); //������ ����
        List<String> list3 = dataListIn.subList(2*NumElem-1, 3*NumElem); //������ ����
        List<String> list4 = dataListIn.subList(3*NumElem-1, dataListIn.size()); //��������� ����
        //�������� list � ������� �� �������� � �������� ���������� ��������
        Callable task1 = () -> {return Model.getCrd(list1);};
        FutureTask <List<String>> future1 = new FutureTask<>(task1);
        new Thread(future1).start();
        View.ShowList(future1.get());
        
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
        View.ShowList(future4.get());
    }
        
        
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

   

//-------------------------------------Controller End------------------------------------------------------------


