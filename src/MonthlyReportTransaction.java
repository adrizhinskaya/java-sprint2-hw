public class MonthlyReportTransaction {
    String name;
    boolean isExpence;
    int quantity;
    int unitPrice;
    int amount;

    MonthlyReportTransaction(String[] lineContents) {
        name = lineContents[0];
        isExpence = Boolean.parseBoolean(lineContents[1]);
        quantity = Integer.parseInt(lineContents[2]);
        unitPrice = Integer.parseInt(lineContents[3]);
        amount = quantity * unitPrice;
    }
}
