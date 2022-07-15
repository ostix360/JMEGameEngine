package fr.ostix.game.core.resources;

import fr.ostix.game.core.loader.LoadAnimation;
import fr.ostix.game.entity.animated.animation.animation.Animation;

public class AnimationResources {
    private Animation animation;

    private final String modelName;
    private final String animationName;

    public AnimationResources(String modelName, String animationName) {
        this.modelName = modelName;
        this.animationName = animationName;
    }

    public void loadAnimation() {
        LoadAnimation.loadAnimatedModel("/animations/" +modelName + "/" + animationName);
        this.animation = LoadAnimation.getAnimation();
    }

    public Animation getAnimation() {
        return animation;
    }


    public String getModelName() {
        return modelName;
    }

    public String getAnimationName() {
        return animationName;
    }
}
