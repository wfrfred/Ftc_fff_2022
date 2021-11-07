package org.firstinspires.ftc.teamcode.motionModule;

/**
 * @author wfrfred
 * 向量类，用于舵轮状态的表示和计算
 */
public class Vector {
    private double r;
    private double theta;
    private int positivity;//为了利用电机正反旋转，加入了正反的状态指示符

    /**
     * 构造函数
     * @param r     模长 R，若为负数则将positivity设定为负数
     * @param theta 角度 [-180,180]
     */
    public Vector(double r, double theta) {
        this.r = Math.abs(r);
        this.theta = theta;
        positivity = (r >= 0) ? 1 : -1;
    }

    /**
     * 单位向量的构造函数 模长默认为1
     *
     * @param theta 角度 [-180,180]
     */
    public Vector(double theta) {
        this(1, theta);
    }

    /**
     * 进行向量加法
     *
     * @param A 加数1
     * @param B 加数2
     * @return 返还两向量之和，若最后为零向量，方向默认为A的方向
     */
    public static Vector add(Vector A, Vector B) {
        double ax, ay, bx, by, retR;
        double retTheta = A.getTheta();
        ax = A.getR() * Math.cos(A.getTheta());
        ay = A.getR() * Math.sin(A.getTheta());
        bx = B.getR() * Math.cos(B.getTheta());
        by = B.getR() * Math.sin(B.getTheta());
        ax += bx;
        ay += by;
        retR = Math.sqrt(ax * ax + ay * ay);
        if (ax != 0) retTheta = Math.atan(ay / ax);
        else {
            if (ay < 0) retTheta = -90;
            else if (ay > 0) retTheta = 90;
        }
        return new Vector(retR, retTheta);
    }

    /**
     * 进行向量旋转，最后结果会限定在[-180,180]内
     *
     * @param angle 旋转的角度
     */
    public void turn(double angle) {
        theta += angle;
        while (theta > 180) theta -= 360;
        while (theta < -180) theta += 360;
    }

    public double getR() {
        return r * positivity;
    }

    public double getTheta() throws NullPointerException {
        return theta;
    }

    public int isPositive() {
        return positivity;
    }

    public void setPositivity(int positivity) {
        this.positivity = positivity;
    }

    /**
     * 进行向量的归一化操作
     *
     * @param vectors 输入一个向量组
     * @return 若最长向量长度大于1，将其设为1，并对其余向量进行等比缩放。否则返回原数组
     */
    public static Vector[] normalize(Vector[] vectors) {
        double rMax = Math.abs(vectors[0].getR());
        Vector[] ret = new Vector[vectors.length];
        for (Vector vector : vectors) {
            rMax = Math.max(rMax, Math.abs(vector.getR()));
        }
        if (rMax < 1) {
            return vectors;
        } else {
            for (int i = 0; i < vectors.length; i++) {
                ret[i] = new Vector(vectors[i].getR() / rMax, vectors[i].getTheta());
            }
            return ret;
        }
    }
}
