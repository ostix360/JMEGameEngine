package fr.ostix.game.entity.component.animation;

import fr.ostix.game.core.resources.*;
import fr.ostix.game.entity.*;
import fr.ostix.game.entity.animated.animation.animatedModel.*;
import fr.ostix.game.entity.component.*;

public class AnimationCreator implements ComponentCreator {
    @Override
    public Component createComponent(Entity entity) {
        return null;
    }


    public Component loadComponent(String component, Entity entity) {
        AnimatedModel model;
        if (entity.getModel() instanceof AnimatedModel) {
            model = (AnimatedModel) entity.getModel();
        } else {
            new Exception("Animation component couldn't be created because your entity's model can't be animated");
            return null;
        }
        return new AnimationComponent(entity, ResourcePack.getAnimationByName().get(model));
    }
}
