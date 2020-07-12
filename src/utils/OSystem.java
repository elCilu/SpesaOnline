package utils;

import sample.GlobalVars;

public class OSystem {
    public static boolean isWindows() {

        return (GlobalVars.OS.indexOf("win") >= 0);

    }

    public static boolean isMac() {

        return (GlobalVars.OS.indexOf("mac") >= 0);

    }

    public static boolean isUnix() {

        return (GlobalVars.OS.indexOf("nix") >= 0 || GlobalVars.OS.indexOf("nux") >= 0 || GlobalVars.OS.indexOf("aix") > 0);

    }
}
