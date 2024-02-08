// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimberConstants;

public class Climber extends SubsystemBase {
  private final CANSparkMax m_armClimber = new CANSparkMax(ClimberConstants.kArmClimberID, MotorType.kBrushless);
  private final CANSparkMax m_hookClimber = new CANSparkMax(ClimberConstants.kHookClimberID, MotorType.kBrushless);

  private final RelativeEncoder m_armEncoder = m_armClimber.getEncoder();
  private final RelativeEncoder m_hookEncoder = m_hookClimber.getEncoder();

  private final ShuffleboardTab m_tab = Shuffleboard.getTab("Main");

  private final GenericEntry m_armHeight = m_tab.add("Arm Height", getArmEncoderPosition()).withWidget(BuiltInWidgets.kDial).getEntry();

  /** Creates a new Climber. */
  public Climber() {

    m_armClimber.setInverted(true);
    m_hookClimber.setInverted(true);

    m_armClimber.setIdleMode(IdleMode.kBrake);
    m_hookClimber.setIdleMode(IdleMode.kBrake);

    m_armEncoder.setPosition(0);
    m_hookEncoder.setPosition(0);

    m_armEncoder.setPositionConversionFactor(1); //TODO: enter correct value
    m_hookEncoder.setPositionConversionFactor(1);


  }
// raises arms
  public void raiseArms() {
    // 4 is just a placeholder for the max rotaions of the motor before the robot breaks
    if (getArmEncoderPosition() < 4) {
      m_armClimber.set(0.5);
    } else {
      stopArms();
    }
    
  
  }
// lowers arms
  public void lowerArms() {
    m_armClimber.set(-0.5);

  }
// stops arms
  public void stopArms() {
    m_armClimber.stopMotor();
  }
// raises hooks
  public void raiseHooks() {
    m_hookClimber.set(0.5);
  }
// lowers hooks
  public void lowerHooks() {
    m_hookClimber.set(-0.5);
  }
// stops hooks
  public void stopHooks() {
    m_hookClimber.stopMotor();
  }
// gets arm Encoder value
  public double getArmEncoderPosition() {
    return m_armEncoder.getPosition();
  }
// gets hook encoder value
  public double getHookEncoderPosition() {
    return m_hookEncoder.getPosition();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    m_armHeight.setDouble(getArmEncoderPosition());
  }
}
