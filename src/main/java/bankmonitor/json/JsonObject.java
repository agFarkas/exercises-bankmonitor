package bankmonitor.json;

import lombok.NonNull;
import org.json.JSONObject;

import java.util.Optional;
import java.util.function.BiFunction;

public class JsonObject {

    private final JSONObject internalJsonObject;

    public JsonObject(@NonNull String jsonText) {
        this.internalJsonObject = new JSONObject(jsonText.trim());
    }

    public Optional<String> getString(@NonNull String key) {
        if (internalJsonObject.has(key)) {
            return Optional.ofNullable(internalJsonObject.getString(key));
        }

        return Optional.empty();
    }

    public Optional<Integer> getInt(@NonNull String key) {
        if (internalJsonObject.has(key)) {
            return Optional.of(internalJsonObject.getInt(key));
        }

        return Optional.empty();
    }

    public void put(@NonNull String key, Object value) {
        internalJsonObject.put(key, value);
    }

    public void copyIfExists(@NonNull JsonObject target, @NonNull String key, @NonNull BiFunction<JsonObject, String, Optional<?>> reader) {
        reader.apply(this, key)
                .ifPresent(value -> target.put(key, value));
    }

    @Override
    public String toString() {
        return this.internalJsonObject.toString();
    }
}
