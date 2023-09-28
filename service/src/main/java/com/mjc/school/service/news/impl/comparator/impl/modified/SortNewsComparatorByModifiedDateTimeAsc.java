package com.mjc.school.service.news.impl.comparator.impl.modified;

import com.mjc.school.service.news.impl.comparator.SortNewsComparator;
import com.mjc.school.validation.dto.NewsDTO;

public class SortNewsComparatorByModifiedDateTimeAsc
        implements SortNewsComparator {
    @Override
    public int compare(NewsDTO news1, NewsDTO news2) {
        return news1.getModified().compareTo(news2.getModified());
    }
}