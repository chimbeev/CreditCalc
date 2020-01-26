//-------------------------------------Model Begin----------------------------------------------------------------
package credcalcclientserver;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Model //�odel - ������ ������. � ���� �������� ��� ����� ������ �� ������� ��� ���������� �������

       {
           
          public static List<String> getCrd(List<String> crd)
          {
                //������� ������ �� ������� � ���� ������ ���������� ������ ������� ��������. ����� ������� ���������� ������ �� ������� � ���� ������
                Credit NewCred = new Credit();
                List<String> strCredOutlist = new ArrayList<>();
                String [] StrInArray = new String[7];
                int S=0;//����� �������
                double P=0; //������� �� �������, �������
                int N=0; //���� ������� � �������
                double Rem = 0; //������� ������������� �� �������
                double Payment = 0; //����� ������������ �������
                double PaymentAll = 0; //����� ����  ��������
                double Percent = 0; //���������� ������������ � ������� �� �������
                double PercentAll = 0; //����� ���� ���������
                double Principal = 0; //�����, ������ � ��������� ��������� ����� � ����������� �������
                double PrincipalAll = 0; // ����� �����, ������������ � ��������� ��������� �����
                double p = 0;
                for (String value : crd){ //���� �������� ���� ��������
                        if(value!=null) StrInArray=value.split(";"); //��������� ������ �� �������� ; � ���������� � ������
                        NewCred.id_client = Integer.parseInt(StrInArray[0]);
                        NewCred.size = Integer.parseInt(StrInArray[1]); // ������ �������
                        NewCred.percent = Double.parseDouble(StrInArray[2].replace(",",".")); //������� �� �������
                        NewCred.first_pay_size = Integer.parseInt(StrInArray[3]); //������ ��������������� ������
                        NewCred.typeOfPayment= StrInArray[4].charAt(0); //��� ������� - ����������� ��� ������������������
                        NewCred.term = Integer.parseInt(StrInArray[5]); //���� ������� � �������
                        NewCred.termOfFirstPayment = StrInArray[6]; // ���� ������� �������
                        S=NewCred.size;//����� �������
                        P=NewCred.percent; //������� �� �������, �������
                        N=NewCred.term; //���� ������� � �������
                        Rem = S; //������� ������������� �� �������
                        Payment = 0; //����� ������������ �������
                        PaymentAll = 0;
                        Percent = 0; //���������� ������������ � ������� �� �������
                        PercentAll = 0;
                        Principal = 0; //�����, ������ � ��������� ��������� ����� � ����������� �������
                        PrincipalAll = 0; // ����� �����, ������������ � ��������� ��������� �����
                        
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
                                               
                        if (NewCred.typeOfPayment == '�') //���� ��� ������� �����������
                        /* (����������� ����� ������� �� ��������, �� � ������ ������� � ���� ����� ���������� ������ ������ ��������� ����� �� ������� � ������ ���������� ������)
                            ���������� ����� ������������ ������� ������������ �� �������:
                            Payment = S*(p+(p/((1+p)^N-1)) , ��� p � ��� ��������������� �������� ���������� ������ (p = P / 100 / 12).
                            ��� S = 1200000, P = 17 � N = 36 ����� ������������ ������� ����� 42783.27.
                            ��� ���������� ���������� ������������ � ������� ����� ��������������� ��������� ��������: Percent = p * Rem, ��� Rem � ������� ������������� �� �������. 
                            ����� �����, ������ � ��������� ��������� �����, ����� Principal = Payment - Percent.*/
                        {   p = P/100/12; //��������������� �������� ���������� ������ (p = P /100 / 12).
                            for (int i=1; i<=N; i++) //���� �� ���-�� ����������� ��������
                            {   Payment = getRound(S*(p+(p/(Math.pow((1+p),N)-1))));
                                PaymentAll = getRound(PaymentAll + Payment);
                                Rem=getRound(S-PrincipalAll);
                                Percent = getRound(p * Rem);
                                PercentAll = getRound(PercentAll + Percent);
                                Principal = getRound(Payment - Percent); //�����, ������ � ��������� ��������� �����, ����� Principal = Payment - Percent.
                                PrincipalAll = PrincipalAll + Principal;
                                strCredOut = strCredOut + ' '+ formattedDate + ' ' + Payment + ' ' + Percent + ' ' + Principal;
                                if (i==N) strCredOut = strCredOut + ' ' + PaymentAll + ' ' + PercentAll;
                                calndr1.add(Calendar.MONTH, 1); //���� ���������� ������� �� �������
                                dt = calndr1.getTime(); 
                                formattedDate = new SimpleDateFormat("dd.MM.yyyy").format(dt);
                            }     
                        }
                        else if (NewCred.typeOfPayment == '�') //���� ��� ������� ������������������
                        {   /*����� ������ �� ������������������� ������� �������� ��������� �������:
                            �������: ����� ������� � 300 000 ������, ���� ������� � 6 �������, ������ �� ������� � 20%. ��������� ������� �������������� ������������������� ���������:
                            1. ����������� ������ �� ��������� ����� = ����� ������� / ���������� ��������� �������� � ������� ����� ����� �������.
                            300 000 ������ / 6 ������� = 50 000 ������.
                            2. ����������� ����� ����������� ��������� �� ������� Percent = p * Rem, ��� Rem � ������� ������������� �� �������. 
                            p � ��� ��������������� �������� ���������� ������ (p = P / 100 / 12).
                            3. ����������� ������ �� ������� = ����������� ������ �� ��������� ����� + ����������� ����� ����������� ��������� �� �������.
                            */
                            strCredOut = NewCred.id_client + " " + NewCred.size + " " + NewCred.percent + " " + NewCred.first_pay_size + " " + NewCred.typeOfPayment + " " + NewCred.term + " " + 
                        " " + NewCred.termOfFirstPayment;
                            Principal = getRound(S/N); //����������� ������ �� ��������� �����
                            p = P / 100 / 12; //��������������� �������� ���������� ������ (p = P / 100 / 12).
                            for (int i=1; i<=N; i++) //���� �� ���-�� ����������� ��������
                            {
                                Rem=getRound(S-PrincipalAll); //���� ������ ������ , �� ������� ������������� ����� ����� �������
                                Percent = getRound(p * Rem);
                                PercentAll = getRound(PercentAll + Percent);
                                PrincipalAll = Principal + PrincipalAll;                            
                                Payment = getRound(Principal + Percent); //����������� ������ �� �������
                                PaymentAll = getRound(PaymentAll + Payment);
                                strCredOut = strCredOut + ' '+ formattedDate + ' ' + Payment + ' ' + Percent + ' ' + Principal;
                                if (i==N) strCredOut = strCredOut + ' ' + PaymentAll + ' ' + PercentAll;
                                calndr1.add(Calendar.MONTH, 1); //���� ���������� ������� �� �������
                                dt = calndr1.getTime(); 
                                formattedDate = new SimpleDateFormat("dd.MM.yyyy").format(dt);
                            }     
                        }
                        strCredOutlist.add(strCredOut);//���������� � list ���������� �������
                        System.out.println(strCredOut);
                    };
               return strCredOutlist;
                       
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
          public static List<String> getCrd2(List<String> crd2)
          {
               for (String value : crd2) {
                    System.out.println(value + "dsdsd");
               }
               return null;
          }
          
          public static double getRound(double cir) // ��������� �� �����
          {
             return (new BigDecimal(cir).setScale(2, RoundingMode.HALF_UP).doubleValue()); 
          }
  }

                              
    
     //----------------------------Model End----------------------------------------
         