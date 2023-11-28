package models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder

public class Exh {
    private String tag;
    private String name;
    private String place;
    private String eventDates;
    private String s_description;
    private String description;
    private String caps;
    private String date;
    private String imgsSource;
}
