package bankingAppProject;

import java.util.Date;

public class Transaction {

    private double amount;
    private Date timeStamp;
    private String memo;
    private Account inAccount;

    /**
     *
     * @param amount
     * @param inAccount
     */
    public Transaction (double amount, Account inAccount){
        this.amount = amount;
        this.inAccount = inAccount;
        this.timeStamp = new Date();
        this.memo = "";
    }

    /**
     *
     * @param amount
     * @param memo
     * @param inAccount
     */
    public Transaction (double amount, String memo,
                        Account inAccount){
        // I can call the first constructor and add the new value
        // I want in a separate step
        this(amount, inAccount);
        this.memo = memo;
    }

    /**
     *
     * @return
     */
    public double getAmount(){
        return this.amount;
    }

    /**
     *
     * @return
     */
    public String getSummaryLine() {

        if (this.amount >= 0){
            return String.format("%s : $%.2f : %s : Account Balance $%.2f",
                    this.timeStamp.toString(), this.amount,
            this.memo, this.inAccount.getBalance());
        }
        else{
            return String.format("%s : $(%.2f) : %s",
                        this.timeStamp.toString(), -this.amount,
                        this.memo);
        }
    }
}
