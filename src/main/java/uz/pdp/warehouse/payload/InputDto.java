package uz.pdp.warehouse.payload;

import lombok.Data;

@Data
public class InputDto {

//    private String data;
    private Integer currencyId;
    private Integer warehouseId;
    private Integer supplierId;
    private String factureNumber;

}
