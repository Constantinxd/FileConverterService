package ru.vyatsu.koscheev;

import ru.vyatsu.koscheev.parsing.JsonToXml;
import ru.vyatsu.koscheev.parsing.XmlToJson;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter service - xmlToJson or jsonToXml:");
        String service = in.nextLine();

        System.out.println("Enter input document path:");
        String inputDoc = in.nextLine();

        System.out.println("Enter output document path:");
        String outputDoc = in.nextLine();

        in.close();

        if (service.equals("xmlToJson"))
            new XmlToJson().convert(inputDoc, outputDoc);
        else if (service.equals("jsonToXml"))
            new JsonToXml().convert(inputDoc, outputDoc);
        else
            System.out.println("Wrong service name");
    }
}
