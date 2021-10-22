package uz.pdp.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.warehouse.common.Result;
import uz.pdp.warehouse.entity.*;
import uz.pdp.warehouse.payload.InputDto;
import uz.pdp.warehouse.payload.OutputDto;
import uz.pdp.warehouse.repository.ClientRepository;
import uz.pdp.warehouse.repository.CurrencyRepository;
import uz.pdp.warehouse.repository.OutputRepository;
import uz.pdp.warehouse.repository.WarehouseRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OutputService {

    @Autowired
    OutputRepository outputRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    ClientRepository clientRepository;

    public Result post(OutputDto outputDto) {
        Optional<Client> byClientId = clientRepository.findById(outputDto.getClientId());
        if (!byClientId.isPresent())
            return new Result("client not found", false);

        Optional<Warehouse> byWarehouseId = warehouseRepository.findById(outputDto.getWarehouseId());
        if (!byWarehouseId.isPresent())
            return new Result("warehouse not found", false);

        Optional<Currency> byCurrencyId = currencyRepository.findById(outputDto.getCurrencyId());
        if (!byCurrencyId.isPresent())
            return new Result("currency not found", false);

        Output output = new Output();
        output.setDate(LocalDateTime.now());
        output.setCurrency(byCurrencyId.get());
        output.setClient(byClientId.get());
        output.setWarehouse(byWarehouseId.get());
        output.setFactureNumber(outputDto.getFactureNumber());
        outputRepository.save(output);

        return new Result("saved",true);

    }

    public Page<Output> getAll(Pageable pageable) {

        return outputRepository.findAll(pageable);

    }

    public Result findOne(Integer id) {
        Optional<Output> optionalOutput = outputRepository.findById(id);
        if (optionalOutput.isPresent()){
            return new Result("success", true, optionalOutput.get());
        }
        return new Result("not found", false);
    }

    public Result delete(Integer id) {
        Optional<Output> optionalOutput = outputRepository.findById(id);
        if (optionalOutput.isPresent()){
            outputRepository.deleteById(id);
            return new Result("successfully deleted", true);
        }
        return new Result("not found", false);
    }

    public Result edit(Integer id, OutputDto outputDto) {

        Optional<Output> optionalOutput = outputRepository.findById(id);
        if (!optionalOutput.isPresent()){
            return new Result("output not found", false);
        }

        Optional<Client> byClientId = clientRepository.findById(outputDto.getClientId());
        if (!byClientId.isPresent())
            return new Result("client not found", false);

        Optional<Warehouse> byWarehouseId = warehouseRepository.findById(outputDto.getWarehouseId());
        if (!byWarehouseId.isPresent())
            return new Result("warehouse not found", false);

        Optional<Currency> byCurrencyId = currencyRepository.findById(outputDto.getCurrencyId());
        if (!byCurrencyId.isPresent())
            return new Result("currency not found", false);

        Output output = new Output();
        output.setId(id);
        output.setCurrency(byCurrencyId.get());
        output.setClient(byClientId.get());
        output.setWarehouse(byWarehouseId.get());
        output.setFactureNumber(outputDto.getFactureNumber());
        outputRepository.save(output);

        return new Result("saved",true);
    }
}
