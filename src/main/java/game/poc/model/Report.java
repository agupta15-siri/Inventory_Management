package game.poc.model;

import lombok.Data;

@Data
public class Report {

    int id;
    String userEmail;
    int numOfLogin;
    String assetName;
    long assetCount;
    boolean isActive;

}
