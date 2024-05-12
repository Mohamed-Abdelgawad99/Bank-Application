package bankingAppProject;

import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

public class ATM {

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);

        Bank theBank = new Bank("Bank of Austria");

        // add a user to the bank

        User aUser = theBank.addUser("Mohamed"
        , "Aly","1234" );

        Account newAccount = new Account("Checking", aUser,
                theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;

        while(true){
            // stay in the login prompt until successful log in
            curUser = ATM.mainMenuPrompt(theBank, sc);
            // stay in main menu until user quits
            ATM.printUserMenu(curUser, sc);
        }


    }

    /**
     *
     * @param theBank
     * @param sc
     * @return
     */
    public static User mainMenuPrompt(Bank theBank, Scanner sc) {

        String userID;
        String pin;
        User authUser;

        do{
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter the user iD: ");
            userID = sc.nextLine();
            System.out.print("Enter pin: ");
            pin =sc.nextLine();

            //make the user object correspond to the user id and pin
            authUser = theBank.userLogin(userID,pin);
            if (authUser == null){
                System.out.println("Incorrect user ID/pin " +
                        "combination. Please Try again!");
            }

        }while(authUser==null);
        // continue looping until successful login

        return authUser;
    }

    /**
     *
     * @param curUser
     * @param sc
     */
    public static void printUserMenu(@NotNull User curUser, Scanner sc) {

        //print a summary of the user's account

        curUser.printAccountSummary();

        int choice;

        do{
            System.out.printf("Welcome %s, what would " +
                    "you like to do?\n", curUser.getFirstName());
            System.out.println("  1) Show account transaction " + "history");
            System.out.println("  2) Withdraw");
            System.out.println("  3) Deposit");
            System.out.println("  4) Transfer");
            System.out.println("  5) Quit"+"\n");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            if(choice <1 || choice > 5){
                System.out.println("Invalid choice. Please choose 1-5");
            }
        } while (choice <1 || choice >5);

        switch (choice){

            case 1:
                ATM.showTransHistory(curUser, sc);
                break;
            case 2:
                ATM.withdrawFunds(curUser,sc);
                break;
            case 3:
                ATM.depositMoney(curUser,sc);
                break;
            case 4:
                ATM.transferMoney(curUser,sc);
                break;
            case 5:
                sc.nextLine();
                break;
        }

        // redisplay this menu unless the user wants to quit
        if (choice != 5){
            ATM.printUserMenu(curUser, sc);
        }
    }

    /**
     *
     * @param theUser
     * @param sc
     */
    public static void showTransHistory(User theUser,
                                        Scanner sc){
        int theAccountNumber;
        //get the account that we will show its history
        do{
            System.out.printf("Enter the number (1-%d) "
            +"of the account whose transactions " +
                    "you want to see: ",theUser.numAccounts());
            theAccountNumber = sc.nextInt()-1;
            if (theAccountNumber<0 ||
                    theAccountNumber >= theUser.numAccounts()){
                System.out.println("Invalid account." +
                        "Please try again.");
            }

        } while (theAccountNumber<0 ||
        theAccountNumber>= theUser.numAccounts());

        //print transaction history
        theUser.printTransactionHistory(theAccountNumber);
    }

    /**
     *
     * @param theUser
     * @param sc
     */
    public static void transferMoney(User theUser, Scanner sc){

        int fromAcct;
        int toAcct;
        double amount;
        double acctBalance;
        // get the account to transfer from
        do{
            System.out.printf("Enter the account " +
                    "you want to transfer " +
                    "from (1-%d): ", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if (fromAcct <0 ||
                    fromAcct >= theUser.numAccounts()){
                System.out.println("Invalid account number. " +
                        "please try again!");
            }
        } while (fromAcct <0 ||
                fromAcct >= theUser.numAccounts());
        acctBalance = theUser.getAccountBalance(fromAcct);

        // get the account to transfer to
        do{
            System.out.printf("Enter the account " +
                    "you want to transfer " +
                    "to (1-%d): ", theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if (toAcct <0 ||
                    toAcct >= theUser.numAccounts()){
                System.out.println("Invalid account number " +
                        "please try again!");
            }
        } while (toAcct <0 ||
                toAcct >= theUser.numAccounts());

        //get the amount to transfer
        do{
            System.out.printf("Enter the amount to transfer " +
                    "(max $%.2f): $", acctBalance);
            amount = sc.nextDouble();

            if (amount < 0 ){
                System.out.println("Invalid amount. " +
                        "Please try again!");
            }else if (amount > acctBalance){
                System.out.printf("Amount shouldn't be " +
                        "greater than account balance of " +
                        "$%.2f.\n", acctBalance);
            }
        } while (amount < 0 || amount > acctBalance);

        //doing the transfer
        theUser.addAcctTransaction(fromAcct, -1*amount,
                String.format("Transfer to account %s",
                        theUser.getAcctUUID(toAcct)));
        theUser.addAcctTransaction(toAcct, 1*amount,
                String.format("Transfer from account %s",
                        theUser.getAcctUUID(fromAcct)));
    }

    public static void withdrawFunds(User curUser, Scanner sc){

        int fromAcct;
        double amount;
        double accBalance;
        String memo;

        // check the account we will withdraw from
        do{
            System.out.printf("Enter the number of the " +
                    "account to with draw from (1-%d): "
            , curUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if (fromAcct < 0 ||
                    fromAcct >= curUser.numAccounts()){
                System.out.println("Invalid account. " +
                        "Please try again!");
            }
        } while (fromAcct <0 ||
        fromAcct >= curUser.numAccounts());

        accBalance = curUser.getAccountBalance(fromAcct);

        do{
            System.out.printf("Enter the amount you want " +
                    "to withdraw (max $%.2f): $", accBalance);
            amount = sc.nextDouble();

            if (amount < 0){
                System.out.println("Invalid amount. " +
                        "Please try again!");
            } else if (amount > accBalance){
                System.out.printf("Amount shouldn't " +
                        "be greater than the account's " +
                        "balance of $%.2f.\n", accBalance);
            }
        }while (amount < 0 ||
                amount > accBalance);

        curUser.addAcctTransaction(fromAcct, -1*amount
                , String.format("Withdraw from account %s",
                        curUser.getAcctUUID(fromAcct)));
    }

    public static void depositMoney(User curUser, Scanner sc){

        int toAcct;
        double amount;
        double accBalance;
        String memo;

        // check the account we will withdraw from
        do{
            System.out.printf("Enter the number of the " +
                            "account to with deposit in (1-%d): "
                    , curUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if (toAcct < 0 ||
                    toAcct >= curUser.numAccounts()){
                System.out.println("Invalid account. " +
                        "Please try again!");
            }
        } while (toAcct <0 ||
                toAcct >= curUser.numAccounts());

        accBalance = curUser.getAccountBalance(toAcct);

        do{
            System.out.print("Enter the amount you want " +
                    "to deposit: $");
            amount = sc.nextDouble();

            if (amount < 0){
                System.out.println("Invalid amount. " +
                        "Please try again!");
            }
        }while (amount < 0);

        curUser.addAcctTransaction(toAcct, amount
                , String.format("Deposit into account %s",
                        curUser.getAcctUUID(toAcct)));
    }
}
