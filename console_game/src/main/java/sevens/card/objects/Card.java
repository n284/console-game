package sevens.card.objects;

/**
 * トランプのカードを表すクラス
 */
public class Card {
    /**
     * カードの数字を表す
     */
    private int number;

    /**
     * カードの絵柄を表す
     */
    private String mark;

    /**
     * コンストラクタ
     * 
     * @param number
     * @param mark
     * 
     */
    public Card(int number, String mark) {
        this.number = number;
        this.mark = mark;
    }

    /**
     * numberフィールドのゲッター
     * 
     * @return int
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * markフィールドのゲッター
     * 
     * @return int
     */
    public String getMark() {
        return this.mark;
    }
}
