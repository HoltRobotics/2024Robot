// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Intake;

public class SetIntakeSpeed extends InstantCommand {
  private final double m_fps;
  private final Intake m_intake;

  public SetIntakeSpeed(double fps, Intake intake) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_fps = fps;
    m_intake = intake;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_intake.setSpeed(m_fps);
  }
}
