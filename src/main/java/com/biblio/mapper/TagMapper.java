package com.biblio.mapper;

import com.biblio.dto.request.TagRequest;
import com.biblio.dto.response.TagResponse;
import com.biblio.entity.Tag;

public class TagMapper {
    public static Tag toTagEntity(TagRequest tag) {
        return Tag.builder()
                .id(Long.parseLong(tag.getId()))
                .name(tag.getName())
                .shortScript(tag.getShortScript())
                .fullScript(tag.getFullScript())
                .build();
    }

    public static TagResponse toTagResponse(Tag tag) {
        return TagResponse.builder()
                .id(tag.getId().toString())
                .name(tag.getName())
                .shortScript(tag.getShortScript())
                .fullScript(tag.getFullScript())
                .build();
    }
}
