package sevens.card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import sevens.card.objects.Card;
import sevens.card.utilities.params.Mark;
import sevens.card.utilities.params.Number;
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
            List<Mark> markList = new ArrayList<>(Arrays.asList(Mark.values()));
            List<Number> numberList = new ArrayList<>(Arrays.asList(Number.values()));
            markList.remove(Mark.JOKER);
            numberList.remove(Number.JOKER);

            for (Mark mark : markList) {
                for (Number number : numberList) {
                    deck.add(new Card(number, mark));
                }
            }
            deck.add(new Card(Number.JOKER, Mark.JOKER));

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
     * @throws SystemException 不明なエラーが発生した場合にスローする
     * @return {@link List <{@link Card}>
     */
    public static List<Card> getDeck() throws SystemException {
        List<Card> deck = CardFactory.initialize();
        CardFactory.suffle(deck);

        return deck;
    }
}
