package fr.ostix.game.core.events.listener.quest;

import fr.ostix.game.core.Registered;
import fr.ostix.game.core.events.EventHandler;
import fr.ostix.game.core.events.EventManager;
import fr.ostix.game.core.events.entity.npc.NPCTalkEvent;
import fr.ostix.game.core.events.listener.Listener;
import fr.ostix.game.core.events.quest.QuestFinishedEvent;
import fr.ostix.game.core.quest.QuestDialog;

public class QuestTalkListener implements Listener {

    private final QuestDialog quest;


    public QuestTalkListener(QuestDialog quest) {
        this.quest = quest;
    }

    @EventHandler
    public void onTalk(NPCTalkEvent event) {
        if (event.getNpc().getId() == quest.getNpcID()) {
//            Registered.getNPC(this.quest.getNpcID()).talke(this.quest.getDialogs(),quest.getDialogLine(),event.getWorld());//Add callback?
            Registered.getNPC(this.quest.getNpcID()).talke(event.getWorld(),quest.getDialogLine(),
                    (success) -> EventManager.getInstance().callEvent(new QuestFinishedEvent(quest.getId(),1,
                            event.getWorld().getPlayer())), quest.getDialogs().toArray(new String[0]));
//
//            EventManager.getInstance().callEvent(new QuestFinishedEvent(quest.getId(),1,event.getWorld().getPlayer()));
        }
    }
}
