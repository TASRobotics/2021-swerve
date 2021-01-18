package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.Module;

public class Teleop {
    private static Module i;
    private static Module ii;
    private static Module iii;
    private static Module iv;
    private static Module d;

    public Teleop(Module a, Module b, Module c, Module d) {
        i = a;
        ii = b;
        iii = c; 
        iv = d;
    }

    public static void init() {
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