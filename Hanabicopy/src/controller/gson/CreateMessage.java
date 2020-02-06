package controller.gson;

import java.time.Instant;

public class CreateMessage {
    private String cmd;
    private String nsid;
    private int players;
    private int timeout;
    private int timestamp;
    private boolean force;
    private String md5hash;
    private String rainbow;



    public CreateMessage(String cmd, String nsid, int player, String rainbow,int timeout,  boolean force) {
        this.cmd = cmd;
        this.nsid = nsid;
        this.players = player;
//        this.firework = firework;
        this.timeout = timeout;
        this.rainbow = rainbow;
        this.force = force;
        this.timestamp = (int) Instant.now().getEpochSecond();
    }


}
