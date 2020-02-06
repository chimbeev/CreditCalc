package credcalc2;


import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

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

    
    public static void main(String[] args) throws IOException, ParseException, Exception
    {
        List<String> dataListIn = View.ReadCrd2(); //производим чтение из файла данных по кредитам и получаем данные в виде list
        //Для ускорения расчета кредитов применяем мнопоточность.
        int lList = dataListIn.size(); //длина листа
        int NumElem = lList/1000; //делим лист на 1000 листов
        int endCount = 0;
        View.ClearJson();//Очищаем файл json
        System.out.println("Ведется расчет кредитов ...");
        for (int i = 0;i<1000;i++) //Запускаем 1000 потоков. В этом случае выполнение расчетов идет около 8 минут.
        {   
            endCount = (i+1)*NumElem;
            if (endCount > lList) endCount = lList-1;
            List<String> subList = dataListIn.subList(i*NumElem, endCount);
            Callable task = () -> {return Model.getCrd(subList);};
            FutureTask <String> future = new FutureTask<>(task);
            new Thread(future).start();
            //Записываем результат расчета в json файл путем вызова процедуры View.AddWriteJson() из процедуры Model.getCrd
        }
        System.out.println("Результаты записаны в json файл");
        
    }
}    
//-------------------------------------Controller End------------------------------------------------------------


