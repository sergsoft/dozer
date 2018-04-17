package org.dozer.functional_tests.builder.contexts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;
import org.dozer.MergeConflictException;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.MergePolicy;
import org.dozer.vo.newContexts.Container;
import org.dozer.vo.newContexts.ContainerDTO;
import org.dozer.vo.newContexts.Item;
import org.dozer.vo.newContexts.ItemDTO;
import org.junit.Test;

import java.io.IOException;

import static java.lang.System.identityHashCode;
import static org.dozer.classmap.RelationshipType.NON_CUMULATIVE;
import static org.dozer.loader.api.FieldsMappingOptions.relationshipType;
import static org.dozer.loader.api.FieldsMappingOptions.useMerge;
import static org.dozer.vo.newContexts.Container.ContainerBuilder.aContainer;
import static org.dozer.vo.newContexts.Item.ItemBuilder.anItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DoverV7MergePolicy {
    private Mapper mapper;

    private static Mapper initMapper(BeanMappingBuilder beanMappingBuilder) {
        return DozerBeanMapperBuilder.create()
                .withMappingBuilder(beanMappingBuilder)
                .build();
    }

    @Test
    public void testKEEP_ORIGINAL() {
        mapper = initMapper(new V7MergeMapping(MergePolicy.KEEP_ORIGINAL));
        Container container = makeContainer();
        ContainerDTO dto = mapper.map(container, ContainerDTO.class);

        dto.getLastItem().setName("newName");
        dto.getLastItem().setPrice(15);
        dto.setString("newString");

        mapper.map(dto, container);
        assertEquals("string", container.getString());
        assertEquals("name", container.getLastItem().getName());
        assertEquals(-34, container.getLastItem().getPrice());
    }

    @Test
    public void testKEEP_FIRST_CHANGE() throws IOException {
        mapper = initMapper(new V7MergeMapping(MergePolicy.KEEP_FIRST_CHANGE));
        Container container = makeContainer();
        System.out.println("Before: " + identityHashCode(container.getLastItem()));
        ContainerDTO dto = jsonWrap(mapper.map(container, ContainerDTO.class));

        dto.getLastItem().setName("newName");
        dto.getLastItem().setPrice(15);
        dto.setString("newString");
        dto.getItems().get(0).setName("newName1");
        dto.getItems().get(0).setPrice(12);

        assertNotEquals(identityHashCode(dto.getLastItem()), identityHashCode(dto.getItems().get(0)));
        mapper.map(dto, container);
        System.out.println("After: " + identityHashCode(container.getLastItem()));
        assertEquals("newString", container.getString());
        assertEquals("newName1", container.getLastItem().getName());
        assertEquals(12, container.getLastItem().getPrice());
        assertEquals(identityHashCode(container.getLastItem()), identityHashCode(container.getItems().get(0)));
    }

    @Test
    public void testKEEP_LAST() throws IOException {
        mapper = initMapper(new V7MergeMapping(MergePolicy.KEEP_LAST));
        Container container = makeContainer();
        System.out.println("Before: " + identityHashCode(container.getLastItem()));
        ContainerDTO dto = jsonWrap(mapper.map(container, ContainerDTO.class));

        dto.getLastItem().setName("newName");
        dto.getLastItem().setPrice(15);
        dto.setString("newString");
        dto.getItems().get(0).setName("newName1");
        dto.getItems().get(0).setPrice(12);

        assertNotEquals(identityHashCode(dto.getLastItem()), identityHashCode(dto.getItems().get(0)));
        mapper.map(dto, container);
        System.out.println("After: " + identityHashCode(container.getLastItem()));
        assertEquals("newString", container.getString());
        assertEquals("newName", container.getLastItem().getName());
        assertEquals(15, container.getLastItem().getPrice());
        assertEquals(identityHashCode(container.getLastItem()), identityHashCode(container.getItems().get(0)));
    }

    @Test(expected = MergeConflictException.class)
    public void testERROR_ON_CONFLICTS_withError() throws IOException {
        mapper = initMapper(new V7MergeMapping(MergePolicy.ERROR_ON_CONFLICTS));
        Container container = makeContainer();
        System.out.println("Before: " + identityHashCode(container.getLastItem()));
        ContainerDTO dto = jsonWrap(mapper.map(container, ContainerDTO.class));

        dto.getLastItem().setName("newName");
        dto.getLastItem().setPrice(15);
        dto.setString("newString");
        dto.getItems().get(0).setName("newName1");
        dto.getItems().get(0).setPrice(12);

        assertNotEquals(identityHashCode(dto.getLastItem()), identityHashCode(dto.getItems().get(0)));
        mapper.map(dto, container);
    }

    @Test
    public void testERROR_ON_CONFLICTS_withoutError() throws IOException {
        mapper = initMapper(new V7MergeMapping(MergePolicy.ERROR_ON_CONFLICTS));
        Container container = makeContainer();
        System.out.println("Before: " + identityHashCode(container.getLastItem()));
        ContainerDTO dto = jsonWrap(mapper.map(container, ContainerDTO.class));

        dto.getLastItem().setName("newName");
        dto.getLastItem().setPrice(15);
        dto.setString("newString");
        dto.getItems().get(0).setName("name");
        dto.getItems().get(0).setPrice(-34);

        assertNotEquals(identityHashCode(dto.getLastItem()), identityHashCode(dto.getItems().get(0)));
        mapper.map(dto, container);
        System.out.println("After: " + identityHashCode(container.getLastItem()));
        assertEquals("newString", container.getString());
        assertEquals("newName", container.getLastItem().getName());
        assertEquals(15, container.getLastItem().getPrice());
        assertEquals(identityHashCode(container.getLastItem()), identityHashCode(container.getItems().get(0)));
    }

    private ContainerDTO jsonWrap(ContainerDTO map) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String string = mapper.writeValueAsString(map);
        return mapper.readValue(string, ContainerDTO.class);
    }

    private Container makeContainer() {
        return aContainer()
                .withId(1L)
                .withString("string")
                .withItem(anItem()
                        .withId(10L)
                        .withName("name")
                        .withPrice(-34)
                )
                .build();
    }

    private static class V7MergeMapping extends BeanMappingBuilder {
        private final MergePolicy policy;

        private V7MergeMapping(MergePolicy policy) {
            this.policy = policy;
        }

        @Override
        protected void configure() {
            mapping(Container.class, ContainerDTO.class)
                    .fields("id", "id")
                    .fields("string", "string", useMerge(policy))
                    .fields("items", "items", relationshipType(NON_CUMULATIVE))
                    .fields("lastItem", "lastItem");

            mapping(Item.class, ItemDTO.class)
                    .fields("id", "id")
                    .fields("name", "name", useMerge(policy))
                    .fields("price", "price", useMerge(policy));
        }
    }
}
