package fr.ostix.game.menu.component;

import fr.ostix.game.core.*;
import fr.ostix.game.gui.GuiLayer;
import fr.ostix.game.toolBox.*;
import fr.ostix.game.toolBox.OpenGL.*;
import org.joml.*;
import org.lwjgl.glfw.*;

public class Button extends Component {

    protected boolean pressed;
    protected final IPressable onPress;

    protected GuiLayer layer;

    protected Vector2f scale = new Vector2f(1, 1);

    public Button(float x, float y, float width, float height, int texture, IPressable press) {
        super(x, y, width, height, texture);

        this.onPress = press;
    }

    @Override
    public void init() {
        super.init();
        if (!this.texture.hasLayer()){
            this.texture.setLayer(new Color(0.45f, 0.45f, 0.5f, 0.85f));
        }
    }

    @Override
    public void update() {
        this.texture.hasLayer(this.isIn());
        pressed = isIn() && Input.keysMouse[GLFW.GLFW_MOUSE_BUTTON_1];
        if (pressed) onPress.onPress(this);
    }

    public void setScale(Vector2f scale) {
        this.scale = scale;
        this.texture.setScale(new Vector2f(this.width, this.height).mul(scale));
    }

    @Override
    public void render() {

    }

    @Override
    public boolean isIn() {
        float mX = (float) Input.getMouseX() / DisplayManager.getWidth() * 1920;
        float mY = (float) Input.getMouseY() / DisplayManager.getHeight() * 1080;
        float px = this.x / scale.x(); //TODO: fix this
        float py = this.y / scale.y();
        return mX >= x && mY >= y &&
                mX < (this.x + this.width * scale.x()) && mY < (this.y + this.height * scale.y());
    }

    public interface IPressable {
        void onPress(Button button);
    }

}
