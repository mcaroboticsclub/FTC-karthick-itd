package org.firstinspires.ftc.teamcode.MCAEaglesPrograms;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.teamcode.MecanumDrive;

/**
 * Test the dashboard gamepad integration.
 */
@Autonomous(name = "Bucket Spline Auton", group = "MCA Eagles Programs")
public class bucketSplineAuton extends LinearOpMode {

    // Define All Motors
    DcMotor frontLeft = null;
    DcMotor frontRight = null;
    DcMotor backLeft = null;
    DcMotor backRight = null;
    DcMotorEx viperVL = null;
    DcMotorEx viperVR = null;
    DcMotorEx viperH = null;

    @Override
    public void runOpMode() throws InterruptedException {

        // Hardware Map All Motors

        // Drivetrain
        frontLeft = hardwareMap.dcMotor.get("Front_Left");
        frontRight = hardwareMap.dcMotor.get("Front_Right");
        backLeft = hardwareMap.dcMotor.get("Back_Left");
        backRight = hardwareMap.dcMotor.get("Back_Right");

        // Vipers
        viperVL = (DcMotorEx) hardwareMap.dcMotor.get("Viper_Vertical_Left");
        viperVR = (DcMotorEx) hardwareMap.dcMotor.get("Viper_Vertical_Right");
        viperH = (DcMotorEx) hardwareMap.dcMotor.get("Viper_Horizontal");

        // Set All Motors To Brake On Stop

        // Drivetrain
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Vipers
        viperVL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperVR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperH.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Reverse Direction Of One Side's Wheel Motors
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        // Create the mecanum drivetrain in roadrunner with the defined hardware map and the initial pose of the robot.
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(-16.15, -60.00, Math.toRadians(270.00)));

        Action bucketTrajectory = drive.actionBuilder(new Pose2d(-16.00, -62.17, Math.toRadians(90.00)))
                .splineToLinearHeading(new Pose2d(-7.54, -33.37, Math.toRadians(270.00)), Math.toRadians(76.57))
                .splineTo(new Vector2d(-32.00, -56.23), Math.toRadians(225.00))
                .splineToLinearHeading(new Pose2d(-36.80, -26.97, Math.toRadians(180.00)), Math.toRadians(102.84))
                .splineToLinearHeading(new Pose2d(-56.46, -56.69, Math.toRadians(45.00)), Math.toRadians(235.09))
                .splineToLinearHeading(new Pose2d(-47.31, -26.51, Math.toRadians(180.00)), Math.toRadians(72.07))
                .splineToLinearHeading(new Pose2d(-58.29, -55.09, Math.toRadians(45.00)), Math.toRadians(250.32))
                .splineToLinearHeading(new Pose2d(-54.63, -26.06, Math.toRadians(180.00)), Math.toRadians(86.40))
                .splineTo(new Vector2d(-48.23, -45.26), Math.toRadians(-71.13))
                .splineToLinearHeading(new Pose2d(-54.63, -58.29, Math.toRadians(45.00)), Math.toRadians(-84.37))
                .splineTo(new Vector2d(-52.80, -13.03), Math.toRadians(90.27))
                .splineToLinearHeading(new Pose2d(-25.83, 10.97, Math.toRadians(180.00)), Math.toRadians(31.46))
                .build();

        // Wait for the start button to be pressed.
        waitForStart();

        // If the stop button is pressed, stop the run function.
        if (isStopRequested()) return;

        // Once the start button is pressed, run the bucket trajectory defined previously.
        Actions.runBlocking(bucketTrajectory);
    }
}
