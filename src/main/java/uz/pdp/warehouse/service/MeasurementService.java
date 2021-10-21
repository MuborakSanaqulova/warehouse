package uz.pdp.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.warehouse.common.Result;
import uz.pdp.warehouse.entity.Measurement;
import uz.pdp.warehouse.repository.MeasurementRepository;


import java.util.Optional;

@Service
public class MeasurementService {

    @Autowired
    MeasurementRepository measurementRepository;

    public Result post(Measurement measurement) {
        Optional<Measurement> measurementOptional = measurementRepository.findByName(measurement.getName());
        if (!measurementOptional.isPresent()) {
            measurementRepository.save(measurement);
            return new Result("measurement successfully added", true);
        }
        if (measurementOptional.get().isActive())
            return new Result("already exist measurement", false);

        measurement.setId(measurementOptional.get().getId());
        measurement.setActive(true);
        measurementRepository.save(measurement);
        return new Result("successfully added", true);
    }

    public Page<Measurement> getAll(Pageable pageable) {
        return measurementRepository.findAllByActiveTrue(pageable);
    }


    public Result findOne(Integer id) {
        Optional<Measurement> optionalMeasurement = measurementRepository.findByIdAndActiveTrue(id);
        return optionalMeasurement.map(measurement -> new Result("Success", true, measurement))
                .orElseGet(() -> new Result("Not exist", false));
    }

    public Result delete(Integer id) {
        Optional<Measurement> optionalMeasurement = measurementRepository.findByIdAndActiveTrue(id);
        if (optionalMeasurement.isPresent()) {
            Measurement measurement = optionalMeasurement.get();
            measurement.setActive(false);
            measurementRepository.save(measurement);
            return new Result("deleted", true);
        }
        return new Result("measurement not found", false);
    }

    public Result edit(Integer id, Measurement measurement) {

        Optional<Measurement> optionalMeasurement = measurementRepository.findByIdAndActiveTrue(id);

        if (!optionalMeasurement.isPresent()) {
            return new Result("measurement not found", false);
        }

        Optional<Measurement> measurementOptional = measurementRepository.findByNameAndActiveFalse(measurement.getName());
        measurementOptional.ifPresent(value -> measurementRepository.deleteById(value.getId()));

        measurement.setId(id);
        measurementRepository.save(measurement);
        return new Result("successfully edited", true, measurement);
    }
}
