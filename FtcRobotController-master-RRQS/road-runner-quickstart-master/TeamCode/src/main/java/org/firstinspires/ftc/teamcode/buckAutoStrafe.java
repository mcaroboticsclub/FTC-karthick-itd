package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.*;

/*
 * Test the dashboard gamepad integration.
 */
@Autonomous(name = "Spec + Bucket Strafe", group = "MCA Eagles Group")
public class buckAutoStrafe extends LinearOpMode {
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
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(-15.54, -60.34, Math.toRadians(270.00)));

        // Define the bot's path as an action sequence. Using splines.
        Action bucketTrajectory = drive

                // Start at the initial position against the wall facing the driver side wall.
                .actionBuilder(new Pose2d(-15.54, -60.34, Math.toRadians(270.00)))

                // Move to the submersible to hang a specimen, still facing the driver side wall.
                .strafeToLinearHeading(new Vector2d(-8.46, -34.06), Math.toRadians(270.00))

                // Move to in front of the first sample,
                // right of the rightmost sample on the left side of thè field, facing the sample.
                .strafeTo(new Vector2d(-36.80, -43.89))
                .strafeToLinearHeading(new Vector2d(-37.49, -26.06), Math.toRadians(180.00))

                // Move to the basket and face diagonally away from the basket.
                .strafeToLinearHeading(new Vector2d(-57.37, -59.89), Math.toRadians(45.00))

                // Move to the right of the next sample, facing the sample.
                .strafeToLinearHeading(new Vector2d(-49.14, -26.06), Math.toRadians(180.00))

                // Move to the basket again, facing diagonally away from the basket.
                .strafeToLinearHeading(new Vector2d(-58.74, -58.29), Math.toRadians(45.00))

                // Move to the right of the last sample, facing the last sample.
                // When moving away, move backwards first to avoid hitting the wall.
                .strafeToLinearHeading(new Vector2d(-60.11, -26.06), Math.toRadians(180))
                .strafeTo(new Vector2d(-50.51, -26.97))

                // Move the basket one last time, facing diagonally away from the basket.
                .strafeToLinearHeading(new Vector2d(-59.66, -57.37), Math.toRadians(45.00))

                // Move to the accent zone, turning to face the wall so we can do stage 1 ascent.
                .strafeTo(new Vector2d(-54.40, -11.89))
                .strafeToLinearHeading(new Vector2d(-17.37, 10.06), Math.toRadians(180.00))

                // Build the completed path.
                .build();

        // Wait for the start button to be pressed.
        waitForStart();

        // If the stop button is pressed, stop the run function.
        if (isStopRequested()) return;

        // Once the start button is pressed, run the bucket trajectory defined previously.
        Actions.runBlocking(bucketTrajectory);
    }
}
