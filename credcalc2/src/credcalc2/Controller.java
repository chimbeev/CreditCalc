package credcalc2;


import java.io.IOException;
import java.text.ParseException;
import java.util.List;


//---------------------------Controller Begin ------------------------------

public class Controller //����� ���������� - ���� ������� �� ������������;������ �������; ����� ���������� �������� �������, �������������� ����������� ������� 
//(��������, �������� ������� ������ ��������� �������).
//���������� �� HTTPServer get ������ � ����������� ������� � �������� ���������� ������� �������

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

            //����� 10 �������� � ���������� �� �� ������ ��� ������� 
            String credStr = "";
            HTTPServer3.main(args); //��������� http ������
            for (int i = 10;i<21;i++)
            {   
                credStr = dataListIn.get(i);
                View.GetCrdFromSrv(credStr); //����������� �� ������ ������ � ����������� �������
            }
        
       }       
           
        
}    

   

//-------------------------------------Controller End------------------------------------------------------------


