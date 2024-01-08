package utilities.exceptions;

/**
 * 処理を中断するために発生させる検査例外
 */
public class SystemException extends Exception {
    /** エラーコードを保持する */
    private int errorCode;

    /** メッセージを保持する */
    private String message;

    /**
     * コンストラクタ
     * 
     * @param errorCode
     * @param message
     */
    public SystemException(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    /**
     * errorCodeフィールドのゲッター
     * 
     * @param
     * @return int
     */
    public int getErrorCode() {
        return this.errorCode;
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
