package model;



public class Action{
    private static String Info;

    /*
    this Position is the Index of player which is the action before we sent to Server.
     */
    public static String playaction(Game game,int Position) {
        String colorName;
        if(game.hands.get(game.currentPlayer).get(Position).cardColor =='r')
        {
            colorName = "red";
        }else if(game.hands.get(game.currentPlayer).get(Position).cardColor =='b')
        {
            colorName = "blue";
        }else if (game.hands.get(game.currentPlayer).get(Position).cardColor =='y')
        {
            colorName = "yellow";
        }else if (game.hands.get(game.currentPlayer).get(Position).cardColor =='w')
        {
            colorName = "white";
        }else if (game.hands.get(game.currentPlayer).get(Position).cardColor =='g')
        {
            colorName = "green";
        }else
        {
            colorName = "wild";
        }
        Info = "The player " + (game.getCurrentPlayer() + 1) + " play the " + colorName + " " +game.hands.get(game.currentPlayer).get(Position).cardRank ;
        return Info;
    }
    public static String discardaction(Game game,int Position)
    {
        String colorName;
        if(game.hands.get(game.currentPlayer).get(Position).cardColor =='r')
        {
            colorName = "red";
        }else if(game.hands.get(game.currentPlayer).get(Position).cardColor =='b')
        {
            colorName = "blue";
        }else if (game.hands.get(game.currentPlayer).get(Position).cardColor =='y')
        {
            colorName = "yellow";
        }else if (game.hands.get(game.currentPlayer).get(Position).cardColor =='w')
        {
            colorName = "white";
        }else if (game.hands.get(game.currentPlayer).get(Position).cardColor =='g')
        {
            colorName = "green";
        }else
        {
            colorName = "wild";
        }
        Info= "The player " + (game.getCurrentPlayer() +1) + " discard the " + colorName + " " + game.hands.get(game.currentPlayer).get(Position).cardRank;
        return Info;
    }

    public static String youinformaction(Game game, int cardRank)
    {
        Info= "The player " + (game.getCurrentPlayer() +1) + " gave Information about Rank " + cardRank +" to you";
        return Info;
    }

    public static String youinformaction(Game game, String cardColor)
    {
        String colorName;
        if(cardColor.equals("r"))
        {
            colorName = "red";
        }else if(cardColor.equals("b"))
        {
            colorName = "blue";
        }else if (cardColor.equals("y"))
        {
            colorName = "yellow";
        }else if (cardColor.equals("w"))
        {
            colorName = "white";
        }else
        {
            colorName = "green";
        }
        Info= "The player " + (game.currentPlayer + 1) + " gave Information about Color " + colorName +" to you";
        return Info;
    }


    public static String informaction(Game game,int playerRecieve, int cardRank)
    {
        Info= "The player " + (game.getCurrentPlayer() +1) + " gave Information about Rank " + cardRank +" to player " +playerRecieve;
        return Info;
    }

    public static String informaction(Game game,int playerRecieve, String cardColor)
    {
        String colorName;
        if(cardColor.equals("r"))
        {
            colorName = "red";
        }else if(cardColor.equals("b"))
        {
            colorName = "blue";
        }else if (cardColor.equals("y"))
        {
            colorName = "yellow";
        }else if (cardColor.equals("w"))
        {
            colorName = "white";
        }else
        {
            colorName = "green";
        }
        Info= "The player " + (game.currentPlayer + 1) + " gave Information about Color " + colorName +" to player " +playerRecieve;
        return Info;
    }
}



