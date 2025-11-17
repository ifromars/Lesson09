package lib;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvConfig {
    private static final Dotenv dotenv = Dotenv.configure()
            .directory("./")
            .ignoreIfMalformed()
            .ignoreIfMissing()
            .load();

    public static String getWikipediaLogin() {
        return dotenv.get("WIKIPEDIA_LOGIN", "User");
    }

    public static String getWikipediaPassword() {
        return dotenv.get("WIKIPEDIA_PASSWORD", "Pass");
    }
}