package fr.ostix.game.core.ressourceProcessor;

public class GLRequestProcessor {
    private static final int MAX_REQUEST_TIME = 12;
    private static final GLRequestQueue requestQueue = new GLRequestQueue();
    private static boolean isRunning = false;
    private static boolean forceRequest = false;

    public synchronized static void sendRequest(GLRequest... request) {
        requestQueue.addAllRequest(request);
    }

    public synchronized static void executeRequest() {
        long remainingTime = MAX_REQUEST_TIME * 1_000_000_000L;
        long start = System.nanoTime();
        isRunning = true;
        while (requestQueue.hasRequests()) {
            requestQueue.acceptNextRequest().execute();
            long end = System.nanoTime();
            long timeTaken = end - start;
            remainingTime -= (float) timeTaken;
            start = end;
            if (!forceRequest && remainingTime < 0.0) {
                break;
            }
        }
        isRunning = false;
    }

    public synchronized static void forceRequest() {
        forceRequest = true;
    }

    public synchronized static boolean isRunning() {
        return isRunning;
    }


}
