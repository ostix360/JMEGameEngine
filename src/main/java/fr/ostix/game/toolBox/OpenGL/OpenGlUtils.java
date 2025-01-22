package fr.ostix.game.toolBox.OpenGL;

import fr.ostix.game.toolBox.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GLDebugMessageCallback;

public class OpenGlUtils {


    private static boolean cullingBackFace = false;
    private static boolean inWireframe = false;
    private static boolean isAlphaBlending = false;
    private static boolean additiveBlending = false;
    private static boolean antialiasing = false;
    private static boolean depthTesting = false;

    public static void antialias(boolean enable) {
        if (enable && !antialiasing) {
            GL11.glEnable(GL13.GL_MULTISAMPLE);
            Logger.errGL("Error while enabling antialiasing");
            antialiasing = true;
        } else if (!enable && antialiasing) {
            GL11.glDisable(GL13.GL_MULTISAMPLE);
            Logger.errGL("Error while disabling antialiasing");
            antialiasing = false;
        }
    }

    public static void enableAlphaBlending() {
        if (!isAlphaBlending) {
            GL11.glEnable(GL11.GL_BLEND);
            Logger.errGL("Error while enabling blend");
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            Logger.errGL("Error while enabling alpha blend");
            isAlphaBlending = true;
            additiveBlending = false;
        }
    }

    public static void enableAdditiveBlending() {
        if (!additiveBlending) {
            GL11.glEnable(GL11.GL_BLEND);
            Logger.errGL("Error while enabling blend");
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            Logger.errGL("Error while enabling additive blend");
            additiveBlending = true;
            isAlphaBlending = false;
        }
    }

    public static void disableBlending() {
        if (isAlphaBlending || additiveBlending) {
            GL11.glDisable(GL11.GL_BLEND);
            Logger.errGL("Error while disabling blend");
            isAlphaBlending = false;
            additiveBlending = false;
        }
    }

    public static void enableDepthTesting(boolean enable) {
        if (enable && !depthTesting) {
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            Logger.errGL("Error while enabling depth testing");
            depthTesting = true;
        } else if (!enable && depthTesting) {
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            Logger.errGL("Error while disabling depth testing");
            depthTesting = false;
        }
    }

    public static void cullBackFaces(boolean cull) {
        if (cull && !cullingBackFace) {
            GL11.glEnable(GL11.GL_CULL_FACE);
            Logger.errGL("Error while enabling culling");
            GL11.glCullFace(GL11.GL_BACK);
            Logger.errGL("Error while enabling culling back faces");
            cullingBackFace = true;
        } else if (!cull && cullingBackFace) {
            GL11.glDisable(GL11.GL_CULL_FACE);
            Logger.errGL("Error while disabling culling");
            cullingBackFace = false;
        }
    }

    public static void goWireframe(boolean goWireframe) {
        if (goWireframe && !inWireframe) {
            GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
            Logger.errGL("Error while enabling wireframe");
            inWireframe = true;
        } else if (!goWireframe && inWireframe) {
            GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
            Logger.errGL("Error while disabling wireframe");
            inWireframe = false;
        }
    }

    public static void clearGL() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        Logger.errGL("Error while clearing color buffer");
    }
}
