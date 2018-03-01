package org.dozer.vo.newContexts;

import java.util.Objects;

public class Item {
    private long id;
    private String name;
    private int price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }


    public static final class ItemBuilder {
        private long id;
        private String name;
        private int price;

        private ItemBuilder() {
        }

        public static ItemBuilder anItem() {
            return new ItemBuilder();
        }

        public ItemBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public ItemBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ItemBuilder withPrice(int price) {
            this.price = price;
            return this;
        }

        public Item build() {
            Item item = new Item();
            item.setId(id);
            item.setName(name);
            item.setPrice(price);
            return item;
        }
    }
}
