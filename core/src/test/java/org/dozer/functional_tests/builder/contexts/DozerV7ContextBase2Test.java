package org.dozer.functional_tests.builder.contexts;

import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;
import org.dozer.classmap.RelationshipType;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.vo.cumulative.*;
import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.dozer.functional_tests.builder.contexts.DozerV7ContextBaseTest.makeLibrary;
import static org.dozer.loader.api.FieldsMappingOptions.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DozerV7ContextBase2Test {

    private Mapper mapper;

    private static Mapper initMapper(BeanMappingBuilder beanMappingBuilder) {
        return DozerBeanMapperBuilder.create()
                .withMappingBuilder(beanMappingBuilder)
                .build();
    }

    @Before
    public void setUp() throws Exception {
        mapper = initMapper(new V7ContextMappingExt());
    }

    @Test
    public void fields_defaultContext() {
        Library library = makeLibrary();

        LibraryPrime libraryPrime = mapper.map(library, LibraryPrime.class);
        assertEquals("author", ((BookPrime) libraryPrime.getBooks().get(0)).getAuthor().getName());
        assertEquals("author", ((BookPrime) libraryPrime.getBooks().get(1)).getAuthor().getName());

        ((Book) library.getBooks().get(0)).getAuthor().setName("123");
        ((Book) library.getBooks().get(1)).getAuthor().setName("1234");

        mapper.map(libraryPrime, library);
        assertEquals(2, library.getBooks().size());
        assertEquals("author", ((Book) library.getBooks().get(0)).getAuthor().getName());
        assertEquals("author", ((Book) library.getBooks().get(1)).getAuthor().getName());
    }

    @Test
    public void fields_testContext() {
        Library library = makeLibrary();

        LibraryPrime libraryPrime = mapper.map(library, LibraryPrime.class, "test");
        assertEquals("author", ((BookPrime) libraryPrime.getBooks().get(0)).getAuthor().getName());
        assertEquals("author", ((BookPrime) libraryPrime.getBooks().get(1)).getAuthor().getName());

        ((Book) library.getBooks().get(0)).getAuthor().setName("123");
        ((Book) library.getBooks().get(1)).getAuthor().setName("1234");

        mapper.map(libraryPrime, library, "test");
        assertEquals(2, library.getBooks().size());
        assertEquals("123", ((Book) library.getBooks().get(0)).getAuthor().getName());
        assertEquals("1234", ((Book) library.getBooks().get(1)).getAuthor().getName());
    }

    private static class V7ContextMappingExt extends BeanMappingBuilder {
        @Override
        protected void configure() {
            mapping(Library.class, LibraryPrime.class)
                    .fields("books", "books", hintA(Book.class), hintB(BookPrime.class));

            mapping(Book.class, BookPrime.class)
                    .fields("id", "id")
                    .fields("author", "author", relationshipType(RelationshipType.NON_CUMULATIVE), oneWay().withContext("test"));

            mapping(Author.class, AuthorPrime.class)
                    .fields("id", "id")
                    .fields("name", "name", context("full"), context("test"));
        }
    }
}
