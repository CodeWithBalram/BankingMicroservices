package com.ultimate.cards.mapper;

import com.ultimate.cards.dto.CardsDto;
import com.ultimate.cards.entity.Cards;

public class CardsMapper {
    public static CardsDto mapToCardsDto(Cards cards, CardsDto cardsDto) {
        //CardsDto cardsDto=new CardsDto();
        cardsDto.setCardNumber(cards.getCardNumber());
        cardsDto.setCardType(cards.getCardType());
        cardsDto.setAmountUsed(cards.getAmountUsed());
        cardsDto.setMobileNumber(cards.getMobileNumber());
        cardsDto.setTotalLimit(cards.getTotalLimit());
        cardsDto.setAvailableAmount(cards.getAvailableAmount());
        return cardsDto;
    }

    public static Cards mapToCards(CardsDto cardsDto, Cards cards) {
        // Cards cards=new Cards();
        cards.setMobileNumber(cardsDto.getMobileNumber());
        cards.setCardNumber(cardsDto.getMobileNumber());
        cards.setCardType(cards.getCardType());
        cards.setTotalLimit(cards.getTotalLimit());
        cards.setAmountUsed(cards.getAmountUsed());
        cards.setAvailableAmount(cards.getAvailableAmount());

        return cards;
    }

}
