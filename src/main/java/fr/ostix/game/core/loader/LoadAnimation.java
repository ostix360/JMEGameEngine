package fr.ostix.game.core.loader;


import fr.ostix.game.entity.animated.animation.animation.Animation;
import fr.ostix.game.entity.animated.animation.loaders.AnimationLoader;
import fr.ostix.game.toolBox.FileType;
import fr.ostix.game.toolBox.ToolDirectory;

public class LoadAnimation {


    private static Animation animation;

    public static void loadAnimatedModel(String animationFile) {

        animation = AnimationLoader.loadAnimation(ToolDirectory.RES_FOLDER + animationFile + FileType.COLLADA.getExtension());

    }

    public static Animation getAnimation() {
        return animation;
    }
}
