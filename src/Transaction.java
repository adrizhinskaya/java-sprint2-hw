public class Transaction {
    String name;
    boolean isExpence;
    int quantity;
    int unitPrice;
    int amount;

    Transaction(String[] lineContents, boolean isTransactionMonthly) {
        if(isTransactionMonthly) {
            name = lineContents[0];
            isExpence = Boolean.parseBoolean(lineContents[1]);
            quantity = Integer.parseInt(lineContents[2]);
            unitPrice = Integer.parseInt(lineContents[3]);
            amount  = quantity * unitPrice;
        } else {
            name = lineContents[0];
            amount = Integer.parseInt(lineContents[1]);
            isExpence = Boolean.parseBoolean(lineContents[2]);
        }
    }
}
