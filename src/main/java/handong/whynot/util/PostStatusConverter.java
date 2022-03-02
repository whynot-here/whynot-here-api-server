package handong.whynot.util;

import org.springframework.core.convert.converter.Converter;

import handong.whynot.dto.post.RecruitStatus;
import org.springframework.stereotype.Component;

@Component
public class PostStatusConverter implements Converter<String, RecruitStatus> {

    @Override
    public RecruitStatus convert(String source) {
        return RecruitStatus.valueOf(source.toUpperCase());
    }
}