package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.Module;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpiutil.math;

public class Swerve {   

    private static enum ModuleLocation {
      I(0), 
      II(1), 
      III(2), 
      IV(3);


    };

    private int numMotors;
    private Module[] modules;
    private double[] moduleAngles;
    private double[][] rotV;
    private static const double DEGREES_PER_ROT = 360;


    // Makes an entire swerve drive with NUMMOTORS/2 modules

    public void onInit(int[] motorarray, double[] moduleAngles){
      this.numMotors = motorarray.length;
      this.moduleAngles = moduleAngles;
        for (int i=0;i<this.numMotors/2;i++){
            int[] motornums = new int[] {motorarray[2*i],motorarray[2*i+1]}
            this.modules[i].onInit(motornums);
            double moduleAngle = Math.PI/4+Math.PI/2*i;
            this.rotV[i] = {-Math.sin(moduleAngle),Math.cos(ModuleAngle)};
        }
    }

    public void Drive(float Vx, float Vy, float omega){
        for(int i=0;i<modules.length;i++){
            double[] totalV = new double[] {Vx-omega*rotV[i][0], Vy-omega*rotV[i][1]};
            modules[i].setVectorVelocity(totalV);
            modules[i].run();
        }
    }

    public void Drive(float Vx, float Vy, float omega, float heading){
      double rotangle = 2*Math.PI/DEGREES_PER_ROT*heading;
      double newVx = Vx*Math.cos(rotangle)-Vy*Math.sin(rotangle);
      double newVy = Vx*Math.sin(rotangle)+Vy*Math.cos(rotangle);
      Drive((float)newVx,(float)newVy,omega);
    }

    public void test(XboxController c) {
        Module d;
        switch(c)
          case (c.getAButtonPressed()) {
            d = modules[0];
            break;
          }
          if (c.getBButtonPressed() ) {
            d = modules[1];
          }
          if (c.getXButtonPressed() ) {
            d = modules[2];
          }
          if (c.getYButtonPressed() ) { 
            d = modules[3];
          }
          d.setMotorVelocity(c.getY(Hand.kRight)*40000);
          d.setRotorPos(c.getY(Hand.kLeft) / 4);
          if(c.getTriggerAxis(Hand.kLeft) > 0.5){
            d.setRotorPos(0.25);
          }
    }


}