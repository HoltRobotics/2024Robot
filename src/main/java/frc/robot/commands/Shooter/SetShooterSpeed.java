// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Shooter;

public class SetShooterSpeed extends InstantCommand {
  private final double m_speed;
  private final Shooter m_shooter;

  public SetShooterSpeed(double speed, Shooter shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_speed = speed;
    m_shooter = shooter;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.setSpeed(m_speed);
  }
}
