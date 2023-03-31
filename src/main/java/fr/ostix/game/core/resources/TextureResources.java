package fr.ostix.game.core.resources;

import fr.ostix.game.graphics.textures.TextureProperties;

public class TextureResources {
    private final String name;
    private final String path;
    private final TextureProperties textureProperties;

    public TextureResources(String name, String path, TextureProperties textureProperties) {
        this.name = name;
        this.path = path;
        this.textureProperties = textureProperties;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public TextureProperties getTextureProperties() {
        return textureProperties;
    }
}
