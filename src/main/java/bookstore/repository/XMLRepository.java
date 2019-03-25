package bookstore.repository;

import bookstore.domain.core.Entity;
import bookstore.utils.reader.Reader;
import bookstore.utils.validator.Validator;
import bookstore.utils.validator.exception.ValidationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

public class XMLRepository<ID extends Serializable, T extends Entity<ID>> extends InMemoryRepository<ID, T> {
    private String fileName;
    protected Reader<T> reader;

    public XMLRepository(Validator<T> validator, String fileName, Reader<T> reader) {
        super(validator);
        this.fileName = fileName;
        this.reader = reader;
        loadData();
    }

    /**
     * Load all entities from the file into memory.
     */
    private void loadData() {
        DocumentBuilderFactory documentBuilderFactory =
                DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(fileName);
            Element root = document.getDocumentElement();

            NodeList nodes = root.getChildNodes();
            int len = nodes.getLength();
            for (int i = 0; i < len; i++) {
                Node node = nodes.item(i);
                if (node instanceof Element) {
                    T b = reader.get((Element) node);
                    super.save(b);
                }
            }
        }catch (ParserConfigurationException | SAXException | IOException | ValidationException e){
            e.printStackTrace();
        }
    }

    /**
     * Add a entity and write changes to file.
     * @return an {@code Optional} encapsulating the added entity.
     */
    @Override
    public Optional<T> save(T entity) throws ValidationException {
        Optional<T> optional = super.save(entity);
        optional.ifPresentOrElse(opt -> {}, () -> saveToXML(entity));
        return optional;
    }

    /**
     * Delete a entity and write changes to file.
     * @return an {@code Optional} encapsulating the deleted entity or empty if there is no such entity.
     */
    @Override
    public Optional<T> delete(ID id){
        Optional<T> optional = super.delete(id);
        optional.ifPresent(opt -> deleteFromXML(id));
        return optional;
    }

    /**
     * Update a entity and write changes to file.
     * @return an {@code Optional} encapsulating the updated entity or empty if there is no such entity.
     */
    @Override
    public Optional<T> update(T entity)throws ValidationException {
        Optional<T> optional = super.update(entity);
        optional.ifPresent(opt -> updateXML(entity));
        return optional;
    }

    protected void updateXML(T entity){
        try {
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(fileName);
            ID id = entity.getId();
            Element root = document.getDocumentElement();
            NodeList elements = root.getElementsByTagName(reader.getTag());
            int len = elements.getLength();
            for (int i = 0; i < len; i++) {
                Node element = elements.item(i);
                if (element instanceof Element && ((Element) element).getAttribute("id").equals(id)) {
                    element.getParentNode().removeChild(element);
                    reader.addElement(document, root, entity);
                    break;
                }
            }
            Transformer transformer =
                    TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(root),
                    new StreamResult(new FileOutputStream(fileName)));
        }catch (ParserConfigurationException | SAXException | TransformerException | IOException  e){
            e.printStackTrace();
        }
    }

    /**
     * Delete entity from file
     * @param id - id of the entity to delete
     */
    private void deleteFromXML(ID id){
        try {
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(fileName);
            Element root = document.getDocumentElement();
            NodeList elements = root.getElementsByTagName(reader.getTag());
            int len = elements.getLength();
            for (int i = 0; i < len; i++) {
                Node element = elements.item(i);
                if (element instanceof Element && ((Element) element).getAttribute("id").equals(id)) {
                    element.getParentNode().removeChild(element);
                    break;
                }
            }
            Transformer transformer =
                    TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(root),
                    new StreamResult(new FileOutputStream(fileName)));
        }catch (ParserConfigurationException | SAXException | TransformerException | IOException  e){
            e.printStackTrace();
        }
    }

    /**
     * Write entity to file.
     */
    protected void saveToXML(T entity){
        try {
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(fileName);
            Element root = document.getDocumentElement();

            reader.addElement(document, root, entity);

            Transformer transformer =
                    TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(root),
                    new StreamResult(new FileOutputStream(fileName)));
        }catch (ParserConfigurationException | SAXException | IOException | TransformerException e){
            e.printStackTrace();
        }
    }
}
