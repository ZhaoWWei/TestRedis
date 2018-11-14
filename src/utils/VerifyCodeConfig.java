package utils;

public interface VerifyCodeConfig {
	
    public static String PHONE_PREFIX = "phoneno:";  //ǰ׺
	
    public static String PHONE_SUFFIX = ":code";    //��֤��key��׺ 
    
    public static String COUNT_SUFFIX = ":count";    //������key��׺
    
    public static int CODE_LEN = 6;   //����볤��
    
    public static int CODE_TIMEOUT = 120;//�������Чʱ��

    public static int COUNT_TIMES_1DAY = 3;  //������෢�ʹ���
    
    public static int SECONDS_PER_DAY = 60*60*24; //��������
    
    public static String HOST = "192.168.67.140"; //��������
    
    public static int PORT = 6379; //��������
}

