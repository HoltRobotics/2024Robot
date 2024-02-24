// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Combo;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.Arm.SetArmAngle;
import frc.robot.commands.Arm.WaitForArmAngle;
import frc.robot.commands.Feeder.SetFeederSpeed;
import frc.robot.commands.Feeder.WaitForNoNote;
import frc.robot.commands.Shooter.SetShooterSpeed;
import frc.robot.commands.Shooter.WaitForShooterSpeed;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;

public class SetShootAngle extends SequentialCommandGroup {
  /** Creates a new AutoShoot. */
  public SetShootAngle(double armAngle, Shooter shooter, Feeder feeder, Arm arm) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new ParallelCommandGroup(
        new WaitForShooterSpeed(ShooterConstants.kShooterSpeedRPS, shooter), //TOOD: find a good speed for shooting
        new WaitForArmAngle(armAngle, arm)
      ),
      new SetFeederSpeed(10, feeder), //TODO: find a good speed for feeding
      new WaitCommand(0.25), //TODO: find a good wait time for testing if we have a note
      new WaitForNoNote(feeder),
      new ParallelCommandGroup(
        new SetShooterSpeed(0, shooter),
        new SetFeederSpeed(0, feeder),
        new SetArmAngle(0, arm)
      )
    );
  }
}
