package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS5Controller;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import com.pathplanner.lib.auto.*;

import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.FeederConstants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.Arm.ArmDown;
import frc.robot.commands.Arm.ArmUp;
import frc.robot.commands.Arm.SetArmAngle;
import frc.robot.commands.Climber.MoveArmsDown;
import frc.robot.commands.Climber.MoveArmsUp;
import frc.robot.commands.Combo.AutoShootSpeaker;
import frc.robot.commands.Combo.ManualShooting;
import frc.robot.commands.Combo.RunIntake;
import frc.robot.commands.Combo.SetShootAngle;
import frc.robot.commands.Feeder.SetFeederSpeed;
import frc.robot.commands.Intake.SetIntakeSpeed;
import frc.robot.commands.Shooter.SetShooterSpeed;
import frc.robot.commands.Swerve.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    /* Controllers */
    private final PS5Controller m_driver = new PS5Controller(OIConstants.kDriverController);
    private final Joystick m_operator = new Joystick(OIConstants.kDriveOperator);
    
    /* Subsystems */
    private final Swerve m_swerve = new Swerve();
    private final Shooter m_shooter = new Shooter();
    private final Arm m_arm = new Arm();
    private final Intake m_intake = new Intake();
    private final Feeder m_feeder = new Feeder();
    private final Limelight m_light = new Limelight();
    private final Climber m_climber = new Climber();

    private final ShuffleboardTab m_tab = Shuffleboard.getTab("Main");

    private final SendableChooser<Command> m_chooser;

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        m_swerve.setDefaultCommand(
            new TeleopSwerve(
                () -> m_driver.getLeftY(), 
                () -> m_driver.getLeftX(), 
                () -> m_driver.getRightX(), 
                () -> m_driver.getR1Button(),
                m_swerve
            )
        );

        // Configure the button bindings
        configureButtonBindings();

        m_chooser = AutoBuilder.buildAutoChooser();

        m_tab.add("Auto Chooser", m_chooser);
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        /* Driver Buttons */
        new JoystickButton(m_driver, PS5Controller.Button.kOptions.value).onTrue(new ZeroHeading(m_swerve).ignoringDisable(true));
        new JoystickButton(m_driver, PS5Controller.Button.kCircle.value).whileTrue(new RunIntake(m_arm, m_feeder, m_intake, m_shooter)).onFalse(new SetIntakeSpeed(0, m_intake).alongWith(new SetFeederSpeed(0, m_feeder)).alongWith(new SetShooterSpeed(0, m_shooter)));
        // new JoystickButton(m_driver, PS5Controller.Button.kCross.value).whileTrue(new AutoShootSpeaker(m_shooter, m_swerve, m_light, m_feeder, m_arm));

        new JoystickButton(m_operator, 1).whileTrue(new ArmUp(m_arm));
        new JoystickButton(m_operator, 6).whileTrue(new ArmDown(m_arm));

        new JoystickButton(m_operator, 11).onTrue(new SetArmAngle(ArmConstants.kAmpAngle, m_arm)).onFalse(new SetArmAngle(0, m_arm));
        new JoystickButton(m_operator, 12).onTrue(new SetArmAngle(ArmConstants.kSpeakerAngle, m_arm));
        new JoystickButton(m_operator, 4).onTrue(new SetArmAngle(ArmConstants.kArmHomeAngle, m_arm));

        new JoystickButton(m_operator, 21).whileTrue(new RunIntake(m_arm, m_feeder, m_intake, m_shooter));

        new JoystickButton(m_operator, 2).onTrue(new SetIntakeSpeed(IntakeConstants.kIntakeSpeedFPS, m_intake)).onFalse(new SetIntakeSpeed(0, m_intake));
        new JoystickButton(m_operator, 3).onTrue(new SetShooterSpeed(ShooterConstants.kShooterSpeedRPS, m_shooter)).onFalse(new SetShooterSpeed(0, m_shooter));
        new JoystickButton(m_operator, 7).onTrue(new SetFeederSpeed(20, m_feeder)).onFalse(new SetFeederSpeed(0, m_feeder));
        new JoystickButton(m_operator, 13).onTrue(new SetFeederSpeed(-2, m_feeder).alongWith(new SetIntakeSpeed(-2, m_intake))).onFalse(new SetFeederSpeed(0, m_feeder).alongWith(new SetIntakeSpeed(0, m_intake)));
        new JoystickButton(m_operator, 24).onTrue(new SetFeederSpeed(10, m_feeder).alongWith(new SetShooterSpeed(10, m_shooter))).onFalse(new SetFeederSpeed(0, m_feeder).alongWith(new SetShooterSpeed(0, m_shooter)));

        new JoystickButton(m_operator, 8).onTrue(new ManualShooting(ArmConstants.kSpeakerCloseAngle, ShooterConstants.kShooterSpeedCloseRPS, 3, m_arm, m_shooter, m_intake)).onFalse(new ManualShooting(ArmConstants.kArmHomeAngle, 0, 0, m_arm, m_shooter, m_intake));
        new JoystickButton(m_operator, 9).onTrue(new ManualShooting(ArmConstants.kSpeakerMidAngle, ShooterConstants.kShooterSpeedMidRPS, 3, m_arm, m_shooter, m_intake)).onFalse(new ManualShooting(ArmConstants.kArmHomeAngle, 0, 0, m_arm, m_shooter, m_intake));
        new JoystickButton(m_operator, 10).onTrue(new ManualShooting(ArmConstants.kSpeakerFarAngle, ShooterConstants.kShooterSpeedFarRPS, 3, m_arm, m_shooter, m_intake)).onFalse(new ManualShooting(ArmConstants.kArmHomeAngle, 0, 0, m_arm, m_shooter, m_intake));

        new JoystickButton(m_operator, 18).onTrue(new MoveArmsUp(m_climber));
        new JoystickButton(m_operator, 20).onTrue(new MoveArmsDown(m_climber));

    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return m_chooser.getSelected();
    }
}
