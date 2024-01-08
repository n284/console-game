package sevens.card.utilities.params;

/**
 * トランプのマークを管理する列挙型
 */
public enum Mark {
    SPADE("♠"),
    CLUB("♣"),
    HEART("♥"),
    DAIAMOND("♦"),
    JOKER("★");

    /**
     * マークを表す
     */
    private String mark;

    /**
     * コンストラクタ
     * 
     * @param mark
     */
    private Mark(String mark) {
        this.mark = mark;
    }

    /**
     * markフィールドの値を返す
     * 
     * @param
     * @return {@link String}
     */
    public String getValue() {
        return this.mark;
    }

    /**
     * 引数と同じ絵柄の定数を返す
     * 
     * @param mark
     * @return {@link Mark}
     */
    public static Mark getEnum(String mark) {
        for (Mark _enum : Mark.values()) {
            if (_enum.getValue().equals(mark)) {
                return _enum;
            }
        }

        return null;
    }
}
