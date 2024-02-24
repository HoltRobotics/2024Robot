// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Climber;

public class SetArmHeight extends InstantCommand {
    private final Climber m_climber;
  private final double m_height;

  public SetArmHeight(Climber climber, double height) {
    m_climber = climber;
    m_height = height;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_climber.enable();
    m_climber.setArmSetpoint(m_height);
  }
}
