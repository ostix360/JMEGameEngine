package fr.ostix.game.core;

import fr.ostix.game.audio.*;
import fr.ostix.game.core.loader.*;
import fr.ostix.game.core.resources.*;
import fr.ostix.game.core.ressourceProcessor.*;
import fr.ostix.game.graphics.font.meshCreator.*;
import fr.ostix.game.graphics.font.rendering.*;
import fr.ostix.game.gui.*;
import fr.ostix.game.menu.*;
import fr.ostix.game.toolBox.*;
import fr.ostix.game.toolBox.OpenGL.*;
import org.lwjgl.openal.*;

import static org.lwjgl.glfw.GLFW.*;


public class Game extends Thread {
    public static FontType gameFont;

    private boolean running = false;

    private final StateManager stateManager;

    private final Loader loader;

    private Screen currentScreen;

    private MasterGui guiManager;
    private MasterFont masterFont;

    private long window;

    public Game() {
        super("Game");
        loader = new Loader();
        stateManager = new StateManager(loader);
    }


    public void start() {
        super.start();
        Logger.log("start");
        running = true;
    }


    float[] vertices = new float[]{
            -0.5f, 0.5f, 0,     //S 0
            -0.5f, -0.5f, 0,    //S 1
            0.5f, -0.5f, 0,     //S 2
            0.5f, 0.5f, 0       //S 3
    };

    int[] indices = new int[]{
            0, 1, 3,            // Triangles en haut a droite S 0,1,2
            3, 1, 2             // Triangles en bas a gauche S 1,3,2
    };

    float[] textureCoords = new float[]{
            0, 0,
            0, 1,
            1, 1,
            1, 0
    };

    private void init() {
        window = DisplayManager.createDisplay();


        AudioManager.init(AL11.AL_EXPONENT_DISTANCE);
        SoundSource source = AudioManager.loadSound("loading",true);
//        source.play();
        Input.init(window);
        guiManager = new MasterGui(loader);
        masterFont = new MasterFont(loader);

        glfwShowWindow(window);

        stateManager.init(guiManager);



    }


    public void run() {
        init();
        while (running) {
            if (glfwWindowShouldClose(window)) {
                exit();
                return;
            }
            loop();
        }
    }

    long timer = System.currentTimeMillis();
    long beforeUpdate = System.nanoTime();
    long beforeRender = System.nanoTime();
    double elapsedUpdate;
    double elapsedRender;
    double updateTime = 1_000_000_000.0 / 60;
    double renderTime = 1_000_000_000.0 / 60;
    int ticks = 0;
    int frames = 0;

    private void loop() {
        long now = System.nanoTime();
        elapsedUpdate = now - beforeUpdate;
        elapsedRender = now - beforeRender;

        if (elapsedUpdate > updateTime) {
            try {
                beforeUpdate += updateTime;
                update();
                ticks++;
            } catch (Exception e) {
                e.printStackTrace();
                exit();
            }
        } else if (elapsedRender > renderTime) {
            try {
                beforeRender += renderTime;
                render();
                DisplayManager.updateDisplay();
                frames++;
            } catch (Exception e) {
                e.printStackTrace();
                exit();
            }

        } else {
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (System.currentTimeMillis() - timer > 1000) {
            timer += 1000;
            glfwSetWindowTitle(window, currentScreen.getTitle() + "  ||  " + ticks + " ticks " + frames + " fps");
            ticks = 0;
            frames = 0;
        }
    }

    private void update() {
        Input.updateInput(window);
        GLRequestProcessor.executeRequest();
        currentScreen = stateManager.getCurrentState(stateManager.update());
        currentScreen.update();
        glfwPollEvents();
    }

    private void render() {
        if (currentScreen instanceof WorldState) {
            ((WorldState) currentScreen).render();
        } else {
            OpenGlUtils.clearGL();
        }
        guiManager.render();
        masterFont.render();
    }

    private void exit() {
        Logger.log("Exiting");
        AudioManager.cleanUp();
        masterFont.cleanUp();
        guiManager.cleanUp();
        stateManager.cleanUp();
        running = false;
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
