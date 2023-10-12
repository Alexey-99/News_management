package com.mjc.school.service.news.impl.comparator;

import com.mjc.school.validation.dto.NewsDTO;

import java.util.Comparator;

public interface SortNewsComparator extends Comparator<NewsDTO> {
    @Override
    int compare(NewsDTO news1, NewsDTO news2);
}