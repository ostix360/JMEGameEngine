package fr.ostix.game.core.events.listener.quest;

import fr.ostix.game.core.*;
import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.entity.npc.*;
import fr.ostix.game.core.events.listener.*;
import fr.ostix.game.core.events.quest.*;
import fr.ostix.game.core.quest.*;

public class QuestTalkListener implements Listener {

    private final QuestDialog quest;


    public QuestTalkListener(QuestDialog quest) {
        this.quest = quest;
    }

    @EventHandler
    public void onTalk(NPCTalkEvent event) {
        if (event.getNpc().getId() == quest.getNpc()) {
            Registered.getNPC(this.quest.getNpc()).talke(this.quest.getDialogs(),event.getWorld());
            EventManager.getInstance().callEvent(new QuestFinishedEvent(quest.getId(),1));
        }
    }
}
