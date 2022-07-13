package org.firstinspires.ftc.teamcode.functionModule;

/**
 * 功能模块
 *
 * @author wfrfred
 * @date 2022/07/11
 */
public interface FunctionModule {
    /**
     * 内卷
     *
     * @param start 开始
     */
    void involving(boolean start);

    /**
     * 提升
     *
     * @param floor 层数
     */
    void elevating(int floor);

    /**
     * 射击！
     *
     * @param start 开始
     */
    void shooting(boolean start);

    /**
     * 旋转小黄鸭
     *
     * @param start 开始
     */
    void rotating(boolean start);
}
