package db;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseConfig {
    private static final Dotenv dotenv = Dotenv.configure()
            .directory(".")
            .load();

    public static String getURL(){
        return dotenv.get("DB_URL");
    }
    public static String getUser(){
        return dotenv.get("DB_USER");
    }
    public static String getPassword(){
        return dotenv.get("DB_PASSWORD");
    }
}
