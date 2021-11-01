package uz.pdp.warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.warehouse.common.Result;
import uz.pdp.warehouse.entity.InputProduct;
import uz.pdp.warehouse.entity.Product;
import uz.pdp.warehouse.service.DashboardService;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    DashboardService dashboardService;


    @GetMapping("/sumInput")
    public Result getSumInput(){
       return dashboardService.getInputSum();
    }

//    @GetMapping("/maxOutputProduct")
//    public Product maxOutputProduct(){
//        return dashboardService.maxOutputProduct();
//    }

    @GetMapping("/byExpireDate")
    public Result getExpiredProduct(@PageableDefault(sort = "expireDate", direction = Sort.Direction.ASC)Pageable pageable){
        return dashboardService.getExpiredProducts(pageable);
    }

}
