package bookstore.web.controllers;

import bookstore.core.domain.book.Book;
import bookstore.core.service.book.BookService;
import bookstore.web.dto.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private ConversionService conversionService;

    @RequestMapping(method = RequestMethod.GET)
    public List<BookDto> get() {
        return StreamSupport.stream(bookService.findAll().spliterator(), false)
                            .map(book -> conversionService.convert(book, BookDto.class)).collect(
                        Collectors.toList());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BookDto getById(@PathVariable String id) {
        return conversionService.convert(bookService.findById(id).get(), BookDto.class);
    }

    @RequestMapping(value = "/author/{author}", method = RequestMethod.GET)
    public List<BookDto> getByAuthor(@PathVariable String author) {
        return bookService.filterBooksByAuthor(author).stream()
                          .map(book -> conversionService.convert(book, BookDto.class)).collect(
                        Collectors.toList());
    }

    @RequestMapping(value = "/title/{title}", method = RequestMethod.GET)
    public List<BookDto> getByTitle(@PathVariable String title) {
        return bookService.filterBooksByTitle(title).stream()
                          .map(book -> conversionService.convert(book, BookDto.class)).collect(
                        Collectors.toList());
    }

    @RequestMapping(value = "/quantity/{quantity}", method = RequestMethod.GET)
    public List<BookDto> getByQuantity(@PathVariable Integer quantity) {
        return bookService.filterBooksByQuantity(quantity).stream()
                          .map(book -> conversionService.convert(book, BookDto.class)).collect(
                        Collectors.toList());
    }

    @RequestMapping(value = "/price/{p1}{p2}", method = RequestMethod.GET)
    public List<BookDto> getsByPrice(@PathVariable Double p1, @PathVariable Double p2) {
        return bookService.filterBooksByPrice(p1, p2).stream()
                          .map(book -> conversionService.convert(book, BookDto.class)).collect(
                        Collectors.toList());
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public BookDto update(@RequestBody BookDto dto) {
        return conversionService
                .convert(bookService.update(conversionService.convert(dto, Book.class)), BookDto.class);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(@RequestBody BookDto dto) {
        bookService.save(conversionService.convert(dto, Book.class));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id) {
        bookService.delete(id);
    }

}
