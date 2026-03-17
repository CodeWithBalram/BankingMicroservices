package com.ultimate.cards.service;

import com.ultimate.cards.dto.CardsDto;

public interface ICardsService {
    void createCard(String mobileNumber);

    CardsDto fetchCard(String mobileNumber);

    boolean updateCard(CardsDto cardsDto);

    boolean deleteCard(String mobileNumber);

}
