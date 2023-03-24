package main;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DataValidation {
    public static boolean brandNameValidation(String textfile) {
        // Create a Pattern object
        Pattern r = Pattern.compile("^[a-z-]+$");
        Boolean valuefound = false;

        // Now create matcher object.
        Matcher m = r.matcher(textfile);
        while (m.find()) {
            StdOut.println("Found the brand name : " + m.group(0));
            valuefound = true;
        }
        return valuefound;

    }

    public static boolean priceValidation(String textfile) {
        // 0-9 digits, dot character then 1-4 digits
        String pattern = "^\\d{0,8}(\\.\\d{1,4})?$";
        Boolean valuefound = false;

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(textfile);
        while (m.find()) {
            StdOut.println("Found the price range : " + m.group(0));
            valuefound = true;
        }
        return valuefound;
    }

    public static boolean memoryValidation(String memory) {
        // 2-3 digits
        String pattern = "^\\d{2,3}$";
        Boolean valuefound = false;

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(memory);

        // memory must be a multiplication of 64
        while (m.find())
            if ((Integer.parseInt(memory)) % 64 == 0)
                valuefound = true;

        return valuefound;
    }

    public static void main(String[] args) throws IOException {
        String url = "https://www.bestbuy.ca/en-ca/category/best-buy-mobile/20006";
        Document bestBuy = Jsoup.connect(url).get();
        Scanner input = new Scanner(System.in);
        String txt = bestBuy.body().text();
        String brand;
        String price;
        String memory;

        StdOut.print("\nEnter memory: ");
        memory = input.next();
        while (memoryValidation(memory) == false) {
            StdOut.print("\nInvalid value. Enter memory again: ");
            memory = input.next();
        }
        StdOut.println("Input is valid!");

    }
}
