package fr.ostix.game.core;

import fr.ostix.game.core.quest.QuestCategory;
import fr.ostix.game.entity.Entity;
import fr.ostix.game.entity.Player;
import fr.ostix.game.entity.entities.NPC;
import fr.ostix.game.items.Item;

import java.util.HashMap;

public class Registered {
    private static final HashMap<Integer, QuestCategory> questRegistered = new HashMap<>();
    private static final HashMap<Integer, Item> itemRegistered = new HashMap<>();
    private static final HashMap<Integer, Entity> entityRegistered = new HashMap<>();
    private static final HashMap<Integer, NPC> npcRegistered = new HashMap<>();
    private static Player player;

    public static void registerPlayer(Player p) {
        player = p;
    }

    public static void registerQuest(QuestCategory qc) {
        questRegistered.put(qc.getId(), qc);
    }

    public static void registerItem(Item i) {
        itemRegistered.put(i.getId(), i);
    }

    public static void registerEntity(Entity e) {
        entityRegistered.put(e.getId(), e);
    }

    public static void registerNPC(NPC npc) {
        npcRegistered.put(npc.getId(), npc);
    }

    public static QuestCategory getQuest(int id) {
        return questRegistered.get(id);
    }

    public static Item getItem(int id) {
        return itemRegistered.get(id);
    }

    public static Entity getEntity(int id) {
        return entityRegistered.get(id);
    }

    public static Player getPlayer() {
        return player;
    }

    public static NPC getNPC(int id) {
        return npcRegistered.get(id);
    }
}
