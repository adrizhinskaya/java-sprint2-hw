import java.util.ArrayList;
import java.util.HashMap;

public class MonthlyReport {
    FileReader fileReader = new FileReader();
    HashMap<String, ArrayList<MonthlyReportTransaction>> monthlyTransactionsMap = new HashMap<>();

    void loadFile(String fileName) {
        ArrayList<MonthlyReportTransaction> transactions = new ArrayList<>();
        ArrayList<String> lines = fileReader.readFileContents(fileName); // считывание файла
        if (!lines.isEmpty()) {
            for (int i = 1; i < lines.size(); i++) {
                String[] lineContents = lines.get(i).split(","); // массив строк(данных) из строки
                MonthlyReportTransaction transaction = new MonthlyReportTransaction(lineContents); // перевод данных строки в объект Transaction
                transactions.add(transaction);
            }
            monthlyTransactionsMap.put(fileName.substring(6,8), transactions);
            System.out.println("Файл успешно считан");
        } else {
            System.out.println("Отчёт файла " + fileName + " НЕ считан из файла. Пожалуйста, считайте данные.");
        }
    }

    void printAllMonthsReport() {
        for (String monthName : monthlyTransactionsMap.keySet()) {
            System.out.println(monthName);
            printMaxProfitableProductName(monthName);
            printMaxExpense(monthName);
        }
    }

    private void printMaxProfitableProductName(String monthName) {
        HashMap<String, Integer> productSummary = makeIncomeSummaryMap(monthName);
        String maxTitle = findMaxProfitableProductName(productSummary);
        System.out.println("Самый прибыльный товар - " + maxTitle + ". Сумма прибыли - " + productSummary.get((maxTitle)));
    }

    private HashMap<String, Integer> makeIncomeSummaryMap(String monthName) {
        HashMap<String, Integer> productSummary = new HashMap<>();
        ArrayList<MonthlyReportTransaction> currentMonthTransactions = monthlyTransactionsMap.get(monthName);
        for (MonthlyReportTransaction transaction : currentMonthTransactions) {
            int lastProductTransaction = productSummary.getOrDefault(transaction.name, 0);
            if (transaction.isExpence) {
                productSummary.put(transaction.name, lastProductTransaction - transaction.amount);
            } else {
                productSummary.put(transaction.name, lastProductTransaction + transaction.amount);
            }
        }
        return productSummary;
    }

    private String findMaxProfitableProductName(HashMap<String, Integer> productSummary) {
        String maxProductName = null;
        for (String productName : productSummary.keySet()) {
            if (maxProductName == null) {
                maxProductName = productName;
            } else if (productSummary.get(maxProductName) < productSummary.get(productName)) {
                maxProductName = productName;
            }
        }
        return maxProductName;
    }

    private void printMaxExpense(String monthName) {
        MonthlyReportTransaction maxExpenceTransaction = findMaxExpenceTransaction(monthName);
        System.out.println("Самая большая трата - " + maxExpenceTransaction.name + ". Сумма расхода - " + maxExpenceTransaction.amount);
    }

    private MonthlyReportTransaction findMaxExpenceTransaction(String monthName) {
        MonthlyReportTransaction maxExpenceTransaction = null;
        ArrayList<MonthlyReportTransaction> currentMonthTransactions = monthlyTransactionsMap.get(monthName);

        for (MonthlyReportTransaction transaction : currentMonthTransactions) {
            if (maxExpenceTransaction == null) {
                maxExpenceTransaction = transaction;
            } else if (transaction.isExpence && transaction.amount > maxExpenceTransaction.amount) {
                maxExpenceTransaction = transaction;
            }
        }
        return maxExpenceTransaction;
    }

    HashMap<String, HashMap<Boolean, Integer>> createExpenceAndIncomeMonthlyMap() {
        HashMap<String, HashMap<Boolean, Integer>> informationByMonthlyReport = new HashMap<>();
        for (String monthName : monthlyTransactionsMap.keySet()) {
            for (MonthlyReportTransaction transaction : monthlyTransactionsMap.get(monthName)) {
                if (!informationByMonthlyReport.containsKey(monthName)) {
                    informationByMonthlyReport.put(monthName, new HashMap<>());
                }
                HashMap<Boolean, Integer> isExpenceToAmount = informationByMonthlyReport.get(monthName);
                isExpenceToAmount.put(transaction.isExpence, isExpenceToAmount.getOrDefault(transaction.isExpence, 0) + transaction.amount);
            }
        }
        return  informationByMonthlyReport;
    }
}
