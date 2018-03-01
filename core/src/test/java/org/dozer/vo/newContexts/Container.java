package org.dozer.vo.newContexts;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Container {
    private long id;
    private String string;
    private List<Item> items;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Container container = (Container) o;
        return id == container.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static final class ContainerBuilder {
        private long id;
        private String string;
        private List<Item> items = new ArrayList<>();

        private ContainerBuilder() {
        }

        public static ContainerBuilder aContainer() {
            return new ContainerBuilder();
        }

        public ContainerBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public ContainerBuilder withString(String string) {
            this.string = string;
            return this;
        }

        public ContainerBuilder withItems(List<Item> items) {
            this.items = items;
            return this;
        }

        public ContainerBuilder withItem(Item item) {
            this.items.add(item);
            return this;
        }

        public ContainerBuilder withItem(Item.ItemBuilder item) {
            this.items.add(item.build());
            return this;
        }

        public Container build() {
            Container container = new Container();
            container.setId(id);
            container.setString(string);
            container.setItems(items);
            return container;
        }
    }
}
