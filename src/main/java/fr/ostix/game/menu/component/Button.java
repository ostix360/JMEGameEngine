package fr.ostix.game.menu.component;

import fr.ostix.game.core.*;
import fr.ostix.game.gui.GuiLayer;
import fr.ostix.game.toolBox.*;
import fr.ostix.game.toolBox.OpenGL.*;
import org.joml.*;
import org.lwjgl.glfw.*;

public class Button extends Component {

    protected boolean pressed;
    private final IPressable onPress;

    protected GuiLayer layer;

    public Button(float x, float y, float width, float height, int texture, IPressable press) {
        super(x, y, width, height, texture);

        this.onPress = press;
    }

    @Override
    public void init() {
        super.init();
        this.texture.setLayer(new Color(0.45f, 0.45f, 0.5f, 0.85f));
    }

    @Override
    public void update() {
        this.texture.hasLayer(isIn());
        pressed = isIn() && Input.keysMouse[GLFW.GLFW_MOUSE_BUTTON_1];
        if (pressed) onPress.onPress(this);
    }

    @Override
    public void render() {

    }

    public boolean mouseIn(Vector2f MousePos) {
        float mX = MousePos.x() / DisplayManager.getWidth() * 1920;
        float mY = MousePos.y() / DisplayManager.getHeight() * 1080;


        return pressed = mX >= this.x && mY >= this.y &&
                mX < (this.x + this.width) && mY < (this.y + this.height);
    }

    public interface IPressable {
        void onPress(Button button);
    }

}
