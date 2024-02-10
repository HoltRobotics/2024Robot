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

public class Shooter extends SubsystemBase {
  private final TalonFX m_topShooter = new TalonFX(1);
  private final TalonFX m_bottomShooter = new TalonFX(2);

  private final MotionMagicVelocityVoltage m_request = new MotionMagicVelocityVoltage(0);

  private double m_setpoint = 0;

  private final ShuffleboardTab m_tab = Shuffleboard.getTab("Main");
  private final GenericEntry m_targetRPS;
  private final GenericEntry m_actaualRPS;

  /** Creates a new Shooter. */
  public Shooter() {
    m_topShooter.setInverted(false);
    m_bottomShooter.setInverted(true);

    setSetpoint(0);

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
  }

  public void setSetpoint(double rps) {
    m_setpoint = rps;
  }

  public double getSetpoint() {
    return m_setpoint;
  }


  public double getActualRPS() {
    return (Math.abs(m_topShooter.getVelocity().getValueAsDouble()) + Math.abs(m_bottomShooter.getVelocity().getValueAsDouble())) / 2;
  }

  public void run() {
    m_topShooter.setControl(m_request.withVelocity(m_setpoint));
    m_bottomShooter.setControl(m_request.withVelocity(-m_setpoint));

  }

  public void stop() {
    m_topShooter.stopMotor();
    m_bottomShooter.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    m_setpoint = m_targetRPS.getDouble(1.0);
    m_actaualRPS.setDouble(getActualRPS());
  }
}
