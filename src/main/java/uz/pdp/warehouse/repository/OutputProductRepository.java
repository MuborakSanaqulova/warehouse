package uz.pdp.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.warehouse.entity.OutputProduct;

import java.util.List;

@Repository
public interface OutputProductRepository extends JpaRepository<OutputProduct, Integer> {

//    @Query(value = "select product_id, count(product_id) AS 'times' from output_product " +
//            "join output o on o.id = output_product.output_id" +
//            " group by product_id order by 'times' LIMIT 2",nativeQuery = true)
//    List<Integer> getMaxRepeatedOutputProduct();

}
