package gofish;

import gofish.model.Card;
import gofish.model.Player;
import gofish.model.Series;

public interface GuiRenderer {
    
    void startGame();
    
    void endGame(Player winner);
    
    void playerTurn(Player player);
    
    void showSeries(Player player);

    void invalidQuery(Player.Query query);
    
    void playerQuery(Player.Query query);
    
    void playerOut(Player player);
    
    void moveCard(Player from, Player to, Card card);
    
    void seriesCompleted(Player player, Series series);
    
    void goFish(Player player);
    
    void error(String message);
    
}
