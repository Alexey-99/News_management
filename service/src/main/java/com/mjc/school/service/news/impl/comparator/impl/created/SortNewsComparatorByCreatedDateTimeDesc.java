package com.mjc.school.service.news.impl.comparator.impl.created;

import com.mjc.school.service.news.impl.comparator.SortNewsComparator;
import com.mjc.school.validation.dto.NewsDTO;

public class SortNewsComparatorByCreatedDateTimeDesc
        implements SortNewsComparator {
    @Override
    public int compare(NewsDTO news1, NewsDTO news2) {
        return news2.getCreated().compareTo(news1.getCreated());
    }
}