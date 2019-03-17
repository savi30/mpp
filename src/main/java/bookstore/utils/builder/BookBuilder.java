package bookstore.utils.builder;

import bookstore.domain.book.Book;
import bookstore.domain.core.NamedEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BookBuilder implements Builder<Book> {
    public Book get(String line){
        List<String> items = Arrays.asList(line.split(","));

        String id = String.valueOf(items.get(0));
        String title = items.get(1);
        List<String> authors = Arrays.asList(items.get(2).split(" "));
        Timestamp date = Timestamp.valueOf(items.get(3));
        Integer quantity = Integer.valueOf(items.get(5));
        Double price = Double.valueOf(items.get(4));
        Book book = new Book(id, title);
        book.setAuthors(authors.stream().map(a -> new NamedEntity(1, a))
                .collect(Collectors.toList()));
        book.setPublishYear(date);
        book.setPrice(price);
        book.setQuantity(quantity);
        return book;
    }

    public Book get(Element bookNode){
        String id = bookNode.getAttribute("id");
        String title = getTextFromTagName(bookNode, "title");
        Book book = new Book(id, title);
        book.setId(id);

        String authors = getTextFromTagName(bookNode, "authors");
        book.setAuthors(Arrays.stream(authors.split(";"))
                .map(a -> new NamedEntity(1, a))
                .collect(Collectors.toList()));
        book.setPublishYear(Timestamp.valueOf(getTextFromTagName(bookNode, "year")));
        book.setPrice(Double.valueOf(getTextFromTagName(bookNode, "price")));
        book.setQuantity(Integer.valueOf(getTextFromTagName(bookNode, "quantity")));
        return book;
    }

    private static String getTextFromTagName(Element element, String tagName) {
        NodeList elements = element.getElementsByTagName(tagName);
        Node node = elements.item(0);
        return node.getTextContent();
    }

    public void addElement(Document document, Element root, Book book){
        Element bookElement = document.createElement("book");
        bookElement.setAttribute("id", book.getId());
        root.appendChild(bookElement);

        appendChildWithText(document, bookElement, "title", book.getTitle());
        appendChildWithText(document, bookElement, "authors", book.getAuthorsString());
        appendChildWithText(document, bookElement, "year",
                String.valueOf(book.getPublishYear()));
        appendChildWithText(document, bookElement, "price",
                String.valueOf(book.getPrice()));
        appendChildWithText(document, bookElement, "quantity",
                String.valueOf(book.getQuantity()));
    }

    private static void appendChildWithText(Document document,
                                            Node parent, String tagName, String textContent) {
        Element element = document.createElement(tagName);
        element.setTextContent(textContent);
        parent.appendChild(element);
    }

    public String getTag(){
        return "book";
    }
}
