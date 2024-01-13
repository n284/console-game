package sevens.player.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sevens.card.objects.Card;
import sevens.player.utilities.interfaces.Player;
import utilities.MessageLoader;
import utilities.SettingLoader;
import utilities.exceptions.SystemException;

/**
 * CPUを表すクラス
 */
public class CPUPlayer implements Player {
    /** 名前を表す */
    private String name;

    /** 手札を表す */
    private List<Card> hand;

    /** パスの回数を表す */
    private int passNum;

    /**
     * コンストラクタ
     * 
     * @param name
     * @throws SystemException 不明なエラーが発生した場合にスローする
     */
    public CPUPlayer(String name) throws SystemException {
        this.name = name;
        this.passNum = SettingLoader.loadSetting("sevens.pass");
    }

    /**
     * 完了しているか確認する
     * 
     * @param
     * @return boolean
     */
    @Override
    public boolean isFinished() {
        return (this.passNum == 0 || this.hand.isEmpty());
    }

    /**
     * 手札からカードを選択する
     * 
     * @param candidatingCardList
     * @return {@link Card}
     * @throws SystemException 不明なエラーが発生した場合にスローする
     */
    @Override
    public Card selectCard(List<Card> candidatingCardList) throws SystemException {
        List<Card> cardList = new ArrayList<>();

        for (Card card : this.hand) {
            if (candidatingCardList.contains(card)) {
                cardList.add(card);
            }
        }

        if (!cardList.isEmpty()) {
            Collections.shuffle(cardList);
            Card card = cardList.get(0);
            this.hand.remove(card);
            candidatingCardList.remove(card);

            return card;
        } else {
            this.pass();

            return null;
        }
    }

    /**
     * パスする
     * 
     * @param
     * @return
     * @throws SystemException 不明なエラーが発生した場合にスローする
     */
    @Override
    public void pass() throws SystemException {
        if (this.passNum > 0) {
            System.out.println(MessageLoader.loadMessage("sevens.pass"));
            this.passNum--;
        } else {
            throw new SystemException(MessageLoader.loadMessage("error.unknown"));
        }
    }

    /**
     * nameフィールドのゲッター
     * 
     * @param
     * @return {@link String}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * handフィールドのゲッター
     * 
     * @param
     * @return {@link List <{@link Card}>
     */
    @Override
    public List<Card> getHand() {
        return this.hand;
    }

    /**
     * handフィールドのセッター
     * 
     * @param hand
     * @return
     */
    @Override
    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    /**
     * passNumフィールドのゲッター
     * 
     * @param
     * @return int
     */
    @Override
    public int getPassNum() {
        return this.passNum;
    }

    /**
     * 文字列に変換
     * 
     * @param
     * @return {@link String}
     */
    @Override
    public String toString() {
        return this.name;
    }
}