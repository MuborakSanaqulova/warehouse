package uz.pdp.warehouse.payload;

import lombok.Data;

@Data
public class OutputDto {
    private Integer currencyId;
    private Integer warehouseId;
    private Integer clientId;
    private String factureNumber;
}
