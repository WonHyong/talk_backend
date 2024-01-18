package com.wonhyong.talk.base.dto;

import com.wonhyong.talk.base.model.BaseTimeModel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public abstract class BaseTimeResponseDto {

    public static final String DATE_FORMAT_PATTERN = "yyyy.MM.dd HH:mm";

    private String createdDate;
    private String modifiedDate;

    public BaseTimeResponseDto(BaseTimeModel baseTimeModel) {
        this.createdDate = baseTimeModel.getCreatedDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN));
        this.modifiedDate = baseTimeModel.getModifiedDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN));
    }
}
