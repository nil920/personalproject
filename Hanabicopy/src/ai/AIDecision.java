package ai;

import model.Card;
import model.Game;
import java.util.Random;

public class AIDecision {

    public static AIPlay AIMakeMove(Game game) {

        //the play the AI is making
        AIPlay play;

        //the card the AI could play
        Card cardToPlay;

        //the position of the card to play
        int playPos;

        //try to play a card, but only play if it is 100% sure it can go onto a firework.
        //unless it is last round, then only one of the rank or colour need to be known to try to play
        playPos = canAIPlay(game);
         if(playPos != -1)
         {
             cardToPlay = game.getHands().get(game.getCurrentPlayer()).get(playPos);

             //check if the card is a valid card(if no cards can be played, it returns an invalid card)
             if ((cardToPlay.getCardColor() == 'b' ||
                     cardToPlay.getCardColor() == 'r' ||
                     cardToPlay.getCardColor() == 'w' ||
                     cardToPlay.getCardColor() == 'm' ||
                     cardToPlay.getCardColor() == 'y' ||
                     cardToPlay.getCardColor() == 'g') &&
                     (cardToPlay.getCardRank() == 1 ||
                             cardToPlay.getCardRank() == 2 ||
                             cardToPlay.getCardRank() == 3 ||
                             cardToPlay.getCardRank() == 4 ||
                             cardToPlay.getCardRank() == 5)) {
                 //if it is valid, return it
                 return new AIPlay("play", playPos);
             }
         }
        //if the AI is not going to play, then give info
        //only give info if the amount of tokens is 2 or more(never take the last token)
        if (game.getInforTokenCountor() >= 5) {
            return canAIInfo(game);
        }

        //if it is not giving info, choose a card to discard.
        //can't discard if if there is maximum info tokens(this should never come up because it there is tokens,
        // it will give info first)
        if (game.getInforTokenCountor() != 8) {
            return canAIDiscard(game);
        }

        //if something goes wrong, return an "error" play
        return new AIPlay("ERROR");
    }

    //can the AI play a card
    //@param game - the game state
    //@return int - the position of the card that can be played
    public static int canAIPlay(Game game) {
        Card thePlay = new Card(-1, 'q', 0);


        //keeps track of whether or not this firework is completed
        boolean red = false;
        boolean blue = false;
        boolean yellow = false;
        boolean white = false;
        boolean green = false;
        boolean wild = false; //might not be used

        //do not discard a 5

        //for every firework
        for(int i = 0; i < game.getFirework().size(); i++)
        {
            if(game.getFirework().get(i).size() != 0) {
                if (game.getFirework().get(i).get(0).getCardColor() == 'r') {
                    red = true;
                }
                if (game.getFirework().get(i).get(0).getCardColor() == 'y') {
                    yellow = true;
                }
                if (game.getFirework().get(i).get(0).getCardColor() == 'g') {
                    green = true;
                }
                if (game.getFirework().get(i).get(0).getCardColor() == 'w') {
                    white = true;
                }
                if (game.getFirework().get(i).get(0).getCardColor() == 'b') {
                    blue = true;
                }
                if (game.getFirework().get(i).get(0).getCardColor() == 'm') {
                    wild = true;
                }
            }
        }
        //for every card in hand
        for(int j = 0; j < game.getHands().get(game.getCurrentPlayer()).size(); j++)
        {
            if(game.getHands().get(game.getCurrentPlayer()).get(j).getCardRank() == 1)
            {
                if(game.getHands().get(game.getCurrentPlayer()).get(j).getCardColor() == 'r' && red ==false)
                {
                    return j;
                }
                if(game.getHands().get(game.getCurrentPlayer()).get(j).getCardColor() == 'y' && yellow == false)
                {
                    return j;
                }
                if(game.getHands().get(game.getCurrentPlayer()).get(j).getCardColor() == 'g' && green == false)
                {
                    return j;
                }
                if(game.getHands().get(game.getCurrentPlayer()).get(j).getCardColor() == 'w' && white == false)
                {
                    return j;
                }
                if(game.getHands().get(game.getCurrentPlayer()).get(j).getCardColor() == 'b' && blue ==false)
                {
                    return j;
                }
                if(game.getHands().get(game.getCurrentPlayer()).get(j).getCardColor() == 'm' && wild ==false)
                {
                    return j;
                }
            }
        }

        //for every firework
        for(int i = 0; i < game.getFirework().size(); i++) {

            //check if a card in your hand can be played onto that firework
            for(int j = 0; j < game.getHands().get(game.getCurrentPlayer()).size(); j++) {

                //whether the color and rank are known
                boolean colourKnown = game.getHands().get(game.getCurrentPlayer()).get(j).colorKnown;
                boolean rankKnown = game.getHands().get(game.getCurrentPlayer()).get(j).rankKnown;

                //get the color and rank of the card
                char colour = game.getHands().get(game.getCurrentPlayer()).get(j).getCardColor() ;
                int rank = game.getHands().get(game.getCurrentPlayer()).get(j).getCardRank() ;

                //if the firework colour is a wildcard, and wildcards are on, do not check colour
                if (game.getFirework().get(i).size() != 0 && 'w' == game.getFirework().get(i).get(0).getCardColor() && game.wild) {
                    if ((colourKnown || rankKnown) &&     //is the colour and rank known?
                            (game.getFirework().get(i).get(0) != null) && // is the firework not null?
                            ((game.getFirework().get(i).size() + 1) == rank)) { //is the card rank the next one?
                        //if yes to all, return the card
                        return j;
                    }
                }
                else { //else, both clues need to be known about a card
                    if ((colourKnown && rankKnown) &&     //is the colour and rank known?
                            (game.getFirework().get(i).get(0) != null) &&// is the firework not null?
                            ((game.getFirework().get(i).size()) == rank)) { //is the card rank the next one?
                        return j;
                    }
                }

                //if it is the last turn, only one clue needs to be known (focus on completing fireworks)
                if (game.getLastTurn()) {
                    if ((colourKnown || rankKnown) &&     //is the colour and rank known?
                            (game.getFirework().get(i).get(0) != null) &&// is the firework not null?
                            ((colour == game.getFirework().get(i).get(0).getCardColor()) ||//check firework colour
                                    (game.getFirework().get(i).size()) == rank)) {//is the card rank the next one?
                        //if yes to all, return the card
                        return j;
                    }
                }
                else { //else, both clues need to be known about a card
                    if ((colourKnown && rankKnown) &&     //is the colour and rank known?
                            (game.getFirework().get(i).get(0) != null) &&// is the firework not null?
                            (colour == game.getFirework().get(i).get(0).getCardColor()) &&//check firework colour
                            (game.getFirework().get(i).size() + 1) == rank) { //is the card rank the next one?
                        return j;
                    }
                }
            }
        }
        return -1;
    }

    //can the AI give info
    //@param game - the game state
    //@return AIPlay - a class the contains the player, and clue to give
    public static AIPlay canAIInfo(Game game) {

        //keeps track of whether or not a firework of this colour has been started yet
        boolean red= false;
        boolean yellow= false;
        boolean white= false;
        boolean green= false;
        boolean blue = false;
        boolean wildCard = false; // might not be used

        //if a firework colour has not been started yet give info about that colours 1's
        //for every firework
        for (int i = 0; i < game.getFirework().size(); i++) {
            if (game.getFirework().get(i).size()!=0 &&game.getFirework().get(i).getFirst().getCardColor() == 'r') {
                red = true;
            }

            if (game.getFirework().get(i).size()!=0 && game.getFirework().get(i).getFirst().getCardColor() == 'b') {
                blue = true;
            }

            if (game.getFirework().get(i).size()!=0 &&game.getFirework().get(i).getFirst().getCardColor() == 'g') {
                green = true;
            }

            if (game.getFirework().get(i).size()!=0 &&game.getFirework().get(i).getFirst().getCardColor() == 'w') {
                white = true;
            }

            if (game.getFirework().get(i).size()!=0 &&game.getFirework().get(i).getFirst().getCardColor() == 'y') {
                yellow = true;
            }

            if ((game.getFirework().get(i).size()!=0 &&game.getFirework().get(i).getFirst().getCardColor() == 'm') && !game.wild) {
                wildCard = true;
            }
        }

        //if a colour is false, look at all hands and give a clue about that colours 1

        //for every player
        for (int j = 0; j < game.getHands().size(); j++) {
            //for every card in that players hand
            for (int x = 0; x < game.getHands().get(j).size(); x++) {
                //if the card is a 1 and the info has not already been given, give info on it...
                if ((game.getHands().get(j).get(x).getCardRank() == 1) &&
                        (game.getHands().get(j).get(x).rankKnown == false)) {
                    //if the card is red, and the red firework has not been made yet
                    if ((game.getHands().get(j).get(x).getCardColor() == 'r') && (red == false)) {
                        //give info on the card
                        return new AIPlay("info", 1, j);
                    }
                    //if the card is blue, and the blue firework has not been made yet
                    if ((game.getHands().get(j).get(x).getCardColor() == 'b') && (blue == false)) {
                        //give info on the card
                        return new AIPlay("info", 1, j);
                    }
                    //if the card is yellow, and the yellow firework has not been made yet
                    if ((game.getHands().get(j).get(x).getCardColor() == 'y') && (yellow == false)) {
                        //give info on the card
                        return new AIPlay("info", 1, j);
                    }
                    //if the card is green, and the green firework has not been made yet
                    if ((game.getHands().get(j).get(x).getCardColor() == 'g') && (green == false)) {
                        //give info on the card
                        return new AIPlay("info", 1, j);
                    }
                    //if the card is white, and the white firework has not been made yet
                    if ((game.getHands().get(j).get(x).getCardColor() == 'w') && (white == false)) {
                        //give info on the card
                        return new AIPlay("info", 1, j);
                    }
                    //if the card is wild, and the wild firework has not been made yet, and wild are there own firework
                    if ((game.getHands().get(j).get(x).getCardColor() == 'm') && (wildCard == false) && !wildCard) {
                        //give info on the card
                        return new AIPlay("info", 1, j);
                    }
                }
            }
        }

        //try to give info about a card that can go onto a firework.
        //for every firework
        for (int i = 0; i < game.getFirework().size(); i++) {
            //for every player
            for (int j = 0; j < game.getHands().size(); j++) {
                //for every card in that players hand
                for (int x = 0; x < game.getHands().get(j).size(); x++) {
                    //get the card
                    Card card = game.getHands().get(j).get(x);

                    //if the card already has all of the info, move on
                    if (!(card.getColorKnown() && card.getRankKnown())) {
                        //otherwise, check to see if it can go onto a firework

                        //if the card has the same colour as the firework
                        if (game.getFirework().get(i).size()!=0 &&game.getFirework().get(i).get(0).getCardColor() == card.getCardColor()) {
                            //and it can be placed onto the fireworkimport model.Card;
                            if (game.getFirework().get(i).size() == card.getCardRank()) {
                                //give info on it
                                //check if it already knows the rank
                                if (game.getHands().get(j).get(x).rankKnown) {
                                    System.out.println("giving info about card colour " + card.getCardColor() );
                                    return new AIPlay("info",card.getCardColor() , j);
                                }
                                //else check if the colour is known
                                else if (game.getHands().get(j).get(x).colorKnown) {
                                    return new AIPlay("info",card.getCardRank() , j );
                                }
                            }
                        }
                    }
                }
            }
        }


        //if no cards can be put onto a firework,
        //give info on a random card.
        //for every player
        for (int j = 0; j < game.getHands().size(); j++) {

            //for every card in that players hand
            for(int x = 0; x < game.getHands().get(j).size(); x++) {

                    //get the card
                    Card card = game.getHands().get(j).get(x);

                    //if the card already has all of the info, move on
                    if (!(card.getColorKnown() && card.getRankKnown()) && j!=game.getYou()) {
                        //give info on it
                        //check if it already knows the rank
                        if (game.getHands().get(j).get(x).rankKnown) {
                            return new AIPlay("info", card.getCardColor(), j);
                        }
                        //else check if the colour is known
                        else if (game.getHands().get(j).get(x).colorKnown) {
                            return new AIPlay("info", card.getCardRank(), j);
                        }
                    }
                }
            }
        //if it get here, return error
        return new AIPlay("info",game.getHands().get(0).get(0).getCardRank(),0);
    }


    /**
     * choose a card to discard
     * @param game the game state
     * @return AIPlay a class the contains the discard choice, and which card to discard
     */
    public static AIPlay canAIDiscard(Game game) {
        //keeps track of whether or not it is ok to discard this card.
        boolean pos1 = false;
        boolean pos2 = false;
        boolean pos3 = false;
        boolean pos4 = false;
        boolean pos5 = false; // might not have this position

        //keeps track of whether or not this firework is completed
        boolean red = false;
        boolean blue = false;
        boolean yellow = false;
        boolean white = false;
        boolean green = false;
        boolean wild = false; //might not be used

        //do not discard a 5

        //check each card in the hand, and see if it is a 5
        if (game.getHands().get(game.getCurrentPlayer()).get(0).getCardRank() != 5) {
            pos1 = true;
        }

        else if (game.getHands().get(game.getCurrentPlayer()).get(1).getCardRank() != 5) {
            pos2 = true;
        }

        else if (game.getHands().get(game.getCurrentPlayer()).get(2).getCardRank() != 5) {
            pos3 = true;
        }

        else if (game.getHands().get(game.getCurrentPlayer()).get(3).getCardRank() != 5) {
            pos4 = true;
        }
        else {
            pos5 = true;
        }

        //for cards that know rank and colour
        //discard it if that firework has no use for the card
        //NOTE: 1's can always be discard because the AI would have played it otherwise
        //for every firework
        for (int i = 0; i < game.getFirework().size(); i++) {
            //for each card in hand
            for (int j = 0; j < game.getHands().get(game.getCurrentPlayer()).size(); j++) {
                //check if the colour and rank are known
                boolean colourKnown = game.getHands().get(game.getCurrentPlayer()).get(j).colorKnown;
                boolean rankKnown = game.getHands().get(game.getCurrentPlayer()).get(j).rankKnown;

                //get the color and rank of the card
                char colour = game.getHands().get(game.getCurrentPlayer()).get(j).getCardColor();
                int rank = game.getHands().get(game.getCurrentPlayer()).get(j).getCardRank();

                if ((colourKnown && rankKnown) &&     //is the colour and rank known?
                        (game.getFirework().get(i).get(0) != null) &&// is the firework not null?
                        ((colour == game.getFirework().get(i).get(0).getCardColor()) &&//check firework colour
                                (game.getFirework().get(i).size()) > rank)) //is the card rank below the top?
                {
                    //if yes to all, that card can be discarded
                    return new AIPlay("discard", j);
                }
            }
        }

        //if just the rank is known it is safe to discard if all fireworks are above it
        //for every firework
        for (int i = 0; i < game.getFirework().size(); i++) {
            //for each card in hand
            for (int j = 0; j < game.getHands().get(game.getCurrentPlayer()).size(); j++) {
                //get the height of the lowest firework
                int height = getHeight(game);

                //if the card rank is less than the lowest firework height...
                if (game.getHands().get(game.getCurrentPlayer()).get(j).getCardRank() < height &&
                        game.getHands().get(game.getCurrentPlayer()).get(j).rankKnown) {

                    //it is safe to discard
                    return new AIPlay("discard", j);
                }
            }
        }

        //if just the colour is known, it is safe to discard if that firework is done
        //for every firework
        for (int i = 0; i < game.getFirework().size(); i++) {
            //if the firework is at 5, get its colour and set it to false
            if (game.getFirework().get(i).size() == 5) {
                //make sure it has atleast one card, otherwise errors will happen
                if (game.getFirework().get(i).size() == 0) {
                    //just don't do the other stuff
                }

                else if(game.getFirework().get(i).get(0).getCardColor() == 'r') {
                    red = true;
                }

                else if (game.getFirework().get(i).get(0).getCardColor() == 'b') {
                    blue = true;
                }

                else if (game.getFirework().get(i).get(0).getCardColor() == 'y') {
                    yellow = true;
                }
                else if (game.getFirework().get(i).get(0).getCardColor() == 'g') {
                    green = true;
                }
                else if (game.getFirework().get(i).get(0).getCardColor() == 'w') {
                    white = true;
                }
                else {
                    wild = true;
                }
            }
        }

        //now, check all cards and discard them if they share a colour with a firework that is done
        //for each card in hand
        for (int j = 0; j < game.getHands().get(game.getCurrentPlayer()).size(); j++) {
            //if the card colour is not useful
            if (game.getHands().get(game.getCurrentPlayer()).get(j).getCardColor() == 'w' && white == true) {
                return new AIPlay("discard", j);
            }

            //if the card colour is not useful
            if (game.getHands().get(game.getCurrentPlayer()).get(j).getCardColor() == 'b' && blue == true) {
                return new AIPlay("discard", j);
            }

            //if the card colour is not useful
            if (game.getHands().get(game.getCurrentPlayer()).get(j).getCardColor() == 'r' && red == true) {
                return new AIPlay("discard", j);
            }

            //if the card colour is not useful
            if (game.getHands().get(game.getCurrentPlayer()).get(j).getCardColor() == 'y' && yellow == true) {
                return new AIPlay("discard", j);
            }

            //if the card colour is not useful
            if (game.getHands().get(game.getCurrentPlayer()).get(j).getCardColor() == 'g' && green == true) {
                return new AIPlay("discard", j);
            }

            //if the card colour is not useful
            if (game.getHands().get(game.getCurrentPlayer()).get(j).getCardColor() == 'm' && wild == true && !game.wild) {
                return new AIPlay("discard", j);
            }
        }
        //if we get here somehow, discard a card at random
        Random rand = new Random();
        return new AIPlay("discard", rand.nextInt(5));
    }

    //a helper function that determines the height of the lowest firework
    //@Param game - the game state
    //@return int - the height of the lowest firework
    public static int getHeight(Game game) {
        //the height of hte lowest firework
        int height = 100;

        //for every firework
        for (int i = 0; i < game.getFirework().size(); i++) {
            //if the height is less than the minimum height, set the new min height
            if (game.getFirework().get(i).size() < height) {
                height = game.getFirework().get(i).size();
            }
        }
        return height;
    }
}
