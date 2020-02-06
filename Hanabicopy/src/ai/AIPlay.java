package ai;


public class AIPlay
{
    public String play; //either "play", "info" or "discard"
    public int position; //the position of the card being played/discarded
    public char colour; // the colour of the clue
    public int rank = 0; // the rank of the clue
    public int player; // the player receiving the info

    //constructor, used for play and discard
    public AIPlay(String play, int position)
    {
        this.play = play;
        this.position = position+1;
    }

    //alternate constructor, used for ERROR
    public AIPlay(String play)
    {
        this.play = play;
    }

    //alternate constructor, used for giving colour info
    public AIPlay(String play, char clue, int player)
    {
        this.play = play;
        this.colour = clue;
        this.player = player;
    }

    //alternate constructor, used for giving rank info
    public AIPlay(String play, int clue, int player)
    {
        this.play = play;
        this.rank = clue;
        this.player = player;
    }

}
