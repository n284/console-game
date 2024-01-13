package sevens.card.objects;

import sevens.card.utilities.params.Mark;
import sevens.card.utilities.params.Number;

/**
 * トランプのカードを表すクラス
 */
public class Card {
    /** カードの数字を表す */
    private Number number;

    /** カードの絵柄を表す */
    private Mark mark;

    /**
     * コンストラクタ
     * 
     * @param number
     * @param mark
     * 
     */
    public Card(Number number, Mark mark) {
        this.number = number;
        this.mark = mark;
    }

    /**
     * numberフィールドのゲッター
     * 
     * @param
     * @return {@link Number}
     */
    public Number getNumber() {
        return this.number;
    }

    /**
     * markフィールドのゲッター
     * 
     * @param
     * @return {@link Mark}
     */
    public Mark getMark() {
        return this.mark;
    }

    /**
     * 文字列に変換
     * 
     * @param
     * @return {@link String}
     */
    @Override
    public String toString() {
        return String.format("%s%s", this.mark, this.number);
    }
}
