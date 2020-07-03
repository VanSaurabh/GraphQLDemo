package com.graphql.service;

import com.graphql.controller.GraphQLController;
import com.graphql.datafetcher.*;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Service
public class GraphQLService {

    private static final Logger logger = LoggerFactory.getLogger(GraphQLController.class);

    @Value("classpath:books.graphql")
    private Resource resource;

    @Autowired
    private AllBooksDataFetcher allBooksDataFetcher;
    @Autowired
    private AuthorDataFetcher authorDataFetcher;
    @Autowired
    private BookDataFetcher bookDataFetcher;
    @Autowired
    private AllAuthorsDataFetcher allAuthorsDataFetcher;
    @Autowired
    private SaveBook saveBook;
    @Autowired
    private UpdateBook updateBook;

    private GraphQL graphQL;

    private void loadSchema() {
        logger.info("Entering loadSchema method of GraphQLService...");
        File file = null;
        try{
            file = resource.getFile();
        }catch (IOException ex) {
            logger.error("Got exception while reading schema file", ex);
        }
        if(Objects.nonNull(file)){
            TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(file);
            RuntimeWiring runtimeWiring = buildRuntimeWiring();
            GraphQLSchema graphQLSchema = new SchemaGenerator()
                    .makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
        }else {
            logger.error("Schema file not found...");
        }

    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(TypeRuntimeWiring.newTypeWiring("Query")
                .dataFetcher("allBooks", allBooksDataFetcher)
                .dataFetcher("book", bookDataFetcher)
                .dataFetcher("allAuthors", allAuthorsDataFetcher)
                .dataFetcher("author", authorDataFetcher)
                )
                .type(TypeRuntimeWiring.newTypeWiring("Mutation")
                .dataFetcher("addBook", saveBook)
                .dataFetcher("updateBook", updateBook)
                )
                .build();
    }

    public GraphQL getGraphQL() {
        logger.info("Entering getGraphQL method of GraphQLService...");
        loadSchema();
        return graphQL;
    }
}
