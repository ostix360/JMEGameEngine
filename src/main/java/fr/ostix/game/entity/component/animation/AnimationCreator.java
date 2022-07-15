package fr.ostix.game.entity.component.animation;

import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.entity.Entity;
import fr.ostix.game.entity.animated.animation.animatedModel.AnimatedModel;
import fr.ostix.game.entity.component.Component;
import fr.ostix.game.entity.component.ComponentCreator;

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
