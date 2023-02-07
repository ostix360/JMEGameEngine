package fr.ostix.game.menu.component;

import fr.ostix.game.core.*;
import fr.ostix.game.toolBox.*;
import fr.ostix.game.toolBox.OpenGL.*;
import org.joml.*;
import org.lwjgl.glfw.*;

public class Button extends Component {

    private boolean pressed;
    private final IPressable onPress;

    public Button(float x, float y, float width, float height, int texture, IPressable press) {
        super(x, y, width, height, texture);
        this.texture.setLayer(new Color(0.45f, 0.45f, 0.5f, 0.85f));
        this.onPress = press;
    }

    @Override
    public void update() {
        pressed = isIn() && Input.keysMouse[GLFW.GLFW_MOUSE_BUTTON_1];
        if (pressed) onPress.onPress(this);
        this.texture.hasLayer(pressed);
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
