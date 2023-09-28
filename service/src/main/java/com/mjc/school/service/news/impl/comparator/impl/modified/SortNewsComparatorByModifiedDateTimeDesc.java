package com.mjc.school.service.news.impl.comparator.impl.modified;

import com.mjc.school.service.news.impl.comparator.SortNewsComparator;
import com.mjc.school.validation.dto.NewsDTO;

public class SortNewsComparatorByModifiedDateTimeDesc
        implements SortNewsComparator {

    @Override
    public int compare(NewsDTO news1, NewsDTO news2) {
        return news2.getModified().compareTo(news1.getModified());
    }
}