package com.javidev.ecommerce.entities;

import lombok.Getter;
import lombok.Setter;

public class MomeCloud {
    @Getter
    @Setter
    private String fileBase64;

    @Getter
    @Setter
    private String filename;

    @Getter
    @Setter
    private String originalFilename;

    @Getter
    @Setter
    private String size;
}
