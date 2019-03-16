package bookstore.utils.reader;

import bookstore.domain.logs.LogsEntry;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class LogReader implements Reader<LogsEntry> {
    @Override
    public LogsEntry get(String line) {
        List<String> items = Arrays.asList(line.split(" "));

        String id = String.valueOf(items.get(0));
        String userId = items.get(1);
        String bookId = items.get(2);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = null;
        try {
            parsedDate = simpleDateFormat.parse(items.get(3));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp transactionDate = new Timestamp(parsedDate.getTime());

        return new LogsEntry(id, userId, bookId, transactionDate);
    }

    @Override
    public LogsEntry get(Element node) {
        String id = node.getAttribute("id");
        String clientId = getTextFromTagName(node, "clientId");
        String bookId = getTextFromTagName(node, "bookId");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = null;
        try {
            parsedDate = simpleDateFormat.parse(getTextFromTagName(node, "transactionDate"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp transactionDate = new Timestamp(parsedDate.getTime());
        LogsEntry logsEntry = new LogsEntry(id, clientId, bookId, transactionDate);
        return logsEntry;
    }

    private static String getTextFromTagName(Element element, String tagName) {
        NodeList elements = element.getElementsByTagName(tagName);
        Node node = elements.item(0);
        return node.getTextContent();
    }

    @Override
    public void addElement(Document document, Element root, LogsEntry entity) {
        Element element = document.createElement("logEntry");
        element.setAttribute("id", entity.getId());
        root.appendChild(element);
        appendChildWithText(document, element, "clientId", entity.getClientId());
        appendChildWithText(document, element, "bookId", entity.getBookId());
        appendChildWithText(document, element, "year",
                String.valueOf(entity.getTransactionDate()));
    }

    private static void appendChildWithText(Document document,
                                            Node parent, String tagName, String textContent) {
        Element element = document.createElement(tagName);
        element.setTextContent(textContent);
        parent.appendChild(element);
    }

    @Override
    public String getTag() {
        return "logEntry";
    }
}
