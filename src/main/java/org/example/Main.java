package org.example;

import java.util.Scanner;

public class Main {
    static final String URL = "jdbc:postgresql://localhost:5432/culture_fest";
    static final String USER = "user";
    static final String PASS = "pass";
    static final int DEFAULT_ENTRIES_GENERATED = 10;

    public static void main(String[] args) {
        EmployeeService employeeService = new EmployeeService(URL, USER, PASS);
        Scanner scan = new Scanner(System.in);
        String[] inp = scan.nextLine().trim().toLowerCase().split(" ");
        while (!inp[0].equals("close")) {
            switch (inp[0]) {
                case "create" -> employeeService.createTable();
                case "fill" -> {
                    try {
                        employeeService.fillData(Integer.parseInt(inp[1]));
                        System.out.printf("%d data entries generated%n", Integer.parseInt(inp[1]));
                    } catch (IndexOutOfBoundsException e) {
                        employeeService.fillData(DEFAULT_ENTRIES_GENERATED);
                        System.out.printf("%d data entries generated%n", DEFAULT_ENTRIES_GENERATED);
                    } catch (NumberFormatException e) {
                        System.out.println("Amount of entries should be an integer");
                    }
                }
                case "findbetween" -> {
                    try {
                        System.out.println(employeeService.findBetween(inp[1], inp[2]));
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Parameters \"fromDate\" and \"toDate\" required");
                    }
                }
                case "findbyid" -> {
                    try {
                        System.out.println(employeeService.findById(Integer.parseInt(inp[1])));
                    } catch(IndexOutOfBoundsException e) {
                        System.out.println("Parameter \"id\" required");
                    } catch (NumberFormatException e) {
                        System.out.println("ID should be a number");
                    }
                }
                case "groupbyname" -> System.out.println(employeeService.groupByName());
                case "list" -> System.out.println(employeeService.list());
                default -> printHelp();
            }
            inp = scan.nextLine().trim().toLowerCase().split(" ");
        }
        employeeService.disconnect();
    }

    private static void printHelp() {
        System.out.println(
                "Usage: \n" +
                        "\"close\" to exit program\n" +
                        "\"create\" to create or replace Employee table\n" +
                        "\"fill $entries\" to fill table with <entries> amount of gibberish entries; " +
                        "default " + DEFAULT_ENTRIES_GENERATED + "\n" +
                        "\"findBetween $fromDate $toDate\" to get list of employees with birth dates" +
                        "from <fromDate> to <toDate>, formatted as YYYY-MM-DD\n" +
                        "\"findById $id\" to get an employee by their <id>\n" +
                        "\"groupByName\" to get list of employees grouped by their names\n" +
                        "\"list\" to list all entries in table\n"
                );
    }
}