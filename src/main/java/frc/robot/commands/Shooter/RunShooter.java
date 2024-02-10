// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;

public class RunShooter extends Command {
  private final Shooter m_shooter;
  private final Double m_speed;

  /**
   * Runs the shooter with the last set setpoint.
   * @param shooter The Shooter subsystem.
   */
  public RunShooter(Shooter shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_shooter = shooter;
    m_speed = m_shooter.getSetpoint();
    addRequirements(m_shooter);
  }

  /**
   * Runs the shooter with the given setpoint.
   * @param speed Speed in RPS.
   * @param shooter The Shooter subsystem.
   */
  public RunShooter(double speed, Shooter shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_shooter = shooter;
    m_speed = speed;
    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.setSetpoint(m_speed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_shooter.run();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
