package credcalc2;


import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.stream.Stream;


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

    
    public static void main(String[] args) throws IOException, ParseException
       {
        Stream<String> lineStream = View.ReadCrd(); //���������� ������ �� ����� ������ �� �������� � �������� ������ � ���� ������
        Stream<String> lineStreamOut =  Model.getCrd(lineStream); //�������� ����� � ������� �� �������� �������� ����������
        View.PrintToFile((InputStream) lineStreamOut); //���������� ���������� � ����
       }    

   

//-------------------------------------Controller End------------------------------------------------------------

}
