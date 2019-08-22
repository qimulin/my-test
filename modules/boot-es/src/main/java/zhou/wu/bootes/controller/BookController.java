package zhou.wu.bootes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import zhou.wu.bootes.models.Book;
import zhou.wu.bootes.repositories.BookRepository;
import java.util.List;

/**
 * Created by lin.xc on 2019/8/2
 */
@RequestMapping("/book")
@RestController
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @RequestMapping(value = "/add_index", method = RequestMethod.POST)
    public ResponseEntity<String> indexDoc(@RequestBody Book book) {
        System.out.println("book===" + book);
        bookRepository.save(book);
        return new ResponseEntity<>("save executed!", HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<Iterable> getAll() {
        Iterable<Book> all = bookRepository.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ResponseEntity<Book> getByName(@Param("name") String name) {
        Book book = bookRepository.findByName(name);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @RequestMapping(value = "/finds", method = RequestMethod.GET)
    public ResponseEntity<Iterable> getByAuthor(@Param("author") String author) {
        List<Book> books = bookRepository.findByAuthor(author);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Book> updateBook(@PathVariable("id") String id,
                                           @RequestBody Book updateBook) {
        Book book = bookRepository.findBookById(id);
        if (!StringUtils.isEmpty(updateBook.getId())) {
            book.setId(updateBook.getId());
        }
        if (!StringUtils.isEmpty(updateBook.getName())) {
            book.setName(updateBook.getName());
        }
        if (!StringUtils.isEmpty(updateBook.getAuthor())) {
            book.setAuthor(updateBook.getAuthor());
        }
        bookRepository.save(book);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteBook(@PathVariable("id") String id) {
        bookRepository.deleteById(id);
        return new ResponseEntity<>("delete execute!", HttpStatus.OK);
    }
}
