package models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder

public class Product {
    private String tag;
    private String name;
    private String price;
    private String description;
    private String imgSource;
    private String date;
}
