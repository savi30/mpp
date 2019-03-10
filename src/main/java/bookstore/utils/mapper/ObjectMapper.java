package bookstore.utils.mapper;

import com.google.common.base.CaseFormat;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ObjectMapper<T> {
    private Class clazz;
    private List<Field> fields = new ArrayList<>();

    public ObjectMapper(Class clazz) {
        this.clazz = clazz;

        while (clazz != null && clazz != Object.class) {
            this.fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        fields.forEach(field -> field.setAccessible(true));
    }

    public Optional<T> map(ResultSet resultSet) {
        T dto = null;
        try {
            dto = (T) clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            Logger.getLogger(ObjectMapper.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        T result = dto;
        fields.forEach(field -> {
            String name = adaptFiledName(field.getName());
            try {
                Object value = resultSet.getObject(name);
                field.set(result, value);
            } catch (SQLException | IllegalAccessException e) {
                Logger.getLogger(ObjectMapper.class.getName()).log(Level.INFO, e.getMessage());
            }
        });
        return Optional.of(Objects.requireNonNull(result));
    }

    private String adaptFiledName(String name) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
    }
}
