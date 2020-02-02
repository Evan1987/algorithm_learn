package Chap02_Sorting.Chap02_01;

import utils.CardsDeck;
import utils.CardsDeck.*;
import utils.Tuple;


/**
 * 扑克牌花色排序
 * */
public class Ex13 {
    public static void main(String[] args) {
        test();
    }

    static void arrange(PlayingCard[] deck){
        // 双指针移动
        int i = 0;
        int j = 1;
        while(i < 39){
            // 调用可用方法查看牌
            Tuple<PlayingCard, PlayingCard> pair = pickPair(deck, i, j);
            Suit suit1 = pair._1.suit;
            Suit suit2 = pair._2.suit;

            // 如果第一张牌的花色在应放置区间内，则第一张指针向下移动
            if((i < 13 && suit1 == Suit.SPADE) ||
                    (i >= 13 && i < 26 && suit1 == Suit.HEART) ||
                    (i >= 26 && suit1 == Suit.CLUB)){
                i ++;
                j = i + 1;
                continue;
            }

            // 如果第二张花色比第一张小，则互换两张牌
            if(suit2.compareTo(suit1) < 0) swap(deck, i, j);

            // 持续移动第二张指针
            j ++;

            // 如果第二张指针到头，则第一张指针移动，第二张指针重置
            if(j > 51){
                i ++;
                j = i + 1;
            }
        }
    }

    static void swap(PlayingCard[] deck, int i, int j){
        PlayingCard t = deck[i];
        deck[i] = deck[j];
        deck[j] = t;
    }

    static Tuple<PlayingCard, PlayingCard> pickPair(PlayingCard[] deck, int i, int j){
        return new Tuple<>(deck[i], deck[j]);
    }

    static boolean isSorted(PlayingCard[] deck){
        for(int i = 1; i < deck.length; i ++){
            if(deck[i].suit.compareTo(deck[i - 1].suit) < 0) return false;
        }
        return true;
    }

    static void show(PlayingCard[] deck){
        for(int i = 0; i < deck.length; i ++){
            System.out.print(deck[i] + " ");
            if((i + 1) % 13 == 0) System.out.println();
        }
    }

    static void test(){
        CardsDeck cardsDeck = new CardsDeck();
        cardsDeck.shuffle();
        PlayingCard[] deck = cardsDeck.getDeck();
        arrange(deck);
        assert isSorted(deck): "The deck is not correctly arranged";
        System.out.println("Done!");
        show(deck);
    }
}
