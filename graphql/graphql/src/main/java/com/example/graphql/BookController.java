package com.example.graphql;

import com.example.graphql.externalservice.InternalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@Slf4j
public class BookController {

    @QueryMapping
    public Book bookById(@Argument String id) {

        log.info("book {} ", Book.getById(id));
        return Book.getById(id);
    }

    @QueryMapping
    public Author authorById(@Argument String id ) {
        log.info("author {} ", Author.getById(id));
        return Author.getById(id);
    }

    @QueryMapping
    public Author authorByIdAndFirstName(@Argument String id,@Argument String firstName ) {
        log.info("id {} firstName {} ", id, firstName);
        return Author.getById(id);
    }

    @SchemaMapping
    public Author author(Book book){
        return Author.getById(book.authorId());
    }

    @SchemaMapping(typeName = "Book", field = "discount")
    public Integer getDiscount(Book book){
        if ("Effective Java".equals(book.name())){
            return 30;
        } else {
            return 0;
        }
    }

@Autowired
    InternalService internalService;

    @GetMapping("/test")
    public ResponseEntity test(){
log.info("Log in test");
        return ResponseEntity.ok(internalService.getInternalData2());
    }

}
