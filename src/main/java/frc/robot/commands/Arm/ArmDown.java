// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;

public class ArmDown extends Command {
  private final Arm m_arm;
  private boolean m_pastLimit = false;

  /** Creates a new ArmDown. */
  public ArmDown(Arm arm) {
    m_arm = arm;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_arm.disable();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_arm.down();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if(m_pastLimit) { // Checks to see if the arm went past its min.
      m_arm.setAngle(0); // If it did, set it back to the min.
    } else{
      m_arm.setAngle(m_arm.getAngle()); // If it didn't set the PID setpoint to the new angle.
    }
    m_arm.enable(); // Re-enables the PID controller.
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
       if(m_arm.getAngle() < 0) { // Checks to see if the arm is past the max angle.
      m_pastLimit = true; // If it is, tells the command that it went past the limit.
      return true; // Ends the command.
    } else{
      return false;// If its not, let the command keep running.
    }

  }
}
