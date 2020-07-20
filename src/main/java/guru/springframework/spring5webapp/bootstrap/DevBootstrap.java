package guru.springframework.spring5webapp.bootstrap;

import guru.springframework.spring5webapp.model.Author;
import guru.springframework.spring5webapp.model.Book;
import guru.springframework.spring5webapp.model.Publisher;
import guru.springframework.spring5webapp.repositories.AuthorRepository;
import guru.springframework.spring5webapp.repositories.BookRepository;
import guru.springframework.spring5webapp.repositories.PublisherRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private PublisherRepository publisherRepository;

    public DevBootstrap(BookRepository bookRepository, AuthorRepository authorRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }

    private void initData() {
        Author maroun = new Author("Maroun", "Tawk");
        Publisher press = new Publisher("Press", "USA");
        Book vuejs = new Book("hands-on VueJs", "00-1121-1154", press);

        maroun.getBooks().add(vuejs);
        vuejs.getAuthors().add(maroun);

        authorRepository.save(maroun); // non class owner instance
        publisherRepository.save(press); // non class owner instance
        bookRepository.save(vuejs); // class owner instance, will add a record in the book table first, add a record in the author_books table also.

        Author hassan = new Author("Hassan", "Tawk");
        Publisher harper = new Publisher("Harper Colin", "USA");
        Book java = new Book("Spring-Boot", "00-6655-1154", harper);


        hassan.getBooks().add(java);
        java.getAuthors().add(hassan);
        authorRepository.save(hassan);
        publisherRepository.save(harper);
        bookRepository.save(java);

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }
}
