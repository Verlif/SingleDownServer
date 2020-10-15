package sd.annotation;

/**
 * 指令接口
 */
public interface CommandT {

    /**
     * 命令描述
     * @return  描述的文本内容
     */
    String desc();

    /**
     * 命令的执行内容
     * 此方法运行于主线程
     * @param params 指令后的附加信息, 以空格作为分割
     */
    void run(String[] params);

}
