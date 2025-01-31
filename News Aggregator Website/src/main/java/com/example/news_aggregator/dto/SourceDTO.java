package com.example.news_aggregator.dto;

import com.example.news_aggregator.entity.Source;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class SourceDTO {

    @NotBlank(message = "Name is mandatory")
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String name;

    @NotBlank(message = "URL is mandatory")
    @Size(max = 255, message = "URL must be less than 255 characters")
    private String url;

    public static Source convertToSourceEntity(SourceDTO sourceDTO) {
        if (sourceDTO == null)
            return null;

        Source source = new Source();
        source.setName(sourceDTO.getName());
        source.setUrl(sourceDTO.getUrl());

        return source;
    }

}
