package sevens.player.utilities.interfaces;

import java.util.List;
import sevens.card.objects.Card;
import utilities.exceptions.SystemException;

/**
 * プレイヤーのインターフェース
 */
public interface Player {
    /**
     * nameフィールドのゲッター
     * 
     * @param
     * @return {@link String}
     */
    public String getName();

    /**
     * handフィールドのゲッター
     * 
     * @param
     * @return {@link List <{@link Card}>
     */
    public List<Card> getHand();

    /**
     * handフィールドのセッター
     * 
     * @param hand
     * @return
     */
    public void setHand(List<Card> hand);

    /**
     * passNumフィールドのゲッター
     * 
     * @param
     * @return int
     */
    public int getPassNum();

    /**
     * 完了しているか確認する
     * 
     * @param
     * @return boolean
     */
    public boolean isFinished();

    /**
     * 手札からカードを選択する
     * 
     * @param candidatingCardList
     * @throws SystemException 不明なエラーが発生した場合にスローする
     * @return {@link Card}
     */
    public Card selectCard(List<Card> candidatingCardList) throws SystemException;

    /**
     * パスする
     * 
     * @param
     * @return
     */
    public void pass();

}