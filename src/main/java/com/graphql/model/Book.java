package com.graphql.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String publisher;
    private String publishedDate;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_author", joinColumns = {@JoinColumn(name="id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "author_id", referencedColumnName = "id")})
    private Set<Author> authors = new HashSet<>();

    public Book(String title, String publisher, String publishedDate){
        this.title = title;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
    }
}
