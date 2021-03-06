package uz.pdp.warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import uz.pdp.warehouse.common.Result;
import uz.pdp.warehouse.entity.Client;
import uz.pdp.warehouse.service.ClientService;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @PostMapping
    public Result post(@RequestBody Client client){
        return clientService.post(client);
    }

    @GetMapping
    public Page<Client> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC)Pageable pageable){
        return clientService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id){
        return clientService.findOne(id);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return clientService.delete(id);
    }

    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id, @RequestBody Client client){
        return clientService.edit(id, client);
    }

}
