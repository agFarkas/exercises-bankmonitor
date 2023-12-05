package bankmonitor.converter;

import lombok.NonNull;

public abstract class AbstractConverter<F, T> {

    public abstract T convert(@NonNull F f);
}
