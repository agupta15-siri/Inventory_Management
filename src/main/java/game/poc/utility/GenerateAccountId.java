package game.poc.utility;

import java.util.UUID;

public class GenerateAccountId {

    public String generateAccountID(){
        return UUID.randomUUID().toString().substring(0, 5);
    }
}
