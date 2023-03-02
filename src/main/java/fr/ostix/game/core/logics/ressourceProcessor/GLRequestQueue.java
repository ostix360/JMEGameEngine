package fr.ostix.game.core.logics.ressourceProcessor;

import java.util.*;

public class GLRequestQueue {

    private final List<GLRequest> requestQueue = new ArrayList<>();

    public synchronized void addRequest(GLRequest request) {
        this.requestQueue.add(request);
    }

    public synchronized void addAllRequest(GLRequest... request) {
        this.requestQueue.addAll(Arrays.asList(request));
    }



    public synchronized GLRequest acceptNextRequest() {
        return this.requestQueue.remove(0);
    }

    public synchronized boolean hasRequests() {
        return !this.requestQueue.isEmpty();
    }
}
