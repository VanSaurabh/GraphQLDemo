package com.graphql.controller;

import com.graphql.model.Data;
import com.graphql.service.GraphQLService;
import graphql.ExecutionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GraphQLController {

    private static final Logger logger = LoggerFactory.getLogger(GraphQLController.class);

    @Autowired
    private GraphQLService graphQLService;

    @PostMapping("/graphql")
    public ResponseEntity<Object> getBooksAndAuthors(@RequestBody Data data){
        logger.info("Entering getBooksAndAuthors of GraphQLController");
        ExecutionResult result = graphQLService.getGraphQL().execute(data.getQuery());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
