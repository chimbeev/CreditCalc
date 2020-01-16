package credcalc2;


import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.stream.Stream;


//---------------------------Controller Begin ------------------------------

public class Controller //класс контроллер - приём запроса от пользователя;анализ запроса; выбор следующего действия системы, соответственно результатам анализа 
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
        Stream<String> lineStream = View.ReadCrd(); //производим чтение из файла данных по кредитам и получаем данные в виде потока
        Stream<String> lineStreamOut =  Model.getCrd(lineStream); //передаем поток с данными по кредитам получаем результаты
        View.PrintToFile((InputStream) lineStreamOut); //записываем результаты в файл
       }    

   

//-------------------------------------Controller End------------------------------------------------------------

}
