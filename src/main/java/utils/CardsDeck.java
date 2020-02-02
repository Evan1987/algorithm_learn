package utils;

import java.util.*;

enum Suit {
    SPADE(1, "\u2660"),
    HEART(2, "\u2665"),
    CLUB(3, "\u2663"),
    DIAMOND(4, "\u2666");

    int color;
    String code;
    Suit(int color, String code){
        this.color = color;
        this.code = code;
    }
}




public class CardsDeck {
    private PlayingCard[] deck;

    public static class PlayingCard implements Comparable<PlayingCard> {
        int value;
        Suit suit;

        PlayingCard(int value, Suit suit){
            this.value = value;
            this.suit = suit;
        }

        @Override
        public int compareTo(PlayingCard o) {
            int suitCompare = this.suit.compareTo(o.suit);
            if(suitCompare != 0) return suitCompare;
            return Integer.compare(this.value, o.value);
        }

        @Override
        public String toString() {
            return this.suit.code + this.value;
        }
    }

    public CardsDeck(){
        int n = 52;
        this.deck = new PlayingCard[n];
        Suit[] suits = {Suit.SPADE, Suit.HEART, Suit.CLUB, Suit.DIAMOND};
        int j = 0;
        for(Suit suit: suits){
            for(int i = 1; i < 14; i ++){
                this.deck[j] = new PlayingCard(i, suit);
                j ++;
            }
        }
    }

    public void shuffle(){
        Collections.shuffle(Arrays.asList(this.deck));
    }

    public void shuffle(Long seed){
        Random random = new Random(seed);
        Collections.shuffle(Arrays.asList(this.deck), random);
    }

    public PlayingCard[] getDeck(){
        return this.deck;
    }

}


