package bookstore.utils.builder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface Builder<T> {
    T get(String line);
    T get(Element node);
    void addElement(Document document, Element root, T entity);
    String getTag();
}
