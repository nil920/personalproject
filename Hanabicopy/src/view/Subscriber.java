package view;

public interface Subscriber {
    public void notifyPointChange();
    public void notifyHandChange();
    public void notifyInfoTokenChange();
    public void notifyFuseTokenChange();
    public void notifyCurrentPlayerChange();
    public void notifyFireworkChange();
    public void notifyDiscardChange();
    public void addActionListener();
    public void disableActionListner();
}
