package credcalc2;


import java.io.IOException;
import java.text.ParseException;
import java.util.List;

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
        List<String> dataListOut =  Model.getCrd(dataListIn); //�������� list � ������� �� �������� � �������� ���������� ��������
        //View.PrintToFile(lineStreamOut); //���������� ���������� � ����
        View.WriteJson(dataListOut);
        //HTTPServer3.main(args); //��������� http ������
        //ClientSocket2.main(args);//���������� ��������� ������� ��� �������
        //SrvSocket srv = new SrvSocket();
        //Thread childThread = new Thread(srv);
        //childThread.start(); //���������� ������ � ��������� ����
          //ClientSocket.main(args);
        //Test1.main(args);
           
           
        
       }    

   

//-------------------------------------Controller End------------------------------------------------------------

}
