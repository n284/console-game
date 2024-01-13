package sevens.card.utilities.params;

/**
 * トランプの数字を管理する列挙型
 */
public enum Number {
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
    KING(13),
    JOKER(0);

    /** 数字を表す */
    private int number;

    /**
     * コンストラクタ
     * 
     * @param number
     */
    private Number(int number) {
        this.number = number;
    }

    /**
     * numberフィールドの値を返す
     * 
     * @param
     * @return int
     */
    public int getValue() {
        return this.number;
    }

    /**
     * 引数と同じ数字の定数を返す
     * 
     * @param mark
     * @return {@link Number}
     */
    public static Number getEnum(int number) {
        for (Number _enum : Number.values()) {
            if (_enum.getValue() == number) {
                return _enum;
            }
        }

        return null;
    }

    /**
     * 文字列に変換
     * 
     * @param
     * @return {@link String}
     */
    @Override
    public String toString() {
        return String.valueOf(this.number);
    }
}
