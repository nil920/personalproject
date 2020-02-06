package controller.gson;

public class GivenInfoInt {
    String action;
    int player;
    int rank;

    public GivenInfoInt(String action, int player, int rank) {
        this.action =  action;
        this.player = player+1;
        this.rank = rank;
    }
}
