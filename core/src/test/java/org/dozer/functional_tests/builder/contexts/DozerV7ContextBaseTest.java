package org.dozer.functional_tests.builder.contexts;

import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.vo.cumulative.Author;
import org.dozer.vo.cumulative.AuthorPrime;
import org.dozer.vo.cumulative.Book;
import org.dozer.vo.cumulative.BookPrime;
import org.dozer.vo.cumulative.Library;
import org.dozer.vo.cumulative.LibraryPrime;
import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.dozer.loader.api.FieldsMappingOptions.context;
import static org.dozer.loader.api.FieldsMappingOptions.excludeContext;
import static org.dozer.loader.api.FieldsMappingOptions.hintA;
import static org.dozer.loader.api.FieldsMappingOptions.hintB;
import static org.dozer.loader.api.FieldsMappingOptions.oneWay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DozerV7ContextBaseTest {

    private Mapper mapper;

    private static Mapper initMapper(BeanMappingBuilder beanMappingBuilder) {
        return DozerBeanMapperBuilder.create()
                .withMappingBuilder(beanMappingBuilder)
                .build();
    }

    @Before
    public void setUp() throws Exception {
        mapper = initMapper(new V7ContextMapping());
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

    @Test
    public void fieldsWithDefContextAndExplicitContext() {
        testContextTest();
    }

    private void testContextTest() {
        Library library = makeLibrary();

        LibraryPrime libraryPrime = mapper.map(library, LibraryPrime.class, "test");
        assertEquals("author", ((BookPrime) libraryPrime.getBooks().get(0)).getAuthor().getName());
        assertEquals("author", ((BookPrime) libraryPrime.getBooks().get(1)).getAuthor().getName());

        Library libResult = mapper.map(libraryPrime, Library.class, "test");
        assertNull(((Book) libResult.getBooks().get(0)).getAuthor());
    }

    @Test
    public void fieldsWithDefContextAndExplicitContext_extMapping() {
        mapper = initMapper(new V7ContextMappingExt());
        testContextTest();
    }

    static Library makeLibrary() {
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
                    .fields("author", "author", excludeContext("test"))
                    .fields("author", "author", oneWay(), context("test"));

            mapping(Author.class, AuthorPrime.class)
                    .fields("id", "id")
                    .fields("name", "name", context("full"), context("test"));
        }
    }

    private static class V7ContextMappingExt extends BeanMappingBuilder {
        @Override
        protected void configure() {
            mapping(Library.class, LibraryPrime.class)
                    .fields("books", "books", hintA(Book.class), hintB(BookPrime.class));

            mapping(Book.class, BookPrime.class)
                    .fields("id", "id")
                    .fields("author", "author", oneWay().withContext("test"));

            mapping(Author.class, AuthorPrime.class)
                    .fields("id", "id")
                    .fields("name", "name", context("full"), context("test"));
        }
    }
}
