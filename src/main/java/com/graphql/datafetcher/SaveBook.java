package com.graphql.datafetcher;

import com.graphql.model.Author;
import com.graphql.model.Book;
import com.graphql.repository.AuthorRepository;
import com.graphql.repository.BookRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class SaveBook implements DataFetcher<Book> {

    private static final Logger logger = LoggerFactory.getLogger(SaveBook.class);

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public Book get(DataFetchingEnvironment dataFetchingEnvironment) {

        logger.info("Entering get of SaveBook...");
        Map<String, Object> arguments = dataFetchingEnvironment.getArguments();
        Book book = new Book(String.valueOf(arguments.get("title")), String.valueOf(arguments.get("publisher")),
                String.valueOf(arguments.get("publishedDate")));
        LinkedHashMap<String, String> authorMap = ((List<LinkedHashMap<String, String>>)arguments.get("author")).get(0);
        Author author = new Author(authorMap.get("authorName"), authorMap.get("age"));

        authorRepository.save(author);
        book.setAuthors(Collections.singleton(author));
        return bookRepository.save(book);
    }
}
