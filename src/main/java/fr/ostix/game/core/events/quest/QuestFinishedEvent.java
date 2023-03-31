package fr.ostix.game.core.events.quest;

import fr.ostix.game.entity.Player;

public class QuestFinishedEvent extends QuestEvent {
    private Player p;

    public QuestFinishedEvent(int questID, int priority, Player p) {
        super(questID, priority);
        this.p = p;
    }

    public Player getP() {
        return p;
    }
}
