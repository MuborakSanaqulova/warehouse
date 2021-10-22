package uz.pdp.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.warehouse.common.Result;
import uz.pdp.warehouse.entity.Client;
import uz.pdp.warehouse.repository.ClientRepository;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public Result post(Client client) {

        Optional<Client> optionalClient = clientRepository.findByPhoneNumber(client.getPhoneNumber());

        if (optionalClient.isPresent()){
            if(optionalClient.get().isActive()){
                return new Result("already exist", false);
            }
            else {
                clientRepository.delete(optionalClient.get());
            }
        }

        clientRepository.save(client);
        return new Result("suceesfully added", true);
    }

    public Page<Client> getAll(Pageable pageable) {
        return clientRepository.findAllByActiveTrue(pageable);
    }

    public Result findOne(Integer id) {

        Optional<Client> optionalClient = clientRepository.findByIdAndActiveTrue(id);
        return optionalClient.map(client -> new Result("success", true, client))
                .orElseGet(() -> new Result("not found", false));
    }

    public Result delete(Integer id) {
        Optional<Client> optionalClient = clientRepository.findByIdAndActiveTrue(id);
        if (optionalClient.isPresent()){
            Client client = optionalClient.get();
            client.setActive(false);
            clientRepository.save(client);
            return new Result("deleted", true);
        }
        return new Result("not found", false);
    }

    public Result edit(Integer id, Client client) {
        Optional<Client> optionalClient = clientRepository.findByIdAndActiveTrue(id);
        if (!optionalClient.isPresent()){
            return new Result("not found", false);
        }

        Optional<Client> numberAndActiveFalse = clientRepository.findByPhoneNumberAndActiveFalse(client.getPhoneNumber());
        if (numberAndActiveFalse.isPresent()){
            clientRepository.delete(numberAndActiveFalse.get());
        }

        client.setId(id);
        clientRepository.save(client);
        return new Result("successfully edited", true, optionalClient.get());
    }
}
