package utilities.exceptions;

/**
 * 処理を中断するために発生させる検査例外
 */
public class SystemException extends Exception {
    /** メッセージを保持する */
    private String message;

    /**
     * コンストラクタ
     * 
     * @param errorCode
     * @param message
     */
    public SystemException(String message) {
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
