package com.zlin.property;

public class Constant {
	/**
	 * 所有的action的监听的必须要以"ACTION_"开头
	 * 
	 */
	/**
	 * 加密的公共秘钥
	 */

	public static String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxGIk4mSchwWTUUJXH+A4Boscq\r"
			+ "7Wqd8hZyBdvYWRNClIZUCGlWzbluogLhelEqFBhnobVKQPqG7RrMTCuVb3bLv/LD\r"
			+ "kjEvWFmgECOhVE23/3bHIAgSHjYB4GTqZfpEsWY/4tclVcC+zboG2iai5oo4bnrV\r"
			+ "QmSYrIUjQx/s8IjxkwIDAQAB\r";
	/**
	 * 是否加密
	 */
	public static boolean isEncrypt = false;

	/**
	 * 数据库相关
	 */
	public static String BASE_DIR = android.os.Environment.getExternalStorageDirectory()+"/greatsoft";
	public static String DB_DIR = BASE_DIR+"/db";
	public static String PIC_DIR = BASE_DIR +"/pics";
	public static String DB_CHECKS = DB_DIR+"/checks.db";
	public static String DB_LOCAL = "followup.db";
	public static String DB_APK = BASE_DIR+"/chss.apk";
	public static String LOG_DIR = BASE_DIR+"/logs/";

	/**
	 * 动态添加空间的ID
	 */
	@android.support.annotation.IdRes
	public static int EXAM_ID = 1;


	/**
	 * 访问网络是否成功
	 */
	public static String NET_SUCCESS = "0";
	public static String NET_ERROR = "1";

	/**
	 * 服务项目:个人健康档案 1000
	 */
	public static final String SERVICE_ITEM_1000 = "1000";

	/**
	 * 服务项目:档案首页 1001
	 */
	public static final String SERVICE_ITEM_1001 = "1001";

	/**
	 * 服务项目:封面 1002
	 */
	public static final String SERVICE_ITEM_1002 = "1002";

	/**
	 * 服务项目:个人基本信息 1003
	 */
	public static final String SERVICE_ITEM_1003 = "1003";

	/**
	 * 服务项目:健康体检 1004
	 */
	public static final String SERVICE_ITEM_1004 = "1004";

	/**
	 * 服务项目:门诊记录 1005
	 */
	public static final String SERVICE_ITEM_1005 = "1005";

	/**
	 * 服务项目:住院记录 1006
	 */
	public static final String SERVICE_ITEM_1006 = "1006";

	/**
	 * 服务项目:一体机检测记录 1007
	 */
	public static final String SERVICE_ITEM_1007 = "1007";

	/**
	 * 服务项目:健康小屋 1008
	 */
	public static final String SERVICE_ITEM_1008 = "1008";

	/**
	 * 服务项目:家庭成员 1009
	 */
	public static final String SERVICE_ITEM_1009 = "1009";

	/**
	 * 服务项目:0~6岁儿童健康管理 2000
	 */
	public static final String SERVICE_ITEM_2000 = "2000";

	/**
	 * 服务项目:出生医学证明 2001
	 */
	public static final String SERVICE_ITEM_2001 = "2001";

	/**
	 * 服务项目:查看新生儿听力筛查 2002
	 */
	public static final String SERVICE_ITEM_2002 = "2002";

	/**
	 * 服务项目:查看新生儿疾病筛查 2003
	 */
	public static final String SERVICE_ITEM_2003 = "2003";

	/**
	 * 服务项目:新生儿家庭访视 2004
	 */
	public static final String SERVICE_ITEM_2004 = "2004";

	/**
	 * 服务项目:1岁以内儿童健康检查 2005
	 */
	public static final String SERVICE_ITEM_2005 = "2005";

	/**
	 * 服务项目:1~2岁儿童健康检查 2006
	 */
	public static final String SERVICE_ITEM_2006 = "2006";

	/**
	 * 服务项目:3~6岁儿童健康检查 2007
	 */
	public static final String SERVICE_ITEM_2007 = "2007";

	/**
	 * 服务项目:儿童花名册 2009
	 */
	public static final String SERVICE_ITEM_2009 = "2009";

	/**
	 * 服务项目:孕产妇健康管理 3000
	 */
	public static final String SERVICE_ITEM_3000 = "3000";

	/**
	 * 服务项目:第一次产前随访服务记录 3001
	 */
	public static final String SERVICE_ITEM_3001 = "3001";

	/**
	 * 服务项目:第2~5次产前随访服务记录 3002
	 */
	public static final String SERVICE_ITEM_3002 = "3002";

	/**
	 * 服务项目:产后访视记录 3003
	 */
	public static final String SERVICE_ITEM_3003 = "3003";

	/**
	 * 服务项目:产后42天健康记录 3004
	 */
	public static final String SERVICE_ITEM_3004 = "3004";

	/**
	 * 服务项目:分娩登记 3005
	 */
	public static final String SERVICE_ITEM_3005 = "3005";

	/**
	 * 服务项目:妇女花名册 3006
	 */
	public static final String SERVICE_ITEM_3006 = "3006";

	/**
	 * 服务项目:孕期信息补充表 3007
	 */
	public static final String SERVICE_ITEM_3007 = "3007";

	/**
	 * 服务项目:高危妊娠评分 3008
	 */
	public static final String SERVICE_ITEM_3008 = "3008";

	/**
	 * 服务项目:孕产登记 3009
	 */
	public static final String SERVICE_ITEM_3009 = "3009";

	/**
	 * 服务项目:老年人健康管理 4000
	 */
	public static final String SERVICE_ITEM_4000 = "4000";

	/**
	 * 服务项目:自理能力评估 4001
	 */
	public static final String SERVICE_ITEM_4001 = "4001";

	/**
	 * 服务项目:抑郁调查 4002
	 */
	public static final String SERVICE_ITEM_4002 = "4002";

	/**
	 * 服务项目:认知调查 4003
	 */
	public static final String SERVICE_ITEM_4003 = "4003";

	/**
	 * 服务项目:中医药保健 4004
	 */
	public static final String SERVICE_ITEM_4004 = "4004";

	/**
	 * 服务项目:老年人花名册 4005
	 */
	public static final String SERVICE_ITEM_4005 = "4005";

	/**
	 * 服务项目:疾病管理 5000
	 */
	public static final String SERVICE_ITEM_5000 = "5000";

	/**
	 * 服务项目:高血压患者随访服务记录 5001
	 */
	public static final String SERVICE_ITEM_5001 = "5001";

	/**
	 * 服务项目:2型糖尿病患者随访服务记录 5002
	 */
	public static final String SERVICE_ITEM_5002 = "5002";

	/**
	 * 服务项目:恶性肿瘤 5003
	 */
	public static final String SERVICE_ITEM_5003 = "5003";

	/**
	 * 服务项目:重性精神病患者随访服务记录 5004
	 */
	public static final String SERVICE_ITEM_5004 = "5004";

	/**
	 * 服务项目:传染病 5005
	 */
	public static final String SERVICE_ITEM_5005 = "5005";

	/**
	 * 服务项目:肺结核患者随访 5006
	 */
	public static final String SERVICE_ITEM_5006 = "5006";

	/**
	 * 服务项目:肺结核患者第一次入户随访 5007
	 */
	public static final String SERVICE_ITEM_5007 = "5007";

	/**
	 * 服务项目:结核病专档5015
	 */
	public static final String SERVICE_ITEM_5015 = "5015";

	/**
	 * 服务项目:结核病转诊单5016
	 */
	public static final String SERVICE_ITEM_5016 = "5016";

	/**
	 * 服务项目:脑卒中患者管理卡*5018
	 */
	public static final String SERVICE_ITEM_5018 = "5018";

	/**
	 * 服务项目:脑卒中患者随访服务记录*5019
	 */
	public static final String SERVICE_ITEM_5019 = "5019";

	/**
	 * 服务项目:冠心病患者管理卡*5020
	 */
	public static final String SERVICE_ITEM_5020 = "5020";

	/**
	 * 服务项目:冠心病患者随访服务记录**5021
	 */
	public static final String SERVICE_ITEM_5021 = "5021";

	/**
	 * 服务项目:糖尿病高危随访 5008
	 */
	public static final String SERVICE_ITEM_5008 = "5008";

	/**
	 * 服务项目:高血压高危人群管理卡(随访记录卡)5009
	 */
	public static final String SERVICE_ITEM_5009 = "5009";

	/**
	 * 服务项目:高血压患者管理卡5010
	 */
	public static final String SERVICE_ITEM_5010 = "5010";

	/**
	 * 服务项目:糖尿病患者管理卡5011
	 */
	public static final String SERVICE_ITEM_5011 = "5011";

	/**
	 * 服务项目:糖尿病高危管理卡5012
	 */
	public static final String SERVICE_ITEM_5012 = "5012";

	/**
	 * 服务项目:高血压高危管理卡5013
	 */
	public static final String SERVICE_ITEM_5013 = "5013";

	/**
	 * 服务项目:重性精神疾病患者个人信息补充表5014
	 */
	public static final String SERVICE_ITEM_5014 = "5014";

	/**
	 * 预防接种卡7001
	 */
	public static final String SERVICE_ITEM_7001 = "7001";

	/**
	 * 计划内免疫接种7002
	 */
	public static final String SERVICE_ITEM_7002 = "7002";

	/**
	 * 异常反应处理7003
	 */
	public static final String SERVICE_ITEM_7003 = "7003";

	/**
	 * 疫苗免疫程序7004
	 */
	public static final String SERVICE_ITEM_7004 = "7004";

	/**
	 * 听力言语残疾随访表*
	 */
	public static final String SERVICE_ITEM_9001 = "9001";

	/**
	 * 肢体残疾随访表*
	 */
	public static final String SERVICE_ITEM_9002 = "9002";

	/**
	 * 智力残疾随访表*
	 */
	public static final String SERVICE_ITEM_9003 = "9003";

	/**
	 * 视力残疾随访表*9004
	 */
	public static final String SERVICE_ITEM_9004 = "9004";

	/**
	 * 接诊记录表*8004
	 */
	public static final String SERVICE_ITEM_8004 = "8004";

	/**
	 * 会诊记录表*8005
	 */
	public static final String SERVICE_ITEM_8005 = "8005";

	/**
	 * 双向转诊单*8006
	 */
	public static final String SERVICE_ITEM_8006 = "8006";
	/**
	 * 服务器的配置
	 */
	public static final String LOGIN_CONFIG = "xmpp_login_config";// 登录设置

	public static final String USERNAME = "username";// 账户

	public static final String PASSWORD = "password";// 密码

	public static final String XMPP_HOST = "xmpp_host";// 地址

	public static final String XMPP_PORT = "xmpp_port";// 端口

	public static final String XMPP_SEIVICE_NAME = "xmpp_service_name";// 服务名

	public static final String IS_AUTOLOGIN = "isAutoLogin";// 是否自动登录

	public static final String IS_NOVISIBLE = "isNovisible";// 是否隐身

	public static final String IS_REMEMBER = "isRemember";// 是否记住账户密码

	public static final String IS_LOGIN_RUNNING = "isLoingRunning";// 是否登录后运行（前台后太均算true,只有未登录为false）

	// public static final String IS_FIRSTSTART = "isFirstStart";// 是否首次启动

	/**
	 * 登录提示
	 */
	public static final int LOGIN_SECCESS = 0;// 成功

	public static final int HAS_NEW_VERSION = 1;// 发现新版本

	public static final int IS_NEW_VERSION = 2;// 当前版本为最新

	public static final int LOGIN_ERROR_ACCOUNT_PASS = 3;// 账号或者密码错误

	public static final int SERVER_UNAVAILABLE = 4;// 无法连接到服务器

	public static final int LOGIN_ERROR = 5;// 连接失败

	public static final String XMPP_CONNECTION_CLOSED = "xmpp_connection_closed";// 连接中断

	public static final String LOGIN = "login"; // 登录

	public static final String RELOGIN = "relogin"; // 重新登录

	/**
	 * 好友列表 组名
	 */
	public static final String ALL_FRIEND = "所有好友";// 所有好友

	public static final String NO_GROUP_FRIEND = "未分组好友";// 所有好友

	/**
	 * 系统消息
	 */
	public static final String ACTION_SYS_MSG = "action_sys_msg";// 消息类型关键字

	public static final String MSG_TYPE = "broadcast";// 消息类型关键字

	public static final String SYS_MSG = "sysMsg";// 系统消息关键字

	public static final String SYS_MSG_DIS = "系统消息";// 系统消息

	public static final String ADD_FRIEND_QEQUEST = "好友请求";// 系统消息关键字

	/**
	 * 请求某个操作返回的状态值
	 */
	public static final int SUCCESS = 0;// 存在

	public static final int FAIL = 1;// 不存在

	public static final int UNKNOWERROR = 2;// 出现莫名的错误.

	public static final int NETWORKERROR = 3;// 网络错误



	/***
	 * 创建请求xml操作类型
	 */
	public static final String add = "00";// 增加

	public static final String rename = "01";// 增加

	public static final String remove = "02";// 增加

	/**
	 * 是否在线的SharedPreferences名称
	 */
	public static final String PREFENCE_USER_STATE = "prefence_user_state";

	public static final String IS_ONLINE = "is_online";

	/**
	 * 精确到毫秒
	 */
	public static final String MS_FORMART = "yyyy-MM-dd HH:mm:ss SSS";


	public static int O_COLOR = 0xFFFD8556;	//老
	public static int H_COLOR = 0xFF50AEFF;	//高
	public static int D_COLOR = 0xFF7AC187;	//糖
	public static int S_COLOR = 0xFFCD9CED;	//糖



}
