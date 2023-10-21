import java.util.HashMap;

public class ReportsService {
    private boolean monthReportLoadFlag = false;
    private boolean yearReportLoadFlag = false;
    private boolean isCheckSuccessful = true;
    MonthlyReport monthlyReport = new MonthlyReport();
    YearlyReport yearlyReport = new YearlyReport();

    void readMonthlyReports(String fileName) {
        monthlyReport.loadFile(fileName);
        monthReportLoadFlag = true;
    }
    void readYearReport(String fileName) {
        yearlyReport.loadFile(fileName);
        yearReportLoadFlag = true;
    }
    void printMonthlyReportsInfo() {
        if(monthReportLoadFlag) {
            monthlyReport.printAllMonthsReport();
        } else {
            System.out.println("Месячные отчёты НЕ считаны из файла. Пожалуйста, считайте данные.");
        }
    }
    void printYearReportInfo() {
        if(yearReportLoadFlag) {
            yearlyReport.printYearlyReport();
        } else {
            System.out.println("Годовой отчёт НЕ считан из файла. Пожалуйста, считайте данные.");
        }
    }
    void printCheckReportsResult() {
        if(monthReportLoadFlag && yearReportLoadFlag) {
            boolean checkResult = checkReports();
            if(checkResult) {
                System.out.println("Проверка прошла успешно!!!");
            } else {
                System.out.println("Обнаружены несоответствия!");
            }
        } else {
            System.out.println("Отчёты НЕ считаны из файлов. Пожалуйста, считайте данные.");
        }
    }
    private boolean checkReports() {
        HashMap<String, HashMap<Boolean, Integer>> informationByYearReport = yearlyReport.createExpenceAndIncomeYearlyMap();
        HashMap<String, HashMap<Boolean, Integer>> informationByMonthlyReport = monthlyReport.createExpenceAndIncomeMonthlyMap();

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
