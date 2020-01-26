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



public class Model //Мodel - бизнес логика. В него передаем как поток данные по кредиту для проведения расчета

       {
           
          public static List<String> getCrd(List<String> crd)
          {
                //получив данные по кредиту в виде потока производим расчет графика платежей. После расчета возвращаем данные по кредиту в виде стрима
                Credit NewCred = new Credit();
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
                for (String value : crd){ //цикл перебора всех кредитов
                        if(value!=null) StrInArray=value.split(";"); //разделяем строку по символам ; и записываем в массив
                        NewCred.id_client = Integer.parseInt(StrInArray[0]);
                        NewCred.size = Integer.parseInt(StrInArray[1]); // размер кредита
                        NewCred.percent = Double.parseDouble(StrInArray[2].replace(",",".")); //процент по кредиту
                        NewCred.first_pay_size = Integer.parseInt(StrInArray[3]); //размер первоначального взноса
                        NewCred.typeOfPayment= StrInArray[4].charAt(0); //вид платежа - аннуитетный или дифференцированный
                        NewCred.term = Integer.parseInt(StrInArray[5]); //срок кредита в месяцах
                        NewCred.termOfFirstPayment = StrInArray[6]; // дата первого платежа
                        S=NewCred.size;//сумма кредита
                        P=NewCred.percent; //процент по кредиту, годовых
                        N=NewCred.term; //срок кредита в месяцах
                        Rem = S; //остаток задолженности по кредиту
                        Payment = 0; //сумма аннуитетного платежа
                        PaymentAll = 0;
                        Percent = 0; //процентная составляющая в платеже по кредиту
                        PercentAll = 0;
                        Principal = 0; //сумма, идущая в погашение основного долга в аннуитетном платеже
                        PrincipalAll = 0; // общая сумма, направленная в погашение основного долга
                        
                        String strCredOut = NewCred.id_client + " " + NewCred.size + " " + NewCred.percent + " " + NewCred.first_pay_size + " " + NewCred.typeOfPayment + " " + NewCred.term + " " + 
                        " " + NewCred.termOfFirstPayment;
                        //преобразуем строку с датой в обьект календарь
                        String input = NewCred.termOfFirstPayment;
                        // Setting to a different date 
                        Calendar calndr1 = (Calendar)Calendar.getInstance(); 
                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                        Date date = null;
                        try { date = sdf.parse(input); } 
                        catch (ParseException ex) {Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);}
                        calndr1.setTime(date); // установили дату
                        Date dt = calndr1.getTime(); 
                        String formattedDate = new SimpleDateFormat("dd.MM.yyyy").format(dt);
                                               
                        if (NewCred.typeOfPayment == 'А') //если тип платежа аннуитетный
                        /* (ежемесячная сумма выплаты не меняется, но с каждым месяцем в этой сумме содержится больше выплат основного долга по кредиту и меньше процентных выплат)
                            Вычисление суммы аннуитетного платежа производится по формуле:
                            Payment = S*(p+(p/((1+p)^N-1)) , где p – это нормализованная месячная процентная ставка (p = P / 100 / 12).
                            Для S = 1200000, P = 17 и N = 36 сумма аннуитетного платежа равна 42783.27.
                            Для вычисления процентной составляющей в платеже можно воспользоваться следующей формулой: Percent = p * Rem, где Rem – остаток задолженности по кредиту. 
                            Тогда сумма, идущая в погашение основного долга, равна Principal = Payment - Percent.*/
                        {   p = P/100/12; //нормализованная месячная процентная ставка (p = P /100 / 12).
                            for (int i=1; i<=N; i++) //цикл по кол-ву ежемесячных платежей
                            {   Payment = getRound(S*(p+(p/(Math.pow((1+p),N)-1))));
                                PaymentAll = getRound(PaymentAll + Payment);
                                Rem=getRound(S-PrincipalAll);
                                Percent = getRound(p * Rem);
                                PercentAll = getRound(PercentAll + Percent);
                                Principal = getRound(Payment - Percent); //сумма, идущая в погашение основного долга, равна Principal = Payment - Percent.
                                PrincipalAll = PrincipalAll + Principal;
                                strCredOut = strCredOut + ' '+ formattedDate + ' ' + Payment + ' ' + Percent + ' ' + Principal;
                                if (i==N) strCredOut = strCredOut + ' ' + PaymentAll + ' ' + PercentAll;
                                calndr1.add(Calendar.MONTH, 1); //дата следующего платежа по кредиту
                                dt = calndr1.getTime(); 
                                formattedDate = new SimpleDateFormat("dd.MM.yyyy").format(dt);
                            }     
                        }
                        else if (NewCred.typeOfPayment == 'Д') //если тип платежа дифференцированный
                        {   /*Схема оплаты по дифференцированному платежу выглядит следующим образом:
                            Условие: сумма кредита — 300 000 рублей, срок кредита — 6 месяцев, ставка по кредиту — 20%. Погашение кредита осуществляется дифференцированными платежами:
                            1. Ежемесячный платеж по основному долгу = сумма кредита / количество платежных периодов в течение всего срока кредита.
                            300 000 рублей / 6 месяцев = 50 000 рублей.
                            2. Ежемесячная сумма начисленных процентов по кредиту Percent = p * Rem, где Rem – остаток задолженности по кредиту. 
                            p – это нормализованная месячная процентная ставка (p = P / 100 / 12).
                            3. Ежемесячный платеж по кредиту = ежемесячный платеж по основному долгу + ежемесячная сумма начисленных процентов по кредиту.
                            */
                            strCredOut = NewCred.id_client + " " + NewCred.size + " " + NewCred.percent + " " + NewCred.first_pay_size + " " + NewCred.typeOfPayment + " " + NewCred.term + " " + 
                        " " + NewCred.termOfFirstPayment;
                            Principal = getRound(S/N); //Ежемесячный платеж по основному долгу
                            p = P / 100 / 12; //нормализованная месячная процентная ставка (p = P / 100 / 12).
                            for (int i=1; i<=N; i++) //цикл по кол-ву ежемесячных платежей
                            {
                                Rem=getRound(S-PrincipalAll); //если первый платеж , то остаток задолженности равен сумме кредита
                                Percent = getRound(p * Rem);
                                PercentAll = getRound(PercentAll + Percent);
                                PrincipalAll = Principal + PrincipalAll;                            
                                Payment = getRound(Principal + Percent); //Ежемесячный платеж по кредиту
                                PaymentAll = getRound(PaymentAll + Payment);
                                strCredOut = strCredOut + ' '+ formattedDate + ' ' + Payment + ' ' + Percent + ' ' + Principal;
                                if (i==N) strCredOut = strCredOut + ' ' + PaymentAll + ' ' + PercentAll;
                                calndr1.add(Calendar.MONTH, 1); //дата следующего платежа по кредиту
                                dt = calndr1.getTime(); 
                                formattedDate = new SimpleDateFormat("dd.MM.yyyy").format(dt);
                            }     
                        }
                        strCredOutlist.add(strCredOut);//Записываем в list результаты расчета
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
          
          public static double getRound(double cir) // округляет до сотых
          {
             return (new BigDecimal(cir).setScale(2, RoundingMode.HALF_UP).doubleValue()); 
          }
  }

                              
    
     //----------------------------Model End----------------------------------------
         