package controller;

import ai.AIDecision;
import ai.AIPlay;
import model.Game;
import java.awt.event.ActionEvent;

public class ComputerController
{
    private Game game;

    public ComputerController(Game game){
        this.game = game;
    }

    public void actionPerformed()
    {
        AIPlay result = AIDecision.AIMakeMove(game);

        if (result.play.equals("play"))
        {
            MovesController.play(result.position);
        }
        else if (result.play.equals("discard"))
        {
            MovesController.discard(result.position);
        }
        else if (result.play.equals("info") && result.rank == 0)
        {
            System.out.println("The colour is: " + result.colour);

            MovesController.giveClueSuit(result.player+1, Character.toString(result.colour));
            game.colorhinttoother(result.player+1, result.colour);
        } else if(result.play.equals("info") && result.rank != 0)
        {
            System.out.println(result.colour);
            System.out.println(result.rank);
            System.out.println(result.play);
            System.out.println(result.player);
            MovesController.giveClueRank(result.player, result.rank);
            game.inthinttoother(result.player+1,result.rank);
        }
    }
}


