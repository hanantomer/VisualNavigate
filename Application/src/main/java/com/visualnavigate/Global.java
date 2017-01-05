package com.visualnavigate;

/**
 * Created by htomer on 12/19/2016.
 */
public class Global {

    public enum OperationMode {Recording, Navigating}

    private static Global ourInstance = new Global();

    public static Global getInstance() {
        return ourInstance;
    }

    public static OperationMode operationMode;

    public static float accuracy;

    public static double lastLatitude;

    public static double lastLongitude;

    public static String locationProvider;

    private Global() {
    }
}
