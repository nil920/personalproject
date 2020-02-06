package model;


public class Waitingroom{
    private int timer;
    private int gameid;
    private String secretToken;
    private int playersNeeded;

    public Waitingroom(int gameid, String secretToken, int timer,int playersNeeded){
        this.gameid = gameid;
        this.secretToken = secretToken;
        this.timer = timer;
        this.playersNeeded = playersNeeded;
    }

    public int getGameid() {
        return gameid;
    }

    public int getTimer() {
        return timer;
    }

    public String getSecretToken() {
        return secretToken;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public int getPlayersNeeded() {
        return playersNeeded;
    }

    public void setPlayersNeeded(int playersNeeded) {
        this.playersNeeded = playersNeeded;
    }
}
