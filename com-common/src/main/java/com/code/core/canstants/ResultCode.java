package com.code.core.canstants;


import com.code.core.common.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author king
 */

@Getter
@AllArgsConstructor
public enum ResultCode implements BaseErrorCode {

    /**
     * 操作太快,请稍后再试
     */
    OPT_TOO_FAST("10000", "操作太快,请稍后再试"),

    /**
     *
     */
    PARAM_RESOLVE_EXCEPTION("10001", "参数解析异常"),

    /**
     *
     */
    NON_RESULT("10002", "无法回结果"),

    EXCEL_CREATE_FAIL("10003", "Excel创建失败"),

    EXCEL_ANALYZING_FAIL("10004", "Excel解析失败"),

    EXCEL_DOWNLOAD_FAIL("10005", "Excel导出失败"),
    /**
     *
     */
    USER_NOT_EXIST("10006", "用户不存在"),
    /**
     *
     */
    INVALIDATE_PARAM("10007", "参数不合法"),
    /**
     *
     */
    VERIFY_USER_FAIL("10008", "认证用户失败"),

    THIRD_PARTY_ACCOUNT_EXCEPTION("10029", "您的账号异常，请联系管理员"),
    /**
     *
     */
    OFF_LIEN("10009", "你已下线"),
    /**
     *
     */
    REQUEST_FAIL("10010", "请求失败"),
    /**
     *
     */
    INVALIDATE_KEY("10011", "非法秘钥"),

    /**
     *
     */
    INVALIDATE_APP("10012", "非法应用"),
    /**
     *
     */
    INVALIDATE_SIGN_PARAM("10013", "签名参数错误"),
    /**
     *
     */
    NONSUPPORT_VERSION("10014", "未知的验证版本"),

    /**
     * id生成异常
     */
    ID_GEN_EXCEPTION("10015", "ID生成异常"),

    /**
     * 登录异常,请稍后再试
     */
    LOGIN_EXCEPTION("10016", "登录异常,请稍后再试"),

    /**
     * 当前编号已被使用完毕
     */
    NUMBER_USE_UP("10017", "当前编号已使用完毕"),

    /**
     *
     */
    INVALIDATE_SIGN("10018", "签名不合法"),
    /**
     *
     */
    NONSUPPORT_CONTENT_TYPE("10019", "不支持的Content-Type"),

    /**
     *
     */
    NO_SIGN("10020", "没有签名字段"),

    /**
     * 请求加密异常
     */
    DATA_SIGN_ENCRYPT_EXCEPTION("10021", "数据加密异常"),
    /**
     * 文件上传失败
     */
    UPLOAD_FAIL("10022", "文件上传失败"),
    /**
     * 文件下载失败
     */
    DOWNLOAD_FAIL("10023", "文件下载失败"),
    /**
     * 暂无数据
     */
    NOT_EXIST_DATA("10024", "No data available"),

    ACCOUNT_OR_PWD_ERROR("10025", "账号或者密码错误"),

    ACCOUNT_NOT_EXISTS("10028", "账号不存在"),

    ACCOUNT_EXISTS("10030", "账号已存在"),

    ACCOUNT_STATUS_EXCEPTION("10026", "账号状态不正确"),

    SERVICE_NOT_EXISTS("10504", "服务不存在"),

    FILE_LIST_IS_EMPTY("10505", "文件列表为空"),

    FILE_UPLOAD_IO_ERROR("10506", "下载文件失败"),

    /* 定时任务：100001-109999 */
    FAILED_TO_CREATE_SCHEDULED_TASK("100001", "创建定时任务失败！"),
    FAILED_TO_UPDATE_SCHEDULED_TASK("100002", "更新定时任务失败！"),
    FAILED_TO_DELETED_SCHEDULED_TASK("100003", "删除定时任务失败！"),
    FAILED_TO_RESUME_SCHEDULED_TASK("100004", "恢复定时任务失败！"),
    TIMED_TASK_EXECUTION_FAILED("100005", "定时任务执行失败！"),
    TIMED_TASK_PAUSE_FAILED("100006", "定时任务暂停失败！"),
    THE_TASK_IS_NOT_OPERATIONAL("100007", "该任务不可操作！"),;

    private final String code;
    private final String desc;
}
