package bankingAppProject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {

    private String firstName;
    private String lastName;
    private String uuid;
    private byte pinHash[];
    private ArrayList<Account> accounts;

    public String getFirstName(){
        return this.firstName;
    }

    /**
     *
     * @param firstName
     * @param lastName
     * @param pin
     * @param theBank
     */
    public User(String firstName, String lastName, String pin, Bank theBank){

        this.firstName = firstName;
        this.lastName = lastName;

        //Hashing our pin using MD5 algorithm
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        }catch (NoSuchAlgorithmException e){
            System.out.println("Error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        this.uuid = theBank.getNewUserUUID();

        this.accounts = new ArrayList<Account>();

        System.out.printf("New user %s, %s with ID %s created. \n",
                lastName, firstName, this.uuid);
    }

    /**
     *
     * @param account
     */
    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    /**
     *
     * @return
     */
    public String getUUID() {
        return this.uuid;
    }

    /**
     *
     * @param pin
     * @return
     */
    public boolean validatePin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(pin.getBytes()),
            this.pinHash);
        } catch (NoSuchAlgorithmException e){
            System.out.println("Error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    /**
     * Print summaries for the accounts of this user
     */
    public void printAccountSummary(){
        System.out.printf("\n\n%s's accounts summary\n",
                this.firstName);
        for (int a =0; a < this.accounts.size(); a++){
            System.out.printf("  %d) %s\n", a+1,
                    this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }

    /**
     *
     * @return
     */
    public int numAccounts(){
        return this.accounts.size();
    }

    /**
     *
     * @param theAccountNumber
     */
    public void printTransactionHistory(int theAccountNumber) {

        this.accounts.get(theAccountNumber).printTransactionHistory();
    }

    /**
     *
     * @param fromAcct
     * @return
     */
    public double getAccountBalance(int fromAcct) {
        return this.accounts.get(fromAcct).getBalance();
    }

    /**
     *
     * @param accountIndex
     * @return
     */
    public String getAcctUUID(int accountIndex) {
        return this.accounts.get(accountIndex).getUUID();
    }

    public void addAcctTransaction( int accountIndex,
                                    double amount,
                                    String transferMessage){
        this.accounts.get(accountIndex).addTransaction(amount,
                transferMessage);
    }
}
