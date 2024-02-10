package frc.robot;

import edu.wpi.first.wpilibj.PS5Controller;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.Shooter.*;
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

    /* Subsystems */
    private final Swerve m_swerve = new Swerve();
    private final Shooter m_shooter = new Shooter();


    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        m_swerve.setDefaultCommand(
            new TeleopSwerve(
                () -> -m_driver.getLeftY(), 
                () -> -m_driver.getLeftX(), 
                () -> -m_driver.getRightX(), 
                () -> m_driver.getR1Button(),
                m_swerve
            )
        );

        m_shooter.setDefaultCommand(new StopShooter(m_shooter));

        // Configure the button bindings
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        /* Driver Buttons */
        new JoystickButton(m_driver, PS5Controller.Button.kOptions.value).onTrue(new ZeroHeading(m_swerve));
        new JoystickButton(m_driver, PS5Controller.Button.kCross.value).whileTrue(new RunShooter(m_shooter));
        new JoystickButton(m_driver, PS5Controller.Button.kCircle.value).whileTrue(new RunShooter(50, m_shooter));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return new WaitCommand(0);
    }
}
