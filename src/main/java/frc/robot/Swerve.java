package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.Module;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpiutil.math;

public class Swerve {
    private Module i;
    private Module ii;
    private Module iii;
    private Module iv;
    


    int numMotors;
    SwerveModule[] modules = new SwerveModule[numMotors];


    // Makes an entire swerve drive with NUMMOTORS/2 modules
    public Swerve(int[] motorarray){
        this.numMotors = motorarray.length;
        for (int i=0;i<this.numMotors/2;i++){
            this.modules[i].init(new TalonFX(motorarray[2*i]),new TalonFX(motorarray[2*i+1]));
        }
    }

    public void Drive(Matrix V, float omega){
        Matrix moduleV = new Matrix(2,4);
        Matrix moduleCoords = new Matrix(2,4);
    }


    public Swerve(Module i, Module ii, Module iii, Module iv) {
        this.i = i;
        this.ii = ii;
        this.iii = iii;
        this.iv = iv;
    }

    public void init() {
        i.onInit(new int[] {0,1});
        ii.onInit(new int[] {2,3});
        iii.onInit(new int[] {4,5});
        iv.onInit(new int[] {6,7});    
    }

    public static void test(XboxController c) {
        if (c.getAButtonPressed() ) {
            d = i;
          }
          if (c.getBButtonPressed() ) {
            d = ii;
          }
          if (c.getXButtonPressed() ) {
            d = iii;
          }
          if (c.getYButtonPressed() ) { 
            d = iv;
          }
          d.setMotorVelocity(c.getY(Hand.kRight)*40000);
          d.setRotorPos(c.getY(Hand.kLeft) / 4);
          if(c.getTriggerAxis(Hand.kLeft) > 0.5){
            d.setRotorPos(0.25);
          }
    }

    public static void run(){
        i.run();
        ii.run();
        iii.run();
        iv.run();
    }

}