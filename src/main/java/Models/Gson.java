package Models;

import com.google.gson.GsonBuilder;

public enum Gson {
    INSTANCE(new GsonBuilder().setPrettyPrinting().create());
    private final com.google.gson.Gson gson;
    Gson(com.google.gson.Gson gson) {
        this.gson = gson;
    }

    public com.google.gson.Gson get() {
        return gson;
    }
}
