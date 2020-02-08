//-------------------------------------Model Begin----------------------------------------------------------------
package credcalc2;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model extends Thread {//�odel - ������ ������. � ���� �������� ������ �� ������� ��� ���������� �������
 
    
    public static List<String> getCrd(List<String> crd) throws IOException 
        
          {     
                //������� ������ �� ������� � ���� ����� ���������� ������ ������� ��������. ����� ������� ���������� ������ �� ������� � ���� �����
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
                String input = null;
                Calendar calndr1 = (Calendar)Calendar.getInstance(); 
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                Date date = null;
                Date dt = null;
                String formattedDate = "";
                int b = 0;
                int i = 0;
                DecimalFormat df = new DecimalFormat("#.##");
                df.setRoundingMode(RoundingMode.CEILING);
                StringBuilder strCredOut = new StringBuilder(100_000);
                for (int h = 0; h <= crd.size()-1; h++) {
                        //if(crd.get(h)!=null) StrInArray=crd.get(h).split(";");
                        StrInArray=crd.get(h).split(";");
                        S=Integer.parseInt(StrInArray[1]);//����� �������
                        P=Double.parseDouble(StrInArray[2].replace(",",".")); //������� �� �������, �������
                        N=Integer.parseInt(StrInArray[5]); //���� ������� � �������
                        Rem = S; //������� ������������� �� �������
                        Payment = 0; //����� ������������ �������
                        PaymentAll = 0;
                        Percent = 0; //���������� ������������ � ������� �� �������
                        PercentAll = 0;
                        Principal = 0; //�����, ������ � ��������� ��������� ����� � ����������� �������
                        PrincipalAll = 0; // ����� �����, ������������ � ��������� ��������� �����
                        strCredOut.setLength(0); //�������� StringBuilder
                        strCredOutlist.add("");
                        strCredOutlist.set(h,(strCredOut.append(Integer.parseInt(StrInArray[0])).append(" ").append(Integer.parseInt(StrInArray[1])).append(" ").append(Double.parseDouble(StrInArray[2].replace(",","."))).append(" ")
                                .append(Integer.parseInt(StrInArray[3])).append(" ").append(StrInArray[4].charAt(0)).append(" ").append(Integer.parseInt(StrInArray[5])).append(" ").append(StrInArray[6].substring(0, 10))).toString());
                        
                        input = StrInArray[6].substring(0, 10);
                        try { date = sdf.parse(input); } 
                        catch (ParseException ex) {Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);}
                        calndr1.setTime(date); //����������� ������ � ����� � ������ ���������
                        dt = calndr1.getTime(); 
                        formattedDate = sdf.format(dt);                       
                        if (StrInArray[4].charAt(0) == '�') //���� ��� ������� �����������
                        /* (����������� ����� ������� �� ��������, �� � ������ ������� � ���� ����� ���������� ������ ������ ��������� ����� �� ������� � ������ ���������� ������)
                            ���������� ����� ������������ ������� ������������ �� �������:
                            Payment = S*(p+(p/((1+p)^N-1)) , ��� p � ��� ��������������� �������� ���������� ������ (p = P / 100 / 12).
                            ��� S = 1200000, P = 17 � N = 36 ����� ������������ ������� ����� 42783.27.
                            ��� ���������� ���������� ������������ � ������� ����� ��������������� ��������� ��������: Percent = p * Rem, ��� Rem � ������� ������������� �� �������. 
                            ����� �����, ������ � ��������� ��������� �����, ����� Principal = Payment - Percent.*/
                        {   p = P/100/12; //��������������� �������� ���������� ������ (p = P /100 / 12).
                            
                            for (b=1; b<=N; b++) //���� �� ���-�� ����������� ��������
                            {   Payment = Double.parseDouble((df.format(S*(p+(p/(Math.pow((1+p),N)-1))))).replace(",","."));
                                PaymentAll = Double.parseDouble((df.format(PaymentAll + Payment)).replace(",","."));
                                Rem=Double.parseDouble((df.format(S-PrincipalAll)).replace(",","."));
                                Percent = Double.parseDouble((df.format(p * Rem)).replace(",","."));
                                PercentAll = Double.parseDouble((df.format(PercentAll + Percent)).replace(",","."));
                                Principal = Double.parseDouble((df.format(Payment - Percent)).replace(",",".")); //�����, ������ � ��������� ��������� �����, ����� Principal = Payment - Percent.
                                PrincipalAll = PrincipalAll + Principal;
                                strCredOut.setLength(0);
                                strCredOutlist.set(h,strCredOutlist.get(h) + (strCredOut.append(" ").append(formattedDate).append(" ").append(Payment).append(" ").append(Percent).append(" ").append(Principal)).toString());
                                if (b==N) 
                                {
                                    strCredOut.setLength(0);
                                    strCredOutlist.set(h,strCredOutlist.get(h) + (strCredOut.append(" ").append(PaymentAll).append(" ").append(PercentAll)).toString());
                                }
                                calndr1.add(Calendar.MONTH, 1); //���� ���������� ������� �� �������
                                dt = calndr1.getTime(); 
                                formattedDate = sdf.format(dt);
                            }     
                        }
                        else if (StrInArray[4].charAt(0) == '�') //���� ��� ������� ������������������
                        {   /*����� ������ �� ������������������� ������� �������� ��������� �������:
                            �������: ����� ������� � 300 000 ������, ���� ������� � 6 �������, ������ �� ������� � 20%. ��������� ������� �������������� ������������������� ���������:
                            1. ����������� ������ �� ��������� ����� = ����� ������� / ���������� ��������� �������� � ������� ����� ����� �������.
                            300 000 ������ / 6 ������� = 50 000 ������.
                            2. ����������� ����� ����������� ��������� �� ������� Percent = p * Rem, ��� Rem � ������� ������������� �� �������. 
                            p � ��� ��������������� �������� ���������� ������ (p = P / 100 / 12).
                            3. ����������� ������ �� ������� = ����������� ������ �� ��������� ����� + ����������� ����� ����������� ��������� �� �������.
                            */
                            Principal = Double.parseDouble((df.format(S/N)).replace(",",".")); //����������� ������ �� ��������� �����
                            p = P / 100 / 12; //��������������� �������� ���������� ������ (p = P / 100 / 12).
                            for (i=1; i<=N; i++) //���� �� ���-�� ����������� ��������
                            {
                                Rem=Double.parseDouble((df.format(S-PrincipalAll)).replace(",",".")); //���� ������ ������ , �� ������� ������������� ����� ����� �������
                                Percent = Double.parseDouble((df.format(p * Rem)).replace(",","."));
                                PercentAll = Double.parseDouble((df.format(PercentAll + Percent)).replace(",","."));
                                PrincipalAll = Principal + PrincipalAll;                            
                                Payment = Double.parseDouble((df.format(Principal + Percent)).replace(",",".")); //����������� ������ �� �������
                                PaymentAll = Double.parseDouble((df.format(PaymentAll + Payment)).replace(",","."));
                                strCredOut.setLength(0);
                                strCredOutlist.set(h,strCredOutlist.get(h) + (strCredOut.append(" ").append(formattedDate).append(" ").append(Payment).append(" ").append(Percent).append(" ").append(Principal)).toString());
                                if (i==N) 
                                {
                                    strCredOut.setLength(0);
                                    strCredOutlist.set(h,strCredOutlist.get(h) + (strCredOut.append(" ").append(PaymentAll).append(" ").append(PercentAll)).toString());
                                }
                                calndr1.add(Calendar.MONTH, 1); //���� ���������� ������� �� �������
                                dt = calndr1.getTime(); 
                                formattedDate = sdf.format(dt);
                            }     
                        }
                        //System.out.println(h);
                    }
                    
               return strCredOutlist;
                       
          }
    public static void getTest(List<String> crd) 
    {
        for(int i=0;i<crd.size();i++)
        {
        System.out.println(crd.get(i));
        } 
     
    }

          
  }

                              
    
     //----------------------------Model End----------------------------------------
         