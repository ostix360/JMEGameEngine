package fr.ostix.game.core.loader.json;

import com.google.gson.*;
import fr.ostix.game.core.quest.*;
import fr.ostix.game.core.quest.serialization.*;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;

public class JsonUtils {

    public static Gson gsonInstance() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting().registerTypeAdapter(Rewards.class, new RewardsTypeAdapter());
        return gsonBuilder.create();
    }

    public static String loadJson(String jsonFile) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(Files.newInputStream(Paths.get(jsonFile)), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
