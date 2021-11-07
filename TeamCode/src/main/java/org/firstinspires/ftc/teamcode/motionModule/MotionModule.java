package org.firstinspires.ftc.teamcode.motionModule;

/**
 * @author wfrfred
 * 运动模块的接口
 */
public interface MotionModule {
    /**
     * 平移运动
     * @param speed 平移的速度 [0,1]
     * @param angle 平移的方向，以向前为0°，逆时针为正 [-180,180]
     */
    void move(double speed, double angle);

    /**
     * 以几何中心为旋转中心进行自转
     * @param speed 每个轮子的速度 [-1,1]
     */
    void rotate(double speed);

    /**
     * 平移并旋转
     * @param rotateSpeed   旋转速度 [-1,1]
     * @param moveDirection 平移方向，以向前为0°，逆时针为正 [-180,180]
     * @param speed         平移速度 [1,-1]
     */
    void rotateAndMove(double rotateSpeed, double moveDirection, double speed);

    /**
     * 舵轮停止并复位
     */
    void stop();

    /**
     * 通过手柄控制运动
     * @param move  前后速度 [-1,1]
     * @param turn  旋转速度 [-1,1]
     * @param fun   左右速度 [-1,1]
     * @param k     缩放系数 [0,1]
     */
    void moveGamepad(double move, double turn, double fun, double k);
}
