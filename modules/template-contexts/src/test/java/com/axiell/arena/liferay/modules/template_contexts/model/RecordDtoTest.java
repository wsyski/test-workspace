package com.axiell.arena.liferay.modules.template_contexts.model;

import com.axiell.arena.liferay.modules.template_contexts.model.local.FieldsDto;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;

public class RecordDtoTest {

    @Test
    public void getSimilarRecordsSearchQuery_1() {
        RecordModel recordModel = new RecordModel();
        FieldsDto fieldsDto = new FieldsDto();
        fieldsDto.setSubjects(Arrays.asList("horses", "hamsters"));
        fieldsDto.setLanguages(Arrays.asList("eng", "swe"));
        fieldsDto.setMediaclass("book");
        recordModel.setFields(fieldsDto);
        recordModel.setUberkey("12345-12345-12345-12345");
        String similarRecordsSearchQuery = recordModel.getSimilarRecordsQuery();
        assertThat(similarRecordsSearchQuery, CoreMatchers.is("(subject:\"horses\" OR subject:\"hamsters\") AND (language:\"eng\" OR language:\"swe\") AND mediaclass:\"book\" AND NOT uberkey:\"12345-12345-12345-12345\""));
    }

    @Test
    public void getSimilarRecordsSearchQuery_2() {
        RecordModel recordModel = new RecordModel();
        FieldsDto fieldsDto = new FieldsDto();
        fieldsDto.setLanguages(Arrays.asList("eng", "swe"));
        fieldsDto.setMediaclass("book");
        recordModel.setFields(fieldsDto);
        recordModel.setUberkey("12345-12345-12345-12345");
        String similarRecordsSearchQuery = recordModel.getSimilarRecordsQuery();
        assertThat(similarRecordsSearchQuery, CoreMatchers.is("(language:\"eng\" OR language:\"swe\") AND mediaclass:\"book\" AND NOT uberkey:\"12345-12345-12345-12345\""));
    }

    @Test
    public void getSimilarRecordsSearchQuery_3() {
        RecordModel recordModel = new RecordModel();
        FieldsDto fieldsDto = new FieldsDto();
        fieldsDto.setSubjects(Arrays.asList("horses", "hamsters"));
        fieldsDto.setMediaclass("book");
        recordModel.setFields(fieldsDto);
        recordModel.setUberkey("12345-12345-12345-12345");
        String similarRecordsSearchQuery = recordModel.getSimilarRecordsQuery();
        assertThat(similarRecordsSearchQuery, CoreMatchers.is("(subject:\"horses\" OR subject:\"hamsters\") AND mediaclass:\"book\" AND NOT uberkey:\"12345-12345-12345-12345\""));
    }

    @Test
    public void getSimilarRecordsSearchQuery_4() {
        RecordModel recordModel = new RecordModel();
        FieldsDto fieldsDto = new FieldsDto();
        fieldsDto.setLanguages(Collections.singletonList("eng"));
        recordModel.setFields(fieldsDto);
        recordModel.setUberkey("12345-12345-12345-12345");
        String similarRecordsSearchQuery = recordModel.getSimilarRecordsQuery();
        assertThat(similarRecordsSearchQuery, CoreMatchers.is("(language:\"eng\") AND NOT uberkey:\"12345-12345-12345-12345\""));
    }

    @Test
    public void getSimilarRecordsSearchQuery_5() {
        RecordModel recordModel = new RecordModel();
        FieldsDto fieldsDto = new FieldsDto();
        fieldsDto.setMediaclass("book");
        recordModel.setFields(fieldsDto);
        recordModel.setUberkey("12345-12345-12345-12345");
        String similarRecordsSearchQuery = recordModel.getSimilarRecordsQuery();
        assertThat(similarRecordsSearchQuery, CoreMatchers.is("mediaclass:\"book\" AND NOT uberkey:\"12345-12345-12345-12345\""));
    }

}
