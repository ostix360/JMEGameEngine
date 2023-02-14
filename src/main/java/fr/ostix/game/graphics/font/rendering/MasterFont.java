package fr.ostix.game.graphics.font.rendering;

import fr.ostix.game.core.loader.*;
import fr.ostix.game.core.ressourceProcessor.*;
import fr.ostix.game.graphics.font.meshCreator.*;
import fr.ostix.game.graphics.model.*;

import java.util.*;

public class MasterFont {

    private static final Map<FontType, List<GUIText>> guisTexts = new HashMap<>();
    private static final Map<FontType, List<GUIText>> tempGuisTexts = new HashMap<>();
    private static Loader loader = Loader.INSTANCE;
    private final FontRenderer renderer;

    private static MasterFont instance;

    public MasterFont(Loader theLoader) {
        renderer = new FontRenderer();
        loader = theLoader;
        instance = this;
    }

    public static void add(GUIText text) {
        if (text.isEdited()) {
            GLRequestProcessor.sendRequest(new FontRequest(text,false,instance));
        }else{
            instance.addText(text,false);
        }
    }

    public static void addTempFont(GUIText text) {
        if (text.isEdited()) {
            GLRequestProcessor.sendRequest(new FontRequest(text,true,instance));
        }else{
            instance.addText(text,true);
        }
    }

    public void addText(GUIText text, boolean isTemp) {
        FontType font = text.getFont();
        if (isTemp){
            List<GUIText> textBatch = tempGuisTexts.computeIfAbsent(font, k -> new ArrayList<>());
            textBatch.add(text);
        }else{
            List<GUIText> textBatch = guisTexts.computeIfAbsent(font, k -> new ArrayList<>());
            textBatch.add(text);
        }
    }

    public void render() {
        addTempText();
        renderer.render(guisTexts);
        removeTempGuis();
    }

    private void addTempText() {
        for (FontType Tfont : tempGuisTexts.keySet()) {
            List<GUIText> textBatch = tempGuisTexts.get(Tfont);
            if (guisTexts.containsKey(Tfont)) {
                guisTexts.get(Tfont).addAll(textBatch);
            } else {
                guisTexts.put(Tfont, textBatch);
            }
        }
    }

    private void removeTempGuis() {
        for (FontType Tfont : tempGuisTexts.keySet()) {
            List<GUIText> texts = guisTexts.get(Tfont);
            List<GUIText> tTexts = tempGuisTexts.get(Tfont);
            texts.removeAll(tTexts);
            tTexts.clear();
            if (texts.isEmpty()) {
                guisTexts.remove(Tfont);
                tempGuisTexts.remove(Tfont);
            }
        }
    }

    public static void remove(GUIText text) {
        List<GUIText> texts = guisTexts.get(text.getFont());
        if (texts == null) {
            return;
        }
        texts.remove(text);
        if (texts.isEmpty()) {
            guisTexts.remove(text.getFont());
        }

    }

    public static void clear(){
        guisTexts.clear();
        tempGuisTexts.clear();
    }

    public void cleanUp() {
        tempGuisTexts.clear();
        guisTexts.clear();
        renderer.cleanUp();
    }


}
