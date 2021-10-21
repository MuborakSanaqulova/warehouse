package uz.pdp.warehouse.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.warehouse.common.Result;
import uz.pdp.warehouse.entity.Currency;
import uz.pdp.warehouse.repository.CurrencyRepository;

import java.util.Optional;

@Service
public class CurrencyService {

    @Autowired
    CurrencyRepository currencyRepository;

    public Result post(Currency currency) {

        Optional<Currency> optionalCurrency = currencyRepository.findByName(currency.getName());
        if (!optionalCurrency.isPresent()){
            currencyRepository.save(currency);
            return new Result("successfully saved", true);
        }
        if (optionalCurrency.get().isActive()){
            return new Result("already exist", false);
        }

        currency.setId(optionalCurrency.get().getId());
        currency.setActive(true);
        currencyRepository.save(currency);
        return new Result("successfully added", true);

    }


    public Page<Currency> getAll(Pageable pageable) {
        return currencyRepository.findAllByActiveTrue(pageable);
    }

    public Result findOne(Integer id) {
        Optional<Currency> optionalCurrency = currencyRepository.findByIdAndActiveTrue(id);
        return optionalCurrency.map(currency -> new Result("success", true, currency))
                .orElseGet(() -> new Result("currency not found", false));
    }

    public Result delete(Integer id) {
        Optional<Currency> optionalCurrency = currencyRepository.findByIdAndActiveTrue(id);
        if (optionalCurrency.isPresent()){
            Currency currency= optionalCurrency.get();
            currency.setActive(false);
            currencyRepository.save(currency);
            return new Result("successfully deleted", true);
        }
        return new Result("currency not found", false);
    }


    public Result edit(Integer id, Currency currency) {
        Optional<Currency> optionalCurrency = currencyRepository.findByIdAndActiveTrue(id);
        if (!optionalCurrency.isPresent()){
            return new Result("not found", false);
        }
        Optional<Currency> currencyOptional = currencyRepository.findByNameAndActiveFalse(currency.getName());
        currencyOptional.ifPresent(value -> currencyRepository.deleteById(value.getId()));

        currency.setId(id);
        currencyRepository.save(currency);
        return new Result("successfully saved", true);
    }
}
