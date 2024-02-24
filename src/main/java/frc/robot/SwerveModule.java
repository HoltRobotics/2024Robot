package frc.robot;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.lib.math.Conversions;
import frc.lib.util.SwerveModuleConstants;

public class SwerveModule {
    public int m_moduleNumber;
    private Rotation2d m_angleOffset;

    private TalonFX m_angleMotor;
    private TalonFX m_driveMotor;
    private CANcoder m_angleEncoder;

    private final SimpleMotorFeedforward m_driveFeedForward = new SimpleMotorFeedforward(Constants.SwerveConstants.driveKS, Constants.SwerveConstants.driveKV, Constants.SwerveConstants.driveKA);

    /* drive motor control requests */
    private final DutyCycleOut m_driveDutyCycle = new DutyCycleOut(0);
    private final VelocityVoltage m_driveVelocity = new VelocityVoltage(0);

    /* angle motor control requests */
    private final PositionVoltage m_anglePosition = new PositionVoltage(0);

    public SwerveModule(int moduleNumber, SwerveModuleConstants moduleConstants){
        m_moduleNumber = moduleNumber;
        m_angleOffset = moduleConstants.angleOffset;
        
        /* Angle Encoder Config */
        m_angleEncoder = new CANcoder(moduleConstants.cancoderID);
        m_angleEncoder.getConfigurator().apply(Robot.m_ctreConfigs.swerveCANcoderConfig);

        /* Angle Motor Config */
        m_angleMotor = new TalonFX(moduleConstants.angleMotorID);
        m_angleMotor.getConfigurator().apply(Robot.m_ctreConfigs.swerveAngleFXConfig);
        resetToAbsolute();

        /* Drive Motor Config */
        m_driveMotor = new TalonFX(moduleConstants.driveMotorID);
        m_driveMotor.getConfigurator().apply(Robot.m_ctreConfigs.swerveDriveFXConfig);
        m_driveMotor.getConfigurator().setPosition(0.0);
    }

    public void setDesiredState(SwerveModuleState desiredState, boolean isOpenLoop){
        desiredState = SwerveModuleState.optimize(desiredState, getState().angle); 
        m_angleMotor.setControl(m_anglePosition.withPosition(desiredState.angle.getRotations()));
        setSpeed(desiredState, isOpenLoop);
    }

    private void setSpeed(SwerveModuleState desiredState, boolean isOpenLoop){
        if(isOpenLoop){
            m_driveDutyCycle.Output = desiredState.speedMetersPerSecond / Constants.SwerveConstants.maxSpeed;
            m_driveMotor.setControl(m_driveDutyCycle);
        }
        else {
            m_driveVelocity.Velocity = Conversions.MPSToRPS(desiredState.speedMetersPerSecond, Constants.SwerveConstants.wheelCircumference);
            m_driveVelocity.FeedForward = m_driveFeedForward.calculate(desiredState.speedMetersPerSecond);
            m_driveMotor.setControl(m_driveVelocity);
        }
    }

    public Rotation2d getCANcoder(){
        return Rotation2d.fromRotations(m_angleEncoder.getAbsolutePosition().getValue());
    }

    public void resetToAbsolute(){
        double absolutePosition = getCANcoder().getRotations() - m_angleOffset.getRotations();
        m_angleMotor.setPosition(absolutePosition);
    }

    public SwerveModuleState getState(){
        return new SwerveModuleState(
            Conversions.RPSToMPS(m_driveMotor.getVelocity().getValue(), Constants.SwerveConstants.wheelCircumference), 
            Rotation2d.fromRotations(m_angleMotor.getPosition().getValue())
        );
    }

    public SwerveModulePosition getPosition(){
        return new SwerveModulePosition(
            Conversions.rotationsToMeters(m_driveMotor.getPosition().getValue(), Constants.SwerveConstants.wheelCircumference), 
            Rotation2d.fromRotations(m_angleMotor.getPosition().getValue())
        );
    }
}