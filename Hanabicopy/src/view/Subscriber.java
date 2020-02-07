package view;

public interface Subscriber {
    // COUNTER TYPE
    public void notifyPointChange();
    public void notifyInfoTokenChange();
    public void notifyFuseTokenChange();

    // ACTION = INFO
    public void notifyHint();

    // ACTION = DISCARD OR PLAY
    public void notifyDiscardChange();
    public void notifyHandChange();
    public void notifyFireworkChange();
    public void notifyCurrentPlayerRemove();


    public void notifyCurrentPlayerChange();



    // ACTION LISTENER
    public void addActionListener();
    public void disableActionListeners();

}
