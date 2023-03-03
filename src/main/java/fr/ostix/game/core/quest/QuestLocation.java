package fr.ostix.game.core.quest;

import com.google.gson.annotations.Expose;
import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.listener.quest.*;
import fr.ostix.game.core.loader.json.*;
import fr.ostix.game.core.quest.serialization.RewardsTypeAdapter;
import fr.ostix.game.toolBox.Vec3f;
import org.joml.*;

public class QuestLocation extends Quest {
    @Expose
    private final Vec3f pos;
    @Expose
    private final float range;



    public QuestLocation() {
        this.pos = new Vec3f();
        this.range = 5;
    }

    @Override
    public void execute() {
        EventManager.getInstance().register(this.listener = new QuestLocationListener(this));
    }

    public static QuestLocation load(String questData) {
        return JsonUtils.gsonInstance(Rewards.class,new RewardsTypeAdapter(),true).fromJson(questData, QuestLocation.class);
    }



    public Vector3f getPos() {
        return pos.toVector3f();
    }

    public float getRange() {
        return range;
    }

    @Override
    public String save() {
        return JsonUtils.gsonInstance(Rewards.class,new RewardsTypeAdapter(),true).toJson(this);
    }
}
