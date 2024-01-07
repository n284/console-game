package sevens.card.utilities.params;

/**
 * トランプのマークを管理する列挙型
 */
public enum MarkParams {
    JOKER("★"),
    SPADE("♠"),
    HEART("♥"),
    DAIAMOND("♦"),
    CLUB("♣");

    /**
     * マークを表す
     */
    private String mark;

    /**
     * コンストラクタ
     * 
     * @param mark
     */
    private MarkParams(String mark) {
        this.mark = mark;
    }

    /**
     * markフィールドのゲッター
     * 
     * @param
     * @return {@link String}
     */
    public String getMark() {
        return this.mark;
    }
}
