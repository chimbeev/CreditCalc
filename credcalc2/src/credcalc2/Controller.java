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
        List<String> dataListIn = View.ReadCrd2(); //���������� ������ �� ����� ������ �� �������� � �������� ������ � ���� list
        //��� ��������� ������� �������� ��������� �������������.
        int lList = dataListIn.size(); //����� �����
        int NumElem = lList/1000; //����� ���� �� 1000 ������
        int endCount = 0;
        View.ClearJson();//������� ���� json
        System.out.println("������� ������ �������� ...");
        for (int i = 0;i<1000;i++) //��������� 1000 �������. � ���� ������ ���������� �������� ���� ����� 8 �����.
        {   
            endCount = (i+1)*NumElem;
            if (endCount > lList) endCount = lList-1;
            List<String> subList = dataListIn.subList(i*NumElem, endCount);
            Callable task = () -> {return Model.getCrd(subList);};
            FutureTask <String> future = new FutureTask<>(task);
            new Thread(future).start();
            //���������� ��������� ������� � json ���� ����� ������ ��������� View.AddWriteJson() �� ��������� Model.getCrd
        }
        System.out.println("���������� �������� � json ����");
        
    }
}    
//-------------------------------------Controller End------------------------------------------------------------


