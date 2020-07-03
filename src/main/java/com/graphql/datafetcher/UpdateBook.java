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

import java.util.*;

@Component
public class UpdateBook implements DataFetcher<Book> {

    private static final Logger logger = LoggerFactory.getLogger(SaveBook.class);

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public Book get(DataFetchingEnvironment dataFetchingEnvironment) {
        Map<String, Object> arguments = dataFetchingEnvironment.getArguments();

        Set<Author> authorSet = new HashSet<>();
        List<LinkedHashMap<String, String>> authorsList;

        if(Objects.nonNull(arguments.get("authors"))) {
            authorsList = (List<LinkedHashMap<String, String>>) arguments.get("authors");
            authorsList.forEach(authorDetails -> {
                Long authorId = Long.valueOf(authorDetails.get("authorId"));
                Author author = authorRepository.findById(authorId).orElse(null);
                if(Objects.nonNull(author)){
                    author.setAuthorName(authorDetails.get("authorName"));
                    author.setAge(authorDetails.get("age"));
                }
                authorSet.add(author);
            });
        }
        Long bookId = Long.valueOf(arguments.get("bookId").toString());
        Book book = bookRepository.findById(bookId).orElse(null);
        if(Objects.nonNull(book)){
            book.setTitle(String.valueOf(arguments.get("title")));
            book.setPublisher(String.valueOf(arguments.get("publisher")));
            book.setPublishedDate(String.valueOf(arguments.get("publishedDate")));
            book.setAuthors(authorSet);
            bookRepository.save(book);
        }
        return book;
    }
}
