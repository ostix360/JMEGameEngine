package fr.ostix.game.graphics.font.meshCreator;


import fr.ostix.game.graphics.font.rendering.*;
import fr.ostix.game.graphics.model.*;
import fr.ostix.game.toolBox.*;
import org.joml.*;

import java.util.*;


public class GUIText {

    private String textString;
    private final float fontSize;
    private final boolean centerText;
    private final Vector2f position;
    private final float lineMaxSize;
    private final FontType font;
    private MeshModel textMeshVao;
    private int vertexCount;
    private int numberOfLines;

    private boolean edited;
    private Vector3f colour = new Vector3f(0f, 0f, 0f);

    public GUIText(String text, float fontSize, FontType font, Vector2f position, float maxLineLength,
                   boolean centered) {
        setText(text);
        this.fontSize = fontSize;
        this.font = font;
        this.position = position.div(1920, 1080);
        this.lineMaxSize = maxLineLength / 1920f;
        this.centerText = centered;
    }

    public void remove() {
        MasterFont.remove(this);
    }

    public FontType getFont() {
        return font;
    }

    public void setColour(Color c) {
        colour = c.getVec3f();
    }


    public Vector3f getColour() {
        return colour;
    }

    public int getNumberOfLines() {
        return numberOfLines;
    }

    protected void setNumberOfLines(int number) {
        this.numberOfLines = number;
    }

    public Vector2f getPosition() {
        return position;
    }

    public MeshModel getVao() {
        return textMeshVao;
    }

    public void setMeshInfo(MeshModel vao, int verticesCount) {
        this.textMeshVao = vao;
        this.vertexCount = verticesCount;
        this.edited = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GUIText guiText = (GUIText) o;
        return Float.compare(guiText.fontSize, fontSize) == 0 && centerText == guiText.centerText && Float.compare(guiText.lineMaxSize, lineMaxSize) == 0 && numberOfLines == guiText.numberOfLines && Objects.equals(textString, guiText.textString) && Objects.equals(position, guiText.position) && Objects.equals(font, guiText.font) && Objects.equals(colour, guiText.colour);
    }


    public int getVertexCount() {
        return this.vertexCount;
    }

    protected float getFontSize() {
        return fontSize;
    }

    protected boolean isCentered() {
        return centerText;
    }

    protected float getMaxLineSize() {
        return lineMaxSize;
    }

    public String getTextString() {
        return textString;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setText(String textString) {
        this.textString = textString;
        this.edited = true;
    }
}
