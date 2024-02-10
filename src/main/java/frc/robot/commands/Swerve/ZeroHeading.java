// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Swerve;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Swerve;

public class ZeroHeading extends InstantCommand {

  private final Swerve m_swerve;

  /**
   * Zeros the gyro of the robot. Where the robot is faceing, that's the new "forward".
   * @param swerve The Swerve subsystem.
   */
  public ZeroHeading(Swerve swerve) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_swerve = swerve;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_swerve.zeroHeading();
  }
}
