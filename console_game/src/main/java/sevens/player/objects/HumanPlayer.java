package sevens.player.objects;

import java.util.ArrayList;
import java.util.List;

import sevens.card.objects.Card;
import sevens.player.utilities.interfaces.Player;
import utilities.Input;
import utilities.MessageLoader;
import utilities.exceptions.SystemException;

public class HumanPlayer implements Player {
    /**
     * 名前を表す
     */
    private String name;

    /**
     * 手札を表す
     */
    private List<Card> hand;

    /**
     * パスの回数を表す
     */
    private int passNum;

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
     * コンストラクタ
     * 
     * @param name
     */
    public HumanPlayer(String name) {
        this.name = name;
        this.passNum = 3;
    }

    /**
     * 完了しているか確認する
     * 
     * @param
     * @return boolean
     */
    @Override
    public boolean isFinished() {
        if (this.passNum == 0 || this.hand.isEmpty()) {
            return true;
        } else {
            return false;
        }
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
        try {
            List<Card> cardList = new ArrayList<>();
            for (Card card : this.hand) {
                if (candidatingCardList.contains(card)) {
                    cardList.add(card);
                }
            }
            if (cardList.size() > 0) {
                while (true) {
                    int index = Input.inputInt(1, this.hand.size()) - 1;
                    Card card = this.hand.get(index);
                    if (cardList.contains(card)) {
                        this.hand.remove(index);
                        return card;
                    } else {
                        System.out.println("選んだカードは出せるカードではありません");
                    }
                }
            } else {
                this.pass();

                return null;
            }
        } catch (IllegalArgumentException | IndexOutOfBoundsException | ClassCastException | NullPointerException e) {
            throw new SystemException(500, MessageLoader.loadMessage("error.unknown"));
        }
    }

    /**
     * パスする
     * 
     * @param
     * @return
     */
    @Override
    public void pass() {
        System.out.println("パスします");
        this.passNum--;
    }

    /**
     * 手札を表示する
     * 
     * @param cardList
     * @throws SystemException 不明なエラーが発生した場合にスローする
     * @return
     */
    public void printHand(List<Card> cardList) throws SystemException {
        try {
            for (Card card : this.hand) {
                System.out.print(card.getMark().getValue() + "\t");
            }
            System.out.println();
            for (Card card : this.hand) {
                System.out.print(card.getNumber().getValue() + "\t");
            }
            System.out.println();
            for (Card card : this.hand) {
                if (cardList.contains(card)) {
                    System.out.print("〇\t");
                } else {
                    System.out.print("　\t");
                }
            }
            System.out.println();
        } catch (ClassCastException | NullPointerException e) {
            throw new SystemException(500, MessageLoader.loadMessage("error.unknown"));
        }
    }
}
