package de.nurrobin.util;

public class Logger {
    private String className;

    public Logger(Class<?> clazz) {
        this.className = clazz.getName();
    }

    public void log(String message) {
        System.out.println("[" + className + "] " + message);
    }

    public void logError(String message) {
        System.err.println("[" + className + "] " + message);
    }

    public void logError(String message, Throwable throwable) {
        System.err.println("[" + className + "] " + message);
        throwable.printStackTrace();
    }

    public void logWarning(String message) {
        System.out.println("WARNING: [" + className + "] " + message);
    }

    public void logInfo(String message) {
        System.out.println("INFO: [" + className + "] " + message);
    }

    public void logDebug(String message) {
        System.out.println("DEBUG: [" + className + "] " + message);
    }

    public void logTrace(String message) {
        System.out.println("TRACE: [" + className + "] " + message);
    }

    public void logException(Throwable throwable) {
        System.err.println("[" + className + "] " + throwable.getMessage());
        throwable.printStackTrace();
    }
}