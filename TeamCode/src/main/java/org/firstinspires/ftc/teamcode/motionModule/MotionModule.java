package org.firstinspires.ftc.teamcode.motionModule;

/**
 * @author wfrfred
 * 运动模块的接口
 */
public interface MotionModule {
    /**
     * 平移运动
     * @param speed 平移的速度 [0,1]
     */
    void move(double speed);

    /**
     * 以几何中心为旋转中心进行自转
     * @param speed 每个轮子的速度 [-1,1]，正为顺时针，负为逆时针
     */
    void rotate(double speed);

    /**
     * 停止
     */
    void stop();

    /**
     * 通过手柄控制运动
     * @param move  前后速度 [-1,1]
     * @param turn  旋转速度 [-1,1]
     * @param k     缩放系数 [0,1]
     */
    void moveGamepad(double move, double turn, double k);
}
