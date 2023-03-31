package fr.ostix.game.core.loader.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonUtils {

    public static Gson gsonInstance() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        return gsonBuilder.create();
    }

      public static Gson gsonInstance(Class<?> type, TypeAdapter<?> serializer, boolean needToExcludeSomeFields) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        if (needToExcludeSomeFields) gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        return gsonBuilder.registerTypeAdapter(type, serializer)
                .create();
    }

    public static Gson gsonInstance(Class<?> type,  boolean needToExcludeSomeFields, Object... serializers) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        if (needToExcludeSomeFields) gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        for (Object serializer : serializers) {
            gsonBuilder.registerTypeAdapter(type, serializer);
        }
        return gsonBuilder.create();
    }

    public static Gson gsonInstance(Class<?> type, JsonSerializer<?> serializer, boolean needToExcludeSomeFields) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        if (needToExcludeSomeFields) gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        return gsonBuilder.registerTypeAdapter(type, serializer)
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    public static Gson gsonInstance(boolean needToExcludeSomeFields) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        if (needToExcludeSomeFields) gsonBuilder.excludeFieldsWithoutExposeAnnotation();
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
            return null;
        }
        return sb.toString();
    }

    public static void saveJson(String name, String content) {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(name))) {
            br.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
