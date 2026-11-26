package com.meghana.parabank.utils;

import java.io.InputStream;
import java.util.Properties;

/** Reads src/test/resources/config.properties. Any value can be overridden with -Dkey=value. */
public final class Config {
    public static final String ROOT = "https://parabank.parasoft.com/parabank";
    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in != null) {
                PROPS.load(in);
            }
        } catch (Exception e) {
            System.out.println("config.properties could not be read: " + e.getMessage());
        }
    }

    private Config() { }

    private static String get(String key, String fallback) {
        return System.getProperty(key, PROPS.getProperty(key, fallback)).trim();
    }

    public static String homeUrl()  { return ROOT + "/index.htm"; }
    public static String username() { return get("parabank.username", "your_username"); }
    public static String password() { return get("parabank.password", "your_password"); }
    public static int timeout()     { return Integer.parseInt(get("timeout", "60")); }
    /** Waiting time for a result that should appear right after a click. */
    public static int quick()       { return Math.min(timeout(), 20); }
    public static boolean headless(){ return Boolean.parseBoolean(get("headless", "false")); }
    public static String browser()  { return get("browser", "chrome").toLowerCase(); }
}
