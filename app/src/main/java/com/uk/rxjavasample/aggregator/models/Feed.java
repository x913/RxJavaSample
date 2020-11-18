package com.uk.rxjavasample.aggregator.models;

import androidx.annotation.NonNull;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name="feed", strict = false)
public class Feed {
    @Element(name="id")
    private String id;

    @Element(name="title")
    private String title;

    @ElementList(inline = true, required = false)
    private List<Entry> entry;

    public Feed() {

    }

    public Feed(String id, String title, List<Entry> entry) {
        this.id = id;
        this.title = title;
        this.entry = entry;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Entry> getEntry() {
        return entry;
    }

    public void setEntry(List<Entry> entry) {
        this.entry = entry;
    }
}
