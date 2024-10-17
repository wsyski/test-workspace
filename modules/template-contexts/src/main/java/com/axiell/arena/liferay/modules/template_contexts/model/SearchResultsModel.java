package com.axiell.arena.liferay.modules.template_contexts.model;

import java.util.List;

public class SearchResultsModel {
    private int total;
    private List<RecordModel> records;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RecordModel> getRecords() {
        return records;
    }

    public void setRecords(List<RecordModel> records) {
        this.records = records;
    }
}
