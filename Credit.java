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




public class Credit //класс контроллер - приём запроса от пользователя;анализ запроса; выбор следующего действия системы, соответственно результатам анализа 
//(например, передача запроса другим элементам системы).
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
           StrIn = sc.nextLine(); //считываем целиком строку из файла
           sc.close();
           Credit NewCred = new Credit(); 
           String [] StrInArray = StrIn.split(";");
           NewCred.id_client = Integer.parseInt(StrInArray[0]);
           NewCred.size = Integer.parseInt(StrInArray[1]); // размер кредита
           NewCred.percent = Integer.parseInt(StrInArray[2]); //процент по кредиту
           NewCred.first_pay_size = Integer.parseInt(StrInArray[3]); //размер первоначального взноса
           NewCred.typeOfPayment= StrInArray[4].charAt(0); //вид платежа - аннуитетный или дифференцированный
           NewCred.term = Integer.parseInt(StrInArray[5]); //срок кредита в месяцах
           NewCred.termOfFirstPayment = StrInArray[6]; // дата первого платежа
           //        
           //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ENGLISH);
           //LocalDate date = LocalDate.parse(NewCred.termOfFirstPayment, formatter);
           //System.out.println(date); // 2010-01-02
           
       }    

       public static void prn(int nc) //Для вывода на экран
       {
            System.out.println(nc);

       }
       public static void prn(Date nc) //Для вывода на экран
       {
            System.out.println(nc);

       }

       public class Model //Мodel - бизнес логика. В него передаем как обьект данные по кредиту для проведения расчета

       {
           
          public void getCrd(Credit crd)
          {
               //получив данные по кредиту производим расчет графика платежей
               
              
               int S=crd.size;//сумма кредита
               int P=crd.percent; //процент по кредиту, годовых
               int N=crd.term; //срок кредита в месяцах
               double Rem = S; //остаток задолженности по кредиту
               int numPay = 0; //номер платежа по кредиту;
               double Payment = 0; //сумма аннуитетного платежа
               double Percent = 0; //процентная составляющая в платеже по кредиту
               double Principal = 0; //сумма, идущая в погашение основного долга в аннуитетном платеже
               double PrincipalAll = 0; // общая сумма, направленная в погашение основного долга

               if (crd.typeOfPayment == 'А') //если тип платежа аннуитетный
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
                         Principal = Payment - Percent;
                         PrincipalAll = PrincipalAll + Principal;
                    }     
               }
               else if (crd.typeOfPayment == 'Д') //если тип платежа дифференцированный
               {
               /*Схема оплаты по дифференцированному платежу выглядит следующим образом:
               Условие: сумма кредита — 300 000 рублей, срок кредита — 6 месяцев, ставка по кредиту — 20%. Погашение кредита осуществляется дифференцированными платежами:
               1. Ежемесячный платеж по основному долгу = сумма кредита / количество платежных периодов в течение всего срока кредита.
               300 000 рублей / 6 месяцев = 50 000 рублей.
               2. Ежемесячная сумма начисленных процентов по кредиту = остаток основного долга в текущем периоде * годовая процентная ставка * число дней в платежном 
               периоде (от 28 до 31) / число дней в году (365 или 366).
               3. Ежемесячный платеж по кредиту = ежемесячный платеж по основному долгу + ежемесячная сумма начисленных процентов по кредиту.
               */
               }
          }
     }
}
