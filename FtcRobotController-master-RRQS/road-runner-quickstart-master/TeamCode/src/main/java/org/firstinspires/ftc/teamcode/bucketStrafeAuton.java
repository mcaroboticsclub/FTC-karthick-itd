package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.*;

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
    DcMotorEx viperV = null;
    DcMotorEx viperH = null;
    DcMotor susL = null;
    DcMotor susR = null;
    Servo sClawL = null;
    Servo sClawR = null;
    CRServo intake = null;
    Servo wrist = null;
    Servo bucket = null;
    Servo susGrip = null;

    public void openClaw() {
        sClawL.setPosition(0.4875);
        sClawR.setPosition(0.4625);
    }

    public void closeClaw() {
        sClawL.setPosition(0.3);
        sClawR.setPosition(0.7);
    }

    public void activateIntakeIn() {
        intake.setPower(-1);
    }

    public void activateIntakeOut() {
        intake.setPower(1);
    }

    public void deactivateIntake() {
        intake.setPower(0);
    }

    @Override
    public void runOpMode() throws InterruptedException {

        // Hardware Map All Motors

        // Drivetrain
        frontLeft = hardwareMap.dcMotor.get("Front_Left");
        frontRight = hardwareMap.dcMotor.get("Front_Right");
        backLeft = hardwareMap.dcMotor.get("Back_Left");
        backRight = hardwareMap.dcMotor.get("Back_Right");

        // Vipers
        viperV = (DcMotorEx) hardwareMap.dcMotor.get("Viper_Vertical");
        viperH = (DcMotorEx) hardwareMap.dcMotor.get("Viper_Horizontal");

        // Suspension Actuators
        susL = hardwareMap.dcMotor.get("Suspension_Left");
        susR = hardwareMap.dcMotor.get("Suspension_Right");

        // Hardware Map Servos

        // Suspension Claws
        sClawL = hardwareMap.servo.get("Claw_Left");
        sClawR = hardwareMap.servo.get("Claw_Right");

        // Intake And Wrist
        intake = hardwareMap.crservo.get("Intake");
        wrist = hardwareMap.servo.get("Intake_Wrist");

        // Bucket
        bucket = hardwareMap.servo.get("Bucket");

        // Suspension Axle Lock
        susGrip = hardwareMap.servo.get("Suspension_Grip");

        // Set All Motors To Brake On Stop

        // Drivetrain
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Vipers
        viperV.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperH.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Suspension Actuators
        susL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        susR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Reverse Direction Of One Side's Wheel Motors
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        // Create the mecanum drivetrain in roadrunner with the defined hardware map and the initial pose of the robot.
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(-17.14, -60.34, Math.toRadians(270.00)));

        // Define a trajectory.
        Action bucketTrajectory = drive

                // Start position by the driver side wall facing the wall.
                .actionBuilder(new Pose2d(-17.14, -60.34, Math.toRadians(270.00)))

                // Move to bars to hang sample.
                .strafeToLinearHeading(new Vector2d(-9.83, -32.69), Math.toRadians(270.00))

                // Move in front of the sample facing the left wall.
                .strafeTo(new Vector2d(-35.43, -42.97))
                .strafeToLinearHeading(new Vector2d(-39.54, -26.06), Math.toRadians(180.00))

                // Move to the basket facing away from the basket corner.
                .strafeToLinearHeading(new Vector2d(-59.66, -59.43), Math.toRadians(45.00))

                // Move to the next sample facing the left wall.
                .strafeToLinearHeading(new Vector2d(-49.37, -26.29), Math.toRadians(180.00))

                // Move back to basket facing away from the basket.
                .strafeToLinearHeading(new Vector2d(-59.66, -59.20), Math.toRadians(45.00))

                // Move to the last sample facing the left wall. Move away from the wall before moving on to avoid a collision.
                .strafeToLinearHeading(new Vector2d(-56.00, -26.06), Math.toRadians(180.00))
                .strafeTo(new Vector2d(-51.43, -43.89))

                // Move to the basket one last time facing away from the wall.
                .strafeToLinearHeading(new Vector2d(-59.89, -59.20), Math.toRadians(45.00))

                // Move to the ascension zone facing the left wall to do L1 suspension.
                .strafeTo(new Vector2d(-56.46, -14.17))
                .strafeToLinearHeading(new Vector2d(-24.00, 10.51), Math.toRadians(180.00))

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
