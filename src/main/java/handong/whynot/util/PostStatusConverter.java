package handong.whynot.util;

import org.springframework.core.convert.converter.Converter;

import handong.whynot.dto.post.RecruitEnum;
import org.springframework.stereotype.Component;

@Component
public class PostStatusConverter implements Converter<String, RecruitEnum> {

    @Override
    public RecruitEnum convert(String source) {
        return RecruitEnum.valueOf(source.toUpperCase());
    }
}