// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {
  private final CANSparkMax m_frontIntake = new CANSparkMax(IntakeConstants.kFrontIntakeID, MotorType.kBrushless);
  private final CANSparkMax m_backIntake = new CANSparkMax(IntakeConstants.kBackIntakeID, MotorType.kBrushless);

  private final RelativeEncoder m_frontEncoder;
  private final RelativeEncoder m_backEncoder;

  private final SparkPIDController m_frontPID;
  private final SparkPIDController m_backPID;

  private double m_setPoint = 0;
  private boolean m_enable = false;

  /** Creates a new Intake. */
  public Intake() { //TODO: test this subsystem
    m_frontIntake.setInverted(false);
    m_backIntake.setInverted(true);

    m_frontPID = m_frontIntake.getPIDController();
    m_backPID = m_backIntake.getPIDController();

    m_frontEncoder = m_frontIntake.getEncoder();
    m_backEncoder = m_backIntake.getEncoder();

    m_frontEncoder.setVelocityConversionFactor((Math.PI * IntakeConstants.kWheelDiameterIN) / (12 * 60 * IntakeConstants.kGearboxRatio));
    
    m_backEncoder.setVelocityConversionFactor((Math.PI * IntakeConstants.kWheelDiameterIN) / (12 * 60 * IntakeConstants.kGearboxRatio));

    setUpPIDControllers();

    setSpeed(0);
    enable();
  }

  private void setUpPIDControllers() { //TODO: tune these
    m_frontPID.setP(0.001);
    m_frontPID.setI(0.001);
    m_frontPID.setD(0.001);
    m_frontPID.setOutputRange(-1, 1);

    m_backPID.setP(0.001);
    m_backPID.setI(0.001);
    m_backPID.setD(0.001);
    m_backPID.setOutputRange(-1, 1);
  }

  public void setSpeed(double speed) {
    m_setPoint = speed;
  }

  public void enable() {
    m_enable = true;
  }

  public void disable() {
    m_enable = false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if (m_enable) {
      m_frontPID.setReference(m_setPoint, CANSparkMax.ControlType.kVelocity);
      m_backPID.setReference(m_setPoint, CANSparkMax.ControlType.kVelocity);
    }
  }
}
