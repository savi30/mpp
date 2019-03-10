package bookstore.utils.mapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObjectMapper<T> {
    private Class clazz;
    private List<Field> fields = new ArrayList<>();

    public ObjectMapper(Class clazz) {
        this.clazz = clazz;

        this.fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
    }
}
