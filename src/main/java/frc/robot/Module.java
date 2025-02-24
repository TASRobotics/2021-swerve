package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;


public class Module {
    
    private static enum MotorMode {
        POSITION, VELOCITY
    };

    private TalonFX rotor;
    private TalonFX motor;
    private CANCoder angle;

    private MotorMode runMode = MotorMode.VELOCITY;
    private double motorPos = 0;
    private double motorVel = 0;
    private double rotorTargPos = 0;

    private double radius;
    private double rHat;
    private double zeroAngle;

    private static final double FALCON_TICKS = 2048;
    private static final double ROTOR_RATIO = 12;
    private static final double TOLERANCE = .005;
    private static final double ROTOR_ANGLE_RATIO = ROTOR_RATIO * FALCON_TICKS;
    private static final double DEGREES_IN_REV = 360;
    private static final double INIT_ANGLE = 90/DEGREES_IN_REV;
    private static final double MAX_MOTOR_RPM = 6300;
    private static final double SECONDS_IN_MINUTE = 60;
    private static final double MAX_MOTOR_SPEED = FALCON_TICKS*MAX_MOTOR_RPM/(10*SECONDS_IN_MINUTE);



    /**
     * Called at the start of autonomous or teleop.
     * 
     * @param timestamp
     */
    public void onStart(double timestamp) {}

    /**
     * Called once when the submodule is initialized.
     */
    public void onInit(int ids[],int angleSensor, double zeroAngle) {
        this.zeroAngle = zeroAngle;
        motor = new TalonFX(ids[0]);
        rotor = new TalonFX(ids[1]);
        angle = new CANCoder(angleSensor);
        motor.configFactoryDefault();
        motor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        motor.selectProfileSlot(1,0);
        motor.config_kP(0, 0.3);
        motor.config_kD(0, 0.05);
        motor.config_kP(1, 0.05);
        motor.config_kD(1, 0.0);
        motor.configMotionAcceleration(100000);
        motor.configMotionCruiseVelocity(80000);
        motor.setInverted(false);

        rotor.configFactoryDefault();
        rotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        rotor.selectProfileSlot(0,0);
        rotor.config_kP(0, 0.8);
        rotor.config_kD(0, 1.3);
        rotor.configMotionAcceleration(100000);
        rotor.configMotionCruiseVelocity(80000);
        rotor.setInverted(true);
        rotor.setSelectedSensorPosition(-0*(int)(INIT_ANGLE*ROTOR_ANGLE_RATIO));
        
        //angle.configSensorInitializationStrategy(SensorInitializationStrategy.BootToAbsolutePosition);
        //angle.getAllConfigs(allConfigs);
        rotor.setSelectedSensorPosition((int)((angle.getAbsolutePosition()-zeroAngle)/360*ROTOR_ANGLE_RATIO));
    }

    public void changeMotorMode(MotorMode mode){
        runMode = mode;
        switch(runMode){
            case POSITION:        
                motor.selectProfileSlot(
                    0, 0);
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
        pos = (pos) / 360;
        double dPos = pos - getAngle();
        dPos = dPos % 1;
        if (dPos < 0) dPos += 1;

        dPos -= 0.25; // range: [0.25, 0.75)
        if (dPos > 0.25) dPos = reverseMotor(dPos);
        rotorTargPos = pos;//dPos + getAngle();
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
     * Sets the module to a 2-D target velocity.  Input array should have magnitude less than 1
     */
    public void setVectorVelocity(double Vx, double Vy){
        
        this.setRotorPos(DEGREES_IN_REV/(2*Math.PI)*Math.atan2(Vy, Vx));
        this.setMotorVelocity(Math.sqrt(Vx*Vx+Vy*Vy)*MAX_MOTOR_SPEED);
    }


    /**
     * 
     * Runs components in the submodule that have continuously changing 
     * inputs.
     */
    public void run() {
        System.out.println("Module:"+angle.getDeviceID());
        System.out.println("rotorTargPos:"+rotorTargPos);
        System.out.println("motorVel:"+motorVel);
        //System.out.println("abs encoder:"+angle.getAbsolutePosition());
        //rotor.setSelectedSensorPosition((int)((angle.getAbsolutePosition()-zeroAngle)*ROTOR_ANGLE_RATIO));        
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
        double rawAngle = rotor.getSelectedSensorPosition(0) / ROTOR_ANGLE_RATIO;
        return INIT_ANGLE/ROTOR_ANGLE_RATIO - rawAngle;
    }
}