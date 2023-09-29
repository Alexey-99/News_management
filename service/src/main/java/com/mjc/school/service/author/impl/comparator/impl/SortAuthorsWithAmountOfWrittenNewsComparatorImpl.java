package com.mjc.school.service.author.impl.comparator.impl;

import com.mjc.school.entity.AuthorIdWithAmountOfWrittenNews;
import com.mjc.school.service.author.impl.comparator.SortAuthorsWithAmountOfWrittenNewsComparator;

public class SortAuthorsWithAmountOfWrittenNewsComparatorImpl
        implements SortAuthorsWithAmountOfWrittenNewsComparator {
    @Override
    public int compare(AuthorIdWithAmountOfWrittenNews o1, AuthorIdWithAmountOfWrittenNews o2) {
        return (int) (o2.getAmountOfWrittenNews() - o1.getAmountOfWrittenNews());
    }
}