package bankingAppProject;

import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accounts;

    /**
     *
     * @param bankName
     */

    public Bank(String bankName) {
        this.name = bankName;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    public String getName(){
        return this.name;
    }

    /**
     *
     * @return
     */
    public String getNewUserUUID(){

        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique ;
        // continue looping until we get a unique ID
        do{
            //generate the number
            uuid = "";
            for (int numIndex = 0; numIndex < len; numIndex++ ){
                uuid+= ((Integer)rng.nextInt(10)).toString();
            }
            // check to make sure its unique
            nonUnique = false;
            for (User u : this.users){
                if (uuid.compareTo(u.getUUID())==0){
                    nonUnique = true;
                    break;
                }
            }

        }while (nonUnique);

        return uuid;

    }

    /**
     *
     * @return
     */
    public String getNewAccountUUID() {
        String uuid;
        Random rng = new Random();
        int len = 10;
        boolean nonUnique ;
        // continue looping until we grt a unique ID
        do{
            //generate the number
            uuid = "";
            for (int numIndex = 0; numIndex < len; numIndex++ ){
                uuid+= ((Integer)rng.nextInt(10)).toString();
            }
            // check to make sure its unique
            nonUnique = false;
            for (Account a : this.accounts){
                if (uuid.compareTo(a.getUUID())==0){
                    nonUnique = true;
                    break;
                }
            }
        }while (nonUnique);
        return uuid;
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
     * @param firstName
     * @param lastName
     * @param pin
     * @return
     */
    public User addUser(String firstName, String lastName,
    String pin){
        User newUser = new User(firstName,lastName,pin,this);
        this.users.add(newUser);

        //CREATE A SAVINGS ACCOUNT
        Account newAccount = new Account("Savings",
                newUser, this);
        //adding the account to the holder and bank list
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }

    /**
     *
     * @param userID
     * @param pin
     * @return
     */
    public User userLogin(String userID, String pin){

        // search through list of users
        for (User u: this.users){
            // check if the user id is correct
            if (u.getUUID().compareTo(userID)==0
            && u.validatePin(pin)){
                return u;
            }
        }
        return null;
    }
}
