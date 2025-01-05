package org.firstinspires.ftc.teamcode.MCAEaglesPrograms;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.teamcode.MecanumDrive;

import java.util.Vector;

/**
 * Test the dashboard gamepad integration.
 */
@Autonomous(name = "Bucket Strafe Auton", group = "MCA Eagles Programs")
public class bucketStrafeAuton extends LinearOpMode {

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

        // Hardware Map Servos

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
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(-17.14, -60.34, Math.toRadians(270.00)));

        // Define a trajectory.
        Action bucketTrajectory = drive

                // Start position by the driver side wall facing the wall.
                .actionBuilder(new Pose2d(-9.23, -60.23, Math.toRadians(270.00)))

                // Move to bars to hang sample.
                .strafeToLinearHeading(new Vector2d(-7.38, -32.54), Math.toRadians(270.00))

                // Move in front of the sample facing the left wall.
                .strafeTo(new Vector2d(-30.00, -40.62))
                .strafeToLinearHeading(new Vector2d(-39.69, -25.38), Math.toRadians(180.00))

                // Move to the basket facing away from the basket corner.
                .strafeToLinearHeading(new Vector2d(-55.85, -59.77), Math.toRadians(45.00))

                // Move to the next sample facing the left wall.
                .strafeToLinearHeading(new Vector2d(-49.85, -25.85), Math.toRadians(180.00))

                // Move back to basket facing away from the basket.
                .strafeToLinearHeading(new Vector2d(-57.92, -57.92), Math.toRadians(45.00))

                // Move to the last sample facing the left wall. Move away from the wall before moving on to avoid a collision.
                .strafeToLinearHeading(new Vector2d(-60.46, -25.62), Math.toRadians(180.00))
                .strafeTo(new Vector2d(-57.23, -42.00))

                // Move to the basket one last time facing away from the wall.
                .strafeToLinearHeading(new Vector2d(-60.46, -56.0), Math.toRadians(45.00))

                // Move to the ascension zone facing the left wall to do L1 suspension.
                .strafeTo(new Vector2d(-52.62, -11.08))
                .strafeToLinearHeading(new Vector2d(-23.54, 12.23), Math.toRadians(180.00))

                // End trajectory.
                .build();

        // Wait for the start button to be pressed.
        waitForStart();

        // If the stop button is pressed, stop the run function.
        if (isStopRequested()) return;

        // Once the start button is pressed, run the bucket trajectory defined previously.
        Actions.runBlocking(bucketTrajectory);
    }
}
