package handong.whynot.util;

import org.springframework.core.convert.converter.Converter;

import handong.whynot.dto.job.JobEnum;
import org.springframework.stereotype.Component;

@Component
public class JobEnumConverter implements Converter<String, JobEnum> {

    @Override
    public JobEnum convert(String source) {
        return JobEnum.valueOf(source.toUpperCase());
    }
}