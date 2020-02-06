package model;



public class Action{
    private static String Info;

    /*
    this Position is the Index of player which is the action before we sent to Server.
     */
    public static String playaction(Game game,int Position) {
        String colorName;
        if(game.getHands().get(game.getCurrentPlayer()).get(Position).getCardColor() =='r')
        {
            colorName = "red";
        }else if(game.getHands().get(game.getCurrentPlayer()).get(Position).getCardColor() =='b')
        {
            colorName = "blue";
        }else if (game.getHands().get(game.getCurrentPlayer()).get(Position).getCardColor() =='y')
        {
            colorName = "yellow";
        }else if (game.getHands().get(game.getCurrentPlayer()).get(Position).getCardColor() =='w')
        {
            colorName = "white";
        }else if (game.getHands().get(game.getCurrentPlayer()).get(Position).getCardColor() =='g')
        {
            colorName = "green";
        }else
        {
            colorName = "wild";
        }
        Info = "The player " + (game.getCurrentPlayer() + 1) + " play the " + colorName + " " +game.getHands().get(game.getCurrentPlayer()).get(Position).getCardRank() ;
        return Info;
    }
    public static String discardaction(Game game,int Position)
    {
        String colorName;
        if(game.getHands().get(game.getCurrentPlayer()).get(Position).getCardColor() =='r')
        {
            colorName = "red";
        }else if(game.getHands().get(game.getCurrentPlayer()).get(Position).getCardColor() =='b')
        {
            colorName = "blue";
        }else if (game.getHands().get(game.getCurrentPlayer()).get(Position).getCardColor() =='y')
        {
            colorName = "yellow";
        }else if (game.getHands().get(game.getCurrentPlayer()).get(Position).getCardColor() =='w')
        {
            colorName = "white";
        }else if (game.getHands().get(game.getCurrentPlayer()).get(Position).getCardColor() =='g')
        {
            colorName = "green";
        }else
        {
            colorName = "wild";
        }
        Info= "The player " + (game.getCurrentPlayer() +1) + " discard the " + colorName + " " + game.getHands().get(game.getCurrentPlayer()).get(Position).getCardRank();
        return Info;
    }

    public static String youinformaction(Game game, int cardRank)
    {
        Info= "The player " + (game.getCurrentPlayer() +1) + " gave Information about Rank " + cardRank +" to you";
        return Info;
    }

    public static String youinformaction(Game game, String CardColor)
    {
        String colorName;
        if(CardColor.equals("r"))
        {
            colorName = "red";
        }else if(CardColor.equals("b"))
        {
            colorName = "blue";
        }else if (CardColor.equals("y"))
        {
            colorName = "yellow";
        }else if (CardColor.equals("w"))
        {
            colorName = "white";
        }else
        {
            colorName = "green";
        }
        Info= "The player " + (game.getCurrentPlayer() + 1) + " gave Information about Color " + colorName +" to you";
        return Info;
    }


    public static String informaction(Game game,int playerRecieve, int cardRank)
    {
        Info= "The player " + (game.getCurrentPlayer() +1) + " gave Information about Rank " + cardRank +" to player " +playerRecieve;
        return Info;
    }

    public static String informaction(Game game,int playerRecieve, String CardColor)
    {
        String colorName;
        if(CardColor.equals("r"))
        {
            colorName = "red";
        }else if(CardColor.equals("b"))
        {
            colorName = "blue";
        }else if (CardColor.equals("y"))
        {
            colorName = "yellow";
        }else if (CardColor.equals("w"))
        {
            colorName = "white";
        }else
        {
            colorName = "green";
        }
        Info= "The player " + (game.getCurrentPlayer() + 1) + " gave Information about Color " + colorName +" to player " +playerRecieve;
        return Info;
    }
}



