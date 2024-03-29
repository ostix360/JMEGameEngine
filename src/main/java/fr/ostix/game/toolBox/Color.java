package fr.ostix.game.toolBox;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

public class Color {


    //private static final Logger LOGGER = LogManager.getLogger(Color.class);

    public static final Color WHITE = new Color(1, 1, 1);
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color RED = new Color(1, 0, 0);
    public static final Color BLUE = new Color(0, 0, 1);
    public static final Color GREEN = new Color(0, 1, 0);
    public static final Color YELLOW = new Color(1, 1, 0);
    public static final Color CYAN = new Color(0, 1, 1);
    public static final Color MAGENTA = new Color(1, 0, 1);
    public static final Color GRAY = new Color(0.5f, 0.5f, 0.5f);
    public static final Color SUN = new Color(1.5f, 1.25f, 1.45f);
    public static final Color NONE = new Color(0, 0, 0, 0);


    private float red;
    private float green;
    private float blue;
    private float alpha;

    public Color(float red, float green, float blue) {
        this(red, green, blue, 1);
    }

    public Color(float red, float green, float blue, float alpha) {
        this.red = Maths.clampf(red, 0, 10);
        this.green = Maths.clampf(green, 0, 10);
        this.blue = Maths.clampf(blue, 0, 10);
        this.alpha = Maths.clampf(alpha, 0, 1);
    }

    public Color(float red, float green,float blue, boolean convert) {
        if (convert){
            this.red = (red / 255.0F);
            this.green = (green / 255.0F);
            this.blue = (blue / 255.0F);
        }else{
            this.red = Maths.clampf(red, 0, 10);
            this.green = Maths.clampf(green, 0, 10);
            this.blue = Maths.clampf(blue, 0, 10);
        }

    }

    public float[] getFloatArray() {
        return new float[]{red, green, blue, alpha};
    }

    public static float[] getFloatVec4Array(Color c1, Color c2, Color c3, Color c4) {
        List<Float> colors = new ArrayList<>();
        float[] floats = new float[16];
        for (float f : c1.getFloatArray()) {
            colors.add(f);
        }
        for (float f : c2.getFloatArray()) {
            colors.add(f);
        }
        for (float f : c3.getFloatArray()) {
            colors.add(f);
        }
        for (float f : c4.getFloatArray()) {
            colors.add(f);
        }
        for (int i = 0; i < colors.size(); i++) {
            //LOGGER.debug(i + " color : "+colors.get(i));
            floats[i] = colors.get(i);
        }
        return floats;
    }

    public void set(float r, float g, float b, float a) {
        this.red = r;
        this.green = b;
        this.blue = b;
        this.alpha = a;
    }

    public float getRed() {
        return red;
    }

    public float getGreen() {
        return green;
    }

    public float getBlue() {
        return blue;
    }

    public float getAlpha() {
        return alpha;
    }

    public static Color getInterpolatedColor(Color c1, Color c2, float blendFactor) {
        float r1 = c1.getRed() * (1.0F - blendFactor);
        float g1 = c1.getGreen() * (1.0F - blendFactor);
        float b1 = c1.getBlue() * (1.0F - blendFactor);
        float a1 = c1.getAlpha() * (1.0F - blendFactor);
        float r2 = c2.getRed() * blendFactor;
        float g2 = c2.getGreen() * blendFactor;
        float b2 = c2.getBlue() * blendFactor;
        float a2 = c2.getAlpha() * blendFactor;
        return new Color(r1 + r2, g1 + g2, b1 + b2, a1 + a2);
    }

    @Override
    public String toString() {
        return "Color{" +
                "red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                ", alpha=" + alpha +
                '}';
    }

    public Vector3f getVec3f() {
        return new Vector3f(this.red, this.green, this.blue);
    }

    public Vector4f getVec4f() {
        return new Vector4f(this.red, this.green, this.blue, this.alpha);
    }
}

