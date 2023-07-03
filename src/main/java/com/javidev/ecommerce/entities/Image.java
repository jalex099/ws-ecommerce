package com.javidev.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.javidev.ecommerce.config.Params;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "images")
@Where(clause = "company_id = " + Params.COMPANY_ID)
public class Image {

    public Image() {
    }
    public Image(
            String type,
            String extension,
            String size,
            Long reference,
            String url,
            String key,
            Long height,
            Long width,
            Long companyId
    ) {
        this.type = type;
        this.extension = extension;
        this.size = size;
        this.reference = reference;
        this.url = url;
        this.key = key;
        this.height = height;
        this.width = width;
        this.companyId = companyId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Getter
    @Setter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    @Column(name = "type", nullable = false)
    @Getter
    @Setter
    private String type;

    @Column(name = "extension", nullable = false)
    @Getter
    @Setter
    private String extension;

    @Column(name = "size")
    @Getter
    @Setter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String size;

    @Column(name = "reference", nullable = false)
    @Getter
    @Setter
    private Long reference;

    @Column(name = "url", nullable = false)
    @Getter
    @Setter
    private String url;

    @Column(name = "key", nullable = false)
    @Getter
    @Setter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String key;

    @Column(name = "height")
    @Getter
    @Setter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long height;

    @Column(name = "width")
    @Getter
    @Setter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long width;

    @Column(name = "company_id")
    @Getter
    @Setter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long companyId ;

}
