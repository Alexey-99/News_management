package com.mjc.school.converter.impl;

import com.mjc.school.NewsTag;
import com.mjc.school.Tag;
import org.springframework.stereotype.Component;

@Component
public class NewsTagsConverter {
    public Tag toTag(NewsTag newsTag) {
        return newsTag.getTag();
    }
}