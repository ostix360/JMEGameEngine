package fr.ostix.game.graphics.font.meshCreator;


import fr.ostix.game.graphics.font.rendering.MasterFont;
import fr.ostix.game.graphics.model.MeshModel;
import fr.ostix.game.toolBox.Color;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.Objects;


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

    private TextProperties prop;

    private boolean edited;
    private Vector3f colour = new Vector3f(0f, 0f, 0f);

    public GUIText(String text, float fontSize, FontType font, Vector2f position, float maxLineLength,
                   boolean centered) {
        this.fontSize = fontSize;
        this.font = font;
        this.position = position.div(1920, 1080);
        this.lineMaxSize = maxLineLength / 1920f;
        this.centerText = centered;
        prop = new TextProperties(text, fontSize, numberOfLines);
        setText(text);
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
        prop.setNumberOfLines(number);
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
        this.prop.setTextString(textString);
        this.edited = true;
    }

    public TextProperties getProp() {
        return prop;
    }

    public static class TextProperties{
        private String textString;
        private float fontSize;
        private int numberOfLines;

        public TextProperties(String textString, float fontSize, int numberOfLines) {
            this.textString = textString;
            this.fontSize = fontSize;
            this.numberOfLines = numberOfLines;
        }

        public String getTextString() {
            return textString;
        }

        public void setTextString(String textString) {
            this.textString = textString;
        }

        public float getFontSize() {
            return fontSize;
        }

        public void setFontSize(float fontSize) {
            this.fontSize = fontSize;
        }

        public int getNumberOfLines() {
            return numberOfLines;
        }

        public void setNumberOfLines(int numberOfLines) {
            this.numberOfLines = numberOfLines;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TextProperties that = (TextProperties) o;
            return Float.compare(that.getFontSize(), getFontSize()) == 0 && getNumberOfLines() == that.getNumberOfLines() && Objects.equals(getTextString(), that.getTextString());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getTextString(), getFontSize(), getNumberOfLines());
        }
    }
}
