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


public class Model //Мodel - бизнес логика. В него передаем как поток данные по кредиту для проведения расчета

       {
           
          public static void getCrd(Stream<String> crd)
          {
               //получив данные по кредиту в виде потока производим расчет графика платежей
               Credit NewCred = new Credit();  
               crd
                    .forEach(e -> 
                    {
                        String [] StrInArray = e.split(";"); //разделяем строку по символам ; и записываем в массив
                        NewCred.id_client = Integer.parseInt(StrInArray[0]);
                        NewCred.size = Integer.parseInt(StrInArray[1]); // размер кредита
                        NewCred.percent = Integer.parseInt(StrInArray[2]); //процент по кредиту
                        NewCred.first_pay_size = Integer.parseInt(StrInArray[3]); //размер первоначального взноса
                        NewCred.typeOfPayment= StrInArray[4].charAt(0); //вид платежа - аннуитетный или дифференцированный
                        NewCred.term = Integer.parseInt(StrInArray[5]); //срок кредита в месяцах
                        NewCred.termOfFirstPayment = StrInArray[6]; // дата первого платежа
                        int S=NewCred.size;//сумма кредита
                        int P=NewCred.percent; //процент по кредиту, годовых
                        int N=NewCred.term; //срок кредита в месяцах
                        double Rem = S; //остаток задолженности по кредиту
                        int numPay = 0; //номер платежа по кредиту;
                        double Payment = 0; //сумма аннуитетного платежа
                        double Percent = 0; //процентная составляющая в платеже по кредиту
                        double Principal = 0; //сумма, идущая в погашение основного долга в аннуитетном платеже
                        double PrincipalAll = 0; // общая сумма, направленная в погашение основного долга
                        
                        String strCredOut = NewCred.id_client + " " + NewCred.size + " " + NewCred.percent + " " + NewCred.first_pay_size + " " + NewCred.typeOfPayment + " " + NewCred.term + " " + 
                        " " + NewCred.termOfFirstPayment;
                        //преобразуем строку с датой в обьект календарь
                        String input = NewCred.termOfFirstPayment;
                        DateTimeFormatter f = DateTimeFormatter.ofPattern( "dd MM uuuu" );
                        ZonedDateTime zdt = ZonedDateTime.parse( input , f );
                        
                        if (NewCred.typeOfPayment == 'А') //если тип платежа аннуитетный
                        /* (ежемесячная сумма выплаты не меняется, но с каждым месяцем в этой сумме содержится больше выплат основного долга по кредиту и меньше процентных выплат)
                            Вычисление суммы аннуитетного платежа производится по формуле:
                            Payment = S*(p+(p/((1+N)^N-1)) , где p – это нормализованная месячная процентная ставка (p = P / 100 / 12).
                            Для S = 1200000, P = 17 и N = 36 сумма аннуитетного платежа равна 42783.27.
                            Для вычисления процентной составляющей в платеже можно воспользоваться следующей формулой: Percent = p * Rem, где Rem – остаток задолженности по кредиту. 
                            Тогда сумма, идущая в погашение основного долга, равна Principal = Payment - Percent.
                        */
                        {
                            int p = P / 100 / 12; //нормализованная месячная процентная ставка (p = P / 100 / 12).
                            for (int i=1; i<=N; i++) //цикл по кол-ву ежемесячных платежей
                            {
                                Payment = S*(p+(p/(Math.pow((1+N),N)-1)));
                                Rem=S-PrincipalAll; //если первый платеж , то остаток задолженности равен сумме кредита
                                Percent = p * Rem;
                                Principal = Payment - Percent; //сумма, идущая в погашение основного долга, равна Principal = Payment - Percent.
                                PrincipalAll = PrincipalAll + Principal;
                                strCredOut = strCredOut + ' '+ zdt + ' ' + Payment + ' ' + Percent + ' ' + Principal;
                                zdt.plusMonths(1);
                            }     
                        }
                        else if (NewCred.typeOfPayment == 'Д') //если тип платежа дифференцированный
                        {
                            /*Схема оплаты по дифференцированному платежу выглядит следующим образом:
                            Условие: сумма кредита — 300 000 рублей, срок кредита — 6 месяцев, ставка по кредиту — 20%. Погашение кредита осуществляется дифференцированными платежами:
                            1. Ежемесячный платеж по основному долгу = сумма кредита / количество платежных периодов в течение всего срока кредита.
                            300 000 рублей / 6 месяцев = 50 000 рублей.
                            2. Ежемесячная сумма начисленных процентов по кредиту Percent = p * Rem, где Rem – остаток задолженности по кредиту. 
                            p – это нормализованная месячная процентная ставка (p = P / 100 / 12).
                            3. Ежемесячный платеж по кредиту = ежемесячный платеж по основному долгу + ежемесячная сумма начисленных процентов по кредиту.
                            */
                            strCredOut = NewCred.id_client + " " + NewCred.size + " " + NewCred.percent + " " + NewCred.first_pay_size + " " + NewCred.typeOfPayment + " " + NewCred.term + " " + 
                        " " + NewCred.termOfFirstPayment;
                            Principal = S/N; //Ежемесячный платеж по основному долгу
                            int p = P / 100 / 12; //нормализованная месячная процентная ставка (p = P / 100 / 12).
                            for (int i=1; i<=N; i++) //цикл по кол-ву ежемесячных платежей
                            {
                                Rem=S-PrincipalAll; //если первый платеж , то остаток задолженности равен сумме кредита
                                Percent = p * Rem;
                                PrincipalAll = PrincipalAll + Principal; 
                                Payment = Principal + Percent; //Ежемесячный платеж по кредиту
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
         