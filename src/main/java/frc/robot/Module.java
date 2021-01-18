package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class Module {
    
    private static enum MotorMode {
        POSITION, VELOCITY
    };

    private TalonFX rotor;
    private TalonFX motor;

    private MotorMode runMode = MotorMode.VELOCITY;
    private double motorPos = 0;
    private double motorVel = 0;
    private double rotorTargPos = 0;

    private double radius;
    private double rHat;

    private static final double FALCON_TICKS = 2048;
    private static final double ROTOR_RATIO = 12;
    private static final double ROTOR_ANGLE_RATIO = ROTOR_RATIO * FALCON_TICKS;

    /**
     * Called at the start of autonomous or teleop.
     * 
     * @param timestamp
     */
    public void onStart(double timestamp) {}

    /**
     * Called once when the submodule is initialized.
     */
    public void onInit(int ids[]) {
        motor = new TalonFX(ids[0]);
        rotor = new TalonFX(ids[1]);
        
        motor.configFactoryDefault();
        motor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        motor.selectProfileSlot(1,0);
        motor.config_kP(0, 0.3);
        motor.config_kD(0, 0.05);
        motor.config_kP(1, 0.05);
        motor.config_kD(1, 0.0);
        motor.configMotionAcceleration(100000);
        motor.configMotionCruiseVelocity(80000);

        rotor.configFactoryDefault();
        rotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        rotor.selectProfileSlot(0,0);
        rotor.config_kP(0, 0.8);
        rotor.config_kD(0, 1.3);
        rotor.configMotionAcceleration(100000);
        rotor.configMotionCruiseVelocity(80000);
        rotor.setSelectedSensorPosition(0);
    }

    public void changeMotorMode(MotorMode mode){
        runMode = mode;
        switch(runMode){
            case POSITION:        
                motor.selectProfileSlot(0, 0);
            break;
            case VELOCITY:            
                motor.selectProfileSlot(1, 0);
            break;
        }
    }

/**
 * sets the rotor to PID to a position
 * @param pos the position to go to in units of full rotations
 */
    public void setRotorPos(double pos) {
        double dPos = pos - getAngle();
        dPos = dPos % 1;
        if (dPos < 0) dPos += 1;
        dPos -= 0.25; // range: [0.25, 0.75)
        if (dPos > 0.25) dPos = reverseMotor(dPos);
        rotorTargPos = dPos + getAngle();
    }

    private double reverseMotor(double dPos) {
        return dPos - 0.5;
    }

    public void setMotorVelocity(double speed) {
        motorVel = speed;
    }

    public void setMotorPosition(double position) {
        motorPos = position;    
    }

    /**
     * Reads cached inputs & calculate outputs.
     */
    public void update(double timestamp) {
        
    }
    
    /**
     * Runs components in the submodule that have continuously changing 
     * inputs.
     */
    public void run() {
        System.out.println("rotorTargPos:"+rotorTargPos);
        System.out.println("motorVel:"+motorVel);
        rotor.set(ControlMode.MotionMagic, rotorTargPos * ROTOR_ANGLE_RATIO);
        switch(runMode){
            case POSITION:        
                motor.set(ControlMode.MotionMagic, motorPos);
            break;
            case VELOCITY:            
                motor.set(ControlMode.Velocity, motorVel);
            break;
        }
    }

    /**
     * Resets the sensor(s) to zero.
     */
    public void zero() {
    }

    public double getAngle() {
        return rotor.getSelectedSensorPosition(0) / ROTOR_ANGLE_RATIO;
    }
}