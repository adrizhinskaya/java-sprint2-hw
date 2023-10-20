import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int exitCommand = 1111;
        Scanner scanner = new Scanner(System.in);
        MonthlyReport monthlyReport = new MonthlyReport();
        YearlyReport yearlyReport = new YearlyReport();

        while (true) {
            printMenu();
            int userInput = scanner.nextInt();

            if (userInput == 1) {
                monthlyReport.loadFile("m.202101.csv");
                monthlyReport.loadFile("m.202102.csv");
                monthlyReport.loadFile("m.202103.csv");
            } else if (userInput == 2) {
                yearlyReport.loadFile("y.2021.csv");
            } else if (userInput == 3) {
                String resultString;
                if(yearlyReport.transactions.isEmpty() || monthlyReport.monthlyTransactionsMap.isEmpty()) {
                    System.out.println("Отчёты НЕ считаны из файла. Пожалуйста, считайте данные.");
                    continue;
                }
                Checker checker = new Checker(monthlyReport, yearlyReport);
                resultString = checker.check() ? "Проверка прошла успешно!!!" : "Обнаружены несоответствия!";
                System.out.println(resultString);
            } else if (userInput == 4) {
                if(monthlyReport.monthlyTransactionsMap.isEmpty()) {
                    System.out.println("Месячные отчёты НЕ считаны из файла. Пожалуйста, считайте данные.");
                    continue;
                }
                monthlyReport.printAllMonthsReport();
            } else if (userInput == 5) {
                if(yearlyReport.transactions.isEmpty()) {
                    System.out.println("Годовой отчёт НЕ считан из файла. Пожалуйста, считайте данные.");
                    continue;
                }
                yearlyReport.printYearlyReport();
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

