package bankingAppProject;

import java.util.ArrayList;

public class Account {

    private String name;
    private String uuid;
    private User holder;
    private ArrayList<Transaction> transactions;

    /**
     *
     * @param name
     * @param holder
     * @param theBank
     */
    public Account (String name, User holder, Bank theBank){

        this.name = name;
        this.holder = holder;

        this.uuid = theBank.getNewAccountUUID();
        // creating an empthy list of transactions
        this.transactions = new ArrayList<Transaction>();

    }

    /**
     *
     * @return
     */
    public String getUUID() {
        return this.uuid;
    }

    /**
     * Get the summary line for the account
     * @return
     */

    public String getSummaryLine() {

        //get the balance
        double balance = this.getBalance();

        // format the summary line depending on if the
        // balance is negative

        if (balance>= 0){
            return String.format("%s : $%.02f : %s", this.uuid,
                    balance, this.name);
        }
        else {
            return String.format("%s : $(%.02f) : %s", this.uuid,
                    balance, this.name);
        }


    }

    /**
     *
     * @return
     */
    public double getBalance(){
        double balance =0;
        for (Transaction t : this.transactions){
            balance += t.getAmount();
        }
        return balance;
    }

    /**
     *
     */
    public void printTransactionHistory() {
        System.out.printf("\n Transaction history for the " +
                "account %s:\n", this.uuid);
        for (int t = this.transactions.size()-1 ;t>=0;t--){
            System.out.printf(
                    this.transactions.get(t).getSummaryLine()+"\n");
        }
        System.out.println();
    }

    public void addTransaction(double amount, String
                               transferMessage){
        // create a new transaction object and add it to out list
        Transaction newTrans = new Transaction(amount,
                transferMessage, this);
        this.transactions.add(newTrans);
    }
}
