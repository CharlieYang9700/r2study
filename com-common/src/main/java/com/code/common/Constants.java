package com.code.common;

import java.time.LocalDate;

/**
 * @author ping
 * @date 2021-04-01
 * @description 常量表
 */
public class Constants {
    /**
     * 返回结果状态
     */
    public static final String STATUS = "status";
    /**
     * 返回结果字段
     */
    public static final String DATA = "data";
    /**
     * 返回错误信息
     */
    public static final String ERROR_MSG = "errorMsg";
    /**
     * 返回错误码
     */
    public static final String ERROR_CODE = "errorCode";
    /**
     * 返回成功标识
     */
    public static final String STATUS_OK = "OK";
    /**
     * 返回错误标识
     */
    public static final String STATUS_ERROR = "ERROR";
    /**
     * client name
     */
    public static final String APP_KEY = "appKey";
    /**
     * 加密版本号
     */
    public static final String VERSION = "version";
    /**
     * 签名字段
     */
    public static final String SIGN = "sign";
    /**
     * 使用rsa的验证版本
     */
    public static final String RSA_VERSION = "1";
    /**
     * 登录token
     */
    public static final String TOKEN = "Authorization";
    /**
     * 登录认证控制字段
     */
    public static final String TOKEN_CONTROL = "Access-Control-Expose-Headers";
    /**
     * 默认上下文用户的key
     */
    public static final String USER = "session_user";

    /**
     * 字符串截取或者拼接
     */
    public static final String SPLIT_STR = ",";

    /**
     * 空字符串
     */
    public static final String EMPTY_STR = "";

    /**
     * GET请求
     */
    public static final String GET_METHOD = "GET";
    /**
     * 日期格式化
     */
    public static final String DATE_TIMESTAMP_DESC_STR = "yyyy-MM-dd HH:mm:ss";
    /**
     * 时间戳格式化
     */
    public static final String DATE_TIME_STR = "yyyyMMddHHmmss";

    public static final String YEAR_MOTH_DAT_STR = "yyyyMMddHH";
    /**
     * and符号
     */
    public static final String AND_CHARACTER_STR = "&";
    /**
     * unknown
     */
    public static final String UNKNOWN_STR = "unknown";
    /**
     * 点
     */
    public static final String POINT_STR = ".";
    /**
     * 本机ip地址
     */
    public static final String LOCAL_IP_STR = "127.0.0.1";
    /**
     * 时区
     */
    public static final String TIME_ZONE_STR = "+8";

    /**
     * 接收短信手机区号
     */
    public static final String RECEIVE_SMS_ZONE = "86";

    /**
     * 发送短信成功标志
     */
    public static final int SEND_SMS_SUCCESS = 0;
    /**
     * 横线
     */
    public static final String LINE_STR = "-";
    /**
     * UTF-8编码字符串
     */
    public static final String UTF_8_ENCODE_STR = "UTF-8";
    /**
     * 等于符比较
     */
    public static final String EQ_COMPARE_STR = "=";
    /**
     * WEB
     */
    public static final String WEB_STR = "WEB";
    /**
     * 斜杠
     */
    public static final String SLASH_STR = "/";
    /**
     * json字符串
     */
    public static final String JSON_STR = "json";
    /**
     * 不存在的金额显示
     */
    public static final String EMPTY_AMOUNT_STR = "0.00";
    /**
     * 支付宝支付成功标识
     */
    public static final String ALI_PAY_SUCCESS_TAG = "success";
    /**
     * 问号
     */
    public static final String QUESTION_MARK_STR = "?";
    /**
     * 支付签名类型
     */
    public static final String ALI_PAY_SIGN_TYPE_STR = "sign_type";
    /**
     * 支付签名
     */
    public static final String ALI_PAY_SIGN_STR = "sign";

    public static final String REQUEST_JSON_VALUE = "handled_json_value";

    /**
     * JWT-code:
     */
    public static final String CODE_STR = "code";
    /**
     * 默认截取字符串
     */
    public static final String DEFAULT_SPILT_STR = ":";

    /**
     * 腾讯云图片处理的固定路径
     */
    public static final String IMAGE_OP_FLAG = "?imageMogr2/thumbnail/";
    /**
     * 数字类型正则
     */
    public static final String NUM_REG_STR = "[0-9]+";
    /**
     * 无归属
     */
    public static final String NO_BIND_CN_STR = "待分组教师";

    /**
     * 老师辅导间的用户表示
     */
    public static final String TEACHER_ROOM_USER_TAG = "T";

    /**
     * 学生辅导间的用户表示
     */
    public static final String STUDENT_ROOM_USER_TAG = "S";

    /**
     * 管理员辅导间的用户表示
     */
    public static final String ADMIN_ROOM_USER_TAG = "A";

    /**
     * 研发环境标识
     */
    public static final String DEV_ENV_STR = "dev";

    /**
     * Excel查询数据库条数
     */
    public static final int EXCEL_QUERY_DB_MAX_SIZE = 200;

    /**
     * 数字100
     */
    public static final int HUNDRED_NUMBER = 100;

    public static final String POINT = "\\.";

    /**
     * 百分号
     */
    public static final String PERCENT_SIGN = "%";

    public static final String DEF_VALUE = "@";

    public static final String TRUE_CH = "是";

    public static final String FALSE_CH = "否";

    public static final String SEMICOLON = ";\n";
    /**
     * HTTPS前缀
     */
    public static final String HTTPS_PRE = "https://";
    /**
     * http前缀
     */
    public static final String HTTP_PRE = "http://";
    /**
     * 连接分隔符
     */
    public static final String CONNECT_SPIT_TAG = "#";

    public static final String LOGIN_MS_CODE = "login_ms_code";

    public static final String ATTACH_HEALTHCARE = "HEALTHCARE";
    public static final String ATTACH_VIP = "VIP";
    public static final String ATTACH_RECHARGE = "RECHARGE";

    public static final Long CORE_AGENT_BUSINESS_ID = 10000L;

    public static final Long HEAD_USER_ID = 2L;


    /**
     * 默认初始化日期
     */
    public static final LocalDate DEFAULT_INIT_DATE = LocalDate.of(1970, 1, 1);
}
