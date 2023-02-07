package fr.ostix.game.entity.component.ai;

import fr.ostix.game.core.loader.json.*;
import fr.ostix.game.entity.*;
import fr.ostix.game.entity.component.*;
import fr.ostix.game.toolBox.*;

public class AICreator implements ComponentCreator {
    @Override
    public Component createComponent(Entity entity) {
        return null;
    }

    @Override
    public Component loadComponent(String component, Entity e) {
        AIComponent ai = null;
        try {
            AIProperties prop = JsonUtils.gsonInstance().fromJson(component, AIProperties.class);
            ai = new AIComponent(e, prop);
        } catch (Exception ex) {
            Logger.err("Failed to load AI Component");
            ex.printStackTrace();
        }
        return ai;
    }
}
