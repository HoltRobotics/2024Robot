// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Feeder;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Feeder;

public class SetFeederSpeed extends InstantCommand {
  private final double m_fps;
  private final Feeder m_feeder;

  public SetFeederSpeed(double fps, Feeder feeder) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_fps = fps;
    m_feeder = feeder;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_feeder.enable();
    m_feeder.setGoal(m_fps);
  }
}
