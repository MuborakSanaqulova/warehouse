package uz.pdp.warehouse.payload;

import lombok.Data;

@Data
public class InputProductDto {

    private double amount;
    private double price;
    private String expireDate;
    private Integer productId;
    private Integer inputId;

}
