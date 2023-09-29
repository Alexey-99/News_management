package com.mjc.school.service.author.impl.comparator;

import com.mjc.school.entity.AuthorIdWithAmountOfWrittenNews;

import java.util.Comparator;

public interface SortAuthorsWithAmountOfWrittenNewsComparator
        extends Comparator<AuthorIdWithAmountOfWrittenNews> {
    @Override
    public int compare(AuthorIdWithAmountOfWrittenNews o1, AuthorIdWithAmountOfWrittenNews o2);
}