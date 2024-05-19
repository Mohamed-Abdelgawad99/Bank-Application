package bankingAppProject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class BankTest {

    @Test
    void getNewUserUUID(){
        Bank newbank = new Bank("Masr");
        String result = newbank.getNewUserUUID();
        Assertions.assertEquals(result.length(), 6);
    }

}