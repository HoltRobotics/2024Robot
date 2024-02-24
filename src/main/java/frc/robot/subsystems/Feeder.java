// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.ProfiledPIDSubsystem;
import frc.robot.Constants.FeederConstants;
import frc.robot.LaserCANLS;

public class Feeder extends ProfiledPIDSubsystem {
  private final CANSparkMax m_feederMotor = new CANSparkMax(FeederConstants.kFeederID, MotorType.kBrushless);

  private final LaserCANLS m_LC = new LaserCANLS(FeederConstants.kLaserCANID);

  private final RelativeEncoder m_encoder;

  /** Creates a new FeederPro. */
  public Feeder() {
    super(
        // The ProfiledPIDController used by the subsystem
        new ProfiledPIDController(
            1,
            0,
            0,
            // The motion profile constraints
            new TrapezoidProfile.Constraints(10000, 10000)));

    m_feederMotor.setIdleMode(IdleMode.kCoast);
    m_feederMotor.setInverted(true);
    m_encoder = m_feederMotor.getEncoder();

    m_encoder.setVelocityConversionFactor((Math.PI * FeederConstants.kWheelDiameterIN) / (12 * 60 * FeederConstants.kGearboxRatio));

    setSpeed(0);
    enable();

    m_controller.setTolerance(1);
  }

  @Override
  public void useOutput(double output, TrapezoidProfile.State setpoint) {
    // Use the output (and optionally the setpoint) here
    m_feederMotor.setVoltage(output);
  }

  @Override
  public double getMeasurement() {
    // Return the process variable measurement here
    return m_encoder.getVelocity();
  }

  public void setSpeed(double speed) {
    setGoal(speed);
  }

  public boolean atSpeed() {
    return m_controller.atSetpoint();
  }

  public boolean haveNote() {
    return m_LC.get();
  }

  public void periodic() {
    super.periodic();
    // if(haveNote()) {
    //   System.out.println("has note");
    // } else {
    //   System.out.println("no note");
    // }
  }
}
