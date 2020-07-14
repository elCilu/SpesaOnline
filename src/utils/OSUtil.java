package utils;

import sample.Global;

public class OSUtil {
    public static boolean isWindows() {
        return (Global.OS.contains("win"));
    }

    public static boolean isMac() {
        return (Global.OS.contains("mac"));
    }

    public static boolean isUnix() {
        return (Global.OS.contains("nix") || Global.OS.contains("nux") || Global.OS.indexOf("aix") > 0);
    }
}
