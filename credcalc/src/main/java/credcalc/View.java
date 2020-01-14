package credcalc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;
import java.util.stream.*;

//----------------------------View Begin --------------------------------------
public class View //Представление получает данные из файла и отправляет в Model, затем забирает из Model и выводит в файл и на экран
     {    
          public static Stream<String> ReadCrd() throws IOException//считывает из файла входные данные по кредитам
          
          {     List<String> CredOut = Arrays.asList(); //
                try (Stream<String> stream = Files.lines(Paths.get("input3.csv"))) {
                    stream.forEach( line -> {
                            Model.Credit NewCred = new Model.Credit(); 
                            String [] StrInArray = line.split(";");
                            NewCred.id_client = Integer.parseInt(StrInArray[0]);
                            NewCred.size = Integer.parseInt(StrInArray[1]);
                            NewCred.percent = Integer.parseInt(StrInArray[2]);
                            NewCred.first_pay_size = Integer.parseInt(StrInArray[3]);
                            NewCred.typeOfPayment= StrInArray[4].charAt(0);
                            NewCred.term = Integer.parseInt(StrInArray[5]);
                            NewCred.termOfFirstPayment = StrInArray[6];
                    });
                    /*-----
                    String fileName = "c://lines.txt";
		List<String> list = new ArrayList<>();

		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

			//1. filter line 3
			//2. convert all content to upper case
			//3. convert it into a List
			list = stream
					.filter(line -> !line.startsWith("line3"))
					.map(String::toUpperCase)
					.collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}

		list.forEach(System.out::println);
                    */

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ENGLISH);
                    LocalDate date = LocalDate.parse(NewCred.termOfFirstPayment, formatter);
                    System.out.println(date); // 2010-01-02
                
                try (Stream<String> stream = Files.lines(Paths.get("input3.csv"))) {

			stream.forEach(System.out::println);

		} catch (IOException e) {
			e.printStackTrace();
		}
                      
              
               return lineStream;
               
          }
     }
      //----------------------------View End----------------------------------------