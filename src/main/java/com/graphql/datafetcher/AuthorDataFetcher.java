package com.graphql.datafetcher;

import com.graphql.model.Author;
import com.graphql.repository.AuthorRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorDataFetcher implements DataFetcher<Author> {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Author get(DataFetchingEnvironment dataFetchingEnvironment) {
        Long authorId = Long.valueOf(dataFetchingEnvironment.getArgument("id"));
        return authorRepository.findById(authorId).orElse(null);
    }
}
