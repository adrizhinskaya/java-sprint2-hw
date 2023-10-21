import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int exitCommand = 1111;
        Scanner scanner = new Scanner(System.in);
        ReportsService reportsService = new ReportsService();

        while (true) {
            printMenu();
            int userInput = scanner.nextInt();

            if (userInput == 1) {
                reportsService.readMonthlyReports("m.202101.csv");
                reportsService.readMonthlyReports("m.202102.csv");
                reportsService.readMonthlyReports("m.202103.csv");
            } else if (userInput == 2) {
                reportsService.readYearReport("y.2021.csv");
            } else if (userInput == 3) {
                reportsService.printCheckReportsResult();
            } else if (userInput == 4) {
                reportsService.printMonthlyReportsInfo();
            } else if (userInput == 5) {
                reportsService.printYearReportInfo();
            } else if (userInput == exitCommand) {
                break;
            } else {
                System.out.println("Такой команды нет. Попробуйте ещё раз.");
            }
        }
    }

    public static void printMenu() {
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию обо всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте");
    }
}

