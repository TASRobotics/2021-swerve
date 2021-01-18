/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import frc.robot.Module;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  Module i = new Module();
  Module ii = new Module();
  Module iii = new Module();
  Module iv = new Module();
  Module d = i;
  Teleop teleop = new Teleop(i, ii, iii, iv);

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    Teleop.init();
    /**
    s.configFactoryDefault();
    s.setNeutralMode(NeutralMode.Coast);

    r.configFactoryDefault();
    r.setNeutralMode(NeutralMode.Coast);
    r.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0,10);
    
    r.setInverted(true);
    r.setSensorPhase(true);
		r.selectProfileSlot(0,0);
    r.config_kP(0, 0.4);
    r.config_kD(0, 0.4);
    r.configMotionAcceleration(80000);
    r.configMotionCruiseVelocity(50000);
    r.setSelectedSensorPosition(0);
    **/
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  /**
   * This function is called once when teleop is enabled.
   */
  private static XboxController c = new XboxController(0);
  @Override
  public void teleopInit() {
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    
    //r.set(ControlMode.MotionMagic, c.getY(Hand.kLeft)*10000);
    //s.set(ControlMode.PercentOutput, c.getY(Hand.kRight));
    
    //r.set(ControlMode.PercentOutput, c.getY(Hand.kLeft));

  }

  /**
   * This function is called once when the robot is disabled.
   */
}
