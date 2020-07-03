package com.graphql;

import com.graphql.model.Author;
import com.graphql.model.Book;
import com.graphql.repository.AuthorRepository;
import com.graphql.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Collections;
import java.util.stream.Stream;

@SpringBootApplication
public class GraphQlDemoApplication {

	private static BookRepository bookRepository;
	private static AuthorRepository authorRepository;

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(GraphQlDemoApplication.class, args);
		bookRepository = applicationContext.getBean(BookRepository.class);
		authorRepository = applicationContext.getBean(AuthorRepository.class);
		loadDataIntoDb();
	}

	private static void loadDataIntoDb() {

		Book book1 = new Book("Three mistakes of my life", "Rupa publications", "2008");

		Author author1 = new Author("Chetan Bhagat", "46");

		Stream.of(author1).forEach(author -> authorRepository.save(author));
		book1.setAuthors(Collections.singleton(author1));
		Stream.of(book1).forEach(book -> bookRepository.save(book));
	}
}
