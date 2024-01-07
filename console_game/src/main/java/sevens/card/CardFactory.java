package sevens.card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import sevens.card.objects.Card;
import sevens.card.utilities.params.MarkParams;
import sevens.card.utilities.params.NumberParams;
import utilities.MessageLoader;
import utilities.exceptions.SystemException;

/**
 * トランプを管理するクラス
 * カードクラスのインスタンス生成をして山を作成する
 */
public class CardFactory {
    /**
     * 山を初期化して返す
     * 
     * @param
     * @throws SystemException 不明なエラーが発生した場合にスローする
     * @return {@link List <{@link Card}>
     */
    private static List<Card> initialize() throws SystemException {
        try {
            List<Card> deck = new ArrayList<>();
            List<MarkParams> markList = new ArrayList<>(Arrays.asList(MarkParams.values()));
            List<NumberParams> numberList = new ArrayList<>(Arrays.asList(NumberParams.values()));
            MarkParams JOKER_MARK = markList.get(0);
            NumberParams JOKER_NUMBER = numberList.get(0);
            markList.remove(0);
            numberList.remove(0);

            for (MarkParams mark : markList) {
                for (NumberParams number : numberList) {
                    deck.add(new Card(number.getNumber(), mark.getMark()));
                }
            }

            deck.add(new Card(JOKER_NUMBER.getNumber(), JOKER_MARK.getMark()));

            return deck;
        } catch (NullPointerException | IndexOutOfBoundsException | UnsupportedOperationException | ClassCastException
                | IllegalArgumentException e) {
            throw new SystemException(500, MessageLoader.loadMessage("error.unknown"));
        }
    }

    /**
     * 山をシャフルする
     * 
     * @param deck
     * @throws SystemException 不明なエラーが発生した場合にスローする
     * @return {@link List <{@link Card}>
     */
    private static void suffle(List<Card> deck) throws SystemException {
        try {
            Collections.shuffle(deck);
        } catch (UnsupportedOperationException e) {
            throw new SystemException(500, MessageLoader.loadMessage("error.unknown"));
        }

    }

    /**
     * 山を取得する
     * 
     * @param
     * @return {@link List <{@link Card}>
     * @throws SystemException 不明なエラーが発生した場合にスローする
     */
    public static List<Card> getDeck() throws SystemException {
        List<Card> deck = CardFactory.initialize();
        CardFactory.suffle(deck);
        return deck;
    }
}
