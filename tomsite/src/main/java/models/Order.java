package models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder

public class Order {
    private String userID;
    private String uniqueID;
    private String orderList;
    private int price;
    private String status;
    private String date;
}
