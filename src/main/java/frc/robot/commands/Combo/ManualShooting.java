// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Combo;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.Arm.SetArmAngle;
import frc.robot.commands.Intake.SetIntakeSpeed;
import frc.robot.commands.Shooter.SetShooterSpeed;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class ManualShooting extends ParallelCommandGroup {
  /** Creates a new ManualShooting. */
  public ManualShooting(double armAngle, double shooterRPM, double intakeFPS, Arm arm, Shooter shooter, Intake intake) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new SetArmAngle(armAngle, arm),
      new SetShooterSpeed(shooterRPM, shooter),
      new SetIntakeSpeed(intakeFPS, intake)
    );
  }
}
