// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.ProfiledPIDSubsystem;
import frc.robot.Constants.ArmConstants;

public class Arm extends ProfiledPIDSubsystem {
  private final CANSparkMax m_armMotor = new CANSparkMax(ArmConstants.kArmID, MotorType.kBrushless);

  private final RelativeEncoder m_encoder;

  /** Creates a new Arm. */
  public Arm() {
    super(
      // The ProfiledPIDController used by the subsystem
      new ProfiledPIDController( //TODO: tune this
        1,
        0,
        0,
        // The motion profile constraints
        new TrapezoidProfile.Constraints(10000, 50) //TODO: and this
      )
    );

    m_armMotor.setInverted(true); //TODO: check this
    m_encoder = m_armMotor.getEncoder();
    m_encoder.setPositionConversionFactor((125 * (32 / 12)) / 360); //TODO: test this might be right
    m_encoder.setPositionConversionFactor(1);
    m_encoder.setPosition(0);

    m_controller.setTolerance(1); //TODO: might have to change this

    setAngle(0);
    m_controller.setTolerance(2);
    enable();
  }

  @Override
  public void useOutput(double output, TrapezoidProfile.State setpoint) {
    // Use the output (and optionally the setpoint) here
    m_armMotor.setVoltage(output);
  }

  @Override
  public double getMeasurement() {
    // Return the process variable measurement here
    return getAngle();
  }

  public void setAngle(double angle) {
    setGoal(angle);
  }

  public double getAngle() {
    return m_encoder.getPosition();
  }

  public void up() {
    m_armMotor.set(0.25);
  }

  public void down() {
    m_armMotor.set(-0.25);
  }

  public double distanceToArmAngle(double distance) {
    return distance; //TODO: add the right equation
  }

  public double shooterAngleToArmAngle(double angle) {
    return angle; //TODO: find the right conversion
  }

  public void periodic() {
    super.periodic();
  }
}
