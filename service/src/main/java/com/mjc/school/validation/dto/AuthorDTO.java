package com.mjc.school.validation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mjc.school.validation.annotation.IsNotExistsAuthorName;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_AUTHOR_NAME;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_AUTHOR_NAME_EXISTS;

@Validated
public class AuthorDTO {
    @JsonIgnore
    private long id;
    @NotNull(message = BAD_AUTHOR_NAME)
    @NotBlank(message = BAD_AUTHOR_NAME)
    @Size(min = 3,
            max = 15,
            message = "author.name_not_valid")
    @IsNotExistsAuthorName(message = BAD_PARAMETER_AUTHOR_NAME_EXISTS)
    private String name;
    private int countNews;
    @JsonIgnore
    private List<NewsDTO> news;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(
            String name) {
        this.name = name;
    }

    public List<NewsDTO> getNews() {
        return news;
    }

    public void setNews(List<NewsDTO> news) {
        this.news = news;
    }

    public int getCountNews() {
        return countNews;
    }

    public void setCountNews(int countNews) {
        this.countNews = countNews;
    }

    public static class AuthorDTOBuilder {
        private final AuthorDTO authorDTO;

        public AuthorDTOBuilder() {
            this.authorDTO = new AuthorDTO();
        }

        public AuthorDTOBuilder setId(long id) {
            this.authorDTO.setId(id);
            return this;
        }

        public AuthorDTOBuilder setName(String name) {
            this.authorDTO.setName(name);
            return this;
        }

        public AuthorDTOBuilder setNews(List<NewsDTO> news) {
            this.authorDTO.setNews(news);
            return this;
        }

        public AuthorDTOBuilder setCountNews(int countNews) {
            this.authorDTO.setCountNews(countNews);
            return this;
        }

        public AuthorDTO build() {
            return this.authorDTO;
        }
    }
}