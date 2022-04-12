package br.magri.texoit.model.view;

public interface WinningProducer {
    String getProducer();
    Integer getInterval();
    Integer getPreviousWin();
    Integer getFollowingWin();
}
