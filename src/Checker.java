import java.util.HashMap;

public class Checker {
    boolean isCheckSuccessful = true;
    MonthlyReport monthlyReport;
    YearlyReport yearlyReport;

    public Checker(MonthlyReport monthlyReport, YearlyReport yearlyReport) {
        this.monthlyReport = monthlyReport;
        this.yearlyReport = yearlyReport;
    }

    public boolean check() {
        HashMap<String, HashMap<Boolean, Integer>> informationByYearReport = createExpenceAndIncomeYearlyMap();
        HashMap<String, HashMap<Boolean, Integer>> informationByMonthlyReport = createExpenceAndIncomeMonthlyMap();

        for (String month : informationByYearReport.keySet()) {
            HashMap<Boolean, Integer> isExpenceToAmountByYear = informationByYearReport.get(month);
            HashMap<Boolean, Integer> isExpenceToAmountByMonth = informationByMonthlyReport.get(month);

            if(isExpenceToAmountByMonth == null) {
                System.out.println("Месяц " + month + " есть в годовом отчёте, но нет его месячного отчёта");
                isCheckSuccessful = false;
                continue;
            }
            findDifferencesAndPrint(isExpenceToAmountByYear, isExpenceToAmountByMonth, month);
        }
        return isCheckSuccessful;
    }

    private HashMap<String, HashMap<Boolean, Integer>> createExpenceAndIncomeYearlyMap() {
        HashMap<String, HashMap<Boolean, Integer>> informationByYearReport = new HashMap<>();
        for (Transaction transaction : yearlyReport.transactions) {
            if(!informationByYearReport.containsKey(transaction.name)) {
                informationByYearReport.put(transaction.name, new HashMap<>());
            }
            HashMap<Boolean, Integer> isExpenceToAmount = informationByYearReport.get(transaction.name);
            isExpenceToAmount.put(transaction.isExpence, isExpenceToAmount.getOrDefault(transaction.isExpence,0) + transaction.amount);

        }
        return informationByYearReport;
    }

    private HashMap<String, HashMap<Boolean, Integer>> createExpenceAndIncomeMonthlyMap() {
        HashMap<String, HashMap<Boolean, Integer>> informationByMonthlyReport = new HashMap<>();
        for (String monthName : monthlyReport.monthlyTransactionsMap.keySet()) {
            for (Transaction transaction : monthlyReport.monthlyTransactionsMap.get(monthName)) {
                if (!informationByMonthlyReport.containsKey(monthName)) {
                    informationByMonthlyReport.put(monthName, new HashMap<>());
                }
                HashMap<Boolean, Integer> isExpenceToAmount = informationByMonthlyReport.get(monthName);
                isExpenceToAmount.put(transaction.isExpence, isExpenceToAmount.getOrDefault(transaction.isExpence, 0) + transaction.amount);
            }
        }
        return  informationByMonthlyReport;
    }

    private void findDifferencesAndPrint(HashMap<Boolean, Integer> isExpenceToAmountByYear, HashMap<Boolean, Integer> isExpenceToAmountByMonth, String month) {
        for (Boolean isExpence : isExpenceToAmountByYear.keySet()) {
            int amountByYear = isExpenceToAmountByYear.get((isExpence));
            int amountByMonth = isExpenceToAmountByMonth.getOrDefault(isExpence, 0);
            if(amountByYear != amountByMonth) {
                String operation = isExpence ? "расход" : "доход";
                System.out.println("По годовому отчёту за " + month + " месяц " + operation + " составил - " + amountByYear
                        + ".\nА по месячному отчёту " + operation + " составил - " + amountByMonth + "\n");
                isCheckSuccessful = false;
            }
        }
    }
}
