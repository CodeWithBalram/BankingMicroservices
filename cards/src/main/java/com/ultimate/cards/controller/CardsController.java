package com.ultimate.cards.controller;

import com.ultimate.cards.constants.CardsConstants;
import com.ultimate.cards.dto.CardsDto;
import com.ultimate.cards.dto.ResponseDto;
import com.ultimate.cards.service.ICardsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")

public class CardsController {
    private ICardsService iCardsService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(@Valid @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "mobile number should be 10 digit") String mobileNumber) {
        iCardsService.createCard(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CardsDto> fetchCardDetails(@Valid @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "mobile number should be 10 digit") String mobileNumber) {
        CardsDto cardsDto = iCardsService.fetchCard(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardsDto);
    }

    public ResponseEntity<ResponseDto> updateCardDetails(@RequestParam CardsDto cardsDto) {
        boolean isUpdated = iCardsService.updateCard(cardsDto);

        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE));
        }

    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCardDetails(@RequestParam String mobileNumber) {
        boolean isDeleted = iCardsService.deleteCard(mobileNumber);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_DELETE));
        }
    }

}

