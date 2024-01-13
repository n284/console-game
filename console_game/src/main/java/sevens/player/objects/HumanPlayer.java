package sevens.player.objects;

import java.util.ArrayList;
import java.util.List;

import sevens.card.objects.Card;
import sevens.player.utilities.interfaces.Player;
import utilities.Keyboard;
import utilities.MessageLoader;
import utilities.SettingLoader;
import utilities.exceptions.SystemException;

/**
 * 人間プレイヤーを表すクラス
 */
public class HumanPlayer implements Player {
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
    public HumanPlayer(String name) throws SystemException {
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
     * @throws SystemException 不明なエラーが発生した場合にスローする
     * @return {@link Card}
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
            this.printHand(candidatingCardList);
            System.out.println(MessageLoader.loadMessage("sevens.pass.question", this.passNum));
            boolean isPass = Keyboard.inputInt(0, 1) == 1 ? true : false;

            if (isPass) {
                this.pass();

                return null;
            }

            while (true) {
                int index = Keyboard.inputInt(1, this.hand.size()) - 1;
                Card card = this.hand.get(index);
                if (cardList.contains(card)) {
                    this.hand.remove(index);
                    candidatingCardList.remove(card);

                    return card;
                } else {
                    System.out.println(MessageLoader.loadMessage("sevens.error.not.select"));
                }
            }
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
     * 手札を表示する
     * 
     * @param cardList
     * @return
     * @throws SystemException 不明なエラーが発生した場合にスローする
     */
    public void printHand(List<Card> cardList) throws SystemException {
        System.out.print("カード\t");
        for (Card card : this.hand) {
            System.out.print(String.format("%s\t", card));
        }
        System.out.println();
        System.out.print("番号\t");
        for (int i = 1; i <= this.hand.size(); i++) {
            System.out.print(String.format("%2d\t", i));
        }
        System.out.println();
        System.out.print("可能\t");
        for (Card card : this.hand) {
            if (cardList.contains(card)) {
                System.out.print("〇\t");
            } else {
                System.out.print("　\t");
            }
        }
        System.out.println();
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
