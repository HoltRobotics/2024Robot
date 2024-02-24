// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Combo;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Arm.WaitForArmAngle;
import frc.robot.commands.Feeder.SetFeederSpeed;
import frc.robot.commands.Feeder.WaitForNote;
import frc.robot.commands.Intake.SetIntakeSpeed;
import frc.robot.commands.Shooter.SetShooterSpeed;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class RunIntake extends SequentialCommandGroup {
  /** Creates a new RunIntake. */
  public RunIntake(Arm m_arm, Feeder feeder, Intake intake, Shooter shooter) {
    addCommands(
      new WaitForArmAngle(0, m_arm),
      new ParallelCommandGroup(
        new WaitForNote(feeder),
        new SetIntakeSpeed(3, intake), //TODO: find a good speed for intaking
        new SetFeederSpeed(4, feeder),
        new SetShooterSpeed(-5, shooter)
      ),
      new ParallelCommandGroup(
        new SetIntakeSpeed(0, intake),
        new SetShooterSpeed(0, shooter),
        new SetFeederSpeed(-0.25, feeder) //TODO: find a good speed and time
      ),
      new WaitCommand(0.25),
      new SetFeederSpeed(0, feeder)
    );
  }
}
