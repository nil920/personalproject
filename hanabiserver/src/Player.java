import java.net.Socket;
import java.util.ArrayList;



public class Player {
    /*
    this class can use for count player's marks.
    In the furture may used for a ranking board.
     */
    private int seat;
    private Socket socket;
    private int invaildactioncounter;

    public Player(Socket socket) {
        this.seat = 0;
        this.socket = socket;
        this.invaildactioncounter = 0;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public int getInvaildactioncounter() {
        return invaildactioncounter;
    }

    public void setInvaildactioncounter(int invaildactioncounter) {
        this.invaildactioncounter = invaildactioncounter;
    }
}
