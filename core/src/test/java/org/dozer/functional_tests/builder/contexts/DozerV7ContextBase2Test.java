package org.dozer.functional_tests.builder.contexts;

import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.vo.newContexts.Container;
import org.dozer.vo.newContexts.ContainerDTO;
import org.dozer.vo.newContexts.Item;
import org.dozer.vo.newContexts.ItemDTO;
import org.junit.Before;
import org.junit.Test;

import static org.dozer.classmap.RelationshipType.NON_CUMULATIVE;
import static org.dozer.loader.api.FieldsMappingOptions.customConverter;
import static org.dozer.loader.api.FieldsMappingOptions.excludeContext;
import static org.dozer.loader.api.FieldsMappingOptions.none;
import static org.dozer.loader.api.FieldsMappingOptions.oneWay;
import static org.dozer.loader.api.FieldsMappingOptions.relationshipType;
import static org.dozer.vo.newContexts.Container.ContainerBuilder.aContainer;
import static org.dozer.vo.newContexts.Item.ItemBuilder.anItem;
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
        Container container = makeContainer();

        ContainerDTO dto = mapper.map(container, ContainerDTO.class);

        assertEquals(1L, dto.getId());
        assertEquals("string", dto.getString());
        assertEquals(1, dto.getItems().size());
        ItemDTO itemDTO = dto.getItems().get(0);
        assertEquals(10L, itemDTO.getId());
        assertEquals("  name  ", itemDTO.getName());
        assertEquals(-34, itemDTO.getPrice());

        mapper.map(dto, container);

        assertEquals(1L, container.getId());
        assertEquals("string", container.getString());
        assertEquals(1, container.getItems().size());
        Item item = container.getItems().get(0);
        assertEquals(10L, item.getId());
        assertEquals("  name  ", item.getName());
        assertEquals(-34, item.getPrice());
    }

    @Test
    public void fields_contextPrivate() {
        Container container = makeContainer();

        ContainerDTO dto = mapper.map(container, ContainerDTO.class, "private");

        assertEquals(1L, dto.getId());
        assertNull(dto.getString());
        assertEquals(1, dto.getItems().size());
        ItemDTO itemDTO = dto.getItems().get(0);
        assertEquals(10L, itemDTO.getId());
        assertNull(itemDTO.getName());
        assertEquals(-34, itemDTO.getPrice());

        mapper.map(dto, container, "private");

        assertEquals(1L, container.getId());
        assertEquals("string", container.getString());
        assertEquals(1, container.getItems().size());
        Item item = container.getItems().get(0);
        assertEquals(10L, item.getId());
        assertEquals("  name  ", item.getName());
        assertEquals(-34, item.getPrice());
    }

    @Test
    public void fields_contextOneWay() {
        Container container = makeContainer();

        ContainerDTO dto = mapper.map(container, ContainerDTO.class, "oneWay");

        assertEquals(1L, dto.getId());
        assertEquals("string", dto.getString());
        assertEquals(1, dto.getItems().size());
        ItemDTO itemDTO = dto.getItems().get(0);
        assertEquals(10L, itemDTO.getId());
        assertEquals("  name  ", itemDTO.getName());
        assertEquals(-34, itemDTO.getPrice());

        itemDTO.setId(11L);
        itemDTO.setName("newName");
        itemDTO.setPrice(23);

        mapper.map(dto, container, "oneWay");

        assertEquals(1L, container.getId());
        assertEquals("string", container.getString());
        assertEquals(1, container.getItems().size());
        Item item = container.getItems().get(0);
        assertEquals(10L, item.getId());
        assertEquals("  name  ", item.getName());
        assertEquals(-34, item.getPrice());
    }

    @Test
    public void fields_contextCustom() {
        Container container = makeContainer();

        ContainerDTO dto = mapper.map(container, ContainerDTO.class, "custom");

        assertEquals(1L, dto.getId());
        assertEquals("string", dto.getString());
        assertEquals(1, dto.getItems().size());
        ItemDTO itemDTO = dto.getItems().get(0);
        assertEquals(10L, itemDTO.getId());
        assertEquals("name", itemDTO.getName());
        assertEquals(34, itemDTO.getPrice());

        itemDTO.setName("  new Name  ");
        itemDTO.setPrice(-145);

        mapper.map(dto, container, "custom");

        assertEquals(1L, container.getId());
        assertEquals("string", container.getString());
        assertEquals(1, container.getItems().size());
        Item item = container.getItems().get(0);
        assertEquals(10L, item.getId());
        assertEquals("new Name", item.getName());
        assertEquals(145, item.getPrice());
    }

    private Container makeContainer() {
        return aContainer()
                .withId(1L)
                .withString("string")
                .withItem(anItem()
                        .withId(10L)
                        .withName("  name  ")
                        .withPrice(-34)
                )
                .build();
    }

    private static class V7ContextMappingExt extends BeanMappingBuilder {
        @Override
        protected void configure() {
            mapping(Container.class, ContainerDTO.class)
                    .fields("id", "id")
                    .fields("string", "string", excludeContext("private"))
                    .fields("items", "items", relationshipType(NON_CUMULATIVE), oneWay().withContexts("oneWay"));

            mapping(Item.class, ItemDTO.class)
                    .fields("id", "id")
                    .fields("name", "name", excludeContext("private"), customConverter(TrimStringConverter.class).withContexts("custom"))
                    .fields("price", "price", none(), customConverter(AbsConverter.class).withContexts("custom"));
        }
    }
}
