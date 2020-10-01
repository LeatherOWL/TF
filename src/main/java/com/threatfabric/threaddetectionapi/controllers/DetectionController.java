package com.threatfabric.threaddetectionapi.controllers;

import com.threatfabric.threaddetectionapi.exceptions.ResourceNotFoundException;
import com.threatfabric.threaddetectionapi.services.DetectionService;
import com.threatfabric.threaddetectionapi.specification.DetectionInfoSpecificationsBuilder;
import com.threatfabric.threaddetectionapi.specification.SearchCriteria;
import com.threatfabric.threaddetectionapi.v1.mapper.DetectionMapper;
import com.threatfabric.threaddetectionapi.v1.model.DetectionDTO;
import com.threatfabric.threaddetectionapi.v1.model.DetectionInfo;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Long.valueOf;

@RestController
@RequestMapping(DetectionController.DETECTION_BASE_URL)
@AllArgsConstructor
public class DetectionController {

    public static final String DETECTION_BASE_URL = "/v1/detection";

    private final DetectionMapper detectionMapper = DetectionMapper.INSTANCE;
    private final DetectionService detectionService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<DetectionDTO> getAll(@RequestParam(value = "nameOfApp", required = false) String nameOfApp,
                                     @RequestParam(value = "typeOfApp", required = false) String typeOfApp,
                                     @RequestParam(value = "detectionType", required = false) String detectionType) {

        DetectionInfoSpecificationsBuilder specificationsBuilder = new DetectionInfoSpecificationsBuilder();

        if (nameOfApp != null) {
            specificationsBuilder.with("nameOfApp", nameOfApp);
        }
        if (typeOfApp != null) {
            specificationsBuilder.with("typeOfApp", typeOfApp);
        }
        if (detectionType != null) {
            specificationsBuilder.with("detectionType", detectionType);
        }

        Specification<DetectionInfo> specification = specificationsBuilder.build();

        if (specification != null) {
            return detectionService.findAll(specification);
        }
        return detectionService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DetectionDTO getDetectionById(@PathVariable String id) {
        return detectionService.getById(valueOf(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DetectionDTO createDetection(@RequestBody @Valid DetectionDTO detectionDTO) {
        return detectionMapper.detectionInfoToDetectionDTO(detectionService.save(detectionDTO));
    }

    @ControllerAdvice
    public static class DetectionControllerAdvice extends ResponseEntityExceptionHandler {

        @ExceptionHandler({ResourceNotFoundException.class, EmptyResultDataAccessException.class})
        public ResponseEntity<Object> handleNotFoundException(Exception exception, WebRequest request) {
            return new ResponseEntity<>(exception.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
        }

        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                      HttpHeaders headers, HttpStatus status,
                                                                      WebRequest request) {
            List<String> errorList = exception
                    .getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(x -> x.getField() + ": " + x.getDefaultMessage())
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errorList, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler({ConstraintViolationException.class})
        public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException exception,
                                                                         WebRequest request) {
            List<String> errorList = exception
                    .getConstraintViolations()
                    .stream()
                    .map(x -> x.getPropertyPath() + ": " + x.getMessage())
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errorList, HttpStatus.BAD_REQUEST);
        }
    }
}
