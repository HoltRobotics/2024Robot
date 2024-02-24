// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;
import frc.robot.Constants.LimelightConstants;

public class Limelight extends SubsystemBase {
  /** Creates a new Limelight. */
  public Limelight() {
  }

  public double getDistance() {
    return (LimelightConstants.kGoalHeightMeters - LimelightConstants.kLimelightLensHeightMeters) / Math.tan(LimelightConstants.kMountAngleRadians + Units.degreesToRadians(LimelightHelpers.getTY("")));
  }

  public double distanceToArmAngle(double distance) {
    return distance; //TODO: enter right equation
  }

  public double getTX() {
    return LimelightHelpers.getTX("");
  }

  public double getTY() {
    return LimelightHelpers.getTY("");
  }

  public boolean hasCorrectTarget() {
    if (DriverStation.getAlliance().get() == Alliance.Blue && LimelightHelpers.getFiducialID("") == 1) {
      return true;
    } else if (DriverStation.getAlliance().get() == Alliance.Red && LimelightHelpers.getFiducialID("") == 3) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
