package algo4.chap02_sorting.Chap02_01;

import utils.CardsDeck;
import utils.CardsDeck.*;
import utils.Tuple;

/**
 * 出列排序
 * 在当前限制下，类似于选择排序，每次将顶部较大的牌移至底部，直到比较完整副牌，然后将最小的牌出列。
 * */
public class Ex14 {

    public static void main(String[] args) {
        test();
    }

    static PlayingCard[] arrange(PlayingCard[] deck){
        PlayingCard[] output = new PlayingCard[52];
        int outputIndex = 0;

        int i = 0;
        while(deck.length > 1){
            Tuple<PlayingCard, PlayingCard> topPair = pickTopPair(deck);
            PlayingCard card1 = topPair._1;
            PlayingCard card2 = topPair._2;
            if(card1.compareTo(card2) < 0) swapTopPair(deck);
            swapTop2Bottom(deck);  // 将较大的牌放到底部
            i ++;

            // 已经比较了一副牌
            if(i == deck.length - 1){
                Tuple<PlayingCard, PlayingCard[]> res = pop(deck);
                output[outputIndex] = res._1;
                outputIndex ++;
                deck = res._2;
                i = 0;
            }
        }
        output[51] = deck[0];
        return output;
    }

    static Tuple<PlayingCard, PlayingCard> pickTopPair(PlayingCard[] deck){
        return new Tuple<>(deck[0], deck[1]);
    }

    static void swapTopPair(PlayingCard[] deck){
        PlayingCard t = deck[0];
        deck[0] = deck[1];
        deck[1] = t;
    }

    static void swapTop2Bottom(PlayingCard[] deck){
        PlayingCard t = deck[0];
        System.arraycopy(deck, 1, deck, 0, deck.length - 1);
        deck[deck.length - 1] = t;
    }

    static Tuple<PlayingCard, PlayingCard[]> pop(PlayingCard[] deck){
        PlayingCard t = deck[0];
        PlayingCard[] newDeck = new PlayingCard[deck.length - 1];
        System.arraycopy(deck, 1, newDeck, 0, newDeck.length);
        return new Tuple<>(t, newDeck);
    }

    static boolean isSorted(PlayingCard[] deck){
        for(int i = 1; i < deck.length; i ++){
            if(deck[i].compareTo(deck[i - 1]) < 0) return false;
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
        PlayingCard[] newDeck = arrange(deck);
        assert isSorted(newDeck): "The deck is not correctly arranged";
        System.out.println("Done!");
        show(newDeck);
    }
}
