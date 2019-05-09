package bookstore.controllers;

import bookstore.domain.book.Book;
import bookstore.service.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Book> getBooks() {
        return (List<Book>) bookService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Book getById(@PathVariable String id) {
        return bookService.findById(id).get();
    }

    @RequestMapping(value = "/author/{author}", method = RequestMethod.GET)
    public List<Book> getBooksByAuthor(@PathVariable String author) {
        return (List<Book>) bookService.filterBooksByAuthor(author);
    }

    @RequestMapping(value = "/title/{title}", method = RequestMethod.GET)
    public List<Book> getBooksByTitle(@PathVariable String title) {
        return (List<Book>) bookService.filterBooksByTitle(title);
    }

    @RequestMapping(value = "/quantity/{quantity}", method = RequestMethod.GET)
    public List<Book> getBooksByQuantity(@PathVariable Integer quantity) {
        return (List<Book>) bookService.filterBooksByQuantity(quantity);
    }

    @RequestMapping(value = "/price/{p1}{p2}", method = RequestMethod.GET)
    public List<Book> getBooksByPrice(@PathVariable Double p1, @PathVariable Double p2) {
        return (List<Book>) bookService.filterBooksByPrice(p1, p2);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Book updateBook(@RequestBody Book book) {
        return bookService.update(book);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void saveBook(@RequestBody Book book) {
        bookService.save(book);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id) {
        bookService.delete(id);
    }

}

