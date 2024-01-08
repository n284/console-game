package sevens.player.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sevens.card.objects.Card;
import sevens.player.utilities.interfaces.Player;
import utilities.MessageLoader;
import utilities.exceptions.SystemException;

public class CPUPlayer implements Player {
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
     * コンストラクタ
     * 
     * @param name
     */
    public CPUPlayer(String name) {
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
     * @param
     * @return {@link Card}
     * @throws SystemException 不明なエラーが発生した場合にスローする
     */
    @Override
    public Card selectCard(List<Card> candidatingCardList) throws SystemException {
        try {
            List<Integer> indexList = new ArrayList<>();
            for (Card card : this.hand) {
                if (candidatingCardList.contains(card)) {
                    indexList.add(this.hand.indexOf(card));
                }
            }
            if (indexList.size() > 0) {
                int index = new Random().nextInt(indexList.size());
                Card card = this.hand.get(index);
                this.hand.remove(index);

                return card;
            } else {
                this.pass();

                return null;
            }

        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new SystemException(500, MessageLoader.loadMessage("error.unknown"));
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
        System.out.println(MessageLoader.loadMessage("sevens.pass"));
        this.passNum--;
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
}