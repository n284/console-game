package sevens.card.utilities.params;

/**
 * トランプの数字を管理する列挙型
 */
public enum NumberParams {
    JOKER(0),
    ACE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(11),
    QUEEN(12),
    KING(13);

    /**
     * 数字を表す
     */
    private final int number;

    /**
     * コンストラクタ
     * 
     * @param number
     */
    private NumberParams(int number) {
        this.number = number;
    }

    /**
     * numberフィールドのゲッター
     * 
     * @param
     * @return int
     */
    public int getNumber() {
        return this.number;
    }
}
