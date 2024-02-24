// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;

public class WaitForShooterSpeed extends Command {
  private final double m_speed;
  private final Shooter m_shooter;

  /** Creates a new WaitForShooterSpeed. */
  public WaitForShooterSpeed(double speed, Shooter shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_speed = speed;
    m_shooter = shooter;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.setSpeed(m_speed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_shooter.atSpeed();
  }
}
