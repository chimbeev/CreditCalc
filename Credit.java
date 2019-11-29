package credcalc;

import java.util.*;
import java.util.Date;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;




public class Credit //����� ���������� - ���� ������� �� ������������;������ �������; ����� ���������� �������� �������, �������������� ����������� ������� 
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
           File input = new File("input3.csv");
           Scanner sc = new Scanner(input);
           String StrIn = sc.nextLine();
           StrIn = sc.nextLine(); //��������� ������� ������ �� �����
           sc.close();
           Credit NewCred = new Credit(); 
           String [] StrInArray = StrIn.split(";");
           NewCred.id_client = Integer.parseInt(StrInArray[0]);
           NewCred.size = Integer.parseInt(StrInArray[1]); // ������ �������
           NewCred.percent = Integer.parseInt(StrInArray[2]); //������� �� �������
           NewCred.first_pay_size = Integer.parseInt(StrInArray[3]); //������ ��������������� ������
           NewCred.typeOfPayment= StrInArray[4].charAt(0); //��� ������� - ����������� ��� ������������������
           NewCred.term = Integer.parseInt(StrInArray[5]); //���� ������� � �������
           NewCred.termOfFirstPayment = StrInArray[6]; // ���� ������� �������
           //        
           //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ENGLISH);
           //LocalDate date = LocalDate.parse(NewCred.termOfFirstPayment, formatter);
           //System.out.println(date); // 2010-01-02
           
       }    

       public static void prn(int nc) //��� ������ �� �����
       {
            System.out.println(nc);

       }
       public static void prn(Date nc) //��� ������ �� �����
       {
            System.out.println(nc);

       }

       public class Model //�odel - ������ ������. � ���� �������� ��� ������ ������ �� ������� ��� ���������� �������

       {
           
          public void getCrd(Credit crd)
          {
               //������� ������ �� ������� ���������� ������ ������� ��������
               
              
               int S=crd.size;//����� �������
               int P=crd.percent; //������� �� �������, �������
               int N=crd.term; //���� ������� � �������
               double Rem = S; //������� ������������� �� �������
               int numPay = 0; //����� ������� �� �������;
               double Payment = 0; //����� ������������ �������
               double Percent = 0; //���������� ������������ � ������� �� �������
               double Principal = 0; //�����, ������ � ��������� ��������� ����� � ����������� �������
               double PrincipalAll = 0; // ����� �����, ������������ � ��������� ��������� �����

               if (crd.typeOfPayment == '�') //���� ��� ������� �����������
                /* (����������� ����� ������� �� ��������, �� � ������ ������� � ���� ����� ���������� ������ ������ ��������� ����� �� ������� � ������ ���������� ������)
                    ���������� ����� ������������ ������� ������������ �� �������:
                    Payment = S*(p+(p/((1+N)^N-1)) , ��� p � ��� ��������������� �������� ���������� ������ (p = P / 100 / 12).
                    ��� S = 1200000, P = 17 � N = 36 ����� ������������ ������� ����� 42783.27.
                    ��� ���������� ���������� ������������ � ������� ����� ��������������� ��������� ��������: Percent = p * Rem, ��� Rem � ������� ������������� �� �������. 
                    ����� �����, ������ � ��������� ��������� �����, ����� Principal = Payment - Percent.
               */
               {
                    int p = P / 100 / 12; //��������������� �������� ���������� ������ (p = P / 100 / 12).
                    for (int i=1; i<=N; i++) //���� �� ���-�� ����������� ��������
                    {
                         
                         Payment = S*(p+(p/(Math.pow((1+N),N)-1)));
                         Rem=S-PrincipalAll; //���� ������ ������ , �� ������� ������������� ����� ����� �������
                         Percent = p * Rem;
                         Principal = Payment - Percent;
                         PrincipalAll = PrincipalAll + Principal;
                    }     
               }
               else if (crd.typeOfPayment == '�') //���� ��� ������� ������������������
               {
               /*����� ������ �� ������������������� ������� �������� ��������� �������:
               �������: ����� ������� � 300 000 ������, ���� ������� � 6 �������, ������ �� ������� � 20%. ��������� ������� �������������� ������������������� ���������:
               1. ����������� ������ �� ��������� ����� = ����� ������� / ���������� ��������� �������� � ������� ����� ����� �������.
               300 000 ������ / 6 ������� = 50 000 ������.
               2. ����������� ����� ����������� ��������� �� ������� = ������� ��������� ����� � ������� ������� * ������� ���������� ������ * ����� ���� � ��������� 
               ������� (�� 28 �� 31) / ����� ���� � ���� (365 ��� 366).
               3. ����������� ������ �� ������� = ����������� ������ �� ��������� ����� + ����������� ����� ����������� ��������� �� �������.
               */
               }
          }
     }
}
