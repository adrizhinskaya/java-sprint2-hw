import java.util.ArrayList;
import java.util.HashMap;

public class YearlyReport {
    private String year;
    FileReader fileReader = new FileReader();
    ArrayList<YearlyReportTransaction> transactions = new ArrayList<>();

    void loadFile(String fileName) {
        year = fileName.substring(2,6);
        ArrayList<String> lines = fileReader.readFileContents(fileName);
        if (!lines.isEmpty()) {
            for (int i = 1; i < lines.size(); i++) {
                String[] lineContents = lines.get(i).split(",");
                YearlyReportTransaction transaction = new YearlyReportTransaction(lineContents);
                transactions.add(transaction);
            }
            System.out.println("Файл успешно считан");
        } else {
            System.out.println("Отчёт файла " + fileName + " НЕ считан. Пожалуйста, считайте данные.");
        }
    }

    void printYearlyReport() {
        System.out.println(year);
        printMonthlyProfit();
        System.out.println("Средний расход за все имеющиеся операции в году - " + getAverageAnnualExpenceOrIncome(true));
        System.out.println("Средний доход за все имеющиеся операции в году - " + getAverageAnnualExpenceOrIncome(false));
    }

    private void printMonthlyProfit() {
        HashMap<String, Integer> productSummary = makeIncomeSummaryMap();
        for (String monthName : productSummary.keySet()) {
            System.out.println("Прибыль за " + monthName + "." + year + " равна - " + productSummary.get(monthName));
        }
    }

    private HashMap<String, Integer> makeIncomeSummaryMap() {
        HashMap<String, Integer> productSummary = new HashMap<>();
        for (YearlyReportTransaction transaction : transactions) {
            int lastProductTransaction = productSummary.getOrDefault(transaction.name, 0);
            if (transaction.isExpence) {
                productSummary.put(transaction.name, lastProductTransaction - transaction.amount);
            } else {
                productSummary.put(transaction.name, lastProductTransaction + transaction.amount);
            }
        }
        return productSummary;
    }

    private float getAverageAnnualExpenceOrIncome(boolean IsAverageExpenceNeed) {
        float sum = 0;
        for (YearlyReportTransaction transaction : transactions) {
            if ((IsAverageExpenceNeed && transaction.isExpence) || (!IsAverageExpenceNeed && !transaction.isExpence)) {
                sum += transaction.amount;
            }
        }
        return sum / transactions.size();
    }

    HashMap<String, HashMap<Boolean, Integer>> createExpenceAndIncomeYearlyMap() {
        HashMap<String, HashMap<Boolean, Integer>> informationByYearReport = new HashMap<>();
        for (YearlyReportTransaction transaction : transactions) {
            if(!informationByYearReport.containsKey(transaction.name)) {
                informationByYearReport.put(transaction.name, new HashMap<>());
            }
            HashMap<Boolean, Integer> isExpenceToAmount = informationByYearReport.get(transaction.name);
            isExpenceToAmount.put(transaction.isExpence, isExpenceToAmount.getOrDefault(transaction.isExpence,0) + transaction.amount);

        }
        return informationByYearReport;
    }
}
