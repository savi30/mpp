package bookstore.utils.reader;

import bookstore.utils.domain.user.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Arrays;
import java.util.List;

public class UserReader implements Reader<User> {
    public User get(String line){
        List<String> items = Arrays.asList(line.split(","));

        String id = String.valueOf(items.get(0));
        String name = items.get(1);

        return new User(id, name);
    }

    public User get(Element node){
        String id = node.getAttribute("id");
        String name = getTextFromTagName(node, "name");
        return new User(id, name);
    }

    private static String getTextFromTagName(Element element, String tagName) {
        NodeList elements = element.getElementsByTagName(tagName);
        Node node = elements.item(0);
        return node.getTextContent();
    }

    public void addElement(Document document, Element root, User user){
        Element userElement = document.createElement("user");
        userElement.setAttribute("id", user.getId());
        root.appendChild(userElement);
        appendChildWithText(document, userElement, "id", user.getId());
        appendChildWithText(document, userElement, "name", user.getName());
    }

    private static void appendChildWithText(Document document,
                                              Node parent, String tagName, String textContent) {
        Element element = document.createElement(tagName);
        element.setTextContent(textContent);
        parent.appendChild(element);
    }

    public String getTag(){
        return "user";
    }
}
