public class YearlyReportTransaction {
    String name;
    boolean isExpence;
    int amount;

    YearlyReportTransaction(String[] lineContents) {
        name = lineContents[0];
        amount = Integer.parseInt(lineContents[1]);
        isExpence = Boolean.parseBoolean(lineContents[2]);
    }
}
