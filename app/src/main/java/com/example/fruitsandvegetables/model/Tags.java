package com.example.fruitsandvegetables.model;

import java.util.List;

/**
 * Tags实体类
 *
 * @author paul
 */
public class Tags extends JsonResult {
    List<Tag> tags;

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

}
