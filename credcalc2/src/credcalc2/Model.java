//-------------------------------------Model Begin----------------------------------------------------------------
package credcalc2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.*;


public class Model //�odel - ������ ������. � ���� �������� ��� ����� ������ �� ������� ��� ���������� �������

       {
           
          public static Stream<String> getCrd(Stream<String> crd)
          {
               //������� ������ �� ������� � ���� ������ ���������� ������ ������� ��������. ����� ������� ���������� ������ �� ������� � ���� ������
               Credit NewCred = new Credit();
               List<String> strCredOutlist = new ArrayList<>();
               crd
                    .skip(1)
                    .forEach(e -> 
                    {
                        String [] StrInArray = e.split(";"); //��������� ������ �� �������� ; � ���������� � ������
                        NewCred.id_client = Integer.parseInt(StrInArray[0]);
                        NewCred.size = Integer.parseInt(StrInArray[1]); // ������ �������
                        NewCred.percent = Double.parseDouble(StrInArray[2].replace(",",".")); //������� �� �������
                        NewCred.first_pay_size = Integer.parseInt(StrInArray[3]); //������ ��������������� ������
                        NewCred.typeOfPayment= StrInArray[4].charAt(0); //��� ������� - ����������� ��� ������������������
                        NewCred.term = Integer.parseInt(StrInArray[5]); //���� ������� � �������
                        NewCred.termOfFirstPayment = StrInArray[6]; // ���� ������� �������
                        int S=NewCred.size;//����� �������
                        double P=NewCred.percent; //������� �� �������, �������
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
                        // Setting to a different date 
                        Calendar calndr1 = (Calendar)Calendar.getInstance(); 
                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                        Date date = null;
                        try { date = sdf.parse(input); } 
                        catch (ParseException ex) {Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);}
                        calndr1.setTime(date); // ���������� ����
                        Date dt = calndr1.getTime(); 
                        String formattedDate = new SimpleDateFormat("dd.MM.yyyy").format(dt);
                        //System.out.println("The modified Date:" + formattedDate);
                        
                        if (NewCred.typeOfPayment == '�') //���� ��� ������� �����������
                        /* (����������� ����� ������� �� ��������, �� � ������ ������� � ���� ����� ���������� ������ ������ ��������� ����� �� ������� � ������ ���������� ������)
                            ���������� ����� ������������ ������� ������������ �� �������:
                            Payment = S*(p+(p/((1+N)^N-1)) , ��� p � ��� ��������������� �������� ���������� ������ (p = P / 100 / 12).
                            ��� S = 1200000, P = 17 � N = 36 ����� ������������ ������� ����� 42783.27.
                            ��� ���������� ���������� ������������ � ������� ����� ��������������� ��������� ��������: Percent = p * Rem, ��� Rem � ������� ������������� �� �������. 
                            ����� �����, ������ � ��������� ��������� �����, ����� Principal = Payment - Percent.
                        */
                        {
                            double p = P / 100 / 12; //��������������� �������� ���������� ������ (p = P / 100 / 12).
                            for (int i=1; i<=N; i++) //���� �� ���-�� ����������� ��������
                            {
                                Payment = S*(p+(p/(Math.pow((1+N),N)-1)));
                                Rem=S-PrincipalAll; //���� ������ ������ , �� ������� ������������� ����� ����� �������
                                Percent = p * Rem;
                                Principal = Payment - Percent; //�����, ������ � ��������� ��������� �����, ����� Principal = Payment - Percent.
                                PrincipalAll = PrincipalAll + Principal;
                                strCredOut = strCredOut + ' '+ formattedDate + ' ' + Payment + ' ' + Percent + ' ' + Principal;
                                calndr1.add(Calendar.MONTH, 1); //���� ���������� ������� �� �������
                                dt = calndr1.getTime(); 
                                formattedDate = new SimpleDateFormat("dd.MM.yyyy").format(dt);
                                //System.out.println("The modified Date2:" + formattedDate);
                                
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
                            double p = P / 100 / 12; //��������������� �������� ���������� ������ (p = P / 100 / 12).
                            for (int i=1; i<=N; i++) //���� �� ���-�� ����������� ��������
                            {
                                Rem=S-PrincipalAll; //���� ������ ������ , �� ������� ������������� ����� ����� �������
                                Percent = p * Rem;
                                PrincipalAll = PrincipalAll + Principal; 
                                Payment = Principal + Percent; //����������� ������ �� �������
                                strCredOut = strCredOut + ' '+ formattedDate + ' ' + Payment + ' ' + Percent + ' ' + Principal;
                                calndr1.add(Calendar.MONTH, 1); //���� ���������� ������� �� �������
                                dt = calndr1.getTime(); 
                                formattedDate = new SimpleDateFormat("dd.MM.yyyy").format(dt);
                                //System.out.println("The modified Date2:" + formattedDate);
                            }     
                   
                        }
                        strCredOutlist.add(strCredOut);//���������� � list ���������� �������
                    });
               return(strCredOutlist.stream());//������������ list � stream
          }
          public static class Credit {
            int id_client;
            int size;
            double percent;
            int first_pay_size;
            char typeOfPayment;
            int term;
            String termOfFirstPayment;
          }
               }

                                  
    
     //----------------------------Model End----------------------------------------
         