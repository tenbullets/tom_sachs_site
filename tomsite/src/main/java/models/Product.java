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
    private int price;
    private String description;
    private String date;
    private String imgSource;
    private int count;
}
