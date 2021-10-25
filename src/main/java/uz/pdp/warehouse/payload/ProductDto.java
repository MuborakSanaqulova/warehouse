package uz.pdp.warehouse.payload;

import lombok.Data;

@Data
public class ProductDto {

    private String name;
    private Integer categoryId;
    private Integer attachmentId;
    private Integer measurementId;

}
