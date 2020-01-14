//-------------------------------------Model Begin----------------------------------------------------------------
package credcalc;

import java.util.*;
import java.util.Date;
import java.util.stream.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.time.*;
import java.time.format.*;
import java.nio.file.*;


public class Model //�odel - ������ ������. � ���� �������� ��� ����� ������ �� ������� ��� ���������� �������

       {
           
          public static void getCrd(Stream<String> crd)
          {
               //������� ������ �� ������� � ���� ������ ���������� ������ ������� ��������
               Credit NewCred = new Credit();  
               crd
                    .forEach(e -> 
                    {
                        String [] StrInArray = e.split(";"); //��������� ������ �� �������� ; � ���������� � ������
                        NewCred.id_client = Integer.parseInt(StrInArray[0]);
                        NewCred.size = Integer.parseInt(StrInArray[1]); // ������ �������
                        NewCred.percent = Integer.parseInt(StrInArray[2]); //������� �� �������
                        NewCred.first_pay_size = Integer.parseInt(StrInArray[3]); //������ ��������������� ������
                        NewCred.typeOfPayment= StrInArray[4].charAt(0); //��� ������� - ����������� ��� ������������������
                        NewCred.term = Integer.parseInt(StrInArray[5]); //���� ������� � �������
                        NewCred.termOfFirstPayment = StrInArray[6]; // ���� ������� �������
                        int S=NewCred.size;//����� �������
                        int P=NewCred.percent; //������� �� �������, �������
                        int N=NewCred.term; //���� ������� � �������
                        double Rem = S; //������� ������������� �� �������
                        int numPay = 0; //����� ������� �� �������;
                        double Payment = 0; //����� ������������ �������
                        double Percent = 0; //���������� ������������ � ������� �� �������
                        double Principal = 0; //�����, ������ � ��������� ��������� ����� � ����������� �������
                        double PrincipalAll = 0; // ����� �����, ������������ � ��������� ��������� �����
                        
                        String strCredOut = NewCred.id_client + " " + NewCred.size + " " + NewCred.percent + " " + NewCred.first_pay_size + " " + NewCred.typeOfPayment + " " + NewCred.term + " " + 
                        " " + NewCred.termOfFirstPayment;
                        //����������� ������ � ����� � ������ ���������
                        String input = NewCred.termOfFirstPayment;
                        DateTimeFormatter f = DateTimeFormatter.ofPattern( "dd MM uuuu" );
                        ZonedDateTime zdt = ZonedDateTime.parse( input , f );
                        
                        if (NewCred.typeOfPayment == '�') //���� ��� ������� �����������
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
                                Principal = Payment - Percent; //�����, ������ � ��������� ��������� �����, ����� Principal = Payment - Percent.
                                PrincipalAll = PrincipalAll + Principal;
                                strCredOut = strCredOut + ' '+ zdt + ' ' + Payment + ' ' + Percent + ' ' + Principal;
                                zdt.plusMonths(1);
                            }     
                        }
                        else if (NewCred.typeOfPayment == '�') //���� ��� ������� ������������������
                        {
                            /*����� ������ �� ������������������� ������� �������� ��������� �������:
                            �������: ����� ������� � 300 000 ������, ���� ������� � 6 �������, ������ �� ������� � 20%. ��������� ������� �������������� ������������������� ���������:
                            1. ����������� ������ �� ��������� ����� = ����� ������� / ���������� ��������� �������� � ������� ����� ����� �������.
                            300 000 ������ / 6 ������� = 50 000 ������.
                            2. ����������� ����� ����������� ��������� �� ������� Percent = p * Rem, ��� Rem � ������� ������������� �� �������. 
                            p � ��� ��������������� �������� ���������� ������ (p = P / 100 / 12).
                            3. ����������� ������ �� ������� = ����������� ������ �� ��������� ����� + ����������� ����� ����������� ��������� �� �������.
                            */
                            strCredOut = NewCred.id_client + " " + NewCred.size + " " + NewCred.percent + " " + NewCred.first_pay_size + " " + NewCred.typeOfPayment + " " + NewCred.term + " " + 
                        " " + NewCred.termOfFirstPayment;
                            Principal = S/N; //����������� ������ �� ��������� �����
                            int p = P / 100 / 12; //��������������� �������� ���������� ������ (p = P / 100 / 12).
                            for (int i=1; i<=N; i++) //���� �� ���-�� ����������� ��������
                            {
                                Rem=S-PrincipalAll; //���� ������ ������ , �� ������� ������������� ����� ����� �������
                                Percent = p * Rem;
                                PrincipalAll = PrincipalAll + Principal; 
                                Payment = Principal + Percent; //����������� ������ �� �������
                                strCredOut = strCredOut + ' '+ zdt + ' ' + Payment + ' ' + Percent + ' ' + Principal;
                                zdt.plusMonths(1);
                            }     
                   
                        }
                    });
          }
          public static class Credit {
            int id_client;
            int size;
            int percent;
            int first_pay_size;
            char typeOfPayment;
            int term;
            String termOfFirstPayment;
          }
               }

                                  
    
     //----------------------------Model End----------------------------------------
         