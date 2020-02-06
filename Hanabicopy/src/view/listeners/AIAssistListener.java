package view.listeners;

import ai.AIDecision;
import ai.AIPlay;
import model.Game;
import view.CoordinateSystem;

import java.awt.event.ActionEvent;

public class AIAssistListener extends MainListener{

    private Game game;

    public AIAssistListener(Game game){
        this.game = game;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        AIPlay result =AIDecision.AIMakeMove(game);
        if (result.play.equals("play")){
            CoordinateSystem.logWindow.append("\nAI suggestion: " + result.play +"the card "+ result.position);
        }
        if (result.play.equals("discard")){
            CoordinateSystem.logWindow.append("\nAI suggestion: " + result.play +"the card "+ result.position);
        }
        if (result.play.equals("info") && result.rank==0){
            int player = result.player +1;
            CoordinateSystem.logWindow.append("\nAI suggestion: give information to the " + player +" which is "+ result.colour);
        }
        else {
            int player = result.player +1;
            CoordinateSystem.logWindow.append("\nAI suggestion: give information to the " + player +" which is "+ result.rank);
        }
    }
}
