package Chap02_Sorting.Chap02_01;

import utils.CardsDeck;
import utils.CardsDeck.PlayingCard;

public class Ex13 {
    public static void main(String[] args) {
        CardsDeck cardsDeck = new CardsDeck();
        cardsDeck.shuffle();
        PlayingCard[] deck = cardsDeck.getDeck();
        for(int i = 0; i < 5; i ++)
            System.out.println(deck[i]);

    }
}
