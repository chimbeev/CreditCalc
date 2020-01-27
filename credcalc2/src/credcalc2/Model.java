//-------------------------------------Model Begin----------------------------------------------------------------
package credcalc2;


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
                //String strCredOut = "";
                String input = null;
                Calendar calndr1 = (Calendar)Calendar.getInstance(); 
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                Date date = null;
                Date dt = null;
                String formattedDate = "";
                int b = 0;
                int i = 0;
                //StringBuilder builder = new StringBuilder();
                StringBuilder strCredOut = new StringBuilder();
                ///for (String value : crd){ //���� �������� ���� ��������
                for (int h = 0; h < crd.size()-1; h++) {
                        //System.out.println(crd.get(h));
                        //if(value!=null) StrInArray=value.split(";"); //��������� ������ �� �������� ; � ���������� � ������
                        if(crd.get(h)!=null) StrInArray=crd.get(h).split(";");
                        //System.out.println(StrInArray[0]);
                        NewCred.id_client = Integer.parseInt(StrInArray[0]);
                        NewCred.size = Integer.parseInt(StrInArray[1]); // ������ �������
                        NewCred.percent = Double.parseDouble(StrInArray[2].replace(",",".")); //������� �� �������
                        NewCred.first_pay_size = Integer.parseInt(StrInArray[3]); //������ ��������������� ������
                        NewCred.typeOfPayment= StrInArray[4].charAt(0); //��� ������� - ����������� ��� ������������������
                        NewCred.term = Integer.parseInt(StrInArray[5]); //���� ������� � �������
                        NewCred.termOfFirstPayment = StrInArray[6].substring(0, 10); // ���� ������� �������
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
                        //strCredOut = "";
                        //strCredOut.setLength(0); //�������� StringBuilder
                        //System.out.println(h+1);
                        //strCredOutlist.set(h+1, "");//�������� ������
                        strCredOutlist.add("");
                        strCredOutlist.set(h, NewCred.id_client + " " + NewCred.size + " " + NewCred.percent + " " + NewCred.first_pay_size + " " + NewCred.typeOfPayment
                        + " " + NewCred.term + " " + NewCred.termOfFirstPayment);
                        
                        //strCredOut = strCredOut.append(NewCred.id_client).append(" ").append(NewCred.size).append(" ").append(NewCred.percent).append(" ")
                        //        .append(NewCred.first_pay_size).append(" ").append(NewCred.typeOfPayment).append(" ").append(NewCred.term).append(" ").append(NewCred.termOfFirstPayment);
                        
                        //����������� ������ � ����� � ������ ���������
                        input = NewCred.termOfFirstPayment;
                        // Setting to a different date 
                        
                        try { date = sdf.parse(input); } 
                        catch (ParseException ex) {Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);}
                        calndr1.setTime(date); // ���������� ����
                        dt = calndr1.getTime(); 
                        //formattedDate = new SimpleDateFormat("dd.MM.yyyy").format(dt);
                        formattedDate = sdf.format(dt);                       
                        if (NewCred.typeOfPayment == '�') //���� ��� ������� �����������
                        /* (����������� ����� ������� �� ��������, �� � ������ ������� � ���� ����� ���������� ������ ������ ��������� ����� �� ������� � ������ ���������� ������)
                            ���������� ����� ������������ ������� ������������ �� �������:
                            Payment = S*(p+(p/((1+p)^N-1)) , ��� p � ��� ��������������� �������� ���������� ������ (p = P / 100 / 12).
                            ��� S = 1200000, P = 17 � N = 36 ����� ������������ ������� ����� 42783.27.
                            ��� ���������� ���������� ������������ � ������� ����� ��������������� ��������� ��������: Percent = p * Rem, ��� Rem � ������� ������������� �� �������. 
                            ����� �����, ������ � ��������� ��������� �����, ����� Principal = Payment - Percent.*/
                        {   p = P/100/12; //��������������� �������� ���������� ������ (p = P /100 / 12).
                            
                            for (b=1; b<=N; b++) //���� �� ���-�� ����������� ��������
                            {   Payment = getRound(S*(p+(p/(Math.pow((1+p),N)-1))));
                                PaymentAll = getRound(PaymentAll + Payment);
                                Rem=getRound(S-PrincipalAll);
                                Percent = getRound(p * Rem);
                                PercentAll = getRound(PercentAll + Percent);
                                Principal = getRound(Payment - Percent); //�����, ������ � ��������� ��������� �����, ����� Principal = Payment - Percent.
                                PrincipalAll = PrincipalAll + Principal;
                                strCredOutlist.set(h,strCredOutlist.get(h) + " " + formattedDate + " " + Payment + " " + Percent + " " + Principal);
                                
                                //strCredOut = strCredOut.append(" ").append(formattedDate).append(" ").append(Payment).append(" ").append(Percent).append(" ").append(Principal);
                                //strCredOut = strCredOut + " " + formattedDate + " " + Payment + " " + Percent + " " + Principal;
                                if (b==N) 
                                {
                                    strCredOutlist.set(h,strCredOutlist.get(h) + " " + PaymentAll + " " + PercentAll);
                                    //strCredOut = strCredOut.append(" ").append(PaymentAll).append(" ").append(PercentAll);
                                    //strCredOut = strCredOut + ' ' + PaymentAll + ' ' + PercentAll;
                                }
                                calndr1.add(Calendar.MONTH, 1); //���� ���������� ������� �� �������
                                dt = calndr1.getTime(); 
                                //formattedDate = new SimpleDateFormat("dd.MM.yyyy").format(dt);
                                formattedDate = sdf.format(dt);
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
                            //strCredOut = NewCred.id_client + " " + NewCred.size + " " + NewCred.percent + " " + NewCred.first_pay_size + " " + NewCred.typeOfPayment + " " + NewCred.term + " " + 
                        //" " + NewCred.termOfFirstPayment;
                            Principal = getRound(S/N); //����������� ������ �� ��������� �����
                            p = P / 100 / 12; //��������������� �������� ���������� ������ (p = P / 100 / 12).
                            for (i=1; i<=N; i++) //���� �� ���-�� ����������� ��������
                            {
                                Rem=getRound(S-PrincipalAll); //���� ������ ������ , �� ������� ������������� ����� ����� �������
                                Percent = getRound(p * Rem);
                                PercentAll = getRound(PercentAll + Percent);
                                PrincipalAll = Principal + PrincipalAll;                            
                                Payment = getRound(Principal + Percent); //����������� ������ �� �������
                                PaymentAll = getRound(PaymentAll + Payment);
                                strCredOutlist.set(h,strCredOutlist.get(h) + " " + formattedDate + " " + Payment + " " + Percent + " " + Principal);
                                //strCredOut = strCredOut.append(" ").append(formattedDate).append(" ").append(Payment).append(" ").append(Percent).append(" ").append(Principal);
                                //strCredOut = strCredOut + " " + formattedDate + " " + Payment + " " + Percent + " " + Principal;
                                if (i==N) 
                                {
                                    strCredOutlist.set(h,strCredOutlist.get(h) + " " + PaymentAll + " " + PercentAll);
                                    //strCredOut = strCredOut.append(" ").append(PaymentAll).append(" ").append(PercentAll);
                                    //strCredOut = strCredOut + ' ' + PaymentAll + ' ' + PercentAll;
                                }
                                calndr1.add(Calendar.MONTH, 1); //���� ���������� ������� �� �������
                                dt = calndr1.getTime(); 
                                //formattedDate = new SimpleDateFormat("dd.MM.yyyy").format(dt);
                                formattedDate = sdf.format(dt);
                            }     
                        }
                        //System.out.println(strCredOut.toString());
                        
                        //strCredOutlist.add(strCredOut.toString());//���������� � list ���������� �������
                        
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
         