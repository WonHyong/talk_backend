package com.wonhyong.talk.base.dto;

import com.wonhyong.talk.base.model.BaseTimeModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@RequiredArgsConstructor
public abstract class BaseTimeDto {

    public static final String DATE_FORMAT_PATTERN = "yyyy.MM.dd HH:mm";

    private final String createdDate;
    private final String modifiedDate;

    public BaseTimeDto(BaseTimeModel baseTimeModel) {
        this.createdDate = baseTimeModel.getCreatedDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN));
        this.modifiedDate = baseTimeModel.getModifiedDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN));
    }
}
