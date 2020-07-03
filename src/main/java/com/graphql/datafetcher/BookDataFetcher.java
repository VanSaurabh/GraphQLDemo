package com.graphql.datafetcher;

import com.graphql.model.Book;
import com.graphql.repository.BookRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookDataFetcher implements DataFetcher<Book> {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book get(DataFetchingEnvironment dataFetchingEnvironment) {
        Long bookId = Long.valueOf(dataFetchingEnvironment.getArgument("id"));
        return bookRepository.findById(bookId).orElse(null);
    }
}
