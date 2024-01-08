package sevens.player.utilities.interfaces;

import java.util.List;

import sevens.card.objects.Card;
import utilities.exceptions.SystemException;

/**
 * プレイヤーのインターフェース
 */
public interface Player {
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
     * @return {@link Card}
     * @throws SystemException 不明なエラーが発生した場合にスローする
     */
    public Card selectCard(List<Card> candidatingCardList) throws SystemException;

    /**
     * パスする
     * 
     * @param
     * @return
     * @throws SystemException 不明なエラーが発生した場合にスローする
     */
    public void pass() throws SystemException;

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
}