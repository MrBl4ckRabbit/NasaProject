package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        String URL = "https://api.nasa.gov/planetary/apod?api_key=IwBTrFvtgYLw4jQz9yYTCxGfxo3LeVVTtL30UtF7&date=2024-06-19";

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(URL);
        CloseableHttpResponse response = httpclient.execute(httpGet);

//        Scanner scanner = new Scanner(response.getEntity().getContent());
//        if (scanner.hasNextLine()) {
//            System.out.println(scanner.nextLine());
//        } else {
//            System.out.println("Ответ не содержит строк!");
//        }


        ////////////////////////////////////////////////////////////////////////////////////////////////////
//        int statusCode = response.getCode();
//        if (statusCode == 200) {
//            // Обработка успешного ответа
//            Scanner scanner = new Scanner(response.getEntity().getContent());
//            if (scanner.hasNextLine()) {
//                System.out.println(scanner.nextLine());
//            } else {
//                System.out.println("Ответ не содержит строк!");
//            }
//        } else {
//            // Обработка ошибки
//            System.out.println("Ошибка запроса: " + statusCode);
//        }
        ObjectMapper mapper = new ObjectMapper();
        NASAAnswer answer = mapper.readValue(response.getEntity().getContent(), NASAAnswer.class);

        String imgURL = answer.url;
        String[] splittedURL = imgURL.split("/");
        String fileName = splittedURL[splittedURL.length - 1];

        HttpGet imageGet = new HttpGet(imgURL);
        CloseableHttpResponse image = httpclient.execute(imageGet);
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        image.getEntity().writeTo(fileOutputStream);

    }
}
