// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
  private final TalonFX m_topShooter = new TalonFX(ShooterConstants.kTopShooterID);
  private final TalonFX m_bottomShooter = new TalonFX(ShooterConstants.kBottomShooterID);

  private final MotionMagicVelocityVoltage m_request = new MotionMagicVelocityVoltage(0);

  private double m_shooterSetpoint = 0;

  private final ShuffleboardTab m_tab = Shuffleboard.getTab("Main");
  private final GenericEntry m_targetRPS;
  private final GenericEntry m_actaualRPS;

  private boolean m_enable;
  private double m_tolerance = 0;

  /** Creates a new Shooter. */
  public Shooter() {
    TalonFXConfiguration talonFXConfigs = new TalonFXConfiguration();

    Slot0Configs slot0Configs = talonFXConfigs.Slot0;
    slot0Configs.kS = 0.25;
    slot0Configs.kV = 0.12;
    slot0Configs.kA = 0.01;
    slot0Configs.kP = 0.11;
    slot0Configs.kI = 0;
    slot0Configs.kD = 0;

    var motionMagicConfigs = talonFXConfigs.MotionMagic;
    motionMagicConfigs.MotionMagicAcceleration = 100;

    m_topShooter.getConfigurator().apply(talonFXConfigs);
    m_bottomShooter.getConfigurator().apply(talonFXConfigs);

    m_targetRPS = m_tab.add("Set RPS", 1.0).getEntry();
    m_actaualRPS = m_tab.add("Actual RPS", getActualRPS()).getEntry();

    setSpeed(0);

    enable();
  }

  public void setSpeed(double rps) {
    m_shooterSetpoint = rps;
  }

  public double getShooterSetpoint() {
    return m_shooterSetpoint;
  }

  public void setTolerance(double tolerance) {
    m_tolerance = tolerance;
  }

  public boolean atSpeed() {
    if(Math.abs(getActualRPS() - m_shooterSetpoint) > m_tolerance) {
      return false;
    } else {
      return true;
    }
  }

  public double getActualRPS() {
    return (Math.abs(m_topShooter.getVelocity().getValueAsDouble()) + Math.abs(m_bottomShooter.getVelocity().getValueAsDouble())) / 2;
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
    // m_shooterSetpoint = m_targetRPS.getDouble(1.0);
    m_actaualRPS.setDouble(getActualRPS());
    if(m_enable) {
      m_topShooter.setControl(m_request.withVelocity(-m_shooterSetpoint));
      m_topShooter.feed();
      m_bottomShooter.setControl(m_request.withVelocity(m_shooterSetpoint));
      m_bottomShooter.feed();
    } else {
      m_topShooter.setControl(m_request.withVelocity(0));
      m_topShooter.feed();
      m_bottomShooter.setControl(m_request.withVelocity(-0));
      m_bottomShooter.feed();
    }
  }
}
