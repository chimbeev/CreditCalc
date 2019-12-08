package credcalc;

import java.util.*;
import java.util.Date;

import credcalc.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.nio.*;


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
        Model.getCrd(lineStream); //�������� ����� � ������� �� ��������
       }    

       public static void prn(int nc) //��� ������ �� �����
       {
            System.out.println(nc);

       }
       public static void prn(Date nc) //��� ������ �� �����
       {
            System.out.println(nc);

       }

//-------------------------------------Controller End------------------------------------------------------------

}
