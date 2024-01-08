package utilities.exceptions;

/**
 * 任意で例外を発生させたい場合に使用する非検査例外クラス
 */
public class ApplicationException extends RuntimeException {
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
    public ApplicationException(int errorCode, String message) {
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
