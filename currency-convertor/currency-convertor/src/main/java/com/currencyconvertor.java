package com;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

public class currencyconvertor {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Type currency to convert from");
        String convertFrom = scanner.nextLine();
        System.out.println("Type currency to convert to");
        String convertTo = scanner.nextLine();
        System.out.println("Type quantity to convert");
        BigDecimal quantity = scanner.nextBigDecimal();

        String urlString = "https://www.frankfurter.app/latest?from=" + convertFrom.toUpperCase();


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlString)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String stringResponse = response.body().string();
        JSONObject jsonObject = new JSONObject(stringResponse);
        JSONObject ratesObject = jsonObject.getJSONObject("rates");
        BigDecimal rate = ratesObject.getBigDecimal(convertTo.toUpperCase());

        BigDecimal result = rate.multiply(quantity);
        System.out.println(result);

    }
}
