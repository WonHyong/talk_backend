package com.wonhyong.talk.base.dto;

import com.wonhyong.talk.base.domain.BaseTimeDomain;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public abstract class BaseTimeResponseDto {

    public static final String DATE_FORMAT_PATTERN = "yyyy.MM.dd HH:mm";

    private String createdDate;
    private String modifiedDate;

    public BaseTimeResponseDto(BaseTimeDomain baseTimeDomain) {
        this.createdDate = baseTimeDomain.getCreatedDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN));
        this.modifiedDate = baseTimeDomain.getModifiedDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN));
    }
}
