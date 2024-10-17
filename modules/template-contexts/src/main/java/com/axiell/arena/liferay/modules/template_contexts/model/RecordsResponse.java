package com.axiell.arena.liferay.modules.template_contexts.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordsResponse {
    private List<FedEventResponse> list;
    private Integer total;
}
