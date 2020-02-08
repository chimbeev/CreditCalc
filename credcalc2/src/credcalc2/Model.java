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

public class Model extends Thread {//Мodel - бизнес логика. В него передаем данные по кредиту для проведения расчета
 
    
    public static List<String> getCrd(List<String> crd) throws IOException 
        
          {     
                //получив данные по кредиту в виде листа производим расчет графика платежей. После расчета возвращаем данные по кредиту в виде листа
                List<String> strCredOutlist = new ArrayList<>();
                String [] StrInArray = new String[7];
                int S=0;//сумма кредита
                double P=0; //процент по кредиту, годовых
                int N=0; //срок кредита в месяцах
                double Rem = 0; //остаток задолженности по кредиту
                double Payment = 0; //сумма аннуитетного платежа
                double PaymentAll = 0; //сумма всех  платежей
                double Percent = 0; //процентная составляющая в платеже по кредиту
                double PercentAll = 0; //сумма всех процентов
                double Principal = 0; //сумма, идущая в погашение основного долга в аннуитетном платеже
                double PrincipalAll = 0; // общая сумма, направленная в погашение основного долга
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
                        S=Integer.parseInt(StrInArray[1]);//сумма кредита
                        P=Double.parseDouble(StrInArray[2].replace(",",".")); //процент по кредиту, годовых
                        N=Integer.parseInt(StrInArray[5]); //срок кредита в месяцах
                        Rem = S; //остаток задолженности по кредиту
                        Payment = 0; //сумма аннуитетного платежа
                        PaymentAll = 0;
                        Percent = 0; //процентная составляющая в платеже по кредиту
                        PercentAll = 0;
                        Principal = 0; //сумма, идущая в погашение основного долга в аннуитетном платеже
                        PrincipalAll = 0; // общая сумма, направленная в погашение основного долга
                        strCredOut.setLength(0); //обнуляем StringBuilder
                        strCredOutlist.add("");
                        strCredOutlist.set(h,(strCredOut.append(Integer.parseInt(StrInArray[0])).append(" ").append(Integer.parseInt(StrInArray[1])).append(" ").append(Double.parseDouble(StrInArray[2].replace(",","."))).append(" ")
                                .append(Integer.parseInt(StrInArray[3])).append(" ").append(StrInArray[4].charAt(0)).append(" ").append(Integer.parseInt(StrInArray[5])).append(" ").append(StrInArray[6].substring(0, 10))).toString());
                        
                        input = StrInArray[6].substring(0, 10);
                        try { date = sdf.parse(input); } 
                        catch (ParseException ex) {Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);}
                        calndr1.setTime(date); //преобразуем строку с датой в обьект календарь
                        dt = calndr1.getTime(); 
                        formattedDate = sdf.format(dt);                       
                        if (StrInArray[4].charAt(0) == 'А') //если тип платежа аннуитетный
                        /* (ежемесячная сумма выплаты не меняется, но с каждым месяцем в этой сумме содержится больше выплат основного долга по кредиту и меньше процентных выплат)
                            Вычисление суммы аннуитетного платежа производится по формуле:
                            Payment = S*(p+(p/((1+p)^N-1)) , где p – это нормализованная месячная процентная ставка (p = P / 100 / 12).
                            Для S = 1200000, P = 17 и N = 36 сумма аннуитетного платежа равна 42783.27.
                            Для вычисления процентной составляющей в платеже можно воспользоваться следующей формулой: Percent = p * Rem, где Rem – остаток задолженности по кредиту. 
                            Тогда сумма, идущая в погашение основного долга, равна Principal = Payment - Percent.*/
                        {   p = P/100/12; //нормализованная месячная процентная ставка (p = P /100 / 12).
                            
                            for (b=1; b<=N; b++) //цикл по кол-ву ежемесячных платежей
                            {   Payment = Double.parseDouble((df.format(S*(p+(p/(Math.pow((1+p),N)-1))))).replace(",","."));
                                PaymentAll = Double.parseDouble((df.format(PaymentAll + Payment)).replace(",","."));
                                Rem=Double.parseDouble((df.format(S-PrincipalAll)).replace(",","."));
                                Percent = Double.parseDouble((df.format(p * Rem)).replace(",","."));
                                PercentAll = Double.parseDouble((df.format(PercentAll + Percent)).replace(",","."));
                                Principal = Double.parseDouble((df.format(Payment - Percent)).replace(",",".")); //сумма, идущая в погашение основного долга, равна Principal = Payment - Percent.
                                PrincipalAll = PrincipalAll + Principal;
                                strCredOut.setLength(0);
                                strCredOutlist.set(h,strCredOutlist.get(h) + (strCredOut.append(" ").append(formattedDate).append(" ").append(Payment).append(" ").append(Percent).append(" ").append(Principal)).toString());
                                if (b==N) 
                                {
                                    strCredOut.setLength(0);
                                    strCredOutlist.set(h,strCredOutlist.get(h) + (strCredOut.append(" ").append(PaymentAll).append(" ").append(PercentAll)).toString());
                                }
                                calndr1.add(Calendar.MONTH, 1); //дата следующего платежа по кредиту
                                dt = calndr1.getTime(); 
                                formattedDate = sdf.format(dt);
                            }     
                        }
                        else if (StrInArray[4].charAt(0) == 'Д') //если тип платежа дифференцированный
                        {   /*Схема оплаты по дифференцированному платежу выглядит следующим образом:
                            Условие: сумма кредита — 300 000 рублей, срок кредита — 6 месяцев, ставка по кредиту — 20%. Погашение кредита осуществляется дифференцированными платежами:
                            1. Ежемесячный платеж по основному долгу = сумма кредита / количество платежных периодов в течение всего срока кредита.
                            300 000 рублей / 6 месяцев = 50 000 рублей.
                            2. Ежемесячная сумма начисленных процентов по кредиту Percent = p * Rem, где Rem – остаток задолженности по кредиту. 
                            p – это нормализованная месячная процентная ставка (p = P / 100 / 12).
                            3. Ежемесячный платеж по кредиту = ежемесячный платеж по основному долгу + ежемесячная сумма начисленных процентов по кредиту.
                            */
                            Principal = Double.parseDouble((df.format(S/N)).replace(",",".")); //Ежемесячный платеж по основному долгу
                            p = P / 100 / 12; //нормализованная месячная процентная ставка (p = P / 100 / 12).
                            for (i=1; i<=N; i++) //цикл по кол-ву ежемесячных платежей
                            {
                                Rem=Double.parseDouble((df.format(S-PrincipalAll)).replace(",",".")); //если первый платеж , то остаток задолженности равен сумме кредита
                                Percent = Double.parseDouble((df.format(p * Rem)).replace(",","."));
                                PercentAll = Double.parseDouble((df.format(PercentAll + Percent)).replace(",","."));
                                PrincipalAll = Principal + PrincipalAll;                            
                                Payment = Double.parseDouble((df.format(Principal + Percent)).replace(",",".")); //Ежемесячный платеж по кредиту
                                PaymentAll = Double.parseDouble((df.format(PaymentAll + Payment)).replace(",","."));
                                strCredOut.setLength(0);
                                strCredOutlist.set(h,strCredOutlist.get(h) + (strCredOut.append(" ").append(formattedDate).append(" ").append(Payment).append(" ").append(Percent).append(" ").append(Principal)).toString());
                                if (i==N) 
                                {
                                    strCredOut.setLength(0);
                                    strCredOutlist.set(h,strCredOutlist.get(h) + (strCredOut.append(" ").append(PaymentAll).append(" ").append(PercentAll)).toString());
                                }
                                calndr1.add(Calendar.MONTH, 1); //дата следующего платежа по кредиту
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
         