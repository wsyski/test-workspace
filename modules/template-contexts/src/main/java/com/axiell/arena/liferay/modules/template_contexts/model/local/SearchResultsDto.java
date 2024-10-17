package com.axiell.arena.liferay.modules.template_contexts.model.local;

import java.util.List;

public class SearchResultsDto {
    private int total;
    private List<RecordDto> records;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RecordDto> getRecords() {
        return records;
    }

    public void setRecords(List<RecordDto> records) {
        this.records = records;
    }
}
