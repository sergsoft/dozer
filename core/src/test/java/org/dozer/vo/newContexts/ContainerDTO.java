package org.dozer.vo.newContexts;

import java.util.List;

public class ContainerDTO {
    private long id;
    private String string;
    private List<ItemDTO> items;

    public ItemDTO getLastItem() {
        return lastItem;
    }

    public void setLastItem(ItemDTO lastItem) {
        this.lastItem = lastItem;
    }

    private ItemDTO lastItem;

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

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }
}
