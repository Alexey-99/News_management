package com.mjc.school.converter;

import com.mjc.school.NewsTag;
import com.mjc.school.Tag;
import org.springframework.stereotype.Component;

@Component
public class NewsTagsConverter {
    public Tag toTag(NewsTag newsTag) {
        return newsTag.getTag();
    }
}