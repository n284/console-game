package utilities.exceptions;

/**
 * 任意で例外を発生させたい場合に使用する非検査例外クラス
 */
public class ApplicationException extends RuntimeException {
    /** メッセージを保持する */
    private String message;

    /**
     * コンストラクタ
     * 
     * @param errorCode
     * @param message
     */
    public ApplicationException(String message) {
        this.message = message;
    }

    /**
     * messageフィールドのゲッター
     * 
     * @param
     * @return {@link String}
     */
    public String getMessage() {
        return this.message;
    }
}
