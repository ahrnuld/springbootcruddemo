package nl.inholland.cruddemo.controller;

import nl.inholland.cruddemo.model.Book;
import nl.inholland.cruddemo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @GetMapping({"", "/", "/index"})
    public String index(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "book/index";
    }

    @GetMapping("/add")
    public String add(Book book) {
        return "book/add";
    }

    @PostMapping("/add")
    public String add(@Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "book/add";
        }

        bookRepository.save(book);
        return "redirect:/index";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") long id, Model model) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));

        model.addAttribute("book", book);
        return "book/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") long id, @Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            book.setId(id);
            return "book/edit";
        }

        bookRepository.save(book);
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        bookRepository.delete(book);
        return "redirect:/index";
    }
}
