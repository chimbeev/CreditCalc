package credcalc2;


import java.io.IOException;
import java.text.ParseException;
import java.util.List;


//---------------------------Controller Begin ------------------------------

public class Controller //класс контроллер - приём запроса от пользователя;анализ запроса; выбор следующего действия системы, соответственно результатам анализа 
//(например, передача запроса другим элементам системы).
//Отправляем на HTTPServer get запрос с параметрами кредита и получаем результаты расчета кредита

{
    int id_client;
    int size;
    int percent;
    int first_pay_size;
    char typeOfPayment;
    int term;
    String termOfFirstPayment;

    
    public static void main(String[] args) throws IOException, ParseException, Exception
       {
        
            List<String> dataListIn = View.ReadCrd2(); //производим чтение из файла данных по кредитам и получаем данные в виде list

            //берем 10 кредитов и отправляем их на сервер для расчета 
            String credStr = "";
            HTTPServer3.main(args); //запускаем http сервер
            for (int i = 10;i<21;i++)
            {   
                credStr = dataListIn.get(i);
                View.GetCrdFromSrv(credStr); //отппралвяем на сервер строку с параметрами кредита
            }
        
       }       
           
        
}    

   

//-------------------------------------Controller End------------------------------------------------------------


