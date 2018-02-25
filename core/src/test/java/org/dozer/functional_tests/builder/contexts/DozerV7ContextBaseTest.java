package org.dozer.functional_tests.builder.contexts;

import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;
import org.dozer.loader.api.TypeMappingOptions;
import org.dozer.vo.cumulative.*;
import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.dozer.loader.api.FieldsMappingOptions.context;
import static org.dozer.loader.api.FieldsMappingOptions.hintA;
import static org.dozer.loader.api.FieldsMappingOptions.hintB;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DozerV7ContextBaseTest {

    private final Mapper mapper = initMapper();

    private static Mapper initMapper() {
        return DozerBeanMapperBuilder.create()
                .withMappingBuilder(new V7ContextMapping())
                .build();
    }

    @Test
    public void defaultContext() {
        Library library = makeLibrary();

        LibraryPrime libraryPrime = mapper.map(library, LibraryPrime.class);
        assertNull(((BookPrime) libraryPrime.getBooks().get(0)).getAuthor().getName());
        assertNull(((BookPrime) libraryPrime.getBooks().get(1)).getAuthor().getName());
    }

    @Test
    public void fullContext() {
        Library library = makeLibrary();

        LibraryPrime libraryPrime = mapper.map(library, LibraryPrime.class, "full");
        assertEquals("author", ((BookPrime) libraryPrime.getBooks().get(0)).getAuthor().getName());
        assertEquals("author", ((BookPrime) libraryPrime.getBooks().get(1)).getAuthor().getName());
    }

    private Library makeLibrary() {
        Library library = new Library();
        Author author = new Author("author", 1L);
        Book book1 = new Book(10L, author);
        Book book2 = new Book(11L, author);
        library.setBooks(asList(book1, book2));
        return library;
    }

    private static class V7ContextMapping extends BeanMappingBuilder {
        @Override
        protected void configure() {
            mapping(Library.class, LibraryPrime.class)
                    .fields("books", "books", hintA(Book.class), hintB(BookPrime.class));

            mapping(Book.class, BookPrime.class)
                    .fields("id", "id")
                    .fields("author", "author");

            mapping(Author.class, AuthorPrime.class)
                    .fields("id", "id")
                    .fields("name", "name", context("full"));
        }
    }
}
