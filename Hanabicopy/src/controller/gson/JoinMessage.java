package controller.gson;


import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class JoinMessage {
    private String cmd;
    private String nsid;
    @SerializedName("game-id")
    private int gameId;
    private String token;
    private int timestamp;

    private static String computeHash(String msg) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(msg.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            return ("MD5 ... what's MD5?");
        }
    }

    public JoinMessage(String cmd, String nsid, int game_id, String token) {
        this.cmd = cmd;
        this.nsid = nsid;
        this.gameId = game_id;
        this.token = token;
    }
}
